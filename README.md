# React Native Android Guide

This guide walks you through the process of bundling and configuring the Couchbase dependencies in an Android application that's using React Native.

## What you'll build

You'll use the React Native Android starter project to persist documents locally to Couchbase Lite and replicate them to Sync Gateway

![](assets/demo-ui.png)

The Couchbase Lite database URL will contain a randomly generated username and password for security reasons:

```
http://u8io2rhj32cn293nci239:9nc02u39j0ci3ckn29c@localhost:5984/todos
```

and by passing this URL to the JavaScript code, you will persist documents like so:

```
{"type": "task", "text": "Learn React Native"}
```

## Prerequisites

- Node.js 4.0 or higher
- [Android Studio](http://developer.android.com/sdk/installing/studio.html) and an emulator

## Getting Started

To **skip the basics**, do the following:

- Download and unzip the source for this guide or clone it: `git clone https://github.com/jamiltz/reactnative-android-guide`
- cd into `reactnative-android-guide/initial`

## Using CBLModule

The initial project is already using the Native Module called `CBLModule` to instantiate the Couchbase Lite `Manager` object and start the Listener with a random username and password on port `5984`. You can find out more about how Native Modules work by reading this part of the [React Native documentation](http://facebook.github.io/react-native/docs/native-modules-android.html#callbacks). With that in mind, you can now add some JavaScript code to interface with the `CBLModule` and retrieve the database listener URL. At a high level, React Native uses [fetch](https://facebook.github.io/react-native/docs/network.html) for any network request and in this case, you must specify certain fields in the `options` object. For example, to create a document in a database named **todos**, you would do the following:

```js
fetch(localDatabaseUrl + '/todos', {
	method: 'post',
	body: JSON.stringify({text: 'Learn React Native', 'type': 'task'}),
	credentials: 'include'
}
```

In the `componentWillMount` method of **initial/app/components/Home.js**, add the following:

```js
CBLModule.getUrl((err) => {
      console.log('Callback error :: ' + err);
    }, (url) => {
      api.localDatabaseUrl = url.substring(0, url.length - 1);

      api.getTodos()
        .then((res) => {
          var todos = res.rows.map(function (row) {
            return row.doc;
          });
          this.setState({
            todos: todos
          });
        });
    });
```

Here, you're using the `CBLModule` to retrieve the secure URL and then loading the different task items in a ListView. You can open **initial/app/utils/api.js** to see what the `api` object looks like. There are 3 methods:

- `saveTodo`: Saves a new document to the local database
- `getTodos`: Retrieves all the documents from the database (it also creates the database if it doesn't already exist)
- `startSync`: Kicks off a continuous push replication.

Notice in this file that `remoteDatabaseUrl` is **http://localhost:4984**. So in the section, you will set up Sync Gateway locally.

## Adding Sync Gateway

Download Sync Gateway from the link below and unzip the file:

> http://www.couchbase.com/nosql-databases/downloads

In a new file named **sync-gateway-config.json**, paste the following:

```js
{
  "log": ["*"],
  "databases": {
    "todos": {
      "server": "walrus:",
      "users": { "GUEST": { "disabled": false, "admin_channels": ["*"] } }
    }
  }
}
```

And run Sync Gateway with this config file:

```js
~/Downloads/couchbase-sync-gateway/bin/sync_gateway /path/to/project/sync-gateway-config.json
```

To make the Sync Gateway endpoint reachable inside of the Android VM emulator, you need to enable a port from the host to the VM. In Terminal, run the following:

```bash
adb reverse tcp:4984 tcp:4984
```

Open the Admin UI to monitor the documents that were saved to Sync Gateway:

> http://localhost:4985/_admin/

Try adding more task documents and notice how they get pushed to Sync Gateway automatically.

## Summary

Congratulations! You've just got your React Native Android + Couchbase environment set up and you're now ready to make use of all the great of features of React Native such as [Live Reload](https://facebook.github.io/react-native/docs/debugging.html) and [UI Components](https://facebook.github.io/react-native/docs/native-components-android.html#content). Check out the [Listener API](http://developer.couchbase.com/documentation/mobile/1.1.0/develop/references/couchbase-lite/rest-api/index.html) reference to add more functionality to the API object.
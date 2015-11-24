var React = require('react-native');
var couchbase = require('./../utils/couchbase-lite-fetch');
var CBLModule = require('./../utils/CBLModule');

var {
  Text,
  View,
  } = React;

class Home extends React.Component {
  componentWillMount() {
    CBLModule.getUrl((err) => {
      console.log('Callback error :: ' + err);
    }, (url) => {
      var db = new couchbase(url, "todos");
      db.createDatabase()
        .then((res) => {
          db.createDocument({"text": "hello there"})
            .then((res) => {
              console.log(res);
            });
          return res;
        });
    });
  }
  
  render() {
    return (
      <View>
        <Text>Hello</Text>
      </View>
    );
  }

}

module.exports = Home;
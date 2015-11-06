package com.couchbase.todolitereactnativeandroid;

import android.content.Context;
import android.util.Log;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.uimanager.IllegalViewOperationException;

/**
 * Created by jamesnocentini on 05/11/2015.
 */
public class CBLModule extends ReactContextBaseJavaModule {
    private Context mActivityContext;

    private String mListenerURL;

    public CBLModule(ReactApplicationContext reactContext, Context activityContext, String listenerURL) {
        super(reactContext);
        mActivityContext = activityContext;
        mListenerURL = listenerURL;
    }

    @Override
    public String getName() {
        return "CBLModule";
    }

    @ReactMethod
    public void getUrl(Callback errorCallback, Callback successCallback) {
        Log.d(MainActivity.TAG, "Couchbase Lite Listener URL :: " + mListenerURL);
        try {
            successCallback.invoke(mListenerURL);
        } catch (IllegalViewOperationException e) {
            errorCallback.invoke(e.getMessage());
        }

    }



}

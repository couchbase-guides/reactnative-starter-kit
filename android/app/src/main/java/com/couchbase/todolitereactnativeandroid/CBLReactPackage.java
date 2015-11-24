package com.couchbase.todolitereactnativeandroid;

import android.content.Context;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.shell.MainReactPackage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jamesnocentini on 05/11/2015.
 */
public class CBLReactPackage extends MainReactPackage {

    private Context mContext;
    private String mListenerURL;

    CBLReactPackage(Context activityContext, String listenerURL) {
        mContext = activityContext;
        mListenerURL = listenerURL;
    }

    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        List<NativeModule> main = super.createNativeModules(reactContext);
        List<NativeModule> modules = new ArrayList<>();
        modules.addAll(main);
        modules.add(new CBLModule(reactContext, mContext, mListenerURL));
        return modules;

    }

}
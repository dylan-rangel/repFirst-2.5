package com.wix.reactnativenotifications.core;

import android.os.Bundle;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

/* loaded from: classes.dex */
public class JsIOHelper {
    public boolean sendEventToJS(String str, Bundle bundle, ReactContext reactContext) {
        if (reactContext == null) {
            return false;
        }
        sendEventToJS(str, Arguments.fromBundle(bundle), reactContext);
        return true;
    }

    public boolean sendEventToJS(String str, WritableMap writableMap, ReactContext reactContext) {
        if (reactContext == null) {
            return false;
        }
        ((DeviceEventManagerModule.RCTDeviceEventEmitter) reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)).emit(str, writableMap);
        return true;
    }
}

package com.facebook.fbreact.specs;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReactModuleWithSpec;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.turbomodule.core.interfaces.TurboModule;

/* loaded from: classes.dex */
public abstract class NativeAlertManagerSpec extends ReactContextBaseJavaModule implements ReactModuleWithSpec, TurboModule {
    @ReactMethod
    public abstract void alertWithArgs(ReadableMap readableMap, Callback callback);

    public NativeAlertManagerSpec(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
    }
}

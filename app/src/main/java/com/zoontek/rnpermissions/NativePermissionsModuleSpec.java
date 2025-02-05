package com.zoontek.rnpermissions;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReactModuleWithSpec;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.turbomodule.core.interfaces.TurboModule;
import java.util.Map;
import javax.annotation.Nullable;

/* loaded from: classes.dex */
public abstract class NativePermissionsModuleSpec extends ReactContextBaseJavaModule implements ReactModuleWithSpec, TurboModule {
    @ReactMethod
    public abstract void check(String str, Promise promise);

    @ReactMethod
    public abstract void checkLocationAccuracy(Promise promise);

    @ReactMethod
    public abstract void checkMultiple(ReadableArray readableArray, Promise promise);

    @ReactMethod
    public abstract void checkNotifications(Promise promise);

    protected abstract Map<String, Object> getTypedExportedConstants();

    @ReactMethod
    public abstract void openPhotoPicker(Promise promise);

    @ReactMethod
    public abstract void openSettings(Promise promise);

    @ReactMethod
    public abstract void request(String str, Promise promise);

    @ReactMethod
    public abstract void requestLocationAccuracy(String str, Promise promise);

    @ReactMethod
    public abstract void requestMultiple(ReadableArray readableArray, Promise promise);

    @ReactMethod
    public abstract void requestNotifications(ReadableArray readableArray, Promise promise);

    @ReactMethod
    public abstract void shouldShowRequestRationale(String str, Promise promise);

    public NativePermissionsModuleSpec(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
    }

    @Override // com.facebook.react.bridge.BaseJavaModule
    @Nullable
    public final Map<String, Object> getConstants() {
        return getTypedExportedConstants();
    }
}

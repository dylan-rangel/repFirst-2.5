package com.oblador.vectoricons;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

/* loaded from: classes2.dex */
public class VectorIconsModule extends ReactContextBaseJavaModule {

    @interface Errors {
        public static final String E_NOT_IMPLEMENTED = "E_NOT_IMPLEMENTED";
        public static final String E_UNKNOWN_ERROR = "E_UNKNOWN_ERROR";
    }

    @Override // com.facebook.react.bridge.NativeModule
    public String getName() {
        return VectorIconsModuleImpl.NAME;
    }

    VectorIconsModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
    }

    @ReactMethod
    public void getImageForFont(String str, String str2, Integer num, Integer num2, Promise promise) {
        try {
            promise.resolve(VectorIconsModuleImpl.getImageForFont(str, str2, num, num2, getReactApplicationContext()));
        } catch (Throwable th) {
            promise.reject("E_UNKNOWN_ERROR", th);
        }
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getImageForFontSync(String str, String str2, Integer num, Integer num2) {
        try {
            return VectorIconsModuleImpl.getImageForFont(str, str2, num, num2, getReactApplicationContext());
        } catch (Throwable unused) {
            return null;
        }
    }

    @ReactMethod
    public void loadFontWithFileName(String str, String str2, Promise promise) {
        promise.reject(Errors.E_NOT_IMPLEMENTED);
    }
}

package io.invertase.firebase.common;

import android.app.Activity;
import android.content.Context;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import io.invertase.firebase.interfaces.ContextProvider;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import net.openid.appauth.ResponseTypeValues;

/* loaded from: classes3.dex */
public class ReactNativeFirebaseModule extends ReactContextBaseJavaModule implements ContextProvider {
    private final TaskExecutorService executorService;
    private String moduleName;

    public ReactNativeFirebaseModule(ReactApplicationContext reactApplicationContext, String str) {
        super(reactApplicationContext);
        this.moduleName = str;
        this.executorService = new TaskExecutorService(getName());
    }

    public static void rejectPromiseWithExceptionMap(Promise promise, Exception exc) {
        promise.reject(exc, SharedUtils.getExceptionMap(exc));
    }

    public static void rejectPromiseWithCodeAndMessage(Promise promise, String str, String str2, ReadableMap readableMap) {
        WritableMap createMap = Arguments.createMap();
        createMap.putString(ResponseTypeValues.CODE, str);
        createMap.putString("message", str2);
        if (readableMap != null) {
            createMap.putMap("resolver", readableMap);
        }
        promise.reject(str, str2, createMap);
    }

    public static void rejectPromiseWithCodeAndMessage(Promise promise, String str, String str2) {
        WritableMap createMap = Arguments.createMap();
        createMap.putString(ResponseTypeValues.CODE, str);
        createMap.putString("message", str2);
        promise.reject(str, str2, createMap);
    }

    public static void rejectPromiseWithCodeAndMessage(Promise promise, String str, String str2, String str3) {
        WritableMap createMap = Arguments.createMap();
        createMap.putString(ResponseTypeValues.CODE, str);
        createMap.putString("message", str2);
        createMap.putString("nativeErrorMessage", str3);
        promise.reject(str, str2, createMap);
    }

    @Override // com.facebook.react.bridge.BaseJavaModule, com.facebook.react.bridge.NativeModule, com.facebook.react.turbomodule.core.interfaces.TurboModule
    public void initialize() {
        super.initialize();
    }

    @Override // io.invertase.firebase.interfaces.ContextProvider
    public ReactContext getContext() {
        return getReactApplicationContext();
    }

    public final ExecutorService getExecutor() {
        return this.executorService.getExecutor();
    }

    public final ExecutorService getTransactionalExecutor() {
        return this.executorService.getTransactionalExecutor();
    }

    public final ExecutorService getTransactionalExecutor(String str) {
        return this.executorService.getTransactionalExecutor(str);
    }

    @Override // com.facebook.react.bridge.BaseJavaModule, com.facebook.react.bridge.NativeModule
    public void onCatalystInstanceDestroy() {
        this.executorService.shutdown();
    }

    public final void removeEventListeningExecutor(String str) {
        this.executorService.removeExecutor(this.executorService.getExecutorName(true, str));
    }

    @Override // io.invertase.firebase.interfaces.ContextProvider
    public Context getApplicationContext() {
        return getReactApplicationContext().getApplicationContext();
    }

    @Override // io.invertase.firebase.interfaces.ContextProvider
    public Activity getActivity() {
        return getCurrentActivity();
    }

    @Override // com.facebook.react.bridge.NativeModule
    public String getName() {
        return "RNFB" + this.moduleName + "Module";
    }

    @Override // com.facebook.react.bridge.BaseJavaModule
    public Map<String, Object> getConstants() {
        return new HashMap();
    }
}

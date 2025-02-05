package com.zoontek.rnpermissions;

import android.Manifest;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Process;
import android.util.SparseArray;
import androidx.core.app.NotificationManagerCompat;
import com.facebook.common.logging.FLog;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.modules.core.PermissionAwareActivity;
import com.facebook.react.modules.core.PermissionListener;
import io.sentry.protocol.SentryStackFrame;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@ReactModule(name = RNPermissionsModule.NAME)
/* loaded from: classes.dex */
public class RNPermissionsModule extends NativePermissionsModuleSpec implements PermissionListener {
    private static final String ERROR_INVALID_ACTIVITY = "E_INVALID_ACTIVITY";
    public static final String NAME = "RNPermissionsModule";
    private final String BLOCKED;
    private final String DENIED;
    private final String GRANTED;
    private final String UNAVAILABLE;
    private final SparseArray<Callback> mCallbacks;
    private int mRequestCode;

    @Override // com.facebook.react.bridge.NativeModule
    public String getName() {
        return NAME;
    }

    public RNPermissionsModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
        this.mRequestCode = 0;
        this.GRANTED = "granted";
        this.DENIED = "denied";
        this.UNAVAILABLE = "unavailable";
        this.BLOCKED = "blocked";
        this.mCallbacks = new SparseArray<>();
    }

    private boolean isPermissionUnavailable(String str) {
        try {
            Manifest.permission.class.getField(str.replace("android.permission.", "").replace("com.android.voicemail.permission.", ""));
            return false;
        } catch (NoSuchFieldException unused) {
            return true;
        }
    }

    private WritableMap getLegacyNotificationsResponse(String str) {
        boolean areNotificationsEnabled = NotificationManagerCompat.from(getReactApplicationContext()).areNotificationsEnabled();
        WritableMap createMap = Arguments.createMap();
        WritableMap createMap2 = Arguments.createMap();
        if (areNotificationsEnabled) {
            str = "granted";
        }
        createMap.putString("status", str);
        createMap.putMap("settings", createMap2);
        return createMap;
    }

    @Override // com.zoontek.rnpermissions.NativePermissionsModuleSpec
    @ReactMethod
    public void checkNotifications(Promise promise) {
        promise.resolve(getLegacyNotificationsResponse("denied"));
    }

    @Override // com.zoontek.rnpermissions.NativePermissionsModuleSpec
    public void requestNotifications(ReadableArray readableArray, Promise promise) {
        promise.resolve(getLegacyNotificationsResponse("blocked"));
    }

    @Override // com.zoontek.rnpermissions.NativePermissionsModuleSpec
    @ReactMethod
    public void openSettings(Promise promise) {
        try {
            ReactApplicationContext reactApplicationContext = getReactApplicationContext();
            Intent intent = new Intent();
            String packageName = reactApplicationContext.getPackageName();
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.addFlags(268435456);
            intent.setData(Uri.fromParts(SentryStackFrame.JsonKeys.PACKAGE, packageName, null));
            reactApplicationContext.startActivity(intent);
            promise.resolve(true);
        } catch (Exception e) {
            promise.reject(ERROR_INVALID_ACTIVITY, e);
        }
    }

    @Override // com.zoontek.rnpermissions.NativePermissionsModuleSpec
    @ReactMethod
    public void check(String str, Promise promise) {
        if (str == null || isPermissionUnavailable(str)) {
            promise.resolve("unavailable");
            return;
        }
        Context baseContext = getReactApplicationContext().getBaseContext();
        if (Build.VERSION.SDK_INT < 23) {
            promise.resolve(baseContext.checkPermission(str, Process.myPid(), Process.myUid()) != 0 ? "blocked" : "granted");
        } else if (baseContext.checkSelfPermission(str) == 0) {
            promise.resolve("granted");
        } else {
            promise.resolve("denied");
        }
    }

    @Override // com.zoontek.rnpermissions.NativePermissionsModuleSpec
    @ReactMethod
    public void shouldShowRequestRationale(String str, Promise promise) {
        if (str == null || Build.VERSION.SDK_INT < 23) {
            promise.resolve(false);
            return;
        }
        try {
            promise.resolve(Boolean.valueOf(getPermissionAwareActivity().shouldShowRequestPermissionRationale(str)));
        } catch (IllegalStateException e) {
            promise.reject(ERROR_INVALID_ACTIVITY, e);
        }
    }

    @Override // com.zoontek.rnpermissions.NativePermissionsModuleSpec
    @ReactMethod
    public void request(final String str, final Promise promise) {
        if (str == null || isPermissionUnavailable(str)) {
            promise.resolve("unavailable");
            return;
        }
        Context baseContext = getReactApplicationContext().getBaseContext();
        if (Build.VERSION.SDK_INT < 23) {
            promise.resolve(baseContext.checkPermission(str, Process.myPid(), Process.myUid()) != 0 ? "blocked" : "granted");
            return;
        }
        if (baseContext.checkSelfPermission(str) == 0) {
            promise.resolve("granted");
            return;
        }
        try {
            PermissionAwareActivity permissionAwareActivity = getPermissionAwareActivity();
            this.mCallbacks.put(this.mRequestCode, new Callback() { // from class: com.zoontek.rnpermissions.RNPermissionsModule.1
                @Override // com.facebook.react.bridge.Callback
                public void invoke(Object... objArr) {
                    int[] iArr = (int[]) objArr[0];
                    if (iArr.length > 0 && iArr[0] == 0) {
                        promise.resolve("granted");
                    } else if (((PermissionAwareActivity) objArr[1]).shouldShowRequestPermissionRationale(str)) {
                        promise.resolve("denied");
                    } else {
                        promise.resolve("blocked");
                    }
                }
            });
            permissionAwareActivity.requestPermissions(new String[]{str}, this.mRequestCode, this);
            this.mRequestCode++;
        } catch (IllegalStateException e) {
            promise.reject(ERROR_INVALID_ACTIVITY, e);
        }
    }

    @Override // com.zoontek.rnpermissions.NativePermissionsModuleSpec
    @ReactMethod
    public void checkMultiple(ReadableArray readableArray, Promise promise) {
        WritableNativeMap writableNativeMap = new WritableNativeMap();
        Context baseContext = getReactApplicationContext().getBaseContext();
        for (int i = 0; i < readableArray.size(); i++) {
            String string = readableArray.getString(i);
            if (isPermissionUnavailable(string)) {
                writableNativeMap.putString(string, "unavailable");
            } else if (Build.VERSION.SDK_INT < 23) {
                writableNativeMap.putString(string, baseContext.checkPermission(string, Process.myPid(), Process.myUid()) != 0 ? "blocked" : "granted");
            } else if (baseContext.checkSelfPermission(string) == 0) {
                writableNativeMap.putString(string, "granted");
            } else {
                writableNativeMap.putString(string, "denied");
            }
        }
        promise.resolve(writableNativeMap);
    }

    @Override // com.zoontek.rnpermissions.NativePermissionsModuleSpec
    @ReactMethod
    public void requestMultiple(ReadableArray readableArray, final Promise promise) {
        final WritableNativeMap writableNativeMap = new WritableNativeMap();
        final ArrayList arrayList = new ArrayList();
        Context baseContext = getReactApplicationContext().getBaseContext();
        int i = 0;
        for (int i2 = 0; i2 < readableArray.size(); i2++) {
            String string = readableArray.getString(i2);
            if (isPermissionUnavailable(string)) {
                writableNativeMap.putString(string, "unavailable");
            } else if (Build.VERSION.SDK_INT < 23) {
                writableNativeMap.putString(string, baseContext.checkPermission(string, Process.myPid(), Process.myUid()) != 0 ? "blocked" : "granted");
            } else if (baseContext.checkSelfPermission(string) == 0) {
                writableNativeMap.putString(string, "granted");
            } else {
                arrayList.add(string);
            }
            i++;
        }
        if (readableArray.size() == i) {
            promise.resolve(writableNativeMap);
            return;
        }
        try {
            PermissionAwareActivity permissionAwareActivity = getPermissionAwareActivity();
            this.mCallbacks.put(this.mRequestCode, new Callback() { // from class: com.zoontek.rnpermissions.RNPermissionsModule.2
                @Override // com.facebook.react.bridge.Callback
                public void invoke(Object... objArr) {
                    int[] iArr = (int[]) objArr[0];
                    PermissionAwareActivity permissionAwareActivity2 = (PermissionAwareActivity) objArr[1];
                    for (int i3 = 0; i3 < arrayList.size(); i3++) {
                        String str = (String) arrayList.get(i3);
                        if (iArr.length > 0 && iArr[i3] == 0) {
                            writableNativeMap.putString(str, "granted");
                        } else if (permissionAwareActivity2.shouldShowRequestPermissionRationale(str)) {
                            writableNativeMap.putString(str, "denied");
                        } else {
                            writableNativeMap.putString(str, "blocked");
                        }
                    }
                    promise.resolve(writableNativeMap);
                }
            });
            permissionAwareActivity.requestPermissions((String[]) arrayList.toArray(new String[0]), this.mRequestCode, this);
            this.mRequestCode++;
        } catch (IllegalStateException e) {
            promise.reject(ERROR_INVALID_ACTIVITY, e);
        }
    }

    @Override // com.zoontek.rnpermissions.NativePermissionsModuleSpec
    protected Map<String, Object> getTypedExportedConstants() {
        return new HashMap();
    }

    @Override // com.zoontek.rnpermissions.NativePermissionsModuleSpec
    public void checkLocationAccuracy(Promise promise) {
        promise.reject("Permissions:checkLocationAccuracy", "checkLocationAccuracy is not supported on Android");
    }

    @Override // com.zoontek.rnpermissions.NativePermissionsModuleSpec
    public void requestLocationAccuracy(String str, Promise promise) {
        promise.reject("Permissions:requestLocationAccuracy", "requestLocationAccuracy is not supported on Android");
    }

    @Override // com.zoontek.rnpermissions.NativePermissionsModuleSpec
    public void openPhotoPicker(Promise promise) {
        promise.reject("Permissions:openPhotoPicker", "openPhotoPicker is not supported on Android");
    }

    @Override // com.facebook.react.modules.core.PermissionListener
    public boolean onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        try {
            this.mCallbacks.get(i).invoke(iArr, getPermissionAwareActivity());
            this.mCallbacks.remove(i);
            return this.mCallbacks.size() == 0;
        } catch (IllegalStateException e) {
            FLog.e("PermissionsModule", e, "Unexpected invocation of `onRequestPermissionsResult` with invalid current activity", new Object[0]);
            return false;
        } catch (NullPointerException e2) {
            FLog.e("PermissionsModule", e2, "Unexpected invocation of `onRequestPermissionsResult` with invalid request code", new Object[0]);
            return false;
        }
    }

    private PermissionAwareActivity getPermissionAwareActivity() {
        ComponentCallbacks2 currentActivity = getCurrentActivity();
        if (currentActivity == null) {
            throw new IllegalStateException("Tried to use permissions API while not attached to an Activity.");
        }
        if (!(currentActivity instanceof PermissionAwareActivity)) {
            throw new IllegalStateException("Tried to use permissions API but the host Activity doesn't implement PermissionAwareActivity.");
        }
        return (PermissionAwareActivity) currentActivity;
    }
}

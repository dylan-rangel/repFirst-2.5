package com.reactnativecommunity.geolocation;

import android.os.Build;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.JavaOnlyArray;
import com.facebook.react.bridge.PromiseImpl;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.modules.permissions.PermissionsModule;
import com.google.android.gms.common.GoogleApiAvailability;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public class GeolocationModule extends ReactContextBaseJavaModule {
    public static final String NAME = "RNCGeolocation";
    private Configuration mConfiguration;
    private BaseLocationManager mLocationManager;

    @Override // com.facebook.react.bridge.NativeModule
    public String getName() {
        return "RNCGeolocation";
    }

    public GeolocationModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
        this.mConfiguration = Configuration.getDefault();
        this.mLocationManager = new AndroidLocationManager(reactApplicationContext);
    }

    public void setConfiguration(ReadableMap readableMap) {
        Configuration fromReactMap = Configuration.fromReactMap(readableMap);
        this.mConfiguration = fromReactMap;
        onConfigurationChange(fromReactMap);
    }

    private void onConfigurationChange(Configuration configuration) {
        ReactApplicationContext reactApplicationContext = this.mLocationManager.mReactContext;
        if (Objects.equals(configuration.locationProvider, "android") && (this.mLocationManager instanceof PlayServicesLocationManager)) {
            this.mLocationManager = new AndroidLocationManager(reactApplicationContext);
        } else if (Objects.equals(configuration.locationProvider, "playServices") && (this.mLocationManager instanceof AndroidLocationManager) && new GoogleApiAvailability().isGooglePlayServicesAvailable(reactApplicationContext.getApplicationContext()) == 0) {
            this.mLocationManager = new PlayServicesLocationManager(reactApplicationContext);
        }
    }

    public void requestAuthorization(final Callback callback, final Callback callback2) {
        if (Build.VERSION.SDK_INT >= 23) {
            final PermissionsModule permissionsModule = (PermissionsModule) getReactApplicationContext().getNativeModule(PermissionsModule.class);
            ArrayList arrayList = new ArrayList();
            arrayList.add("android.permission.ACCESS_COARSE_LOCATION");
            arrayList.add("android.permission.ACCESS_FINE_LOCATION");
            final JavaOnlyArray from = JavaOnlyArray.from(arrayList);
            final Callback callback3 = new Callback() { // from class: com.reactnativecommunity.geolocation.GeolocationModule$$ExternalSyntheticLambda0
                @Override // com.facebook.react.bridge.Callback
                public final void invoke(Object[] objArr) {
                    GeolocationModule.lambda$requestAuthorization$0(Callback.this, callback2, objArr);
                }
            };
            final Callback callback4 = new Callback() { // from class: com.reactnativecommunity.geolocation.GeolocationModule$$ExternalSyntheticLambda1
                @Override // com.facebook.react.bridge.Callback
                public final void invoke(Object[] objArr) {
                    Callback.this.invoke(PositionError.buildError(PositionError.PERMISSION_DENIED, "Failed to request location permission."));
                }
            };
            final Callback callback5 = new Callback() { // from class: com.reactnativecommunity.geolocation.GeolocationModule$$ExternalSyntheticLambda2
                @Override // com.facebook.react.bridge.Callback
                public final void invoke(Object[] objArr) {
                    Callback.this.invoke(PositionError.buildError(PositionError.PERMISSION_DENIED, "Failed to check location permission."));
                }
            };
            final Callback callback6 = new Callback() { // from class: com.reactnativecommunity.geolocation.GeolocationModule$$ExternalSyntheticLambda3
                @Override // com.facebook.react.bridge.Callback
                public final void invoke(Object[] objArr) {
                    GeolocationModule.lambda$requestAuthorization$3(PermissionsModule.this, from, callback3, callback4, callback, objArr);
                }
            };
            permissionsModule.checkPermission("android.permission.ACCESS_FINE_LOCATION", new PromiseImpl(callback6, new Callback() { // from class: com.reactnativecommunity.geolocation.GeolocationModule$$ExternalSyntheticLambda4
                @Override // com.facebook.react.bridge.Callback
                public final void invoke(Object[] objArr) {
                    PermissionsModule.this.checkPermission("android.permission.ACCESS_COARSE_LOCATION", new PromiseImpl(callback6, callback5));
                }
            }));
            return;
        }
        callback.invoke(new Object[0]);
    }

    static /* synthetic */ void lambda$requestAuthorization$0(Callback callback, Callback callback2, Object[] objArr) {
        if (((WritableNativeMap) objArr[0]).getString("android.permission.ACCESS_COARSE_LOCATION").equals("granted")) {
            callback.invoke(new Object[0]);
        } else {
            callback2.invoke(PositionError.buildError(PositionError.PERMISSION_DENIED, "Location permission was not granted."));
        }
    }

    static /* synthetic */ void lambda$requestAuthorization$3(PermissionsModule permissionsModule, ReadableArray readableArray, Callback callback, Callback callback2, Callback callback3, Object[] objArr) {
        if (!((Boolean) objArr[0]).booleanValue()) {
            permissionsModule.requestMultiplePermissions(readableArray, new PromiseImpl(callback, callback2));
        } else {
            callback3.invoke(new Object[0]);
        }
    }

    public void getCurrentPosition(final ReadableMap readableMap, final Callback callback, final Callback callback2) {
        try {
            if (this.mConfiguration.skipPermissionRequests.booleanValue()) {
                this.mLocationManager.getCurrentLocationData(readableMap, callback, callback2);
            } else {
                requestAuthorization(new Callback() { // from class: com.reactnativecommunity.geolocation.GeolocationModule$$ExternalSyntheticLambda7
                    @Override // com.facebook.react.bridge.Callback
                    public final void invoke(Object[] objArr) {
                        GeolocationModule.this.m170x5af86fc7(readableMap, callback, callback2, objArr);
                    }
                }, callback2);
            }
        } catch (SecurityException e) {
            throwLocationPermissionMissing(e);
        }
    }

    /* renamed from: lambda$getCurrentPosition$5$com-reactnativecommunity-geolocation-GeolocationModule, reason: not valid java name */
    /* synthetic */ void m170x5af86fc7(ReadableMap readableMap, Callback callback, Callback callback2, Object[] objArr) {
        this.mLocationManager.getCurrentLocationData(readableMap, callback, callback2);
    }

    public void startObserving(final ReadableMap readableMap) {
        try {
            if (this.mConfiguration.skipPermissionRequests.booleanValue()) {
                this.mLocationManager.startObserving(readableMap);
            } else {
                requestAuthorization(new Callback() { // from class: com.reactnativecommunity.geolocation.GeolocationModule$$ExternalSyntheticLambda5
                    @Override // com.facebook.react.bridge.Callback
                    public final void invoke(Object[] objArr) {
                        GeolocationModule.this.m171x973fed7b(readableMap, objArr);
                    }
                }, new Callback() { // from class: com.reactnativecommunity.geolocation.GeolocationModule$$ExternalSyntheticLambda6
                    @Override // com.facebook.react.bridge.Callback
                    public final void invoke(Object[] objArr) {
                        GeolocationModule.lambda$startObserving$7(objArr);
                    }
                });
            }
        } catch (SecurityException e) {
            throwLocationPermissionMissing(e);
        }
    }

    /* renamed from: lambda$startObserving$6$com-reactnativecommunity-geolocation-GeolocationModule, reason: not valid java name */
    /* synthetic */ void m171x973fed7b(ReadableMap readableMap, Object[] objArr) {
        this.mLocationManager.startObserving(readableMap);
    }

    static /* synthetic */ void lambda$startObserving$7(Object[] objArr) {
        throw new SecurityException(objArr.toString());
    }

    public void stopObserving() {
        this.mLocationManager.stopObserving();
    }

    private static void throwLocationPermissionMissing(SecurityException securityException) {
        throw new SecurityException("Looks like the app doesn't have the permission to access location.\nAdd the following line to your app's AndroidManifest.xml:\n<uses-permission android:name=\"android.permission.ACCESS_FINE_LOCATION\" />", securityException);
    }

    private static class Configuration {
        String locationProvider;
        Boolean skipPermissionRequests;

        private Configuration(String str, boolean z) {
            this.locationProvider = str;
            this.skipPermissionRequests = Boolean.valueOf(z);
        }

        protected static Configuration getDefault() {
            return new Configuration("auto", false);
        }

        protected static Configuration fromReactMap(ReadableMap readableMap) {
            return new Configuration(readableMap.hasKey("locationProvider") ? readableMap.getString("locationProvider") : "auto", readableMap.hasKey("skipPermissionRequests") ? readableMap.getBoolean("skipPermissionRequests") : false);
        }
    }
}

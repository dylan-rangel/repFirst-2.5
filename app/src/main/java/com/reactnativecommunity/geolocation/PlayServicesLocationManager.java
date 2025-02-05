package com.reactnativecommunity.geolocation;

import android.app.Activity;
import android.location.Location;
import android.os.Looper;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.common.SystemClock;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.reactnativecommunity.geolocation.BaseLocationManager;

/* loaded from: classes2.dex */
public class PlayServicesLocationManager extends BaseLocationManager {
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;
    private SettingsClient mLocationServicesSettingsClient;
    private LocationCallback mSingleLocationCallback;

    protected PlayServicesLocationManager(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
        this.mFusedLocationClient = LocationServices.getFusedLocationProviderClient(reactApplicationContext);
        this.mLocationServicesSettingsClient = LocationServices.getSettingsClient(reactApplicationContext);
    }

    @Override // com.reactnativecommunity.geolocation.BaseLocationManager
    public void getCurrentLocationData(final ReadableMap readableMap, final Callback callback, final Callback callback2) {
        final BaseLocationManager.LocationOptions fromReactMap = BaseLocationManager.LocationOptions.fromReactMap(readableMap);
        Activity currentActivity = this.mReactContext.getCurrentActivity();
        if (currentActivity == null) {
            LocationCallback createSingleLocationCallback = createSingleLocationCallback(callback, callback2);
            this.mSingleLocationCallback = createSingleLocationCallback;
            checkLocationSettings(readableMap, createSingleLocationCallback);
        } else {
            try {
                this.mFusedLocationClient.getLastLocation().addOnSuccessListener(currentActivity, new OnSuccessListener() { // from class: com.reactnativecommunity.geolocation.PlayServicesLocationManager$$ExternalSyntheticLambda0
                    @Override // com.google.android.gms.tasks.OnSuccessListener
                    public final void onSuccess(Object obj) {
                        PlayServicesLocationManager.this.m174xe571d96a(fromReactMap, callback, callback2, readableMap, (Location) obj);
                    }
                });
            } catch (SecurityException e) {
                throw e;
            }
        }
    }

    /* renamed from: lambda$getCurrentLocationData$0$com-reactnativecommunity-geolocation-PlayServicesLocationManager, reason: not valid java name */
    /* synthetic */ void m174xe571d96a(BaseLocationManager.LocationOptions locationOptions, Callback callback, Callback callback2, ReadableMap readableMap, Location location) {
        if (location != null && SystemClock.currentTimeMillis() - location.getTime() < locationOptions.maximumAge) {
            callback.invoke(locationToMap(location));
            return;
        }
        LocationCallback createSingleLocationCallback = createSingleLocationCallback(callback, callback2);
        this.mSingleLocationCallback = createSingleLocationCallback;
        checkLocationSettings(readableMap, createSingleLocationCallback);
    }

    @Override // com.reactnativecommunity.geolocation.BaseLocationManager
    public void startObserving(ReadableMap readableMap) {
        LocationCallback locationCallback = new LocationCallback() { // from class: com.reactnativecommunity.geolocation.PlayServicesLocationManager.1
            @Override // com.google.android.gms.location.LocationCallback
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    PlayServicesLocationManager.this.emitError(PositionError.POSITION_UNAVAILABLE, "No location provided (FusedLocationProvider/observer).");
                } else {
                    ((DeviceEventManagerModule.RCTDeviceEventEmitter) PlayServicesLocationManager.this.mReactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)).emit("geolocationDidChange", BaseLocationManager.locationToMap(locationResult.getLastLocation()));
                }
            }

            @Override // com.google.android.gms.location.LocationCallback
            public void onLocationAvailability(LocationAvailability locationAvailability) {
                if (locationAvailability.isLocationAvailable()) {
                    return;
                }
                PlayServicesLocationManager.this.emitError(PositionError.POSITION_UNAVAILABLE, "Location not available (FusedLocationProvider).");
            }
        };
        this.mLocationCallback = locationCallback;
        checkLocationSettings(readableMap, locationCallback);
    }

    @Override // com.reactnativecommunity.geolocation.BaseLocationManager
    public void stopObserving() {
        this.mFusedLocationClient.removeLocationUpdates(this.mLocationCallback);
    }

    private void checkLocationSettings(ReadableMap readableMap, final LocationCallback locationCallback) {
        BaseLocationManager.LocationOptions fromReactMap = BaseLocationManager.LocationOptions.fromReactMap(readableMap);
        final LocationRequest create = LocationRequest.create();
        create.setInterval(fromReactMap.interval);
        if (fromReactMap.fastestInterval >= 0) {
            create.setFastestInterval(fromReactMap.fastestInterval);
        }
        create.setExpirationDuration((long) fromReactMap.maximumAge);
        if (fromReactMap.distanceFilter >= 0.0f) {
            create.setSmallestDisplacement(fromReactMap.distanceFilter);
        }
        create.setPriority(fromReactMap.highAccuracy ? 100 : 104);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(create);
        this.mLocationServicesSettingsClient.checkLocationSettings(builder.build()).addOnSuccessListener(new OnSuccessListener() { // from class: com.reactnativecommunity.geolocation.PlayServicesLocationManager$$ExternalSyntheticLambda1
            @Override // com.google.android.gms.tasks.OnSuccessListener
            public final void onSuccess(Object obj) {
                PlayServicesLocationManager.this.m172xa11ff365(create, locationCallback, (LocationSettingsResponse) obj);
            }
        }).addOnFailureListener(new OnFailureListener() { // from class: com.reactnativecommunity.geolocation.PlayServicesLocationManager$$ExternalSyntheticLambda2
            @Override // com.google.android.gms.tasks.OnFailureListener
            public final void onFailure(Exception exc) {
                PlayServicesLocationManager.this.m173xb4f7b84(exc);
            }
        });
    }

    /* renamed from: lambda$checkLocationSettings$1$com-reactnativecommunity-geolocation-PlayServicesLocationManager, reason: not valid java name */
    /* synthetic */ void m172xa11ff365(LocationRequest locationRequest, LocationCallback locationCallback, LocationSettingsResponse locationSettingsResponse) {
        requestLocationUpdates(locationRequest, locationCallback);
    }

    /* renamed from: lambda$checkLocationSettings$2$com-reactnativecommunity-geolocation-PlayServicesLocationManager, reason: not valid java name */
    /* synthetic */ void m173xb4f7b84(Exception exc) {
        emitError(PositionError.POSITION_UNAVAILABLE, "Location not available (FusedLocationProvider/settings).");
    }

    private void requestLocationUpdates(LocationRequest locationRequest, LocationCallback locationCallback) {
        try {
            this.mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
        } catch (SecurityException e) {
            throw e;
        }
    }

    private LocationCallback createSingleLocationCallback(final Callback callback, final Callback callback2) {
        return new LocationCallback() { // from class: com.reactnativecommunity.geolocation.PlayServicesLocationManager.2
            @Override // com.google.android.gms.location.LocationCallback
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    callback2.invoke(PositionError.buildError(PositionError.POSITION_UNAVAILABLE, "No location provided (FusedLocationProvider/lastLocation)."));
                    return;
                }
                callback.invoke(BaseLocationManager.locationToMap(locationResult.getLastLocation()));
                PlayServicesLocationManager.this.mFusedLocationClient.removeLocationUpdates(PlayServicesLocationManager.this.mSingleLocationCallback);
                PlayServicesLocationManager.this.mSingleLocationCallback = null;
            }

            @Override // com.google.android.gms.location.LocationCallback
            public void onLocationAvailability(LocationAvailability locationAvailability) {
                if (locationAvailability.isLocationAvailable()) {
                    return;
                }
                callback2.invoke(PositionError.buildError(PositionError.POSITION_UNAVAILABLE, "Location not available (FusedLocationProvider/lastLocation)."));
            }
        };
    }
}

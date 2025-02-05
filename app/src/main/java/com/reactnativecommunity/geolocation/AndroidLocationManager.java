package com.reactnativecommunity.geolocation;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import androidx.core.content.ContextCompat;
import com.facebook.common.logging.FLog;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.common.ReactConstants;
import com.facebook.react.common.SystemClock;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.reactnativecommunity.geolocation.BaseLocationManager;
import javax.annotation.Nullable;

/* loaded from: classes2.dex */
public class AndroidLocationManager extends BaseLocationManager {
    private final LocationListener mLocationListener;

    @Nullable
    private String mWatchedProvider;

    protected AndroidLocationManager(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
        this.mLocationListener = new LocationListener() { // from class: com.reactnativecommunity.geolocation.AndroidLocationManager.1
            @Override // android.location.LocationListener
            public void onProviderDisabled(String str) {
            }

            @Override // android.location.LocationListener
            public void onProviderEnabled(String str) {
            }

            @Override // android.location.LocationListener
            public void onLocationChanged(Location location) {
                ((DeviceEventManagerModule.RCTDeviceEventEmitter) AndroidLocationManager.this.mReactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)).emit("geolocationDidChange", BaseLocationManager.locationToMap(location));
            }

            @Override // android.location.LocationListener
            public void onStatusChanged(String str, int i, Bundle bundle) {
                if (i == 0) {
                    AndroidLocationManager.this.emitError(PositionError.POSITION_UNAVAILABLE, "Provider " + str + " is out of service.");
                    return;
                }
                if (i == 1) {
                    AndroidLocationManager.this.emitError(PositionError.TIMEOUT, "Provider " + str + " is temporarily unavailable.");
                }
            }
        };
    }

    @Override // com.reactnativecommunity.geolocation.BaseLocationManager
    public void getCurrentLocationData(ReadableMap readableMap, Callback callback, Callback callback2) {
        BaseLocationManager.LocationOptions fromReactMap = BaseLocationManager.LocationOptions.fromReactMap(readableMap);
        try {
            LocationManager locationManager = (LocationManager) this.mReactContext.getSystemService(FirebaseAnalytics.Param.LOCATION);
            String validProvider = getValidProvider(locationManager, fromReactMap.highAccuracy);
            if (validProvider == null) {
                callback2.invoke(PositionError.buildError(PositionError.POSITION_UNAVAILABLE, "No location provider available."));
                return;
            }
            Location lastKnownLocation = locationManager.getLastKnownLocation(validProvider);
            if (lastKnownLocation == null || SystemClock.currentTimeMillis() - lastKnownLocation.getTime() >= fromReactMap.maximumAge) {
                new SingleUpdateRequest(locationManager, validProvider, fromReactMap.timeout, callback, callback2).invoke(lastKnownLocation);
            } else {
                callback.invoke(locationToMap(lastKnownLocation));
            }
        } catch (SecurityException e) {
            throw e;
        }
    }

    @Override // com.reactnativecommunity.geolocation.BaseLocationManager
    public void startObserving(ReadableMap readableMap) {
        if ("gps".equals(this.mWatchedProvider)) {
            return;
        }
        BaseLocationManager.LocationOptions fromReactMap = BaseLocationManager.LocationOptions.fromReactMap(readableMap);
        try {
            LocationManager locationManager = (LocationManager) this.mReactContext.getSystemService(FirebaseAnalytics.Param.LOCATION);
            String validProvider = getValidProvider(locationManager, fromReactMap.highAccuracy);
            if (validProvider == null) {
                emitError(PositionError.POSITION_UNAVAILABLE, "No location provider available.");
                return;
            }
            if (!validProvider.equals(this.mWatchedProvider)) {
                locationManager.removeUpdates(this.mLocationListener);
                locationManager.requestLocationUpdates(validProvider, 1000L, fromReactMap.distanceFilter, this.mLocationListener);
            }
            this.mWatchedProvider = validProvider;
        } catch (SecurityException e) {
            throw e;
        }
    }

    @Override // com.reactnativecommunity.geolocation.BaseLocationManager
    public void stopObserving() {
        ((LocationManager) this.mReactContext.getSystemService(FirebaseAnalytics.Param.LOCATION)).removeUpdates(this.mLocationListener);
        this.mWatchedProvider = null;
    }

    @Nullable
    private String getValidProvider(LocationManager locationManager, boolean z) {
        String str = z ? "gps" : "network";
        if (!locationManager.isProviderEnabled(str)) {
            String str2 = str.equals("gps") ? "network" : "gps";
            if (!locationManager.isProviderEnabled(str2)) {
                return null;
            }
            str = str2;
        }
        int checkSelfPermission = ContextCompat.checkSelfPermission(this.mReactContext, "android.permission.ACCESS_FINE_LOCATION");
        int checkSelfPermission2 = ContextCompat.checkSelfPermission(this.mReactContext, "android.permission.ACCESS_COARSE_LOCATION");
        if (!str.equals("gps") || checkSelfPermission == 0 || checkSelfPermission2 == 0) {
            return str;
        }
        return null;
    }

    private static class SingleUpdateRequest {
        private static final int TWO_MINUTES = 120000;
        private final Callback mError;
        private final Handler mHandler;
        private final LocationListener mLocationListener;
        private final LocationManager mLocationManager;
        private Location mOldLocation;
        private final String mProvider;
        private final Callback mSuccess;
        private final long mTimeout;
        private final Runnable mTimeoutRunnable;
        private boolean mTriggered;

        private SingleUpdateRequest(LocationManager locationManager, String str, long j, Callback callback, Callback callback2) {
            this.mHandler = new Handler();
            this.mTimeoutRunnable = new Runnable() { // from class: com.reactnativecommunity.geolocation.AndroidLocationManager.SingleUpdateRequest.1
                @Override // java.lang.Runnable
                public void run() {
                    synchronized (SingleUpdateRequest.this) {
                        if (!SingleUpdateRequest.this.mTriggered) {
                            SingleUpdateRequest.this.mError.invoke(PositionError.buildError(PositionError.TIMEOUT, "Location request timed out"));
                            SingleUpdateRequest.this.mLocationManager.removeUpdates(SingleUpdateRequest.this.mLocationListener);
                            FLog.i(ReactConstants.TAG, "LocationModule: Location request timed out");
                            SingleUpdateRequest.this.mTriggered = true;
                        }
                    }
                }
            };
            this.mLocationListener = new LocationListener() { // from class: com.reactnativecommunity.geolocation.AndroidLocationManager.SingleUpdateRequest.2
                @Override // android.location.LocationListener
                public void onProviderDisabled(String str2) {
                }

                @Override // android.location.LocationListener
                public void onProviderEnabled(String str2) {
                }

                @Override // android.location.LocationListener
                public void onStatusChanged(String str2, int i, Bundle bundle) {
                }

                @Override // android.location.LocationListener
                public void onLocationChanged(Location location) {
                    synchronized (SingleUpdateRequest.this) {
                        if (!SingleUpdateRequest.this.mTriggered) {
                            SingleUpdateRequest singleUpdateRequest = SingleUpdateRequest.this;
                            if (singleUpdateRequest.isBetterLocation(location, singleUpdateRequest.mOldLocation)) {
                                SingleUpdateRequest.this.mSuccess.invoke(BaseLocationManager.locationToMap(location));
                                SingleUpdateRequest.this.mHandler.removeCallbacks(SingleUpdateRequest.this.mTimeoutRunnable);
                                SingleUpdateRequest.this.mTriggered = true;
                                SingleUpdateRequest.this.mLocationManager.removeUpdates(SingleUpdateRequest.this.mLocationListener);
                            }
                        }
                        SingleUpdateRequest.this.mOldLocation = location;
                    }
                }
            };
            this.mLocationManager = locationManager;
            this.mProvider = str;
            this.mTimeout = j;
            this.mSuccess = callback;
            this.mError = callback2;
        }

        public void invoke(Location location) {
            this.mOldLocation = location;
            this.mLocationManager.requestLocationUpdates(this.mProvider, 100L, 1.0f, this.mLocationListener);
            this.mHandler.postDelayed(this.mTimeoutRunnable, this.mTimeout);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isBetterLocation(Location location, Location location2) {
            if (location2 == null) {
                return true;
            }
            long time = location.getTime() - location2.getTime();
            boolean z = time > 120000;
            boolean z2 = time < -120000;
            boolean z3 = time > 0;
            if (z) {
                return true;
            }
            if (z2) {
                return false;
            }
            int accuracy = (int) (location.getAccuracy() - location2.getAccuracy());
            boolean z4 = accuracy > 0;
            boolean z5 = accuracy < 0;
            boolean z6 = accuracy > 200;
            boolean isSameProvider = isSameProvider(location.getProvider(), location2.getProvider());
            if (z5) {
                return true;
            }
            if (!z3 || z4) {
                return z3 && !z6 && isSameProvider;
            }
            return true;
        }

        private boolean isSameProvider(String str, String str2) {
            if (str == null) {
                return str2 == null;
            }
            return str.equals(str2);
        }
    }
}

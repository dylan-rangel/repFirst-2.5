package com.wix.reactnativenotifications;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.wix.reactnativenotifications.core.AppLifecycleFacadeHolder;
import com.wix.reactnativenotifications.core.InitialNotificationHolder;
import com.wix.reactnativenotifications.core.NotificationIntentAdapter;
import com.wix.reactnativenotifications.core.ReactAppLifecycleFacade;
import com.wix.reactnativenotifications.core.notification.IPushNotification;
import com.wix.reactnativenotifications.core.notification.NotificationChannel;
import com.wix.reactnativenotifications.core.notification.PushNotification;
import com.wix.reactnativenotifications.core.notification.PushNotificationProps;
import com.wix.reactnativenotifications.core.notificationdrawer.PushNotificationsDrawer;
import com.wix.reactnativenotifications.fcm.FcmInstanceIdRefreshHandlerService;

/* loaded from: classes.dex */
public class RNNotificationsModule extends ReactContextBaseJavaModule implements ActivityEventListener {
    @Override // com.facebook.react.bridge.NativeModule
    public String getName() {
        return "RNBridgeModule";
    }

    @Override // com.facebook.react.bridge.ActivityEventListener
    public void onActivityResult(Activity activity, int i, int i2, Intent intent) {
    }

    @ReactMethod
    public void setCategories(ReadableArray readableArray) {
    }

    public RNNotificationsModule(Application application, ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
        if (AppLifecycleFacadeHolder.get() instanceof ReactAppLifecycleFacade) {
            ((ReactAppLifecycleFacade) AppLifecycleFacadeHolder.get()).init(reactApplicationContext);
        }
        reactApplicationContext.addActivityEventListener(this);
    }

    @Override // com.facebook.react.bridge.BaseJavaModule, com.facebook.react.bridge.NativeModule, com.facebook.react.turbomodule.core.interfaces.TurboModule
    public void initialize() {
        startFcmIntentService(FcmInstanceIdRefreshHandlerService.EXTRA_IS_APP_INIT);
        PushNotificationsDrawer.get(getReactApplicationContext().getApplicationContext()).onAppInit();
    }

    @Override // com.facebook.react.bridge.ActivityEventListener
    public void onNewIntent(Intent intent) {
        if (NotificationIntentAdapter.canHandleIntent(intent)) {
            IPushNotification iPushNotification = PushNotification.get(getReactApplicationContext().getApplicationContext(), intent.getExtras());
            if (iPushNotification != null) {
                iPushNotification.onOpened();
            }
        }
    }

    @ReactMethod
    public void refreshToken() {
        startFcmIntentService(FcmInstanceIdRefreshHandlerService.EXTRA_MANUAL_REFRESH);
    }

    @ReactMethod
    public void getInitialNotification(Promise promise) {
        PushNotificationProps pushNotificationProps;
        WritableMap writableMap = null;
        try {
            try {
                pushNotificationProps = InitialNotificationHolder.getInstance().get();
            } catch (NullPointerException unused) {
                Log.e(Defs.LOGTAG, "getInitialNotification: Null pointer exception");
            }
            if (pushNotificationProps == null) {
                return;
            }
            writableMap = Arguments.fromBundle(pushNotificationProps.asBundle());
            InitialNotificationHolder.getInstance().clear();
        } finally {
            promise.resolve(writableMap);
        }
    }

    @ReactMethod
    public void postLocalNotification(ReadableMap readableMap, int i) {
        PushNotification.get(getReactApplicationContext().getApplicationContext(), Arguments.toBundle(readableMap)).onPostRequest(Integer.valueOf(i));
    }

    @ReactMethod
    public void cancelLocalNotification(int i) {
        PushNotificationsDrawer.get(getReactApplicationContext().getApplicationContext()).onNotificationClearRequest(i);
    }

    public void cancelDeliveredNotification(String str, int i) {
        PushNotificationsDrawer.get(getReactApplicationContext().getApplicationContext()).onNotificationClearRequest(str, i);
    }

    @ReactMethod
    public void isRegisteredForRemoteNotifications(Promise promise) {
        promise.resolve(new Boolean(NotificationManagerCompatFacade.from(getReactApplicationContext()).areNotificationsEnabled()));
    }

    @ReactMethod
    void removeAllDeliveredNotifications() {
        PushNotificationsDrawer.get(getReactApplicationContext().getApplicationContext()).onAllNotificationsClearRequest();
    }

    @ReactMethod
    void setNotificationChannel(ReadableMap readableMap) {
        NotificationChannel.get(getReactApplicationContext().getApplicationContext(), Arguments.toBundle(readableMap)).setNotificationChannel();
    }

    protected void startFcmIntentService(String str) {
        Context applicationContext = getReactApplicationContext().getApplicationContext();
        Intent intent = new Intent(applicationContext, (Class<?>) FcmInstanceIdRefreshHandlerService.class);
        intent.putExtra(str, true);
        FcmInstanceIdRefreshHandlerService.enqueueWork(applicationContext, intent);
    }
}

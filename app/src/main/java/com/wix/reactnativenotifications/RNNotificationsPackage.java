package com.wix.reactnativenotifications;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import com.google.firebase.FirebaseApp;
import com.wix.reactnativenotifications.core.AppLifecycleFacade;
import com.wix.reactnativenotifications.core.AppLifecycleFacadeHolder;
import com.wix.reactnativenotifications.core.InitialNotificationHolder;
import com.wix.reactnativenotifications.core.NotificationIntentAdapter;
import com.wix.reactnativenotifications.core.notification.IPushNotification;
import com.wix.reactnativenotifications.core.notification.PushNotification;
import com.wix.reactnativenotifications.core.notificationdrawer.PushNotificationsDrawer;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public class RNNotificationsPackage implements ReactPackage, AppLifecycleFacade.AppVisibilityListener, Application.ActivityLifecycleCallbacks {
    private final Application mApplication;

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityDestroyed(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityResumed(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStopped(Activity activity) {
    }

    @Override // com.wix.reactnativenotifications.core.AppLifecycleFacade.AppVisibilityListener
    public void onAppNotVisible() {
    }

    public RNNotificationsPackage(Application application) {
        this.mApplication = application;
        FirebaseApp.initializeApp(application.getApplicationContext());
        AppLifecycleFacadeHolder.get().addVisibilityListener(this);
        application.registerActivityLifecycleCallbacks(this);
    }

    @Override // com.facebook.react.ReactPackage
    public List<NativeModule> createNativeModules(ReactApplicationContext reactApplicationContext) {
        return Arrays.asList(new RNNotificationsModule(this.mApplication, reactApplicationContext));
    }

    @Override // com.facebook.react.ReactPackage
    public List<ViewManager> createViewManagers(ReactApplicationContext reactApplicationContext) {
        return Collections.emptyList();
    }

    @Override // com.wix.reactnativenotifications.core.AppLifecycleFacade.AppVisibilityListener
    public void onAppVisible() {
        PushNotificationsDrawer.get(this.mApplication.getApplicationContext()).onAppVisible();
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(Activity activity, Bundle bundle) {
        PushNotificationsDrawer.get(this.mApplication.getApplicationContext()).onNewActivity(activity);
        callOnOpenedIfNeed(activity);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStarted(Activity activity) {
        if (InitialNotificationHolder.getInstance().get() == null) {
            callOnOpenedIfNeed(activity);
        }
    }

    private void callOnOpenedIfNeed(Activity activity) {
        IPushNotification iPushNotification;
        Intent intent = activity.getIntent();
        if (!NotificationIntentAdapter.canHandleIntent(intent) || (iPushNotification = PushNotification.get(this.mApplication.getApplicationContext(), NotificationIntentAdapter.extractPendingNotificationDataFromIntent(intent))) == null) {
            return;
        }
        iPushNotification.onOpened();
        activity.setIntent(new Intent());
    }
}

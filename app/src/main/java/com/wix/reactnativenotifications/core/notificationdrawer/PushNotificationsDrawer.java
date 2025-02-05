package com.wix.reactnativenotifications.core.notificationdrawer;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import com.wix.reactnativenotifications.core.AppLaunchHelper;

/* loaded from: classes.dex */
public class PushNotificationsDrawer implements IPushNotificationsDrawer {
    protected final AppLaunchHelper mAppLaunchHelper;
    protected final Context mContext;

    @Override // com.wix.reactnativenotifications.core.notificationdrawer.IPushNotificationsDrawer
    public void onAppInit() {
    }

    @Override // com.wix.reactnativenotifications.core.notificationdrawer.IPushNotificationsDrawer
    public void onAppVisible() {
    }

    @Override // com.wix.reactnativenotifications.core.notificationdrawer.IPushNotificationsDrawer
    public void onNewActivity(Activity activity) {
    }

    @Override // com.wix.reactnativenotifications.core.notificationdrawer.IPushNotificationsDrawer
    public void onNotificationOpened() {
    }

    public static IPushNotificationsDrawer get(Context context) {
        return get(context, new AppLaunchHelper());
    }

    public static IPushNotificationsDrawer get(Context context, AppLaunchHelper appLaunchHelper) {
        Object applicationContext = context.getApplicationContext();
        if (applicationContext instanceof INotificationsDrawerApplication) {
            return ((INotificationsDrawerApplication) applicationContext).getPushNotificationsDrawer(context, appLaunchHelper);
        }
        return new PushNotificationsDrawer(context, appLaunchHelper);
    }

    protected PushNotificationsDrawer(Context context, AppLaunchHelper appLaunchHelper) {
        this.mContext = context;
        this.mAppLaunchHelper = appLaunchHelper;
    }

    @Override // com.wix.reactnativenotifications.core.notificationdrawer.IPushNotificationsDrawer
    public void onNotificationClearRequest(int i) {
        ((NotificationManager) this.mContext.getSystemService("notification")).cancel(i);
    }

    @Override // com.wix.reactnativenotifications.core.notificationdrawer.IPushNotificationsDrawer
    public void onNotificationClearRequest(String str, int i) {
        ((NotificationManager) this.mContext.getSystemService("notification")).cancel(str, i);
    }

    @Override // com.wix.reactnativenotifications.core.notificationdrawer.IPushNotificationsDrawer
    public void onAllNotificationsClearRequest() {
        ((NotificationManager) this.mContext.getSystemService("notification")).cancelAll();
    }
}

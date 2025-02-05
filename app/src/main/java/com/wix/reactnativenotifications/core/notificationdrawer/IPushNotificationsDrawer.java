package com.wix.reactnativenotifications.core.notificationdrawer;

import android.app.Activity;

/* loaded from: classes.dex */
public interface IPushNotificationsDrawer {
    void onAllNotificationsClearRequest();

    void onAppInit();

    void onAppVisible();

    void onNewActivity(Activity activity);

    void onNotificationClearRequest(int i);

    void onNotificationClearRequest(String str, int i);

    void onNotificationOpened();
}

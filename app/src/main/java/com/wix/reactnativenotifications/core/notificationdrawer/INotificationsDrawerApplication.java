package com.wix.reactnativenotifications.core.notificationdrawer;

import android.content.Context;
import com.wix.reactnativenotifications.core.AppLaunchHelper;

/* loaded from: classes.dex */
public interface INotificationsDrawerApplication {
    IPushNotificationsDrawer getPushNotificationsDrawer(Context context, AppLaunchHelper appLaunchHelper);
}

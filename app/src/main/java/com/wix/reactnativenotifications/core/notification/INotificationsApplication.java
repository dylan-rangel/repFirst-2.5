package com.wix.reactnativenotifications.core.notification;

import android.content.Context;
import android.os.Bundle;
import com.wix.reactnativenotifications.core.AppLaunchHelper;
import com.wix.reactnativenotifications.core.AppLifecycleFacade;

/* loaded from: classes.dex */
public interface INotificationsApplication {
    IPushNotification getPushNotification(Context context, Bundle bundle, AppLifecycleFacade appLifecycleFacade, AppLaunchHelper appLaunchHelper);
}

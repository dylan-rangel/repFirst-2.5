package com.wix.reactnativenotifications.core;

import android.app.IntentService;
import android.content.Intent;
import com.wix.reactnativenotifications.core.notification.IPushNotification;
import com.wix.reactnativenotifications.core.notification.PushNotification;

/* loaded from: classes.dex */
public class ProxyService extends IntentService {
    private static final String TAG = "ProxyService";

    public ProxyService() {
        super("notificationsProxyService");
    }

    @Override // android.app.IntentService
    protected void onHandleIntent(Intent intent) {
        IPushNotification iPushNotification = PushNotification.get(this, NotificationIntentAdapter.extractPendingNotificationDataFromIntent(intent));
        if (iPushNotification != null) {
            iPushNotification.onOpened();
        }
    }
}

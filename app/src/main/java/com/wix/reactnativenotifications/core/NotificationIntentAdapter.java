package com.wix.reactnativenotifications.core;

import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import com.google.firebase.messaging.Constants;
import com.wix.reactnativenotifications.core.notification.PushNotificationProps;

/* loaded from: classes.dex */
public class NotificationIntentAdapter {
    private static final String PUSH_NOTIFICATION_EXTRA_NAME = "pushNotification";

    public static PendingIntent createPendingNotificationIntent(Context context, PushNotificationProps pushNotificationProps) {
        if (canHandleTrampolineActivity(context)) {
            Intent intent = new Intent(context, (Class<?>) ProxyService.class);
            intent.putExtra(PUSH_NOTIFICATION_EXTRA_NAME, pushNotificationProps.asBundle());
            return PendingIntent.getService(context, (int) System.currentTimeMillis(), intent, 1073741824);
        }
        Intent launchIntentForPackage = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        launchIntentForPackage.putExtra(PUSH_NOTIFICATION_EXTRA_NAME, pushNotificationProps.asBundle());
        TaskStackBuilder create = TaskStackBuilder.create(context);
        create.addNextIntentWithParentStack(launchIntentForPackage);
        return create.getPendingIntent((int) System.currentTimeMillis(), 1275068416);
    }

    public static boolean canHandleTrampolineActivity(Context context) {
        return Build.VERSION.SDK_INT < 30 || context.getApplicationInfo().targetSdkVersion < 31;
    }

    public static Bundle extractPendingNotificationDataFromIntent(Intent intent) {
        Bundle bundleExtra = intent.getBundleExtra(PUSH_NOTIFICATION_EXTRA_NAME);
        return bundleExtra != null ? bundleExtra : intent.getExtras();
    }

    public static boolean canHandleIntent(Intent intent) {
        Bundle extras;
        if (intent == null || (extras = intent.getExtras()) == null) {
            return false;
        }
        return intent.hasExtra(PUSH_NOTIFICATION_EXTRA_NAME) || extras.getString(Constants.MessagePayloadKeys.MSGID, null) != null;
    }
}

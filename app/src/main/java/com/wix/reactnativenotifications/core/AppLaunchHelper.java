package com.wix.reactnativenotifications.core;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/* loaded from: classes.dex */
public class AppLaunchHelper {
    private static final String LAUNCH_FLAG_KEY_NAME = "launchedFromNotification";
    private static final String TAG = "AppLaunchHelper";

    public Intent getLaunchIntent(Context context) {
        try {
            Intent intent = new Intent(context, Class.forName(context.getPackageManager().getLaunchIntentForPackage(context.getPackageName()).getComponent().getClassName()));
            intent.setFlags(270532608);
            intent.putExtra(LAUNCH_FLAG_KEY_NAME, true);
            return intent;
        } catch (ClassNotFoundException unused) {
            return null;
        }
    }

    public boolean isLaunchIntentsActivity(Activity activity) {
        return activity.getComponentName().getClassName().equals(activity.getPackageManager().getLaunchIntentForPackage(activity.getPackageName()).getComponent().getClassName());
    }

    public boolean isLaunchIntentOfNotification(Intent intent) {
        return intent.getBooleanExtra(LAUNCH_FLAG_KEY_NAME, false);
    }
}

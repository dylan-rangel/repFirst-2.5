package com.wix.reactnativenotifications.core.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import com.facebook.react.uimanager.ViewProps;
import com.wix.reactnativenotifications.Defs;
import com.wix.reactnativenotifications.core.AppLaunchHelper;
import com.wix.reactnativenotifications.core.AppLifecycleFacade;
import com.wix.reactnativenotifications.core.AppLifecycleFacadeHolder;
import com.wix.reactnativenotifications.core.InitialNotificationHolder;
import com.wix.reactnativenotifications.core.JsIOHelper;
import com.wix.reactnativenotifications.core.NotificationIntentAdapter;
import com.wix.reactnativenotifications.core.notification.IPushNotification;

/* loaded from: classes.dex */
public class PushNotification implements IPushNotification {
    protected final AppLaunchHelper mAppLaunchHelper;
    protected final AppLifecycleFacade mAppLifecycleFacade;
    protected final Context mContext;
    protected final JsIOHelper mJsIOHelper;
    protected final PushNotificationProps mNotificationProps;
    protected final AppLifecycleFacade.AppVisibilityListener mAppVisibilityListener = new AppLifecycleFacade.AppVisibilityListener() { // from class: com.wix.reactnativenotifications.core.notification.PushNotification.1
        @Override // com.wix.reactnativenotifications.core.AppLifecycleFacade.AppVisibilityListener
        public void onAppNotVisible() {
        }

        @Override // com.wix.reactnativenotifications.core.AppLifecycleFacade.AppVisibilityListener
        public void onAppVisible() {
            PushNotification.this.mAppLifecycleFacade.removeVisibilityListener(this);
            PushNotification.this.dispatchImmediately();
        }
    };
    private final String DEFAULT_CHANNEL_ID = "channel_01";
    private final String DEFAULT_CHANNEL_NAME = "Channel Name";

    public static IPushNotification get(Context context, Bundle bundle) {
        Object applicationContext = context.getApplicationContext();
        if (applicationContext instanceof INotificationsApplication) {
            return ((INotificationsApplication) applicationContext).getPushNotification(context, bundle, AppLifecycleFacadeHolder.get(), new AppLaunchHelper());
        }
        return new PushNotification(context, bundle, AppLifecycleFacadeHolder.get(), new AppLaunchHelper(), new JsIOHelper());
    }

    protected PushNotification(Context context, Bundle bundle, AppLifecycleFacade appLifecycleFacade, AppLaunchHelper appLaunchHelper, JsIOHelper jsIOHelper) {
        this.mContext = context;
        this.mAppLifecycleFacade = appLifecycleFacade;
        this.mAppLaunchHelper = appLaunchHelper;
        this.mJsIOHelper = jsIOHelper;
        this.mNotificationProps = createProps(bundle);
        initDefaultChannel(context);
    }

    @Override // com.wix.reactnativenotifications.core.notification.IPushNotification
    public void onReceived() throws IPushNotification.InvalidNotificationException {
        if (!this.mAppLifecycleFacade.isAppVisible()) {
            postNotification(null);
            notifyReceivedBackgroundToJS();
        } else {
            notifyReceivedToJS();
        }
    }

    @Override // com.wix.reactnativenotifications.core.notification.IPushNotification
    public void onOpened() {
        digestNotification();
    }

    @Override // com.wix.reactnativenotifications.core.notification.IPushNotification
    public int onPostRequest(Integer num) {
        return postNotification(num);
    }

    @Override // com.wix.reactnativenotifications.core.notification.IPushNotification
    public PushNotificationProps asProps() {
        return this.mNotificationProps.copy();
    }

    protected int postNotification(Integer num) {
        if (this.mNotificationProps.isDataOnlyPushNotification()) {
            return -1;
        }
        return postNotification(buildNotification(NotificationIntentAdapter.createPendingNotificationIntent(this.mContext, this.mNotificationProps)), num);
    }

    protected void digestNotification() {
        if (!this.mAppLifecycleFacade.isReactInitialized()) {
            setAsInitialNotification();
            launchOrResumeApp();
            return;
        }
        if (this.mAppLifecycleFacade.getRunningReactContext().getCurrentActivity() == null) {
            setAsInitialNotification();
        }
        if (this.mAppLifecycleFacade.isAppVisible()) {
            dispatchImmediately();
        } else if (this.mAppLifecycleFacade.isAppDestroyed()) {
            launchOrResumeApp();
        } else {
            dispatchUponVisibility();
        }
    }

    protected PushNotificationProps createProps(Bundle bundle) {
        return new PushNotificationProps(bundle);
    }

    protected void setAsInitialNotification() {
        InitialNotificationHolder.getInstance().set(this.mNotificationProps);
    }

    protected void dispatchImmediately() {
        notifyOpenedToJS();
    }

    protected void dispatchUponVisibility() {
        this.mAppLifecycleFacade.addVisibilityListener(getIntermediateAppVisibilityListener());
        launchOrResumeApp();
    }

    protected AppLifecycleFacade.AppVisibilityListener getIntermediateAppVisibilityListener() {
        return this.mAppVisibilityListener;
    }

    protected Notification buildNotification(PendingIntent pendingIntent) {
        return getNotificationBuilder(pendingIntent).build();
    }

    protected Notification.Builder getNotificationBuilder(PendingIntent pendingIntent) {
        Notification.Builder autoCancel = new Notification.Builder(this.mContext).setContentTitle(this.mNotificationProps.getTitle()).setContentText(this.mNotificationProps.getBody()).setContentIntent(pendingIntent).setDefaults(-1).setAutoCancel(true);
        setUpIcon(autoCancel);
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationManager notificationManager = (NotificationManager) this.mContext.getSystemService("notification");
            String channelId = this.mNotificationProps.getChannelId();
            if (notificationManager.getNotificationChannel(channelId) == null) {
                channelId = "channel_01";
            }
            autoCancel.setChannelId(channelId);
        }
        return autoCancel;
    }

    private void setUpIcon(Notification.Builder builder) {
        int appResourceId = getAppResourceId("notification_icon", "drawable");
        if (appResourceId != 0) {
            builder.setSmallIcon(appResourceId);
        } else {
            builder.setSmallIcon(this.mContext.getApplicationInfo().icon);
        }
        setUpIconColor(builder);
    }

    private void setUpIconColor(Notification.Builder builder) {
        int appResourceId = getAppResourceId("colorAccent", ViewProps.COLOR);
        if (appResourceId != 0) {
            builder.setColor(this.mContext.getResources().getColor(appResourceId));
        }
    }

    protected int postNotification(Notification notification, Integer num) {
        int intValue = num != null ? num.intValue() : createNotificationId(notification);
        postNotification(intValue, notification);
        return intValue;
    }

    protected void postNotification(int i, Notification notification) {
        ((NotificationManager) this.mContext.getSystemService("notification")).notify(i, notification);
    }

    protected int createNotificationId(Notification notification) {
        return (int) System.nanoTime();
    }

    private void notifyReceivedToJS() {
        try {
            this.mJsIOHelper.sendEventToJS(Defs.NOTIFICATION_RECEIVED_EVENT_NAME, this.mNotificationProps.asBundle(), this.mAppLifecycleFacade.getRunningReactContext());
        } catch (NullPointerException unused) {
            Log.e(Defs.LOGTAG, "notifyReceivedToJS: Null pointer exception");
        }
    }

    private void notifyReceivedBackgroundToJS() {
        try {
            this.mJsIOHelper.sendEventToJS(Defs.NOTIFICATION_RECEIVED_BACKGROUND_EVENT_NAME, this.mNotificationProps.asBundle(), this.mAppLifecycleFacade.getRunningReactContext());
        } catch (NullPointerException unused) {
            Log.e(Defs.LOGTAG, "notifyReceivedBackgroundToJS: Null pointer exception");
        }
    }

    private void notifyOpenedToJS() {
        Bundle bundle = new Bundle();
        try {
            bundle.putBundle("notification", this.mNotificationProps.asBundle());
            this.mJsIOHelper.sendEventToJS(Defs.NOTIFICATION_OPENED_EVENT_NAME, bundle, this.mAppLifecycleFacade.getRunningReactContext());
        } catch (NullPointerException unused) {
            Log.e(Defs.LOGTAG, "notifyOpenedToJS: Null pointer exception");
        }
    }

    protected void launchOrResumeApp() {
        if (NotificationIntentAdapter.canHandleTrampolineActivity(this.mContext)) {
            this.mContext.startActivity(this.mAppLaunchHelper.getLaunchIntent(this.mContext));
        }
    }

    private int getAppResourceId(String str, String str2) {
        return this.mContext.getResources().getIdentifier(str, str2, this.mContext.getPackageName());
    }

    private void initDefaultChannel(Context context) {
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
            if (notificationManager.getNotificationChannels().size() == 0) {
                notificationManager.createNotificationChannel(new android.app.NotificationChannel("channel_01", "Channel Name", 3));
            }
        }
    }
}

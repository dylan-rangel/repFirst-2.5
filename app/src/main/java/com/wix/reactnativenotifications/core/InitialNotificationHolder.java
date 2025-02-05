package com.wix.reactnativenotifications.core;

import com.wix.reactnativenotifications.core.notification.PushNotificationProps;

/* loaded from: classes.dex */
public class InitialNotificationHolder {
    private static InitialNotificationHolder sInstance;
    private PushNotificationProps mNotification;

    public static void setInstance(InitialNotificationHolder initialNotificationHolder) {
        sInstance = initialNotificationHolder;
    }

    InitialNotificationHolder() {
    }

    public static InitialNotificationHolder getInstance() {
        if (sInstance == null) {
            sInstance = new InitialNotificationHolder();
        }
        return sInstance;
    }

    public void set(PushNotificationProps pushNotificationProps) {
        this.mNotification = pushNotificationProps;
    }

    public void clear() {
        this.mNotification = null;
    }

    public PushNotificationProps get() {
        return this.mNotification;
    }
}

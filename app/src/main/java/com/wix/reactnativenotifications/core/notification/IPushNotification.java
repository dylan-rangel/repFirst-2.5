package com.wix.reactnativenotifications.core.notification;

/* loaded from: classes.dex */
public interface IPushNotification {
    PushNotificationProps asProps();

    void onOpened();

    int onPostRequest(Integer num);

    void onReceived() throws InvalidNotificationException;

    public static class InvalidNotificationException extends Exception {
        public InvalidNotificationException(String str) {
            super(str);
        }
    }
}

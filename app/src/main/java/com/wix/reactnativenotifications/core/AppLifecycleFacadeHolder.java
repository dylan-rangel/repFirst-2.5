package com.wix.reactnativenotifications.core;

/* loaded from: classes.dex */
public class AppLifecycleFacadeHolder {
    protected static AppLifecycleFacade sInstance = new ReactAppLifecycleFacade();

    public static AppLifecycleFacade get() {
        return sInstance;
    }

    public static void set(AppLifecycleFacade appLifecycleFacade) {
        sInstance = appLifecycleFacade;
    }
}

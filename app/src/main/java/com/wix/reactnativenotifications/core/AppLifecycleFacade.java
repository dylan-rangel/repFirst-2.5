package com.wix.reactnativenotifications.core;

import com.facebook.react.bridge.ReactContext;

/* loaded from: classes.dex */
public interface AppLifecycleFacade {

    public interface AppVisibilityListener {
        void onAppNotVisible();

        void onAppVisible();
    }

    void addVisibilityListener(AppVisibilityListener appVisibilityListener);

    ReactContext getRunningReactContext();

    boolean isAppDestroyed();

    boolean isAppVisible();

    boolean isReactInitialized();

    void removeVisibilityListener(AppVisibilityListener appVisibilityListener);
}

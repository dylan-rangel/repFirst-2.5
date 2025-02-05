package com.wix.reactnativenotifications.core;

import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactContext;
import com.wix.reactnativenotifications.core.AppLifecycleFacade;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/* loaded from: classes.dex */
public class ReactAppLifecycleFacade implements AppLifecycleFacade {
    private boolean mIsDestroyed;
    private boolean mIsVisible;
    private Set<AppLifecycleFacade.AppVisibilityListener> mListeners = new CopyOnWriteArraySet();
    private ReactContext mReactContext;

    public void init(ReactContext reactContext) {
        this.mReactContext = reactContext;
        reactContext.addLifecycleEventListener(new LifecycleEventListener() { // from class: com.wix.reactnativenotifications.core.ReactAppLifecycleFacade.1
            @Override // com.facebook.react.bridge.LifecycleEventListener
            public void onHostResume() {
                ReactAppLifecycleFacade.this.switchToVisible();
            }

            @Override // com.facebook.react.bridge.LifecycleEventListener
            public void onHostPause() {
                ReactAppLifecycleFacade.this.switchToInvisible();
            }

            @Override // com.facebook.react.bridge.LifecycleEventListener
            public void onHostDestroy() {
                ReactAppLifecycleFacade.this.switchToInvisible();
            }
        });
    }

    @Override // com.wix.reactnativenotifications.core.AppLifecycleFacade
    public synchronized boolean isReactInitialized() {
        ReactContext reactContext = this.mReactContext;
        if (reactContext == null) {
            return false;
        }
        try {
            return reactContext.hasActiveCatalystInstance();
        } catch (Exception unused) {
            return this.mReactContext.hasActiveReactInstance();
        }
    }

    @Override // com.wix.reactnativenotifications.core.AppLifecycleFacade
    public ReactContext getRunningReactContext() {
        ReactContext reactContext = this.mReactContext;
        if (reactContext == null) {
            return null;
        }
        return reactContext;
    }

    @Override // com.wix.reactnativenotifications.core.AppLifecycleFacade
    public boolean isAppVisible() {
        return this.mIsVisible;
    }

    @Override // com.wix.reactnativenotifications.core.AppLifecycleFacade
    public boolean isAppDestroyed() {
        return this.mIsDestroyed;
    }

    @Override // com.wix.reactnativenotifications.core.AppLifecycleFacade
    public void addVisibilityListener(AppLifecycleFacade.AppVisibilityListener appVisibilityListener) {
        this.mListeners.add(appVisibilityListener);
    }

    @Override // com.wix.reactnativenotifications.core.AppLifecycleFacade
    public void removeVisibilityListener(AppLifecycleFacade.AppVisibilityListener appVisibilityListener) {
        this.mListeners.remove(appVisibilityListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void switchToVisible() {
        if (!this.mIsVisible) {
            this.mIsVisible = true;
            Iterator<AppLifecycleFacade.AppVisibilityListener> it = this.mListeners.iterator();
            while (it.hasNext()) {
                it.next().onAppVisible();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void switchToInvisible() {
        if (this.mIsVisible) {
            this.mIsVisible = false;
            Iterator<AppLifecycleFacade.AppVisibilityListener> it = this.mListeners.iterator();
            while (it.hasNext()) {
                it.next().onAppNotVisible();
            }
        }
    }

    private synchronized void switchToDestroyed() {
        switchToInvisible();
        if (!this.mIsDestroyed) {
            this.mIsDestroyed = true;
        }
    }
}

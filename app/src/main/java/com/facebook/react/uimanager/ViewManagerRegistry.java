package com.facebook.react.uimanager;

import android.content.ComponentCallbacks2;
import android.content.res.Configuration;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.common.MapBuilder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public final class ViewManagerRegistry implements ComponentCallbacks2 {
    private final ViewManagerResolver mViewManagerResolver;
    private final Map<String, ViewManager> mViewManagers;

    @Override // android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
    }

    public ViewManagerRegistry(ViewManagerResolver viewManagerResolver) {
        this.mViewManagers = MapBuilder.newHashMap();
        this.mViewManagerResolver = viewManagerResolver;
    }

    public ViewManagerRegistry(List<ViewManager> list) {
        HashMap newHashMap = MapBuilder.newHashMap();
        for (ViewManager viewManager : list) {
            newHashMap.put(viewManager.getName(), viewManager);
        }
        this.mViewManagers = newHashMap;
        this.mViewManagerResolver = null;
    }

    public ViewManagerRegistry(Map<String, ViewManager> map) {
        this.mViewManagers = map == null ? MapBuilder.newHashMap() : map;
        this.mViewManagerResolver = null;
    }

    public ViewManager get(String str) {
        ViewManager viewManager = this.mViewManagers.get(str);
        if (viewManager != null) {
            return viewManager;
        }
        if (this.mViewManagerResolver != null) {
            ViewManager viewManagerFromResolver = getViewManagerFromResolver(str);
            if (viewManagerFromResolver != null) {
                return viewManagerFromResolver;
            }
            throw new IllegalViewOperationException("ViewManagerResolver returned null for " + str + ", existing names are: " + this.mViewManagerResolver.getViewManagerNames());
        }
        throw new IllegalViewOperationException("No ViewManager found for class " + str);
    }

    private ViewManager getViewManagerFromResolver(String str) {
        ViewManager viewManager = this.mViewManagerResolver.getViewManager(str);
        if (viewManager != null) {
            this.mViewManagers.put(str, viewManager);
        }
        return viewManager;
    }

    ViewManager getViewManagerIfExists(String str) {
        ViewManager viewManager = this.mViewManagers.get(str);
        if (viewManager != null) {
            return viewManager;
        }
        if (this.mViewManagerResolver != null) {
            return getViewManagerFromResolver(str);
        }
        return null;
    }

    public void onSurfaceStopped(final int i) {
        Runnable runnable = new Runnable() { // from class: com.facebook.react.uimanager.ViewManagerRegistry.1
            @Override // java.lang.Runnable
            public void run() {
                Iterator it = ViewManagerRegistry.this.mViewManagers.entrySet().iterator();
                while (it.hasNext()) {
                    ((ViewManager) ((Map.Entry) it.next()).getValue()).onSurfaceStopped(i);
                }
            }
        };
        if (UiThreadUtil.isOnUiThread()) {
            runnable.run();
        } else {
            UiThreadUtil.runOnUiThread(runnable);
        }
    }

    @Override // android.content.ComponentCallbacks2
    public void onTrimMemory(int i) {
        Runnable runnable = new Runnable() { // from class: com.facebook.react.uimanager.ViewManagerRegistry.2
            @Override // java.lang.Runnable
            public void run() {
                Iterator it = ViewManagerRegistry.this.mViewManagers.entrySet().iterator();
                while (it.hasNext()) {
                    ((ViewManager) ((Map.Entry) it.next()).getValue()).trimMemory();
                }
            }
        };
        if (UiThreadUtil.isOnUiThread()) {
            runnable.run();
        } else {
            UiThreadUtil.runOnUiThread(runnable);
        }
    }

    @Override // android.content.ComponentCallbacks
    public void onLowMemory() {
        onTrimMemory(0);
    }
}

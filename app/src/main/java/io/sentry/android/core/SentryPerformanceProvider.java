package io.sentry.android.core;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.ProviderInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import io.sentry.SentryDate;

/* loaded from: classes3.dex */
public final class SentryPerformanceProvider extends EmptySecureContentProvider implements Application.ActivityLifecycleCallbacks {
    private Application application;
    private boolean firstActivityCreated = false;
    private boolean firstActivityResumed = false;
    private static SentryDate appStartTime = AndroidDateUtils.getCurrentSentryDateTime();
    private static long appStartMillis = SystemClock.uptimeMillis();

    @Override // android.content.ContentProvider
    public String getType(Uri uri) {
        return null;
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityDestroyed(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStarted(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStopped(Activity activity) {
    }

    public SentryPerformanceProvider() {
        AppStartState.getInstance().setAppStartTime(appStartMillis, appStartTime);
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        Context context = getContext();
        if (context == null) {
            return false;
        }
        if (context.getApplicationContext() != null) {
            context = context.getApplicationContext();
        }
        if (!(context instanceof Application)) {
            return true;
        }
        Application application = (Application) context;
        this.application = application;
        application.registerActivityLifecycleCallbacks(this);
        return true;
    }

    @Override // android.content.ContentProvider
    public void attachInfo(Context context, ProviderInfo providerInfo) {
        if (SentryPerformanceProvider.class.getName().equals(providerInfo.authority)) {
            throw new IllegalStateException("An applicationId is required to fulfill the manifest placeholder.");
        }
        super.attachInfo(context, providerInfo);
    }

    static void setAppStartTime(long j, SentryDate sentryDate) {
        appStartMillis = j;
        appStartTime = sentryDate;
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(Activity activity, Bundle bundle) {
        if (this.firstActivityCreated) {
            return;
        }
        AppStartState.getInstance().setColdStart(bundle == null);
        this.firstActivityCreated = true;
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityResumed(Activity activity) {
        if (!this.firstActivityResumed) {
            this.firstActivityResumed = true;
            AppStartState.getInstance().setAppStartEnd();
        }
        Application application = this.application;
        if (application != null) {
            application.unregisterActivityLifecycleCallbacks(this);
        }
    }
}

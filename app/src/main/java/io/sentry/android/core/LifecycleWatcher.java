package io.sentry.android.core;

import androidx.core.app.NotificationCompat;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import com.facebook.react.modules.appstate.AppStateModule;
import com.facebook.react.uimanager.ViewProps;
import io.sentry.Breadcrumb;
import io.sentry.IHub;
import io.sentry.Scope;
import io.sentry.ScopeCallback;
import io.sentry.SentryLevel;
import io.sentry.Session;
import io.sentry.android.core.internal.util.BreadcrumbFactory;
import io.sentry.protocol.SentryThread;
import io.sentry.transport.CurrentDateProvider;
import io.sentry.transport.ICurrentDateProvider;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicLong;

/* loaded from: classes3.dex */
final class LifecycleWatcher implements DefaultLifecycleObserver {
    private final ICurrentDateProvider currentDateProvider;
    private final boolean enableAppLifecycleBreadcrumbs;
    private final boolean enableSessionTracking;
    private final IHub hub;
    private final AtomicLong lastUpdatedSession;
    private final long sessionIntervalMillis;
    private final Timer timer;
    private final Object timerLock;
    private TimerTask timerTask;

    @Override // androidx.lifecycle.DefaultLifecycleObserver, androidx.lifecycle.FullLifecycleObserver
    public /* synthetic */ void onCreate(LifecycleOwner lifecycleOwner) {
        DefaultLifecycleObserver.CC.$default$onCreate(this, lifecycleOwner);
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver, androidx.lifecycle.FullLifecycleObserver
    public /* synthetic */ void onDestroy(LifecycleOwner lifecycleOwner) {
        DefaultLifecycleObserver.CC.$default$onDestroy(this, lifecycleOwner);
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver, androidx.lifecycle.FullLifecycleObserver
    public /* synthetic */ void onPause(LifecycleOwner lifecycleOwner) {
        DefaultLifecycleObserver.CC.$default$onPause(this, lifecycleOwner);
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver, androidx.lifecycle.FullLifecycleObserver
    public /* synthetic */ void onResume(LifecycleOwner lifecycleOwner) {
        DefaultLifecycleObserver.CC.$default$onResume(this, lifecycleOwner);
    }

    LifecycleWatcher(IHub iHub, long j, boolean z, boolean z2) {
        this(iHub, j, z, z2, CurrentDateProvider.getInstance());
    }

    LifecycleWatcher(IHub iHub, long j, boolean z, boolean z2, ICurrentDateProvider iCurrentDateProvider) {
        this.lastUpdatedSession = new AtomicLong(0L);
        this.timerLock = new Object();
        this.sessionIntervalMillis = j;
        this.enableSessionTracking = z;
        this.enableAppLifecycleBreadcrumbs = z2;
        this.hub = iHub;
        this.currentDateProvider = iCurrentDateProvider;
        if (z) {
            this.timer = new Timer(true);
        } else {
            this.timer = null;
        }
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver, androidx.lifecycle.FullLifecycleObserver
    public void onStart(LifecycleOwner lifecycleOwner) {
        startSession();
        addAppBreadcrumb("foreground");
        AppState.getInstance().setInBackground(false);
    }

    private void startSession() {
        if (this.enableSessionTracking) {
            cancelTask();
            long currentTimeMillis = this.currentDateProvider.getCurrentTimeMillis();
            this.hub.configureScope(new ScopeCallback() { // from class: io.sentry.android.core.LifecycleWatcher$$ExternalSyntheticLambda0
                @Override // io.sentry.ScopeCallback
                public final void run(Scope scope) {
                    LifecycleWatcher.this.m258lambda$startSession$0$iosentryandroidcoreLifecycleWatcher(scope);
                }
            });
            long j = this.lastUpdatedSession.get();
            if (j == 0 || j + this.sessionIntervalMillis <= currentTimeMillis) {
                addSessionBreadcrumb(ViewProps.START);
                this.hub.startSession();
            }
            this.lastUpdatedSession.set(currentTimeMillis);
        }
    }

    /* renamed from: lambda$startSession$0$io-sentry-android-core-LifecycleWatcher, reason: not valid java name */
    /* synthetic */ void m258lambda$startSession$0$iosentryandroidcoreLifecycleWatcher(Scope scope) {
        Session session;
        if (this.lastUpdatedSession.get() != 0 || (session = scope.getSession()) == null || session.getStarted() == null) {
            return;
        }
        this.lastUpdatedSession.set(session.getStarted().getTime());
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver, androidx.lifecycle.FullLifecycleObserver
    public void onStop(LifecycleOwner lifecycleOwner) {
        if (this.enableSessionTracking) {
            this.lastUpdatedSession.set(this.currentDateProvider.getCurrentTimeMillis());
            scheduleEndSession();
        }
        AppState.getInstance().setInBackground(true);
        addAppBreadcrumb(AppStateModule.APP_STATE_BACKGROUND);
    }

    private void scheduleEndSession() {
        synchronized (this.timerLock) {
            cancelTask();
            if (this.timer != null) {
                TimerTask timerTask = new TimerTask() { // from class: io.sentry.android.core.LifecycleWatcher.1
                    @Override // java.util.TimerTask, java.lang.Runnable
                    public void run() {
                        LifecycleWatcher.this.addSessionBreadcrumb(ViewProps.END);
                        LifecycleWatcher.this.hub.endSession();
                    }
                };
                this.timerTask = timerTask;
                this.timer.schedule(timerTask, this.sessionIntervalMillis);
            }
        }
    }

    private void cancelTask() {
        synchronized (this.timerLock) {
            TimerTask timerTask = this.timerTask;
            if (timerTask != null) {
                timerTask.cancel();
                this.timerTask = null;
            }
        }
    }

    private void addAppBreadcrumb(String str) {
        if (this.enableAppLifecycleBreadcrumbs) {
            Breadcrumb breadcrumb = new Breadcrumb();
            breadcrumb.setType(NotificationCompat.CATEGORY_NAVIGATION);
            breadcrumb.setData(SentryThread.JsonKeys.STATE, str);
            breadcrumb.setCategory("app.lifecycle");
            breadcrumb.setLevel(SentryLevel.INFO);
            this.hub.addBreadcrumb(breadcrumb);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addSessionBreadcrumb(String str) {
        this.hub.addBreadcrumb(BreadcrumbFactory.forSession(str));
    }

    TimerTask getTimerTask() {
        return this.timerTask;
    }

    Timer getTimer() {
        return this.timer;
    }
}

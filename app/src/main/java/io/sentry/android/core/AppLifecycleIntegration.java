package io.sentry.android.core;

import androidx.lifecycle.ProcessLifecycleOwner;
import io.sentry.IHub;
import io.sentry.ILogger;
import io.sentry.Integration;
import io.sentry.SentryIntegrationPackageStorage;
import io.sentry.SentryLevel;
import io.sentry.SentryOptions;
import io.sentry.android.core.internal.util.AndroidMainThreadChecker;
import io.sentry.util.Objects;
import java.io.Closeable;
import java.io.IOException;

/* loaded from: classes3.dex */
public final class AppLifecycleIntegration implements Integration, Closeable {
    private final MainLooperHandler handler;
    private SentryAndroidOptions options;
    volatile LifecycleWatcher watcher;

    @Override // io.sentry.IntegrationName
    public /* synthetic */ void addIntegrationToSdkVersion() {
        SentryIntegrationPackageStorage.getInstance().addIntegration(getIntegrationName());
    }

    @Override // io.sentry.IntegrationName
    public /* synthetic */ String getIntegrationName() {
        String replace;
        replace = getClass().getSimpleName().replace("Sentry", "").replace("Integration", "").replace("Interceptor", "").replace("EventProcessor", "");
        return replace;
    }

    public AppLifecycleIntegration() {
        this(new MainLooperHandler());
    }

    AppLifecycleIntegration(MainLooperHandler mainLooperHandler) {
        this.handler = mainLooperHandler;
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:18:0x008f -> B:14:0x009a). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:20:0x0082 -> B:14:0x009a). Please report as a decompilation issue!!! */
    @Override // io.sentry.Integration
    public void register(final IHub iHub, SentryOptions sentryOptions) {
        Objects.requireNonNull(iHub, "Hub is required");
        SentryAndroidOptions sentryAndroidOptions = (SentryAndroidOptions) Objects.requireNonNull(sentryOptions instanceof SentryAndroidOptions ? (SentryAndroidOptions) sentryOptions : null, "SentryAndroidOptions is required");
        this.options = sentryAndroidOptions;
        sentryAndroidOptions.getLogger().log(SentryLevel.DEBUG, "enableSessionTracking enabled: %s", Boolean.valueOf(this.options.isEnableAutoSessionTracking()));
        this.options.getLogger().log(SentryLevel.DEBUG, "enableAppLifecycleBreadcrumbs enabled: %s", Boolean.valueOf(this.options.isEnableAppLifecycleBreadcrumbs()));
        if (this.options.isEnableAutoSessionTracking() || this.options.isEnableAppLifecycleBreadcrumbs()) {
            try {
                Class.forName("androidx.lifecycle.DefaultLifecycleObserver");
                Class.forName("androidx.lifecycle.ProcessLifecycleOwner");
                if (AndroidMainThreadChecker.getInstance().isMainThread()) {
                    m257lambda$register$0$iosentryandroidcoreAppLifecycleIntegration(iHub);
                    sentryOptions = sentryOptions;
                } else {
                    this.handler.post(new Runnable() { // from class: io.sentry.android.core.AppLifecycleIntegration$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            AppLifecycleIntegration.this.m257lambda$register$0$iosentryandroidcoreAppLifecycleIntegration(iHub);
                        }
                    });
                    sentryOptions = sentryOptions;
                }
            } catch (ClassNotFoundException e) {
                ILogger logger = sentryOptions.getLogger();
                logger.log(SentryLevel.INFO, "androidx.lifecycle is not available, AppLifecycleIntegration won't be installed", e);
                sentryOptions = logger;
            } catch (IllegalStateException e2) {
                ILogger logger2 = sentryOptions.getLogger();
                logger2.log(SentryLevel.ERROR, "AppLifecycleIntegration could not be installed", e2);
                sentryOptions = logger2;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: addObserver, reason: merged with bridge method [inline-methods] */
    public void m257lambda$register$0$iosentryandroidcoreAppLifecycleIntegration(IHub iHub) {
        SentryAndroidOptions sentryAndroidOptions = this.options;
        if (sentryAndroidOptions == null) {
            return;
        }
        this.watcher = new LifecycleWatcher(iHub, sentryAndroidOptions.getSessionTrackingIntervalMillis(), this.options.isEnableAutoSessionTracking(), this.options.isEnableAppLifecycleBreadcrumbs());
        try {
            ProcessLifecycleOwner.get().getLifecycle().addObserver(this.watcher);
            this.options.getLogger().log(SentryLevel.DEBUG, "AppLifecycleIntegration installed.", new Object[0]);
            addIntegrationToSdkVersion();
        } catch (Throwable th) {
            this.watcher = null;
            this.options.getLogger().log(SentryLevel.ERROR, "AppLifecycleIntegration failed to get Lifecycle and could not be installed.", th);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: removeObserver, reason: merged with bridge method [inline-methods] */
    public void m256lambda$close$1$iosentryandroidcoreAppLifecycleIntegration() {
        LifecycleWatcher lifecycleWatcher = this.watcher;
        if (lifecycleWatcher != null) {
            ProcessLifecycleOwner.get().getLifecycle().removeObserver(lifecycleWatcher);
            SentryAndroidOptions sentryAndroidOptions = this.options;
            if (sentryAndroidOptions != null) {
                sentryAndroidOptions.getLogger().log(SentryLevel.DEBUG, "AppLifecycleIntegration removed.", new Object[0]);
            }
        }
        this.watcher = null;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (this.watcher == null) {
            return;
        }
        if (AndroidMainThreadChecker.getInstance().isMainThread()) {
            m256lambda$close$1$iosentryandroidcoreAppLifecycleIntegration();
        } else {
            this.handler.post(new Runnable() { // from class: io.sentry.android.core.AppLifecycleIntegration$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    AppLifecycleIntegration.this.m256lambda$close$1$iosentryandroidcoreAppLifecycleIntegration();
                }
            });
        }
    }
}

package io.sentry.android.core;

import io.sentry.IHub;
import io.sentry.Integration;
import io.sentry.SentryIntegrationPackageStorage;
import io.sentry.SentryLevel;
import io.sentry.SentryOptions;
import io.sentry.Session;
import io.sentry.util.Objects;
import java.io.Closeable;
import java.io.IOException;

/* loaded from: classes3.dex */
public final class NdkIntegration implements Integration, Closeable {
    public static final String SENTRY_NDK_CLASS_NAME = "io.sentry.android.ndk.SentryNdk";
    private SentryAndroidOptions options;
    private final Class<?> sentryNdkClass;

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

    public NdkIntegration(Class<?> cls) {
        this.sentryNdkClass = cls;
    }

    @Override // io.sentry.Integration
    public final void register(IHub iHub, SentryOptions sentryOptions) {
        Objects.requireNonNull(iHub, "Hub is required");
        SentryAndroidOptions sentryAndroidOptions = (SentryAndroidOptions) Objects.requireNonNull(sentryOptions instanceof SentryAndroidOptions ? (SentryAndroidOptions) sentryOptions : null, "SentryAndroidOptions is required");
        this.options = sentryAndroidOptions;
        boolean isEnableNdk = sentryAndroidOptions.isEnableNdk();
        this.options.getLogger().log(SentryLevel.DEBUG, "NdkIntegration enabled: %s", Boolean.valueOf(isEnableNdk));
        if (isEnableNdk && this.sentryNdkClass != null) {
            if (this.options.getCacheDirPath() == null) {
                this.options.getLogger().log(SentryLevel.ERROR, "No cache dir path is defined in options.", new Object[0]);
                disableNdkIntegration(this.options);
                return;
            }
            try {
                this.sentryNdkClass.getMethod(Session.JsonKeys.INIT, SentryAndroidOptions.class).invoke(null, this.options);
                this.options.getLogger().log(SentryLevel.DEBUG, "NdkIntegration installed.", new Object[0]);
                addIntegrationToSdkVersion();
                return;
            } catch (NoSuchMethodException e) {
                disableNdkIntegration(this.options);
                this.options.getLogger().log(SentryLevel.ERROR, "Failed to invoke the SentryNdk.init method.", e);
                return;
            } catch (Throwable th) {
                disableNdkIntegration(this.options);
                this.options.getLogger().log(SentryLevel.ERROR, "Failed to initialize SentryNdk.", th);
                return;
            }
        }
        disableNdkIntegration(this.options);
    }

    private void disableNdkIntegration(SentryOptions sentryOptions) {
        sentryOptions.setEnableNdk(false);
        sentryOptions.setEnableScopeSync(false);
    }

    Class<?> getSentryNdkClass() {
        return this.sentryNdkClass;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        SentryAndroidOptions sentryAndroidOptions = this.options;
        if (sentryAndroidOptions == null || !sentryAndroidOptions.isEnableNdk()) {
            return;
        }
        Class<?> cls = this.sentryNdkClass;
        try {
            if (cls != null) {
                try {
                    try {
                        cls.getMethod("close", new Class[0]).invoke(null, new Object[0]);
                        this.options.getLogger().log(SentryLevel.DEBUG, "NdkIntegration removed.", new Object[0]);
                    } finally {
                        disableNdkIntegration(this.options);
                    }
                } catch (NoSuchMethodException e) {
                    this.options.getLogger().log(SentryLevel.ERROR, "Failed to invoke the SentryNdk.close method.", e);
                }
                disableNdkIntegration(this.options);
            }
        } catch (Throwable th) {
            disableNdkIntegration(this.options);
        }
    }
}

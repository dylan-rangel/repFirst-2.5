package io.sentry.cache;

import io.sentry.IOptionsObserver;
import io.sentry.JsonDeserializer;
import io.sentry.SentryLevel;
import io.sentry.SentryOptions;
import io.sentry.protocol.SdkVersion;
import java.util.Map;

/* loaded from: classes3.dex */
public final class PersistingOptionsObserver implements IOptionsObserver {
    public static final String DIST_FILENAME = "dist.json";
    public static final String ENVIRONMENT_FILENAME = "environment.json";
    public static final String OPTIONS_CACHE = ".options-cache";
    public static final String PROGUARD_UUID_FILENAME = "proguard-uuid.json";
    public static final String RELEASE_FILENAME = "release.json";
    public static final String SDK_VERSION_FILENAME = "sdk-version.json";
    public static final String TAGS_FILENAME = "tags.json";
    private final SentryOptions options;

    public PersistingOptionsObserver(SentryOptions sentryOptions) {
        this.options = sentryOptions;
    }

    private void serializeToDisk(final Runnable runnable) {
        try {
            this.options.getExecutorService().submit(new Runnable() { // from class: io.sentry.cache.PersistingOptionsObserver$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    PersistingOptionsObserver.this.m267x5a11aa57(runnable);
                }
            });
        } catch (Throwable th) {
            this.options.getLogger().log(SentryLevel.ERROR, "Serialization task could not be scheduled", th);
        }
    }

    /* renamed from: lambda$serializeToDisk$0$io-sentry-cache-PersistingOptionsObserver, reason: not valid java name */
    /* synthetic */ void m267x5a11aa57(Runnable runnable) {
        try {
            runnable.run();
        } catch (Throwable th) {
            this.options.getLogger().log(SentryLevel.ERROR, "Serialization task failed", th);
        }
    }

    @Override // io.sentry.IOptionsObserver
    public void setRelease(final String str) {
        serializeToDisk(new Runnable() { // from class: io.sentry.cache.PersistingOptionsObserver$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                PersistingOptionsObserver.this.m271lambda$setRelease$1$iosentrycachePersistingOptionsObserver(str);
            }
        });
    }

    /* renamed from: lambda$setRelease$1$io-sentry-cache-PersistingOptionsObserver, reason: not valid java name */
    /* synthetic */ void m271lambda$setRelease$1$iosentrycachePersistingOptionsObserver(String str) {
        if (str == null) {
            delete(RELEASE_FILENAME);
        } else {
            store(str, RELEASE_FILENAME);
        }
    }

    @Override // io.sentry.IOptionsObserver
    public void setProguardUuid(final String str) {
        serializeToDisk(new Runnable() { // from class: io.sentry.cache.PersistingOptionsObserver$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                PersistingOptionsObserver.this.m270x86935416(str);
            }
        });
    }

    /* renamed from: lambda$setProguardUuid$2$io-sentry-cache-PersistingOptionsObserver, reason: not valid java name */
    /* synthetic */ void m270x86935416(String str) {
        if (str == null) {
            delete(PROGUARD_UUID_FILENAME);
        } else {
            store(str, PROGUARD_UUID_FILENAME);
        }
    }

    @Override // io.sentry.IOptionsObserver
    public void setSdkVersion(final SdkVersion sdkVersion) {
        serializeToDisk(new Runnable() { // from class: io.sentry.cache.PersistingOptionsObserver$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                PersistingOptionsObserver.this.m272lambda$setSdkVersion$3$iosentrycachePersistingOptionsObserver(sdkVersion);
            }
        });
    }

    /* renamed from: lambda$setSdkVersion$3$io-sentry-cache-PersistingOptionsObserver, reason: not valid java name */
    /* synthetic */ void m272lambda$setSdkVersion$3$iosentrycachePersistingOptionsObserver(SdkVersion sdkVersion) {
        if (sdkVersion == null) {
            delete(SDK_VERSION_FILENAME);
        } else {
            store(sdkVersion, SDK_VERSION_FILENAME);
        }
    }

    @Override // io.sentry.IOptionsObserver
    public void setDist(final String str) {
        serializeToDisk(new Runnable() { // from class: io.sentry.cache.PersistingOptionsObserver$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                PersistingOptionsObserver.this.m268lambda$setDist$4$iosentrycachePersistingOptionsObserver(str);
            }
        });
    }

    /* renamed from: lambda$setDist$4$io-sentry-cache-PersistingOptionsObserver, reason: not valid java name */
    /* synthetic */ void m268lambda$setDist$4$iosentrycachePersistingOptionsObserver(String str) {
        if (str == null) {
            delete(DIST_FILENAME);
        } else {
            store(str, DIST_FILENAME);
        }
    }

    @Override // io.sentry.IOptionsObserver
    public void setEnvironment(final String str) {
        serializeToDisk(new Runnable() { // from class: io.sentry.cache.PersistingOptionsObserver$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                PersistingOptionsObserver.this.m269x87bf78f(str);
            }
        });
    }

    /* renamed from: lambda$setEnvironment$5$io-sentry-cache-PersistingOptionsObserver, reason: not valid java name */
    /* synthetic */ void m269x87bf78f(String str) {
        if (str == null) {
            delete(ENVIRONMENT_FILENAME);
        } else {
            store(str, ENVIRONMENT_FILENAME);
        }
    }

    /* renamed from: lambda$setTags$6$io-sentry-cache-PersistingOptionsObserver, reason: not valid java name */
    /* synthetic */ void m273lambda$setTags$6$iosentrycachePersistingOptionsObserver(Map map) {
        store(map, "tags.json");
    }

    @Override // io.sentry.IOptionsObserver
    public void setTags(final Map<String, String> map) {
        serializeToDisk(new Runnable() { // from class: io.sentry.cache.PersistingOptionsObserver$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                PersistingOptionsObserver.this.m273lambda$setTags$6$iosentrycachePersistingOptionsObserver(map);
            }
        });
    }

    private <T> void store(T t, String str) {
        CacheUtils.store(this.options, t, OPTIONS_CACHE, str);
    }

    private void delete(String str) {
        CacheUtils.delete(this.options, OPTIONS_CACHE, str);
    }

    public static <T> T read(SentryOptions sentryOptions, String str, Class<T> cls) {
        return (T) read(sentryOptions, str, cls, null);
    }

    public static <T, R> T read(SentryOptions sentryOptions, String str, Class<T> cls, JsonDeserializer<R> jsonDeserializer) {
        return (T) CacheUtils.read(sentryOptions, OPTIONS_CACHE, str, cls, jsonDeserializer);
    }
}

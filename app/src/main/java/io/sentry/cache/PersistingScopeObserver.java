package io.sentry.cache;

import io.sentry.Breadcrumb;
import io.sentry.IScopeObserver;
import io.sentry.JsonDeserializer;
import io.sentry.SentryLevel;
import io.sentry.SentryOptions;
import io.sentry.SpanContext;
import io.sentry.protocol.Contexts;
import io.sentry.protocol.Request;
import io.sentry.protocol.User;
import java.util.Collection;
import java.util.Map;

/* loaded from: classes3.dex */
public final class PersistingScopeObserver implements IScopeObserver {
    public static final String BREADCRUMBS_FILENAME = "breadcrumbs.json";
    public static final String CONTEXTS_FILENAME = "contexts.json";
    public static final String EXTRAS_FILENAME = "extras.json";
    public static final String FINGERPRINT_FILENAME = "fingerprint.json";
    public static final String LEVEL_FILENAME = "level.json";
    public static final String REQUEST_FILENAME = "request.json";
    public static final String SCOPE_CACHE = ".scope-cache";
    public static final String TAGS_FILENAME = "tags.json";
    public static final String TRACE_FILENAME = "trace.json";
    public static final String TRANSACTION_FILENAME = "transaction.json";
    public static final String USER_FILENAME = "user.json";
    private final SentryOptions options;

    @Override // io.sentry.IScopeObserver
    public /* synthetic */ void addBreadcrumb(Breadcrumb breadcrumb) {
        IScopeObserver.CC.$default$addBreadcrumb(this, breadcrumb);
    }

    @Override // io.sentry.IScopeObserver
    public /* synthetic */ void removeExtra(String str) {
        IScopeObserver.CC.$default$removeExtra(this, str);
    }

    @Override // io.sentry.IScopeObserver
    public /* synthetic */ void removeTag(String str) {
        IScopeObserver.CC.$default$removeTag(this, str);
    }

    @Override // io.sentry.IScopeObserver
    public /* synthetic */ void setExtra(String str, String str2) {
        IScopeObserver.CC.$default$setExtra(this, str, str2);
    }

    @Override // io.sentry.IScopeObserver
    public /* synthetic */ void setTag(String str, String str2) {
        IScopeObserver.CC.$default$setTag(this, str, str2);
    }

    public PersistingScopeObserver(SentryOptions sentryOptions) {
        this.options = sentryOptions;
    }

    @Override // io.sentry.IScopeObserver
    public void setUser(final User user) {
        serializeToDisk(new Runnable() { // from class: io.sentry.cache.PersistingScopeObserver$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                PersistingScopeObserver.this.m284lambda$setUser$0$iosentrycachePersistingScopeObserver(user);
            }
        });
    }

    /* renamed from: lambda$setUser$0$io-sentry-cache-PersistingScopeObserver, reason: not valid java name */
    /* synthetic */ void m284lambda$setUser$0$iosentrycachePersistingScopeObserver(User user) {
        if (user == null) {
            delete(USER_FILENAME);
        } else {
            store(user, USER_FILENAME);
        }
    }

    /* renamed from: lambda$setBreadcrumbs$1$io-sentry-cache-PersistingScopeObserver, reason: not valid java name */
    /* synthetic */ void m275lambda$setBreadcrumbs$1$iosentrycachePersistingScopeObserver(Collection collection) {
        store(collection, BREADCRUMBS_FILENAME);
    }

    @Override // io.sentry.IScopeObserver
    public void setBreadcrumbs(final Collection<Breadcrumb> collection) {
        serializeToDisk(new Runnable() { // from class: io.sentry.cache.PersistingScopeObserver$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                PersistingScopeObserver.this.m275lambda$setBreadcrumbs$1$iosentrycachePersistingScopeObserver(collection);
            }
        });
    }

    /* renamed from: lambda$setTags$2$io-sentry-cache-PersistingScopeObserver, reason: not valid java name */
    /* synthetic */ void m281lambda$setTags$2$iosentrycachePersistingScopeObserver(Map map) {
        store(map, "tags.json");
    }

    @Override // io.sentry.IScopeObserver
    public void setTags(final Map<String, String> map) {
        serializeToDisk(new Runnable() { // from class: io.sentry.cache.PersistingScopeObserver$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                PersistingScopeObserver.this.m281lambda$setTags$2$iosentrycachePersistingScopeObserver(map);
            }
        });
    }

    /* renamed from: lambda$setExtras$3$io-sentry-cache-PersistingScopeObserver, reason: not valid java name */
    /* synthetic */ void m277lambda$setExtras$3$iosentrycachePersistingScopeObserver(Map map) {
        store(map, EXTRAS_FILENAME);
    }

    @Override // io.sentry.IScopeObserver
    public void setExtras(final Map<String, Object> map) {
        serializeToDisk(new Runnable() { // from class: io.sentry.cache.PersistingScopeObserver$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                PersistingScopeObserver.this.m277lambda$setExtras$3$iosentrycachePersistingScopeObserver(map);
            }
        });
    }

    @Override // io.sentry.IScopeObserver
    public void setRequest(final Request request) {
        serializeToDisk(new Runnable() { // from class: io.sentry.cache.PersistingScopeObserver$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                PersistingScopeObserver.this.m280lambda$setRequest$4$iosentrycachePersistingScopeObserver(request);
            }
        });
    }

    /* renamed from: lambda$setRequest$4$io-sentry-cache-PersistingScopeObserver, reason: not valid java name */
    /* synthetic */ void m280lambda$setRequest$4$iosentrycachePersistingScopeObserver(Request request) {
        if (request == null) {
            delete(REQUEST_FILENAME);
        } else {
            store(request, REQUEST_FILENAME);
        }
    }

    /* renamed from: lambda$setFingerprint$5$io-sentry-cache-PersistingScopeObserver, reason: not valid java name */
    /* synthetic */ void m278lambda$setFingerprint$5$iosentrycachePersistingScopeObserver(Collection collection) {
        store(collection, FINGERPRINT_FILENAME);
    }

    @Override // io.sentry.IScopeObserver
    public void setFingerprint(final Collection<String> collection) {
        serializeToDisk(new Runnable() { // from class: io.sentry.cache.PersistingScopeObserver$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                PersistingScopeObserver.this.m278lambda$setFingerprint$5$iosentrycachePersistingScopeObserver(collection);
            }
        });
    }

    @Override // io.sentry.IScopeObserver
    public void setLevel(final SentryLevel sentryLevel) {
        serializeToDisk(new Runnable() { // from class: io.sentry.cache.PersistingScopeObserver$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                PersistingScopeObserver.this.m279lambda$setLevel$6$iosentrycachePersistingScopeObserver(sentryLevel);
            }
        });
    }

    /* renamed from: lambda$setLevel$6$io-sentry-cache-PersistingScopeObserver, reason: not valid java name */
    /* synthetic */ void m279lambda$setLevel$6$iosentrycachePersistingScopeObserver(SentryLevel sentryLevel) {
        if (sentryLevel == null) {
            delete(LEVEL_FILENAME);
        } else {
            store(sentryLevel, LEVEL_FILENAME);
        }
    }

    @Override // io.sentry.IScopeObserver
    public void setTransaction(final String str) {
        serializeToDisk(new Runnable() { // from class: io.sentry.cache.PersistingScopeObserver$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                PersistingScopeObserver.this.m283lambda$setTransaction$7$iosentrycachePersistingScopeObserver(str);
            }
        });
    }

    /* renamed from: lambda$setTransaction$7$io-sentry-cache-PersistingScopeObserver, reason: not valid java name */
    /* synthetic */ void m283lambda$setTransaction$7$iosentrycachePersistingScopeObserver(String str) {
        if (str == null) {
            delete(TRANSACTION_FILENAME);
        } else {
            store(str, TRANSACTION_FILENAME);
        }
    }

    @Override // io.sentry.IScopeObserver
    public void setTrace(final SpanContext spanContext) {
        serializeToDisk(new Runnable() { // from class: io.sentry.cache.PersistingScopeObserver$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                PersistingScopeObserver.this.m282lambda$setTrace$8$iosentrycachePersistingScopeObserver(spanContext);
            }
        });
    }

    /* renamed from: lambda$setTrace$8$io-sentry-cache-PersistingScopeObserver, reason: not valid java name */
    /* synthetic */ void m282lambda$setTrace$8$iosentrycachePersistingScopeObserver(SpanContext spanContext) {
        if (spanContext == null) {
            delete(TRACE_FILENAME);
        } else {
            store(spanContext, TRACE_FILENAME);
        }
    }

    /* renamed from: lambda$setContexts$9$io-sentry-cache-PersistingScopeObserver, reason: not valid java name */
    /* synthetic */ void m276lambda$setContexts$9$iosentrycachePersistingScopeObserver(Contexts contexts) {
        store(contexts, CONTEXTS_FILENAME);
    }

    @Override // io.sentry.IScopeObserver
    public void setContexts(final Contexts contexts) {
        serializeToDisk(new Runnable() { // from class: io.sentry.cache.PersistingScopeObserver$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                PersistingScopeObserver.this.m276lambda$setContexts$9$iosentrycachePersistingScopeObserver(contexts);
            }
        });
    }

    private void serializeToDisk(final Runnable runnable) {
        try {
            this.options.getExecutorService().submit(new Runnable() { // from class: io.sentry.cache.PersistingScopeObserver$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    PersistingScopeObserver.this.m274x4fa54fae(runnable);
                }
            });
        } catch (Throwable th) {
            this.options.getLogger().log(SentryLevel.ERROR, "Serialization task could not be scheduled", th);
        }
    }

    /* renamed from: lambda$serializeToDisk$10$io-sentry-cache-PersistingScopeObserver, reason: not valid java name */
    /* synthetic */ void m274x4fa54fae(Runnable runnable) {
        try {
            runnable.run();
        } catch (Throwable th) {
            this.options.getLogger().log(SentryLevel.ERROR, "Serialization task failed", th);
        }
    }

    private <T> void store(T t, String str) {
        CacheUtils.store(this.options, t, SCOPE_CACHE, str);
    }

    private void delete(String str) {
        CacheUtils.delete(this.options, SCOPE_CACHE, str);
    }

    public static <T> T read(SentryOptions sentryOptions, String str, Class<T> cls) {
        return (T) read(sentryOptions, str, cls, null);
    }

    public static <T, R> T read(SentryOptions sentryOptions, String str, Class<T> cls, JsonDeserializer<R> jsonDeserializer) {
        return (T) CacheUtils.read(sentryOptions, SCOPE_CACHE, str, cls, jsonDeserializer);
    }
}

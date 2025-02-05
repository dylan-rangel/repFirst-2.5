package io.sentry.android.ndk;

import io.sentry.Breadcrumb;
import io.sentry.DateUtils;
import io.sentry.IScopeObserver;
import io.sentry.SentryLevel;
import io.sentry.SentryOptions;
import io.sentry.SpanContext;
import io.sentry.protocol.Contexts;
import io.sentry.protocol.Request;
import io.sentry.protocol.User;
import io.sentry.util.Objects;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes3.dex */
public final class NdkScopeObserver implements IScopeObserver {
    private final INativeScope nativeScope;
    private final SentryOptions options;

    @Override // io.sentry.IScopeObserver
    public /* synthetic */ void setBreadcrumbs(Collection collection) {
        IScopeObserver.CC.$default$setBreadcrumbs(this, collection);
    }

    @Override // io.sentry.IScopeObserver
    public /* synthetic */ void setContexts(Contexts contexts) {
        IScopeObserver.CC.$default$setContexts(this, contexts);
    }

    @Override // io.sentry.IScopeObserver
    public /* synthetic */ void setExtras(Map map) {
        IScopeObserver.CC.$default$setExtras(this, map);
    }

    @Override // io.sentry.IScopeObserver
    public /* synthetic */ void setFingerprint(Collection collection) {
        IScopeObserver.CC.$default$setFingerprint(this, collection);
    }

    @Override // io.sentry.IScopeObserver
    public /* synthetic */ void setLevel(SentryLevel sentryLevel) {
        IScopeObserver.CC.$default$setLevel(this, sentryLevel);
    }

    @Override // io.sentry.IScopeObserver
    public /* synthetic */ void setRequest(Request request) {
        IScopeObserver.CC.$default$setRequest(this, request);
    }

    @Override // io.sentry.IScopeObserver
    public /* synthetic */ void setTags(Map map) {
        IScopeObserver.CC.$default$setTags(this, map);
    }

    @Override // io.sentry.IScopeObserver
    public /* synthetic */ void setTrace(SpanContext spanContext) {
        IScopeObserver.CC.$default$setTrace(this, spanContext);
    }

    @Override // io.sentry.IScopeObserver
    public /* synthetic */ void setTransaction(String str) {
        IScopeObserver.CC.$default$setTransaction(this, str);
    }

    public NdkScopeObserver(SentryOptions sentryOptions) {
        this(sentryOptions, new NativeScope());
    }

    NdkScopeObserver(SentryOptions sentryOptions, INativeScope iNativeScope) {
        this.options = (SentryOptions) Objects.requireNonNull(sentryOptions, "The SentryOptions object is required.");
        this.nativeScope = (INativeScope) Objects.requireNonNull(iNativeScope, "The NativeScope object is required.");
    }

    @Override // io.sentry.IScopeObserver
    public void setUser(User user) {
        try {
            if (user == null) {
                this.nativeScope.removeUser();
            } else {
                this.nativeScope.setUser(user.getId(), user.getEmail(), user.getIpAddress(), user.getUsername());
            }
        } catch (Throwable th) {
            this.options.getLogger().log(SentryLevel.ERROR, th, "Scope sync setUser has an error.", new Object[0]);
        }
    }

    @Override // io.sentry.IScopeObserver
    public void addBreadcrumb(Breadcrumb breadcrumb) {
        try {
            String str = null;
            String lowerCase = breadcrumb.getLevel() != null ? breadcrumb.getLevel().name().toLowerCase(Locale.ROOT) : null;
            String timestamp = DateUtils.getTimestamp(breadcrumb.getTimestamp());
            try {
                Map<String, Object> data = breadcrumb.getData();
                if (!data.isEmpty()) {
                    str = this.options.getSerializer().serialize(data);
                }
            } catch (Throwable th) {
                this.options.getLogger().log(SentryLevel.ERROR, th, "Breadcrumb data is not serializable.", new Object[0]);
            }
            this.nativeScope.addBreadcrumb(lowerCase, breadcrumb.getMessage(), breadcrumb.getCategory(), breadcrumb.getType(), timestamp, str);
        } catch (Throwable th2) {
            this.options.getLogger().log(SentryLevel.ERROR, th2, "Scope sync addBreadcrumb has an error.", new Object[0]);
        }
    }

    @Override // io.sentry.IScopeObserver
    public void setTag(String str, String str2) {
        try {
            this.nativeScope.setTag(str, str2);
        } catch (Throwable th) {
            this.options.getLogger().log(SentryLevel.ERROR, th, "Scope sync setTag(%s) has an error.", str);
        }
    }

    @Override // io.sentry.IScopeObserver
    public void removeTag(String str) {
        try {
            this.nativeScope.removeTag(str);
        } catch (Throwable th) {
            this.options.getLogger().log(SentryLevel.ERROR, th, "Scope sync removeTag(%s) has an error.", str);
        }
    }

    @Override // io.sentry.IScopeObserver
    public void setExtra(String str, String str2) {
        try {
            this.nativeScope.setExtra(str, str2);
        } catch (Throwable th) {
            this.options.getLogger().log(SentryLevel.ERROR, th, "Scope sync setExtra(%s) has an error.", str);
        }
    }

    @Override // io.sentry.IScopeObserver
    public void removeExtra(String str) {
        try {
            this.nativeScope.removeExtra(str);
        } catch (Throwable th) {
            this.options.getLogger().log(SentryLevel.ERROR, th, "Scope sync removeExtra(%s) has an error.", str);
        }
    }
}

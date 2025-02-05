package io.sentry;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import io.sentry.protocol.Contexts;
import io.sentry.protocol.Request;
import io.sentry.protocol.User;
import java.util.Collection;
import java.util.Map;

/* loaded from: classes3.dex */
public interface IScopeObserver {

    @SynthesizedClassV2(kind = 7, versionHash = "5e5398f0546d1d7afd62641edb14d82894f11ddc41bce363a0c8d0dac82c9c5a")
    /* renamed from: io.sentry.IScopeObserver$-CC, reason: invalid class name */
    public final /* synthetic */ class CC {
        public static void $default$addBreadcrumb(IScopeObserver _this, Breadcrumb breadcrumb) {
        }

        public static void $default$removeExtra(IScopeObserver _this, String str) {
        }

        public static void $default$removeTag(IScopeObserver _this, String str) {
        }

        public static void $default$setBreadcrumbs(IScopeObserver _this, Collection collection) {
        }

        public static void $default$setContexts(IScopeObserver _this, Contexts contexts) {
        }

        public static void $default$setExtra(IScopeObserver _this, String str, String str2) {
        }

        public static void $default$setExtras(IScopeObserver _this, Map map) {
        }

        public static void $default$setFingerprint(IScopeObserver _this, Collection collection) {
        }

        public static void $default$setLevel(IScopeObserver _this, SentryLevel sentryLevel) {
        }

        public static void $default$setRequest(IScopeObserver _this, Request request) {
        }

        public static void $default$setTag(IScopeObserver _this, String str, String str2) {
        }

        public static void $default$setTags(IScopeObserver _this, Map map) {
        }

        public static void $default$setTrace(IScopeObserver _this, SpanContext spanContext) {
        }

        public static void $default$setTransaction(IScopeObserver _this, String str) {
        }

        public static void $default$setUser(IScopeObserver _this, User user) {
        }
    }

    void addBreadcrumb(Breadcrumb breadcrumb);

    void removeExtra(String str);

    void removeTag(String str);

    void setBreadcrumbs(Collection<Breadcrumb> collection);

    void setContexts(Contexts contexts);

    void setExtra(String str, String str2);

    void setExtras(Map<String, Object> map);

    void setFingerprint(Collection<String> collection);

    void setLevel(SentryLevel sentryLevel);

    void setRequest(Request request);

    void setTag(String str, String str2);

    void setTags(Map<String, String> map);

    void setTrace(SpanContext spanContext);

    void setTransaction(String str);

    void setUser(User user);
}

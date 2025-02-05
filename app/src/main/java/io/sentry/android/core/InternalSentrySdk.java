package io.sentry.android.core;

import android.content.Context;
import android.content.pm.PackageInfo;
import io.sentry.DateUtils;
import io.sentry.HubAdapter;
import io.sentry.IHub;
import io.sentry.ILogger;
import io.sentry.ISerializer;
import io.sentry.Scope;
import io.sentry.ScopeCallback;
import io.sentry.SentryBaseEvent;
import io.sentry.SentryEnvelope;
import io.sentry.SentryEnvelopeItem;
import io.sentry.SentryEvent;
import io.sentry.SentryLevel;
import io.sentry.SentryOptions;
import io.sentry.Session;
import io.sentry.protocol.App;
import io.sentry.protocol.SentryId;
import io.sentry.protocol.User;
import io.sentry.util.MapObjectWriter;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes3.dex */
public final class InternalSentrySdk {
    public static Scope getCurrentScope() {
        final AtomicReference atomicReference = new AtomicReference();
        HubAdapter.getInstance().configureScope(new ScopeCallback() { // from class: io.sentry.android.core.InternalSentrySdk$$ExternalSyntheticLambda0
            @Override // io.sentry.ScopeCallback
            public final void run(Scope scope) {
                atomicReference.set(new Scope(scope));
            }
        });
        return (Scope) atomicReference.get();
    }

    public static Map<String, Object> serializeScope(Context context, SentryAndroidOptions sentryAndroidOptions, Scope scope) {
        HashMap hashMap = new HashMap();
        if (scope == null) {
            return hashMap;
        }
        try {
            ILogger logger = sentryAndroidOptions.getLogger();
            MapObjectWriter mapObjectWriter = new MapObjectWriter(hashMap);
            DeviceInfoUtil deviceInfoUtil = DeviceInfoUtil.getInstance(context, sentryAndroidOptions);
            scope.getContexts().setDevice(deviceInfoUtil.collectDeviceInformation(true, true));
            scope.getContexts().setOperatingSystem(deviceInfoUtil.getOperatingSystem());
            User user = scope.getUser();
            if (user == null) {
                user = new User();
                scope.setUser(user);
            }
            if (user.getId() == null) {
                try {
                    user.setId(Installation.id(context));
                } catch (RuntimeException e) {
                    logger.log(SentryLevel.ERROR, "Could not retrieve installation ID", e);
                }
            }
            if (scope.getContexts().getApp() == null) {
                App app = new App();
                app.setAppName(ContextUtils.getApplicationName(context, sentryAndroidOptions.getLogger()));
                app.setAppStartTime(DateUtils.toUtilDate(AppStartState.getInstance().getAppStartTime()));
                BuildInfoProvider buildInfoProvider = new BuildInfoProvider(sentryAndroidOptions.getLogger());
                PackageInfo packageInfo = ContextUtils.getPackageInfo(context, 4096, sentryAndroidOptions.getLogger(), buildInfoProvider);
                if (packageInfo != null) {
                    ContextUtils.setAppPackageInfo(packageInfo, buildInfoProvider, app);
                }
                scope.getContexts().setApp(app);
            }
            mapObjectWriter.name("user").value(logger, scope.getUser());
            mapObjectWriter.name("contexts").value(logger, scope.getContexts());
            mapObjectWriter.name("tags").value(logger, scope.getTags());
            mapObjectWriter.name("extras").value(logger, scope.getExtras());
            mapObjectWriter.name(SentryEvent.JsonKeys.FINGERPRINT).value(logger, scope.getFingerprint());
            mapObjectWriter.name("level").value(logger, scope.getLevel());
            mapObjectWriter.name(SentryBaseEvent.JsonKeys.BREADCRUMBS).value(logger, scope.getBreadcrumbs());
            return hashMap;
        } catch (Throwable th) {
            sentryAndroidOptions.getLogger().log(SentryLevel.ERROR, "Could not serialize scope.", th);
            return new HashMap();
        }
    }

    public static SentryId captureEnvelope(byte[] bArr) {
        HubAdapter hubAdapter = HubAdapter.getInstance();
        SentryOptions options = hubAdapter.getOptions();
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
            try {
                ISerializer serializer = options.getSerializer();
                SentryEnvelope read = options.getEnvelopeReader().read(byteArrayInputStream);
                if (read != null) {
                    ArrayList arrayList = new ArrayList();
                    boolean z = false;
                    Session.State state = null;
                    for (SentryEnvelopeItem sentryEnvelopeItem : read.getItems()) {
                        arrayList.add(sentryEnvelopeItem);
                        SentryEvent event = sentryEnvelopeItem.getEvent(serializer);
                        if (event != null) {
                            if (event.isCrashed()) {
                                state = Session.State.Crashed;
                            }
                            if (event.isCrashed() || event.isErrored()) {
                                z = true;
                            }
                        }
                    }
                    Session updateSession = updateSession(hubAdapter, options, state, z);
                    if (updateSession != null) {
                        arrayList.add(SentryEnvelopeItem.fromSession(serializer, updateSession));
                    }
                    SentryId captureEnvelope = hubAdapter.captureEnvelope(new SentryEnvelope(read.getHeader(), arrayList));
                    byteArrayInputStream.close();
                    return captureEnvelope;
                }
                byteArrayInputStream.close();
                return null;
            } finally {
            }
        } catch (Throwable th) {
            options.getLogger().log(SentryLevel.ERROR, "Failed to capture envelope", th);
            return null;
        }
    }

    private static Session updateSession(IHub iHub, final SentryOptions sentryOptions, final Session.State state, final boolean z) {
        final AtomicReference atomicReference = new AtomicReference();
        iHub.configureScope(new ScopeCallback() { // from class: io.sentry.android.core.InternalSentrySdk$$ExternalSyntheticLambda1
            @Override // io.sentry.ScopeCallback
            public final void run(Scope scope) {
                InternalSentrySdk.lambda$updateSession$1(Session.State.this, z, atomicReference, sentryOptions, scope);
            }
        });
        return (Session) atomicReference.get();
    }

    static /* synthetic */ void lambda$updateSession$1(Session.State state, boolean z, AtomicReference atomicReference, SentryOptions sentryOptions, Scope scope) {
        Session session = scope.getSession();
        if (session != null) {
            if (session.update(state, null, z, null)) {
                if (session.getStatus() == Session.State.Crashed) {
                    session.end();
                }
                atomicReference.set(session);
                return;
            }
            return;
        }
        sentryOptions.getLogger().log(SentryLevel.INFO, "Session is null on updateSession", new Object[0]);
    }
}

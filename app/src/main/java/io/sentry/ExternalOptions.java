package io.sentry;

import com.facebook.react.uimanager.ViewProps;
import io.sentry.SentryBaseEvent;
import io.sentry.SentryOptions;
import io.sentry.config.PropertiesProvider;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/* loaded from: classes3.dex */
public final class ExternalOptions {
    private static final String PROXY_PORT_DEFAULT = "80";
    private Boolean debug;
    private String dist;
    private String dsn;
    private Boolean enableDeduplication;
    private Boolean enablePrettySerializationOutput;
    private Boolean enableTracing;
    private Boolean enableUncaughtExceptionHandler;
    private Boolean enabled;
    private String environment;
    private Long idleTimeout;
    private List<String> ignoredCheckIns;
    private SentryOptions.RequestSize maxRequestBodySize;
    private Boolean printUncaughtStackTrace;
    private Double profilesSampleRate;
    private String proguardUuid;
    private SentryOptions.Proxy proxy;
    private String release;
    private Boolean sendClientReports;
    private Boolean sendModules;
    private String serverName;
    private Double tracesSampleRate;
    private final Map<String, String> tags = new ConcurrentHashMap();
    private final List<String> inAppExcludes = new CopyOnWriteArrayList();
    private final List<String> inAppIncludes = new CopyOnWriteArrayList();
    private List<String> tracePropagationTargets = null;
    private final List<String> contextTags = new CopyOnWriteArrayList();
    private final Set<Class<? extends Throwable>> ignoredExceptionsForType = new CopyOnWriteArraySet();
    private Set<String> bundleIds = new CopyOnWriteArraySet();

    /* JADX WARN: Multi-variable type inference failed */
    public static ExternalOptions from(PropertiesProvider propertiesProvider, ILogger iLogger) {
        ExternalOptions externalOptions = new ExternalOptions();
        externalOptions.setDsn(propertiesProvider.getProperty("dsn"));
        externalOptions.setEnvironment(propertiesProvider.getProperty("environment"));
        externalOptions.setRelease(propertiesProvider.getProperty("release"));
        externalOptions.setDist(propertiesProvider.getProperty(SentryBaseEvent.JsonKeys.DIST));
        externalOptions.setServerName(propertiesProvider.getProperty("servername"));
        externalOptions.setEnableUncaughtExceptionHandler(propertiesProvider.getBooleanProperty("uncaught.handler.enabled"));
        externalOptions.setPrintUncaughtStackTrace(propertiesProvider.getBooleanProperty("uncaught.handler.print-stacktrace"));
        externalOptions.setEnableTracing(propertiesProvider.getBooleanProperty("enable-tracing"));
        externalOptions.setTracesSampleRate(propertiesProvider.getDoubleProperty("traces-sample-rate"));
        externalOptions.setProfilesSampleRate(propertiesProvider.getDoubleProperty("profiles-sample-rate"));
        externalOptions.setDebug(propertiesProvider.getBooleanProperty(com.swmansion.reanimated.BuildConfig.BUILD_TYPE));
        externalOptions.setEnableDeduplication(propertiesProvider.getBooleanProperty("enable-deduplication"));
        externalOptions.setSendClientReports(propertiesProvider.getBooleanProperty("send-client-reports"));
        String property = propertiesProvider.getProperty("max-request-body-size");
        if (property != null) {
            externalOptions.setMaxRequestBodySize(SentryOptions.RequestSize.valueOf(property.toUpperCase(Locale.ROOT)));
        }
        for (Map.Entry<String, String> entry : propertiesProvider.getMap("tags").entrySet()) {
            externalOptions.setTag(entry.getKey(), entry.getValue());
        }
        String property2 = propertiesProvider.getProperty("proxy.host");
        String property3 = propertiesProvider.getProperty("proxy.user");
        String property4 = propertiesProvider.getProperty("proxy.pass");
        String property5 = propertiesProvider.getProperty("proxy.port", PROXY_PORT_DEFAULT);
        if (property2 != null) {
            externalOptions.setProxy(new SentryOptions.Proxy(property2, property5, property3, property4));
        }
        Iterator<String> it = propertiesProvider.getList("in-app-includes").iterator();
        while (it.hasNext()) {
            externalOptions.addInAppInclude(it.next());
        }
        Iterator<String> it2 = propertiesProvider.getList("in-app-excludes").iterator();
        while (it2.hasNext()) {
            externalOptions.addInAppExclude(it2.next());
        }
        List<String> list = propertiesProvider.getProperty("trace-propagation-targets") != null ? propertiesProvider.getList("trace-propagation-targets") : null;
        if (list == null && propertiesProvider.getProperty("tracing-origins") != null) {
            list = propertiesProvider.getList("tracing-origins");
        }
        if (list != null) {
            Iterator<String> it3 = list.iterator();
            while (it3.hasNext()) {
                externalOptions.addTracePropagationTarget(it3.next());
            }
        }
        Iterator<String> it4 = propertiesProvider.getList("context-tags").iterator();
        while (it4.hasNext()) {
            externalOptions.addContextTag(it4.next());
        }
        externalOptions.setProguardUuid(propertiesProvider.getProperty("proguard-uuid"));
        Iterator<String> it5 = propertiesProvider.getList("bundle-ids").iterator();
        while (it5.hasNext()) {
            externalOptions.addBundleId(it5.next());
        }
        externalOptions.setIdleTimeout(propertiesProvider.getLongProperty("idle-timeout"));
        externalOptions.setEnabled(propertiesProvider.getBooleanProperty(ViewProps.ENABLED));
        externalOptions.setEnablePrettySerializationOutput(propertiesProvider.getBooleanProperty("enable-pretty-serialization-output"));
        externalOptions.setSendModules(propertiesProvider.getBooleanProperty("send-modules"));
        externalOptions.setIgnoredCheckIns(propertiesProvider.getList("ignored-checkins"));
        for (String str : propertiesProvider.getList("ignored-exceptions-for-type")) {
            try {
                Class<?> cls = Class.forName(str);
                if (Throwable.class.isAssignableFrom(cls)) {
                    externalOptions.addIgnoredExceptionForType(cls);
                } else {
                    iLogger.log(SentryLevel.WARNING, "Skipping setting %s as ignored-exception-for-type. Reason: %s does not extend Throwable", str, str);
                }
            } catch (ClassNotFoundException unused) {
                iLogger.log(SentryLevel.WARNING, "Skipping setting %s as ignored-exception-for-type. Reason: %s class is not found", str, str);
            }
        }
        return externalOptions;
    }

    public String getDsn() {
        return this.dsn;
    }

    public void setDsn(String str) {
        this.dsn = str;
    }

    public String getEnvironment() {
        return this.environment;
    }

    public void setEnvironment(String str) {
        this.environment = str;
    }

    public String getRelease() {
        return this.release;
    }

    public void setRelease(String str) {
        this.release = str;
    }

    public String getDist() {
        return this.dist;
    }

    public void setDist(String str) {
        this.dist = str;
    }

    public String getServerName() {
        return this.serverName;
    }

    public void setServerName(String str) {
        this.serverName = str;
    }

    public Boolean getEnableUncaughtExceptionHandler() {
        return this.enableUncaughtExceptionHandler;
    }

    public void setEnableUncaughtExceptionHandler(Boolean bool) {
        this.enableUncaughtExceptionHandler = bool;
    }

    @Deprecated
    public List<String> getTracingOrigins() {
        return this.tracePropagationTargets;
    }

    public List<String> getTracePropagationTargets() {
        return this.tracePropagationTargets;
    }

    public Boolean getDebug() {
        return this.debug;
    }

    public void setDebug(Boolean bool) {
        this.debug = bool;
    }

    public Boolean getEnableDeduplication() {
        return this.enableDeduplication;
    }

    public void setEnableDeduplication(Boolean bool) {
        this.enableDeduplication = bool;
    }

    public Boolean getEnableTracing() {
        return this.enableTracing;
    }

    public void setEnableTracing(Boolean bool) {
        this.enableTracing = bool;
    }

    public Double getTracesSampleRate() {
        return this.tracesSampleRate;
    }

    public void setTracesSampleRate(Double d) {
        this.tracesSampleRate = d;
    }

    public Double getProfilesSampleRate() {
        return this.profilesSampleRate;
    }

    public void setProfilesSampleRate(Double d) {
        this.profilesSampleRate = d;
    }

    public SentryOptions.RequestSize getMaxRequestBodySize() {
        return this.maxRequestBodySize;
    }

    public void setMaxRequestBodySize(SentryOptions.RequestSize requestSize) {
        this.maxRequestBodySize = requestSize;
    }

    public Map<String, String> getTags() {
        return this.tags;
    }

    public SentryOptions.Proxy getProxy() {
        return this.proxy;
    }

    public void setProxy(SentryOptions.Proxy proxy) {
        this.proxy = proxy;
    }

    public List<String> getInAppExcludes() {
        return this.inAppExcludes;
    }

    public List<String> getInAppIncludes() {
        return this.inAppIncludes;
    }

    public List<String> getContextTags() {
        return this.contextTags;
    }

    public String getProguardUuid() {
        return this.proguardUuid;
    }

    public void setProguardUuid(String str) {
        this.proguardUuid = str;
    }

    public Set<Class<? extends Throwable>> getIgnoredExceptionsForType() {
        return this.ignoredExceptionsForType;
    }

    public void addInAppInclude(String str) {
        this.inAppIncludes.add(str);
    }

    public void addInAppExclude(String str) {
        this.inAppExcludes.add(str);
    }

    @Deprecated
    public void addTracingOrigin(String str) {
        addTracePropagationTarget(str);
    }

    public void addTracePropagationTarget(String str) {
        if (this.tracePropagationTargets == null) {
            this.tracePropagationTargets = new CopyOnWriteArrayList();
        }
        if (str.isEmpty()) {
            return;
        }
        this.tracePropagationTargets.add(str);
    }

    public void addContextTag(String str) {
        this.contextTags.add(str);
    }

    public void addIgnoredExceptionForType(Class<? extends Throwable> cls) {
        this.ignoredExceptionsForType.add(cls);
    }

    public void setTag(String str, String str2) {
        this.tags.put(str, str2);
    }

    public Boolean getPrintUncaughtStackTrace() {
        return this.printUncaughtStackTrace;
    }

    public void setPrintUncaughtStackTrace(Boolean bool) {
        this.printUncaughtStackTrace = bool;
    }

    public Long getIdleTimeout() {
        return this.idleTimeout;
    }

    public void setIdleTimeout(Long l) {
        this.idleTimeout = l;
    }

    public Boolean getSendClientReports() {
        return this.sendClientReports;
    }

    public void setSendClientReports(Boolean bool) {
        this.sendClientReports = bool;
    }

    public Set<String> getBundleIds() {
        return this.bundleIds;
    }

    public void addBundleId(String str) {
        this.bundleIds.add(str);
    }

    public Boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(Boolean bool) {
        this.enabled = bool;
    }

    public Boolean isEnablePrettySerializationOutput() {
        return this.enablePrettySerializationOutput;
    }

    public void setEnablePrettySerializationOutput(Boolean bool) {
        this.enablePrettySerializationOutput = bool;
    }

    public Boolean isSendModules() {
        return this.sendModules;
    }

    public void setSendModules(Boolean bool) {
        this.sendModules = bool;
    }

    public void setIgnoredCheckIns(List<String> list) {
        this.ignoredCheckIns = list;
    }

    public List<String> getIgnoredCheckIns() {
        return this.ignoredCheckIns;
    }
}

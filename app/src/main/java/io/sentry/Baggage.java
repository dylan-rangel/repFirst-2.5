package io.sentry;

import io.sentry.protocol.SentryId;
import io.sentry.protocol.TransactionNameSource;
import io.sentry.protocol.User;
import io.sentry.util.SampleRateUtils;
import io.sentry.util.StringUtils;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes3.dex */
public final class Baggage {
    static final String CHARSET = "UTF-8";
    static final String SENTRY_BAGGAGE_PREFIX = "sentry-";
    final Map<String, String> keyValues;
    final ILogger logger;
    private boolean mutable;
    final String thirdPartyHeader;
    static final Integer MAX_BAGGAGE_STRING_LENGTH = 8192;
    static final Integer MAX_BAGGAGE_LIST_MEMBER_COUNT = 64;

    public static final class DSCKeys {
        public static final String TRACE_ID = "sentry-trace_id";
        public static final String PUBLIC_KEY = "sentry-public_key";
        public static final String RELEASE = "sentry-release";
        public static final String USER_ID = "sentry-user_id";
        public static final String ENVIRONMENT = "sentry-environment";
        public static final String USER_SEGMENT = "sentry-user_segment";
        public static final String TRANSACTION = "sentry-transaction";
        public static final String SAMPLE_RATE = "sentry-sample_rate";
        public static final String SAMPLED = "sentry-sampled";
        public static final List<String> ALL = Arrays.asList(TRACE_ID, PUBLIC_KEY, RELEASE, USER_ID, ENVIRONMENT, USER_SEGMENT, TRANSACTION, SAMPLE_RATE, SAMPLED);
    }

    public static Baggage fromHeader(String str) {
        return fromHeader(str, false, HubAdapter.getInstance().getOptions().getLogger());
    }

    public static Baggage fromHeader(List<String> list) {
        return fromHeader(list, false, HubAdapter.getInstance().getOptions().getLogger());
    }

    public static Baggage fromHeader(String str, ILogger iLogger) {
        return fromHeader(str, false, iLogger);
    }

    public static Baggage fromHeader(List<String> list, ILogger iLogger) {
        return fromHeader(list, false, iLogger);
    }

    public static Baggage fromHeader(List<String> list, boolean z, ILogger iLogger) {
        if (list != null) {
            return fromHeader(StringUtils.join(",", list), z, iLogger);
        }
        return fromHeader((String) null, z, iLogger);
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0082  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static io.sentry.Baggage fromHeader(java.lang.String r16, boolean r17, io.sentry.ILogger r18) {
        /*
            r1 = r16
            r2 = r18
            java.util.HashMap r3 = new java.util.HashMap
            r3.<init>()
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
            java.lang.String r5 = ","
            r6 = 1
            if (r1 == 0) goto L7a
            r7 = 0
            r0 = -1
            java.lang.String[] r8 = r1.split(r5, r0)     // Catch: java.lang.Throwable -> L6c
            int r9 = r8.length     // Catch: java.lang.Throwable -> L6c
            r10 = 0
            r11 = 1
        L1c:
            if (r10 >= r9) goto L79
            r12 = r8[r10]     // Catch: java.lang.Throwable -> L6a
            java.lang.String r0 = r12.trim()     // Catch: java.lang.Throwable -> L6a
            java.lang.String r13 = "sentry-"
            boolean r0 = r0.startsWith(r13)     // Catch: java.lang.Throwable -> L6a
            if (r0 == 0) goto L5e
            java.lang.String r0 = "="
            int r0 = r12.indexOf(r0)     // Catch: java.lang.Throwable -> L51
            java.lang.String r13 = r12.substring(r7, r0)     // Catch: java.lang.Throwable -> L51
            java.lang.String r13 = r13.trim()     // Catch: java.lang.Throwable -> L51
            java.lang.String r13 = decode(r13)     // Catch: java.lang.Throwable -> L51
            int r0 = r0 + 1
            java.lang.String r0 = r12.substring(r0)     // Catch: java.lang.Throwable -> L51
            java.lang.String r0 = r0.trim()     // Catch: java.lang.Throwable -> L51
            java.lang.String r0 = decode(r0)     // Catch: java.lang.Throwable -> L51
            r3.put(r13, r0)     // Catch: java.lang.Throwable -> L51
            r11 = 0
            goto L67
        L51:
            r0 = move-exception
            io.sentry.SentryLevel r13 = io.sentry.SentryLevel.ERROR     // Catch: java.lang.Throwable -> L6a
            java.lang.String r14 = "Unable to decode baggage key value pair %s"
            java.lang.Object[] r15 = new java.lang.Object[r6]     // Catch: java.lang.Throwable -> L6a
            r15[r7] = r12     // Catch: java.lang.Throwable -> L6a
            r2.log(r13, r0, r14, r15)     // Catch: java.lang.Throwable -> L6a
            goto L67
        L5e:
            if (r17 == 0) goto L67
            java.lang.String r0 = r12.trim()     // Catch: java.lang.Throwable -> L6a
            r4.add(r0)     // Catch: java.lang.Throwable -> L6a
        L67:
            int r10 = r10 + 1
            goto L1c
        L6a:
            r0 = move-exception
            goto L6e
        L6c:
            r0 = move-exception
            r11 = 1
        L6e:
            io.sentry.SentryLevel r8 = io.sentry.SentryLevel.ERROR
            java.lang.Object[] r6 = new java.lang.Object[r6]
            r6[r7] = r1
            java.lang.String r1 = "Unable to decode baggage header %s"
            r2.log(r8, r0, r1, r6)
        L79:
            r6 = r11
        L7a:
            boolean r0 = r4.isEmpty()
            if (r0 == 0) goto L82
            r0 = 0
            goto L86
        L82:
            java.lang.String r0 = io.sentry.util.StringUtils.join(r5, r4)
        L86:
            io.sentry.Baggage r1 = new io.sentry.Baggage
            r1.<init>(r3, r0, r6, r2)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: io.sentry.Baggage.fromHeader(java.lang.String, boolean, io.sentry.ILogger):io.sentry.Baggage");
    }

    public static Baggage fromEvent(SentryEvent sentryEvent, SentryOptions sentryOptions) {
        Baggage baggage = new Baggage(sentryOptions.getLogger());
        SpanContext trace = sentryEvent.getContexts().getTrace();
        baggage.setTraceId(trace != null ? trace.getTraceId().toString() : null);
        baggage.setPublicKey(new Dsn(sentryOptions.getDsn()).getPublicKey());
        baggage.setRelease(sentryEvent.getRelease());
        baggage.setEnvironment(sentryEvent.getEnvironment());
        User user = sentryEvent.getUser();
        baggage.setUserSegment(user != null ? getSegment(user) : null);
        baggage.setTransaction(sentryEvent.getTransaction());
        baggage.setSampleRate(null);
        baggage.setSampled(null);
        baggage.freeze();
        return baggage;
    }

    public Baggage(ILogger iLogger) {
        this(new HashMap(), null, true, iLogger);
    }

    public Baggage(Baggage baggage) {
        this(baggage.keyValues, baggage.thirdPartyHeader, baggage.mutable, baggage.logger);
    }

    public Baggage(Map<String, String> map, String str, boolean z, ILogger iLogger) {
        this.keyValues = map;
        this.logger = iLogger;
        this.mutable = z;
        this.thirdPartyHeader = str;
    }

    public void freeze() {
        this.mutable = false;
    }

    public boolean isMutable() {
        return this.mutable;
    }

    public String getThirdPartyHeader() {
        return this.thirdPartyHeader;
    }

    public String toHeaderString(String str) {
        String str2;
        int i;
        StringBuilder sb = new StringBuilder();
        if (str == null || str.isEmpty()) {
            str2 = "";
            i = 0;
        } else {
            sb.append(str);
            i = StringUtils.countOf(str, ',') + 1;
            str2 = ",";
        }
        for (String str3 : new TreeSet(this.keyValues.keySet())) {
            String str4 = this.keyValues.get(str3);
            if (str4 != null) {
                Integer num = MAX_BAGGAGE_LIST_MEMBER_COUNT;
                if (i >= num.intValue()) {
                    this.logger.log(SentryLevel.ERROR, "Not adding baggage value %s as the total number of list members would exceed the maximum of %s.", str3, num);
                } else {
                    try {
                        String str5 = str2 + encode(str3) + "=" + encode(str4);
                        int length = sb.length() + str5.length();
                        Integer num2 = MAX_BAGGAGE_STRING_LENGTH;
                        if (length > num2.intValue()) {
                            this.logger.log(SentryLevel.ERROR, "Not adding baggage value %s as the total header value length would exceed the maximum of %s.", str3, num2);
                        } else {
                            i++;
                            sb.append(str5);
                            str2 = ",";
                        }
                    } catch (Throwable th) {
                        this.logger.log(SentryLevel.ERROR, th, "Unable to encode baggage key value pair (key=%s,value=%s).", str3, str4);
                    }
                }
            }
        }
        return sb.toString();
    }

    private String encode(String str) throws UnsupportedEncodingException {
        return URLEncoder.encode(str, "UTF-8").replaceAll("\\+", "%20");
    }

    private static String decode(String str) throws UnsupportedEncodingException {
        return URLDecoder.decode(str, "UTF-8");
    }

    public String get(String str) {
        if (str == null) {
            return null;
        }
        return this.keyValues.get(str);
    }

    public String getTraceId() {
        return get(DSCKeys.TRACE_ID);
    }

    public void setTraceId(String str) {
        set(DSCKeys.TRACE_ID, str);
    }

    public String getPublicKey() {
        return get(DSCKeys.PUBLIC_KEY);
    }

    public void setPublicKey(String str) {
        set(DSCKeys.PUBLIC_KEY, str);
    }

    public String getEnvironment() {
        return get(DSCKeys.ENVIRONMENT);
    }

    public void setEnvironment(String str) {
        set(DSCKeys.ENVIRONMENT, str);
    }

    public String getRelease() {
        return get(DSCKeys.RELEASE);
    }

    public void setRelease(String str) {
        set(DSCKeys.RELEASE, str);
    }

    public String getUserId() {
        return get(DSCKeys.USER_ID);
    }

    public void setUserId(String str) {
        set(DSCKeys.USER_ID, str);
    }

    public String getUserSegment() {
        return get(DSCKeys.USER_SEGMENT);
    }

    public void setUserSegment(String str) {
        set(DSCKeys.USER_SEGMENT, str);
    }

    public String getTransaction() {
        return get(DSCKeys.TRANSACTION);
    }

    public void setTransaction(String str) {
        set(DSCKeys.TRANSACTION, str);
    }

    public String getSampleRate() {
        return get(DSCKeys.SAMPLE_RATE);
    }

    public String getSampled() {
        return get(DSCKeys.SAMPLED);
    }

    public void setSampleRate(String str) {
        set(DSCKeys.SAMPLE_RATE, str);
    }

    public void setSampled(String str) {
        set(DSCKeys.SAMPLED, str);
    }

    public void set(String str, String str2) {
        if (this.mutable) {
            this.keyValues.put(str, str2);
        }
    }

    public Map<String, Object> getUnknown() {
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        for (Map.Entry<String, String> entry : this.keyValues.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (!DSCKeys.ALL.contains(key) && value != null) {
                concurrentHashMap.put(key.replaceFirst(SENTRY_BAGGAGE_PREFIX, ""), value);
            }
        }
        return concurrentHashMap;
    }

    public void setValuesFromTransaction(ITransaction iTransaction, User user, SentryOptions sentryOptions, TracesSamplingDecision tracesSamplingDecision) {
        setTraceId(iTransaction.getSpanContext().getTraceId().toString());
        setPublicKey(new Dsn(sentryOptions.getDsn()).getPublicKey());
        setRelease(sentryOptions.getRelease());
        setEnvironment(sentryOptions.getEnvironment());
        setUserSegment(user != null ? getSegment(user) : null);
        setTransaction(isHighQualityTransactionName(iTransaction.getTransactionNameSource()) ? iTransaction.getName() : null);
        setSampleRate(sampleRateToString(sampleRate(tracesSamplingDecision)));
        setSampled(StringUtils.toString(sampled(tracesSamplingDecision)));
    }

    public void setValuesFromScope(Scope scope, SentryOptions sentryOptions) {
        PropagationContext propagationContext = scope.getPropagationContext();
        User user = scope.getUser();
        setTraceId(propagationContext.getTraceId().toString());
        setPublicKey(new Dsn(sentryOptions.getDsn()).getPublicKey());
        setRelease(sentryOptions.getRelease());
        setEnvironment(sentryOptions.getEnvironment());
        setUserSegment(user != null ? getSegment(user) : null);
        setTransaction(null);
        setSampleRate(null);
        setSampled(null);
    }

    private static String getSegment(User user) {
        if (user.getSegment() != null) {
            return user.getSegment();
        }
        Map<String, String> data = user.getData();
        if (data != null) {
            return data.get("segment");
        }
        return null;
    }

    private static Double sampleRate(TracesSamplingDecision tracesSamplingDecision) {
        if (tracesSamplingDecision == null) {
            return null;
        }
        return tracesSamplingDecision.getSampleRate();
    }

    private static String sampleRateToString(Double d) {
        if (SampleRateUtils.isValidTracesSampleRate(d, false)) {
            return new DecimalFormat("#.################", DecimalFormatSymbols.getInstance(Locale.ROOT)).format(d);
        }
        return null;
    }

    private static Boolean sampled(TracesSamplingDecision tracesSamplingDecision) {
        if (tracesSamplingDecision == null) {
            return null;
        }
        return tracesSamplingDecision.getSampled();
    }

    private static boolean isHighQualityTransactionName(TransactionNameSource transactionNameSource) {
        return (transactionNameSource == null || TransactionNameSource.URL.equals(transactionNameSource)) ? false : true;
    }

    public Double getSampleRateDouble() {
        String sampleRate = getSampleRate();
        if (sampleRate != null) {
            try {
                double parseDouble = Double.parseDouble(sampleRate);
                if (SampleRateUtils.isValidTracesSampleRate(Double.valueOf(parseDouble), false)) {
                    return Double.valueOf(parseDouble);
                }
            } catch (NumberFormatException unused) {
            }
        }
        return null;
    }

    public TraceContext toTraceContext() {
        String traceId = getTraceId();
        String publicKey = getPublicKey();
        if (traceId == null || publicKey == null) {
            return null;
        }
        TraceContext traceContext = new TraceContext(new SentryId(traceId), publicKey, getRelease(), getEnvironment(), getUserId(), getUserSegment(), getTransaction(), getSampleRate(), getSampled());
        traceContext.setUnknown(getUnknown());
        return traceContext;
    }
}

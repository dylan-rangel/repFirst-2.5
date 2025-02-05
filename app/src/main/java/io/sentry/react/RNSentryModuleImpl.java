package io.sentry.react;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.SparseIntArray;
import androidx.core.app.FrameMetricsAggregator;
import com.facebook.hermes.instrumentation.HermesSamplingProfiler;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;
import io.sentry.Breadcrumb;
import io.sentry.DateUtils;
import io.sentry.Hint;
import io.sentry.HubAdapter;
import io.sentry.ILogger;
import io.sentry.Integration;
import io.sentry.Scope;
import io.sentry.ScopeCallback;
import io.sentry.Sentry;
import io.sentry.SentryBaseEvent;
import io.sentry.SentryDate;
import io.sentry.SentryEvent;
import io.sentry.SentryLevel;
import io.sentry.SentryOptions;
import io.sentry.Session;
import io.sentry.UncaughtExceptionHandlerIntegration;
import io.sentry.android.core.AndroidLogger;
import io.sentry.android.core.AnrIntegration;
import io.sentry.android.core.AppStartState;
import io.sentry.android.core.BuildInfoProvider;
import io.sentry.android.core.CurrentActivityHolder;
import io.sentry.android.core.InternalSentrySdk;
import io.sentry.android.core.NdkIntegration;
import io.sentry.android.core.SentryAndroid;
import io.sentry.android.core.SentryAndroidOptions;
import io.sentry.android.core.ViewHierarchyEventProcessor;
import io.sentry.android.core.internal.util.ScreenshotUtils;
import io.sentry.protocol.OperatingSystem;
import io.sentry.protocol.SdkVersion;
import io.sentry.protocol.SentryException;
import io.sentry.protocol.SentryPackage;
import io.sentry.protocol.User;
import io.sentry.protocol.ViewHierarchy;
import io.sentry.util.JsonSerializationUtils;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class RNSentryModuleImpl {
    private static final String ANDROID_SDK_NAME = "sentry.java.android.react-native";
    private static final int FROZEN_FRAME_THRESHOLD = 700;
    public static final String NAME = "RNSentry";
    private static final String NATIVE_SDK_NAME = "sentry.native.android.react-native";
    private static final int SCREENSHOT_TIMEOUT_SECONDS = 2;
    private static final int SLOW_FRAME_THRESHOLD = 16;
    private static final Charset UTF_8;
    private static final BuildInfoProvider buildInfo;
    private static boolean didFetchAppStart = false;
    private static final ILogger logger;
    private static final String modulesPath = "modules.json";
    private boolean androidXAvailable;
    private FrameMetricsAggregator frameMetricsAggregator = null;
    private final PackageInfo packageInfo;
    private final ReactApplicationContext reactApplicationContext;

    static {
        AndroidLogger androidLogger = new AndroidLogger(NAME);
        logger = androidLogger;
        buildInfo = new BuildInfoProvider(androidLogger);
        UTF_8 = Charset.forName("UTF-8");
    }

    public RNSentryModuleImpl(ReactApplicationContext reactApplicationContext) {
        this.packageInfo = getPackageInfo(reactApplicationContext);
        this.reactApplicationContext = reactApplicationContext;
    }

    private ReactApplicationContext getReactApplicationContext() {
        return this.reactApplicationContext;
    }

    private Activity getCurrentActivity() {
        return this.reactApplicationContext.getCurrentActivity();
    }

    public void initNativeSdk(final ReadableMap readableMap, Promise promise) {
        SentryAndroid.init(getReactApplicationContext(), (Sentry.OptionsConfiguration<SentryAndroidOptions>) new Sentry.OptionsConfiguration() { // from class: io.sentry.react.RNSentryModuleImpl$$ExternalSyntheticLambda1
            @Override // io.sentry.Sentry.OptionsConfiguration
            public final void configure(SentryOptions sentryOptions) {
                RNSentryModuleImpl.this.m293lambda$initNativeSdk$1$iosentryreactRNSentryModuleImpl(readableMap, (SentryAndroidOptions) sentryOptions);
            }
        });
        promise.resolve(true);
    }

    /* renamed from: lambda$initNativeSdk$1$io-sentry-react-RNSentryModuleImpl, reason: not valid java name */
    /* synthetic */ void m293lambda$initNativeSdk$1$iosentryreactRNSentryModuleImpl(ReadableMap readableMap, final SentryAndroidOptions sentryAndroidOptions) {
        SdkVersion sdkVersion = sentryAndroidOptions.getSdkVersion();
        if (sdkVersion == null) {
            sdkVersion = new SdkVersion(ANDROID_SDK_NAME, "6.33.1");
        } else {
            sdkVersion.setName(ANDROID_SDK_NAME);
        }
        sentryAndroidOptions.setSentryClientName(sdkVersion.getName() + "/" + sdkVersion.getVersion());
        sentryAndroidOptions.setNativeSdkName(NATIVE_SDK_NAME);
        sentryAndroidOptions.setSdkVersion(sdkVersion);
        if (readableMap.hasKey(com.swmansion.reanimated.BuildConfig.BUILD_TYPE) && readableMap.getBoolean(com.swmansion.reanimated.BuildConfig.BUILD_TYPE)) {
            sentryAndroidOptions.setDebug(true);
        }
        if (readableMap.hasKey("dsn") && readableMap.getString("dsn") != null) {
            String string = readableMap.getString("dsn");
            logger.log(SentryLevel.INFO, String.format("Starting with DSN: '%s'", string), new Object[0]);
            sentryAndroidOptions.setDsn(string);
        } else {
            sentryAndroidOptions.setDsn("");
        }
        if (readableMap.hasKey("sendClientReports")) {
            sentryAndroidOptions.setSendClientReports(readableMap.getBoolean("sendClientReports"));
        }
        if (readableMap.hasKey("maxBreadcrumbs")) {
            sentryAndroidOptions.setMaxBreadcrumbs(readableMap.getInt("maxBreadcrumbs"));
        }
        if (readableMap.hasKey("maxCacheItems")) {
            sentryAndroidOptions.setMaxCacheItems(readableMap.getInt("maxCacheItems"));
        }
        if (readableMap.hasKey("environment") && readableMap.getString("environment") != null) {
            sentryAndroidOptions.setEnvironment(readableMap.getString("environment"));
        }
        if (readableMap.hasKey("release") && readableMap.getString("release") != null) {
            sentryAndroidOptions.setRelease(readableMap.getString("release"));
        }
        if (readableMap.hasKey(SentryBaseEvent.JsonKeys.DIST) && readableMap.getString(SentryBaseEvent.JsonKeys.DIST) != null) {
            sentryAndroidOptions.setDist(readableMap.getString(SentryBaseEvent.JsonKeys.DIST));
        }
        if (readableMap.hasKey("enableAutoSessionTracking")) {
            sentryAndroidOptions.setEnableAutoSessionTracking(readableMap.getBoolean("enableAutoSessionTracking"));
        }
        if (readableMap.hasKey("sessionTrackingIntervalMillis")) {
            sentryAndroidOptions.setSessionTrackingIntervalMillis(readableMap.getInt("sessionTrackingIntervalMillis"));
        }
        if (readableMap.hasKey("shutdownTimeout")) {
            sentryAndroidOptions.setShutdownTimeoutMillis(readableMap.getInt("shutdownTimeout"));
        }
        if (readableMap.hasKey("enableNdkScopeSync")) {
            sentryAndroidOptions.setEnableScopeSync(readableMap.getBoolean("enableNdkScopeSync"));
        }
        if (readableMap.hasKey("attachStacktrace")) {
            sentryAndroidOptions.setAttachStacktrace(readableMap.getBoolean("attachStacktrace"));
        }
        if (readableMap.hasKey("attachThreads")) {
            sentryAndroidOptions.setAttachThreads(readableMap.getBoolean("attachThreads"));
        }
        if (readableMap.hasKey("attachScreenshot")) {
            sentryAndroidOptions.setAttachScreenshot(readableMap.getBoolean("attachScreenshot"));
        }
        if (readableMap.hasKey("attachViewHierarchy")) {
            sentryAndroidOptions.setAttachViewHierarchy(readableMap.getBoolean("attachViewHierarchy"));
        }
        if (readableMap.hasKey("sendDefaultPii")) {
            sentryAndroidOptions.setSendDefaultPii(readableMap.getBoolean("sendDefaultPii"));
        }
        if (readableMap.hasKey("maxQueueSize")) {
            sentryAndroidOptions.setMaxQueueSize(readableMap.getInt("maxQueueSize"));
        }
        if (readableMap.hasKey("enableNdk")) {
            sentryAndroidOptions.setEnableNdk(readableMap.getBoolean("enableNdk"));
        }
        sentryAndroidOptions.setBeforeSend(new SentryOptions.BeforeSendCallback() { // from class: io.sentry.react.RNSentryModuleImpl$$ExternalSyntheticLambda0
            @Override // io.sentry.SentryOptions.BeforeSendCallback
            public final SentryEvent execute(SentryEvent sentryEvent, Hint hint) {
                return RNSentryModuleImpl.this.m292lambda$initNativeSdk$0$iosentryreactRNSentryModuleImpl(sentryAndroidOptions, sentryEvent, hint);
            }
        });
        if (readableMap.hasKey("enableNativeCrashHandling") && !readableMap.getBoolean("enableNativeCrashHandling")) {
            List<Integration> integrations = sentryAndroidOptions.getIntegrations();
            for (Integration integration : integrations) {
                if ((integration instanceof UncaughtExceptionHandlerIntegration) || (integration instanceof AnrIntegration) || (integration instanceof NdkIntegration)) {
                    integrations.remove(integration);
                }
            }
        }
        logger.log(SentryLevel.INFO, String.format("Native Integrations '%s'", sentryAndroidOptions.getIntegrations()), new Object[0]);
        CurrentActivityHolder currentActivityHolder = CurrentActivityHolder.getInstance();
        Activity currentActivity = getCurrentActivity();
        if (currentActivity != null) {
            currentActivityHolder.setActivity(currentActivity);
        }
    }

    /* renamed from: lambda$initNativeSdk$0$io-sentry-react-RNSentryModuleImpl, reason: not valid java name */
    /* synthetic */ SentryEvent m292lambda$initNativeSdk$0$iosentryreactRNSentryModuleImpl(SentryAndroidOptions sentryAndroidOptions, SentryEvent sentryEvent, Hint hint) {
        try {
            SentryException sentryException = sentryEvent.getExceptions().get(0);
            if (sentryException != null) {
                if (sentryException.getType().contains("JavascriptException")) {
                    return null;
                }
            }
        } catch (Throwable unused) {
        }
        setEventOriginTag(sentryEvent);
        addPackages(sentryEvent, sentryAndroidOptions.getSdkVersion());
        return sentryEvent;
    }

    public void crash() {
        throw new RuntimeException("TEST - Sentry Client Crash (only works in release mode)");
    }

    public void fetchModules(Promise promise) {
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(getReactApplicationContext().getResources().getAssets().open(modulesPath));
            try {
                byte[] bArr = new byte[bufferedInputStream.available()];
                bufferedInputStream.read(bArr);
                bufferedInputStream.close();
                promise.resolve(new String(bArr, UTF_8));
                bufferedInputStream.close();
            } catch (Throwable th) {
                try {
                    bufferedInputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (FileNotFoundException unused) {
            promise.resolve(null);
        } catch (Throwable unused2) {
            logger.log(SentryLevel.WARNING, "Fetching JS Modules failed.", new Object[0]);
            promise.resolve(null);
        }
    }

    public void fetchNativeRelease(Promise promise) {
        WritableMap createMap = Arguments.createMap();
        createMap.putString("id", this.packageInfo.packageName);
        createMap.putString("version", this.packageInfo.versionName);
        createMap.putString(OperatingSystem.JsonKeys.BUILD, String.valueOf(this.packageInfo.versionCode));
        promise.resolve(createMap);
    }

    public void fetchNativeAppStart(Promise promise) {
        AppStartState appStartState = AppStartState.getInstance();
        SentryDate appStartTime = appStartState.getAppStartTime();
        Boolean isColdStart = appStartState.isColdStart();
        if (appStartTime == null) {
            logger.log(SentryLevel.WARNING, "App start won't be sent due to missing appStartTime.", new Object[0]);
            promise.resolve(null);
        } else if (isColdStart == null) {
            logger.log(SentryLevel.WARNING, "App start won't be sent due to missing isColdStart.", new Object[0]);
            promise.resolve(null);
        } else {
            double nanosToMillis = DateUtils.nanosToMillis(appStartTime.nanoTimestamp());
            WritableMap createMap = Arguments.createMap();
            createMap.putDouble("appStartTime", nanosToMillis);
            createMap.putBoolean("isColdStart", isColdStart.booleanValue());
            createMap.putBoolean("didFetchAppStart", didFetchAppStart);
            promise.resolve(createMap);
        }
        didFetchAppStart = true;
    }

    public void fetchNativeFrames(Promise promise) {
        int i;
        int i2;
        int i3;
        SparseIntArray sparseIntArray;
        if (!isFrameMetricsAggregatorAvailable()) {
            promise.resolve(null);
            return;
        }
        try {
            SparseIntArray[] metrics = this.frameMetricsAggregator.getMetrics();
            if (metrics == null || (sparseIntArray = metrics[0]) == null) {
                i = 0;
                i2 = 0;
                i3 = 0;
            } else {
                i = 0;
                i2 = 0;
                i3 = 0;
                for (int i4 = 0; i4 < sparseIntArray.size(); i4++) {
                    int keyAt = sparseIntArray.keyAt(i4);
                    int valueAt = sparseIntArray.valueAt(i4);
                    i += valueAt;
                    if (keyAt > 700) {
                        i3 += valueAt;
                    } else if (keyAt > 16) {
                        i2 += valueAt;
                    }
                }
            }
            if (i == 0 && i2 == 0 && i3 == 0) {
                promise.resolve(null);
                return;
            }
            WritableMap createMap = Arguments.createMap();
            createMap.putInt("totalFrames", i);
            createMap.putInt("slowFrames", i2);
            createMap.putInt("frozenFrames", i3);
            promise.resolve(createMap);
        } catch (Throwable unused) {
            logger.log(SentryLevel.WARNING, "Error fetching native frames.", new Object[0]);
            promise.resolve(null);
        }
    }

    public void captureEnvelope(ReadableArray readableArray, ReadableMap readableMap, Promise promise) {
        byte[] bArr = new byte[readableArray.size()];
        for (int i = 0; i < readableArray.size(); i++) {
            bArr[i] = (byte) readableArray.getInt(i);
        }
        try {
            InternalSentrySdk.captureEnvelope(bArr);
        } catch (Throwable unused) {
            logger.log(SentryLevel.ERROR, "Error while capturing envelope", new Object[0]);
            promise.resolve(false);
        }
        promise.resolve(true);
    }

    public void captureScreenshot(Promise promise) {
        Activity currentActivity = getCurrentActivity();
        if (currentActivity == null) {
            logger.log(SentryLevel.WARNING, "CurrentActivity is null, can't capture screenshot.", new Object[0]);
            promise.resolve(null);
            return;
        }
        byte[] takeScreenshotOnUiThread = takeScreenshotOnUiThread(currentActivity);
        if (takeScreenshotOnUiThread == null) {
            logger.log(SentryLevel.WARNING, "Screenshot is null, screen was not captured.", new Object[0]);
            promise.resolve(null);
            return;
        }
        WritableNativeArray writableNativeArray = new WritableNativeArray();
        for (byte b : takeScreenshotOnUiThread) {
            writableNativeArray.pushInt(b);
        }
        WritableNativeMap writableNativeMap = new WritableNativeMap();
        writableNativeMap.putString("contentType", "image/png");
        writableNativeMap.putArray("data", writableNativeArray);
        writableNativeMap.putString("filename", "screenshot.png");
        WritableNativeArray writableNativeArray2 = new WritableNativeArray();
        writableNativeArray2.pushMap(writableNativeMap);
        promise.resolve(writableNativeArray2);
    }

    private static byte[] takeScreenshotOnUiThread(final Activity activity) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final byte[][] bArr = {new byte[0]};
        Runnable runnable = new Runnable() { // from class: io.sentry.react.RNSentryModuleImpl$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                RNSentryModuleImpl.lambda$takeScreenshotOnUiThread$2(bArr, activity, countDownLatch);
            }
        };
        if (UiThreadUtil.isOnUiThread()) {
            runnable.run();
        } else {
            UiThreadUtil.runOnUiThread(runnable);
        }
        try {
            countDownLatch.await(2L, TimeUnit.SECONDS);
            return bArr[0];
        } catch (InterruptedException unused) {
            logger.log(SentryLevel.ERROR, "Screenshot process was interrupted.", new Object[0]);
            return null;
        }
    }

    static /* synthetic */ void lambda$takeScreenshotOnUiThread$2(byte[][] bArr, Activity activity, CountDownLatch countDownLatch) {
        bArr[0] = ScreenshotUtils.takeScreenshot(activity, logger, buildInfo);
        countDownLatch.countDown();
    }

    public void fetchViewHierarchy(Promise promise) {
        Activity currentActivity = getCurrentActivity();
        ILogger iLogger = logger;
        ViewHierarchy snapshotViewHierarchy = ViewHierarchyEventProcessor.snapshotViewHierarchy(currentActivity, iLogger);
        if (snapshotViewHierarchy == null) {
            iLogger.log(SentryLevel.ERROR, "Could not get ViewHierarchy.", new Object[0]);
            promise.resolve(null);
            return;
        }
        byte[] bytesFrom = JsonSerializationUtils.bytesFrom(HubAdapter.getInstance().getOptions().getSerializer(), iLogger, snapshotViewHierarchy);
        if (bytesFrom == null) {
            iLogger.log(SentryLevel.ERROR, "Could not serialize ViewHierarchy.", new Object[0]);
            promise.resolve(null);
        } else {
            if (bytesFrom.length < 1) {
                iLogger.log(SentryLevel.ERROR, "Got empty bytes array after serializing ViewHierarchy.", new Object[0]);
                promise.resolve(null);
                return;
            }
            WritableNativeArray writableNativeArray = new WritableNativeArray();
            for (byte b : bytesFrom) {
                writableNativeArray.pushInt(b);
            }
            promise.resolve(writableNativeArray);
        }
    }

    private static PackageInfo getPackageInfo(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException unused) {
            logger.log(SentryLevel.WARNING, "Error getting package info.", new Object[0]);
            return null;
        }
    }

    public void setUser(final ReadableMap readableMap, final ReadableMap readableMap2) {
        Sentry.configureScope(new ScopeCallback() { // from class: io.sentry.react.RNSentryModuleImpl$$ExternalSyntheticLambda8
            @Override // io.sentry.ScopeCallback
            public final void run(Scope scope) {
                RNSentryModuleImpl.lambda$setUser$3(ReadableMap.this, readableMap2, scope);
            }
        });
    }

    static /* synthetic */ void lambda$setUser$3(ReadableMap readableMap, ReadableMap readableMap2, Scope scope) {
        if (readableMap == null && readableMap2 == null) {
            scope.setUser(null);
            return;
        }
        User user = new User();
        if (readableMap != null) {
            if (readableMap.hasKey("email")) {
                user.setEmail(readableMap.getString("email"));
            }
            if (readableMap.hasKey("id")) {
                user.setId(readableMap.getString("id"));
            }
            if (readableMap.hasKey("username")) {
                user.setUsername(readableMap.getString("username"));
            }
            if (readableMap.hasKey("ip_address")) {
                user.setIpAddress(readableMap.getString("ip_address"));
            }
            if (readableMap.hasKey("segment")) {
                user.setSegment(readableMap.getString("segment"));
            }
        }
        if (readableMap2 != null) {
            HashMap hashMap = new HashMap();
            ReadableMapKeySetIterator keySetIterator = readableMap2.keySetIterator();
            while (keySetIterator.hasNextKey()) {
                String nextKey = keySetIterator.nextKey();
                String string = readableMap2.getString(nextKey);
                if (string != null) {
                    hashMap.put(nextKey, string);
                }
            }
            user.setData(hashMap);
        }
        scope.setUser(user);
    }

    public void addBreadcrumb(final ReadableMap readableMap) {
        Sentry.configureScope(new ScopeCallback() { // from class: io.sentry.react.RNSentryModuleImpl$$ExternalSyntheticLambda4
            @Override // io.sentry.ScopeCallback
            public final void run(Scope scope) {
                RNSentryModuleImpl.lambda$addBreadcrumb$4(ReadableMap.this, scope);
            }
        });
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    static /* synthetic */ void lambda$addBreadcrumb$4(ReadableMap readableMap, Scope scope) {
        Breadcrumb breadcrumb = new Breadcrumb();
        if (readableMap.hasKey("message")) {
            breadcrumb.setMessage(readableMap.getString("message"));
        }
        if (readableMap.hasKey("type")) {
            breadcrumb.setType(readableMap.getString("type"));
        }
        if (readableMap.hasKey("category")) {
            breadcrumb.setCategory(readableMap.getString("category"));
        }
        if (readableMap.hasKey("level")) {
            String string = readableMap.getString("level");
            char c = 65535;
            switch (string.hashCode()) {
                case 3237038:
                    if (string.equals("info")) {
                        c = 4;
                        break;
                    }
                    break;
                case 95458899:
                    if (string.equals(com.swmansion.reanimated.BuildConfig.BUILD_TYPE)) {
                        c = 2;
                        break;
                    }
                    break;
                case 96784904:
                    if (string.equals("error")) {
                        c = 3;
                        break;
                    }
                    break;
                case 97203460:
                    if (string.equals("fatal")) {
                        c = 0;
                        break;
                    }
                    break;
                case 1124446108:
                    if (string.equals("warning")) {
                        c = 1;
                        break;
                    }
                    break;
            }
            if (c == 0) {
                breadcrumb.setLevel(SentryLevel.FATAL);
            } else if (c == 1) {
                breadcrumb.setLevel(SentryLevel.WARNING);
            } else if (c == 2) {
                breadcrumb.setLevel(SentryLevel.DEBUG);
            } else if (c == 3) {
                breadcrumb.setLevel(SentryLevel.ERROR);
            } else {
                breadcrumb.setLevel(SentryLevel.INFO);
            }
        }
        if (readableMap.hasKey("data")) {
            for (Map.Entry<String, Object> entry : readableMap.getMap("data").toHashMap().entrySet()) {
                if (entry.getValue() != null) {
                    breadcrumb.setData(entry.getKey(), entry.getValue());
                }
            }
        }
        scope.addBreadcrumb(breadcrumb);
    }

    public void clearBreadcrumbs() {
        Sentry.configureScope(new ScopeCallback() { // from class: io.sentry.react.RNSentryModuleImpl$$ExternalSyntheticLambda5
            @Override // io.sentry.ScopeCallback
            public final void run(Scope scope) {
                scope.clearBreadcrumbs();
            }
        });
    }

    public void setExtra(final String str, final String str2) {
        Sentry.configureScope(new ScopeCallback() { // from class: io.sentry.react.RNSentryModuleImpl$$ExternalSyntheticLambda7
            @Override // io.sentry.ScopeCallback
            public final void run(Scope scope) {
                scope.setExtra(str, str2);
            }
        });
    }

    public void setContext(final String str, final ReadableMap readableMap) {
        if (str == null || readableMap == null) {
            return;
        }
        Sentry.configureScope(new ScopeCallback() { // from class: io.sentry.react.RNSentryModuleImpl$$ExternalSyntheticLambda3
            @Override // io.sentry.ScopeCallback
            public final void run(Scope scope) {
                scope.setContexts(str, ReadableMap.this.toHashMap());
            }
        });
    }

    public void setTag(final String str, final String str2) {
        Sentry.configureScope(new ScopeCallback() { // from class: io.sentry.react.RNSentryModuleImpl$$ExternalSyntheticLambda2
            @Override // io.sentry.ScopeCallback
            public final void run(Scope scope) {
                scope.setTag(str, str2);
            }
        });
    }

    public void closeNativeSdk(Promise promise) {
        Sentry.close();
        disableNativeFramesTracking();
        promise.resolve(true);
    }

    public void enableNativeFramesTracking() {
        boolean checkAndroidXAvailability = checkAndroidXAvailability();
        this.androidXAvailable = checkAndroidXAvailability;
        if (checkAndroidXAvailability) {
            this.frameMetricsAggregator = new FrameMetricsAggregator();
            Activity currentActivity = getCurrentActivity();
            FrameMetricsAggregator frameMetricsAggregator = this.frameMetricsAggregator;
            if (frameMetricsAggregator != null && currentActivity != null) {
                try {
                    frameMetricsAggregator.add(currentActivity);
                    logger.log(SentryLevel.INFO, "FrameMetricsAggregator installed.", new Object[0]);
                    return;
                } catch (Throwable unused) {
                    logger.log(SentryLevel.ERROR, "Error adding Activity to frameMetricsAggregator.", new Object[0]);
                    return;
                }
            }
            logger.log(SentryLevel.INFO, "currentActivity isn't available.", new Object[0]);
            return;
        }
        logger.log(SentryLevel.WARNING, "androidx.core' isn't available as a dependency.", new Object[0]);
    }

    public void disableNativeFramesTracking() {
        if (isFrameMetricsAggregatorAvailable()) {
            this.frameMetricsAggregator.stop();
            this.frameMetricsAggregator = null;
        }
    }

    public WritableMap startProfiling() {
        WritableNativeMap writableNativeMap = new WritableNativeMap();
        try {
            HermesSamplingProfiler.enable();
            writableNativeMap.putBoolean(Session.JsonKeys.STARTED, true);
        } catch (Throwable th) {
            writableNativeMap.putBoolean(Session.JsonKeys.STARTED, false);
            writableNativeMap.putString("error", th.toString());
        }
        return writableNativeMap;
    }

    public WritableMap stopProfiling() {
        boolean isDebug = HubAdapter.getInstance().getOptions().isDebug();
        WritableNativeMap writableNativeMap = new WritableNativeMap();
        File file = null;
        try {
            HermesSamplingProfiler.disable();
            file = File.createTempFile("sampling-profiler-trace", ".cpuprofile", this.reactApplicationContext.getCacheDir());
            if (isDebug) {
                logger.log(SentryLevel.INFO, "Profile saved to: " + file.getAbsolutePath(), new Object[0]);
            }
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            try {
                HermesSamplingProfiler.dumpSampledTraceToFile(file.getPath());
                StringBuilder sb = new StringBuilder();
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                    sb.append(readLine);
                    sb.append('\n');
                }
                writableNativeMap.putString("profile", sb.toString());
                bufferedReader.close();
                if (file != null) {
                    try {
                        if (!file.delete()) {
                            logger.log(SentryLevel.WARNING, "Profile not deleted from:" + file.getAbsolutePath(), new Object[0]);
                        }
                    } catch (Throwable unused) {
                        logger.log(SentryLevel.WARNING, "Profile not deleted from:" + file.getAbsolutePath(), new Object[0]);
                    }
                }
            } finally {
            }
        } catch (Throwable th) {
            try {
                writableNativeMap.putString("error", th.toString());
                if (file != null) {
                    try {
                        if (!file.delete()) {
                            logger.log(SentryLevel.WARNING, "Profile not deleted from:" + file.getAbsolutePath(), new Object[0]);
                        }
                    } catch (Throwable unused2) {
                        logger.log(SentryLevel.WARNING, "Profile not deleted from:" + file.getAbsolutePath(), new Object[0]);
                    }
                }
            } catch (Throwable th2) {
                if (file != null) {
                    try {
                        if (!file.delete()) {
                            logger.log(SentryLevel.WARNING, "Profile not deleted from:" + file.getAbsolutePath(), new Object[0]);
                        }
                    } catch (Throwable unused3) {
                        logger.log(SentryLevel.WARNING, "Profile not deleted from:" + file.getAbsolutePath(), new Object[0]);
                    }
                }
                throw th2;
            }
        }
        return writableNativeMap;
    }

    public void fetchNativeDeviceContexts(Promise promise) {
        SentryOptions options = HubAdapter.getInstance().getOptions();
        if (!(options instanceof SentryAndroidOptions)) {
            promise.resolve(null);
            return;
        }
        Context applicationContext = getReactApplicationContext().getApplicationContext();
        if (applicationContext == null) {
            promise.resolve(null);
        } else {
            promise.resolve(MapConverter.convertToWritable(InternalSentrySdk.serializeScope(applicationContext, (SentryAndroidOptions) options, InternalSentrySdk.getCurrentScope())));
        }
    }

    public void fetchNativeSdkInfo(Promise promise) {
        SdkVersion sdkVersion = HubAdapter.getInstance().getOptions().getSdkVersion();
        if (sdkVersion == null) {
            promise.resolve(null);
            return;
        }
        WritableNativeMap writableNativeMap = new WritableNativeMap();
        writableNativeMap.putString("name", sdkVersion.getName());
        writableNativeMap.putString("version", sdkVersion.getVersion());
        promise.resolve(writableNativeMap);
    }

    public void fetchNativePackageName(Promise promise) {
        promise.resolve(this.packageInfo.packageName);
    }

    private void setEventOriginTag(SentryEvent sentryEvent) {
        SdkVersion sdk = sentryEvent.getSdk();
        if (sdk != null) {
            String name = sdk.getName();
            name.hashCode();
            if (name.equals(ANDROID_SDK_NAME)) {
                setEventEnvironmentTag(sentryEvent, SentryBaseEvent.DEFAULT_PLATFORM);
            } else if (name.equals(NATIVE_SDK_NAME)) {
                setEventEnvironmentTag(sentryEvent, "native");
            }
        }
    }

    private void setEventEnvironmentTag(SentryEvent sentryEvent, String str) {
        sentryEvent.setTag("event.origin", "android");
        sentryEvent.setTag("event.environment", str);
    }

    private void addPackages(SentryEvent sentryEvent, SdkVersion sdkVersion) {
        SdkVersion sdk = sentryEvent.getSdk();
        if (sdk == null || !sdk.getName().equals("sentry.javascript.react-native") || sdkVersion == null) {
            return;
        }
        List<SentryPackage> packages = sdkVersion.getPackages();
        if (packages != null) {
            for (SentryPackage sentryPackage : packages) {
                sdk.addPackage(sentryPackage.getName(), sentryPackage.getVersion());
            }
        }
        List<String> integrations = sdkVersion.getIntegrations();
        if (integrations != null) {
            Iterator<String> it = integrations.iterator();
            while (it.hasNext()) {
                sdk.addIntegration(it.next());
            }
        }
        sentryEvent.setSdk(sdk);
    }

    private boolean checkAndroidXAvailability() {
        try {
            Class.forName("androidx.core.app.FrameMetricsAggregator");
            return true;
        } catch (ClassNotFoundException unused) {
            return false;
        }
    }

    private boolean isFrameMetricsAggregatorAvailable() {
        return this.androidXAvailable && this.frameMetricsAggregator != null;
    }
}

package io.sentry.android.core;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import androidx.core.app.NotificationCompat;
import io.sentry.Breadcrumb;
import io.sentry.FullyDisplayedReporter;
import io.sentry.Hint;
import io.sentry.IHub;
import io.sentry.ISpan;
import io.sentry.ITransaction;
import io.sentry.Instrumenter;
import io.sentry.Integration;
import io.sentry.MeasurementUnit;
import io.sentry.NoOpTransaction;
import io.sentry.Scope;
import io.sentry.ScopeCallback;
import io.sentry.SentryDate;
import io.sentry.SentryIntegrationPackageStorage;
import io.sentry.SentryLevel;
import io.sentry.SentryOptions;
import io.sentry.Session;
import io.sentry.SpanStatus;
import io.sentry.TransactionContext;
import io.sentry.TransactionFinishedCallback;
import io.sentry.TransactionOptions;
import io.sentry.TypeCheckHint;
import io.sentry.android.core.internal.util.FirstDrawDoneListener;
import io.sentry.protocol.MeasurementValue;
import io.sentry.protocol.SentryThread;
import io.sentry.protocol.TransactionNameSource;
import io.sentry.util.Objects;
import io.sentry.util.TracingUtils;
import java.io.Closeable;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public final class ActivityLifecycleIntegration implements Integration, Closeable, Application.ActivityLifecycleCallbacks {
    static final String APP_START_COLD = "app.start.cold";
    static final String APP_START_WARM = "app.start.warm";
    private static final String TRACE_ORIGIN = "auto.ui.activity";
    static final String TTFD_OP = "ui.load.full_display";
    static final long TTFD_TIMEOUT_MILLIS = 30000;
    static final String TTID_OP = "ui.load.initial_display";
    static final String UI_LOAD_OP = "ui.load";
    private final ActivityFramesTracker activityFramesTracker;
    private ISpan appStartSpan;
    private final Application application;
    private final BuildInfoProvider buildInfoProvider;
    private final boolean foregroundImportance;
    private IHub hub;
    private boolean isAllActivityCallbacksAvailable;
    private SentryAndroidOptions options;
    private boolean performanceEnabled = false;
    private boolean timeToFullDisplaySpanEnabled = false;
    private boolean firstActivityCreated = false;
    private FullyDisplayedReporter fullyDisplayedReporter = null;
    private final WeakHashMap<Activity, ISpan> ttidSpanMap = new WeakHashMap<>();
    private final WeakHashMap<Activity, ISpan> ttfdSpanMap = new WeakHashMap<>();
    private SentryDate lastPausedTime = AndroidDateUtils.getCurrentSentryDateTime();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    private Future<?> ttfdAutoCloseFuture = null;
    private final WeakHashMap<Activity, ITransaction> activitiesWithOngoingTransactions = new WeakHashMap<>();

    private String getAppStartDesc(boolean z) {
        return z ? "Cold Start" : "Warm Start";
    }

    private String getAppStartOp(boolean z) {
        return z ? APP_START_COLD : APP_START_WARM;
    }

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

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPostResumed(Activity activity) {
    }

    public ActivityLifecycleIntegration(Application application, BuildInfoProvider buildInfoProvider, ActivityFramesTracker activityFramesTracker) {
        Application application2 = (Application) Objects.requireNonNull(application, "Application is required");
        this.application = application2;
        this.buildInfoProvider = (BuildInfoProvider) Objects.requireNonNull(buildInfoProvider, "BuildInfoProvider is required");
        this.activityFramesTracker = (ActivityFramesTracker) Objects.requireNonNull(activityFramesTracker, "ActivityFramesTracker is required");
        if (buildInfoProvider.getSdkInfoVersion() >= 29) {
            this.isAllActivityCallbacksAvailable = true;
        }
        this.foregroundImportance = ContextUtils.isForegroundImportance(application2);
    }

    @Override // io.sentry.Integration
    public void register(IHub iHub, SentryOptions sentryOptions) {
        this.options = (SentryAndroidOptions) Objects.requireNonNull(sentryOptions instanceof SentryAndroidOptions ? (SentryAndroidOptions) sentryOptions : null, "SentryAndroidOptions is required");
        this.hub = (IHub) Objects.requireNonNull(iHub, "Hub is required");
        this.options.getLogger().log(SentryLevel.DEBUG, "ActivityLifecycleIntegration enabled: %s", Boolean.valueOf(this.options.isEnableActivityLifecycleBreadcrumbs()));
        this.performanceEnabled = isPerformanceEnabled(this.options);
        this.fullyDisplayedReporter = this.options.getFullyDisplayedReporter();
        this.timeToFullDisplaySpanEnabled = this.options.isEnableTimeToFullDisplayTracing();
        this.application.registerActivityLifecycleCallbacks(this);
        this.options.getLogger().log(SentryLevel.DEBUG, "ActivityLifecycleIntegration installed.", new Object[0]);
        addIntegrationToSdkVersion();
    }

    private boolean isPerformanceEnabled(SentryAndroidOptions sentryAndroidOptions) {
        return sentryAndroidOptions.isTracingEnabled() && sentryAndroidOptions.isEnableAutoActivityLifecycleTracing();
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.application.unregisterActivityLifecycleCallbacks(this);
        SentryAndroidOptions sentryAndroidOptions = this.options;
        if (sentryAndroidOptions != null) {
            sentryAndroidOptions.getLogger().log(SentryLevel.DEBUG, "ActivityLifecycleIntegration removed.", new Object[0]);
        }
        this.activityFramesTracker.stop();
    }

    private void addBreadcrumb(Activity activity, String str) {
        SentryAndroidOptions sentryAndroidOptions = this.options;
        if (sentryAndroidOptions == null || this.hub == null || !sentryAndroidOptions.isEnableActivityLifecycleBreadcrumbs()) {
            return;
        }
        Breadcrumb breadcrumb = new Breadcrumb();
        breadcrumb.setType(NotificationCompat.CATEGORY_NAVIGATION);
        breadcrumb.setData(SentryThread.JsonKeys.STATE, str);
        breadcrumb.setData("screen", getActivityName(activity));
        breadcrumb.setCategory("ui.lifecycle");
        breadcrumb.setLevel(SentryLevel.INFO);
        Hint hint = new Hint();
        hint.set(TypeCheckHint.ANDROID_ACTIVITY, activity);
        this.hub.addBreadcrumb(breadcrumb, hint);
    }

    private String getActivityName(Activity activity) {
        return activity.getClass().getSimpleName();
    }

    private void stopPreviousTransactions() {
        for (Map.Entry<Activity, ITransaction> entry : this.activitiesWithOngoingTransactions.entrySet()) {
            finishTransaction(entry.getValue(), this.ttidSpanMap.get(entry.getKey()), this.ttfdSpanMap.get(entry.getKey()));
        }
    }

    private void startTracing(Activity activity) {
        final WeakReference weakReference = new WeakReference(activity);
        if (this.hub == null || isRunningTransactionOrTrace(activity)) {
            return;
        }
        boolean z = this.performanceEnabled;
        if (!z) {
            this.activitiesWithOngoingTransactions.put(activity, NoOpTransaction.getInstance());
            TracingUtils.startNewTrace(this.hub);
            return;
        }
        if (z) {
            stopPreviousTransactions();
            final String activityName = getActivityName(activity);
            SentryDate appStartTime = this.foregroundImportance ? AppStartState.getInstance().getAppStartTime() : null;
            Boolean isColdStart = AppStartState.getInstance().isColdStart();
            TransactionOptions transactionOptions = new TransactionOptions();
            if (this.options.isEnableActivityLifecycleTracingAutoFinish()) {
                transactionOptions.setIdleTimeout(this.options.getIdleTimeout());
                transactionOptions.setTrimEnd(true);
            }
            transactionOptions.setWaitForChildren(true);
            transactionOptions.setTransactionFinishedCallback(new TransactionFinishedCallback() { // from class: io.sentry.android.core.ActivityLifecycleIntegration$$ExternalSyntheticLambda5
                @Override // io.sentry.TransactionFinishedCallback
                public final void execute(ITransaction iTransaction) {
                    ActivityLifecycleIntegration.this.m251x4339eaa7(weakReference, activityName, iTransaction);
                }
            });
            SentryDate sentryDate = (this.firstActivityCreated || appStartTime == null || isColdStart == null) ? this.lastPausedTime : appStartTime;
            transactionOptions.setStartTimestamp(sentryDate);
            final ITransaction startTransaction = this.hub.startTransaction(new TransactionContext(activityName, TransactionNameSource.COMPONENT, UI_LOAD_OP), transactionOptions);
            setSpanOrigin(startTransaction);
            if (!this.firstActivityCreated && appStartTime != null && isColdStart != null) {
                ISpan startChild = startTransaction.startChild(getAppStartOp(isColdStart.booleanValue()), getAppStartDesc(isColdStart.booleanValue()), appStartTime, Instrumenter.SENTRY);
                this.appStartSpan = startChild;
                setSpanOrigin(startChild);
                finishAppStartSpan();
            }
            final ISpan startChild2 = startTransaction.startChild(TTID_OP, getTtidDesc(activityName), sentryDate, Instrumenter.SENTRY);
            this.ttidSpanMap.put(activity, startChild2);
            setSpanOrigin(startChild2);
            if (this.timeToFullDisplaySpanEnabled && this.fullyDisplayedReporter != null && this.options != null) {
                final ISpan startChild3 = startTransaction.startChild(TTFD_OP, getTtfdDesc(activityName), sentryDate, Instrumenter.SENTRY);
                setSpanOrigin(startChild3);
                try {
                    this.ttfdSpanMap.put(activity, startChild3);
                    this.ttfdAutoCloseFuture = this.options.getExecutorService().schedule(new Runnable() { // from class: io.sentry.android.core.ActivityLifecycleIntegration$$ExternalSyntheticLambda6
                        @Override // java.lang.Runnable
                        public final void run() {
                            ActivityLifecycleIntegration.this.m252xd0749c28(startChild3, startChild2);
                        }
                    }, TTFD_TIMEOUT_MILLIS);
                } catch (RejectedExecutionException e) {
                    this.options.getLogger().log(SentryLevel.ERROR, "Failed to call the executor. Time to full display span will not be finished automatically. Did you call Sentry.close()?", e);
                }
            }
            this.hub.configureScope(new ScopeCallback() { // from class: io.sentry.android.core.ActivityLifecycleIntegration$$ExternalSyntheticLambda7
                @Override // io.sentry.ScopeCallback
                public final void run(Scope scope) {
                    ActivityLifecycleIntegration.this.m253x5daf4da9(startTransaction, scope);
                }
            });
            this.activitiesWithOngoingTransactions.put(activity, startTransaction);
        }
    }

    /* renamed from: lambda$startTracing$0$io-sentry-android-core-ActivityLifecycleIntegration, reason: not valid java name */
    /* synthetic */ void m251x4339eaa7(WeakReference weakReference, String str, ITransaction iTransaction) {
        Activity activity = (Activity) weakReference.get();
        if (activity != null) {
            this.activityFramesTracker.setMetrics(activity, iTransaction.getEventId());
            return;
        }
        SentryAndroidOptions sentryAndroidOptions = this.options;
        if (sentryAndroidOptions != null) {
            sentryAndroidOptions.getLogger().log(SentryLevel.WARNING, "Unable to track activity frames as the Activity %s has been destroyed.", str);
        }
    }

    private void setSpanOrigin(ISpan iSpan) {
        if (iSpan != null) {
            iSpan.getSpanContext().setOrigin(TRACE_ORIGIN);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: applyScope, reason: merged with bridge method [inline-methods] */
    public void m253x5daf4da9(final Scope scope, final ITransaction iTransaction) {
        scope.withTransaction(new Scope.IWithTransaction() { // from class: io.sentry.android.core.ActivityLifecycleIntegration$$ExternalSyntheticLambda3
            @Override // io.sentry.Scope.IWithTransaction
            public final void accept(ITransaction iTransaction2) {
                ActivityLifecycleIntegration.this.m246xd15ba770(scope, iTransaction, iTransaction2);
            }
        });
    }

    /* renamed from: lambda$applyScope$3$io-sentry-android-core-ActivityLifecycleIntegration, reason: not valid java name */
    /* synthetic */ void m246xd15ba770(Scope scope, ITransaction iTransaction, ITransaction iTransaction2) {
        if (iTransaction2 == null) {
            scope.setTransaction(iTransaction);
            return;
        }
        SentryAndroidOptions sentryAndroidOptions = this.options;
        if (sentryAndroidOptions != null) {
            sentryAndroidOptions.getLogger().log(SentryLevel.DEBUG, "Transaction '%s' won't be bound to the Scope since there's one already in there.", iTransaction.getName());
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: clearScope, reason: merged with bridge method [inline-methods] */
    public void m247x44f2e05d(final Scope scope, final ITransaction iTransaction) {
        scope.withTransaction(new Scope.IWithTransaction() { // from class: io.sentry.android.core.ActivityLifecycleIntegration$$ExternalSyntheticLambda2
            @Override // io.sentry.Scope.IWithTransaction
            public final void accept(ITransaction iTransaction2) {
                ActivityLifecycleIntegration.lambda$clearScope$4(ITransaction.this, scope, iTransaction2);
            }
        });
    }

    static /* synthetic */ void lambda$clearScope$4(ITransaction iTransaction, Scope scope, ITransaction iTransaction2) {
        if (iTransaction2 == iTransaction) {
            scope.clearTransaction();
        }
    }

    private boolean isRunningTransactionOrTrace(Activity activity) {
        return this.activitiesWithOngoingTransactions.containsKey(activity);
    }

    private void stopTracing(Activity activity, boolean z) {
        if (this.performanceEnabled && z) {
            finishTransaction(this.activitiesWithOngoingTransactions.get(activity), null, null);
        }
    }

    private void finishTransaction(final ITransaction iTransaction, ISpan iSpan, ISpan iSpan2) {
        if (iTransaction == null || iTransaction.isFinished()) {
            return;
        }
        finishSpan(iSpan, SpanStatus.DEADLINE_EXCEEDED);
        m252xd0749c28(iSpan2, iSpan);
        cancelTtfdAutoClose();
        SpanStatus status = iTransaction.getStatus();
        if (status == null) {
            status = SpanStatus.OK;
        }
        iTransaction.finish(status);
        IHub iHub = this.hub;
        if (iHub != null) {
            iHub.configureScope(new ScopeCallback() { // from class: io.sentry.android.core.ActivityLifecycleIntegration$$ExternalSyntheticLambda4
                @Override // io.sentry.ScopeCallback
                public final void run(Scope scope) {
                    ActivityLifecycleIntegration.this.m247x44f2e05d(iTransaction, scope);
                }
            });
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public synchronized void onActivityCreated(Activity activity, Bundle bundle) {
        setColdStart(bundle);
        addBreadcrumb(activity, "created");
        startTracing(activity);
        final ISpan iSpan = this.ttfdSpanMap.get(activity);
        this.firstActivityCreated = true;
        FullyDisplayedReporter fullyDisplayedReporter = this.fullyDisplayedReporter;
        if (fullyDisplayedReporter != null) {
            fullyDisplayedReporter.registerFullyDrawnListener(new FullyDisplayedReporter.FullyDisplayedReporterListener() { // from class: io.sentry.android.core.ActivityLifecycleIntegration$$ExternalSyntheticLambda8
                @Override // io.sentry.FullyDisplayedReporter.FullyDisplayedReporterListener
                public final void onFullyDrawn() {
                    ActivityLifecycleIntegration.this.m248x7f71294d(iSpan);
                }
            });
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public synchronized void onActivityStarted(Activity activity) {
        if (this.performanceEnabled) {
            this.activityFramesTracker.addActivity(activity);
        }
        addBreadcrumb(activity, Session.JsonKeys.STARTED);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public synchronized void onActivityResumed(Activity activity) {
        if (this.performanceEnabled) {
            SentryDate appStartTime = AppStartState.getInstance().getAppStartTime();
            SentryDate appStartEndTime = AppStartState.getInstance().getAppStartEndTime();
            if (appStartTime != null && appStartEndTime == null) {
                AppStartState.getInstance().setAppStartEnd();
            }
            finishAppStartSpan();
            final ISpan iSpan = this.ttidSpanMap.get(activity);
            final ISpan iSpan2 = this.ttfdSpanMap.get(activity);
            View findViewById = activity.findViewById(android.R.id.content);
            if (this.buildInfoProvider.getSdkInfoVersion() >= 16 && findViewById != null) {
                FirstDrawDoneListener.registerForNextDraw(findViewById, new Runnable() { // from class: io.sentry.android.core.ActivityLifecycleIntegration$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        ActivityLifecycleIntegration.this.m249x90c9b07d(iSpan2, iSpan);
                    }
                }, this.buildInfoProvider);
            } else {
                this.mainHandler.post(new Runnable() { // from class: io.sentry.android.core.ActivityLifecycleIntegration$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        ActivityLifecycleIntegration.this.m250x1e0461fe(iSpan2, iSpan);
                    }
                });
            }
        }
        addBreadcrumb(activity, "resumed");
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPrePaused(Activity activity) {
        if (this.isAllActivityCallbacksAvailable) {
            IHub iHub = this.hub;
            if (iHub == null) {
                this.lastPausedTime = AndroidDateUtils.getCurrentSentryDateTime();
            } else {
                this.lastPausedTime = iHub.getOptions().getDateProvider().now();
            }
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public synchronized void onActivityPaused(Activity activity) {
        if (!this.isAllActivityCallbacksAvailable) {
            IHub iHub = this.hub;
            if (iHub == null) {
                this.lastPausedTime = AndroidDateUtils.getCurrentSentryDateTime();
            } else {
                this.lastPausedTime = iHub.getOptions().getDateProvider().now();
            }
        }
        addBreadcrumb(activity, "paused");
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public synchronized void onActivityStopped(Activity activity) {
        addBreadcrumb(activity, "stopped");
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public synchronized void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        addBreadcrumb(activity, "saveInstanceState");
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public synchronized void onActivityDestroyed(Activity activity) {
        if (this.performanceEnabled || this.options.isEnableActivityLifecycleBreadcrumbs()) {
            addBreadcrumb(activity, "destroyed");
            finishSpan(this.appStartSpan, SpanStatus.CANCELLED);
            ISpan iSpan = this.ttidSpanMap.get(activity);
            ISpan iSpan2 = this.ttfdSpanMap.get(activity);
            finishSpan(iSpan, SpanStatus.DEADLINE_EXCEEDED);
            m252xd0749c28(iSpan2, iSpan);
            cancelTtfdAutoClose();
            stopTracing(activity, true);
            this.appStartSpan = null;
            this.ttidSpanMap.remove(activity);
            this.ttfdSpanMap.remove(activity);
        }
        this.activitiesWithOngoingTransactions.remove(activity);
    }

    private void finishSpan(ISpan iSpan) {
        if (iSpan == null || iSpan.isFinished()) {
            return;
        }
        iSpan.finish();
    }

    private void finishSpan(ISpan iSpan, SentryDate sentryDate) {
        finishSpan(iSpan, sentryDate, null);
    }

    private void finishSpan(ISpan iSpan, SentryDate sentryDate, SpanStatus spanStatus) {
        if (iSpan == null || iSpan.isFinished()) {
            return;
        }
        if (spanStatus == null) {
            spanStatus = iSpan.getStatus() != null ? iSpan.getStatus() : SpanStatus.OK;
        }
        iSpan.finish(spanStatus, sentryDate);
    }

    private void finishSpan(ISpan iSpan, SpanStatus spanStatus) {
        if (iSpan == null || iSpan.isFinished()) {
            return;
        }
        iSpan.finish(spanStatus);
    }

    private void cancelTtfdAutoClose() {
        Future<?> future = this.ttfdAutoCloseFuture;
        if (future != null) {
            future.cancel(false);
            this.ttfdAutoCloseFuture = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: onFirstFrameDrawn, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public void m250x1e0461fe(ISpan iSpan, ISpan iSpan2) {
        SentryAndroidOptions sentryAndroidOptions = this.options;
        if (sentryAndroidOptions != null && iSpan2 != null) {
            SentryDate now = sentryAndroidOptions.getDateProvider().now();
            long millis = TimeUnit.NANOSECONDS.toMillis(now.diff(iSpan2.getStartDate()));
            iSpan2.setMeasurement(MeasurementValue.KEY_TIME_TO_INITIAL_DISPLAY, Long.valueOf(millis), MeasurementUnit.Duration.MILLISECOND);
            if (iSpan != null && iSpan.isFinished()) {
                iSpan.updateEndDate(now);
                iSpan2.setMeasurement(MeasurementValue.KEY_TIME_TO_FULL_DISPLAY, Long.valueOf(millis), MeasurementUnit.Duration.MILLISECOND);
            }
            finishSpan(iSpan2, now);
            return;
        }
        finishSpan(iSpan2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: onFullFrameDrawn, reason: merged with bridge method [inline-methods] */
    public void m248x7f71294d(ISpan iSpan) {
        SentryAndroidOptions sentryAndroidOptions = this.options;
        if (sentryAndroidOptions != null && iSpan != null) {
            SentryDate now = sentryAndroidOptions.getDateProvider().now();
            iSpan.setMeasurement(MeasurementValue.KEY_TIME_TO_FULL_DISPLAY, Long.valueOf(TimeUnit.NANOSECONDS.toMillis(now.diff(iSpan.getStartDate()))), MeasurementUnit.Duration.MILLISECOND);
            finishSpan(iSpan, now);
        } else {
            finishSpan(iSpan);
        }
        cancelTtfdAutoClose();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: finishExceededTtfdSpan, reason: merged with bridge method [inline-methods] */
    public void m252xd0749c28(ISpan iSpan, ISpan iSpan2) {
        if (iSpan == null || iSpan.isFinished()) {
            return;
        }
        iSpan.setDescription(getExceededTtfdDesc(iSpan));
        SentryDate finishDate = iSpan2 != null ? iSpan2.getFinishDate() : null;
        if (finishDate == null) {
            finishDate = iSpan.getStartDate();
        }
        finishSpan(iSpan, finishDate, SpanStatus.DEADLINE_EXCEEDED);
    }

    WeakHashMap<Activity, ITransaction> getActivitiesWithOngoingTransactions() {
        return this.activitiesWithOngoingTransactions;
    }

    ActivityFramesTracker getActivityFramesTracker() {
        return this.activityFramesTracker;
    }

    ISpan getAppStartSpan() {
        return this.appStartSpan;
    }

    WeakHashMap<Activity, ISpan> getTtidSpanMap() {
        return this.ttidSpanMap;
    }

    WeakHashMap<Activity, ISpan> getTtfdSpanMap() {
        return this.ttfdSpanMap;
    }

    private void setColdStart(Bundle bundle) {
        if (this.firstActivityCreated) {
            return;
        }
        AppStartState.getInstance().setColdStart(bundle == null);
    }

    private String getTtidDesc(String str) {
        return str + " initial display";
    }

    private String getTtfdDesc(String str) {
        return str + " full display";
    }

    private String getExceededTtfdDesc(ISpan iSpan) {
        String description = iSpan.getDescription();
        if (description != null && description.endsWith(" - Deadline Exceeded")) {
            return description;
        }
        return iSpan.getDescription() + " - Deadline Exceeded";
    }

    private void finishAppStartSpan() {
        SentryDate appStartEndTime = AppStartState.getInstance().getAppStartEndTime();
        if (!this.performanceEnabled || appStartEndTime == null) {
            return;
        }
        finishSpan(this.appStartSpan, appStartEndTime);
    }
}

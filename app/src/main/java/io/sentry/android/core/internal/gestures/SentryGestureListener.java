package io.sentry.android.core.internal.gestures;

import android.app.Activity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import com.facebook.react.uimanager.ViewProps;
import io.sentry.Breadcrumb;
import io.sentry.Hint;
import io.sentry.IHub;
import io.sentry.ITransaction;
import io.sentry.Scope;
import io.sentry.ScopeCallback;
import io.sentry.SentryLevel;
import io.sentry.SpanStatus;
import io.sentry.TransactionContext;
import io.sentry.TransactionOptions;
import io.sentry.TypeCheckHint;
import io.sentry.android.core.SentryAndroidOptions;
import io.sentry.internal.gestures.UiElement;
import io.sentry.protocol.TransactionNameSource;
import io.sentry.util.TracingUtils;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Map;

/* loaded from: classes3.dex */
public final class SentryGestureListener implements GestureDetector.OnGestureListener {
    private static final String TRACE_ORIGIN = "auto.ui.gesture_listener";
    static final String UI_ACTION = "ui.action";
    private final WeakReference<Activity> activityRef;
    private final IHub hub;
    private final SentryAndroidOptions options;
    private UiElement activeUiElement = null;
    private ITransaction activeTransaction = null;
    private String activeEventType = null;
    private final ScrollState scrollState = new ScrollState();

    @Override // android.view.GestureDetector.OnGestureListener
    public void onLongPress(MotionEvent motionEvent) {
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public void onShowPress(MotionEvent motionEvent) {
    }

    public SentryGestureListener(Activity activity, IHub iHub, SentryAndroidOptions sentryAndroidOptions) {
        this.activityRef = new WeakReference<>(activity);
        this.hub = iHub;
        this.options = sentryAndroidOptions;
    }

    public void onUp(MotionEvent motionEvent) {
        View ensureWindowDecorView = ensureWindowDecorView("onUp");
        UiElement uiElement = this.scrollState.target;
        if (ensureWindowDecorView == null || uiElement == null) {
            return;
        }
        if (this.scrollState.type == null) {
            this.options.getLogger().log(SentryLevel.DEBUG, "Unable to define scroll type. No breadcrumb captured.", new Object[0]);
            return;
        }
        addBreadcrumb(uiElement, this.scrollState.type, Collections.singletonMap("direction", this.scrollState.calculateDirection(motionEvent)), motionEvent);
        startTracing(uiElement, this.scrollState.type);
        this.scrollState.reset();
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public boolean onDown(MotionEvent motionEvent) {
        if (motionEvent == null) {
            return false;
        }
        this.scrollState.reset();
        this.scrollState.startX = motionEvent.getX();
        this.scrollState.startY = motionEvent.getY();
        return false;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        View ensureWindowDecorView = ensureWindowDecorView("onSingleTapUp");
        if (ensureWindowDecorView != null && motionEvent != null) {
            UiElement findTarget = ViewUtils.findTarget(this.options, ensureWindowDecorView, motionEvent.getX(), motionEvent.getY(), UiElement.Type.CLICKABLE);
            if (findTarget == null) {
                this.options.getLogger().log(SentryLevel.DEBUG, "Unable to find click target. No breadcrumb captured.", new Object[0]);
                return false;
            }
            addBreadcrumb(findTarget, "click", Collections.emptyMap(), motionEvent);
            startTracing(findTarget, "click");
        }
        return false;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        View ensureWindowDecorView = ensureWindowDecorView("onScroll");
        if (ensureWindowDecorView != null && motionEvent != null && this.scrollState.type == null) {
            UiElement findTarget = ViewUtils.findTarget(this.options, ensureWindowDecorView, motionEvent.getX(), motionEvent.getY(), UiElement.Type.SCROLLABLE);
            if (findTarget == null) {
                this.options.getLogger().log(SentryLevel.DEBUG, "Unable to find scroll target. No breadcrumb captured.", new Object[0]);
                return false;
            }
            this.options.getLogger().log(SentryLevel.DEBUG, "Scroll target found: " + findTarget.getIdentifier(), new Object[0]);
            this.scrollState.setTarget(findTarget);
            this.scrollState.type = ViewProps.SCROLL;
        }
        return false;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        this.scrollState.type = "swipe";
        return false;
    }

    private void addBreadcrumb(UiElement uiElement, String str, Map<String, Object> map, MotionEvent motionEvent) {
        if (this.options.isEnableUserInteractionBreadcrumbs()) {
            Hint hint = new Hint();
            hint.set(TypeCheckHint.ANDROID_MOTION_EVENT, motionEvent);
            hint.set(TypeCheckHint.ANDROID_VIEW, uiElement.getView());
            this.hub.addBreadcrumb(Breadcrumb.userInteraction(str, uiElement.getResourceName(), uiElement.getClassName(), uiElement.getTag(), map), hint);
        }
    }

    private void startTracing(UiElement uiElement, String str) {
        UiElement uiElement2 = this.activeUiElement;
        if (!this.options.isTracingEnabled() || !this.options.isEnableUserInteractionTracing()) {
            if (uiElement.equals(uiElement2) && str.equals(this.activeEventType)) {
                return;
            }
            TracingUtils.startNewTrace(this.hub);
            this.activeUiElement = uiElement;
            this.activeEventType = str;
            return;
        }
        Activity activity = this.activityRef.get();
        if (activity == null) {
            this.options.getLogger().log(SentryLevel.DEBUG, "Activity is null, no transaction captured.", new Object[0]);
            return;
        }
        String identifier = uiElement.getIdentifier();
        if (this.activeTransaction != null) {
            if (uiElement.equals(uiElement2) && str.equals(this.activeEventType) && !this.activeTransaction.isFinished()) {
                this.options.getLogger().log(SentryLevel.DEBUG, "The view with id: " + identifier + " already has an ongoing transaction assigned. Rescheduling finish", new Object[0]);
                if (this.options.getIdleTimeout() != null) {
                    this.activeTransaction.scheduleFinish();
                    return;
                }
                return;
            }
            stopTracing(SpanStatus.OK);
        }
        TransactionOptions transactionOptions = new TransactionOptions();
        transactionOptions.setWaitForChildren(true);
        transactionOptions.setIdleTimeout(this.options.getIdleTimeout());
        transactionOptions.setTrimEnd(true);
        final ITransaction startTransaction = this.hub.startTransaction(new TransactionContext(getActivityName(activity) + "." + identifier, TransactionNameSource.COMPONENT, "ui.action." + str), transactionOptions);
        startTransaction.getSpanContext().setOrigin("auto.ui.gesture_listener." + uiElement.getOrigin());
        this.hub.configureScope(new ScopeCallback() { // from class: io.sentry.android.core.internal.gestures.SentryGestureListener$$ExternalSyntheticLambda2
            @Override // io.sentry.ScopeCallback
            public final void run(Scope scope) {
                SentryGestureListener.this.m262x5513955d(startTransaction, scope);
            }
        });
        this.activeTransaction = startTransaction;
        this.activeUiElement = uiElement;
        this.activeEventType = str;
    }

    void stopTracing(SpanStatus spanStatus) {
        ITransaction iTransaction = this.activeTransaction;
        if (iTransaction != null) {
            iTransaction.finish(spanStatus);
        }
        this.hub.configureScope(new ScopeCallback() { // from class: io.sentry.android.core.internal.gestures.SentryGestureListener$$ExternalSyntheticLambda1
            @Override // io.sentry.ScopeCallback
            public final void run(Scope scope) {
                SentryGestureListener.this.m263x526f98b6(scope);
            }
        });
        this.activeTransaction = null;
        if (this.activeUiElement != null) {
            this.activeUiElement = null;
        }
        this.activeEventType = null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: clearScope, reason: merged with bridge method [inline-methods] */
    public void m263x526f98b6(final Scope scope) {
        scope.withTransaction(new Scope.IWithTransaction() { // from class: io.sentry.android.core.internal.gestures.SentryGestureListener$$ExternalSyntheticLambda0
            @Override // io.sentry.Scope.IWithTransaction
            public final void accept(ITransaction iTransaction) {
                SentryGestureListener.this.m261xfa8c04b4(scope, iTransaction);
            }
        });
    }

    /* renamed from: lambda$clearScope$2$io-sentry-android-core-internal-gestures-SentryGestureListener, reason: not valid java name */
    /* synthetic */ void m261xfa8c04b4(Scope scope, ITransaction iTransaction) {
        if (iTransaction == this.activeTransaction) {
            scope.clearTransaction();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: applyScope, reason: merged with bridge method [inline-methods] */
    public void m262x5513955d(final Scope scope, final ITransaction iTransaction) {
        scope.withTransaction(new Scope.IWithTransaction() { // from class: io.sentry.android.core.internal.gestures.SentryGestureListener$$ExternalSyntheticLambda3
            @Override // io.sentry.Scope.IWithTransaction
            public final void accept(ITransaction iTransaction2) {
                SentryGestureListener.this.m260xd83f30f4(scope, iTransaction, iTransaction2);
            }
        });
    }

    /* renamed from: lambda$applyScope$3$io-sentry-android-core-internal-gestures-SentryGestureListener, reason: not valid java name */
    /* synthetic */ void m260xd83f30f4(Scope scope, ITransaction iTransaction, ITransaction iTransaction2) {
        if (iTransaction2 == null) {
            scope.setTransaction(iTransaction);
        } else {
            this.options.getLogger().log(SentryLevel.DEBUG, "Transaction '%s' won't be bound to the Scope since there's one already in there.", iTransaction.getName());
        }
    }

    private String getActivityName(Activity activity) {
        return activity.getClass().getSimpleName();
    }

    private View ensureWindowDecorView(String str) {
        Activity activity = this.activityRef.get();
        if (activity == null) {
            this.options.getLogger().log(SentryLevel.DEBUG, "Activity is null in " + str + ". No breadcrumb captured.", new Object[0]);
            return null;
        }
        Window window = activity.getWindow();
        if (window == null) {
            this.options.getLogger().log(SentryLevel.DEBUG, "Window is null in " + str + ". No breadcrumb captured.", new Object[0]);
            return null;
        }
        View decorView = window.getDecorView();
        if (decorView != null) {
            return decorView;
        }
        this.options.getLogger().log(SentryLevel.DEBUG, "DecorView is null in " + str + ". No breadcrumb captured.", new Object[0]);
        return null;
    }

    private static final class ScrollState {
        private float startX;
        private float startY;
        private UiElement target;
        private String type;

        private ScrollState() {
            this.type = null;
            this.startX = 0.0f;
            this.startY = 0.0f;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setTarget(UiElement uiElement) {
            this.target = uiElement;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public String calculateDirection(MotionEvent motionEvent) {
            float x = motionEvent.getX() - this.startX;
            float y = motionEvent.getY() - this.startY;
            return Math.abs(x) > Math.abs(y) ? x > 0.0f ? ViewProps.RIGHT : ViewProps.LEFT : y > 0.0f ? "down" : "up";
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void reset() {
            this.target = null;
            this.type = null;
            this.startX = 0.0f;
            this.startY = 0.0f;
        }
    }
}

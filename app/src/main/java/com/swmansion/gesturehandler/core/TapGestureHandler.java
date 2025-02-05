package com.swmansion.gesturehandler.core;

import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import androidx.core.app.NotificationCompat;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: TapGestureHandler.kt */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\f\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0011\u0018\u0000 12\b\u0012\u0004\u0012\u00020\u00000\u0001:\u00011B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J\b\u0010\u001d\u001a\u00020\u001aH\u0002J\b\u0010\u001e\u001a\u00020\u001aH\u0014J\u0018\u0010\u001f\u001a\u00020\u001a2\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020!H\u0014J\b\u0010#\u001a\u00020\u001aH\u0014J\b\u0010$\u001a\u00020\u001aH\u0016J\u000e\u0010%\u001a\u00020\u00002\u0006\u0010\f\u001a\u00020\rJ\u000e\u0010&\u001a\u00020\u00002\u0006\u0010'\u001a\u00020\nJ\u000e\u0010(\u001a\u00020\u00002\u0006\u0010\u0011\u001a\u00020\rJ\u000e\u0010)\u001a\u00020\u00002\u0006\u0010*\u001a\u00020\nJ\u000e\u0010+\u001a\u00020\u00002\u0006\u0010,\u001a\u00020\nJ\u000e\u0010-\u001a\u00020\u00002\u0006\u0010\u0012\u001a\u00020\u0004J\u000e\u0010.\u001a\u00020\u00002\u0006\u0010\u0013\u001a\u00020\u0004J\b\u0010/\u001a\u00020\u001cH\u0002J\b\u00100\u001a\u00020\u001aH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u00062"}, d2 = {"Lcom/swmansion/gesturehandler/core/TapGestureHandler;", "Lcom/swmansion/gesturehandler/core/GestureHandler;", "()V", "currentMaxNumberOfPointers", "", "failDelayed", "Ljava/lang/Runnable;", "handler", "Landroid/os/Handler;", "lastX", "", "lastY", "maxDelayMs", "", "maxDeltaX", "maxDeltaY", "maxDistSq", "maxDurationMs", "minNumberOfPointers", "numberOfTaps", "offsetX", "offsetY", "startX", "startY", "tapsSoFar", "activate", "", "force", "", "endTap", "onCancel", "onHandle", NotificationCompat.CATEGORY_EVENT, "Landroid/view/MotionEvent;", "sourceEvent", "onReset", "resetConfig", "setMaxDelayMs", "setMaxDist", "maxDist", "setMaxDurationMs", "setMaxDx", "deltaX", "setMaxDy", "deltaY", "setMinNumberOfPointers", "setNumberOfTaps", "shouldFail", "startTap", "Companion", "react-native-gesture-handler_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes.dex */
public final class TapGestureHandler extends GestureHandler<TapGestureHandler> {
    private static final long DEFAULT_MAX_DELAY_MS = 200;
    private static final long DEFAULT_MAX_DURATION_MS = 500;
    private static final int DEFAULT_MIN_NUMBER_OF_POINTERS = 1;
    private static final int DEFAULT_NUMBER_OF_TAPS = 1;
    private static final float MAX_VALUE_IGNORE = Float.MIN_VALUE;
    private Handler handler;
    private float lastX;
    private float lastY;
    private float offsetX;
    private float offsetY;
    private float startX;
    private float startY;
    private int tapsSoFar;
    private float maxDeltaX = Float.MIN_VALUE;
    private float maxDeltaY = Float.MIN_VALUE;
    private float maxDistSq = Float.MIN_VALUE;
    private long maxDurationMs = DEFAULT_MAX_DURATION_MS;
    private long maxDelayMs = DEFAULT_MAX_DELAY_MS;
    private int numberOfTaps = 1;
    private int minNumberOfPointers = 1;
    private int currentMaxNumberOfPointers = 1;
    private final Runnable failDelayed = new Runnable() { // from class: com.swmansion.gesturehandler.core.TapGestureHandler$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            TapGestureHandler.m187failDelayed$lambda0(TapGestureHandler.this);
        }
    };

    public TapGestureHandler() {
        setShouldCancelWhenOutside(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: failDelayed$lambda-0, reason: not valid java name */
    public static final void m187failDelayed$lambda0(TapGestureHandler this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.fail();
    }

    @Override // com.swmansion.gesturehandler.core.GestureHandler
    public void resetConfig() {
        super.resetConfig();
        this.maxDeltaX = Float.MIN_VALUE;
        this.maxDeltaY = Float.MIN_VALUE;
        this.maxDistSq = Float.MIN_VALUE;
        this.maxDurationMs = DEFAULT_MAX_DURATION_MS;
        this.maxDelayMs = DEFAULT_MAX_DELAY_MS;
        this.numberOfTaps = 1;
        this.minNumberOfPointers = 1;
    }

    public final TapGestureHandler setNumberOfTaps(int numberOfTaps) {
        this.numberOfTaps = numberOfTaps;
        return this;
    }

    public final TapGestureHandler setMaxDelayMs(long maxDelayMs) {
        this.maxDelayMs = maxDelayMs;
        return this;
    }

    public final TapGestureHandler setMaxDurationMs(long maxDurationMs) {
        this.maxDurationMs = maxDurationMs;
        return this;
    }

    public final TapGestureHandler setMaxDx(float deltaX) {
        this.maxDeltaX = deltaX;
        return this;
    }

    public final TapGestureHandler setMaxDy(float deltaY) {
        this.maxDeltaY = deltaY;
        return this;
    }

    public final TapGestureHandler setMaxDist(float maxDist) {
        this.maxDistSq = maxDist * maxDist;
        return this;
    }

    public final TapGestureHandler setMinNumberOfPointers(int minNumberOfPointers) {
        this.minNumberOfPointers = minNumberOfPointers;
        return this;
    }

    private final void startTap() {
        Handler handler = this.handler;
        if (handler == null) {
            this.handler = new Handler(Looper.getMainLooper());
        } else {
            Intrinsics.checkNotNull(handler);
            handler.removeCallbacksAndMessages(null);
        }
        Handler handler2 = this.handler;
        Intrinsics.checkNotNull(handler2);
        handler2.postDelayed(this.failDelayed, this.maxDurationMs);
    }

    private final void endTap() {
        Handler handler = this.handler;
        if (handler == null) {
            this.handler = new Handler(Looper.getMainLooper());
        } else {
            Intrinsics.checkNotNull(handler);
            handler.removeCallbacksAndMessages(null);
        }
        int i = this.tapsSoFar + 1;
        this.tapsSoFar = i;
        if (i == this.numberOfTaps && this.currentMaxNumberOfPointers >= this.minNumberOfPointers) {
            activate();
            return;
        }
        Handler handler2 = this.handler;
        Intrinsics.checkNotNull(handler2);
        handler2.postDelayed(this.failDelayed, this.maxDelayMs);
    }

    private final boolean shouldFail() {
        float f = (this.lastX - this.startX) + this.offsetX;
        if (!(this.maxDeltaX == Float.MIN_VALUE) && Math.abs(f) > this.maxDeltaX) {
            return true;
        }
        float f2 = (this.lastY - this.startY) + this.offsetY;
        if (!(this.maxDeltaY == Float.MIN_VALUE) && Math.abs(f2) > this.maxDeltaY) {
            return true;
        }
        float f3 = (f2 * f2) + (f * f);
        float f4 = this.maxDistSq;
        return !((f4 > Float.MIN_VALUE ? 1 : (f4 == Float.MIN_VALUE ? 0 : -1)) == 0) && f3 > f4;
    }

    @Override // com.swmansion.gesturehandler.core.GestureHandler
    protected void onHandle(MotionEvent event, MotionEvent sourceEvent) {
        Intrinsics.checkNotNullParameter(event, "event");
        Intrinsics.checkNotNullParameter(sourceEvent, "sourceEvent");
        int state = getState();
        int actionMasked = sourceEvent.getActionMasked();
        if (state == 0) {
            this.offsetX = 0.0f;
            this.offsetY = 0.0f;
            this.startX = GestureUtils.INSTANCE.getLastPointerX(sourceEvent, true);
            this.startY = GestureUtils.INSTANCE.getLastPointerY(sourceEvent, true);
        }
        if (actionMasked == 5 || actionMasked == 6) {
            this.offsetX += this.lastX - this.startX;
            this.offsetY += this.lastY - this.startY;
            this.lastX = GestureUtils.INSTANCE.getLastPointerX(sourceEvent, true);
            float lastPointerY = GestureUtils.INSTANCE.getLastPointerY(sourceEvent, true);
            this.lastY = lastPointerY;
            this.startX = this.lastX;
            this.startY = lastPointerY;
        } else {
            this.lastX = GestureUtils.INSTANCE.getLastPointerX(sourceEvent, true);
            this.lastY = GestureUtils.INSTANCE.getLastPointerY(sourceEvent, true);
        }
        if (this.currentMaxNumberOfPointers < sourceEvent.getPointerCount()) {
            this.currentMaxNumberOfPointers = sourceEvent.getPointerCount();
        }
        if (shouldFail()) {
            fail();
            return;
        }
        if (state == 0) {
            if (actionMasked == 0) {
                begin();
            }
            startTap();
        } else if (state == 2) {
            if (actionMasked == 0) {
                startTap();
            } else {
                if (actionMasked != 1) {
                    return;
                }
                endTap();
            }
        }
    }

    @Override // com.swmansion.gesturehandler.core.GestureHandler
    public void activate(boolean force) {
        super.activate(force);
        end();
    }

    @Override // com.swmansion.gesturehandler.core.GestureHandler
    protected void onCancel() {
        Handler handler = this.handler;
        if (handler == null) {
            return;
        }
        handler.removeCallbacksAndMessages(null);
    }

    @Override // com.swmansion.gesturehandler.core.GestureHandler
    protected void onReset() {
        this.tapsSoFar = 0;
        this.currentMaxNumberOfPointers = 0;
        Handler handler = this.handler;
        if (handler == null) {
            return;
        }
        handler.removeCallbacksAndMessages(null);
    }
}

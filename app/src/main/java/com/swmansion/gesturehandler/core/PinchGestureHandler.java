package com.swmansion.gesturehandler.core;

import android.content.Context;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import androidx.core.app.NotificationCompat;
import com.swmansion.gesturehandler.core.ScaleGestureDetector;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PinchGestureHandler.kt */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J\u0018\u0010\u001a\u001a\u00020\u00172\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001cH\u0014J\b\u0010\u001e\u001a\u00020\u0017H\u0014J\b\u0010\u001f\u001a\u00020\u0017H\u0016R\u001e\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0004@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001e\u0010\b\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0004@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0007R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\r\u001a\u00020\f2\u0006\u0010\u0003\u001a\u00020\f@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010\u0014\u001a\u00020\f2\u0006\u0010\u0003\u001a\u00020\f@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u000f¨\u0006 "}, d2 = {"Lcom/swmansion/gesturehandler/core/PinchGestureHandler;", "Lcom/swmansion/gesturehandler/core/GestureHandler;", "()V", "<set-?>", "", "focalPointX", "getFocalPointX", "()F", "focalPointY", "getFocalPointY", "gestureListener", "Lcom/swmansion/gesturehandler/core/ScaleGestureDetector$OnScaleGestureListener;", "", "scale", "getScale", "()D", "scaleGestureDetector", "Lcom/swmansion/gesturehandler/core/ScaleGestureDetector;", "spanSlop", "startingSpan", "velocity", "getVelocity", "activate", "", "force", "", "onHandle", NotificationCompat.CATEGORY_EVENT, "Landroid/view/MotionEvent;", "sourceEvent", "onReset", "resetProgress", "react-native-gesture-handler_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes.dex */
public final class PinchGestureHandler extends GestureHandler<PinchGestureHandler> {
    private float focalPointX = Float.NaN;
    private float focalPointY = Float.NaN;
    private final ScaleGestureDetector.OnScaleGestureListener gestureListener = new ScaleGestureDetector.OnScaleGestureListener() { // from class: com.swmansion.gesturehandler.core.PinchGestureHandler$gestureListener$1
        @Override // com.swmansion.gesturehandler.core.ScaleGestureDetector.OnScaleGestureListener
        public void onScaleEnd(ScaleGestureDetector detector) {
            Intrinsics.checkNotNullParameter(detector, "detector");
        }

        {
            PinchGestureHandler.this.setShouldCancelWhenOutside(false);
        }

        @Override // com.swmansion.gesturehandler.core.ScaleGestureDetector.OnScaleGestureListener
        public boolean onScale(ScaleGestureDetector detector) {
            float f;
            float f2;
            Intrinsics.checkNotNullParameter(detector, "detector");
            double scale = PinchGestureHandler.this.getScale();
            PinchGestureHandler pinchGestureHandler = PinchGestureHandler.this;
            pinchGestureHandler.scale = pinchGestureHandler.getScale() * detector.getScaleFactor();
            long timeDelta = detector.getTimeDelta();
            if (timeDelta > 0) {
                PinchGestureHandler pinchGestureHandler2 = PinchGestureHandler.this;
                pinchGestureHandler2.velocity = (pinchGestureHandler2.getScale() - scale) / timeDelta;
            }
            f = PinchGestureHandler.this.startingSpan;
            float abs = Math.abs(f - detector.getCurrentSpan());
            f2 = PinchGestureHandler.this.spanSlop;
            if (abs < f2 || PinchGestureHandler.this.getState() != 2) {
                return true;
            }
            PinchGestureHandler.this.activate();
            return true;
        }

        @Override // com.swmansion.gesturehandler.core.ScaleGestureDetector.OnScaleGestureListener
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            Intrinsics.checkNotNullParameter(detector, "detector");
            PinchGestureHandler.this.startingSpan = detector.getCurrentSpan();
            return true;
        }
    };
    private double scale;
    private ScaleGestureDetector scaleGestureDetector;
    private float spanSlop;
    private float startingSpan;
    private double velocity;

    public final double getScale() {
        return this.scale;
    }

    public final double getVelocity() {
        return this.velocity;
    }

    public final float getFocalPointX() {
        return this.focalPointX;
    }

    public final float getFocalPointY() {
        return this.focalPointY;
    }

    @Override // com.swmansion.gesturehandler.core.GestureHandler
    protected void onHandle(MotionEvent event, MotionEvent sourceEvent) {
        Intrinsics.checkNotNullParameter(event, "event");
        Intrinsics.checkNotNullParameter(sourceEvent, "sourceEvent");
        if (getState() == 0) {
            View view = getView();
            Intrinsics.checkNotNull(view);
            Context context = view.getContext();
            resetProgress();
            this.scaleGestureDetector = new ScaleGestureDetector(context, this.gestureListener);
            this.spanSlop = ViewConfiguration.get(context).getScaledTouchSlop();
            this.focalPointX = event.getX();
            this.focalPointY = event.getY();
            begin();
        }
        ScaleGestureDetector scaleGestureDetector = this.scaleGestureDetector;
        if (scaleGestureDetector != null) {
            scaleGestureDetector.onTouchEvent(sourceEvent);
        }
        ScaleGestureDetector scaleGestureDetector2 = this.scaleGestureDetector;
        if (scaleGestureDetector2 != null) {
            PointF transformPoint = transformPoint(new PointF(scaleGestureDetector2.getFocusX(), scaleGestureDetector2.getFocusY()));
            this.focalPointX = transformPoint.x;
            this.focalPointY = transformPoint.y;
        }
        int pointerCount = sourceEvent.getPointerCount();
        if (sourceEvent.getActionMasked() == 6) {
            pointerCount--;
        }
        if (getState() == 4 && pointerCount < 2) {
            end();
        } else if (sourceEvent.getActionMasked() == 1) {
            fail();
        }
    }

    @Override // com.swmansion.gesturehandler.core.GestureHandler
    public void activate(boolean force) {
        if (getState() != 4) {
            resetProgress();
        }
        super.activate(force);
    }

    @Override // com.swmansion.gesturehandler.core.GestureHandler
    protected void onReset() {
        this.scaleGestureDetector = null;
        this.focalPointX = Float.NaN;
        this.focalPointY = Float.NaN;
        resetProgress();
    }

    @Override // com.swmansion.gesturehandler.core.GestureHandler
    public void resetProgress() {
        this.velocity = 0.0d;
        this.scale = 1.0d;
    }
}

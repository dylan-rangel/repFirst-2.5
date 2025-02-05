package com.swmansion.gesturehandler.core;

import android.graphics.PointF;
import android.view.MotionEvent;
import androidx.core.app.NotificationCompat;
import com.facebook.react.uimanager.ViewProps;
import com.swmansion.gesturehandler.core.RotationGestureDetector;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: RotationGestureHandler.kt */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 \u001e2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u001eB\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u0018\u0010\u0018\u001a\u00020\u00152\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001aH\u0014J\b\u0010\u001c\u001a\u00020\u0015H\u0014J\b\u0010\u001d\u001a\u00020\u0015H\u0016R\u001e\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0004@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001e\u0010\b\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0004@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0007R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\r\u001a\u00020\f2\u0006\u0010\u0003\u001a\u00020\f@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010\u0012\u001a\u00020\f2\u0006\u0010\u0003\u001a\u00020\f@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000f¨\u0006\u001f"}, d2 = {"Lcom/swmansion/gesturehandler/core/RotationGestureHandler;", "Lcom/swmansion/gesturehandler/core/GestureHandler;", "()V", "<set-?>", "", "anchorX", "getAnchorX", "()F", "anchorY", "getAnchorY", "gestureListener", "Lcom/swmansion/gesturehandler/core/RotationGestureDetector$OnRotationGestureListener;", "", ViewProps.ROTATION, "getRotation", "()D", "rotationGestureDetector", "Lcom/swmansion/gesturehandler/core/RotationGestureDetector;", "velocity", "getVelocity", "activate", "", "force", "", "onHandle", NotificationCompat.CATEGORY_EVENT, "Landroid/view/MotionEvent;", "sourceEvent", "onReset", "resetProgress", "Companion", "react-native-gesture-handler_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes.dex */
public final class RotationGestureHandler extends GestureHandler<RotationGestureHandler> {
    private static final double ROTATION_RECOGNITION_THRESHOLD = 0.08726646259971647d;
    private float anchorX = Float.NaN;
    private float anchorY = Float.NaN;
    private final RotationGestureDetector.OnRotationGestureListener gestureListener;
    private double rotation;
    private RotationGestureDetector rotationGestureDetector;
    private double velocity;

    public RotationGestureHandler() {
        setShouldCancelWhenOutside(false);
        this.gestureListener = new RotationGestureDetector.OnRotationGestureListener() { // from class: com.swmansion.gesturehandler.core.RotationGestureHandler$gestureListener$1
            @Override // com.swmansion.gesturehandler.core.RotationGestureDetector.OnRotationGestureListener
            public boolean onRotationBegin(RotationGestureDetector detector) {
                Intrinsics.checkNotNullParameter(detector, "detector");
                return true;
            }

            @Override // com.swmansion.gesturehandler.core.RotationGestureDetector.OnRotationGestureListener
            public boolean onRotation(RotationGestureDetector detector) {
                Intrinsics.checkNotNullParameter(detector, "detector");
                double rotation = RotationGestureHandler.this.getRotation();
                RotationGestureHandler rotationGestureHandler = RotationGestureHandler.this;
                rotationGestureHandler.rotation = rotationGestureHandler.getRotation() + detector.getRotation();
                long timeDelta = detector.getTimeDelta();
                if (timeDelta > 0) {
                    RotationGestureHandler rotationGestureHandler2 = RotationGestureHandler.this;
                    rotationGestureHandler2.velocity = (rotationGestureHandler2.getRotation() - rotation) / timeDelta;
                }
                if (Math.abs(RotationGestureHandler.this.getRotation()) < 0.08726646259971647d || RotationGestureHandler.this.getState() != 2) {
                    return true;
                }
                RotationGestureHandler.this.activate();
                return true;
            }

            @Override // com.swmansion.gesturehandler.core.RotationGestureDetector.OnRotationGestureListener
            public void onRotationEnd(RotationGestureDetector detector) {
                Intrinsics.checkNotNullParameter(detector, "detector");
                RotationGestureHandler.this.end();
            }
        };
    }

    public final double getRotation() {
        return this.rotation;
    }

    public final double getVelocity() {
        return this.velocity;
    }

    public final float getAnchorX() {
        return this.anchorX;
    }

    public final float getAnchorY() {
        return this.anchorY;
    }

    @Override // com.swmansion.gesturehandler.core.GestureHandler
    protected void onHandle(MotionEvent event, MotionEvent sourceEvent) {
        Intrinsics.checkNotNullParameter(event, "event");
        Intrinsics.checkNotNullParameter(sourceEvent, "sourceEvent");
        if (getState() == 0) {
            resetProgress();
            this.rotationGestureDetector = new RotationGestureDetector(this.gestureListener);
            this.anchorX = event.getX();
            this.anchorY = event.getY();
            begin();
        }
        RotationGestureDetector rotationGestureDetector = this.rotationGestureDetector;
        if (rotationGestureDetector != null) {
            rotationGestureDetector.onTouchEvent(sourceEvent);
        }
        RotationGestureDetector rotationGestureDetector2 = this.rotationGestureDetector;
        if (rotationGestureDetector2 != null) {
            PointF transformPoint = transformPoint(new PointF(rotationGestureDetector2.getAnchorX(), rotationGestureDetector2.getAnchorY()));
            this.anchorX = transformPoint.x;
            this.anchorY = transformPoint.y;
        }
        if (sourceEvent.getActionMasked() == 1) {
            if (getState() == 4) {
                end();
            } else {
                fail();
            }
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
        this.rotationGestureDetector = null;
        this.anchorX = Float.NaN;
        this.anchorY = Float.NaN;
        resetProgress();
    }

    @Override // com.swmansion.gesturehandler.core.GestureHandler
    public void resetProgress() {
        this.velocity = 0.0d;
        this.rotation = 0.0d;
    }
}

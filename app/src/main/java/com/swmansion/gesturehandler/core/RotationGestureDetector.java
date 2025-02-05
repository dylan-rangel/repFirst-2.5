package com.swmansion.gesturehandler.core;

import android.view.MotionEvent;
import androidx.core.app.NotificationCompat;
import com.facebook.react.uimanager.ViewProps;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: RotationGestureDetector.kt */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0015\n\u0000\n\u0002\u0010\u0006\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001!B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u001b\u001a\u00020\u001cH\u0002J\u000e\u0010\u001d\u001a\u00020\u000f2\u0006\u0010\u001e\u001a\u00020\u001fJ\u0010\u0010 \u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002R\u001e\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0006@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001e\u0010\n\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0006@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\tR\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010\u0015\u001a\u00020\u00132\u0006\u0010\u0005\u001a\u00020\u0013@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0018\u001a\u00020\r8F¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001a¨\u0006\""}, d2 = {"Lcom/swmansion/gesturehandler/core/RotationGestureDetector;", "", "gestureListener", "Lcom/swmansion/gesturehandler/core/RotationGestureDetector$OnRotationGestureListener;", "(Lcom/swmansion/gesturehandler/core/RotationGestureDetector$OnRotationGestureListener;)V", "<set-?>", "", "anchorX", "getAnchorX", "()F", "anchorY", "getAnchorY", "currentTime", "", "isInProgress", "", "pointerIds", "", "previousAngle", "", "previousTime", ViewProps.ROTATION, "getRotation", "()D", "timeDelta", "getTimeDelta", "()J", "finish", "", "onTouchEvent", NotificationCompat.CATEGORY_EVENT, "Landroid/view/MotionEvent;", "updateCurrent", "OnRotationGestureListener", "react-native-gesture-handler_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes.dex */
public final class RotationGestureDetector {
    private float anchorX;
    private float anchorY;
    private long currentTime;
    private final OnRotationGestureListener gestureListener;
    private boolean isInProgress;
    private final int[] pointerIds = new int[2];
    private double previousAngle;
    private long previousTime;
    private double rotation;

    /* compiled from: RotationGestureDetector.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\t"}, d2 = {"Lcom/swmansion/gesturehandler/core/RotationGestureDetector$OnRotationGestureListener;", "", "onRotation", "", "detector", "Lcom/swmansion/gesturehandler/core/RotationGestureDetector;", "onRotationBegin", "onRotationEnd", "", "react-native-gesture-handler_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public interface OnRotationGestureListener {
        boolean onRotation(RotationGestureDetector detector);

        boolean onRotationBegin(RotationGestureDetector detector);

        void onRotationEnd(RotationGestureDetector detector);
    }

    public RotationGestureDetector(OnRotationGestureListener onRotationGestureListener) {
        this.gestureListener = onRotationGestureListener;
    }

    public final double getRotation() {
        return this.rotation;
    }

    public final float getAnchorX() {
        return this.anchorX;
    }

    public final float getAnchorY() {
        return this.anchorY;
    }

    public final long getTimeDelta() {
        return this.currentTime - this.previousTime;
    }

    private final void updateCurrent(MotionEvent event) {
        this.previousTime = this.currentTime;
        this.currentTime = event.getEventTime();
        int findPointerIndex = event.findPointerIndex(this.pointerIds[0]);
        int findPointerIndex2 = event.findPointerIndex(this.pointerIds[1]);
        float x = event.getX(findPointerIndex);
        float y = event.getY(findPointerIndex);
        float x2 = event.getX(findPointerIndex2);
        float y2 = event.getY(findPointerIndex2);
        this.anchorX = (x + x2) * 0.5f;
        this.anchorY = (y + y2) * 0.5f;
        double d = -Math.atan2(y2 - y, x2 - x);
        double d2 = Double.isNaN(this.previousAngle) ? 0.0d : this.previousAngle - d;
        this.rotation = d2;
        this.previousAngle = d;
        if (d2 > 3.141592653589793d) {
            this.rotation = d2 - 3.141592653589793d;
        } else if (d2 < -3.141592653589793d) {
            this.rotation = d2 + 3.141592653589793d;
        }
        double d3 = this.rotation;
        if (d3 > 1.5707963267948966d) {
            this.rotation = d3 - 3.141592653589793d;
        } else if (d3 < -1.5707963267948966d) {
            this.rotation = d3 + 3.141592653589793d;
        }
    }

    private final void finish() {
        if (this.isInProgress) {
            this.isInProgress = false;
            OnRotationGestureListener onRotationGestureListener = this.gestureListener;
            if (onRotationGestureListener == null) {
                return;
            }
            onRotationGestureListener.onRotationEnd(this);
        }
    }

    public final boolean onTouchEvent(MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        int actionMasked = event.getActionMasked();
        if (actionMasked == 0) {
            this.isInProgress = false;
            this.pointerIds[0] = event.getPointerId(event.getActionIndex());
            this.pointerIds[1] = -1;
        } else if (actionMasked == 1) {
            finish();
        } else if (actionMasked != 2) {
            if (actionMasked == 5) {
                if (!this.isInProgress) {
                    this.pointerIds[1] = event.getPointerId(event.getActionIndex());
                    this.isInProgress = true;
                    this.previousTime = event.getEventTime();
                    this.previousAngle = Double.NaN;
                    updateCurrent(event);
                    OnRotationGestureListener onRotationGestureListener = this.gestureListener;
                    if (onRotationGestureListener != null) {
                        onRotationGestureListener.onRotationBegin(this);
                    }
                }
            } else if (actionMasked == 6 && this.isInProgress) {
                int pointerId = event.getPointerId(event.getActionIndex());
                int[] iArr = this.pointerIds;
                if (pointerId == iArr[0] || pointerId == iArr[1]) {
                    finish();
                }
            }
        } else if (this.isInProgress) {
            updateCurrent(event);
            OnRotationGestureListener onRotationGestureListener2 = this.gestureListener;
            if (onRotationGestureListener2 != null) {
                onRotationGestureListener2.onRotation(this);
            }
        }
        return true;
    }
}

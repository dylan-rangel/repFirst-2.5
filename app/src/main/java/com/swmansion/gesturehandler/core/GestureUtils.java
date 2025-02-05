package com.swmansion.gesturehandler.core;

import android.view.MotionEvent;
import androidx.core.app.NotificationCompat;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: GestureUtils.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bJ\u0016\u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b¨\u0006\n"}, d2 = {"Lcom/swmansion/gesturehandler/core/GestureUtils;", "", "()V", "getLastPointerX", "", NotificationCompat.CATEGORY_EVENT, "Landroid/view/MotionEvent;", "averageTouches", "", "getLastPointerY", "react-native-gesture-handler_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes.dex */
public final class GestureUtils {
    public static final GestureUtils INSTANCE = new GestureUtils();

    private GestureUtils() {
    }

    public final float getLastPointerX(MotionEvent event, boolean averageTouches) {
        Intrinsics.checkNotNullParameter(event, "event");
        int actionIndex = event.getActionMasked() == 6 ? event.getActionIndex() : -1;
        if (averageTouches) {
            float f = 0.0f;
            int pointerCount = event.getPointerCount();
            int i = 0;
            int i2 = 0;
            while (i < pointerCount) {
                int i3 = i + 1;
                if (i != actionIndex) {
                    f += event.getX(i);
                    i2++;
                }
                i = i3;
            }
            return f / i2;
        }
        int pointerCount2 = event.getPointerCount() - 1;
        if (pointerCount2 == actionIndex) {
            pointerCount2--;
        }
        return event.getX(pointerCount2);
    }

    public final float getLastPointerY(MotionEvent event, boolean averageTouches) {
        Intrinsics.checkNotNullParameter(event, "event");
        int actionIndex = event.getActionMasked() == 6 ? event.getActionIndex() : -1;
        if (averageTouches) {
            float f = 0.0f;
            int pointerCount = event.getPointerCount();
            int i = 0;
            int i2 = 0;
            while (i < pointerCount) {
                int i3 = i + 1;
                if (i != actionIndex) {
                    f += event.getY(i);
                    i2++;
                }
                i = i3;
            }
            return f / i2;
        }
        int pointerCount2 = event.getPointerCount() - 1;
        if (pointerCount2 == actionIndex) {
            pointerCount2--;
        }
        return event.getY(pointerCount2);
    }
}

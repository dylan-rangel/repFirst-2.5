package com.facebook.react.fabric.mounting;

import android.view.View;
import com.android.tools.r8.annotations.SynthesizedClassV2;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.yoga.YogaMeasureMode;

/* loaded from: classes.dex */
public interface LayoutMetricsConversions {

    @SynthesizedClassV2(kind = 7, versionHash = "5e5398f0546d1d7afd62641edb14d82894f11ddc41bce363a0c8d0dac82c9c5a")
    /* renamed from: com.facebook.react.fabric.mounting.LayoutMetricsConversions$-CC, reason: invalid class name */
    public final /* synthetic */ class CC {
        public static float getMinSize(int i) {
            int mode = View.MeasureSpec.getMode(i);
            int size = View.MeasureSpec.getSize(i);
            if (mode == 1073741824) {
                return size;
            }
            return 0.0f;
        }

        public static float getMaxSize(int i) {
            int mode = View.MeasureSpec.getMode(i);
            int size = View.MeasureSpec.getSize(i);
            if (mode == 0) {
                return Float.POSITIVE_INFINITY;
            }
            return size;
        }

        public static float getYogaSize(float f, float f2) {
            if (f == f2) {
                return PixelUtil.toPixelFromDIP(f2);
            }
            if (Float.isInfinite(f2)) {
                return Float.POSITIVE_INFINITY;
            }
            return PixelUtil.toPixelFromDIP(f2);
        }

        public static YogaMeasureMode getYogaMeasureMode(float f, float f2) {
            if (f == f2) {
                return YogaMeasureMode.EXACTLY;
            }
            if (Float.isInfinite(f2)) {
                return YogaMeasureMode.UNDEFINED;
            }
            return YogaMeasureMode.AT_MOST;
        }
    }
}

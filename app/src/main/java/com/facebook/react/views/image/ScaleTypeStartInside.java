package com.facebook.react.views.image;

import android.graphics.Matrix;
import android.graphics.Rect;
import com.facebook.drawee.drawable.ScalingUtils;

/* loaded from: classes.dex */
public class ScaleTypeStartInside extends ScalingUtils.AbstractScaleType {
    public static final ScalingUtils.ScaleType INSTANCE = new ScaleTypeStartInside();

    public String toString() {
        return "start_inside";
    }

    @Override // com.facebook.drawee.drawable.ScalingUtils.AbstractScaleType
    public void getTransformImpl(Matrix matrix, Rect rect, int i, int i2, float f, float f2, float f3, float f4) {
        float min = Math.min(Math.min(f3, f4), 1.0f);
        float f5 = rect.left;
        float f6 = rect.top;
        matrix.setScale(min, min);
        matrix.postTranslate((int) (f5 + 0.5f), (int) (f6 + 0.5f));
    }
}

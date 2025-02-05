package com.google.android.material.carousel;

import android.view.View;

/* loaded from: classes2.dex */
public abstract class CarouselStrategy {
    static float getChildMaskPercentage(float f, float f2, float f3) {
        return 1.0f - ((f - f3) / (f2 - f3));
    }

    abstract KeylineState onFirstChildMeasuredWithMargins(Carousel carousel, View view);
}

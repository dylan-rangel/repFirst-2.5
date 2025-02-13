package com.facebook.common.memory;

/* loaded from: classes.dex */
public enum MemoryTrimType {
    OnCloseToDalvikHeapLimit(0.5d),
    OnSystemMemoryCriticallyLowWhileAppInForeground(1.0d),
    OnSystemLowMemoryWhileAppInForeground(0.5d),
    OnSystemLowMemoryWhileAppInBackground(1.0d),
    OnAppBackgrounded(1.0d);

    private double mSuggestedTrimRatio;

    MemoryTrimType(double suggestedTrimRatio) {
        this.mSuggestedTrimRatio = suggestedTrimRatio;
    }

    public double getSuggestedTrimRatio() {
        return this.mSuggestedTrimRatio;
    }
}

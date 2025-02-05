package com.facebook.react.animated;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import io.sentry.protocol.SentryStackTrace;

/* loaded from: classes.dex */
class FrameBasedAnimationDriver extends AnimationDriver {
    private static final double FRAME_TIME_MILLIS = 16.666666666666668d;
    private int mCurrentLoop;
    private double[] mFrames;
    private double mFromValue;
    private int mIterations;
    private long mStartFrameTimeNanos;
    private double mToValue;

    FrameBasedAnimationDriver(ReadableMap readableMap) {
        resetConfig(readableMap);
    }

    @Override // com.facebook.react.animated.AnimationDriver
    public void resetConfig(ReadableMap readableMap) {
        ReadableArray array = readableMap.getArray(SentryStackTrace.JsonKeys.FRAMES);
        int size = array.size();
        double[] dArr = this.mFrames;
        if (dArr == null || dArr.length != size) {
            this.mFrames = new double[size];
        }
        for (int i = 0; i < size; i++) {
            this.mFrames[i] = array.getDouble(i);
        }
        if (readableMap.hasKey("toValue")) {
            this.mToValue = readableMap.getType("toValue") == ReadableType.Number ? readableMap.getDouble("toValue") : 0.0d;
        } else {
            this.mToValue = 0.0d;
        }
        if (readableMap.hasKey("iterations")) {
            this.mIterations = readableMap.getType("iterations") == ReadableType.Number ? readableMap.getInt("iterations") : 1;
        } else {
            this.mIterations = 1;
        }
        this.mCurrentLoop = 1;
        this.mHasFinished = this.mIterations == 0;
        this.mStartFrameTimeNanos = -1L;
    }

    @Override // com.facebook.react.animated.AnimationDriver
    public void runAnimationStep(long j) {
        double d;
        if (this.mStartFrameTimeNanos < 0) {
            this.mStartFrameTimeNanos = j;
            if (this.mCurrentLoop == 1) {
                this.mFromValue = this.mAnimatedValue.mValue;
            }
        }
        int round = (int) Math.round(((j - this.mStartFrameTimeNanos) / 1000000) / FRAME_TIME_MILLIS);
        if (round < 0) {
            throw new IllegalStateException("Calculated frame index should never be lower than 0");
        }
        if (this.mHasFinished) {
            return;
        }
        double[] dArr = this.mFrames;
        if (round >= dArr.length - 1) {
            d = this.mToValue;
            int i = this.mIterations;
            if (i == -1 || this.mCurrentLoop < i) {
                this.mStartFrameTimeNanos = -1L;
                this.mCurrentLoop++;
            } else {
                this.mHasFinished = true;
            }
        } else {
            double d2 = this.mFromValue;
            d = d2 + (dArr[round] * (this.mToValue - d2));
        }
        this.mAnimatedValue.mValue = d;
    }
}

package com.facebook.react.animated;

import com.facebook.react.bridge.ReadableMap;

/* loaded from: classes.dex */
class ValueAnimatedNode extends AnimatedNode {
    Object mAnimatedObject;
    double mOffset;
    double mValue;
    private AnimatedNodeValueListener mValueListener;

    public ValueAnimatedNode() {
        this.mAnimatedObject = null;
        this.mValue = Double.NaN;
        this.mOffset = 0.0d;
    }

    public ValueAnimatedNode(ReadableMap readableMap) {
        this.mAnimatedObject = null;
        this.mValue = Double.NaN;
        this.mOffset = 0.0d;
        this.mValue = readableMap.getDouble("value");
        this.mOffset = readableMap.getDouble("offset");
    }

    public double getValue() {
        if (Double.isNaN(this.mOffset + this.mValue)) {
            update();
        }
        return this.mOffset + this.mValue;
    }

    public Object getAnimatedObject() {
        return this.mAnimatedObject;
    }

    public void flattenOffset() {
        this.mValue += this.mOffset;
        this.mOffset = 0.0d;
    }

    public void extractOffset() {
        this.mOffset += this.mValue;
        this.mValue = 0.0d;
    }

    public void onValueUpdate() {
        AnimatedNodeValueListener animatedNodeValueListener = this.mValueListener;
        if (animatedNodeValueListener == null) {
            return;
        }
        animatedNodeValueListener.onValueUpdate(getValue());
    }

    public void setValueListener(AnimatedNodeValueListener animatedNodeValueListener) {
        this.mValueListener = animatedNodeValueListener;
    }

    @Override // com.facebook.react.animated.AnimatedNode
    public String prettyPrint() {
        return "ValueAnimatedNode[" + this.mTag + "]: value: " + this.mValue + " offset: " + this.mOffset;
    }
}

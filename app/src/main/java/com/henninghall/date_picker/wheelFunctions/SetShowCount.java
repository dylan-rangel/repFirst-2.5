package com.henninghall.date_picker.wheelFunctions;

import com.henninghall.date_picker.wheels.Wheel;

/* loaded from: classes2.dex */
public class SetShowCount implements WheelFunction {
    private final int count;

    public SetShowCount(int i) {
        this.count = i;
    }

    @Override // com.henninghall.date_picker.wheelFunctions.WheelFunction
    public void apply(Wheel wheel) {
        wheel.picker.setShownCount(this.count);
    }
}

package com.henninghall.date_picker.wheelFunctions;

import com.henninghall.date_picker.wheels.Wheel;

/* loaded from: classes2.dex */
public class SetDividerHeight implements WheelFunction {
    private final int height;

    public SetDividerHeight(int i) {
        this.height = i;
    }

    @Override // com.henninghall.date_picker.wheelFunctions.WheelFunction
    public void apply(Wheel wheel) {
        wheel.picker.setDividerHeight(this.height);
    }
}

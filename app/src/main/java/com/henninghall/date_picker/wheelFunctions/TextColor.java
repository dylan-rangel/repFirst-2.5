package com.henninghall.date_picker.wheelFunctions;

import com.henninghall.date_picker.wheels.Wheel;

/* loaded from: classes2.dex */
public class TextColor implements WheelFunction {
    private final String color;

    public TextColor(String str) {
        this.color = str;
    }

    @Override // com.henninghall.date_picker.wheelFunctions.WheelFunction
    public void apply(Wheel wheel) {
        wheel.picker.setTextColor(this.color);
    }
}

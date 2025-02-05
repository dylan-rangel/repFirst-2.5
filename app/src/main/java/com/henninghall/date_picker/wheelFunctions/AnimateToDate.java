package com.henninghall.date_picker.wheelFunctions;

import com.henninghall.date_picker.wheels.Wheel;
import java.util.Calendar;

/* loaded from: classes2.dex */
public class AnimateToDate implements WheelFunction {
    private Calendar date;

    public AnimateToDate(Calendar calendar) {
        this.date = calendar;
    }

    @Override // com.henninghall.date_picker.wheelFunctions.WheelFunction
    public void apply(Wheel wheel) {
        wheel.animateToDate(this.date);
    }
}

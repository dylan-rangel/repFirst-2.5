package com.henninghall.date_picker.wheelFunctions;

import com.henninghall.date_picker.pickers.Picker;
import com.henninghall.date_picker.ui.WheelChangeListener;
import com.henninghall.date_picker.wheels.Wheel;

/* loaded from: classes2.dex */
public class AddOnChangeListener implements WheelFunction {
    private final WheelChangeListener onChangeListener;

    public AddOnChangeListener(WheelChangeListener wheelChangeListener) {
        this.onChangeListener = wheelChangeListener;
    }

    @Override // com.henninghall.date_picker.wheelFunctions.WheelFunction
    public void apply(final Wheel wheel) {
        wheel.picker.setOnValueChangedListener(new Picker.OnValueChangeListener() { // from class: com.henninghall.date_picker.wheelFunctions.AddOnChangeListener.1
            @Override // com.henninghall.date_picker.pickers.Picker.OnValueChangeListener
            public void onValueChange() {
                AddOnChangeListener.this.onChangeListener.onChange(wheel);
            }
        });
    }
}

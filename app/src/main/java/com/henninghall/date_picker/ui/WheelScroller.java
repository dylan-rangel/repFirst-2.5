package com.henninghall.date_picker.ui;

import com.henninghall.date_picker.pickers.Picker;
import com.henninghall.date_picker.wheels.Wheel;

/* loaded from: classes2.dex */
public class WheelScroller {
    public void scroll(Wheel wheel, int i) {
        Picker picker = wheel.picker;
        int value = picker.getValue();
        int maxValue = picker.getMaxValue();
        boolean wrapSelectorWheel = picker.getWrapSelectorWheel();
        int i2 = value + i;
        if (i2 <= maxValue || wrapSelectorWheel) {
            picker.smoothScrollToValue(i2 % (maxValue + 1));
        }
    }
}

package com.henninghall.date_picker.wheels;

import android.graphics.Paint;
import com.henninghall.date_picker.State;
import com.henninghall.date_picker.models.Mode;
import com.henninghall.date_picker.pickers.Picker;
import java.util.ArrayList;
import java.util.Calendar;

/* loaded from: classes2.dex */
public class MinutesWheel extends Wheel {
    @Override // com.henninghall.date_picker.wheels.Wheel
    public String getFormatPattern() {
        return "mm";
    }

    @Override // com.henninghall.date_picker.wheels.Wheel
    public boolean wrapSelectorWheel() {
        return true;
    }

    public MinutesWheel(Picker picker, State state) {
        super(picker, state);
    }

    @Override // com.henninghall.date_picker.wheels.Wheel
    public ArrayList<String> getValues() {
        Calendar calendar = Calendar.getInstance();
        ArrayList<String> arrayList = new ArrayList<>();
        int i = 0;
        calendar.set(12, 0);
        while (i < 60) {
            arrayList.add(this.format.format(calendar.getTime()));
            calendar.add(12, this.state.getMinuteInterval());
            i += this.state.getMinuteInterval();
        }
        return arrayList;
    }

    @Override // com.henninghall.date_picker.wheels.Wheel
    public boolean visible() {
        return this.state.getMode() != Mode.date;
    }

    @Override // com.henninghall.date_picker.wheels.Wheel
    public Paint.Align getTextAlign() {
        return this.state.derived.hasOnly2Wheels() ? Paint.Align.LEFT : Paint.Align.RIGHT;
    }
}

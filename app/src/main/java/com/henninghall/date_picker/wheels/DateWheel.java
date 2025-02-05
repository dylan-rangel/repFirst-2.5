package com.henninghall.date_picker.wheels;

import android.graphics.Paint;
import com.henninghall.date_picker.LocaleUtils;
import com.henninghall.date_picker.State;
import com.henninghall.date_picker.models.Mode;
import com.henninghall.date_picker.pickers.Picker;
import java.util.ArrayList;
import java.util.Calendar;

/* loaded from: classes2.dex */
public class DateWheel extends Wheel {
    @Override // com.henninghall.date_picker.wheels.Wheel
    public boolean wrapSelectorWheel() {
        return true;
    }

    public DateWheel(Picker picker, State state) {
        super(picker, state);
    }

    @Override // com.henninghall.date_picker.wheels.Wheel
    public ArrayList<String> getValues() {
        Calendar calendar = Calendar.getInstance();
        ArrayList<String> arrayList = new ArrayList<>();
        calendar.set(2, 0);
        calendar.set(5, 1);
        for (int i = 1; i <= 31; i++) {
            arrayList.add(getLocaleString(calendar));
            calendar.add(5, 1);
        }
        return arrayList;
    }

    @Override // com.henninghall.date_picker.wheels.Wheel
    public boolean visible() {
        return this.state.getMode() == Mode.date;
    }

    @Override // com.henninghall.date_picker.wheels.Wheel
    public String getFormatPattern() {
        return LocaleUtils.getDate(this.state.getLocaleLanguageTag());
    }

    @Override // com.henninghall.date_picker.wheels.Wheel
    public Paint.Align getTextAlign() {
        return Paint.Align.RIGHT;
    }
}

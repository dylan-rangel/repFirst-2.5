package com.henninghall.date_picker.wheels;

import android.graphics.Paint;
import com.henninghall.date_picker.LocaleUtils;
import com.henninghall.date_picker.State;
import com.henninghall.date_picker.models.Mode;
import com.henninghall.date_picker.pickers.Picker;
import java.util.ArrayList;
import java.util.Calendar;

/* loaded from: classes2.dex */
public class YearWheel extends Wheel {
    private int defaultEndYear;
    private int defaultStartYear;

    @Override // com.henninghall.date_picker.wheels.Wheel
    public boolean wrapSelectorWheel() {
        return false;
    }

    public YearWheel(Picker picker, State state) {
        super(picker, state);
        this.defaultStartYear = 1900;
        this.defaultEndYear = 2100;
    }

    @Override // com.henninghall.date_picker.wheels.Wheel
    public ArrayList<String> getValues() {
        ArrayList<String> arrayList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int startYear = getStartYear();
        int endYear = getEndYear() - startYear;
        calendar.set(1, startYear);
        for (int i = 0; i <= endYear; i++) {
            arrayList.add(getLocaleString(calendar));
            calendar.add(1, 1);
        }
        return arrayList;
    }

    private int getEndYear() {
        if (this.state.getMaximumDate() == null) {
            return this.defaultEndYear;
        }
        return this.state.getMaximumDate().get(1);
    }

    private int getStartYear() {
        if (this.state.getMinimumDate() == null) {
            return this.defaultStartYear;
        }
        return this.state.getMinimumDate().get(1);
    }

    @Override // com.henninghall.date_picker.wheels.Wheel
    public boolean visible() {
        return this.state.getMode() == Mode.date;
    }

    @Override // com.henninghall.date_picker.wheels.Wheel
    public Paint.Align getTextAlign() {
        return Paint.Align.RIGHT;
    }

    @Override // com.henninghall.date_picker.wheels.Wheel
    public String getFormatPattern() {
        return LocaleUtils.getYear(this.state.getLocaleLanguageTag());
    }
}

package com.henninghall.date_picker.wheels;

import android.graphics.Paint;
import com.henninghall.date_picker.LocaleUtils;
import com.henninghall.date_picker.State;
import com.henninghall.date_picker.Utils;
import com.henninghall.date_picker.models.Mode;
import com.henninghall.date_picker.pickers.Picker;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/* loaded from: classes2.dex */
public class DayWheel extends Wheel {
    private static int defaultNumberOfDays = 150;
    private HashMap<String, String> displayValues;
    private String todayValue;

    @Override // com.henninghall.date_picker.wheels.Wheel
    public boolean wrapSelectorWheel() {
        return false;
    }

    public DayWheel(Picker picker, State state) {
        super(picker, state);
    }

    @Override // com.henninghall.date_picker.wheels.Wheel
    public ArrayList<String> getValues() {
        ArrayList<String> arrayList = new ArrayList<>();
        this.displayValues = new HashMap<>();
        Calendar startCal = getStartCal();
        Calendar endCal = getEndCal();
        while (!startCal.after(endCal)) {
            String value = getValue(startCal);
            arrayList.add(value);
            this.displayValues.put(value, getDisplayValue(startCal));
            if (Utils.isToday(startCal)) {
                this.todayValue = value;
            }
            startCal.add(5, 1);
        }
        return arrayList;
    }

    private Calendar getStartCal() {
        Calendar maximumDate = this.state.getMaximumDate();
        Calendar minimumDate = this.state.getMinimumDate();
        if (minimumDate != null) {
            return (Calendar) minimumDate.clone();
        }
        if (maximumDate != null) {
            Calendar calendar = (Calendar) maximumDate.clone();
            calendar.add(5, (-calendar.getActualMaximum(6)) / 2);
            return calendar;
        }
        Calendar pickerDate = this.state.getPickerDate();
        pickerDate.add(5, (-defaultNumberOfDays) / 2);
        return pickerDate;
    }

    private Calendar getEndCal() {
        Calendar pickerDate;
        Calendar maximumDate = this.state.getMaximumDate();
        Calendar minimumDate = this.state.getMinimumDate();
        if (maximumDate != null) {
            return (Calendar) maximumDate.clone();
        }
        if (minimumDate != null) {
            pickerDate = (Calendar) minimumDate.clone();
            pickerDate.add(5, pickerDate.getActualMaximum(6) / 2);
        } else {
            pickerDate = this.state.getPickerDate();
            pickerDate.add(5, defaultNumberOfDays / 2);
        }
        return pickerDate;
    }

    private String getValue(Calendar calendar) {
        return this.format.format(calendar.getTime());
    }

    private String getDisplayValue(Calendar calendar) {
        return getDisplayValueFormat().format(calendar.getTime());
    }

    private String getDisplayValueFormatPattern() {
        return LocaleUtils.getDay(this.state.getLocaleLanguageTag());
    }

    private SimpleDateFormat getDisplayValueFormat() {
        return new SimpleDateFormat(getDisplayValueFormatPattern(), this.state.getLocale());
    }

    @Override // com.henninghall.date_picker.wheels.Wheel
    public boolean visible() {
        return this.state.getMode() == Mode.datetime;
    }

    @Override // com.henninghall.date_picker.wheels.Wheel
    public String getFormatPattern() {
        return LocaleUtils.getDatePattern(this.state.getLocale()).replace("EEEE", "EEE").replace("MMMM", "MMM");
    }

    @Override // com.henninghall.date_picker.wheels.Wheel
    public String toDisplayValue(String str) {
        if (str.equals(this.todayValue)) {
            return toTodayString(str);
        }
        return this.displayValues.get(str);
    }

    @Override // com.henninghall.date_picker.wheels.Wheel
    public Paint.Align getTextAlign() {
        return Paint.Align.RIGHT;
    }

    private String toTodayString(String str) {
        String printToday = Utils.printToday(this.state.getLocale());
        return Character.isUpperCase(str.charAt(0)) ? Utils.capitalize(printToday) : printToday;
    }
}

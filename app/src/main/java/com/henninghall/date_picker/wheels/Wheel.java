package com.henninghall.date_picker.wheels;

import android.graphics.Paint;
import com.henninghall.date_picker.State;
import com.henninghall.date_picker.models.Mode;
import com.henninghall.date_picker.pickers.Picker;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;

/* loaded from: classes2.dex */
public abstract class Wheel {
    public SimpleDateFormat format;
    public Picker picker;
    protected final State state;
    private Calendar userSetValue;
    private ArrayList<String> values = new ArrayList<>();

    public abstract String getFormatPattern();

    public abstract Paint.Align getTextAlign();

    public abstract ArrayList<String> getValues();

    public String toDisplayValue(String str) {
        return str;
    }

    public abstract boolean visible();

    public abstract boolean wrapSelectorWheel();

    public Wheel(Picker picker, State state) {
        this.state = state;
        this.picker = picker;
        this.format = new SimpleDateFormat(getFormatPattern(), state.getLocale());
        picker.setTextAlign(getTextAlign());
        picker.setWrapSelectorWheel(wrapSelectorWheel());
    }

    private int getIndexOfDate(Calendar calendar) {
        this.format.setTimeZone(this.state.getTimeZone());
        return this.values.indexOf(this.format.format(calendar.getTime()));
    }

    public void animateToDate(Calendar calendar) {
        this.picker.smoothScrollToValue(getIndexOfDate(calendar));
    }

    public String getValue() {
        return !visible() ? this.format.format(this.userSetValue.getTime()) : getValueAtIndex(getIndex());
    }

    public String getPastValue(int i) {
        if (!visible()) {
            return this.format.format(this.userSetValue.getTime());
        }
        int size = this.values.size();
        return getValueAtIndex(((getIndex() + size) - i) % size);
    }

    private int getIndex() {
        return this.picker.getValue();
    }

    public String getValueAtIndex(int i) {
        return this.values.get(i);
    }

    public void setValue(Calendar calendar) {
        this.format.setTimeZone(this.state.getTimeZone());
        this.userSetValue = calendar;
        int indexOfDate = getIndexOfDate(calendar);
        if (indexOfDate > -1) {
            if (this.picker.getValue() == 0) {
                this.picker.setValue(indexOfDate);
            } else {
                this.picker.smoothScrollToValue(indexOfDate);
            }
        }
    }

    public void refresh() {
        this.format = new SimpleDateFormat(getFormatPattern(), this.state.getLocale());
        if (visible()) {
            init();
        }
    }

    public String getDisplayValue() {
        return toDisplayValue(getValueAtIndex(getIndex()));
    }

    private String[] getDisplayValues(ArrayList<String> arrayList) {
        ArrayList arrayList2 = new ArrayList();
        Iterator<String> it = arrayList.iterator();
        while (it.hasNext()) {
            arrayList2.add(toDisplayValue(it.next()));
        }
        return (String[]) arrayList2.toArray(new String[0]);
    }

    private void init() {
        this.picker.setMinValue(0);
        this.picker.setMaxValue(0);
        ArrayList<String> values = getValues();
        this.values = values;
        this.picker.setDisplayedValues(getDisplayValues(values));
        this.picker.setMaxValue(this.values.size() - 1);
    }

    public void updateVisibility() {
        this.picker.setVisibility(visible() ? 0 : 8);
    }

    private SimpleDateFormat getFormat(Locale locale) {
        return new SimpleDateFormat(getFormatPattern(), locale);
    }

    String getLocaleString(Calendar calendar) {
        return getString(calendar, this.state.getLocale());
    }

    private String getString(Calendar calendar, Locale locale) {
        return getFormat(locale).format(calendar.getTime());
    }

    public void setHorizontalPadding() {
        this.picker.setItemPaddingHorizontal(getHorizontalPadding());
    }

    public int getHorizontalPadding() {
        Mode mode = this.state.getMode();
        if (this.state.derived.hasOnly2Wheels()) {
            return 10;
        }
        return AnonymousClass1.$SwitchMap$com$henninghall$date_picker$models$Mode[mode.ordinal()] != 1 ? 5 : 15;
    }

    /* renamed from: com.henninghall.date_picker.wheels.Wheel$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$henninghall$date_picker$models$Mode;

        static {
            int[] iArr = new int[Mode.values().length];
            $SwitchMap$com$henninghall$date_picker$models$Mode = iArr;
            try {
                iArr[Mode.date.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$henninghall$date_picker$models$Mode[Mode.time.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$henninghall$date_picker$models$Mode[Mode.datetime.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }
}

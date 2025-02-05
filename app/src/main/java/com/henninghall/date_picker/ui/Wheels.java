package com.henninghall.date_picker.ui;

import android.view.View;
import cn.carbswang.android.numberpickerview.library.NumberPickerView;
import com.henninghall.date_picker.R;
import com.henninghall.date_picker.State;
import com.henninghall.date_picker.models.Mode;
import com.henninghall.date_picker.models.Variant;
import com.henninghall.date_picker.models.WheelType;
import com.henninghall.date_picker.pickers.Picker;
import com.henninghall.date_picker.wheelFunctions.SetDividerHeight;
import com.henninghall.date_picker.wheelFunctions.SetShowCount;
import com.henninghall.date_picker.wheelFunctions.WheelFunction;
import com.henninghall.date_picker.wheels.AmPmWheel;
import com.henninghall.date_picker.wheels.DateWheel;
import com.henninghall.date_picker.wheels.DayWheel;
import com.henninghall.date_picker.wheels.HourWheel;
import com.henninghall.date_picker.wheels.MinutesWheel;
import com.henninghall.date_picker.wheels.MonthWheel;
import com.henninghall.date_picker.wheels.Wheel;
import com.henninghall.date_picker.wheels.YearWheel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes2.dex */
public class Wheels {
    private AmPmWheel ampmWheel;
    private DateWheel dateWheel;
    private DayWheel dayWheel;
    private final NumberPickerView emptyEnd;
    private final NumberPickerView emptyStart;
    private HourWheel hourWheel;
    private MinutesWheel minutesWheel;
    private MonthWheel monthWheel;
    private final PickerWrapper pickerWrapper;
    private View rootView;
    private final State state;
    private HashMap<WheelType, Wheel> wheelPerWheelType = getWheelPerType();
    private YearWheel yearWheel;

    Wheels(State state, View view) {
        this.state = state;
        this.rootView = view;
        this.pickerWrapper = new PickerWrapper(view);
        this.yearWheel = new YearWheel(getPickerWithId(R.id.year), state);
        this.monthWheel = new MonthWheel(getPickerWithId(R.id.month), state);
        this.dateWheel = new DateWheel(getPickerWithId(R.id.date), state);
        this.dayWheel = new DayWheel(getPickerWithId(R.id.day), state);
        this.minutesWheel = new MinutesWheel(getPickerWithId(R.id.minutes), state);
        this.ampmWheel = new AmPmWheel(getPickerWithId(R.id.ampm), state);
        this.hourWheel = new HourWheel(getPickerWithId(R.id.hour), state);
        this.emptyStart = (NumberPickerView) view.findViewById(R.id.empty_start);
        this.emptyEnd = (NumberPickerView) view.findViewById(R.id.empty_end);
        changeAmPmWhenPassingMidnightOrNoon();
    }

    private Picker getPickerWithId(int i) {
        return (Picker) this.rootView.findViewById(i);
    }

    void applyOnAll(WheelFunction wheelFunction) {
        Iterator<Wheel> it = getAll().iterator();
        while (it.hasNext()) {
            wheelFunction.apply(it.next());
        }
    }

    void applyOnVisible(WheelFunction wheelFunction) {
        for (Wheel wheel : getAll()) {
            if (wheel.visible()) {
                wheelFunction.apply(wheel);
            }
        }
    }

    void applyOnInVisible(WheelFunction wheelFunction) {
        for (Wheel wheel : getAll()) {
            if (!wheel.visible()) {
                wheelFunction.apply(wheel);
            }
        }
    }

    void updateHeight() {
        int shownCount = this.state.derived.getShownCount();
        applyOnAll(new SetShowCount(shownCount));
        if (this.state.getVariant() == Variant.iosClone) {
            this.emptyStart.setShownCount(shownCount);
            this.emptyEnd.setShownCount(shownCount);
        }
    }

    void updateDividerHeight() {
        int dividerHeight = this.state.getDividerHeight();
        applyOnAll(new SetDividerHeight(dividerHeight));
        if (this.state.getVariant() == Variant.iosClone) {
            this.emptyStart.setDividerHeight(dividerHeight);
            this.emptyEnd.setDividerHeight(dividerHeight);
        }
    }

    void updateWheelOrder() {
        this.pickerWrapper.removeAll();
        if (this.state.getVariant() == Variant.iosClone) {
            this.pickerWrapper.addPicker(this.emptyStart);
        }
        addInOrder();
        if (this.state.getVariant() == Variant.iosClone) {
            this.pickerWrapper.addPicker(this.emptyEnd);
        }
    }

    Wheel getWheel(WheelType wheelType) {
        return this.wheelPerWheelType.get(wheelType);
    }

    String getDateTimeString(int i) {
        return getDateString(i) + StringUtils.SPACE + getTimeString();
    }

    private String getDateModeString(int i) {
        ArrayList<Wheel> orderedVisibleWheels = getOrderedVisibleWheels();
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < 3; i2++) {
            if (i2 != 0) {
                sb.append(StringUtils.SPACE);
            }
            Wheel wheel = orderedVisibleWheels.get(i2);
            if (wheel instanceof DateWheel) {
                sb.append(wheel.getPastValue(i));
            } else {
                sb.append(wheel.getValue());
            }
        }
        return sb.toString();
    }

    private String getDateString(int i) {
        if (this.state.getMode() == Mode.date) {
            return getDateModeString(i);
        }
        return this.dayWheel.getValue();
    }

    String getTimeString() {
        return this.hourWheel.getValue() + StringUtils.SPACE + this.minutesWheel.getValue() + this.ampmWheel.getValue();
    }

    String getDateTimeString() {
        return getDateTimeString(0);
    }

    String getDateString() {
        return getDateString(0);
    }

    String getDisplayValue() {
        StringBuilder sb = new StringBuilder();
        Iterator<Wheel> it = getOrderedVisibleWheels().iterator();
        while (it.hasNext()) {
            sb.append(it.next().getDisplayValue());
        }
        return sb.toString();
    }

    private void addInOrder() {
        Iterator<WheelType> it = this.state.derived.getOrderedVisibleWheels().iterator();
        while (it.hasNext()) {
            this.pickerWrapper.addPicker(getWheel(it.next()).picker.getView());
        }
    }

    private ArrayList<Wheel> getOrderedVisibleWheels() {
        ArrayList<Wheel> arrayList = new ArrayList<>();
        Iterator<WheelType> it = this.state.derived.getOrderedVisibleWheels().iterator();
        while (it.hasNext()) {
            arrayList.add(getWheel(it.next()));
        }
        return arrayList;
    }

    private void changeAmPmWhenPassingMidnightOrNoon() {
        this.hourWheel.picker.setOnValueChangeListenerInScrolling(new Picker.OnValueChangeListenerInScrolling() { // from class: com.henninghall.date_picker.ui.Wheels.1
            @Override // com.henninghall.date_picker.pickers.Picker.OnValueChangeListenerInScrolling
            public void onValueChangeInScrolling(Picker picker, int i, int i2) {
                if (Wheels.this.state.derived.usesAmPm()) {
                    String valueAtIndex = Wheels.this.hourWheel.getValueAtIndex(i);
                    String valueAtIndex2 = Wheels.this.hourWheel.getValueAtIndex(i2);
                    if ((valueAtIndex.equals("12") && valueAtIndex2.equals("11")) || (valueAtIndex.equals("11") && valueAtIndex2.equals("12"))) {
                        Wheels.this.ampmWheel.picker.smoothScrollToValue((Wheels.this.ampmWheel.picker.getValue() + 1) % 2, false);
                    }
                }
            }
        });
    }

    private List<Wheel> getAll() {
        return new ArrayList(Arrays.asList(this.yearWheel, this.monthWheel, this.dateWheel, this.dayWheel, this.hourWheel, this.minutesWheel, this.ampmWheel));
    }

    private String getDateFormatPattern() {
        ArrayList<Wheel> orderedVisibleWheels = getOrderedVisibleWheels();
        if (this.state.getMode() == Mode.date) {
            return orderedVisibleWheels.get(0).getFormatPattern() + StringUtils.SPACE + orderedVisibleWheels.get(1).getFormatPattern() + StringUtils.SPACE + orderedVisibleWheels.get(2).getFormatPattern();
        }
        return this.dayWheel.getFormatPattern();
    }

    public String getFormatPattern() {
        return getDateFormatPattern() + StringUtils.SPACE + this.hourWheel.getFormatPattern() + StringUtils.SPACE + this.minutesWheel.getFormatPattern() + this.ampmWheel.getFormatPattern();
    }

    private HashMap<WheelType, Wheel> getWheelPerType() {
        return new HashMap<WheelType, Wheel>() { // from class: com.henninghall.date_picker.ui.Wheels.2
            {
                put(WheelType.DAY, Wheels.this.dayWheel);
                put(WheelType.YEAR, Wheels.this.yearWheel);
                put(WheelType.MONTH, Wheels.this.monthWheel);
                put(WheelType.DATE, Wheels.this.dateWheel);
                put(WheelType.HOUR, Wheels.this.hourWheel);
                put(WheelType.MINUTE, Wheels.this.minutesWheel);
                put(WheelType.AM_PM, Wheels.this.ampmWheel);
            }
        };
    }

    public boolean hasSpinningWheel() {
        Iterator<Wheel> it = getAll().iterator();
        while (it.hasNext()) {
            if (it.next().picker.isSpinning()) {
                return true;
            }
        }
        return false;
    }
}

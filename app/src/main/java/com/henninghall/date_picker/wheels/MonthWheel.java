package com.henninghall.date_picker.wheels;

import android.graphics.Paint;
import com.henninghall.date_picker.State;
import com.henninghall.date_picker.models.Mode;
import com.henninghall.date_picker.pickers.Picker;
import java.util.ArrayList;
import java.util.Calendar;

/* loaded from: classes2.dex */
public class MonthWheel extends Wheel {
    @Override // com.henninghall.date_picker.wheels.Wheel
    public String getFormatPattern() {
        return "LLLL";
    }

    @Override // com.henninghall.date_picker.wheels.Wheel
    public int getHorizontalPadding() {
        return 1;
    }

    @Override // com.henninghall.date_picker.wheels.Wheel
    public boolean wrapSelectorWheel() {
        return true;
    }

    public MonthWheel(Picker picker, State state) {
        super(picker, state);
    }

    @Override // com.henninghall.date_picker.wheels.Wheel
    public ArrayList<String> getValues() {
        ArrayList<String> arrayList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2, 0);
        for (int i = 0; i <= 11; i++) {
            arrayList.add(getLocaleString(calendar));
            calendar.add(2, 1);
        }
        return arrayList;
    }

    @Override // com.henninghall.date_picker.wheels.Wheel
    public boolean visible() {
        return this.state.getMode() == Mode.date;
    }

    @Override // com.henninghall.date_picker.wheels.Wheel
    public Paint.Align getTextAlign() {
        return Paint.Align.LEFT;
    }
}

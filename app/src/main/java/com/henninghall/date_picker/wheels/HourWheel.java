package com.henninghall.date_picker.wheels;

import android.graphics.Paint;
import com.google.android.gms.auth.api.credentials.CredentialsApi;
import com.henninghall.date_picker.HourDisplayBugWorkaround;
import com.henninghall.date_picker.State;
import com.henninghall.date_picker.models.Mode;
import com.henninghall.date_picker.pickers.Picker;
import java.util.ArrayList;
import java.util.Calendar;

/* loaded from: classes2.dex */
public class HourWheel extends Wheel {
    private final HourDisplayBugWorkaround hourDisplayAdjustment;

    @Override // com.henninghall.date_picker.wheels.Wheel
    public boolean wrapSelectorWheel() {
        return true;
    }

    public HourWheel(Picker picker, State state) {
        super(picker, state);
        this.hourDisplayAdjustment = new HourDisplayBugWorkaround(this.state);
    }

    @Override // com.henninghall.date_picker.wheels.Wheel
    public ArrayList<String> getValues() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(CredentialsApi.CREDENTIAL_PICKER_REQUEST_CODE, 0, 0, 0, 0, 0);
        ArrayList<String> arrayList = new ArrayList<>();
        int i = this.state.derived.usesAmPm() ? 12 : 24;
        for (int i2 = 0; i2 < i; i2++) {
            arrayList.add(this.format.format(calendar.getTime()));
            calendar.add(11, 1);
        }
        return arrayList;
    }

    @Override // com.henninghall.date_picker.wheels.Wheel
    public String toDisplayValue(String str) {
        return this.hourDisplayAdjustment.adjustValueIfNecessary(str);
    }

    @Override // com.henninghall.date_picker.wheels.Wheel
    public boolean visible() {
        return this.state.getMode() != Mode.date;
    }

    @Override // com.henninghall.date_picker.wheels.Wheel
    public String getFormatPattern() {
        return this.state.derived.usesAmPm() ? "h" : "HH";
    }

    @Override // com.henninghall.date_picker.wheels.Wheel
    public Paint.Align getTextAlign() {
        return Paint.Align.RIGHT;
    }
}

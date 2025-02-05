package com.henninghall.date_picker.wheels;

import android.graphics.Paint;
import com.google.android.gms.auth.api.credentials.CredentialsApi;
import com.henninghall.date_picker.State;
import com.henninghall.date_picker.models.Mode;
import com.henninghall.date_picker.pickers.Picker;
import java.util.ArrayList;
import java.util.Calendar;

/* loaded from: classes2.dex */
public class AmPmWheel extends Wheel {
    @Override // com.henninghall.date_picker.wheels.Wheel
    public boolean wrapSelectorWheel() {
        return false;
    }

    public AmPmWheel(Picker picker, State state) {
        super(picker, state);
    }

    @Override // com.henninghall.date_picker.wheels.Wheel
    public ArrayList<String> getValues() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(CredentialsApi.CREDENTIAL_PICKER_REQUEST_CODE, 0, 0, 0, 0, 0);
        ArrayList<String> arrayList = new ArrayList<>();
        calendar.set(11, 0);
        arrayList.add(this.format.format(calendar.getTime()));
        calendar.add(11, 12);
        arrayList.add(this.format.format(calendar.getTime()));
        return arrayList;
    }

    @Override // com.henninghall.date_picker.wheels.Wheel
    public boolean visible() {
        return this.state.derived.usesAmPm() && this.state.getMode() != Mode.date;
    }

    @Override // com.henninghall.date_picker.wheels.Wheel
    public String getFormatPattern() {
        return this.state.derived.usesAmPm() ? " a " : "";
    }

    @Override // com.henninghall.date_picker.wheels.Wheel
    public Paint.Align getTextAlign() {
        return Paint.Align.RIGHT;
    }
}

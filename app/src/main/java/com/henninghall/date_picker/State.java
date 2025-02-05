package com.henninghall.date_picker;

import com.facebook.react.bridge.Dynamic;
import com.henninghall.date_picker.models.Is24HourSource;
import com.henninghall.date_picker.models.Mode;
import com.henninghall.date_picker.models.Variant;
import com.henninghall.date_picker.props.DateProp;
import com.henninghall.date_picker.props.DividerHeightProp;
import com.henninghall.date_picker.props.FadeToColorProp;
import com.henninghall.date_picker.props.HeightProp;
import com.henninghall.date_picker.props.IdProp;
import com.henninghall.date_picker.props.Is24hourSourceProp;
import com.henninghall.date_picker.props.LocaleProp;
import com.henninghall.date_picker.props.MaximumDateProp;
import com.henninghall.date_picker.props.MinimumDateProp;
import com.henninghall.date_picker.props.MinuteIntervalProp;
import com.henninghall.date_picker.props.ModeProp;
import com.henninghall.date_picker.props.Prop;
import com.henninghall.date_picker.props.TextColorProp;
import com.henninghall.date_picker.props.TimezoneOffsetInMinutesProp;
import com.henninghall.date_picker.props.VariantProp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;
import org.apache.commons.lang3.time.TimeZones;

/* loaded from: classes2.dex */
public class State {
    private Calendar lastSelectedDate = null;
    private final DateProp dateProp = new DateProp();
    private final ModeProp modeProp = new ModeProp();
    private final LocaleProp localeProp = new LocaleProp();
    private final FadeToColorProp fadeToColorProp = new FadeToColorProp();
    private final TextColorProp textColorProp = new TextColorProp();
    private final MinuteIntervalProp minuteIntervalProp = new MinuteIntervalProp();
    private final MinimumDateProp minimumDateProp = new MinimumDateProp();
    private final MaximumDateProp maximumDateProp = new MaximumDateProp();
    private final TimezoneOffsetInMinutesProp timezoneOffsetInMinutesProp = new TimezoneOffsetInMinutesProp();
    private final HeightProp heightProp = new HeightProp();
    private final VariantProp variantProp = new VariantProp();
    private final DividerHeightProp dividerHeightProp = new DividerHeightProp();
    private final Is24hourSourceProp is24hourSourceProp = new Is24hourSourceProp();
    private final IdProp idProp = new IdProp();
    private final HashMap props = new HashMap<String, Prop>() { // from class: com.henninghall.date_picker.State.1
        {
            put(DateProp.name, State.this.dateProp);
            put(ModeProp.name, State.this.modeProp);
            put("locale", State.this.localeProp);
            put(FadeToColorProp.name, State.this.fadeToColorProp);
            put(TextColorProp.name, State.this.textColorProp);
            put(MinuteIntervalProp.name, State.this.minuteIntervalProp);
            put(MinimumDateProp.name, State.this.minimumDateProp);
            put(MaximumDateProp.name, State.this.maximumDateProp);
            put(TimezoneOffsetInMinutesProp.name, State.this.timezoneOffsetInMinutesProp);
            put("height", State.this.heightProp);
            put(VariantProp.name, State.this.variantProp);
            put(DividerHeightProp.name, State.this.dividerHeightProp);
            put(Is24hourSourceProp.name, State.this.is24hourSourceProp);
            put("id", State.this.idProp);
        }
    };
    public DerivedData derived = new DerivedData(this);

    private Prop getProp(String str) {
        return (Prop) this.props.get(str);
    }

    void setProp(String str, Dynamic dynamic) {
        getProp(str).setValue(dynamic);
    }

    public Mode getMode() {
        return this.modeProp.getValue();
    }

    public String getFadeToColor() {
        return this.fadeToColorProp.getValue();
    }

    public String getTextColor() {
        return this.textColorProp.getValue();
    }

    public int getMinuteInterval() {
        return this.minuteIntervalProp.getValue().intValue();
    }

    public Locale getLocale() {
        return this.localeProp.getValue();
    }

    public Calendar getMinimumDate() {
        return Utils.isoToCalendar(this.minimumDateProp.getValue(), getTimeZone());
    }

    public Calendar getMaximumDate() {
        return Utils.isoToCalendar(this.maximumDateProp.getValue(), getTimeZone());
    }

    public TimeZone getTimeZone() {
        String value = this.timezoneOffsetInMinutesProp.getValue();
        if (value == null || value.equals("")) {
            return TimeZone.getDefault();
        }
        int parseInt = Integer.parseInt(value);
        int abs = Math.abs(parseInt);
        char c = parseInt < 0 ? '-' : '+';
        int floor = (int) Math.floor(abs / 60.0f);
        return TimeZone.getTimeZone(TimeZones.GMT_ID + c + floor + ":" + Utils.toPaddedMinutes(abs - (floor * 60)));
    }

    public String getIsoDate() {
        return this.dateProp.getValue();
    }

    private Calendar getDate() {
        return Utils.isoToCalendar(getIsoDate(), getTimeZone());
    }

    public Calendar getPickerDate() {
        Calendar date = getDate();
        int minuteInterval = getMinuteInterval();
        if (minuteInterval <= 1) {
            return date;
        }
        date.add(12, -(Integer.parseInt(new SimpleDateFormat("mm", getLocale()).format(date.getTime())) % minuteInterval));
        return (Calendar) date.clone();
    }

    public Integer getHeight() {
        return this.heightProp.getValue();
    }

    public String getLocaleLanguageTag() {
        return this.localeProp.getLanguageTag();
    }

    public Variant getVariant() {
        return this.variantProp.getValue();
    }

    public int getDividerHeight() {
        return this.dividerHeightProp.getValue().intValue();
    }

    public String getId() {
        return this.idProp.getValue();
    }

    public Is24HourSource getIs24HourSource() {
        return this.is24hourSourceProp.getValue();
    }

    public Calendar getLastSelectedDate() {
        return this.lastSelectedDate;
    }

    public void setLastSelectedDate(Calendar calendar) {
        this.lastSelectedDate = calendar;
    }
}

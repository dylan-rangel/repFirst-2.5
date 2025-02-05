package net.time4j.calendar.bahai;

import androidx.exifinterface.media.ExifInterface;
import java.util.Locale;
import java.util.Map;
import net.time4j.format.CalendarText;

/* loaded from: classes3.dex */
public enum BadiIntercalaryDays implements BadiDivision {
    AYYAM_I_HA;

    public String getDisplayName(Locale locale) {
        return CalendarText.getInstance("bahai", locale).getTextForms().get(ExifInterface.GPS_MEASUREMENT_IN_PROGRESS);
    }

    public String getMeaning(Locale locale) {
        Map<String, String> textForms = CalendarText.getInstance("bahai", locale).getTextForms();
        String str = textForms.get("a");
        return str == null ? textForms.get(ExifInterface.GPS_MEASUREMENT_IN_PROGRESS) : str;
    }
}

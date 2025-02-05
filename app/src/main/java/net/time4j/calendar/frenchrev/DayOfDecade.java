package net.time4j.calendar.frenchrev;

import androidx.exifinterface.media.ExifInterface;
import java.util.Locale;
import net.time4j.format.CalendarText;
import net.time4j.format.OutputContext;
import net.time4j.format.TextWidth;

/* loaded from: classes3.dex */
public enum DayOfDecade {
    PRIMIDI,
    DUODI,
    TRIDI,
    QUARTIDI,
    QUINTIDI,
    SEXTIDI,
    SEPTIDI,
    OCTIDI,
    NONIDI,
    DECADI;

    public static DayOfDecade valueOf(int i) {
        if (i < 1 || i > 10) {
            throw new IllegalArgumentException("Out of range: " + i);
        }
        return values()[i - 1];
    }

    public int getValue() {
        return ordinal() + 1;
    }

    public String getDisplayName(Locale locale) {
        return getDisplayName(locale, TextWidth.WIDE, OutputContext.FORMAT);
    }

    public String getDisplayName(Locale locale, TextWidth textWidth, OutputContext outputContext) {
        CalendarText calendarText = CalendarText.getInstance("frenchrev", locale);
        String str = textWidth == TextWidth.NARROW ? "N" : outputContext == OutputContext.FORMAT ? "w" : ExifInterface.LONGITUDE_WEST;
        return calendarText.getTextForms().get("D(" + str + ")_" + getValue());
    }
}

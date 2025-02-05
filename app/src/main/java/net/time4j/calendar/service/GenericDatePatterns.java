package net.time4j.calendar.service;

import java.util.Locale;
import net.time4j.engine.DisplayStyle;
import net.time4j.format.CalendarText;
import net.time4j.format.DisplayMode;
import net.time4j.i18n.PropertyBundle;

/* loaded from: classes3.dex */
public final class GenericDatePatterns {
    private GenericDatePatterns() {
    }

    public static String get(String str, DisplayStyle displayStyle, Locale locale) {
        DisplayMode ofStyle = DisplayMode.ofStyle(displayStyle.getStyleValue());
        if (str.equals(CalendarText.ISO_CALENDAR_TYPE)) {
            return CalendarText.patternForDate(ofStyle, locale);
        }
        String str2 = "F(" + Character.toLowerCase(ofStyle.name().charAt(0)) + ')';
        PropertyBundle bundle = GenericTextProviderSPI.getBundle(str, locale);
        if (!bundle.containsKey(str2)) {
            bundle = GenericTextProviderSPI.getBundle("generic", locale);
        }
        return bundle.getString(str2);
    }
}

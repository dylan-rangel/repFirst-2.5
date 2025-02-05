package net.time4j.calendar;

import java.util.Locale;
import net.time4j.engine.CalendarEra;
import net.time4j.format.CalendarText;
import net.time4j.format.TextWidth;

/* loaded from: classes3.dex */
public enum MinguoEra implements CalendarEra {
    BEFORE_ROC,
    ROC;

    public String getDisplayName(Locale locale) {
        return getDisplayName(locale, TextWidth.WIDE);
    }

    public String getDisplayName(Locale locale, TextWidth textWidth) {
        return CalendarText.getInstance("roc", locale).getEras(textWidth).print(this);
    }
}

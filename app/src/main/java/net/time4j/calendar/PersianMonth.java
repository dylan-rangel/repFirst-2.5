package net.time4j.calendar;

import java.util.Locale;
import net.time4j.engine.ChronoCondition;
import net.time4j.format.CalendarText;
import net.time4j.format.OutputContext;
import net.time4j.format.TextWidth;

/* loaded from: classes3.dex */
public enum PersianMonth implements ChronoCondition<PersianCalendar> {
    FARVARDIN,
    ORDIBEHESHT,
    KHORDAD,
    TIR,
    MORDAD,
    SHAHRIVAR,
    MEHR,
    ABAN,
    AZAR,
    DEY,
    BAHMAN,
    ESFAND;

    private static final PersianMonth[] ENUMS = values();

    public static PersianMonth valueOf(int i) {
        if (i < 1 || i > 12) {
            throw new IllegalArgumentException("Out of range: " + i);
        }
        return ENUMS[i - 1];
    }

    public int getValue() {
        return ordinal() + 1;
    }

    public String getDisplayName(Locale locale) {
        return getDisplayName(locale, TextWidth.WIDE, OutputContext.FORMAT);
    }

    public String getDisplayName(Locale locale, TextWidth textWidth, OutputContext outputContext) {
        return CalendarText.getInstance("persian", locale).getStdMonths(textWidth, outputContext).print(this);
    }

    @Override // net.time4j.engine.ChronoCondition
    public boolean test(PersianCalendar persianCalendar) {
        return persianCalendar.getMonth() == this;
    }
}

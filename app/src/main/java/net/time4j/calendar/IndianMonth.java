package net.time4j.calendar;

import java.util.Locale;
import net.time4j.engine.ChronoCondition;
import net.time4j.format.CalendarText;
import net.time4j.format.OutputContext;
import net.time4j.format.TextWidth;

/* loaded from: classes3.dex */
public enum IndianMonth implements ChronoCondition<IndianCalendar> {
    CHAITRA,
    VAISHAKHA,
    JYESHTHA,
    ASHADHA,
    SHRAVANA,
    BHAADRA,
    ASHWIN,
    KARTIKA,
    AGRAHAYANA,
    PAUSHA,
    MAGHA,
    PHALGUNA;

    private static final IndianMonth[] ENUMS = values();

    public static IndianMonth valueOf(int i) {
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
        return CalendarText.getInstance("indian", locale).getStdMonths(textWidth, outputContext).print(this);
    }

    @Override // net.time4j.engine.ChronoCondition
    public boolean test(IndianCalendar indianCalendar) {
        return indianCalendar.getMonth() == this;
    }

    public IndianMonth roll(int i) {
        return valueOf(((ordinal() + ((i % 12) + 12)) % 12) + 1);
    }
}

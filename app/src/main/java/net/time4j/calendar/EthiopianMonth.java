package net.time4j.calendar;

import java.util.Locale;
import net.time4j.engine.ChronoCondition;
import net.time4j.format.CalendarText;
import net.time4j.format.OutputContext;
import net.time4j.format.TextWidth;

/* loaded from: classes3.dex */
public enum EthiopianMonth implements ChronoCondition<EthiopianCalendar> {
    MESKEREM,
    TEKEMT,
    HEDAR,
    TAHSAS,
    TER,
    YEKATIT,
    MEGABIT,
    MIAZIA,
    GENBOT,
    SENE,
    HAMLE,
    NEHASSE,
    PAGUMEN;

    private static final EthiopianMonth[] ENUMS = values();

    public static EthiopianMonth valueOf(int i) {
        if (i < 1 || i > 13) {
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
        return CalendarText.getInstance("ethiopic", locale).getStdMonths(textWidth, outputContext).print(this);
    }

    @Override // net.time4j.engine.ChronoCondition
    public boolean test(EthiopianCalendar ethiopianCalendar) {
        return ethiopianCalendar.getMonth() == this;
    }
}

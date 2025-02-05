package net.time4j.calendar;

import java.util.Locale;
import net.time4j.engine.ChronoCondition;
import net.time4j.format.CalendarText;
import net.time4j.format.OutputContext;
import net.time4j.format.TextWidth;

/* loaded from: classes3.dex */
public enum CopticMonth implements ChronoCondition<CopticCalendar> {
    TOUT,
    BABA,
    HATOR,
    KIAHK,
    TOBA,
    AMSHIR,
    BARAMHAT,
    BARAMOUDA,
    BASHANS,
    PAONA,
    EPEP,
    MESRA,
    NASIE;

    private static final CopticMonth[] ENUMS = values();

    public static CopticMonth valueOf(int i) {
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
        return CalendarText.getInstance("coptic", locale).getStdMonths(textWidth, outputContext).print(this);
    }

    @Override // net.time4j.engine.ChronoCondition
    public boolean test(CopticCalendar copticCalendar) {
        return copticCalendar.getMonth() == this;
    }
}

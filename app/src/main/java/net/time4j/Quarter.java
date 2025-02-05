package net.time4j;

import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Locale;
import net.time4j.base.GregorianDate;
import net.time4j.engine.ChronoCondition;
import net.time4j.engine.ChronoOperator;
import net.time4j.format.CalendarText;
import net.time4j.format.OutputContext;
import net.time4j.format.TextWidth;

/* loaded from: classes3.dex */
public enum Quarter implements ChronoCondition<GregorianDate>, ChronoOperator<PlainDate> {
    Q1,
    Q2,
    Q3,
    Q4;

    private static final Quarter[] ENUMS = values();

    public static Quarter valueOf(int i) {
        if (i < 1 || i > 4) {
            throw new IllegalArgumentException("Out of range: " + i);
        }
        return ENUMS[i - 1];
    }

    public int getValue() {
        return ordinal() + 1;
    }

    public Quarter next() {
        return roll(1);
    }

    public Quarter previous() {
        return roll(-1);
    }

    public Quarter roll(int i) {
        return valueOf(((ordinal() + ((i % 4) + 4)) % 4) + 1);
    }

    public String getDisplayName(Locale locale) {
        return getDisplayName(locale, TextWidth.WIDE, OutputContext.FORMAT);
    }

    public String getDisplayName(Locale locale, TextWidth textWidth, OutputContext outputContext) {
        return CalendarText.getIsoInstance(locale).getQuarters(textWidth, outputContext).print(this);
    }

    public static Quarter parse(CharSequence charSequence, Locale locale, TextWidth textWidth, OutputContext outputContext) throws ParseException {
        ParsePosition parsePosition = new ParsePosition(0);
        Quarter quarter = (Quarter) CalendarText.getIsoInstance(locale).getQuarters(textWidth, outputContext).parse(charSequence, parsePosition, Quarter.class);
        if (quarter != null) {
            return quarter;
        }
        throw new ParseException("Cannot parse: " + ((Object) charSequence), parsePosition.getErrorIndex());
    }

    @Override // net.time4j.engine.ChronoCondition
    public boolean test(GregorianDate gregorianDate) {
        return getValue() == ((gregorianDate.getMonth() - 1) / 3) + 1;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.time4j.engine.ChronoOperator
    public PlainDate apply(PlainDate plainDate) {
        return (PlainDate) plainDate.with(PlainDate.QUARTER_OF_YEAR, (NavigableElement<Quarter>) this);
    }
}

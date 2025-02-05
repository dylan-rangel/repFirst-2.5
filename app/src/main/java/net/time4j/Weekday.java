package net.time4j;

import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Locale;
import net.time4j.base.GregorianDate;
import net.time4j.base.GregorianMath;
import net.time4j.engine.ChronoCondition;
import net.time4j.engine.ChronoOperator;
import net.time4j.format.CalendarText;
import net.time4j.format.OutputContext;
import net.time4j.format.TextWidth;

/* loaded from: classes3.dex */
public enum Weekday implements ChronoCondition<GregorianDate>, ChronoOperator<PlainDate> {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY;

    private static final Weekday[] ENUMS = values();

    public int getValue() {
        return ordinal() + 1;
    }

    public int getValue(Weekmodel weekmodel) {
        return (((ordinal() + 7) - weekmodel.getFirstDayOfWeek().ordinal()) % 7) + 1;
    }

    public static Weekday[] values(Weekmodel weekmodel) {
        Weekday[] weekdayArr = new Weekday[7];
        Weekday firstDayOfWeek = weekmodel.getFirstDayOfWeek();
        for (int i = 0; i < 7; i++) {
            weekdayArr[i] = firstDayOfWeek;
            firstDayOfWeek = firstDayOfWeek.next();
        }
        return weekdayArr;
    }

    public static Weekday valueOf(int i) {
        if (i < 1 || i > 7) {
            throw new IllegalArgumentException("Out of range: " + i);
        }
        return ENUMS[i - 1];
    }

    public static Weekday valueOf(int i, Weekmodel weekmodel) {
        if (i < 1 || i > 7) {
            throw new IllegalArgumentException("Weekday out of range: " + i);
        }
        return ENUMS[((i - 1) + weekmodel.getFirstDayOfWeek().ordinal()) % 7];
    }

    public static Weekday valueOf(int i, Month month, int i2) {
        return valueOf(GregorianMath.getDayOfWeek(i, month.getValue(), i2));
    }

    public Weekday next() {
        return roll(1);
    }

    public Weekday previous() {
        return roll(-1);
    }

    public Weekday roll(int i) {
        return valueOf(((ordinal() + ((i % 7) + 7)) % 7) + 1);
    }

    public String getDisplayName(Locale locale) {
        return getDisplayName(locale, TextWidth.WIDE, OutputContext.FORMAT);
    }

    public String getDisplayName(Locale locale, TextWidth textWidth, OutputContext outputContext) {
        return CalendarText.getIsoInstance(locale).getWeekdays(textWidth, outputContext).print(this);
    }

    public static Weekday parse(CharSequence charSequence, Locale locale, TextWidth textWidth, OutputContext outputContext) throws ParseException {
        ParsePosition parsePosition = new ParsePosition(0);
        Weekday weekday = (Weekday) CalendarText.getIsoInstance(locale).getWeekdays(textWidth, outputContext).parse(charSequence, parsePosition, Weekday.class);
        if (weekday != null) {
            return weekday;
        }
        throw new ParseException("Cannot parse: " + ((Object) charSequence), parsePosition.getErrorIndex());
    }

    @Override // net.time4j.engine.ChronoCondition
    public boolean test(GregorianDate gregorianDate) {
        return GregorianMath.getDayOfWeek(gregorianDate.getYear(), gregorianDate.getMonth(), gregorianDate.getDayOfMonth()) == getValue();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.time4j.engine.ChronoOperator
    public PlainDate apply(PlainDate plainDate) {
        return (PlainDate) plainDate.with(PlainDate.DAY_OF_WEEK, (NavigableElement<Weekday>) this);
    }
}

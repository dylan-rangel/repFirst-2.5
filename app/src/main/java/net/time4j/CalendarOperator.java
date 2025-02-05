package net.time4j;

import net.time4j.base.GregorianMath;
import net.time4j.engine.ChronoOperator;

/* loaded from: classes3.dex */
final class CalendarOperator extends ElementOperator<PlainDate> {
    static final CalendarOperator FIRST_DAY_OF_NEXT_MONTH = new CalendarOperator(17);
    static final CalendarOperator FIRST_DAY_OF_NEXT_QUARTER = new CalendarOperator(18);
    static final CalendarOperator FIRST_DAY_OF_NEXT_YEAR = new CalendarOperator(19);
    static final CalendarOperator LAST_DAY_OF_PREVIOUS_MONTH = new CalendarOperator(20);
    static final CalendarOperator LAST_DAY_OF_PREVIOUS_QUARTER = new CalendarOperator(21);
    static final CalendarOperator LAST_DAY_OF_PREVIOUS_YEAR = new CalendarOperator(22);
    private final ChronoOperator<PlainTimestamp> tsop;

    private CalendarOperator(int i) {
        super(PlainDate.COMPONENT, i);
        this.tsop = new ChronoOperator<PlainTimestamp>() { // from class: net.time4j.CalendarOperator.1
            @Override // net.time4j.engine.ChronoOperator
            public PlainTimestamp apply(PlainTimestamp plainTimestamp) {
                return plainTimestamp.with(CalendarOperator.this.doApply(plainTimestamp.getCalendarDate()));
            }
        };
    }

    @Override // net.time4j.engine.ChronoOperator
    public PlainDate apply(PlainDate plainDate) {
        return doApply(plainDate);
    }

    @Override // net.time4j.ElementOperator
    ChronoOperator<PlainTimestamp> onTimestamp() {
        return this.tsop;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public PlainDate doApply(PlainDate plainDate) {
        int year = plainDate.getYear();
        int month = plainDate.getMonth();
        int i = 12;
        switch (getType()) {
            case 17:
                int i2 = month + 1;
                if (i2 >= 13) {
                    year++;
                    i2 = 1;
                }
                return PlainDate.of(year, i2, 1);
            case 18:
                Quarter quarterOfYear = Month.valueOf(month).getQuarterOfYear();
                int value = Month.atStartOfQuarterYear(quarterOfYear.next()).getValue();
                if (quarterOfYear == Quarter.Q4) {
                    year++;
                }
                return PlainDate.of(year, value, 1);
            case 19:
                return PlainDate.of(year + 1, 1, 1);
            case 20:
                int i3 = month - 1;
                if (i3 <= 0) {
                    year--;
                } else {
                    i = i3;
                }
                return PlainDate.of(year, i, GregorianMath.getLengthOfMonth(year, i));
            case 21:
                Quarter previous = Month.valueOf(month).getQuarterOfYear().previous();
                int value2 = Month.atEndOfQuarterYear(previous).getValue();
                if (previous == Quarter.Q4) {
                    return PlainDate.of(year - 1, value2, 31);
                }
                if (previous == Quarter.Q1) {
                    return PlainDate.of(year, value2, 31);
                }
                return PlainDate.of(year, value2, 30);
            case 22:
                return PlainDate.of(year - 1, 12, 31);
            default:
                throw new AssertionError("Unknown: " + getType());
        }
    }
}

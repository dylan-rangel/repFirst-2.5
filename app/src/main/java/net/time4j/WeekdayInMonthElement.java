package net.time4j;

import java.io.ObjectStreamException;
import net.time4j.base.GregorianMath;
import net.time4j.base.MathUtils;
import net.time4j.engine.ChronoEntity;
import net.time4j.engine.ChronoException;
import net.time4j.engine.ChronoOperator;

/* loaded from: classes3.dex */
final class WeekdayInMonthElement extends AbstractDateElement<Integer> implements OrdinalWeekdayElement {
    static final WeekdayInMonthElement INSTANCE = new WeekdayInMonthElement();
    private static final int LAST = 5;
    private static final long serialVersionUID = -2378018589067147278L;

    @Override // net.time4j.engine.BasicElement, net.time4j.engine.ChronoElement
    public char getSymbol() {
        return 'F';
    }

    @Override // net.time4j.engine.ChronoElement
    public boolean isDateElement() {
        return true;
    }

    @Override // net.time4j.engine.BasicElement
    protected boolean isSingleton() {
        return true;
    }

    @Override // net.time4j.engine.ChronoElement
    public boolean isTimeElement() {
        return false;
    }

    private WeekdayInMonthElement() {
        super("WEEKDAY_IN_MONTH");
    }

    @Override // net.time4j.engine.ChronoElement
    public Class<Integer> getType() {
        return Integer.class;
    }

    @Override // net.time4j.engine.ChronoElement
    public Integer getDefaultMinimum() {
        return 1;
    }

    @Override // net.time4j.engine.ChronoElement
    public Integer getDefaultMaximum() {
        return 5;
    }

    @Override // net.time4j.OrdinalWeekdayElement
    public ElementOperator<PlainDate> setToFirst(Weekday weekday) {
        return setTo(1, weekday);
    }

    @Override // net.time4j.OrdinalWeekdayElement
    public ElementOperator<PlainDate> setToSecond(Weekday weekday) {
        return setTo(2, weekday);
    }

    @Override // net.time4j.OrdinalWeekdayElement
    public ElementOperator<PlainDate> setToThird(Weekday weekday) {
        return setTo(3, weekday);
    }

    @Override // net.time4j.OrdinalWeekdayElement
    public ElementOperator<PlainDate> setToFourth(Weekday weekday) {
        return setTo(4, weekday);
    }

    @Override // net.time4j.OrdinalWeekdayElement
    public ElementOperator<PlainDate> setToLast(Weekday weekday) {
        return setTo(5, weekday);
    }

    private ElementOperator<PlainDate> setTo(int i, Weekday weekday) {
        return new SpecialOperator(i, weekday);
    }

    private Object readResolve() throws ObjectStreamException {
        return INSTANCE;
    }

    private static class SpecialOperator extends ElementOperator<PlainDate> {
        private final Weekday dayOfWeek;
        private final long ordinal;
        private final ChronoOperator<PlainTimestamp> specialTS;

        SpecialOperator(int i, Weekday weekday) {
            super(WeekdayInMonthElement.INSTANCE, 7);
            if (weekday == null) {
                throw new NullPointerException("Missing value.");
            }
            this.ordinal = i;
            this.dayOfWeek = weekday;
            this.specialTS = new ChronoOperator<PlainTimestamp>() { // from class: net.time4j.WeekdayInMonthElement.SpecialOperator.1
                @Override // net.time4j.engine.ChronoOperator
                public PlainTimestamp apply(PlainTimestamp plainTimestamp) {
                    return (PlainTimestamp) SpecialOperator.this.doApply(plainTimestamp);
                }
            };
        }

        @Override // net.time4j.engine.ChronoOperator
        public PlainDate apply(PlainDate plainDate) {
            return (PlainDate) doApply(plainDate);
        }

        @Override // net.time4j.ElementOperator
        ChronoOperator<PlainTimestamp> onTimestamp() {
            return this.specialTS;
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Multi-variable type inference failed */
        public <T extends ChronoEntity<T>> T doApply(T t) {
            long floorDivide;
            if (t.contains(PlainDate.CALENDAR_DATE)) {
                PlainDate plainDate = (PlainDate) t.get(PlainDate.CALENDAR_DATE);
                int value = this.dayOfWeek.getValue() - ((Weekday) plainDate.get(PlainDate.DAY_OF_WEEK)).getValue();
                int dayOfMonth = plainDate.getDayOfMonth() + value;
                long j = this.ordinal;
                if (j == 5) {
                    floorDivide = ((5 - (MathUtils.floorDivide(dayOfMonth - 1, 7) + 1)) * 7) + value;
                    if (plainDate.getDayOfMonth() + floorDivide > GregorianMath.getLengthOfMonth(plainDate.getYear(), plainDate.getMonth())) {
                        floorDivide -= 7;
                    }
                } else {
                    floorDivide = ((j - (MathUtils.floorDivide(dayOfMonth - 1, 7) + 1)) * 7) + value;
                }
                return (T) t.with(PlainDate.CALENDAR_DATE, (PlainDate) plainDate.plus(floorDivide, CalendarUnit.DAYS));
            }
            throw new ChronoException("Rule not found for ordinal day of week in month: " + t);
        }
    }
}

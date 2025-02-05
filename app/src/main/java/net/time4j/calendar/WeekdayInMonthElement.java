package net.time4j.calendar;

import net.time4j.Weekday;
import net.time4j.base.MathUtils;
import net.time4j.calendar.service.StdIntegerDateElement;
import net.time4j.engine.CalendarDate;
import net.time4j.engine.ChronoElement;
import net.time4j.engine.ChronoEntity;
import net.time4j.engine.ChronoOperator;
import net.time4j.engine.ElementRule;
import net.time4j.engine.EpochDays;
import net.time4j.engine.IntElementRule;

/* loaded from: classes3.dex */
final class WeekdayInMonthElement<T extends ChronoEntity<T> & CalendarDate> extends StdIntegerDateElement<T> implements OrdinalWeekdayElement<T> {
    private static final int LAST = Integer.MAX_VALUE;
    private static final long serialVersionUID = 4275169663905222176L;
    private final transient ChronoElement<Integer> domElement;
    private final transient ChronoElement<Weekday> dowElement;

    WeekdayInMonthElement(Class<T> cls, ChronoElement<Integer> chronoElement, ChronoElement<Weekday> chronoElement2) {
        super("WEEKDAY_IN_MONTH", cls, 1, chronoElement.getDefaultMaximum().intValue() / 7, 'F', new WeekOperator(true), new WeekOperator(false));
        this.domElement = chronoElement;
        this.dowElement = chronoElement2;
    }

    @Override // net.time4j.calendar.OrdinalWeekdayElement
    public ChronoOperator<T> setToFirst(Weekday weekday) {
        return setTo(1, weekday);
    }

    @Override // net.time4j.calendar.OrdinalWeekdayElement
    public ChronoOperator<T> setToLast(Weekday weekday) {
        return setTo(Integer.MAX_VALUE, weekday);
    }

    @Override // net.time4j.calendar.OrdinalWeekdayElement
    public ChronoOperator<T> setTo(int i, Weekday weekday) {
        return new SetOperator(this, i, weekday);
    }

    static <T extends ChronoEntity<T> & CalendarDate> ElementRule<T, Integer> getRule(WeekdayInMonthElement<T> weekdayInMonthElement) {
        return new Rule(weekdayInMonthElement);
    }

    private static class Rule<T extends ChronoEntity<T> & CalendarDate> implements IntElementRule<T> {
        private final WeekdayInMonthElement<T> wim;

        /* JADX WARN: Incorrect types in method signature: (TT;)Lnet/time4j/engine/ChronoElement<*>; */
        @Override // net.time4j.engine.ElementRule
        public ChronoElement getChildAtCeiling(ChronoEntity chronoEntity) {
            return null;
        }

        /* JADX WARN: Incorrect types in method signature: (TT;)Lnet/time4j/engine/ChronoElement<*>; */
        @Override // net.time4j.engine.ElementRule
        public ChronoElement getChildAtFloor(ChronoEntity chronoEntity) {
            return null;
        }

        Rule(WeekdayInMonthElement<T> weekdayInMonthElement) {
            this.wim = weekdayInMonthElement;
        }

        /* JADX WARN: Incorrect types in method signature: (TT;)Ljava/lang/Integer; */
        @Override // net.time4j.engine.ElementRule
        public Integer getValue(ChronoEntity chronoEntity) {
            return Integer.valueOf(getInt(chronoEntity));
        }

        /* JADX WARN: Incorrect types in method signature: (TT;)Ljava/lang/Integer; */
        @Override // net.time4j.engine.ElementRule
        public Integer getMinimum(ChronoEntity chronoEntity) {
            return 1;
        }

        /* JADX WARN: Incorrect types in method signature: (TT;)Ljava/lang/Integer; */
        @Override // net.time4j.engine.ElementRule
        public Integer getMaximum(ChronoEntity chronoEntity) {
            return Integer.valueOf(getMax(chronoEntity));
        }

        /* JADX WARN: Incorrect types in method signature: (TT;Ljava/lang/Integer;)Z */
        @Override // net.time4j.engine.ElementRule
        /* renamed from: isValid, reason: merged with bridge method [inline-methods] */
        public boolean isValid2(ChronoEntity chronoEntity, Integer num) {
            return num != null && isValid(chronoEntity, num.intValue());
        }

        /* JADX WARN: Incorrect return type in method signature: (TT;Ljava/lang/Integer;Z)TT; */
        @Override // net.time4j.engine.ElementRule
        /* renamed from: withValue, reason: merged with bridge method [inline-methods] */
        public ChronoEntity withValue2(ChronoEntity chronoEntity, Integer num, boolean z) {
            if (num != null) {
                return withValue(chronoEntity, num.intValue(), z);
            }
            throw new IllegalArgumentException("Missing value.");
        }

        /* JADX WARN: Incorrect types in method signature: (TT;I)Z */
        @Override // net.time4j.engine.IntElementRule
        public boolean isValid(ChronoEntity chronoEntity, int i) {
            return i >= 1 && i <= getMax(chronoEntity);
        }

        /* JADX WARN: Incorrect return type in method signature: (TT;IZ)TT; */
        @Override // net.time4j.engine.IntElementRule
        public ChronoEntity withValue(ChronoEntity chronoEntity, int i, boolean z) {
            if (isValid(chronoEntity, i)) {
                return chronoEntity.with(this.wim.setTo(i, (Weekday) chronoEntity.get(((WeekdayInMonthElement) this.wim).dowElement)));
            }
            throw new IllegalArgumentException("Invalid value: " + i);
        }

        /* JADX WARN: Incorrect types in method signature: (TT;)I */
        @Override // net.time4j.engine.IntElementRule
        public int getInt(ChronoEntity chronoEntity) {
            return MathUtils.floorDivide(chronoEntity.getInt(((WeekdayInMonthElement) this.wim).domElement) - 1, 7) + 1;
        }

        /* JADX WARN: Incorrect types in method signature: (TT;)I */
        private int getMax(ChronoEntity chronoEntity) {
            int i = chronoEntity.getInt(((WeekdayInMonthElement) this.wim).domElement);
            while (true) {
                int i2 = i + 7;
                if (i2 > ((Integer) chronoEntity.getMaximum(((WeekdayInMonthElement) this.wim).domElement)).intValue()) {
                    return MathUtils.floorDivide(i - 1, 7) + 1;
                }
                i = i2;
            }
        }
    }

    private static class SetOperator<T extends ChronoEntity<T> & CalendarDate> implements ChronoOperator<T> {
        private final Weekday dayOfWeek;
        private final long ordinal;
        private final WeekdayInMonthElement<T> wim;

        SetOperator(WeekdayInMonthElement<T> weekdayInMonthElement, int i, Weekday weekday) {
            if (weekday == null) {
                throw new NullPointerException("Missing value.");
            }
            this.wim = weekdayInMonthElement;
            this.ordinal = i;
            this.dayOfWeek = weekday;
        }

        /* JADX WARN: Incorrect return type in method signature: (TT;)TT; */
        /* JADX WARN: Multi-variable type inference failed */
        @Override // net.time4j.engine.ChronoOperator
        public ChronoEntity apply(ChronoEntity chronoEntity) {
            long floorDivide;
            Weekday weekday = (Weekday) chronoEntity.get(((WeekdayInMonthElement) this.wim).dowElement);
            int i = chronoEntity.getInt(((WeekdayInMonthElement) this.wim).domElement);
            if (this.ordinal == 2147483647L) {
                int intValue = ((Integer) chronoEntity.getMaximum(((WeekdayInMonthElement) this.wim).domElement)).intValue() - i;
                int value = weekday.getValue() + (intValue % 7);
                if (value > 7) {
                    value -= 7;
                }
                int value2 = this.dayOfWeek.getValue() - value;
                floorDivide = intValue + value2;
                if (value2 > 0) {
                    floorDivide -= 7;
                }
            } else {
                floorDivide = ((this.ordinal - (MathUtils.floorDivide((i + r2) - 1, 7) + 1)) * 7) + (this.dayOfWeek.getValue() - weekday.getValue());
            }
            return chronoEntity.with(EpochDays.UTC, ((CalendarDate) chronoEntity).getDaysSinceEpochUTC() + floorDivide);
        }
    }

    private static class WeekOperator<T extends ChronoEntity<T>> implements ChronoOperator<T> {
        private final boolean backwards;

        WeekOperator(boolean z) {
            this.backwards = z;
        }

        @Override // net.time4j.engine.ChronoOperator
        public T apply(T t) {
            long longValue = ((Long) t.get(EpochDays.UTC)).longValue();
            return (T) t.with(EpochDays.UTC, this.backwards ? longValue - 7 : longValue + 7);
        }
    }
}

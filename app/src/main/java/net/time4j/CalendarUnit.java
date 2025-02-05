package net.time4j;

import net.time4j.engine.CalendarDate;
import net.time4j.engine.ChronoEntity;
import net.time4j.engine.TimePoint;
import net.time4j.engine.UnitRule;

/* loaded from: classes3.dex */
public enum CalendarUnit implements IsoDateUnit {
    MILLENNIA { // from class: net.time4j.CalendarUnit.1
        @Override // net.time4j.engine.ChronoUnit
        public double getLength() {
            return 3.1556952E10d;
        }

        @Override // net.time4j.IsoUnit
        public char getSymbol() {
            return 'I';
        }
    },
    CENTURIES { // from class: net.time4j.CalendarUnit.2
        @Override // net.time4j.engine.ChronoUnit
        public double getLength() {
            return 3.1556952E9d;
        }

        @Override // net.time4j.IsoUnit
        public char getSymbol() {
            return 'C';
        }
    },
    DECADES { // from class: net.time4j.CalendarUnit.3
        @Override // net.time4j.engine.ChronoUnit
        public double getLength() {
            return 3.1556952E8d;
        }

        @Override // net.time4j.IsoUnit
        public char getSymbol() {
            return 'E';
        }
    },
    YEARS { // from class: net.time4j.CalendarUnit.4
        @Override // net.time4j.engine.ChronoUnit
        public double getLength() {
            return 3.1556952E7d;
        }

        @Override // net.time4j.IsoUnit
        public char getSymbol() {
            return 'Y';
        }
    },
    QUARTERS { // from class: net.time4j.CalendarUnit.5
        @Override // net.time4j.engine.ChronoUnit
        public double getLength() {
            return 7889238.0d;
        }

        @Override // net.time4j.IsoUnit
        public char getSymbol() {
            return 'Q';
        }
    },
    MONTHS { // from class: net.time4j.CalendarUnit.6
        @Override // net.time4j.engine.ChronoUnit
        public double getLength() {
            return 2629746.0d;
        }

        @Override // net.time4j.IsoUnit
        public char getSymbol() {
            return 'M';
        }
    },
    WEEKS { // from class: net.time4j.CalendarUnit.7
        @Override // net.time4j.engine.ChronoUnit
        public double getLength() {
            return 604800.0d;
        }

        @Override // net.time4j.IsoUnit
        public char getSymbol() {
            return 'W';
        }
    },
    DAYS { // from class: net.time4j.CalendarUnit.8
        @Override // net.time4j.engine.ChronoUnit
        public double getLength() {
            return 86400.0d;
        }

        @Override // net.time4j.IsoUnit
        public char getSymbol() {
            return 'D';
        }
    };

    private final IsoDateUnit co;
    private final IsoDateUnit eof;
    private final IsoDateUnit joda;
    private final IsoDateUnit kld;
    private final IsoDateUnit nvd;
    private final IsoDateUnit ui;

    @Override // net.time4j.engine.ChronoUnit
    public boolean isCalendrical() {
        return true;
    }

    CalendarUnit() {
        this.eof = new OverflowUnit(this, 2);
        this.kld = new OverflowUnit(this, 5);
        this.ui = new OverflowUnit(this, 4);
        this.nvd = new OverflowUnit(this, 1);
        this.co = new OverflowUnit(this, 3);
        this.joda = new OverflowUnit(this, 6);
    }

    public <T extends TimePoint<? super CalendarUnit, T>> long between(T t, T t2) {
        return t.until(t2, this);
    }

    /* renamed from: net.time4j.CalendarUnit$9, reason: invalid class name */
    static /* synthetic */ class AnonymousClass9 {
        static final /* synthetic */ int[] $SwitchMap$net$time4j$CalendarUnit;

        static {
            int[] iArr = new int[CalendarUnit.values().length];
            $SwitchMap$net$time4j$CalendarUnit = iArr;
            try {
                iArr[CalendarUnit.WEEKS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$net$time4j$CalendarUnit[CalendarUnit.DAYS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$net$time4j$CalendarUnit[CalendarUnit.MILLENNIA.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$net$time4j$CalendarUnit[CalendarUnit.CENTURIES.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$net$time4j$CalendarUnit[CalendarUnit.DECADES.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$net$time4j$CalendarUnit[CalendarUnit.YEARS.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$net$time4j$CalendarUnit[CalendarUnit.QUARTERS.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$net$time4j$CalendarUnit[CalendarUnit.MONTHS.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }

    public IsoDateUnit nextValidDate() {
        int i = AnonymousClass9.$SwitchMap$net$time4j$CalendarUnit[ordinal()];
        return (i == 1 || i == 2) ? this : this.nvd;
    }

    public IsoDateUnit withCarryOver() {
        int i = AnonymousClass9.$SwitchMap$net$time4j$CalendarUnit[ordinal()];
        return (i == 1 || i == 2) ? this : this.co;
    }

    public IsoDateUnit unlessInvalid() {
        int i = AnonymousClass9.$SwitchMap$net$time4j$CalendarUnit[ordinal()];
        return (i == 1 || i == 2) ? this : this.ui;
    }

    public IsoDateUnit atEndOfMonth() {
        int i = AnonymousClass9.$SwitchMap$net$time4j$CalendarUnit[ordinal()];
        if (i == 1 || i == 2) {
            throw new UnsupportedOperationException("Original unit is not month-based: " + name());
        }
        return this.eof;
    }

    public IsoDateUnit keepingEndOfMonth() {
        int i = AnonymousClass9.$SwitchMap$net$time4j$CalendarUnit[ordinal()];
        if (i == 1 || i == 2) {
            throw new UnsupportedOperationException("Original unit is not month-based: " + name());
        }
        return this.kld;
    }

    public IsoDateUnit withJodaMetric() {
        int i = AnonymousClass9.$SwitchMap$net$time4j$CalendarUnit[ordinal()];
        return (i == 1 || i == 2) ? this : this.joda;
    }

    public static IsoDateUnit weekBasedYears() {
        return Weekcycle.YEARS;
    }

    static class Rule<T extends ChronoEntity<T>> implements UnitRule<T> {
        private final int policy;
        private final CalendarUnit unit;

        Rule(CalendarUnit calendarUnit) {
            this(calendarUnit, 0);
        }

        Rule(CalendarUnit calendarUnit, int i) {
            this.unit = calendarUnit;
            this.policy = i;
        }

        @Override // net.time4j.engine.UnitRule
        public T addTo(T t, long j) {
            return (T) t.with(PlainDate.CALENDAR_DATE, PlainDate.doAdd(this.unit, (PlainDate) t.get(PlainDate.CALENDAR_DATE), j, this.policy));
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // net.time4j.engine.UnitRule
        public long between(T t, T t2) {
            long dayDelta;
            PlainDate plainDate = (PlainDate) t.get(PlainDate.CALENDAR_DATE);
            PlainDate plainDate2 = (PlainDate) t2.get(PlainDate.CALENDAR_DATE);
            switch (AnonymousClass9.$SwitchMap$net$time4j$CalendarUnit[this.unit.ordinal()]) {
                case 1:
                    dayDelta = dayDelta(plainDate, plainDate2) / 7;
                    break;
                case 2:
                    dayDelta = dayDelta(plainDate, plainDate2);
                    break;
                case 3:
                    dayDelta = monthDelta(plainDate, plainDate2) / 12000;
                    break;
                case 4:
                    dayDelta = monthDelta(plainDate, plainDate2) / 1200;
                    break;
                case 5:
                    dayDelta = monthDelta(plainDate, plainDate2) / 120;
                    break;
                case 6:
                    dayDelta = monthDelta(plainDate, plainDate2) / 12;
                    break;
                case 7:
                    dayDelta = monthDelta(plainDate, plainDate2) / 3;
                    break;
                case 8:
                    dayDelta = monthDelta(plainDate, plainDate2);
                    break;
                default:
                    throw new UnsupportedOperationException(this.unit.name());
            }
            if (dayDelta == 0 || !t.contains(PlainTime.WALL_TIME) || !t2.contains(PlainTime.WALL_TIME)) {
                return dayDelta;
            }
            boolean z = true;
            if (this.unit != CalendarUnit.DAYS && ((PlainDate) plainDate.plus(dayDelta, this.unit)).compareByTime(plainDate2) != 0) {
                z = false;
            }
            if (!z) {
                return dayDelta;
            }
            PlainTime plainTime = (PlainTime) t.get(PlainTime.WALL_TIME);
            PlainTime plainTime2 = (PlainTime) t2.get(PlainTime.WALL_TIME);
            return (dayDelta <= 0 || !plainTime.isAfter(plainTime2)) ? (dayDelta >= 0 || !plainTime.isBefore(plainTime2)) ? dayDelta : dayDelta + 1 : dayDelta - 1;
        }

        private long monthDelta(PlainDate plainDate, PlainDate plainDate2) {
            long epochMonths = plainDate2.getEpochMonths() - plainDate.getEpochMonths();
            int i = this.policy;
            if (i == 5 || i == 2 || i == 6) {
                CalendarUnit calendarUnit = CalendarUnit.MONTHS;
                if (epochMonths <= 0 || !PlainDate.doAdd(calendarUnit, plainDate, epochMonths, this.policy).isAfter((CalendarDate) plainDate2)) {
                    if (epochMonths >= 0 || !PlainDate.doAdd(calendarUnit, plainDate, epochMonths, this.policy).isBefore((CalendarDate) plainDate2)) {
                        return epochMonths;
                    }
                    return epochMonths + 1;
                }
                return epochMonths - 1;
            }
            if (epochMonths <= 0 || plainDate2.getDayOfMonth() >= plainDate.getDayOfMonth()) {
                if (epochMonths >= 0 || plainDate2.getDayOfMonth() <= plainDate.getDayOfMonth()) {
                    return epochMonths;
                }
                return epochMonths + 1;
            }
            return epochMonths - 1;
        }

        private static long dayDelta(PlainDate plainDate, PlainDate plainDate2) {
            if (plainDate.getYear() == plainDate2.getYear()) {
                return plainDate2.getDayOfYear() - plainDate.getDayOfYear();
            }
            return plainDate2.getDaysSinceUTC() - plainDate.getDaysSinceUTC();
        }
    }
}

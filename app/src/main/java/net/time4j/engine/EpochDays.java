package net.time4j.engine;

import java.util.Locale;
import net.time4j.base.MathUtils;

/* loaded from: classes3.dex */
public enum EpochDays implements ChronoElement<Long> {
    UTC(2441317),
    UNIX(2440587),
    MODIFIED_JULIAN_DATE(2400000),
    EXCEL(2415019),
    ANSI(2305812),
    RATA_DIE(1721424),
    JULIAN_DAY_NUMBER(-1),
    LILIAN_DAY_NUMBER(2299159);

    private final int offset;

    @Override // net.time4j.engine.ChronoElement
    public boolean isDateElement() {
        return true;
    }

    @Override // net.time4j.engine.ChronoElement
    public boolean isLenient() {
        return false;
    }

    @Override // net.time4j.engine.ChronoElement
    public boolean isTimeElement() {
        return false;
    }

    EpochDays(int i) {
        this.offset = (i - 2440587) - 730;
    }

    public long transform(long j, EpochDays epochDays) {
        try {
            return MathUtils.safeAdd(j, epochDays.offset - this.offset);
        } catch (ArithmeticException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override // net.time4j.engine.ChronoElement
    public Class<Long> getType() {
        return Long.class;
    }

    @Override // net.time4j.engine.ChronoElement
    public char getSymbol() {
        return this == MODIFIED_JULIAN_DATE ? 'g' : (char) 0;
    }

    @Override // java.util.Comparator
    public int compare(ChronoDisplay chronoDisplay, ChronoDisplay chronoDisplay2) {
        return ((Long) chronoDisplay.get(this)).compareTo((Long) chronoDisplay2.get(this));
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.time4j.engine.ChronoElement
    public Long getDefaultMinimum() {
        return Long.valueOf((-365243219892L) - this.offset);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.time4j.engine.ChronoElement
    public Long getDefaultMaximum() {
        return Long.valueOf(365241779741L - this.offset);
    }

    @Override // net.time4j.engine.ChronoElement
    public String getDisplayName(Locale locale) {
        return name();
    }

    <D extends ChronoEntity<D>> ElementRule<D, Long> derive(CalendarSystem<D> calendarSystem) {
        return new Rule(this, calendarSystem);
    }

    private static class Rule<D extends ChronoEntity<D>> implements ElementRule<D, Long> {
        private static final int UTC_OFFSET = 730;
        private final CalendarSystem<D> calsys;
        private final EpochDays element;

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtCeiling(D d) {
            return null;
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtFloor(D d) {
            return null;
        }

        Rule(EpochDays epochDays, CalendarSystem<D> calendarSystem) {
            this.element = epochDays;
            this.calsys = calendarSystem;
        }

        @Override // net.time4j.engine.ElementRule
        public Long getValue(D d) {
            return Long.valueOf(this.element.transform(this.calsys.transform((CalendarSystem<D>) d) + 730, EpochDays.UNIX));
        }

        @Override // net.time4j.engine.ElementRule
        public boolean isValid2(D d, Long l) {
            if (l == null) {
                return false;
            }
            try {
                long safeSubtract = MathUtils.safeSubtract(EpochDays.UNIX.transform(l.longValue(), this.element), 730L);
                if (safeSubtract <= this.calsys.getMaximumSinceUTC()) {
                    return safeSubtract >= this.calsys.getMinimumSinceUTC();
                }
                return false;
            } catch (ArithmeticException | IllegalArgumentException unused) {
                return false;
            }
        }

        @Override // net.time4j.engine.ElementRule
        public D withValue2(D d, Long l, boolean z) {
            if (l == null) {
                throw new IllegalArgumentException("Missing epoch day value.");
            }
            return this.calsys.transform(MathUtils.safeSubtract(EpochDays.UNIX.transform(l.longValue(), this.element), 730L));
        }

        @Override // net.time4j.engine.ElementRule
        public Long getMinimum(D d) {
            return Long.valueOf(this.element.transform(this.calsys.getMinimumSinceUTC() + 730, EpochDays.UNIX));
        }

        @Override // net.time4j.engine.ElementRule
        public Long getMaximum(D d) {
            return Long.valueOf(this.element.transform(this.calsys.getMaximumSinceUTC() + 730, EpochDays.UNIX));
        }
    }
}

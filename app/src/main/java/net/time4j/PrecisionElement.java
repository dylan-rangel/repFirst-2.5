package net.time4j;

import java.lang.Comparable;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import net.time4j.engine.ChronoDisplay;
import net.time4j.engine.ChronoElement;

/* loaded from: classes3.dex */
class PrecisionElement<U extends Comparable<U>> implements ChronoElement<U> {
    static final ChronoElement<ClockUnit> CLOCK_PRECISION = new PrecisionElement(ClockUnit.class, ClockUnit.HOURS, ClockUnit.NANOS);
    static final ChronoElement<TimeUnit> TIME_PRECISION = new PrecisionElement(TimeUnit.class, TimeUnit.DAYS, TimeUnit.NANOSECONDS);
    private final transient U max;
    private final transient U min;
    private final Class<U> type;

    @Override // net.time4j.engine.ChronoElement
    public char getSymbol() {
        return (char) 0;
    }

    @Override // net.time4j.engine.ChronoElement
    public boolean isDateElement() {
        return false;
    }

    @Override // net.time4j.engine.ChronoElement
    public boolean isLenient() {
        return false;
    }

    @Override // net.time4j.engine.ChronoElement
    public boolean isTimeElement() {
        return true;
    }

    @Override // net.time4j.engine.ChronoElement
    public String name() {
        return "PRECISION";
    }

    private PrecisionElement(Class<U> cls, U u, U u2) {
        this.type = cls;
        this.min = u;
        this.max = u2;
    }

    @Override // net.time4j.engine.ChronoElement
    public Class<U> getType() {
        return this.type;
    }

    @Override // net.time4j.engine.ChronoElement
    public U getDefaultMinimum() {
        return this.min;
    }

    @Override // net.time4j.engine.ChronoElement
    public U getDefaultMaximum() {
        return this.max;
    }

    @Override // java.util.Comparator
    public int compare(ChronoDisplay chronoDisplay, ChronoDisplay chronoDisplay2) {
        Comparable comparable = (Comparable) chronoDisplay.get(this);
        Comparable comparable2 = (Comparable) chronoDisplay2.get(this);
        if (this.type == ClockUnit.class) {
            return comparable.compareTo(comparable2);
        }
        return comparable2.compareTo(comparable);
    }

    @Override // net.time4j.engine.ChronoElement
    public String getDisplayName(Locale locale) {
        return name();
    }

    private Object readResolve() {
        return this.type == ClockUnit.class ? CLOCK_PRECISION : TIME_PRECISION;
    }
}

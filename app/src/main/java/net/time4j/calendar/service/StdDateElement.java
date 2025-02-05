package net.time4j.calendar.service;

import java.io.InvalidObjectException;
import java.io.ObjectStreamException;
import java.lang.Comparable;
import net.time4j.calendar.StdCalendarElement;
import net.time4j.engine.BasicElement;
import net.time4j.engine.ChronoElement;
import net.time4j.engine.ChronoEntity;
import net.time4j.engine.ChronoOperator;
import net.time4j.engine.Chronology;
import net.time4j.engine.EpochDays;
import net.time4j.engine.StdOperator;
import net.time4j.format.DisplayElement;

/* loaded from: classes3.dex */
public abstract class StdDateElement<V extends Comparable<V>, T extends ChronoEntity<T>> extends DisplayElement<V> implements StdCalendarElement<V, T> {
    private final Class<T> chrono;
    private final transient boolean daywise;
    private final transient char symbol;

    @Override // net.time4j.engine.ChronoElement
    public boolean isDateElement() {
        return true;
    }

    @Override // net.time4j.engine.ChronoElement
    public boolean isTimeElement() {
        return false;
    }

    public StdDateElement(String str, Class<T> cls, char c, boolean z) {
        super(str);
        this.chrono = cls;
        this.symbol = c;
        this.daywise = z;
    }

    @Override // net.time4j.engine.BasicElement, net.time4j.engine.ChronoElement
    public char getSymbol() {
        return this.symbol;
    }

    @Override // net.time4j.calendar.StdCalendarElement
    public ChronoOperator<T> minimized() {
        return StdOperator.minimized(this);
    }

    @Override // net.time4j.calendar.StdCalendarElement
    public ChronoOperator<T> maximized() {
        return StdOperator.maximized(this);
    }

    public ChronoOperator<T> decremented() {
        if (this.daywise) {
            return new DayOperator(true);
        }
        return StdOperator.decremented(this);
    }

    public ChronoOperator<T> incremented() {
        if (this.daywise) {
            return new DayOperator(false);
        }
        return StdOperator.incremented(this);
    }

    @Override // net.time4j.calendar.StdCalendarElement
    public ChronoOperator<T> atFloor() {
        return StdOperator.atFloor(this);
    }

    @Override // net.time4j.calendar.StdCalendarElement
    public ChronoOperator<T> atCeiling() {
        return StdOperator.atCeiling(this);
    }

    @Override // net.time4j.engine.BasicElement
    protected boolean doEquals(BasicElement<?> basicElement) {
        return this.chrono == ((StdDateElement) basicElement).chrono;
    }

    protected Object readResolve() throws ObjectStreamException {
        String name = name();
        for (ChronoElement<?> chronoElement : Chronology.lookup(this.chrono).getRegisteredElements()) {
            if (chronoElement.name().equals(name)) {
                return chronoElement;
            }
        }
        throw new InvalidObjectException(name);
    }

    protected Class<T> getChronoType() {
        return this.chrono;
    }

    private static class DayOperator<T extends ChronoEntity<T>> implements ChronoOperator<T> {
        private final boolean backwards;

        DayOperator(boolean z) {
            this.backwards = z;
        }

        @Override // net.time4j.engine.ChronoOperator
        public T apply(T t) {
            long longValue = ((Long) t.get(EpochDays.UTC)).longValue();
            return (T) t.with(EpochDays.UTC, this.backwards ? longValue - 1 : longValue + 1);
        }
    }
}

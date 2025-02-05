package net.time4j;

import java.io.ObjectStreamException;
import java.io.Serializable;
import net.time4j.engine.BasicUnit;
import net.time4j.engine.ChronoEntity;
import net.time4j.engine.Chronology;
import net.time4j.engine.UnitRule;

/* loaded from: classes3.dex */
public final class Weekcycle extends BasicUnit implements IsoDateUnit, Serializable {
    public static final Weekcycle YEARS = new Weekcycle();
    private static final long serialVersionUID = -4981215347844372171L;

    @Override // net.time4j.IsoUnit
    public char getSymbol() {
        return 'Y';
    }

    @Override // net.time4j.engine.BasicUnit, net.time4j.engine.ChronoUnit
    public boolean isCalendrical() {
        return true;
    }

    public String toString() {
        return "WEEK_BASED_YEARS";
    }

    private Weekcycle() {
    }

    public long between(PlainDate plainDate, PlainDate plainDate2) {
        return derive((Weekcycle) plainDate).between(plainDate, plainDate2);
    }

    @Override // net.time4j.engine.ChronoUnit
    public double getLength() {
        return CalendarUnit.YEARS.getLength();
    }

    @Override // net.time4j.engine.BasicUnit
    protected <T extends ChronoEntity<T>> UnitRule<T> derive(Chronology<T> chronology) {
        if (chronology.isRegistered(PlainDate.CALENDAR_DATE)) {
            return YOWElement.unitRule();
        }
        return null;
    }

    private Object readResolve() throws ObjectStreamException {
        return YEARS;
    }
}

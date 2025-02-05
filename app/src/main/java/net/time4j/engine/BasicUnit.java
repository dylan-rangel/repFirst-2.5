package net.time4j.engine;

/* loaded from: classes3.dex */
public abstract class BasicUnit implements ChronoUnit {
    protected <T extends ChronoEntity<T>> UnitRule<T> derive(Chronology<T> chronology) {
        return null;
    }

    protected BasicUnit() {
    }

    @Override // net.time4j.engine.ChronoUnit
    public boolean isCalendrical() {
        return Double.compare(getLength(), 86400.0d) >= 0;
    }

    protected final <T extends ChronoEntity<T>> UnitRule<T> derive(T t) {
        return derive(t.getChronology());
    }
}

package net.time4j.calendar.service;

import net.time4j.engine.ChronoEntity;
import net.time4j.engine.ChronoOperator;

/* loaded from: classes3.dex */
public class StdIntegerDateElement<T extends ChronoEntity<T>> extends StdDateElement<Integer, T> {
    private static final long serialVersionUID = -4975173343610190782L;
    private final transient ChronoOperator<T> decrementor;
    private final transient ChronoOperator<T> incrementor;
    private final transient int max;
    private final transient int min;

    public StdIntegerDateElement(String str, Class<T> cls, int i, int i2, char c) {
        super(str, cls, c, str.startsWith("DAY_OF_"));
        this.min = i;
        this.max = i2;
        this.decrementor = null;
        this.incrementor = null;
    }

    public StdIntegerDateElement(String str, Class<T> cls, int i, int i2, char c, ChronoOperator<T> chronoOperator, ChronoOperator<T> chronoOperator2) {
        super(str, cls, c, false);
        this.min = i;
        this.max = i2;
        this.decrementor = chronoOperator;
        this.incrementor = chronoOperator2;
    }

    @Override // net.time4j.engine.ChronoElement
    public Class<Integer> getType() {
        return Integer.class;
    }

    @Override // net.time4j.engine.ChronoElement
    public Integer getDefaultMinimum() {
        return Integer.valueOf(this.min);
    }

    @Override // net.time4j.engine.ChronoElement
    public Integer getDefaultMaximum() {
        return Integer.valueOf(this.max);
    }

    @Override // net.time4j.calendar.service.StdDateElement, net.time4j.calendar.StdCalendarElement
    public ChronoOperator<T> decremented() {
        ChronoOperator<T> chronoOperator = this.decrementor;
        return chronoOperator != null ? chronoOperator : super.decremented();
    }

    @Override // net.time4j.calendar.service.StdDateElement, net.time4j.calendar.StdCalendarElement
    public ChronoOperator<T> incremented() {
        ChronoOperator<T> chronoOperator = this.incrementor;
        return chronoOperator != null ? chronoOperator : super.incremented();
    }
}

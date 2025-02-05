package net.time4j;

import java.lang.Comparable;
import net.time4j.engine.ChronoFunction;
import net.time4j.format.DisplayElement;
import net.time4j.tz.TZID;
import net.time4j.tz.Timezone;
import net.time4j.tz.ZonalOffset;

/* loaded from: classes3.dex */
abstract class AbstractDateElement<V extends Comparable<V>> extends DisplayElement<V> implements AdjustableElement<V, PlainDate> {
    private final transient ElementOperator<PlainDate> maximizer;
    private final transient ElementOperator<PlainDate> minimizer;

    AbstractDateElement(String str) {
        super(str);
        this.minimizer = new DateOperator(this, 0);
        this.maximizer = new DateOperator(this, 1);
    }

    @Override // net.time4j.AdjustableElement
    public ElementOperator<PlainDate> newValue(V v) {
        return new DateOperator(this, -1, v);
    }

    @Override // net.time4j.AdjustableElement
    public ElementOperator<PlainDate> minimized() {
        return this.minimizer;
    }

    @Override // net.time4j.AdjustableElement
    public ElementOperator<PlainDate> maximized() {
        return this.maximizer;
    }

    @Override // net.time4j.AdjustableElement
    public ElementOperator<PlainDate> decremented() {
        return new DateOperator(this, 2);
    }

    @Override // net.time4j.AdjustableElement
    public ElementOperator<PlainDate> incremented() {
        return new DateOperator(this, 3);
    }

    @Override // net.time4j.AdjustableElement
    public ElementOperator<PlainDate> atFloor() {
        return new DateOperator(this, 4);
    }

    @Override // net.time4j.AdjustableElement
    public ElementOperator<PlainDate> atCeiling() {
        return new DateOperator(this, 5);
    }

    public ElementOperator<PlainDate> setLenient(V v) {
        return new DateOperator(this, 6, v);
    }

    @Override // net.time4j.ZonalElement
    public ChronoFunction<Moment, V> inStdTimezone() {
        return in(Timezone.ofSystem());
    }

    @Override // net.time4j.ZonalElement
    public ChronoFunction<Moment, V> inTimezone(TZID tzid) {
        return in(Timezone.of(tzid));
    }

    @Override // net.time4j.ZonalElement
    public ChronoFunction<Moment, V> in(Timezone timezone) {
        return new ZonalQuery(this, timezone);
    }

    @Override // net.time4j.ZonalElement
    public ChronoFunction<Moment, V> atUTC() {
        return at(ZonalOffset.UTC);
    }

    @Override // net.time4j.ZonalElement
    public ChronoFunction<Moment, V> at(ZonalOffset zonalOffset) {
        return new ZonalQuery(this, zonalOffset);
    }
}

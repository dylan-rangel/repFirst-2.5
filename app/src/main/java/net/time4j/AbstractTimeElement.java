package net.time4j;

import java.lang.Comparable;
import net.time4j.engine.ChronoFunction;
import net.time4j.format.DisplayElement;
import net.time4j.tz.TZID;
import net.time4j.tz.Timezone;
import net.time4j.tz.ZonalOffset;

/* loaded from: classes3.dex */
abstract class AbstractTimeElement<V extends Comparable<V>> extends DisplayElement<V> implements AdjustableElement<V, PlainTime> {
    private final transient ElementOperator<PlainTime> maximizer;
    private final transient ElementOperator<PlainTime> minimizer;

    AbstractTimeElement(String str) {
        super(str);
        this.minimizer = new TimeOperator(this, 0);
        this.maximizer = new TimeOperator(this, 1);
    }

    @Override // net.time4j.AdjustableElement
    public ElementOperator<PlainTime> newValue(V v) {
        return new TimeOperator(this, -1, v);
    }

    @Override // net.time4j.AdjustableElement
    public ElementOperator<PlainTime> minimized() {
        return this.minimizer;
    }

    @Override // net.time4j.AdjustableElement
    public ElementOperator<PlainTime> maximized() {
        return this.maximizer;
    }

    @Override // net.time4j.AdjustableElement
    public ElementOperator<PlainTime> decremented() {
        return new TimeOperator(this, 2);
    }

    @Override // net.time4j.AdjustableElement
    public ElementOperator<PlainTime> incremented() {
        return new TimeOperator(this, 3);
    }

    @Override // net.time4j.AdjustableElement
    public ElementOperator<PlainTime> atFloor() {
        return new TimeOperator(this, 4);
    }

    @Override // net.time4j.AdjustableElement
    public ElementOperator<PlainTime> atCeiling() {
        return new TimeOperator(this, 5);
    }

    public ElementOperator<PlainTime> setLenient(V v) {
        return new TimeOperator(this, 6, v);
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

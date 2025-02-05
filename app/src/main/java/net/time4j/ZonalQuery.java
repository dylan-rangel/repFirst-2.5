package net.time4j;

import net.time4j.engine.ChronoElement;
import net.time4j.engine.ChronoFunction;
import net.time4j.tz.Timezone;
import net.time4j.tz.ZonalOffset;

/* loaded from: classes3.dex */
class ZonalQuery<V> implements ChronoFunction<Moment, V> {
    private final ChronoElement<V> element;
    private final ZonalOffset offset;
    private final Timezone tz;

    ZonalQuery(ChronoElement<V> chronoElement, Timezone timezone) {
        if (timezone == null) {
            throw new NullPointerException("Missing timezone.");
        }
        this.element = chronoElement;
        this.tz = timezone;
        this.offset = null;
    }

    ZonalQuery(ChronoElement<V> chronoElement, ZonalOffset zonalOffset) {
        if (zonalOffset == null) {
            throw new NullPointerException("Missing timezone offset.");
        }
        this.element = chronoElement;
        this.tz = null;
        this.offset = zonalOffset;
    }

    @Override // net.time4j.engine.ChronoFunction
    public V apply(Moment moment) {
        ZonalOffset zonalOffset = this.offset;
        if (zonalOffset == null) {
            zonalOffset = this.tz.getOffset(moment);
        }
        if (this.element == PlainTime.SECOND_OF_MINUTE && moment.isLeapSecond() && zonalOffset.getFractionalAmount() == 0 && zonalOffset.getAbsoluteSeconds() % 60 == 0) {
            return this.element.getType().cast(60);
        }
        return (V) PlainTimestamp.from(moment, zonalOffset).get(this.element);
    }
}

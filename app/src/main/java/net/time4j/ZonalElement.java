package net.time4j;

import net.time4j.engine.ChronoElement;
import net.time4j.engine.ChronoFunction;
import net.time4j.tz.TZID;
import net.time4j.tz.Timezone;
import net.time4j.tz.ZonalOffset;

/* loaded from: classes3.dex */
public interface ZonalElement<V> extends ChronoElement<V> {
    ChronoFunction<Moment, V> at(ZonalOffset zonalOffset);

    ChronoFunction<Moment, V> atUTC();

    ChronoFunction<Moment, V> in(Timezone timezone);

    ChronoFunction<Moment, V> inStdTimezone();

    ChronoFunction<Moment, V> inTimezone(TZID tzid);
}

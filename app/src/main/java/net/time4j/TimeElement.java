package net.time4j;

import java.io.ObjectStreamException;
import net.time4j.engine.BasicElement;
import net.time4j.engine.ChronoFunction;
import net.time4j.tz.TZID;
import net.time4j.tz.Timezone;
import net.time4j.tz.ZonalOffset;

/* loaded from: classes3.dex */
final class TimeElement extends BasicElement<PlainTime> implements WallTimeElement {
    static final TimeElement INSTANCE = new TimeElement();
    private static final long serialVersionUID = -3712256393866098916L;

    @Override // net.time4j.engine.ChronoElement
    public boolean isDateElement() {
        return false;
    }

    @Override // net.time4j.engine.BasicElement
    protected boolean isSingleton() {
        return true;
    }

    @Override // net.time4j.engine.ChronoElement
    public boolean isTimeElement() {
        return true;
    }

    private TimeElement() {
        super("WALL_TIME");
    }

    @Override // net.time4j.engine.ChronoElement
    public Class<PlainTime> getType() {
        return PlainTime.class;
    }

    @Override // net.time4j.engine.ChronoElement
    public PlainTime getDefaultMinimum() {
        return PlainTime.MIN;
    }

    @Override // net.time4j.engine.ChronoElement
    public PlainTime getDefaultMaximum() {
        return PlainTime.of(23, 59, 59, 999999999);
    }

    @Override // net.time4j.WallTimeElement
    public ElementOperator<?> setToNext(PlainTime plainTime) {
        return new WallTimeOperator(9, plainTime);
    }

    @Override // net.time4j.WallTimeElement
    public ElementOperator<?> setToPrevious(PlainTime plainTime) {
        return new WallTimeOperator(10, plainTime);
    }

    @Override // net.time4j.WallTimeElement
    public ElementOperator<?> setToNextOrSame(PlainTime plainTime) {
        return new WallTimeOperator(11, plainTime);
    }

    @Override // net.time4j.WallTimeElement
    public ElementOperator<?> setToPreviousOrSame(PlainTime plainTime) {
        return new WallTimeOperator(12, plainTime);
    }

    @Override // net.time4j.WallTimeElement
    public ElementOperator<PlainTime> roundedToFullHour() {
        return FullValueOperator.ROUNDING_FULL_HOUR;
    }

    @Override // net.time4j.WallTimeElement
    public ElementOperator<PlainTime> roundedToFullMinute() {
        return FullValueOperator.ROUNDING_FULL_MINUTE;
    }

    @Override // net.time4j.WallTimeElement
    public ElementOperator<PlainTime> setToNextFullHour() {
        return FullValueOperator.NEXT_FULL_HOUR;
    }

    @Override // net.time4j.WallTimeElement
    public ElementOperator<PlainTime> setToNextFullMinute() {
        return FullValueOperator.NEXT_FULL_MINUTE;
    }

    @Override // net.time4j.ZonalElement
    public ChronoFunction<Moment, PlainTime> inStdTimezone() {
        return in(Timezone.ofSystem());
    }

    @Override // net.time4j.ZonalElement
    public ChronoFunction<Moment, PlainTime> inTimezone(TZID tzid) {
        return in(Timezone.of(tzid));
    }

    @Override // net.time4j.ZonalElement
    public ChronoFunction<Moment, PlainTime> in(Timezone timezone) {
        return new ZonalQuery(this, timezone);
    }

    @Override // net.time4j.ZonalElement
    public ChronoFunction<Moment, PlainTime> atUTC() {
        return at(ZonalOffset.UTC);
    }

    @Override // net.time4j.ZonalElement
    public ChronoFunction<Moment, PlainTime> at(ZonalOffset zonalOffset) {
        return new ZonalQuery(this, zonalOffset);
    }

    private Object readResolve() throws ObjectStreamException {
        return INSTANCE;
    }
}

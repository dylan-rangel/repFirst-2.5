package net.time4j;

import java.io.ObjectStreamException;
import net.time4j.engine.BasicElement;
import net.time4j.engine.ChronoFunction;
import net.time4j.tz.TZID;
import net.time4j.tz.Timezone;
import net.time4j.tz.ZonalOffset;

/* loaded from: classes3.dex */
final class DateElement extends BasicElement<PlainDate> implements CalendarDateElement {
    static final DateElement INSTANCE = new DateElement();
    private static final long serialVersionUID = -6519899440006935829L;

    @Override // net.time4j.engine.ChronoElement
    public boolean isDateElement() {
        return true;
    }

    @Override // net.time4j.engine.BasicElement
    protected boolean isSingleton() {
        return true;
    }

    @Override // net.time4j.engine.ChronoElement
    public boolean isTimeElement() {
        return false;
    }

    private DateElement() {
        super("CALENDAR_DATE");
    }

    @Override // net.time4j.engine.ChronoElement
    public Class<PlainDate> getType() {
        return PlainDate.class;
    }

    @Override // net.time4j.engine.ChronoElement
    public PlainDate getDefaultMinimum() {
        return PlainDate.MIN;
    }

    @Override // net.time4j.engine.ChronoElement
    public PlainDate getDefaultMaximum() {
        return PlainDate.MAX;
    }

    @Override // net.time4j.CalendarDateElement
    public ElementOperator<PlainDate> firstDayOfNextMonth() {
        return CalendarOperator.FIRST_DAY_OF_NEXT_MONTH;
    }

    @Override // net.time4j.CalendarDateElement
    public ElementOperator<PlainDate> firstDayOfNextQuarter() {
        return CalendarOperator.FIRST_DAY_OF_NEXT_QUARTER;
    }

    @Override // net.time4j.CalendarDateElement
    public ElementOperator<PlainDate> firstDayOfNextYear() {
        return CalendarOperator.FIRST_DAY_OF_NEXT_YEAR;
    }

    @Override // net.time4j.CalendarDateElement
    public ElementOperator<PlainDate> lastDayOfPreviousMonth() {
        return CalendarOperator.LAST_DAY_OF_PREVIOUS_MONTH;
    }

    @Override // net.time4j.CalendarDateElement
    public ElementOperator<PlainDate> lastDayOfPreviousQuarter() {
        return CalendarOperator.LAST_DAY_OF_PREVIOUS_QUARTER;
    }

    @Override // net.time4j.CalendarDateElement
    public ElementOperator<PlainDate> lastDayOfPreviousYear() {
        return CalendarOperator.LAST_DAY_OF_PREVIOUS_YEAR;
    }

    @Override // net.time4j.ZonalElement
    public ChronoFunction<Moment, PlainDate> inStdTimezone() {
        return in(Timezone.ofSystem());
    }

    @Override // net.time4j.ZonalElement
    public ChronoFunction<Moment, PlainDate> inTimezone(TZID tzid) {
        return in(Timezone.of(tzid));
    }

    @Override // net.time4j.ZonalElement
    public ChronoFunction<Moment, PlainDate> in(Timezone timezone) {
        return new ZonalQuery(this, timezone);
    }

    @Override // net.time4j.ZonalElement
    public ChronoFunction<Moment, PlainDate> atUTC() {
        return at(ZonalOffset.UTC);
    }

    @Override // net.time4j.ZonalElement
    public ChronoFunction<Moment, PlainDate> at(ZonalOffset zonalOffset) {
        return new ZonalQuery(this, zonalOffset);
    }

    private Object readResolve() throws ObjectStreamException {
        return INSTANCE;
    }
}

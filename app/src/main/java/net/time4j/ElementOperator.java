package net.time4j;

import net.time4j.Moment;
import net.time4j.engine.ChronoElement;
import net.time4j.engine.ChronoOperator;
import net.time4j.tz.TZID;
import net.time4j.tz.Timezone;
import net.time4j.tz.ZonalOffset;

/* loaded from: classes3.dex */
public abstract class ElementOperator<T> implements ChronoOperator<T> {
    static final int OP_CEILING = 5;
    static final int OP_DECREMENT = 2;
    static final int OP_FIRST_DAY_OF_NEXT_MONTH = 17;
    static final int OP_FIRST_DAY_OF_NEXT_QUARTER = 18;
    static final int OP_FIRST_DAY_OF_NEXT_YEAR = 19;
    static final int OP_FLOOR = 4;
    static final int OP_INCREMENT = 3;
    static final int OP_LAST_DAY_OF_PREVIOUS_MONTH = 20;
    static final int OP_LAST_DAY_OF_PREVIOUS_QUARTER = 21;
    static final int OP_LAST_DAY_OF_PREVIOUS_YEAR = 22;
    static final int OP_LENIENT = 6;
    static final int OP_MAXIMIZE = 1;
    static final int OP_MINIMIZE = 0;
    static final int OP_NAV_NEXT = 9;
    static final int OP_NAV_NEXT_OR_SAME = 11;
    static final int OP_NAV_PREVIOUS = 10;
    static final int OP_NAV_PREVIOUS_OR_SAME = 12;
    static final int OP_NEW_VALUE = -1;
    static final int OP_NEXT_FULL_HOUR = 15;
    static final int OP_NEXT_FULL_MINUTE = 16;
    static final int OP_ROUND_FULL_HOUR = 13;
    static final int OP_ROUND_FULL_MINUTE = 14;
    static final int OP_WIM = 7;
    static final int OP_YOW = 8;
    private final ChronoElement<?> element;
    private final int type;

    abstract ChronoOperator<PlainTimestamp> onTimestamp();

    ElementOperator(ChronoElement<?> chronoElement, int i) {
        this.element = chronoElement;
        this.type = i;
    }

    public ChronoOperator<Moment> inStdTimezone() {
        return new Moment.Operator(onTimestamp(), this.element, this.type);
    }

    public final ChronoOperator<Moment> inTimezone(TZID tzid) {
        return new Moment.Operator(onTimestamp(), this.element, this.type, Timezone.of(tzid));
    }

    public final ChronoOperator<Moment> in(Timezone timezone) {
        if (timezone == null) {
            throw new NullPointerException("Missing timezone.");
        }
        return new Moment.Operator(onTimestamp(), this.element, this.type, timezone);
    }

    public final ChronoOperator<Moment> atUTC() {
        return at(ZonalOffset.UTC);
    }

    public final ChronoOperator<Moment> at(ZonalOffset zonalOffset) {
        return new Moment.Operator(onTimestamp(), this.element, this.type, Timezone.of(zonalOffset));
    }

    ChronoElement<?> getElement() {
        return this.element;
    }

    int getType() {
        return this.type;
    }
}

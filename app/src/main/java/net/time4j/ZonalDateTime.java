package net.time4j;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.text.ParseException;
import net.time4j.engine.ChronoDisplay;
import net.time4j.engine.ChronoElement;
import net.time4j.engine.ChronoException;
import net.time4j.format.Attributes;
import net.time4j.format.RawValues;
import net.time4j.format.TemporalFormatter;
import net.time4j.scale.TimeScale;
import net.time4j.scale.UniversalTime;
import net.time4j.tz.TZID;
import net.time4j.tz.Timezone;
import net.time4j.tz.ZonalOffset;

/* loaded from: classes3.dex */
public final class ZonalDateTime implements ChronoDisplay, UniversalTime {
    private final Moment moment;
    private final transient PlainTimestamp timestamp;
    private final Timezone zone;

    @Override // net.time4j.engine.ChronoDisplay
    public boolean hasTimezone() {
        return true;
    }

    private ZonalDateTime(Moment moment, Timezone timezone) {
        this.zone = timezone;
        ZonalOffset offset = timezone.getOffset(moment);
        if (moment.isLeapSecond() && (offset.getFractionalAmount() != 0 || offset.getAbsoluteSeconds() % 60 != 0)) {
            throw new IllegalArgumentException("Leap second can only be represented  with timezone-offset in full minutes: " + offset);
        }
        this.moment = moment;
        this.timestamp = PlainTimestamp.from(moment, offset);
    }

    private ZonalDateTime(PlainTimestamp plainTimestamp, ZonalOffset zonalOffset) {
        this.moment = plainTimestamp.at(zonalOffset);
        this.zone = Timezone.of(zonalOffset);
        this.timestamp = plainTimestamp;
    }

    static ZonalDateTime of(Moment moment, Timezone timezone) {
        return new ZonalDateTime(moment, timezone);
    }

    static ZonalDateTime of(PlainTimestamp plainTimestamp, ZonalOffset zonalOffset) {
        return new ZonalDateTime(plainTimestamp, zonalOffset);
    }

    public int compareByMoment(ZonalDateTime zonalDateTime) {
        int compareTo = this.moment.compareTo(zonalDateTime.moment);
        return compareTo == 0 ? this.timestamp.compareTo(zonalDateTime.timestamp) : compareTo;
    }

    public int compareByLocalTimestamp(ZonalDateTime zonalDateTime) {
        int compareTo = this.timestamp.compareTo(zonalDateTime.timestamp);
        return compareTo == 0 ? this.moment.compareTo(zonalDateTime.moment) : compareTo;
    }

    @Override // net.time4j.engine.ChronoDisplay
    public boolean contains(ChronoElement<?> chronoElement) {
        return this.timestamp.contains(chronoElement) || this.moment.contains(chronoElement);
    }

    @Override // net.time4j.engine.ChronoDisplay
    public <V> V get(ChronoElement<V> chronoElement) {
        if (this.moment.isLeapSecond() && chronoElement == PlainTime.SECOND_OF_MINUTE) {
            return chronoElement.getType().cast(60);
        }
        if (this.timestamp.contains(chronoElement)) {
            return (V) this.timestamp.get(chronoElement);
        }
        return (V) this.moment.get(chronoElement);
    }

    @Override // net.time4j.engine.ChronoDisplay
    public int getInt(ChronoElement<Integer> chronoElement) {
        if (this.moment.isLeapSecond() && chronoElement == PlainTime.SECOND_OF_MINUTE) {
            return 60;
        }
        int i = this.timestamp.getInt(chronoElement);
        return i == Integer.MIN_VALUE ? this.moment.getInt(chronoElement) : i;
    }

    @Override // net.time4j.engine.ChronoDisplay
    public <V> V getMinimum(ChronoElement<V> chronoElement) {
        if (this.timestamp.contains(chronoElement)) {
            return (V) this.timestamp.getMinimum(chronoElement);
        }
        return (V) this.moment.getMinimum(chronoElement);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.time4j.engine.ChronoDisplay
    public <V> V getMaximum(ChronoElement<V> chronoElement) {
        V v;
        if (this.timestamp.contains(chronoElement)) {
            v = (V) this.timestamp.getMaximum(chronoElement);
        } else {
            v = (V) this.moment.getMaximum(chronoElement);
        }
        if (chronoElement == PlainTime.SECOND_OF_MINUTE && this.timestamp.getYear() >= 1972) {
            PlainTimestamp plainTimestamp = (PlainTimestamp) this.timestamp.with((ChronoElement<ChronoElement<V>>) chronoElement, (ChronoElement<V>) v);
            if (!this.zone.isInvalid(plainTimestamp, plainTimestamp) && plainTimestamp.in(this.zone).plus(1L, SI.SECONDS).isLeapSecond()) {
                return chronoElement.getType().cast(60);
            }
        }
        return v;
    }

    @Override // net.time4j.engine.ChronoDisplay
    public TZID getTimezone() {
        return this.zone.getID();
    }

    public ZonalOffset getOffset() {
        return this.zone.getOffset(this.moment);
    }

    public Moment toMoment() {
        return this.moment;
    }

    public PlainTimestamp toTimestamp() {
        return this.timestamp;
    }

    @Override // net.time4j.scale.UniversalTime
    public long getElapsedTime(TimeScale timeScale) {
        return this.moment.getElapsedTime(timeScale);
    }

    @Override // net.time4j.scale.UniversalTime
    public int getNanosecond(TimeScale timeScale) {
        return this.moment.getNanosecond(timeScale);
    }

    @Override // net.time4j.scale.UniversalTime
    public boolean isLeapSecond() {
        return this.moment.isLeapSecond();
    }

    @Override // net.time4j.base.UnixTime
    public long getPosixTime() {
        return this.moment.getPosixTime();
    }

    @Override // net.time4j.base.UnixTime
    public int getNanosecond() {
        return this.moment.getNanosecond();
    }

    public String print(TemporalFormatter<Moment> temporalFormatter) {
        return temporalFormatter.withTimezone(getTimezone()).print(this.moment);
    }

    public static ZonalDateTime parse(String str, TemporalFormatter<Moment> temporalFormatter) {
        Timezone timezone;
        try {
            RawValues rawValues = new RawValues();
            Moment parse = temporalFormatter.parse(str, rawValues);
            if (rawValues.get().hasTimezone()) {
                timezone = toTimezone(rawValues.get().getTimezone(), str);
            } else if (temporalFormatter.getAttributes().contains(Attributes.TIMEZONE_ID)) {
                timezone = toTimezone((TZID) temporalFormatter.getAttributes().get(Attributes.TIMEZONE_ID), str);
            } else {
                throw new ChronoException("Missing timezone: " + str);
            }
            return of(parse, timezone);
        } catch (ParseException e) {
            throw new ChronoException(e.getMessage(), e);
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ZonalDateTime)) {
            return false;
        }
        ZonalDateTime zonalDateTime = (ZonalDateTime) obj;
        return this.moment.equals(zonalDateTime.moment) && this.zone.equals(zonalDateTime.zone);
    }

    public int hashCode() {
        return this.moment.hashCode() ^ this.zone.hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(40);
        sb.append(this.timestamp.getCalendarDate());
        sb.append('T');
        int hour = this.timestamp.getHour();
        if (hour < 10) {
            sb.append('0');
        }
        sb.append(hour);
        sb.append(':');
        int minute = this.timestamp.getMinute();
        if (minute < 10) {
            sb.append('0');
        }
        sb.append(minute);
        sb.append(':');
        if (isLeapSecond()) {
            sb.append("60");
        } else {
            int second = this.timestamp.getSecond();
            if (second < 10) {
                sb.append('0');
            }
            sb.append(second);
        }
        int nanosecond = this.timestamp.getNanosecond();
        if (nanosecond != 0) {
            PlainTime.printNanos(sb, nanosecond);
        }
        sb.append(getOffset());
        TZID timezone = getTimezone();
        if (!(timezone instanceof ZonalOffset)) {
            sb.append('[');
            sb.append(timezone.canonical());
            sb.append(']');
        }
        return sb.toString();
    }

    public void write(ObjectOutput objectOutput) throws IOException {
        objectOutput.writeObject(this.moment);
        objectOutput.writeObject(this.zone);
    }

    public static ZonalDateTime read(ObjectInput objectInput) throws IOException, ClassNotFoundException {
        return new ZonalDateTime((Moment) objectInput.readObject(), (Timezone) objectInput.readObject());
    }

    Timezone getTimezone0() {
        return this.zone;
    }

    private static Timezone toTimezone(TZID tzid, String str) {
        try {
            return Timezone.of(tzid);
        } catch (IllegalArgumentException e) {
            throw new ChronoException("Timezone error: " + str, e);
        }
    }
}

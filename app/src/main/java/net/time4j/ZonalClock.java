package net.time4j;

import net.time4j.base.TimeSource;
import net.time4j.engine.CalendarFamily;
import net.time4j.engine.CalendarVariant;
import net.time4j.engine.ChronoEntity;
import net.time4j.engine.Chronology;
import net.time4j.engine.StartOfDay;
import net.time4j.engine.VariantSource;
import net.time4j.format.Attributes;
import net.time4j.tz.TZID;
import net.time4j.tz.Timezone;

/* loaded from: classes3.dex */
public final class ZonalClock {
    private static final ZonalClock SYSTEM = new ZonalClock();
    private final TimeSource<?> timeSource;
    private final Timezone timezone;

    public ZonalClock(TimeSource<?> timeSource, TZID tzid) {
        this(timeSource, Timezone.of(tzid));
    }

    public ZonalClock(TimeSource<?> timeSource, String str) {
        this(timeSource, Timezone.of(str));
    }

    public ZonalClock(TimeSource<?> timeSource, Timezone timezone) {
        if (timeSource == null) {
            throw new NullPointerException("Missing time source.");
        }
        if (timezone == null) {
            throw new NullPointerException("Missing timezone.");
        }
        this.timeSource = timeSource;
        this.timezone = timezone;
    }

    private ZonalClock() {
        this.timeSource = SystemClock.INSTANCE;
        this.timezone = null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [net.time4j.base.UnixTime] */
    public PlainDate today() {
        ?? currentTime = this.timeSource.currentTime();
        Timezone timezone = this.timezone;
        Timezone timezone2 = timezone;
        if (timezone == null) {
            timezone2 = Timezone.ofSystem();
        }
        return PlainDate.from(currentTime, timezone2.getOffset(currentTime));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [net.time4j.base.UnixTime] */
    public PlainTimestamp now() {
        ?? currentTime = this.timeSource.currentTime();
        Timezone timezone = this.timezone;
        Timezone timezone2 = timezone;
        if (timezone == null) {
            timezone2 = Timezone.ofSystem();
        }
        return PlainTimestamp.from(currentTime, timezone2.getOffset(currentTime));
    }

    public <T extends ChronoEntity<T>> T now(Chronology<T> chronology) {
        Timezone timezone = this.timezone;
        if (timezone == null) {
            timezone = Timezone.ofSystem();
        }
        T createFrom = chronology.createFrom(this.timeSource, new Attributes.Builder().setTimezone(timezone.getID()).build());
        if (createFrom != null) {
            return createFrom;
        }
        Class<T> chronoType = chronology.getChronoType();
        if (CalendarVariant.class.isAssignableFrom(chronoType)) {
            throw new IllegalArgumentException("Calendar variant required: " + chronoType.getName());
        }
        throw new IllegalArgumentException("Insufficient data: " + chronoType.getName());
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [net.time4j.base.UnixTime] */
    public <C extends CalendarVariant<C>> GeneralTimestamp<C> now(CalendarFamily<C> calendarFamily, String str, StartOfDay startOfDay) {
        Timezone timezone = this.timezone;
        if (timezone == null) {
            timezone = Timezone.ofSystem();
        }
        return Moment.from(this.timeSource.currentTime()).toGeneralTimestamp(calendarFamily, str, timezone.getID(), startOfDay);
    }

    public <C extends CalendarVariant<C>> GeneralTimestamp<C> now(CalendarFamily<C> calendarFamily, VariantSource variantSource, StartOfDay startOfDay) {
        return now(calendarFamily, variantSource.getVariant(), startOfDay);
    }

    public TimeSource<?> getSource() {
        return this.timeSource;
    }

    public TZID getTimezone() {
        Timezone timezone = this.timezone;
        if (timezone == null) {
            timezone = Timezone.ofSystem();
        }
        return timezone.getID();
    }

    static ZonalClock ofSystem() {
        return SYSTEM;
    }
}

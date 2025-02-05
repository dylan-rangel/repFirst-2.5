package net.time4j.engine;

import net.time4j.base.MathUtils;
import net.time4j.base.UnixTime;
import net.time4j.tz.TZID;
import net.time4j.tz.Timezone;

/* loaded from: classes3.dex */
public abstract class StartOfDay {
    public static final StartOfDay MIDNIGHT = fixed(0);
    public static final StartOfDay EVENING = fixed(-21600);
    public static final StartOfDay MORNING = fixed(21600);

    public abstract int getDeviation(CalendarDate calendarDate, TZID tzid);

    protected StartOfDay() {
    }

    public static StartOfDay ofFixedDeviation(int i) {
        if (i == 0) {
            return MIDNIGHT;
        }
        if (i == -21600) {
            return EVENING;
        }
        if (i > 43200 || i <= -43200) {
            throw new IllegalArgumentException("Start of day out of range: " + i);
        }
        return new FixedStartOfDay(i);
    }

    public static <T extends UnixTime> StartOfDay definedBy(ChronoFunction<CalendarDate, T> chronoFunction) {
        return new FunctionalStartOfDay(chronoFunction);
    }

    private static StartOfDay fixed(int i) {
        return new FixedStartOfDay(i);
    }

    private static class FixedStartOfDay extends StartOfDay {
        private final int deviation;

        private FixedStartOfDay(int i) {
            this.deviation = i;
        }

        @Override // net.time4j.engine.StartOfDay
        public int getDeviation(CalendarDate calendarDate, TZID tzid) {
            return this.deviation;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof FixedStartOfDay) && this.deviation == ((FixedStartOfDay) obj).deviation;
        }

        public int hashCode() {
            return this.deviation;
        }

        public String toString() {
            return "FixedStartOfDay[" + this.deviation + "]";
        }
    }

    private static class FunctionalStartOfDay<T extends UnixTime> extends StartOfDay {
        private final ChronoFunction<CalendarDate, T> event;

        private FunctionalStartOfDay(ChronoFunction<CalendarDate, T> chronoFunction) {
            if (chronoFunction == null) {
                throw new NullPointerException("Missing event function.");
            }
            this.event = chronoFunction;
        }

        @Override // net.time4j.engine.StartOfDay
        public int getDeviation(CalendarDate calendarDate, TZID tzid) {
            T apply = this.event.apply(calendarDate);
            if (apply != null) {
                int safeCast = MathUtils.safeCast(((apply.getPosixTime() - 63072000) + Timezone.of(tzid).getOffset(apply).getIntegralAmount()) - (calendarDate.getDaysSinceEpochUTC() * 86400));
                return safeCast >= 43200 ? safeCast - 86400 : safeCast;
            }
            throw new ChronoException("Cannot determine start of day: No event.");
        }
    }
}

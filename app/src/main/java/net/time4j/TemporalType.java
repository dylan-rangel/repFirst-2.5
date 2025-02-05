package net.time4j;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import net.time4j.base.MathUtils;
import net.time4j.engine.Converter;
import net.time4j.scale.TimeScale;
import net.time4j.tz.Timezone;

/* loaded from: classes3.dex */
public abstract class TemporalType<S, T> implements Converter<S, T> {
    public static final TemporalType<Calendar, ZonalDateTime> JAVA_UTIL_CALENDAR;
    public static final TemporalType<Date, Moment> JAVA_UTIL_DATE;
    public static final TemporalType<TimeZone, Timezone> JAVA_UTIL_TIMEZONE;
    private static final String JUT_PROVIDER = "java.util.TimeZone~";
    public static final TemporalType<Long, Moment> MILLIS_SINCE_UNIX;
    private static final int MIO = 1000000;

    @Override // net.time4j.engine.Converter
    public abstract S from(T t);

    @Override // net.time4j.engine.Converter
    public abstract T translate(S s);

    static {
        JAVA_UTIL_DATE = new JavaUtilDateRule();
        MILLIS_SINCE_UNIX = new MillisSinceUnixRule();
        JAVA_UTIL_CALENDAR = new CalendarRule();
        JAVA_UTIL_TIMEZONE = new ZoneRule();
    }

    protected TemporalType() {
    }

    private static class JavaUtilDateRule extends TemporalType<Date, Moment> {
        private JavaUtilDateRule() {
        }

        @Override // net.time4j.TemporalType, net.time4j.engine.Converter
        public Moment translate(Date date) {
            long time = date.getTime();
            return Moment.of(MathUtils.floorDivide(time, 1000), MathUtils.floorModulo(time, 1000) * 1000000, TimeScale.POSIX);
        }

        @Override // net.time4j.TemporalType, net.time4j.engine.Converter
        public Date from(Moment moment) {
            return new Date(MathUtils.safeAdd(MathUtils.safeMultiply(moment.getPosixTime(), 1000L), moment.getNanosecond() / 1000000));
        }

        @Override // net.time4j.engine.Converter
        public Class<Date> getSourceType() {
            return Date.class;
        }
    }

    private static class MillisSinceUnixRule extends TemporalType<Long, Moment> {
        private MillisSinceUnixRule() {
        }

        @Override // net.time4j.TemporalType, net.time4j.engine.Converter
        public Moment translate(Long l) {
            long longValue = l.longValue();
            return Moment.of(MathUtils.floorDivide(longValue, 1000), MathUtils.floorModulo(longValue, 1000) * 1000000, TimeScale.POSIX);
        }

        @Override // net.time4j.TemporalType, net.time4j.engine.Converter
        public Long from(Moment moment) {
            return Long.valueOf(MathUtils.safeAdd(MathUtils.safeMultiply(moment.getPosixTime(), 1000L), moment.getNanosecond() / 1000000));
        }

        @Override // net.time4j.engine.Converter
        public Class<Long> getSourceType() {
            return Long.class;
        }
    }

    private static class CalendarRule extends TemporalType<Calendar, ZonalDateTime> {
        private CalendarRule() {
        }

        @Override // net.time4j.TemporalType, net.time4j.engine.Converter
        public ZonalDateTime translate(Calendar calendar) {
            return ZonalDateTime.of(TemporalType.JAVA_UTIL_DATE.translate(calendar.getTime()), TemporalType.JAVA_UTIL_TIMEZONE.translate(calendar.getTimeZone()));
        }

        @Override // net.time4j.TemporalType, net.time4j.engine.Converter
        public Calendar from(ZonalDateTime zonalDateTime) {
            Date from = TemporalType.JAVA_UTIL_DATE.from(zonalDateTime.toMoment());
            TimeZone from2 = TemporalType.JAVA_UTIL_TIMEZONE.from(zonalDateTime.getTimezone0());
            GregorianCalendar gregorianCalendar = new GregorianCalendar();
            gregorianCalendar.setGregorianChange(new Date(Long.MIN_VALUE));
            gregorianCalendar.setFirstDayOfWeek(2);
            gregorianCalendar.setMinimalDaysInFirstWeek(4);
            gregorianCalendar.setTimeZone(from2);
            gregorianCalendar.setTime(from);
            return gregorianCalendar;
        }

        @Override // net.time4j.engine.Converter
        public Class<Calendar> getSourceType() {
            return Calendar.class;
        }
    }

    private static class ZoneRule extends TemporalType<TimeZone, Timezone> {
        private ZoneRule() {
        }

        @Override // net.time4j.TemporalType, net.time4j.engine.Converter
        public Timezone translate(TimeZone timeZone) {
            if (timeZone instanceof OldApiTimezone) {
                return ((OldApiTimezone) timeZone).getDelegate();
            }
            return Timezone.of(TemporalType.JUT_PROVIDER + timeZone.getID());
        }

        @Override // net.time4j.TemporalType, net.time4j.engine.Converter
        public TimeZone from(Timezone timezone) {
            if (timezone.getHistory() == null) {
                String canonical = timezone.getID().canonical();
                if (canonical.startsWith(TemporalType.JUT_PROVIDER)) {
                    canonical = canonical.substring(19);
                }
                return TimeZone.getTimeZone(canonical);
            }
            return new OldApiTimezone(timezone);
        }

        @Override // net.time4j.engine.Converter
        public Class<TimeZone> getSourceType() {
            return TimeZone.class;
        }
    }
}

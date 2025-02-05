package net.time4j.calendar.astro;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import net.time4j.Moment;
import net.time4j.PlainDate;
import net.time4j.PlainTime;
import net.time4j.base.MathUtils;
import net.time4j.engine.CalendarDate;
import net.time4j.engine.EpochDays;
import net.time4j.scale.LeapSeconds;
import net.time4j.scale.TimeScale;
import net.time4j.tz.ZonalOffset;

/* loaded from: classes3.dex */
public final class JulianDay implements Serializable {
    private static final int DAY_IN_SECONDS = 86400;
    public static final double MAX = 2817152.0d;
    public static final double MIN = 990575.0d;
    private static final int MRD = 1000000000;
    private static final long OFFSET_1970 = 210866760000L;
    private static final long OFFSET_1972 = 210929832000L;
    private static final long serialVersionUID = 486345450973062467L;
    private final TimeScale scale;
    private final double value;

    private JulianDay(double d, TimeScale timeScale) {
        check(d, timeScale);
        this.value = d;
        this.scale = timeScale;
    }

    public static JulianDay ofEphemerisTime(double d) {
        return new JulianDay(d, TimeScale.TT);
    }

    public static JulianDay ofEphemerisTime(Moment moment) {
        return new JulianDay(getValue(moment, TimeScale.TT), TimeScale.TT);
    }

    public static JulianDay ofEphemerisTime(CalendarDate calendarDate, PlainTime plainTime, ZonalOffset zonalOffset) {
        return new JulianDay(((EpochDays.JULIAN_DAY_NUMBER.transform(calendarDate.getDaysSinceEpochUTC(), EpochDays.UTC) - 0.5d) + (((Long) plainTime.get(PlainTime.NANO_OF_DAY)).longValue() / 8.64E13d)) - (zonalOffset.getIntegralAmount() / 86400.0d), TimeScale.TT);
    }

    public static JulianDay ofMeanSolarTime(double d) {
        return new JulianDay(d, TimeScale.UT);
    }

    public static JulianDay ofMeanSolarTime(Moment moment) {
        return new JulianDay(getValue(moment, TimeScale.UT), TimeScale.UT);
    }

    public static JulianDay ofSimplifiedTime(double d) {
        return new JulianDay(d, TimeScale.POSIX);
    }

    public static JulianDay ofSimplifiedTime(Moment moment) {
        return new JulianDay(getValue(moment, TimeScale.POSIX), TimeScale.POSIX);
    }

    public double getValue() {
        return this.value;
    }

    public double getMJD() {
        return this.value - 2400000.5d;
    }

    public double getCenturyJ2000() {
        return (this.value - 2451545.0d) / 36525.0d;
    }

    public TimeScale getScale() {
        return this.scale;
    }

    public JulianDay plusDays(double d) {
        return new JulianDay(this.value + d, this.scale);
    }

    public JulianDay minusDays(double d) {
        return new JulianDay(this.value - d, this.scale);
    }

    public JulianDay plusSeconds(double d) {
        return new JulianDay(this.value + (d / 86400.0d), this.scale);
    }

    public JulianDay minusSeconds(double d) {
        return new JulianDay(this.value - (d / 86400.0d), this.scale);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof JulianDay)) {
            return false;
        }
        JulianDay julianDay = (JulianDay) obj;
        return this.value == julianDay.value && this.scale == julianDay.scale;
    }

    public int hashCode() {
        return AstroUtils.hashCode(this.value) ^ this.scale.hashCode();
    }

    public String toString() {
        return "JD(" + this.scale.name() + ')' + this.value;
    }

    public Moment toMoment() {
        double d = this.value * 86400.0d;
        TimeScale timeScale = this.scale;
        if (!LeapSeconds.getInstance().isEnabled() && timeScale != TimeScale.POSIX) {
            if (timeScale == TimeScale.TT) {
                PlainDate of = PlainDate.of((long) Math.floor(getMJD()), EpochDays.MODIFIED_JULIAN_DATE);
                d -= TimeScale.deltaT(of.getYear(), of.getMonth());
            }
            d += 6.3072E7d;
            timeScale = TimeScale.POSIX;
        }
        return Moment.of(MathUtils.safeSubtract((long) d, jdOffset(timeScale)), (int) ((d - Math.floor(d)) * 1.0E9d), timeScale);
    }

    static double getValue(Moment moment, TimeScale timeScale) {
        return ((moment.getElapsedTime(timeScale) + jdOffset(timeScale)) + (moment.getNanosecond(timeScale) / 1.0E9d)) / 86400.0d;
    }

    /* renamed from: net.time4j.calendar.astro.JulianDay$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$time4j$scale$TimeScale;

        static {
            int[] iArr = new int[TimeScale.values().length];
            $SwitchMap$net$time4j$scale$TimeScale = iArr;
            try {
                iArr[TimeScale.UT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$net$time4j$scale$TimeScale[TimeScale.TT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$net$time4j$scale$TimeScale[TimeScale.POSIX.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    private static long jdOffset(TimeScale timeScale) {
        int i = AnonymousClass1.$SwitchMap$net$time4j$scale$TimeScale[timeScale.ordinal()];
        if (i == 1 || i == 2) {
            return OFFSET_1972;
        }
        if (i == 3) {
            return OFFSET_1970;
        }
        throw new UnsupportedOperationException(timeScale.name());
    }

    private static void check(double d, TimeScale timeScale) {
        if (Double.isNaN(d) || Double.isInfinite(d)) {
            throw new IllegalArgumentException("Value is not finite: " + d);
        }
        int i = AnonymousClass1.$SwitchMap$net$time4j$scale$TimeScale[timeScale.ordinal()];
        if (i == 1 || i == 2 || i == 3) {
            if (Double.compare(990575.0d, d) > 0 || Double.compare(d, 2817152.0d) > 0) {
                throw new IllegalArgumentException("Out of range: " + d);
            }
            return;
        }
        throw new IllegalArgumentException("Unsupported time scale: " + timeScale);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException {
        try {
            objectInputStream.defaultReadObject();
            check(this.value, this.scale);
        } catch (ClassNotFoundException unused) {
            throw new StreamCorruptedException();
        } catch (IllegalArgumentException unused2) {
            throw new StreamCorruptedException();
        }
    }
}

package net.time4j.calendar.astro;

import com.facebook.imagepipeline.common.RotationOptions;
import com.google.android.gms.auth.api.credentials.CredentialsApi;
import java.util.concurrent.TimeUnit;
import net.time4j.Moment;
import net.time4j.PlainTimestamp;
import net.time4j.base.MathUtils;
import net.time4j.engine.ChronoElement;
import net.time4j.scale.UniversalTime;

/* loaded from: classes3.dex */
public enum MoonPhase {
    NEW_MOON(0),
    FIRST_QUARTER(90),
    FULL_MOON(RotationOptions.ROTATE_180),
    LAST_QUARTER(RotationOptions.ROTATE_270);

    private static final double MEAN_SYNODIC_MONTH = 29.530588861d;
    private final transient int phase;
    private static final int[] FACTORS = {100, 1000, 10000, 100000};
    private static final Moment ZERO_REF = PlainTimestamp.of(CredentialsApi.CREDENTIAL_PICKER_REQUEST_CODE, 1, 6, 18, 13, 42).atUTC();
    private static final int[] W_NEW_FULL = {0, 1, 0, 0, 1, 1, 2, 0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private static final int[] W_QUARTER = {0, 1, 1, 0, 0, 1, 2, 0, 0, 0, 1, 1, 1, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private static final int[] X_NEW_FULL = {0, 1, 0, 0, -1, 1, 2, 0, 0, 1, 0, 1, 1, -1, 2, 0, 3, 1, 0, 1, -1, -1, 1, 0};
    private static final int[] X_QUARTER = {0, 1, 1, 0, 0, -1, 2, 0, 0, 0, -1, 1, 1, 2, 1, -1, 0, 1, -2, 1, 3, 0, -1, 1};
    private static final int[] Y_NEW_FULL = {1, 0, 2, 0, 1, 1, 0, 1, 1, 2, 3, 0, 0, 2, 1, 2, 0, 1, 2, 1, 1, 1, 3, 4};
    private static final int[] Y_QUARTER = {1, 0, 1, 2, 0, 1, 0, 1, 1, 3, 2, 0, 0, 1, 2, 1, 2, 1, 1, 1, 0, 2, 1, 3};
    private static final int[] Z_NEW_FULL = {0, 0, 0, 2, 0, 0, 0, -2, 2, 0, 0, 2, -2, 0, 0, -2, 0, -2, 2, 2, 2, -2, 0, 0};
    private static final int[] Z_QUARTER = {0, 0, 0, 0, 2, 0, 0, -2, 2, 0, 0, 2, -2, 0, 0, -2, 2, 2, 0, -2, 0, -2, 2, 0};
    private static final double[] V_NEW = {-0.4072d, 0.17241d, 0.01608d, 0.01039d, 0.00739d, -0.00514d, 0.00208d, -0.00111d, -5.7E-4d, 5.6E-4d, -4.2E-4d, 4.2E-4d, 3.8E-4d, -2.4E-4d, -7.0E-5d, 4.0E-5d, 4.0E-5d, 3.0E-5d, 3.0E-5d, -3.0E-5d, 3.0E-5d, -2.0E-5d, -2.0E-5d, 2.0E-5d};
    private static final double[] V_FULL = {-0.40614d, 0.17302d, 0.01614d, 0.01043d, 0.00734d, -0.00515d, 0.00209d, -0.00111d, -5.7E-4d, 5.6E-4d, -4.2E-4d, 4.2E-4d, 3.8E-4d, -2.4E-4d, -7.0E-5d, 4.0E-5d, 4.0E-5d, 3.0E-5d, 3.0E-5d, -3.0E-5d, 3.0E-5d, -2.0E-5d, -2.0E-5d, 2.0E-5d};
    private static final double[] V_QUARTER = {-0.62801d, 0.17172d, -0.01183d, 0.00862d, 0.00804d, 0.00454d, 0.00204d, -0.0018d, -7.0E-4d, -4.0E-4d, -3.4E-4d, 3.2E-4d, 3.2E-4d, -2.8E-4d, 2.7E-4d, -5.0E-5d, 4.0E-5d, -4.0E-5d, 4.0E-5d, 3.0E-5d, 3.0E-5d, 2.0E-5d, 2.0E-5d, -2.0E-5d};

    public static int maxLunation() {
        return 12379;
    }

    public static int minLunation() {
        return -49473;
    }

    MoonPhase(int i) {
        this.phase = i;
    }

    public Moment after(Moment moment) {
        int estimatedLunations = getEstimatedLunations(moment);
        Moment atLunation = atLunation(estimatedLunations);
        int i = estimatedLunations;
        while (!atLunation.isAfter((UniversalTime) moment)) {
            i++;
            atLunation = atLunation(i);
        }
        if (i <= estimatedLunations) {
            while (true) {
                i--;
                Moment atLunation2 = atLunation(i);
                if (!atLunation2.isAfter((UniversalTime) moment)) {
                    break;
                }
                atLunation = atLunation2;
            }
        }
        return atLunation;
    }

    public Moment atOrAfter(Moment moment) {
        int estimatedLunations = getEstimatedLunations(moment);
        Moment atLunation = atLunation(estimatedLunations);
        int i = estimatedLunations;
        while (atLunation.isBefore((UniversalTime) moment)) {
            i++;
            atLunation = atLunation(i);
        }
        if (i <= estimatedLunations) {
            while (true) {
                i--;
                Moment atLunation2 = atLunation(i);
                if (atLunation2.isBefore((UniversalTime) moment)) {
                    break;
                }
                atLunation = atLunation2;
            }
        }
        return atLunation;
    }

    public Moment before(Moment moment) {
        int estimatedLunations = getEstimatedLunations(moment);
        Moment atLunation = atLunation(estimatedLunations);
        int i = estimatedLunations;
        while (!atLunation.isBefore((UniversalTime) moment)) {
            i--;
            atLunation = atLunation(i);
        }
        if (i >= estimatedLunations) {
            while (atLunation.plus(29L, (long) TimeUnit.DAYS).isBefore((UniversalTime) moment)) {
                i++;
                Moment atLunation2 = atLunation(i);
                if (!atLunation2.isBefore((UniversalTime) moment)) {
                    break;
                }
                atLunation = atLunation2;
            }
        }
        return atLunation;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Moment atLunation(int i) {
        double d = i + (this.phase / 360.0d);
        double d2 = d / 1236.85d;
        double d3 = d2 * d2;
        double d4 = 1.0d - (((7.4E-6d * d2) + 0.002516d) * d2);
        double d5 = ((29.1053567d * d) + 2.5534d) - (((1.1E-7d * d2) + 1.4E-6d) * d3);
        double d6 = (385.81693528d * d) + 201.5643d + ((((1.238E-5d - (5.8E-8d * d2)) * d2) + 0.0107582d) * d3);
        double d7 = (390.67050284d * d) + 160.7108d + (((((1.1E-8d * d2) - 2.27E-6d) * d2) - 0.0016118d) * d3);
        double sin = ((((MEAN_SYNODIC_MONTH * d) + 2451550.09766d) + (((((7.3E-10d * d2) - 1.5E-7d) * d2) + 1.5437E-4d) * d3)) - (Math.sin(Math.toRadians((124.7746d - (1.56375588d * d)) + (((2.15E-6d * d2) + 0.0020672d) * d3))) * 1.7E-4d)) + periodic24(d4, d5, d6, d7);
        if (this == FIRST_QUARTER) {
            sin += corrQuarter(d4, d5, d6, d7);
        } else if (this == LAST_QUARTER) {
            sin -= corrQuarter(d4, d5, d6, d7);
        }
        double[] dArr = {((0.107408d * d) + 299.77d) - (d3 * 0.009173d), 3.25E-4d, (0.016321d * d) + 251.88d, 1.65E-4d, (26.651886d * d) + 251.83d, 1.64E-4d, (36.412478d * d) + 349.42d, 1.26E-4d, (18.206239d * d) + 84.66d, 1.1E-4d, (53.303771d * d) + 141.74d, 6.2E-5d, (2.453732d * d) + 207.14d, 6.0E-5d, (7.30686d * d) + 154.84d, 5.6E-5d, (27.261239d * d) + 34.52d, 4.7E-5d, (0.121824d * d) + 207.19d, 4.2E-5d, (1.844379d * d) + 291.34d, 4.0E-5d, (24.198154d * d) + 161.72d, 3.7E-5d, (25.513099d * d) + 239.56d, 3.5E-5d, (d * 3.592518d) + 331.55d, 2.3E-5d};
        for (int i2 = 0; i2 < 28; i2 += 2) {
            sin += dArr[i2 + 1] * Math.sin(Math.toRadians(dArr[i2]));
        }
        return (Moment) JulianDay.ofEphemerisTime(sin).toMoment().with((ChronoElement<ChronoElement<TimeUnit>>) Moment.PRECISION, (ChronoElement<TimeUnit>) TimeUnit.SECONDS);
    }

    public static double getIllumination(Moment moment) {
        return getIllumination(moment, 0);
    }

    public static double getIllumination(Moment moment, int i) {
        double centuryJ2000 = JulianDay.ofEphemerisTime(moment).getCenturyJ2000();
        double meanElongation = MoonPosition.getMeanElongation(centuryJ2000);
        double meanAnomalyOfSun = MoonPosition.getMeanAnomalyOfSun(centuryJ2000);
        double meanAnomalyOfMoon = MoonPosition.getMeanAnomalyOfMoon(centuryJ2000);
        double d = meanElongation * 2.0d;
        double cos = (cos(((((((180.0d - meanElongation) - (sin(meanAnomalyOfMoon) * 6.289d)) + (sin(meanAnomalyOfSun) * 2.1d)) - (sin(d - meanAnomalyOfMoon) * 1.274d)) - (sin(d) * 0.658d)) - (sin(meanAnomalyOfMoon * 2.0d) * 0.214d)) - (sin(meanElongation) * 0.11d)) + 1.0d) / 2.0d;
        double d2 = FACTORS[i];
        double d3 = cos * d2;
        if (d2 - d3 <= 0.5d) {
            return 1.0d;
        }
        return Math.floor(d3) / d2;
    }

    private int getEstimatedLunations(Moment moment) {
        return MathUtils.safeCast(Math.round((ZERO_REF.until(moment, (Moment) TimeUnit.DAYS) / MEAN_SYNODIC_MONTH) - (this.phase / 360.0d)));
    }

    private double periodic24(double d, double d2, double d3, double d4) {
        MoonPhase moonPhase = NEW_MOON;
        double[] dArr = this == moonPhase ? V_NEW : this == FULL_MOON ? V_FULL : V_QUARTER;
        int[] iArr = (this == moonPhase || this == FULL_MOON) ? W_NEW_FULL : W_QUARTER;
        int[] iArr2 = (this == moonPhase || this == FULL_MOON) ? X_NEW_FULL : X_QUARTER;
        int[] iArr3 = (this == moonPhase || this == FULL_MOON) ? Y_NEW_FULL : Y_QUARTER;
        int[] iArr4 = (this == moonPhase || this == FULL_MOON) ? Z_NEW_FULL : Z_QUARTER;
        double d5 = 0.0d;
        for (int i = 23; i >= 0; i--) {
            double d6 = dArr[i];
            int i2 = iArr[i];
            if (i2 != 1) {
                if (i2 == 2) {
                    d6 *= d;
                } else {
                    d5 += d6 * sin((iArr2[i] * d2) + (iArr3[i] * d3) + (iArr4[i] * d4));
                }
            }
            d6 *= d;
            d5 += d6 * sin((iArr2[i] * d2) + (iArr3[i] * d3) + (iArr4[i] * d4));
        }
        return d5;
    }

    private static double corrQuarter(double d, double d2, double d3, double d4) {
        return (((0.00306d - ((d * 3.8E-4d) * cos(d2))) + (cos(d3) * 2.6E-4d)) - (cos(d3 - d2) * 2.0E-5d)) + (cos(d3 + d2) * 2.0E-5d) + (cos(d4 * 2.0d) * 2.0E-5d);
    }

    private static double sin(double d) {
        return Math.sin((d * 3.141592653589793d) / 180.0d);
    }

    private static double cos(double d) {
        return Math.cos((d * 3.141592653589793d) / 180.0d);
    }
}

package net.time4j.calendar.hindu;

import io.sentry.protocol.SentryThread;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.StringTokenizer;
import net.time4j.Moment;
import net.time4j.PlainDate;
import net.time4j.calendar.IndianMonth;
import net.time4j.calendar.astro.GeoLocation;
import net.time4j.calendar.astro.JulianDay;
import net.time4j.calendar.astro.MoonPhase;
import net.time4j.calendar.astro.StdSolarCalculator;
import net.time4j.engine.EpochDays;
import net.time4j.engine.VariantSource;
import net.time4j.scale.TimeScale;

/* loaded from: classes3.dex */
public final class HinduVariant implements VariantSource, Serializable {
    private static final int TYPE_OLD_LUNAR = -2;
    private static final int TYPE_OLD_SOLAR = -1;
    private static final double U_OFFSET = 18184.4d;
    private final transient HinduEra defaultEra;
    private final transient double depressionAngle;
    private final transient boolean elapsedMode;
    private final transient GeoLocation location;
    private final transient int type;
    static final GeoLocation UJJAIN = new HinduLocation(23.15d, 75.76833333333333d, 0);
    private static final HinduRule[] RULES = HinduRule.values();
    static final HinduVariant VAR_OLD_SOLAR = new HinduVariant(AryaSiddhanta.SOLAR);
    static final HinduVariant VAR_OLD_LUNAR = new HinduVariant(AryaSiddhanta.LUNAR);

    private interface LongFunction {
        double apply(long j);
    }

    @Deprecated
    public HinduVariant withAlternativeHinduSunrise() {
        return this;
    }

    HinduVariant(HinduRule hinduRule, HinduEra hinduEra) {
        this(hinduRule.ordinal(), hinduEra, useStandardElapsedMode(hinduEra, hinduRule), Double.NaN, UJJAIN);
    }

    private HinduVariant(AryaSiddhanta aryaSiddhanta) {
        this(aryaSiddhanta == AryaSiddhanta.SOLAR ? -1 : -2, HinduEra.KALI_YUGA, true, Double.NaN, UJJAIN);
    }

    private HinduVariant(int i, HinduEra hinduEra, boolean z, double d, GeoLocation geoLocation) {
        if (i < -2 || i >= HinduRule.values().length) {
            throw new IllegalArgumentException("Undefined Hindu rule.");
        }
        if (hinduEra == null) {
            throw new NullPointerException("Missing default Hindu era.");
        }
        if (geoLocation == null) {
            throw new NullPointerException("Missing geographical location.");
        }
        if (Double.isInfinite(d)) {
            throw new IllegalArgumentException("Infinite depression angle.");
        }
        if (!Double.isNaN(d) && Math.abs(d) > 10.0d) {
            throw new IllegalArgumentException("Depression angle is too big: " + d);
        }
        this.type = i;
        this.defaultEra = hinduEra;
        this.elapsedMode = z;
        this.depressionAngle = d;
        this.location = geoLocation;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v6, types: [net.time4j.calendar.astro.GeoLocation] */
    public static HinduVariant from(String str) {
        if (str.startsWith("AryaSiddhanta@")) {
            try {
                return AryaSiddhanta.valueOf(str.substring(14)) == AryaSiddhanta.SOLAR ? VAR_OLD_SOLAR : VAR_OLD_LUNAR;
            } catch (IndexOutOfBoundsException e) {
                throw new IllegalArgumentException("Invalid variant: " + str, e);
            }
        }
        StringTokenizer stringTokenizer = new StringTokenizer(str, "|");
        GeoLocation geoLocation = UJJAIN;
        double latitude = geoLocation.getLatitude();
        double longitude = geoLocation.getLongitude();
        HinduEra hinduEra = null;
        double d = Double.NaN;
        double d2 = latitude;
        int i = 0;
        boolean z = true;
        int i2 = Integer.MIN_VALUE;
        boolean z2 = true;
        int altitude = geoLocation.getAltitude();
        double d3 = longitude;
        while (stringTokenizer.hasMoreTokens()) {
            i++;
            String nextToken = stringTokenizer.nextToken();
            switch (i) {
                case 1:
                    i2 = Integer.valueOf(nextToken).intValue();
                case 2:
                    hinduEra = HinduEra.valueOf(nextToken);
                case 3:
                    z2 = nextToken.equals("elapsed");
                case 4:
                    if (!nextToken.equals("oldstyle") && !nextToken.equals("alt") && !nextToken.equals("std")) {
                        d = Double.valueOf(nextToken).doubleValue();
                    }
                    break;
                case 5:
                    d2 = Double.valueOf(nextToken).doubleValue();
                    z = d2 == UJJAIN.getLatitude();
                case 6:
                    d3 = Double.valueOf(nextToken).doubleValue();
                    if (z && d3 == UJJAIN.getLongitude()) {
                    }
                    break;
                case 7:
                    altitude = Integer.valueOf(nextToken).intValue();
                    if (z && altitude == 0) {
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Invalid variant: " + str);
            }
        }
        if (i2 < 0) {
            throw new IllegalArgumentException("Invalid variant: " + str);
        }
        try {
            return new HinduVariant(i2, hinduEra, z2, d, z ? UJJAIN : new HinduLocation(d2, d3, altitude));
        } catch (Exception unused) {
            throw new IllegalArgumentException("Invalid variant: " + str);
        }
    }

    public HinduEra getDefaultEra() {
        return this.defaultEra;
    }

    public GeoLocation getLocation() {
        return this.location;
    }

    public boolean isSolar() {
        return !isLunisolar();
    }

    public boolean isLunisolar() {
        return isAmanta() || isPurnimanta();
    }

    public boolean isAmanta() {
        int i = this.type;
        if (i == -2) {
            return true;
        }
        return i >= HinduRule.AMANTA.ordinal() && this.type < HinduRule.PURNIMANTA.ordinal();
    }

    public boolean isPurnimanta() {
        return this.type == HinduRule.PURNIMANTA.ordinal();
    }

    public boolean isOld() {
        return this.type < 0;
    }

    public boolean isUsingElapsedYears() {
        return this.elapsedMode;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof HinduVariant)) {
            return false;
        }
        HinduVariant hinduVariant = (HinduVariant) obj;
        return this.type == hinduVariant.type && this.defaultEra == hinduVariant.defaultEra && this.elapsedMode == hinduVariant.elapsedMode && equals(this.depressionAngle, hinduVariant.depressionAngle) && this.location.getLatitude() == hinduVariant.location.getLatitude() && this.location.getLongitude() == hinduVariant.location.getLongitude() && this.location.getAltitude() == hinduVariant.location.getAltitude();
    }

    public int hashCode() {
        return this.type + (this.defaultEra.hashCode() * 17) + (this.elapsedMode ? 1 : 0) + (Double.isNaN(this.depressionAngle) ? 100 : ((int) this.depressionAngle) * 100);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Hindu-variant=[");
        int i = this.type;
        if (i == -2) {
            sb.append("OLD-LUNAR");
        } else if (i == -1) {
            sb.append("OLD-SOLAR");
        } else {
            sb.append(getRule().name());
        }
        if (!isOld()) {
            sb.append("|default-era=");
            sb.append(this.defaultEra.name());
            sb.append('|');
            sb.append(this.elapsedMode ? "elapsed-year-mode" : "current-year-mode");
            if (!Double.isNaN(this.depressionAngle)) {
                sb.append("|depression-angle=");
                sb.append(this.depressionAngle);
            }
            if (this.location != UJJAIN) {
                sb.append("|lat=");
                sb.append(this.location.getLatitude());
                sb.append(",lng=");
                sb.append(this.location.getLongitude());
                int altitude = this.location.getAltitude();
                if (altitude != 0) {
                    sb.append(",alt=");
                    sb.append(altitude);
                }
            }
        }
        sb.append(']');
        return sb.toString();
    }

    @Override // net.time4j.engine.VariantSource
    public String getVariant() {
        if (isOld()) {
            return "AryaSiddhanta@" + (this.type == -1 ? AryaSiddhanta.SOLAR : AryaSiddhanta.LUNAR).name();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(this.type);
        sb.append('|');
        sb.append(this.defaultEra.name());
        sb.append('|');
        sb.append(this.elapsedMode ? "elapsed" : SentryThread.JsonKeys.CURRENT);
        sb.append('|');
        sb.append(Double.isNaN(this.depressionAngle) ? "oldstyle" : Double.valueOf(this.depressionAngle));
        if (this.location != UJJAIN) {
            sb.append('|');
            sb.append(this.location.getLatitude());
            sb.append('|');
            sb.append(this.location.getLongitude());
            int altitude = this.location.getAltitude();
            if (altitude != 0) {
                sb.append('|');
                sb.append(altitude);
            }
        }
        return sb.toString();
    }

    public HinduVariant with(HinduEra hinduEra) {
        return (isOld() || this.defaultEra.equals(hinduEra)) ? this : new HinduVariant(this.type, hinduEra, this.elapsedMode, this.depressionAngle, this.location);
    }

    public HinduVariant withElapsedYears() {
        return (isOld() || this.elapsedMode) ? this : new HinduVariant(this.type, this.defaultEra, true, this.depressionAngle, this.location);
    }

    public HinduVariant withCurrentYears() {
        return (isOld() || !this.elapsedMode) ? this : new HinduVariant(this.type, this.defaultEra, false, this.depressionAngle, this.location);
    }

    public HinduVariant withModernAstronomy(double d) {
        if (Double.isNaN(d) || Double.isInfinite(d)) {
            throw new IllegalArgumentException("Depression angle must be a finite number.");
        }
        return isOld() ? this : new HinduVariant(this.type, this.defaultEra, this.elapsedMode, d, this.location);
    }

    public HinduVariant withAlternativeLocation(GeoLocation geoLocation) {
        if (Math.abs(geoLocation.getLatitude()) <= 60.0d) {
            return isOld() ? this : (geoLocation.getLatitude() == this.location.getLatitude() && geoLocation.getLongitude() == this.location.getLongitude() && geoLocation.getAltitude() == this.location.getAltitude()) ? this : new HinduVariant(this.type, this.defaultEra, this.elapsedMode, this.depressionAngle, geoLocation);
        }
        throw new IllegalArgumentException("Latitudes beyond +/-60° degrees not supported.");
    }

    boolean prefersRasiNames() {
        return this.type == HinduRule.MADRAS.ordinal() || this.type == HinduRule.MALAYALI.ordinal();
    }

    HinduCS getCalendarSystem() {
        int i = this.type;
        if (i == -2) {
            return AryaSiddhanta.LUNAR.getCalendarSystem();
        }
        if (i == -1) {
            return AryaSiddhanta.SOLAR.getCalendarSystem();
        }
        return new ModernHinduCS(this);
    }

    int getFirstMonthOfYear() {
        IndianMonth indianMonth;
        if (isOld()) {
            return 1;
        }
        int i = AnonymousClass1.$SwitchMap$net$time4j$calendar$hindu$HinduRule[getRule().ordinal()];
        if (i == 1) {
            indianMonth = IndianMonth.ASHADHA;
        } else if (i == 2) {
            indianMonth = IndianMonth.KARTIKA;
        } else {
            indianMonth = IndianMonth.CHAITRA;
        }
        return indianMonth.getValue();
    }

    HinduCS toAmanta() {
        return new HinduVariant(HinduRule.AMANTA.ordinal(), this.defaultEra, this.elapsedMode, this.depressionAngle, this.location).getCalendarSystem();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean useModernAstronomy() {
        return !Double.isNaN(this.depressionAngle);
    }

    /* renamed from: net.time4j.calendar.hindu.HinduVariant$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$time4j$calendar$hindu$HinduEra;
        static final /* synthetic */ int[] $SwitchMap$net$time4j$calendar$hindu$HinduRule;

        static {
            int[] iArr = new int[HinduEra.values().length];
            $SwitchMap$net$time4j$calendar$hindu$HinduEra = iArr;
            try {
                iArr[HinduEra.SAKA.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$net$time4j$calendar$hindu$HinduEra[HinduEra.KOLLAM.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            int[] iArr2 = new int[HinduRule.values().length];
            $SwitchMap$net$time4j$calendar$hindu$HinduRule = iArr2;
            try {
                iArr2[HinduRule.AMANTA_ASHADHA.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$net$time4j$calendar$hindu$HinduRule[HinduRule.AMANTA_KARTIKA.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$net$time4j$calendar$hindu$HinduRule[HinduRule.MADRAS.ordinal()] = 3;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$net$time4j$calendar$hindu$HinduRule[HinduRule.MALAYALI.ordinal()] = 4;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$net$time4j$calendar$hindu$HinduRule[HinduRule.TAMIL.ordinal()] = 5;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$net$time4j$calendar$hindu$HinduRule[HinduRule.ORISSA.ordinal()] = 6;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$net$time4j$calendar$hindu$HinduRule[HinduRule.AMANTA.ordinal()] = 7;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$net$time4j$calendar$hindu$HinduRule[HinduRule.PURNIMANTA.ordinal()] = 8;
            } catch (NoSuchFieldError unused10) {
            }
        }
    }

    private static boolean useStandardElapsedMode(HinduEra hinduEra, HinduRule hinduRule) {
        int i = AnonymousClass1.$SwitchMap$net$time4j$calendar$hindu$HinduEra[hinduEra.ordinal()];
        if (i != 1) {
            return i != 2;
        }
        int i2 = AnonymousClass1.$SwitchMap$net$time4j$calendar$hindu$HinduRule[hinduRule.ordinal()];
        return (i2 == 3 || i2 == 4 || i2 == 5) ? false : true;
    }

    private static boolean equals(double d, double d2) {
        if (Double.isNaN(d)) {
            return Double.isNaN(d2);
        }
        return !Double.isNaN(d2) && d == d2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public HinduRule getRule() {
        return RULES[this.type];
    }

    private Object writeReplace() {
        return new SPX(this, 21);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException {
        throw new InvalidObjectException("Serialization proxy required.");
    }

    static class ModernHinduCS extends HinduCS {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static final double ANOMALISTIC_MONTH = 27.554597974680476d;
        private static final double ANOMALISTIC_YEAR = 365.25878920258134d;
        private static final boolean CC = false;
        private static final double CREATION = -7.14403429586E11d;
        private static final int MAX_YEAR = 5999;
        private static final double MEAN_SIDEREAL_YEAR = 365.25636d;
        private static final double MEAN_SYNODIC_MONTH = 29.530588861d;
        private static final int MIN_YEAR = 1200;
        private static final double SIDEREAL_MONTH = 27.321674162683866d;
        static final double SIDEREAL_START = 336.1360765905204d;
        static final double SIDEREAL_YEAR = 365.2587564814815d;
        private static final double SYNODIC_MONTH = 29.53058794607172d;
        private volatile long max;
        private volatile long min;
        private static final double EPSILON = Math.pow(2.0d, -1000.0d);
        private static final double[] RISING_SIGN_FACTORS = {0.9277777777777778d, 0.9972222222222222d, 1.075d, 1.075d, 0.9972222222222222d, 0.9277777777777778d};

        ModernHinduCS(HinduVariant hinduVariant) {
            super(hinduVariant);
            this.min = Long.MIN_VALUE;
            this.max = Long.MAX_VALUE;
        }

        @Override // net.time4j.calendar.hindu.HinduCS
        HinduCalendar create(long j) {
            int value;
            HinduVariant hinduVariant = this.variant;
            switch (AnonymousClass1.$SwitchMap$net$time4j$calendar$hindu$HinduRule[getRule().ordinal()]) {
                case 1:
                case 2:
                    HinduCalendar create = hinduVariant.toAmanta().create(j);
                    int expiredYearOfKaliYuga = create.getExpiredYearOfKaliYuga();
                    if (create.getMonth().getValue().getValue() < hinduVariant.getFirstMonthOfYear()) {
                        expiredYearOfKaliYuga--;
                    }
                    return new HinduCalendar(hinduVariant, expiredYearOfKaliYuga, create.getMonth(), create.getDayOfMonth(), j);
                case 3:
                case 4:
                case 5:
                case 6:
                    return hSolarFromFixed(j, hinduVariant);
                case 7:
                    return hLunarFromFixed(j, hinduVariant);
                case 8:
                    HinduCS amanta = hinduVariant.toAmanta();
                    HinduCalendar create2 = amanta.create(j);
                    if (create2.getDayOfMonth().getValue() >= 16) {
                        value = amanta.create(20 + j).getMonth().getValue().getValue();
                    } else {
                        value = create2.getMonth().getValue().getValue();
                    }
                    HinduMonth ofLunisolar = HinduMonth.ofLunisolar(value);
                    if (create2.getMonth().isLeap()) {
                        ofLunisolar = ofLunisolar.withLeap();
                    }
                    return new HinduCalendar(hinduVariant, create2.getExpiredYearOfKaliYuga(), ofLunisolar, create2.getDayOfMonth(), j);
                default:
                    throw new UnsupportedOperationException(getRule().name());
            }
        }

        @Override // net.time4j.calendar.hindu.HinduCS
        HinduCalendar create(int i, HinduMonth hinduMonth, HinduDay hinduDay) {
            long hFixedFromSolar;
            HinduMonth hinduMonth2;
            HinduVariant hinduVariant = this.variant;
            switch (AnonymousClass1.$SwitchMap$net$time4j$calendar$hindu$HinduRule[getRule().ordinal()]) {
                case 1:
                case 2:
                    return new HinduCalendar(hinduVariant, i, hinduMonth, hinduDay, hinduVariant.toAmanta().create(hinduMonth.getValue().getValue() < hinduVariant.getFirstMonthOfYear() ? i + 1 : i, hinduMonth, hinduDay).getDaysSinceEpochUTC());
                case 3:
                case 4:
                case 5:
                case 6:
                    hFixedFromSolar = hFixedFromSolar(i, hinduMonth, hinduDay, hinduVariant);
                    break;
                case 7:
                    hFixedFromSolar = hFixedFromLunar(i, hinduMonth, hinduDay, hinduVariant);
                    break;
                case 8:
                    if (hinduMonth.isLeap() || hinduDay.getValue() <= 15) {
                        hinduMonth2 = hinduMonth;
                    } else if (this.variant.toAmanta().isExpunged(i, prevMonth(hinduMonth, 1))) {
                        hinduMonth2 = prevMonth(hinduMonth, 2);
                    } else {
                        hinduMonth2 = prevMonth(hinduMonth, 1);
                    }
                    hFixedFromSolar = hFixedFromLunar(i, hinduMonth2, hinduDay, hinduVariant);
                    break;
                default:
                    throw new UnsupportedOperationException(getRule().name());
            }
            return new HinduCalendar(hinduVariant, i, hinduMonth, hinduDay, hFixedFromSolar);
        }

        @Override // net.time4j.calendar.hindu.HinduCS
        boolean isValid(int i, HinduMonth hinduMonth, HinduDay hinduDay) {
            HinduCS amanta;
            if (i < MIN_YEAR || i > MAX_YEAR || hinduMonth == null || hinduDay == null) {
                return false;
            }
            if (this.variant.isSolar() && (hinduMonth.isLeap() || hinduDay.isLeap())) {
                return false;
            }
            if (this.variant.isLunisolar() && hinduDay.getValue() > 30) {
                return false;
            }
            HinduRule rule = getRule();
            if (rule == HinduRule.AMANTA_ASHADHA || rule == HinduRule.AMANTA_KARTIKA) {
                if (hinduMonth.getValue().getValue() < this.variant.getFirstMonthOfYear()) {
                    i++;
                }
                amanta = this.variant.toAmanta();
            } else {
                amanta = this;
            }
            return !amanta.isExpunged(i, hinduMonth, hinduDay);
        }

        @Override // net.time4j.engine.CalendarSystem
        public long getMinimumSinceUTC() {
            HinduCalendar createNewYear;
            if (this.min == Long.MIN_VALUE) {
                if (this.variant.isPurnimanta()) {
                    createNewYear = createNewYear(1201).withFirstDayOfMonth();
                } else {
                    createNewYear = createNewYear(MIN_YEAR);
                }
                this.min = createNewYear.getDaysSinceEpochUTC();
            }
            return this.min;
        }

        @Override // net.time4j.engine.CalendarSystem
        public long getMaximumSinceUTC() {
            if (this.max == Long.MAX_VALUE) {
                HinduCalendar createNewYear = createNewYear(6000);
                if (this.variant.isPurnimanta()) {
                    createNewYear = createNewYear.withFirstDayOfMonth();
                }
                this.max = createNewYear.getDaysSinceEpochUTC() - 1;
            }
            return this.max;
        }

        private HinduCalendar createNewYear(int i) {
            return create(i, HinduMonth.of(IndianMonth.AGRAHAYANA), HinduDay.valueOf(1)).withNewYear();
        }

        private HinduRule getRule() {
            return this.variant.getRule();
        }

        private static HinduMonth prevMonth(HinduMonth hinduMonth, int i) {
            int value = hinduMonth.getValue().getValue() - i;
            if (value <= 0) {
                value += 12;
            }
            return HinduMonth.ofLunisolar(value);
        }

        private static double hSineTable(double d) {
            double sin = Math.sin(Math.toRadians(d * 3.75d)) * 3438.0d;
            return Math.floor((sin + ((Math.signum(sin) * 0.215d) * Math.signum(Math.abs(sin) - 1716.0d))) + 0.5d) / 3438.0d;
        }

        private static double hSine(double d) {
            double d2 = d / 3.75d;
            double modulo = modulo(d2, 1.0d);
            return (hSineTable(Math.ceil(d2)) * modulo) + ((1.0d - modulo) * hSineTable(Math.floor(d2)));
        }

        private static double hArcSin(double d) {
            if (d < EPSILON) {
                return -hArcSin(-d);
            }
            int i = 0;
            while (true) {
                double d2 = i;
                if (d <= hSineTable(d2)) {
                    double d3 = i - 1;
                    double hSineTable = hSineTable(d3);
                    return (d3 + ((d - hSineTable) / (hSineTable(d2) - hSineTable))) * 3.75d;
                }
                i++;
            }
        }

        private static double hMeanPosition(double d, double d2) {
            return modulo((d - CREATION) / d2, 1.0d) * 360.0d;
        }

        private static double hTruePosition(double d, double d2, double d3, double d4, double d5) {
            double hMeanPosition = hMeanPosition(d, d2);
            double hSine = hSine(hMeanPosition(d, d4));
            return modulo(hMeanPosition - hArcSin(hSine * (d3 - ((Math.abs(hSine) * d5) * d3))), 360.0d);
        }

        private static double hSiderealSolarLongitude(double d) {
            return modulo((StdSolarCalculator.CC.getFeature(toJDE(d).getValue(), "solar-longitude") - hPrecession(d)) + SIDEREAL_START, 360.0d);
        }

        static double hSolarLongitude(double d) {
            return hTruePosition(d, SIDEREAL_YEAR, 0.03888888888888889d, ANOMALISTIC_YEAR, 0.023809523809523808d);
        }

        static double hPrecession(double d) {
            double centuryJ2000 = toJDE(d).getCenturyJ2000();
            double modulo = modulo(((((1.6666666666666667E-8d * centuryJ2000) - 9.172222222222223E-6d) * centuryJ2000) + 0.01305636111111111d) * centuryJ2000, 360.0d);
            double modulo2 = modulo((((9.822222222222223E-6d * centuryJ2000) - 0.24161358333333333d) * centuryJ2000) + 174.876384d, 360.0d);
            return modulo((modulo((((((((-1) * 6.0E-6d) / 3600.0d) * centuryJ2000) + 3.0864722222222223E-4d) * centuryJ2000) + 1.3969712777777776d) * centuryJ2000, 360.0d) + modulo2) - Math.toDegrees(Math.atan2(Math.cos(Math.toRadians(modulo)) * Math.sin(Math.toRadians(modulo2)), Math.cos(Math.toRadians(modulo2)))), 360.0d);
        }

        private static JulianDay toJDE(double d) {
            return JulianDay.ofEphemerisTime(Moment.of(Math.round(((d + 1721424.0d) - 2440587.0d) * 86400.0d), TimeScale.POSIX));
        }

        private static double toRataDie(Moment moment) {
            return ((moment.getPosixTime() / 86400.0d) + 2440587.0d) - 1721424.0d;
        }

        private static int hZodiac(double d) {
            return (int) (Math.floor(hSolarLongitude(d) / 30.0d) + 1.0d);
        }

        private static int hSiderealZodiac(double d) {
            return (int) (Math.floor(hSiderealSolarLongitude(d) / 30.0d) + 1.0d);
        }

        private static double hLunarLongitude(double d) {
            return hTruePosition(d, SIDEREAL_MONTH, 0.08888888888888889d, ANOMALISTIC_MONTH, 0.010416666666666666d);
        }

        private static double hLunarPhase(double d) {
            return modulo(hLunarLongitude(d) - hSolarLongitude(d), 360.0d);
        }

        private static int hLunarDayFromMoment(double d, HinduVariant hinduVariant) {
            double hLunarPhase;
            if (hinduVariant.useModernAstronomy()) {
                double value = toJDE(d).getValue();
                hLunarPhase = modulo(StdSolarCalculator.CC.getFeature(value, "lunar-longitude") - StdSolarCalculator.CC.getFeature(value, "solar-longitude"), 360.0d);
                double modulo = modulo((d - nthNewMoon((int) Math.round((d - nthNewMoon(0)) / MEAN_SYNODIC_MONTH))) / MEAN_SYNODIC_MONTH, 1.0d) * 360.0d;
                if (Math.abs(hLunarPhase - modulo) > 180.0d) {
                    hLunarPhase = modulo;
                }
            } else {
                hLunarPhase = hLunarPhase(d);
            }
            return (int) (Math.floor(hLunarPhase / 12.0d) + 1.0d);
        }

        private static double nthNewMoon(int i) {
            return toRataDie(MoonPhase.NEW_MOON.atLunation(i - 24724));
        }

        private static double hNewMoonBefore(double d) {
            double hLunarPhase = d - ((hLunarPhase(d) * SYNODIC_MONTH) / 360.0d);
            return binarySearchLunarPhase(hLunarPhase - 1.0d, Math.min(d, hLunarPhase + 1.0d));
        }

        private static double binarySearchLunarPhase(double d, double d2) {
            double d3 = (d + d2) / 2.0d;
            if (hZodiac(d) == hZodiac(d2) || d2 - d < EPSILON) {
                return d3;
            }
            if (hLunarPhase(d3) < 180.0d) {
                return binarySearchLunarPhase(d, d3);
            }
            return binarySearchLunarPhase(d3, d2);
        }

        private static int hCalendarYear(double d, HinduVariant hinduVariant) {
            double floor;
            if (hinduVariant.useModernAstronomy()) {
                floor = Math.floor((((d - (-1132959.0d)) / MEAN_SIDEREAL_YEAR) + 0.5d) - (hSiderealSolarLongitude(d) / 360.0d));
            } else {
                floor = Math.floor((((d - (-1132959.0d)) / SIDEREAL_YEAR) + 0.5d) - (hSolarLongitude(d) / 360.0d));
            }
            return (int) floor;
        }

        private static HinduCalendar hSolarFromFixed(long j, HinduVariant hinduVariant) {
            int hZodiac;
            long modulo;
            LongFunction hCritical = hCritical(hinduVariant);
            long transform = EpochDays.RATA_DIE.transform(j, EpochDays.UTC);
            double apply = hCritical.apply(transform);
            int hCalendarYear = hCalendarYear(apply, hinduVariant);
            if (hinduVariant.useModernAstronomy()) {
                hZodiac = hSiderealZodiac(apply);
                modulo = (transform - 3) - ((int) modulo(Math.floor(hSiderealSolarLongitude(apply)), 30.0d));
                while (hSiderealZodiac(hCritical.apply(modulo)) != hZodiac) {
                    modulo++;
                }
            } else {
                hZodiac = hZodiac(apply);
                modulo = (transform - 3) - ((int) modulo(Math.floor(hSolarLongitude(apply)), 30.0d));
                while (hZodiac(hCritical.apply(modulo)) != hZodiac) {
                    modulo++;
                }
            }
            return new HinduCalendar(hinduVariant, hCalendarYear, HinduMonth.ofSolar(hZodiac), HinduDay.valueOf((int) ((transform - modulo) + 1)), j);
        }

        private static long hFixedFromSolar(int i, HinduMonth hinduMonth, HinduDay hinduDay, HinduVariant hinduVariant) {
            int rasi = hinduMonth.getRasi();
            LongFunction hCritical = hCritical(hinduVariant);
            long floor = ((long) Math.floor((hinduVariant.useModernAstronomy() ? MEAN_SIDEREAL_YEAR : SIDEREAL_YEAR) * (i + ((rasi - 1) / 12.0d)))) - 1132962;
            if (hinduVariant.useModernAstronomy()) {
                while (hSiderealZodiac(hCritical.apply(floor)) != rasi) {
                    floor++;
                }
            } else {
                while (hZodiac(hCritical.apply(floor)) != rasi) {
                    floor++;
                }
            }
            return EpochDays.UTC.transform((hinduDay.getValue() - 1) + floor, EpochDays.RATA_DIE);
        }

        private static LongFunction hCritical(final HinduVariant hinduVariant) {
            int i = AnonymousClass1.$SwitchMap$net$time4j$calendar$hindu$HinduRule[hinduVariant.getRule().ordinal()];
            if (i == 3) {
                return new LongFunction() { // from class: net.time4j.calendar.hindu.HinduVariant.ModernHinduCS.4
                    @Override // net.time4j.calendar.hindu.HinduVariant.LongFunction
                    public double apply(long j) {
                        return ModernHinduCS.hStandardFromSundial(j + 1, HinduVariant.this);
                    }
                };
            }
            if (i == 4) {
                return new LongFunction() { // from class: net.time4j.calendar.hindu.HinduVariant.ModernHinduCS.3
                    @Override // net.time4j.calendar.hindu.HinduVariant.LongFunction
                    public double apply(long j) {
                        return ModernHinduCS.hStandardFromSundial(j + 0.55d, HinduVariant.this);
                    }
                };
            }
            if (i == 5) {
                return new LongFunction() { // from class: net.time4j.calendar.hindu.HinduVariant.ModernHinduCS.2
                    @Override // net.time4j.calendar.hindu.HinduVariant.LongFunction
                    public double apply(long j) {
                        return ModernHinduCS.hSunset(j, HinduVariant.this);
                    }
                };
            }
            if (i == 6) {
                return new LongFunction() { // from class: net.time4j.calendar.hindu.HinduVariant.ModernHinduCS.1
                    @Override // net.time4j.calendar.hindu.HinduVariant.LongFunction
                    public double apply(long j) {
                        return ModernHinduCS.hSunrise(j + 1, HinduVariant.this);
                    }
                };
            }
            throw new UnsupportedOperationException("Not yet implemented.");
        }

        private static HinduCalendar hLunarFromFixed(long j, HinduVariant hinduVariant) {
            int hZodiac;
            int hZodiac2;
            double transform = EpochDays.RATA_DIE.transform(j, EpochDays.UTC);
            double hSunrise = hSunrise(transform, hinduVariant);
            int hLunarDayFromMoment = hLunarDayFromMoment(hSunrise, hinduVariant);
            HinduDay valueOf = HinduDay.valueOf(hLunarDayFromMoment);
            if (hLunarDayFromMoment(hSunrise(r2 - 1, hinduVariant), hinduVariant) == hLunarDayFromMoment) {
                valueOf = valueOf.withLeap();
            }
            if (hinduVariant.useModernAstronomy()) {
                Moment moment = toJDE(hSunrise).toMoment();
                double rataDie = toRataDie(MoonPhase.NEW_MOON.before(moment));
                double rataDie2 = toRataDie(MoonPhase.NEW_MOON.atOrAfter(moment));
                hZodiac = hSiderealZodiac(rataDie);
                hZodiac2 = hSiderealZodiac(rataDie2);
            } else {
                double hNewMoonBefore = hNewMoonBefore(hSunrise);
                double hNewMoonBefore2 = hNewMoonBefore(Math.floor(hNewMoonBefore) + 35.0d);
                hZodiac = hZodiac(hNewMoonBefore);
                hZodiac2 = hZodiac(hNewMoonBefore2);
            }
            int i = hZodiac == 12 ? 1 : hZodiac + 1;
            HinduMonth ofLunisolar = HinduMonth.ofLunisolar(i);
            if (hZodiac2 == hZodiac) {
                ofLunisolar = ofLunisolar.withLeap();
            }
            if (i <= 2) {
                transform += 180.0d;
            }
            return new HinduCalendar(hinduVariant, hCalendarYear(transform, hinduVariant), ofLunisolar, valueOf, j);
        }

        private static long hFixedFromLunar(int i, HinduMonth hinduMonth, HinduDay hinduDay, HinduVariant hinduVariant) {
            double d;
            double hSolarLongitude;
            int value = hinduMonth.getValue().getValue();
            if (hinduVariant.useModernAstronomy()) {
                d = ((i + ((value - 1) / 12.0d)) * MEAN_SIDEREAL_YEAR) - 1132959.0d;
                hSolarLongitude = hSiderealSolarLongitude(d);
            } else {
                d = ((i + ((value - 1) / 12.0d)) * SIDEREAL_YEAR) - 1132959.0d;
                hSolarLongitude = hSolarLongitude(d);
            }
            long floor = (long) Math.floor(d - ((modulo(((hSolarLongitude / 360.0d) - ((value - 1) / 12.0d)) + 0.5d, 1.0d) - 0.5d) * SIDEREAL_YEAR));
            int hLunarDayFromMoment = hLunarDayFromMoment(floor + 0.25d, hinduVariant);
            int value2 = hinduDay.getValue();
            if (hLunarDayFromMoment <= 3 || hLunarDayFromMoment >= 27) {
                HinduCalendar hLunarFromFixed = hLunarFromFixed(EpochDays.UTC.transform(floor - 15, EpochDays.RATA_DIE), hinduVariant);
                if (hLunarFromFixed.getMonth().getValue() != hinduMonth.getValue() || (hLunarFromFixed.getMonth().isLeap() && !hinduMonth.isLeap())) {
                    hLunarDayFromMoment = ((int) modulo(hLunarDayFromMoment + 15, 30.0d)) - 15;
                } else {
                    hLunarDayFromMoment = ((int) modulo(hLunarDayFromMoment - 15, 30.0d)) + 15;
                }
            }
            long modulo = (14 + ((floor + value2) - hLunarDayFromMoment)) - ((int) modulo((hLunarDayFromMoment(r2 + 0.25d, hinduVariant) - value2) + 15, 30.0d));
            while (true) {
                int hLunarDayFromMoment2 = hLunarDayFromMoment(hSunrise(modulo, hinduVariant), hinduVariant);
                int modulo2 = (int) modulo(value2 + 1, 30.0d);
                if (modulo2 == 0) {
                    modulo2 = 30;
                }
                if (hLunarDayFromMoment2 == value2 || hLunarDayFromMoment2 == modulo2) {
                    break;
                }
                modulo++;
            }
            if (hinduDay.isLeap()) {
                modulo++;
            }
            return EpochDays.UTC.transform(modulo, EpochDays.RATA_DIE);
        }

        private static double hAscensionalDifference(double d, GeoLocation geoLocation) {
            double hSine = hSine(hTropicalLongitude(d)) * 0.4063408958696917d;
            double latitude = geoLocation.getLatitude();
            return hArcSin(((hSine * (hSine(latitude) / hSine(latitude + 90.0d))) * (-1.0d)) / hSine(hArcSin(hSine) + 90.0d));
        }

        private static double hSolarSiderealDifference(double d) {
            return hDailyMotion(d) * hRisingSign(d);
        }

        private static double hEquationOfTime(double d) {
            double hSine = hSine(hMeanPosition(d, ANOMALISTIC_YEAR));
            return (hDailyMotion(d) / 360.0d) * (((57.3d * hSine) * (0.03888888888888889d - (Math.abs(hSine) / 1080.0d))) / 360.0d) * SIDEREAL_YEAR;
        }

        private static double hTropicalLongitude(double d) {
            return modulo(hSolarLongitude(d) - (27.0d - Math.abs((modulo((((d - (-1132959.0d)) * 3.80247937727211E-7d) - 0.25d) + 0.5d, 1.0d) - 0.5d) * 108.0d)), 360.0d);
        }

        private static double hDailyMotion(double d) {
            double hMeanPosition = hMeanPosition(d, ANOMALISTIC_YEAR);
            double abs = 0.03888888888888889d - (Math.abs(hSine(hMeanPosition)) * 9.25925925925926E-4d);
            double floor = Math.floor(hMeanPosition / 3.75d);
            return (1.0d - (((hSineTable(floor + 1.0d) - hSineTable(floor)) * 15.28d) * abs)) * 0.9856026545889308d;
        }

        private static double hRisingSign(double d) {
            return RISING_SIGN_FACTORS[(int) modulo((int) Math.floor(hTropicalLongitude(d) / 30.0d), 6.0d)];
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static double hSunrise(double d, HinduVariant hinduVariant) {
            if (hinduVariant.useModernAstronomy()) {
                GeoLocation location = hinduVariant.getLocation();
                double posixTime = (StdSolarCalculator.CC.sunrise(PlainDate.of((long) Math.floor(d), EpochDays.RATA_DIE), location.getLatitude(), location.getLongitude(), hinduVariant.depressionAngle + 90.0d).getPosixTime() + HinduVariant.U_OFFSET) / 86400.0d;
                return (EpochDays.RATA_DIE.transform((long) Math.floor(posixTime), EpochDays.UNIX) + posixTime) - Math.floor(posixTime);
            }
            return (((d + 0.25d) + ((HinduVariant.UJJAIN.getLongitude() - hinduVariant.getLocation().getLongitude()) / 360.0d)) - hEquationOfTime(d)) + (((hSolarSiderealDifference(d) * 0.25d) + hAscensionalDifference(d, hinduVariant.getLocation())) * 0.002770193582919304d);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static double hSunset(double d, HinduVariant hinduVariant) {
            if (hinduVariant.useModernAstronomy()) {
                GeoLocation location = hinduVariant.getLocation();
                double posixTime = (StdSolarCalculator.CC.sunset(PlainDate.of((long) Math.floor(d), EpochDays.RATA_DIE), location.getLatitude(), location.getLongitude(), hinduVariant.depressionAngle + 90.0d).getPosixTime() + HinduVariant.U_OFFSET) / 86400.0d;
                return (EpochDays.RATA_DIE.transform((long) Math.floor(posixTime), EpochDays.UNIX) + posixTime) - Math.floor(posixTime);
            }
            return (((d + 0.75d) + ((HinduVariant.UJJAIN.getLongitude() - hinduVariant.getLocation().getLongitude()) / 360.0d)) - hEquationOfTime(d)) + (((hSolarSiderealDifference(d) * 0.75d) - hAscensionalDifference(d, hinduVariant.getLocation())) * 0.002770193582919304d);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static double hStandardFromSundial(double d, HinduVariant hinduVariant) {
            double hSunrise;
            double hSunset;
            double d2;
            double floor = Math.floor(d);
            double d3 = d - floor;
            int floor2 = (int) Math.floor(4.0d * d3);
            if (floor2 == 0) {
                hSunrise = hSunset(floor - 1.0d, hinduVariant);
                hSunset = hSunrise(floor, hinduVariant);
                d2 = -0.25d;
            } else if (floor2 == 3) {
                double hSunset2 = hSunset(floor, hinduVariant);
                hSunset = hSunrise(floor + 1.0d, hinduVariant);
                hSunrise = hSunset2;
                d2 = 0.75d;
            } else {
                hSunrise = hSunrise(floor, hinduVariant);
                hSunset = hSunset(floor, hinduVariant);
                d2 = 0.25d;
            }
            return hSunrise + ((hSunset - hSunrise) * 2.0d * (d3 - d2));
        }
    }

    private static class HinduLocation implements GeoLocation {
        private final int altitude;
        private final double latitude;
        private final double longitude;

        @Override // net.time4j.calendar.astro.GeoLocation
        public double getLatitude() {
            return this.latitude;
        }

        @Override // net.time4j.calendar.astro.GeoLocation
        public double getLongitude() {
            return this.longitude;
        }

        @Override // net.time4j.calendar.astro.GeoLocation
        public int getAltitude() {
            return this.altitude;
        }

        HinduLocation(double d, double d2, int i) {
            if (Double.isNaN(d) || Double.isInfinite(d)) {
                throw new IllegalArgumentException("Latitude must be a finite value: " + d);
            }
            if (Double.isNaN(d2) || Double.isInfinite(d2)) {
                throw new IllegalArgumentException("Longitude must be a finite value: " + d2);
            }
            if (Double.compare(d, 90.0d) > 0 || Double.compare(d, -90.0d) < 0) {
                throw new IllegalArgumentException("Degrees out of range -90.0 <= latitude <= +90.0: " + d);
            }
            if (Double.compare(d2, 180.0d) >= 0 || Double.compare(d2, -180.0d) < 0) {
                throw new IllegalArgumentException("Degrees out of range -180.0 <= longitude < +180.0: " + d2);
            }
            if (i < 0 || i >= 11000) {
                throw new IllegalArgumentException("Meters out of range 0 <= altitude < +11,000: " + i);
            }
            this.latitude = d;
            this.longitude = d2;
            this.altitude = i;
        }
    }
}

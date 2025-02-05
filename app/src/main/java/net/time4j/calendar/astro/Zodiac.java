package net.time4j.calendar.astro;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import net.time4j.Moment;
import net.time4j.engine.ChronoCondition;
import net.time4j.engine.ChronoElement;

/* loaded from: classes3.dex */
public enum Zodiac {
    ARIES(9800, 26.766d, 11.048d),
    TAURUS(9801, 51.113d, 18.648d),
    GEMINI(9802, 90.218d, 23.439d),
    CANCER(9803, 120.198d, 20.542d),
    LEO(9804, 140.637d, 15.375d),
    VIRGO(9805, 174.4d, 2.423d),
    LIBRA(9806, 215.634d, -14.176d),
    SCORPIUS(9807, 238.861d, -20.359d),
    OPHIUCHUS(9934, 245.915d, -21.594d),
    SAGITTARIUS(9808, 265.968d, -23.388d),
    CAPRICORNUS(9809, 301.869d, -20.214d),
    AQUARIUS(9810, 329.79d, -12.306d),
    PISCES(9811, 352.284d, -3.331d);

    private static final Map<String, String[]> LANG_TO_NAMES;
    private static final double MEAN_SYNODIC_MONTH = 29.530588861d;
    private static final double MEAN_TROPICAL_YEAR = 365.242189d;
    private final transient EquatorialCoordinates entry;
    private final transient char symbol;

    static {
        HashMap hashMap = new HashMap();
        hashMap.put("", new String[]{"Aries", "Taurus", "Gemini", "Cancer", "Leo", "Virgo", "Libra", "Scorpius", "Ophiuchus", "Sagittarius", "Capricornus", "Aquarius", "Pisces"});
        hashMap.put("da", new String[]{"Vædderen", "Tyren", "Tvillingerne", "Krebsen", "Løven", "Jomfruen", "Vægten", "Skorpionen", "Slangebæreren", "Skytten", "Stenbukken", "Vandmanden", "Fiskene"});
        hashMap.put("de", new String[]{"Widder", "Stier", "Zwillinge", "Krebs", "Löwe", "Jungfrau", "Waage", "Skorpion", "Schlangenträger", "Schütze", "Steinbock", "Wassermann", "Fische"});
        hashMap.put("en", new String[]{"Ram", "Bull", "Twins", "Crab", "Lion", "Maiden", "Scales", "Scorpion", "Serpent-bearer", "Archer", "Capricorn", "Water-bearer", "Fish"});
        hashMap.put("es", new String[]{"Aries", "Tauro", "Géminis", "Cáncer", "Leo", "Virgo", "Libra", "Escorpio", "Ofiuco", "Sagitario", "Capricornio", "Acuario", "Piscis"});
        hashMap.put("fr", new String[]{"Bélier", "Taureau", "Gémeaux", "Cancer", "Lion", "Vierge", "Balance", "Scorpion", "Serpentaire", "Sagittaire", "Capricorne", "Verseau", "Poissons"});
        hashMap.put("it", new String[]{"Ariete", "Toro", "Gemelli", "Cancro", "Leone", "Vergine", "Bilancia", "Scorpione", "Ofiuco", "Sagittario", "Capricorno", "Acquario", "Pesci"});
        hashMap.put("nl", new String[]{"Ram", "Stier", "Tweelingen", "Kreeft", "Leeuw", "Maagd", "Weegschaal", "Schorpioen", "Slangendrager", "Schutter", "Steenbok", "Waterman", "Vissen"});
        hashMap.put("ru", new String[]{"Овен", "Телец", "Близнецы", "Рак", "Лев", "Дева", "Весы", "Скорпион", "Змееносец", "Стрелец", "Козерог", "Водолей", "Рыбы"});
        hashMap.put("tr", new String[]{"Koç", "Boğa", "İkizler", "Yengeç", "Aslan", "Başak", "Terazi", "Akrep", "Ophiuchus", "Yay", "Oğlak", "Kova", "Balık"});
        LANG_TO_NAMES = Collections.unmodifiableMap(hashMap);
    }

    Zodiac(char c, double d, double d2) {
        this.symbol = c;
        this.entry = new SkyPosition(d, d2);
    }

    public static Zodiac constellationPassedBySun(Moment moment) {
        return of('S', moment, false);
    }

    public static Zodiac constellationPassedByMoon(Moment moment) {
        return of('L', moment, false);
    }

    public static Zodiac signPassedBySun(Moment moment) {
        return of('S', moment, true);
    }

    public static Zodiac signPassedByMoon(Moment moment) {
        return of('L', moment, true);
    }

    public char getSymbol() {
        return this.symbol;
    }

    public String getDisplayName(Locale locale) {
        Map<String, String[]> map = LANG_TO_NAMES;
        String[] strArr = map.get(locale.getLanguage());
        if (strArr == null) {
            strArr = map.get("");
        }
        return strArr[ordinal()];
    }

    public Zodiac previous() {
        return values()[(ordinal() + 12) % 13];
    }

    public Zodiac next() {
        return values()[(ordinal() + 1) % 13];
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static Zodiac of(char c, Moment moment, boolean z) {
        double eclipticAngle;
        double eclipticAngle2;
        Moment moment2 = (Moment) moment.with((ChronoElement<ChronoElement<TimeUnit>>) Moment.PRECISION, (ChronoElement<TimeUnit>) TimeUnit.MINUTES);
        double value = JulianDay.ofEphemerisTime(moment2).getValue();
        double solarLongitude = c == 'S' ? getSolarLongitude(value) : getLunarLongitude(value);
        for (Zodiac zodiac : values()) {
            Zodiac next = zodiac.next();
            if (z) {
                Zodiac zodiac2 = OPHIUCHUS;
                if (zodiac != zodiac2) {
                    if (next == zodiac2) {
                        next = SAGITTARIUS;
                    }
                    int i = zodiac.compareTo(zodiac2) < 0 ? 0 : -1;
                    int i2 = next.compareTo(zodiac2) < 0 ? 0 : -1;
                    eclipticAngle = (zodiac.ordinal() + i) * 30;
                    eclipticAngle2 = (next.ordinal() + i2) * 30;
                } else {
                    continue;
                }
            } else {
                eclipticAngle = toEclipticAngle(moment2, zodiac.entry.getRightAscension(), zodiac.entry.getDeclination());
                eclipticAngle2 = toEclipticAngle(moment2, next.entry.getRightAscension(), next.entry.getDeclination());
            }
            if (eclipticAngle2 < eclipticAngle) {
                eclipticAngle2 += 360.0d;
                if (solarLongitude < 180.0d) {
                    solarLongitude += 360.0d;
                }
            }
            if (solarLongitude >= eclipticAngle && solarLongitude < eclipticAngle2) {
                return zodiac;
            }
        }
        throw new NoSuchElementException("Unable to determine zodiac.");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static double getSolarLongitude(double d) {
        return StdSolarCalculator.TIME4J.getFeature(d, "solar-longitude");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static double getLunarLongitude(double d) {
        return MoonPosition.lunarLongitude(d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static double toEclipticAngle(Moment moment, double d, double d2) {
        double centuryJ2000 = JulianDay.ofSimplifiedTime(moment).getCenturyJ2000();
        double radians = Math.toRadians(StdSolarCalculator.meanObliquity(centuryJ2000));
        double d3 = (((((0.018203d * centuryJ2000) + 1.09468d) * centuryJ2000) + 2306.2181d) * centuryJ2000) / 3600.0d;
        double d4 = ((2004.3109d - (((0.041833d * centuryJ2000) + 0.42665d) * centuryJ2000)) * centuryJ2000) / 3600.0d;
        double radians2 = Math.toRadians(d + ((((((0.017998d * centuryJ2000) + 0.30188d) * centuryJ2000) + 2306.2181d) * centuryJ2000) / 3600.0d));
        double cos = Math.cos(radians2);
        double cos2 = Math.cos(Math.toRadians(d4));
        double sin = Math.sin(Math.toRadians(d4));
        double cos3 = Math.cos(Math.toRadians(d2));
        double sin2 = Math.sin(Math.toRadians(d2));
        double radians3 = Math.toRadians(Math.toDegrees(Math.atan2(Math.sin(radians2) * cos3, ((cos2 * cos3) * cos) - (sin * sin2))) + d3);
        double degrees = Math.toDegrees(Math.atan2((Math.sin(radians3) * Math.cos(radians)) + (Math.tan(Math.asin((sin * cos3 * cos) + (cos2 * sin2))) * Math.sin(radians)), Math.cos(radians3)));
        return degrees < 0.0d ? degrees + 360.0d : degrees;
    }

    public static class Event implements ChronoCondition<Moment> {
        private final char body;
        private final boolean horoscope;
        private final Zodiac zodiac;

        private Event(char c, Zodiac zodiac, boolean z) {
            if (c != 'S' && c != 'L') {
                throw new IllegalArgumentException("Unsupported celestial body: " + c);
            }
            if (zodiac == null) {
                throw new IllegalArgumentException("Celestial coordinates must be finite.");
            }
            if (z && zodiac == Zodiac.OPHIUCHUS) {
                throw new IllegalArgumentException("Ophiuchus is not an astrological zodiac sign.");
            }
            this.body = c;
            this.zodiac = zodiac;
            this.horoscope = z;
        }

        public Moment atMomentOfEntry(Moment moment) {
            return atTime(atTime(moment, false, true), false, false);
        }

        public Moment atMomentOfExit(Moment moment) {
            return atTime(atTime(moment, true, true), true, false);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // net.time4j.engine.ChronoCondition
        public boolean test(Moment moment) {
            double d;
            double d2;
            Moment moment2 = (Moment) moment.with((ChronoElement<ChronoElement<TimeUnit>>) Moment.PRECISION, (ChronoElement<TimeUnit>) TimeUnit.MINUTES);
            double value = JulianDay.ofEphemerisTime(moment2).getValue();
            double solarLongitude = this.body == 'S' ? Zodiac.getSolarLongitude(value) : Zodiac.getLunarLongitude(value);
            if (this.horoscope) {
                d = getHoroscopeLongitude(false);
                d2 = getHoroscopeLongitude(true);
            } else {
                Zodiac zodiac = this.zodiac;
                Zodiac next = zodiac.next();
                double eclipticAngle = Zodiac.toEclipticAngle(moment2, zodiac.entry.getRightAscension(), zodiac.entry.getDeclination());
                double eclipticAngle2 = Zodiac.toEclipticAngle(moment2, next.entry.getRightAscension(), next.entry.getDeclination());
                d = eclipticAngle;
                d2 = eclipticAngle2;
            }
            if (d2 < d) {
                d2 += 360.0d;
                if (solarLongitude < 180.0d) {
                    solarLongitude += 360.0d;
                }
            }
            return solarLongitude >= d && solarLongitude < d2;
        }

        static Event ofSign(char c, Zodiac zodiac) {
            return new Event(c, zodiac, true);
        }

        static Event ofConstellation(char c, Zodiac zodiac) {
            return new Event(c, zodiac, false);
        }

        private Zodiac getZodiac(boolean z) {
            Zodiac zodiac = this.zodiac;
            if (z) {
                zodiac = zodiac.next();
            }
            return (this.horoscope && zodiac == Zodiac.OPHIUCHUS) ? Zodiac.SAGITTARIUS : zodiac;
        }

        private int getHoroscopeLongitude(boolean z) {
            Zodiac zodiac = getZodiac(z);
            return (zodiac.ordinal() + (zodiac.compareTo(Zodiac.OPHIUCHUS) < 0 ? 0 : -1)) * 30;
        }

        /* JADX WARN: Multi-variable type inference failed */
        private Moment atTime(Moment moment, boolean z, boolean z2) {
            double eclipticAngle;
            double lunarLongitude;
            double d;
            if (!this.horoscope) {
                Zodiac zodiac = getZodiac(z);
                eclipticAngle = Zodiac.toEclipticAngle(moment, zodiac.entry.getRightAscension(), zodiac.entry.getDeclination());
            } else {
                if (!z2) {
                    return moment;
                }
                eclipticAngle = getHoroscopeLongitude(z);
            }
            double value = JulianDay.ofEphemerisTime(moment).getValue();
            if (this.body == 'S') {
                lunarLongitude = eclipticAngle - Zodiac.getSolarLongitude(value);
                if (z2) {
                    lunarLongitude = modulo360(lunarLongitude);
                }
                d = Zodiac.MEAN_TROPICAL_YEAR;
            } else {
                lunarLongitude = eclipticAngle - Zodiac.getLunarLongitude(value);
                if (z2) {
                    lunarLongitude = modulo360(lunarLongitude);
                }
                d = Zodiac.MEAN_SYNODIC_MONTH;
            }
            double d2 = ((lunarLongitude * d) / 360.0d) + value;
            double max = Math.max(value, d2 - 5.0d);
            double d3 = d2 + 5.0d;
            while (true) {
                double d4 = (max + d3) / 2.0d;
                if (d3 - max < 1.0E-4d) {
                    return (Moment) JulianDay.ofEphemerisTime(d4).toMoment().with((ChronoElement<ChronoElement<TimeUnit>>) Moment.PRECISION, (ChronoElement<TimeUnit>) TimeUnit.SECONDS);
                }
                if (modulo360((this.body == 'S' ? Zodiac.getSolarLongitude(d4) : Zodiac.getLunarLongitude(d4)) - eclipticAngle) < 180.0d) {
                    d3 = d4;
                } else {
                    max = d4;
                }
            }
        }

        private static double modulo360(double d) {
            return d - (Math.floor(d / 360.0d) * 360.0d);
        }
    }
}

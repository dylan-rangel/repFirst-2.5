package net.time4j.calendar;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import net.time4j.base.MathUtils;
import net.time4j.base.ResourceLoader;
import net.time4j.engine.CalendarEra;
import net.time4j.engine.EpochDays;
import net.time4j.format.expert.Iso8601Format;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes3.dex */
final class AstronomicalHijriData implements EraYearMonthDaySystem<HijriCalendar> {
    static final AstronomicalHijriData UMALQURA;
    private final int adjustment;
    private final long[] firstOfMonth;
    private final int[] lengthOfMonth;
    private final long maxUTC;
    private final int maxYear;
    private final long minUTC;
    private final int minYear;
    private final String variant;
    private final String version;

    static {
        try {
            UMALQURA = new AstronomicalHijriData(HijriCalendar.VARIANT_UMALQURA);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    AstronomicalHijriData(HijriData hijriData) {
        this.variant = "islamic-" + hijriData.name();
        int i = 0;
        this.adjustment = 0;
        this.version = hijriData.version();
        int minimumYear = hijriData.minimumYear();
        this.minYear = minimumYear;
        int maximumYear = hijriData.maximumYear();
        this.maxYear = maximumYear;
        if (maximumYear < minimumYear) {
            throw new IllegalArgumentException("Maximum year before minimum year.");
        }
        if (hijriData.name().startsWith("islamic")) {
            throw new IllegalArgumentException("Name must not start with \"islamic\".");
        }
        this.minUTC = hijriData.firstGregorianDate().getDaysSinceEpochUTC();
        int i2 = ((maximumYear - minimumYear) + 1) * 12;
        this.lengthOfMonth = new int[i2];
        this.firstOfMonth = new long[i2];
        long j = 0;
        while (minimumYear <= this.maxYear) {
            for (int i3 = 1; i3 <= 12; i3++) {
                int lengthOfMonth = hijriData.lengthOfMonth(minimumYear, i3);
                this.lengthOfMonth[i] = lengthOfMonth;
                this.firstOfMonth[i] = this.minUTC + j;
                j += lengthOfMonth;
                i++;
            }
            minimumYear++;
        }
        this.maxUTC = (this.minUTC + j) - 1;
    }

    AstronomicalHijriData(String str) throws IOException {
        InputStream inputStream;
        Throwable th;
        Properties properties;
        String property;
        InputStream inputStream2;
        HijriAdjustment from = HijriAdjustment.from(str);
        this.variant = str;
        String baseVariant = from.getBaseVariant();
        this.adjustment = from.getValue();
        String str2 = "data/" + baseVariant.replace('-', '_') + ".data";
        InputStream load = ResourceLoader.getInstance().load(ResourceLoader.getInstance().locate("calendar", AstronomicalHijriData.class, str2), true);
        load = load == null ? ResourceLoader.getInstance().load(AstronomicalHijriData.class, str2, true) : load;
        try {
            try {
                properties = new Properties();
                properties.load(load);
                property = properties.getProperty("type");
            } catch (NumberFormatException e) {
                e = e;
            } catch (ParseException e2) {
                e = e2;
            } catch (Throwable th2) {
                th = th2;
                inputStream = load;
                th = th;
                try {
                    inputStream.close();
                    throw th;
                } catch (IOException e3) {
                    e3.printStackTrace(System.err);
                    throw th;
                }
            }
            try {
                if (!baseVariant.equals(property)) {
                    throw new IOException("Wrong hijri variant: expected=" + baseVariant + ", found=" + property);
                }
                this.version = properties.getProperty("version", "1.0");
                long longValue = ((Long) Iso8601Format.EXTENDED_CALENDAR_DATE.parse(properties.getProperty("iso-start", "")).get(EpochDays.UTC)).longValue();
                this.minUTC = longValue;
                int parseInt = Integer.parseInt(properties.getProperty("min", "1"));
                this.minYear = parseInt;
                int parseInt2 = Integer.parseInt(properties.getProperty("max", "0"));
                this.maxYear = parseInt2;
                int i = ((parseInt2 - parseInt) + 1) * 12;
                int[] iArr = new int[i];
                long[] jArr = new long[i];
                int i2 = 0;
                while (true) {
                    if (parseInt > parseInt2) {
                        inputStream2 = load;
                        break;
                    }
                    String property2 = properties.getProperty(String.valueOf(parseInt));
                    if (property2 == null) {
                        throw new IOException("Wrong file format: " + str2 + " (missing year=" + parseInt + ")");
                    }
                    String[] split = property2.split(StringUtils.SPACE);
                    int i3 = 0;
                    while (i3 < Math.min(split.length, 12)) {
                        int parseInt3 = Integer.parseInt(split[i3]);
                        iArr[i2] = parseInt3;
                        jArr[i2] = longValue;
                        longValue += parseInt3;
                        i2++;
                        i3++;
                        load = load;
                        parseInt2 = parseInt2;
                    }
                    inputStream2 = load;
                    int i4 = parseInt2;
                    if (split.length < 12) {
                        int[] iArr2 = new int[i2];
                        long[] jArr2 = new long[i2];
                        System.arraycopy(iArr, 0, iArr2, 0, i2);
                        System.arraycopy(jArr, 0, jArr2, 0, i2);
                        iArr = iArr2;
                        jArr = jArr2;
                        break;
                    }
                    parseInt++;
                    load = inputStream2;
                    parseInt2 = i4;
                }
                this.maxUTC = longValue - 1;
                this.lengthOfMonth = iArr;
                this.firstOfMonth = jArr;
                try {
                    inputStream2.close();
                } catch (IOException e4) {
                    e4.printStackTrace(System.err);
                }
            } catch (NumberFormatException e5) {
                e = e5;
                throw new IOException("Wrong file format: " + str2, e);
            } catch (ParseException e6) {
                e = e6;
                throw new IOException("Wrong file format: " + str2, e);
            }
        } catch (Throwable th3) {
            th = th3;
            th = th;
            inputStream.close();
            throw th;
        }
    }

    @Override // net.time4j.engine.CalendarSystem
    public HijriCalendar transform(long j) {
        long safeAdd = MathUtils.safeAdd(j, this.adjustment);
        int search = search(safeAdd, this.firstOfMonth);
        if (search >= 0) {
            long[] jArr = this.firstOfMonth;
            if (search < jArr.length - 1 || jArr[search] + this.lengthOfMonth[search] > safeAdd) {
                return HijriCalendar.of(this.variant, (search / 12) + this.minYear, (search % 12) + 1, (int) ((safeAdd - jArr[search]) + 1));
            }
        }
        throw new IllegalArgumentException("Out of range: " + j);
    }

    @Override // net.time4j.engine.CalendarSystem
    public long transform(HijriCalendar hijriCalendar) {
        if (!hijriCalendar.getVariant().equals(this.variant)) {
            throw new IllegalArgumentException("Given date does not belong to this calendar system: " + hijriCalendar + " (calendar variants are different).");
        }
        return MathUtils.safeSubtract((this.firstOfMonth[(((hijriCalendar.getYear() - this.minYear) * 12) + hijriCalendar.getMonth().getValue()) - 1] + hijriCalendar.getDayOfMonth()) - 1, this.adjustment);
    }

    @Override // net.time4j.engine.CalendarSystem
    public long getMinimumSinceUTC() {
        return MathUtils.safeSubtract(this.minUTC, this.adjustment);
    }

    @Override // net.time4j.engine.CalendarSystem
    public long getMaximumSinceUTC() {
        return MathUtils.safeSubtract(this.maxUTC, this.adjustment);
    }

    @Override // net.time4j.engine.CalendarSystem
    public List<CalendarEra> getEras() {
        return Collections.singletonList(HijriEra.ANNO_HEGIRAE);
    }

    @Override // net.time4j.calendar.EraYearMonthDaySystem
    public boolean isValid(CalendarEra calendarEra, int i, int i2, int i3) {
        int i4;
        return calendarEra == HijriEra.ANNO_HEGIRAE && i >= (i4 = this.minYear) && i <= this.maxYear && i2 >= 1 && i2 <= 12 && i3 >= 1 && (((i - i4) * 12) + i2) - 1 < this.lengthOfMonth.length && i3 <= getLengthOfMonth(calendarEra, i, i2);
    }

    @Override // net.time4j.calendar.EraYearMonthDaySystem
    public int getLengthOfMonth(CalendarEra calendarEra, int i, int i2) {
        if (calendarEra != HijriEra.ANNO_HEGIRAE) {
            throw new IllegalArgumentException("Wrong era: " + calendarEra);
        }
        int i3 = (((i - this.minYear) * 12) + i2) - 1;
        if (i3 >= 0) {
            int[] iArr = this.lengthOfMonth;
            if (i3 < iArr.length) {
                return iArr[i3];
            }
        }
        throw new IllegalArgumentException("Out of bounds: year=" + i + ", month=" + i2);
    }

    @Override // net.time4j.calendar.EraYearMonthDaySystem
    public int getLengthOfYear(CalendarEra calendarEra, int i) {
        if (calendarEra != HijriEra.ANNO_HEGIRAE) {
            throw new IllegalArgumentException("Wrong era: " + calendarEra);
        }
        if (i < this.minYear || i > this.maxYear) {
            throw new IllegalArgumentException("Out of bounds: yearOfEra=" + i);
        }
        int i2 = 0;
        for (int i3 = 1; i3 <= 12; i3++) {
            int i4 = (((i - this.minYear) * 12) + i3) - 1;
            int[] iArr = this.lengthOfMonth;
            if (i4 >= iArr.length) {
                throw new IllegalArgumentException("Year range is not fully covered by underlying data: " + i);
            }
            i2 += iArr[i4];
        }
        return i2;
    }

    String getVersion() {
        return this.version;
    }

    private static int search(long j, long[] jArr) {
        int length = jArr.length - 1;
        int i = 0;
        while (i <= length) {
            int i2 = (i + length) / 2;
            if (jArr[i2] <= j) {
                i = i2 + 1;
            } else {
                length = i2 - 1;
            }
        }
        return i - 1;
    }
}

package net.time4j;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.time4j.IsoUnit;
import net.time4j.base.GregorianDate;
import net.time4j.base.MathUtils;
import net.time4j.base.WallTime;
import net.time4j.engine.AbstractDuration;
import net.time4j.engine.AbstractMetric;
import net.time4j.engine.ChronoEntity;
import net.time4j.engine.ChronoUnit;
import net.time4j.engine.Normalizer;
import net.time4j.engine.TimeMetric;
import net.time4j.engine.TimePoint;
import net.time4j.engine.TimeSpan;
import net.time4j.format.TimeSpanFormatter;
import net.time4j.tz.Timezone;
import org.apache.commons.lang3.ClassUtils;

/* loaded from: classes3.dex */
public final class Duration<U extends IsoUnit> extends AbstractDuration<U> implements Serializable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int CALENDAR_TYPE = 0;
    private static final Formatter<CalendarUnit> CF_BAS_CAL;
    private static final Formatter<CalendarUnit> CF_BAS_ORD;
    private static final Formatter<CalendarUnit> CF_EXT_CAL;
    private static final Formatter<CalendarUnit> CF_EXT_ORD;
    private static final TimeMetric<ClockUnit, Duration<ClockUnit>> CLOCK_METRIC;
    private static final int CLOCK_TYPE = 1;
    private static final char ISO_DECIMAL_SEPARATOR;
    private static final Comparator<TimeSpan.Item<? extends ChronoUnit>> ITEM_COMPARATOR;
    private static final long MIO = 1000000;
    private static final long MRD = 1000000000;
    private static final int PRINT_STYLE_ISO = 1;
    private static final int PRINT_STYLE_NORMAL = 0;
    private static final int PRINT_STYLE_XML = 2;
    public static Normalizer<CalendarUnit> STD_CALENDAR_PERIOD = null;
    public static Normalizer<ClockUnit> STD_CLOCK_PERIOD = null;
    public static Normalizer<IsoUnit> STD_PERIOD = null;
    private static final int SUPER_TYPE = -1;
    private static final Formatter<ClockUnit> TF_BAS;
    private static final Formatter<ClockUnit> TF_EXT;
    private static final TimeMetric<IsoDateUnit, Duration<IsoDateUnit>> WEEK_BASED_METRIC;
    private static final int WEEK_BASED_TYPE = 2;
    private static final TimeMetric<CalendarUnit, Duration<CalendarUnit>> YMD_METRIC;
    private static final Duration ZERO;
    private static final long serialVersionUID = -6321211763598951499L;
    private final transient List<TimeSpan.Item<U>> items;
    private final transient boolean negative;

    /* JADX WARN: Multi-variable type inference failed */
    private static <T> T cast(Object obj) {
        return obj;
    }

    private static boolean hasMixedSigns(long j, long j2) {
        return (j < 0 && j2 > 0) || (j > 0 && j2 < 0);
    }

    static {
        ISO_DECIMAL_SEPARATOR = Boolean.getBoolean("net.time4j.format.iso.decimal.dot") ? ClassUtils.PACKAGE_SEPARATOR_CHAR : ',';
        ZERO = new Duration();
        CF_EXT_CAL = createAlternativeDateFormat(true, false);
        CF_EXT_ORD = createAlternativeDateFormat(true, true);
        CF_BAS_CAL = createAlternativeDateFormat(false, false);
        CF_BAS_ORD = createAlternativeDateFormat(false, true);
        TF_EXT = createAlternativeTimeFormat(true);
        TF_BAS = createAlternativeTimeFormat(false);
        ITEM_COMPARATOR = StdNormalizer.comparator();
        STD_PERIOD = StdNormalizer.ofMixedUnits();
        STD_CALENDAR_PERIOD = StdNormalizer.ofCalendarUnits();
        STD_CLOCK_PERIOD = StdNormalizer.ofClockUnits();
        YMD_METRIC = in(CalendarUnit.YEARS, CalendarUnit.MONTHS, CalendarUnit.DAYS);
        CLOCK_METRIC = in(ClockUnit.HOURS, ClockUnit.MINUTES, ClockUnit.SECONDS, ClockUnit.NANOS);
        WEEK_BASED_METRIC = in(CalendarUnit.weekBasedYears(), CalendarUnit.WEEKS, CalendarUnit.DAYS);
    }

    Duration(List<TimeSpan.Item<U>> list, boolean z) {
        boolean isEmpty = list.isEmpty();
        if (isEmpty) {
            this.items = Collections.emptyList();
        } else {
            Collections.sort(list, ITEM_COMPARATOR);
            this.items = Collections.unmodifiableList(list);
        }
        this.negative = !isEmpty && z;
    }

    private Duration(Duration<U> duration, boolean z) {
        this.items = duration.items;
        boolean z2 = duration.negative;
        this.negative = z ? !z2 : z2;
    }

    private Duration() {
        this.items = Collections.emptyList();
        this.negative = false;
    }

    public static <U extends IsoUnit> Duration<U> ofZero() {
        return ZERO;
    }

    public static <U extends IsoUnit> Duration<U> of(long j, U u) {
        if (j == 0) {
            return ofZero();
        }
        if (j < 0) {
            j = MathUtils.safeNegate(j);
        }
        if (u instanceof ClockUnit) {
            char symbol = u.getSymbol();
            if (symbol == '3') {
                u = (U) cast(ClockUnit.NANOS);
                j = MathUtils.safeMultiply(j, MIO);
            } else if (symbol == '6') {
                u = (U) cast(ClockUnit.NANOS);
                j = MathUtils.safeMultiply(j, 1000L);
            }
        }
        ArrayList arrayList = new ArrayList(1);
        arrayList.add(TimeSpan.Item.of(j, u));
        return new Duration<>(arrayList, j < 0);
    }

    public static Builder ofPositive() {
        return new Builder(false);
    }

    public static Builder ofNegative() {
        return new Builder(true);
    }

    public static Duration<CalendarUnit> ofCalendarUnits(int i, int i2, int i3) {
        return ofCalendarUnits(i, i2, i3, false);
    }

    public static Duration<ClockUnit> ofClockUnits(int i, int i2, int i3) {
        return ofClockUnits(i, i2, i3, 0L, false);
    }

    public static <U extends IsoUnit> TimeMetric<U, Duration<U>> in(U... uArr) {
        return new Metric(uArr);
    }

    public static <U extends IsoUnit> TimeMetric<U, Duration<U>> in(Collection<? extends U> collection) {
        return new Metric(collection);
    }

    public static TimeMetric<CalendarUnit, Duration<CalendarUnit>> inYearsMonthsDays() {
        return YMD_METRIC;
    }

    public static TimeMetric<ClockUnit, Duration<ClockUnit>> inClockUnits() {
        return CLOCK_METRIC;
    }

    public static TimeMetric<IsoDateUnit, Duration<IsoDateUnit>> inWeekBasedUnits() {
        return WEEK_BASED_METRIC;
    }

    public static TimeMetric<IsoUnit, Duration<IsoUnit>> in(Timezone timezone, IsoUnit... isoUnitArr) {
        return new ZonalMetric(timezone, isoUnitArr);
    }

    @Override // net.time4j.engine.TimeSpan
    public List<TimeSpan.Item<U>> getTotalLength() {
        return this.items;
    }

    @Override // net.time4j.engine.TimeSpan
    public boolean isNegative() {
        return this.negative;
    }

    @Override // net.time4j.engine.AbstractDuration
    public boolean contains(IsoUnit isoUnit) {
        if (isoUnit == null) {
            return false;
        }
        boolean isFractionUnit = isFractionUnit(isoUnit);
        int size = this.items.size();
        for (int i = 0; i < size; i++) {
            TimeSpan.Item<U> item = this.items.get(i);
            U unit = item.getUnit();
            if (unit.equals(isoUnit) || (isFractionUnit && isFractionUnit(unit))) {
                return item.getAmount() > 0;
            }
        }
        return false;
    }

    @Override // net.time4j.engine.AbstractDuration
    public long getPartialAmount(IsoUnit isoUnit) {
        if (isoUnit == null) {
            return 0L;
        }
        boolean isFractionUnit = isFractionUnit(isoUnit);
        int size = this.items.size();
        for (int i = 0; i < size; i++) {
            TimeSpan.Item<U> item = this.items.get(i);
            U unit = item.getUnit();
            if (unit.equals(isoUnit)) {
                return item.getAmount();
            }
            if (isFractionUnit && isFractionUnit(unit)) {
                int symbol = unit.getSymbol() - '0';
                int symbol2 = isoUnit.getSymbol() - '0';
                int i2 = 1;
                for (int i3 = 0; i3 < Math.abs(symbol - symbol2); i3++) {
                    i2 *= 10;
                }
                if (symbol >= symbol2) {
                    return item.getAmount() / i2;
                }
                return item.getAmount() * i2;
            }
        }
        return 0L;
    }

    public static <U extends IsoUnit, T extends TimePoint<U, T>> Comparator<Duration<? extends U>> comparator(T t) {
        return new LengthComparator(t);
    }

    public static Comparator<Duration<ClockUnit>> comparatorOnClock() {
        return new Comparator<Duration<ClockUnit>>() { // from class: net.time4j.Duration.1
            @Override // java.util.Comparator
            public int compare(Duration<ClockUnit> duration, Duration<ClockUnit> duration2) {
                long lengthInSeconds = Duration.lengthInSeconds(duration);
                long lengthInSeconds2 = Duration.lengthInSeconds(duration2);
                if (lengthInSeconds < lengthInSeconds2) {
                    return -1;
                }
                if (lengthInSeconds > lengthInSeconds2) {
                    return 1;
                }
                long partialAmount = duration.getPartialAmount((ChronoUnit) ClockUnit.NANOS) % Duration.MRD;
                long partialAmount2 = duration2.getPartialAmount((ChronoUnit) ClockUnit.NANOS) % Duration.MRD;
                if (duration.isNegative()) {
                    partialAmount = MathUtils.safeNegate(partialAmount);
                }
                if (duration2.isNegative()) {
                    partialAmount2 = MathUtils.safeNegate(partialAmount2);
                }
                if (partialAmount < partialAmount2) {
                    return -1;
                }
                return partialAmount > partialAmount2 ? 1 : 0;
            }
        };
    }

    public Duration<U> plus(long j, U u) {
        long j2;
        boolean z;
        IsoUnit isoUnit;
        if (u == null) {
            throw new NullPointerException("Missing chronological unit.");
        }
        if (j == 0) {
            return this;
        }
        if (j < 0) {
            j2 = MathUtils.safeNegate(j);
            z = true;
        } else {
            j2 = j;
            z = false;
        }
        ArrayList arrayList = new ArrayList(getTotalLength());
        TimeSpan.Item replaceFraction = replaceFraction(j2, u);
        if (replaceFraction != null) {
            j2 = replaceFraction.getAmount();
            isoUnit = (IsoUnit) replaceFraction.getUnit();
        } else {
            isoUnit = u;
        }
        if (isEmpty()) {
            if (replaceFraction == null) {
                replaceFraction = TimeSpan.Item.of(j2, isoUnit);
            }
            arrayList.add(replaceFraction);
            return new Duration<>(arrayList, z);
        }
        int index = getIndex(isoUnit);
        boolean isNegative = isNegative();
        if (index < 0) {
            if (isNegative() == z) {
                arrayList.add(TimeSpan.Item.of(j2, isoUnit));
            } else {
                return plus(of(j, u));
            }
        } else {
            long safeAdd = MathUtils.safeAdd(MathUtils.safeMultiply(((TimeSpan.Item) arrayList.get(index)).getAmount(), isNegative() ? -1 : 1), MathUtils.safeMultiply(j2, z ? -1 : 1));
            if (safeAdd == 0) {
                arrayList.remove(index);
            } else {
                if (count() != 1) {
                    if (isNegative() != (safeAdd < 0)) {
                        return plus(of(j, u));
                    }
                }
                if (safeAdd < 0) {
                    safeAdd = MathUtils.safeNegate(safeAdd);
                }
                arrayList.set(index, TimeSpan.Item.of(safeAdd, isoUnit));
                isNegative = safeAdd < 0;
            }
        }
        return new Duration<>(arrayList, isNegative);
    }

    public Duration<U> plus(TimeSpan<? extends U> timeSpan) {
        long j;
        long j2;
        long j3;
        Duration<U> merge = merge(this, timeSpan);
        if (merge != null) {
            return merge;
        }
        long[] jArr = {0, 0, 0, 0};
        if (summarize(this, jArr) && summarize(timeSpan, jArr)) {
            long j4 = jArr[0];
            long j5 = jArr[1];
            long j6 = jArr[2];
            long j7 = jArr[3];
            long j8 = 0;
            if (j7 != 0) {
                j = j5;
                j2 = j7;
            } else if (j6 != 0) {
                j = j5;
                j2 = j6;
            } else {
                j = j5;
                j2 = j;
            }
            if (!hasMixedSigns(j4, j2)) {
                boolean z = j4 < 0 || j2 < 0;
                if (z) {
                    j4 = MathUtils.safeNegate(j4);
                    j3 = MathUtils.safeNegate(j);
                    j6 = MathUtils.safeNegate(j6);
                    j7 = MathUtils.safeNegate(j7);
                } else {
                    j3 = j;
                }
                long j9 = j4 / 12;
                long j10 = j4 % 12;
                if (j7 != 0) {
                    j8 = j7 % MRD;
                    j6 = j7 / MRD;
                }
                long j11 = j6 / 3600;
                long j12 = j6 % 3600;
                HashMap hashMap = new HashMap();
                hashMap.put(CalendarUnit.YEARS, Long.valueOf(j9));
                hashMap.put(CalendarUnit.MONTHS, Long.valueOf(j10));
                hashMap.put(CalendarUnit.DAYS, Long.valueOf(j3));
                hashMap.put(ClockUnit.HOURS, Long.valueOf(j11));
                hashMap.put(ClockUnit.MINUTES, Long.valueOf(j12 / 60));
                hashMap.put(ClockUnit.SECONDS, Long.valueOf(j12 % 60));
                hashMap.put(ClockUnit.NANOS, Long.valueOf(j8));
                return create(hashMap, z);
            }
        }
        throw new IllegalStateException("Mixed signs in result time span not allowed: " + this + " PLUS " + timeSpan);
    }

    public Duration<U> with(long j, U u) {
        if (j < 0) {
            j = MathUtils.safeNegate(j);
        }
        TimeSpan.Item replaceFraction = replaceFraction(j, u);
        if (replaceFraction != null) {
            j = replaceFraction.getAmount();
            u = (U) replaceFraction.getUnit();
        }
        return plus(MathUtils.safeSubtract(MathUtils.safeMultiply(j, j < 0 ? -1L : 1L), MathUtils.safeMultiply(getPartialAmount((IsoUnit) u), isNegative() ? -1L : 1L)), u);
    }

    public Duration<U> abs() {
        return isNegative() ? inverse() : this;
    }

    @Override // net.time4j.engine.AbstractDuration
    public Duration<U> inverse() {
        return multipliedBy(-1);
    }

    public Duration<U> multipliedBy(int i) {
        if (!isEmpty()) {
            boolean z = true;
            if (i != 1) {
                if (i == 0) {
                    return ofZero();
                }
                if (i == -1) {
                    return new Duration<>((Duration) this, true);
                }
                ArrayList arrayList = new ArrayList(count());
                int abs = Math.abs(i);
                int count = count();
                for (int i2 = 0; i2 < count; i2++) {
                    TimeSpan.Item<U> item = getTotalLength().get(i2);
                    arrayList.add(TimeSpan.Item.of(MathUtils.safeMultiply(item.getAmount(), abs), item.getUnit()));
                }
                if (i >= 0) {
                    z = isNegative();
                } else if (isNegative()) {
                    z = false;
                }
                return new Duration<>(arrayList, z);
            }
        }
        return this;
    }

    public List<Duration<U>> union(TimeSpan<? extends U> timeSpan) {
        Duration merge = merge(this, timeSpan);
        if (merge == null) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(this);
            arrayList.add(ofZero().plus(timeSpan));
            return Collections.unmodifiableList(arrayList);
        }
        return Collections.singletonList(merge);
    }

    public static Duration<IsoUnit> compose(Duration<CalendarUnit> duration, Duration<ClockUnit> duration2) {
        return ofZero().plus(duration).plus(duration2);
    }

    public Duration<CalendarUnit> toCalendarPeriod() {
        if (isEmpty()) {
            return ofZero();
        }
        ArrayList arrayList = new ArrayList();
        for (TimeSpan.Item<U> item : this.items) {
            if (item.getUnit() instanceof CalendarUnit) {
                arrayList.add(TimeSpan.Item.of(item.getAmount(), CalendarUnit.class.cast(item.getUnit())));
            }
        }
        if (arrayList.isEmpty()) {
            return ofZero();
        }
        return new Duration<>(arrayList, isNegative());
    }

    public Duration<ClockUnit> toClockPeriod() {
        if (isEmpty()) {
            return ofZero();
        }
        ArrayList arrayList = new ArrayList();
        for (TimeSpan.Item<U> item : this.items) {
            if (item.getUnit() instanceof ClockUnit) {
                arrayList.add(TimeSpan.Item.of(item.getAmount(), ClockUnit.class.cast(item.getUnit())));
            }
        }
        if (arrayList.isEmpty()) {
            return ofZero();
        }
        return new Duration<>(arrayList, isNegative());
    }

    public Duration<ClockUnit> toClockPeriodWithDaysAs24Hours() {
        if (isEmpty()) {
            return ofZero();
        }
        ArrayList arrayList = new ArrayList();
        long j = 0;
        for (TimeSpan.Item<U> item : this.items) {
            if (item.getUnit() instanceof ClockUnit) {
                arrayList.add(TimeSpan.Item.of(item.getAmount(), ClockUnit.class.cast(item.getUnit())));
            } else if (item.getUnit().equals(CalendarUnit.DAYS)) {
                j = MathUtils.safeMultiply(item.getAmount(), 24L);
            }
        }
        if (j != 0) {
            int size = arrayList.size();
            boolean z = false;
            int i = 0;
            while (true) {
                if (i >= size) {
                    break;
                }
                TimeSpan.Item item2 = (TimeSpan.Item) arrayList.get(i);
                if (item2.getUnit() == ClockUnit.HOURS) {
                    arrayList.set(i, TimeSpan.Item.of(MathUtils.safeAdd(item2.getAmount(), j), ClockUnit.HOURS));
                    z = true;
                    break;
                }
                i++;
            }
            if (!z) {
                arrayList.add(TimeSpan.Item.of(j, ClockUnit.HOURS));
            }
        } else if (arrayList.isEmpty()) {
            return ofZero();
        }
        return new Duration<>(arrayList, isNegative());
    }

    public Duration<U> with(Normalizer<U> normalizer) {
        return convert(normalizer.normalize2(this));
    }

    public Duration<U> truncatedTo(U u) {
        if (isEmpty()) {
            return ofZero();
        }
        double length = u.getLength();
        ArrayList arrayList = new ArrayList();
        for (TimeSpan.Item<U> item : this.items) {
            if (Double.compare(item.getUnit().getLength(), length) < 0) {
                break;
            }
            arrayList.add(item);
        }
        if (arrayList.isEmpty()) {
            return ofZero();
        }
        return new Duration<>(arrayList, isNegative());
    }

    public static Normalizer<IsoUnit> approximateHours(int i) {
        return new ApproximateNormalizer(i, ClockUnit.HOURS);
    }

    public static Normalizer<IsoUnit> approximateMinutes(int i) {
        return new ApproximateNormalizer(i, ClockUnit.MINUTES);
    }

    public static Normalizer<IsoUnit> approximateSeconds(int i) {
        return new ApproximateNormalizer(i, ClockUnit.SECONDS);
    }

    public static Normalizer<IsoUnit> approximateMaxUnitOnly() {
        return new ApproximateNormalizer(false);
    }

    public static Normalizer<IsoUnit> approximateMaxUnitOrWeeks() {
        return new ApproximateNormalizer(true);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Duration)) {
            return false;
        }
        Duration duration = (Duration) Duration.class.cast(obj);
        return this.negative == duration.negative && getTotalLength().equals(duration.getTotalLength());
    }

    public int hashCode() {
        int hashCode = getTotalLength().hashCode();
        return this.negative ? hashCode ^ hashCode : hashCode;
    }

    @Override // net.time4j.engine.AbstractDuration
    public String toString() {
        return toString(0);
    }

    public String toStringISO() {
        return toString(1);
    }

    public String toStringXML() {
        return toString(2);
    }

    public static Duration<IsoUnit> parsePeriod(String str) throws ParseException {
        return parsePeriod(str, IsoUnit.class);
    }

    public static Duration<CalendarUnit> parseCalendarPeriod(String str) throws ParseException {
        return parsePeriod(str, CalendarUnit.class);
    }

    public static Duration<ClockUnit> parseClockPeriod(String str) throws ParseException {
        return parsePeriod(str, ClockUnit.class);
    }

    public static Duration<IsoDateUnit> parseWeekBasedPeriod(String str) throws ParseException {
        return parsePeriod(str, IsoDateUnit.class);
    }

    public static Formatter<IsoUnit> formatter(String str) {
        return Formatter.ofPattern(str);
    }

    public static <U extends IsoUnit> Formatter<U> formatter(Class<U> cls, String str) {
        return Formatter.ofPattern(cls, str);
    }

    /* JADX WARN: Removed duplicated region for block: B:114:0x0201  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.lang.String toString(int r22) {
        /*
            Method dump skipped, instructions count: 542
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: net.time4j.Duration.toString(int):java.lang.String");
    }

    private int count() {
        return getTotalLength().size();
    }

    private static <U> boolean isEmpty(TimeSpan<U> timeSpan) {
        List<TimeSpan.Item<U>> totalLength = timeSpan.getTotalLength();
        int size = totalLength.size();
        for (int i = 0; i < size; i++) {
            if (totalLength.get(i).getAmount() > 0) {
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static long lengthInSeconds(Duration<ClockUnit> duration) {
        long safeAdd = MathUtils.safeAdd(MathUtils.safeAdd(MathUtils.safeAdd(MathUtils.safeMultiply(duration.getPartialAmount((ChronoUnit) ClockUnit.HOURS), 3600L), MathUtils.safeMultiply(duration.getPartialAmount((ChronoUnit) ClockUnit.MINUTES), 60L)), duration.getPartialAmount((ChronoUnit) ClockUnit.SECONDS)), duration.getPartialAmount((ChronoUnit) ClockUnit.NANOS) / MRD);
        return duration.isNegative() ? MathUtils.safeNegate(safeAdd) : safeAdd;
    }

    private static Duration<CalendarUnit> ofCalendarUnits(long j, long j2, long j3, boolean z) {
        ArrayList arrayList = new ArrayList(3);
        if (j != 0) {
            arrayList.add(TimeSpan.Item.of(j, CalendarUnit.YEARS));
        }
        if (j2 != 0) {
            arrayList.add(TimeSpan.Item.of(j2, CalendarUnit.MONTHS));
        }
        if (j3 != 0) {
            arrayList.add(TimeSpan.Item.of(j3, CalendarUnit.DAYS));
        }
        return new Duration<>(arrayList, z);
    }

    private static Duration<ClockUnit> ofClockUnits(long j, long j2, long j3, long j4, boolean z) {
        ArrayList arrayList = new ArrayList(4);
        if (j != 0) {
            arrayList.add(TimeSpan.Item.of(j, ClockUnit.HOURS));
        }
        if (j2 != 0) {
            arrayList.add(TimeSpan.Item.of(j2, ClockUnit.MINUTES));
        }
        if (j3 != 0) {
            arrayList.add(TimeSpan.Item.of(j3, ClockUnit.SECONDS));
        }
        if (j4 != 0) {
            arrayList.add(TimeSpan.Item.of(j4, ClockUnit.NANOS));
        }
        return new Duration<>(arrayList, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <U extends IsoUnit> Duration<U> create(Map<U, Long> map, boolean z) {
        if (map.isEmpty()) {
            return ofZero();
        }
        ArrayList arrayList = new ArrayList(map.size());
        long j = 0;
        for (Map.Entry<U, Long> entry : map.entrySet()) {
            long longValue = entry.getValue().longValue();
            if (longValue != 0) {
                U key = entry.getKey();
                if (key == ClockUnit.MILLIS) {
                    j = MathUtils.safeAdd(j, MathUtils.safeMultiply(longValue, MIO));
                } else if (key == ClockUnit.MICROS) {
                    j = MathUtils.safeAdd(j, MathUtils.safeMultiply(longValue, 1000L));
                } else if (key == ClockUnit.NANOS) {
                    j = MathUtils.safeAdd(j, longValue);
                } else {
                    arrayList.add(TimeSpan.Item.of(longValue, key));
                }
            }
        }
        if (j != 0) {
            arrayList.add(TimeSpan.Item.of(j, (IsoUnit) cast(ClockUnit.NANOS)));
        } else if (arrayList.isEmpty()) {
            return ofZero();
        }
        return new Duration<>(arrayList, z);
    }

    private int getIndex(ChronoUnit chronoUnit) {
        return getIndex(chronoUnit, getTotalLength());
    }

    private static <U extends ChronoUnit> int getIndex(ChronoUnit chronoUnit, List<TimeSpan.Item<U>> list) {
        int size = list.size() - 1;
        int i = 0;
        while (i <= size) {
            int i2 = (i + size) >>> 1;
            int compare = StdNormalizer.compare((ChronoUnit) list.get(i2).getUnit(), chronoUnit);
            if (compare < 0) {
                i = i2 + 1;
            } else {
                if (compare <= 0) {
                    return i2;
                }
                size = i2 - 1;
            }
        }
        return -1;
    }

    private static <U extends IsoUnit> TimeSpan.Item<U> replaceFraction(long j, U u) {
        long safeMultiply;
        IsoUnit isoUnit;
        if (u.equals(ClockUnit.MILLIS)) {
            safeMultiply = MathUtils.safeMultiply(j, MIO);
            isoUnit = (IsoUnit) cast(ClockUnit.NANOS);
        } else {
            if (!u.equals(ClockUnit.MICROS)) {
                return null;
            }
            safeMultiply = MathUtils.safeMultiply(j, 1000L);
            isoUnit = (IsoUnit) cast(ClockUnit.NANOS);
        }
        return TimeSpan.Item.of(safeMultiply, isoUnit);
    }

    private static <U extends IsoUnit> Duration<U> merge(Duration<U> duration, TimeSpan<? extends U> timeSpan) {
        if (duration.isEmpty()) {
            if (isEmpty(timeSpan)) {
                return duration;
            }
            if (timeSpan instanceof Duration) {
                return (Duration) cast(timeSpan);
            }
        }
        HashMap hashMap = new HashMap();
        int count = duration.count();
        int i = 0;
        while (true) {
            int i2 = -1;
            if (i >= count) {
                break;
            }
            TimeSpan.Item<U> item = duration.getTotalLength().get(i);
            U unit = item.getUnit();
            long amount = item.getAmount();
            if (!duration.isNegative()) {
                i2 = 1;
            }
            hashMap.put(unit, Long.valueOf(MathUtils.safeMultiply(amount, i2)));
            i++;
        }
        boolean isNegative = timeSpan.isNegative();
        int size = timeSpan.getTotalLength().size();
        for (int i3 = 0; i3 < size; i3++) {
            TimeSpan.Item<? extends U> item2 = timeSpan.getTotalLength().get(i3);
            U unit2 = item2.getUnit();
            long amount2 = item2.getAmount();
            TimeSpan.Item replaceFraction = replaceFraction(amount2, unit2);
            if (replaceFraction != null) {
                amount2 = replaceFraction.getAmount();
                unit2 = (U) replaceFraction.getUnit();
            }
            if (hashMap.containsKey(unit2)) {
                hashMap.put(unit2, Long.valueOf(MathUtils.safeAdd(((Long) hashMap.get(unit2)).longValue(), MathUtils.safeMultiply(amount2, isNegative ? -1 : 1))));
            } else {
                hashMap.put(unit2, Long.valueOf(MathUtils.safeMultiply(amount2, isNegative ? -1 : 1)));
            }
        }
        if (duration.isNegative() != isNegative) {
            Iterator it = hashMap.entrySet().iterator();
            isNegative = false;
            boolean z = true;
            while (it.hasNext()) {
                boolean z2 = ((Long) ((Map.Entry) it.next()).getValue()).longValue() < 0;
                if (z) {
                    isNegative = z2;
                    z = false;
                } else if (isNegative != z2) {
                    return null;
                }
            }
        }
        if (isNegative) {
            for (Map.Entry entry : hashMap.entrySet()) {
                long longValue = ((Long) entry.getValue()).longValue();
                Object key = entry.getKey();
                if (longValue < 0) {
                    longValue = MathUtils.safeNegate(longValue);
                }
                hashMap.put(key, Long.valueOf(longValue));
            }
        }
        return create(hashMap, isNegative);
    }

    private static <U extends IsoUnit> boolean summarize(TimeSpan<? extends U> timeSpan, long[] jArr) {
        long j;
        long j2;
        long j3;
        long j4 = jArr[0];
        long j5 = jArr[1];
        long j6 = jArr[2];
        long j7 = jArr[3];
        for (TimeSpan.Item<? extends U> item : timeSpan.getTotalLength()) {
            U unit = item.getUnit();
            long amount = item.getAmount();
            if (timeSpan.isNegative()) {
                amount = MathUtils.safeNegate(amount);
            }
            long j8 = j6;
            long j9 = amount;
            if (unit instanceof CalendarUnit) {
                CalendarUnit calendarUnit = (CalendarUnit) CalendarUnit.class.cast(unit);
                switch (AnonymousClass2.$SwitchMap$net$time4j$CalendarUnit[calendarUnit.ordinal()]) {
                    case 1:
                        j4 = MathUtils.safeAdd(j4, MathUtils.safeMultiply(j9, 12000L));
                        break;
                    case 2:
                        j4 = MathUtils.safeAdd(j4, MathUtils.safeMultiply(j9, 1200L));
                        break;
                    case 3:
                        j4 = MathUtils.safeAdd(j4, MathUtils.safeMultiply(j9, 120L));
                        break;
                    case 4:
                        j4 = MathUtils.safeAdd(j4, MathUtils.safeMultiply(j9, 12L));
                        break;
                    case 5:
                        j4 = MathUtils.safeAdd(j4, MathUtils.safeMultiply(j9, 3L));
                        break;
                    case 6:
                        j4 = MathUtils.safeAdd(j4, j9);
                        break;
                    case 7:
                        j5 = MathUtils.safeAdd(j5, MathUtils.safeMultiply(j9, 7L));
                        break;
                    case 8:
                        j5 = MathUtils.safeAdd(j5, j9);
                        break;
                    default:
                        throw new UnsupportedOperationException(calendarUnit.name());
                }
            } else {
                if (!(unit instanceof ClockUnit)) {
                    return false;
                }
                ClockUnit clockUnit = (ClockUnit) ClockUnit.class.cast(unit);
                switch (AnonymousClass2.$SwitchMap$net$time4j$ClockUnit[clockUnit.ordinal()]) {
                    case 1:
                        j3 = j5;
                        j6 = MathUtils.safeAdd(j8, MathUtils.safeMultiply(j9, 3600L));
                        j5 = j3;
                    case 2:
                        j3 = j5;
                        j6 = MathUtils.safeAdd(j8, MathUtils.safeMultiply(j9, 60L));
                        j5 = j3;
                    case 3:
                        j6 = MathUtils.safeAdd(j8, j9);
                        j3 = j5;
                        j5 = j3;
                    case 4:
                        j7 = MathUtils.safeAdd(j7, MathUtils.safeMultiply(j9, MIO));
                        break;
                    case 5:
                        j7 = MathUtils.safeAdd(j7, MathUtils.safeMultiply(j9, 1000L));
                        break;
                    case 6:
                        j7 = MathUtils.safeAdd(j7, j9);
                        break;
                    default:
                        throw new UnsupportedOperationException(clockUnit.name());
                }
            }
            j6 = j8;
            j3 = j5;
            j5 = j3;
        }
        long j10 = j5;
        long j11 = j6;
        long j12 = 0;
        if (j7 != 0) {
            j = j4;
            j7 = MathUtils.safeAdd(MathUtils.safeAdd(j7, MathUtils.safeMultiply(j10, 86400000000000L)), MathUtils.safeMultiply(j11, MRD));
            j2 = 0;
        } else {
            j = j4;
            if (j11 != 0) {
                j2 = MathUtils.safeAdd(j11, MathUtils.safeMultiply(j10, 86400L));
            } else {
                j12 = j10;
                j2 = j11;
            }
        }
        jArr[0] = j;
        jArr[1] = j12;
        jArr[2] = j2;
        jArr[3] = j7;
        return true;
    }

    /* renamed from: net.time4j.Duration$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$net$time4j$CalendarUnit;
        static final /* synthetic */ int[] $SwitchMap$net$time4j$ClockUnit;

        static {
            int[] iArr = new int[ClockUnit.values().length];
            $SwitchMap$net$time4j$ClockUnit = iArr;
            try {
                iArr[ClockUnit.HOURS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$net$time4j$ClockUnit[ClockUnit.MINUTES.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$net$time4j$ClockUnit[ClockUnit.SECONDS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$net$time4j$ClockUnit[ClockUnit.MILLIS.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$net$time4j$ClockUnit[ClockUnit.MICROS.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$net$time4j$ClockUnit[ClockUnit.NANOS.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            int[] iArr2 = new int[CalendarUnit.values().length];
            $SwitchMap$net$time4j$CalendarUnit = iArr2;
            try {
                iArr2[CalendarUnit.MILLENNIA.ordinal()] = 1;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$net$time4j$CalendarUnit[CalendarUnit.CENTURIES.ordinal()] = 2;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$net$time4j$CalendarUnit[CalendarUnit.DECADES.ordinal()] = 3;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$net$time4j$CalendarUnit[CalendarUnit.YEARS.ordinal()] = 4;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$net$time4j$CalendarUnit[CalendarUnit.QUARTERS.ordinal()] = 5;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$net$time4j$CalendarUnit[CalendarUnit.MONTHS.ordinal()] = 6;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$net$time4j$CalendarUnit[CalendarUnit.WEEKS.ordinal()] = 7;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$net$time4j$CalendarUnit[CalendarUnit.DAYS.ordinal()] = 8;
            } catch (NoSuchFieldError unused14) {
            }
        }
    }

    private static <U extends IsoUnit> Duration<U> convert(TimeSpan<U> timeSpan) {
        if (timeSpan instanceof Duration) {
            return (Duration) cast(timeSpan);
        }
        return ofZero().plus(timeSpan);
    }

    private boolean isFractionUnit(IsoUnit isoUnit) {
        char symbol = isoUnit.getSymbol();
        return symbol >= '1' && symbol <= '9';
    }

    private static <U extends IsoUnit> Duration<U> parsePeriod(String str, Class<U> cls) throws ParseException {
        int i;
        boolean z;
        int i2;
        boolean z2;
        int i3 = 0;
        if (str.length() == 0) {
            throw new ParseException("Empty period string.", 0);
        }
        if (str.charAt(0) == '-') {
            i = 1;
            z = true;
        } else {
            i = 0;
            z = false;
        }
        try {
            if (str.charAt(i) != 'P') {
                throw new ParseException("Format symbol 'P' expected: " + str, i);
            }
            int i4 = i + 1;
            ArrayList arrayList = new ArrayList();
            int indexOf = str.indexOf(84, i4);
            boolean z3 = indexOf == -1;
            if (cls == CalendarUnit.class) {
                i2 = 0;
            } else if (cls == ClockUnit.class) {
                i2 = 1;
            } else {
                i2 = cls == IsoDateUnit.class ? 2 : -1;
            }
            if (!z3) {
                if (indexOf <= i4) {
                    z2 = false;
                } else {
                    if (i2 == 1) {
                        throw new ParseException("Unexpected date component found: " + str, i4);
                    }
                    z2 = parse(str.substring(0, indexOf), i4, indexOf, 0, arrayList);
                }
                if (cls == CalendarUnit.class) {
                    throw new ParseException("Unexpected time component found: " + str, indexOf);
                }
                if (z2) {
                    parseAlt(str, indexOf + 1, str.length(), false, arrayList);
                } else {
                    parse(str, indexOf + 1, str.length(), 1, arrayList);
                }
            } else {
                if (i2 == 1) {
                    throw new ParseException("Format symbol 'T' expected: " + str, i4);
                }
                int length = str.length();
                if (i2 != -1) {
                    i3 = i2;
                }
                parse(str, i4, length, i3, arrayList);
            }
            return new Duration<>(arrayList, z);
        } catch (IndexOutOfBoundsException e) {
            ParseException parseException = new ParseException("Unexpected termination of period string: " + str, i);
            parseException.initCause(e);
            throw parseException;
        }
    }

    private static <U extends ChronoUnit> boolean parse(String str, int i, int i2, int i3, List<TimeSpan.Item<U>> list) throws ParseException {
        int i4;
        int i5;
        ChronoUnit parseDateSymbol;
        char charAt = str.charAt(i2 - 1);
        char c = '9';
        char c2 = '0';
        if (charAt >= '0' && charAt <= '9' && i3 != 2) {
            parseAlt(str, i, i2, i3 == 0, list);
            return true;
        }
        if (i == i2) {
            throw new ParseException(str, i);
        }
        int i6 = i;
        int i7 = i6;
        ChronoUnit chronoUnit = null;
        StringBuilder sb = null;
        boolean z = false;
        boolean z2 = false;
        while (i7 < i2) {
            char charAt2 = str.charAt(i7);
            if (charAt2 >= c2 && charAt2 <= c) {
                if (sb == null) {
                    sb = new StringBuilder();
                    i6 = i7;
                    z = false;
                }
                sb.append(charAt2);
                i4 = i7;
            } else if (charAt2 == ',' || charAt2 == '.') {
                i4 = i7;
                int i8 = i6;
                if (sb == null || i3 != 1) {
                    throw new ParseException("Decimal separator misplaced: " + str, i4);
                }
                chronoUnit = addParsedItem(ClockUnit.SECONDS, chronoUnit, parseAmount(str, sb.toString(), i8), str, i4, list);
                i6 = i8;
                sb = null;
                z = true;
                z2 = true;
            } else {
                if (z) {
                    throw new ParseException("Unexpected char '" + charAt2 + "' found: " + str, i7);
                }
                if (z2) {
                    if (charAt2 != 'S') {
                        throw new ParseException("Second symbol expected: " + str, i7);
                    }
                    if (sb == null) {
                        throw new ParseException("Decimal separator misplaced: " + str, i7 - 1);
                    }
                    if (sb.length() > 9) {
                        sb.delete(9, sb.length());
                    }
                    for (int length = sb.length(); length < 9; length++) {
                        sb.append(c2);
                    }
                    i5 = i6;
                    i4 = i7;
                    chronoUnit = addParsedItem(ClockUnit.NANOS, chronoUnit, parseAmount(str, sb.toString(), i6), str, i7, list);
                } else {
                    i5 = i6;
                    i4 = i7;
                    long parseAmount = parseAmount(str, sb == null ? String.valueOf(charAt2) : sb.toString(), i5);
                    if (i3 == 1) {
                        parseDateSymbol = parseTimeSymbol(charAt2, str, i4);
                    } else if (i3 == 2) {
                        parseDateSymbol = parseWeekBasedSymbol(charAt2, str, i4);
                    } else {
                        parseDateSymbol = parseDateSymbol(charAt2, str, i4);
                    }
                    chronoUnit = addParsedItem(parseDateSymbol, chronoUnit, parseAmount, str, i4, list);
                }
                i6 = i5;
                sb = null;
                z = true;
            }
            i7 = i4 + 1;
            c = '9';
            c2 = '0';
        }
        if (z) {
            return false;
        }
        throw new ParseException("Unit symbol expected: " + str, i2);
    }

    /* JADX WARN: Type inference failed for: r10v6 */
    /* JADX WARN: Type inference failed for: r10v7, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r10v9 */
    /* JADX WARN: Type inference failed for: r5v0 */
    /* JADX WARN: Type inference failed for: r5v1 */
    /* JADX WARN: Type inference failed for: r5v2, types: [boolean, int] */
    private static <U extends ChronoUnit> void parseAlt(String str, int i, int i2, boolean z, List<TimeSpan.Item<U>> list) throws ParseException {
        long partialAmount;
        long j;
        r5 = true;
        boolean z2 = true;
        if (z) {
            int i3 = i + 4;
            ?? r10 = (i3 >= i2 || str.charAt(i3) != '-') ? 0 : 1;
            if (r10 == 0 ? i + 7 != i2 : i + 8 != i2) {
                z2 = false;
            }
            Duration parse = getAlternativeDateFormat(r10, z2).parse(str, i);
            long partialAmount2 = parse.getPartialAmount((IsoUnit) CalendarUnit.YEARS);
            if (z2) {
                j = parse.getPartialAmount((IsoUnit) CalendarUnit.DAYS);
                partialAmount = 0;
            } else {
                partialAmount = parse.getPartialAmount((IsoUnit) CalendarUnit.MONTHS);
                long partialAmount3 = parse.getPartialAmount((IsoUnit) CalendarUnit.DAYS);
                if (partialAmount > 12) {
                    throw new ParseException("ISO-8601 prohibits months-part > 12: " + str, i3 + r10);
                }
                if (partialAmount3 > 30) {
                    throw new ParseException("ISO-8601 prohibits days-part > 30: " + str, i + 6 + (r10 == 0 ? 0 : 2));
                }
                j = partialAmount3;
            }
            if (partialAmount2 > 0) {
                list.add(TimeSpan.Item.of(partialAmount2, (ChronoUnit) cast(CalendarUnit.YEARS)));
            }
            if (partialAmount > 0) {
                list.add(TimeSpan.Item.of(partialAmount, (ChronoUnit) cast(CalendarUnit.MONTHS)));
            }
            if (j > 0) {
                list.add(TimeSpan.Item.of(j, (ChronoUnit) cast(CalendarUnit.DAYS)));
                return;
            }
            return;
        }
        int i4 = i + 2;
        ?? r5 = (i4 >= i2 || str.charAt(i4) != ':') ? 0 : 1;
        Duration parse2 = getAlternativeTimeFormat(r5).parse(str, i);
        long partialAmount4 = parse2.getPartialAmount((IsoUnit) ClockUnit.HOURS);
        if (partialAmount4 > 0) {
            if (partialAmount4 > 24) {
                throw new ParseException("ISO-8601 prohibits hours-part > 24: " + str, i);
            }
            list.add(TimeSpan.Item.of(partialAmount4, (ChronoUnit) cast(ClockUnit.HOURS)));
        }
        long partialAmount5 = parse2.getPartialAmount((IsoUnit) ClockUnit.MINUTES);
        if (partialAmount5 > 0) {
            if (partialAmount5 > 60) {
                throw new ParseException("ISO-8601 prohibits minutes-part > 60: " + str, i4 + r5);
            }
            list.add(TimeSpan.Item.of(partialAmount5, (ChronoUnit) cast(ClockUnit.MINUTES)));
        }
        long partialAmount6 = parse2.getPartialAmount((IsoUnit) ClockUnit.SECONDS);
        if (partialAmount6 > 0) {
            if (partialAmount6 > 60) {
                throw new ParseException("ISO-8601 prohibits seconds-part > 60: " + str, i + 4 + (r5 == 0 ? 0 : 2));
            }
            list.add(TimeSpan.Item.of(partialAmount6, (ChronoUnit) cast(ClockUnit.SECONDS)));
        }
        long partialAmount7 = parse2.getPartialAmount((IsoUnit) ClockUnit.NANOS);
        if (partialAmount7 > 0) {
            list.add(TimeSpan.Item.of(partialAmount7, (ChronoUnit) cast(ClockUnit.NANOS)));
        }
    }

    private static Formatter<CalendarUnit> createAlternativeDateFormat(boolean z, boolean z2) {
        return Formatter.ofPattern(CalendarUnit.class, z ? z2 ? "YYYY-DDD" : "YYYY-MM-DD" : z2 ? "YYYYDDD" : "YYYYMMDD");
    }

    private static Formatter<CalendarUnit> getAlternativeDateFormat(boolean z, boolean z2) {
        return z ? z2 ? CF_EXT_ORD : CF_EXT_CAL : z2 ? CF_BAS_ORD : CF_BAS_CAL;
    }

    private static Formatter<ClockUnit> createAlternativeTimeFormat(boolean z) {
        return Formatter.ofPattern(ClockUnit.class, z ? "hh[:mm[:ss[,fffffffff]]]" : "hh[mm[ss[,fffffffff]]]");
    }

    private static Formatter<ClockUnit> getAlternativeTimeFormat(boolean z) {
        return z ? TF_EXT : TF_BAS;
    }

    private static CalendarUnit parseDateSymbol(char c, String str, int i) throws ParseException {
        if (c == 'I') {
            return CalendarUnit.MILLENNIA;
        }
        if (c == 'M') {
            return CalendarUnit.MONTHS;
        }
        if (c == 'Q') {
            return CalendarUnit.QUARTERS;
        }
        if (c == 'W') {
            return CalendarUnit.WEEKS;
        }
        if (c != 'Y') {
            switch (c) {
                case 'C':
                    return CalendarUnit.CENTURIES;
                case 'D':
                    return CalendarUnit.DAYS;
                case 'E':
                    return CalendarUnit.DECADES;
                default:
                    throw new ParseException("Symbol '" + c + "' not supported: " + str, i);
            }
        }
        return CalendarUnit.YEARS;
    }

    private static ClockUnit parseTimeSymbol(char c, String str, int i) throws ParseException {
        if (c == 'H') {
            return ClockUnit.HOURS;
        }
        if (c == 'M') {
            return ClockUnit.MINUTES;
        }
        if (c == 'S') {
            return ClockUnit.SECONDS;
        }
        throw new ParseException("Symbol '" + c + "' not supported: " + str, i);
    }

    private static IsoDateUnit parseWeekBasedSymbol(char c, String str, int i) throws ParseException {
        if (c == 'D') {
            return CalendarUnit.DAYS;
        }
        if (c == 'W') {
            return CalendarUnit.WEEKS;
        }
        if (c == 'Y') {
            return CalendarUnit.weekBasedYears();
        }
        throw new ParseException("Symbol '" + c + "' not supported: " + str, i);
    }

    private static <U extends ChronoUnit> ChronoUnit addParsedItem(ChronoUnit chronoUnit, ChronoUnit chronoUnit2, long j, String str, int i, List<TimeSpan.Item<U>> list) throws ParseException {
        if (chronoUnit2 == null || Double.compare(chronoUnit.getLength(), chronoUnit2.getLength()) < 0) {
            if (j != 0) {
                list.add(TimeSpan.Item.of(j, (ChronoUnit) cast(chronoUnit)));
            }
            return chronoUnit;
        }
        if (Double.compare(chronoUnit.getLength(), chronoUnit2.getLength()) == 0) {
            throw new ParseException("Duplicate unit items: " + str, i);
        }
        throw new ParseException("Wrong order of unit items: " + str, i);
    }

    private static long parseAmount(String str, String str2, int i) throws ParseException {
        try {
            return Long.parseLong(str2);
        } catch (NumberFormatException e) {
            ParseException parseException = new ParseException(str, i);
            parseException.initCause(e);
            throw parseException;
        }
    }

    private Object writeReplace() {
        return new SPX(this, 6);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException {
        throw new InvalidObjectException("Serialization proxy required.");
    }

    public static class Builder {
        private final boolean negative;
        private boolean millisSet = false;
        private boolean microsSet = false;
        private boolean nanosSet = false;
        private final List<TimeSpan.Item<IsoUnit>> items = new ArrayList(10);

        Builder(boolean z) {
            this.negative = z;
        }

        public Builder years(int i) {
            set(i, CalendarUnit.YEARS);
            return this;
        }

        public Builder months(int i) {
            set(i, CalendarUnit.MONTHS);
            return this;
        }

        public Builder days(int i) {
            set(i, CalendarUnit.DAYS);
            return this;
        }

        public Builder hours(int i) {
            set(i, ClockUnit.HOURS);
            return this;
        }

        public Builder minutes(int i) {
            set(i, ClockUnit.MINUTES);
            return this;
        }

        public Builder seconds(int i) {
            set(i, ClockUnit.SECONDS);
            return this;
        }

        public Builder millis(int i) {
            millisCalled();
            update(i, Duration.MIO);
            return this;
        }

        public Builder micros(int i) {
            microsCalled();
            update(i, 1000L);
            return this;
        }

        public Builder nanos(int i) {
            nanosCalled();
            update(i, 1L);
            return this;
        }

        public Duration<IsoUnit> build() {
            if (this.items.isEmpty()) {
                throw new IllegalStateException("Not set any amount and unit.");
            }
            return new Duration<>(this.items, this.negative);
        }

        private Builder set(long j, IsoUnit isoUnit) {
            int size = this.items.size();
            for (int i = 0; i < size; i++) {
                if (this.items.get(i).getUnit() == isoUnit) {
                    throw new IllegalStateException("Already registered: " + isoUnit);
                }
            }
            if (j != 0) {
                this.items.add(TimeSpan.Item.of(j, isoUnit));
            }
            return this;
        }

        private void update(long j, long j2) {
            if (j >= 0) {
                ClockUnit clockUnit = ClockUnit.NANOS;
                for (int size = this.items.size() - 1; size >= 0; size--) {
                    TimeSpan.Item<IsoUnit> item = this.items.get(size);
                    if (item.getUnit().equals(ClockUnit.NANOS)) {
                        this.items.set(size, TimeSpan.Item.of(MathUtils.safeAdd(MathUtils.safeMultiply(j, j2), item.getAmount()), clockUnit));
                        return;
                    }
                }
                if (j != 0) {
                    this.items.add(TimeSpan.Item.of(MathUtils.safeMultiply(j, j2), clockUnit));
                    return;
                }
                return;
            }
            throw new IllegalArgumentException("Illegal negative amount: " + j);
        }

        private void millisCalled() {
            if (this.millisSet) {
                throw new IllegalStateException("Called twice for: " + ClockUnit.MILLIS.name());
            }
            this.millisSet = true;
        }

        private void microsCalled() {
            if (this.microsSet) {
                throw new IllegalStateException("Called twice for: " + ClockUnit.MICROS.name());
            }
            this.microsSet = true;
        }

        private void nanosCalled() {
            if (this.nanosSet) {
                throw new IllegalStateException("Called twice for: " + ClockUnit.NANOS.name());
            }
            this.nanosSet = true;
        }
    }

    private static class ZonalMetric implements TimeMetric<IsoUnit, Duration<IsoUnit>> {
        private final TimeMetric<IsoUnit, Duration<IsoUnit>> metric;
        private final Timezone tz;

        private ZonalMetric(Timezone timezone, IsoUnit... isoUnitArr) {
            if (timezone == null) {
                throw new NullPointerException("Missing timezone.");
            }
            this.tz = timezone;
            this.metric = new Metric(isoUnitArr);
        }

        @Override // net.time4j.engine.TimeMetric
        public <T extends TimePoint<? super IsoUnit, T>> Duration<IsoUnit> between(T t, T t2) {
            boolean z;
            if (t.compareTo(t2) > 0) {
                z = true;
                t2 = t;
                t = t2;
            } else {
                z = false;
            }
            Duration<IsoUnit> between = this.metric.between(t, t2.plus(getOffset(t) - getOffset(t2), ClockUnit.SECONDS));
            return z ? between.inverse() : between;
        }

        @Override // net.time4j.engine.TimeMetric
        public TimeMetric<IsoUnit, Duration<IsoUnit>> reversible() {
            throw new UnsupportedOperationException("Not reversible.");
        }

        private int getOffset(ChronoEntity<?> chronoEntity) {
            return this.tz.getStrategy().getOffset((GregorianDate) chronoEntity.get(PlainDate.COMPONENT), (WallTime) chronoEntity.get(PlainTime.COMPONENT), this.tz).getIntegralAmount();
        }
    }

    public static final class Formatter<U extends IsoUnit> extends TimeSpanFormatter<U, Duration<U>> {
        private static final String JODA_PATTERN = "'P'[-#################Y'Y'][-#################M'M'][-#################W'W'][-#################D'D']['T'[-#################h'H'][-#################m'M'][-#################s'S'[.fffffffff]]]";

        private Formatter(Class<U> cls, String str) {
            super(cls, str);
        }

        public static Formatter<IsoUnit> ofJodaStyle() {
            return ofPattern(IsoUnit.class, JODA_PATTERN);
        }

        public static Formatter<IsoUnit> ofPattern(String str) {
            return ofPattern(IsoUnit.class, str);
        }

        public static <U extends IsoUnit> Formatter<U> ofPattern(Class<U> cls, String str) {
            return new Formatter<>(cls, str);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // net.time4j.format.TimeSpanFormatter
        public Duration<U> convert(Map<U, Long> map, boolean z) {
            return Duration.create(map, z);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // net.time4j.format.TimeSpanFormatter
        public U getUnit(char c) {
            if (c == 'I') {
                return CalendarUnit.MILLENNIA;
            }
            if (c == 'M') {
                return CalendarUnit.MONTHS;
            }
            if (c == 'Q') {
                return CalendarUnit.QUARTERS;
            }
            if (c == 'W') {
                return CalendarUnit.WEEKS;
            }
            if (c == 'Y') {
                return CalendarUnit.YEARS;
            }
            if (c == 'f') {
                return ClockUnit.NANOS;
            }
            if (c == 'h') {
                return ClockUnit.HOURS;
            }
            if (c == 'm') {
                return ClockUnit.MINUTES;
            }
            if (c != 's') {
                switch (c) {
                    case 'C':
                        return CalendarUnit.CENTURIES;
                    case 'D':
                        return CalendarUnit.DAYS;
                    case 'E':
                        return CalendarUnit.DECADES;
                    default:
                        throw new IllegalArgumentException("Unsupported pattern symbol: " + c);
                }
            }
            return ClockUnit.SECONDS;
        }
    }

    private static class Metric<U extends IsoUnit> extends AbstractMetric<U, Duration<U>> {
        private Metric(U... uArr) {
            super(uArr.length > 1, uArr);
        }

        private Metric(Collection<? extends U> collection) {
            super(collection.size() > 1, collection);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // net.time4j.engine.AbstractMetric
        public Duration<U> createEmptyTimeSpan() {
            return Duration.ofZero();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // net.time4j.engine.AbstractMetric
        public Duration<U> createTimeSpan(List<TimeSpan.Item<U>> list, boolean z) {
            return new Duration<>(list, z);
        }

        @Override // net.time4j.engine.AbstractMetric
        protected TimeSpan.Item<U> resolve(TimeSpan.Item<U> item) {
            U unit = item.getUnit();
            if (unit.equals(ClockUnit.MILLIS)) {
                return TimeSpan.Item.of(MathUtils.safeMultiply(item.getAmount(), Duration.MIO), ClockUnit.NANOS);
            }
            return unit.equals(ClockUnit.MICROS) ? TimeSpan.Item.of(MathUtils.safeMultiply(item.getAmount(), 1000L), ClockUnit.NANOS) : item;
        }
    }

    private static class LengthComparator<U extends IsoUnit, T extends TimePoint<U, T>> implements Comparator<Duration<? extends U>> {
        private final T base;

        private LengthComparator(T t) {
            if (t == null) {
                throw new NullPointerException("Missing base time point.");
            }
            this.base = t;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.Comparator
        public int compare(Duration<? extends U> duration, Duration<? extends U> duration2) {
            boolean isNegative = duration.isNegative();
            boolean isNegative2 = duration2.isNegative();
            if (isNegative && !isNegative2) {
                return -1;
            }
            if (!isNegative && isNegative2) {
                return 1;
            }
            if (duration.isEmpty() && duration2.isEmpty()) {
                return 0;
            }
            return this.base.plus(duration).compareTo(this.base.plus(duration2));
        }
    }

    private static class ApproximateNormalizer implements Normalizer<IsoUnit> {
        private final boolean daysToWeeks;
        private final int steps;
        private final ClockUnit unit;

        ApproximateNormalizer(boolean z) {
            this.daysToWeeks = z;
            this.steps = 1;
            this.unit = null;
        }

        ApproximateNormalizer(int i, ClockUnit clockUnit) {
            if (i < 1) {
                throw new IllegalArgumentException("Step width is not positive: " + i);
            }
            if (clockUnit.compareTo(ClockUnit.SECONDS) > 0) {
                throw new IllegalArgumentException("Unsupported unit.");
            }
            this.daysToWeeks = false;
            this.steps = i;
            this.unit = clockUnit;
        }

        /* JADX WARN: Removed duplicated region for block: B:28:0x015a  */
        @Override // net.time4j.engine.Normalizer
        /* renamed from: normalize */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public net.time4j.engine.TimeSpan<net.time4j.IsoUnit> normalize2(net.time4j.engine.TimeSpan<? extends net.time4j.IsoUnit> r14) {
            /*
                Method dump skipped, instructions count: 406
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: net.time4j.Duration.ApproximateNormalizer.normalize2(net.time4j.engine.TimeSpan):net.time4j.Duration");
        }

        private static int safeCast(double d) {
            if (d >= -2.147483648E9d && d <= 2.147483647E9d) {
                return (int) d;
            }
            throw new ArithmeticException("Out of range: " + d);
        }

        private static IsoUnit maxUnit(double d) {
            for (IsoUnit isoUnit : Arrays.asList(CalendarUnit.YEARS, CalendarUnit.MONTHS, CalendarUnit.DAYS, ClockUnit.HOURS, ClockUnit.MINUTES)) {
                if (d >= isoUnit.getLength()) {
                    return isoUnit;
                }
            }
            return ClockUnit.SECONDS;
        }
    }
}

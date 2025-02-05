package net.time4j;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import net.time4j.base.MathUtils;
import net.time4j.base.ResourceLoader;
import net.time4j.base.TimeSource;
import net.time4j.base.UnixTime;
import net.time4j.engine.TimeSpan;
import net.time4j.format.NumberSymbolProvider;
import net.time4j.format.NumberSystem;
import net.time4j.format.NumberType;
import net.time4j.format.PluralCategory;
import net.time4j.format.PluralRules;
import net.time4j.format.TemporalFormatter;
import net.time4j.format.TextWidth;
import net.time4j.tz.TZID;
import net.time4j.tz.Timezone;
import net.time4j.tz.ZonalOffset;

/* loaded from: classes3.dex */
public final class PrettyTime {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final ConcurrentMap<Locale, PrettyTime> LANGUAGE_MAP;
    private static final int MIO = 1000000;
    private static final NumberSymbolProvider NUMBER_SYMBOLS;
    private static final long START_1972;
    private static final IsoUnit[] STD_UNITS;
    private static final Set<IsoUnit> SUPPORTED_UNITS;
    private static final IsoUnit[] TSP_UNITS;
    private final IsoUnit emptyUnit;
    private final String endListSeparator;
    private final Locale locale;
    private final String minusSign;
    private final TimeSource<?> refClock;
    private final PluralRules rules;
    private final boolean shortStyle;
    private final String stdListSeparator;
    private final boolean weekToDays;
    private final char zeroDigit;

    static {
        NumberSymbolProvider numberSymbolProvider = null;
        int i = 0;
        for (NumberSymbolProvider numberSymbolProvider2 : ResourceLoader.getInstance().services(NumberSymbolProvider.class)) {
            int length = numberSymbolProvider2.getAvailableLocales().length;
            if (length >= i) {
                numberSymbolProvider = numberSymbolProvider2;
                i = length;
            }
        }
        if (numberSymbolProvider == null) {
            numberSymbolProvider = NumberSymbolProvider.DEFAULT;
        }
        NUMBER_SYMBOLS = numberSymbolProvider;
        LANGUAGE_MAP = new ConcurrentHashMap();
        IsoUnit[] isoUnitArr = {CalendarUnit.YEARS, CalendarUnit.MONTHS, CalendarUnit.WEEKS, CalendarUnit.DAYS, ClockUnit.HOURS, ClockUnit.MINUTES, ClockUnit.SECONDS};
        STD_UNITS = isoUnitArr;
        TSP_UNITS = new IsoUnit[]{CalendarUnit.YEARS, CalendarUnit.MONTHS, CalendarUnit.DAYS, ClockUnit.HOURS, ClockUnit.MINUTES, ClockUnit.SECONDS};
        HashSet hashSet = new HashSet();
        Collections.addAll(hashSet, isoUnitArr);
        hashSet.add(ClockUnit.NANOS);
        SUPPORTED_UNITS = Collections.unmodifiableSet(hashSet);
        START_1972 = 63072000L;
    }

    private PrettyTime(Locale locale, TimeSource<?> timeSource, char c, String str, IsoUnit isoUnit, boolean z, boolean z2, String str2, String str3) {
        if (isoUnit == null) {
            throw new NullPointerException("Missing zero time unit.");
        }
        if (timeSource == null) {
            throw new NullPointerException("Missing reference clock.");
        }
        this.rules = PluralRules.of(locale, NumberType.CARDINALS);
        this.locale = locale;
        this.refClock = timeSource;
        this.zeroDigit = c;
        this.emptyUnit = isoUnit;
        this.minusSign = str;
        this.weekToDays = z;
        this.shortStyle = z2;
        this.stdListSeparator = str2;
        this.endListSeparator = str3;
    }

    public static PrettyTime of(Locale locale) {
        ConcurrentMap<Locale, PrettyTime> concurrentMap = LANGUAGE_MAP;
        PrettyTime prettyTime = concurrentMap.get(locale);
        if (prettyTime != null) {
            return prettyTime;
        }
        SystemClock systemClock = SystemClock.INSTANCE;
        NumberSymbolProvider numberSymbolProvider = NUMBER_SYMBOLS;
        PrettyTime prettyTime2 = new PrettyTime(locale, systemClock, numberSymbolProvider.getZeroDigit(locale), numberSymbolProvider.getMinusSign(locale), ClockUnit.SECONDS, false, false, null, null);
        PrettyTime putIfAbsent = concurrentMap.putIfAbsent(locale, prettyTime2);
        return putIfAbsent != null ? putIfAbsent : prettyTime2;
    }

    public Locale getLocale() {
        return this.locale;
    }

    public TimeSource<?> getReferenceClock() {
        return this.refClock;
    }

    public PrettyTime withReferenceClock(TimeSource<?> timeSource) {
        return new PrettyTime(this.locale, timeSource, this.zeroDigit, this.minusSign, this.emptyUnit, this.weekToDays, this.shortStyle, this.stdListSeparator, this.endListSeparator);
    }

    public PrettyTime withZeroDigit(NumberSystem numberSystem) {
        if (numberSystem.isDecimal()) {
            return withZeroDigit(numberSystem.getDigits().charAt(0));
        }
        throw new IllegalArgumentException("Number system is not decimal: " + numberSystem);
    }

    public PrettyTime withZeroDigit(char c) {
        return this.zeroDigit == c ? this : new PrettyTime(this.locale, this.refClock, c, this.minusSign, this.emptyUnit, this.weekToDays, this.shortStyle, this.stdListSeparator, this.endListSeparator);
    }

    public PrettyTime withMinusSign(String str) {
        return str.equals(this.minusSign) ? this : new PrettyTime(this.locale, this.refClock, this.zeroDigit, str, this.emptyUnit, this.weekToDays, this.shortStyle, this.stdListSeparator, this.endListSeparator);
    }

    public PrettyTime withEmptyUnit(CalendarUnit calendarUnit) {
        return this.emptyUnit.equals(calendarUnit) ? this : new PrettyTime(this.locale, this.refClock, this.zeroDigit, this.minusSign, calendarUnit, this.weekToDays, this.shortStyle, this.stdListSeparator, this.endListSeparator);
    }

    public PrettyTime withEmptyUnit(ClockUnit clockUnit) {
        return this.emptyUnit.equals(clockUnit) ? this : new PrettyTime(this.locale, this.refClock, this.zeroDigit, this.minusSign, clockUnit, this.weekToDays, this.shortStyle, this.stdListSeparator, this.endListSeparator);
    }

    public PrettyTime withWeeksToDays() {
        return this.weekToDays ? this : new PrettyTime(this.locale, this.refClock, this.zeroDigit, this.minusSign, this.emptyUnit, true, this.shortStyle, this.stdListSeparator, this.endListSeparator);
    }

    public PrettyTime withShortStyle() {
        return this.shortStyle ? this : new PrettyTime(this.locale, this.refClock, this.zeroDigit, this.minusSign, this.emptyUnit, this.weekToDays, true, this.stdListSeparator, this.endListSeparator);
    }

    public PrettyTime withDefaultListSeparator(String str) {
        return str.equals(this.stdListSeparator) ? this : new PrettyTime(this.locale, this.refClock, this.zeroDigit, this.minusSign, this.emptyUnit, this.weekToDays, this.shortStyle, str, this.endListSeparator);
    }

    public PrettyTime withLastListSeparator(String str) {
        return str.equals(this.endListSeparator) ? this : new PrettyTime(this.locale, this.refClock, this.zeroDigit, this.minusSign, this.emptyUnit, this.weekToDays, this.shortStyle, this.stdListSeparator, str);
    }

    public String printYesterday() {
        return UnitPatterns.of(getLocale()).getYesterdayWord();
    }

    public String printToday() {
        return UnitPatterns.of(getLocale()).getTodayWord();
    }

    public String printTomorrow() {
        return UnitPatterns.of(getLocale()).getTomorrowWord();
    }

    public String printLast(Weekday weekday) {
        return UnitPatterns.of(getLocale()).labelForLast(weekday);
    }

    public String printNext(Weekday weekday) {
        return UnitPatterns.of(getLocale()).labelForNext(weekday);
    }

    public String print(long j, CalendarUnit calendarUnit, TextWidth textWidth) {
        CalendarUnit calendarUnit2;
        UnitPatterns of = UnitPatterns.of(this.locale);
        switch (AnonymousClass1.$SwitchMap$net$time4j$CalendarUnit[calendarUnit.ordinal()]) {
            case 1:
                j = MathUtils.safeMultiply(j, 1000L);
                calendarUnit2 = CalendarUnit.YEARS;
                break;
            case 2:
                j = MathUtils.safeMultiply(j, 100L);
                calendarUnit2 = CalendarUnit.YEARS;
                break;
            case 3:
                j = MathUtils.safeMultiply(j, 10L);
                calendarUnit2 = CalendarUnit.YEARS;
                break;
            case 4:
                calendarUnit2 = CalendarUnit.YEARS;
                break;
            case 5:
                j = MathUtils.safeMultiply(j, 3L);
                calendarUnit2 = CalendarUnit.MONTHS;
                break;
            case 6:
                calendarUnit2 = CalendarUnit.MONTHS;
                break;
            case 7:
                if (this.weekToDays) {
                    j = MathUtils.safeMultiply(j, 7L);
                    calendarUnit2 = CalendarUnit.DAYS;
                    break;
                } else {
                    calendarUnit2 = CalendarUnit.WEEKS;
                    break;
                }
            case 8:
                calendarUnit2 = CalendarUnit.DAYS;
                break;
            default:
                throw new UnsupportedOperationException(calendarUnit.name());
        }
        return format(of.getPattern(textWidth, getCategory(j), calendarUnit2), j);
    }

    public String print(long j, ClockUnit clockUnit, TextWidth textWidth) {
        return format(UnitPatterns.of(this.locale).getPattern(textWidth, getCategory(j), clockUnit), j);
    }

    public String print(Duration<?> duration) {
        return print(duration, this.shortStyle ? TextWidth.ABBREVIATED : TextWidth.WIDE, false, Integer.MAX_VALUE);
    }

    public String print(Duration<?> duration, TextWidth textWidth) {
        return print(duration, textWidth, false, Integer.MAX_VALUE);
    }

    public String print(Duration<?> duration, TextWidth textWidth, boolean z, int i) {
        String listPattern;
        int i2;
        if (i < 1) {
            throw new IllegalArgumentException("Max length is invalid: " + i);
        }
        long j = 0;
        if (duration.isEmpty()) {
            if (this.emptyUnit.isCalendrical()) {
                return print(0L, (CalendarUnit) CalendarUnit.class.cast(this.emptyUnit), textWidth);
            }
            return print(0L, (ClockUnit) ClockUnit.class.cast(this.emptyUnit), textWidth);
        }
        boolean isNegative = duration.isNegative();
        long[] jArr = new long[8];
        pushDuration(jArr, duration, this.refClock, this.weekToDays);
        ArrayList arrayList = new ArrayList();
        int i3 = 0;
        int i4 = 0;
        for (int i5 = 8; i4 < i5; i5 = 8) {
            if (i3 >= i || ((this.weekToDays && i4 == 2) || ((!z || i3 <= 0) && jArr[i4] <= j))) {
                i2 = i4;
                i3 = i3;
            } else {
                i2 = i4;
                arrayList.add(format(jArr[i4], i4 == 7 ? ClockUnit.NANOS : STD_UNITS[i4], isNegative, textWidth));
                i3++;
            }
            i4 = i2 + 1;
            j = 0;
        }
        int i6 = i3;
        if (i6 == 1) {
            return arrayList.get(0).toString();
        }
        String str = this.stdListSeparator;
        if (str != null) {
            String str2 = this.endListSeparator;
            if (str2 != null) {
                str = str2;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("{0}");
            int i7 = i6 - 1;
            for (int i8 = 1; i8 < i7; i8++) {
                sb.append(this.stdListSeparator);
                sb.append('{');
                sb.append(i8);
                sb.append('}');
            }
            sb.append(str);
            sb.append('{');
            sb.append(i7);
            sb.append('}');
            listPattern = sb.toString();
        } else {
            listPattern = UnitPatterns.of(this.locale).getListPattern(textWidth, i6);
        }
        return MessageFormat.format(listPattern, arrayList.toArray(new Object[i6]));
    }

    public String printRelativeInStdTimezone(UnixTime unixTime) {
        return printRelative(unixTime, Timezone.ofSystem(), TimeUnit.SECONDS);
    }

    public String printRelative(UnixTime unixTime, TZID tzid) {
        return printRelative(unixTime, Timezone.of(tzid), TimeUnit.SECONDS);
    }

    public String printRelative(UnixTime unixTime, String str) {
        return printRelative(unixTime, Timezone.of(str), TimeUnit.SECONDS);
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [net.time4j.base.UnixTime] */
    public String printRelative(UnixTime unixTime, Timezone timezone, TimeUnit timeUnit) {
        Moment from = Moment.from(getReferenceClock().currentTime());
        Moment from2 = Moment.from(unixTime);
        if (timeUnit.compareTo(TimeUnit.SECONDS) <= 0) {
            long until = from.until(from2, (Moment) TimeUnit.SECONDS);
            if (Math.abs(until) < 60) {
                return printRelativeSeconds(from, from2, until);
            }
        }
        return printRelativeTime(from, from2, timezone, timeUnit, null, null);
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [net.time4j.base.UnixTime] */
    public String printRelativeOrDateTime(UnixTime unixTime, Timezone timezone, TimeUnit timeUnit, long j, TemporalFormatter<Moment> temporalFormatter) {
        Moment from = Moment.from(getReferenceClock().currentTime());
        Moment from2 = Moment.from(unixTime);
        long until = from.until(from2, (Moment) TimeUnit.SECONDS);
        if (Math.abs(until) > j) {
            return temporalFormatter.format(from2);
        }
        if (timeUnit.compareTo(TimeUnit.SECONDS) <= 0 && Math.abs(until) < 60) {
            return printRelativeSeconds(from, from2, until);
        }
        return printRelativeTime(from, from2, timezone, timeUnit, null, null);
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [net.time4j.base.UnixTime] */
    public String printRelativeOrDateTime(UnixTime unixTime, Timezone timezone, TimeUnit timeUnit, CalendarUnit calendarUnit, TemporalFormatter<Moment> temporalFormatter) {
        if (calendarUnit == null) {
            throw new NullPointerException("Missing max relative unit.");
        }
        Moment from = Moment.from(getReferenceClock().currentTime());
        Moment from2 = Moment.from(unixTime);
        long until = from.until(from2, (Moment) TimeUnit.SECONDS);
        if (timeUnit.compareTo(TimeUnit.SECONDS) <= 0 && Math.abs(until) < 60) {
            return printRelativeSeconds(from, from2, until);
        }
        return printRelativeTime(from, from2, timezone, timeUnit, calendarUnit, temporalFormatter);
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [net.time4j.base.UnixTime] */
    public String printRelativeOrDate(PlainDate plainDate, TZID tzid, CalendarUnit calendarUnit, TemporalFormatter<PlainDate> temporalFormatter) {
        Duration<CalendarUnit> duration;
        String futurePattern;
        if (calendarUnit == null) {
            throw new NullPointerException("Missing max relative unit.");
        }
        PlainDate date = Moment.from(getReferenceClock().currentTime()).toZonalTimestamp(tzid).toDate();
        if (this.weekToDays) {
            duration = Duration.inYearsMonthsDays().between(date, plainDate);
        } else {
            duration = (Duration) Duration.in(CalendarUnit.YEARS, CalendarUnit.MONTHS, CalendarUnit.WEEKS, CalendarUnit.DAYS).between(date, plainDate);
        }
        if (duration.isEmpty()) {
            return getEmptyRelativeString(TimeUnit.DAYS);
        }
        TimeSpan.Item<CalendarUnit> item = duration.getTotalLength().get(0);
        long amount = item.getAmount();
        CalendarUnit unit = item.getUnit();
        if (Double.compare(unit.getLength(), calendarUnit.getLength()) > 0) {
            return temporalFormatter.format(plainDate);
        }
        if (unit.equals(CalendarUnit.DAYS)) {
            String relativeReplacement = getRelativeReplacement(plainDate, duration.isNegative(), amount);
            if (!relativeReplacement.isEmpty()) {
                return relativeReplacement;
            }
        }
        if (duration.isNegative()) {
            futurePattern = getPastPattern(amount, unit);
        } else {
            futurePattern = getFuturePattern(amount, unit);
        }
        return format(futurePattern, amount);
    }

    private String printRelativeSeconds(Moment moment, Moment moment2, long j) {
        String futurePattern;
        long posixTime = moment.getPosixTime();
        long j2 = START_1972;
        if (posixTime >= j2 && moment2.getPosixTime() >= j2) {
            j = SI.SECONDS.between(moment, moment2);
        }
        if (j == 0) {
            return UnitPatterns.of(this.locale).getNowWord();
        }
        long abs = Math.abs(j);
        if (j < 0) {
            futurePattern = getPastPattern(abs, ClockUnit.SECONDS);
        } else {
            futurePattern = getFuturePattern(abs, ClockUnit.SECONDS);
        }
        return format(futurePattern, abs);
    }

    private String printRelativeTime(Moment moment, Moment moment2, Timezone timezone, TimeUnit timeUnit, CalendarUnit calendarUnit, TemporalFormatter<Moment> temporalFormatter) {
        String futurePattern;
        PlainTimestamp from = PlainTimestamp.from(moment, timezone.getOffset(moment));
        PlainTimestamp from2 = PlainTimestamp.from(moment2, timezone.getOffset(moment2));
        Duration<IsoUnit> between = Duration.in(timezone, this.weekToDays ? TSP_UNITS : STD_UNITS).between(from, from2);
        if (between.isEmpty()) {
            return getEmptyRelativeString(timeUnit);
        }
        TimeSpan.Item<IsoUnit> item = between.getTotalLength().get(0);
        long amount = item.getAmount();
        IsoUnit unit = item.getUnit();
        if (unit instanceof ClockUnit) {
            if (5 - ((ClockUnit) unit).ordinal() < timeUnit.ordinal()) {
                return getEmptyRelativeString(timeUnit);
            }
        } else {
            if (calendarUnit != null && Double.compare(unit.getLength(), calendarUnit.getLength()) > 0) {
                return temporalFormatter.format(moment2);
            }
            if (unit.equals(CalendarUnit.DAYS)) {
                String relativeReplacement = getRelativeReplacement(from2.toDate(), between.isNegative(), amount);
                if (!relativeReplacement.isEmpty()) {
                    return relativeReplacement;
                }
            }
        }
        if (between.isNegative()) {
            if (unit.isCalendrical()) {
                futurePattern = getPastPattern(amount, (CalendarUnit) unit);
            } else {
                futurePattern = getPastPattern(amount, (ClockUnit) unit);
            }
        } else if (unit.isCalendrical()) {
            futurePattern = getFuturePattern(amount, (CalendarUnit) unit);
        } else {
            futurePattern = getFuturePattern(amount, (ClockUnit) unit);
        }
        return format(futurePattern, amount);
    }

    private String getRelativeReplacement(PlainDate plainDate, boolean z, long j) {
        if (j < 1 || j > 7) {
            return "";
        }
        UnitPatterns of = UnitPatterns.of(this.locale);
        if (j == 1) {
            return z ? of.getYesterdayWord() : of.getTomorrowWord();
        }
        Weekday dayOfWeek = plainDate.getDayOfWeek();
        return z ? of.labelForLast(dayOfWeek) : of.labelForNext(dayOfWeek);
    }

    private String getEmptyRelativeString(TimeUnit timeUnit) {
        UnitPatterns of = UnitPatterns.of(this.locale);
        if (timeUnit.equals(TimeUnit.DAYS)) {
            String todayWord = of.getTodayWord();
            if (!todayWord.isEmpty()) {
                return todayWord;
            }
        }
        return of.getNowWord();
    }

    private String getPastPattern(long j, CalendarUnit calendarUnit) {
        return UnitPatterns.of(this.locale).getPatternInPast(getCategory(j), this.shortStyle, calendarUnit);
    }

    private String getFuturePattern(long j, CalendarUnit calendarUnit) {
        return UnitPatterns.of(this.locale).getPatternInFuture(getCategory(j), this.shortStyle, calendarUnit);
    }

    private String getPastPattern(long j, ClockUnit clockUnit) {
        return UnitPatterns.of(this.locale).getPatternInPast(getCategory(j), this.shortStyle, clockUnit);
    }

    private String getFuturePattern(long j, ClockUnit clockUnit) {
        return UnitPatterns.of(this.locale).getPatternInFuture(getCategory(j), this.shortStyle, clockUnit);
    }

    private PluralCategory getCategory(long j) {
        return this.rules.getCategory(Math.abs(j));
    }

    /* JADX WARN: Type inference failed for: r3v8, types: [net.time4j.base.UnixTime] */
    private static void pushDuration(long[] jArr, Duration<?> duration, TimeSource<?> timeSource, boolean z) {
        int size = duration.getTotalLength().size();
        for (int i = 0; i < size; i++) {
            TimeSpan.Item<?> item = duration.getTotalLength().get(i);
            IsoUnit isoUnit = (IsoUnit) item.getUnit();
            long amount = item.getAmount();
            if (isoUnit instanceof CalendarUnit) {
                push(jArr, (CalendarUnit) CalendarUnit.class.cast(isoUnit), amount, z);
            } else if (isoUnit instanceof ClockUnit) {
                push(jArr, (ClockUnit) ClockUnit.class.cast(isoUnit), amount);
            } else if (isoUnit instanceof OverflowUnit) {
                push(jArr, ((OverflowUnit) OverflowUnit.class.cast(isoUnit)).getCalendarUnit(), amount, z);
            } else if (isoUnit.equals(CalendarUnit.weekBasedYears())) {
                jArr[0] = MathUtils.safeAdd(amount, jArr[0]);
            } else {
                PlainTimestamp zonalTimestamp = Moment.from(timeSource.currentTime()).toZonalTimestamp(ZonalOffset.UTC);
                pushDuration(jArr, (Duration) Duration.in(z ? TSP_UNITS : STD_UNITS).between(zonalTimestamp, zonalTimestamp.plus(amount, isoUnit)), timeSource, z);
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static void push(long[] jArr, CalendarUnit calendarUnit, long j, boolean z) {
        char c = 3;
        switch (AnonymousClass1.$SwitchMap$net$time4j$CalendarUnit[calendarUnit.ordinal()]) {
            case 1:
                j = MathUtils.safeMultiply(j, 1000L);
                c = 0;
                jArr[c] = MathUtils.safeAdd(j, jArr[c]);
                return;
            case 2:
                j = MathUtils.safeMultiply(j, 100L);
                c = 0;
                jArr[c] = MathUtils.safeAdd(j, jArr[c]);
                return;
            case 3:
                j = MathUtils.safeMultiply(j, 10L);
                c = 0;
                jArr[c] = MathUtils.safeAdd(j, jArr[c]);
                return;
            case 4:
                c = 0;
                jArr[c] = MathUtils.safeAdd(j, jArr[c]);
                return;
            case 5:
                j = MathUtils.safeMultiply(j, 3L);
                c = 1;
                jArr[c] = MathUtils.safeAdd(j, jArr[c]);
                return;
            case 6:
                c = 1;
                jArr[c] = MathUtils.safeAdd(j, jArr[c]);
                return;
            case 7:
                if (z) {
                    j = MathUtils.safeMultiply(j, 7L);
                } else {
                    c = 2;
                }
                jArr[c] = MathUtils.safeAdd(j, jArr[c]);
                return;
            case 8:
                jArr[c] = MathUtils.safeAdd(j, jArr[c]);
                return;
            default:
                throw new UnsupportedOperationException(calendarUnit.name());
        }
    }

    /* renamed from: net.time4j.PrettyTime$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
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

    private static void push(long[] jArr, ClockUnit clockUnit, long j) {
        char c = 7;
        switch (AnonymousClass1.$SwitchMap$net$time4j$ClockUnit[clockUnit.ordinal()]) {
            case 1:
                c = 4;
                break;
            case 2:
                c = 5;
                break;
            case 3:
                c = 6;
                break;
            case 4:
                j = MathUtils.safeMultiply(j, 1000000L);
                break;
            case 5:
                j = MathUtils.safeMultiply(j, 1000L);
                break;
            case 6:
                break;
            default:
                throw new UnsupportedOperationException(clockUnit.name());
        }
        jArr[c] = MathUtils.safeAdd(j, jArr[c]);
    }

    private String format(long j, IsoUnit isoUnit, boolean z, TextWidth textWidth) {
        long safeNegate = z ? MathUtils.safeNegate(j) : j;
        if (SUPPORTED_UNITS.contains(isoUnit)) {
            if (isoUnit.isCalendrical()) {
                return print(safeNegate, (CalendarUnit) CalendarUnit.class.cast(isoUnit), textWidth);
            }
            ClockUnit clockUnit = (ClockUnit) ClockUnit.class.cast(isoUnit);
            if (clockUnit == ClockUnit.NANOS) {
                if (j % 1000000 == 0) {
                    clockUnit = ClockUnit.MILLIS;
                    safeNegate /= 1000000;
                } else if (j % 1000 == 0) {
                    clockUnit = ClockUnit.MICROS;
                    safeNegate /= 1000;
                }
            }
            return print(safeNegate, clockUnit, textWidth);
        }
        throw new UnsupportedOperationException("Unknown unit: " + isoUnit);
    }

    private String format(String str, long j) {
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (i < length - 2 && str.charAt(i) == '{' && str.charAt(i + 1) == '0' && str.charAt(i + 2) == '}') {
                StringBuilder sb = new StringBuilder(str);
                sb.replace(i, i + 3, format(j));
                return sb.toString();
            }
        }
        if (j >= 0) {
            return str;
        }
        return this.minusSign + str;
    }

    private String format(long j) {
        String valueOf = String.valueOf(Math.abs(j));
        char c = this.zeroDigit;
        StringBuilder sb = new StringBuilder();
        if (j < 0) {
            sb.append(this.minusSign);
        }
        int length = valueOf.length();
        for (int i = 0; i < length; i++) {
            char charAt = valueOf.charAt(i);
            if (c != '0') {
                charAt = (char) ((charAt + c) - 48);
            }
            sb.append(charAt);
        }
        return sb.toString();
    }
}

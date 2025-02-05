package net.time4j.format.platform;

import java.io.IOException;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import net.time4j.Meridiem;
import net.time4j.Moment;
import net.time4j.PlainDate;
import net.time4j.PlainTime;
import net.time4j.PlainTimestamp;
import net.time4j.TemporalType;
import net.time4j.ZonalDateTime;
import net.time4j.engine.AttributeKey;
import net.time4j.engine.AttributeQuery;
import net.time4j.engine.ChronoElement;
import net.time4j.engine.ChronoEntity;
import net.time4j.engine.ChronoException;
import net.time4j.engine.Chronology;
import net.time4j.format.Attributes;
import net.time4j.format.DisplayMode;
import net.time4j.format.Leniency;
import net.time4j.format.RawValues;
import net.time4j.format.TemporalFormatter;
import net.time4j.format.internal.FormatUtils;
import net.time4j.tz.NameStyle;
import net.time4j.tz.TZID;
import net.time4j.tz.Timezone;
import net.time4j.tz.ZonalOffset;
import org.apache.commons.lang3.time.TimeZones;

/* loaded from: classes3.dex */
public final class SimpleFormatter<T> implements TemporalFormatter<T> {
    public static final SimpleFormatter<Moment> RFC_1123;
    private static final String RFC_1123_PATTERN = "<RFC-1123>";
    private static final String RFC_1123_SHORT = "d MMM yyyy HH:mm:ss Z";
    private static final String RFC_1123_WIDE = "EEE, d MMM yyyy HH:mm:ss Z";
    private static final Map<Class<?>, Chronology<?>> SUPPORTED_TYPES;
    private final Leniency leniency;
    private final Locale locale;
    private final String pattern;
    private final Class<T> type;
    private final String tzid;
    private static final Date PROLEPTIC_GREGORIAN = new Date(Long.MIN_VALUE);
    private static final PlainDate UNIX_EPOCH_DATE = PlainDate.of(1970, 1, 1);

    static {
        HashMap hashMap = new HashMap();
        hashMap.put(PlainDate.class, PlainDate.axis());
        hashMap.put(PlainTime.class, PlainTime.axis());
        hashMap.put(PlainTimestamp.class, PlainTimestamp.axis());
        hashMap.put(Moment.class, Moment.axis());
        SUPPORTED_TYPES = Collections.unmodifiableMap(hashMap);
        RFC_1123 = new SimpleFormatter<>(Moment.class, RFC_1123_PATTERN, Locale.ENGLISH, Leniency.SMART, TimeZones.GMT_ID);
    }

    private SimpleFormatter(Class<T> cls, String str, Locale locale, Leniency leniency, String str2) {
        if (cls == null) {
            throw new NullPointerException("Missing chronological type");
        }
        if (str.isEmpty()) {
            throw new IllegalArgumentException("Format pattern is empty.");
        }
        if (locale == null) {
            throw new NullPointerException("Locale is not specified.");
        }
        if (leniency == null) {
            throw new NullPointerException("Missing leniency.");
        }
        this.type = cls;
        this.pattern = str;
        this.locale = locale;
        this.leniency = leniency;
        this.tzid = str2;
    }

    public static SimpleFormatter<PlainDate> ofDatePattern(String str, Locale locale) {
        return new SimpleFormatter<>(PlainDate.class, str, locale, Leniency.SMART, null);
    }

    public static SimpleFormatter<PlainDate> ofDateStyle(DisplayMode displayMode, Locale locale) {
        return ofDatePattern(getFormatPattern(DateFormat.getDateInstance(displayMode.getStyleValue(), locale)), locale);
    }

    public static SimpleFormatter<PlainTime> ofTimePattern(String str, Locale locale) {
        return new SimpleFormatter<>(PlainTime.class, str, locale, Leniency.SMART, null);
    }

    public static SimpleFormatter<PlainTime> ofTimeStyle(DisplayMode displayMode, Locale locale) {
        return ofTimePattern(FormatUtils.removeZones(getFormatPattern(DateFormat.getTimeInstance(displayMode.getStyleValue(), locale))), locale);
    }

    public static SimpleFormatter<PlainTimestamp> ofTimestampPattern(String str, Locale locale) {
        return new SimpleFormatter<>(PlainTimestamp.class, str, locale, Leniency.SMART, null);
    }

    public static SimpleFormatter<PlainTimestamp> ofTimestampStyle(DisplayMode displayMode, DisplayMode displayMode2, Locale locale) {
        return ofTimestampPattern(FormatUtils.removeZones(getFormatPattern(DateFormat.getDateTimeInstance(displayMode.getStyleValue(), displayMode2.getStyleValue(), locale))), locale);
    }

    public static SimpleFormatter<Moment> ofMomentPattern(String str, Locale locale, TZID tzid) {
        return new SimpleFormatter<>(Moment.class, str, locale, Leniency.SMART, tzid.canonical());
    }

    public static SimpleFormatter<Moment> ofMomentStyle(DisplayMode displayMode, DisplayMode displayMode2, Locale locale, TZID tzid) {
        return ofMomentPattern(getFormatPattern(DateFormat.getDateTimeInstance(displayMode.getStyleValue(), displayMode2.getStyleValue(), locale)), locale, tzid);
    }

    @Override // net.time4j.format.TemporalFormatter
    public String format(T t) {
        return print(t);
    }

    @Override // net.time4j.format.TemporalFormatter
    public String print(T t) {
        StringBuilder sb = new StringBuilder();
        try {
            print(t, sb);
            return sb.toString();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    @Override // net.time4j.format.TemporalFormatter
    public T parse(CharSequence charSequence) throws ParseException {
        ParsePosition parsePosition = new ParsePosition(0);
        try {
            T parseInternal = parseInternal(charSequence, parsePosition, null);
            if (parseInternal != null && parsePosition.getErrorIndex() <= -1) {
                return parseInternal;
            }
            throw new ParseException("Cannot parse: " + ((Object) charSequence), parsePosition.getErrorIndex());
        } catch (RuntimeException e) {
            ParseException parseException = new ParseException(e.getMessage(), parsePosition.getErrorIndex());
            parseException.initCause(e);
            throw parseException;
        }
    }

    @Override // net.time4j.format.TemporalFormatter
    public T parse(CharSequence charSequence, RawValues rawValues) throws ParseException {
        if (rawValues == null) {
            throw new NullPointerException("Missing raw values.");
        }
        ParsePosition parsePosition = new ParsePosition(0);
        try {
            T parseInternal = parseInternal(charSequence, parsePosition, rawValues);
            if (parseInternal != null && parsePosition.getErrorIndex() <= -1) {
                return parseInternal;
            }
            throw new ParseException("Cannot parse: " + ((Object) charSequence), parsePosition.getErrorIndex());
        } catch (RuntimeException e) {
            ParseException parseException = new ParseException(e.getMessage(), parsePosition.getErrorIndex());
            parseException.initCause(e);
            throw parseException;
        }
    }

    @Override // net.time4j.format.TemporalFormatter
    public TemporalFormatter<T> withTimezone(TZID tzid) {
        return withTimezone(tzid.canonical());
    }

    @Override // net.time4j.format.TemporalFormatter
    public TemporalFormatter<T> withTimezone(String str) {
        return new SimpleFormatter(this.type, this.pattern, this.locale, this.leniency, str);
    }

    @Override // net.time4j.format.TemporalFormatter
    public TemporalFormatter<T> with(Locale locale) {
        return new SimpleFormatter(this.type, this.pattern, locale, this.leniency, this.tzid);
    }

    @Override // net.time4j.format.TemporalFormatter
    public TemporalFormatter<T> with(Leniency leniency) {
        return new SimpleFormatter(this.type, this.pattern, this.locale, leniency, this.tzid);
    }

    @Override // net.time4j.format.TemporalFormatter
    public AttributeQuery getAttributes() {
        Chronology<?> chronology = SUPPORTED_TYPES.get(this.type);
        Attributes.Builder builder = chronology == null ? new Attributes.Builder() : new Attributes.Builder(chronology);
        builder.setLanguage(this.locale);
        builder.set((AttributeKey<AttributeKey<Leniency>>) Attributes.LENIENCY, (AttributeKey<Leniency>) this.leniency);
        String str = this.tzid;
        if (str != null) {
            builder.setTimezone(str);
        }
        return builder.build();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SimpleFormatter)) {
            return false;
        }
        SimpleFormatter simpleFormatter = (SimpleFormatter) obj;
        if (this.type.equals(simpleFormatter.type) && this.pattern.equals(simpleFormatter.pattern) && this.locale.equals(simpleFormatter.locale) && this.leniency == simpleFormatter.leniency) {
            String str = this.tzid;
            String str2 = simpleFormatter.tzid;
            if (str == null) {
                if (str2 == null) {
                    return true;
                }
            } else if (str.equals(str2)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return (this.pattern.hashCode() * 17) + (this.locale.hashCode() * 31) + (this.tzid.hashCode() * 37);
    }

    private void print(T t, Appendable appendable) throws IOException {
        String str;
        NameStyle nameStyle;
        if (this.type.equals(PlainDate.class)) {
            str = setUpLocal().format(TemporalType.JAVA_UTIL_DATE.from(((PlainDate) PlainDate.class.cast(t)).atStartOfDay().atUTC()));
        } else if (this.type.equals(PlainTime.class)) {
            str = setUpLocal().format(TemporalType.JAVA_UTIL_DATE.from(UNIX_EPOCH_DATE.at((PlainTime) PlainTime.class.cast(t)).atUTC()));
        } else if (this.type.equals(PlainTimestamp.class)) {
            str = setUpLocal().format(TemporalType.JAVA_UTIL_DATE.from(((PlainTimestamp) PlainTimestamp.class.cast(t)).atUTC()));
        } else if (this.type.equals(Moment.class)) {
            Moment moment = (Moment) Moment.class.cast(t);
            if (this.tzid == null) {
                throw new IllegalArgumentException("Cannot print moment without timezone.");
            }
            String str2 = this.pattern;
            if (str2.equals(RFC_1123_PATTERN)) {
                str2 = RFC_1123_WIDE;
            }
            str = new SimpleFormatter(ZonalDateTime.class, str2, this.locale, this.leniency, this.tzid).print(moment.inZonalView(this.tzid));
        } else if (this.type.equals(ZonalDateTime.class)) {
            ZonalDateTime zonalDateTime = (ZonalDateTime) ZonalDateTime.class.cast(t);
            Moment moment2 = zonalDateTime.toMoment();
            Date from = TemporalType.JAVA_UTIL_DATE.from(moment2);
            String str3 = this.tzid;
            if (str3 == null) {
                str3 = zonalDateTime.getTimezone().canonical();
            }
            Timezone of = Timezone.of(str3);
            SimpleDateFormat up = setUp(this.pattern, this.locale, new XCalendar(TimeZone.getTimeZone(TimeZones.GMT_ID + of.getOffset(moment2).toString()), this.locale), !this.leniency.isStrict());
            FieldPosition fieldPosition = new FieldPosition(17);
            String stringBuffer = up.format(from, new StringBuffer(), fieldPosition).toString();
            int beginIndex = fieldPosition.getBeginIndex();
            int endIndex = fieldPosition.getEndIndex();
            if (endIndex <= beginIndex || beginIndex <= 0 || (of.getID() instanceof ZonalOffset) || !hasTimezoneField()) {
                str = stringBuffer;
            } else {
                boolean isDaylightSaving = of.isDaylightSaving(moment2);
                if (!this.pattern.contains("zzzz")) {
                    nameStyle = isDaylightSaving ? NameStyle.SHORT_DAYLIGHT_TIME : NameStyle.SHORT_STANDARD_TIME;
                } else {
                    nameStyle = isDaylightSaving ? NameStyle.LONG_DAYLIGHT_TIME : NameStyle.LONG_STANDARD_TIME;
                }
                str = stringBuffer.substring(0, beginIndex) + of.getDisplayName(nameStyle, this.locale) + stringBuffer.substring(endIndex);
            }
        } else {
            throw new IllegalArgumentException("Not formattable: " + t);
        }
        appendable.append(str);
    }

    private boolean hasTimezoneField() {
        boolean z = false;
        for (int length = this.pattern.length() - 1; length >= 0; length--) {
            char charAt = this.pattern.charAt(length);
            if (charAt == '\'') {
                z = !z;
            } else if (!z && charAt == 'z') {
                return true;
            }
        }
        return false;
    }

    private static void updateRawValues(RawValues rawValues, SimpleDateFormat simpleDateFormat) {
        if (rawValues != null) {
            rawValues.accept(new Parsed((XCalendar) XCalendar.class.cast(simpleDateFormat.getCalendar())));
        }
    }

    private SimpleDateFormat setUpLocal() {
        return setUp(this.pattern, this.locale, new XCalendar(TimeZone.getTimeZone(TimeZones.GMT_ID), this.locale), !this.leniency.isStrict());
    }

    private static SimpleDateFormat setUp(String str, Locale locale, XCalendar xCalendar, boolean z) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str, locale);
        simpleDateFormat.setCalendar(xCalendar);
        simpleDateFormat.setLenient(z);
        return simpleDateFormat;
    }

    private T parseInternal(CharSequence charSequence, ParsePosition parsePosition, RawValues rawValues) {
        String canonical;
        String charSequence2 = charSequence.toString();
        if (this.type.equals(PlainDate.class)) {
            SimpleDateFormat upLocal = setUpLocal();
            r2 = TemporalType.JAVA_UTIL_DATE.translate(upLocal.parse(charSequence2, parsePosition)).toZonalTimestamp(ZonalOffset.UTC).getCalendarDate();
            updateRawValues(rawValues, upLocal);
        } else if (this.type.equals(PlainTime.class)) {
            SimpleDateFormat upLocal2 = setUpLocal();
            r2 = TemporalType.JAVA_UTIL_DATE.translate(upLocal2.parse(charSequence2, parsePosition)).toZonalTimestamp(ZonalOffset.UTC).getWallTime();
            updateRawValues(rawValues, upLocal2);
        } else if (this.type.equals(PlainTimestamp.class)) {
            SimpleDateFormat upLocal3 = setUpLocal();
            r2 = TemporalType.JAVA_UTIL_DATE.translate(upLocal3.parse(charSequence2, parsePosition)).toZonalTimestamp(ZonalOffset.UTC);
            updateRawValues(rawValues, upLocal3);
        } else {
            int i = 0;
            if (this.type.equals(Moment.class)) {
                String str = this.pattern;
                if (str.equals(RFC_1123_PATTERN)) {
                    String substring = charSequence2.substring(parsePosition.getIndex());
                    str = (substring.length() < 4 || substring.charAt(3) != ',') ? RFC_1123_SHORT : RFC_1123_WIDE;
                    int length = substring.length();
                    while (true) {
                        length--;
                        if (length < 0 || i >= 2) {
                            break;
                        }
                        if (substring.charAt(length) == ':') {
                            i++;
                        }
                    }
                    if (i >= 2) {
                        str = str.replace(":ss", "");
                    }
                }
                ZonalDateTime zonalDateTime = (ZonalDateTime) new SimpleFormatter(ZonalDateTime.class, str, this.locale, this.leniency, this.tzid).parseInternal(charSequence, parsePosition, rawValues);
                if (zonalDateTime != null) {
                    r2 = zonalDateTime.toMoment();
                }
            } else if (this.type.equals(ZonalDateTime.class)) {
                String str2 = this.tzid;
                String replace = str2 == null ? "GMT-18:00" : str2.replace("UTC", TimeZones.GMT_ID);
                XCalendar xCalendar = new XCalendar(TimeZone.getTimeZone(replace), this.locale);
                Date parse = setUp(this.pattern, this.locale, xCalendar, !this.leniency.isStrict()).parse(charSequence2, parsePosition);
                if (parse == null || parsePosition.getErrorIndex() >= 0) {
                    return null;
                }
                Parsed parsed = new Parsed(xCalendar);
                int i2 = xCalendar.get(15) + xCalendar.get(16);
                if (i2 == -1080) {
                    parsePosition.setErrorIndex(parsePosition.getIndex());
                    throw new IllegalArgumentException("Cannot parse text without timezone: " + charSequence2);
                }
                if (!xCalendar.getTimeZone().getID().equals(replace)) {
                    canonical = xCalendar.getTimeZone().getID();
                } else {
                    ZonalOffset ofTotalSeconds = ZonalOffset.ofTotalSeconds(i2 / 1000);
                    if (this.tzid != null && xCalendar.getTimeZone().getOffset(parse.getTime()) == i2) {
                        canonical = this.tzid;
                    } else {
                        canonical = ofTotalSeconds.canonical();
                    }
                }
                TZID id = Timezone.of(canonical).getID();
                parsed.setTimezone(id);
                PlainTimestamp createFrom = PlainTimestamp.axis().createFrom((ChronoEntity<?>) parsed, getAttributes(), this.leniency.isLax(), false);
                r2 = createFrom != null ? createFrom.inTimezone(id).inZonalView(id) : null;
                if (rawValues != null) {
                    rawValues.accept(parsed);
                }
            }
        }
        return this.type.cast(r2);
    }

    private static String getFormatPattern(DateFormat dateFormat) {
        if (dateFormat instanceof SimpleDateFormat) {
            return ((SimpleDateFormat) SimpleDateFormat.class.cast(dateFormat)).toPattern();
        }
        throw new IllegalStateException("Cannot retrieve format pattern: " + dateFormat);
    }

    private static class Parsed extends ChronoEntity<Parsed> {
        private TZID tzid = null;
        private final Map<ChronoElement<?>, Object> values;

        @Override // net.time4j.engine.ChronoEntity
        public <V> boolean isValid(ChronoElement<V> chronoElement, V v) {
            return chronoElement != null;
        }

        @Override // net.time4j.engine.ChronoEntity
        public /* bridge */ /* synthetic */ Parsed with(ChronoElement chronoElement, Object obj) {
            return with2((ChronoElement<ChronoElement>) chronoElement, (ChronoElement) obj);
        }

        Parsed(XCalendar xCalendar) {
            HashMap hashMap = new HashMap();
            if (xCalendar.isSet(1)) {
                hashMap.put(PlainDate.YEAR, Integer.valueOf(xCalendar.getRawValue(1)));
            }
            if (xCalendar.isSet(2)) {
                hashMap.put(PlainDate.MONTH_AS_NUMBER, Integer.valueOf(xCalendar.getRawValue(2) + 1));
            }
            if (xCalendar.isSet(6)) {
                hashMap.put(PlainDate.DAY_OF_YEAR, Integer.valueOf(xCalendar.getRawValue(6)));
            }
            if (xCalendar.isSet(5)) {
                hashMap.put(PlainDate.DAY_OF_MONTH, Integer.valueOf(xCalendar.getRawValue(5)));
            }
            if (xCalendar.isSet(9)) {
                hashMap.put(PlainTime.AM_PM_OF_DAY, Meridiem.values()[xCalendar.getRawValue(9)]);
            }
            if (xCalendar.isSet(10)) {
                hashMap.put(PlainTime.DIGITAL_HOUR_OF_AMPM, Integer.valueOf(xCalendar.getRawValue(10)));
            }
            if (xCalendar.isSet(11)) {
                hashMap.put(PlainTime.DIGITAL_HOUR_OF_DAY, Integer.valueOf(xCalendar.getRawValue(11)));
            }
            if (xCalendar.isSet(12)) {
                hashMap.put(PlainTime.MINUTE_OF_HOUR, Integer.valueOf(xCalendar.getRawValue(12)));
            }
            if (xCalendar.isSet(13)) {
                hashMap.put(PlainTime.SECOND_OF_MINUTE, Integer.valueOf(xCalendar.getRawValue(13)));
            }
            if (xCalendar.isSet(14)) {
                hashMap.put(PlainTime.MILLI_OF_SECOND, Integer.valueOf(xCalendar.getRawValue(14)));
            }
            this.values = Collections.unmodifiableMap(hashMap);
        }

        @Override // net.time4j.engine.ChronoEntity, net.time4j.engine.ChronoDisplay
        public boolean contains(ChronoElement<?> chronoElement) {
            return this.values.containsKey(chronoElement);
        }

        @Override // net.time4j.engine.ChronoEntity, net.time4j.engine.ChronoDisplay
        public <V> V get(ChronoElement<V> chronoElement) {
            check(chronoElement);
            return chronoElement.getType().cast(this.values.get(chronoElement));
        }

        @Override // net.time4j.engine.ChronoEntity, net.time4j.engine.ChronoDisplay
        public int getInt(ChronoElement<Integer> chronoElement) {
            if (this.values.containsKey(chronoElement)) {
                return ((Integer) Integer.class.cast(this.values.get(chronoElement))).intValue();
            }
            return Integer.MIN_VALUE;
        }

        @Override // net.time4j.engine.ChronoEntity, net.time4j.engine.ChronoDisplay
        public <V> V getMinimum(ChronoElement<V> chronoElement) {
            check(chronoElement);
            return chronoElement.getDefaultMinimum();
        }

        @Override // net.time4j.engine.ChronoEntity, net.time4j.engine.ChronoDisplay
        public <V> V getMaximum(ChronoElement<V> chronoElement) {
            check(chronoElement);
            return chronoElement.getDefaultMaximum();
        }

        @Override // net.time4j.engine.ChronoEntity
        /* renamed from: with, reason: avoid collision after fix types in other method */
        public <V> Parsed with2(ChronoElement<V> chronoElement, V v) {
            chronoElement.getClass();
            if (v == null) {
                this.values.remove(chronoElement);
            } else {
                this.values.put(chronoElement, v);
            }
            return this;
        }

        @Override // net.time4j.engine.ChronoEntity, net.time4j.engine.ChronoDisplay
        public boolean hasTimezone() {
            return this.tzid != null;
        }

        @Override // net.time4j.engine.ChronoEntity, net.time4j.engine.ChronoDisplay
        public TZID getTimezone() {
            TZID tzid = this.tzid;
            if (tzid != null) {
                return tzid;
            }
            throw new ChronoException("Timezone was not parsed.");
        }

        @Override // net.time4j.engine.ChronoEntity
        public Set<ChronoElement<?>> getRegisteredElements() {
            return Collections.unmodifiableSet(this.values.keySet());
        }

        void setTimezone(TZID tzid) {
            this.tzid = tzid;
        }

        @Override // net.time4j.engine.ChronoEntity
        protected Chronology<Parsed> getChronology() {
            throw new UnsupportedOperationException("Parsed values do not have any chronology.");
        }

        private void check(ChronoElement<?> chronoElement) {
            if (this.values.containsKey(chronoElement)) {
                return;
            }
            throw new ChronoException("Element not supported: " + chronoElement.name());
        }
    }

    private static class XCalendar extends GregorianCalendar {
        XCalendar(TimeZone timeZone, Locale locale) {
            super(timeZone, locale);
            setGregorianChange(SimpleFormatter.PROLEPTIC_GREGORIAN);
        }

        int getRawValue(int i) {
            return super.internalGet(i);
        }
    }
}

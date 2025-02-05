package net.time4j.format;

import androidx.exifinterface.media.ExifInterface;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import net.time4j.base.ResourceLoader;
import net.time4j.engine.BridgeChronology;
import net.time4j.engine.CalendarEra;
import net.time4j.engine.ChronoElement;
import net.time4j.engine.Chronology;
import net.time4j.format.internal.ExtendedPatterns;
import net.time4j.format.internal.FormatUtils;
import net.time4j.i18n.IsoTextProviderSPI;
import net.time4j.i18n.PropertyBundle;

/* loaded from: classes3.dex */
public final class CalendarText {
    private static final ConcurrentMap<String, CalendarText> CACHE;
    private static final FormatPatternProvider FORMAT_PATTERN_PROVIDER;
    public static final String ISO_CALENDAR_TYPE = "iso8601";
    private static final TextProvider JDK_PROVIDER;
    private static final TextProvider ROOT_PROVIDER;
    private static final Set<String> RTL;
    private final String calendarType;
    private final Map<TextWidth, TextAccessor> eras;
    private final Map<TextWidth, Map<OutputContext, TextAccessor>> leapMonths;
    private final Locale locale;
    private final Map<TextWidth, Map<OutputContext, TextAccessor>> meridiems;
    private final MissingResourceException mre;
    private final String provider;
    private final Map<TextWidth, Map<OutputContext, TextAccessor>> quarters;
    private final Map<TextWidth, Map<OutputContext, TextAccessor>> stdMonths;
    private final Map<String, String> textForms;
    private final Map<TextWidth, Map<OutputContext, TextAccessor>> weekdays;

    static {
        FormatPatternProvider isoTextProviderSPI;
        HashSet hashSet = new HashSet();
        hashSet.add("ar");
        hashSet.add("dv");
        hashSet.add("fa");
        hashSet.add("ha");
        hashSet.add("he");
        hashSet.add("iw");
        hashSet.add("ji");
        hashSet.add("ps");
        hashSet.add("sd");
        hashSet.add("ug");
        hashSet.add("ur");
        hashSet.add("yi");
        RTL = Collections.unmodifiableSet(hashSet);
        Iterator it = ResourceLoader.getInstance().services(FormatPatternProvider.class).iterator();
        if (it.hasNext()) {
            isoTextProviderSPI = (FormatPatternProvider) it.next();
        } else {
            isoTextProviderSPI = new IsoTextProviderSPI();
        }
        FORMAT_PATTERN_PROVIDER = new FormatPatterns(isoTextProviderSPI);
        AnonymousClass1 anonymousClass1 = null;
        JDK_PROVIDER = new JDKTextProvider(anonymousClass1);
        ROOT_PROVIDER = new FallbackProvider(anonymousClass1);
        CACHE = new ConcurrentHashMap();
    }

    private CalendarText(String str, Locale locale, TextProvider textProvider) {
        this.provider = textProvider.toString();
        Map<TextWidth, Map<OutputContext, TextAccessor>> unmodifiableMap = Collections.unmodifiableMap(getMonths(str, locale, textProvider, false));
        this.stdMonths = unmodifiableMap;
        Map<TextWidth, Map<OutputContext, TextAccessor>> months = getMonths(str, locale, textProvider, true);
        if (months == null) {
            this.leapMonths = unmodifiableMap;
        } else {
            this.leapMonths = Collections.unmodifiableMap(months);
        }
        EnumMap enumMap = new EnumMap(TextWidth.class);
        for (TextWidth textWidth : TextWidth.values()) {
            EnumMap enumMap2 = new EnumMap(OutputContext.class);
            for (OutputContext outputContext : OutputContext.values()) {
                enumMap2.put((EnumMap) outputContext, (OutputContext) new TextAccessor(textProvider.quarters(str, locale, textWidth, outputContext)));
            }
            enumMap.put((EnumMap) textWidth, (TextWidth) enumMap2);
        }
        this.quarters = Collections.unmodifiableMap(enumMap);
        EnumMap enumMap3 = new EnumMap(TextWidth.class);
        for (TextWidth textWidth2 : TextWidth.values()) {
            EnumMap enumMap4 = new EnumMap(OutputContext.class);
            for (OutputContext outputContext2 : OutputContext.values()) {
                enumMap4.put((EnumMap) outputContext2, (OutputContext) new TextAccessor(textProvider.weekdays(str, locale, textWidth2, outputContext2)));
            }
            enumMap3.put((EnumMap) textWidth2, (TextWidth) enumMap4);
        }
        this.weekdays = Collections.unmodifiableMap(enumMap3);
        EnumMap enumMap5 = new EnumMap(TextWidth.class);
        for (TextWidth textWidth3 : TextWidth.values()) {
            enumMap5.put((EnumMap) textWidth3, (TextWidth) new TextAccessor(textProvider.eras(str, locale, textWidth3)));
        }
        this.eras = Collections.unmodifiableMap(enumMap5);
        EnumMap enumMap6 = new EnumMap(TextWidth.class);
        for (TextWidth textWidth4 : TextWidth.values()) {
            EnumMap enumMap7 = new EnumMap(OutputContext.class);
            for (OutputContext outputContext3 : OutputContext.values()) {
                enumMap7.put((EnumMap) outputContext3, (OutputContext) new TextAccessor(textProvider.meridiems(str, locale, textWidth4, outputContext3)));
            }
            enumMap6.put((EnumMap) textWidth4, (TextWidth) enumMap7);
        }
        this.meridiems = Collections.unmodifiableMap(enumMap6);
        HashMap hashMap = new HashMap();
        MissingResourceException e = null;
        try {
            PropertyBundle load = PropertyBundle.load("calendar/names/" + str + "/" + str, locale);
            for (String str2 : load.keySet()) {
                hashMap.put(str2, load.getString(str2));
            }
        } catch (MissingResourceException e2) {
            e = e2;
        }
        this.textForms = Collections.unmodifiableMap(hashMap);
        this.calendarType = str;
        this.locale = locale;
        this.mre = e;
    }

    public static CalendarText getIsoInstance(Locale locale) {
        return getInstance(ISO_CALENDAR_TYPE, locale);
    }

    public static CalendarText getInstance(Chronology<?> chronology, Locale locale) {
        return getInstance(extractCalendarType(chronology), locale);
    }

    public static CalendarText getInstance(String str, Locale locale) {
        if (str == null) {
            throw new NullPointerException("Missing calendar type.");
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(':');
        sb.append(locale.getLanguage());
        String country = locale.getCountry();
        if (!country.isEmpty()) {
            sb.append('-');
            sb.append(country);
        }
        String sb2 = sb.toString();
        CalendarText calendarText = CACHE.get(sb2);
        if (calendarText != null) {
            return calendarText;
        }
        TextProvider textProvider = null;
        if (locale.getLanguage().isEmpty() && str.equals(ISO_CALENDAR_TYPE)) {
            textProvider = ROOT_PROVIDER;
        } else {
            Iterator it = ResourceLoader.getInstance().services(TextProvider.class).iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                TextProvider textProvider2 = (TextProvider) it.next();
                if (textProvider2.supportsCalendarType(str) && textProvider2.supportsLanguage(locale)) {
                    textProvider = textProvider2;
                    break;
                }
            }
            if (textProvider == null) {
                TextProvider textProvider3 = JDK_PROVIDER;
                if (textProvider3.supportsCalendarType(str) && textProvider3.supportsLanguage(locale)) {
                    textProvider = textProvider3;
                }
                if (textProvider == null) {
                    textProvider = ROOT_PROVIDER;
                }
            }
        }
        CalendarText calendarText2 = new CalendarText(str, locale, textProvider);
        CalendarText putIfAbsent = CACHE.putIfAbsent(sb2, calendarText2);
        return putIfAbsent != null ? putIfAbsent : calendarText2;
    }

    public TextAccessor getStdMonths(TextWidth textWidth, OutputContext outputContext) {
        return getMonths(textWidth, outputContext, false);
    }

    public TextAccessor getLeapMonths(TextWidth textWidth, OutputContext outputContext) {
        return getMonths(textWidth, outputContext, true);
    }

    public TextAccessor getQuarters(TextWidth textWidth, OutputContext outputContext) {
        return this.quarters.get(textWidth).get(outputContext);
    }

    public TextAccessor getWeekdays(TextWidth textWidth, OutputContext outputContext) {
        return this.weekdays.get(textWidth).get(outputContext);
    }

    public TextAccessor getEras(TextWidth textWidth) {
        return this.eras.get(textWidth);
    }

    public TextAccessor getMeridiems(TextWidth textWidth, OutputContext outputContext) {
        return this.meridiems.get(textWidth).get(outputContext);
    }

    public Map<String, String> getTextForms() {
        return this.textForms;
    }

    public <V extends Enum<V>> TextAccessor getTextForms(ChronoElement<V> chronoElement, String... strArr) {
        return getTextForms(chronoElement.name(), chronoElement.getType(), strArr);
    }

    public <V extends Enum<V>> TextAccessor getTextForms(String str, Class<V> cls, String... strArr) {
        if (this.mre != null) {
            throw new MissingResourceException(this.mre.getMessage(), this.mre.getClassName(), this.mre.getKey());
        }
        V[] enumConstants = cls.getEnumConstants();
        int length = enumConstants.length;
        String[] strArr2 = new String[length];
        String keyPrefix = getKeyPrefix(str);
        int i = !CalendarEra.class.isAssignableFrom(cls) ? 1 : 0;
        for (int i2 = 0; i2 < length; i2++) {
            String str2 = null;
            int i3 = 0;
            while (true) {
                String keyStart = getKeyStart(keyPrefix, i3, strArr);
                if (keyStart == null) {
                    break;
                }
                String key = toKey(keyStart, i2, i);
                if (this.textForms.containsKey(key)) {
                    str2 = key;
                    break;
                }
                i3++;
            }
            if (str2 == null) {
                if (this.textForms.containsKey(str)) {
                    strArr2[i2] = this.textForms.get(str);
                } else {
                    strArr2[i2] = enumConstants[i2].name();
                }
            } else {
                strArr2[i2] = this.textForms.get(str2);
            }
        }
        return new TextAccessor(strArr2);
    }

    public static String patternForDate(DisplayMode displayMode, Locale locale) {
        return FORMAT_PATTERN_PROVIDER.getDatePattern(displayMode, locale);
    }

    public static String patternForTime(DisplayMode displayMode, Locale locale) {
        return FORMAT_PATTERN_PROVIDER.getTimePattern(displayMode, locale);
    }

    public static String patternForTimestamp(DisplayMode displayMode, DisplayMode displayMode2, Locale locale) {
        return FormatUtils.removeZones(FORMAT_PATTERN_PROVIDER.getDateTimePattern(displayMode, displayMode2, locale));
    }

    public static String patternForMoment(DisplayMode displayMode, DisplayMode displayMode2, Locale locale) {
        return FORMAT_PATTERN_PROVIDER.getDateTimePattern(displayMode, displayMode2, locale);
    }

    public static String patternForInterval(Locale locale) {
        return FORMAT_PATTERN_PROVIDER.getIntervalPattern(locale);
    }

    public String toString() {
        return this.provider + "(" + this.calendarType + "/" + this.locale + ")";
    }

    public static void clearCache() {
        CACHE.clear();
    }

    public static boolean isRTL(Locale locale) {
        return RTL.contains(locale.getLanguage());
    }

    static String extractCalendarType(Chronology<?> chronology) {
        while (chronology instanceof BridgeChronology) {
            chronology = chronology.preparser();
        }
        CalendarType calendarType = (CalendarType) chronology.getChronoType().getAnnotation(CalendarType.class);
        return calendarType == null ? ISO_CALENDAR_TYPE : calendarType.value();
    }

    private TextAccessor getMonths(TextWidth textWidth, OutputContext outputContext, boolean z) {
        if (z) {
            return this.leapMonths.get(textWidth).get(outputContext);
        }
        return this.stdMonths.get(textWidth).get(outputContext);
    }

    private static Map<TextWidth, Map<OutputContext, TextAccessor>> getMonths(String str, Locale locale, TextProvider textProvider, boolean z) {
        int i;
        OutputContext[] outputContextArr;
        EnumMap enumMap;
        TextWidth textWidth;
        EnumMap enumMap2 = new EnumMap(TextWidth.class);
        TextWidth[] values = TextWidth.values();
        int length = values.length;
        boolean z2 = false;
        int i2 = 0;
        while (i2 < length) {
            TextWidth textWidth2 = values[i2];
            EnumMap enumMap3 = new EnumMap(OutputContext.class);
            OutputContext[] values2 = OutputContext.values();
            int length2 = values2.length;
            boolean z3 = z2;
            int i3 = 0;
            while (i3 < length2) {
                OutputContext outputContext = values2[i3];
                int i4 = i3;
                String[] months = textProvider.months(str, locale, textWidth2, outputContext, z);
                if (!z || z3) {
                    i = length2;
                    outputContextArr = values2;
                    enumMap = enumMap3;
                    textWidth = textWidth2;
                } else {
                    i = length2;
                    outputContextArr = values2;
                    enumMap = enumMap3;
                    textWidth = textWidth2;
                    z3 = !Arrays.equals(textProvider.months(str, locale, textWidth2, outputContext, false), months);
                }
                enumMap.put((EnumMap) outputContext, (OutputContext) new TextAccessor(months));
                i3 = i4 + 1;
                length2 = i;
                values2 = outputContextArr;
                enumMap3 = enumMap;
                textWidth2 = textWidth;
            }
            enumMap2.put((EnumMap) textWidth2, (TextWidth) enumMap3);
            i2++;
            z2 = z3;
        }
        if (!z || z2) {
            return enumMap2;
        }
        return null;
    }

    private String getKeyPrefix(String str) {
        if (!this.textForms.containsKey("useShortKeys") || !"true".equals(this.textForms.get("useShortKeys"))) {
            return str;
        }
        if (str.equals("MONTH_OF_YEAR") || str.equals("DAY_OF_WEEK") || str.equals("QUARTER_OF_YEAR") || str.equals("ERA")) {
            return str.substring(0, 1);
        }
        return str.equals("EVANGELIST") ? "EV" : str.equals("SANSCULOTTIDES") ? ExifInterface.LATITUDE_SOUTH : str.equals("DAY_OF_DECADE") ? "D" : str;
    }

    private static String getKeyStart(String str, int i, String... strArr) {
        if (strArr == null || strArr.length <= 0) {
            if (i > 0) {
                return null;
            }
            return str;
        }
        if (strArr.length < i) {
            return null;
        }
        StringBuilder sb = new StringBuilder(str);
        boolean z = true;
        for (int i2 = 0; i2 < strArr.length - i; i2++) {
            if (z) {
                sb.append('(');
                z = false;
            } else {
                sb.append('|');
            }
            sb.append(strArr[i2]);
        }
        if (!z) {
            sb.append(')');
        }
        return sb.toString();
    }

    private static String toKey(String str, int i, int i2) {
        return str + '_' + (i + i2);
    }

    private static class JDKTextProvider implements TextProvider {
        public String toString() {
            return "JDKTextProvider";
        }

        private JDKTextProvider() {
        }

        /* synthetic */ JDKTextProvider(AnonymousClass1 anonymousClass1) {
            this();
        }

        @Override // net.time4j.format.TextProvider
        public boolean supportsCalendarType(String str) {
            return CalendarText.ISO_CALENDAR_TYPE.equals(str);
        }

        @Override // net.time4j.format.TextProvider
        public boolean supportsLanguage(Locale locale) {
            String language = locale.getLanguage();
            for (Locale locale2 : DateFormatSymbols.getAvailableLocales()) {
                if (locale2.getLanguage().equals(language)) {
                    return true;
                }
            }
            return false;
        }

        @Override // net.time4j.format.TextProvider
        public String[] getSupportedCalendarTypes() {
            return new String[]{CalendarText.ISO_CALENDAR_TYPE};
        }

        @Override // net.time4j.format.TextProvider
        public Locale[] getAvailableLocales() {
            return DateFormatSymbols.getAvailableLocales();
        }

        @Override // net.time4j.format.TextProvider
        public String[] months(String str, Locale locale, TextWidth textWidth, OutputContext outputContext, boolean z) {
            DateFormatSymbols dateFormatSymbols = DateFormatSymbols.getInstance(locale);
            int i = AnonymousClass1.$SwitchMap$net$time4j$format$TextWidth[textWidth.ordinal()];
            if (i == 1) {
                return dateFormatSymbols.getMonths();
            }
            if (i == 2 || i == 3) {
                return dateFormatSymbols.getShortMonths();
            }
            if (i == 4) {
                return narrow(dateFormatSymbols.getShortMonths(), 12);
            }
            throw new UnsupportedOperationException(textWidth.name());
        }

        @Override // net.time4j.format.TextProvider
        public String[] quarters(String str, Locale locale, TextWidth textWidth, OutputContext outputContext) {
            return new String[]{"Q1", "Q2", "Q3", "Q4"};
        }

        @Override // net.time4j.format.TextProvider
        public String[] weekdays(String str, Locale locale, TextWidth textWidth, OutputContext outputContext) {
            String[] weekdays;
            DateFormatSymbols dateFormatSymbols = DateFormatSymbols.getInstance(locale);
            int i = AnonymousClass1.$SwitchMap$net$time4j$format$TextWidth[textWidth.ordinal()];
            if (i == 1) {
                weekdays = dateFormatSymbols.getWeekdays();
            } else if (i == 2 || i == 3) {
                weekdays = dateFormatSymbols.getShortWeekdays();
            } else if (i == 4) {
                weekdays = narrow(weekdays("", locale, TextWidth.SHORT, outputContext), 7);
            } else {
                throw new UnsupportedOperationException("Unknown text width: " + textWidth);
            }
            if (weekdays.length <= 7) {
                return weekdays;
            }
            String str2 = weekdays[1];
            String[] strArr = new String[7];
            System.arraycopy(weekdays, 2, strArr, 0, 6);
            strArr[6] = str2;
            return strArr;
        }

        @Override // net.time4j.format.TextProvider
        public String[] eras(String str, Locale locale, TextWidth textWidth) {
            DateFormatSymbols dateFormatSymbols = DateFormatSymbols.getInstance(locale);
            if (textWidth == TextWidth.NARROW) {
                String[] eras = dateFormatSymbols.getEras();
                String[] strArr = new String[eras.length];
                int length = eras.length;
                for (int i = 0; i < length; i++) {
                    if (!eras[i].isEmpty()) {
                        strArr[i] = toSingleLetter(eras[i]);
                    } else if (i == 0 && eras.length == 2) {
                        strArr[i] = "B";
                    } else if (i == 1 && eras.length == 2) {
                        strArr[i] = ExifInterface.GPS_MEASUREMENT_IN_PROGRESS;
                    } else {
                        strArr[i] = String.valueOf(i);
                    }
                }
                return strArr;
            }
            return dateFormatSymbols.getEras();
        }

        @Override // net.time4j.format.TextProvider
        public String[] meridiems(String str, Locale locale, TextWidth textWidth, OutputContext outputContext) {
            if (textWidth == TextWidth.NARROW) {
                return new String[]{ExifInterface.GPS_MEASUREMENT_IN_PROGRESS, "P"};
            }
            return DateFormatSymbols.getInstance(locale).getAmPmStrings();
        }

        private static String[] narrow(String[] strArr, int i) {
            String[] strArr2 = new String[i];
            for (int i2 = 0; i2 < i; i2++) {
                if (!strArr[i2].isEmpty()) {
                    strArr2[i2] = toSingleLetter(strArr[i2]);
                } else {
                    strArr2[i2] = String.valueOf(i2 + 1);
                }
            }
            return strArr2;
        }

        private static String toSingleLetter(String str) {
            char charAt = Normalizer.normalize(str, Normalizer.Form.NFD).charAt(0);
            if (charAt >= 'A' && charAt <= 'Z') {
                return String.valueOf(charAt);
            }
            if (charAt >= 'a' && charAt <= 'z') {
                return String.valueOf((char) (charAt - ' '));
            }
            if (charAt < 1040 || charAt > 1071) {
                return (charAt < 1072 || charAt > 1103) ? str : String.valueOf((char) (charAt - ' '));
            }
            return String.valueOf(charAt);
        }
    }

    private static class FallbackProvider implements TextProvider {
        @Override // net.time4j.format.TextProvider
        public boolean supportsCalendarType(String str) {
            return true;
        }

        @Override // net.time4j.format.TextProvider
        public boolean supportsLanguage(Locale locale) {
            return true;
        }

        public String toString() {
            return "FallbackProvider";
        }

        private FallbackProvider() {
        }

        /* synthetic */ FallbackProvider(AnonymousClass1 anonymousClass1) {
            this();
        }

        @Override // net.time4j.format.TextProvider
        public String[] getSupportedCalendarTypes() {
            throw new UnsupportedOperationException("Never called.");
        }

        @Override // net.time4j.format.TextProvider
        public Locale[] getAvailableLocales() {
            throw new UnsupportedOperationException("Never called.");
        }

        @Override // net.time4j.format.TextProvider
        public String[] months(String str, Locale locale, TextWidth textWidth, OutputContext outputContext, boolean z) {
            if (textWidth == TextWidth.WIDE) {
                return new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13"};
            }
            return new String[]{"1", ExifInterface.GPS_MEASUREMENT_2D, ExifInterface.GPS_MEASUREMENT_3D, "4", "5", "6", "7", "8", "9", "10", "11", "12", "13"};
        }

        @Override // net.time4j.format.TextProvider
        public String[] quarters(String str, Locale locale, TextWidth textWidth, OutputContext outputContext) {
            if (textWidth == TextWidth.NARROW) {
                return new String[]{"1", ExifInterface.GPS_MEASUREMENT_2D, ExifInterface.GPS_MEASUREMENT_3D, "4"};
            }
            return new String[]{"Q1", "Q2", "Q3", "Q4"};
        }

        @Override // net.time4j.format.TextProvider
        public String[] weekdays(String str, Locale locale, TextWidth textWidth, OutputContext outputContext) {
            return new String[]{"1", ExifInterface.GPS_MEASUREMENT_2D, ExifInterface.GPS_MEASUREMENT_3D, "4", "5", "6", "7"};
        }

        @Override // net.time4j.format.TextProvider
        public String[] eras(String str, Locale locale, TextWidth textWidth) {
            if (textWidth == TextWidth.NARROW) {
                return new String[]{"B", ExifInterface.GPS_MEASUREMENT_IN_PROGRESS};
            }
            return new String[]{"BC", "AD"};
        }

        @Override // net.time4j.format.TextProvider
        public String[] meridiems(String str, Locale locale, TextWidth textWidth, OutputContext outputContext) {
            if (textWidth == TextWidth.NARROW) {
                return new String[]{ExifInterface.GPS_MEASUREMENT_IN_PROGRESS, "P"};
            }
            return new String[]{"AM", "PM"};
        }
    }

    private static class FormatPatterns implements FormatPatternProvider {
        private final FormatPatternProvider delegate;

        FormatPatterns(FormatPatternProvider formatPatternProvider) {
            this.delegate = formatPatternProvider;
        }

        @Override // net.time4j.format.FormatPatternProvider
        public String getDatePattern(DisplayMode displayMode, Locale locale) {
            FormatPatternProvider formatPatternProvider = this.delegate;
            if (formatPatternProvider == null) {
                return getFormatPattern(DateFormat.getDateInstance(getFormatStyle(displayMode), locale));
            }
            return formatPatternProvider.getDatePattern(displayMode, locale);
        }

        @Override // net.time4j.format.FormatPatternProvider
        public String getTimePattern(DisplayMode displayMode, Locale locale) {
            String timePattern;
            FormatPatternProvider formatPatternProvider = this.delegate;
            if (formatPatternProvider == null) {
                timePattern = getFormatPattern(DateFormat.getTimeInstance(getFormatStyle(displayMode), locale));
            } else if (formatPatternProvider instanceof ExtendedPatterns) {
                timePattern = ((ExtendedPatterns) ExtendedPatterns.class.cast(formatPatternProvider)).getTimePattern(displayMode, locale, true);
            } else {
                timePattern = formatPatternProvider.getTimePattern(displayMode, locale);
            }
            return FormatUtils.removeZones(timePattern);
        }

        @Override // net.time4j.format.FormatPatternProvider
        public String getDateTimePattern(DisplayMode displayMode, DisplayMode displayMode2, Locale locale) {
            FormatPatternProvider formatPatternProvider = this.delegate;
            if (formatPatternProvider == null) {
                return getFormatPattern(DateFormat.getDateTimeInstance(getFormatStyle(displayMode), getFormatStyle(displayMode2), locale));
            }
            return this.delegate.getDateTimePattern(displayMode, displayMode2, locale).replace("{1}", this.delegate.getDatePattern(displayMode, locale)).replace("{0}", formatPatternProvider.getTimePattern(displayMode2, locale));
        }

        @Override // net.time4j.format.FormatPatternProvider
        public String getIntervalPattern(Locale locale) {
            FormatPatternProvider formatPatternProvider = this.delegate;
            if (formatPatternProvider == null) {
                return (locale.getLanguage().isEmpty() && locale.getCountry().isEmpty()) ? "{0}/{1}" : "{0} - {1}";
            }
            return formatPatternProvider.getIntervalPattern(locale);
        }

        private static int getFormatStyle(DisplayMode displayMode) {
            int i = AnonymousClass1.$SwitchMap$net$time4j$format$DisplayMode[displayMode.ordinal()];
            if (i == 1) {
                return 0;
            }
            if (i == 2) {
                return 1;
            }
            if (i == 3) {
                return 2;
            }
            if (i == 4) {
                return 3;
            }
            throw new UnsupportedOperationException("Unknown: " + displayMode);
        }

        private static String getFormatPattern(DateFormat dateFormat) {
            if (dateFormat instanceof SimpleDateFormat) {
                return ((SimpleDateFormat) SimpleDateFormat.class.cast(dateFormat)).toPattern();
            }
            throw new IllegalStateException("Cannot retrieve format pattern: " + dateFormat);
        }
    }

    /* renamed from: net.time4j.format.CalendarText$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$time4j$format$DisplayMode;
        static final /* synthetic */ int[] $SwitchMap$net$time4j$format$TextWidth;

        static {
            int[] iArr = new int[DisplayMode.values().length];
            $SwitchMap$net$time4j$format$DisplayMode = iArr;
            try {
                iArr[DisplayMode.FULL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$net$time4j$format$DisplayMode[DisplayMode.LONG.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$net$time4j$format$DisplayMode[DisplayMode.MEDIUM.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$net$time4j$format$DisplayMode[DisplayMode.SHORT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            int[] iArr2 = new int[TextWidth.values().length];
            $SwitchMap$net$time4j$format$TextWidth = iArr2;
            try {
                iArr2[TextWidth.WIDE.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$net$time4j$format$TextWidth[TextWidth.ABBREVIATED.ordinal()] = 2;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$net$time4j$format$TextWidth[TextWidth.SHORT.ordinal()] = 3;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$net$time4j$format$TextWidth[TextWidth.NARROW.ordinal()] = 4;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }
}

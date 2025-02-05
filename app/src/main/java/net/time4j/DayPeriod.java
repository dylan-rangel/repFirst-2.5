package net.time4j;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import net.time4j.engine.AttributeKey;
import net.time4j.engine.AttributeQuery;
import net.time4j.engine.BasicElement;
import net.time4j.engine.ChronoDisplay;
import net.time4j.engine.ChronoElement;
import net.time4j.engine.ChronoEntity;
import net.time4j.engine.ChronoException;
import net.time4j.engine.ChronoExtension;
import net.time4j.engine.ChronoFunction;
import net.time4j.engine.Chronology;
import net.time4j.engine.ElementRule;
import net.time4j.format.Attributes;
import net.time4j.format.CalendarText;
import net.time4j.format.OutputContext;
import net.time4j.format.TextElement;
import net.time4j.format.TextWidth;

/* loaded from: classes3.dex */
public final class DayPeriod {
    private static final AttributeKey<DayPeriod> CUSTOM;
    private static DayPeriod FALLBACK;
    private static final SortedMap<PlainTime, String> STD_RULES;
    private final transient String calendarType;
    private final transient SortedMap<PlainTime, String> codeMap;
    private final transient Locale locale;

    static {
        TreeMap treeMap = new TreeMap();
        treeMap.put(PlainTime.midnightAtStartOfDay(), "am");
        treeMap.put(PlainTime.of(12), "pm");
        SortedMap<PlainTime, String> unmodifiableSortedMap = Collections.unmodifiableSortedMap(treeMap);
        STD_RULES = unmodifiableSortedMap;
        FALLBACK = new DayPeriod(Locale.ROOT, CalendarText.ISO_CALENDAR_TYPE, unmodifiableSortedMap);
        CUSTOM = Attributes.createKey("CUSTOM_DAY_PERIOD", DayPeriod.class);
    }

    private DayPeriod(Locale locale, String str, SortedMap<PlainTime, String> sortedMap) {
        this.locale = locale;
        this.calendarType = str;
        this.codeMap = Collections.unmodifiableSortedMap(sortedMap);
    }

    public static DayPeriod of(Locale locale) {
        return of(locale, CalendarText.ISO_CALENDAR_TYPE);
    }

    public static DayPeriod of(Map<PlainTime, String> map) {
        if (map.isEmpty()) {
            throw new IllegalArgumentException("Label map is empty.");
        }
        TreeMap treeMap = new TreeMap(map);
        for (PlainTime plainTime : map.keySet()) {
            if (plainTime.getHour() == 24) {
                treeMap.put(PlainTime.midnightAtStartOfDay(), map.get(plainTime));
                treeMap.remove(plainTime);
            } else if (map.get(plainTime).isEmpty()) {
                throw new IllegalArgumentException("Map has empty label: " + map);
            }
        }
        return new DayPeriod(null, "", treeMap);
    }

    public ChronoFunction<ChronoDisplay, String> fixed() {
        return fixed(TextWidth.WIDE, OutputContext.FORMAT);
    }

    public ChronoFunction<ChronoDisplay, String> fixed(TextWidth textWidth, OutputContext outputContext) {
        return new PeriodName(true, textWidth, outputContext);
    }

    public ChronoFunction<ChronoDisplay, String> approximate() {
        return approximate(TextWidth.WIDE, OutputContext.FORMAT);
    }

    public ChronoFunction<ChronoDisplay, String> approximate(TextWidth textWidth, OutputContext outputContext) {
        return new PeriodName(false, textWidth, outputContext);
    }

    public PlainTime getStart(PlainTime plainTime) {
        if (plainTime.getHour() == 24) {
            plainTime = PlainTime.midnightAtStartOfDay();
        }
        PlainTime lastKey = this.codeMap.lastKey();
        for (PlainTime plainTime2 : this.codeMap.keySet()) {
            if (plainTime.isSimultaneous(plainTime2)) {
                return plainTime2;
            }
            if (plainTime.isBefore(plainTime2)) {
                break;
            }
            lastKey = plainTime2;
        }
        return lastKey;
    }

    public PlainTime getEnd(PlainTime plainTime) {
        if (plainTime.getHour() == 24) {
            plainTime = PlainTime.midnightAtStartOfDay();
        }
        for (PlainTime plainTime2 : this.codeMap.keySet()) {
            if (plainTime.isBefore(plainTime2)) {
                return plainTime2;
            }
        }
        return this.codeMap.firstKey();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DayPeriod)) {
            return false;
        }
        DayPeriod dayPeriod = (DayPeriod) obj;
        Locale locale = this.locale;
        if (locale == null) {
            if (dayPeriod.locale != null) {
                return false;
            }
        } else if (!locale.equals(dayPeriod.locale)) {
            return false;
        }
        return this.codeMap.equals(dayPeriod.codeMap) && this.calendarType.equals(dayPeriod.calendarType);
    }

    public int hashCode() {
        return this.codeMap.hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(64);
        sb.append("DayPeriod[");
        if (isPredefined()) {
            sb.append("locale=");
            sb.append(this.locale);
            sb.append(',');
            if (!this.calendarType.equals(CalendarText.ISO_CALENDAR_TYPE)) {
                sb.append(",calendar-type=");
                sb.append(this.calendarType);
                sb.append(',');
            }
        }
        sb.append(this.codeMap);
        sb.append(']');
        return sb.toString();
    }

    static DayPeriod of(Locale locale, String str) {
        String language = locale.getLanguage();
        if (language.equals("nn")) {
            locale = new Locale("nb");
        }
        Map<String, String> loadTextForms = loadTextForms(locale, str);
        TreeMap treeMap = new TreeMap();
        for (String str2 : loadTextForms.keySet()) {
            if (accept(str2)) {
                int parseInt = Integer.parseInt(str2.substring(1, 3));
                int parseInt2 = Integer.parseInt(str2.substring(3, 5));
                PlainTime midnightAtStartOfDay = PlainTime.midnightAtStartOfDay();
                if (parseInt == 24) {
                    if (parseInt2 != 0) {
                        throw new IllegalStateException("Invalid time key: " + str2);
                    }
                } else if (parseInt >= 0 && parseInt < 24 && parseInt2 >= 0 && parseInt2 < 60) {
                    midnightAtStartOfDay = midnightAtStartOfDay.plus((parseInt * 60) + parseInt2, ClockUnit.MINUTES);
                } else {
                    throw new IllegalStateException("Invalid time key: " + str2);
                }
                treeMap.put(midnightAtStartOfDay, loadTextForms.get(str2));
            }
        }
        if (treeMap.isEmpty() || language.isEmpty()) {
            return FALLBACK;
        }
        Iterator it = treeMap.keySet().iterator();
        String str3 = "";
        while (it.hasNext()) {
            String str4 = (String) treeMap.get((PlainTime) it.next());
            if (str4.equals(str3)) {
                it.remove();
            } else {
                str3 = str4;
            }
        }
        return new DayPeriod(locale, str, treeMap);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isPredefined() {
        return this.locale != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getFixedCode(PlainTime plainTime) {
        int intValue = ((Integer) plainTime.get(PlainTime.MINUTE_OF_DAY)).intValue();
        return (intValue == 0 || intValue == 1440) ? "midnight" : intValue < 720 ? "am" : intValue == 720 ? "noon" : "pm";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String createKey(Map<String, String> map, TextWidth textWidth, OutputContext outputContext, String str) {
        if (textWidth == TextWidth.SHORT) {
            textWidth = TextWidth.ABBREVIATED;
        }
        String str2 = toPrefix(textWidth, outputContext) + str;
        if (map.containsKey(str2)) {
            return str2;
        }
        if (outputContext != OutputContext.STANDALONE) {
            return textWidth != TextWidth.ABBREVIATED ? createKey(map, TextWidth.ABBREVIATED, outputContext, str) : str2;
        }
        if (textWidth == TextWidth.ABBREVIATED) {
            return createKey(map, textWidth, OutputContext.FORMAT, str);
        }
        return createKey(map, TextWidth.ABBREVIATED, outputContext, str);
    }

    /* renamed from: net.time4j.DayPeriod$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$time4j$format$TextWidth;

        static {
            int[] iArr = new int[TextWidth.values().length];
            $SwitchMap$net$time4j$format$TextWidth = iArr;
            try {
                iArr[TextWidth.WIDE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$net$time4j$format$TextWidth[TextWidth.NARROW.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    private static String toPrefix(TextWidth textWidth, OutputContext outputContext) {
        int i = AnonymousClass1.$SwitchMap$net$time4j$format$TextWidth[textWidth.ordinal()];
        char c = i != 1 ? i != 2 ? 'a' : 'n' : 'w';
        if (outputContext == OutputContext.STANDALONE) {
            c = Character.toUpperCase(c);
        }
        return "P(" + c + ")_";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Map<String, String> loadTextForms(Locale locale, String str) {
        Map<String, String> textForms = CalendarText.getInstance(str, locale).getTextForms();
        return (str.equals(CalendarText.ISO_CALENDAR_TYPE) || "true".equals(textForms.get("hasDayPeriods"))) ? textForms : CalendarText.getIsoInstance(locale).getTextForms();
    }

    private static boolean accept(String str) {
        return str.charAt(0) == 'T' && str.length() == 5 && Character.isDigit(str.charAt(1));
    }

    static class Extension implements ChronoExtension {
        Extension() {
        }

        @Override // net.time4j.engine.ChronoExtension
        public boolean accept(Class<?> cls) {
            return PlainTime.class.isAssignableFrom(cls);
        }

        @Override // net.time4j.engine.ChronoExtension
        public Set<ChronoElement<?>> getElements(Locale locale, AttributeQuery attributeQuery) {
            DayPeriod from = from(locale, attributeQuery);
            HashSet hashSet = new HashSet();
            hashSet.add(new Element(false, from));
            if (!attributeQuery.contains(DayPeriod.CUSTOM)) {
                hashSet.add(new Element(true, from));
            }
            return Collections.unmodifiableSet(hashSet);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:23:0x012b A[LOOP:0: B:12:0x003f->B:23:0x012b, LOOP_END] */
        /* JADX WARN: Removed duplicated region for block: B:24:0x011a A[EDGE_INSN: B:24:0x011a->B:25:0x011a BREAK  A[LOOP:0: B:12:0x003f->B:23:0x012b], SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:52:0x00ec  */
        /* JADX WARN: Removed duplicated region for block: B:70:0x0115 A[SYNTHETIC] */
        /* JADX WARN: Type inference failed for: r0v2, types: [net.time4j.engine.ChronoEntity] */
        /* JADX WARN: Type inference failed for: r0v5, types: [net.time4j.engine.ChronoEntity, net.time4j.engine.ChronoEntity<?>] */
        /* JADX WARN: Type inference failed for: r0v7 */
        /* JADX WARN: Type inference failed for: r0v8 */
        @Override // net.time4j.engine.ChronoExtension
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public net.time4j.engine.ChronoEntity<?> resolve(net.time4j.engine.ChronoEntity<?> r17, java.util.Locale r18, net.time4j.engine.AttributeQuery r19) {
            /*
                Method dump skipped, instructions count: 359
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: net.time4j.DayPeriod.Extension.resolve(net.time4j.engine.ChronoEntity, java.util.Locale, net.time4j.engine.AttributeQuery):net.time4j.engine.ChronoEntity");
        }

        @Override // net.time4j.engine.ChronoExtension
        public boolean canResolve(ChronoElement<?> chronoElement) {
            return chronoElement instanceof Element;
        }

        private static int getHour12(ChronoEntity<?> chronoEntity) {
            if (chronoEntity.contains(PlainTime.CLOCK_HOUR_OF_AMPM)) {
                int intValue = ((Integer) chronoEntity.get(PlainTime.CLOCK_HOUR_OF_AMPM)).intValue();
                if (intValue == 12) {
                    return 0;
                }
                return intValue;
            }
            if (chronoEntity.contains(PlainTime.DIGITAL_HOUR_OF_AMPM)) {
                return ((Integer) chronoEntity.get(PlainTime.DIGITAL_HOUR_OF_AMPM)).intValue();
            }
            return -1;
        }

        private static DayPeriod from(Locale locale, AttributeQuery attributeQuery) {
            if (attributeQuery.contains(DayPeriod.CUSTOM)) {
                return (DayPeriod) attributeQuery.get(DayPeriod.CUSTOM);
            }
            return DayPeriod.of(locale, (String) attributeQuery.get(Attributes.CALENDAR_TYPE, CalendarText.ISO_CALENDAR_TYPE));
        }
    }

    static class Element extends BasicElement<String> implements TextElement<String>, ElementRule<ChronoEntity<?>, String> {
        private static final long serialVersionUID = 5589976208326940032L;
        private final transient DayPeriod dayPeriod;
        private final transient boolean fixed;

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtCeiling(ChronoEntity<?> chronoEntity) {
            return null;
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtFloor(ChronoEntity<?> chronoEntity) {
            return null;
        }

        @Override // net.time4j.engine.ChronoElement
        public boolean isDateElement() {
            return false;
        }

        @Override // net.time4j.engine.ChronoElement
        public boolean isTimeElement() {
            return true;
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: isValid, reason: avoid collision after fix types in other method */
        public boolean isValid2(ChronoEntity<?> chronoEntity, String str) {
            return false;
        }

        Element(boolean z, Locale locale, String str) {
            this(z, DayPeriod.of(locale, str));
        }

        Element(boolean z, DayPeriod dayPeriod) {
            super(z ? "FIXED_DAY_PERIOD" : "APPROXIMATE_DAY_PERIOD");
            this.fixed = z;
            this.dayPeriod = dayPeriod;
        }

        @Override // net.time4j.engine.ChronoElement
        public Class<String> getType() {
            return String.class;
        }

        @Override // net.time4j.engine.BasicElement, net.time4j.engine.ChronoElement
        public char getSymbol() {
            return this.fixed ? 'b' : 'B';
        }

        @Override // net.time4j.engine.ChronoElement
        public String getDefaultMinimum() {
            if (this.fixed) {
                return "am";
            }
            return (String) this.dayPeriod.codeMap.get((PlainTime) this.dayPeriod.codeMap.firstKey());
        }

        @Override // net.time4j.engine.ChronoElement
        public String getDefaultMaximum() {
            if (this.fixed) {
                return "pm";
            }
            return (String) this.dayPeriod.codeMap.get((PlainTime) this.dayPeriod.codeMap.lastKey());
        }

        @Override // net.time4j.engine.BasicElement
        public String toString() {
            StringBuilder sb = new StringBuilder(32);
            sb.append(name());
            sb.append('@');
            sb.append(this.dayPeriod);
            return sb.toString();
        }

        @Override // net.time4j.engine.BasicElement
        protected <T extends ChronoEntity<T>> ElementRule<T, String> derive(Chronology<T> chronology) {
            if (chronology.isRegistered(PlainTime.COMPONENT)) {
                return this;
            }
            return null;
        }

        @Override // net.time4j.engine.BasicElement
        protected boolean doEquals(BasicElement<?> basicElement) {
            return this.dayPeriod.equals(((Element) basicElement).dayPeriod);
        }

        @Override // net.time4j.engine.ElementRule
        public String getValue(ChronoEntity<?> chronoEntity) {
            PlainTime plainTime = (PlainTime) chronoEntity.get(PlainTime.COMPONENT);
            if (this.fixed) {
                return DayPeriod.getFixedCode(plainTime);
            }
            if (this.dayPeriod.isPredefined()) {
                Map loadTextForms = DayPeriod.loadTextForms(getLocale(), getCalendarType());
                String str = null;
                if (plainTime.isMidnight()) {
                    str = "midnight";
                } else if (plainTime.isSimultaneous(PlainTime.of(12))) {
                    str = "noon";
                }
                if (str != null && loadTextForms.containsKey(DayPeriod.createKey(loadTextForms, TextWidth.ABBREVIATED, OutputContext.FORMAT, str))) {
                    return str;
                }
            }
            return (String) this.dayPeriod.codeMap.get(this.dayPeriod.getStart(plainTime));
        }

        @Override // net.time4j.engine.ElementRule
        public String getMinimum(ChronoEntity<?> chronoEntity) {
            return getDefaultMinimum();
        }

        @Override // net.time4j.engine.ElementRule
        public String getMaximum(ChronoEntity<?> chronoEntity) {
            return getDefaultMaximum();
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: withValue, reason: avoid collision after fix types in other method */
        public ChronoEntity<?> withValue2(ChronoEntity<?> chronoEntity, String str, boolean z) {
            throw new IllegalArgumentException("Day period element cannot be set.");
        }

        boolean isFixed() {
            return this.fixed;
        }

        Locale getLocale() {
            return this.dayPeriod.locale;
        }

        String getCalendarType() {
            return this.dayPeriod.calendarType;
        }

        Object getCodeMap() {
            return this.dayPeriod.codeMap;
        }

        private Object writeReplace() {
            return new SPX(this, 7);
        }

        private void readObject(ObjectInputStream objectInputStream) throws IOException {
            throw new InvalidObjectException("Serialization proxy required.");
        }

        @Override // net.time4j.format.TextElement
        public void print(ChronoDisplay chronoDisplay, Appendable appendable, AttributeQuery attributeQuery) throws IOException, ChronoException {
            String apply;
            TextWidth textWidth = (TextWidth) attributeQuery.get(Attributes.TEXT_WIDTH, TextWidth.WIDE);
            OutputContext outputContext = (OutputContext) attributeQuery.get(Attributes.OUTPUT_CONTEXT, OutputContext.FORMAT);
            if (this.fixed) {
                apply = this.dayPeriod.fixed(textWidth, outputContext).apply(chronoDisplay);
            } else {
                apply = this.dayPeriod.approximate(textWidth, outputContext).apply(chronoDisplay);
            }
            appendable.append(apply);
        }

        @Override // net.time4j.format.TextElement
        public String parse(CharSequence charSequence, ParsePosition parsePosition, AttributeQuery attributeQuery) {
            int index = parsePosition.getIndex();
            OutputContext outputContext = (OutputContext) attributeQuery.get(Attributes.OUTPUT_CONTEXT, OutputContext.FORMAT);
            String parse = parse(charSequence, parsePosition, attributeQuery, outputContext);
            if (parse != null || !((Boolean) attributeQuery.get(Attributes.PARSE_MULTIPLE_CONTEXT, Boolean.TRUE)).booleanValue()) {
                return parse;
            }
            parsePosition.setErrorIndex(-1);
            parsePosition.setIndex(index);
            return parse(charSequence, parsePosition, attributeQuery, outputContext == OutputContext.FORMAT ? OutputContext.STANDALONE : OutputContext.FORMAT);
        }

        private String parse(CharSequence charSequence, ParsePosition parsePosition, AttributeQuery attributeQuery, OutputContext outputContext) {
            String str;
            Map map;
            String str2;
            String str3;
            String str4;
            boolean z;
            String createKey;
            OutputContext outputContext2 = outputContext;
            ArrayList arrayList = new ArrayList();
            String str5 = "pm";
            String str6 = "am";
            if (this.fixed) {
                arrayList.add("am");
                arrayList.add("pm");
                arrayList.add("midnight");
                arrayList.add("noon");
            } else {
                arrayList.addAll(new LinkedHashSet(this.dayPeriod.codeMap.values()));
                if (this.dayPeriod.isPredefined()) {
                    arrayList.add("midnight");
                    arrayList.add("noon");
                }
            }
            Map loadTextForms = this.dayPeriod.isPredefined() ? DayPeriod.loadTextForms(getLocale(), getCalendarType()) : null;
            TextWidth textWidth = (TextWidth) attributeQuery.get(Attributes.TEXT_WIDTH, TextWidth.WIDE);
            boolean booleanValue = ((Boolean) attributeQuery.get(Attributes.PARSE_CASE_INSENSITIVE, Boolean.TRUE)).booleanValue();
            boolean booleanValue2 = ((Boolean) attributeQuery.get(Attributes.PARSE_PARTIAL_COMPARE, Boolean.FALSE)).booleanValue();
            int index = parsePosition.getIndex();
            int length = charSequence.length();
            Iterator it = arrayList.iterator();
            String str7 = null;
            int i = 0;
            while (it.hasNext()) {
                Iterator it2 = it;
                String str8 = (String) it.next();
                if (this.dayPeriod.isPredefined()) {
                    if (this.fixed) {
                        createKey = DayPeriod.createKey(loadTextForms, textWidth, outputContext2, str8);
                        if (!loadTextForms.containsKey(createKey)) {
                            if (str8.equals("midnight")) {
                                createKey = DayPeriod.createKey(loadTextForms, textWidth, outputContext2, str6);
                            } else if (str8.equals("noon")) {
                                createKey = DayPeriod.createKey(loadTextForms, textWidth, outputContext2, str5);
                            }
                        }
                    } else {
                        createKey = DayPeriod.createKey(loadTextForms, textWidth, outputContext2, str8);
                    }
                    str = loadTextForms.containsKey(createKey) ? (String) loadTextForms.get(createKey) : null;
                } else {
                    str = str8;
                }
                if (str != null) {
                    int length2 = str.length();
                    map = loadTextForms;
                    int i2 = index;
                    int i3 = 0;
                    boolean z2 = true;
                    while (z2 && i3 < length2) {
                        String str9 = str5;
                        int i4 = index + i3;
                        if (i4 >= length) {
                            str4 = str6;
                            z2 = false;
                        } else {
                            str4 = str6;
                            char charAt = charSequence.charAt(i4);
                            char charAt2 = str.charAt(i3);
                            if (booleanValue) {
                                z = compareIgnoreCase(charAt, charAt2);
                            } else {
                                z = charAt == charAt2;
                            }
                            if (z) {
                                i2++;
                            }
                            z2 = z;
                        }
                        i3++;
                        str5 = str9;
                        str6 = str4;
                    }
                    str2 = str5;
                    str3 = str6;
                    if (booleanValue2 || length2 == 1) {
                        length2 = i2 - index;
                        if (i >= length2) {
                            if (str7 != null && i == length2) {
                                if (!this.fixed) {
                                    str7 = str7 + "|" + str8;
                                }
                                str7 = null;
                            }
                        }
                        i = length2;
                        str7 = str8;
                    } else if (z2) {
                        if (str7 != null) {
                            if (this.fixed) {
                                i = length2;
                                str7 = null;
                            } else {
                                str7 = str7 + "|" + str8;
                                i = length2;
                            }
                        }
                        i = length2;
                        str7 = str8;
                    }
                } else {
                    map = loadTextForms;
                    str2 = str5;
                    str3 = str6;
                }
                outputContext2 = outputContext;
                loadTextForms = map;
                it = it2;
                str5 = str2;
                str6 = str3;
            }
            if (str7 == null) {
                parsePosition.setErrorIndex(index);
            } else {
                parsePosition.setIndex(index + i);
            }
            return str7;
        }

        private boolean compareIgnoreCase(char c, char c2) {
            if (c >= 'a' && c <= 'z') {
                c = (char) ((c - 'a') + 65);
            }
            if (c2 >= 'a' && c2 <= 'z') {
                c2 = (char) ((c2 - 'a') + 65);
            }
            if (c >= 'A' && c <= 'Z') {
                return c == c2;
            }
            Locale locale = getLocale();
            return String.valueOf(c).toUpperCase(locale).equals(String.valueOf(c2).toUpperCase(locale));
        }
    }

    private class PeriodName implements ChronoFunction<ChronoDisplay, String> {
        private final boolean fixed;
        private final OutputContext outputContext;
        private final TextWidth width;

        PeriodName(boolean z, TextWidth textWidth, OutputContext outputContext) {
            if (textWidth == null) {
                throw new NullPointerException("Missing text width.");
            }
            if (outputContext == null) {
                throw new NullPointerException("Missing output context.");
            }
            this.fixed = z;
            this.width = textWidth;
            this.outputContext = outputContext;
        }

        @Override // net.time4j.engine.ChronoFunction
        public String apply(ChronoDisplay chronoDisplay) {
            PlainTime plainTime = (PlainTime) chronoDisplay.get(PlainTime.COMPONENT);
            DayPeriod dayPeriod = DayPeriod.this;
            Locale locale = dayPeriod.locale;
            if (this.fixed) {
                String fixedCode = DayPeriod.getFixedCode(plainTime);
                if (!dayPeriod.isPredefined()) {
                    return fixedCode;
                }
                Map loadTextForms = DayPeriod.loadTextForms(locale, dayPeriod.calendarType);
                String createKey = DayPeriod.createKey(loadTextForms, this.width, this.outputContext, fixedCode);
                if (!loadTextForms.containsKey(createKey)) {
                    if (fixedCode.equals("midnight")) {
                        createKey = DayPeriod.createKey(loadTextForms, this.width, this.outputContext, "am");
                    } else if (fixedCode.equals("noon")) {
                        createKey = DayPeriod.createKey(loadTextForms, this.width, this.outputContext, "pm");
                    }
                }
                if (loadTextForms.containsKey(createKey)) {
                    return (String) loadTextForms.get(createKey);
                }
            } else if (dayPeriod.isPredefined()) {
                Map loadTextForms2 = DayPeriod.loadTextForms(locale, dayPeriod.calendarType);
                if (plainTime.isMidnight()) {
                    String createKey2 = DayPeriod.createKey(loadTextForms2, this.width, this.outputContext, "midnight");
                    if (loadTextForms2.containsKey(createKey2)) {
                        return (String) loadTextForms2.get(createKey2);
                    }
                } else if (plainTime.isSimultaneous(PlainTime.of(12))) {
                    String createKey3 = DayPeriod.createKey(loadTextForms2, this.width, this.outputContext, "noon");
                    if (loadTextForms2.containsKey(createKey3)) {
                        return (String) loadTextForms2.get(createKey3);
                    }
                }
                String createKey4 = DayPeriod.createKey(loadTextForms2, this.width, this.outputContext, (String) dayPeriod.codeMap.get(dayPeriod.getStart(plainTime)));
                if (loadTextForms2.containsKey(createKey4)) {
                    return (String) loadTextForms2.get(createKey4);
                }
            } else {
                return (String) dayPeriod.codeMap.get(dayPeriod.getStart(plainTime));
            }
            Meridiem meridiem = (Meridiem) plainTime.get(PlainTime.AM_PM_OF_DAY);
            if (locale == null) {
                locale = Locale.ROOT;
            }
            return meridiem.getDisplayName(locale);
        }
    }
}

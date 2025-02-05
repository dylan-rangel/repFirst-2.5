package net.time4j.history;

import io.sentry.protocol.ViewHierarchyNode;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import net.time4j.CalendarUnit;
import net.time4j.PlainDate;
import net.time4j.engine.AttributeKey;
import net.time4j.engine.ChronoElement;
import net.time4j.engine.EpochDays;
import net.time4j.engine.FormattableElement;
import net.time4j.engine.VariantSource;
import net.time4j.format.Attributes;
import net.time4j.format.TextElement;
import net.time4j.format.expert.Iso8601Format;
import net.time4j.history.internal.HistoricVariant;
import okhttp3.HttpUrl;

/* loaded from: classes3.dex */
public final class ChronoHistory implements VariantSource, Serializable {
    static final int BYZANTINE_YMAX = 999984973;
    private static final long EARLIEST_CUTOVER;
    private static final ChronoHistory INTRODUCTION_BY_POPE_GREGOR;
    static final int JULIAN_YMAX = 999979465;
    private static final Map<String, ChronoHistory> LOOKUP;
    public static final ChronoHistory PROLEPTIC_BYZANTINE;
    public static final ChronoHistory PROLEPTIC_JULIAN;
    private static final ChronoHistory SWEDEN;
    private static final long serialVersionUID = 4100690610730913643L;
    private final transient AncientJulianLeapYears ajly;
    private final transient ChronoElement<Integer> centuryElement;
    private final transient ChronoElement<HistoricDate> dateElement;
    private final transient TextElement<Integer> dayOfMonthElement;
    private final transient TextElement<Integer> dayOfYearElement;
    private final transient Set<ChronoElement<?>> elements;
    private final transient ChronoElement<HistoricEra> eraElement;
    private final transient EraPreference eraPreference;
    private final transient List<CutOverEvent> events;
    private final transient TextElement<Integer> monthElement;
    private final transient NewYearStrategy nys;
    private final transient HistoricVariant variant;
    private final transient ChronoElement<Integer> yearAfterElement;
    private final transient ChronoElement<Integer> yearBeforeElement;
    private final transient TextElement<Integer> yearOfEraElement;
    public static final AttributeKey<YearDefinition> YEAR_DEFINITION = Attributes.createKey("YEAR_DEFINITION", YearDefinition.class);
    public static final ChronoHistory PROLEPTIC_GREGORIAN = new ChronoHistory(HistoricVariant.PROLEPTIC_GREGORIAN, Collections.singletonList(new CutOverEvent(Long.MIN_VALUE, CalendarAlgorithm.GREGORIAN, CalendarAlgorithm.GREGORIAN)));

    static {
        ChronoHistory chronoHistory = new ChronoHistory(HistoricVariant.PROLEPTIC_JULIAN, Collections.singletonList(new CutOverEvent(Long.MIN_VALUE, CalendarAlgorithm.JULIAN, CalendarAlgorithm.JULIAN)));
        PROLEPTIC_JULIAN = chronoHistory;
        PROLEPTIC_BYZANTINE = new ChronoHistory(HistoricVariant.PROLEPTIC_BYZANTINE, Collections.singletonList(new CutOverEvent(Long.MIN_VALUE, CalendarAlgorithm.JULIAN, CalendarAlgorithm.JULIAN)), null, new NewYearStrategy(NewYearRule.BEGIN_OF_SEPTEMBER, Integer.MAX_VALUE), EraPreference.byzantineUntil(PlainDate.axis().getMaximum()));
        long longValue = ((Long) PlainDate.of(1582, 10, 15).get(EpochDays.MODIFIED_JULIAN_DATE)).longValue();
        EARLIEST_CUTOVER = longValue;
        INTRODUCTION_BY_POPE_GREGOR = ofGregorianReform(longValue);
        ArrayList arrayList = new ArrayList();
        arrayList.add(new CutOverEvent(-57959L, CalendarAlgorithm.JULIAN, CalendarAlgorithm.SWEDISH));
        arrayList.add(new CutOverEvent(-53575L, CalendarAlgorithm.SWEDISH, CalendarAlgorithm.JULIAN));
        arrayList.add(new CutOverEvent(-38611L, CalendarAlgorithm.JULIAN, CalendarAlgorithm.GREGORIAN));
        ChronoHistory chronoHistory2 = new ChronoHistory(HistoricVariant.SWEDEN, Collections.unmodifiableList(arrayList));
        SWEDEN = chronoHistory2;
        HashMap hashMap = new HashMap();
        PlainDate convert = chronoHistory.convert(HistoricDate.of(HistoricEra.AD, 988, 3, 1));
        PlainDate convert2 = chronoHistory.convert(HistoricDate.of(HistoricEra.AD, 1382, 12, 24));
        PlainDate convert3 = chronoHistory.convert(HistoricDate.of(HistoricEra.AD, 1421, 12, 24));
        PlainDate convert4 = chronoHistory.convert(HistoricDate.of(HistoricEra.AD, 1699, 12, 31));
        hashMap.put("ES", ofFirstGregorianReform().with(NewYearRule.BEGIN_OF_JANUARY.until(1383).and(NewYearRule.CHRISTMAS_STYLE.until(1556))).with(EraPreference.hispanicUntil(convert2)));
        hashMap.put("PT", ofFirstGregorianReform().with(NewYearRule.BEGIN_OF_JANUARY.until(1422).and(NewYearRule.CHRISTMAS_STYLE.until(1556))).with(EraPreference.hispanicUntil(convert3)));
        hashMap.put("FR", ofGregorianReform(PlainDate.of(1582, 12, 20)).with(NewYearRule.EASTER_STYLE.until(1567)));
        hashMap.put("DE", ofFirstGregorianReform().with(NewYearRule.CHRISTMAS_STYLE.until(1544)));
        hashMap.put("DE-BAYERN", ofGregorianReform(PlainDate.of(1583, 10, 16)).with(NewYearRule.CHRISTMAS_STYLE.until(1544)));
        hashMap.put("DE-PREUSSEN", ofGregorianReform(PlainDate.of(1610, 9, 2)).with(NewYearRule.CHRISTMAS_STYLE.until(1559)));
        hashMap.put("DE-PROTESTANT", ofGregorianReform(PlainDate.of(1700, 3, 1)).with(NewYearRule.CHRISTMAS_STYLE.until(1559)));
        hashMap.put("NL", ofGregorianReform(PlainDate.of(1583, 1, 1)));
        hashMap.put("AT", ofGregorianReform(PlainDate.of(1584, 1, 17)));
        hashMap.put("CH", ofGregorianReform(PlainDate.of(1584, 1, 22)));
        hashMap.put("HU", ofGregorianReform(PlainDate.of(1587, 11, 1)));
        hashMap.put("DK", ofGregorianReform(PlainDate.of(1700, 3, 1)).with(NewYearRule.MARIA_ANUNCIATA.until(1623)));
        hashMap.put("NO", ofGregorianReform(PlainDate.of(1700, 3, 1)).with(NewYearRule.MARIA_ANUNCIATA.until(1623)));
        hashMap.put("IT", ofFirstGregorianReform().with(NewYearRule.CHRISTMAS_STYLE.until(1583)));
        hashMap.put("IT-FLORENCE", ofFirstGregorianReform().with(NewYearRule.MARIA_ANUNCIATA.until(1749)));
        hashMap.put("IT-PISA", ofFirstGregorianReform().with(NewYearRule.CALCULUS_PISANUS.until(1749)));
        hashMap.put("IT-VENICE", ofFirstGregorianReform().with(NewYearRule.BEGIN_OF_MARCH.until(1798)));
        hashMap.put("GB", ofGregorianReform(PlainDate.of(1752, 9, 14)).with(NewYearRule.CHRISTMAS_STYLE.until(1087).and(NewYearRule.BEGIN_OF_JANUARY.until(1155)).and(NewYearRule.MARIA_ANUNCIATA.until(1752))));
        hashMap.put("GB-SCT", ofGregorianReform(PlainDate.of(1752, 9, 14)).with(NewYearRule.CHRISTMAS_STYLE.until(1087).and(NewYearRule.BEGIN_OF_JANUARY.until(1155)).and(NewYearRule.MARIA_ANUNCIATA.until(1600))));
        hashMap.put("RU", ofGregorianReform(PlainDate.of(1918, 2, 14)).with(NewYearRule.BEGIN_OF_JANUARY.until(988).and(NewYearRule.BEGIN_OF_MARCH.until(1493)).and(NewYearRule.BEGIN_OF_SEPTEMBER.until(1700))).with(EraPreference.byzantineBetween(convert, convert4)));
        hashMap.put("SE", chronoHistory2);
        LOOKUP = Collections.unmodifiableMap(hashMap);
    }

    private ChronoHistory(HistoricVariant historicVariant, List<CutOverEvent> list) {
        this(historicVariant, list, null, null, EraPreference.DEFAULT);
    }

    private ChronoHistory(HistoricVariant historicVariant, List<CutOverEvent> list, AncientJulianLeapYears ancientJulianLeapYears, NewYearStrategy newYearStrategy, EraPreference eraPreference) {
        if (list.isEmpty()) {
            throw new IllegalArgumentException("At least one cutover event must be present in chronological history.");
        }
        if (historicVariant == null) {
            throw new NullPointerException("Missing historic variant.");
        }
        if (eraPreference == null) {
            throw new NullPointerException("Missing era preference.");
        }
        this.variant = historicVariant;
        this.events = list;
        this.ajly = ancientJulianLeapYears;
        this.nys = newYearStrategy;
        this.eraPreference = eraPreference;
        HistoricDateElement historicDateElement = new HistoricDateElement(this);
        this.dateElement = historicDateElement;
        HistoricEraElement historicEraElement = new HistoricEraElement(this);
        this.eraElement = historicEraElement;
        HistoricIntegerElement historicIntegerElement = new HistoricIntegerElement('y', 1, 999999999, this, 2);
        this.yearOfEraElement = historicIntegerElement;
        HistoricIntegerElement historicIntegerElement2 = new HistoricIntegerElement((char) 0, 1, 999999999, this, 6);
        this.yearAfterElement = historicIntegerElement2;
        HistoricIntegerElement historicIntegerElement3 = new HistoricIntegerElement((char) 0, 1, 999999999, this, 7);
        this.yearBeforeElement = historicIntegerElement3;
        HistoricIntegerElement historicIntegerElement4 = new HistoricIntegerElement('M', 1, 12, this, 3);
        this.monthElement = historicIntegerElement4;
        HistoricIntegerElement historicIntegerElement5 = new HistoricIntegerElement('d', 1, 31, this, 4);
        this.dayOfMonthElement = historicIntegerElement5;
        HistoricIntegerElement historicIntegerElement6 = new HistoricIntegerElement('D', 1, 365, this, 5);
        this.dayOfYearElement = historicIntegerElement6;
        HistoricIntegerElement historicIntegerElement7 = new HistoricIntegerElement((char) 0, 1, 10000000, this, 8);
        this.centuryElement = historicIntegerElement7;
        HashSet hashSet = new HashSet();
        hashSet.add(historicDateElement);
        hashSet.add(historicEraElement);
        hashSet.add(historicIntegerElement);
        hashSet.add(historicIntegerElement2);
        hashSet.add(historicIntegerElement3);
        hashSet.add(historicIntegerElement4);
        hashSet.add(historicIntegerElement5);
        hashSet.add(historicIntegerElement6);
        hashSet.add(historicIntegerElement7);
        this.elements = Collections.unmodifiableSet(hashSet);
    }

    public static ChronoHistory ofFirstGregorianReform() {
        return INTRODUCTION_BY_POPE_GREGOR;
    }

    public static ChronoHistory ofGregorianReform(PlainDate plainDate) {
        if (plainDate.equals(PlainDate.axis().getMaximum())) {
            return PROLEPTIC_JULIAN;
        }
        if (plainDate.equals(PlainDate.axis().getMinimum())) {
            return PROLEPTIC_GREGORIAN;
        }
        long longValue = ((Long) plainDate.get(EpochDays.MODIFIED_JULIAN_DATE)).longValue();
        check(longValue);
        if (longValue == EARLIEST_CUTOVER) {
            return INTRODUCTION_BY_POPE_GREGOR;
        }
        return ofGregorianReform(longValue);
    }

    public static ChronoHistory of(Locale locale) {
        ChronoHistory chronoHistory;
        String country = locale.getCountry();
        if (locale.getVariant().isEmpty()) {
            chronoHistory = null;
        } else {
            country = country + "-" + locale.getVariant();
            chronoHistory = LOOKUP.get(country);
        }
        if (chronoHistory == null) {
            chronoHistory = LOOKUP.get(country);
        }
        return chronoHistory == null ? ofFirstGregorianReform() : chronoHistory;
    }

    public static ChronoHistory ofSweden() {
        return SWEDEN;
    }

    public boolean isValid(HistoricDate historicDate) {
        Calculus algorithm;
        return (historicDate == null || isOutOfRange(historicDate) || (algorithm = getAlgorithm(historicDate)) == null || !algorithm.isValid(historicDate)) ? false : true;
    }

    public PlainDate convert(HistoricDate historicDate) {
        if (isOutOfRange(historicDate)) {
            throw new IllegalArgumentException("Out of supported range: " + historicDate);
        }
        Calculus algorithm = getAlgorithm(historicDate);
        if (algorithm == null) {
            throw new IllegalArgumentException("Invalid historic date: " + historicDate);
        }
        return PlainDate.of(algorithm.toMJD(historicDate), EpochDays.MODIFIED_JULIAN_DATE);
    }

    public HistoricDate convert(PlainDate plainDate) {
        HistoricDate historicDate;
        long longValue = ((Long) plainDate.get(EpochDays.MODIFIED_JULIAN_DATE)).longValue();
        int size = this.events.size() - 1;
        while (true) {
            if (size < 0) {
                historicDate = null;
                break;
            }
            CutOverEvent cutOverEvent = this.events.get(size);
            if (longValue >= cutOverEvent.start) {
                historicDate = cutOverEvent.algorithm.fromMJD(longValue);
                break;
            }
            size--;
        }
        if (historicDate == null) {
            historicDate = getJulianAlgorithm().fromMJD(longValue);
        }
        HistoricEra preferredEra = this.eraPreference.getPreferredEra(historicDate, plainDate);
        if (preferredEra != historicDate.getEra()) {
            historicDate = HistoricDate.of(preferredEra, preferredEra.yearOfEra(historicDate.getEra(), historicDate.getYearOfEra()), historicDate.getMonth(), historicDate.getDayOfMonth());
        }
        if (!isOutOfRange(historicDate)) {
            return historicDate;
        }
        throw new IllegalArgumentException("Out of supported range: " + historicDate);
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0088  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00cb  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x012a  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x01ba A[ORIG_RETURN, RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static net.time4j.history.ChronoHistory from(java.lang.String r14) {
        /*
            Method dump skipped, instructions count: 500
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: net.time4j.history.ChronoHistory.from(java.lang.String):net.time4j.history.ChronoHistory");
    }

    private static PlainDate getGregorianCutOverDate(String[] strArr, String str) {
        String[] split = strArr[1].split("=");
        if (split.length != 2) {
            throw new IllegalArgumentException("Invalid syntax in variant description: " + str);
        }
        if (split[0].equals("cutover")) {
            try {
                return Iso8601Format.EXTENDED_DATE.parse(split[1]);
            } catch (ParseException unused) {
            }
        }
        throw new IllegalArgumentException("Invalid cutover definition: " + str);
    }

    @Override // net.time4j.engine.VariantSource
    public String getVariant() {
        StringBuilder sb = new StringBuilder(64);
        sb.append("historic-");
        sb.append(this.variant.name());
        int i = AnonymousClass1.$SwitchMap$net$time4j$history$internal$HistoricVariant[this.variant.ordinal()];
        if (i == 1 || i == 2 || i == 3) {
            sb.append(":no-cutover");
        } else {
            if (i == 5 || i == 6) {
                sb.append(":cutover=");
                sb.append(getGregorianCutOverDate());
            }
            sb.append(":ancient-julian-leap-years=");
            AncientJulianLeapYears ancientJulianLeapYears = this.ajly;
            if (ancientJulianLeapYears != null) {
                int[] pattern = ancientJulianLeapYears.getPattern();
                sb.append('[');
                sb.append(pattern[0]);
                for (int i2 = 1; i2 < pattern.length; i2++) {
                    sb.append(',');
                    sb.append(pattern[i2]);
                }
                sb.append(']');
            } else {
                sb.append(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI);
            }
            sb.append(":new-year-strategy=");
            sb.append(getNewYearStrategy());
            sb.append(":era-preference=");
            sb.append(getEraPreference());
        }
        return sb.toString();
    }

    public PlainDate getGregorianCutOverDate() {
        long j = this.events.get(r0.size() - 1).start;
        if (j == Long.MIN_VALUE) {
            throw new UnsupportedOperationException("Proleptic history without any gregorian reform date.");
        }
        return PlainDate.of(j, EpochDays.MODIFIED_JULIAN_DATE);
    }

    public boolean hasGregorianCutOverDate() {
        List<CutOverEvent> list = this.events;
        return list.get(list.size() - 1).start > Long.MIN_VALUE;
    }

    public HistoricDate getBeginOfYear(HistoricEra historicEra, int i) {
        HistoricDate newYear = getNewYearStrategy().newYear(historicEra, i);
        if (isValid(newYear)) {
            HistoricEra preferredEra = this.eraPreference.getPreferredEra(newYear, convert(newYear));
            return preferredEra != historicEra ? HistoricDate.of(preferredEra, preferredEra.yearOfEra(newYear.getEra(), newYear.getYearOfEra()), newYear.getMonth(), newYear.getDayOfMonth()) : newYear;
        }
        throw new IllegalArgumentException("Cannot determine valid New Year: " + historicEra + "-" + i);
    }

    public int getLengthOfYear(HistoricEra historicEra, int i) {
        HistoricDate newYear;
        HistoricDate historicDate;
        try {
            NewYearStrategy newYearStrategy = this.nys;
            int i2 = 1;
            if (newYearStrategy == null) {
                newYear = HistoricDate.of(historicEra, i, 1, 1);
                historicDate = HistoricDate.of(historicEra, i, 12, 31);
            } else {
                newYear = newYearStrategy.newYear(historicEra, i);
                if (historicEra != HistoricEra.BC) {
                    HistoricDate newYear2 = this.nys.newYear(historicEra, i + 1);
                    if (historicEra == HistoricEra.BYZANTINE) {
                        historicDate = this.nys.newYear(HistoricEra.AD, historicEra.annoDomini(i));
                        if (historicDate.compareTo(newYear) > 0) {
                        }
                    }
                    historicDate = newYear2;
                } else if (i == 1) {
                    historicDate = this.nys.newYear(HistoricEra.AD, 1);
                } else {
                    historicDate = this.nys.newYear(historicEra, i - 1);
                }
                i2 = 0;
            }
            return (int) (CalendarUnit.DAYS.between(convert(newYear), convert(historicDate)) + i2);
        } catch (RuntimeException unused) {
            return -1;
        }
    }

    public AncientJulianLeapYears getAncientJulianLeapYears() {
        AncientJulianLeapYears ancientJulianLeapYears = this.ajly;
        if (ancientJulianLeapYears != null) {
            return ancientJulianLeapYears;
        }
        throw new UnsupportedOperationException("No historic julian leap years were defined.");
    }

    public boolean hasAncientJulianLeapYears() {
        return this.ajly != null;
    }

    public ChronoHistory with(AncientJulianLeapYears ancientJulianLeapYears) {
        if (ancientJulianLeapYears != null) {
            return !hasGregorianCutOverDate() ? this : new ChronoHistory(this.variant, this.events, ancientJulianLeapYears, this.nys, this.eraPreference);
        }
        throw new NullPointerException("Missing ancient julian leap years.");
    }

    public NewYearStrategy getNewYearStrategy() {
        NewYearStrategy newYearStrategy = this.nys;
        return newYearStrategy == null ? NewYearStrategy.DEFAULT : newYearStrategy;
    }

    public ChronoHistory with(NewYearStrategy newYearStrategy) {
        return newYearStrategy.equals(NewYearStrategy.DEFAULT) ? this.nys == null ? this : new ChronoHistory(this.variant, this.events, this.ajly, null, this.eraPreference) : !hasGregorianCutOverDate() ? this : new ChronoHistory(this.variant, this.events, this.ajly, newYearStrategy, this.eraPreference);
    }

    public ChronoHistory with(EraPreference eraPreference) {
        return (eraPreference.equals(this.eraPreference) || !hasGregorianCutOverDate()) ? this : new ChronoHistory(this.variant, this.events, this.ajly, this.nys, eraPreference);
    }

    public ChronoElement<HistoricDate> date() {
        return this.dateElement;
    }

    @FormattableElement(format = "G")
    public ChronoElement<HistoricEra> era() {
        return this.eraElement;
    }

    @FormattableElement(format = ViewHierarchyNode.JsonKeys.Y)
    public TextElement<Integer> yearOfEra() {
        return this.yearOfEraElement;
    }

    /* renamed from: net.time4j.history.ChronoHistory$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$time4j$history$HistoricEra;
        static final /* synthetic */ int[] $SwitchMap$net$time4j$history$YearDefinition;
        static final /* synthetic */ int[] $SwitchMap$net$time4j$history$internal$HistoricVariant;

        static {
            int[] iArr = new int[YearDefinition.values().length];
            $SwitchMap$net$time4j$history$YearDefinition = iArr;
            try {
                iArr[YearDefinition.DUAL_DATING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$net$time4j$history$YearDefinition[YearDefinition.AFTER_NEW_YEAR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$net$time4j$history$YearDefinition[YearDefinition.BEFORE_NEW_YEAR.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            int[] iArr2 = new int[HistoricEra.values().length];
            $SwitchMap$net$time4j$history$HistoricEra = iArr2;
            try {
                iArr2[HistoricEra.HISPANIC.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$net$time4j$history$HistoricEra[HistoricEra.BYZANTINE.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$net$time4j$history$HistoricEra[HistoricEra.AB_URBE_CONDITA.ordinal()] = 3;
            } catch (NoSuchFieldError unused6) {
            }
            int[] iArr3 = new int[HistoricVariant.values().length];
            $SwitchMap$net$time4j$history$internal$HistoricVariant = iArr3;
            try {
                iArr3[HistoricVariant.PROLEPTIC_GREGORIAN.ordinal()] = 1;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$net$time4j$history$internal$HistoricVariant[HistoricVariant.PROLEPTIC_JULIAN.ordinal()] = 2;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$net$time4j$history$internal$HistoricVariant[HistoricVariant.PROLEPTIC_BYZANTINE.ordinal()] = 3;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$net$time4j$history$internal$HistoricVariant[HistoricVariant.SWEDEN.ordinal()] = 4;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$net$time4j$history$internal$HistoricVariant[HistoricVariant.INTRODUCTION_ON_1582_10_15.ordinal()] = 5;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$net$time4j$history$internal$HistoricVariant[HistoricVariant.SINGLE_CUTOVER_DATE.ordinal()] = 6;
            } catch (NoSuchFieldError unused12) {
            }
        }
    }

    public ChronoElement<Integer> yearOfEra(YearDefinition yearDefinition) {
        int i = AnonymousClass1.$SwitchMap$net$time4j$history$YearDefinition[yearDefinition.ordinal()];
        if (i == 1) {
            return this.yearOfEraElement;
        }
        if (i == 2) {
            return this.yearAfterElement;
        }
        if (i == 3) {
            return this.yearBeforeElement;
        }
        throw new UnsupportedOperationException(yearDefinition.name());
    }

    public ChronoElement<Integer> centuryOfEra() {
        return this.centuryElement;
    }

    @FormattableElement(alt = "L", format = "M")
    public TextElement<Integer> month() {
        return this.monthElement;
    }

    @FormattableElement(format = "d")
    public ChronoElement<Integer> dayOfMonth() {
        return this.dayOfMonthElement;
    }

    @FormattableElement(format = "D")
    public ChronoElement<Integer> dayOfYear() {
        return this.dayOfYearElement;
    }

    public Set<ChronoElement<?>> getElements() {
        return this.elements;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ChronoHistory) {
            ChronoHistory chronoHistory = (ChronoHistory) obj;
            if (this.variant == chronoHistory.variant && isEqual(this.ajly, chronoHistory.ajly) && isEqual(this.nys, chronoHistory.nys) && this.eraPreference.equals(chronoHistory.eraPreference)) {
                return this.variant != HistoricVariant.SINGLE_CUTOVER_DATE || this.events.get(0).start == chronoHistory.events.get(0).start;
            }
        }
        return false;
    }

    public int hashCode() {
        if (this.variant == HistoricVariant.SINGLE_CUTOVER_DATE) {
            long j = this.events.get(0).start;
            return (int) (j ^ (j << 32));
        }
        return this.variant.hashCode();
    }

    public String toString() {
        return "ChronoHistory[" + getVariant() + "]";
    }

    Calculus getAlgorithm(HistoricDate historicDate) {
        for (int size = this.events.size() - 1; size >= 0; size--) {
            CutOverEvent cutOverEvent = this.events.get(size);
            if (historicDate.compareTo(cutOverEvent.dateAtCutOver) >= 0) {
                return cutOverEvent.algorithm;
            }
            if (historicDate.compareTo(cutOverEvent.dateBeforeCutOver) > 0) {
                return null;
            }
        }
        return getJulianAlgorithm();
    }

    HistoricDate adjustDayOfMonth(HistoricDate historicDate) {
        int maximumDayOfMonth;
        Calculus algorithm = getAlgorithm(historicDate);
        return (algorithm != null && (maximumDayOfMonth = algorithm.getMaximumDayOfMonth(historicDate)) < historicDate.getDayOfMonth()) ? HistoricDate.of(historicDate.getEra(), historicDate.getYearOfEra(), historicDate.getMonth(), maximumDayOfMonth) : historicDate;
    }

    List<CutOverEvent> getEvents() {
        return this.events;
    }

    HistoricVariant getHistoricVariant() {
        return this.variant;
    }

    EraPreference getEraPreference() {
        return this.eraPreference;
    }

    private boolean isOutOfRange(HistoricDate historicDate) {
        int annoDomini = historicDate.getEra().annoDomini(historicDate.getYearOfEra());
        return this == PROLEPTIC_BYZANTINE ? annoDomini < -5508 || (annoDomini == -5508 && historicDate.getMonth() < 9) || annoDomini > JULIAN_YMAX : this == PROLEPTIC_JULIAN ? Math.abs(annoDomini) > JULIAN_YMAX : this == PROLEPTIC_GREGORIAN ? Math.abs(annoDomini) > 999999999 : annoDomini < -44 || annoDomini > 9999;
    }

    private Calculus getJulianAlgorithm() {
        AncientJulianLeapYears ancientJulianLeapYears = this.ajly;
        if (ancientJulianLeapYears != null) {
            return ancientJulianLeapYears.getCalculus();
        }
        return CalendarAlgorithm.JULIAN;
    }

    private static boolean isEqual(Object obj, Object obj2) {
        if (obj == null) {
            return obj2 == null;
        }
        return obj.equals(obj2);
    }

    private static void check(long j) {
        if (j < EARLIEST_CUTOVER) {
            throw new IllegalArgumentException("Gregorian calendar did not exist before 1582-10-15");
        }
    }

    private static ChronoHistory ofGregorianReform(long j) {
        return new ChronoHistory(j == EARLIEST_CUTOVER ? HistoricVariant.INTRODUCTION_ON_1582_10_15 : HistoricVariant.SINGLE_CUTOVER_DATE, Collections.singletonList(new CutOverEvent(j, CalendarAlgorithm.JULIAN, CalendarAlgorithm.GREGORIAN)));
    }

    private Object writeReplace() {
        return new SPX(this, 3);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException {
        throw new InvalidObjectException("Serialization proxy required.");
    }
}

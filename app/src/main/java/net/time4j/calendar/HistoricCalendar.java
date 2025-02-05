package net.time4j.calendar;

import androidx.exifinterface.media.ExifInterface;
import com.google.android.gms.auth.api.credentials.CredentialsApi;
import io.sentry.protocol.ViewHierarchyNode;
import java.io.Externalizable;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.time4j.GeneralTimestamp;
import net.time4j.Moment;
import net.time4j.Month;
import net.time4j.PlainDate;
import net.time4j.PlainTime;
import net.time4j.SystemClock;
import net.time4j.Weekday;
import net.time4j.Weekmodel;
import net.time4j.base.MathUtils;
import net.time4j.base.TimeSource;
import net.time4j.calendar.CommonElements;
import net.time4j.calendar.service.GenericDatePatterns;
import net.time4j.calendar.service.StdEnumDateElement;
import net.time4j.calendar.service.StdIntegerDateElement;
import net.time4j.calendar.service.StdWeekdayElement;
import net.time4j.engine.AttributeQuery;
import net.time4j.engine.BasicElement;
import net.time4j.engine.CalendarDays;
import net.time4j.engine.CalendarEra;
import net.time4j.engine.CalendarFamily;
import net.time4j.engine.CalendarSystem;
import net.time4j.engine.CalendarVariant;
import net.time4j.engine.ChronoDisplay;
import net.time4j.engine.ChronoElement;
import net.time4j.engine.ChronoEntity;
import net.time4j.engine.ChronoException;
import net.time4j.engine.ChronoExtension;
import net.time4j.engine.ChronoFunction;
import net.time4j.engine.ChronoMerger;
import net.time4j.engine.ChronoOperator;
import net.time4j.engine.Chronology;
import net.time4j.engine.DisplayStyle;
import net.time4j.engine.ElementRule;
import net.time4j.engine.EpochDays;
import net.time4j.engine.FormattableElement;
import net.time4j.engine.IntElementRule;
import net.time4j.engine.StartOfDay;
import net.time4j.engine.ValidationElement;
import net.time4j.format.Attributes;
import net.time4j.format.CalendarText;
import net.time4j.format.CalendarType;
import net.time4j.format.DisplayElement;
import net.time4j.format.Leniency;
import net.time4j.format.LocalizedPatternSupport;
import net.time4j.format.NumberSystem;
import net.time4j.format.TextElement;
import net.time4j.format.internal.DualFormatElement;
import net.time4j.history.ChronoHistory;
import net.time4j.history.HistoricDate;
import net.time4j.history.HistoricEra;
import net.time4j.history.YearDefinition;
import net.time4j.history.internal.HistoricAttribute;
import net.time4j.i18n.HistoricExtension;
import net.time4j.tz.TZID;
import net.time4j.tz.Timezone;

@CalendarType("historic")
/* loaded from: classes3.dex */
public final class HistoricCalendar extends CalendarVariant<HistoricCalendar> implements LocalizedPatternSupport {
    private static final Map<String, CalendarSystem<HistoricCalendar>> CALSYS;
    private static final int CENTURY_INDEX = 4;
    public static final ChronoElement<Integer> CENTURY_OF_ERA;
    private static final ChronoElement<Integer> CONTINUOUS_DOM;
    private static final int CONTINUOUS_DOM_INDEX = 5;

    @FormattableElement(format = "d")
    public static final StdCalendarElement<Integer, HistoricCalendar> DAY_OF_MONTH;
    private static final int DAY_OF_MONTH_INDEX = 2;

    @FormattableElement(format = ExifInterface.LONGITUDE_EAST)
    public static final StdCalendarElement<Weekday, HistoricCalendar> DAY_OF_WEEK;

    @FormattableElement(format = "D")
    public static final StdCalendarElement<Integer, HistoricCalendar> DAY_OF_YEAR;
    private static final int DAY_OF_YEAR_INDEX = 3;
    private static final CalendarFamily<HistoricCalendar> ENGINE;

    @FormattableElement(format = "G")
    public static final ChronoElement<HistoricEra> ERA;

    @FormattableElement(alt = "L", format = "M")
    public static final StdCalendarElement<Month, HistoricCalendar> MONTH_OF_YEAR;

    @FormattableElement(format = ViewHierarchyNode.JsonKeys.Y)
    public static final TextElement<Integer> RELATED_STANDARD_YEAR;

    @FormattableElement(format = "F")
    public static final OrdinalWeekdayElement<HistoricCalendar> WEEKDAY_IN_MONTH;
    private static final WeekdayInMonthElement<HistoricCalendar> WIM_ELEMENT;
    private static final int YEAR_INDEX = 0;
    private static final long serialVersionUID = 7723641381724201009L;
    private final transient HistoricDate date;
    private final PlainDate gregorian;
    private final ChronoHistory history;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.time4j.engine.ChronoEntity
    public HistoricCalendar getContext() {
        return this;
    }

    static {
        EraElement eraElement = new EraElement();
        ERA = eraElement;
        SimpleElement simpleElement = new SimpleElement("CENTURY_OF_ERA", ChronoHistory.ofFirstGregorianReform().centuryOfEra().getDefaultMinimum().intValue(), ChronoHistory.ofFirstGregorianReform().centuryOfEra().getDefaultMaximum().intValue());
        CENTURY_OF_ERA = simpleElement;
        YearElement yearElement = new YearElement();
        RELATED_STANDARD_YEAR = yearElement;
        StdEnumDateElement<Month, HistoricCalendar> stdEnumDateElement = new StdEnumDateElement<Month, HistoricCalendar>("MONTH_OF_YEAR", HistoricCalendar.class, Month.class, 'M', new MonthOperator(true), new MonthOperator(false)) { // from class: net.time4j.calendar.HistoricCalendar.1
            @Override // net.time4j.calendar.service.StdEnumDateElement
            protected String getCalendarType(AttributeQuery attributeQuery) {
                return CalendarText.ISO_CALENDAR_TYPE;
            }
        };
        MONTH_OF_YEAR = stdEnumDateElement;
        StdIntegerDateElement stdIntegerDateElement = new StdIntegerDateElement("DAY_OF_MONTH", HistoricCalendar.class, 1, 31, 'd');
        DAY_OF_MONTH = stdIntegerDateElement;
        StdIntegerDateElement stdIntegerDateElement2 = new StdIntegerDateElement("DAY_OF_YEAR", HistoricCalendar.class, 1, 365, 'D');
        DAY_OF_YEAR = stdIntegerDateElement2;
        StdWeekdayElement stdWeekdayElement = new StdWeekdayElement(HistoricCalendar.class, getDefaultWeekmodel());
        DAY_OF_WEEK = stdWeekdayElement;
        SimpleElement simpleElement2 = new SimpleElement("HC_CONTINUOUS_DOM", 1, 31);
        CONTINUOUS_DOM = simpleElement2;
        WeekdayInMonthElement<HistoricCalendar> weekdayInMonthElement = new WeekdayInMonthElement<>(HistoricCalendar.class, simpleElement2, stdWeekdayElement);
        WIM_ELEMENT = weekdayInMonthElement;
        WEEKDAY_IN_MONTH = weekdayInMonthElement;
        VariantMap variantMap = new VariantMap();
        ChronoHistory ofFirstGregorianReform = ChronoHistory.ofFirstGregorianReform();
        variantMap.put(ofFirstGregorianReform.getVariant(), new Transformer(ofFirstGregorianReform));
        CALSYS = variantMap;
        ENGINE = CalendarFamily.Builder.setUp(HistoricCalendar.class, new Merger(), variantMap).appendElement((ChronoElement) PlainDate.COMPONENT, (ElementRule) new GregorianDateRule()).appendElement((ChronoElement) eraElement, (ElementRule) new EraRule()).appendElement((ChronoElement) simpleElement, (ElementRule) new IntegerRule(4)).appendElement((ChronoElement) yearElement, (ElementRule) new IntegerRule(0)).appendElement((ChronoElement) stdEnumDateElement, (ElementRule) new MonthRule()).appendElement((ChronoElement) CommonElements.RELATED_GREGORIAN_YEAR, (ElementRule) new RelatedGregorianYearRule(variantMap, stdIntegerDateElement2)).appendElement((ChronoElement) simpleElement2, (ElementRule) new IntegerRule(5)).appendElement((ChronoElement) stdIntegerDateElement, (ElementRule) new IntegerRule(2)).appendElement((ChronoElement) stdIntegerDateElement2, (ElementRule) new IntegerRule(3)).appendElement((ChronoElement) stdWeekdayElement, (ElementRule) new WeekdayRule(getDefaultWeekmodel(), new ChronoFunction<HistoricCalendar, CalendarSystem<HistoricCalendar>>() { // from class: net.time4j.calendar.HistoricCalendar.2
            @Override // net.time4j.engine.ChronoFunction
            public CalendarSystem<HistoricCalendar> apply(HistoricCalendar historicCalendar) {
                return historicCalendar.getChronology().getCalendarSystem(historicCalendar.getVariant());
            }
        })).appendElement((ChronoElement) weekdayInMonthElement, WeekdayInMonthElement.getRule(weekdayInMonthElement)).appendExtension((ChronoExtension) new CommonElements.Weekengine(HistoricCalendar.class, stdIntegerDateElement, stdIntegerDateElement2, getDefaultWeekmodel())).build();
    }

    private HistoricCalendar(ChronoHistory chronoHistory, HistoricDate historicDate) {
        this.gregorian = chronoHistory.convert(historicDate);
        this.date = historicDate;
        this.history = chronoHistory;
    }

    private HistoricCalendar(ChronoHistory chronoHistory, PlainDate plainDate) {
        this.date = chronoHistory.convert(plainDate);
        this.gregorian = plainDate;
        this.history = chronoHistory;
    }

    public static HistoricCalendar of(ChronoHistory chronoHistory, HistoricEra historicEra, int i, int i2, int i3) {
        return of(chronoHistory, historicEra, i, YearDefinition.DUAL_DATING, i2, i3);
    }

    public static HistoricCalendar of(ChronoHistory chronoHistory, HistoricEra historicEra, int i, YearDefinition yearDefinition, int i2, int i3) {
        return of(chronoHistory, HistoricDate.of(historicEra, i, i2, i3, yearDefinition, chronoHistory.getNewYearStrategy()));
    }

    public static HistoricCalendar nowInSystemTime(ChronoHistory chronoHistory) {
        return (HistoricCalendar) SystemClock.inLocalView().now(family(), chronoHistory, StartOfDay.MIDNIGHT).toDate();
    }

    public ChronoHistory getHistory() {
        return this.history;
    }

    public HistoricEra getEra() {
        return this.date.getEra();
    }

    public int getCentury() {
        return getInt(this.history.centuryOfEra());
    }

    public int getYear() {
        return this.date.getYearOfEra(this.history.getNewYearStrategy());
    }

    public Month getMonth() {
        return Month.valueOf(this.date.getMonth());
    }

    public int getDayOfMonth() {
        return this.date.getDayOfMonth();
    }

    public Weekday getDayOfWeek() {
        return Weekday.valueOf(MathUtils.floorModulo(this.gregorian.getDaysSinceEpochUTC() + 5, 7) + 1);
    }

    public int getDayOfYear() {
        return ((Integer) get(DAY_OF_YEAR)).intValue();
    }

    @Override // net.time4j.engine.VariantSource
    public String getVariant() {
        return this.history.getVariant();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int lengthOfMonth() {
        try {
            StdCalendarElement<Integer, HistoricCalendar> stdCalendarElement = DAY_OF_MONTH;
            return ((int) CalendarDays.between(((HistoricCalendar) with((ChronoElement<Integer>) stdCalendarElement, ((Integer) getMinimum(stdCalendarElement)).intValue())).gregorian, ((HistoricCalendar) with((ChronoElement<Integer>) stdCalendarElement, ((Integer) getMaximum(stdCalendarElement)).intValue())).gregorian).getAmount()) + 1;
        } catch (RuntimeException unused) {
            return -1;
        }
    }

    public int lengthOfYear() {
        return this.history.getLengthOfYear(getEra(), getYear());
    }

    public HistoricCalendar withNewYear() {
        return of(this.history, this.history.getBeginOfYear(getEra(), getYear()));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public HistoricCalendar nextDay() {
        return (HistoricCalendar) with(DAY_OF_MONTH.incremented());
    }

    public GeneralTimestamp<HistoricCalendar> at(PlainTime plainTime) {
        return GeneralTimestamp.of(this, plainTime);
    }

    public GeneralTimestamp<HistoricCalendar> atTime(int i, int i2) {
        return at(PlainTime.of(i, i2));
    }

    @Override // net.time4j.engine.CalendarVariant
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof HistoricCalendar)) {
            return false;
        }
        HistoricCalendar historicCalendar = (HistoricCalendar) obj;
        return this.gregorian.equals(historicCalendar.gregorian) && this.history.equals(historicCalendar.history) && this.date.equals(historicCalendar.date);
    }

    @Override // net.time4j.engine.CalendarVariant
    public int hashCode() {
        return (this.gregorian.hashCode() * 17) + (this.history.hashCode() * 31);
    }

    @Override // net.time4j.engine.CalendarVariant
    public String toString() {
        StringBuilder sb = new StringBuilder(32);
        sb.append(this.date);
        sb.append('[');
        sb.append(this.history);
        sb.append(']');
        return sb.toString();
    }

    public static Weekmodel getDefaultWeekmodel() {
        return Weekmodel.of(Weekday.SUNDAY, 1);
    }

    public static CalendarFamily<HistoricCalendar> family() {
        return ENGINE;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.time4j.engine.CalendarVariant, net.time4j.engine.ChronoEntity
    public CalendarFamily<HistoricCalendar> getChronology() {
        return ENGINE;
    }

    private static HistoricCalendar of(ChronoHistory chronoHistory, HistoricDate historicDate) {
        if (chronoHistory.isValid(historicDate)) {
            return new HistoricCalendar(chronoHistory, historicDate);
        }
        throw new IllegalArgumentException("Historic date \"" + historicDate + "\" invalid in history: " + chronoHistory);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ChronoHistory getHistory(AttributeQuery attributeQuery) {
        if (attributeQuery.contains(HistoricAttribute.CALENDAR_HISTORY)) {
            return (ChronoHistory) attributeQuery.get(HistoricAttribute.CALENDAR_HISTORY);
        }
        if (((String) attributeQuery.get(Attributes.CALENDAR_TYPE, CalendarText.ISO_CALENDAR_TYPE)).equals("historic") && attributeQuery.contains(Attributes.CALENDAR_VARIANT)) {
            return ChronoHistory.from((String) attributeQuery.get(Attributes.CALENDAR_VARIANT));
        }
        if (((Leniency) attributeQuery.get(Attributes.LENIENCY, Leniency.SMART)).isStrict()) {
            return null;
        }
        return ChronoHistory.of((Locale) attributeQuery.get(Attributes.LANGUAGE, Locale.ROOT));
    }

    private Object writeReplace() {
        return new SPX(this);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException {
        throw new InvalidObjectException("Serialization proxy required.");
    }

    private static class Transformer implements CalendarSystem<HistoricCalendar> {
        private final ChronoHistory history;

        Transformer(ChronoHistory chronoHistory) {
            this.history = chronoHistory;
        }

        @Override // net.time4j.engine.CalendarSystem
        public HistoricCalendar transform(long j) {
            return new HistoricCalendar(this.history, PlainDate.of(j, EpochDays.UTC));
        }

        @Override // net.time4j.engine.CalendarSystem
        public long transform(HistoricCalendar historicCalendar) {
            return historicCalendar.gregorian.getDaysSinceEpochUTC();
        }

        @Override // net.time4j.engine.CalendarSystem
        public long getMinimumSinceUTC() {
            return this.history.convert((HistoricDate) PlainDate.of(CredentialsApi.CREDENTIAL_PICKER_REQUEST_CODE, 1, 1).getMinimum(this.history.date())).getDaysSinceEpochUTC();
        }

        @Override // net.time4j.engine.CalendarSystem
        public long getMaximumSinceUTC() {
            return this.history.convert((HistoricDate) PlainDate.of(CredentialsApi.CREDENTIAL_PICKER_REQUEST_CODE, 1, 1).getMaximum(this.history.date())).getDaysSinceEpochUTC();
        }

        @Override // net.time4j.engine.CalendarSystem
        public List<CalendarEra> getEras() {
            ArrayList arrayList = new ArrayList();
            for (HistoricEra historicEra : HistoricEra.values()) {
                arrayList.add(historicEra);
            }
            return Collections.unmodifiableList(arrayList);
        }
    }

    private static class VariantMap extends ConcurrentHashMap<String, CalendarSystem<HistoricCalendar>> {
        private VariantMap() {
        }

        @Override // java.util.concurrent.ConcurrentHashMap, java.util.AbstractMap, java.util.Map
        public CalendarSystem<HistoricCalendar> get(Object obj) {
            CalendarSystem<HistoricCalendar> calendarSystem = (CalendarSystem) super.get(obj);
            if (calendarSystem != null) {
                return calendarSystem;
            }
            String obj2 = obj.toString();
            try {
                Transformer transformer = new Transformer(ChronoHistory.from(obj2));
                CalendarSystem<HistoricCalendar> putIfAbsent = putIfAbsent(obj2, transformer);
                return putIfAbsent != null ? putIfAbsent : transformer;
            } catch (IllegalArgumentException unused) {
                return null;
            }
        }
    }

    private static class IntegerRule implements IntElementRule<HistoricCalendar> {
        private final int index;

        IntegerRule(int i) {
            this.index = i;
        }

        @Override // net.time4j.engine.ElementRule
        public Integer getValue(HistoricCalendar historicCalendar) {
            return Integer.valueOf(getInt(historicCalendar));
        }

        @Override // net.time4j.engine.ElementRule
        public Integer getMinimum(HistoricCalendar historicCalendar) {
            if (this.index == 5) {
                int dayOfMonth = historicCalendar.date.getDayOfMonth();
                HistoricEra era = historicCalendar.date.getEra();
                int yearOfEra = historicCalendar.date.getYearOfEra();
                int month = historicCalendar.date.getMonth();
                for (int i = 1; i <= dayOfMonth; i++) {
                    if (historicCalendar.history.isValid(HistoricDate.of(era, yearOfEra, month, i))) {
                        return Integer.valueOf(i);
                    }
                }
            }
            return (Integer) historicCalendar.getMinimum(getElement(historicCalendar));
        }

        @Override // net.time4j.engine.ElementRule
        public Integer getMaximum(HistoricCalendar historicCalendar) {
            if (this.index == 5) {
                int intValue = ((Integer) historicCalendar.getMaximum(historicCalendar.history.dayOfMonth())).intValue();
                HistoricEra era = historicCalendar.date.getEra();
                int yearOfEra = historicCalendar.date.getYearOfEra();
                int month = historicCalendar.date.getMonth();
                int i = 0;
                for (int i2 = 1; i2 <= intValue; i2++) {
                    if (!historicCalendar.history.isValid(HistoricDate.of(era, yearOfEra, month, i2))) {
                        i++;
                    }
                }
                return Integer.valueOf(intValue - i);
            }
            return (Integer) historicCalendar.getMaximum(getElement(historicCalendar));
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: isValid, reason: merged with bridge method [inline-methods] */
        public boolean isValid2(HistoricCalendar historicCalendar, Integer num) {
            if (num == null || this.index == 5) {
                return false;
            }
            return historicCalendar.isValid((ChronoElement<ChronoElement<Integer>>) getElement(historicCalendar), (ChronoElement<Integer>) num);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // net.time4j.engine.ElementRule
        /* renamed from: withValue, reason: merged with bridge method [inline-methods] */
        public HistoricCalendar withValue2(HistoricCalendar historicCalendar, Integer num, boolean z) {
            return (HistoricCalendar) historicCalendar.with((ChronoElement<ChronoElement<Integer>>) getElement(historicCalendar), (ChronoElement<Integer>) num);
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtFloor(HistoricCalendar historicCalendar) {
            int i = this.index;
            if (i == 2 || i == 3) {
                return null;
            }
            throw new UnsupportedOperationException("Never called.");
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtCeiling(HistoricCalendar historicCalendar) {
            int i = this.index;
            if (i == 2 || i == 3) {
                return null;
            }
            throw new UnsupportedOperationException("Never called.");
        }

        private ChronoElement<Integer> getElement(HistoricCalendar historicCalendar) {
            int i = this.index;
            if (i == 0) {
                return historicCalendar.history.yearOfEra();
            }
            if (i == 2) {
                return historicCalendar.history.dayOfMonth();
            }
            if (i == 3) {
                return historicCalendar.history.dayOfYear();
            }
            if (i == 4) {
                return historicCalendar.history.centuryOfEra();
            }
            throw new UnsupportedOperationException("Unknown element index: " + this.index);
        }

        @Override // net.time4j.engine.IntElementRule
        public int getInt(HistoricCalendar historicCalendar) {
            int i = this.index;
            if (i == 0) {
                return historicCalendar.date.getYearOfEra();
            }
            if (i == 2) {
                return historicCalendar.date.getDayOfMonth();
            }
            if (i == 5) {
                int dayOfMonth = historicCalendar.date.getDayOfMonth();
                HistoricEra era = historicCalendar.date.getEra();
                int yearOfEra = historicCalendar.date.getYearOfEra();
                int month = historicCalendar.date.getMonth();
                int i2 = 0;
                for (int i3 = 1; i3 < dayOfMonth; i3++) {
                    if (!historicCalendar.history.isValid(HistoricDate.of(era, yearOfEra, month, i3))) {
                        i2++;
                    }
                }
                return dayOfMonth - i2;
            }
            return historicCalendar.getInt(getElement(historicCalendar));
        }

        @Override // net.time4j.engine.IntElementRule
        public boolean isValid(HistoricCalendar historicCalendar, int i) {
            if (this.index == 5) {
                return false;
            }
            return historicCalendar.isValid(getElement(historicCalendar), i);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // net.time4j.engine.IntElementRule
        public HistoricCalendar withValue(HistoricCalendar historicCalendar, int i, boolean z) {
            return (HistoricCalendar) historicCalendar.with(getElement(historicCalendar), i);
        }
    }

    private static class MonthRule implements ElementRule<HistoricCalendar, Month> {
        private MonthRule() {
        }

        @Override // net.time4j.engine.ElementRule
        public Month getValue(HistoricCalendar historicCalendar) {
            return historicCalendar.getMonth();
        }

        @Override // net.time4j.engine.ElementRule
        public Month getMinimum(HistoricCalendar historicCalendar) {
            return Month.valueOf(((Integer) historicCalendar.getMinimum(historicCalendar.history.month())).intValue());
        }

        @Override // net.time4j.engine.ElementRule
        public Month getMaximum(HistoricCalendar historicCalendar) {
            return Month.valueOf(((Integer) historicCalendar.getMaximum(historicCalendar.history.month())).intValue());
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: isValid, reason: avoid collision after fix types in other method */
        public boolean isValid2(HistoricCalendar historicCalendar, Month month) {
            if (month == null) {
                return false;
            }
            return historicCalendar.isValid((ChronoElement<Integer>) historicCalendar.history.month(), month.getValue());
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // net.time4j.engine.ElementRule
        /* renamed from: withValue, reason: avoid collision after fix types in other method */
        public HistoricCalendar withValue2(HistoricCalendar historicCalendar, Month month, boolean z) {
            return (HistoricCalendar) historicCalendar.with((ChronoElement<Integer>) historicCalendar.history.month(), month.getValue());
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtFloor(HistoricCalendar historicCalendar) {
            return HistoricCalendar.DAY_OF_MONTH;
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtCeiling(HistoricCalendar historicCalendar) {
            return HistoricCalendar.DAY_OF_MONTH;
        }
    }

    private static class GregorianDateRule implements ElementRule<HistoricCalendar, PlainDate> {
        private GregorianDateRule() {
        }

        @Override // net.time4j.engine.ElementRule
        public PlainDate getValue(HistoricCalendar historicCalendar) {
            return historicCalendar.gregorian;
        }

        @Override // net.time4j.engine.ElementRule
        public PlainDate getMinimum(HistoricCalendar historicCalendar) {
            return historicCalendar.history.convert((HistoricDate) historicCalendar.gregorian.getMinimum(historicCalendar.history.date()));
        }

        @Override // net.time4j.engine.ElementRule
        public PlainDate getMaximum(HistoricCalendar historicCalendar) {
            return historicCalendar.history.convert((HistoricDate) historicCalendar.gregorian.getMaximum(historicCalendar.history.date()));
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: isValid, reason: avoid collision after fix types in other method */
        public boolean isValid2(HistoricCalendar historicCalendar, PlainDate plainDate) {
            if (plainDate == null) {
                return false;
            }
            try {
                historicCalendar.history.convert(plainDate);
                return true;
            } catch (IllegalArgumentException unused) {
                return false;
            }
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: withValue, reason: avoid collision after fix types in other method */
        public HistoricCalendar withValue2(HistoricCalendar historicCalendar, PlainDate plainDate, boolean z) {
            return new HistoricCalendar(historicCalendar.history, plainDate);
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtFloor(HistoricCalendar historicCalendar) {
            throw new UnsupportedOperationException("Never called.");
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtCeiling(HistoricCalendar historicCalendar) {
            throw new UnsupportedOperationException("Never called.");
        }
    }

    private static class EraRule implements ElementRule<HistoricCalendar, HistoricEra> {
        private EraRule() {
        }

        @Override // net.time4j.engine.ElementRule
        public HistoricEra getValue(HistoricCalendar historicCalendar) {
            return historicCalendar.getEra();
        }

        @Override // net.time4j.engine.ElementRule
        public HistoricEra getMinimum(HistoricCalendar historicCalendar) {
            HistoricEra era = historicCalendar.getEra();
            return era == HistoricEra.AD ? HistoricEra.BC : era;
        }

        @Override // net.time4j.engine.ElementRule
        public HistoricEra getMaximum(HistoricCalendar historicCalendar) {
            HistoricEra era = historicCalendar.getEra();
            return era == HistoricEra.BC ? HistoricEra.AD : era;
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: isValid, reason: avoid collision after fix types in other method */
        public boolean isValid2(HistoricCalendar historicCalendar, HistoricEra historicEra) {
            return historicEra != null && historicCalendar.date.getEra() == historicEra;
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: withValue, reason: avoid collision after fix types in other method */
        public HistoricCalendar withValue2(HistoricCalendar historicCalendar, HistoricEra historicEra, boolean z) {
            if (historicEra == null || historicCalendar.date.getEra() != historicEra) {
                throw new IllegalArgumentException(historicEra.name());
            }
            return historicCalendar;
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtFloor(HistoricCalendar historicCalendar) {
            throw new UnsupportedOperationException("Never called.");
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtCeiling(HistoricCalendar historicCalendar) {
            throw new UnsupportedOperationException("Never called.");
        }
    }

    private static class EraElement extends DisplayElement<HistoricEra> implements TextElement<HistoricEra> {
        private static final long serialVersionUID = -4614710504356171166L;

        @Override // net.time4j.engine.BasicElement, net.time4j.engine.ChronoElement
        public char getSymbol() {
            return 'G';
        }

        @Override // net.time4j.engine.ChronoElement
        public boolean isDateElement() {
            return true;
        }

        @Override // net.time4j.engine.ChronoElement
        public boolean isTimeElement() {
            return false;
        }

        EraElement() {
            super("ERA");
        }

        @Override // net.time4j.engine.ChronoElement
        public Class<HistoricEra> getType() {
            return HistoricEra.class;
        }

        @Override // net.time4j.engine.ChronoElement
        public HistoricEra getDefaultMinimum() {
            return HistoricEra.BC;
        }

        @Override // net.time4j.engine.ChronoElement
        public HistoricEra getDefaultMaximum() {
            return HistoricEra.AD;
        }

        @Override // net.time4j.format.TextElement
        public void print(ChronoDisplay chronoDisplay, Appendable appendable, AttributeQuery attributeQuery) throws IOException, ChronoException {
            if (chronoDisplay instanceof HistoricCalendar) {
                HistoricCalendar historicCalendar = (HistoricCalendar) HistoricCalendar.class.cast(chronoDisplay);
                ((TextElement) TextElement.class.cast(historicCalendar.history.era())).print(historicCalendar, appendable, attributeQuery);
            } else {
                throw new ChronoException("Cannot cast to historic calendar: " + chronoDisplay);
            }
        }

        @Override // net.time4j.format.TextElement
        public HistoricEra parse(CharSequence charSequence, ParsePosition parsePosition, AttributeQuery attributeQuery) {
            ChronoHistory history = HistoricCalendar.getHistory(attributeQuery);
            if (history == null) {
                return null;
            }
            return (HistoricEra) HistoricEra.class.cast(((TextElement) TextElement.class.cast(history.era())).parse(charSequence, parsePosition, attributeQuery));
        }

        private Object readResolve() throws ObjectStreamException {
            return HistoricCalendar.ERA;
        }
    }

    private static class SimpleElement extends DisplayElement<Integer> {
        private static final long serialVersionUID = 3808762239145701486L;
        private final transient Integer max;
        private final transient Integer min;

        @Override // net.time4j.engine.ChronoElement
        public boolean isDateElement() {
            return true;
        }

        @Override // net.time4j.engine.ChronoElement
        public boolean isTimeElement() {
            return false;
        }

        private SimpleElement(String str, int i, int i2) {
            super(str);
            this.min = Integer.valueOf(i);
            this.max = Integer.valueOf(i2);
        }

        @Override // net.time4j.engine.ChronoElement
        public Class<Integer> getType() {
            return Integer.class;
        }

        @Override // net.time4j.engine.ChronoElement
        public Integer getDefaultMinimum() {
            return this.min;
        }

        @Override // net.time4j.engine.ChronoElement
        public Integer getDefaultMaximum() {
            return this.max;
        }

        @Override // net.time4j.engine.BasicElement
        protected boolean doEquals(BasicElement<?> basicElement) {
            SimpleElement simpleElement = (SimpleElement) basicElement;
            return this.min.equals(simpleElement.min) && this.max.equals(simpleElement.max);
        }

        private Object readResolve() throws ObjectStreamException {
            String name = name();
            if (name.equals("HC_CONTINUOUS_DOM")) {
                return HistoricCalendar.CONTINUOUS_DOM;
            }
            if (name.equals("CENTURY_OF_ERA")) {
                return HistoricCalendar.CENTURY_OF_ERA;
            }
            throw new InvalidObjectException("Unknown element: " + name);
        }
    }

    private static class YearElement extends SimpleElement implements DualFormatElement {
        private static final long serialVersionUID = 6400379438892131807L;

        @Override // net.time4j.engine.BasicElement, net.time4j.engine.ChronoElement
        public char getSymbol() {
            return 'y';
        }

        private YearElement() {
            super("YEAR_OF_ERA", 1, 999999999);
        }

        @Override // net.time4j.format.TextElement
        public void print(ChronoDisplay chronoDisplay, Appendable appendable, AttributeQuery attributeQuery) throws IOException, ChronoException {
            if (chronoDisplay instanceof HistoricCalendar) {
                HistoricCalendar historicCalendar = (HistoricCalendar) HistoricCalendar.class.cast(chronoDisplay);
                historicCalendar.history.yearOfEra().print(historicCalendar, appendable, attributeQuery);
            } else {
                throw new ChronoException("Cannot cast to historic calendar: " + chronoDisplay);
            }
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // net.time4j.format.TextElement
        public Integer parse(CharSequence charSequence, ParsePosition parsePosition, AttributeQuery attributeQuery) {
            ChronoHistory history = HistoricCalendar.getHistory(attributeQuery);
            if (history == null) {
                return null;
            }
            return history.yearOfEra().parse(charSequence, parsePosition, attributeQuery);
        }

        @Override // net.time4j.format.internal.DualFormatElement
        public void print(ChronoDisplay chronoDisplay, Appendable appendable, AttributeQuery attributeQuery, NumberSystem numberSystem, char c, int i, int i2) throws IOException, ChronoException {
            if (chronoDisplay instanceof HistoricCalendar) {
                ((DualFormatElement) DualFormatElement.class.cast(((HistoricCalendar) HistoricCalendar.class.cast(chronoDisplay)).history.yearOfEra())).print(chronoDisplay, appendable, attributeQuery, numberSystem, c, i, i2);
                return;
            }
            throw new ChronoException("Cannot cast to historic calendar: " + chronoDisplay);
        }

        @Override // net.time4j.format.internal.DualFormatElement
        public Integer parse(CharSequence charSequence, ParsePosition parsePosition, AttributeQuery attributeQuery, ChronoEntity<?> chronoEntity) {
            ChronoHistory history = HistoricCalendar.getHistory(attributeQuery);
            if (history == null) {
                return null;
            }
            return ((DualFormatElement) DualFormatElement.class.cast(history.yearOfEra())).parse(charSequence, parsePosition, attributeQuery, chronoEntity);
        }

        private Object readResolve() throws ObjectStreamException {
            return HistoricCalendar.RELATED_STANDARD_YEAR;
        }
    }

    private static class MonthOperator implements ChronoOperator<HistoricCalendar> {
        private final boolean backwards;

        MonthOperator(boolean z) {
            this.backwards = z;
        }

        @Override // net.time4j.engine.ChronoOperator
        public HistoricCalendar apply(HistoricCalendar historicCalendar) {
            HistoricEra era = historicCalendar.date.getEra();
            int yearOfEra = historicCalendar.date.getYearOfEra();
            int month = historicCalendar.date.getMonth() + (this.backwards ? -1 : 1);
            int dayOfMonth = historicCalendar.date.getDayOfMonth();
            if (month > 12) {
                if (era == HistoricEra.BC) {
                    yearOfEra--;
                    if (yearOfEra == 0) {
                        era = HistoricEra.AD;
                        yearOfEra = 1;
                    }
                } else {
                    yearOfEra++;
                }
                month = 1;
            } else if (month < 1) {
                if (era == HistoricEra.BC) {
                    yearOfEra++;
                } else {
                    yearOfEra--;
                    if (yearOfEra == 0 && era == HistoricEra.AD) {
                        era = HistoricEra.BC;
                        yearOfEra = 1;
                    }
                }
                month = 12;
            }
            HistoricDate of = HistoricDate.of(era, yearOfEra, month, dayOfMonth);
            int i = dayOfMonth;
            while (i > 1 && !historicCalendar.history.isValid(of)) {
                i--;
                of = HistoricDate.of(era, yearOfEra, month, i);
            }
            if (i == 1) {
                while (dayOfMonth <= 31 && !historicCalendar.history.isValid(of)) {
                    dayOfMonth++;
                    of = HistoricDate.of(era, yearOfEra, month, dayOfMonth);
                }
            }
            return new HistoricCalendar(historicCalendar.history, of);
        }
    }

    private static class Merger implements ChronoMerger<HistoricCalendar> {
        @Override // net.time4j.engine.ChronoMerger
        public ChronoDisplay preformat(HistoricCalendar historicCalendar, AttributeQuery attributeQuery) {
            return historicCalendar;
        }

        @Override // net.time4j.engine.ChronoMerger
        public Chronology<?> preparser() {
            return null;
        }

        private Merger() {
        }

        @Override // net.time4j.engine.ChronoMerger
        public /* bridge */ /* synthetic */ HistoricCalendar createFrom(TimeSource timeSource, AttributeQuery attributeQuery) {
            return createFrom2((TimeSource<?>) timeSource, attributeQuery);
        }

        @Override // net.time4j.engine.ChronoMerger
        public /* bridge */ /* synthetic */ HistoricCalendar createFrom(ChronoEntity chronoEntity, AttributeQuery attributeQuery, boolean z, boolean z2) {
            return createFrom2((ChronoEntity<?>) chronoEntity, attributeQuery, z, z2);
        }

        @Override // net.time4j.engine.ChronoMerger
        public String getFormatPattern(DisplayStyle displayStyle, Locale locale) {
            return GenericDatePatterns.get("generic", displayStyle, locale);
        }

        @Override // net.time4j.engine.ChronoMerger
        public StartOfDay getDefaultStartOfDay() {
            return StartOfDay.MIDNIGHT;
        }

        @Override // net.time4j.engine.ChronoMerger
        public int getDefaultPivotYear() {
            return PlainDate.axis().getDefaultPivotYear();
        }

        /* JADX WARN: Type inference failed for: r5v1, types: [net.time4j.base.UnixTime] */
        @Override // net.time4j.engine.ChronoMerger
        /* renamed from: createFrom, reason: avoid collision after fix types in other method */
        public HistoricCalendar createFrom2(TimeSource<?> timeSource, AttributeQuery attributeQuery) {
            TZID id;
            String str = (String) attributeQuery.get(Attributes.CALENDAR_VARIANT, "");
            if (str.isEmpty()) {
                return null;
            }
            if (attributeQuery.contains(Attributes.TIMEZONE_ID)) {
                id = (TZID) attributeQuery.get(Attributes.TIMEZONE_ID);
            } else {
                if (!((Leniency) attributeQuery.get(Attributes.LENIENCY, Leniency.SMART)).isLax()) {
                    return null;
                }
                id = Timezone.ofSystem().getID();
            }
            return (HistoricCalendar) Moment.from(timeSource.currentTime()).toGeneralTimestamp(HistoricCalendar.ENGINE, str, id, (StartOfDay) attributeQuery.get(Attributes.START_OF_DAY, getDefaultStartOfDay())).toDate();
        }

        @Override // net.time4j.engine.ChronoMerger
        /* renamed from: createFrom, reason: avoid collision after fix types in other method */
        public HistoricCalendar createFrom2(ChronoEntity<?> chronoEntity, AttributeQuery attributeQuery, boolean z, boolean z2) {
            ChronoHistory history = HistoricCalendar.getHistory(attributeQuery);
            if (history == null) {
                chronoEntity.with((ChronoElement<ValidationElement>) ValidationElement.ERROR_MESSAGE, (ValidationElement) "Cannot find any calendar history.");
                return null;
            }
            if (chronoEntity.contains(HistoricCalendar.ERA)) {
                HistoricEra historicEra = (HistoricEra) chronoEntity.get(HistoricCalendar.ERA);
                chronoEntity.with((ChronoElement<ChronoElement>) HistoricCalendar.ERA, (ChronoElement) null);
                chronoEntity.with((ChronoElement<ChronoElement>) history.era(), (ChronoElement) historicEra);
            }
            if (chronoEntity.contains(HistoricCalendar.RELATED_STANDARD_YEAR)) {
                int i = chronoEntity.getInt(HistoricCalendar.RELATED_STANDARD_YEAR);
                chronoEntity.with(HistoricCalendar.RELATED_STANDARD_YEAR, (TextElement<Integer>) null);
                chronoEntity.with((ChronoElement<Integer>) history.yearOfEra(), i);
            }
            if (chronoEntity.contains(HistoricCalendar.DAY_OF_YEAR)) {
                int i2 = chronoEntity.getInt(HistoricCalendar.DAY_OF_YEAR);
                chronoEntity.with(HistoricCalendar.DAY_OF_YEAR, (StdCalendarElement<Integer, HistoricCalendar>) null);
                chronoEntity.with(history.dayOfYear(), i2);
            } else {
                if (chronoEntity.contains(HistoricCalendar.MONTH_OF_YEAR)) {
                    Month month = (Month) chronoEntity.get(HistoricCalendar.MONTH_OF_YEAR);
                    chronoEntity.with(HistoricCalendar.MONTH_OF_YEAR, (StdCalendarElement<Month, HistoricCalendar>) null);
                    chronoEntity.with((ChronoElement<Integer>) history.month(), month.getValue());
                }
                if (chronoEntity.contains(HistoricCalendar.DAY_OF_MONTH)) {
                    int i3 = chronoEntity.getInt(HistoricCalendar.DAY_OF_MONTH);
                    chronoEntity.with(HistoricCalendar.DAY_OF_MONTH, (StdCalendarElement<Integer, HistoricCalendar>) null);
                    chronoEntity.with(history.dayOfMonth(), i3);
                }
            }
            ChronoEntity<?> resolve = new HistoricExtension().resolve(chronoEntity, history, attributeQuery);
            if (resolve.contains(PlainDate.COMPONENT)) {
                return new HistoricCalendar(history, (PlainDate) resolve.get(PlainDate.COMPONENT));
            }
            return null;
        }
    }

    private static class SPX implements Externalizable {
        private static final int HISTORIC = 11;
        private static final long serialVersionUID = 1;
        private transient Object obj;

        public SPX() {
        }

        SPX(Object obj) {
            this.obj = obj;
        }

        @Override // java.io.Externalizable
        public void writeExternal(ObjectOutput objectOutput) throws IOException {
            objectOutput.writeByte(11);
            writeHistoric(objectOutput);
        }

        @Override // java.io.Externalizable
        public void readExternal(ObjectInput objectInput) throws IOException {
            if (objectInput.readByte() == 11) {
                this.obj = readHistoric(objectInput);
                return;
            }
            throw new InvalidObjectException("Unknown calendar type.");
        }

        private Object readResolve() throws ObjectStreamException {
            return this.obj;
        }

        private void writeHistoric(ObjectOutput objectOutput) throws IOException {
            HistoricCalendar historicCalendar = (HistoricCalendar) this.obj;
            objectOutput.writeUTF(historicCalendar.getHistory().getVariant());
            objectOutput.writeLong(((PlainDate) historicCalendar.get(PlainDate.COMPONENT)).getDaysSinceEpochUTC());
        }

        private HistoricCalendar readHistoric(ObjectInput objectInput) throws IOException {
            return (HistoricCalendar) PlainDate.of(objectInput.readLong(), EpochDays.UTC).transform(HistoricCalendar.class, objectInput.readUTF());
        }
    }
}

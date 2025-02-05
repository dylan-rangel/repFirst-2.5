package net.time4j.calendar;

import androidx.exifinterface.media.ExifInterface;
import java.io.ObjectStreamException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import net.time4j.Weekday;
import net.time4j.Weekmodel;
import net.time4j.base.MathUtils;
import net.time4j.calendar.service.StdEnumDateElement;
import net.time4j.calendar.service.StdIntegerDateElement;
import net.time4j.engine.AttributeQuery;
import net.time4j.engine.BasicElement;
import net.time4j.engine.CalendarDate;
import net.time4j.engine.CalendarVariant;
import net.time4j.engine.ChronoDisplay;
import net.time4j.engine.ChronoElement;
import net.time4j.engine.ChronoEntity;
import net.time4j.engine.ChronoExtension;
import net.time4j.engine.ChronoOperator;
import net.time4j.engine.Chronology;
import net.time4j.engine.ElementRule;
import net.time4j.engine.EpochDays;
import net.time4j.engine.FormattableElement;

/* loaded from: classes3.dex */
public class CommonElements {

    @FormattableElement(format = "r")
    public static final ChronoElement<Integer> RELATED_GREGORIAN_YEAR = RelatedGregorianYearElement.SINGLETON;

    private CommonElements() {
    }

    @FormattableElement(alt = "c", format = "e")
    public static <T extends ChronoEntity<T> & CalendarDate> StdCalendarElement<Weekday, T> localDayOfWeek(Chronology<T> chronology, Weekmodel weekmodel) {
        checkSevenDayWeek(chronology);
        return new DayOfWeekElement(chronology.getChronoType(), weekmodel);
    }

    @FormattableElement(format = "w")
    public static <T extends ChronoEntity<T> & CalendarDate> StdCalendarElement<Integer, T> weekOfYear(Chronology<T> chronology, Weekmodel weekmodel) {
        ChronoElement<Integer> findDayElement = findDayElement(chronology, "DAY_OF_YEAR");
        if (findDayElement == null) {
            throw new IllegalArgumentException("Cannot derive a rule for given chronology: " + chronology);
        }
        return new CalendarWeekElement("WEEK_OF_YEAR", chronology.getChronoType(), 1, 52, 'w', weekmodel, findDayElement, false);
    }

    @FormattableElement(format = ExifInterface.LONGITUDE_WEST)
    public static <T extends ChronoEntity<T> & CalendarDate> StdCalendarElement<Integer, T> weekOfMonth(Chronology<T> chronology, Weekmodel weekmodel) {
        ChronoElement<Integer> findDayElement = findDayElement(chronology, "DAY_OF_MONTH");
        if (findDayElement == null) {
            throw new IllegalArgumentException("Cannot derive a rule for given chronology: " + chronology);
        }
        return new CalendarWeekElement("WEEK_OF_MONTH", chronology.getChronoType(), 1, 5, 'W', weekmodel, findDayElement, false);
    }

    public static <T extends ChronoEntity<T> & CalendarDate> StdCalendarElement<Integer, T> boundedWeekOfYear(Chronology<T> chronology, Weekmodel weekmodel) {
        ChronoElement<Integer> findDayElement = findDayElement(chronology, "DAY_OF_YEAR");
        if (findDayElement == null) {
            throw new IllegalArgumentException("Cannot derive a rule for given chronology: " + chronology);
        }
        return new CalendarWeekElement("BOUNDED_WEEK_OF_YEAR", chronology.getChronoType(), 1, 52, (char) 0, weekmodel, findDayElement, true);
    }

    public static <T extends ChronoEntity<T> & CalendarDate> StdCalendarElement<Integer, T> boundedWeekOfMonth(Chronology<T> chronology, Weekmodel weekmodel) {
        ChronoElement<Integer> findDayElement = findDayElement(chronology, "DAY_OF_MONTH");
        if (findDayElement == null) {
            throw new IllegalArgumentException("Cannot derive a rule for given chronology: " + chronology);
        }
        return new CalendarWeekElement("BOUNDED_WEEK_OF_MONTH", chronology.getChronoType(), 1, 5, (char) 0, weekmodel, findDayElement, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <D extends ChronoEntity<D>> int getMax(ChronoElement<?> chronoElement, D d) {
        return ((Integer) Integer.class.cast(d.getMaximum(chronoElement))).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Weekday getDayOfWeek(long j) {
        return Weekday.valueOf(MathUtils.floorModulo(j + 5, 7) + 1);
    }

    private static void checkSevenDayWeek(Chronology<?> chronology) {
        Object[] enumConstants;
        if (CalendarDate.class.isAssignableFrom(chronology.getChronoType())) {
            for (ChronoElement<?> chronoElement : chronology.getRegisteredElements()) {
                if (chronoElement.name().equals("DAY_OF_WEEK") && (enumConstants = chronoElement.getType().getEnumConstants()) != null && enumConstants.length == 7) {
                    return;
                }
            }
        }
        throw new IllegalArgumentException("No 7-day-week: " + chronology);
    }

    private static <D extends ChronoEntity<D>> ChronoElement<Integer> findDayElement(Chronology<D> chronology, String str) {
        checkSevenDayWeek(chronology);
        Iterator<ChronoElement<?>> it = chronology.getRegisteredElements().iterator();
        while (it.hasNext()) {
            ChronoElement<Integer> chronoElement = (ChronoElement) it.next();
            if (chronoElement.name().equals(str)) {
                if (chronoElement.getType() == Integer.class) {
                    return chronoElement;
                }
                return null;
            }
        }
        return null;
    }

    static class Weekengine implements ChronoExtension {
        private final Class<? extends ChronoEntity> chronoType;
        private final ChronoElement<Integer> dayOfMonthElement;
        private final ChronoElement<Integer> dayOfYearElement;
        private final Weekmodel defaultWeekmodel;

        @Override // net.time4j.engine.ChronoExtension
        public boolean canResolve(ChronoElement<?> chronoElement) {
            return false;
        }

        @Override // net.time4j.engine.ChronoExtension
        public ChronoEntity<?> resolve(ChronoEntity<?> chronoEntity, Locale locale, AttributeQuery attributeQuery) {
            return chronoEntity;
        }

        Weekengine(Class<? extends ChronoEntity> cls, ChronoElement<Integer> chronoElement, ChronoElement<Integer> chronoElement2, Weekmodel weekmodel) {
            this.chronoType = cls;
            this.dayOfMonthElement = chronoElement;
            this.dayOfYearElement = chronoElement2;
            this.defaultWeekmodel = weekmodel;
        }

        @Override // net.time4j.engine.ChronoExtension
        public boolean accept(Class<?> cls) {
            return this.chronoType.equals(cls);
        }

        @Override // net.time4j.engine.ChronoExtension
        public Set<ChronoElement<?>> getElements(Locale locale, AttributeQuery attributeQuery) {
            Weekmodel of = locale.getCountry().isEmpty() ? this.defaultWeekmodel : Weekmodel.of(locale);
            HashSet hashSet = new HashSet();
            hashSet.add(DayOfWeekElement.of(this.chronoType, of));
            Weekmodel weekmodel = of;
            hashSet.add(CalendarWeekElement.of("WEEK_OF_MONTH", this.chronoType, 1, 5, 'W', weekmodel, this.dayOfMonthElement, false));
            hashSet.add(CalendarWeekElement.of("WEEK_OF_YEAR", this.chronoType, 1, 52, 'w', weekmodel, this.dayOfYearElement, false));
            hashSet.add(CalendarWeekElement.of("BOUNDED_WEEK_OF_MONTH", this.chronoType, 1, 5, (char) 0, weekmodel, this.dayOfMonthElement, true));
            hashSet.add(CalendarWeekElement.of("BOUNDED_WEEK_OF_YEAR", this.chronoType, 1, 52, (char) 0, weekmodel, this.dayOfYearElement, true));
            return Collections.unmodifiableSet(hashSet);
        }
    }

    private static class CalendarWeekElement<T extends ChronoEntity<T>> extends StdIntegerDateElement<T> {
        private static final long serialVersionUID = -7471192143785466686L;
        private final boolean bounded;
        private final ChronoElement<Integer> dayElement;
        private final Weekmodel model;

        @Override // net.time4j.engine.BasicElement, net.time4j.engine.ChronoElement
        public boolean isLenient() {
            return true;
        }

        @Override // net.time4j.calendar.service.StdDateElement
        protected Object readResolve() throws ObjectStreamException {
            return this;
        }

        CalendarWeekElement(String str, Class<T> cls, int i, int i2, char c, Weekmodel weekmodel, ChronoElement<Integer> chronoElement, boolean z) {
            super(str, cls, i, i2, c);
            if (weekmodel == null) {
                throw new NullPointerException("Missing week model.");
            }
            this.model = weekmodel;
            this.dayElement = chronoElement;
            this.bounded = z;
        }

        static <T extends ChronoEntity<T>> CalendarWeekElement<T> of(String str, Class<T> cls, int i, int i2, char c, Weekmodel weekmodel, ChronoElement<Integer> chronoElement, boolean z) {
            return new CalendarWeekElement<>(str, cls, i, i2, c, weekmodel, chronoElement, z);
        }

        @Override // net.time4j.calendar.service.StdIntegerDateElement, net.time4j.calendar.service.StdDateElement, net.time4j.calendar.StdCalendarElement
        public ChronoOperator<T> decremented() {
            return new DayOperator(-7);
        }

        @Override // net.time4j.calendar.service.StdIntegerDateElement, net.time4j.calendar.service.StdDateElement, net.time4j.calendar.StdCalendarElement
        public ChronoOperator<T> incremented() {
            return new DayOperator(7);
        }

        @Override // net.time4j.calendar.service.StdDateElement, net.time4j.engine.BasicElement
        protected boolean doEquals(BasicElement<?> basicElement) {
            if (!super.doEquals(basicElement)) {
                return false;
            }
            CalendarWeekElement calendarWeekElement = (CalendarWeekElement) CalendarWeekElement.class.cast(basicElement);
            return this.model.equals(calendarWeekElement.model) && this.bounded == calendarWeekElement.bounded;
        }

        @Override // net.time4j.engine.BasicElement
        protected <D extends ChronoEntity<D>> ElementRule<D, Integer> derive(Chronology<D> chronology) {
            if (getChronoType().equals(chronology.getChronoType())) {
                return this.bounded ? new BWRule(this) : new CWRule(this);
            }
            return null;
        }
    }

    private static class CWRule<D extends ChronoEntity<D>> implements ElementRule<D, Integer> {
        private final CalendarWeekElement<?> owner;

        private CWRule(CalendarWeekElement<?> calendarWeekElement) {
            this.owner = calendarWeekElement;
        }

        @Override // net.time4j.engine.ElementRule
        public Integer getValue(D d) {
            return Integer.valueOf(getCalendarWeek(d));
        }

        @Override // net.time4j.engine.ElementRule
        public Integer getMinimum(D d) {
            return 1;
        }

        @Override // net.time4j.engine.ElementRule
        public Integer getMaximum(D d) {
            return Integer.valueOf(getMaxCalendarWeek(d));
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: isValid, reason: merged with bridge method [inline-methods] */
        public boolean isValid2(D d, Integer num) {
            int intValue;
            return num != null && (intValue = num.intValue()) >= 1 && intValue <= getMaxCalendarWeek(d);
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: withValue, reason: merged with bridge method [inline-methods] */
        public D withValue2(D d, Integer num, boolean z) {
            int intValue = num.intValue();
            if (!z && !isValid2((CWRule<D>) d, num)) {
                throw new IllegalArgumentException("Invalid value: " + intValue + " (context=" + d + ")");
            }
            return setCalendarWeek(d, intValue);
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtFloor(D d) {
            return getChild(d.getClass());
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtCeiling(D d) {
            return getChild(d.getClass());
        }

        private ChronoElement<?> getChild(Object obj) {
            return new DayOfWeekElement((Class) obj, ((CalendarWeekElement) this.owner).model);
        }

        /* JADX WARN: Multi-variable type inference failed */
        private int getMaxCalendarWeek(D d) {
            int i = d.getInt(((CalendarWeekElement) this.owner).dayElement);
            int firstCalendarWeekAsDay = getFirstCalendarWeekAsDay(d, 0);
            if (firstCalendarWeekAsDay <= i) {
                int firstCalendarWeekAsDay2 = getFirstCalendarWeekAsDay(d, 1) + getLengthOfYM(d, 0);
                if (firstCalendarWeekAsDay2 <= i) {
                    try {
                        int firstCalendarWeekAsDay3 = getFirstCalendarWeekAsDay(d, 1);
                        firstCalendarWeekAsDay2 = getFirstCalendarWeekAsDay(d.with(EpochDays.UTC, ((Long) d.get(EpochDays.UTC)).longValue() + 7), 1) + getLengthOfYM(d, 1);
                        firstCalendarWeekAsDay = firstCalendarWeekAsDay3;
                    } catch (RuntimeException unused) {
                        firstCalendarWeekAsDay2 += 7;
                    }
                }
                return (firstCalendarWeekAsDay2 - firstCalendarWeekAsDay) / 7;
            }
            return ((firstCalendarWeekAsDay + getLengthOfYM(d, -1)) - getFirstCalendarWeekAsDay(d, -1)) / 7;
        }

        private int getFirstCalendarWeekAsDay(D d, int i) {
            Weekday weekdayStart = getWeekdayStart(d, i);
            Weekmodel weekmodel = ((CalendarWeekElement) this.owner).model;
            int value = weekdayStart.getValue(weekmodel);
            return value <= 8 - weekmodel.getMinimalDaysInFirstWeek() ? 2 - value : 9 - value;
        }

        private Weekday getWeekdayStart(D d, int i) {
            int i2 = d.getInt(((CalendarWeekElement) this.owner).dayElement);
            if (i == -1) {
                return CommonElements.getDayOfWeek(((((Long) d.get(EpochDays.UTC)).longValue() - i2) - d.with(EpochDays.UTC, r4).getInt(((CalendarWeekElement) this.owner).dayElement)) + 1);
            }
            if (i == 0) {
                return CommonElements.getDayOfWeek((((Long) d.get(EpochDays.UTC)).longValue() - i2) + 1);
            }
            if (i == 1) {
                return CommonElements.getDayOfWeek(((((Long) d.get(EpochDays.UTC)).longValue() + CommonElements.getMax(((CalendarWeekElement) this.owner).dayElement, d)) + 1) - i2);
            }
            throw new AssertionError("Unexpected: " + i);
        }

        private int getLengthOfYM(D d, int i) {
            int i2 = d.getInt(((CalendarWeekElement) this.owner).dayElement);
            if (i == -1) {
                return CommonElements.getMax(((CalendarWeekElement) this.owner).dayElement, d.with(EpochDays.UTC, ((Long) d.get(EpochDays.UTC)).longValue() - i2));
            }
            if (i == 0) {
                return CommonElements.getMax(((CalendarWeekElement) this.owner).dayElement, d);
            }
            if (i == 1) {
                return CommonElements.getMax(((CalendarWeekElement) this.owner).dayElement, d.with(EpochDays.UTC, ((((Long) d.get(EpochDays.UTC)).longValue() + CommonElements.getMax(((CalendarWeekElement) this.owner).dayElement, d)) + 1) - i2));
            }
            throw new AssertionError("Unexpected: " + i);
        }

        private int getCalendarWeek(D d) {
            int lengthOfYM;
            int i = d.getInt(((CalendarWeekElement) this.owner).dayElement);
            int firstCalendarWeekAsDay = getFirstCalendarWeekAsDay(d, 0);
            if (firstCalendarWeekAsDay <= i) {
                if (getFirstCalendarWeekAsDay(d, 1) + getLengthOfYM(d, 0) <= i) {
                    return 1;
                }
                lengthOfYM = (i - firstCalendarWeekAsDay) / 7;
            } else {
                lengthOfYM = ((i + getLengthOfYM(d, -1)) - getFirstCalendarWeekAsDay(d, -1)) / 7;
            }
            return lengthOfYM + 1;
        }

        private D setCalendarWeek(D d, int i) {
            return i == getCalendarWeek(d) ? d : (D) d.with(EpochDays.UTC, ((Long) d.get(EpochDays.UTC)).longValue() + ((i - r0) * 7));
        }
    }

    private static class BWRule<D extends ChronoEntity<D>> implements ElementRule<D, Integer> {
        private final CalendarWeekElement<?> owner;

        private BWRule(CalendarWeekElement<?> calendarWeekElement) {
            this.owner = calendarWeekElement;
        }

        @Override // net.time4j.engine.ElementRule
        public Integer getValue(D d) {
            return Integer.valueOf(getWeek(d));
        }

        @Override // net.time4j.engine.ElementRule
        public Integer getMinimum(D d) {
            return Integer.valueOf(getMinWeek(d));
        }

        @Override // net.time4j.engine.ElementRule
        public Integer getMaximum(D d) {
            return Integer.valueOf(getMaxWeek(d));
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtFloor(D d) {
            return getChild(d, false);
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtCeiling(D d) {
            return getChild(d, true);
        }

        private ChronoElement<?> getChild(D d, boolean z) {
            DayOfWeekElement of = DayOfWeekElement.of(d.getClass(), ((CalendarWeekElement) this.owner).model);
            int week = getWeek(d);
            long longValue = ((Long) d.get(EpochDays.UTC)).longValue();
            int i = d.getInt(((CalendarWeekElement) this.owner).dayElement);
            if (z) {
                if (((Integer) d.getMaximum(((CalendarWeekElement) this.owner).dayElement)).intValue() < i + (((Long) d.with(of, d.getMaximum(of)).get(EpochDays.UTC)).longValue() - longValue)) {
                    return ((CalendarWeekElement) this.owner).dayElement;
                }
            } else if (week <= 1) {
                if (((Integer) d.getMinimum(((CalendarWeekElement) this.owner).dayElement)).intValue() > i - (longValue - ((Long) d.with(of, d.getMinimum(of)).get(EpochDays.UTC)).longValue())) {
                    return ((CalendarWeekElement) this.owner).dayElement;
                }
            }
            return of;
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: isValid, reason: merged with bridge method [inline-methods] */
        public boolean isValid2(D d, Integer num) {
            int intValue;
            return num != null && (intValue = num.intValue()) >= getMinWeek(d) && intValue <= getMaxWeek(d);
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: withValue, reason: merged with bridge method [inline-methods] */
        public D withValue2(D d, Integer num, boolean z) {
            if (num == null || (!z && !isValid2((BWRule<D>) d, num))) {
                throw new IllegalArgumentException("Invalid value: " + num + " (context=" + d + ")");
            }
            return setWeek(d, num.intValue());
        }

        private int getWeek(D d) {
            return getWeek(d, 0);
        }

        private int getMinWeek(D d) {
            return getWeek(d, -1);
        }

        private int getMaxWeek(D d) {
            return getWeek(d, 1);
        }

        private int getWeek(D d, int i) {
            int i2 = d.getInt(((CalendarWeekElement) this.owner).dayElement);
            int value = CommonElements.getDayOfWeek((((Long) d.get(EpochDays.UTC)).longValue() - i2) + 1).getValue(((CalendarWeekElement) this.owner).model);
            int i3 = value <= 8 - ((CalendarWeekElement) this.owner).model.getMinimalDaysInFirstWeek() ? 2 - value : 9 - value;
            if (i == -1) {
                i2 = 1;
            } else if (i != 0) {
                if (i == 1) {
                    i2 = ((Integer) d.getMaximum(((CalendarWeekElement) this.owner).dayElement)).intValue();
                } else {
                    throw new AssertionError("Unexpected: " + i);
                }
            }
            return MathUtils.floorDivide(i2 - i3, 7) + 1;
        }

        private D setWeek(D d, int i) {
            if (i == getWeek(d)) {
                return d;
            }
            return (D) d.with(EpochDays.UTC, ((Long) d.get(EpochDays.UTC)).longValue() + ((i - r0) * 7));
        }
    }

    private static class DayOfWeekElement<T extends ChronoEntity<T>> extends StdEnumDateElement<Weekday, T> {
        private static final long serialVersionUID = 5613494586572932860L;
        private final Weekmodel model;

        @Override // net.time4j.calendar.service.StdEnumDateElement
        protected boolean isWeekdayElement() {
            return true;
        }

        @Override // net.time4j.calendar.service.StdDateElement
        protected Object readResolve() throws ObjectStreamException {
            return this;
        }

        DayOfWeekElement(Class<T> cls, Weekmodel weekmodel) {
            super("LOCAL_DAY_OF_WEEK", cls, Weekday.class, 'e');
            this.model = weekmodel;
        }

        static <T extends ChronoEntity<T>> DayOfWeekElement<T> of(Class<T> cls, Weekmodel weekmodel) {
            return new DayOfWeekElement<>(cls, weekmodel);
        }

        @Override // net.time4j.calendar.service.StdEnumDateElement, net.time4j.calendar.service.StdDateElement, net.time4j.calendar.StdCalendarElement
        public ChronoOperator<T> decremented() {
            return new DayOperator(-1);
        }

        @Override // net.time4j.calendar.service.StdEnumDateElement, net.time4j.calendar.service.StdDateElement, net.time4j.calendar.StdCalendarElement
        public ChronoOperator<T> incremented() {
            return new DayOperator(1);
        }

        @Override // net.time4j.calendar.service.StdEnumDateElement, net.time4j.format.NumericalElement
        public int numerical(Weekday weekday) {
            return weekday.getValue(this.model);
        }

        @Override // net.time4j.calendar.service.StdEnumDateElement, net.time4j.engine.ChronoElement
        public Weekday getDefaultMinimum() {
            return this.model.getFirstDayOfWeek();
        }

        @Override // net.time4j.calendar.service.StdEnumDateElement, net.time4j.engine.ChronoElement
        public Weekday getDefaultMaximum() {
            return this.model.getFirstDayOfWeek().roll(6);
        }

        @Override // net.time4j.engine.BasicElement, java.util.Comparator
        public int compare(ChronoDisplay chronoDisplay, ChronoDisplay chronoDisplay2) {
            int value = ((Weekday) chronoDisplay.get(this)).getValue(this.model);
            int value2 = ((Weekday) chronoDisplay2.get(this)).getValue(this.model);
            if (value < value2) {
                return -1;
            }
            return value == value2 ? 0 : 1;
        }

        @Override // net.time4j.calendar.service.StdDateElement, net.time4j.engine.BasicElement
        protected boolean doEquals(BasicElement<?> basicElement) {
            if (!super.doEquals(basicElement)) {
                return false;
            }
            return this.model.equals(((DayOfWeekElement) DayOfWeekElement.class.cast(basicElement)).model);
        }

        @Override // net.time4j.engine.BasicElement
        protected <D extends ChronoEntity<D>> ElementRule<D, Weekday> derive(Chronology<D> chronology) {
            if (getChronoType().equals(chronology.getChronoType())) {
                return new DRule(this);
            }
            return null;
        }
    }

    private static class DRule<T extends ChronoEntity<T>> implements ElementRule<T, Weekday> {
        private final DayOfWeekElement<?> element;

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtCeiling(T t) {
            return null;
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtFloor(T t) {
            return null;
        }

        private DRule(DayOfWeekElement<?> dayOfWeekElement) {
            this.element = dayOfWeekElement;
        }

        @Override // net.time4j.engine.ElementRule
        public Weekday getValue(T t) {
            return CommonElements.getDayOfWeek(((Long) t.get(EpochDays.UTC)).longValue());
        }

        @Override // net.time4j.engine.ElementRule
        public Weekday getMinimum(T t) {
            long minimumSinceUTC;
            Chronology lookup = Chronology.lookup(t.getClass());
            if (t instanceof CalendarVariant) {
                minimumSinceUTC = lookup.getCalendarSystem(((CalendarVariant) CalendarVariant.class.cast(t)).getVariant()).getMinimumSinceUTC();
            } else {
                minimumSinceUTC = lookup.getCalendarSystem().getMinimumSinceUTC();
            }
            if ((((Long) t.get(EpochDays.UTC)).longValue() + 1) - CommonElements.getDayOfWeek(r2).getValue(((DayOfWeekElement) this.element).model) < minimumSinceUTC) {
                return CommonElements.getDayOfWeek(minimumSinceUTC);
            }
            return this.element.getDefaultMinimum();
        }

        @Override // net.time4j.engine.ElementRule
        public Weekday getMaximum(T t) {
            long maximumSinceUTC;
            Chronology lookup = Chronology.lookup(t.getClass());
            if (t instanceof CalendarVariant) {
                maximumSinceUTC = lookup.getCalendarSystem(((CalendarVariant) CalendarVariant.class.cast(t)).getVariant()).getMaximumSinceUTC();
            } else {
                maximumSinceUTC = lookup.getCalendarSystem().getMaximumSinceUTC();
            }
            if ((((Long) t.get(EpochDays.UTC)).longValue() + 7) - CommonElements.getDayOfWeek(r2).getValue(((DayOfWeekElement) this.element).model) > maximumSinceUTC) {
                return CommonElements.getDayOfWeek(maximumSinceUTC);
            }
            return this.element.getDefaultMaximum();
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: isValid, reason: merged with bridge method [inline-methods] */
        public boolean isValid2(T t, Weekday weekday) {
            if (weekday == null) {
                return false;
            }
            try {
                withValue2((DRule<T>) t, weekday, false);
                return true;
            } catch (ArithmeticException | IllegalArgumentException unused) {
                return false;
            }
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: withValue, reason: merged with bridge method [inline-methods] */
        public T withValue2(T t, Weekday weekday, boolean z) {
            long longValue = ((Long) t.get(EpochDays.UTC)).longValue();
            Weekday dayOfWeek = CommonElements.getDayOfWeek(longValue);
            if (weekday == dayOfWeek) {
                return t;
            }
            return (T) t.with(EpochDays.UTC, (longValue + weekday.getValue(((DayOfWeekElement) this.element).model)) - dayOfWeek.getValue(((DayOfWeekElement) this.element).model));
        }
    }

    private static class DayOperator<T extends ChronoEntity<T>> implements ChronoOperator<T> {
        private final int amount;

        DayOperator(int i) {
            this.amount = i;
        }

        @Override // net.time4j.engine.ChronoOperator
        public T apply(T t) {
            return (T) t.with(EpochDays.UTC, MathUtils.safeAdd(((Long) t.get(EpochDays.UTC)).longValue(), this.amount));
        }
    }
}

package net.time4j.calendar;

import androidx.exifinterface.media.ExifInterface;
import java.io.Externalizable;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import net.time4j.PlainDate;
import net.time4j.SystemClock;
import net.time4j.Weekday;
import net.time4j.Weekmodel;
import net.time4j.calendar.CommonElements;
import net.time4j.calendar.service.StdIntegerDateElement;
import net.time4j.calendar.service.StdWeekdayElement;
import net.time4j.engine.AttributeQuery;
import net.time4j.engine.CalendarEra;
import net.time4j.engine.CalendarSystem;
import net.time4j.engine.ChronoElement;
import net.time4j.engine.ChronoEntity;
import net.time4j.engine.ChronoExtension;
import net.time4j.engine.ChronoFunction;
import net.time4j.engine.ChronoUnit;
import net.time4j.engine.ElementRule;
import net.time4j.engine.FormattableElement;
import net.time4j.engine.TimeAxis;
import net.time4j.engine.ValidationElement;
import net.time4j.format.CalendarType;
import net.time4j.format.LocalizedPatternSupport;
import net.time4j.format.TextElement;
import net.time4j.tz.OffsetSign;
import net.time4j.tz.ZonalOffset;

@CalendarType("vietnam")
/* loaded from: classes3.dex */
public final class VietnameseCalendar extends EastAsianCalendar<Unit, VietnameseCalendar> implements LocalizedPatternSupport {
    private static final EastAsianCS<VietnameseCalendar> CALSYS;
    public static final ChronoElement<Integer> CYCLE;

    @FormattableElement(format = "d")
    public static final StdCalendarElement<Integer, VietnameseCalendar> DAY_OF_MONTH;

    @FormattableElement(format = ExifInterface.LONGITUDE_EAST)
    public static final StdCalendarElement<Weekday, VietnameseCalendar> DAY_OF_WEEK;

    @FormattableElement(format = "D")
    public static final StdCalendarElement<Integer, VietnameseCalendar> DAY_OF_YEAR;
    private static final TimeAxis<Unit, VietnameseCalendar> ENGINE;
    private static final int[] LEAP_MONTHS = {4450, 2, 4452, 6, 4455, 4, 4458, 3, 4460, 7, 4463, 5, 4466, 4, 4468, 9, 4471, 6, 4474, 4, 4477, 3, 4479, 7, 4482, 5, 4485, 4, 4487, 8, 4490, 7, 4493, 5, 4496, 3, 4498, 8, 4501, 5, 4504, 4, 4506, 10, 4509, 6, 4512, 5, 4515, 3, 4517, 7, 4520, 5, 4523, 4, 4526, 2, 4528, 6, 4531, 5, 4534, 3, 4536, 8, 4539, 5, 4542, 4, 4545, 2, 4547, 6, 4550, 5, 4553, 3, 4555, 7, 4558, 6, 4561, 4, 4564, 2, 4566, 6, 4569, 5, 4572, 3, 4574, 7, 4577, 6, 4580, 4, 4583, 2, 4585, 7, 4588, 5, 4591, 3, 4593, 8, 4596, 6, 4599, 4, 4602, 3, 4604, 7, 4607, 5, 4610, 4, 4612, 8, 4615, 6, 4618, 4, 4621, 2, 4623, 7, 4626, 5, 4629, 3, 4631, 8, 4634, 5, 4637, 4, 4640, 2, 4642, 7, 4645, 5, 4648, 4, 4650, 9, 4653, 6, 4656, 4, 4659, 2, 4661, 6, 4664, 5, 4667, 3, 4669, 11, 4672, 6, 4675, 5, 4678, 2, 4680, 7, 4683, 5, 4686, 3, 4688, 8, 4691, 6, 4694, 4, 4697, 3, 4699, 7, 4702, 5, 4705, 4, 4707, 8, 4710, 6, 4713, 4, 4716, 3, 4718, 7, 4721, 5, 4724, 4, 4726, 8, 4729, 6, 4732, 4, 4735, 2, 4737, 7, 4740, 5, 4743, 4, 4745, 9, 4748, 6, 4751, 4, 4754, 3, 4756, 7, 4759, 5, 4762, 4, 4764, 11, 4767, 6, 4770, 5, 4773, 2, 4775, 7, 4778, 5, 4781, 4, 4784, 1, 4786, 6, 4789, 5, 4792, 3, 4794, 7, 4797, 6, 4800, 4, 4802, 10, 4805, 6, 4808, 5, 4811, 3, 4813, 7, 4816, 6, 4819, 4, 4822, 2, 4824, 6, 4827, 5, 4830, 3, 4832, 7, 4835, 6, 4838, 4, 4840, 9, 4843, 6, 4846, 4, 4849, 3, 4851, 7, 4854, 5, 4857, 4, 4859, 11, 4862, 7, 4865, 5, 4868, 3, 4870, 8, 4873, 5, 4876, 4, 4878, 11, 4881, 6, 4884, 5, 4887, 3, 4889, 7, 4892, 6, 4895, 4, 4898, 1, 4900, 6, 4903, 5, 4906, 3, 4908, 8, 4911, 6, 4914, 4, 4917, 2, 4919, 6, 4922, 5, 4925, 3, 4927, 7, 4930, 6, 4933, 4, 4936, 2, 4938, 6, 4941, 5, 4944, 3, 4946, 7, 4949, 6, 4952, 4, 4954, 10, 4957, 7, 4960, 5, 4963, 3, 4965, 8, 4968, 6, 4971, 4, 4974, 3, 4976, 7, 4979, 5, 4982, 4, 4984, 8, 4987, 6, 4990, 5, 4993, 1, 4995, 7, 4998, 5, 5001, 4, 5003, 8, 5006, 6, 5009, 5, 5012, 2, 5014, 7, 5017, 5, 5020, 4, 5022, 10, 5025, 6, 5028, 4, 5031, 2, 5033, 6, 5036, 5, 5039, 3, 5041, 7, 5044, 6, 5047, 5, 5050, 2, 5052, 7, 5055, 5, 5058, 3, 5060, 8, 5063, 6, 5066, 4, 5069, 3, 5071, 7, 5074, 5, 5077, 4, 5079, 8, 5082, 7, 5085, 5, 5088, 3, 5090, 8, 5093, 5, 5096, 4, 5098, 8, 5101, 6, 5104, 5, 5107, 3, 5109, 7, 5112, 5, 5115, 4, 5117, 10, 5120, 6, 5123, 5, 5126, 3, 5128, 7, 5131, 5, 5134, 4, 5136, 10, 5139, 6, 5142, 5, 5145, 2, 5147, 7, 5150, 6, 5153, 4, 5156, 1, 5158, 6, 5161, 5, 5164, 3, 5166, 7, 5169, 6, 5172, 4, 5174, 10, 5177, 7, 5180, 5, 5183, 3, 5185, 7, 5188, 6, 5191, 4, 5193, 8, 5196, 7, 5199, 5, 5202, 3, 5204, 7, 5207, 6, 5210, 4, 5212, 10, 5215, 6, 5218, 5, 5221, 3, 5223, 7, 5226, 5, 5229, 4, 5231, 9, 5234, 7, 5237, 5, 5240, 3, 5242, 8, 5245, 6, 5248, 4, 5250, 11, 5253, 6, 5256, 5, 5259, 3, 5261, 8, 5264, 6, 5267, 5, 5270, 1, 5272, 7, 5275, 5, 5278, 3, 5280, 8, 5283, 6, 5286, 4, 5289, 2, 5291, 7, 5294, 5, 5297, 3, 5299, 7, 5302, 6, 5305, 4, 5308, 3, 5310, 7, 5313, 5, 5316, 3, 5318, 7, 5321, 6, 5324, 4, 5327, 2, 5329, 7, 5332, 5, 5335, 3, 5337, 8, 5340, 6, 5343, 4, 5346, 3, 5348, 7, 5351, 5, 5354, 4, 5356, 9, 5359, 6, 5362, 5, 5364, 11, 5367, 7, 5370, 5, 5373, 4, 5375, 9, 5378, 6, 5381, 5, 5384, 2, 5386, 7, 5389, 6, 5392, 4, 5394, 8, 5397, 6, 5400, 5, 5403, 3, 5405, 7, 5408, 6, 5411, 3, 5413, 8, 5416, 6, 5419, 5, 5422, 3, 5424, 7, 5427, 6, 5430, 3, 5432, 8, 5435, 6, 5438, 4, 5441, 3, 5443, 7, 5446, 6, 5449, 4, 5451, 9, 5454, 7, 5457, 5, 5460, 3, 5462, 8, 5465, 5, 5468, 4, 5470, 9, 5473, 6, 5476, 5, 5479, 3, 5481, 8, 5484, 6, 5487, 5, 5489, 9, 5492, 7, 5495, 5, 5498, 3, 5500, 7, 5503, 6, 5506, 4, 5508, 10, 5511, 6, 5514, 5, 5517, 3, 5519, 7, 5522, 6, 5525, 4, 5527, 10, 5530, 6, 5533, 5, 5536, 3, 5538, 7, 5541, 6, 5544, 4, 5546, 11, 5549, 7, 5552, 5, 5555, 3, 5557, 8, 5560, 6, 5563, 4, 5565, 9, 5568, 7, 5571, 5, 5574, 4, 5576, 8, 5579, 6, 5582, 4, 5584, 8, 5587, 7, 5590, 5, 5593, 4, 5595, 8, 5598, 6, 5601, 4, 5603, 10, 5606, 7, 5609, 5, 5612, 3, 5614, 8, 5617, 6, 5620, 4, 5622, 10, 5625, 6, 5628, 5, 5631, 3, 5633, 8, 5636, 6};
    public static final StdCalendarElement<Integer, VietnameseCalendar> MONTH_AS_ORDINAL;

    @FormattableElement(alt = "L", format = "M")
    public static final TextElement<EastAsianMonth> MONTH_OF_YEAR;
    public static final ChronoElement<SolarTerm> SOLAR_TERM;

    @FormattableElement(format = "F")
    public static final OrdinalWeekdayElement<VietnameseCalendar> WEEKDAY_IN_MONTH;
    private static final WeekdayInMonthElement<VietnameseCalendar> WIM_ELEMENT;

    @FormattableElement(format = "U")
    public static final TextElement<CyclicYear> YEAR_OF_CYCLE;
    private static final long serialVersionUID = -3151525803739185874L;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.time4j.engine.ChronoEntity
    public VietnameseCalendar getContext() {
        return this;
    }

    static {
        StdIntegerDateElement stdIntegerDateElement = new StdIntegerDateElement("CYCLE", VietnameseCalendar.class, 75, 94, (char) 0, null, null);
        CYCLE = stdIntegerDateElement;
        EastAsianCY eastAsianCY = EastAsianCY.SINGLETON;
        YEAR_OF_CYCLE = eastAsianCY;
        EastAsianST eastAsianST = EastAsianST.getInstance();
        SOLAR_TERM = eastAsianST;
        EastAsianME eastAsianME = EastAsianME.SINGLETON_EA;
        MONTH_OF_YEAR = eastAsianME;
        StdIntegerDateElement stdIntegerDateElement2 = new StdIntegerDateElement("MONTH_AS_ORDINAL", VietnameseCalendar.class, 1, 12, (char) 0, null, null);
        MONTH_AS_ORDINAL = stdIntegerDateElement2;
        StdIntegerDateElement stdIntegerDateElement3 = new StdIntegerDateElement("DAY_OF_MONTH", VietnameseCalendar.class, 1, 30, 'd');
        DAY_OF_MONTH = stdIntegerDateElement3;
        StdIntegerDateElement stdIntegerDateElement4 = new StdIntegerDateElement("DAY_OF_YEAR", VietnameseCalendar.class, 1, 355, 'D');
        DAY_OF_YEAR = stdIntegerDateElement4;
        StdWeekdayElement stdWeekdayElement = new StdWeekdayElement(VietnameseCalendar.class, getDefaultWeekmodel());
        DAY_OF_WEEK = stdWeekdayElement;
        WeekdayInMonthElement<VietnameseCalendar> weekdayInMonthElement = new WeekdayInMonthElement<>(VietnameseCalendar.class, stdIntegerDateElement3, stdWeekdayElement);
        WIM_ELEMENT = weekdayInMonthElement;
        WEEKDAY_IN_MONTH = weekdayInMonthElement;
        Transformer transformer = new Transformer();
        CALSYS = transformer;
        ENGINE = TimeAxis.Builder.setUp(Unit.class, VietnameseCalendar.class, new Merger(), transformer).appendElement((ChronoElement) stdIntegerDateElement, EastAsianCalendar.getCycleRule(eastAsianCY)).appendElement(eastAsianCY, EastAsianCalendar.getVietYearOfCycleRule(eastAsianME), Unit.YEARS).appendElement((ChronoElement) eastAsianST, (ElementRule) EastAsianST.getInstance()).appendElement(eastAsianME, EastAsianCalendar.getMonthOfYearRule(stdIntegerDateElement3), Unit.MONTHS).appendElement(stdIntegerDateElement2, EastAsianCalendar.getMonthAsOrdinalRule(stdIntegerDateElement3), Unit.MONTHS).appendElement(stdIntegerDateElement3, EastAsianCalendar.getDayOfMonthRule(), Unit.DAYS).appendElement(stdIntegerDateElement4, EastAsianCalendar.getDayOfYearRule(), Unit.DAYS).appendElement(stdWeekdayElement, new WeekdayRule(getDefaultWeekmodel(), new ChronoFunction<VietnameseCalendar, CalendarSystem<VietnameseCalendar>>() { // from class: net.time4j.calendar.VietnameseCalendar.1
            @Override // net.time4j.engine.ChronoFunction
            public CalendarSystem<VietnameseCalendar> apply(VietnameseCalendar vietnameseCalendar) {
                return VietnameseCalendar.CALSYS;
            }
        }), Unit.DAYS).appendElement((ChronoElement) weekdayInMonthElement, WeekdayInMonthElement.getRule(weekdayInMonthElement)).appendElement((ChronoElement) CommonElements.RELATED_GREGORIAN_YEAR, (ElementRule) new RelatedGregorianYearRule(transformer, stdIntegerDateElement4)).appendUnit(Unit.CYCLES, EastAsianCalendar.getUnitRule(0), Unit.CYCLES.getLength(), Collections.singleton(Unit.YEARS)).appendUnit(Unit.YEARS, EastAsianCalendar.getUnitRule(1), Unit.YEARS.getLength(), Collections.singleton(Unit.CYCLES)).appendUnit(Unit.MONTHS, EastAsianCalendar.getUnitRule(2), Unit.MONTHS.getLength(), Collections.emptySet()).appendUnit(Unit.WEEKS, EastAsianCalendar.getUnitRule(3), Unit.WEEKS.getLength(), Collections.singleton(Unit.DAYS)).appendUnit(Unit.DAYS, EastAsianCalendar.getUnitRule(4), Unit.DAYS.getLength(), Collections.singleton(Unit.WEEKS)).appendExtension((ChronoExtension) new CommonElements.Weekengine(VietnameseCalendar.class, stdIntegerDateElement3, stdIntegerDateElement4, getDefaultWeekmodel())).build();
    }

    private VietnameseCalendar(int i, int i2, EastAsianMonth eastAsianMonth, int i3, long j) {
        super(i, i2, eastAsianMonth, i3, j);
    }

    public static VietnameseCalendar ofTet(int i) {
        return of(EastAsianYear.forGregorian(i), EastAsianMonth.valueOf(1), 1);
    }

    public static VietnameseCalendar of(EastAsianYear eastAsianYear, EastAsianMonth eastAsianMonth, int i) {
        return of(eastAsianYear.getCycle(), eastAsianYear.getYearOfCycle().getNumber(), eastAsianMonth, i);
    }

    public static VietnameseCalendar nowInSystemTime() {
        return (VietnameseCalendar) SystemClock.inLocalView().now(axis());
    }

    public static boolean isValid(EastAsianYear eastAsianYear, EastAsianMonth eastAsianMonth, int i) {
        return CALSYS.isValid(eastAsianYear.getCycle(), eastAsianYear.getYearOfCycle().getNumber(), eastAsianMonth, i);
    }

    public static Weekmodel getDefaultWeekmodel() {
        return Weekmodel.of(new Locale("vi", "VN"));
    }

    public static TimeAxis<Unit, VietnameseCalendar> axis() {
        return ENGINE;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.time4j.engine.TimePoint, net.time4j.engine.ChronoEntity
    public TimeAxis<Unit, VietnameseCalendar> getChronology() {
        return ENGINE;
    }

    @Override // net.time4j.calendar.EastAsianCalendar
    EastAsianCS<VietnameseCalendar> getCalendarSystem() {
        return CALSYS;
    }

    static VietnameseCalendar of(int i, int i2, EastAsianMonth eastAsianMonth, int i3) {
        return new VietnameseCalendar(i, i2, eastAsianMonth, i3, CALSYS.transform(i, i2, eastAsianMonth, i3));
    }

    private Object writeReplace() {
        return new SPX(this);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException {
        throw new InvalidObjectException("Serialization proxy required.");
    }

    public enum Unit implements ChronoUnit {
        CYCLES(1.893415507776E9d),
        YEARS(3.15569251296E7d),
        MONTHS(2551442.8775903997d),
        WEEKS(604800.0d),
        DAYS(86400.0d);

        private final transient double length;

        @Override // net.time4j.engine.ChronoUnit
        public boolean isCalendrical() {
            return true;
        }

        Unit(double d) {
            this.length = d;
        }

        @Override // net.time4j.engine.ChronoUnit
        public double getLength() {
            return this.length;
        }

        public int between(VietnameseCalendar vietnameseCalendar, VietnameseCalendar vietnameseCalendar2) {
            return (int) vietnameseCalendar.until(vietnameseCalendar2, (VietnameseCalendar) this);
        }
    }

    private static class Transformer extends EastAsianCS<VietnameseCalendar> {
        private static final long MIN_LIMIT;
        private static final List<ZonalOffset> OFFSETS;
        private static final long OFFSET_SWITCH_1841;
        private static final long OFFSET_SWITCH_1954;
        private static final long OFFSET_SWITCH_1968;

        private Transformer() {
        }

        static {
            ArrayList arrayList = new ArrayList(5);
            arrayList.add(ZonalOffset.atLongitude(OffsetSign.AHEAD_OF_UTC, 116, 25, 0.0d));
            arrayList.add(ZonalOffset.atLongitude(OffsetSign.AHEAD_OF_UTC, 107, 35, 0.0d));
            arrayList.add(ZonalOffset.ofHours(OffsetSign.AHEAD_OF_UTC, 8));
            arrayList.add(ZonalOffset.ofHours(OffsetSign.AHEAD_OF_UTC, 7));
            OFFSETS = Collections.unmodifiableList(arrayList);
            OFFSET_SWITCH_1841 = PlainDate.of(1841, 1, 1).getDaysSinceEpochUTC();
            OFFSET_SWITCH_1954 = PlainDate.of(1954, 7, 1).getDaysSinceEpochUTC();
            OFFSET_SWITCH_1968 = PlainDate.of(1968, 1, 1).getDaysSinceEpochUTC();
            MIN_LIMIT = PlainDate.of(1813, 2, 1).getDaysSinceEpochUTC();
        }

        @Override // net.time4j.calendar.EastAsianCS, net.time4j.engine.CalendarSystem
        public long getMinimumSinceUTC() {
            return MIN_LIMIT;
        }

        @Override // net.time4j.engine.CalendarSystem
        public List<CalendarEra> getEras() {
            return Collections.emptyList();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // net.time4j.calendar.EastAsianCS
        public VietnameseCalendar create(int i, int i2, EastAsianMonth eastAsianMonth, int i3, long j) {
            return new VietnameseCalendar(i, i2, eastAsianMonth, i3, j);
        }

        @Override // net.time4j.calendar.EastAsianCS
        ZonalOffset getOffset(long j) {
            List<ZonalOffset> list;
            int i;
            if (j < OFFSET_SWITCH_1841) {
                return OFFSETS.get(0);
            }
            if (j < OFFSET_SWITCH_1954) {
                return OFFSETS.get(1);
            }
            if (j < OFFSET_SWITCH_1968) {
                list = OFFSETS;
                i = 2;
            } else {
                list = OFFSETS;
                i = 3;
            }
            return list.get(i);
        }

        @Override // net.time4j.calendar.EastAsianCS
        int[] getLeapMonths() {
            return VietnameseCalendar.LEAP_MONTHS;
        }

        @Override // net.time4j.calendar.EastAsianCS
        boolean isValid(int i, int i2, EastAsianMonth eastAsianMonth, int i3) {
            if (i < 75) {
                return false;
            }
            if (i != 75 || i2 >= 10) {
                return super.isValid(i, i2, eastAsianMonth, i3);
            }
            return false;
        }
    }

    private static class Merger extends AbstractMergerEA<VietnameseCalendar> {
        @Override // net.time4j.calendar.AbstractMergerEA, net.time4j.engine.ChronoMerger
        public /* bridge */ /* synthetic */ Object createFrom(ChronoEntity chronoEntity, AttributeQuery attributeQuery, boolean z, boolean z2) {
            return createFrom((ChronoEntity<?>) chronoEntity, attributeQuery, z, z2);
        }

        @Override // net.time4j.calendar.AbstractMergerEA, net.time4j.engine.ChronoMerger
        public /* bridge */ /* synthetic */ EastAsianCalendar createFrom(ChronoEntity chronoEntity, AttributeQuery attributeQuery, boolean z, boolean z2) {
            return createFrom((ChronoEntity<?>) chronoEntity, attributeQuery, z, z2);
        }

        Merger() {
            super(VietnameseCalendar.class);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // net.time4j.calendar.AbstractMergerEA, net.time4j.engine.ChronoMerger
        public VietnameseCalendar createFrom(ChronoEntity<?> chronoEntity, AttributeQuery attributeQuery, boolean z, boolean z2) {
            EastAsianYear forGregorian;
            int i = chronoEntity.getInt(CommonElements.RELATED_GREGORIAN_YEAR);
            if (i == Integer.MIN_VALUE) {
                if (chronoEntity.contains(VietnameseCalendar.YEAR_OF_CYCLE)) {
                    CyclicYear cyclicYear = (CyclicYear) chronoEntity.get(VietnameseCalendar.YEAR_OF_CYCLE);
                    int i2 = chronoEntity.getInt(VietnameseCalendar.CYCLE);
                    if (i2 != Integer.MIN_VALUE) {
                        forGregorian = cyclicYear.inCycle(i2);
                    }
                }
                forGregorian = null;
            } else {
                forGregorian = EastAsianYear.forGregorian(i);
            }
            if (forGregorian == null) {
                chronoEntity.with((ChronoElement<ValidationElement>) ValidationElement.ERROR_MESSAGE, (ValidationElement) "Cannot determine East Asian year.");
                return null;
            }
            if (chronoEntity.contains(VietnameseCalendar.MONTH_OF_YEAR)) {
                EastAsianMonth eastAsianMonth = (EastAsianMonth) chronoEntity.get(VietnameseCalendar.MONTH_OF_YEAR);
                int i3 = chronoEntity.getInt(VietnameseCalendar.DAY_OF_MONTH);
                if (i3 != Integer.MIN_VALUE) {
                    return VietnameseCalendar.of(forGregorian, eastAsianMonth, i3);
                }
            } else {
                int i4 = chronoEntity.getInt(VietnameseCalendar.DAY_OF_YEAR);
                if (i4 != Integer.MIN_VALUE && i4 >= 1) {
                    return (VietnameseCalendar) VietnameseCalendar.of(forGregorian, EastAsianMonth.valueOf(1), 1).plus(i4 - 1, Unit.DAYS);
                }
            }
            return null;
        }
    }

    private static class SPX implements Externalizable {
        private static final int VIETNAMESE = 16;
        private static final long serialVersionUID = 1;
        private transient Object obj;

        public SPX() {
        }

        SPX(Object obj) {
            this.obj = obj;
        }

        @Override // java.io.Externalizable
        public void writeExternal(ObjectOutput objectOutput) throws IOException {
            objectOutput.writeByte(16);
            writeVietnamese(objectOutput);
        }

        @Override // java.io.Externalizable
        public void readExternal(ObjectInput objectInput) throws IOException {
            if (objectInput.readByte() == 16) {
                this.obj = readVietnamese(objectInput);
                return;
            }
            throw new InvalidObjectException("Unknown calendar type.");
        }

        private Object readResolve() throws ObjectStreamException {
            return this.obj;
        }

        private void writeVietnamese(ObjectOutput objectOutput) throws IOException {
            EastAsianCalendar eastAsianCalendar = (EastAsianCalendar) this.obj;
            objectOutput.writeByte(eastAsianCalendar.getCycle());
            objectOutput.writeByte(eastAsianCalendar.getYear().getNumber());
            objectOutput.writeByte(eastAsianCalendar.getMonth().getNumber());
            objectOutput.writeBoolean(eastAsianCalendar.getMonth().isLeap());
            objectOutput.writeByte(eastAsianCalendar.getDayOfMonth());
        }

        private VietnameseCalendar readVietnamese(ObjectInput objectInput) throws IOException {
            byte readByte = objectInput.readByte();
            byte readByte2 = objectInput.readByte();
            byte readByte3 = objectInput.readByte();
            boolean readBoolean = objectInput.readBoolean();
            byte readByte4 = objectInput.readByte();
            EastAsianMonth valueOf = EastAsianMonth.valueOf(readByte3);
            if (readBoolean) {
                valueOf = valueOf.withLeap();
            }
            return VietnameseCalendar.of(readByte, readByte2, valueOf, readByte4);
        }
    }
}

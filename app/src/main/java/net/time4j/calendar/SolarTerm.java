package net.time4j.calendar;

import java.text.ParseException;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import net.time4j.Moment;
import net.time4j.PlainDate;
import net.time4j.base.MathUtils;
import net.time4j.calendar.astro.JulianDay;
import net.time4j.calendar.astro.StdSolarCalculator;
import net.time4j.engine.ChronoOperator;

/* loaded from: classes3.dex */
public enum SolarTerm {
    MINOR_01_LICHUN_315,
    MAJOR_01_YUSHUI_330,
    MINOR_02_JINGZHE_345,
    MAJOR_02_CHUNFEN_000,
    MINOR_03_QINGMING_015,
    MAJOR_03_GUYU_030,
    MINOR_04_LIXIA_045,
    MAJOR_04_XIAOMAN_060,
    MINOR_05_MANGZHONG_075,
    MAJOR_05_XIAZHI_090,
    MINOR_06_XIAOSHU_105,
    MAJOR_06_DASHU_120,
    MINOR_07_LIQIU_135,
    MAJOR_07_CHUSHU_150,
    MINOR_08_BAILU_165,
    MAJOR_08_QIUFEN_180,
    MINOR_09_HANLU_195,
    MAJOR_09_SHUANGJIANG_210,
    MINOR_10_LIDONG_225,
    MAJOR_10_XIAOXUE_240,
    MINOR_11_DAXUE_255,
    MAJOR_11_DONGZHI_270,
    MINOR_12_XIAOHAN_285,
    MAJOR_12_DAHAN_300;

    private static final double MEAN_TROPICAL_YEAR = 365.242189d;
    private static final SolarTerm[] ENUMS = values();
    private static final String[] SIMPLE = {"lichun", "yushui", "jingzhe", "chunfen", "qingming", "guyu", "lixia", "xiaoman", "mangzhong", "xiazhi", "xiaoshu", "dashu", "liqiu", "chushu", "bailu", "qiufen", "hanlu", "shuangjiang", "lidong", "xiaoxue", "daxue", "dongzhi", "xiaohan", "dahan"};
    private static final String[] TRANSSCRIPTION = {"lìchūn", "yǔshuǐ", "jīngzhé", "chūnfēn", "qīngmíng", "gǔyǔ", "lìxià", "xiǎomǎn", "mángzhòng", "xiàzhì", "xiǎoshǔ", "dàshǔ", "lìqiū", "chǔshǔ", "báilù", "qiūfēn", "hánlù", "shuāngjiàng", "lìdōng", "xiǎoxuě", "dàxuě", "dōngzhì", "xiǎohán", "dàhán"};
    private static final String[] CHINESE_SIMPLIFIED = {"立春", "雨水", "惊蛰", "春分", "清明", "谷雨", "立夏", "小满", "芒种", "夏至", "小暑", "大暑", "立秋", "处暑", "白露", "秋分", "寒露", "霜降", "立冬", "小雪", "大雪", "冬至", "小寒", "大寒"};
    private static final String[] CHINESE_TRADITIONAL = {"立春", "雨水", "驚蟄", "春分", "清明", "穀雨", "立夏", "小滿", "芒種", "夏至", "小暑", "大暑", "立秋", "處暑", "白露", "秋分", "寒露", "霜降", "立冬", "小雪", "大雪", "冬至", "小寒", "大寒"};
    private static final String[] KOREAN = {"입춘", "우수", "경칩", "춘분", "청명", "곡우", "입하", "소만", "망종", "하지", "소서", "대서", "입추", "처서", "백로", "추분", "한로", "상강", "입동", "소설", "대설", "동지", "소한", "대한"};
    private static final String[] VIETNAMESE = {"Lập xuân", "Vũ thủy", "Kinh trập", "Xuân phân", "Thanh minh", "Cốc vũ", "Lập hạ", "Tiểu mãn", "Mang chủng", "Hạ chí", "Tiểu thử", "Đại thử", "Lập thu", "Xử thử", "Bạch lộ", "Thu phân", "Hàn lộ", "Sương giáng", "Lập đông", "Tiểu tuyết", "Đại tuyết", "Đông chí", "Tiểu hàn", "Đại hàn"};
    private static final String[] JAPANESE = {"立春", "雨水", "啓蟄", "春分", "清明", "穀雨", "立夏", "小満", "芒種", "夏至", "小暑", "大暑", "立秋", "処暑", "白露", "秋分", "寒露", "霜降", "立冬", "小雪", "大雪", "冬至", "小寒", "大寒"};

    public static SolarTerm ofMajor(int i) {
        if (i < 1 || i > 12) {
            throw new IllegalArgumentException("Out of range: " + i);
        }
        return ENUMS[(i * 2) - 1];
    }

    public static SolarTerm ofMinor(int i) {
        if (i < 1 || i > 12) {
            throw new IllegalArgumentException("Out of range: " + i);
        }
        return ENUMS[(i - 1) * 2];
    }

    public static SolarTerm of(Moment moment) {
        return ENUMS[(((int) Math.floor(solarLongitude(JulianDay.ofEphemerisTime(moment).getValue()) / 15.0d)) + 3) % 24];
    }

    public int getIndex() {
        return (ordinal() / 2) + 1;
    }

    public boolean isMajor() {
        return ordinal() % 2 == 1;
    }

    public boolean isMinor() {
        return ordinal() % 2 == 0;
    }

    public int getSolarLongitude() {
        return ((ordinal() + 21) % 24) * 15;
    }

    public SolarTerm roll(int i) {
        return ENUMS[MathUtils.floorModulo(ordinal() + i, 24)];
    }

    public <D extends EastAsianCalendar<?, D>> D onOrAfter(D d) {
        EastAsianCS calendarSystem = d.getCalendarSystem();
        long daysSinceEpochUTC = d.getDaysSinceEpochUTC();
        return (D) calendarSystem.transform(atOrAfter(calendarSystem.midnight(daysSinceEpochUTC)).toZonalTimestamp(calendarSystem.getOffset(daysSinceEpochUTC)).toDate().getDaysSinceEpochUTC());
    }

    public <D extends EastAsianCalendar<?, D>> ChronoOperator<D> sinceLichun() {
        return (ChronoOperator<D>) new ChronoOperator<D>() { // from class: net.time4j.calendar.SolarTerm.1
            /* JADX WARN: Incorrect return type in method signature: (TD;)TD; */
            @Override // net.time4j.engine.ChronoOperator
            public EastAsianCalendar apply(EastAsianCalendar eastAsianCalendar) {
                return SolarTerm.this.onOrAfter(SolarTerm.MINOR_01_LICHUN_315.onOrAfter(eastAsianCalendar.getCalendarSystem().transform(PlainDate.of(((PlainDate) eastAsianCalendar.transform(PlainDate.class)).getYear(), 1, 1).getDaysSinceEpochUTC())));
            }
        };
    }

    public <D extends EastAsianCalendar<?, D>> ChronoOperator<D> sinceNewYear() {
        return (ChronoOperator<D>) new ChronoOperator<D>() { // from class: net.time4j.calendar.SolarTerm.2
            /* JADX WARN: Incorrect return type in method signature: (TD;)TD; */
            @Override // net.time4j.engine.ChronoOperator
            public EastAsianCalendar apply(EastAsianCalendar eastAsianCalendar) {
                return SolarTerm.this.onOrAfter(eastAsianCalendar.getCalendarSystem().transform(SolarTerm.newYear(eastAsianCalendar)));
            }
        };
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <D extends EastAsianCalendar<?, D>> List<D> list(int i, Class<D> cls) {
        ArrayList arrayList = new ArrayList(24);
        EastAsianCalendar onOrAfter = MINOR_01_LICHUN_315.onOrAfter((EastAsianCalendar) PlainDate.of(i, 1, 1).transform(cls));
        arrayList.add(onOrAfter);
        SolarTerm[] values = values();
        for (int i2 = 1; i2 < 24; i2++) {
            onOrAfter = values[i2].onOrAfter(onOrAfter);
            arrayList.add(onOrAfter);
        }
        return arrayList;
    }

    public String getDisplayName(Locale locale) {
        return getTextForms(locale)[ordinal()];
    }

    public static SolarTerm parse(CharSequence charSequence, Locale locale) throws ParseException {
        SolarTerm parse = parse(charSequence, locale, new ParsePosition(0));
        if (parse != null) {
            return parse;
        }
        throw new ParseException("Cannot parse: " + ((Object) charSequence), 0);
    }

    static SolarTerm parse(CharSequence charSequence, Locale locale, ParsePosition parsePosition) {
        String[] textForms = getTextForms(locale);
        boolean isEmpty = locale.getLanguage().isEmpty();
        int index = parsePosition.getIndex();
        for (int i = 0; i < textForms.length; i++) {
            String str = textForms[i];
            String charSequence2 = charSequence.subSequence(index, Math.min(charSequence.length(), str.length() + index)).toString();
            if ((isEmpty && charSequence2.equalsIgnoreCase(str)) || charSequence2.equals(str)) {
                parsePosition.setIndex(parsePosition.getIndex() + str.length());
                return ENUMS[i];
            }
        }
        if (locale.getLanguage().isEmpty() || textForms != TRANSSCRIPTION) {
            parsePosition.setErrorIndex(parsePosition.getIndex());
            return null;
        }
        return parse(charSequence, Locale.ROOT, parsePosition);
    }

    static double solarLongitude(double d) {
        return StdSolarCalculator.TIME4J.getFeature(d, "solar-longitude");
    }

    private static double modulo360(double d) {
        return d - (Math.floor(d / 360.0d) * 360.0d);
    }

    private static String[] getTextForms(Locale locale) {
        if (locale.getLanguage().equals("zh")) {
            return locale.getCountry().equals("TW") ? CHINESE_TRADITIONAL : CHINESE_SIMPLIFIED;
        }
        if (locale.getLanguage().equals("ko")) {
            return KOREAN;
        }
        if (locale.getLanguage().equals("vi")) {
            return VIETNAMESE;
        }
        if (locale.getLanguage().equals("ja")) {
            return JAPANESE;
        }
        return locale.getLanguage().isEmpty() ? SIMPLE : TRANSSCRIPTION;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <D extends EastAsianCalendar<?, ?>> long newYear(D d) {
        return d.getCalendarSystem().newYear(d.getCycle(), d.getYear().getNumber());
    }

    private Moment atOrAfter(Moment moment) {
        double solarLongitude = getSolarLongitude();
        double value = JulianDay.ofEphemerisTime(moment).getValue();
        double modulo360 = ((modulo360(solarLongitude - solarLongitude(value)) * MEAN_TROPICAL_YEAR) / 360.0d) + value;
        double max = Math.max(value, modulo360 - 5.0d);
        double d = modulo360 + 5.0d;
        while (true) {
            double d2 = (max + d) / 2.0d;
            if (d - max < 1.0E-5d) {
                return JulianDay.ofEphemerisTime(d2).toMoment();
            }
            if (modulo360(solarLongitude(d2) - solarLongitude) < 180.0d) {
                d = d2;
            } else {
                max = d2;
            }
        }
    }
}

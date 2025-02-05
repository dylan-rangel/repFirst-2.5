package net.time4j.calendar;

import java.util.Locale;
import net.time4j.engine.CalendarEra;
import net.time4j.format.TextWidth;

/* loaded from: classes3.dex */
public enum ChineseEra implements CalendarEra {
    QING_SHUNZHI_1644_1662,
    QING_KANGXI_1662_1723,
    QING_YONGZHENG_1723_1736,
    QING_QIANLONG_1736_1796,
    QING_JIAQING_1796_1821,
    QING_DAOGUANG_1821_1851,
    QING_XIANFENG_1851_1862,
    QING_TONGZHI_1862_1875,
    QING_GUANGXU_1875_1909,
    QING_XUANTONG_1909_1912,
    YELLOW_EMPEROR;

    public String getDisplayName(Locale locale) {
        return getDisplayName(locale, TextWidth.WIDE);
    }

    public String getDisplayName(Locale locale, TextWidth textWidth) {
        String language = locale.getLanguage();
        switch (AnonymousClass1.$SwitchMap$net$time4j$calendar$ChineseEra[ordinal()]) {
            case 1:
                return language.equals("zh") ? "順治" : language.isEmpty() ? "Shunzhi" : "Shùnzhì";
            case 2:
                return language.equals("zh") ? "康熙" : language.isEmpty() ? "Kangxi" : "Kāngxī";
            case 3:
                return language.equals("zh") ? "雍正" : language.isEmpty() ? "Yongzheng" : "Yōngzhèng";
            case 4:
                return language.equals("zh") ? "乾隆" : language.isEmpty() ? "Qianlong" : "Qiánlóng";
            case 5:
                return language.equals("zh") ? "嘉慶" : language.isEmpty() ? "Jiaqing" : "Jiāqìng";
            case 6:
                return language.equals("zh") ? "道光" : language.isEmpty() ? "Daoguang" : "Dàoguāng";
            case 7:
                return language.equals("zh") ? "咸豐" : language.isEmpty() ? "Xianfeng" : "Xiánfēng";
            case 8:
                return language.equals("zh") ? "同治" : language.isEmpty() ? "Tongzhi" : "Tóngzhì";
            case 9:
                return language.equals("zh") ? "光緒" : language.isEmpty() ? "Guangxu" : "Guāngxù";
            case 10:
                return language.equals("zh") ? "宣統" : language.isEmpty() ? "Xuantong" : "Xuāntǒng";
            case 11:
                return language.equals("zh") ? "黄帝紀年" : textWidth == TextWidth.WIDE ? language.isEmpty() ? "Anno Huangdi" : "Anno Huángdì" : textWidth == TextWidth.NARROW ? "H" : language.isEmpty() ? "Huangdi" : "Huángdì";
            default:
                throw new UnsupportedOperationException("Not yet implemented: " + name());
        }
    }

    /* renamed from: net.time4j.calendar.ChineseEra$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$time4j$calendar$ChineseEra;

        static {
            int[] iArr = new int[ChineseEra.values().length];
            $SwitchMap$net$time4j$calendar$ChineseEra = iArr;
            try {
                iArr[ChineseEra.QING_SHUNZHI_1644_1662.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$net$time4j$calendar$ChineseEra[ChineseEra.QING_KANGXI_1662_1723.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$net$time4j$calendar$ChineseEra[ChineseEra.QING_YONGZHENG_1723_1736.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$net$time4j$calendar$ChineseEra[ChineseEra.QING_QIANLONG_1736_1796.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$net$time4j$calendar$ChineseEra[ChineseEra.QING_JIAQING_1796_1821.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$net$time4j$calendar$ChineseEra[ChineseEra.QING_DAOGUANG_1821_1851.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$net$time4j$calendar$ChineseEra[ChineseEra.QING_XIANFENG_1851_1862.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$net$time4j$calendar$ChineseEra[ChineseEra.QING_TONGZHI_1862_1875.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$net$time4j$calendar$ChineseEra[ChineseEra.QING_GUANGXU_1875_1909.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$net$time4j$calendar$ChineseEra[ChineseEra.QING_XUANTONG_1909_1912.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$net$time4j$calendar$ChineseEra[ChineseEra.YELLOW_EMPEROR.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
        }
    }

    public boolean isQingDynasty() {
        return ordinal() <= QING_XUANTONG_1909_1912.ordinal();
    }

    int getMinYearOfEra() {
        return this == YELLOW_EMPEROR ? 4343 : 1;
    }

    int getMaxYearOfEra() {
        switch (AnonymousClass1.$SwitchMap$net$time4j$calendar$ChineseEra[ordinal()]) {
            case 1:
                return 18;
            case 2:
                return 61;
            case 3:
                return 13;
            case 4:
                return 60;
            case 5:
                return 25;
            case 6:
                return 30;
            case 7:
                return 11;
            case 8:
                return 13;
            case 9:
                return 34;
            case 10:
                return 3;
            case 11:
                return 5697;
            default:
                throw new UnsupportedOperationException("Not yet implemented: " + name());
        }
    }

    int getStartAsGregorianYear() {
        switch (AnonymousClass1.$SwitchMap$net$time4j$calendar$ChineseEra[ordinal()]) {
            case 1:
                return 1644;
            case 2:
                return 1662;
            case 3:
                return 1723;
            case 4:
                return 1736;
            case 5:
                return 1796;
            case 6:
                return 1821;
            case 7:
                return 1851;
            case 8:
                return 1862;
            case 9:
                return 1875;
            case 10:
                return 1909;
            case 11:
                return -2697;
            default:
                throw new UnsupportedOperationException("Not yet implemented: " + name());
        }
    }
}

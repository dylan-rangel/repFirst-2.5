package net.time4j;

import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Locale;
import net.time4j.base.GregorianDate;
import net.time4j.base.GregorianMath;
import net.time4j.engine.ChronoCondition;
import net.time4j.engine.ChronoOperator;
import net.time4j.format.CalendarText;
import net.time4j.format.OutputContext;
import net.time4j.format.TextWidth;

/* loaded from: classes3.dex */
public enum Month implements ChronoCondition<GregorianDate>, ChronoOperator<PlainDate> {
    JANUARY,
    FEBRUARY,
    MARCH,
    APRIL,
    MAY,
    JUNE,
    JULY,
    AUGUST,
    SEPTEMBER,
    OCTOBER,
    NOVEMBER,
    DECEMBER;

    private static final Month[] ENUMS = values();

    public static Month valueOf(int i) {
        if (i < 1 || i > 12) {
            throw new IllegalArgumentException("Out of range: " + i);
        }
        return ENUMS[i - 1];
    }

    public int getValue() {
        return ordinal() + 1;
    }

    public Quarter getQuarterOfYear() {
        switch (AnonymousClass1.$SwitchMap$net$time4j$Month[ordinal()]) {
            case 1:
            case 2:
            case 3:
                return Quarter.Q1;
            case 4:
            case 5:
            case 6:
                return Quarter.Q2;
            case 7:
            case 8:
            case 9:
                return Quarter.Q3;
            default:
                return Quarter.Q4;
        }
    }

    /* renamed from: net.time4j.Month$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$time4j$Month;
        static final /* synthetic */ int[] $SwitchMap$net$time4j$Quarter;

        static {
            int[] iArr = new int[Quarter.values().length];
            $SwitchMap$net$time4j$Quarter = iArr;
            try {
                iArr[Quarter.Q1.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$net$time4j$Quarter[Quarter.Q2.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$net$time4j$Quarter[Quarter.Q3.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            int[] iArr2 = new int[Month.values().length];
            $SwitchMap$net$time4j$Month = iArr2;
            try {
                iArr2[Month.JANUARY.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$net$time4j$Month[Month.FEBRUARY.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$net$time4j$Month[Month.MARCH.ordinal()] = 3;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$net$time4j$Month[Month.APRIL.ordinal()] = 4;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$net$time4j$Month[Month.MAY.ordinal()] = 5;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$net$time4j$Month[Month.JUNE.ordinal()] = 6;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$net$time4j$Month[Month.JULY.ordinal()] = 7;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$net$time4j$Month[Month.AUGUST.ordinal()] = 8;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$net$time4j$Month[Month.SEPTEMBER.ordinal()] = 9;
            } catch (NoSuchFieldError unused12) {
            }
        }
    }

    public static Month atStartOfQuarterYear(Quarter quarter) {
        int i = AnonymousClass1.$SwitchMap$net$time4j$Quarter[quarter.ordinal()];
        if (i == 1) {
            return JANUARY;
        }
        if (i == 2) {
            return APRIL;
        }
        if (i == 3) {
            return JULY;
        }
        return OCTOBER;
    }

    public static Month atEndOfQuarterYear(Quarter quarter) {
        int i = AnonymousClass1.$SwitchMap$net$time4j$Quarter[quarter.ordinal()];
        if (i == 1) {
            return MARCH;
        }
        if (i == 2) {
            return JUNE;
        }
        if (i == 3) {
            return SEPTEMBER;
        }
        return DECEMBER;
    }

    public int getLength(int i) {
        return GregorianMath.getLengthOfMonth(i, getValue());
    }

    public Month next() {
        return roll(1);
    }

    public Month previous() {
        return roll(-1);
    }

    public Month roll(int i) {
        return valueOf(((ordinal() + ((i % 12) + 12)) % 12) + 1);
    }

    public String getDisplayName(Locale locale) {
        return getDisplayName(locale, TextWidth.WIDE, OutputContext.FORMAT);
    }

    public String getDisplayName(Locale locale, TextWidth textWidth, OutputContext outputContext) {
        return CalendarText.getIsoInstance(locale).getStdMonths(textWidth, outputContext).print(this);
    }

    public static Month parse(CharSequence charSequence, Locale locale, TextWidth textWidth, OutputContext outputContext) throws ParseException {
        ParsePosition parsePosition = new ParsePosition(0);
        Month month = (Month) CalendarText.getIsoInstance(locale).getStdMonths(textWidth, outputContext).parse(charSequence, parsePosition, Month.class);
        if (month != null) {
            return month;
        }
        throw new ParseException("Cannot parse: " + ((Object) charSequence), parsePosition.getErrorIndex());
    }

    @Override // net.time4j.engine.ChronoCondition
    public boolean test(GregorianDate gregorianDate) {
        return gregorianDate.getMonth() == getValue();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.time4j.engine.ChronoOperator
    public PlainDate apply(PlainDate plainDate) {
        return (PlainDate) plainDate.with(PlainDate.MONTH_OF_YEAR, (NavigableElement<Month>) this);
    }
}

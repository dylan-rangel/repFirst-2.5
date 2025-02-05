package net.time4j.history;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectStreamException;
import java.text.ParsePosition;
import java.util.List;
import java.util.Locale;
import net.time4j.Month;
import net.time4j.PlainDate;
import net.time4j.base.GregorianDate;
import net.time4j.base.MathUtils;
import net.time4j.engine.AttributeQuery;
import net.time4j.engine.BasicElement;
import net.time4j.engine.CalendarDays;
import net.time4j.engine.ChronoDisplay;
import net.time4j.engine.ChronoElement;
import net.time4j.engine.ChronoEntity;
import net.time4j.engine.ChronoException;
import net.time4j.engine.Chronology;
import net.time4j.engine.ElementRule;
import net.time4j.format.Attributes;
import net.time4j.format.CalendarText;
import net.time4j.format.Leniency;
import net.time4j.format.NumberSystem;
import net.time4j.format.OutputContext;
import net.time4j.format.TextAccessor;
import net.time4j.format.TextWidth;
import net.time4j.format.internal.DualFormatElement;
import net.time4j.history.internal.StdHistoricalElement;
import okhttp3.internal.http2.Http2Connection;

/* loaded from: classes3.dex */
final class HistoricIntegerElement extends StdHistoricalElement implements DualFormatElement {
    static final int CENTURY_INDEX = 8;
    static final int DAY_OF_MONTH_INDEX = 4;
    static final int DAY_OF_YEAR_INDEX = 5;
    static final int MONTH_INDEX = 3;
    static final int YEAR_AFTER_INDEX = 6;
    static final int YEAR_BEFORE_INDEX = 7;
    static final int YEAR_OF_ERA_INDEX = 2;
    private static final long serialVersionUID = -6283098762945747308L;
    private final ChronoHistory history;
    private final transient int index;

    @Override // net.time4j.history.internal.StdHistoricalElement, net.time4j.engine.BasicElement
    protected boolean isSingleton() {
        return false;
    }

    HistoricIntegerElement(char c, int i, int i2, ChronoHistory chronoHistory, int i3) {
        super(toName(i3), c, i, i2);
        this.history = chronoHistory;
        this.index = i3;
    }

    @Override // net.time4j.format.TextElement
    public void print(ChronoDisplay chronoDisplay, Appendable appendable, AttributeQuery attributeQuery) throws IOException {
        char c;
        char charAt;
        NumberSystem numberSystem = (NumberSystem) attributeQuery.get(Attributes.NUMBER_SYSTEM, NumberSystem.ARABIC);
        if (attributeQuery.contains(Attributes.ZERO_DIGIT)) {
            charAt = ((Character) attributeQuery.get(Attributes.ZERO_DIGIT)).charValue();
        } else {
            if (!numberSystem.isDecimal()) {
                c = '0';
                print(chronoDisplay, appendable, attributeQuery, numberSystem, c, 1, 10);
            }
            charAt = numberSystem.getDigits().charAt(0);
        }
        c = charAt;
        print(chronoDisplay, appendable, attributeQuery, numberSystem, c, 1, 10);
    }

    @Override // net.time4j.format.internal.DualFormatElement
    public void print(ChronoDisplay chronoDisplay, Appendable appendable, AttributeQuery attributeQuery, NumberSystem numberSystem, char c, int i, int i2) throws IOException {
        HistoricDate historicDate;
        int yearOfEra;
        if (this.index == 5) {
            appendable.append(String.valueOf(chronoDisplay.get(this.history.dayOfYear())));
            return;
        }
        if (chronoDisplay instanceof GregorianDate) {
            historicDate = this.history.convert(PlainDate.from((GregorianDate) chronoDisplay));
        } else {
            historicDate = (HistoricDate) chronoDisplay.get(this.history.date());
        }
        int i3 = this.index;
        if (i3 != 2) {
            if (i3 != 3) {
                if (i3 == 4) {
                    appendable.append(String.valueOf(historicDate.getDayOfMonth()));
                    return;
                }
                throw new ChronoException("Not printable as text: " + name());
            }
            int intValue = ((Integer) attributeQuery.get(DualFormatElement.COUNT_OF_PATTERN_SYMBOLS, 0)).intValue();
            int month = historicDate.getMonth();
            if (intValue == 0) {
                appendable.append(monthAccessor(attributeQuery, (OutputContext) attributeQuery.get(Attributes.OUTPUT_CONTEXT, OutputContext.FORMAT)).print(Month.valueOf(month)));
                return;
            }
            String numeral = numberSystem.toNumeral(month);
            if (numberSystem.isDecimal()) {
                numeral = pad(numeral, intValue, c);
            }
            appendable.append(numeral);
            return;
        }
        NewYearStrategy newYearStrategy = this.history.getNewYearStrategy();
        int yearOfEra2 = historicDate.getYearOfEra();
        String str = null;
        if (!NewYearStrategy.DEFAULT.equals(newYearStrategy) && (yearOfEra = historicDate.getYearOfEra(newYearStrategy)) != yearOfEra2) {
            if (attributeQuery.get(ChronoHistory.YEAR_DEFINITION, YearDefinition.DUAL_DATING) == YearDefinition.DUAL_DATING) {
                str = dual(numberSystem, c, yearOfEra, yearOfEra2, i);
            } else {
                yearOfEra2 = yearOfEra;
            }
        }
        if (str == null) {
            if (numberSystem.isDecimal()) {
                str = pad(numberSystem.toNumeral(yearOfEra2), i, c);
            } else {
                str = numberSystem.toNumeral(yearOfEra2);
            }
        }
        if (numberSystem.isDecimal()) {
            char charAt = numberSystem.getDigits().charAt(0);
            if (c != charAt) {
                StringBuilder sb = new StringBuilder();
                int length = str.length();
                for (int i4 = 0; i4 < length; i4++) {
                    char charAt2 = str.charAt(i4);
                    if (numberSystem.contains(charAt2)) {
                        sb.append((char) (charAt2 + (c - charAt)));
                    } else {
                        sb.append(charAt2);
                    }
                }
                str = sb.toString();
            }
            checkLength(str, i2);
        }
        appendable.append(str);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.time4j.format.TextElement
    public Integer parse(CharSequence charSequence, ParsePosition parsePosition, AttributeQuery attributeQuery) {
        return parse(charSequence, parsePosition, attributeQuery, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x00bc  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x016d  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0171  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x00bf  */
    @Override // net.time4j.format.internal.DualFormatElement
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Integer parse(java.lang.CharSequence r17, java.text.ParsePosition r18, net.time4j.engine.AttributeQuery r19, net.time4j.engine.ChronoEntity<?> r20) {
        /*
            Method dump skipped, instructions count: 401
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: net.time4j.history.HistoricIntegerElement.parse(java.lang.CharSequence, java.text.ParsePosition, net.time4j.engine.AttributeQuery, net.time4j.engine.ChronoEntity):java.lang.Integer");
    }

    @Override // net.time4j.engine.BasicElement
    protected <T extends ChronoEntity<T>> ElementRule<T, Integer> derive(Chronology<T> chronology) {
        if (chronology.isRegistered(PlainDate.COMPONENT)) {
            return new Rule(this.index, this.history);
        }
        return null;
    }

    @Override // net.time4j.engine.BasicElement
    protected boolean doEquals(BasicElement<?> basicElement) {
        return this.history.equals(((HistoricIntegerElement) basicElement).history);
    }

    private int getAncientYear(int i, int i2, int i3) {
        if (i2 < 0) {
            return Integer.MAX_VALUE;
        }
        if (i2 >= 100 || i < 100) {
            return Integer.MAX_VALUE;
        }
        int i4 = i2 < 10 ? 10 : 100;
        if (Math.abs(i2 - MathUtils.floorModulo(i, i4)) <= i3) {
            return (MathUtils.floorDivide(i, i4) * i4) + i2;
        }
        return Integer.MAX_VALUE;
    }

    private String dual(NumberSystem numberSystem, char c, int i, int i2, int i3) {
        StringBuilder sb = new StringBuilder();
        sb.append(numberSystem.toNumeral(i));
        sb.append('/');
        if (numberSystem.isDecimal() && i2 >= 100 && MathUtils.floorDivide(i, 100) == MathUtils.floorDivide(i2, 100)) {
            int floorModulo = MathUtils.floorModulo(i2, 100);
            if (floorModulo < 10) {
                sb.append(c);
            }
            sb.append(numberSystem.toNumeral(floorModulo));
        } else {
            sb.append(numberSystem.toNumeral(i2));
        }
        if (numberSystem.isDecimal()) {
            return pad(sb.toString(), i3, c);
        }
        return sb.toString();
    }

    private static String pad(String str, int i, char c) {
        int length = str.length();
        if (i <= length) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        int i2 = i - length;
        for (int i3 = 0; i3 < i2; i3++) {
            sb.append(c);
        }
        sb.append(str);
        return sb.toString();
    }

    private void checkLength(String str, int i) {
        if (str.length() <= i) {
            return;
        }
        throw new IllegalArgumentException("Element " + name() + " cannot be printed as the formatted value " + str + " exceeds the maximum width of " + i + ".");
    }

    private TextAccessor monthAccessor(AttributeQuery attributeQuery, OutputContext outputContext) {
        return CalendarText.getIsoInstance((Locale) attributeQuery.get(Attributes.LANGUAGE, Locale.ROOT)).getStdMonths((TextWidth) attributeQuery.get(Attributes.TEXT_WIDTH, TextWidth.WIDE), outputContext);
    }

    private static int parseNum(NumberSystem numberSystem, char c, CharSequence charSequence, int i, ParsePosition parsePosition, Leniency leniency) {
        int i2;
        boolean z;
        int charAt;
        int i3 = 0;
        long j = 0;
        if (numberSystem.isDecimal()) {
            if (numberSystem == NumberSystem.ARABIC && charSequence.charAt(i) == '-') {
                i2 = i + 1;
                z = true;
            } else {
                i2 = i;
                z = false;
            }
            char charAt2 = leniency.isStrict() ? (char) 0 : numberSystem.getDigits().charAt(0);
            int min = Math.min(i2 + 9, charSequence.length());
            int i4 = i2;
            while (i2 < min) {
                int charAt3 = charSequence.charAt(i2) - c;
                if (charAt3 >= 0 && charAt3 <= 9) {
                    j = (j * 10) + charAt3;
                    i4++;
                } else {
                    if (charAt2 == 0 || c == charAt2 || (charAt = charSequence.charAt(i2) - charAt2) < 0 || charAt > 9) {
                        break;
                    }
                    j = (j * 10) + charAt;
                    i4++;
                    c = charAt2;
                }
                i2++;
            }
            if (j > 2147483647L) {
                parsePosition.setErrorIndex(i);
                return Integer.MIN_VALUE;
            }
            if (z) {
                if (i4 != i + 1) {
                    j = MathUtils.safeNegate(j);
                }
            }
            i = i4;
        } else {
            int length = charSequence.length();
            for (int i5 = i; i5 < length && numberSystem.contains(charSequence.charAt(i5)); i5++) {
                i3++;
            }
            if (i3 > 0) {
                int i6 = i3 + i;
                j = numberSystem.toInteger(charSequence.subSequence(i, i6).toString(), leniency);
                i = i6;
            }
        }
        parsePosition.setIndex(i);
        return (int) j;
    }

    private static String toName(int i) {
        switch (i) {
            case 2:
                return "YEAR_OF_ERA";
            case 3:
                return "HISTORIC_MONTH";
            case 4:
                return "HISTORIC_DAY_OF_MONTH";
            case 5:
                return "HISTORIC_DAY_OF_YEAR";
            case 6:
                return "YEAR_AFTER";
            case 7:
                return "YEAR_BEFORE";
            case 8:
                return "CENTURY_OF_ERA";
            default:
                throw new UnsupportedOperationException("Unknown element index: " + i);
        }
    }

    private Object readResolve() throws ObjectStreamException {
        String name = name();
        if (name.equals("YEAR_OF_ERA")) {
            return this.history.yearOfEra();
        }
        if (name.equals("HISTORIC_MONTH")) {
            return this.history.month();
        }
        if (name.equals("HISTORIC_DAY_OF_MONTH")) {
            return this.history.dayOfMonth();
        }
        if (name.equals("HISTORIC_DAY_OF_YEAR")) {
            return this.history.dayOfYear();
        }
        if (name.equals("YEAR_AFTER")) {
            return this.history.yearOfEra(YearDefinition.AFTER_NEW_YEAR);
        }
        if (name.equals("YEAR_BEFORE")) {
            return this.history.yearOfEra(YearDefinition.BEFORE_NEW_YEAR);
        }
        if (name.equals("CENTURY_OF_ERA")) {
            return this.history.centuryOfEra();
        }
        throw new InvalidObjectException("Unknown element: " + name);
    }

    private static class Rule<C extends ChronoEntity<C>> implements ElementRule<C, Integer> {
        private final ChronoHistory history;
        private final int index;

        Rule(int i, ChronoHistory chronoHistory) {
            this.index = i;
            this.history = chronoHistory;
        }

        @Override // net.time4j.engine.ElementRule
        public Integer getValue(C c) {
            int yearOfEra;
            try {
                PlainDate plainDate = (PlainDate) c.get(PlainDate.COMPONENT);
                HistoricDate convert = this.history.convert(plainDate);
                switch (this.index) {
                    case 2:
                        yearOfEra = convert.getYearOfEra();
                        break;
                    case 3:
                        yearOfEra = convert.getMonth();
                        break;
                    case 4:
                        yearOfEra = convert.getDayOfMonth();
                        break;
                    case 5:
                        yearOfEra = (int) ((plainDate.getDaysSinceEpochUTC() - this.history.convert(this.history.getBeginOfYear(convert.getEra(), convert.getYearOfEra(this.history.getNewYearStrategy()))).getDaysSinceEpochUTC()) + 1);
                        break;
                    case 6:
                    case 7:
                        yearOfEra = convert.getYearOfEra(this.history.getNewYearStrategy());
                        break;
                    case 8:
                        yearOfEra = ((convert.getYearOfEra() - 1) / 100) + 1;
                        break;
                    default:
                        throw new UnsupportedOperationException("Unknown element index: " + this.index);
                }
                return Integer.valueOf(yearOfEra);
            } catch (IllegalArgumentException e) {
                throw new ChronoException(e.getMessage(), e);
            }
        }

        @Override // net.time4j.engine.ElementRule
        public Integer getMinimum(C c) {
            try {
                HistoricDate convert = this.history.convert((PlainDate) c.get(PlainDate.COMPONENT));
                int i = this.index;
                if (i != 2 && i != 6 && i != 7 && i != 8) {
                    HistoricDate adjust = adjust(c, 1);
                    if (this.history.isValid(adjust)) {
                        return 1;
                    }
                    if (this.index == 5) {
                        throw new ChronoException("Historic New Year cannot be determined.");
                    }
                    List<CutOverEvent> events = this.history.getEvents();
                    int size = events.size() - 1;
                    while (true) {
                        if (size < 0) {
                            break;
                        }
                        CutOverEvent cutOverEvent = events.get(size);
                        if (convert.compareTo(cutOverEvent.dateAtCutOver) >= 0) {
                            adjust = cutOverEvent.dateAtCutOver;
                            break;
                        }
                        size--;
                    }
                    return Integer.valueOf(this.index == 3 ? adjust.getMonth() : adjust.getDayOfMonth());
                }
                if (convert.getEra() == HistoricEra.BYZANTINE && convert.getMonth() >= 9) {
                    return 0;
                }
                return 1;
            } catch (IllegalArgumentException e) {
                throw new ChronoException(e.getMessage(), e);
            }
        }

        @Override // net.time4j.engine.ElementRule
        public Integer getMaximum(C c) {
            HistoricDate adjust;
            int i;
            try {
                HistoricDate convert = this.history.convert((PlainDate) c.get(PlainDate.COMPONENT));
                int i2 = 8;
                int i3 = 999984973;
                switch (this.index) {
                    case 2:
                    case 6:
                    case 7:
                    case 8:
                        if (this.history != ChronoHistory.PROLEPTIC_BYZANTINE) {
                            if (this.history == ChronoHistory.PROLEPTIC_JULIAN) {
                                i3 = 999979465;
                                if (convert.getEra() == HistoricEra.BC) {
                                    i3 = 999979466;
                                }
                            } else if (this.history == ChronoHistory.PROLEPTIC_GREGORIAN) {
                                i3 = 999999999;
                                if (convert.getEra() == HistoricEra.BC) {
                                    i3 = Http2Connection.DEGRADED_PONG_TIMEOUT_NS;
                                }
                            } else {
                                i3 = convert.getEra() == HistoricEra.BC ? 45 : 9999;
                            }
                        }
                        if (this.index == 8) {
                            i3 = ((i3 - 1) / 100) + 1;
                        }
                        return Integer.valueOf(i3);
                    case 3:
                        if (convert.getEra() != HistoricEra.BYZANTINE || convert.getYearOfEra() != 999984973) {
                            i2 = 12;
                        }
                        adjust = adjust(c, i2);
                        i = i2;
                        break;
                    case 4:
                        i = this.history.getAlgorithm(convert).getMaximumDayOfMonth(convert);
                        adjust = adjust(c, i);
                        break;
                    case 5:
                        int lengthOfYear = this.history.getLengthOfYear(convert.getEra(), convert.getYearOfEra(this.history.getNewYearStrategy()));
                        if (lengthOfYear == -1) {
                            throw new ChronoException("Length of historic year undefined.");
                        }
                        return Integer.valueOf(lengthOfYear);
                    default:
                        throw new UnsupportedOperationException("Unknown element index: " + this.index);
                }
                if (this.history.isValid(adjust)) {
                    return Integer.valueOf(i);
                }
                List<CutOverEvent> events = this.history.getEvents();
                int size = events.size() - 1;
                while (true) {
                    if (size >= 0) {
                        CutOverEvent cutOverEvent = events.get(size);
                        if (convert.compareTo(cutOverEvent.dateAtCutOver) < 0) {
                            adjust = cutOverEvent.dateBeforeCutOver;
                        } else {
                            size--;
                        }
                    }
                }
                return Integer.valueOf(this.index == 3 ? adjust.getMonth() : adjust.getDayOfMonth());
            } catch (RuntimeException e) {
                throw new ChronoException(e.getMessage(), e);
            }
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: isValid, reason: merged with bridge method [inline-methods] */
        public boolean isValid2(C c, Integer num) {
            if (num == null) {
                return false;
            }
            try {
                return this.history.isValid(adjust(c, num.intValue()));
            } catch (IllegalArgumentException unused) {
                return false;
            }
        }

        @Override // net.time4j.engine.ElementRule
        /* renamed from: withValue, reason: merged with bridge method [inline-methods] */
        public C withValue2(C c, Integer num, boolean z) {
            if (num == null) {
                throw new IllegalArgumentException("Missing historic element value.");
            }
            return (C) c.with(PlainDate.COMPONENT, this.history.convert(adjust(c, num.intValue())));
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtFloor(C c) {
            throw new UnsupportedOperationException("Never called.");
        }

        @Override // net.time4j.engine.ElementRule
        public ChronoElement<?> getChildAtCeiling(C c) {
            throw new UnsupportedOperationException("Never called.");
        }

        private HistoricDate adjust(C c, int i) {
            HistoricDate convert = this.history.convert((PlainDate) c.get(PlainDate.COMPONENT));
            YearDefinition yearDefinition = YearDefinition.DUAL_DATING;
            NewYearStrategy newYearStrategy = this.history.getNewYearStrategy();
            int i2 = this.index;
            switch (i2) {
                case 2:
                    break;
                case 3:
                    return this.history.adjustDayOfMonth(HistoricDate.of(convert.getEra(), convert.getYearOfEra(), i, convert.getDayOfMonth()));
                case 4:
                    return HistoricDate.of(convert.getEra(), convert.getYearOfEra(), convert.getMonth(), i);
                case 5:
                    int yearOfEra = convert.getYearOfEra(this.history.getNewYearStrategy());
                    HistoricDate beginOfYear = this.history.getBeginOfYear(convert.getEra(), yearOfEra);
                    int lengthOfYear = this.history.getLengthOfYear(convert.getEra(), yearOfEra);
                    if (i == 1) {
                        return beginOfYear;
                    }
                    if (i > 1 && i <= lengthOfYear) {
                        return this.history.convert(this.history.convert(beginOfYear).plus(CalendarDays.of(i - 1)));
                    }
                    throw new IllegalArgumentException("Out of range: " + i);
                case 6:
                case 7:
                    yearDefinition = i2 == 6 ? YearDefinition.AFTER_NEW_YEAR : YearDefinition.BEFORE_NEW_YEAR;
                    break;
                case 8:
                    int yearOfEra2 = convert.getYearOfEra() % 100;
                    return this.history.adjustDayOfMonth(HistoricDate.of(convert.getEra(), ((i - 1) * 100) + (yearOfEra2 != 0 ? yearOfEra2 : 100), convert.getMonth(), convert.getDayOfMonth(), yearDefinition, newYearStrategy));
                default:
                    throw new UnsupportedOperationException("Unknown element index: " + this.index);
            }
            return this.history.adjustDayOfMonth(HistoricDate.of(convert.getEra(), i, convert.getMonth(), convert.getDayOfMonth(), yearDefinition, newYearStrategy));
        }
    }
}

package net.time4j.format.expert;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collections;
import java.util.Locale;
import net.time4j.Moment;
import net.time4j.PlainDate;
import net.time4j.PlainTime;
import net.time4j.PlainTimestamp;
import net.time4j.Weekmodel;
import net.time4j.engine.AttributeKey;
import net.time4j.engine.AttributeQuery;
import net.time4j.engine.ChronoCondition;
import net.time4j.engine.ChronoDisplay;
import net.time4j.engine.ChronoElement;
import net.time4j.engine.ChronoEntity;
import net.time4j.engine.ChronoFunction;
import net.time4j.format.Attributes;
import net.time4j.format.DisplayMode;
import net.time4j.format.Leniency;
import net.time4j.format.NumberSystem;
import net.time4j.format.expert.ChronoFormatter;
import net.time4j.tz.TZID;
import net.time4j.tz.ZonalOffset;
import org.apache.commons.lang3.ClassUtils;

/* loaded from: classes3.dex */
public class Iso8601Format {
    public static final ChronoFormatter<PlainDate> BASIC_CALENDAR_DATE;
    public static final ChronoFormatter<PlainDate> BASIC_DATE;
    public static final ChronoFormatter<PlainTimestamp> BASIC_DATE_TIME;
    public static final ChronoFormatter<Moment> BASIC_DATE_TIME_OFFSET;
    public static final ChronoFormatter<PlainDate> BASIC_ORDINAL_DATE;
    public static final ChronoFormatter<PlainTime> BASIC_WALL_TIME;
    public static final ChronoFormatter<PlainDate> BASIC_WEEK_DATE;
    public static final ChronoFormatter<PlainDate> EXTENDED_CALENDAR_DATE;
    public static final ChronoFormatter<PlainDate> EXTENDED_DATE;
    public static final ChronoFormatter<PlainTimestamp> EXTENDED_DATE_TIME;
    public static final ChronoFormatter<Moment> EXTENDED_DATE_TIME_OFFSET;
    public static final ChronoFormatter<PlainDate> EXTENDED_ORDINAL_DATE;
    public static final ChronoFormatter<PlainTime> EXTENDED_WALL_TIME;
    public static final ChronoFormatter<PlainDate> EXTENDED_WEEK_DATE;
    private static final char ISO_DECIMAL_SEPARATOR;
    private static final NonZeroCondition NON_ZERO_FRACTION;
    private static final NonZeroCondition NON_ZERO_SECOND;
    private static final ChronoCondition<ChronoDisplay> SECOND_PART;
    private static final ChronoCondition<Character> T_CONDITION;

    static {
        ISO_DECIMAL_SEPARATOR = Boolean.getBoolean("net.time4j.format.iso.decimal.dot") ? ClassUtils.PACKAGE_SEPARATOR_CHAR : ',';
        NonZeroCondition nonZeroCondition = new NonZeroCondition(PlainTime.SECOND_OF_MINUTE);
        NON_ZERO_SECOND = nonZeroCondition;
        NonZeroCondition nonZeroCondition2 = new NonZeroCondition(PlainTime.NANO_OF_SECOND);
        NON_ZERO_FRACTION = nonZeroCondition2;
        SECOND_PART = nonZeroCondition.or(nonZeroCondition2);
        T_CONDITION = new TCondition();
        BASIC_CALENDAR_DATE = calendarFormat(false);
        EXTENDED_CALENDAR_DATE = calendarFormat(true);
        BASIC_ORDINAL_DATE = ordinalFormat(false);
        EXTENDED_ORDINAL_DATE = ordinalFormat(true);
        BASIC_WEEK_DATE = weekdateFormat(false);
        EXTENDED_WEEK_DATE = weekdateFormat(true);
        BASIC_DATE = generalDateFormat(false);
        EXTENDED_DATE = generalDateFormat(true);
        BASIC_WALL_TIME = timeFormat(false);
        EXTENDED_WALL_TIME = timeFormat(true);
        BASIC_DATE_TIME = timestampFormat(false);
        EXTENDED_DATE_TIME = timestampFormat(true);
        BASIC_DATE_TIME_OFFSET = momentFormat(false);
        EXTENDED_DATE_TIME_OFFSET = momentFormat(true);
    }

    private Iso8601Format() {
    }

    public static PlainDate parseDate(CharSequence charSequence) throws ParseException {
        ParseLog parseLog = new ParseLog();
        PlainDate parseDate = parseDate(charSequence, parseLog);
        if (parseDate == null || parseLog.isError()) {
            throw new ParseException(parseLog.getErrorMessage(), parseLog.getErrorIndex());
        }
        if (parseLog.getPosition() >= charSequence.length()) {
            return parseDate;
        }
        throw new ParseException("Trailing characters found: " + ((Object) charSequence), parseLog.getPosition());
    }

    public static PlainDate parseDate(CharSequence charSequence, ParseLog parseLog) {
        int length = charSequence.length();
        int position = parseLog.getPosition();
        int i = length - position;
        if (i < 7) {
            parseLog.setError(length, "Too short to be compatible with ISO-8601: " + ((Object) charSequence.subSequence(position, length)));
            return null;
        }
        int i2 = 0;
        for (int i3 = position + 1; i3 < length; i3++) {
            char charAt = charSequence.charAt(i3);
            if (charAt == '-') {
                i2++;
            } else {
                if (charAt == '/' || charAt == 'T') {
                    i = i3 - position;
                    break;
                }
                if (charAt == 'W') {
                    return (i2 > 0 ? EXTENDED_WEEK_DATE : BASIC_WEEK_DATE).parse(charSequence, parseLog);
                }
            }
        }
        if (i2 != 0) {
            if (i2 == 1) {
                return EXTENDED_ORDINAL_DATE.parse(charSequence, parseLog);
            }
            return EXTENDED_CALENDAR_DATE.parse(charSequence, parseLog);
        }
        int i4 = i - 4;
        char charAt2 = charSequence.charAt(position);
        if (charAt2 == '+' || charAt2 == '-') {
            i4 -= 2;
        }
        return (i4 == 3 ? BASIC_ORDINAL_DATE : BASIC_CALENDAR_DATE).parse(charSequence, parseLog);
    }

    private static ChronoFormatter<PlainDate> calendarFormat(boolean z) {
        ChronoFormatter.Builder addInteger = ChronoFormatter.setUp(PlainDate.class, Locale.ROOT).startSection((AttributeKey<AttributeKey<NumberSystem>>) Attributes.NUMBER_SYSTEM, (AttributeKey<NumberSystem>) NumberSystem.ARABIC).startSection(Attributes.ZERO_DIGIT, '0').addInteger(PlainDate.YEAR, 4, 9, SignPolicy.SHOW_WHEN_BIG_NUMBER);
        if (z) {
            addInteger.addLiteral('-');
        }
        addInteger.addFixedInteger(PlainDate.MONTH_AS_NUMBER, 2);
        if (z) {
            addInteger.addLiteral('-');
        }
        return addInteger.addFixedInteger(PlainDate.DAY_OF_MONTH, 2).endSection().endSection().build().with(Leniency.STRICT);
    }

    private static ChronoFormatter<PlainDate> ordinalFormat(boolean z) {
        ChronoFormatter.Builder addInteger = ChronoFormatter.setUp(PlainDate.class, Locale.ROOT).startSection((AttributeKey<AttributeKey<NumberSystem>>) Attributes.NUMBER_SYSTEM, (AttributeKey<NumberSystem>) NumberSystem.ARABIC).startSection(Attributes.ZERO_DIGIT, '0').addInteger(PlainDate.YEAR, 4, 9, SignPolicy.SHOW_WHEN_BIG_NUMBER);
        if (z) {
            addInteger.addLiteral('-');
        }
        return addInteger.addFixedInteger(PlainDate.DAY_OF_YEAR, 3).endSection().endSection().build().with(Leniency.STRICT);
    }

    private static ChronoFormatter<PlainDate> weekdateFormat(boolean z) {
        ChronoFormatter.Builder addInteger = ChronoFormatter.setUp(PlainDate.class, Locale.ROOT).startSection((AttributeKey<AttributeKey<NumberSystem>>) Attributes.NUMBER_SYSTEM, (AttributeKey<NumberSystem>) NumberSystem.ARABIC).startSection(Attributes.ZERO_DIGIT, '0').addInteger(PlainDate.YEAR_OF_WEEKDATE, 4, 9, SignPolicy.SHOW_WHEN_BIG_NUMBER);
        if (z) {
            addInteger.addLiteral('-');
        }
        addInteger.addLiteral('W');
        addInteger.addFixedInteger(Weekmodel.ISO.weekOfYear(), 2);
        if (z) {
            addInteger.addLiteral('-');
        }
        return addInteger.addFixedNumerical(PlainDate.DAY_OF_WEEK, 1).endSection().endSection().build().with(Leniency.STRICT);
    }

    private static ChronoFormatter<PlainDate> generalDateFormat(boolean z) {
        ChronoFormatter.Builder up = ChronoFormatter.setUp(PlainDate.class, Locale.ROOT);
        up.addCustomized(PlainDate.COMPONENT, generalDatePrinter(z), generalDateParser(z));
        return up.build().with(Leniency.STRICT);
    }

    private static ChronoFormatter<PlainTime> timeFormat(boolean z) {
        ChronoFormatter.Builder up = ChronoFormatter.setUp(PlainTime.class, Locale.ROOT);
        up.skipUnknown(T_CONDITION, 1);
        addWallTime(up, z);
        return up.build().with(Leniency.STRICT);
    }

    private static ChronoFormatter<PlainTimestamp> timestampFormat(boolean z) {
        ChronoFormatter.Builder up = ChronoFormatter.setUp(PlainTimestamp.class, Locale.ROOT);
        up.addCustomized(PlainDate.COMPONENT, generalDatePrinter(z), generalDateParser(z));
        up.addLiteral('T');
        addWallTime(up, z);
        return up.build().with(Leniency.STRICT);
    }

    private static ChronoFormatter<Moment> momentFormat(boolean z) {
        ChronoFormatter.Builder up = ChronoFormatter.setUp(Moment.class, Locale.ROOT);
        up.addCustomized(Moment.axis().element(), momentFormat(DisplayMode.MEDIUM, z), momentFormat(DisplayMode.SHORT, z));
        return up.build().with(Leniency.STRICT).withTimezone((TZID) ZonalOffset.UTC);
    }

    private static ChronoFormatter<Moment> momentFormat(DisplayMode displayMode, boolean z) {
        ChronoFormatter.Builder up = ChronoFormatter.setUp(Moment.class, Locale.ROOT);
        up.addCustomized(PlainDate.COMPONENT, generalDatePrinter(z), generalDateParser(z));
        up.addLiteral('T');
        addWallTime(up, z);
        up.addTimezoneOffset(displayMode, z, Collections.singletonList("Z"));
        return up.build();
    }

    private static ChronoPrinter<PlainDate> generalDatePrinter(final boolean z) {
        return new ChronoPrinter<PlainDate>() { // from class: net.time4j.format.expert.Iso8601Format.1
            @Override // net.time4j.format.expert.ChronoPrinter
            public <R> R print(PlainDate plainDate, Appendable appendable, AttributeQuery attributeQuery, ChronoFunction<ChronoDisplay, R> chronoFunction) throws IOException {
                (z ? Iso8601Format.EXTENDED_CALENDAR_DATE : Iso8601Format.BASIC_CALENDAR_DATE).print(plainDate, appendable, attributeQuery);
                return null;
            }
        };
    }

    private static ChronoParser<PlainDate> generalDateParser(final boolean z) {
        return new ChronoParser<PlainDate>() { // from class: net.time4j.format.expert.Iso8601Format.2
            @Override // net.time4j.format.expert.ChronoParser
            public PlainDate parse(CharSequence charSequence, ParseLog parseLog, AttributeQuery attributeQuery) {
                int length = charSequence.length();
                int position = parseLog.getPosition();
                int i = length - position;
                int i2 = 0;
                for (int i3 = position + 1; i3 < length; i3++) {
                    char charAt = charSequence.charAt(i3);
                    if (charAt == '-') {
                        i2++;
                    } else {
                        if (charAt == '/' || charAt == 'T') {
                            i = i3 - position;
                            break;
                        }
                        if (charAt == 'W') {
                            if (z) {
                                return Iso8601Format.EXTENDED_WEEK_DATE.parse(charSequence, parseLog);
                            }
                            return Iso8601Format.BASIC_WEEK_DATE.parse(charSequence, parseLog);
                        }
                    }
                }
                if (z) {
                    if (i2 == 1) {
                        return Iso8601Format.EXTENDED_ORDINAL_DATE.parse(charSequence, parseLog);
                    }
                    return Iso8601Format.EXTENDED_CALENDAR_DATE.parse(charSequence, parseLog);
                }
                int i4 = i - 4;
                char charAt2 = charSequence.charAt(position);
                if (charAt2 == '+' || charAt2 == '-') {
                    i4 -= 2;
                }
                if (i4 == 3) {
                    return Iso8601Format.BASIC_ORDINAL_DATE.parse(charSequence, parseLog);
                }
                return Iso8601Format.BASIC_CALENDAR_DATE.parse(charSequence, parseLog);
            }
        };
    }

    private static <T extends ChronoEntity<T>> void addWallTime(ChronoFormatter.Builder<T> builder, boolean z) {
        builder.startSection((AttributeKey<AttributeKey<NumberSystem>>) Attributes.NUMBER_SYSTEM, (AttributeKey<NumberSystem>) NumberSystem.ARABIC);
        builder.startSection(Attributes.ZERO_DIGIT, '0');
        builder.addFixedInteger(PlainTime.HOUR_FROM_0_TO_24, 2);
        builder.startOptionalSection();
        if (z) {
            builder.addLiteral(':');
        }
        builder.addFixedInteger(PlainTime.MINUTE_OF_HOUR, 2);
        builder.startOptionalSection(SECOND_PART);
        if (z) {
            builder.addLiteral(':');
        }
        builder.addFixedInteger(PlainTime.SECOND_OF_MINUTE, 2);
        builder.startOptionalSection(NON_ZERO_FRACTION);
        if (ISO_DECIMAL_SEPARATOR == ',') {
            builder.addLiteral(',', ClassUtils.PACKAGE_SEPARATOR_CHAR);
        } else {
            builder.addLiteral(ClassUtils.PACKAGE_SEPARATOR_CHAR, ',');
        }
        builder.addFraction(PlainTime.NANO_OF_SECOND, 0, 9, false);
        for (int i = 0; i < 5; i++) {
            builder.endSection();
        }
    }

    private static class NonZeroCondition implements ChronoCondition<ChronoDisplay> {
        private final ChronoElement<Integer> element;

        NonZeroCondition(ChronoElement<Integer> chronoElement) {
            this.element = chronoElement;
        }

        @Override // net.time4j.engine.ChronoCondition
        public boolean test(ChronoDisplay chronoDisplay) {
            return chronoDisplay.getInt(this.element) > 0;
        }

        ChronoCondition<ChronoDisplay> or(final NonZeroCondition nonZeroCondition) {
            return new ChronoCondition<ChronoDisplay>() { // from class: net.time4j.format.expert.Iso8601Format.NonZeroCondition.1
                @Override // net.time4j.engine.ChronoCondition
                public boolean test(ChronoDisplay chronoDisplay) {
                    return NonZeroCondition.this.test(chronoDisplay) || nonZeroCondition.test(chronoDisplay);
                }
            };
        }
    }

    private static class TCondition implements ChronoCondition<Character> {
        private TCondition() {
        }

        @Override // net.time4j.engine.ChronoCondition
        public boolean test(Character ch) {
            return ch.charValue() == 'T';
        }
    }
}

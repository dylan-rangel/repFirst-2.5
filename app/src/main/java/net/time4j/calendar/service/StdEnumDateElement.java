package net.time4j.calendar.service;

import java.io.IOException;
import java.lang.Enum;
import java.text.ParsePosition;
import java.util.Locale;
import net.time4j.engine.AttributeQuery;
import net.time4j.engine.ChronoDisplay;
import net.time4j.engine.ChronoEntity;
import net.time4j.engine.ChronoOperator;
import net.time4j.format.Attributes;
import net.time4j.format.CalendarText;
import net.time4j.format.CalendarType;
import net.time4j.format.NumericalElement;
import net.time4j.format.OutputContext;
import net.time4j.format.TextAccessor;
import net.time4j.format.TextElement;
import net.time4j.format.TextWidth;

/* loaded from: classes3.dex */
public class StdEnumDateElement<V extends Enum<V>, T extends ChronoEntity<T>> extends StdDateElement<V, T> implements NumericalElement<V>, TextElement<V> {
    private static final long serialVersionUID = -2452569351302286113L;
    private final transient ChronoOperator<T> decrementor;
    private final transient String defaultCalendarType;
    private final transient ChronoOperator<T> incrementor;
    private final transient Class<V> type;

    private static boolean isWeekdayElement(char c) {
        return c == 'E';
    }

    protected boolean hasLeapMonth(ChronoDisplay chronoDisplay) {
        return false;
    }

    public StdEnumDateElement(String str, Class<T> cls, Class<V> cls2, char c) {
        super(str, cls, c, isWeekdayElement(c));
        this.type = cls2;
        this.defaultCalendarType = extractCalendarType(cls);
        this.decrementor = null;
        this.incrementor = null;
    }

    public StdEnumDateElement(String str, Class<T> cls, Class<V> cls2, char c, String str2) {
        super(str, cls, c, isWeekdayElement(c));
        this.type = cls2;
        this.defaultCalendarType = str2;
        this.decrementor = null;
        this.incrementor = null;
    }

    public StdEnumDateElement(String str, Class<T> cls, Class<V> cls2, char c, ChronoOperator<T> chronoOperator, ChronoOperator<T> chronoOperator2) {
        super(str, cls, c, false);
        this.type = cls2;
        this.defaultCalendarType = extractCalendarType(cls);
        this.decrementor = chronoOperator;
        this.incrementor = chronoOperator2;
    }

    @Override // net.time4j.engine.ChronoElement
    public Class<V> getType() {
        return this.type;
    }

    @Override // net.time4j.engine.ChronoElement
    public V getDefaultMinimum() {
        return this.type.getEnumConstants()[0];
    }

    @Override // net.time4j.engine.ChronoElement
    public V getDefaultMaximum() {
        return this.type.getEnumConstants()[r0.length - 1];
    }

    @Override // net.time4j.format.NumericalElement
    public int numerical(V v) {
        return v.ordinal() + 1;
    }

    @Override // net.time4j.format.NumericalElement
    public int printToInt(V v, ChronoDisplay chronoDisplay, AttributeQuery attributeQuery) {
        return numerical((StdEnumDateElement<V, T>) v);
    }

    public boolean parseFromInt(ChronoEntity<?> chronoEntity, int i) {
        for (V v : getType().getEnumConstants()) {
            if (numerical((StdEnumDateElement<V, T>) v) == i) {
                chronoEntity.with(this, (StdEnumDateElement<V, T>) v);
                return true;
            }
        }
        return false;
    }

    @Override // net.time4j.calendar.service.StdDateElement, net.time4j.calendar.StdCalendarElement
    public ChronoOperator<T> decremented() {
        ChronoOperator<T> chronoOperator = this.decrementor;
        return chronoOperator != null ? chronoOperator : super.decremented();
    }

    @Override // net.time4j.calendar.service.StdDateElement, net.time4j.calendar.StdCalendarElement
    public ChronoOperator<T> incremented() {
        ChronoOperator<T> chronoOperator = this.incrementor;
        return chronoOperator != null ? chronoOperator : super.incremented();
    }

    public void print(ChronoDisplay chronoDisplay, Appendable appendable, AttributeQuery attributeQuery) throws IOException {
        appendable.append(accessor(attributeQuery, (OutputContext) attributeQuery.get(Attributes.OUTPUT_CONTEXT, OutputContext.FORMAT), hasLeapMonth(chronoDisplay)).print((Enum) chronoDisplay.get(this)));
    }

    @Override // net.time4j.format.TextElement
    public V parse(CharSequence charSequence, ParsePosition parsePosition, AttributeQuery attributeQuery) {
        int index = parsePosition.getIndex();
        OutputContext outputContext = (OutputContext) attributeQuery.get(Attributes.OUTPUT_CONTEXT, OutputContext.FORMAT);
        V v = (V) accessor(attributeQuery, outputContext, false).parse(charSequence, parsePosition, getType(), attributeQuery);
        if (v == null && isMonthElement()) {
            parsePosition.setErrorIndex(-1);
            parsePosition.setIndex(index);
            v = (V) accessor(attributeQuery, outputContext, true).parse(charSequence, parsePosition, getType(), attributeQuery);
        }
        if (v != null || !((Boolean) attributeQuery.get(Attributes.PARSE_MULTIPLE_CONTEXT, Boolean.TRUE)).booleanValue()) {
            return v;
        }
        parsePosition.setErrorIndex(-1);
        parsePosition.setIndex(index);
        OutputContext outputContext2 = outputContext == OutputContext.FORMAT ? OutputContext.STANDALONE : OutputContext.FORMAT;
        V v2 = (V) accessor(attributeQuery, outputContext2, false).parse(charSequence, parsePosition, getType(), attributeQuery);
        if (v2 != null || !isMonthElement()) {
            return v2;
        }
        parsePosition.setErrorIndex(-1);
        parsePosition.setIndex(index);
        return (V) accessor(attributeQuery, outputContext2, true).parse(charSequence, parsePosition, getType(), attributeQuery);
    }

    protected String getCalendarType(AttributeQuery attributeQuery) {
        if (isMonthElement() || isEraElement()) {
            return (String) attributeQuery.get(Attributes.CALENDAR_TYPE, this.defaultCalendarType);
        }
        return isWeekdayElement() ? CalendarText.ISO_CALENDAR_TYPE : this.defaultCalendarType;
    }

    protected boolean isEraElement() {
        return getSymbol() == 'G';
    }

    protected boolean isMonthElement() {
        return getSymbol() == 'M';
    }

    protected boolean isWeekdayElement() {
        return isWeekdayElement(getSymbol());
    }

    protected TextAccessor accessor(AttributeQuery attributeQuery, OutputContext outputContext, boolean z) {
        Locale locale = (Locale) attributeQuery.get(Attributes.LANGUAGE, Locale.ROOT);
        TextWidth textWidth = (TextWidth) attributeQuery.get(Attributes.TEXT_WIDTH, TextWidth.WIDE);
        CalendarText calendarText = CalendarText.getInstance(getCalendarType(attributeQuery), locale);
        if (isMonthElement()) {
            if (z) {
                return calendarText.getLeapMonths(textWidth, outputContext);
            }
            return calendarText.getStdMonths(textWidth, outputContext);
        }
        if (isWeekdayElement()) {
            return calendarText.getWeekdays(textWidth, outputContext);
        }
        if (isEraElement()) {
            return calendarText.getEras(textWidth);
        }
        return calendarText.getTextForms(name(), this.type, new String[0]);
    }

    private static String extractCalendarType(Class<?> cls) {
        CalendarType calendarType = (CalendarType) cls.getAnnotation(CalendarType.class);
        return calendarType == null ? CalendarText.ISO_CALENDAR_TYPE : calendarType.value();
    }
}

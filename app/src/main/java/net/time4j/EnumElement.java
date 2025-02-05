package net.time4j;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectStreamException;
import java.lang.Enum;
import java.text.ParsePosition;
import java.util.Locale;
import net.time4j.engine.AttributeQuery;
import net.time4j.engine.ChronoDisplay;
import net.time4j.engine.ChronoEntity;
import net.time4j.engine.ChronoException;
import net.time4j.format.Attributes;
import net.time4j.format.CalendarText;
import net.time4j.format.Leniency;
import net.time4j.format.NumericalElement;
import net.time4j.format.OutputContext;
import net.time4j.format.TextAccessor;
import net.time4j.format.TextWidth;
import net.time4j.format.internal.GregorianTextElement;

/* loaded from: classes3.dex */
final class EnumElement<V extends Enum<V>> extends AbstractDateElement<V> implements NavigableElement<V>, NumericalElement<V>, GregorianTextElement<V> {
    static final int DAY_OF_WEEK = 102;
    static final int MONTH = 101;
    static final int QUARTER_OF_YEAR = 103;
    private static final long serialVersionUID = 2055272540517425102L;
    private final transient V dmax;
    private final transient V dmin;
    private final transient int index;
    private final transient char symbol;
    private final transient Class<V> type;

    @Override // net.time4j.engine.ChronoElement
    public boolean isDateElement() {
        return true;
    }

    @Override // net.time4j.engine.BasicElement
    protected boolean isSingleton() {
        return true;
    }

    @Override // net.time4j.engine.ChronoElement
    public boolean isTimeElement() {
        return false;
    }

    EnumElement(String str, Class<V> cls, V v, V v2, int i, char c) {
        super(str);
        this.type = cls;
        this.dmin = v;
        this.dmax = v2;
        this.index = i;
        this.symbol = c;
    }

    @Override // net.time4j.engine.ChronoElement
    public Class<V> getType() {
        return this.type;
    }

    @Override // net.time4j.engine.BasicElement, net.time4j.engine.ChronoElement
    public char getSymbol() {
        return this.symbol;
    }

    @Override // net.time4j.engine.ChronoElement
    public V getDefaultMinimum() {
        return this.dmin;
    }

    @Override // net.time4j.engine.ChronoElement
    public V getDefaultMaximum() {
        return this.dmax;
    }

    @Override // net.time4j.NavigableElement
    public ElementOperator<PlainDate> setToNext(V v) {
        return new NavigationOperator(this, 9, v);
    }

    @Override // net.time4j.NavigableElement
    public ElementOperator<PlainDate> setToPrevious(V v) {
        return new NavigationOperator(this, 10, v);
    }

    @Override // net.time4j.NavigableElement
    public ElementOperator<PlainDate> setToNextOrSame(V v) {
        return new NavigationOperator(this, 11, v);
    }

    @Override // net.time4j.NavigableElement
    public ElementOperator<PlainDate> setToPreviousOrSame(V v) {
        return new NavigationOperator(this, 12, v);
    }

    @Override // net.time4j.format.NumericalElement
    public int numerical(V v) {
        return v.ordinal() + 1;
    }

    @Override // net.time4j.format.NumericalElement
    public int printToInt(V v, ChronoDisplay chronoDisplay, AttributeQuery attributeQuery) {
        return v.ordinal() + 1;
    }

    @Override // net.time4j.format.NumericalElement
    public boolean parseFromInt(ChronoEntity<?> chronoEntity, int i) {
        for (V v : getType().getEnumConstants()) {
            if (numerical((EnumElement<V>) v) == i) {
                chronoEntity.with(this, (EnumElement<V>) v);
                return true;
            }
        }
        return false;
    }

    @Override // net.time4j.format.TextElement
    public void print(ChronoDisplay chronoDisplay, Appendable appendable, AttributeQuery attributeQuery) throws IOException {
        appendable.append(accessor((Locale) attributeQuery.get(Attributes.LANGUAGE, Locale.ROOT), (TextWidth) attributeQuery.get(Attributes.TEXT_WIDTH, TextWidth.WIDE), (OutputContext) attributeQuery.get(Attributes.OUTPUT_CONTEXT, OutputContext.FORMAT)).print((Enum) chronoDisplay.get(this)));
    }

    @Override // net.time4j.format.TextElement
    public V parse(CharSequence charSequence, ParsePosition parsePosition, AttributeQuery attributeQuery) {
        int index = parsePosition.getIndex();
        Locale locale = (Locale) attributeQuery.get(Attributes.LANGUAGE, Locale.ROOT);
        TextWidth textWidth = (TextWidth) attributeQuery.get(Attributes.TEXT_WIDTH, TextWidth.WIDE);
        OutputContext outputContext = (OutputContext) attributeQuery.get(Attributes.OUTPUT_CONTEXT, OutputContext.FORMAT);
        V v = (V) accessor(locale, textWidth, outputContext).parse(charSequence, parsePosition, getType(), attributeQuery);
        if (v != null || !((Boolean) attributeQuery.get(Attributes.PARSE_MULTIPLE_CONTEXT, Boolean.TRUE)).booleanValue()) {
            return v;
        }
        parsePosition.setErrorIndex(-1);
        parsePosition.setIndex(index);
        return (V) accessor(locale, textWidth, outputContext == OutputContext.FORMAT ? OutputContext.STANDALONE : OutputContext.FORMAT).parse(charSequence, parsePosition, getType(), attributeQuery);
    }

    @Override // net.time4j.format.internal.GregorianTextElement
    public void print(ChronoDisplay chronoDisplay, Appendable appendable, Locale locale, TextWidth textWidth, OutputContext outputContext) throws IOException, ChronoException {
        appendable.append(accessor(locale, textWidth, outputContext).print((Enum) chronoDisplay.get(this)));
    }

    @Override // net.time4j.format.internal.GregorianTextElement
    public V parse(CharSequence charSequence, ParsePosition parsePosition, Locale locale, TextWidth textWidth, OutputContext outputContext, Leniency leniency) {
        int index = parsePosition.getIndex();
        V v = (V) accessor(locale, textWidth, outputContext).parse(charSequence, parsePosition, getType(), leniency);
        if (v != null || leniency.isStrict()) {
            return v;
        }
        parsePosition.setErrorIndex(-1);
        parsePosition.setIndex(index);
        return (V) accessor(locale, textWidth, outputContext == OutputContext.FORMAT ? OutputContext.STANDALONE : OutputContext.FORMAT).parse(charSequence, parsePosition, getType(), leniency);
    }

    int getIndex() {
        return this.index;
    }

    private TextAccessor accessor(Locale locale, TextWidth textWidth, OutputContext outputContext) {
        switch (this.index) {
            case 101:
                return CalendarText.getIsoInstance(locale).getStdMonths(textWidth, outputContext);
            case 102:
                return CalendarText.getIsoInstance(locale).getWeekdays(textWidth, outputContext);
            case 103:
                return CalendarText.getIsoInstance(locale).getQuarters(textWidth, outputContext);
            default:
                throw new UnsupportedOperationException(name());
        }
    }

    private Object readResolve() throws ObjectStreamException {
        Object lookupElement = PlainDate.lookupElement(name());
        if (lookupElement != null) {
            return lookupElement;
        }
        throw new InvalidObjectException(name());
    }
}

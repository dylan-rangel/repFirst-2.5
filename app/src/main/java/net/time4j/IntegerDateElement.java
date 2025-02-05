package net.time4j;

import java.io.InvalidObjectException;
import java.io.ObjectStreamException;
import java.math.BigDecimal;
import net.time4j.engine.ChronoEntity;
import net.time4j.engine.ChronoFunction;
import net.time4j.engine.ChronoOperator;

/* loaded from: classes3.dex */
final class IntegerDateElement extends AbstractDateElement<Integer> implements ProportionalElement<Integer, PlainDate> {
    static final int DAY_OF_MONTH = 16;
    static final int DAY_OF_QUARTER = 18;
    static final int DAY_OF_YEAR = 17;
    static final int MONTH = 15;
    static final int YEAR = 14;
    private static final long serialVersionUID = -1337148214680014674L;
    private final transient Integer defaultMax;
    private final transient Integer defaultMin;
    private final transient int index;
    private final transient ChronoFunction<ChronoEntity<?>, BigDecimal> rf;
    private final transient char symbol;

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

    @Override // net.time4j.ProportionalElement
    public /* bridge */ /* synthetic */ ElementOperator<PlainDate> setLenient(Integer num) {
        return super.setLenient((IntegerDateElement) num);
    }

    private IntegerDateElement(String str, int i, Integer num, Integer num2, char c) {
        super(str);
        this.index = i;
        this.defaultMin = num;
        this.defaultMax = num2;
        this.symbol = c;
        this.rf = new ProportionalFunction(this, false);
    }

    @Override // net.time4j.engine.ChronoElement
    public Class<Integer> getType() {
        return Integer.class;
    }

    @Override // net.time4j.engine.BasicElement, net.time4j.engine.ChronoElement
    public char getSymbol() {
        return this.symbol;
    }

    @Override // net.time4j.engine.ChronoElement
    public Integer getDefaultMinimum() {
        return this.defaultMin;
    }

    @Override // net.time4j.engine.ChronoElement
    public Integer getDefaultMaximum() {
        return this.defaultMax;
    }

    @Override // net.time4j.ProportionalElement
    public ChronoFunction<ChronoEntity<?>, BigDecimal> ratio() {
        return this.rf;
    }

    @Override // net.time4j.ProportionalElement
    public ChronoOperator<PlainDate> roundedUp(int i) {
        return new RoundingOperator(this, Boolean.TRUE, i);
    }

    @Override // net.time4j.ProportionalElement
    public ChronoOperator<PlainDate> roundedHalf(int i) {
        return new RoundingOperator(this, null, i);
    }

    @Override // net.time4j.ProportionalElement
    public ChronoOperator<PlainDate> roundedDown(int i) {
        return new RoundingOperator(this, Boolean.FALSE, i);
    }

    int getIndex() {
        return this.index;
    }

    static IntegerDateElement create(String str, int i, int i2, int i3, char c) {
        return new IntegerDateElement(str, i, Integer.valueOf(i2), Integer.valueOf(i3), c);
    }

    private Object readResolve() throws ObjectStreamException {
        Object lookupElement = PlainDate.lookupElement(name());
        if (lookupElement != null) {
            return lookupElement;
        }
        throw new InvalidObjectException(name());
    }
}

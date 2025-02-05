package net.time4j;

import java.io.InvalidObjectException;
import java.io.ObjectStreamException;
import java.math.BigDecimal;
import net.time4j.engine.ChronoEntity;
import net.time4j.engine.ChronoFunction;
import net.time4j.engine.ChronoOperator;

/* loaded from: classes3.dex */
final class IntegerTimeElement extends AbstractTimeElement<Integer> implements ProportionalElement<Integer, PlainTime> {
    static final int CLOCK_HOUR_OF_AMPM = 1;
    static final int CLOCK_HOUR_OF_DAY = 2;
    static final int DIGITAL_HOUR_OF_AMPM = 3;
    static final int DIGITAL_HOUR_OF_DAY = 4;
    static final int ISO_HOUR = 5;
    static final int MICRO_OF_SECOND = 11;
    static final int MILLI_OF_DAY = 13;
    static final int MILLI_OF_SECOND = 10;
    static final int MINUTE_OF_DAY = 7;
    static final int MINUTE_OF_HOUR = 6;
    static final int NANO_OF_SECOND = 12;
    static final int SECOND_OF_DAY = 9;
    static final int SECOND_OF_MINUTE = 8;
    private static final long serialVersionUID = -1337148214680014674L;
    private final transient Integer defaultMax;
    private final transient Integer defaultMin;
    private final transient int index;
    private final transient ChronoFunction<ChronoEntity<?>, BigDecimal> rf;
    private final transient char symbol;

    @Override // net.time4j.engine.ChronoElement
    public boolean isDateElement() {
        return false;
    }

    @Override // net.time4j.engine.BasicElement
    protected boolean isSingleton() {
        return true;
    }

    @Override // net.time4j.engine.ChronoElement
    public boolean isTimeElement() {
        return true;
    }

    @Override // net.time4j.ProportionalElement
    public /* bridge */ /* synthetic */ ElementOperator<PlainTime> setLenient(Integer num) {
        return super.setLenient((IntegerTimeElement) num);
    }

    private IntegerTimeElement(String str, int i, Integer num, Integer num2, char c) {
        super(str);
        this.index = i;
        this.defaultMin = num;
        this.defaultMax = num2;
        this.symbol = c;
        this.rf = new ProportionalFunction(this, i == 5 || i == 7 || i == 9 || i == 13);
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
    public ChronoOperator<PlainTime> roundedUp(int i) {
        return new RoundingOperator(this, Boolean.TRUE, i);
    }

    @Override // net.time4j.ProportionalElement
    public ChronoOperator<PlainTime> roundedHalf(int i) {
        return new RoundingOperator(this, null, i);
    }

    @Override // net.time4j.ProportionalElement
    public ChronoOperator<PlainTime> roundedDown(int i) {
        return new RoundingOperator(this, Boolean.FALSE, i);
    }

    int getIndex() {
        return this.index;
    }

    static IntegerTimeElement createTimeElement(String str, int i, int i2, int i3, char c) {
        return new IntegerTimeElement(str, i, Integer.valueOf(i2), Integer.valueOf(i3), c);
    }

    static IntegerTimeElement createClockElement(String str, boolean z) {
        return new IntegerTimeElement(str, z ? 2 : 1, 1, Integer.valueOf(z ? 24 : 12), z ? 'k' : 'h');
    }

    private Object readResolve() throws ObjectStreamException {
        Object lookupElement = PlainTime.lookupElement(name());
        if (lookupElement != null) {
            return lookupElement;
        }
        throw new InvalidObjectException(name());
    }
}

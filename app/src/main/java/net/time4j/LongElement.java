package net.time4j;

import java.io.InvalidObjectException;
import java.io.ObjectStreamException;
import java.math.BigDecimal;
import net.time4j.engine.ChronoElement;
import net.time4j.engine.ChronoEntity;
import net.time4j.engine.ChronoFunction;
import net.time4j.engine.ChronoOperator;

/* loaded from: classes3.dex */
final class LongElement extends AbstractTimeElement<Long> implements ProportionalElement<Long, PlainTime> {
    static final ChronoElement<Long> DAY_OVERFLOW = new LongElement();
    private static final long serialVersionUID = 5930990958663061693L;
    private final transient Long defaultMax;
    private final transient Long defaultMin;
    private final transient ChronoFunction<ChronoEntity<?>, BigDecimal> rf;

    @Override // net.time4j.engine.ChronoElement
    public boolean isDateElement() {
        return false;
    }

    @Override // net.time4j.engine.ChronoElement
    public boolean isTimeElement() {
        return true;
    }

    @Override // net.time4j.ProportionalElement
    public /* bridge */ /* synthetic */ ElementOperator<PlainTime> setLenient(Long l) {
        return super.setLenient((LongElement) l);
    }

    private LongElement() {
        this("DAY_OVERFLOW", Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private LongElement(String str, long j, long j2) {
        super(str);
        this.defaultMin = Long.valueOf(j);
        this.defaultMax = Long.valueOf(j2);
        this.rf = new ProportionalFunction(this, true);
    }

    @Override // net.time4j.engine.ChronoElement
    public Class<Long> getType() {
        return Long.class;
    }

    @Override // net.time4j.engine.ChronoElement
    public Long getDefaultMinimum() {
        return this.defaultMin;
    }

    @Override // net.time4j.engine.ChronoElement
    public Long getDefaultMaximum() {
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

    static LongElement create(String str, long j, long j2) {
        return new LongElement(str, j, j2);
    }

    private Object readResolve() throws ObjectStreamException {
        Object lookupElement = PlainTime.lookupElement(name());
        if (lookupElement != null) {
            return lookupElement;
        }
        if (name().equals("DAY_OVERFLOW")) {
            return DAY_OVERFLOW;
        }
        throw new InvalidObjectException(name());
    }
}

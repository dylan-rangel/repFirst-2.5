package net.time4j;

import java.io.InvalidObjectException;
import java.io.ObjectStreamException;
import java.math.BigDecimal;
import net.time4j.engine.BasicElement;
import net.time4j.engine.ChronoFunction;
import net.time4j.tz.TZID;
import net.time4j.tz.Timezone;
import net.time4j.tz.ZonalOffset;

/* loaded from: classes3.dex */
final class DecimalTimeElement extends BasicElement<BigDecimal> implements ZonalElement<BigDecimal> {
    private static final long serialVersionUID = -4837430960549551204L;
    private final transient BigDecimal defaultMax;

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

    DecimalTimeElement(String str, BigDecimal bigDecimal) {
        super(str);
        this.defaultMax = bigDecimal;
    }

    @Override // net.time4j.engine.ChronoElement
    public Class<BigDecimal> getType() {
        return BigDecimal.class;
    }

    @Override // net.time4j.engine.ChronoElement
    public BigDecimal getDefaultMinimum() {
        return BigDecimal.ZERO;
    }

    @Override // net.time4j.engine.ChronoElement
    public BigDecimal getDefaultMaximum() {
        return this.defaultMax;
    }

    @Override // net.time4j.ZonalElement
    public ChronoFunction<Moment, BigDecimal> inStdTimezone() {
        return in(Timezone.ofSystem());
    }

    @Override // net.time4j.ZonalElement
    public ChronoFunction<Moment, BigDecimal> inTimezone(TZID tzid) {
        return in(Timezone.of(tzid));
    }

    @Override // net.time4j.ZonalElement
    public ChronoFunction<Moment, BigDecimal> in(Timezone timezone) {
        return new ZonalQuery(this, timezone);
    }

    @Override // net.time4j.ZonalElement
    public ChronoFunction<Moment, BigDecimal> atUTC() {
        return at(ZonalOffset.UTC);
    }

    @Override // net.time4j.ZonalElement
    public ChronoFunction<Moment, BigDecimal> at(ZonalOffset zonalOffset) {
        return new ZonalQuery(this, zonalOffset);
    }

    private Object readResolve() throws ObjectStreamException {
        Object lookupElement = PlainTime.lookupElement(name());
        if (lookupElement != null) {
            return lookupElement;
        }
        throw new InvalidObjectException(name());
    }
}

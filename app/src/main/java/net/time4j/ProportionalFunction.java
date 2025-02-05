package net.time4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import net.time4j.engine.ChronoElement;
import net.time4j.engine.ChronoEntity;
import net.time4j.engine.ChronoFunction;

/* loaded from: classes3.dex */
final class ProportionalFunction implements ChronoFunction<ChronoEntity<?>, BigDecimal> {
    private final ChronoElement<? extends Number> element;
    private final boolean extendedRange;

    ProportionalFunction(ChronoElement<? extends Number> chronoElement, boolean z) {
        this.element = chronoElement;
        this.extendedRange = z;
    }

    @Override // net.time4j.engine.ChronoFunction
    public BigDecimal apply(ChronoEntity<?> chronoEntity) {
        long longValue = ((Number) chronoEntity.get(this.element)).longValue();
        long longValue2 = ((Number) chronoEntity.getMinimum(this.element)).longValue();
        long longValue3 = ((Number) chronoEntity.getMaximum(this.element)).longValue();
        if (longValue > longValue3) {
            longValue = longValue3;
        }
        if (longValue == longValue2) {
            return BigDecimal.ZERO;
        }
        if (this.extendedRange && (chronoEntity instanceof PlainTime) && !((PlainTime) PlainTime.class.cast(chronoEntity)).hasReducedRange(this.element)) {
            if (longValue == longValue3) {
                return BigDecimal.ONE;
            }
            longValue3--;
        }
        return new BigDecimal(longValue - longValue2).setScale(15).divide(new BigDecimal((longValue3 - longValue2) + 1), RoundingMode.HALF_UP).stripTrailingZeros();
    }
}

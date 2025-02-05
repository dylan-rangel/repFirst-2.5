package net.time4j;

import net.time4j.engine.ChronoElement;
import net.time4j.engine.ChronoOperator;
import net.time4j.engine.StdOperator;

/* loaded from: classes3.dex */
final class DateOperator extends ElementOperator<PlainDate> {
    private final Object opDelegate;

    DateOperator(ChronoElement<?> chronoElement, int i) {
        this(chronoElement, i, null);
    }

    DateOperator(ChronoElement<?> chronoElement, int i, Object obj) {
        super(chronoElement, i);
        switch (i) {
            case -1:
                this.opDelegate = newValue(chronoElement, obj);
                return;
            case 0:
                this.opDelegate = StdOperator.minimized(chronoElement);
                return;
            case 1:
                this.opDelegate = StdOperator.maximized(chronoElement);
                return;
            case 2:
                this.opDelegate = StdOperator.decremented(chronoElement);
                return;
            case 3:
                this.opDelegate = StdOperator.incremented(chronoElement);
                return;
            case 4:
                this.opDelegate = StdOperator.atFloor(chronoElement);
                return;
            case 5:
                this.opDelegate = StdOperator.atCeiling(chronoElement);
                return;
            case 6:
                this.opDelegate = lenient(chronoElement, obj);
                return;
            default:
                throw new AssertionError("Unknown: " + getType());
        }
    }

    @Override // net.time4j.engine.ChronoOperator
    public PlainDate apply(PlainDate plainDate) {
        return (PlainDate) ((ChronoOperator) this.opDelegate).apply(plainDate);
    }

    @Override // net.time4j.ElementOperator
    ChronoOperator<PlainTimestamp> onTimestamp() {
        return (ChronoOperator) this.opDelegate;
    }

    private static <V> Object newValue(ChronoElement<V> chronoElement, Object obj) {
        return ValueOperator.of(StdOperator.newValue(chronoElement.getType().cast(obj), chronoElement), obj);
    }

    private static <V> Object lenient(ChronoElement<V> chronoElement, Object obj) {
        return ValueOperator.of(StdOperator.setLenient(chronoElement.getType().cast(obj), chronoElement), obj);
    }
}

package net.time4j;

import net.time4j.engine.ChronoElement;
import net.time4j.engine.ChronoEntity;
import net.time4j.engine.ChronoOperator;
import net.time4j.engine.StdOperator;

/* loaded from: classes3.dex */
final class TimeOperator extends ElementOperator<PlainTime> {
    private final Object opDelegate;

    TimeOperator(ChronoElement<?> chronoElement, int i) {
        this(chronoElement, i, null);
    }

    TimeOperator(ChronoElement<?> chronoElement, int i, Object obj) {
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
                this.opDelegate = child(chronoElement, false);
                return;
            case 5:
                this.opDelegate = child(chronoElement, true);
                return;
            case 6:
                this.opDelegate = lenient(chronoElement, obj);
                return;
            default:
                throw new AssertionError("Unknown: " + getType());
        }
    }

    @Override // net.time4j.engine.ChronoOperator
    public PlainTime apply(PlainTime plainTime) {
        return (PlainTime) ((ChronoOperator) this.opDelegate).apply(plainTime);
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

    private static <V, T extends ChronoEntity<T>> ChronoOperator<T> child(ChronoElement<V> chronoElement, boolean z) {
        String name = chronoElement.name();
        if (name.equals("MILLI_OF_SECOND") || name.equals("MILLI_OF_DAY")) {
            return new FractionOperator('3', z);
        }
        if (name.equals("MICRO_OF_SECOND") || name.equals("MICRO_OF_DAY")) {
            return new FractionOperator('6', z);
        }
        if (name.equals("NANO_OF_SECOND") || name.equals("NANO_OF_DAY")) {
            return new FractionOperator('9', z);
        }
        if (z) {
            return StdOperator.atCeiling(chronoElement);
        }
        return StdOperator.atFloor(chronoElement);
    }
}

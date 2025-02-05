package net.time4j;

import net.time4j.base.MathUtils;
import net.time4j.engine.ChronoUnit;
import net.time4j.scale.TimeScale;

/* loaded from: classes3.dex */
public enum SI implements ChronoUnit {
    SECONDS(1.0d),
    NANOSECONDS(1.0E-9d);

    private final double length;

    @Override // net.time4j.engine.ChronoUnit
    public boolean isCalendrical() {
        return false;
    }

    SI(double d) {
        this.length = d;
    }

    public long between(Moment moment, Moment moment2) {
        Moment.check1972(moment);
        Moment.check1972(moment2);
        int i = AnonymousClass1.$SwitchMap$net$time4j$SI[ordinal()];
        if (i == 1) {
            long elapsedTime = moment2.getElapsedTime(TimeScale.UTC) - moment.getElapsedTime(TimeScale.UTC);
            return elapsedTime < 0 ? moment2.getNanosecond() > moment.getNanosecond() ? elapsedTime + 1 : elapsedTime : (elapsedTime <= 0 || moment2.getNanosecond() >= moment.getNanosecond()) ? elapsedTime : elapsedTime - 1;
        }
        if (i == 2) {
            return MathUtils.safeAdd(MathUtils.safeMultiply(MathUtils.safeSubtract(moment2.getElapsedTime(TimeScale.UTC), moment.getElapsedTime(TimeScale.UTC)), 1000000000L), moment2.getNanosecond() - moment.getNanosecond());
        }
        throw new UnsupportedOperationException();
    }

    /* renamed from: net.time4j.SI$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$time4j$SI;

        static {
            int[] iArr = new int[SI.values().length];
            $SwitchMap$net$time4j$SI = iArr;
            try {
                iArr[SI.SECONDS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$net$time4j$SI[SI.NANOSECONDS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    @Override // net.time4j.engine.ChronoUnit
    public double getLength() {
        return this.length;
    }
}

package net.time4j.tz;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import net.time4j.base.GregorianDate;
import net.time4j.base.GregorianMath;
import net.time4j.base.MathUtils;
import net.time4j.base.WallTime;

/* loaded from: classes3.dex */
final class TransitionResolver implements TransitionStrategy, Serializable {
    private static final Map<Integer, TransitionResolver> INSTANCES = new HashMap();
    private static final String NO_HISTORY = "Timezone provider does not expose its transition history.";
    private static final long serialVersionUID = 1790434289322009750L;
    private final transient GapResolver gapResolver;
    private final transient OverlapResolver overlapResolver;

    static {
        for (GapResolver gapResolver : GapResolver.values()) {
            for (OverlapResolver overlapResolver : OverlapResolver.values()) {
                INSTANCES.put(Integer.valueOf((gapResolver.ordinal() * 2) + overlapResolver.ordinal()), new TransitionResolver(gapResolver, overlapResolver));
            }
        }
    }

    private TransitionResolver(GapResolver gapResolver, OverlapResolver overlapResolver) {
        this.gapResolver = gapResolver;
        this.overlapResolver = overlapResolver;
    }

    @Override // net.time4j.tz.TransitionStrategy
    public long resolve(GregorianDate gregorianDate, WallTime wallTime, Timezone timezone) {
        long localSeconds;
        int totalOffset;
        int year = gregorianDate.getYear();
        int month = gregorianDate.getMonth();
        int dayOfMonth = gregorianDate.getDayOfMonth();
        int hour = wallTime.getHour();
        int minute = wallTime.getMinute();
        int second = wallTime.getSecond();
        TransitionHistory history = timezone.getHistory();
        if (history != null || this.overlapResolver != OverlapResolver.LATER_OFFSET || (this.gapResolver != GapResolver.PUSH_FORWARD && this.gapResolver != GapResolver.ABORT)) {
            if (history == null) {
                throw new UnsupportedOperationException(NO_HISTORY);
            }
            ZonalTransition conflictTransition = history.getConflictTransition(gregorianDate, wallTime);
            if (conflictTransition != null) {
                if (conflictTransition.isGap()) {
                    int i = AnonymousClass1.$SwitchMap$net$time4j$tz$GapResolver[this.gapResolver.ordinal()];
                    if (i == 1) {
                        localSeconds = toLocalSeconds(year, month, dayOfMonth, hour, minute, second) + conflictTransition.getSize();
                        totalOffset = conflictTransition.getTotalOffset();
                    } else {
                        if (i == 2) {
                            return conflictTransition.getPosixTime();
                        }
                        if (i == 3) {
                            throwInvalidException(gregorianDate, wallTime, timezone);
                        } else {
                            throw new UnsupportedOperationException(this.gapResolver.name());
                        }
                    }
                } else if (conflictTransition.isOverlap()) {
                    localSeconds = toLocalSeconds(year, month, dayOfMonth, hour, minute, second);
                    totalOffset = conflictTransition.getTotalOffset();
                    if (this.overlapResolver == OverlapResolver.EARLIER_OFFSET) {
                        totalOffset = conflictTransition.getPreviousOffset();
                    }
                }
            }
            return toLocalSeconds(year, month, dayOfMonth, hour, minute, second) - history.getValidOffsets(gregorianDate, wallTime).get(0).getIntegralAmount();
        }
        GregorianCalendar gregorianCalendar = new GregorianCalendar(TimeZone.getTimeZone(timezone.getID().canonical()));
        gregorianCalendar.setGregorianChange(new Date(Long.MIN_VALUE));
        gregorianCalendar.set(14, 0);
        gregorianCalendar.set(year, month - 1, dayOfMonth, hour, minute, second);
        int i2 = gregorianCalendar.get(1);
        int i3 = 1 + gregorianCalendar.get(2);
        int i4 = gregorianCalendar.get(5);
        int i5 = gregorianCalendar.get(11);
        int i6 = gregorianCalendar.get(12);
        int i7 = gregorianCalendar.get(13);
        if (this.gapResolver == GapResolver.ABORT && (year != i2 || month != i3 || dayOfMonth != i4 || hour != i5 || minute != i6 || second != i7)) {
            throwInvalidException(gregorianDate, wallTime, timezone);
        }
        localSeconds = toLocalSeconds(i2, i3, i4, i5, i6, i7);
        totalOffset = timezone.getOffset(gregorianDate, wallTime).getIntegralAmount();
        return localSeconds - totalOffset;
    }

    /* renamed from: net.time4j.tz.TransitionResolver$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$time4j$tz$GapResolver;

        static {
            int[] iArr = new int[GapResolver.values().length];
            $SwitchMap$net$time4j$tz$GapResolver = iArr;
            try {
                iArr[GapResolver.PUSH_FORWARD.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$net$time4j$tz$GapResolver[GapResolver.NEXT_VALID_TIME.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$net$time4j$tz$GapResolver[GapResolver.ABORT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    @Override // net.time4j.tz.TransitionStrategy
    public ZonalOffset getOffset(GregorianDate gregorianDate, WallTime wallTime, Timezone timezone) {
        TransitionHistory history = timezone.getHistory();
        if (history == null && this.overlapResolver == OverlapResolver.LATER_OFFSET && (this.gapResolver == GapResolver.PUSH_FORWARD || this.gapResolver == GapResolver.ABORT)) {
            if (this.gapResolver == GapResolver.ABORT && timezone.isInvalid(gregorianDate, wallTime)) {
                throwInvalidException(gregorianDate, wallTime, timezone);
            }
            return timezone.getOffset(gregorianDate, wallTime);
        }
        if (history == null) {
            throw new UnsupportedOperationException(NO_HISTORY);
        }
        ZonalTransition conflictTransition = history.getConflictTransition(gregorianDate, wallTime);
        if (conflictTransition != null) {
            int totalOffset = conflictTransition.getTotalOffset();
            if (conflictTransition.isGap()) {
                if (this.gapResolver == GapResolver.ABORT) {
                    throwInvalidException(gregorianDate, wallTime, timezone);
                } else {
                    return ZonalOffset.ofTotalSeconds(totalOffset);
                }
            } else if (conflictTransition.isOverlap()) {
                if (this.overlapResolver == OverlapResolver.EARLIER_OFFSET) {
                    totalOffset = conflictTransition.getPreviousOffset();
                }
                return ZonalOffset.ofTotalSeconds(totalOffset);
            }
        }
        return history.getValidOffsets(gregorianDate, wallTime).get(0);
    }

    @Override // net.time4j.tz.TransitionStrategy
    public TransitionStrategy using(OverlapResolver overlapResolver) {
        return overlapResolver == this.overlapResolver ? this : this.gapResolver.and(overlapResolver);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(32);
        sb.append(getClass().getName());
        sb.append(":[gap=");
        sb.append(this.gapResolver);
        sb.append(",overlap=");
        sb.append(this.overlapResolver);
        sb.append(']');
        return sb.toString();
    }

    static TransitionResolver of(GapResolver gapResolver, OverlapResolver overlapResolver) {
        return INSTANCES.get(Integer.valueOf((gapResolver.ordinal() * 2) + overlapResolver.ordinal()));
    }

    int getKey() {
        return (this.gapResolver.ordinal() * 2) + this.overlapResolver.ordinal();
    }

    private static long toLocalSeconds(int i, int i2, int i3, int i4, int i5, int i6) {
        return MathUtils.safeMultiply(MathUtils.safeSubtract(GregorianMath.toMJD(i, i2, i3), 40587L), 86400L) + (i4 * 3600) + (i5 * 60) + i6;
    }

    private static void throwInvalidException(GregorianDate gregorianDate, WallTime wallTime, Timezone timezone) {
        throw new IllegalArgumentException("Invalid local timestamp due to timezone transition: local-date=" + gregorianDate + ", local-time=" + wallTime + " [" + timezone.getID().canonical() + "]");
    }

    private Object writeReplace() {
        return new SPX(this, 13);
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Serialization proxy required.");
    }
}

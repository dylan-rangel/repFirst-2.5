package net.time4j.tz;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.util.List;
import net.time4j.base.GregorianDate;
import net.time4j.base.UnixTime;
import net.time4j.base.WallTime;

/* loaded from: classes3.dex */
final class HistorizedTimezone extends Timezone {
    private static final long serialVersionUID = 1738909257417361021L;
    private final transient TransitionHistory history;
    private final transient TZID id;
    private final transient TransitionStrategy strategy;

    HistorizedTimezone(TZID tzid, TransitionHistory transitionHistory) {
        this(tzid, transitionHistory, Timezone.DEFAULT_CONFLICT_STRATEGY);
    }

    HistorizedTimezone(TZID tzid, TransitionHistory transitionHistory, TransitionStrategy transitionStrategy) {
        if (tzid == null) {
            throw new NullPointerException("Missing timezone id.");
        }
        if ((tzid instanceof ZonalOffset) && !transitionHistory.isEmpty()) {
            throw new IllegalArgumentException("Fixed zonal offset can't be combined with offset transitions: " + tzid.canonical());
        }
        if (transitionHistory == null) {
            throw new NullPointerException("Missing timezone history.");
        }
        if (transitionStrategy == null) {
            throw new NullPointerException("Missing transition strategy.");
        }
        this.id = tzid;
        this.history = transitionHistory;
        this.strategy = transitionStrategy;
    }

    @Override // net.time4j.tz.Timezone
    public TZID getID() {
        return this.id;
    }

    @Override // net.time4j.tz.Timezone
    public ZonalOffset getOffset(UnixTime unixTime) {
        ZonalTransition startTransition = this.history.getStartTransition(unixTime);
        if (startTransition == null) {
            return this.history.getInitialOffset();
        }
        return ZonalOffset.ofTotalSeconds(startTransition.getTotalOffset());
    }

    @Override // net.time4j.tz.Timezone
    public ZonalOffset getStandardOffset(UnixTime unixTime) {
        ZonalTransition startTransition = this.history.getStartTransition(unixTime);
        if (startTransition == null) {
            return this.history.getInitialOffset();
        }
        return ZonalOffset.ofTotalSeconds(startTransition.getStandardOffset());
    }

    @Override // net.time4j.tz.Timezone
    public ZonalOffset getDaylightSavingOffset(UnixTime unixTime) {
        ZonalTransition startTransition = this.history.getStartTransition(unixTime);
        if (startTransition == null) {
            return ZonalOffset.UTC;
        }
        return ZonalOffset.ofTotalSeconds(startTransition.getDaylightSavingOffset());
    }

    @Override // net.time4j.tz.Timezone
    public ZonalOffset getOffset(GregorianDate gregorianDate, WallTime wallTime) {
        List<ZonalOffset> validOffsets = this.history.getValidOffsets(gregorianDate, wallTime);
        if (validOffsets.size() == 1) {
            return validOffsets.get(0);
        }
        return ZonalOffset.ofTotalSeconds(this.history.getConflictTransition(gregorianDate, wallTime).getTotalOffset());
    }

    @Override // net.time4j.tz.Timezone
    public boolean isInvalid(GregorianDate gregorianDate, WallTime wallTime) {
        ZonalTransition conflictTransition = this.history.getConflictTransition(gregorianDate, wallTime);
        return conflictTransition != null && conflictTransition.isGap();
    }

    @Override // net.time4j.tz.Timezone
    public boolean isDaylightSaving(UnixTime unixTime) {
        UnixTime previousTime;
        ZonalTransition startTransition;
        ZonalTransition startTransition2 = this.history.getStartTransition(unixTime);
        if (startTransition2 == null) {
            return false;
        }
        int daylightSavingOffset = startTransition2.getDaylightSavingOffset();
        if (daylightSavingOffset > 0) {
            return true;
        }
        if (daylightSavingOffset < 0 || !this.history.hasNegativeDST() || (startTransition = this.history.getStartTransition((previousTime = SimpleUT.previousTime(startTransition2.getPosixTime(), 0)))) == null) {
            return false;
        }
        if (startTransition.getStandardOffset() == startTransition2.getStandardOffset()) {
            return startTransition.getDaylightSavingOffset() < 0;
        }
        return isDaylightSaving(previousTime);
    }

    @Override // net.time4j.tz.Timezone
    public boolean isFixed() {
        return this.history.isEmpty();
    }

    @Override // net.time4j.tz.Timezone
    public TransitionHistory getHistory() {
        return this.history;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof HistorizedTimezone)) {
            return false;
        }
        HistorizedTimezone historizedTimezone = (HistorizedTimezone) obj;
        return this.id.canonical().equals(historizedTimezone.id.canonical()) && this.history.equals(historizedTimezone.history) && this.strategy.equals(historizedTimezone.strategy);
    }

    public int hashCode() {
        return this.id.canonical().hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(32);
        sb.append('[');
        sb.append(getClass().getName());
        sb.append(':');
        sb.append(this.id.canonical());
        sb.append(",history={");
        sb.append(this.history);
        sb.append("},strategy=");
        sb.append(this.strategy);
        sb.append(']');
        return sb.toString();
    }

    @Override // net.time4j.tz.Timezone
    public TransitionStrategy getStrategy() {
        return this.strategy;
    }

    @Override // net.time4j.tz.Timezone
    public Timezone with(TransitionStrategy transitionStrategy) {
        return this.strategy == transitionStrategy ? this : new HistorizedTimezone(this.id, this.history, transitionStrategy);
    }

    private Object writeReplace() {
        return new SPX(this, 14);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException {
        throw new InvalidObjectException("Serialization proxy required.");
    }
}

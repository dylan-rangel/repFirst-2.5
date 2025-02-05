package net.time4j.tz;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import net.time4j.base.GregorianDate;
import net.time4j.base.UnixTime;
import net.time4j.base.WallTime;

/* loaded from: classes3.dex */
final class SingleOffsetTimezone extends Timezone implements TransitionHistory {
    private static final SingleOffsetTimezone UTC = new SingleOffsetTimezone(ZonalOffset.UTC);
    private static final long serialVersionUID = 7807230388259573234L;
    private final ZonalOffset offset;

    @Override // net.time4j.tz.TransitionHistory
    public ZonalTransition getConflictTransition(GregorianDate gregorianDate, WallTime wallTime) {
        return null;
    }

    @Override // net.time4j.tz.Timezone
    public TransitionHistory getHistory() {
        return this;
    }

    @Override // net.time4j.tz.TransitionHistory
    public ZonalTransition getNextTransition(UnixTime unixTime) {
        return null;
    }

    @Override // net.time4j.tz.TransitionHistory
    public ZonalTransition getStartTransition(UnixTime unixTime) {
        return null;
    }

    @Override // net.time4j.tz.TransitionHistory
    public boolean hasNegativeDST() {
        return false;
    }

    @Override // net.time4j.tz.Timezone
    public boolean isDaylightSaving(UnixTime unixTime) {
        return false;
    }

    @Override // net.time4j.tz.TransitionHistory
    public boolean isEmpty() {
        return true;
    }

    @Override // net.time4j.tz.Timezone
    public boolean isFixed() {
        return true;
    }

    @Override // net.time4j.tz.Timezone
    public boolean isInvalid(GregorianDate gregorianDate, WallTime wallTime) {
        return false;
    }

    @Override // net.time4j.tz.Timezone
    public Timezone with(TransitionStrategy transitionStrategy) {
        return this;
    }

    private SingleOffsetTimezone(ZonalOffset zonalOffset) {
        if (zonalOffset.getFractionalAmount() == 0) {
            this.offset = zonalOffset;
        } else {
            int integralAmount = zonalOffset.getIntegralAmount();
            this.offset = ZonalOffset.ofTotalSeconds(zonalOffset.getFractionalAmount() < 0 ? integralAmount - 1 : integralAmount);
        }
    }

    @Override // net.time4j.tz.Timezone
    public TZID getID() {
        return this.offset;
    }

    @Override // net.time4j.tz.Timezone
    public ZonalOffset getOffset(UnixTime unixTime) {
        return this.offset;
    }

    @Override // net.time4j.tz.Timezone
    public ZonalOffset getStandardOffset(UnixTime unixTime) {
        return this.offset;
    }

    @Override // net.time4j.tz.Timezone
    public ZonalOffset getDaylightSavingOffset(UnixTime unixTime) {
        return ZonalOffset.UTC;
    }

    @Override // net.time4j.tz.Timezone
    public ZonalOffset getOffset(GregorianDate gregorianDate, WallTime wallTime) {
        return this.offset;
    }

    @Override // net.time4j.tz.TransitionHistory
    public List<ZonalTransition> getStdTransitions() {
        return Collections.emptyList();
    }

    @Override // net.time4j.tz.TransitionHistory
    public List<ZonalTransition> getTransitions(UnixTime unixTime, UnixTime unixTime2) {
        return Collections.emptyList();
    }

    @Override // net.time4j.tz.TransitionHistory
    public List<ZonalOffset> getValidOffsets(GregorianDate gregorianDate, WallTime wallTime) {
        return Collections.singletonList(this.offset);
    }

    @Override // net.time4j.tz.TransitionHistory
    public ZonalOffset getInitialOffset() {
        return this.offset;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof SingleOffsetTimezone) {
            return this.offset.equals(((SingleOffsetTimezone) obj).offset);
        }
        return false;
    }

    public int hashCode() {
        return this.offset.hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(32);
        sb.append('[');
        sb.append(getClass().getName());
        sb.append(':');
        sb.append(this.offset);
        sb.append(']');
        return sb.toString();
    }

    @Override // net.time4j.tz.Timezone
    public String getDisplayName(NameStyle nameStyle, Locale locale) {
        if (nameStyle.isAbbreviation()) {
            return this.offset.toString();
        }
        return this.offset.canonical();
    }

    @Override // net.time4j.tz.Timezone
    public TransitionStrategy getStrategy() {
        return DEFAULT_CONFLICT_STRATEGY;
    }

    static SingleOffsetTimezone of(ZonalOffset zonalOffset) {
        if (zonalOffset.getIntegralAmount() == 0 && zonalOffset.getFractionalAmount() == 0) {
            return UTC;
        }
        return new SingleOffsetTimezone(zonalOffset);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        if (this.offset.getFractionalAmount() != 0) {
            throw new InvalidObjectException("Fractional offset is invalid.");
        }
    }
}

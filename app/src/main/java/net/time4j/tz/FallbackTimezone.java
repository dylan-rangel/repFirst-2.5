package net.time4j.tz;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import net.time4j.base.GregorianDate;
import net.time4j.base.UnixTime;
import net.time4j.base.WallTime;

/* loaded from: classes3.dex */
final class FallbackTimezone extends Timezone {
    private static final long serialVersionUID = -2894726563499525332L;
    private final Timezone fallback;
    private final TZID tzid;

    FallbackTimezone(TZID tzid, Timezone timezone) {
        if (tzid == null || timezone == null) {
            throw null;
        }
        this.tzid = tzid;
        this.fallback = timezone;
    }

    @Override // net.time4j.tz.Timezone
    public TZID getID() {
        return this.tzid;
    }

    @Override // net.time4j.tz.Timezone
    public ZonalOffset getOffset(UnixTime unixTime) {
        return this.fallback.getOffset(unixTime);
    }

    @Override // net.time4j.tz.Timezone
    public ZonalOffset getStandardOffset(UnixTime unixTime) {
        return this.fallback.getStandardOffset(unixTime);
    }

    @Override // net.time4j.tz.Timezone
    public ZonalOffset getDaylightSavingOffset(UnixTime unixTime) {
        return this.fallback.getDaylightSavingOffset(unixTime);
    }

    @Override // net.time4j.tz.Timezone
    public ZonalOffset getOffset(GregorianDate gregorianDate, WallTime wallTime) {
        return this.fallback.getOffset(gregorianDate, wallTime);
    }

    @Override // net.time4j.tz.Timezone
    public boolean isInvalid(GregorianDate gregorianDate, WallTime wallTime) {
        return this.fallback.isInvalid(gregorianDate, wallTime);
    }

    @Override // net.time4j.tz.Timezone
    public boolean isDaylightSaving(UnixTime unixTime) {
        return this.fallback.isDaylightSaving(unixTime);
    }

    @Override // net.time4j.tz.Timezone
    public boolean isFixed() {
        return this.fallback.isFixed();
    }

    @Override // net.time4j.tz.Timezone
    public TransitionHistory getHistory() {
        return this.fallback.getHistory();
    }

    @Override // net.time4j.tz.Timezone
    public TransitionStrategy getStrategy() {
        return this.fallback.getStrategy();
    }

    @Override // net.time4j.tz.Timezone
    public Timezone with(TransitionStrategy transitionStrategy) {
        return new FallbackTimezone(this.tzid, this.fallback.with(transitionStrategy));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FallbackTimezone)) {
            return false;
        }
        FallbackTimezone fallbackTimezone = (FallbackTimezone) obj;
        return this.tzid.canonical().equals(fallbackTimezone.tzid.canonical()) && this.fallback.equals(fallbackTimezone.fallback);
    }

    public int hashCode() {
        return this.tzid.canonical().hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(32);
        sb.append('[');
        sb.append(getClass().getName());
        sb.append(':');
        sb.append(this.tzid.canonical());
        sb.append(",fallback=");
        sb.append(this.fallback);
        sb.append(']');
        return sb.toString();
    }

    Timezone getFallback() {
        return this.fallback;
    }

    private Object writeReplace() {
        return new SPX(this, 12);
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Serialization proxy required.");
    }
}

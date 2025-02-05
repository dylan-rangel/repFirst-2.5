package net.time4j.tz.model;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import net.time4j.Month;
import net.time4j.PlainDate;
import net.time4j.Weekday;
import net.time4j.base.GregorianMath;

/* loaded from: classes3.dex */
final class LastWeekdayPattern extends GregorianTimezoneRule {
    private static final long serialVersionUID = -946839310332554772L;
    private final transient byte dayOfWeek;

    @Override // net.time4j.tz.model.DaylightSavingRule
    int getType() {
        return 122;
    }

    LastWeekdayPattern(Month month, Weekday weekday, int i, OffsetIndicator offsetIndicator, int i2) {
        super(month, i, offsetIndicator, i2);
        this.dayOfWeek = (byte) weekday.getValue();
    }

    @Override // net.time4j.tz.model.GregorianTimezoneRule
    protected PlainDate getDate0(int i) {
        byte monthValue = getMonthValue();
        int lengthOfMonth = GregorianMath.getLengthOfMonth(i, monthValue);
        int dayOfWeek = GregorianMath.getDayOfWeek(i, monthValue, lengthOfMonth) - this.dayOfWeek;
        if (dayOfWeek < 0) {
            dayOfWeek += 7;
        }
        return PlainDate.of(i, monthValue, lengthOfMonth - dayOfWeek);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LastWeekdayPattern)) {
            return false;
        }
        LastWeekdayPattern lastWeekdayPattern = (LastWeekdayPattern) obj;
        return this.dayOfWeek == lastWeekdayPattern.dayOfWeek && super.isEqual(lastWeekdayPattern);
    }

    public int hashCode() {
        return (this.dayOfWeek * 17) + (getMonthValue() * 37);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(64);
        sb.append("LastDayOfWeekPattern:[month=");
        sb.append((int) getMonthValue());
        sb.append(",day-of-week=");
        sb.append(Weekday.valueOf(this.dayOfWeek));
        sb.append(",day-overflow=");
        sb.append(getDayOverflow());
        sb.append(",time-of-day=");
        sb.append(getTimeOfDay());
        sb.append(",offset-indicator=");
        sb.append(getIndicator());
        sb.append(",dst-offset=");
        sb.append(getSavings());
        sb.append(']');
        return sb.toString();
    }

    byte getDayOfWeek() {
        return this.dayOfWeek;
    }

    private Object writeReplace() {
        return new SPX(this, getType());
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException {
        throw new InvalidObjectException("Serialization proxy required.");
    }
}

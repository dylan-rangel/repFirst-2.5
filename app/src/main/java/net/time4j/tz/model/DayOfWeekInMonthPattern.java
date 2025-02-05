package net.time4j.tz.model;

import com.google.android.gms.auth.api.credentials.CredentialsApi;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import net.time4j.CalendarUnit;
import net.time4j.Month;
import net.time4j.PlainDate;
import net.time4j.Weekday;
import net.time4j.base.GregorianMath;

/* loaded from: classes3.dex */
final class DayOfWeekInMonthPattern extends GregorianTimezoneRule {
    private static final long serialVersionUID = -7354650946442523175L;
    private final transient boolean after;
    private final transient byte dayOfMonth;
    private final transient byte dayOfWeek;

    @Override // net.time4j.tz.model.DaylightSavingRule
    int getType() {
        return 121;
    }

    DayOfWeekInMonthPattern(Month month, int i, Weekday weekday, int i2, OffsetIndicator offsetIndicator, int i3, boolean z) {
        super(month, i2, offsetIndicator, i3);
        GregorianMath.checkDate(CredentialsApi.CREDENTIAL_PICKER_REQUEST_CODE, month.getValue(), i);
        this.dayOfMonth = (byte) i;
        this.dayOfWeek = (byte) weekday.getValue();
        this.after = z;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.time4j.tz.model.GregorianTimezoneRule
    protected PlainDate getDate0(int i) {
        byte monthValue = getMonthValue();
        int dayOfWeek = GregorianMath.getDayOfWeek(i, monthValue, this.dayOfMonth);
        PlainDate of = PlainDate.of(i, monthValue, this.dayOfMonth);
        byte b = this.dayOfWeek;
        if (dayOfWeek == b) {
            return of;
        }
        int i2 = dayOfWeek - b;
        int i3 = -1;
        if (this.after) {
            i2 = -i2;
            i3 = 1;
        }
        if (i2 < 0) {
            i2 += 7;
        }
        return (PlainDate) of.plus(i2 * i3, CalendarUnit.DAYS);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DayOfWeekInMonthPattern)) {
            return false;
        }
        DayOfWeekInMonthPattern dayOfWeekInMonthPattern = (DayOfWeekInMonthPattern) obj;
        return this.dayOfMonth == dayOfWeekInMonthPattern.dayOfMonth && this.dayOfWeek == dayOfWeekInMonthPattern.dayOfWeek && this.after == dayOfWeekInMonthPattern.after && super.isEqual(dayOfWeekInMonthPattern);
    }

    public int hashCode() {
        return this.dayOfMonth + ((this.dayOfWeek + (getMonthValue() * 37)) * 17) + (this.after ? 1 : 0);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(64);
        sb.append("DayOfWeekInMonthPattern:[month=");
        sb.append((int) getMonthValue());
        sb.append(",dayOfMonth=");
        sb.append((int) this.dayOfMonth);
        sb.append(",dayOfWeek=");
        sb.append(Weekday.valueOf(this.dayOfWeek));
        sb.append(",day-overflow=");
        sb.append(getDayOverflow());
        sb.append(",time-of-day=");
        sb.append(getTimeOfDay());
        sb.append(",offset-indicator=");
        sb.append(getIndicator());
        sb.append(",dst-offset=");
        sb.append(getSavings());
        sb.append(",after=");
        sb.append(this.after);
        sb.append(']');
        return sb.toString();
    }

    int getDayOfMonth() {
        return this.dayOfMonth;
    }

    byte getDayOfWeek() {
        return this.dayOfWeek;
    }

    boolean isAfter() {
        return this.after;
    }

    private Object writeReplace() {
        return new SPX(this, getType());
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException {
        throw new InvalidObjectException("Serialization proxy required.");
    }
}

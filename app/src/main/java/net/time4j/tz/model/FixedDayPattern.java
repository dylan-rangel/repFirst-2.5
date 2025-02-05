package net.time4j.tz.model;

import com.google.android.gms.auth.api.credentials.CredentialsApi;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import net.time4j.Month;
import net.time4j.PlainDate;
import net.time4j.base.GregorianMath;

/* loaded from: classes3.dex */
final class FixedDayPattern extends GregorianTimezoneRule {
    private static final long serialVersionUID = 3957240859230862745L;
    private final transient byte dayOfMonth;

    @Override // net.time4j.tz.model.DaylightSavingRule
    int getType() {
        return 120;
    }

    FixedDayPattern(Month month, int i, int i2, OffsetIndicator offsetIndicator, int i3) {
        super(month, i2, offsetIndicator, i3);
        GregorianMath.checkDate(CredentialsApi.CREDENTIAL_PICKER_REQUEST_CODE, month.getValue(), i);
        this.dayOfMonth = (byte) i;
    }

    @Override // net.time4j.tz.model.GregorianTimezoneRule
    protected PlainDate getDate0(int i) {
        return PlainDate.of(i, getMonthValue(), this.dayOfMonth);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FixedDayPattern)) {
            return false;
        }
        FixedDayPattern fixedDayPattern = (FixedDayPattern) obj;
        return this.dayOfMonth == fixedDayPattern.dayOfMonth && super.isEqual(fixedDayPattern);
    }

    public int hashCode() {
        return this.dayOfMonth + (getMonthValue() * 37);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(64);
        sb.append("FixedDayPattern:[month=");
        sb.append((int) getMonthValue());
        sb.append(",day-of-month=");
        sb.append((int) this.dayOfMonth);
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

    int getDayOfMonth() {
        return this.dayOfMonth;
    }

    private Object writeReplace() {
        return new SPX(this, getType());
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException {
        throw new InvalidObjectException("Serialization proxy required.");
    }
}

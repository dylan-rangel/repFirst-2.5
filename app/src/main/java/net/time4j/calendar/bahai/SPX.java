package net.time4j.calendar.bahai;

import java.io.Externalizable;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.InvalidObjectException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;

/* loaded from: classes3.dex */
final class SPX implements Externalizable {
    static final int BAHAI = 19;
    private static final long serialVersionUID = 1;
    private transient Object obj;
    private transient int type;

    public SPX() {
    }

    SPX(Object obj, int i) {
        this.obj = obj;
        this.type = i;
    }

    @Override // java.io.Externalizable
    public void writeExternal(ObjectOutput objectOutput) throws IOException {
        objectOutput.writeByte(this.type);
        if (this.type == 19) {
            writeBahai(objectOutput);
            return;
        }
        throw new InvalidClassException("Unsupported calendar type.");
    }

    @Override // java.io.Externalizable
    public void readExternal(ObjectInput objectInput) throws IOException {
        if (objectInput.readByte() == 19) {
            this.obj = readBahai(objectInput);
            return;
        }
        throw new InvalidObjectException("Unknown calendar type.");
    }

    private Object readResolve() throws ObjectStreamException {
        return this.obj;
    }

    private void writeBahai(ObjectOutput objectOutput) throws IOException {
        BadiCalendar badiCalendar = (BadiCalendar) this.obj;
        objectOutput.writeByte(badiCalendar.getKullishai());
        objectOutput.writeByte(badiCalendar.getVahid());
        objectOutput.writeByte(badiCalendar.getYearOfVahid());
        objectOutput.writeByte(badiCalendar.hasMonth() ? badiCalendar.getMonth().getValue() : 0);
        objectOutput.writeByte(badiCalendar.getDayOfDivision());
    }

    private BadiCalendar readBahai(ObjectInput objectInput) throws IOException {
        byte readByte = objectInput.readByte();
        byte readByte2 = objectInput.readByte();
        byte readByte3 = objectInput.readByte();
        byte readByte4 = objectInput.readByte();
        return BadiCalendar.ofComplete(readByte, readByte2, readByte3, readByte4 == 0 ? BadiIntercalaryDays.AYYAM_I_HA : BadiMonth.valueOf(readByte4), objectInput.readByte());
    }
}

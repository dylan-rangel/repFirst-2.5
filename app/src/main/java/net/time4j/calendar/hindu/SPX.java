package net.time4j.calendar.hindu;

import java.io.Externalizable;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.InvalidObjectException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;

/* loaded from: classes3.dex */
final class SPX implements Externalizable {
    static final int HINDU_CAL = 20;
    static final int HINDU_VAR = 21;
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
        int i = this.type;
        if (i == 20) {
            writeHinduCalendar(objectOutput);
        } else {
            if (i == 21) {
                writeHinduVariant(objectOutput);
                return;
            }
            throw new InvalidClassException("Unsupported calendar type.");
        }
    }

    @Override // java.io.Externalizable
    public void readExternal(ObjectInput objectInput) throws IOException {
        byte readByte = objectInput.readByte();
        if (readByte == 20) {
            this.obj = readHinduCalendar(objectInput);
        } else {
            if (readByte == 21) {
                this.obj = readHinduVariant(objectInput);
                return;
            }
            throw new InvalidObjectException("Unknown calendar type.");
        }
    }

    private Object readResolve() throws ObjectStreamException {
        return this.obj;
    }

    private void writeHinduCalendar(ObjectOutput objectOutput) throws IOException {
        HinduCalendar hinduCalendar = (HinduCalendar) this.obj;
        objectOutput.writeUTF(hinduCalendar.getVariant());
        objectOutput.writeLong(hinduCalendar.getDaysSinceEpochUTC());
    }

    private HinduCalendar readHinduCalendar(ObjectInput objectInput) throws IOException {
        HinduVariant from = HinduVariant.from(objectInput.readUTF());
        return from.getCalendarSystem().transform(objectInput.readLong());
    }

    private void writeHinduVariant(ObjectOutput objectOutput) throws IOException {
        objectOutput.writeUTF(((HinduVariant) this.obj).getVariant());
    }

    private HinduVariant readHinduVariant(ObjectInput objectInput) throws IOException {
        return HinduVariant.from(objectInput.readUTF());
    }
}

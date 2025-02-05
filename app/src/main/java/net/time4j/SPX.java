package net.time4j;

import com.google.common.base.Ascii;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.Externalizable;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import net.time4j.DayPeriod;
import net.time4j.engine.TimeSpan;
import net.time4j.scale.TimeScale;

/* loaded from: classes3.dex */
final class SPX implements Externalizable {
    static final int DATE_TYPE = 1;
    static final int DAY_PERIOD_TYPE = 7;
    static final int DURATION_TYPE = 6;
    static final int MACHINE_TIME_TYPE = 5;
    static final int MOMENT_TYPE = 4;
    static final int TIMESTAMP_TYPE = 8;
    static final int TIME_TYPE = 2;
    static final int WEEKMODEL_TYPE = 3;
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
        switch (this.type) {
            case 1:
                writeDate(objectOutput);
                return;
            case 2:
                writeTime(objectOutput);
                return;
            case 3:
                writeWeekmodel(objectOutput);
                return;
            case 4:
                writeMoment(objectOutput);
                return;
            case 5:
                writeMachineTime(objectOutput);
                return;
            case 6:
                writeDuration(objectOutput);
                return;
            case 7:
                writeDayPeriod(objectOutput);
                return;
            case 8:
                writeTimestamp(objectOutput);
                return;
            default:
                throw new InvalidClassException("Unknown serialized type.");
        }
    }

    @Override // java.io.Externalizable
    public void readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
        byte readByte = objectInput.readByte();
        switch ((readByte & 255) >> 4) {
            case 1:
                this.obj = readDate(objectInput, readByte);
                return;
            case 2:
                this.obj = readTime(objectInput);
                return;
            case 3:
                this.obj = readWeekmodel(objectInput, readByte);
                return;
            case 4:
                this.obj = readMoment(objectInput, readByte);
                return;
            case 5:
                this.obj = readMachineTime(objectInput, readByte);
                return;
            case 6:
                this.obj = readDuration(objectInput, readByte);
                return;
            case 7:
                this.obj = readDayPeriod(objectInput, readByte);
                return;
            case 8:
                this.obj = readTimestamp(objectInput, readByte);
                return;
            default:
                throw new StreamCorruptedException("Unknown serialized type.");
        }
    }

    private Object readResolve() throws ObjectStreamException {
        return this.obj;
    }

    private void writeDate(DataOutput dataOutput) throws IOException {
        writeDate((PlainDate) this.obj, 1, dataOutput);
    }

    private static void writeDate(PlainDate plainDate, int i, DataOutput dataOutput) throws IOException {
        int i2;
        int year = plainDate.getYear();
        if (year < 1850 || year > 2100) {
            i2 = Math.abs(year) < 10000 ? 2 : 3;
        } else {
            i2 = 1;
        }
        dataOutput.writeByte((i << 4) | plainDate.getMonth());
        dataOutput.writeByte(plainDate.getDayOfMonth() | (i2 << 5));
        if (i2 == 1) {
            dataOutput.writeByte((year - 1850) - 128);
        } else if (i2 == 2) {
            dataOutput.writeShort(year);
        } else {
            dataOutput.writeInt(year);
        }
    }

    private PlainDate readDate(DataInput dataInput, byte b) throws IOException {
        int readByte;
        int i = b & Ascii.SI;
        byte readByte2 = dataInput.readByte();
        int i2 = (readByte2 >> 5) & 3;
        int i3 = readByte2 & Ascii.US;
        if (i2 == 1) {
            readByte = dataInput.readByte() + 1850 + 128;
        } else if (i2 == 2) {
            readByte = dataInput.readShort();
        } else if (i2 == 3) {
            readByte = dataInput.readInt();
        } else {
            throw new StreamCorruptedException("Unknown year range.");
        }
        return PlainDate.of(readByte, Month.valueOf(i), i3);
    }

    private void writeTime(DataOutput dataOutput) throws IOException {
        PlainTime plainTime = (PlainTime) this.obj;
        dataOutput.writeByte(32);
        writeTime(plainTime, dataOutput);
    }

    private static void writeTime(PlainTime plainTime, DataOutput dataOutput) throws IOException {
        if (plainTime.getNanosecond() == 0) {
            if (plainTime.getSecond() == 0) {
                if (plainTime.getMinute() == 0) {
                    dataOutput.writeByte(~plainTime.getHour());
                    return;
                } else {
                    dataOutput.writeByte(plainTime.getHour());
                    dataOutput.writeByte(~plainTime.getMinute());
                    return;
                }
            }
            dataOutput.writeByte(plainTime.getHour());
            dataOutput.writeByte(plainTime.getMinute());
            dataOutput.writeByte(~plainTime.getSecond());
            return;
        }
        dataOutput.writeByte(plainTime.getHour());
        dataOutput.writeByte(plainTime.getMinute());
        dataOutput.writeByte(plainTime.getSecond());
        dataOutput.writeInt(plainTime.getNanosecond());
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v2, types: [int] */
    private PlainTime readTime(DataInput dataInput) throws IOException {
        int readInt;
        byte readByte = dataInput.readByte();
        if (readByte < 0) {
            return PlainTime.of(~readByte);
        }
        int readByte2 = dataInput.readByte();
        byte b = 0;
        if (readByte2 < 0) {
            readByte2 = ~readByte2;
        } else {
            byte readByte3 = dataInput.readByte();
            if (readByte3 >= 0) {
                readInt = dataInput.readInt();
                b = readByte3;
                return PlainTime.of(readByte, readByte2, b, readInt);
            }
            b = ~readByte3;
        }
        readInt = 0;
        return PlainTime.of(readByte, readByte2, b, readInt);
    }

    private void writeWeekmodel(DataOutput dataOutput) throws IOException {
        Weekmodel weekmodel = (Weekmodel) this.obj;
        boolean z = weekmodel.getStartOfWeekend() == Weekday.SATURDAY && weekmodel.getEndOfWeekend() == Weekday.SUNDAY;
        dataOutput.writeByte(z ? 48 : 49);
        dataOutput.writeByte((weekmodel.getFirstDayOfWeek().getValue() << 4) | weekmodel.getMinimalDaysInFirstWeek());
        if (z) {
            return;
        }
        dataOutput.writeByte(weekmodel.getEndOfWeekend().getValue() | (weekmodel.getStartOfWeekend().getValue() << 4));
    }

    private Object readWeekmodel(DataInput dataInput, byte b) throws IOException {
        byte readByte = dataInput.readByte();
        Weekday valueOf = Weekday.valueOf(readByte >> 4);
        int i = readByte & Ascii.SI;
        Weekday weekday = Weekday.SATURDAY;
        Weekday weekday2 = Weekday.SUNDAY;
        if ((b & Ascii.SI) == 1) {
            byte readByte2 = dataInput.readByte();
            weekday = Weekday.valueOf(readByte2 >> 4);
            weekday2 = Weekday.valueOf(readByte2 & Ascii.SI);
        }
        return Weekmodel.of(valueOf, i, weekday, weekday2);
    }

    private void writeMoment(DataOutput dataOutput) throws IOException {
        ((Moment) this.obj).writeTimestamp(dataOutput);
    }

    private Object readMoment(DataInput dataInput, byte b) throws IOException {
        return Moment.readTimestamp(dataInput, (b & 1) != 0, ((b & 2) >>> 1) != 0);
    }

    private void writeTimestamp(DataOutput dataOutput) throws IOException {
        PlainTimestamp plainTimestamp = (PlainTimestamp) this.obj;
        writeDate(plainTimestamp.getCalendarDate(), 8, dataOutput);
        writeTime(plainTimestamp.getWallTime(), dataOutput);
    }

    private Object readTimestamp(DataInput dataInput, byte b) throws IOException {
        return PlainTimestamp.of(readDate(dataInput, b), readTime(dataInput));
    }

    private void writeDuration(ObjectOutput objectOutput) throws IOException {
        boolean z;
        Duration duration = (Duration) Duration.class.cast(this.obj);
        int size = duration.getTotalLength().size();
        int min = Math.min(size, 6);
        int i = 0;
        while (true) {
            if (i >= min) {
                z = false;
                break;
            } else {
                if (((TimeSpan.Item) duration.getTotalLength().get(i)).getAmount() >= 1000) {
                    z = true;
                    break;
                }
                i++;
            }
        }
        objectOutput.writeByte(z ? 97 : 96);
        objectOutput.writeInt(size);
        for (int i2 = 0; i2 < size; i2++) {
            TimeSpan.Item item = (TimeSpan.Item) duration.getTotalLength().get(i2);
            if (z) {
                objectOutput.writeLong(item.getAmount());
            } else {
                objectOutput.writeInt((int) item.getAmount());
            }
            objectOutput.writeObject(item.getUnit());
        }
        if (size > 0) {
            objectOutput.writeBoolean(duration.isNegative());
        }
    }

    private Object readDuration(ObjectInput objectInput, byte b) throws IOException, ClassNotFoundException {
        boolean z = (b & Ascii.SI) == 1;
        int readInt = objectInput.readInt();
        if (readInt == 0) {
            return Duration.ofZero();
        }
        ArrayList arrayList = new ArrayList(readInt);
        for (int i = 0; i < readInt; i++) {
            arrayList.add(TimeSpan.Item.of(z ? objectInput.readLong() : objectInput.readInt(), (IsoUnit) objectInput.readObject()));
        }
        return new Duration(arrayList, objectInput.readBoolean());
    }

    private void writeDayPeriod(ObjectOutput objectOutput) throws IOException {
        DayPeriod.Element element = (DayPeriod.Element) DayPeriod.Element.class.cast(this.obj);
        Locale locale = element.getLocale();
        int i = element.isFixed() ? 113 : 112;
        if (locale == null) {
            i |= 2;
        }
        objectOutput.writeByte(i);
        if (locale == null) {
            objectOutput.writeObject(element.getCodeMap());
            return;
        }
        String language = locale.getLanguage();
        if (!locale.getCountry().isEmpty()) {
            language = language + "-" + locale.getCountry();
        }
        objectOutput.writeUTF(language);
        objectOutput.writeUTF(element.getCalendarType());
    }

    private Object readDayPeriod(ObjectInput objectInput, byte b) throws IOException, ClassNotFoundException {
        Locale locale;
        boolean z = (b & 1) == 1;
        if ((b & 2) == 2) {
            return new DayPeriod.Element(z, DayPeriod.of((Map<PlainTime, String>) objectInput.readObject()));
        }
        String readUTF = objectInput.readUTF();
        String readUTF2 = objectInput.readUTF();
        int indexOf = readUTF.indexOf("-");
        if (indexOf == -1) {
            locale = new Locale(readUTF);
        } else {
            locale = new Locale(readUTF.substring(0, indexOf), readUTF.substring(indexOf + 1));
        }
        return new DayPeriod.Element(z, locale, readUTF2);
    }

    private void writeMachineTime(ObjectOutput objectOutput) throws IOException {
        MachineTime machineTime = (MachineTime) MachineTime.class.cast(this.obj);
        int i = machineTime.getScale() == TimeScale.UTC ? 81 : 80;
        if (machineTime.getFraction() == 0) {
            objectOutput.writeByte(i);
            objectOutput.writeLong(machineTime.getSeconds());
        } else {
            objectOutput.writeByte(i | 2);
            objectOutput.writeLong(machineTime.getSeconds());
            objectOutput.writeInt(machineTime.getFraction());
        }
    }

    private Object readMachineTime(ObjectInput objectInput, byte b) throws IOException {
        TimeScale timeScale = (b & 1) == 1 ? TimeScale.UTC : TimeScale.POSIX;
        long readLong = objectInput.readLong();
        int readInt = (b & 2) == 2 ? objectInput.readInt() : 0;
        if (timeScale == TimeScale.UTC) {
            return MachineTime.ofSIUnits(readLong, readInt);
        }
        return MachineTime.ofPosixUnits(readLong, readInt);
    }
}

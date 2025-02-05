package net.time4j.history;

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
import net.time4j.PlainDate;
import net.time4j.engine.EpochDays;
import net.time4j.history.internal.HistoricVariant;

/* loaded from: classes3.dex */
final class SPX implements Externalizable {
    private static final int[] EMPTY_INT_ARRAY = new int[0];
    static final int VERSION_1 = 1;
    static final int VERSION_2 = 2;
    static final int VERSION_3 = 3;
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
        int i = this.type;
        if (i == 1 || i == 2 || i == 3) {
            writeHistory(objectOutput);
            return;
        }
        throw new InvalidClassException("Unknown serialized type.");
    }

    @Override // java.io.Externalizable
    public void readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
        ChronoHistory readHistory;
        byte readByte = objectInput.readByte();
        int i = (readByte & 255) >> 4;
        if (i == 1) {
            readHistory = readHistory(objectInput, readByte);
        } else if (i == 2) {
            ChronoHistory readHistory2 = readHistory(objectInput, readByte);
            AncientJulianLeapYears readTriennalState = readTriennalState(objectInput);
            readHistory = readTriennalState != null ? readHistory2.with(readTriennalState) : readHistory2;
        } else if (i == 3) {
            ChronoHistory readHistory3 = readHistory(objectInput, readByte);
            AncientJulianLeapYears readTriennalState2 = readTriennalState(objectInput);
            if (readTriennalState2 != null) {
                readHistory3 = readHistory3.with(readTriennalState2);
            }
            readHistory = readHistory3.with(NewYearStrategy.readFromStream(objectInput)).with(EraPreference.readFromStream(objectInput));
        } else {
            throw new StreamCorruptedException("Unknown serialized type.");
        }
        this.obj = readHistory;
    }

    private Object readResolve() throws ObjectStreamException {
        return this.obj;
    }

    private void writeHistory(DataOutput dataOutput) throws IOException {
        ChronoHistory chronoHistory = (ChronoHistory) this.obj;
        dataOutput.writeByte(chronoHistory.getHistoricVariant().getSerialValue() | (this.type << 4));
        if (chronoHistory.getHistoricVariant() == HistoricVariant.SINGLE_CUTOVER_DATE) {
            dataOutput.writeLong(chronoHistory.getEvents().get(0).start);
        }
        int[] pattern = chronoHistory.hasAncientJulianLeapYears() ? chronoHistory.getAncientJulianLeapYears().getPattern() : EMPTY_INT_ARRAY;
        dataOutput.writeInt(pattern.length);
        for (int i : pattern) {
            dataOutput.writeInt(i);
        }
        chronoHistory.getNewYearStrategy().writeToStream(dataOutput);
        chronoHistory.getEraPreference().writeToStream(dataOutput);
    }

    private ChronoHistory readHistory(DataInput dataInput, byte b) throws IOException, ClassNotFoundException {
        int i = AnonymousClass1.$SwitchMap$net$time4j$history$internal$HistoricVariant[getEnum(b & Ascii.SI).ordinal()];
        if (i == 1) {
            return ChronoHistory.PROLEPTIC_GREGORIAN;
        }
        if (i == 2) {
            return ChronoHistory.PROLEPTIC_JULIAN;
        }
        if (i == 3) {
            return ChronoHistory.PROLEPTIC_BYZANTINE;
        }
        if (i == 4) {
            return ChronoHistory.ofSweden();
        }
        if (i == 5) {
            return ChronoHistory.ofFirstGregorianReform();
        }
        return ChronoHistory.ofGregorianReform(PlainDate.of(dataInput.readLong(), EpochDays.MODIFIED_JULIAN_DATE));
    }

    /* renamed from: net.time4j.history.SPX$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$time4j$history$internal$HistoricVariant;

        static {
            int[] iArr = new int[HistoricVariant.values().length];
            $SwitchMap$net$time4j$history$internal$HistoricVariant = iArr;
            try {
                iArr[HistoricVariant.PROLEPTIC_GREGORIAN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$net$time4j$history$internal$HistoricVariant[HistoricVariant.PROLEPTIC_JULIAN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$net$time4j$history$internal$HistoricVariant[HistoricVariant.PROLEPTIC_BYZANTINE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$net$time4j$history$internal$HistoricVariant[HistoricVariant.SWEDEN.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$net$time4j$history$internal$HistoricVariant[HistoricVariant.INTRODUCTION_ON_1582_10_15.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    private static HistoricVariant getEnum(int i) throws StreamCorruptedException {
        for (HistoricVariant historicVariant : HistoricVariant.values()) {
            if (historicVariant.getSerialValue() == i) {
                return historicVariant;
            }
        }
        throw new StreamCorruptedException("Unknown variant of chronological history.");
    }

    private static AncientJulianLeapYears readTriennalState(DataInput dataInput) throws IOException {
        int readInt = dataInput.readInt();
        if (readInt <= 0) {
            return null;
        }
        int[] iArr = new int[readInt];
        for (int i = 0; i < readInt; i++) {
            iArr[i] = 1 - dataInput.readInt();
        }
        return AncientJulianLeapYears.of(iArr);
    }
}

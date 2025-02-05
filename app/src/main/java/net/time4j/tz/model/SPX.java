package net.time4j.tz.model;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.Externalizable;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.InvalidObjectException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.time4j.Month;
import net.time4j.PlainTime;
import net.time4j.Weekday;
import net.time4j.base.MathUtils;
import net.time4j.tz.ZonalOffset;
import net.time4j.tz.ZonalTransition;

/* loaded from: classes3.dex */
final class SPX implements Externalizable {
    static final int ARRAY_TRANSITION_MODEL_TYPE = 126;
    static final int COMPOSITE_TRANSITION_MODEL_TYPE = 127;
    private static final long DAYS_IN_18_BITS = 22642848000L;
    static final int DAY_OF_WEEK_IN_MONTH_PATTERN_TYPE = 121;
    static final int FIXED_DAY_PATTERN_TYPE = 120;
    static final int LAST_WEEKDAY_PATTERN_TYPE = 122;
    private static final int NO_COMPRESSION = 0;
    private static final long POSIX_TIME_1825 = -4575744000L;
    private static final long QUARTERS_IN_24_BITS = 15040511099L;
    static final int RULE_BASED_TRANSITION_MODEL_TYPE = 125;
    private static final long serialVersionUID = 6526945678752534989L;
    private transient Object obj;
    private transient int type;

    private static int toTimeIndexR(int i) {
        if (i == 0) {
            return 1;
        }
        if (i == 3600) {
            return 2;
        }
        if (i == 7200) {
            return 3;
        }
        if (i == 10800) {
            return 4;
        }
        if (i == 79200) {
            return 5;
        }
        if (i != 82800) {
            return i != 86400 ? 0 : 7;
        }
        return 6;
    }

    private static int toTimeIndexT(int i) {
        if (i == 0) {
            return 1;
        }
        if (i == 60) {
            return 2;
        }
        if (i == 3600) {
            return 3;
        }
        if (i == 7200) {
            return 4;
        }
        if (i == 10800) {
            return 5;
        }
        if (i != 14400) {
            return i != 18000 ? 0 : 7;
        }
        return 6;
    }

    private static int toTimeOfDayR(int i) {
        switch (i) {
            case 1:
                return 0;
            case 2:
                return 3600;
            case 3:
                return 7200;
            case 4:
                return 10800;
            case 5:
                return 79200;
            case 6:
                return 82800;
            case 7:
                return 86400;
            default:
                return -1;
        }
    }

    private static int toTimeOfDayT(int i) {
        switch (i) {
            case 1:
                return 0;
            case 2:
                return 60;
            case 3:
                return 3600;
            case 4:
                return 7200;
            case 5:
                return 10800;
            case 6:
                return 14400;
            case 7:
                return 18000;
            default:
                return -1;
        }
    }

    public SPX() {
    }

    SPX(Object obj, int i) {
        this.obj = obj;
        this.type = i;
    }

    @Override // java.io.Externalizable
    public void writeExternal(ObjectOutput objectOutput) throws IOException {
        objectOutput.writeByte(this.type);
        switch (this.type) {
            case 120:
                writeFixedDayPattern(this.obj, objectOutput);
                return;
            case 121:
                writeDayOfWeekInMonthPattern(this.obj, objectOutput);
                return;
            case 122:
                writeLastDayOfWeekPattern(this.obj, objectOutput);
                return;
            case 123:
            case 124:
            default:
                throw new InvalidClassException("Unknown serialized type.");
            case 125:
                writeRuleBasedTransitionModel(this.obj, objectOutput);
                return;
            case 126:
                writeArrayTransitionModel(this.obj, objectOutput);
                return;
            case 127:
                writeCompositeTransitionModel(this.obj, objectOutput);
                return;
        }
    }

    @Override // java.io.Externalizable
    public void readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
        switch (objectInput.readByte()) {
            case 120:
                this.obj = readFixedDayPattern(objectInput);
                return;
            case 121:
                this.obj = readDayOfWeekInMonthPattern(objectInput);
                return;
            case 122:
                this.obj = readLastDayOfWeekPattern(objectInput);
                return;
            case 123:
            case 124:
            default:
                throw new StreamCorruptedException("Unknown serialized type.");
            case 125:
                this.obj = readRuleBasedTransitionModel(objectInput);
                return;
            case 126:
                this.obj = readArrayTransitionModel(objectInput);
                return;
            case Byte.MAX_VALUE:
                this.obj = readCompositeTransitionModel(objectInput);
                return;
        }
    }

    static void writeTransitions(ZonalTransition[] zonalTransitionArr, int i, DataOutput dataOutput) throws IOException {
        int min = Math.min(i, zonalTransitionArr.length);
        dataOutput.writeInt(min);
        if (min > 0) {
            int previousOffset = zonalTransitionArr[0].getPreviousOffset();
            writeOffset(dataOutput, previousOffset);
            for (int i2 = 0; i2 < min; i2++) {
                previousOffset = writeTransition(zonalTransitionArr[i2], previousOffset, dataOutput);
            }
        }
    }

    private static List<ZonalTransition> readTransitions(ObjectInput objectInput) throws IOException {
        long readByte;
        int i;
        int readInt = objectInput.readInt();
        if (readInt == 0) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList(readInt);
        int readOffset = readOffset(objectInput);
        long j = Long.MIN_VALUE;
        int i2 = readOffset;
        int i3 = 0;
        while (i3 < readInt) {
            byte readByte2 = objectInput.readByte();
            boolean z = readByte2 < 0;
            int i4 = (readByte2 >>> 5) & 3;
            int timeOfDayT = toTimeOfDayT((readByte2 >>> 2) & 7);
            if (timeOfDayT == -1) {
                readByte = objectInput.readLong();
            } else {
                readByte = ((((((((readByte2 & 3) << 16) | ((objectInput.readByte() & 255) << 8)) | (objectInput.readByte() & 255)) * 86400) + POSIX_TIME_1825) + timeOfDayT) - 7200) - readOffset;
            }
            if (readByte <= j) {
                throw new StreamCorruptedException("Wrong order of transitions.");
            }
            if (i4 == 1) {
                i = 0;
            } else if (i4 != 2) {
                i = i4 != 3 ? readOffset(objectInput) : 7200;
            } else {
                i = 3600;
            }
            if (z) {
                readOffset = readOffset(objectInput);
            }
            int i5 = (i == Integer.MAX_VALUE ? 0 : i) + readOffset;
            arrayList.add(new ZonalTransition(readByte, i2, i5, i));
            i3++;
            i2 = i5;
            j = readByte;
        }
        return arrayList;
    }

    private static void writeRules(List<DaylightSavingRule> list, ObjectOutput objectOutput) throws IOException {
        objectOutput.writeByte(list.size());
        for (DaylightSavingRule daylightSavingRule : list) {
            objectOutput.writeByte(daylightSavingRule.getType());
            switch (daylightSavingRule.getType()) {
                case 120:
                    writeFixedDayPattern(daylightSavingRule, objectOutput);
                    break;
                case 121:
                    writeDayOfWeekInMonthPattern(daylightSavingRule, objectOutput);
                    break;
                case 122:
                    writeLastDayOfWeekPattern(daylightSavingRule, objectOutput);
                    break;
                default:
                    objectOutput.writeObject(daylightSavingRule);
                    break;
            }
        }
    }

    private static List<DaylightSavingRule> readRules(ObjectInput objectInput) throws IOException, ClassNotFoundException {
        DaylightSavingRule readFixedDayPattern;
        byte readByte = objectInput.readByte();
        if (readByte == 0) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList(readByte);
        DaylightSavingRule daylightSavingRule = null;
        int i = 0;
        while (i < readByte) {
            switch (objectInput.readByte()) {
                case 120:
                    readFixedDayPattern = readFixedDayPattern(objectInput);
                    break;
                case 121:
                    readFixedDayPattern = readDayOfWeekInMonthPattern(objectInput);
                    break;
                case 122:
                    readFixedDayPattern = readLastDayOfWeekPattern(objectInput);
                    break;
                default:
                    readFixedDayPattern = (DaylightSavingRule) objectInput.readObject();
                    break;
            }
            if (daylightSavingRule != null && RuleComparator.INSTANCE.compare(daylightSavingRule, readFixedDayPattern) >= 0) {
                throw new InvalidObjectException("Order of daylight saving rules is not ascending.");
            }
            arrayList.add(readFixedDayPattern);
            i++;
            daylightSavingRule = readFixedDayPattern;
        }
        return arrayList;
    }

    private static void writeOffset(DataOutput dataOutput, int i) throws IOException {
        if (i % 900 == 0) {
            dataOutput.writeByte(i / 900);
        } else {
            dataOutput.writeByte(127);
            dataOutput.writeInt(i);
        }
    }

    private static int readOffset(DataInput dataInput) throws IOException {
        byte readByte = dataInput.readByte();
        return readByte == Byte.MAX_VALUE ? dataInput.readInt() : readByte * 900;
    }

    private static int readSavings(int i) {
        int i2 = i / 3;
        if (i2 == 0) {
            return 0;
        }
        if (i2 == 1) {
            return 1800;
        }
        if (i2 != 2) {
            return i2 != 3 ? -1 : 7200;
        }
        return 3600;
    }

    private static int toTimeOfDay(GregorianTimezoneRule gregorianTimezoneRule) {
        return gregorianTimezoneRule.getTimeOfDay().getInt(PlainTime.SECOND_OF_DAY) + MathUtils.safeCast(gregorianTimezoneRule.getDayOverflow() * 86400);
    }

    private static void writeFixedDayPattern(Object obj, DataOutput dataOutput) throws IOException {
        FixedDayPattern fixedDayPattern = (FixedDayPattern) obj;
        boolean writeMonthIndicatorOffset = writeMonthIndicatorOffset(fixedDayPattern, dataOutput);
        int dayOfMonth = fixedDayPattern.getDayOfMonth() << 3;
        int timeOfDay = toTimeOfDay(fixedDayPattern);
        int timeIndexR = toTimeIndexR(timeOfDay);
        dataOutput.writeByte((dayOfMonth | timeIndexR) & 255);
        if (!writeMonthIndicatorOffset) {
            writeOffset(dataOutput, fixedDayPattern.getSavings());
        }
        if (timeIndexR == 0) {
            dataOutput.writeInt(timeOfDay);
        }
    }

    private static DaylightSavingRule readFixedDayPattern(DataInput dataInput) throws IOException {
        int readByte = dataInput.readByte() & 255;
        int i = readByte >>> 4;
        int i2 = readByte & 15;
        OffsetIndicator offsetIndicator = OffsetIndicator.VALUES[i2 % 3];
        int readSavings = readSavings(i2);
        int readByte2 = dataInput.readByte() & 255;
        int i3 = readByte2 >>> 3;
        int timeOfDayR = toTimeOfDayR(readByte2 & 7);
        if (readSavings == -1) {
            readSavings = readOffset(dataInput);
        }
        return new FixedDayPattern(Month.valueOf(i), i3, timeOfDayR == -1 ? dataInput.readInt() : timeOfDayR, offsetIndicator, readSavings);
    }

    private static void writeDayOfWeekInMonthPattern(Object obj, DataOutput dataOutput) throws IOException {
        int i;
        DayOfWeekInMonthPattern dayOfWeekInMonthPattern = (DayOfWeekInMonthPattern) obj;
        boolean writeMonthIndicatorOffset = writeMonthIndicatorOffset(dayOfWeekInMonthPattern, dataOutput);
        dataOutput.writeByte(((dayOfWeekInMonthPattern.getDayOfMonth() << 3) | dayOfWeekInMonthPattern.getDayOfWeek()) & 255);
        boolean z = false;
        int i2 = dayOfWeekInMonthPattern.isAfter() ? 128 : 0;
        int timeOfDay = toTimeOfDay(dayOfWeekInMonthPattern);
        if (timeOfDay % 1800 == 0) {
            i = i2 | (timeOfDay / 1800);
            z = true;
        } else {
            i = i2 | 63;
        }
        dataOutput.writeByte(i & 255);
        if (!writeMonthIndicatorOffset) {
            writeOffset(dataOutput, dayOfWeekInMonthPattern.getSavings());
        }
        if (z) {
            return;
        }
        dataOutput.writeInt(timeOfDay);
    }

    private static DaylightSavingRule readDayOfWeekInMonthPattern(DataInput dataInput) throws IOException {
        int readByte = dataInput.readByte() & 255;
        Month valueOf = Month.valueOf(readByte >>> 4);
        int i = readByte & 15;
        OffsetIndicator offsetIndicator = OffsetIndicator.VALUES[i % 3];
        int readSavings = readSavings(i);
        int readByte2 = dataInput.readByte() & 255;
        int i2 = readByte2 >>> 3;
        Weekday valueOf2 = Weekday.valueOf(readByte2 & 7);
        int readByte3 = dataInput.readByte() & 255;
        boolean z = (readByte3 >>> 7) == 1;
        int i3 = readByte3 & 63;
        if (readSavings == -1) {
            readSavings = readOffset(dataInput);
        }
        return new DayOfWeekInMonthPattern(valueOf, i2, valueOf2, i3 == 63 ? dataInput.readInt() : i3 * 1800, offsetIndicator, readSavings, z);
    }

    private static void writeLastDayOfWeekPattern(Object obj, DataOutput dataOutput) throws IOException {
        int i;
        boolean z;
        LastWeekdayPattern lastWeekdayPattern = (LastWeekdayPattern) obj;
        boolean writeMonthIndicatorOffset = writeMonthIndicatorOffset(lastWeekdayPattern, dataOutput);
        int dayOfWeek = lastWeekdayPattern.getDayOfWeek() << 5;
        int timeOfDay = toTimeOfDay(lastWeekdayPattern);
        if (timeOfDay % 3600 == 0) {
            i = dayOfWeek | (timeOfDay / 3600);
            z = true;
        } else {
            i = dayOfWeek | 31;
            z = false;
        }
        dataOutput.writeByte(i & 255);
        if (!writeMonthIndicatorOffset) {
            writeOffset(dataOutput, lastWeekdayPattern.getSavings());
        }
        if (z) {
            return;
        }
        dataOutput.writeInt(timeOfDay);
    }

    private static DaylightSavingRule readLastDayOfWeekPattern(DataInput dataInput) throws IOException {
        int readByte = dataInput.readByte() & 255;
        Month valueOf = Month.valueOf(readByte >>> 4);
        int i = readByte & 15;
        OffsetIndicator offsetIndicator = OffsetIndicator.VALUES[i % 3];
        int readSavings = readSavings(i);
        int readByte2 = dataInput.readByte() & 255;
        Weekday valueOf2 = Weekday.valueOf(readByte2 >>> 5);
        int i2 = readByte2 & 31;
        if (readSavings == -1) {
            readSavings = readOffset(dataInput);
        }
        return new LastWeekdayPattern(valueOf, valueOf2, i2 == 31 ? dataInput.readInt() : i2 * 3600, offsetIndicator, readSavings);
    }

    private static void writeRuleBasedTransitionModel(Object obj, ObjectOutput objectOutput) throws IOException {
        RuleBasedTransitionModel ruleBasedTransitionModel = (RuleBasedTransitionModel) obj;
        ZonalTransition initialTransition = ruleBasedTransitionModel.getInitialTransition();
        long posixTime = initialTransition.getPosixTime();
        if (posixTime >= POSIX_TIME_1825 && posixTime < 10464767099L && posixTime % 900 == 0) {
            int i = (int) ((posixTime - POSIX_TIME_1825) / 900);
            objectOutput.writeByte((i >>> 16) & 255);
            objectOutput.writeByte((i >>> 8) & 255);
            objectOutput.writeByte(i & 255);
        } else {
            objectOutput.writeByte(255);
            objectOutput.writeLong(initialTransition.getPosixTime());
        }
        writeOffset(objectOutput, initialTransition.getPreviousOffset());
        writeOffset(objectOutput, initialTransition.getTotalOffset());
        writeOffset(objectOutput, initialTransition.getDaylightSavingOffset());
        writeRules(ruleBasedTransitionModel.getRules(), objectOutput);
    }

    private static Object readRuleBasedTransitionModel(ObjectInput objectInput) throws IOException, ClassNotFoundException {
        long readByte;
        if ((objectInput.readByte() & 255) == 255) {
            readByte = objectInput.readLong();
        } else {
            readByte = (((r0 << 16) + ((objectInput.readByte() & 255) << 8) + (255 & objectInput.readByte())) * 900) + POSIX_TIME_1825;
        }
        return new RuleBasedTransitionModel(new ZonalTransition(readByte, readOffset(objectInput), readOffset(objectInput), readOffset(objectInput)), readRules(objectInput), false);
    }

    private static void writeArrayTransitionModel(Object obj, ObjectOutput objectOutput) throws IOException {
        ((ArrayTransitionModel) obj).writeTransitions(objectOutput);
    }

    private static Object readArrayTransitionModel(ObjectInput objectInput) throws IOException {
        return new ArrayTransitionModel(readTransitions(objectInput), false, false);
    }

    private static void writeCompositeTransitionModel(Object obj, ObjectOutput objectOutput) throws IOException {
        CompositeTransitionModel compositeTransitionModel = (CompositeTransitionModel) obj;
        compositeTransitionModel.writeTransitions(objectOutput);
        writeRules(compositeTransitionModel.getRules(), objectOutput);
    }

    private static Object readCompositeTransitionModel(ObjectInput objectInput) throws IOException, ClassNotFoundException {
        List<ZonalTransition> readTransitions = readTransitions(objectInput);
        return TransitionModel.of(ZonalOffset.ofTotalSeconds(readTransitions.get(0).getPreviousOffset()), readTransitions, readRules(objectInput), false, false);
    }

    private static int writeTransition(ZonalTransition zonalTransition, int i, DataOutput dataOutput) throws IOException {
        int standardOffset = zonalTransition.getStandardOffset();
        int i2 = 0;
        boolean z = standardOffset != i;
        byte b = z ? (byte) 128 : (byte) 0;
        int daylightSavingOffset = zonalTransition.getDaylightSavingOffset();
        int i3 = daylightSavingOffset != 0 ? daylightSavingOffset != 3600 ? daylightSavingOffset != 7200 ? 0 : 3 : 2 : 1;
        byte b2 = (byte) (b | (i3 << 5));
        long posixTime = zonalTransition.getPosixTime() + i + 7200;
        if (posixTime >= POSIX_TIME_1825 && posixTime < 18067104000L) {
            i2 = toTimeIndexT(MathUtils.floorModulo(posixTime, 86400));
        }
        byte b3 = (byte) ((i2 << 2) | b2);
        if (i2 == 0) {
            dataOutput.writeByte(b3);
            dataOutput.writeLong(zonalTransition.getPosixTime());
        } else {
            int i4 = (int) ((posixTime - POSIX_TIME_1825) / 86400);
            dataOutput.writeByte((byte) (b3 | ((byte) ((i4 >>> 16) & 3))));
            dataOutput.writeByte((i4 >>> 8) & 255);
            dataOutput.writeByte(i4 & 255);
        }
        if (i3 == 0) {
            writeOffset(dataOutput, daylightSavingOffset);
        }
        if (z) {
            writeOffset(dataOutput, standardOffset);
        }
        return standardOffset;
    }

    private static boolean writeMonthIndicatorOffset(GregorianTimezoneRule gregorianTimezoneRule, DataOutput dataOutput) throws IOException {
        int monthValue = gregorianTimezoneRule.getMonthValue() << 4;
        int ordinal = gregorianTimezoneRule.getIndicator().ordinal();
        int savings = gregorianTimezoneRule.getSavings();
        boolean z = true;
        if (savings != 0) {
            if (savings == 1800) {
                ordinal += 3;
            } else if (savings == 3600) {
                ordinal += 6;
            } else if (savings != 7200) {
                z = false;
                ordinal += 12;
            } else {
                ordinal += 9;
            }
        }
        dataOutput.writeByte((monthValue | ordinal) & 255);
        return z;
    }

    private Object readResolve() throws ObjectStreamException {
        return this.obj;
    }
}

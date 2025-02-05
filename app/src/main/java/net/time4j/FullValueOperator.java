package net.time4j;

import net.time4j.engine.ChronoOperator;

/* loaded from: classes3.dex */
final class FullValueOperator extends ElementOperator<PlainTime> {
    private final ChronoOperator<PlainTimestamp> tsop;
    static final FullValueOperator ROUNDING_FULL_HOUR = new FullValueOperator(13);
    static final FullValueOperator ROUNDING_FULL_MINUTE = new FullValueOperator(14);
    static final FullValueOperator NEXT_FULL_HOUR = new FullValueOperator(15);
    static final FullValueOperator NEXT_FULL_MINUTE = new FullValueOperator(16);

    private FullValueOperator(int i) {
        super(PlainTime.COMPONENT, i);
        this.tsop = new ChronoOperator<PlainTimestamp>() { // from class: net.time4j.FullValueOperator.1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // net.time4j.engine.ChronoOperator
            public PlainTimestamp apply(PlainTimestamp plainTimestamp) {
                PlainTime doApply = FullValueOperator.this.doApply(plainTimestamp.getWallTime());
                if (doApply.getHour() == 24) {
                    return PlainTimestamp.of((PlainDate) plainTimestamp.getCalendarDate().plus(1L, CalendarUnit.DAYS), PlainTime.midnightAtStartOfDay());
                }
                return plainTimestamp.with(doApply);
            }
        };
    }

    @Override // net.time4j.engine.ChronoOperator
    public PlainTime apply(PlainTime plainTime) {
        return doApply(plainTime);
    }

    @Override // net.time4j.ElementOperator
    ChronoOperator<PlainTimestamp> onTimestamp() {
        return this.tsop;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public PlainTime doApply(PlainTime plainTime) {
        int hour = plainTime.getHour();
        int minute = plainTime.getMinute();
        int i = 0;
        switch (getType()) {
            case 13:
                if (minute >= 30 && (hour = hour + 1) == 25) {
                    hour = 1;
                }
                return PlainTime.of(hour);
            case 14:
                if (plainTime.getSecond() >= 30) {
                    if (hour == 24) {
                        hour = 0;
                        minute = 1;
                    } else {
                        minute++;
                        if (minute == 60) {
                            hour++;
                            minute = 0;
                        }
                    }
                }
                return PlainTime.of(hour, minute);
            case 15:
                int i2 = hour + 1;
                return PlainTime.of(i2 != 25 ? i2 : 1);
            case 16:
                if (hour == 24) {
                    hour = 0;
                    i = 1;
                } else {
                    int i3 = minute + 1;
                    if (i3 == 60) {
                        hour++;
                    } else {
                        i = i3;
                    }
                }
                return PlainTime.of(hour, i);
            default:
                throw new AssertionError("Unknown: " + getType());
        }
    }
}

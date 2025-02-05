package net.time4j.calendar.hindu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import net.time4j.engine.CalendarEra;
import net.time4j.engine.CalendarSystem;

/* loaded from: classes3.dex */
abstract class HinduCS implements CalendarSystem<HinduCalendar> {
    static final long KALI_YUGA_EPOCH = -1132959;
    final HinduVariant variant;

    abstract HinduCalendar create(int i, HinduMonth hinduMonth, HinduDay hinduDay);

    abstract HinduCalendar create(long j);

    abstract boolean isValid(int i, HinduMonth hinduMonth, HinduDay hinduDay);

    HinduCS(HinduVariant hinduVariant) {
        hinduVariant.getClass();
        this.variant = hinduVariant;
    }

    @Override // net.time4j.engine.CalendarSystem
    public final HinduCalendar transform(long j) {
        long minimumSinceUTC = getMinimumSinceUTC();
        long maximumSinceUTC = getMaximumSinceUTC();
        if (j < minimumSinceUTC || j > maximumSinceUTC) {
            throw new IllegalArgumentException("Out of range: " + minimumSinceUTC + " <= " + j + " <= " + maximumSinceUTC);
        }
        return create(j);
    }

    @Override // net.time4j.engine.CalendarSystem
    public final long transform(HinduCalendar hinduCalendar) {
        return hinduCalendar.getDaysSinceEpochUTC();
    }

    @Override // net.time4j.engine.CalendarSystem
    public List<CalendarEra> getEras() {
        return Collections.unmodifiableList(new ArrayList(Arrays.asList(HinduEra.values())));
    }

    final boolean isExpunged(int i, HinduMonth hinduMonth) {
        return !create(create(i, hinduMonth, HinduDay.valueOf(15)).getDaysSinceEpochUTC()).getMonth().getValue().equals(hinduMonth.getValue());
    }

    final boolean isExpunged(int i, HinduMonth hinduMonth, HinduDay hinduDay) {
        HinduCalendar create = create(create(i, hinduMonth, hinduDay).getDaysSinceEpochUTC());
        return (create.getExpiredYearOfKaliYuga() == i && create.getMonth().equals(hinduMonth) && create.getDayOfMonth().equals(hinduDay)) ? false : true;
    }

    static double modulo(double d, double d2) {
        return d - (d2 * Math.floor(d / d2));
    }
}

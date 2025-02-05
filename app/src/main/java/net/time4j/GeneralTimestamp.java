package net.time4j;

import net.time4j.base.MathUtils;
import net.time4j.engine.CalendarDays;
import net.time4j.engine.CalendarVariant;
import net.time4j.engine.Calendrical;
import net.time4j.engine.ChronoDisplay;
import net.time4j.engine.ChronoElement;
import net.time4j.engine.ChronoException;
import net.time4j.engine.StartOfDay;
import net.time4j.engine.VariantSource;
import net.time4j.tz.TZID;
import net.time4j.tz.Timezone;
import net.time4j.tz.ZonalOffset;

/* loaded from: classes3.dex */
public final class GeneralTimestamp<C> implements ChronoDisplay, VariantSource {
    private final Calendrical<?, ?> ca;
    private final CalendarVariant<?> cv;
    private final PlainTime time;

    @Override // net.time4j.engine.ChronoDisplay
    public boolean hasTimezone() {
        return false;
    }

    /* JADX WARN: Type inference failed for: r3v1, types: [net.time4j.engine.CalendarVariant, net.time4j.engine.CalendarVariant<?>] */
    /* JADX WARN: Type inference failed for: r3v4, types: [net.time4j.engine.Calendrical, net.time4j.engine.Calendrical<?, ?>] */
    private GeneralTimestamp(CalendarVariant<?> calendarVariant, Calendrical<?, ?> calendrical, PlainTime plainTime) {
        if (plainTime.getHour() == 24) {
            if (calendarVariant == null) {
                this.cv = null;
                this.ca = calendrical.plus(CalendarDays.of(1L));
            } else {
                this.cv = calendarVariant.plus(CalendarDays.of(1L));
                this.ca = null;
            }
            this.time = PlainTime.midnightAtStartOfDay();
            return;
        }
        this.cv = calendarVariant;
        this.ca = calendrical;
        this.time = plainTime;
    }

    /* JADX WARN: Incorrect types in method signature: <C:Lnet/time4j/engine/CalendarVariant<TC;>;>(TC;Lnet/time4j/PlainTime;)Lnet/time4j/GeneralTimestamp<TC;>; */
    public static GeneralTimestamp of(CalendarVariant calendarVariant, PlainTime plainTime) {
        if (calendarVariant == null) {
            throw new NullPointerException("Missing date component.");
        }
        return new GeneralTimestamp(calendarVariant, null, plainTime);
    }

    /* JADX WARN: Incorrect types in method signature: <C:Lnet/time4j/engine/Calendrical<*TC;>;>(TC;Lnet/time4j/PlainTime;)Lnet/time4j/GeneralTimestamp<TC;>; */
    public static GeneralTimestamp of(Calendrical calendrical, PlainTime plainTime) {
        if (calendrical == null) {
            throw new NullPointerException("Missing date component.");
        }
        return new GeneralTimestamp(null, calendrical, plainTime);
    }

    public C toDate() {
        C c = (C) this.cv;
        return c == null ? (C) this.ca : c;
    }

    public PlainTime toTime() {
        return this.time;
    }

    public GeneralTimestamp<C> minus(CalendarDays calendarDays) {
        return plus(calendarDays.inverse());
    }

    public GeneralTimestamp<C> minus(long j, ClockUnit clockUnit) {
        return plus(MathUtils.safeNegate(j), clockUnit);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v2, types: [net.time4j.engine.CalendarVariant] */
    /* JADX WARN: Type inference failed for: r0v3 */
    /* JADX WARN: Type inference failed for: r0v4 */
    public GeneralTimestamp<C> plus(CalendarDays calendarDays) {
        CalendarVariant<?> calendarVariant = this.cv;
        ?? plus = calendarVariant == null ? 0 : calendarVariant.plus(calendarDays);
        Calendrical<?, ?> calendrical = this.ca;
        return new GeneralTimestamp<>(plus, calendrical != null ? calendrical.plus(calendarDays) : null, this.time);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v3, types: [net.time4j.engine.CalendarVariant] */
    /* JADX WARN: Type inference failed for: r5v4 */
    /* JADX WARN: Type inference failed for: r5v5 */
    public GeneralTimestamp<C> plus(long j, ClockUnit clockUnit) {
        DayCycles roll = this.time.roll(j, clockUnit);
        CalendarDays of = CalendarDays.of(roll.getDayOverflow());
        CalendarVariant<?> calendarVariant = this.cv;
        ?? plus = calendarVariant == null ? 0 : calendarVariant.plus(of);
        Calendrical<?, ?> calendrical = this.ca;
        return new GeneralTimestamp<>(plus, calendrical != null ? calendrical.plus(of) : null, roll.getWallTime());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof GeneralTimestamp)) {
            return false;
        }
        GeneralTimestamp generalTimestamp = (GeneralTimestamp) GeneralTimestamp.class.cast(obj);
        if (!this.time.equals(generalTimestamp.time)) {
            return false;
        }
        CalendarVariant<?> calendarVariant = this.cv;
        return calendarVariant == null ? generalTimestamp.cv == null && this.ca.equals(generalTimestamp.ca) : generalTimestamp.ca == null && calendarVariant.equals(generalTimestamp.cv);
    }

    public int hashCode() {
        int hashCode;
        CalendarVariant<?> calendarVariant = this.cv;
        if (calendarVariant == null) {
            hashCode = this.ca.hashCode();
        } else {
            hashCode = calendarVariant.hashCode();
        }
        return hashCode + this.time.hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        CalendarVariant<?> calendarVariant = this.cv;
        if (calendarVariant == null) {
            sb.append(this.ca);
        } else {
            sb.append(calendarVariant);
        }
        sb.append(this.time);
        return sb.toString();
    }

    public Moment at(ZonalOffset zonalOffset, StartOfDay startOfDay) {
        PlainTimestamp at;
        CalendarVariant<?> calendarVariant = this.cv;
        if (calendarVariant == null) {
            at = ((PlainDate) this.ca.transform(PlainDate.class)).at(this.time);
        } else {
            at = ((PlainDate) calendarVariant.transform(PlainDate.class)).at(this.time);
        }
        int intValue = ((Integer) this.time.get(PlainTime.SECOND_OF_DAY)).intValue() - startOfDay.getDeviation(at.getCalendarDate(), zonalOffset);
        if (intValue >= 86400) {
            at = at.minus(1L, CalendarUnit.DAYS);
        } else if (intValue < 0) {
            at = at.plus(1L, CalendarUnit.DAYS);
        }
        return at.at(zonalOffset);
    }

    public Moment in(Timezone timezone, StartOfDay startOfDay) {
        PlainTimestamp at;
        CalendarVariant<?> calendarVariant = this.cv;
        if (calendarVariant == null) {
            at = ((PlainDate) this.ca.transform(PlainDate.class)).at(this.time);
        } else {
            at = ((PlainDate) calendarVariant.transform(PlainDate.class)).at(this.time);
        }
        int intValue = ((Integer) this.time.get(PlainTime.SECOND_OF_DAY)).intValue() - startOfDay.getDeviation(at.getCalendarDate(), timezone.getID());
        if (intValue >= 86400) {
            at = at.minus(1L, CalendarUnit.DAYS);
        } else if (intValue < 0) {
            at = at.plus(1L, CalendarUnit.DAYS);
        }
        return at.in(timezone);
    }

    @Override // net.time4j.engine.ChronoDisplay
    public boolean contains(ChronoElement<?> chronoElement) {
        return chronoElement.isDateElement() ? toDate0().contains(chronoElement) : this.time.contains(chronoElement);
    }

    @Override // net.time4j.engine.ChronoDisplay
    public <V> V get(ChronoElement<V> chronoElement) {
        return chronoElement.isDateElement() ? (V) toDate0().get(chronoElement) : (V) this.time.get(chronoElement);
    }

    @Override // net.time4j.engine.ChronoDisplay
    public int getInt(ChronoElement<Integer> chronoElement) {
        return chronoElement.isDateElement() ? toDate0().getInt(chronoElement) : this.time.getInt(chronoElement);
    }

    @Override // net.time4j.engine.ChronoDisplay
    public <V> V getMinimum(ChronoElement<V> chronoElement) {
        return chronoElement.isDateElement() ? (V) toDate0().getMinimum(chronoElement) : (V) this.time.getMinimum(chronoElement);
    }

    @Override // net.time4j.engine.ChronoDisplay
    public <V> V getMaximum(ChronoElement<V> chronoElement) {
        return chronoElement.isDateElement() ? (V) toDate0().getMaximum(chronoElement) : (V) this.time.getMaximum(chronoElement);
    }

    @Override // net.time4j.engine.ChronoDisplay
    public TZID getTimezone() {
        throw new ChronoException("Timezone not available: " + this);
    }

    private ChronoDisplay toDate0() {
        ChronoDisplay chronoDisplay = this.cv;
        if (chronoDisplay == null) {
            chronoDisplay = this.ca;
        }
        return chronoDisplay;
    }

    @Override // net.time4j.engine.VariantSource
    public String getVariant() {
        CalendarVariant<?> calendarVariant = this.cv;
        return calendarVariant == null ? "" : calendarVariant.getVariant();
    }
}

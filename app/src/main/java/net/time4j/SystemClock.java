package net.time4j;

import java.util.Iterator;
import net.time4j.base.MathUtils;
import net.time4j.base.ResourceLoader;
import net.time4j.base.TimeSource;
import net.time4j.scale.LeapSeconds;
import net.time4j.scale.TickProvider;
import net.time4j.scale.TimeScale;
import net.time4j.tz.TZID;
import net.time4j.tz.Timezone;

/* loaded from: classes3.dex */
public final class SystemClock implements TimeSource<Moment> {
    public static final SystemClock INSTANCE;
    private static final int MIO = 1000000;
    public static final SystemClock MONOTONIC;
    private static final boolean MONOTON_MODE;
    private static final int MRD = 1000000000;
    private static final TickProvider PROVIDER;
    private final boolean monotonic;
    private final long offset;

    static {
        TickProvider tickProvider;
        String property = System.getProperty("java.vm.name");
        Iterator it = ResourceLoader.getInstance().services(TickProvider.class).iterator();
        while (true) {
            if (!it.hasNext()) {
                tickProvider = null;
                break;
            } else {
                tickProvider = (TickProvider) it.next();
                if (property.equals(tickProvider.getPlatform())) {
                    break;
                }
            }
        }
        if (tickProvider == null) {
            tickProvider = new StdTickProvider();
        }
        PROVIDER = tickProvider;
        MONOTON_MODE = Boolean.getBoolean("net.time4j.systemclock.nanoTime");
        INSTANCE = new SystemClock(false, calibrate());
        MONOTONIC = new SystemClock(true, calibrate());
    }

    private SystemClock(boolean z, long j) {
        this.monotonic = z;
        this.offset = j;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.time4j.base.TimeSource
    public Moment currentTime() {
        if ((this.monotonic || MONOTON_MODE) && LeapSeconds.getInstance().isEnabled()) {
            long utcNanos = utcNanos();
            return Moment.of(MathUtils.floorDivide(utcNanos, 1000000000), MathUtils.floorModulo(utcNanos, 1000000000), TimeScale.UTC);
        }
        long currentTimeMillis = System.currentTimeMillis();
        return Moment.of(MathUtils.floorDivide(currentTimeMillis, 1000), MathUtils.floorModulo(currentTimeMillis, 1000) * 1000000, TimeScale.POSIX);
    }

    public long currentTimeInMillis() {
        if (this.monotonic || MONOTON_MODE) {
            return MathUtils.safeMultiply(LeapSeconds.getInstance().strip(MathUtils.floorDivide(utcNanos(), 1000000000)), 1000L) + MathUtils.floorModulo(r0, 1000000);
        }
        return System.currentTimeMillis();
    }

    public long currentTimeInMicros() {
        if (this.monotonic || MONOTON_MODE) {
            return MathUtils.safeMultiply(LeapSeconds.getInstance().strip(MathUtils.floorDivide(utcNanos(), 1000000000)), 1000000L) + MathUtils.floorModulo(r0, 1000);
        }
        return MathUtils.safeMultiply(System.currentTimeMillis(), 1000L);
    }

    public long realTimeInMicros() {
        if (this.monotonic || MONOTON_MODE) {
            return MathUtils.floorDivide(utcNanos(), 1000);
        }
        return MathUtils.safeMultiply(LeapSeconds.getInstance().enhance(MathUtils.floorDivide(System.currentTimeMillis(), 1000)), 1000000L) + (MathUtils.floorModulo(r2, 1000) * 1000);
    }

    public static ZonalClock inPlatformView() {
        return new ZonalClock(INSTANCE, Timezone.ofPlatform());
    }

    public static ZonalClock inLocalView() {
        return ZonalClock.ofSystem();
    }

    public static ZonalClock inZonalView(TZID tzid) {
        return new ZonalClock(INSTANCE, tzid);
    }

    public static ZonalClock inZonalView(String str) {
        return new ZonalClock(INSTANCE, str);
    }

    public static Moment currentMoment() {
        return INSTANCE.currentTime();
    }

    public SystemClock recalibrated() {
        return new SystemClock(this.monotonic, calibrate());
    }

    /* JADX WARN: Type inference failed for: r7v1, types: [net.time4j.base.UnixTime] */
    public SystemClock synchronizedWith(TimeSource<?> timeSource) {
        return new SystemClock(this.monotonic, MathUtils.safeSubtract(MathUtils.safeMultiply(Moment.from(timeSource.currentTime()).getElapsedTime(TimeScale.UTC), 1000000000L) + r7.getNanosecond(TimeScale.UTC), MONOTON_MODE ? System.nanoTime() : PROVIDER.getNanos()));
    }

    private static long calibrate() {
        long currentTimeMillis = System.currentTimeMillis();
        long j = 0;
        int i = 0;
        while (i < 10) {
            j = MONOTON_MODE ? System.nanoTime() : PROVIDER.getNanos();
            long currentTimeMillis2 = System.currentTimeMillis();
            if (currentTimeMillis == currentTimeMillis2) {
                break;
            }
            i++;
            currentTimeMillis = currentTimeMillis2;
        }
        return MathUtils.safeSubtract(MathUtils.safeMultiply(LeapSeconds.getInstance().enhance(MathUtils.floorDivide(currentTimeMillis, 1000)), 1000000000L) + (MathUtils.floorModulo(currentTimeMillis, 1000) * 1000000), j);
    }

    private long utcNanos() {
        return MathUtils.safeAdd(MONOTON_MODE ? System.nanoTime() : PROVIDER.getNanos(), this.offset);
    }

    private static class StdTickProvider implements TickProvider {
        @Override // net.time4j.scale.TickProvider
        public String getPlatform() {
            return "";
        }

        private StdTickProvider() {
        }

        @Override // net.time4j.scale.TickProvider
        public long getNanos() {
            return System.nanoTime();
        }
    }
}

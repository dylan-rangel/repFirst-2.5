package net.time4j.tz;

import net.time4j.base.UnixTime;

/* loaded from: classes3.dex */
class SimpleUT implements UnixTime {
    private final int nano;
    private final long posix;

    private SimpleUT(long j, int i) {
        this.posix = j;
        this.nano = i;
    }

    @Override // net.time4j.base.UnixTime
    public long getPosixTime() {
        return this.posix;
    }

    @Override // net.time4j.base.UnixTime
    public int getNanosecond() {
        return this.nano;
    }

    static UnixTime previousTime(UnixTime unixTime) {
        return previousTime(unixTime.getPosixTime(), unixTime.getNanosecond());
    }

    static UnixTime previousTime(long j, int i) {
        if (i == 0) {
            j--;
        }
        return new SimpleUT(j, i == 0 ? 999999999 : i - 1);
    }
}

package net.time4j.scale;

import net.time4j.base.UnixTime;

/* loaded from: classes3.dex */
public interface UniversalTime extends UnixTime {
    long getElapsedTime(TimeScale timeScale);

    int getNanosecond(TimeScale timeScale);

    boolean isLeapSecond();
}

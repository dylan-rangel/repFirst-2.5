package net.time4j.calendar;

import net.time4j.PlainDate;

/* loaded from: classes3.dex */
public interface HijriData {
    PlainDate firstGregorianDate();

    int lengthOfMonth(int i, int i2);

    int maximumYear();

    int minimumYear();

    String name();

    void prepare();

    String version();
}

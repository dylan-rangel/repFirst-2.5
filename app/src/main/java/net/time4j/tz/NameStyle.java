package net.time4j.tz;

/* loaded from: classes3.dex */
public enum NameStyle {
    SHORT_STANDARD_TIME,
    LONG_STANDARD_TIME,
    SHORT_DAYLIGHT_TIME,
    LONG_DAYLIGHT_TIME;

    public boolean isAbbreviation() {
        return this == SHORT_STANDARD_TIME || this == SHORT_DAYLIGHT_TIME;
    }

    public boolean isDaylightSaving() {
        return this == SHORT_DAYLIGHT_TIME || this == LONG_DAYLIGHT_TIME;
    }
}

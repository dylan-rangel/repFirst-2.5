package net.time4j.calendar.hindu;

import net.time4j.calendar.EastAsianMonth;
import net.time4j.engine.AttributeKey;

/* loaded from: classes3.dex */
public abstract class HinduPrimitive {
    public static final AttributeKey<Character> ADHIKA_INDICATOR = EastAsianMonth.LEAP_MONTH_INDICATOR;
    public static final AttributeKey<Boolean> ADHIKA_IS_TRAILING = EastAsianMonth.LEAP_MONTH_IS_TRAILING;

    public abstract boolean isLeap();

    HinduPrimitive() {
    }
}

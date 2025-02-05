package com.facebook.common.util;

import javax.annotation.Nullable;

/* loaded from: classes.dex */
public class HashCodeUtil {
    private static final int X = 31;

    public static int hashCode(int i1) {
        return i1 + 31;
    }

    public static int hashCode(int i1, int i2) {
        return ((i1 + 31) * 31) + i2;
    }

    public static int hashCode(int i1, int i2, int i3) {
        return ((((i1 + 31) * 31) + i2) * 31) + i3;
    }

    public static int hashCode(int i1, int i2, int i3, int i4) {
        return ((((((i1 + 31) * 31) + i2) * 31) + i3) * 31) + i4;
    }

    public static int hashCode(int i1, int i2, int i3, int i4, int i5) {
        return ((((((((i1 + 31) * 31) + i2) * 31) + i3) * 31) + i4) * 31) + i5;
    }

    public static int hashCode(int i1, int i2, int i3, int i4, int i5, int i6) {
        return ((((((((((i1 + 31) * 31) + i2) * 31) + i3) * 31) + i4) * 31) + i5) * 31) + i6;
    }

    public static int hashCode(@Nullable Object o1) {
        return hashCode(o1 == null ? 0 : o1.hashCode());
    }

    public static int hashCode(@Nullable Object o1, @Nullable Object o2) {
        return hashCode(o1 == null ? 0 : o1.hashCode(), o2 != null ? o2.hashCode() : 0);
    }

    public static int hashCode(@Nullable Object o1, @Nullable Object o2, @Nullable Object o3) {
        return hashCode(o1 == null ? 0 : o1.hashCode(), o2 == null ? 0 : o2.hashCode(), o3 != null ? o3.hashCode() : 0);
    }

    public static int hashCode(@Nullable Object o1, @Nullable Object o2, @Nullable Object o3, @Nullable Object o4) {
        return hashCode(o1 == null ? 0 : o1.hashCode(), o2 == null ? 0 : o2.hashCode(), o3 == null ? 0 : o3.hashCode(), o4 != null ? o4.hashCode() : 0);
    }

    public static int hashCode(@Nullable Object o1, @Nullable Object o2, @Nullable Object o3, @Nullable Object o4, @Nullable Object o5) {
        return hashCode(o1 == null ? 0 : o1.hashCode(), o2 == null ? 0 : o2.hashCode(), o3 == null ? 0 : o3.hashCode(), o4 == null ? 0 : o4.hashCode(), o5 != null ? o5.hashCode() : 0);
    }

    public static int hashCode(@Nullable Object o1, @Nullable Object o2, @Nullable Object o3, @Nullable Object o4, @Nullable Object o5, @Nullable Object o6) {
        return hashCode(o1 == null ? 0 : o1.hashCode(), o2 == null ? 0 : o2.hashCode(), o3 == null ? 0 : o3.hashCode(), o4 == null ? 0 : o4.hashCode(), o5 == null ? 0 : o5.hashCode(), o6 == null ? 0 : o6.hashCode());
    }
}

package com.facebook.common.internal;

/* loaded from: classes.dex */
public class Ints {
    private Ints() {
    }

    public static int max(int... array) {
        Preconditions.checkArgument(Boolean.valueOf(array.length > 0));
        int i = array[0];
        for (int i2 = 1; i2 < array.length; i2++) {
            int i3 = array[i2];
            if (i3 > i) {
                i = i3;
            }
        }
        return i;
    }
}

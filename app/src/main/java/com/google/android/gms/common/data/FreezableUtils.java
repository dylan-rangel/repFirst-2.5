package com.google.android.gms.common.data;

import com.facebook.common.internal.ImmutableList;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: com.google.android.gms:play-services-base@@18.1.0 */
/* loaded from: classes2.dex */
public final class FreezableUtils {
    public static <T, E extends Freezable<T>> ArrayList<T> freeze(ArrayList<E> arrayList) {
        ImmutableList immutableList = (ArrayList<T>) new ArrayList(arrayList.size());
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            immutableList.add(arrayList.get(i).freeze());
        }
        return immutableList;
    }

    public static <T, E extends Freezable<T>> ArrayList<T> freezeIterable(Iterable<E> iterable) {
        ImmutableList immutableList = (ArrayList<T>) new ArrayList();
        Iterator<E> it = iterable.iterator();
        while (it.hasNext()) {
            immutableList.add(it.next().freeze());
        }
        return immutableList;
    }

    public static <T, E extends Freezable<T>> ArrayList<T> freeze(E[] eArr) {
        ImmutableList immutableList = (ArrayList<T>) new ArrayList(eArr.length);
        for (E e : eArr) {
            immutableList.add(e.freeze());
        }
        return immutableList;
    }
}

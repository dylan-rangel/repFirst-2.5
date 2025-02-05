package com.google.android.gms.internal.measurement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-measurement-base@@21.5.0 */
/* loaded from: classes2.dex */
final class zzjr extends zzjs {
    private static final Class<?> zza = Collections.unmodifiableList(Collections.emptyList()).getClass();

    private static <E> List<E> zzc(Object obj, long j) {
        return (List) zzmg.zze(obj, j);
    }

    @Override // com.google.android.gms.internal.measurement.zzjs
    final <L> List<L> zza(Object obj, long j) {
        return zza(obj, j, 10);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static <L> List<L> zza(Object obj, long j, int i) {
        zzjq zzjqVar;
        List<L> arrayList;
        List<L> zzc = zzc(obj, j);
        if (zzc.isEmpty()) {
            if (zzc instanceof zzjp) {
                arrayList = new zzjq(i);
            } else if ((zzc instanceof zzkv) && (zzc instanceof zzjf)) {
                arrayList = ((zzjf) zzc).zza(i);
            } else {
                arrayList = new ArrayList<>(i);
            }
            zzmg.zza(obj, j, arrayList);
            return arrayList;
        }
        if (zza.isAssignableFrom(zzc.getClass())) {
            ArrayList arrayList2 = new ArrayList(zzc.size() + i);
            arrayList2.addAll(zzc);
            zzmg.zza(obj, j, arrayList2);
            zzjqVar = arrayList2;
        } else if (zzc instanceof zzmb) {
            zzjq zzjqVar2 = new zzjq(zzc.size() + i);
            zzjqVar2.addAll((zzmb) zzc);
            zzmg.zza(obj, j, zzjqVar2);
            zzjqVar = zzjqVar2;
        } else {
            if (!(zzc instanceof zzkv) || !(zzc instanceof zzjf)) {
                return zzc;
            }
            zzjf zzjfVar = (zzjf) zzc;
            if (zzjfVar.zzc()) {
                return zzc;
            }
            zzjf zza2 = zzjfVar.zza(zzc.size() + i);
            zzmg.zza(obj, j, zza2);
            return zza2;
        }
        return zzjqVar;
    }

    private zzjr() {
        super();
    }

    @Override // com.google.android.gms.internal.measurement.zzjs
    final void zzb(Object obj, long j) {
        Object unmodifiableList;
        List list = (List) zzmg.zze(obj, j);
        if (list instanceof zzjp) {
            unmodifiableList = ((zzjp) list).h_();
        } else {
            if (zza.isAssignableFrom(list.getClass())) {
                return;
            }
            if ((list instanceof zzkv) && (list instanceof zzjf)) {
                zzjf zzjfVar = (zzjf) list;
                if (zzjfVar.zzc()) {
                    zzjfVar.i_();
                    return;
                }
                return;
            }
            unmodifiableList = Collections.unmodifiableList(list);
        }
        zzmg.zza(obj, j, unmodifiableList);
    }

    @Override // com.google.android.gms.internal.measurement.zzjs
    final <E> void zza(Object obj, Object obj2, long j) {
        List zzc = zzc(obj2, j);
        List zza2 = zza(obj, j, zzc.size());
        int size = zza2.size();
        int size2 = zzc.size();
        if (size > 0 && size2 > 0) {
            zza2.addAll(zzc);
        }
        if (size > 0) {
            zzc = zza2;
        }
        zzmg.zza(obj, j, zzc);
    }
}

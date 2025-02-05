package com.google.android.gms.measurement.internal;

import androidx.collection.ArrayMap;
import com.google.android.gms.internal.measurement.zzfi;
import com.google.android.gms.internal.measurement.zzob;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-measurement@@21.5.0 */
/* loaded from: classes2.dex */
final class zzv {
    private String zza;
    private boolean zzb;
    private zzfi.zzl zzc;
    private BitSet zzd;
    private BitSet zze;
    private Map<Integer, Long> zzf;
    private Map<Integer, List<Long>> zzg;
    private final /* synthetic */ zzt zzh;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [com.google.android.gms.internal.measurement.zzfi$zzc$zza, com.google.android.gms.internal.measurement.zzix$zzb] */
    /* JADX WARN: Type inference failed for: r1v10, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r1v8, types: [java.util.ArrayList] */
    /* JADX WARN: Type inference failed for: r1v9, types: [java.lang.Iterable] */
    /* JADX WARN: Type inference failed for: r7v5, types: [com.google.android.gms.internal.measurement.zzfi$zzl$zza] */
    final zzfi.zzc zza(int i) {
        ArrayList arrayList;
        ?? arrayList2;
        ?? zzb = zzfi.zzc.zzb();
        zzb.zza(i);
        zzb.zza(this.zzb);
        zzfi.zzl zzlVar = this.zzc;
        if (zzlVar != null) {
            zzb.zza(zzlVar);
        }
        ?? zzd = zzfi.zzl.zze().zzb(zzmz.zza(this.zzd)).zzd(zzmz.zza(this.zze));
        if (this.zzf == null) {
            arrayList = null;
        } else {
            arrayList = new ArrayList(this.zzf.size());
            Iterator<Integer> it = this.zzf.keySet().iterator();
            while (it.hasNext()) {
                int intValue = it.next().intValue();
                Long l = this.zzf.get(Integer.valueOf(intValue));
                if (l != null) {
                    arrayList.add((zzfi.zzd) ((com.google.android.gms.internal.measurement.zzix) zzfi.zzd.zzc().zza(intValue).zza(l.longValue()).zzab()));
                }
            }
        }
        if (arrayList != null) {
            zzd.zza(arrayList);
        }
        if (this.zzg == null) {
            arrayList2 = Collections.emptyList();
        } else {
            arrayList2 = new ArrayList(this.zzg.size());
            for (Integer num : this.zzg.keySet()) {
                zzfi.zzm.zza zza = zzfi.zzm.zzc().zza(num.intValue());
                List<Long> list = this.zzg.get(num);
                if (list != null) {
                    Collections.sort(list);
                    zza.zza(list);
                }
                arrayList2.add((zzfi.zzm) ((com.google.android.gms.internal.measurement.zzix) zza.zzab()));
            }
        }
        zzd.zzc(arrayList2);
        zzb.zza(zzd);
        return (zzfi.zzc) ((com.google.android.gms.internal.measurement.zzix) zzb.zzab());
    }

    private zzv(zzt zztVar, String str) {
        this.zzh = zztVar;
        this.zza = str;
        this.zzb = true;
        this.zzd = new BitSet();
        this.zze = new BitSet();
        this.zzf = new ArrayMap();
        this.zzg = new ArrayMap();
    }

    private zzv(zzt zztVar, String str, zzfi.zzl zzlVar, BitSet bitSet, BitSet bitSet2, Map<Integer, Long> map, Map<Integer, Long> map2) {
        this.zzh = zztVar;
        this.zza = str;
        this.zzd = bitSet;
        this.zze = bitSet2;
        this.zzf = map;
        this.zzg = new ArrayMap();
        if (map2 != null) {
            for (Integer num : map2.keySet()) {
                ArrayList arrayList = new ArrayList();
                arrayList.add(map2.get(num));
                this.zzg.put(num, arrayList);
            }
        }
        this.zzb = false;
        this.zzc = zzlVar;
    }

    final void zza(zzac zzacVar) {
        int zza = zzacVar.zza();
        if (zzacVar.zzc != null) {
            this.zze.set(zza, zzacVar.zzc.booleanValue());
        }
        if (zzacVar.zzd != null) {
            this.zzd.set(zza, zzacVar.zzd.booleanValue());
        }
        if (zzacVar.zze != null) {
            Long l = this.zzf.get(Integer.valueOf(zza));
            long longValue = zzacVar.zze.longValue() / 1000;
            if (l == null || longValue > l.longValue()) {
                this.zzf.put(Integer.valueOf(zza), Long.valueOf(longValue));
            }
        }
        if (zzacVar.zzf != null) {
            List<Long> list = this.zzg.get(Integer.valueOf(zza));
            if (list == null) {
                list = new ArrayList<>();
                this.zzg.put(Integer.valueOf(zza), list);
            }
            if (zzacVar.zzc()) {
                list.clear();
            }
            if (zzob.zza() && this.zzh.zze().zzf(this.zza, zzbi.zzbg) && zzacVar.zzb()) {
                list.clear();
            }
            if (zzob.zza() && this.zzh.zze().zzf(this.zza, zzbi.zzbg)) {
                long longValue2 = zzacVar.zzf.longValue() / 1000;
                if (list.contains(Long.valueOf(longValue2))) {
                    return;
                }
                list.add(Long.valueOf(longValue2));
                return;
            }
            list.add(Long.valueOf(zzacVar.zzf.longValue() / 1000));
        }
    }
}

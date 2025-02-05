package com.google.android.gms.internal.measurement;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;

/* compiled from: com.google.android.gms:play-services-measurement-base@@21.5.0 */
/* loaded from: classes2.dex */
final class zzld {
    private static final Class<?> zza = zzd();
    private static final zzma<?, ?> zzb = zzc();
    private static final zzma<?, ?> zzc = new zzmc();

    static int zza(int i, List<?> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * zzig.zzb(i, true);
    }

    static int zza(List<?> list) {
        return list.size();
    }

    static int zza(int i, List<zzhm> list) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int zzi = size * zzig.zzi(i);
        for (int i2 = 0; i2 < list.size(); i2++) {
            zzi += zzig.zzb(list.get(i2));
        }
        return zzi;
    }

    static int zzb(int i, List<Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzb(list) + (size * zzig.zzi(i));
    }

    static int zzb(List<Integer> list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzja) {
            zzja zzjaVar = (zzja) list;
            i = 0;
            while (i2 < size) {
                i += zzig.zzd(zzjaVar.zzb(i2));
                i2++;
            }
        } else {
            i = 0;
            while (i2 < size) {
                i += zzig.zzd(list.get(i2).intValue());
                i2++;
            }
        }
        return i;
    }

    static int zzc(int i, List<?> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * zzig.zzf(i, 0);
    }

    static int zzc(List<?> list) {
        return list.size() << 2;
    }

    static int zzd(int i, List<?> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * zzig.zzc(i, 0L);
    }

    static int zzd(List<?> list) {
        return list.size() << 3;
    }

    static int zza(int i, List<zzkj> list, zzlb zzlbVar) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            i2 += zzig.zzb(i, list.get(i3), zzlbVar);
        }
        return i2;
    }

    static int zze(int i, List<Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zze(list) + (size * zzig.zzi(i));
    }

    static int zze(List<Integer> list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzja) {
            zzja zzjaVar = (zzja) list;
            i = 0;
            while (i2 < size) {
                i += zzig.zzf(zzjaVar.zzb(i2));
                i2++;
            }
        } else {
            i = 0;
            while (i2 < size) {
                i += zzig.zzf(list.get(i2).intValue());
                i2++;
            }
        }
        return i;
    }

    static int zzf(int i, List<Long> list, boolean z) {
        if (list.size() == 0) {
            return 0;
        }
        return zzf(list) + (list.size() * zzig.zzi(i));
    }

    static int zzf(List<Long> list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzjy) {
            zzjy zzjyVar = (zzjy) list;
            i = 0;
            while (i2 < size) {
                i += zzig.zzd(zzjyVar.zzb(i2));
                i2++;
            }
        } else {
            i = 0;
            while (i2 < size) {
                i += zzig.zzd(list.get(i2).longValue());
                i2++;
            }
        }
        return i;
    }

    static int zza(int i, Object obj, zzlb zzlbVar) {
        if (obj instanceof zzjn) {
            return zzig.zzb(i, (zzjn) obj);
        }
        return zzig.zzc(i, (zzkj) obj, zzlbVar);
    }

    static int zzb(int i, List<?> list, zzlb zzlbVar) {
        int zza2;
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int zzi = zzig.zzi(i) * size;
        for (int i2 = 0; i2 < size; i2++) {
            Object obj = list.get(i2);
            if (obj instanceof zzjn) {
                zza2 = zzig.zza((zzjn) obj);
            } else {
                zza2 = zzig.zza((zzkj) obj, zzlbVar);
            }
            zzi += zza2;
        }
        return zzi;
    }

    static int zzg(int i, List<Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzg(list) + (size * zzig.zzi(i));
    }

    static int zzg(List<Integer> list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzja) {
            zzja zzjaVar = (zzja) list;
            i = 0;
            while (i2 < size) {
                i += zzig.zzh(zzjaVar.zzb(i2));
                i2++;
            }
        } else {
            i = 0;
            while (i2 < size) {
                i += zzig.zzh(list.get(i2).intValue());
                i2++;
            }
        }
        return i;
    }

    static int zzh(int i, List<Long> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzh(list) + (size * zzig.zzi(i));
    }

    static int zzh(List<Long> list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzjy) {
            zzjy zzjyVar = (zzjy) list;
            i = 0;
            while (i2 < size) {
                i += zzig.zzf(zzjyVar.zzb(i2));
                i2++;
            }
        } else {
            i = 0;
            while (i2 < size) {
                i += zzig.zzf(list.get(i2).longValue());
                i2++;
            }
        }
        return i;
    }

    static int zzb(int i, List<?> list) {
        int zzb2;
        int zzb3;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        int zzi = zzig.zzi(i) * size;
        if (list instanceof zzjp) {
            zzjp zzjpVar = (zzjp) list;
            while (i2 < size) {
                Object zzb4 = zzjpVar.zzb(i2);
                if (zzb4 instanceof zzhm) {
                    zzb3 = zzig.zzb((zzhm) zzb4);
                } else {
                    zzb3 = zzig.zzb((String) zzb4);
                }
                zzi += zzb3;
                i2++;
            }
        } else {
            while (i2 < size) {
                Object obj = list.get(i2);
                if (obj instanceof zzhm) {
                    zzb2 = zzig.zzb((zzhm) obj);
                } else {
                    zzb2 = zzig.zzb((String) obj);
                }
                zzi += zzb2;
                i2++;
            }
        }
        return zzi;
    }

    static int zzi(int i, List<Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzi(list) + (size * zzig.zzi(i));
    }

    static int zzi(List<Integer> list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzja) {
            zzja zzjaVar = (zzja) list;
            i = 0;
            while (i2 < size) {
                i += zzig.zzj(zzjaVar.zzb(i2));
                i2++;
            }
        } else {
            i = 0;
            while (i2 < size) {
                i += zzig.zzj(list.get(i2).intValue());
                i2++;
            }
        }
        return i;
    }

    static int zzj(int i, List<Long> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzj(list) + (size * zzig.zzi(i));
    }

    static int zzj(List<Long> list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzjy) {
            zzjy zzjyVar = (zzjy) list;
            i = 0;
            while (i2 < size) {
                i += zzig.zzg(zzjyVar.zzb(i2));
                i2++;
            }
        } else {
            i = 0;
            while (i2 < size) {
                i += zzig.zzg(list.get(i2).longValue());
                i2++;
            }
        }
        return i;
    }

    private static zzma<?, ?> zzc() {
        try {
            Class<?> zze = zze();
            if (zze == null) {
                return null;
            }
            return (zzma) zze.getConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Throwable unused) {
            return null;
        }
    }

    public static zzma<?, ?> zza() {
        return zzb;
    }

    public static zzma<?, ?> zzb() {
        return zzc;
    }

    private static Class<?> zzd() {
        try {
            return Class.forName("com.google.protobuf.GeneratedMessage");
        } catch (Throwable unused) {
            return null;
        }
    }

    private static Class<?> zze() {
        try {
            return Class.forName("com.google.protobuf.UnknownFieldSetSchema");
        } catch (Throwable unused) {
            return null;
        }
    }

    static <UT, UB> UB zza(Object obj, int i, List<Integer> list, zzje zzjeVar, UB ub, zzma<UT, UB> zzmaVar) {
        if (zzjeVar == null) {
            return ub;
        }
        if (list instanceof RandomAccess) {
            int size = list.size();
            int i2 = 0;
            for (int i3 = 0; i3 < size; i3++) {
                int intValue = list.get(i3).intValue();
                if (zzjeVar.zza(intValue)) {
                    if (i3 != i2) {
                        list.set(i2, Integer.valueOf(intValue));
                    }
                    i2++;
                } else {
                    ub = (UB) zza(obj, i, intValue, ub, zzmaVar);
                }
            }
            if (i2 != size) {
                list.subList(i2, size).clear();
            }
        } else {
            Iterator<Integer> it = list.iterator();
            while (it.hasNext()) {
                int intValue2 = it.next().intValue();
                if (!zzjeVar.zza(intValue2)) {
                    ub = (UB) zza(obj, i, intValue2, ub, zzmaVar);
                    it.remove();
                }
            }
        }
        return ub;
    }

    static <UT, UB> UB zza(Object obj, int i, int i2, UB ub, zzma<UT, UB> zzmaVar) {
        if (ub == null) {
            ub = zzmaVar.zzc(obj);
        }
        zzmaVar.zzb(ub, i, i2);
        return ub;
    }

    static <T, FT extends zzis<FT>> void zza(zzim<FT> zzimVar, T t, T t2) {
        zziq<FT> zza2 = zzimVar.zza(t2);
        if (zza2.zza.isEmpty()) {
            return;
        }
        zzimVar.zzb(t).zza(zza2);
    }

    static <T> void zza(zzkg zzkgVar, T t, T t2, long j) {
        zzmg.zza(t, j, zzkgVar.zza(zzmg.zze(t, j), zzmg.zze(t2, j)));
    }

    static <T, UT, UB> void zza(zzma<UT, UB> zzmaVar, T t, T t2) {
        zzmaVar.zzc(t, zzmaVar.zza(zzmaVar.zzd(t), zzmaVar.zzd(t2)));
    }

    public static void zza(Class<?> cls) {
        Class<?> cls2;
        if (!zzix.class.isAssignableFrom(cls) && (cls2 = zza) != null && !cls2.isAssignableFrom(cls)) {
            throw new IllegalArgumentException("Message classes must extend GeneratedMessage or GeneratedMessageLite");
        }
    }

    public static void zza(int i, List<Boolean> list, zzmw zzmwVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzmwVar.zza(i, list, z);
    }

    public static void zza(int i, List<zzhm> list, zzmw zzmwVar) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzmwVar.zza(i, list);
    }

    public static void zzb(int i, List<Double> list, zzmw zzmwVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzmwVar.zzb(i, list, z);
    }

    public static void zzc(int i, List<Integer> list, zzmw zzmwVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzmwVar.zzc(i, list, z);
    }

    public static void zzd(int i, List<Integer> list, zzmw zzmwVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzmwVar.zzd(i, list, z);
    }

    public static void zze(int i, List<Long> list, zzmw zzmwVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzmwVar.zze(i, list, z);
    }

    public static void zzf(int i, List<Float> list, zzmw zzmwVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzmwVar.zzf(i, list, z);
    }

    public static void zza(int i, List<?> list, zzmw zzmwVar, zzlb zzlbVar) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzmwVar.zza(i, list, zzlbVar);
    }

    public static void zzg(int i, List<Integer> list, zzmw zzmwVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzmwVar.zzg(i, list, z);
    }

    public static void zzh(int i, List<Long> list, zzmw zzmwVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzmwVar.zzh(i, list, z);
    }

    public static void zzb(int i, List<?> list, zzmw zzmwVar, zzlb zzlbVar) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzmwVar.zzb(i, list, zzlbVar);
    }

    public static void zzi(int i, List<Integer> list, zzmw zzmwVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzmwVar.zzi(i, list, z);
    }

    public static void zzj(int i, List<Long> list, zzmw zzmwVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzmwVar.zzj(i, list, z);
    }

    public static void zzk(int i, List<Integer> list, zzmw zzmwVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzmwVar.zzk(i, list, z);
    }

    public static void zzl(int i, List<Long> list, zzmw zzmwVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzmwVar.zzl(i, list, z);
    }

    public static void zzb(int i, List<String> list, zzmw zzmwVar) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzmwVar.zzb(i, list);
    }

    public static void zzm(int i, List<Integer> list, zzmw zzmwVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzmwVar.zzm(i, list, z);
    }

    public static void zzn(int i, List<Long> list, zzmw zzmwVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzmwVar.zzn(i, list, z);
    }

    static boolean zza(Object obj, Object obj2) {
        if (obj != obj2) {
            return obj != null && obj.equals(obj2);
        }
        return true;
    }
}

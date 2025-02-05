package com.google.android.gms.internal.measurement;

import java.util.Iterator;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-measurement-base@@21.5.0 */
/* loaded from: classes2.dex */
final class zzkf implements zzkg {
    @Override // com.google.android.gms.internal.measurement.zzkg
    public final int zza(int i, Object obj, Object obj2) {
        zzkd zzkdVar = (zzkd) obj;
        if (zzkdVar.isEmpty()) {
            return 0;
        }
        Iterator it = zzkdVar.entrySet().iterator();
        if (!it.hasNext()) {
            return 0;
        }
        Map.Entry entry = (Map.Entry) it.next();
        entry.getKey();
        entry.getValue();
        throw new NoSuchMethodError();
    }

    @Override // com.google.android.gms.internal.measurement.zzkg
    public final zzke<?, ?> zza(Object obj) {
        throw new NoSuchMethodError();
    }

    @Override // com.google.android.gms.internal.measurement.zzkg
    public final Object zza(Object obj, Object obj2) {
        zzkd zzkdVar = (zzkd) obj;
        zzkd zzkdVar2 = (zzkd) obj2;
        if (!zzkdVar2.isEmpty()) {
            if (!zzkdVar.zzd()) {
                zzkdVar = zzkdVar.zzb();
            }
            zzkdVar.zza(zzkdVar2);
        }
        return zzkdVar;
    }

    @Override // com.google.android.gms.internal.measurement.zzkg
    public final Object zzb(Object obj) {
        return zzkd.zza().zzb();
    }

    @Override // com.google.android.gms.internal.measurement.zzkg
    public final Object zzc(Object obj) {
        ((zzkd) obj).zzc();
        return obj;
    }

    @Override // com.google.android.gms.internal.measurement.zzkg
    public final Map<?, ?> zzd(Object obj) {
        return (zzkd) obj;
    }

    @Override // com.google.android.gms.internal.measurement.zzkg
    public final Map<?, ?> zze(Object obj) {
        return (zzkd) obj;
    }

    zzkf() {
    }

    @Override // com.google.android.gms.internal.measurement.zzkg
    public final boolean zzf(Object obj) {
        return !((zzkd) obj).zzd();
    }
}

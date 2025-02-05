package com.google.android.gms.internal.measurement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-measurement@@21.5.0 */
/* loaded from: classes2.dex */
final class zzu extends zzal {
    private boolean zzk;
    private boolean zzl;
    private final /* synthetic */ zzr zzm;

    @Override // com.google.android.gms.internal.measurement.zzal
    public final zzaq zza(zzh zzhVar, List<zzaq> list) {
        zzv zzvVar;
        zzv zzvVar2;
        zzv zzvVar3;
        zzg.zzb("log", 1, list);
        if (list.size() == 1) {
            zzvVar3 = this.zzm.zzk;
            zzvVar3.zza(zzs.INFO, zzhVar.zza(list.get(0)).zzf(), Collections.emptyList(), this.zzk, this.zzl);
            return zzc;
        }
        zzs zza = zzs.zza(zzg.zzb(zzhVar.zza(list.get(0)).zze().doubleValue()));
        String zzf = zzhVar.zza(list.get(1)).zzf();
        if (list.size() == 2) {
            zzvVar2 = this.zzm.zzk;
            zzvVar2.zza(zza, zzf, Collections.emptyList(), this.zzk, this.zzl);
            return zzc;
        }
        ArrayList arrayList = new ArrayList();
        for (int i = 2; i < Math.min(list.size(), 5); i++) {
            arrayList.add(zzhVar.zza(list.get(i)).zzf());
        }
        zzvVar = this.zzm.zzk;
        zzvVar.zza(zza, zzf, arrayList, this.zzk, this.zzl);
        return zzc;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zzu(zzr zzrVar, boolean z, boolean z2) {
        super("log");
        this.zzm = zzrVar;
        this.zzk = z;
        this.zzl = z2;
    }
}

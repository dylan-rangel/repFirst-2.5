package com.google.android.gms.measurement.internal;

import java.util.List;
import java.util.concurrent.Callable;

/* compiled from: com.google.android.gms:play-services-measurement@@21.5.0 */
/* loaded from: classes2.dex */
final class zzhq implements Callable<List<zzad>> {
    private final /* synthetic */ String zza;
    private final /* synthetic */ String zzb;
    private final /* synthetic */ String zzc;
    private final /* synthetic */ zzhj zzd;

    @Override // java.util.concurrent.Callable
    public final /* synthetic */ List<zzad> call() throws Exception {
        zzmp zzmpVar;
        zzmp zzmpVar2;
        zzmpVar = this.zzd.zza;
        zzmpVar.zzr();
        zzmpVar2 = this.zzd.zza;
        return zzmpVar2.zzf().zza(this.zza, this.zzb, this.zzc);
    }

    zzhq(zzhj zzhjVar, String str, String str2, String str3) {
        this.zzd = zzhjVar;
        this.zza = str;
        this.zzb = str2;
        this.zzc = str3;
    }
}

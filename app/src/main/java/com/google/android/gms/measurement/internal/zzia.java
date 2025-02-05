package com.google.android.gms.measurement.internal;

import java.util.List;
import java.util.concurrent.Callable;

/* compiled from: com.google.android.gms:play-services-measurement@@21.5.0 */
/* loaded from: classes2.dex */
final class zzia implements Callable<List<zzne>> {
    private final /* synthetic */ String zza;
    private final /* synthetic */ zzhj zzb;

    @Override // java.util.concurrent.Callable
    public final /* synthetic */ List<zzne> call() throws Exception {
        zzmp zzmpVar;
        zzmp zzmpVar2;
        zzmpVar = this.zzb.zza;
        zzmpVar.zzr();
        zzmpVar2 = this.zzb.zza;
        return zzmpVar2.zzf().zzi(this.zza);
    }

    zzia(zzhj zzhjVar, String str) {
        this.zzb = zzhjVar;
        this.zza = str;
    }
}

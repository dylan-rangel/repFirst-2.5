package com.google.android.gms.internal.measurement;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.5.0 */
/* loaded from: classes2.dex */
public final class zznm implements zznn {
    private static final zzgn<Boolean> zza;
    private static final zzgn<Long> zzb;

    static {
        zzgv zza2 = new zzgv(zzgk.zza("com.google.android.gms.measurement")).zzb().zza();
        zza = zza2.zza("measurement.service.deferred_first_open", false);
        zzb = zza2.zza("measurement.id.service.deferred_first_open", 0L);
    }

    @Override // com.google.android.gms.internal.measurement.zznn
    public final boolean zza() {
        return true;
    }

    @Override // com.google.android.gms.internal.measurement.zznn
    public final boolean zzb() {
        return zza.zza().booleanValue();
    }
}

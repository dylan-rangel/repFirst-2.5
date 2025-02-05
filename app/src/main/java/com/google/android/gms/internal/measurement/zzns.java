package com.google.android.gms.internal.measurement;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.5.0 */
/* loaded from: classes2.dex */
public final class zzns implements zznt {
    private static final zzgn<Boolean> zza;
    private static final zzgn<Boolean> zzb;
    private static final zzgn<Boolean> zzc;

    static {
        zzgv zza2 = new zzgv(zzgk.zza("com.google.android.gms.measurement")).zzb().zza();
        zza = zza2.zza("measurement.collection.event_safelist", true);
        zzb = zza2.zza("measurement.service.store_null_safelist", true);
        zzc = zza2.zza("measurement.service.store_safelist", true);
    }

    @Override // com.google.android.gms.internal.measurement.zznt
    public final boolean zza() {
        return true;
    }

    @Override // com.google.android.gms.internal.measurement.zznt
    public final boolean zzb() {
        return zzb.zza().booleanValue();
    }

    @Override // com.google.android.gms.internal.measurement.zznt
    public final boolean zzc() {
        return zzc.zza().booleanValue();
    }
}

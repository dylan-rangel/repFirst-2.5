package com.google.android.gms.internal.measurement;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.5.0 */
/* loaded from: classes2.dex */
public final class zzqf implements zzqc {
    private static final zzgn<Boolean> zza;
    private static final zzgn<Boolean> zzb;

    static {
        zzgv zza2 = new zzgv(zzgk.zza("com.google.android.gms.measurement")).zzb().zza();
        zza = zza2.zza("measurement.sgtm.client.dev", false);
        zzb = zza2.zza("measurement.sgtm.service", false);
    }

    @Override // com.google.android.gms.internal.measurement.zzqc
    public final boolean zza() {
        return true;
    }

    @Override // com.google.android.gms.internal.measurement.zzqc
    public final boolean zzb() {
        return zza.zza().booleanValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzqc
    public final boolean zzc() {
        return zzb.zza().booleanValue();
    }
}

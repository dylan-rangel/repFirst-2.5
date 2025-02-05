package com.google.android.gms.internal.measurement;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.5.0 */
/* loaded from: classes2.dex */
public final class zznr implements zzno {
    private static final zzgn<Boolean> zza;
    private static final zzgn<Boolean> zzb;
    private static final zzgn<Boolean> zzc;
    private static final zzgn<Boolean> zzd;
    private static final zzgn<Boolean> zze;
    private static final zzgn<Boolean> zzf;
    private static final zzgn<Long> zzg;

    static {
        zzgv zza2 = new zzgv(zzgk.zza("com.google.android.gms.measurement")).zzb().zza();
        zza = zza2.zza("measurement.dma_consent.client", false);
        zzb = zza2.zza("measurement.dma_consent.client_bow_check", false);
        zzc = zza2.zza("measurement.dma_consent.service", false);
        zzd = zza2.zza("measurement.dma_consent.service_gcs_v2", false);
        zze = zza2.zza("measurement.dma_consent.service_npa_remote_default", false);
        zzf = zza2.zza("measurement.dma_consent.service_split_batch_on_consent", false);
        zzg = zza2.zza("measurement.id.dma_consent.service", 0L);
    }

    @Override // com.google.android.gms.internal.measurement.zzno
    public final boolean zza() {
        return true;
    }

    @Override // com.google.android.gms.internal.measurement.zzno
    public final boolean zzb() {
        return zza.zza().booleanValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzno
    public final boolean zzc() {
        return zzb.zza().booleanValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzno
    public final boolean zzd() {
        return zzc.zza().booleanValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzno
    public final boolean zze() {
        return zzd.zza().booleanValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzno
    public final boolean zzf() {
        return zze.zza().booleanValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzno
    public final boolean zzg() {
        return zzf.zza().booleanValue();
    }
}

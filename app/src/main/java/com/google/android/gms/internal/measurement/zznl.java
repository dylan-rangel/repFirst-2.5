package com.google.android.gms.internal.measurement;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.5.0 */
/* loaded from: classes2.dex */
public final class zznl implements zzni {
    private static final zzgn<Boolean> zza;
    private static final zzgn<Boolean> zzb;
    private static final zzgn<Boolean> zzc;
    private static final zzgn<Long> zzd;

    @Override // com.google.android.gms.internal.measurement.zzni
    public final long zza() {
        return zzd.zza().longValue();
    }

    static {
        zzgv zza2 = new zzgv(zzgk.zza("com.google.android.gms.measurement")).zzb().zza();
        zza = zza2.zza("measurement.client.consent_state_v1", true);
        zzb = zza2.zza("measurement.client.3p_consent_state_v1", true);
        zzc = zza2.zza("measurement.service.consent_state_v1_W36", true);
        zzd = zza2.zza("measurement.service.storage_consent_support_version", 203600L);
    }
}

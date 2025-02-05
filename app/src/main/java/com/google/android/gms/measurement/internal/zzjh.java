package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.5.0 */
/* loaded from: classes2.dex */
final class zzjh extends zzaw {
    private final /* synthetic */ zziq zza;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzjh(zziq zziqVar, zzif zzifVar) {
        super(zzifVar);
        this.zza = zziqVar;
    }

    @Override // com.google.android.gms.measurement.internal.zzaw
    public final void zzb() {
        zzaw zzawVar;
        if (this.zza.zzu.zzah()) {
            zzawVar = this.zza.zzn;
            zzawVar.zza(2000L);
        }
    }
}

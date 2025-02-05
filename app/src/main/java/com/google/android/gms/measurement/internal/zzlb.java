package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.5.0 */
/* loaded from: classes2.dex */
final class zzlb extends zzaw {
    private final /* synthetic */ zzkp zza;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzlb(zzkp zzkpVar, zzif zzifVar) {
        super(zzifVar);
        this.zza = zzkpVar;
    }

    @Override // com.google.android.gms.measurement.internal.zzaw
    public final void zzb() {
        this.zza.zzj().zzu().zza("Tasks have been queued for a long time");
    }
}

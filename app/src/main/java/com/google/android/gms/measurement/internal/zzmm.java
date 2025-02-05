package com.google.android.gms.measurement.internal;

/* compiled from: com.google.android.gms:play-services-measurement@@21.5.0 */
/* loaded from: classes2.dex */
final class zzmm extends zzaw {
    private final /* synthetic */ zzmj zza;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzmm(zzmj zzmjVar, zzif zzifVar) {
        super(zzifVar);
        this.zza = zzmjVar;
    }

    @Override // com.google.android.gms.measurement.internal.zzaw
    public final void zzb() {
        this.zza.zzu();
        this.zza.zzj().zzp().zza("Starting upload from DelayedRunnable");
        this.zza.zzf.zzw();
    }
}

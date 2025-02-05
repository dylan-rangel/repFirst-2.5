package com.google.android.gms.measurement.internal;

import android.content.Context;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import org.checkerframework.dataflow.qual.Pure;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.5.0 */
/* loaded from: classes2.dex */
class zzid implements zzif {
    protected final zzhf zzu;

    @Override // com.google.android.gms.measurement.internal.zzif
    @Pure
    public Context zza() {
        return this.zzu.zza();
    }

    @Override // com.google.android.gms.measurement.internal.zzif
    @Pure
    public Clock zzb() {
        return this.zzu.zzb();
    }

    @Override // com.google.android.gms.measurement.internal.zzif
    @Pure
    public zzae zzd() {
        return this.zzu.zzd();
    }

    @Pure
    public zzaf zze() {
        return this.zzu.zzf();
    }

    @Pure
    public zzba zzf() {
        return this.zzu.zzg();
    }

    @Pure
    public zzfq zzi() {
        return this.zzu.zzk();
    }

    @Override // com.google.android.gms.measurement.internal.zzif
    @Pure
    public zzfr zzj() {
        return this.zzu.zzj();
    }

    @Pure
    public zzgd zzk() {
        return this.zzu.zzn();
    }

    @Override // com.google.android.gms.measurement.internal.zzif
    @Pure
    public zzgy zzl() {
        return this.zzu.zzl();
    }

    @Pure
    public zznd zzq() {
        return this.zzu.zzt();
    }

    zzid(zzhf zzhfVar) {
        Preconditions.checkNotNull(zzhfVar);
        this.zzu = zzhfVar;
    }

    public void zzr() {
        this.zzu.zzl().zzr();
    }

    public void zzs() {
        this.zzu.zzy();
    }

    public void zzt() {
        this.zzu.zzl().zzt();
    }
}

package com.google.android.gms.measurement.internal;

import android.content.Context;
import com.google.android.gms.common.internal.Preconditions;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.5.0 */
/* loaded from: classes2.dex */
public final class zzio {
    final Context zza;
    String zzb;
    String zzc;
    String zzd;
    Boolean zze;
    long zzf;
    com.google.android.gms.internal.measurement.zzdd zzg;
    boolean zzh;
    Long zzi;
    String zzj;

    public zzio(Context context, com.google.android.gms.internal.measurement.zzdd zzddVar, Long l) {
        this.zzh = true;
        Preconditions.checkNotNull(context);
        Context applicationContext = context.getApplicationContext();
        Preconditions.checkNotNull(applicationContext);
        this.zza = applicationContext;
        this.zzi = l;
        if (zzddVar != null) {
            this.zzg = zzddVar;
            this.zzb = zzddVar.zzf;
            this.zzc = zzddVar.zze;
            this.zzd = zzddVar.zzd;
            this.zzh = zzddVar.zzc;
            this.zzf = zzddVar.zzb;
            this.zzj = zzddVar.zzh;
            if (zzddVar.zzg != null) {
                this.zze = Boolean.valueOf(zzddVar.zzg.getBoolean("dataCollectionDefaultEnabled", true));
            }
        }
    }
}

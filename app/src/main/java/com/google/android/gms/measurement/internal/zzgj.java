package com.google.android.gms.measurement.internal;

import android.content.SharedPreferences;
import com.google.android.gms.common.internal.Preconditions;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.5.0 */
/* loaded from: classes2.dex */
public final class zzgj {
    private final String zza;
    private final String zzb;
    private boolean zzc;
    private String zzd;
    private final /* synthetic */ zzgd zze;

    public final String zza() {
        if (!this.zzc) {
            this.zzc = true;
            this.zzd = this.zze.zzc().getString(this.zza, null);
        }
        return this.zzd;
    }

    public zzgj(zzgd zzgdVar, String str, String str2) {
        this.zze = zzgdVar;
        Preconditions.checkNotEmpty(str);
        this.zza = str;
        this.zzb = null;
    }

    public final void zza(String str) {
        SharedPreferences.Editor edit = this.zze.zzc().edit();
        edit.putString(this.zza, str);
        edit.apply();
        this.zzd = str;
    }
}

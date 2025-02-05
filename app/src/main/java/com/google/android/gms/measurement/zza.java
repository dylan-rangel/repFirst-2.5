package com.google.android.gms.measurement;

import android.os.Bundle;
import androidx.collection.ArrayMap;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.measurement.AppMeasurement;
import com.google.android.gms.measurement.internal.zzhf;
import com.google.android.gms.measurement.internal.zzil;
import com.google.android.gms.measurement.internal.zzim;
import com.google.android.gms.measurement.internal.zziq;
import com.google.android.gms.measurement.internal.zznc;
import java.util.List;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.5.0 */
/* loaded from: classes2.dex */
final class zza extends AppMeasurement.zza {
    private final zzhf zza;
    private final zziq zzb;

    @Override // com.google.android.gms.measurement.internal.zzjz
    public final int zza(String str) {
        Preconditions.checkNotEmpty(str);
        return 25;
    }

    @Override // com.google.android.gms.measurement.internal.zzjz
    public final long zza() {
        return this.zza.zzt().zzm();
    }

    @Override // com.google.android.gms.measurement.AppMeasurement.zza
    public final Boolean zzb() {
        return this.zzb.zzaa();
    }

    @Override // com.google.android.gms.measurement.AppMeasurement.zza
    public final Double zzc() {
        return this.zzb.zzab();
    }

    @Override // com.google.android.gms.measurement.AppMeasurement.zza
    public final Integer zzd() {
        return this.zzb.zzac();
    }

    @Override // com.google.android.gms.measurement.AppMeasurement.zza
    public final Long zze() {
        return this.zzb.zzad();
    }

    @Override // com.google.android.gms.measurement.internal.zzjz
    public final Object zza(int i) {
        if (i == 0) {
            return zzj();
        }
        if (i == 1) {
            return zze();
        }
        if (i == 2) {
            return zzc();
        }
        if (i == 3) {
            return zzd();
        }
        if (i != 4) {
            return null;
        }
        return zzb();
    }

    @Override // com.google.android.gms.measurement.internal.zzjz
    public final String zzf() {
        return this.zzb.zzae();
    }

    @Override // com.google.android.gms.measurement.internal.zzjz
    public final String zzg() {
        return this.zzb.zzaf();
    }

    @Override // com.google.android.gms.measurement.internal.zzjz
    public final String zzh() {
        return this.zzb.zzag();
    }

    @Override // com.google.android.gms.measurement.internal.zzjz
    public final String zzi() {
        return this.zzb.zzae();
    }

    @Override // com.google.android.gms.measurement.AppMeasurement.zza
    public final String zzj() {
        return this.zzb.zzai();
    }

    @Override // com.google.android.gms.measurement.internal.zzjz
    public final List<Bundle> zza(String str, String str2) {
        return this.zzb.zza(str, str2);
    }

    @Override // com.google.android.gms.measurement.AppMeasurement.zza
    public final Map<String, Object> zza(boolean z) {
        List<zznc> zza = this.zzb.zza(z);
        ArrayMap arrayMap = new ArrayMap(zza.size());
        for (zznc zzncVar : zza) {
            Object zza2 = zzncVar.zza();
            if (zza2 != null) {
                arrayMap.put(zzncVar.zza, zza2);
            }
        }
        return arrayMap;
    }

    @Override // com.google.android.gms.measurement.internal.zzjz
    public final Map<String, Object> zza(String str, String str2, boolean z) {
        return this.zzb.zza(str, str2, z);
    }

    public zza(zzhf zzhfVar) {
        super();
        Preconditions.checkNotNull(zzhfVar);
        this.zza = zzhfVar;
        this.zzb = zzhfVar.zzp();
    }

    @Override // com.google.android.gms.measurement.internal.zzjz
    public final void zzb(String str) {
        this.zza.zze().zza(str, this.zza.zzb().elapsedRealtime());
    }

    @Override // com.google.android.gms.measurement.internal.zzjz
    public final void zza(String str, String str2, Bundle bundle) {
        this.zza.zzp().zza(str, str2, bundle);
    }

    @Override // com.google.android.gms.measurement.internal.zzjz
    public final void zzc(String str) {
        this.zza.zze().zzb(str, this.zza.zzb().elapsedRealtime());
    }

    @Override // com.google.android.gms.measurement.internal.zzjz
    public final void zzb(String str, String str2, Bundle bundle) {
        this.zzb.zzb(str, str2, bundle);
    }

    @Override // com.google.android.gms.measurement.internal.zzjz
    public final void zza(String str, String str2, Bundle bundle, long j) {
        this.zzb.zza(str, str2, bundle, true, false, j);
    }

    @Override // com.google.android.gms.measurement.internal.zzjz
    public final void zza(zzil zzilVar) {
        this.zzb.zza(zzilVar);
    }

    @Override // com.google.android.gms.measurement.internal.zzjz
    public final void zza(Bundle bundle) {
        this.zzb.zzb(bundle);
    }

    @Override // com.google.android.gms.measurement.internal.zzjz
    public final void zza(zzim zzimVar) {
        this.zzb.zza(zzimVar);
    }

    @Override // com.google.android.gms.measurement.internal.zzjz
    public final void zzb(zzil zzilVar) {
        this.zzb.zzb(zzilVar);
    }
}

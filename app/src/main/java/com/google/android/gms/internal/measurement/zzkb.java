package com.google.android.gms.internal.measurement;

import java.io.IOException;

/* compiled from: com.google.android.gms:play-services-measurement-base@@21.5.0 */
/* loaded from: classes2.dex */
public final class zzkb<K, V> {
    static <K, V> int zza(zzke<K, V> zzkeVar, K k, V v) {
        return zziq.zza(zzkeVar.zza, 1, k) + zziq.zza(zzkeVar.zzc, 2, v);
    }

    static <K, V> void zza(zzig zzigVar, zzke<K, V> zzkeVar, K k, V v) throws IOException {
        zziq.zza(zzigVar, zzkeVar.zza, 1, k);
        zziq.zza(zzigVar, zzkeVar.zzc, 2, v);
    }
}

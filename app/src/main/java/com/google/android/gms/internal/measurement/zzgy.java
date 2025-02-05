package com.google.android.gms.internal.measurement;

import com.google.common.base.Preconditions;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.5.0 */
/* loaded from: classes2.dex */
public final class zzgy {
    private final boolean zza;

    public zzgy(zzhb zzhbVar) {
        Preconditions.checkNotNull(zzhbVar, "BuildInfo must be non-null");
        this.zza = !zzhbVar.zza();
    }

    public final boolean zza(String str) {
        Preconditions.checkNotNull(str, "flagName must not be null");
        if (this.zza) {
            return zzha.zza.get().containsValue(str);
        }
        return true;
    }
}

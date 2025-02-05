package com.google.android.gms.internal.measurement;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.5.0 */
/* loaded from: classes2.dex */
public final class zzpl implements Supplier<zzpk> {
    private static zzpl zza = new zzpl();
    private final Supplier<zzpk> zzb = Suppliers.ofInstance(new zzpn());

    @Override // com.google.common.base.Supplier
    public final /* synthetic */ zzpk get() {
        return this.zzb.get();
    }

    public static boolean zza() {
        return ((zzpk) zza.get()).zza();
    }

    public static boolean zzb() {
        return ((zzpk) zza.get()).zzb();
    }
}

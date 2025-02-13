package com.google.android.gms.internal.measurement;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.5.0 */
/* loaded from: classes2.dex */
public final class zzqe implements Supplier<zzqh> {
    private static zzqe zza = new zzqe();
    private final Supplier<zzqh> zzb = Suppliers.ofInstance(new zzqg());

    @Override // com.google.common.base.Supplier
    public final /* synthetic */ zzqh get() {
        return this.zzb.get();
    }

    public static boolean zza() {
        return ((zzqh) zza.get()).zza();
    }

    public static boolean zzb() {
        return ((zzqh) zza.get()).zzb();
    }
}

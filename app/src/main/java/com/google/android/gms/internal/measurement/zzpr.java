package com.google.android.gms.internal.measurement;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.5.0 */
/* loaded from: classes2.dex */
public final class zzpr implements Supplier<zzpq> {
    private static zzpr zza = new zzpr();
    private final Supplier<zzpq> zzb = Suppliers.ofInstance(new zzpt());

    @Override // com.google.common.base.Supplier
    public final /* synthetic */ zzpq get() {
        return this.zzb.get();
    }

    public static boolean zza() {
        return ((zzpq) zza.get()).zza();
    }

    public static boolean zzb() {
        return ((zzpq) zza.get()).zzb();
    }
}

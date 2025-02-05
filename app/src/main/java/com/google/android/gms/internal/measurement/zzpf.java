package com.google.android.gms.internal.measurement;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.5.0 */
/* loaded from: classes2.dex */
public final class zzpf implements Supplier<zzpe> {
    private static zzpf zza = new zzpf();
    private final Supplier<zzpe> zzb = Suppliers.ofInstance(new zzph());

    @Override // com.google.common.base.Supplier
    public final /* synthetic */ zzpe get() {
        return this.zzb.get();
    }

    public static boolean zza() {
        return ((zzpe) zza.get()).zza();
    }
}

package com.google.android.gms.internal.measurement;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.5.0 */
/* loaded from: classes2.dex */
public final class zzpy implements Supplier<zzqb> {
    private static zzpy zza = new zzpy();
    private final Supplier<zzqb> zzb = Suppliers.ofInstance(new zzqa());

    @Override // com.google.common.base.Supplier
    public final /* synthetic */ zzqb get() {
        return this.zzb.get();
    }

    public static boolean zza() {
        return ((zzqb) zza.get()).zza();
    }

    public static boolean zzb() {
        return ((zzqb) zza.get()).zzb();
    }

    public static boolean zzc() {
        return ((zzqb) zza.get()).zzc();
    }
}

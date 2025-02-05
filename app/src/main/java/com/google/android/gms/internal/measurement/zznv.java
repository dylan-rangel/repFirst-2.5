package com.google.android.gms.internal.measurement;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.5.0 */
/* loaded from: classes2.dex */
public final class zznv implements Supplier<zznu> {
    private static zznv zza = new zznv();
    private final Supplier<zznu> zzb = Suppliers.ofInstance(new zznx());

    @Override // com.google.common.base.Supplier
    public final /* synthetic */ zznu get() {
        return this.zzb.get();
    }

    public static boolean zza() {
        return ((zznu) zza.get()).zza();
    }

    public static boolean zzb() {
        return ((zznu) zza.get()).zzb();
    }
}

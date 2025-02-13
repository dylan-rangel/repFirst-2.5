package com.google.android.gms.internal.measurement;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.5.0 */
/* loaded from: classes2.dex */
public final class zzoc implements Supplier<zzof> {
    private static zzoc zza = new zzoc();
    private final Supplier<zzof> zzb = Suppliers.ofInstance(new zzoe());

    @Override // com.google.common.base.Supplier
    public final /* synthetic */ zzof get() {
        return this.zzb.get();
    }

    public static boolean zza() {
        return ((zzof) zza.get()).zza();
    }

    public static boolean zzb() {
        return ((zzof) zza.get()).zzb();
    }
}

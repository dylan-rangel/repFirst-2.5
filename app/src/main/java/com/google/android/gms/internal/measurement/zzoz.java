package com.google.android.gms.internal.measurement;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.5.0 */
/* loaded from: classes2.dex */
public final class zzoz implements Supplier<zzoy> {
    private static zzoz zza = new zzoz();
    private final Supplier<zzoy> zzb = Suppliers.ofInstance(new zzpb());

    @Override // com.google.common.base.Supplier
    public final /* synthetic */ zzoy get() {
        return this.zzb.get();
    }

    public static boolean zza() {
        return ((zzoy) zza.get()).zza();
    }
}

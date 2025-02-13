package com.google.android.gms.internal.measurement;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.5.0 */
/* loaded from: classes2.dex */
public final class zzon implements Supplier<zzom> {
    private static zzon zza = new zzon();
    private final Supplier<zzom> zzb = Suppliers.ofInstance(new zzop());

    @Override // com.google.common.base.Supplier
    public final /* synthetic */ zzom get() {
        return this.zzb.get();
    }

    public static boolean zza() {
        return ((zzom) zza.get()).zza();
    }

    public static boolean zzb() {
        return ((zzom) zza.get()).zzb();
    }
}

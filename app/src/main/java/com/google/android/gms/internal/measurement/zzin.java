package com.google.android.gms.internal.measurement;

/* compiled from: com.google.android.gms:play-services-measurement-base@@21.5.0 */
/* loaded from: classes2.dex */
final class zzin {
    private static final zzim<?> zza = new zzio();
    private static final zzim<?> zzb = zzc();

    static zzim<?> zza() {
        zzim<?> zzimVar = zzb;
        if (zzimVar != null) {
            return zzimVar;
        }
        throw new IllegalStateException("Protobuf runtime is not correctly loaded.");
    }

    static zzim<?> zzb() {
        return zza;
    }

    private static zzim<?> zzc() {
        try {
            return (zzim) Class.forName("com.google.protobuf.ExtensionSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception unused) {
            return null;
        }
    }
}

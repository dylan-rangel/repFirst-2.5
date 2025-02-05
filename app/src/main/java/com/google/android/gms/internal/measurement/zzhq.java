package com.google.android.gms.internal.measurement;

/* compiled from: com.google.android.gms:play-services-measurement-base@@21.5.0 */
/* loaded from: classes2.dex */
final class zzhq extends zzhw {
    private final int zzc;
    private final int zzd;

    @Override // com.google.android.gms.internal.measurement.zzhw, com.google.android.gms.internal.measurement.zzhm
    public final byte zza(int i) {
        int zzb = zzb();
        if (((zzb - (i + 1)) | i) >= 0) {
            return this.zzb[this.zzc + i];
        }
        if (i < 0) {
            throw new ArrayIndexOutOfBoundsException("Index < 0: " + i);
        }
        throw new ArrayIndexOutOfBoundsException("Index > length: " + i + ", " + zzb);
    }

    @Override // com.google.android.gms.internal.measurement.zzhw, com.google.android.gms.internal.measurement.zzhm
    final byte zzb(int i) {
        return this.zzb[this.zzc + i];
    }

    @Override // com.google.android.gms.internal.measurement.zzhw
    protected final int zze() {
        return this.zzc;
    }

    @Override // com.google.android.gms.internal.measurement.zzhw, com.google.android.gms.internal.measurement.zzhm
    public final int zzb() {
        return this.zzd;
    }

    zzhq(byte[] bArr, int i, int i2) {
        super(bArr);
        zza(i, i + i2, bArr.length);
        this.zzc = i;
        this.zzd = i2;
    }
}

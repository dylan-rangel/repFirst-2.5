package com.google.android.gms.internal.measurement;

import java.io.IOException;
import java.nio.charset.Charset;

/* compiled from: com.google.android.gms:play-services-measurement-base@@21.5.0 */
/* loaded from: classes2.dex */
class zzhw extends zzhx {
    protected final byte[] zzb;

    @Override // com.google.android.gms.internal.measurement.zzhm
    public byte zza(int i) {
        return this.zzb[i];
    }

    protected int zze() {
        return 0;
    }

    @Override // com.google.android.gms.internal.measurement.zzhm
    byte zzb(int i) {
        return this.zzb[i];
    }

    @Override // com.google.android.gms.internal.measurement.zzhm
    protected final int zzb(int i, int i2, int i3) {
        return zziz.zza(i, this.zzb, zze(), i3);
    }

    @Override // com.google.android.gms.internal.measurement.zzhm
    public int zzb() {
        return this.zzb.length;
    }

    @Override // com.google.android.gms.internal.measurement.zzhm
    public final zzhm zza(int i, int i2) {
        int zza = zza(0, i2, zzb());
        if (zza == 0) {
            return zzhm.zza;
        }
        return new zzhq(this.zzb, zze(), zza);
    }

    @Override // com.google.android.gms.internal.measurement.zzhm
    protected final String zza(Charset charset) {
        return new String(this.zzb, zze(), zzb(), charset);
    }

    zzhw(byte[] bArr) {
        bArr.getClass();
        this.zzb = bArr;
    }

    @Override // com.google.android.gms.internal.measurement.zzhm
    final void zza(zzhn zzhnVar) throws IOException {
        zzhnVar.zza(this.zzb, zze(), zzb());
    }

    @Override // com.google.android.gms.internal.measurement.zzhm
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzhm) || zzb() != ((zzhm) obj).zzb()) {
            return false;
        }
        if (zzb() == 0) {
            return true;
        }
        if (obj instanceof zzhw) {
            zzhw zzhwVar = (zzhw) obj;
            int zza = zza();
            int zza2 = zzhwVar.zza();
            if (zza == 0 || zza2 == 0 || zza == zza2) {
                return zza(zzhwVar, 0, zzb());
            }
            return false;
        }
        return obj.equals(this);
    }

    @Override // com.google.android.gms.internal.measurement.zzhx
    final boolean zza(zzhm zzhmVar, int i, int i2) {
        if (i2 > zzhmVar.zzb()) {
            throw new IllegalArgumentException("Length too large: " + i2 + zzb());
        }
        if (i2 > zzhmVar.zzb()) {
            throw new IllegalArgumentException("Ran off end of other: 0, " + i2 + ", " + zzhmVar.zzb());
        }
        if (zzhmVar instanceof zzhw) {
            zzhw zzhwVar = (zzhw) zzhmVar;
            byte[] bArr = this.zzb;
            byte[] bArr2 = zzhwVar.zzb;
            int zze = zze() + i2;
            int zze2 = zze();
            int zze3 = zzhwVar.zze();
            while (zze2 < zze) {
                if (bArr[zze2] != bArr2[zze3]) {
                    return false;
                }
                zze2++;
                zze3++;
            }
            return true;
        }
        return zzhmVar.zza(0, i2).equals(zza(0, i2));
    }

    @Override // com.google.android.gms.internal.measurement.zzhm
    public final boolean zzd() {
        int zze = zze();
        return zzmh.zzc(this.zzb, zze, zzb() + zze);
    }
}

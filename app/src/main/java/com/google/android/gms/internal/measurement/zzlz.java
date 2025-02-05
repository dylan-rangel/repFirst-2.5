package com.google.android.gms.internal.measurement;

import java.io.IOException;
import java.util.Arrays;

/* compiled from: com.google.android.gms:play-services-measurement-base@@21.5.0 */
/* loaded from: classes2.dex */
public final class zzlz {
    private static final zzlz zza = new zzlz(0, new int[0], new Object[0], false);
    private int zzb;
    private int[] zzc;
    private Object[] zzd;
    private int zze;
    private boolean zzf;

    public final int zza() {
        int zzg;
        int i = this.zze;
        if (i != -1) {
            return i;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < this.zzb; i3++) {
            int i4 = this.zzc[i3];
            int i5 = i4 >>> 3;
            int i6 = i4 & 7;
            if (i6 == 0) {
                zzg = zzig.zzg(i5, ((Long) this.zzd[i3]).longValue());
            } else if (i6 == 1) {
                zzg = zzig.zzc(i5, ((Long) this.zzd[i3]).longValue());
            } else if (i6 == 2) {
                zzg = zzig.zzc(i5, (zzhm) this.zzd[i3]);
            } else if (i6 == 3) {
                zzg = (zzig.zzi(i5) << 1) + ((zzlz) this.zzd[i3]).zza();
            } else {
                if (i6 != 5) {
                    throw new IllegalStateException(zzji.zza());
                }
                zzg = zzig.zzf(i5, ((Integer) this.zzd[i3]).intValue());
            }
            i2 += zzg;
        }
        this.zze = i2;
        return i2;
    }

    public final int zzb() {
        int i = this.zze;
        if (i != -1) {
            return i;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < this.zzb; i3++) {
            i2 += zzig.zzd(this.zzc[i3] >>> 3, (zzhm) this.zzd[i3]);
        }
        this.zze = i2;
        return i2;
    }

    public final int hashCode() {
        int i = this.zzb;
        int i2 = (i + 527) * 31;
        int[] iArr = this.zzc;
        int i3 = 17;
        int i4 = 17;
        for (int i5 = 0; i5 < i; i5++) {
            i4 = (i4 * 31) + iArr[i5];
        }
        int i6 = (i2 + i4) * 31;
        Object[] objArr = this.zzd;
        int i7 = this.zzb;
        for (int i8 = 0; i8 < i7; i8++) {
            i3 = (i3 * 31) + objArr[i8].hashCode();
        }
        return i6 + i3;
    }

    public static zzlz zzc() {
        return zza;
    }

    final zzlz zza(zzlz zzlzVar) {
        if (zzlzVar.equals(zza)) {
            return this;
        }
        zzf();
        int i = this.zzb + zzlzVar.zzb;
        zza(i);
        System.arraycopy(zzlzVar.zzc, 0, this.zzc, this.zzb, zzlzVar.zzb);
        System.arraycopy(zzlzVar.zzd, 0, this.zzd, this.zzb, zzlzVar.zzb);
        this.zzb = i;
        return this;
    }

    static zzlz zza(zzlz zzlzVar, zzlz zzlzVar2) {
        int i = zzlzVar.zzb + zzlzVar2.zzb;
        int[] copyOf = Arrays.copyOf(zzlzVar.zzc, i);
        System.arraycopy(zzlzVar2.zzc, 0, copyOf, zzlzVar.zzb, zzlzVar2.zzb);
        Object[] copyOf2 = Arrays.copyOf(zzlzVar.zzd, i);
        System.arraycopy(zzlzVar2.zzd, 0, copyOf2, zzlzVar.zzb, zzlzVar2.zzb);
        return new zzlz(i, copyOf, copyOf2, true);
    }

    static zzlz zzd() {
        return new zzlz();
    }

    private zzlz() {
        this(0, new int[8], new Object[8], true);
    }

    private zzlz(int i, int[] iArr, Object[] objArr, boolean z) {
        this.zze = -1;
        this.zzb = i;
        this.zzc = iArr;
        this.zzd = objArr;
        this.zzf = z;
    }

    private final void zzf() {
        if (!this.zzf) {
            throw new UnsupportedOperationException();
        }
    }

    private final void zza(int i) {
        int[] iArr = this.zzc;
        if (i > iArr.length) {
            int i2 = this.zzb;
            int i3 = i2 + (i2 / 2);
            if (i3 >= i) {
                i = i3;
            }
            if (i < 8) {
                i = 8;
            }
            this.zzc = Arrays.copyOf(iArr, i);
            this.zzd = Arrays.copyOf(this.zzd, i);
        }
    }

    public final void zze() {
        if (this.zzf) {
            this.zzf = false;
        }
    }

    final void zza(StringBuilder sb, int i) {
        for (int i2 = 0; i2 < this.zzb; i2++) {
            zzko.zza(sb, i, String.valueOf(this.zzc[i2] >>> 3), this.zzd[i2]);
        }
    }

    final void zza(int i, Object obj) {
        zzf();
        zza(this.zzb + 1);
        int[] iArr = this.zzc;
        int i2 = this.zzb;
        iArr[i2] = i;
        this.zzd[i2] = obj;
        this.zzb = i2 + 1;
    }

    final void zza(zzmw zzmwVar) throws IOException {
        if (zzmwVar.zza() == zzmz.zzb) {
            for (int i = this.zzb - 1; i >= 0; i--) {
                zzmwVar.zza(this.zzc[i] >>> 3, this.zzd[i]);
            }
            return;
        }
        for (int i2 = 0; i2 < this.zzb; i2++) {
            zzmwVar.zza(this.zzc[i2] >>> 3, this.zzd[i2]);
        }
    }

    private static void zza(int i, Object obj, zzmw zzmwVar) throws IOException {
        int i2 = i >>> 3;
        int i3 = i & 7;
        if (i3 == 0) {
            zzmwVar.zzb(i2, ((Long) obj).longValue());
            return;
        }
        if (i3 == 1) {
            zzmwVar.zza(i2, ((Long) obj).longValue());
            return;
        }
        if (i3 == 2) {
            zzmwVar.zza(i2, (zzhm) obj);
            return;
        }
        if (i3 != 3) {
            if (i3 == 5) {
                zzmwVar.zzb(i2, ((Integer) obj).intValue());
                return;
            }
            throw new RuntimeException(zzji.zza());
        }
        if (zzmwVar.zza() == zzmz.zza) {
            zzmwVar.zzb(i2);
            ((zzlz) obj).zzb(zzmwVar);
            zzmwVar.zza(i2);
        } else {
            zzmwVar.zza(i2);
            ((zzlz) obj).zzb(zzmwVar);
            zzmwVar.zzb(i2);
        }
    }

    public final void zzb(zzmw zzmwVar) throws IOException {
        if (this.zzb == 0) {
            return;
        }
        if (zzmwVar.zza() == zzmz.zza) {
            for (int i = 0; i < this.zzb; i++) {
                zza(this.zzc[i], this.zzd[i], zzmwVar);
            }
            return;
        }
        for (int i2 = this.zzb - 1; i2 >= 0; i2--) {
            zza(this.zzc[i2], this.zzd[i2], zzmwVar);
        }
    }

    public final boolean equals(Object obj) {
        boolean z;
        boolean z2;
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof zzlz)) {
            return false;
        }
        zzlz zzlzVar = (zzlz) obj;
        int i = this.zzb;
        if (i == zzlzVar.zzb) {
            int[] iArr = this.zzc;
            int[] iArr2 = zzlzVar.zzc;
            int i2 = 0;
            while (true) {
                if (i2 >= i) {
                    z = true;
                    break;
                }
                if (iArr[i2] != iArr2[i2]) {
                    z = false;
                    break;
                }
                i2++;
            }
            if (z) {
                Object[] objArr = this.zzd;
                Object[] objArr2 = zzlzVar.zzd;
                int i3 = this.zzb;
                int i4 = 0;
                while (true) {
                    if (i4 >= i3) {
                        z2 = true;
                        break;
                    }
                    if (!objArr[i4].equals(objArr2[i4])) {
                        z2 = false;
                        break;
                    }
                    i4++;
                }
                if (z2) {
                    return true;
                }
            }
        }
        return false;
    }
}

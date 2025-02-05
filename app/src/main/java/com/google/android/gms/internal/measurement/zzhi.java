package com.google.android.gms.internal.measurement;

import com.google.common.base.Ascii;
import java.io.IOException;

/* compiled from: com.google.android.gms:play-services-measurement-base@@21.5.0 */
/* loaded from: classes2.dex */
final class zzhi {
    static double zza(byte[] bArr, int i) {
        return Double.longBitsToDouble(zzd(bArr, i));
    }

    static float zzb(byte[] bArr, int i) {
        return Float.intBitsToFloat(zzc(bArr, i));
    }

    static int zza(byte[] bArr, int i, zzhl zzhlVar) throws zzji {
        int zzc = zzc(bArr, i, zzhlVar);
        int i2 = zzhlVar.zza;
        if (i2 < 0) {
            throw zzji.zzf();
        }
        if (i2 > bArr.length - zzc) {
            throw zzji.zzh();
        }
        if (i2 == 0) {
            zzhlVar.zzc = zzhm.zza;
            return zzc;
        }
        zzhlVar.zzc = zzhm.zza(bArr, zzc, i2);
        return zzc + i2;
    }

    static int zzc(byte[] bArr, int i) {
        return ((bArr[i + 3] & 255) << 24) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16);
    }

    static int zza(zzlb zzlbVar, byte[] bArr, int i, int i2, int i3, zzhl zzhlVar) throws IOException {
        Object zza = zzlbVar.zza();
        int zza2 = zza(zza, zzlbVar, bArr, i, i2, i3, zzhlVar);
        zzlbVar.zzc(zza);
        zzhlVar.zzc = zza;
        return zza2;
    }

    static int zza(zzlb zzlbVar, byte[] bArr, int i, int i2, zzhl zzhlVar) throws IOException {
        Object zza = zzlbVar.zza();
        int zza2 = zza(zza, zzlbVar, bArr, i, i2, zzhlVar);
        zzlbVar.zzc(zza);
        zzhlVar.zzc = zza;
        return zza2;
    }

    static int zza(zzlb<?> zzlbVar, int i, byte[] bArr, int i2, int i3, zzjf<?> zzjfVar, zzhl zzhlVar) throws IOException {
        int zza = zza(zzlbVar, bArr, i2, i3, zzhlVar);
        zzjfVar.add(zzhlVar.zzc);
        while (zza < i3) {
            int zzc = zzc(bArr, zza, zzhlVar);
            if (i != zzhlVar.zza) {
                break;
            }
            zza = zza(zzlbVar, bArr, zzc, i3, zzhlVar);
            zzjfVar.add(zzhlVar.zzc);
        }
        return zza;
    }

    static int zza(byte[] bArr, int i, zzjf<?> zzjfVar, zzhl zzhlVar) throws IOException {
        zzja zzjaVar = (zzja) zzjfVar;
        int zzc = zzc(bArr, i, zzhlVar);
        int i2 = zzhlVar.zza + zzc;
        while (zzc < i2) {
            zzc = zzc(bArr, zzc, zzhlVar);
            zzjaVar.zzd(zzhlVar.zza);
        }
        if (zzc == i2) {
            return zzc;
        }
        throw zzji.zzh();
    }

    static int zzb(byte[] bArr, int i, zzhl zzhlVar) throws zzji {
        int zzc = zzc(bArr, i, zzhlVar);
        int i2 = zzhlVar.zza;
        if (i2 < 0) {
            throw zzji.zzf();
        }
        if (i2 == 0) {
            zzhlVar.zzc = "";
            return zzc;
        }
        zzhlVar.zzc = zzmh.zzb(bArr, zzc, i2);
        return zzc + i2;
    }

    static int zza(int i, byte[] bArr, int i2, int i3, zzlz zzlzVar, zzhl zzhlVar) throws zzji {
        if ((i >>> 3) == 0) {
            throw zzji.zzc();
        }
        int i4 = i & 7;
        if (i4 == 0) {
            int zzd = zzd(bArr, i2, zzhlVar);
            zzlzVar.zza(i, Long.valueOf(zzhlVar.zzb));
            return zzd;
        }
        if (i4 == 1) {
            zzlzVar.zza(i, Long.valueOf(zzd(bArr, i2)));
            return i2 + 8;
        }
        if (i4 == 2) {
            int zzc = zzc(bArr, i2, zzhlVar);
            int i5 = zzhlVar.zza;
            if (i5 < 0) {
                throw zzji.zzf();
            }
            if (i5 > bArr.length - zzc) {
                throw zzji.zzh();
            }
            if (i5 == 0) {
                zzlzVar.zza(i, zzhm.zza);
            } else {
                zzlzVar.zza(i, zzhm.zza(bArr, zzc, i5));
            }
            return zzc + i5;
        }
        if (i4 != 3) {
            if (i4 == 5) {
                zzlzVar.zza(i, Integer.valueOf(zzc(bArr, i2)));
                return i2 + 4;
            }
            throw zzji.zzc();
        }
        zzlz zzd2 = zzlz.zzd();
        int i6 = (i & (-8)) | 4;
        int i7 = 0;
        while (true) {
            if (i2 >= i3) {
                break;
            }
            int zzc2 = zzc(bArr, i2, zzhlVar);
            int i8 = zzhlVar.zza;
            i7 = i8;
            if (i8 == i6) {
                i2 = zzc2;
                break;
            }
            int zza = zza(i7, bArr, zzc2, i3, zzd2, zzhlVar);
            i7 = i8;
            i2 = zza;
        }
        if (i2 > i3 || i7 != i6) {
            throw zzji.zzg();
        }
        zzlzVar.zza(i, zzd2);
        return i2;
    }

    static int zzc(byte[] bArr, int i, zzhl zzhlVar) {
        int i2 = i + 1;
        byte b = bArr[i];
        if (b >= 0) {
            zzhlVar.zza = b;
            return i2;
        }
        return zza(b, bArr, i2, zzhlVar);
    }

    static int zza(int i, byte[] bArr, int i2, zzhl zzhlVar) {
        int i3 = i & 127;
        int i4 = i2 + 1;
        byte b = bArr[i2];
        if (b >= 0) {
            zzhlVar.zza = i3 | (b << 7);
            return i4;
        }
        int i5 = i3 | ((b & Byte.MAX_VALUE) << 7);
        int i6 = i4 + 1;
        byte b2 = bArr[i4];
        if (b2 >= 0) {
            zzhlVar.zza = i5 | (b2 << Ascii.SO);
            return i6;
        }
        int i7 = i5 | ((b2 & Byte.MAX_VALUE) << 14);
        int i8 = i6 + 1;
        byte b3 = bArr[i6];
        if (b3 >= 0) {
            zzhlVar.zza = i7 | (b3 << Ascii.NAK);
            return i8;
        }
        int i9 = i7 | ((b3 & Byte.MAX_VALUE) << 21);
        int i10 = i8 + 1;
        byte b4 = bArr[i8];
        if (b4 >= 0) {
            zzhlVar.zza = i9 | (b4 << Ascii.FS);
            return i10;
        }
        int i11 = i9 | ((b4 & Byte.MAX_VALUE) << 28);
        while (true) {
            int i12 = i10 + 1;
            if (bArr[i10] >= 0) {
                zzhlVar.zza = i11;
                return i12;
            }
            i10 = i12;
        }
    }

    static int zza(int i, byte[] bArr, int i2, int i3, zzjf<?> zzjfVar, zzhl zzhlVar) {
        zzja zzjaVar = (zzja) zzjfVar;
        int zzc = zzc(bArr, i2, zzhlVar);
        zzjaVar.zzd(zzhlVar.zza);
        while (zzc < i3) {
            int zzc2 = zzc(bArr, zzc, zzhlVar);
            if (i != zzhlVar.zza) {
                break;
            }
            zzc = zzc(bArr, zzc2, zzhlVar);
            zzjaVar.zzd(zzhlVar.zza);
        }
        return zzc;
    }

    static int zzd(byte[] bArr, int i, zzhl zzhlVar) {
        int i2 = i + 1;
        long j = bArr[i];
        if (j >= 0) {
            zzhlVar.zzb = j;
            return i2;
        }
        int i3 = i2 + 1;
        byte b = bArr[i2];
        long j2 = (j & 127) | ((b & Byte.MAX_VALUE) << 7);
        int i4 = 7;
        while (b < 0) {
            int i5 = i3 + 1;
            i4 += 7;
            j2 |= (r10 & Byte.MAX_VALUE) << i4;
            b = bArr[i3];
            i3 = i5;
        }
        zzhlVar.zzb = j2;
        return i3;
    }

    static int zza(Object obj, zzlb zzlbVar, byte[] bArr, int i, int i2, int i3, zzhl zzhlVar) throws IOException {
        int zza = ((zzkn) zzlbVar).zza((zzkn) obj, bArr, i, i2, i3, zzhlVar);
        zzhlVar.zzc = obj;
        return zza;
    }

    static int zza(Object obj, zzlb zzlbVar, byte[] bArr, int i, int i2, zzhl zzhlVar) throws IOException {
        int i3 = i + 1;
        int i4 = bArr[i];
        if (i4 < 0) {
            i3 = zza(i4, bArr, i3, zzhlVar);
            i4 = zzhlVar.zza;
        }
        int i5 = i3;
        if (i4 < 0 || i4 > i2 - i5) {
            throw zzji.zzh();
        }
        int i6 = i4 + i5;
        zzlbVar.zza(obj, bArr, i5, i6, zzhlVar);
        zzhlVar.zzc = obj;
        return i6;
    }

    static int zza(int i, byte[] bArr, int i2, int i3, zzhl zzhlVar) throws zzji {
        if ((i >>> 3) == 0) {
            throw zzji.zzc();
        }
        int i4 = i & 7;
        if (i4 == 0) {
            return zzd(bArr, i2, zzhlVar);
        }
        if (i4 == 1) {
            return i2 + 8;
        }
        if (i4 == 2) {
            return zzc(bArr, i2, zzhlVar) + zzhlVar.zza;
        }
        if (i4 != 3) {
            if (i4 == 5) {
                return i2 + 4;
            }
            throw zzji.zzc();
        }
        int i5 = (i & (-8)) | 4;
        int i6 = 0;
        while (i2 < i3) {
            i2 = zzc(bArr, i2, zzhlVar);
            i6 = zzhlVar.zza;
            if (i6 == i5) {
                break;
            }
            i2 = zza(i6, bArr, i2, i3, zzhlVar);
        }
        if (i2 > i3 || i6 != i5) {
            throw zzji.zzg();
        }
        return i2;
    }

    static long zzd(byte[] bArr, int i) {
        return ((bArr[i + 7] & 255) << 56) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16) | ((bArr[i + 3] & 255) << 24) | ((bArr[i + 4] & 255) << 32) | ((bArr[i + 5] & 255) << 40) | ((bArr[i + 6] & 255) << 48);
    }
}

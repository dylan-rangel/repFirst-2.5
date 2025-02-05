package com.google.android.gms.internal.measurement;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import sun.misc.Unsafe;

/* compiled from: com.google.android.gms:play-services-measurement-base@@21.5.0 */
/* loaded from: classes2.dex */
final class zzkn<T> implements zzlb<T> {
    private static final int[] zza = new int[0];
    private static final Unsafe zzb = zzmg.zzb();
    private final int[] zzc;
    private final Object[] zzd;
    private final int zze;
    private final int zzf;
    private final zzkj zzg;
    private final boolean zzh;
    private final boolean zzi;
    private final zzky zzj;
    private final boolean zzk;
    private final int[] zzl;
    private final int zzm;
    private final int zzn;
    private final zzkr zzo;
    private final zzjs zzp;
    private final zzma<?, ?> zzq;
    private final zzim<?> zzr;
    private final zzkg zzs;

    private static <T> double zza(T t, long j) {
        return ((Double) zzmg.zze(t, j)).doubleValue();
    }

    private static boolean zzg(int i) {
        return (i & 536870912) != 0;
    }

    private static <T> float zzb(T t, long j) {
        return ((Float) zzmg.zze(t, j)).floatValue();
    }

    private static int zza(byte[] bArr, int i, int i2, zzmn zzmnVar, Class<?> cls, zzhl zzhlVar) throws IOException {
        switch (zzkq.zza[zzmnVar.ordinal()]) {
            case 1:
                int zzd = zzhi.zzd(bArr, i, zzhlVar);
                zzhlVar.zzc = Boolean.valueOf(zzhlVar.zzb != 0);
                return zzd;
            case 2:
                return zzhi.zza(bArr, i, zzhlVar);
            case 3:
                zzhlVar.zzc = Double.valueOf(zzhi.zza(bArr, i));
                return i + 8;
            case 4:
            case 5:
                zzhlVar.zzc = Integer.valueOf(zzhi.zzc(bArr, i));
                return i + 4;
            case 6:
            case 7:
                zzhlVar.zzc = Long.valueOf(zzhi.zzd(bArr, i));
                return i + 8;
            case 8:
                zzhlVar.zzc = Float.valueOf(zzhi.zzb(bArr, i));
                return i + 4;
            case 9:
            case 10:
            case 11:
                int zzc = zzhi.zzc(bArr, i, zzhlVar);
                zzhlVar.zzc = Integer.valueOf(zzhlVar.zza);
                return zzc;
            case 12:
            case 13:
                int zzd2 = zzhi.zzd(bArr, i, zzhlVar);
                zzhlVar.zzc = Long.valueOf(zzhlVar.zzb);
                return zzd2;
            case 14:
                return zzhi.zza(zzkx.zza().zza((Class) cls), bArr, i, i2, zzhlVar);
            case 15:
                int zzc2 = zzhi.zzc(bArr, i, zzhlVar);
                zzhlVar.zzc = Integer.valueOf(zzib.zze(zzhlVar.zza));
                return zzc2;
            case 16:
                int zzd3 = zzhi.zzd(bArr, i, zzhlVar);
                zzhlVar.zzc = Long.valueOf(zzib.zza(zzhlVar.zzb));
                return zzd3;
            case 17:
                return zzhi.zzb(bArr, i, zzhlVar);
            default:
                throw new RuntimeException("unsupported field type.");
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Type inference failed for: r10v0 */
    /* JADX WARN: Type inference failed for: r10v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r10v5 */
    @Override // com.google.android.gms.internal.measurement.zzlb
    public final int zza(T t) {
        int i;
        int i2;
        int i3;
        int zza2;
        int zzb2;
        int zzh;
        int zzd;
        int zzi;
        int zzj;
        Unsafe unsafe = zzb;
        int i4 = 1048575;
        ?? r10 = 0;
        int i5 = 1048575;
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        while (i7 < this.zzc.length) {
            int zzc = zzc(i7);
            int i9 = (267386880 & zzc) >>> 20;
            int[] iArr = this.zzc;
            int i10 = iArr[i7];
            int i11 = iArr[i7 + 2];
            int i12 = i11 & i4;
            if (i9 <= 17) {
                if (i12 != i5) {
                    i6 = i12 == i4 ? 0 : unsafe.getInt(t, i12);
                    i5 = i12;
                }
                i = i5;
                i2 = i6;
                i3 = 1 << (i11 >>> 20);
            } else {
                i = i5;
                i2 = i6;
                i3 = 0;
            }
            long j = zzc & i4;
            if (i9 >= zzir.DOUBLE_LIST_PACKED.zza()) {
                zzir.SINT64_LIST_PACKED.zza();
            }
            int i13 = i3;
            switch (i9) {
                case 0:
                    if (zza((zzkn<T>) t, i7, i, i2, i13)) {
                        zza2 = zzig.zza(i10, 0.0d);
                        i8 += zza2;
                        break;
                    } else {
                        break;
                    }
                case 1:
                    if (zza((zzkn<T>) t, i7, i, i2, i13)) {
                        zza2 = zzig.zza(i10, 0.0f);
                        i8 += zza2;
                        break;
                    } else {
                        break;
                    }
                case 2:
                    if (zza((zzkn<T>) t, i7, i, i2, i13)) {
                        zza2 = zzig.zzd(i10, unsafe.getLong(t, j));
                        i8 += zza2;
                        break;
                    } else {
                        break;
                    }
                case 3:
                    if (zza((zzkn<T>) t, i7, i, i2, i13)) {
                        zza2 = zzig.zzg(i10, unsafe.getLong(t, j));
                        i8 += zza2;
                        break;
                    } else {
                        break;
                    }
                case 4:
                    if (zza((zzkn<T>) t, i7, i, i2, i13)) {
                        zza2 = zzig.zzg(i10, unsafe.getInt(t, j));
                        i8 += zza2;
                        break;
                    } else {
                        break;
                    }
                case 5:
                    if (zza((zzkn<T>) t, i7, i, i2, i13)) {
                        zza2 = zzig.zzc(i10, 0L);
                        i8 += zza2;
                        break;
                    } else {
                        break;
                    }
                case 6:
                    if (zza((zzkn<T>) t, i7, i, i2, i13)) {
                        zza2 = zzig.zzf(i10, 0);
                        i8 += zza2;
                        break;
                    }
                    break;
                case 7:
                    if (zza((zzkn<T>) t, i7, i, i2, i13)) {
                        zzb2 = zzig.zzb(i10, true);
                        i8 += zzb2;
                    }
                    break;
                case 8:
                    if (zza((zzkn<T>) t, i7, i, i2, i13)) {
                        Object object = unsafe.getObject(t, j);
                        if (object instanceof zzhm) {
                            zzb2 = zzig.zzc(i10, (zzhm) object);
                        } else {
                            zzb2 = zzig.zzb(i10, (String) object);
                        }
                        i8 += zzb2;
                    }
                    break;
                case 9:
                    if (zza((zzkn<T>) t, i7, i, i2, i13)) {
                        zzb2 = zzld.zza(i10, unsafe.getObject(t, j), zze(i7));
                        i8 += zzb2;
                    }
                    break;
                case 10:
                    if (zza((zzkn<T>) t, i7, i, i2, i13)) {
                        zzb2 = zzig.zzc(i10, (zzhm) unsafe.getObject(t, j));
                        i8 += zzb2;
                    }
                    break;
                case 11:
                    if (zza((zzkn<T>) t, i7, i, i2, i13)) {
                        zzb2 = zzig.zzj(i10, unsafe.getInt(t, j));
                        i8 += zzb2;
                    }
                    break;
                case 12:
                    if (zza((zzkn<T>) t, i7, i, i2, i13)) {
                        zzb2 = zzig.zze(i10, unsafe.getInt(t, j));
                        i8 += zzb2;
                    }
                    break;
                case 13:
                    if (zza((zzkn<T>) t, i7, i, i2, i13)) {
                        zzh = zzig.zzh(i10, 0);
                        i8 += zzh;
                    }
                    break;
                case 14:
                    if (zza((zzkn<T>) t, i7, i, i2, i13)) {
                        zzb2 = zzig.zze(i10, 0L);
                        i8 += zzb2;
                    }
                    break;
                case 15:
                    if (zza((zzkn<T>) t, i7, i, i2, i13)) {
                        zzb2 = zzig.zzi(i10, unsafe.getInt(t, j));
                        i8 += zzb2;
                    }
                    break;
                case 16:
                    if (zza((zzkn<T>) t, i7, i, i2, i13)) {
                        zzb2 = zzig.zzf(i10, unsafe.getLong(t, j));
                        i8 += zzb2;
                    }
                    break;
                case 17:
                    if (zza((zzkn<T>) t, i7, i, i2, i13)) {
                        zzb2 = zzig.zzb(i10, (zzkj) unsafe.getObject(t, j), zze(i7));
                        i8 += zzb2;
                    }
                    break;
                case 18:
                    zzb2 = zzld.zzd(i10, (List) unsafe.getObject(t, j), r10);
                    i8 += zzb2;
                    break;
                case 19:
                    zzb2 = zzld.zzc(i10, (List) unsafe.getObject(t, j), r10);
                    i8 += zzb2;
                    break;
                case 20:
                    zzb2 = zzld.zzf(i10, (List) unsafe.getObject(t, j), r10);
                    i8 += zzb2;
                    break;
                case 21:
                    zzb2 = zzld.zzj(i10, (List) unsafe.getObject(t, j), r10);
                    i8 += zzb2;
                    break;
                case 22:
                    zzb2 = zzld.zze(i10, (List) unsafe.getObject(t, j), r10);
                    i8 += zzb2;
                    break;
                case 23:
                    zzb2 = zzld.zzd(i10, (List) unsafe.getObject(t, j), r10);
                    i8 += zzb2;
                    break;
                case 24:
                    zzb2 = zzld.zzc(i10, (List) unsafe.getObject(t, j), r10);
                    i8 += zzb2;
                    break;
                case 25:
                    zzb2 = zzld.zza(i10, (List<?>) unsafe.getObject(t, j), (boolean) r10);
                    i8 += zzb2;
                    break;
                case 26:
                    zzb2 = zzld.zzb(i10, (List) unsafe.getObject(t, j));
                    i8 += zzb2;
                    break;
                case 27:
                    zzb2 = zzld.zzb(i10, (List<?>) unsafe.getObject(t, j), zze(i7));
                    i8 += zzb2;
                    break;
                case 28:
                    zzb2 = zzld.zza(i10, (List<zzhm>) unsafe.getObject(t, j));
                    i8 += zzb2;
                    break;
                case 29:
                    zzb2 = zzld.zzi(i10, (List) unsafe.getObject(t, j), r10);
                    i8 += zzb2;
                    break;
                case 30:
                    zzb2 = zzld.zzb(i10, (List<Integer>) unsafe.getObject(t, j), (boolean) r10);
                    i8 += zzb2;
                    break;
                case 31:
                    zzb2 = zzld.zzc(i10, (List) unsafe.getObject(t, j), r10);
                    i8 += zzb2;
                    break;
                case 32:
                    zzb2 = zzld.zzd(i10, (List) unsafe.getObject(t, j), r10);
                    i8 += zzb2;
                    break;
                case 33:
                    zzb2 = zzld.zzg(i10, (List) unsafe.getObject(t, j), r10);
                    i8 += zzb2;
                    break;
                case 34:
                    zzb2 = zzld.zzh(i10, (List) unsafe.getObject(t, j), r10);
                    i8 += zzb2;
                    break;
                case 35:
                    zzd = zzld.zzd((List) unsafe.getObject(t, j));
                    if (zzd > 0) {
                        zzi = zzig.zzi(i10);
                        zzj = zzig.zzj(zzd);
                        zzh = zzi + zzj + zzd;
                        i8 += zzh;
                    }
                    break;
                case 36:
                    zzd = zzld.zzc((List) unsafe.getObject(t, j));
                    if (zzd > 0) {
                        zzi = zzig.zzi(i10);
                        zzj = zzig.zzj(zzd);
                        zzh = zzi + zzj + zzd;
                        i8 += zzh;
                    }
                    break;
                case 37:
                    zzd = zzld.zzf((List) unsafe.getObject(t, j));
                    if (zzd > 0) {
                        zzi = zzig.zzi(i10);
                        zzj = zzig.zzj(zzd);
                        zzh = zzi + zzj + zzd;
                        i8 += zzh;
                    }
                    break;
                case 38:
                    zzd = zzld.zzj((List) unsafe.getObject(t, j));
                    if (zzd > 0) {
                        zzi = zzig.zzi(i10);
                        zzj = zzig.zzj(zzd);
                        zzh = zzi + zzj + zzd;
                        i8 += zzh;
                    }
                    break;
                case 39:
                    zzd = zzld.zze((List) unsafe.getObject(t, j));
                    if (zzd > 0) {
                        zzi = zzig.zzi(i10);
                        zzj = zzig.zzj(zzd);
                        zzh = zzi + zzj + zzd;
                        i8 += zzh;
                    }
                    break;
                case 40:
                    zzd = zzld.zzd((List) unsafe.getObject(t, j));
                    if (zzd > 0) {
                        zzi = zzig.zzi(i10);
                        zzj = zzig.zzj(zzd);
                        zzh = zzi + zzj + zzd;
                        i8 += zzh;
                    }
                    break;
                case 41:
                    zzd = zzld.zzc((List) unsafe.getObject(t, j));
                    if (zzd > 0) {
                        zzi = zzig.zzi(i10);
                        zzj = zzig.zzj(zzd);
                        zzh = zzi + zzj + zzd;
                        i8 += zzh;
                    }
                    break;
                case 42:
                    zzd = zzld.zza((List<?>) unsafe.getObject(t, j));
                    if (zzd > 0) {
                        zzi = zzig.zzi(i10);
                        zzj = zzig.zzj(zzd);
                        zzh = zzi + zzj + zzd;
                        i8 += zzh;
                    }
                    break;
                case 43:
                    zzd = zzld.zzi((List) unsafe.getObject(t, j));
                    if (zzd > 0) {
                        zzi = zzig.zzi(i10);
                        zzj = zzig.zzj(zzd);
                        zzh = zzi + zzj + zzd;
                        i8 += zzh;
                    }
                    break;
                case 44:
                    zzd = zzld.zzb((List) unsafe.getObject(t, j));
                    if (zzd > 0) {
                        zzi = zzig.zzi(i10);
                        zzj = zzig.zzj(zzd);
                        zzh = zzi + zzj + zzd;
                        i8 += zzh;
                    }
                    break;
                case 45:
                    zzd = zzld.zzc((List) unsafe.getObject(t, j));
                    if (zzd > 0) {
                        zzi = zzig.zzi(i10);
                        zzj = zzig.zzj(zzd);
                        zzh = zzi + zzj + zzd;
                        i8 += zzh;
                    }
                    break;
                case 46:
                    zzd = zzld.zzd((List) unsafe.getObject(t, j));
                    if (zzd > 0) {
                        zzi = zzig.zzi(i10);
                        zzj = zzig.zzj(zzd);
                        zzh = zzi + zzj + zzd;
                        i8 += zzh;
                    }
                    break;
                case 47:
                    zzd = zzld.zzg((List) unsafe.getObject(t, j));
                    if (zzd > 0) {
                        zzi = zzig.zzi(i10);
                        zzj = zzig.zzj(zzd);
                        zzh = zzi + zzj + zzd;
                        i8 += zzh;
                    }
                    break;
                case 48:
                    zzd = zzld.zzh((List) unsafe.getObject(t, j));
                    if (zzd > 0) {
                        zzi = zzig.zzi(i10);
                        zzj = zzig.zzj(zzd);
                        zzh = zzi + zzj + zzd;
                        i8 += zzh;
                    }
                    break;
                case 49:
                    zzb2 = zzld.zza(i10, (List<zzkj>) unsafe.getObject(t, j), zze(i7));
                    i8 += zzb2;
                    break;
                case 50:
                    zzb2 = this.zzs.zza(i10, unsafe.getObject(t, j), zzf(i7));
                    i8 += zzb2;
                    break;
                case 51:
                    if (zzc((zzkn<T>) t, i10, i7)) {
                        zzb2 = zzig.zza(i10, 0.0d);
                        i8 += zzb2;
                    }
                    break;
                case 52:
                    if (zzc((zzkn<T>) t, i10, i7)) {
                        zzb2 = zzig.zza(i10, 0.0f);
                        i8 += zzb2;
                    }
                    break;
                case 53:
                    if (zzc((zzkn<T>) t, i10, i7)) {
                        zzb2 = zzig.zzd(i10, zzd(t, j));
                        i8 += zzb2;
                    }
                    break;
                case 54:
                    if (zzc((zzkn<T>) t, i10, i7)) {
                        zzb2 = zzig.zzg(i10, zzd(t, j));
                        i8 += zzb2;
                    }
                    break;
                case 55:
                    if (zzc((zzkn<T>) t, i10, i7)) {
                        zzb2 = zzig.zzg(i10, zzc(t, j));
                        i8 += zzb2;
                    }
                    break;
                case 56:
                    if (zzc((zzkn<T>) t, i10, i7)) {
                        zzb2 = zzig.zzc(i10, 0L);
                        i8 += zzb2;
                    }
                    break;
                case 57:
                    if (zzc((zzkn<T>) t, i10, i7)) {
                        zzb2 = zzig.zzf(i10, (int) r10);
                        i8 += zzb2;
                    }
                    break;
                case 58:
                    if (zzc((zzkn<T>) t, i10, i7)) {
                        zzb2 = zzig.zzb(i10, true);
                        i8 += zzb2;
                    }
                    break;
                case 59:
                    if (zzc((zzkn<T>) t, i10, i7)) {
                        Object object2 = unsafe.getObject(t, j);
                        if (object2 instanceof zzhm) {
                            zzb2 = zzig.zzc(i10, (zzhm) object2);
                        } else {
                            zzb2 = zzig.zzb(i10, (String) object2);
                        }
                        i8 += zzb2;
                    }
                    break;
                case 60:
                    if (zzc((zzkn<T>) t, i10, i7)) {
                        zzb2 = zzld.zza(i10, unsafe.getObject(t, j), zze(i7));
                        i8 += zzb2;
                    }
                    break;
                case 61:
                    if (zzc((zzkn<T>) t, i10, i7)) {
                        zzb2 = zzig.zzc(i10, (zzhm) unsafe.getObject(t, j));
                        i8 += zzb2;
                    }
                    break;
                case 62:
                    if (zzc((zzkn<T>) t, i10, i7)) {
                        zzb2 = zzig.zzj(i10, zzc(t, j));
                        i8 += zzb2;
                    }
                    break;
                case 63:
                    if (zzc((zzkn<T>) t, i10, i7)) {
                        zzb2 = zzig.zze(i10, zzc(t, j));
                        i8 += zzb2;
                    }
                    break;
                case 64:
                    if (zzc((zzkn<T>) t, i10, i7)) {
                        zzb2 = zzig.zzh(i10, (int) r10);
                        i8 += zzb2;
                    }
                    break;
                case 65:
                    if (zzc((zzkn<T>) t, i10, i7)) {
                        zzb2 = zzig.zze(i10, 0L);
                        i8 += zzb2;
                    }
                    break;
                case 66:
                    if (zzc((zzkn<T>) t, i10, i7)) {
                        zzb2 = zzig.zzi(i10, zzc(t, j));
                        i8 += zzb2;
                    }
                    break;
                case 67:
                    if (zzc((zzkn<T>) t, i10, i7)) {
                        zzb2 = zzig.zzf(i10, zzd(t, j));
                        i8 += zzb2;
                    }
                    break;
                case 68:
                    if (zzc((zzkn<T>) t, i10, i7)) {
                        zzb2 = zzig.zzb(i10, (zzkj) unsafe.getObject(t, j), zze(i7));
                        i8 += zzb2;
                    }
                    break;
            }
            i7 += 3;
            i5 = i;
            i6 = i2;
            i4 = 1048575;
            r10 = 0;
        }
        int i14 = 0;
        zzma<?, ?> zzmaVar = this.zzq;
        int zza3 = i8 + zzmaVar.zza((zzma<?, ?>) zzmaVar.zzd(t));
        if (!this.zzh) {
            return zza3;
        }
        zziq<?> zza4 = this.zzr.zza(t);
        for (int i15 = 0; i15 < zza4.zza.zzb(); i15++) {
            Map.Entry<?, Object> zzb3 = zza4.zza.zzb(i15);
            i14 += zziq.zza((zzis<?>) zzb3.getKey(), zzb3.getValue());
        }
        for (Map.Entry<?, Object> entry : zza4.zza.zzc()) {
            i14 += zziq.zza((zzis<?>) entry.getKey(), entry.getValue());
        }
        return zza3 + i14;
    }

    @Override // com.google.android.gms.internal.measurement.zzlb
    public final int zzb(T t) {
        int i;
        int zza2;
        int length = this.zzc.length;
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3 += 3) {
            int zzc = zzc(i3);
            int i4 = this.zzc[i3];
            long j = 1048575 & zzc;
            int i5 = 37;
            switch ((zzc & 267386880) >>> 20) {
                case 0:
                    i = i2 * 53;
                    zza2 = zziz.zza(Double.doubleToLongBits(zzmg.zza(t, j)));
                    i2 = i + zza2;
                    break;
                case 1:
                    i = i2 * 53;
                    zza2 = Float.floatToIntBits(zzmg.zzb(t, j));
                    i2 = i + zza2;
                    break;
                case 2:
                    i = i2 * 53;
                    zza2 = zziz.zza(zzmg.zzd(t, j));
                    i2 = i + zza2;
                    break;
                case 3:
                    i = i2 * 53;
                    zza2 = zziz.zza(zzmg.zzd(t, j));
                    i2 = i + zza2;
                    break;
                case 4:
                    i = i2 * 53;
                    zza2 = zzmg.zzc(t, j);
                    i2 = i + zza2;
                    break;
                case 5:
                    i = i2 * 53;
                    zza2 = zziz.zza(zzmg.zzd(t, j));
                    i2 = i + zza2;
                    break;
                case 6:
                    i = i2 * 53;
                    zza2 = zzmg.zzc(t, j);
                    i2 = i + zza2;
                    break;
                case 7:
                    i = i2 * 53;
                    zza2 = zziz.zza(zzmg.zzh(t, j));
                    i2 = i + zza2;
                    break;
                case 8:
                    i = i2 * 53;
                    zza2 = ((String) zzmg.zze(t, j)).hashCode();
                    i2 = i + zza2;
                    break;
                case 9:
                    Object zze = zzmg.zze(t, j);
                    if (zze != null) {
                        i5 = zze.hashCode();
                    }
                    i2 = (i2 * 53) + i5;
                    break;
                case 10:
                    i = i2 * 53;
                    zza2 = zzmg.zze(t, j).hashCode();
                    i2 = i + zza2;
                    break;
                case 11:
                    i = i2 * 53;
                    zza2 = zzmg.zzc(t, j);
                    i2 = i + zza2;
                    break;
                case 12:
                    i = i2 * 53;
                    zza2 = zzmg.zzc(t, j);
                    i2 = i + zza2;
                    break;
                case 13:
                    i = i2 * 53;
                    zza2 = zzmg.zzc(t, j);
                    i2 = i + zza2;
                    break;
                case 14:
                    i = i2 * 53;
                    zza2 = zziz.zza(zzmg.zzd(t, j));
                    i2 = i + zza2;
                    break;
                case 15:
                    i = i2 * 53;
                    zza2 = zzmg.zzc(t, j);
                    i2 = i + zza2;
                    break;
                case 16:
                    i = i2 * 53;
                    zza2 = zziz.zza(zzmg.zzd(t, j));
                    i2 = i + zza2;
                    break;
                case 17:
                    Object zze2 = zzmg.zze(t, j);
                    if (zze2 != null) {
                        i5 = zze2.hashCode();
                    }
                    i2 = (i2 * 53) + i5;
                    break;
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                case 47:
                case 48:
                case 49:
                    i = i2 * 53;
                    zza2 = zzmg.zze(t, j).hashCode();
                    i2 = i + zza2;
                    break;
                case 50:
                    i = i2 * 53;
                    zza2 = zzmg.zze(t, j).hashCode();
                    i2 = i + zza2;
                    break;
                case 51:
                    if (zzc((zzkn<T>) t, i4, i3)) {
                        i = i2 * 53;
                        zza2 = zziz.zza(Double.doubleToLongBits(zza(t, j)));
                        i2 = i + zza2;
                        break;
                    } else {
                        break;
                    }
                case 52:
                    if (zzc((zzkn<T>) t, i4, i3)) {
                        i = i2 * 53;
                        zza2 = Float.floatToIntBits(zzb(t, j));
                        i2 = i + zza2;
                        break;
                    } else {
                        break;
                    }
                case 53:
                    if (zzc((zzkn<T>) t, i4, i3)) {
                        i = i2 * 53;
                        zza2 = zziz.zza(zzd(t, j));
                        i2 = i + zza2;
                        break;
                    } else {
                        break;
                    }
                case 54:
                    if (zzc((zzkn<T>) t, i4, i3)) {
                        i = i2 * 53;
                        zza2 = zziz.zza(zzd(t, j));
                        i2 = i + zza2;
                        break;
                    } else {
                        break;
                    }
                case 55:
                    if (zzc((zzkn<T>) t, i4, i3)) {
                        i = i2 * 53;
                        zza2 = zzc(t, j);
                        i2 = i + zza2;
                        break;
                    } else {
                        break;
                    }
                case 56:
                    if (zzc((zzkn<T>) t, i4, i3)) {
                        i = i2 * 53;
                        zza2 = zziz.zza(zzd(t, j));
                        i2 = i + zza2;
                        break;
                    } else {
                        break;
                    }
                case 57:
                    if (zzc((zzkn<T>) t, i4, i3)) {
                        i = i2 * 53;
                        zza2 = zzc(t, j);
                        i2 = i + zza2;
                        break;
                    } else {
                        break;
                    }
                case 58:
                    if (zzc((zzkn<T>) t, i4, i3)) {
                        i = i2 * 53;
                        zza2 = zziz.zza(zze(t, j));
                        i2 = i + zza2;
                        break;
                    } else {
                        break;
                    }
                case 59:
                    if (zzc((zzkn<T>) t, i4, i3)) {
                        i = i2 * 53;
                        zza2 = ((String) zzmg.zze(t, j)).hashCode();
                        i2 = i + zza2;
                        break;
                    } else {
                        break;
                    }
                case 60:
                    if (zzc((zzkn<T>) t, i4, i3)) {
                        i = i2 * 53;
                        zza2 = zzmg.zze(t, j).hashCode();
                        i2 = i + zza2;
                        break;
                    } else {
                        break;
                    }
                case 61:
                    if (zzc((zzkn<T>) t, i4, i3)) {
                        i = i2 * 53;
                        zza2 = zzmg.zze(t, j).hashCode();
                        i2 = i + zza2;
                        break;
                    } else {
                        break;
                    }
                case 62:
                    if (zzc((zzkn<T>) t, i4, i3)) {
                        i = i2 * 53;
                        zza2 = zzc(t, j);
                        i2 = i + zza2;
                        break;
                    } else {
                        break;
                    }
                case 63:
                    if (zzc((zzkn<T>) t, i4, i3)) {
                        i = i2 * 53;
                        zza2 = zzc(t, j);
                        i2 = i + zza2;
                        break;
                    } else {
                        break;
                    }
                case 64:
                    if (zzc((zzkn<T>) t, i4, i3)) {
                        i = i2 * 53;
                        zza2 = zzc(t, j);
                        i2 = i + zza2;
                        break;
                    } else {
                        break;
                    }
                case 65:
                    if (zzc((zzkn<T>) t, i4, i3)) {
                        i = i2 * 53;
                        zza2 = zziz.zza(zzd(t, j));
                        i2 = i + zza2;
                        break;
                    } else {
                        break;
                    }
                case 66:
                    if (zzc((zzkn<T>) t, i4, i3)) {
                        i = i2 * 53;
                        zza2 = zzc(t, j);
                        i2 = i + zza2;
                        break;
                    } else {
                        break;
                    }
                case 67:
                    if (zzc((zzkn<T>) t, i4, i3)) {
                        i = i2 * 53;
                        zza2 = zziz.zza(zzd(t, j));
                        i2 = i + zza2;
                        break;
                    } else {
                        break;
                    }
                case 68:
                    if (zzc((zzkn<T>) t, i4, i3)) {
                        i = i2 * 53;
                        zza2 = zzmg.zze(t, j).hashCode();
                        i2 = i + zza2;
                        break;
                    } else {
                        break;
                    }
            }
        }
        int hashCode = (i2 * 53) + this.zzq.zzd(t).hashCode();
        return this.zzh ? (hashCode * 53) + this.zzr.zza(t).hashCode() : hashCode;
    }

    private static <T> int zzc(T t, long j) {
        return ((Integer) zzmg.zze(t, j)).intValue();
    }

    /* JADX WARN: Code restructure failed: missing block: B:42:0x0d16, code lost:
    
        if (r14 == 1048575) goto L529;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0d18, code lost:
    
        r29.putInt(r7, r14, r13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0d1e, code lost:
    
        r3 = null;
        r9 = r32.zzm;
     */
    /* JADX WARN: Code restructure failed: missing block: B:459:0x09ff, code lost:
    
        throw com.google.android.gms.internal.measurement.zzji.zzh();
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x0d25, code lost:
    
        if (r9 >= r32.zzn) goto L647;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x0d27, code lost:
    
        r3 = (com.google.android.gms.internal.measurement.zzlz) zza((java.lang.Object) r33, r32.zzl[r9], (int) r3, (com.google.android.gms.internal.measurement.zzma<UT, int>) r32.zzq, (java.lang.Object) r33);
        r9 = r9 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x0d3d, code lost:
    
        if (r3 == null) goto L535;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x0d3f, code lost:
    
        r32.zzq.zzb((java.lang.Object) r7, (T) r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x0d44, code lost:
    
        if (r12 != 0) goto L541;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0d48, code lost:
    
        if (r8 != r36) goto L539;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0d4f, code lost:
    
        throw com.google.android.gms.internal.measurement.zzji.zzg();
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x0d56, code lost:
    
        return r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x0d52, code lost:
    
        if (r8 > r36) goto L545;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0d54, code lost:
    
        if (r11 != r12) goto L545;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x0d5b, code lost:
    
        throw com.google.android.gms.internal.measurement.zzji.zzg();
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:469:0x0c9a  */
    /* JADX WARN: Removed duplicated region for block: B:470:0x0cfe  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x08c8 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:95:0x08b7 A[SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r1v74, types: [int] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    final int zza(T r33, byte[] r34, int r35, int r36, int r37, com.google.android.gms.internal.measurement.zzhl r38) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 3568
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzkn.zza(java.lang.Object, byte[], int, int, int, com.google.android.gms.internal.measurement.zzhl):int");
    }

    private final int zza(int i) {
        if (i < this.zze || i > this.zzf) {
            return -1;
        }
        return zza(i, 0);
    }

    private final int zzb(int i) {
        return this.zzc[i + 2];
    }

    private final int zza(int i, int i2) {
        int length = (this.zzc.length / 3) - 1;
        while (i2 <= length) {
            int i3 = (length + i2) >>> 1;
            int i4 = i3 * 3;
            int i5 = this.zzc[i4];
            if (i == i5) {
                return i4;
            }
            if (i < i5) {
                length = i3 - 1;
            } else {
                i2 = i3 + 1;
            }
        }
        return -1;
    }

    private final int zzc(int i) {
        return this.zzc[i + 1];
    }

    private static <T> long zzd(T t, long j) {
        return ((Long) zzmg.zze(t, j)).longValue();
    }

    private final zzje zzd(int i) {
        return (zzje) this.zzd[((i / 3) << 1) + 1];
    }

    /* JADX WARN: Removed duplicated region for block: B:65:0x0258  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0272  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0275  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x025b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    static <T> com.google.android.gms.internal.measurement.zzkn<T> zza(java.lang.Class<T> r32, com.google.android.gms.internal.measurement.zzkh r33, com.google.android.gms.internal.measurement.zzkr r34, com.google.android.gms.internal.measurement.zzjs r35, com.google.android.gms.internal.measurement.zzma<?, ?> r36, com.google.android.gms.internal.measurement.zzim<?> r37, com.google.android.gms.internal.measurement.zzkg r38) {
        /*
            Method dump skipped, instructions count: 1021
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzkn.zza(java.lang.Class, com.google.android.gms.internal.measurement.zzkh, com.google.android.gms.internal.measurement.zzkr, com.google.android.gms.internal.measurement.zzjs, com.google.android.gms.internal.measurement.zzma, com.google.android.gms.internal.measurement.zzim, com.google.android.gms.internal.measurement.zzkg):com.google.android.gms.internal.measurement.zzkn");
    }

    private final zzlb zze(int i) {
        int i2 = (i / 3) << 1;
        zzlb zzlbVar = (zzlb) this.zzd[i2];
        if (zzlbVar != null) {
            return zzlbVar;
        }
        zzlb<T> zza2 = zzkx.zza().zza((Class) this.zzd[i2 + 1]);
        this.zzd[i2] = zza2;
        return zza2;
    }

    private static zzlz zze(Object obj) {
        zzix zzixVar = (zzix) obj;
        zzlz zzlzVar = zzixVar.zzb;
        if (zzlzVar != zzlz.zzc()) {
            return zzlzVar;
        }
        zzlz zzd = zzlz.zzd();
        zzixVar.zzb = zzd;
        return zzd;
    }

    private final <UT, UB> UB zza(Object obj, int i, UB ub, zzma<UT, UB> zzmaVar, Object obj2) {
        zzje zzd;
        int i2 = this.zzc[i];
        Object zze = zzmg.zze(obj, zzc(i) & 1048575);
        return (zze == null || (zzd = zzd(i)) == null) ? ub : (UB) zza(i, i2, this.zzs.zze(zze), zzd, (zzje) ub, (zzma<UT, zzje>) zzmaVar, obj2);
    }

    private final <K, V, UT, UB> UB zza(int i, int i2, Map<K, V> map, zzje zzjeVar, UB ub, zzma<UT, UB> zzmaVar, Object obj) {
        zzke<?, ?> zza2 = this.zzs.zza(zzf(i));
        Iterator<Map.Entry<K, V>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<K, V> next = it.next();
            if (!zzjeVar.zza(((Integer) next.getValue()).intValue())) {
                if (ub == null) {
                    ub = zzmaVar.zzc(obj);
                }
                zzhv zzc = zzhm.zzc(zzkb.zza(zza2, next.getKey(), next.getValue()));
                try {
                    zzkb.zza(zzc.zzb(), zza2, next.getKey(), next.getValue());
                    zzmaVar.zza((zzma<UT, UB>) ub, i2, zzc.zza());
                    it.remove();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return ub;
    }

    private final Object zzf(int i) {
        return this.zzd[(i / 3) << 1];
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final Object zza(T t, int i) {
        zzlb zze = zze(i);
        long zzc = zzc(i) & 1048575;
        if (!zzc((zzkn<T>) t, i)) {
            return zze.zza();
        }
        Object object = zzb.getObject(t, zzc);
        if (zzg(object)) {
            return object;
        }
        Object zza2 = zze.zza();
        if (object != null) {
            zze.zza(zza2, object);
        }
        return zza2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final Object zza(T t, int i, int i2) {
        zzlb zze = zze(i2);
        if (!zzc((zzkn<T>) t, i, i2)) {
            return zze.zza();
        }
        Object object = zzb.getObject(t, zzc(i2) & 1048575);
        if (zzg(object)) {
            return object;
        }
        Object zza2 = zze.zza();
        if (object != null) {
            zze.zza(zza2, object);
        }
        return zza2;
    }

    @Override // com.google.android.gms.internal.measurement.zzlb
    public final T zza() {
        return (T) this.zzo.zza(this.zzg);
    }

    private static Field zza(Class<?> cls, String str) {
        try {
            return cls.getDeclaredField(str);
        } catch (NoSuchFieldException unused) {
            Field[] declaredFields = cls.getDeclaredFields();
            for (Field field : declaredFields) {
                if (str.equals(field.getName())) {
                    return field;
                }
            }
            throw new RuntimeException("Field " + str + " for " + cls.getName() + " not found. Known fields are " + Arrays.toString(declaredFields));
        }
    }

    private zzkn(int[] iArr, Object[] objArr, int i, int i2, zzkj zzkjVar, zzky zzkyVar, boolean z, int[] iArr2, int i3, int i4, zzkr zzkrVar, zzjs zzjsVar, zzma<?, ?> zzmaVar, zzim<?> zzimVar, zzkg zzkgVar) {
        this.zzc = iArr;
        this.zzd = objArr;
        this.zze = i;
        this.zzf = i2;
        this.zzi = zzkjVar instanceof zzix;
        this.zzj = zzkyVar;
        this.zzh = zzimVar != null && zzimVar.zza(zzkjVar);
        this.zzk = false;
        this.zzl = iArr2;
        this.zzm = i3;
        this.zzn = i4;
        this.zzo = zzkrVar;
        this.zzp = zzjsVar;
        this.zzq = zzmaVar;
        this.zzr = zzimVar;
        this.zzg = zzkjVar;
        this.zzs = zzkgVar;
    }

    private static void zzf(Object obj) {
        if (zzg(obj)) {
            return;
        }
        throw new IllegalArgumentException("Mutating immutable message: " + String.valueOf(obj));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.internal.measurement.zzlb
    public final void zzc(T t) {
        if (zzg(t)) {
            if (t instanceof zzix) {
                zzix zzixVar = (zzix) t;
                zzixVar.zzc(Integer.MAX_VALUE);
                zzixVar.zza = 0;
                zzixVar.zzch();
            }
            int length = this.zzc.length;
            for (int i = 0; i < length; i += 3) {
                int zzc = zzc(i);
                long j = 1048575 & zzc;
                int i2 = (zzc & 267386880) >>> 20;
                if (i2 != 9) {
                    if (i2 == 60 || i2 == 68) {
                        if (zzc((zzkn<T>) t, this.zzc[i], i)) {
                            zze(i).zzc(zzb.getObject(t, j));
                        }
                    } else {
                        switch (i2) {
                            case 18:
                            case 19:
                            case 20:
                            case 21:
                            case 22:
                            case 23:
                            case 24:
                            case 25:
                            case 26:
                            case 27:
                            case 28:
                            case 29:
                            case 30:
                            case 31:
                            case 32:
                            case 33:
                            case 34:
                            case 35:
                            case 36:
                            case 37:
                            case 38:
                            case 39:
                            case 40:
                            case 41:
                            case 42:
                            case 43:
                            case 44:
                            case 45:
                            case 46:
                            case 47:
                            case 48:
                            case 49:
                                this.zzp.zzb(t, j);
                                break;
                            case 50:
                                Unsafe unsafe = zzb;
                                Object object = unsafe.getObject(t, j);
                                if (object != null) {
                                    unsafe.putObject(t, j, this.zzs.zzc(object));
                                    break;
                                } else {
                                    break;
                                }
                        }
                    }
                }
                if (zzc((zzkn<T>) t, i)) {
                    zze(i).zzc(zzb.getObject(t, j));
                }
            }
            this.zzq.zzf(t);
            if (this.zzh) {
                this.zzr.zzc(t);
            }
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzlb
    public final void zza(T t, T t2) {
        zzf(t);
        t2.getClass();
        for (int i = 0; i < this.zzc.length; i += 3) {
            int zzc = zzc(i);
            long j = 1048575 & zzc;
            int i2 = this.zzc[i];
            switch ((zzc & 267386880) >>> 20) {
                case 0:
                    if (zzc((zzkn<T>) t2, i)) {
                        zzmg.zza(t, j, zzmg.zza(t2, j));
                        zzb((zzkn<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 1:
                    if (zzc((zzkn<T>) t2, i)) {
                        zzmg.zza((Object) t, j, zzmg.zzb(t2, j));
                        zzb((zzkn<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 2:
                    if (zzc((zzkn<T>) t2, i)) {
                        zzmg.zza((Object) t, j, zzmg.zzd(t2, j));
                        zzb((zzkn<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 3:
                    if (zzc((zzkn<T>) t2, i)) {
                        zzmg.zza((Object) t, j, zzmg.zzd(t2, j));
                        zzb((zzkn<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 4:
                    if (zzc((zzkn<T>) t2, i)) {
                        zzmg.zza((Object) t, j, zzmg.zzc(t2, j));
                        zzb((zzkn<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 5:
                    if (zzc((zzkn<T>) t2, i)) {
                        zzmg.zza((Object) t, j, zzmg.zzd(t2, j));
                        zzb((zzkn<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 6:
                    if (zzc((zzkn<T>) t2, i)) {
                        zzmg.zza((Object) t, j, zzmg.zzc(t2, j));
                        zzb((zzkn<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 7:
                    if (zzc((zzkn<T>) t2, i)) {
                        zzmg.zzc(t, j, zzmg.zzh(t2, j));
                        zzb((zzkn<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 8:
                    if (zzc((zzkn<T>) t2, i)) {
                        zzmg.zza(t, j, zzmg.zze(t2, j));
                        zzb((zzkn<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 9:
                    zza(t, t2, i);
                    break;
                case 10:
                    if (zzc((zzkn<T>) t2, i)) {
                        zzmg.zza(t, j, zzmg.zze(t2, j));
                        zzb((zzkn<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 11:
                    if (zzc((zzkn<T>) t2, i)) {
                        zzmg.zza((Object) t, j, zzmg.zzc(t2, j));
                        zzb((zzkn<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 12:
                    if (zzc((zzkn<T>) t2, i)) {
                        zzmg.zza((Object) t, j, zzmg.zzc(t2, j));
                        zzb((zzkn<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 13:
                    if (zzc((zzkn<T>) t2, i)) {
                        zzmg.zza((Object) t, j, zzmg.zzc(t2, j));
                        zzb((zzkn<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 14:
                    if (zzc((zzkn<T>) t2, i)) {
                        zzmg.zza((Object) t, j, zzmg.zzd(t2, j));
                        zzb((zzkn<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 15:
                    if (zzc((zzkn<T>) t2, i)) {
                        zzmg.zza((Object) t, j, zzmg.zzc(t2, j));
                        zzb((zzkn<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 16:
                    if (zzc((zzkn<T>) t2, i)) {
                        zzmg.zza((Object) t, j, zzmg.zzd(t2, j));
                        zzb((zzkn<T>) t, i);
                        break;
                    } else {
                        break;
                    }
                case 17:
                    zza(t, t2, i);
                    break;
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                case 47:
                case 48:
                case 49:
                    this.zzp.zza(t, t2, j);
                    break;
                case 50:
                    zzld.zza(this.zzs, t, t2, j);
                    break;
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57:
                case 58:
                case 59:
                    if (zzc((zzkn<T>) t2, i2, i)) {
                        zzmg.zza(t, j, zzmg.zze(t2, j));
                        zzb((zzkn<T>) t, i2, i);
                        break;
                    } else {
                        break;
                    }
                case 60:
                    zzb(t, t2, i);
                    break;
                case 61:
                case 62:
                case 63:
                case 64:
                case 65:
                case 66:
                case 67:
                    if (zzc((zzkn<T>) t2, i2, i)) {
                        zzmg.zza(t, j, zzmg.zze(t2, j));
                        zzb((zzkn<T>) t, i2, i);
                        break;
                    } else {
                        break;
                    }
                case 68:
                    zzb(t, t2, i);
                    break;
            }
        }
        zzld.zza(this.zzq, t, t2);
        if (this.zzh) {
            zzld.zza(this.zzr, t, t2);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0657 A[LOOP:2: B:36:0x0653->B:38:0x0657, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:41:0x066b  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0620 A[Catch: all -> 0x0295, TryCatch #1 {all -> 0x0295, blocks: (B:15:0x05f4, B:44:0x061b, B:46:0x0620, B:47:0x0625, B:160:0x00c9, B:66:0x00db, B:69:0x00ed, B:70:0x00ff, B:71:0x0110, B:72:0x0121, B:74:0x012b, B:77:0x0132, B:78:0x0139, B:79:0x0146, B:80:0x0157, B:81:0x0164, B:82:0x0175, B:83:0x0180, B:84:0x0191, B:85:0x01a2, B:86:0x01b3, B:87:0x01c4, B:88:0x01d5, B:89:0x01e6, B:90:0x01f7, B:91:0x0209, B:93:0x0219, B:94:0x023a, B:95:0x0223, B:97:0x022b, B:98:0x024b, B:99:0x025d, B:100:0x026b, B:101:0x0279, B:102:0x0287), top: B:14:0x05f4 }] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x062b A[SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r19v0, types: [com.google.android.gms.internal.measurement.zzlc] */
    @Override // com.google.android.gms.internal.measurement.zzlb
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void zza(T r18, com.google.android.gms.internal.measurement.zzlc r19, com.google.android.gms.internal.measurement.zzik r20) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 1790
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzkn.zza(java.lang.Object, com.google.android.gms.internal.measurement.zzlc, com.google.android.gms.internal.measurement.zzik):void");
    }

    @Override // com.google.android.gms.internal.measurement.zzlb
    public final void zza(T t, byte[] bArr, int i, int i2, zzhl zzhlVar) throws IOException {
        zza((zzkn<T>) t, bArr, i, i2, 0, zzhlVar);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void zza(T t, T t2, int i) {
        if (zzc((zzkn<T>) t2, i)) {
            long zzc = zzc(i) & 1048575;
            Unsafe unsafe = zzb;
            Object object = unsafe.getObject(t2, zzc);
            if (object == null) {
                throw new IllegalStateException("Source subfield " + this.zzc[i] + " is present but null: " + String.valueOf(t2));
            }
            zzlb zze = zze(i);
            if (!zzc((zzkn<T>) t, i)) {
                if (!zzg(object)) {
                    unsafe.putObject(t, zzc, object);
                } else {
                    Object zza2 = zze.zza();
                    zze.zza(zza2, object);
                    unsafe.putObject(t, zzc, zza2);
                }
                zzb((zzkn<T>) t, i);
                return;
            }
            Object object2 = unsafe.getObject(t, zzc);
            if (!zzg(object2)) {
                Object zza3 = zze.zza();
                zze.zza(zza3, object2);
                unsafe.putObject(t, zzc, zza3);
                object2 = zza3;
            }
            zze.zza(object2, object);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void zzb(T t, T t2, int i) {
        int i2 = this.zzc[i];
        if (zzc((zzkn<T>) t2, i2, i)) {
            long zzc = zzc(i) & 1048575;
            Unsafe unsafe = zzb;
            Object object = unsafe.getObject(t2, zzc);
            if (object == null) {
                throw new IllegalStateException("Source subfield " + this.zzc[i] + " is present but null: " + String.valueOf(t2));
            }
            zzlb zze = zze(i);
            if (!zzc((zzkn<T>) t, i2, i)) {
                if (!zzg(object)) {
                    unsafe.putObject(t, zzc, object);
                } else {
                    Object zza2 = zze.zza();
                    zze.zza(zza2, object);
                    unsafe.putObject(t, zzc, zza2);
                }
                zzb((zzkn<T>) t, i2, i);
                return;
            }
            Object object2 = unsafe.getObject(t, zzc);
            if (!zzg(object2)) {
                Object zza3 = zze.zza();
                zze.zza(zza3, object2);
                unsafe.putObject(t, zzc, zza3);
                object2 = zza3;
            }
            zze.zza(object2, object);
        }
    }

    private final void zza(Object obj, int i, zzlc zzlcVar) throws IOException {
        if (zzg(i)) {
            zzmg.zza(obj, i & 1048575, zzlcVar.zzr());
        } else if (this.zzi) {
            zzmg.zza(obj, i & 1048575, zzlcVar.zzq());
        } else {
            zzmg.zza(obj, i & 1048575, zzlcVar.zzp());
        }
    }

    private final void zzb(T t, int i) {
        int zzb2 = zzb(i);
        long j = 1048575 & zzb2;
        if (j == 1048575) {
            return;
        }
        zzmg.zza((Object) t, j, (1 << (zzb2 >>> 20)) | zzmg.zzc(t, j));
    }

    private final void zzb(T t, int i, int i2) {
        zzmg.zza((Object) t, zzb(i2) & 1048575, i);
    }

    private final void zza(T t, int i, Object obj) {
        zzb.putObject(t, zzc(i) & 1048575, obj);
        zzb((zzkn<T>) t, i);
    }

    private final void zza(T t, int i, int i2, Object obj) {
        zzb.putObject(t, zzc(i2) & 1048575, obj);
        zzb((zzkn<T>) t, i, i2);
    }

    private final <K, V> void zza(zzmw zzmwVar, int i, Object obj, int i2) throws IOException {
        if (obj != null) {
            zzmwVar.zza(i, this.zzs.zza(zzf(i2)), this.zzs.zzd(obj));
        }
    }

    private static void zza(int i, Object obj, zzmw zzmwVar) throws IOException {
        if (obj instanceof String) {
            zzmwVar.zza(i, (String) obj);
        } else {
            zzmwVar.zza(i, (zzhm) obj);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0040  */
    /* JADX WARN: Removed duplicated region for block: B:277:0x0518  */
    /* JADX WARN: Removed duplicated region for block: B:297:0x0559  */
    /* JADX WARN: Removed duplicated region for block: B:509:0x0b90  */
    @Override // com.google.android.gms.internal.measurement.zzlb
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void zza(T r24, com.google.android.gms.internal.measurement.zzmw r25) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 3272
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzkn.zza(java.lang.Object, com.google.android.gms.internal.measurement.zzmw):void");
    }

    private static <UT, UB> void zza(zzma<UT, UB> zzmaVar, T t, zzmw zzmwVar) throws IOException {
        zzmaVar.zzb((zzma<UT, UB>) zzmaVar.zzd(t), zzmwVar);
    }

    private final boolean zzc(T t, T t2, int i) {
        return zzc((zzkn<T>) t, i) == zzc((zzkn<T>) t2, i);
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x006a, code lost:
    
        if (com.google.android.gms.internal.measurement.zzld.zza(com.google.android.gms.internal.measurement.zzmg.zze(r10, r6), com.google.android.gms.internal.measurement.zzmg.zze(r11, r6)) != false) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x007e, code lost:
    
        if (com.google.android.gms.internal.measurement.zzmg.zzd(r10, r6) == com.google.android.gms.internal.measurement.zzmg.zzd(r11, r6)) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0090, code lost:
    
        if (com.google.android.gms.internal.measurement.zzmg.zzc(r10, r6) == com.google.android.gms.internal.measurement.zzmg.zzc(r11, r6)) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x00a4, code lost:
    
        if (com.google.android.gms.internal.measurement.zzmg.zzd(r10, r6) == com.google.android.gms.internal.measurement.zzmg.zzd(r11, r6)) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x00b6, code lost:
    
        if (com.google.android.gms.internal.measurement.zzmg.zzc(r10, r6) == com.google.android.gms.internal.measurement.zzmg.zzc(r11, r6)) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x00c8, code lost:
    
        if (com.google.android.gms.internal.measurement.zzmg.zzc(r10, r6) == com.google.android.gms.internal.measurement.zzmg.zzc(r11, r6)) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00da, code lost:
    
        if (com.google.android.gms.internal.measurement.zzmg.zzc(r10, r6) == com.google.android.gms.internal.measurement.zzmg.zzc(r11, r6)) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00f0, code lost:
    
        if (com.google.android.gms.internal.measurement.zzld.zza(com.google.android.gms.internal.measurement.zzmg.zze(r10, r6), com.google.android.gms.internal.measurement.zzmg.zze(r11, r6)) != false) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0106, code lost:
    
        if (com.google.android.gms.internal.measurement.zzld.zza(com.google.android.gms.internal.measurement.zzmg.zze(r10, r6), com.google.android.gms.internal.measurement.zzmg.zze(r11, r6)) != false) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x011c, code lost:
    
        if (com.google.android.gms.internal.measurement.zzld.zza(com.google.android.gms.internal.measurement.zzmg.zze(r10, r6), com.google.android.gms.internal.measurement.zzmg.zze(r11, r6)) != false) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x012e, code lost:
    
        if (com.google.android.gms.internal.measurement.zzmg.zzh(r10, r6) == com.google.android.gms.internal.measurement.zzmg.zzh(r11, r6)) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0140, code lost:
    
        if (com.google.android.gms.internal.measurement.zzmg.zzc(r10, r6) == com.google.android.gms.internal.measurement.zzmg.zzc(r11, r6)) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x0154, code lost:
    
        if (com.google.android.gms.internal.measurement.zzmg.zzd(r10, r6) == com.google.android.gms.internal.measurement.zzmg.zzd(r11, r6)) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x0165, code lost:
    
        if (com.google.android.gms.internal.measurement.zzmg.zzc(r10, r6) == com.google.android.gms.internal.measurement.zzmg.zzc(r11, r6)) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x0178, code lost:
    
        if (com.google.android.gms.internal.measurement.zzmg.zzd(r10, r6) == com.google.android.gms.internal.measurement.zzmg.zzd(r11, r6)) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x018b, code lost:
    
        if (com.google.android.gms.internal.measurement.zzmg.zzd(r10, r6) == com.google.android.gms.internal.measurement.zzmg.zzd(r11, r6)) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x01a4, code lost:
    
        if (java.lang.Float.floatToIntBits(com.google.android.gms.internal.measurement.zzmg.zzb(r10, r6)) == java.lang.Float.floatToIntBits(com.google.android.gms.internal.measurement.zzmg.zzb(r11, r6))) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x01bf, code lost:
    
        if (java.lang.Double.doubleToLongBits(com.google.android.gms.internal.measurement.zzmg.zza(r10, r6)) == java.lang.Double.doubleToLongBits(com.google.android.gms.internal.measurement.zzmg.zza(r11, r6))) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0038, code lost:
    
        if (com.google.android.gms.internal.measurement.zzld.zza(com.google.android.gms.internal.measurement.zzmg.zze(r10, r6), com.google.android.gms.internal.measurement.zzmg.zze(r11, r6)) != false) goto L105;
     */
    @Override // com.google.android.gms.internal.measurement.zzlb
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean zzb(T r10, T r11) {
        /*
            Method dump skipped, instructions count: 640
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzkn.zzb(java.lang.Object, java.lang.Object):boolean");
    }

    private final boolean zzc(T t, int i) {
        int zzb2 = zzb(i);
        long j = zzb2 & 1048575;
        if (j != 1048575) {
            return (zzmg.zzc(t, j) & (1 << (zzb2 >>> 20))) != 0;
        }
        int zzc = zzc(i);
        long j2 = zzc & 1048575;
        switch ((zzc & 267386880) >>> 20) {
            case 0:
                return Double.doubleToRawLongBits(zzmg.zza(t, j2)) != 0;
            case 1:
                return Float.floatToRawIntBits(zzmg.zzb(t, j2)) != 0;
            case 2:
                return zzmg.zzd(t, j2) != 0;
            case 3:
                return zzmg.zzd(t, j2) != 0;
            case 4:
                return zzmg.zzc(t, j2) != 0;
            case 5:
                return zzmg.zzd(t, j2) != 0;
            case 6:
                return zzmg.zzc(t, j2) != 0;
            case 7:
                return zzmg.zzh(t, j2);
            case 8:
                Object zze = zzmg.zze(t, j2);
                if (zze instanceof String) {
                    return !((String) zze).isEmpty();
                }
                if (zze instanceof zzhm) {
                    return !zzhm.zza.equals(zze);
                }
                throw new IllegalArgumentException();
            case 9:
                return zzmg.zze(t, j2) != null;
            case 10:
                return !zzhm.zza.equals(zzmg.zze(t, j2));
            case 11:
                return zzmg.zzc(t, j2) != 0;
            case 12:
                return zzmg.zzc(t, j2) != 0;
            case 13:
                return zzmg.zzc(t, j2) != 0;
            case 14:
                return zzmg.zzd(t, j2) != 0;
            case 15:
                return zzmg.zzc(t, j2) != 0;
            case 16:
                return zzmg.zzd(t, j2) != 0;
            case 17:
                return zzmg.zze(t, j2) != null;
            default:
                throw new IllegalArgumentException();
        }
    }

    private final boolean zza(T t, int i, int i2, int i3, int i4) {
        if (i2 == 1048575) {
            return zzc((zzkn<T>) t, i);
        }
        return (i3 & i4) != 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v21 */
    /* JADX WARN: Type inference failed for: r1v22 */
    /* JADX WARN: Type inference failed for: r1v23, types: [com.google.android.gms.internal.measurement.zzlb] */
    /* JADX WARN: Type inference failed for: r1v30 */
    /* JADX WARN: Type inference failed for: r1v31 */
    /* JADX WARN: Type inference failed for: r1v8, types: [com.google.android.gms.internal.measurement.zzlb] */
    @Override // com.google.android.gms.internal.measurement.zzlb
    public final boolean zzd(T t) {
        int i;
        int i2;
        int i3 = 1048575;
        int i4 = 0;
        int i5 = 0;
        while (true) {
            boolean z = true;
            if (i5 >= this.zzm) {
                return !this.zzh || this.zzr.zza(t).zzg();
            }
            int i6 = this.zzl[i5];
            int i7 = this.zzc[i6];
            int zzc = zzc(i6);
            int i8 = this.zzc[i6 + 2];
            int i9 = i8 & 1048575;
            int i10 = 1 << (i8 >>> 20);
            if (i9 != i3) {
                if (i9 != 1048575) {
                    i4 = zzb.getInt(t, i9);
                }
                i2 = i4;
                i = i9;
            } else {
                i = i3;
                i2 = i4;
            }
            if (((268435456 & zzc) != 0) && !zza((zzkn<T>) t, i6, i, i2, i10)) {
                return false;
            }
            int i11 = (267386880 & zzc) >>> 20;
            if (i11 == 9 || i11 == 17) {
                if (zza((zzkn<T>) t, i6, i, i2, i10) && !zza((Object) t, zzc, zze(i6))) {
                    return false;
                }
            } else {
                if (i11 != 27) {
                    if (i11 == 60 || i11 == 68) {
                        if (zzc((zzkn<T>) t, i7, i6) && !zza((Object) t, zzc, zze(i6))) {
                            return false;
                        }
                    } else if (i11 != 49) {
                        if (i11 != 50) {
                            continue;
                        } else {
                            Map<?, ?> zzd = this.zzs.zzd(zzmg.zze(t, zzc & 1048575));
                            if (!zzd.isEmpty()) {
                                if (this.zzs.zza(zzf(i6)).zzc.zzb() == zzmx.MESSAGE) {
                                    ?? r1 = 0;
                                    Iterator<?> it = zzd.values().iterator();
                                    while (true) {
                                        if (!it.hasNext()) {
                                            break;
                                        }
                                        Object next = it.next();
                                        r1 = r1;
                                        if (r1 == 0) {
                                            r1 = zzkx.zza().zza((Class) next.getClass());
                                        }
                                        if (!r1.zzd(next)) {
                                            z = false;
                                            break;
                                        }
                                    }
                                }
                            }
                            if (!z) {
                                return false;
                            }
                        }
                    }
                }
                List list = (List) zzmg.zze(t, zzc & 1048575);
                if (!list.isEmpty()) {
                    ?? zze = zze(i6);
                    int i12 = 0;
                    while (true) {
                        if (i12 >= list.size()) {
                            break;
                        }
                        if (!zze.zzd(list.get(i12))) {
                            z = false;
                            break;
                        }
                        i12++;
                    }
                }
                if (!z) {
                    return false;
                }
            }
            i5++;
            i3 = i;
            i4 = i2;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static boolean zza(Object obj, int i, zzlb zzlbVar) {
        return zzlbVar.zzd(zzmg.zze(obj, i & 1048575));
    }

    private static boolean zzg(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof zzix) {
            return ((zzix) obj).zzcj();
        }
        return true;
    }

    private final boolean zzc(T t, int i, int i2) {
        return zzmg.zzc(t, (long) (zzb(i2) & 1048575)) == i;
    }

    private static <T> boolean zze(T t, long j) {
        return ((Boolean) zzmg.zze(t, j)).booleanValue();
    }
}

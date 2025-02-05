package com.google.android.gms.internal.measurement;

import java.util.ArrayList;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-measurement@@21.5.0 */
/* loaded from: classes2.dex */
public final class zzbc extends zzay {
    @Override // com.google.android.gms.internal.measurement.zzay
    public final zzaq zza(String str, zzh zzhVar, List<zzaq> list) {
        int i = 0;
        switch (zzbf.zza[zzg.zza(str).ordinal()]) {
            case 1:
                zzg.zza(zzbv.APPLY, 3, list);
                zzaq zza = zzhVar.zza(list.get(0));
                String zzf = zzhVar.zza(list.get(1)).zzf();
                zzaq zza2 = zzhVar.zza(list.get(2));
                if (!(zza2 instanceof zzaf)) {
                    throw new IllegalArgumentException(String.format("Function arguments for Apply are not a list found %s", zza2.getClass().getCanonicalName()));
                }
                if (zzf.isEmpty()) {
                    throw new IllegalArgumentException("Function name for apply is undefined");
                }
                return zza.zza(zzf, zzhVar, ((zzaf) zza2).zzi());
            case 2:
                return zzhVar.zza().zza(new zzaf(list));
            case 3:
                zzg.zza(zzbv.BREAK, 0, list);
                return zzaq.zzf;
            case 4:
            case 5:
                if (!list.isEmpty()) {
                    zzaq zza3 = zzhVar.zza(list.get(0));
                    if (zza3 instanceof zzaf) {
                        return zzhVar.zza((zzaf) zza3);
                    }
                }
                return zzaq.zzc;
            case 6:
                zzg.zza(zzbv.BREAK, 0, list);
                return zzaq.zze;
            case 7:
                zzg.zzb(zzbv.DEFINE_FUNCTION, 2, list);
                zzar zzarVar = (zzar) zza(zzhVar, list);
                if (zzarVar.zza() == null) {
                    zzhVar.zzc("", zzarVar);
                } else {
                    zzhVar.zzc(zzarVar.zza(), zzarVar);
                }
                return zzarVar;
            case 8:
                return zza(zzhVar, list);
            case 9:
                zzg.zzb(zzbv.IF, 2, list);
                zzaq zza4 = zzhVar.zza(list.get(0));
                zzaq zza5 = zzhVar.zza(list.get(1));
                zzaq zza6 = list.size() > 2 ? zzhVar.zza(list.get(2)) : null;
                zzaq zzaqVar = zzaq.zzc;
                if (zza4.zzd().booleanValue()) {
                    zzaqVar = zzhVar.zza((zzaf) zza5);
                } else if (zza6 != null) {
                    zzaqVar = zzhVar.zza((zzaf) zza6);
                }
                return zzaqVar instanceof zzaj ? zzaqVar : zzaq.zzc;
            case 10:
                return new zzaf(list);
            case 11:
                if (list.isEmpty()) {
                    return zzaq.zzg;
                }
                zzg.zza(zzbv.RETURN, 1, list);
                return new zzaj("return", zzhVar.zza(list.get(0)));
            case 12:
                zzg.zza(zzbv.SWITCH, 3, list);
                zzaq zza7 = zzhVar.zza(list.get(0));
                zzaq zza8 = zzhVar.zza(list.get(1));
                zzaq zza9 = zzhVar.zza(list.get(2));
                if (!(zza8 instanceof zzaf)) {
                    throw new IllegalArgumentException("Malformed SWITCH statement, cases are not a list");
                }
                if (!(zza9 instanceof zzaf)) {
                    throw new IllegalArgumentException("Malformed SWITCH statement, case statements are not a list");
                }
                zzaf zzafVar = (zzaf) zza8;
                zzaf zzafVar2 = (zzaf) zza9;
                boolean z = false;
                while (true) {
                    if (i < zzafVar.zzb()) {
                        if (z || zza7.equals(zzhVar.zza(zzafVar.zza(i)))) {
                            zzaq zza10 = zzhVar.zza(zzafVar2.zza(i));
                            if (!(zza10 instanceof zzaj)) {
                                z = true;
                            } else if (!((zzaj) zza10).zzb().equals("break")) {
                                return zza10;
                            }
                        }
                        i++;
                    } else if (zzafVar.zzb() + 1 == zzafVar2.zzb()) {
                        zzaq zza11 = zzhVar.zza(zzafVar2.zza(zzafVar.zzb()));
                        if (zza11 instanceof zzaj) {
                            String zzb = ((zzaj) zza11).zzb();
                            if (zzb.equals("return") || zzb.equals("continue")) {
                                return zza11;
                            }
                        }
                    }
                }
                return zzaq.zzc;
            case 13:
                zzg.zza(zzbv.TERNARY, 3, list);
                return zzhVar.zza(list.get(0)).zzd().booleanValue() ? zzhVar.zza(list.get(1)) : zzhVar.zza(list.get(2));
            default:
                return super.zza(str);
        }
    }

    private static zzaq zza(zzh zzhVar, List<zzaq> list) {
        zzg.zzb(zzbv.FN, 2, list);
        zzaq zza = zzhVar.zza(list.get(0));
        zzaq zza2 = zzhVar.zza(list.get(1));
        if (!(zza2 instanceof zzaf)) {
            throw new IllegalArgumentException(String.format("FN requires an ArrayValue of parameter names found %s", zza2.getClass().getCanonicalName()));
        }
        List<zzaq> zzi = ((zzaf) zza2).zzi();
        List<zzaq> arrayList = new ArrayList<>();
        if (list.size() > 2) {
            arrayList = list.subList(2, list.size());
        }
        return new zzar(zza.zzf(), zzi, arrayList, zzhVar);
    }

    protected zzbc() {
        this.zza.add(zzbv.APPLY);
        this.zza.add(zzbv.BLOCK);
        this.zza.add(zzbv.BREAK);
        this.zza.add(zzbv.CASE);
        this.zza.add(zzbv.DEFAULT);
        this.zza.add(zzbv.CONTINUE);
        this.zza.add(zzbv.DEFINE_FUNCTION);
        this.zza.add(zzbv.FN);
        this.zza.add(zzbv.IF);
        this.zza.add(zzbv.QUOTE);
        this.zza.add(zzbv.RETURN);
        this.zza.add(zzbv.SWITCH);
        this.zza.add(zzbv.TERNARY);
    }
}

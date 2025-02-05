package com.google.android.gms.measurement.internal;

import android.content.Context;
import androidx.recyclerview.widget.ItemTouchHelper;
import com.google.android.gms.internal.measurement.zznj;
import com.google.android.gms.internal.measurement.zznk;
import com.google.android.gms.internal.measurement.zznp;
import com.google.android.gms.internal.measurement.zznq;
import com.google.android.gms.internal.measurement.zznv;
import com.google.android.gms.internal.measurement.zznw;
import com.google.android.gms.internal.measurement.zzob;
import com.google.android.gms.internal.measurement.zzoc;
import com.google.android.gms.internal.measurement.zzoh;
import com.google.android.gms.internal.measurement.zzoi;
import com.google.android.gms.internal.measurement.zzon;
import com.google.android.gms.internal.measurement.zzoo;
import com.google.android.gms.internal.measurement.zzot;
import com.google.android.gms.internal.measurement.zzou;
import com.google.android.gms.internal.measurement.zzoz;
import com.google.android.gms.internal.measurement.zzpa;
import com.google.android.gms.internal.measurement.zzpf;
import com.google.android.gms.internal.measurement.zzpg;
import com.google.android.gms.internal.measurement.zzpl;
import com.google.android.gms.internal.measurement.zzpm;
import com.google.android.gms.internal.measurement.zzpr;
import com.google.android.gms.internal.measurement.zzps;
import com.google.android.gms.internal.measurement.zzpx;
import com.google.android.gms.internal.measurement.zzpy;
import com.google.android.gms.internal.measurement.zzqd;
import com.google.android.gms.internal.measurement.zzqe;
import com.google.android.gms.internal.measurement.zzqj;
import com.google.android.gms.internal.measurement.zzqk;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.time.DateUtils;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.5.0 */
/* loaded from: classes2.dex */
public final class zzbi {
    public static final zzfi<Long> zzaa;
    public static final zzfi<Long> zzab;
    public static final zzfi<Integer> zzac;
    public static final zzfi<Long> zzad;
    public static final zzfi<Integer> zzae;
    public static final zzfi<Integer> zzaf;
    public static final zzfi<Integer> zzag;
    public static final zzfi<Integer> zzah;
    public static final zzfi<Integer> zzai;
    public static final zzfi<Long> zzaj;
    public static final zzfi<Boolean> zzak;
    public static final zzfi<String> zzal;
    public static final zzfi<Long> zzam;
    public static final zzfi<Integer> zzan;
    public static final zzfi<Double> zzao;
    public static final zzfi<Integer> zzap;
    public static final zzfi<Integer> zzaq;
    public static final zzfi<Integer> zzar;
    public static final zzfi<Long> zzas;
    public static final zzfi<Long> zzat;
    public static final zzfi<Integer> zzau;
    public static final zzfi<String> zzav;
    public static final zzfi<String> zzaw;
    public static final zzfi<String> zzax;
    public static final zzfi<String> zzay;
    public static final zzfi<String> zzaz;
    public static final zzfi<Long> zzb;
    public static final zzfi<String> zzba;
    public static final zzfi<String> zzbb;
    public static final zzfi<Boolean> zzbc;
    public static final zzfi<Boolean> zzbd;
    public static final zzfi<Boolean> zzbe;
    public static final zzfi<Boolean> zzbf;
    public static final zzfi<Boolean> zzbg;
    public static final zzfi<Boolean> zzbh;
    public static final zzfi<Boolean> zzbi;
    public static final zzfi<Boolean> zzbj;
    public static final zzfi<Boolean> zzbk;
    public static final zzfi<Boolean> zzbl;
    public static final zzfi<Boolean> zzbm;
    public static final zzfi<Boolean> zzbn;
    public static final zzfi<Integer> zzbo;
    public static final zzfi<Boolean> zzbp;
    public static final zzfi<Boolean> zzbq;
    public static final zzfi<Boolean> zzbr;
    public static final zzfi<Boolean> zzbs;
    public static final zzfi<Boolean> zzbt;
    public static final zzfi<Boolean> zzbu;
    public static final zzfi<Boolean> zzbv;
    public static final zzfi<Boolean> zzbw;
    public static final zzfi<Boolean> zzbx;
    public static final zzfi<Boolean> zzby;
    public static final zzfi<Boolean> zzbz;
    public static final zzfi<Long> zzc;
    public static final zzfi<Boolean> zzca;
    public static final zzfi<Boolean> zzcb;
    public static final zzfi<Boolean> zzcc;
    public static final zzfi<Boolean> zzcd;
    public static final zzfi<Boolean> zzce;
    public static final zzfi<Boolean> zzcf;
    public static final zzfi<Boolean> zzcg;
    public static final zzfi<Boolean> zzch;
    public static final zzfi<Boolean> zzci;
    public static final zzfi<Boolean> zzcj;
    public static final zzfi<Boolean> zzck;
    public static final zzfi<Boolean> zzcl;
    public static final zzfi<Boolean> zzcm;
    public static final zzfi<Boolean> zzcn;
    public static final zzfi<Boolean> zzco;
    public static final zzfi<Boolean> zzcp;
    public static final zzfi<Boolean> zzcq;
    public static final zzfi<Boolean> zzcr;
    public static final zzfi<Boolean> zzcs;
    public static final zzfi<Boolean> zzct;
    public static zzfi<Boolean> zzcu;
    private static final zzfi<Integer> zzcx;
    private static final zzfi<Boolean> zzcy;
    private static final zzfi<Boolean> zzcz;
    public static final zzfi<Long> zzd;
    private static final zzfi<Boolean> zzda;
    private static final zzfi<Boolean> zzdb;
    public static final zzfi<String> zze;
    public static final zzfi<String> zzf;
    public static final zzfi<Integer> zzg;
    public static final zzfi<Integer> zzh;
    public static final zzfi<Integer> zzi;
    public static final zzfi<Integer> zzj;
    public static final zzfi<Integer> zzk;
    public static final zzfi<Integer> zzl;
    public static final zzfi<Integer> zzm;
    public static final zzfi<Integer> zzn;
    public static final zzfi<Integer> zzo;
    public static final zzfi<Integer> zzp;
    public static final zzfi<String> zzq;
    public static final zzfi<Long> zzr;
    public static final zzfi<Long> zzs;
    public static final zzfi<Long> zzt;
    public static final zzfi<Long> zzu;
    public static final zzfi<Long> zzv;
    public static final zzfi<Long> zzw;
    public static final zzfi<Long> zzx;
    public static final zzfi<Long> zzy;
    public static final zzfi<Long> zzz;
    private static final List<zzfi<?>> zzcv = Collections.synchronizedList(new ArrayList());
    private static final Set<zzfi<?>> zzcw = Collections.synchronizedSet(new HashSet());
    public static final zzfi<Long> zza = zza("measurement.ad_id_cache_time", 10000L, 10000L, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzbk
        @Override // com.google.android.gms.measurement.internal.zzfg
        public final Object zza() {
            Long valueOf;
            valueOf = Long.valueOf(com.google.android.gms.internal.measurement.zzne.zza());
            return valueOf;
        }
    });

    private static <V> zzfi<V> zza(String str, V v, V v2, zzfg<V> zzfgVar) {
        zzfi<V> zzfiVar = new zzfi<>(str, v, v2, zzfgVar);
        zzcv.add(zzfiVar);
        return zzfiVar;
    }

    static /* synthetic */ Long zzce() {
        zzae zzaeVar = zzff.zza;
        return Long.valueOf(com.google.android.gms.internal.measurement.zzne.zzd());
    }

    public static Map<String, String> zza(Context context) {
        com.google.android.gms.internal.measurement.zzfy zza2 = com.google.android.gms.internal.measurement.zzfy.zza(context.getContentResolver(), com.google.android.gms.internal.measurement.zzgk.zza("com.google.android.gms.measurement"), new Runnable() { // from class: com.google.android.gms.measurement.internal.zzbh
            @Override // java.lang.Runnable
            public final void run() {
                com.google.android.gms.internal.measurement.zzgn.zzc();
            }
        });
        return zza2 == null ? Collections.emptyMap() : zza2.zza();
    }

    static {
        Long valueOf = Long.valueOf(DateUtils.MILLIS_PER_HOUR);
        zzb = zza("measurement.app_uninstalled_additional_ad_id_cache_time", valueOf, valueOf, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzbo
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Long valueOf2;
                valueOf2 = Long.valueOf(com.google.android.gms.internal.measurement.zzne.zzb());
                return valueOf2;
            }
        });
        Long valueOf2 = Long.valueOf(DateUtils.MILLIS_PER_DAY);
        zzc = zza("measurement.monitoring.sample_period_millis", valueOf2, valueOf2, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzca
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Long valueOf3;
                valueOf3 = Long.valueOf(com.google.android.gms.internal.measurement.zzne.zzo());
                return valueOf3;
            }
        });
        zzd = zza("measurement.config.cache_time", valueOf2, valueOf, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzcm
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                return zzbi.zzce();
            }
        });
        zze = zza("measurement.config.url_scheme", "https", "https", new zzfg() { // from class: com.google.android.gms.measurement.internal.zzcy
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                String zzam2;
                zzam2 = com.google.android.gms.internal.measurement.zzne.zzam();
                return zzam2;
            }
        });
        zzf = zza("measurement.config.url_authority", "app-measurement.com", "app-measurement.com", new zzfg() { // from class: com.google.android.gms.measurement.internal.zzdk
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                String zzal2;
                zzal2 = com.google.android.gms.internal.measurement.zzne.zzal();
                return zzal2;
            }
        });
        zzg = zza("measurement.upload.max_bundles", 100, 100, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzdw
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Integer valueOf3;
                valueOf3 = Integer.valueOf((int) com.google.android.gms.internal.measurement.zzne.zzz());
                return valueOf3;
            }
        });
        zzh = zza("measurement.upload.max_batch_size", 65536, 65536, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzei
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Integer valueOf3;
                valueOf3 = Integer.valueOf((int) com.google.android.gms.internal.measurement.zzne.zzah());
                return valueOf3;
            }
        });
        zzi = zza("measurement.upload.max_bundle_size", 65536, 65536, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzeu
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Integer valueOf3;
                valueOf3 = Integer.valueOf((int) com.google.android.gms.internal.measurement.zzne.zzy());
                return valueOf3;
            }
        });
        zzj = zza("measurement.upload.max_events_per_bundle", 1000, 1000, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzbm
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Integer valueOf3;
                valueOf3 = Integer.valueOf((int) com.google.android.gms.internal.measurement.zzne.zzac());
                return valueOf3;
            }
        });
        zzk = zza("measurement.upload.max_events_per_day", 100000, 100000, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzbw
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Integer valueOf3;
                valueOf3 = Integer.valueOf((int) com.google.android.gms.internal.measurement.zzne.zzad());
                return valueOf3;
            }
        });
        zzl = zza("measurement.upload.max_error_events_per_day", 1000, 1000, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzcf
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Integer valueOf3;
                valueOf3 = Integer.valueOf((int) com.google.android.gms.internal.measurement.zzne.zzab());
                return valueOf3;
            }
        });
        zzm = zza("measurement.upload.max_public_events_per_day", 50000, 50000, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzcs
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Integer valueOf3;
                valueOf3 = Integer.valueOf((int) com.google.android.gms.internal.measurement.zzne.zzae());
                return valueOf3;
            }
        });
        zzn = zza("measurement.upload.max_conversions_per_day", 10000, 10000, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzdb
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Integer valueOf3;
                valueOf3 = Integer.valueOf((int) com.google.android.gms.internal.measurement.zzne.zzaa());
                return valueOf3;
            }
        });
        zzo = zza("measurement.upload.max_realtime_events_per_day", 10, 10, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzdo
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Integer valueOf3;
                valueOf3 = Integer.valueOf((int) com.google.android.gms.internal.measurement.zzne.zzag());
                return valueOf3;
            }
        });
        zzp = zza("measurement.store.max_stored_events_per_app", 100000, 100000, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzdx
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Integer valueOf3;
                valueOf3 = Integer.valueOf((int) com.google.android.gms.internal.measurement.zzne.zzh());
                return valueOf3;
            }
        });
        zzq = zza("measurement.upload.url", "https://app-measurement.com/a", "https://app-measurement.com/a", new zzfg() { // from class: com.google.android.gms.measurement.internal.zzek
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                String zzat2;
                zzat2 = com.google.android.gms.internal.measurement.zzne.zzat();
                return zzat2;
            }
        });
        zzr = zza("measurement.upload.backoff_period", 43200000L, 43200000L, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzet
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Long valueOf3;
                valueOf3 = Long.valueOf(com.google.android.gms.internal.measurement.zzne.zzv());
                return valueOf3;
            }
        });
        zzs = zza("measurement.upload.window_interval", valueOf, valueOf, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzbj
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Long valueOf3;
                valueOf3 = Long.valueOf(com.google.android.gms.internal.measurement.zzne.zzak());
                return valueOf3;
            }
        });
        zzt = zza("measurement.upload.interval", valueOf, valueOf, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzbl
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Long valueOf3;
                valueOf3 = Long.valueOf(com.google.android.gms.internal.measurement.zzne.zzx());
                return valueOf3;
            }
        });
        zzu = zza("measurement.upload.realtime_upload_interval", 10000L, 10000L, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzbn
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Long valueOf3;
                valueOf3 = Long.valueOf(com.google.android.gms.internal.measurement.zzne.zzp());
                return valueOf3;
            }
        });
        zzv = zza("measurement.upload.debug_upload_interval", 1000L, 1000L, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzbq
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Long valueOf3;
                valueOf3 = Long.valueOf(com.google.android.gms.internal.measurement.zzne.zze());
                return valueOf3;
            }
        });
        zzw = zza("measurement.upload.minimum_delay", 500L, 500L, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzbp
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Long valueOf3;
                valueOf3 = Long.valueOf(com.google.android.gms.internal.measurement.zzne.zzn());
                return valueOf3;
            }
        });
        Long valueOf3 = Long.valueOf(DateUtils.MILLIS_PER_MINUTE);
        zzx = zza("measurement.alarm_manager.minimum_interval", valueOf3, valueOf3, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzbs
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Long valueOf4;
                valueOf4 = Long.valueOf(com.google.android.gms.internal.measurement.zzne.zzm());
                return valueOf4;
            }
        });
        zzy = zza("measurement.upload.stale_data_deletion_interval", valueOf2, valueOf2, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzbr
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Long valueOf4;
                valueOf4 = Long.valueOf(com.google.android.gms.internal.measurement.zzne.zzs());
                return valueOf4;
            }
        });
        zzz = zza("measurement.upload.refresh_blacklisted_config_interval", 604800000L, 604800000L, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzbu
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Long valueOf4;
                valueOf4 = Long.valueOf(com.google.android.gms.internal.measurement.zzne.zzq());
                return valueOf4;
            }
        });
        zzaa = zza("measurement.upload.initial_upload_delay_time", 15000L, 15000L, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzbt
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Long valueOf4;
                valueOf4 = Long.valueOf(com.google.android.gms.internal.measurement.zzne.zzw());
                return valueOf4;
            }
        });
        zzab = zza("measurement.upload.retry_time", 1800000L, 1800000L, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzbv
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Long valueOf4;
                valueOf4 = Long.valueOf(com.google.android.gms.internal.measurement.zzne.zzaj());
                return valueOf4;
            }
        });
        zzac = zza("measurement.upload.retry_count", 6, 6, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzby
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Integer valueOf4;
                valueOf4 = Integer.valueOf((int) com.google.android.gms.internal.measurement.zzne.zzai());
                return valueOf4;
            }
        });
        zzad = zza("measurement.upload.max_queue_time", 2419200000L, 2419200000L, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzbx
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Long valueOf4;
                valueOf4 = Long.valueOf(com.google.android.gms.internal.measurement.zzne.zzaf());
                return valueOf4;
            }
        });
        zzae = zza("measurement.lifetimevalue.max_currency_tracked", 4, 4, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzbz
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Integer valueOf4;
                valueOf4 = Integer.valueOf((int) com.google.android.gms.internal.measurement.zzne.zzf());
                return valueOf4;
            }
        });
        Integer valueOf4 = Integer.valueOf(ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION);
        zzaf = zza("measurement.audience.filter_result_max_count", valueOf4, valueOf4, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzcc
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Integer valueOf5;
                valueOf5 = Integer.valueOf((int) com.google.android.gms.internal.measurement.zzne.zzj());
                return valueOf5;
            }
        });
        zzag = zza("measurement.upload.max_public_user_properties", 25, 25, null);
        zzah = zza("measurement.upload.max_event_name_cardinality", 500, 500, null);
        zzai = zza("measurement.upload.max_public_event_params", 25, 25, null);
        zzaj = zza("measurement.service_client.idle_disconnect_millis", 5000L, 5000L, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzcb
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Long valueOf5;
                valueOf5 = Long.valueOf(com.google.android.gms.internal.measurement.zzne.zzr());
                return valueOf5;
            }
        });
        zzak = zza("measurement.test.boolean_flag", false, false, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzce
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Boolean valueOf5;
                valueOf5 = Boolean.valueOf(zzpa.zze());
                return valueOf5;
            }
        });
        zzal = zza("measurement.test.string_flag", "---", "---", new zzfg() { // from class: com.google.android.gms.measurement.internal.zzcd
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                String zzd2;
                zzd2 = zzpa.zzd();
                return zzd2;
            }
        });
        zzam = zza("measurement.test.long_flag", -1L, -1L, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzcg
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Long valueOf5;
                valueOf5 = Long.valueOf(zzpa.zzc());
                return valueOf5;
            }
        });
        zzan = zza("measurement.test.int_flag", -2, -2, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzci
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Integer valueOf5;
                valueOf5 = Integer.valueOf((int) zzpa.zzb());
                return valueOf5;
            }
        });
        Double valueOf5 = Double.valueOf(-3.0d);
        zzao = zza("measurement.test.double_flag", valueOf5, valueOf5, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzch
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Double valueOf6;
                valueOf6 = Double.valueOf(zzpa.zza());
                return valueOf6;
            }
        });
        zzap = zza("measurement.experiment.max_ids", 50, 50, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzck
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Integer valueOf6;
                valueOf6 = Integer.valueOf((int) com.google.android.gms.internal.measurement.zzne.zzi());
                return valueOf6;
            }
        });
        zzaq = zza("measurement.upload.max_item_scoped_custom_parameters", 27, 27, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzcj
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Integer valueOf6;
                valueOf6 = Integer.valueOf((int) com.google.android.gms.internal.measurement.zzne.zzk());
                return valueOf6;
            }
        });
        zzcx = zza("measurement.upload.max_event_parameter_value_length", 100, 100, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzcl
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Integer valueOf6;
                valueOf6 = Integer.valueOf((int) com.google.android.gms.internal.measurement.zzne.zzg());
                return valueOf6;
            }
        });
        zzar = zza("measurement.max_bundles_per_iteration", 100, 100, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzco
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Integer valueOf6;
                valueOf6 = Integer.valueOf((int) com.google.android.gms.internal.measurement.zzne.zzc());
                return valueOf6;
            }
        });
        zzas = zza("measurement.sdk.attribution.cache.ttl", 604800000L, 604800000L, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzcn
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Long valueOf6;
                valueOf6 = Long.valueOf(com.google.android.gms.internal.measurement.zzne.zzt());
                return valueOf6;
            }
        });
        zzat = zza("measurement.redaction.app_instance_id.ttl", 7200000L, 7200000L, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzcq
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Long valueOf6;
                valueOf6 = Long.valueOf(com.google.android.gms.internal.measurement.zzne.zzu());
                return valueOf6;
            }
        });
        zzau = zza("measurement.rb.attribution.client.min_ad_services_version", 7, 7, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzcp
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Integer valueOf6;
                valueOf6 = Integer.valueOf((int) com.google.android.gms.internal.measurement.zzne.zzl());
                return valueOf6;
            }
        });
        zzav = zza("measurement.rb.attribution.uri_scheme", "https", "https", new zzfg() { // from class: com.google.android.gms.measurement.internal.zzcr
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                String zzas2;
                zzas2 = com.google.android.gms.internal.measurement.zzne.zzas();
                return zzas2;
            }
        });
        zzaw = zza("measurement.rb.attribution.uri_authority", "google-analytics.com", "google-analytics.com", new zzfg() { // from class: com.google.android.gms.measurement.internal.zzcu
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                String zzap2;
                zzap2 = com.google.android.gms.internal.measurement.zzne.zzap();
                return zzap2;
            }
        });
        zzax = zza("measurement.rb.attribution.uri_path", "privacy-sandbox/register-app-conversion", "privacy-sandbox/register-app-conversion", new zzfg() { // from class: com.google.android.gms.measurement.internal.zzct
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                String zzaq2;
                zzaq2 = com.google.android.gms.internal.measurement.zzne.zzaq();
                return zzaq2;
            }
        });
        zzay = zza("measurement.rb.attribution.app_allowlist", "com.labpixies.flood,", "com.labpixies.flood,", new zzfg() { // from class: com.google.android.gms.measurement.internal.zzcw
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                String zzao2;
                zzao2 = com.google.android.gms.internal.measurement.zzne.zzao();
                return zzao2;
            }
        });
        zzaz = zza("measurement.rb.attribution.user_properties", "_npa,npa", "_npa,npa", new zzfg() { // from class: com.google.android.gms.measurement.internal.zzcv
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                String zzau2;
                zzau2 = com.google.android.gms.internal.measurement.zzne.zzau();
                return zzau2;
            }
        });
        zzba = zza("measurement.rb.attribution.event_params", "value|currency", "value|currency", new zzfg() { // from class: com.google.android.gms.measurement.internal.zzcx
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                String zzan2;
                zzan2 = com.google.android.gms.internal.measurement.zzne.zzan();
                return zzan2;
            }
        });
        zzbb = zza("measurement.rb.attribution.query_parameters_to_remove", "", "", new zzfg() { // from class: com.google.android.gms.measurement.internal.zzda
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                String zzar2;
                zzar2 = com.google.android.gms.internal.measurement.zzne.zzar();
                return zzar2;
            }
        });
        zzbc = zza("measurement.collection.log_event_and_bundle_v2", true, true, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzcz
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Boolean valueOf6;
                valueOf6 = Boolean.valueOf(zzpf.zza());
                return valueOf6;
            }
        });
        zzbd = zza("measurement.quality.checksum", false, false, null);
        zzbe = zza("measurement.audience.use_bundle_end_timestamp_for_non_sequence_property_filters", false, false, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzdc
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Boolean valueOf6;
                valueOf6 = Boolean.valueOf(zzob.zzc());
                return valueOf6;
            }
        });
        zzbf = zza("measurement.audience.refresh_event_count_filters_timestamp", false, false, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzde
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Boolean valueOf6;
                valueOf6 = Boolean.valueOf(zzob.zzb());
                return valueOf6;
            }
        });
        zzbg = zza("measurement.audience.use_bundle_timestamp_for_event_count_filters", false, false, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzdd
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Boolean valueOf6;
                valueOf6 = Boolean.valueOf(zzob.zzd());
                return valueOf6;
            }
        });
        zzbh = zza("measurement.sdk.collection.retrieve_deeplink_from_bow_2", true, true, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzdg
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Boolean valueOf6;
                valueOf6 = Boolean.valueOf(zzqj.zza());
                return valueOf6;
            }
        });
        zzbi = zza("measurement.sdk.collection.last_deep_link_referrer_campaign2", false, false, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzdf
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Boolean valueOf6;
                valueOf6 = Boolean.valueOf(zzou.zza());
                return valueOf6;
            }
        });
        zzbj = zza("measurement.lifecycle.app_in_background_parameter", false, false, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzdi
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Boolean valueOf6;
                valueOf6 = Boolean.valueOf(zzoz.zza());
                return valueOf6;
            }
        });
        zzbk = zza("measurement.integration.disable_firebase_instance_id", false, false, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzdh
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Boolean valueOf6;
                valueOf6 = Boolean.valueOf(zzqe.zzb());
                return valueOf6;
            }
        });
        zzbl = zza("measurement.collection.service.update_with_analytics_fix", false, false, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzdj
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Boolean valueOf6;
                valueOf6 = Boolean.valueOf(zzqk.zza());
                return valueOf6;
            }
        });
        zzbm = zza("measurement.client.firebase_feature_rollout.v1.enable", true, true, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzdm
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Boolean valueOf6;
                valueOf6 = Boolean.valueOf(zznv.zzb());
                return valueOf6;
            }
        });
        zzbn = zza("measurement.client.sessions.check_on_reset_and_enable2", true, true, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzdl
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Boolean valueOf6;
                valueOf6 = Boolean.valueOf(zzoh.zzb());
                return valueOf6;
            }
        });
        zzbo = zza("measurement.service.storage_consent_support_version", 203600, 203600, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzdn
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Integer valueOf6;
                valueOf6 = Integer.valueOf((int) zznj.zza());
                return valueOf6;
            }
        });
        zzcy = zza("measurement.client.click_identifier_control.dev", false, false, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzdq
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Boolean valueOf6;
                valueOf6 = Boolean.valueOf(com.google.android.gms.internal.measurement.zzmy.zza());
                return valueOf6;
            }
        });
        zzcz = zza("measurement.service.click_identifier_control", false, false, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzdp
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Boolean valueOf6;
                valueOf6 = Boolean.valueOf(com.google.android.gms.internal.measurement.zznd.zza());
                return valueOf6;
            }
        });
        zzbp = zza("measurement.service.store_null_safelist", true, true, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzds
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Boolean valueOf6;
                valueOf6 = Boolean.valueOf(zznq.zzb());
                return valueOf6;
            }
        });
        zzbq = zza("measurement.service.store_safelist", true, true, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzdr
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Boolean valueOf6;
                valueOf6 = Boolean.valueOf(zznq.zzc());
                return valueOf6;
            }
        });
        zzbr = zza("measurement.collection.enable_session_stitching_token.first_open_fix", true, true, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzdu
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Boolean valueOf6;
                valueOf6 = Boolean.valueOf(zzps.zzc());
                return valueOf6;
            }
        });
        zzbs = zza("measurement.collection.enable_session_stitching_token.client.dev", true, true, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzdt
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Boolean valueOf6;
                valueOf6 = Boolean.valueOf(zzps.zzb());
                return valueOf6;
            }
        });
        zzbt = zza("measurement.session_stitching_token_enabled", false, false, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzdv
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Boolean valueOf6;
                valueOf6 = Boolean.valueOf(zzps.zzd());
                return valueOf6;
            }
        });
        zzda = zza("measurement.sgtm.client.dev", false, false, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzdy
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Boolean valueOf6;
                valueOf6 = Boolean.valueOf(zzqd.zzb());
                return valueOf6;
            }
        });
        zzbu = zza("measurement.sgtm.service", false, false, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzea
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Boolean valueOf6;
                valueOf6 = Boolean.valueOf(zzqd.zzc());
                return valueOf6;
            }
        });
        zzbv = zza("measurement.redaction.retain_major_os_version", true, true, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzdz
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Boolean valueOf6;
                valueOf6 = Boolean.valueOf(zzpl.zza());
                return valueOf6;
            }
        });
        zzbw = zza("measurement.redaction.scion_payload_generator", true, true, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzec
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Boolean valueOf6;
                valueOf6 = Boolean.valueOf(zzpl.zzb());
                return valueOf6;
            }
        });
        zzbx = zza("measurement.sessionid.enable_client_session_id", true, true, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzeb
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Boolean valueOf6;
                valueOf6 = Boolean.valueOf(zzpr.zzb());
                return valueOf6;
            }
        });
        zzby = zza("measurement.sfmc.client", true, true, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzee
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Boolean valueOf6;
                valueOf6 = Boolean.valueOf(zzpy.zzb());
                return valueOf6;
            }
        });
        zzbz = zza("measurement.sfmc.service", true, true, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzed
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Boolean valueOf6;
                valueOf6 = Boolean.valueOf(zzpy.zzc());
                return valueOf6;
            }
        });
        zzca = zza("measurement.gmscore_feature_tracking", true, true, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzeg
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Boolean valueOf6;
                valueOf6 = Boolean.valueOf(zzon.zzb());
                return valueOf6;
            }
        });
        zzcb = zza("measurement.fix_health_monitor_stack_trace", true, true, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzef
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Boolean valueOf6;
                valueOf6 = Boolean.valueOf(zzoc.zzb());
                return valueOf6;
            }
        });
        zzcc = zza("measurement.item_scoped_custom_parameters.client", true, true, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzeh
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Boolean valueOf6;
                valueOf6 = Boolean.valueOf(zzot.zzb());
                return valueOf6;
            }
        });
        zzcd = zza("measurement.item_scoped_custom_parameters.service", false, false, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzej
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Boolean valueOf6;
                valueOf6 = Boolean.valueOf(zzot.zzc());
                return valueOf6;
            }
        });
        zzce = zza("measurement.remove_app_background.client", false, false, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzem
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Boolean valueOf6;
                valueOf6 = Boolean.valueOf(zzpm.zzb());
                return valueOf6;
            }
        });
        zzcf = zza("measurement.rb.attribution.service", false, false, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzel
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Boolean valueOf6;
                valueOf6 = Boolean.valueOf(zzpg.zzd());
                return valueOf6;
            }
        });
        zzcg = zza("measurement.rb.attribution.client2", false, false, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzeo
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Boolean valueOf6;
                valueOf6 = Boolean.valueOf(zzpg.zzb());
                return valueOf6;
            }
        });
        zzch = zza("measurement.rb.attribution.uuid_generation", true, true, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzen
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Boolean valueOf6;
                valueOf6 = Boolean.valueOf(zzpg.zzf());
                return valueOf6;
            }
        });
        zzci = zza("measurement.rb.attribution.enable_trigger_redaction", true, true, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzeq
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Boolean valueOf6;
                valueOf6 = Boolean.valueOf(zzpg.zze());
                return valueOf6;
            }
        });
        zzdb = zza("measurement.rb.attribution.followup1.service", false, false, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzep
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Boolean valueOf6;
                valueOf6 = Boolean.valueOf(zzpg.zzc());
                return valueOf6;
            }
        });
        zzcj = zza("measurement.client.sessions.enable_fix_background_engagement", false, false, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzes
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                return Boolean.valueOf(zzpx.zza());
            }
        });
        zzck = zza("measurement.client.ad_id_consent_fix", true, true, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzer
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                return Boolean.valueOf(zznw.zzb());
            }
        });
        zzcl = zza("measurement.dma_consent.client", false, false, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzew
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Boolean valueOf6;
                valueOf6 = Boolean.valueOf(zznp.zzb());
                return valueOf6;
            }
        });
        zzcm = zza("measurement.dma_consent.service", false, false, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzev
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Boolean valueOf6;
                valueOf6 = Boolean.valueOf(zznp.zzd());
                return valueOf6;
            }
        });
        zzcn = zza("measurement.dma_consent.client_bow_check", false, false, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzey
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Boolean valueOf6;
                valueOf6 = Boolean.valueOf(zznp.zzc());
                return valueOf6;
            }
        });
        zzco = zza("measurement.dma_consent.service_gcs_v2", false, false, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzex
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Boolean valueOf6;
                valueOf6 = Boolean.valueOf(zznp.zze());
                return valueOf6;
            }
        });
        zzcp = zza("measurement.dma_consent.service_npa_remote_default", false, false, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzfa
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Boolean valueOf6;
                valueOf6 = Boolean.valueOf(zznp.zzf());
                return valueOf6;
            }
        });
        zzcq = zza("measurement.dma_consent.service_split_batch_on_consent", false, false, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzez
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Boolean valueOf6;
                valueOf6 = Boolean.valueOf(zznp.zzg());
                return valueOf6;
            }
        });
        zzcr = zza("measurement.service.deferred_first_open", false, false, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzfc
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Boolean valueOf6;
                valueOf6 = Boolean.valueOf(zznk.zzb());
                return valueOf6;
            }
        });
        zzcs = zza("measurement.gbraid_campaign.gbraid.client.dev", false, false, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzfb
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Boolean valueOf6;
                valueOf6 = Boolean.valueOf(zzoi.zzb());
                return valueOf6;
            }
        });
        zzct = zza("measurement.gbraid_campaign.gbraid.service", false, false, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzfe
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Boolean valueOf6;
                valueOf6 = Boolean.valueOf(zzoi.zzc());
                return valueOf6;
            }
        });
        zzcu = zza("measurement.increase_param_lengths", false, false, new zzfg() { // from class: com.google.android.gms.measurement.internal.zzfd
            @Override // com.google.android.gms.measurement.internal.zzfg
            public final Object zza() {
                Boolean valueOf6;
                valueOf6 = Boolean.valueOf(zzoo.zzb());
                return valueOf6;
            }
        });
    }
}

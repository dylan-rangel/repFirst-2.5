package com.google.android.gms.measurement.internal;

import com.google.android.gms.common.internal.Preconditions;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import net.openid.appauth.AuthState;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.5.0 */
/* loaded from: classes2.dex */
final class zzke implements Runnable {
    private final URL zza;
    private final byte[] zzb;
    private final zzkb zzc;
    private final String zzd;
    private final Map<String, String> zze;
    private final /* synthetic */ zzkc zzf;

    public zzke(zzkc zzkcVar, String str, URL url, byte[] bArr, Map<String, String> map, zzkb zzkbVar) {
        this.zzf = zzkcVar;
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(url);
        Preconditions.checkNotNull(zzkbVar);
        this.zza = url;
        this.zzb = null;
        this.zzc = zzkbVar;
        this.zzd = str;
        this.zze = null;
    }

    private final void zzb(final int i, final Exception exc, final byte[] bArr, final Map<String, List<String>> map) {
        this.zzf.zzl().zzb(new Runnable() { // from class: com.google.android.gms.measurement.internal.zzkd
            @Override // java.lang.Runnable
            public final void run() {
                zzke.this.zza(i, exc, bArr, map);
            }
        });
    }

    final /* synthetic */ void zza(int i, Exception exc, byte[] bArr, Map map) {
        this.zzc.zza(this.zzd, i, exc, bArr, map);
    }

    @Override // java.lang.Runnable
    public final void run() {
        HttpURLConnection httpURLConnection;
        Map<String, List<String>> map;
        byte[] zza;
        this.zzf.zzr();
        int i = 0;
        try {
            URLConnection zza2 = com.google.android.gms.internal.measurement.zzcd.zza().zza(this.zza, "client-measurement");
            if (!(zza2 instanceof HttpURLConnection)) {
                throw new IOException("Failed to obtain HTTP connection");
            }
            httpURLConnection = (HttpURLConnection) zza2;
            httpURLConnection.setDefaultUseCaches(false);
            httpURLConnection.setConnectTimeout(AuthState.EXPIRY_TIME_TOLERANCE_MS);
            httpURLConnection.setReadTimeout(61000);
            httpURLConnection.setInstanceFollowRedirects(false);
            httpURLConnection.setDoInput(true);
            try {
                i = httpURLConnection.getResponseCode();
                map = httpURLConnection.getHeaderFields();
            } catch (IOException e) {
                e = e;
                map = null;
            } catch (Throwable th) {
                th = th;
                map = null;
            }
            try {
                zzkc zzkcVar = this.zzf;
                zza = zzkc.zza(httpURLConnection);
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                zzb(i, null, zza, map);
            } catch (IOException e2) {
                e = e2;
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                zzb(i, e, null, map);
            } catch (Throwable th2) {
                th = th2;
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                zzb(i, null, null, map);
                throw th;
            }
        } catch (IOException e3) {
            e = e3;
            httpURLConnection = null;
            map = null;
        } catch (Throwable th3) {
            th = th3;
            httpURLConnection = null;
            map = null;
        }
    }
}

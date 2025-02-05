package com.facebook.react.modules.systeminfo;

import android.content.Context;
import android.os.Build;
import com.facebook.react.R;
import java.util.Locale;

/* loaded from: classes.dex */
public class AndroidInfoHelpers {
    public static final String DEVICE_LOCALHOST = "localhost";
    public static final String EMULATOR_LOCALHOST = "10.0.2.2";
    public static final String GENYMOTION_LOCALHOST = "10.0.3.2";
    public static final String METRO_HOST_PROP_NAME = "metro.host";
    private static final String TAG = "AndroidInfoHelpers";
    private static String metroHostPropValue;

    private static boolean isRunningOnGenymotion() {
        return Build.FINGERPRINT.contains("vbox");
    }

    private static boolean isRunningOnStockEmulator() {
        return Build.FINGERPRINT.contains("generic");
    }

    public static String getServerHost(Integer num) {
        return getServerIpAddress(num.intValue());
    }

    public static String getServerHost(Context context) {
        return getServerIpAddress(getDevServerPort(context).intValue());
    }

    public static String getAdbReverseTcpCommand(Integer num) {
        return "adb reverse tcp:" + num + " tcp:" + num;
    }

    public static String getAdbReverseTcpCommand(Context context) {
        return getAdbReverseTcpCommand(getDevServerPort(context));
    }

    public static String getInspectorProxyHost(Context context) {
        return getServerIpAddress(getInspectorProxyPort(context).intValue());
    }

    public static String getFriendlyDeviceName() {
        if (isRunningOnGenymotion()) {
            return Build.MODEL;
        }
        return Build.MODEL + " - " + Build.VERSION.RELEASE + " - API " + Build.VERSION.SDK_INT;
    }

    private static Integer getDevServerPort(Context context) {
        return Integer.valueOf(context.getResources().getInteger(R.integer.react_native_dev_server_port));
    }

    private static Integer getInspectorProxyPort(Context context) {
        return Integer.valueOf(context.getResources().getInteger(R.integer.react_native_dev_server_port));
    }

    private static String getServerIpAddress(int i) {
        String metroHostPropValue2 = getMetroHostPropValue();
        if (metroHostPropValue2.equals("")) {
            if (isRunningOnGenymotion()) {
                metroHostPropValue2 = GENYMOTION_LOCALHOST;
            } else {
                metroHostPropValue2 = isRunningOnStockEmulator() ? EMULATOR_LOCALHOST : DEVICE_LOCALHOST;
            }
        }
        return String.format(Locale.US, "%s:%d", metroHostPropValue2, Integer.valueOf(i));
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x003f, code lost:
    
        if (r2 == null) goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0041, code lost:
    
        r2.destroy();
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x006f, code lost:
    
        r1 = com.facebook.react.modules.systeminfo.AndroidInfoHelpers.metroHostPropValue;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0072, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x006c, code lost:
    
        if (r2 == null) goto L39;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static synchronized java.lang.String getMetroHostPropValue() {
        /*
            java.lang.Class<com.facebook.react.modules.systeminfo.AndroidInfoHelpers> r0 = com.facebook.react.modules.systeminfo.AndroidInfoHelpers.class
            monitor-enter(r0)
            java.lang.String r1 = com.facebook.react.modules.systeminfo.AndroidInfoHelpers.metroHostPropValue     // Catch: java.lang.Throwable -> L81
            if (r1 == 0) goto L9
            monitor-exit(r0)
            return r1
        L9:
            r1 = 0
            java.lang.Runtime r2 = java.lang.Runtime.getRuntime()     // Catch: java.lang.Throwable -> L51 java.lang.Exception -> L56
            java.lang.String r3 = "/system/bin/getprop"
            java.lang.String r4 = "metro.host"
            java.lang.String[] r3 = new java.lang.String[]{r3, r4}     // Catch: java.lang.Throwable -> L51 java.lang.Exception -> L56
            java.lang.Process r2 = r2.exec(r3)     // Catch: java.lang.Throwable -> L51 java.lang.Exception -> L56
            java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L47 java.lang.Exception -> L4c
            java.io.InputStreamReader r4 = new java.io.InputStreamReader     // Catch: java.lang.Throwable -> L47 java.lang.Exception -> L4c
            java.io.InputStream r5 = r2.getInputStream()     // Catch: java.lang.Throwable -> L47 java.lang.Exception -> L4c
            java.lang.String r6 = "UTF-8"
            java.nio.charset.Charset r6 = java.nio.charset.Charset.forName(r6)     // Catch: java.lang.Throwable -> L47 java.lang.Exception -> L4c
            r4.<init>(r5, r6)     // Catch: java.lang.Throwable -> L47 java.lang.Exception -> L4c
            r3.<init>(r4)     // Catch: java.lang.Throwable -> L47 java.lang.Exception -> L4c
            java.lang.String r1 = ""
        L30:
            java.lang.String r4 = r3.readLine()     // Catch: java.lang.Exception -> L45 java.lang.Throwable -> L73
            if (r4 == 0) goto L38
            r1 = r4
            goto L30
        L38:
            com.facebook.react.modules.systeminfo.AndroidInfoHelpers.metroHostPropValue = r1     // Catch: java.lang.Exception -> L45 java.lang.Throwable -> L73
            r3.close()     // Catch: java.lang.Exception -> L3e java.lang.Throwable -> L81
            goto L3f
        L3e:
        L3f:
            if (r2 == 0) goto L6f
        L41:
            r2.destroy()     // Catch: java.lang.Throwable -> L81
            goto L6f
        L45:
            r1 = move-exception
            goto L5a
        L47:
            r3 = move-exception
            r7 = r3
            r3 = r1
            r1 = r7
            goto L74
        L4c:
            r3 = move-exception
            r7 = r3
            r3 = r1
            r1 = r7
            goto L5a
        L51:
            r2 = move-exception
            r3 = r1
            r1 = r2
            r2 = r3
            goto L74
        L56:
            r2 = move-exception
            r3 = r1
            r1 = r2
            r2 = r3
        L5a:
            java.lang.String r4 = com.facebook.react.modules.systeminfo.AndroidInfoHelpers.TAG     // Catch: java.lang.Throwable -> L73
            java.lang.String r5 = "Failed to query for metro.host prop:"
            com.facebook.common.logging.FLog.w(r4, r5, r1)     // Catch: java.lang.Throwable -> L73
            java.lang.String r1 = ""
            com.facebook.react.modules.systeminfo.AndroidInfoHelpers.metroHostPropValue = r1     // Catch: java.lang.Throwable -> L73
            if (r3 == 0) goto L6c
            r3.close()     // Catch: java.lang.Exception -> L6b java.lang.Throwable -> L81
            goto L6c
        L6b:
        L6c:
            if (r2 == 0) goto L6f
            goto L41
        L6f:
            java.lang.String r1 = com.facebook.react.modules.systeminfo.AndroidInfoHelpers.metroHostPropValue     // Catch: java.lang.Throwable -> L81
            monitor-exit(r0)
            return r1
        L73:
            r1 = move-exception
        L74:
            if (r3 == 0) goto L7b
            r3.close()     // Catch: java.lang.Exception -> L7a java.lang.Throwable -> L81
            goto L7b
        L7a:
        L7b:
            if (r2 == 0) goto L80
            r2.destroy()     // Catch: java.lang.Throwable -> L81
        L80:
            throw r1     // Catch: java.lang.Throwable -> L81
        L81:
            r1 = move-exception
            monitor-exit(r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.react.modules.systeminfo.AndroidInfoHelpers.getMetroHostPropValue():java.lang.String");
    }
}

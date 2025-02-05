package com.facebook.soloader;

import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import com.facebook.soloader.SysUtil;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/* loaded from: classes.dex */
public class DirectApkSoSource extends SoSource {
    private final File mApkFile;
    private final Set<String> mDirectApkLdPaths;
    private final Map<String, Set<String>> mLibsInApkMap;

    public DirectApkSoSource(Context context) {
        this.mLibsInApkMap = new HashMap();
        this.mDirectApkLdPaths = getDirectApkLdPaths("");
        this.mApkFile = new File(context.getApplicationInfo().sourceDir);
    }

    public DirectApkSoSource(File file) {
        this.mLibsInApkMap = new HashMap();
        this.mDirectApkLdPaths = getDirectApkLdPaths(SysUtil.getBaseName(file.getName()));
        this.mApkFile = file;
    }

    @Override // com.facebook.soloader.SoSource
    public int loadLibrary(String str, int i, StrictMode.ThreadPolicy threadPolicy) throws IOException {
        if (SoLoader.sSoFileLoader == null) {
            throw new IllegalStateException("SoLoader.init() not yet called");
        }
        for (String str2 : this.mDirectApkLdPaths) {
            Set<String> set = this.mLibsInApkMap.get(str2);
            if (TextUtils.isEmpty(str2) || set == null || !set.contains(str)) {
                Log.v("SoLoader", str + " not found on " + str2);
            } else {
                loadDependencies(str, i, threadPolicy);
                try {
                    i |= 4;
                    SoLoader.sSoFileLoader.load(str2 + File.separator + str, i);
                    Log.d("SoLoader", str + " found on " + str2);
                    return 1;
                } catch (UnsatisfiedLinkError e) {
                    Log.w("SoLoader", str + " not found on " + str2 + " flag: " + i, e);
                }
            }
        }
        return 0;
    }

    @Override // com.facebook.soloader.SoSource
    public File unpackLibrary(String str) throws IOException {
        throw new UnsupportedOperationException("DirectAPKSoSource doesn't support unpackLibrary");
    }

    static Set<String> getDirectApkLdPaths(String str) {
        HashSet hashSet = new HashSet();
        String classLoaderLdLoadLibrary = Build.VERSION.SDK_INT >= 14 ? SysUtil.Api14Utils.getClassLoaderLdLoadLibrary() : null;
        if (classLoaderLdLoadLibrary != null) {
            for (String str2 : classLoaderLdLoadLibrary.split(":")) {
                if (str2.contains(str + ".apk!/")) {
                    hashSet.add(str2);
                }
            }
        }
        return hashSet;
    }

    private static String[] getDependencies(String str, ElfByteChannel elfByteChannel) throws IOException {
        if (SoLoader.SYSTRACE_LIBRARY_LOADING) {
            Api18TraceUtils.beginTraceSection("SoLoader.getElfDependencies[", str, "]");
        }
        try {
            return NativeDeps.getDependencies(str, elfByteChannel);
        } finally {
            if (SoLoader.SYSTRACE_LIBRARY_LOADING) {
                Api18TraceUtils.endSection();
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0034, code lost:
    
        r2 = new com.facebook.soloader.ElfZipFileChannel(r1, r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0039, code lost:
    
        r8 = getDependencies(r8, r2);
        r3 = r8.length;
        r4 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x003f, code lost:
    
        if (r4 >= r3) goto L49;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0041, code lost:
    
        r5 = r8[r4];
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0047, code lost:
    
        if (contains(r5) != false) goto L51;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x004d, code lost:
    
        if (r5.startsWith("/") == false) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0055, code lost:
    
        r4 = r4 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0050, code lost:
    
        com.facebook.soloader.SoLoader.loadLibraryBySoName(r5, r9 | 1, r10);
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0058, code lost:
    
        r2.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0066, code lost:
    
        r1.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0069, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x005c, code lost:
    
        r8 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0065, code lost:
    
        throw r8;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void loadDependencies(java.lang.String r8, int r9, android.os.StrictMode.ThreadPolicy r10) throws java.io.IOException {
        /*
            r7 = this;
            java.lang.String r0 = "/"
            java.util.zip.ZipFile r1 = new java.util.zip.ZipFile
            java.io.File r2 = r7.mApkFile
            r1.<init>(r2)
            java.util.Enumeration r2 = r1.entries()     // Catch: java.lang.Throwable -> L6a
        Ld:
            boolean r3 = r2.hasMoreElements()     // Catch: java.lang.Throwable -> L6a
            if (r3 == 0) goto L66
            java.lang.Object r3 = r2.nextElement()     // Catch: java.lang.Throwable -> L6a
            java.util.zip.ZipEntry r3 = (java.util.zip.ZipEntry) r3     // Catch: java.lang.Throwable -> L6a
            if (r3 == 0) goto Ld
            java.lang.String r4 = r3.getName()     // Catch: java.lang.Throwable -> L6a
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L6a
            r5.<init>()     // Catch: java.lang.Throwable -> L6a
            r5.append(r0)     // Catch: java.lang.Throwable -> L6a
            r5.append(r8)     // Catch: java.lang.Throwable -> L6a
            java.lang.String r5 = r5.toString()     // Catch: java.lang.Throwable -> L6a
            boolean r4 = r4.endsWith(r5)     // Catch: java.lang.Throwable -> L6a
            if (r4 == 0) goto Ld
            com.facebook.soloader.ElfZipFileChannel r2 = new com.facebook.soloader.ElfZipFileChannel     // Catch: java.lang.Throwable -> L6a
            r2.<init>(r1, r3)     // Catch: java.lang.Throwable -> L6a
            java.lang.String[] r8 = getDependencies(r8, r2)     // Catch: java.lang.Throwable -> L5c
            int r3 = r8.length     // Catch: java.lang.Throwable -> L5c
            r4 = 0
        L3f:
            if (r4 >= r3) goto L58
            r5 = r8[r4]     // Catch: java.lang.Throwable -> L5c
            boolean r6 = r7.contains(r5)     // Catch: java.lang.Throwable -> L5c
            if (r6 != 0) goto L55
            boolean r6 = r5.startsWith(r0)     // Catch: java.lang.Throwable -> L5c
            if (r6 == 0) goto L50
            goto L55
        L50:
            r6 = r9 | 1
            com.facebook.soloader.SoLoader.loadLibraryBySoName(r5, r6, r10)     // Catch: java.lang.Throwable -> L5c
        L55:
            int r4 = r4 + 1
            goto L3f
        L58:
            r2.close()     // Catch: java.lang.Throwable -> L6a
            goto L66
        L5c:
            r8 = move-exception
            r2.close()     // Catch: java.lang.Throwable -> L61
            goto L65
        L61:
            r9 = move-exception
            r8.addSuppressed(r9)     // Catch: java.lang.Throwable -> L6a
        L65:
            throw r8     // Catch: java.lang.Throwable -> L6a
        L66:
            r1.close()
            return
        L6a:
            r8 = move-exception
            r1.close()     // Catch: java.lang.Throwable -> L6f
            goto L73
        L6f:
            r9 = move-exception
            r8.addSuppressed(r9)
        L73:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.soloader.DirectApkSoSource.loadDependencies(java.lang.String, int, android.os.StrictMode$ThreadPolicy):void");
    }

    @Override // com.facebook.soloader.SoSource
    protected void prepare(int i) throws IOException {
        int indexOf;
        int i2;
        String str = null;
        for (String str2 : this.mDirectApkLdPaths) {
            if (!TextUtils.isEmpty(str2) && (indexOf = str2.indexOf(33)) >= 0 && (i2 = indexOf + 2) < str2.length()) {
                str = str2.substring(i2);
            }
            if (!TextUtils.isEmpty(str)) {
                ZipFile zipFile = new ZipFile(this.mApkFile);
                try {
                    Enumeration<? extends ZipEntry> entries = zipFile.entries();
                    while (entries.hasMoreElements()) {
                        ZipEntry nextElement = entries.nextElement();
                        if (nextElement != null && nextElement.getName().startsWith(str) && nextElement.getName().endsWith(".so") && nextElement.getMethod() == 0) {
                            append(str2, nextElement.getName().substring(str.length() + 1));
                        }
                    }
                    zipFile.close();
                } catch (Throwable th) {
                    try {
                        zipFile.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            }
        }
    }

    @Override // com.facebook.soloader.SoSource
    public String toString() {
        return getClass().getName() + "[root = " + LdPathsToString() + ']';
    }

    private synchronized boolean contains(String str) {
        Iterator<Set<String>> it = this.mLibsInApkMap.values().iterator();
        while (it.hasNext()) {
            if (it.next().contains(str)) {
                return true;
            }
        }
        return false;
    }

    private synchronized void append(String str, String str2) {
        if (!this.mLibsInApkMap.containsKey(str)) {
            this.mLibsInApkMap.put(str, new HashSet());
        }
        this.mLibsInApkMap.get(str).add(str2);
    }

    private String LdPathsToString() {
        StringBuilder sb = new StringBuilder();
        sb.append('(');
        Iterator<String> it = this.mDirectApkLdPaths.iterator();
        while (it.hasNext()) {
            sb.append(it.next());
            sb.append(", ");
        }
        sb.append(')');
        return sb.toString();
    }
}

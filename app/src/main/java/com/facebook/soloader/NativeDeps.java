package com.facebook.soloader;

import android.content.Context;
import android.util.Log;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.annotation.Nullable;
import org.apache.commons.lang3.ClassUtils;

/* loaded from: classes.dex */
public final class NativeDeps {
    private static final float HASHMAP_LOAD_FACTOR = 1.0f;
    private static final int INITIAL_HASH = 5381;
    private static final int LIB_PREFIX_LEN = 3;
    private static final int LIB_SUFFIX_LEN = 3;
    private static final String LOG_TAG = "NativeDeps";
    private static final int WAITING_THREADS_WARNING_THRESHOLD = 3;

    @Nullable
    private static byte[] sEncodedDeps = null;
    private static volatile boolean sInitialized = false;
    private static Map<Integer, List<Integer>> sPrecomputedDeps = null;
    private static List<Integer> sPrecomputedLibs = null;
    private static volatile boolean sUseDepsFileAsync = false;
    private static final int LIB_PREFIX_SUFFIX_LEN = 3 + 3;
    private static final ReentrantReadWriteLock sWaitForDepsFileLock = new ReentrantReadWriteLock();

    public static String[] getDependencies(String str, File file) throws IOException {
        String[] awaitGetDepsFromPrecomputedDeps = awaitGetDepsFromPrecomputedDeps(str);
        return awaitGetDepsFromPrecomputedDeps != null ? awaitGetDepsFromPrecomputedDeps : MinElf.extract_DT_NEEDED(file);
    }

    public static String[] getDependencies(String str, ElfByteChannel elfByteChannel) throws IOException {
        String[] awaitGetDepsFromPrecomputedDeps = awaitGetDepsFromPrecomputedDeps(str);
        return awaitGetDepsFromPrecomputedDeps != null ? awaitGetDepsFromPrecomputedDeps : MinElf.extract_DT_NEEDED(elfByteChannel);
    }

    @Nullable
    private static String[] awaitGetDepsFromPrecomputedDeps(String str) {
        if (sInitialized) {
            return tryGetDepsFromPrecomputedDeps(str);
        }
        if (!sUseDepsFileAsync) {
            return null;
        }
        ReentrantReadWriteLock reentrantReadWriteLock = sWaitForDepsFileLock;
        reentrantReadWriteLock.readLock().lock();
        try {
            String[] tryGetDepsFromPrecomputedDeps = tryGetDepsFromPrecomputedDeps(str);
            reentrantReadWriteLock.readLock().unlock();
            return tryGetDepsFromPrecomputedDeps;
        } catch (Throwable th) {
            sWaitForDepsFileLock.readLock().unlock();
            throw th;
        }
    }

    public static boolean useDepsFile(final Context context, boolean z, final boolean z2) {
        if (!z) {
            return useDepsFileFromApkSync(context, z2);
        }
        new Thread(new Runnable() { // from class: com.facebook.soloader.NativeDeps.1
            @Override // java.lang.Runnable
            public void run() {
                NativeDeps.sWaitForDepsFileLock.writeLock().lock();
                boolean unused = NativeDeps.sUseDepsFileAsync = true;
                try {
                    NativeDeps.useDepsFileFromApkSync(context, z2);
                } finally {
                    int readLockCount = NativeDeps.sWaitForDepsFileLock.getReadLockCount();
                    if (readLockCount >= 3) {
                        Log.w(NativeDeps.LOG_TAG, "NativeDeps initialization finished with " + Integer.toString(readLockCount) + " threads waiting.");
                    }
                    NativeDeps.sWaitForDepsFileLock.writeLock().unlock();
                    boolean unused2 = NativeDeps.sUseDepsFileAsync = false;
                }
            }
        }, "soloader-nativedeps-init").start();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean useDepsFileFromApkSync(Context context, boolean z) {
        boolean z2;
        try {
            z2 = initDeps(context, z);
        } catch (IOException unused) {
            z2 = false;
        }
        if (!z2 && z) {
            try {
                NativeDepsUnpacker.ensureNativeDepsAvailable(context);
                z2 = initDeps(context, z);
            } catch (IOException unused2) {
            }
        }
        if (!z2) {
            Log.w(LOG_TAG, "Failed to extract native deps from APK, falling back to using MinElf to get library dependencies.");
        }
        return z2;
    }

    private static boolean initDeps(Context context, boolean z) throws IOException {
        byte[] readNativeDepsFromApk;
        byte[] bArr;
        verifyUninitialized();
        if (z) {
            bArr = SysUtil.makeApkDepBlock(new File(context.getApplicationInfo().sourceDir), context);
            readNativeDepsFromApk = NativeDepsUnpacker.readNativeDepsFromDisk(context);
        } else {
            readNativeDepsFromApk = NativeDepsUnpacker.readNativeDepsFromApk(context);
            bArr = null;
        }
        return processDepsBytes(bArr, readNativeDepsFromApk);
    }

    private static void indexLib(int i, int i2) {
        sPrecomputedLibs.add(Integer.valueOf(i2));
        List<Integer> list = sPrecomputedDeps.get(Integer.valueOf(i));
        if (list == null) {
            list = new ArrayList<>();
            sPrecomputedDeps.put(Integer.valueOf(i), list);
        }
        list.add(Integer.valueOf(i2));
    }

    private static void indexDepsBytes(byte[] bArr, int i) {
        int i2;
        byte b;
        boolean z = true;
        int i3 = 0;
        int i4 = 0;
        while (true) {
            if (z) {
                i3 = INITIAL_HASH;
                i2 = i;
                while (true) {
                    try {
                        b = bArr[i2];
                        if (b <= 32) {
                            break;
                        }
                        i3 = (i3 << 5) + i3 + b;
                        i2++;
                    } catch (IndexOutOfBoundsException unused) {
                        if (z || i == bArr.length) {
                            return;
                        }
                        indexLib(i3, i);
                        return;
                    }
                }
                indexLib(i3, i);
                z = b != 32;
            } else {
                while (bArr[i] != 10) {
                    try {
                        i++;
                    } catch (IndexOutOfBoundsException unused2) {
                        i = i4;
                        if (z) {
                            return;
                        } else {
                            return;
                        }
                    }
                }
                z = true;
                int i5 = i4;
                i2 = i;
                i = i5;
            }
            int i6 = i2 + 1;
            i4 = i;
            i = i6;
        }
    }

    private static int verifyBytesAndGetOffset(@Nullable byte[] bArr, @Nullable byte[] bArr2) {
        if (bArr == null || bArr.length == 0 || bArr2.length < bArr.length + 4) {
            return -1;
        }
        if (bArr2.length != bArr.length + 4 + ByteBuffer.wrap(bArr2, bArr.length, 4).getInt()) {
            return -1;
        }
        for (int i = 0; i < bArr.length; i++) {
            if (bArr[i] != bArr2[i]) {
                return -1;
            }
        }
        return bArr.length + 4;
    }

    private static int findNextLine(byte[] bArr, int i) {
        while (i < bArr.length && bArr[i] != 10) {
            i++;
        }
        return i < bArr.length ? i + 1 : i;
    }

    private static int parseLibCount(byte[] bArr, int i, int i2) {
        try {
            return Integer.parseInt(new String(bArr, i, i2));
        } catch (NumberFormatException unused) {
            return -1;
        }
    }

    static boolean processDepsBytes(byte[] bArr, byte[] bArr2) throws IOException {
        int i;
        int parseLibCount;
        if (bArr != null) {
            i = verifyBytesAndGetOffset(bArr, bArr2);
            if (i == -1) {
                return false;
            }
        } else {
            i = 0;
        }
        int findNextLine = findNextLine(bArr2, i);
        if (findNextLine >= bArr2.length || (parseLibCount = parseLibCount(bArr2, i, (findNextLine - i) - 1)) <= 0) {
            return false;
        }
        sPrecomputedDeps = new HashMap(((int) (parseLibCount / 1.0f)) + 1, 1.0f);
        sPrecomputedLibs = new ArrayList(parseLibCount);
        indexDepsBytes(bArr2, findNextLine);
        if (sPrecomputedLibs.size() != parseLibCount) {
            return false;
        }
        sEncodedDeps = bArr2;
        sInitialized = true;
        return true;
    }

    private static boolean libIsAtOffset(String str, int i) {
        int i2;
        int i3 = LIB_PREFIX_LEN;
        while (true) {
            int length = str.length();
            i2 = LIB_SUFFIX_LEN;
            if (i3 >= length - i2 || i >= sEncodedDeps.length || (str.codePointAt(i3) & 255) != sEncodedDeps[i]) {
                break;
            }
            i3++;
            i++;
        }
        return i3 == str.length() - i2;
    }

    private static int hashLib(String str) {
        int i = INITIAL_HASH;
        for (int i2 = LIB_PREFIX_LEN; i2 < str.length() - LIB_SUFFIX_LEN; i2++) {
            i = str.codePointAt(i2) + (i << 5) + i;
        }
        return i;
    }

    private static int getOffsetForLib(String str) {
        List<Integer> list = sPrecomputedDeps.get(Integer.valueOf(hashLib(str)));
        if (list == null) {
            return -1;
        }
        Iterator<Integer> it = list.iterator();
        while (it.hasNext()) {
            int intValue = it.next().intValue();
            if (libIsAtOffset(str, intValue)) {
                return intValue;
            }
        }
        return -1;
    }

    @Nullable
    private static String getLibString(int i) {
        if (i >= sPrecomputedLibs.size()) {
            return null;
        }
        int intValue = sPrecomputedLibs.get(i).intValue();
        int i2 = intValue;
        while (true) {
            byte[] bArr = sEncodedDeps;
            if (i2 >= bArr.length || bArr[i2] <= 32) {
                break;
            }
            i2++;
        }
        int i3 = (i2 - intValue) + LIB_PREFIX_SUFFIX_LEN;
        char[] cArr = new char[i3];
        cArr[0] = 'l';
        cArr[1] = 'i';
        cArr[2] = 'b';
        for (int i4 = 0; i4 < i3 - LIB_PREFIX_SUFFIX_LEN; i4++) {
            cArr[LIB_PREFIX_LEN + i4] = (char) sEncodedDeps[intValue + i4];
        }
        cArr[i3 - 3] = ClassUtils.PACKAGE_SEPARATOR_CHAR;
        cArr[i3 - 2] = 's';
        cArr[i3 - 1] = 'o';
        return new String(cArr);
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x003d, code lost:
    
        return null;
     */
    @javax.annotation.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.lang.String[] getDepsForLibAtOffset(int r6, int r7) {
        /*
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            int r6 = r6 + r7
            int r7 = com.facebook.soloader.NativeDeps.LIB_PREFIX_SUFFIX_LEN
            int r6 = r6 - r7
            r7 = 0
            r1 = 0
            r2 = 0
        Lc:
            byte[] r3 = com.facebook.soloader.NativeDeps.sEncodedDeps
            int r4 = r3.length
            r5 = 0
            if (r6 >= r4) goto L3e
            r3 = r3[r6]
            r4 = 10
            if (r3 == r4) goto L3e
            r4 = 32
            if (r3 != r4) goto L2b
            if (r2 == 0) goto L3a
            java.lang.String r1 = getLibString(r1)
            if (r1 != 0) goto L25
            return r5
        L25:
            r0.add(r1)
            r1 = 0
            r2 = 0
            goto L3a
        L2b:
            r2 = 48
            if (r3 < r2) goto L3d
            r2 = 57
            if (r3 <= r2) goto L34
            goto L3d
        L34:
            int r1 = r1 * 10
            int r3 = r3 + (-48)
            int r1 = r1 + r3
            r2 = 1
        L3a:
            int r6 = r6 + 1
            goto Lc
        L3d:
            return r5
        L3e:
            if (r2 == 0) goto L4a
            java.lang.String r6 = getLibString(r1)
            if (r6 != 0) goto L47
            return r5
        L47:
            r0.add(r6)
        L4a:
            int r6 = r0.size()
            if (r6 != 0) goto L51
            return r5
        L51:
            int r6 = r0.size()
            java.lang.String[] r6 = new java.lang.String[r6]
            java.lang.Object[] r6 = r0.toArray(r6)
            java.lang.String[] r6 = (java.lang.String[]) r6
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.soloader.NativeDeps.getDepsForLibAtOffset(int, int):java.lang.String[]");
    }

    @Nullable
    static String[] tryGetDepsFromPrecomputedDeps(String str) {
        int offsetForLib;
        if (sInitialized && str.length() > LIB_PREFIX_SUFFIX_LEN && (offsetForLib = getOffsetForLib(str)) != -1) {
            return getDepsForLibAtOffset(offsetForLib, str.length());
        }
        return null;
    }

    private static void verifyUninitialized() {
        if (sInitialized) {
            synchronized (NativeDeps.class) {
                if (sInitialized) {
                    throw new IllegalStateException("Trying to initialize NativeDeps but it was already initialized");
                }
            }
        }
    }
}

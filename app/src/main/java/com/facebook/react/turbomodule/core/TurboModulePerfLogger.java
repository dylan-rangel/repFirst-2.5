package com.facebook.react.turbomodule.core;

import com.facebook.react.perflogger.NativeModulePerfLogger;
import com.facebook.soloader.SoLoader;
import javax.annotation.Nullable;

/* loaded from: classes.dex */
public class TurboModulePerfLogger {
    private static boolean sIsSoLibraryLoaded = false;

    @Nullable
    private static NativeModulePerfLogger sNativeModulePerfLogger;

    private static native void jniEnableCppLogging(NativeModulePerfLogger nativeModulePerfLogger);

    public static void moduleDataCreateStart(String str, int i) {
        NativeModulePerfLogger nativeModulePerfLogger = sNativeModulePerfLogger;
        if (nativeModulePerfLogger != null) {
            nativeModulePerfLogger.moduleDataCreateStart(str, i);
        }
    }

    public static void moduleDataCreateEnd(String str, int i) {
        NativeModulePerfLogger nativeModulePerfLogger = sNativeModulePerfLogger;
        if (nativeModulePerfLogger != null) {
            nativeModulePerfLogger.moduleDataCreateEnd(str, i);
        }
    }

    public static void moduleCreateStart(String str, int i) {
        NativeModulePerfLogger nativeModulePerfLogger = sNativeModulePerfLogger;
        if (nativeModulePerfLogger != null) {
            nativeModulePerfLogger.moduleCreateStart(str, i);
        }
    }

    public static void moduleCreateCacheHit(String str, int i) {
        NativeModulePerfLogger nativeModulePerfLogger = sNativeModulePerfLogger;
        if (nativeModulePerfLogger != null) {
            nativeModulePerfLogger.moduleCreateCacheHit(str, i);
        }
    }

    public static void moduleCreateConstructStart(String str, int i) {
        NativeModulePerfLogger nativeModulePerfLogger = sNativeModulePerfLogger;
        if (nativeModulePerfLogger != null) {
            nativeModulePerfLogger.moduleCreateConstructStart(str, i);
        }
    }

    public static void moduleCreateConstructEnd(String str, int i) {
        NativeModulePerfLogger nativeModulePerfLogger = sNativeModulePerfLogger;
        if (nativeModulePerfLogger != null) {
            nativeModulePerfLogger.moduleCreateConstructEnd(str, i);
        }
    }

    public static void moduleCreateSetUpStart(String str, int i) {
        NativeModulePerfLogger nativeModulePerfLogger = sNativeModulePerfLogger;
        if (nativeModulePerfLogger != null) {
            nativeModulePerfLogger.moduleCreateSetUpStart(str, i);
        }
    }

    public static void moduleCreateSetUpEnd(String str, int i) {
        NativeModulePerfLogger nativeModulePerfLogger = sNativeModulePerfLogger;
        if (nativeModulePerfLogger != null) {
            nativeModulePerfLogger.moduleCreateSetUpEnd(str, i);
        }
    }

    public static void moduleCreateEnd(String str, int i) {
        NativeModulePerfLogger nativeModulePerfLogger = sNativeModulePerfLogger;
        if (nativeModulePerfLogger != null) {
            nativeModulePerfLogger.moduleCreateEnd(str, i);
        }
    }

    public static void moduleCreateFail(String str, int i) {
        NativeModulePerfLogger nativeModulePerfLogger = sNativeModulePerfLogger;
        if (nativeModulePerfLogger != null) {
            nativeModulePerfLogger.moduleCreateFail(str, i);
        }
    }

    private static synchronized void maybeLoadSoLibrary() {
        synchronized (TurboModulePerfLogger.class) {
            if (!sIsSoLibraryLoaded) {
                SoLoader.loadLibrary("turbomodulejsijni");
                sIsSoLibraryLoaded = true;
            }
        }
    }

    public static void enableLogging(NativeModulePerfLogger nativeModulePerfLogger) {
        if (nativeModulePerfLogger != null) {
            sNativeModulePerfLogger = nativeModulePerfLogger;
            maybeLoadSoLibrary();
            jniEnableCppLogging(nativeModulePerfLogger);
        }
    }
}

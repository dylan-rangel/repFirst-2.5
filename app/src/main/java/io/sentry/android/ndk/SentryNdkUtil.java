package io.sentry.android.ndk;

import io.sentry.protocol.SdkVersion;

/* loaded from: classes3.dex */
final class SentryNdkUtil {
    private SentryNdkUtil() {
    }

    static void addPackage(SdkVersion sdkVersion) {
        if (sdkVersion == null) {
            return;
        }
        sdkVersion.addPackage("maven:io.sentry:sentry-android-ndk", "6.33.1");
    }
}

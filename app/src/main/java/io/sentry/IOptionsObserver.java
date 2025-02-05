package io.sentry;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import io.sentry.protocol.SdkVersion;
import java.util.Map;

/* loaded from: classes3.dex */
public interface IOptionsObserver {

    @SynthesizedClassV2(kind = 7, versionHash = "5e5398f0546d1d7afd62641edb14d82894f11ddc41bce363a0c8d0dac82c9c5a")
    /* renamed from: io.sentry.IOptionsObserver$-CC, reason: invalid class name */
    public final /* synthetic */ class CC {
        public static void $default$setDist(IOptionsObserver _this, String str) {
        }

        public static void $default$setEnvironment(IOptionsObserver _this, String str) {
        }

        public static void $default$setProguardUuid(IOptionsObserver _this, String str) {
        }

        public static void $default$setRelease(IOptionsObserver _this, String str) {
        }

        public static void $default$setSdkVersion(IOptionsObserver _this, SdkVersion sdkVersion) {
        }

        public static void $default$setTags(IOptionsObserver _this, Map map) {
        }
    }

    void setDist(String str);

    void setEnvironment(String str);

    void setProguardUuid(String str);

    void setRelease(String str);

    void setSdkVersion(SdkVersion sdkVersion);

    void setTags(Map<String, String> map);
}

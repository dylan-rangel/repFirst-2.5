package io.sentry.config;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public interface PropertiesProvider {
    Boolean getBooleanProperty(String str);

    Double getDoubleProperty(String str);

    List<String> getList(String str);

    Long getLongProperty(String str);

    Map<String, String> getMap(String str);

    String getProperty(String str);

    String getProperty(String str, String str2);

    @SynthesizedClassV2(kind = 7, versionHash = "5e5398f0546d1d7afd62641edb14d82894f11ddc41bce363a0c8d0dac82c9c5a")
    /* renamed from: io.sentry.config.PropertiesProvider$-CC, reason: invalid class name */
    public final /* synthetic */ class CC {
        public static List $default$getList(PropertiesProvider _this, String str) {
            String property = _this.getProperty(str);
            return property != null ? Arrays.asList(property.split(",")) : Collections.emptyList();
        }

        public static String $default$getProperty(PropertiesProvider _this, String str, String str2) {
            String property = _this.getProperty(str);
            return property != null ? property : str2;
        }

        public static Boolean $default$getBooleanProperty(PropertiesProvider _this, String str) {
            String property = _this.getProperty(str);
            if (property != null) {
                return Boolean.valueOf(property);
            }
            return null;
        }

        public static Double $default$getDoubleProperty(PropertiesProvider _this, String str) {
            String property = _this.getProperty(str);
            if (property != null) {
                try {
                    return Double.valueOf(property);
                } catch (NumberFormatException unused) {
                }
            }
            return null;
        }

        public static Long $default$getLongProperty(PropertiesProvider _this, String str) {
            String property = _this.getProperty(str);
            if (property != null) {
                try {
                    return Long.valueOf(property);
                } catch (NumberFormatException unused) {
                }
            }
            return null;
        }
    }
}

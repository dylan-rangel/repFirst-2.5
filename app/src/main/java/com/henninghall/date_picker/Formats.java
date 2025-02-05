package com.henninghall.date_picker;

import io.sentry.protocol.ViewHierarchyNode;
import java.util.EnumMap;
import java.util.HashMap;

/* loaded from: classes2.dex */
public class Formats {
    public static EnumMap<Format, String> defaultFormat = mapOf("EEE, MMM d", "d", ViewHierarchyNode.JsonKeys.Y);
    private static HashMap<String, EnumMap<Format, String>> map = new HashMap<String, EnumMap<Format, String>>() { // from class: com.henninghall.date_picker.Formats.1
        {
            put("af", Formats.mapOf("EEE d MMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("am", Formats.mapOf("EEE፣ MMM d", "d", ViewHierarchyNode.JsonKeys.Y));
            put("ar", Formats.mapOf("EEE، d MMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("ar_DZ", Formats.mapOf("EEE، d MMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("ar_EG", Formats.mapOf("EEE، d MMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("az", Formats.mapOf("d MMM, EEE", "d", ViewHierarchyNode.JsonKeys.Y));
            put("be", Formats.mapOf("EEE, d MMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("bg", Formats.mapOf("EEE, d.MM", "d", "y 'г'."));
            put("bn", Formats.mapOf("EEE d MMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("br", Formats.mapOf("EEE d MMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("bs", Formats.mapOf("EEE, d. MMM", "d.", "y."));
            put("ca", Formats.mapOf("EEE, d MMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("chr", Formats.mapOf("EEE, MMM d", "d", ViewHierarchyNode.JsonKeys.Y));
            put("cs", Formats.mapOf("EEE d. M.", "d.", ViewHierarchyNode.JsonKeys.Y));
            put("cy", Formats.mapOf("EEE, d MMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("da", Formats.mapOf("EEE d. MMM", "d.", ViewHierarchyNode.JsonKeys.Y));
            put("de", Formats.mapOf("EEE, d. MMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("de_AT", Formats.mapOf("EEE, d. MMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("de_CH", Formats.mapOf("EEE, d. MMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("el", Formats.mapOf("EEE, d MMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("en", Formats.mapOf("EEE, MMM d", "d", ViewHierarchyNode.JsonKeys.Y));
            put("en_AU", Formats.mapOf("EEE, d MMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("en_CA", Formats.mapOf("EEE, MMM d", "d", ViewHierarchyNode.JsonKeys.Y));
            put("en_GB", Formats.mapOf("EEE, d MMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("en_IE", Formats.mapOf("EEE, d MMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("en_IN", Formats.mapOf("EEE, d MMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("en_SG", Formats.mapOf("EEE, d MMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("en_US", Formats.mapOf("EEE, MMM d", "d", ViewHierarchyNode.JsonKeys.Y));
            put("en_ZA", Formats.mapOf("EEE, dd MMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("es", Formats.mapOf("EEE, d MMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("es_419", Formats.mapOf("EEE, d MMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("es_ES", Formats.mapOf("EEE, d MMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("es_MX", Formats.mapOf("EEE d 'de' MMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("es_US", Formats.mapOf("EEE, d 'de' MMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("et", Formats.mapOf("EEE, d. MMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("eu", Formats.mapOf("MMM d, EEE", "d", ViewHierarchyNode.JsonKeys.Y));
            put("fa", Formats.mapOf("EEE d LLL", "d", ViewHierarchyNode.JsonKeys.Y));
            put("fi", Formats.mapOf("EEE d. MMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("fil", Formats.mapOf("EEE, MMM d", "d", ViewHierarchyNode.JsonKeys.Y));
            put("fr", Formats.mapOf("EEE d MMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("fr_CA", Formats.mapOf("EEE d MMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("ga", Formats.mapOf("EEE d MMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("gl", Formats.mapOf("EEE, d 'de' MMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("gsw", Formats.mapOf("EEE d. MMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("gu", Formats.mapOf("EEE, d MMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("haw", Formats.mapOf("EEE, d MMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("he", Formats.mapOf("EEE, d בMMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("hi", Formats.mapOf("EEE, d MMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("hr", Formats.mapOf("EEE, d. MMM", "d.", "y."));
            put("hu", Formats.mapOf("MMM d., EEE", "d", "y."));
            put("hy", Formats.mapOf("d MMM, EEE", "d", ViewHierarchyNode.JsonKeys.Y));
            put("id", Formats.mapOf("EEE, d MMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("in", Formats.mapOf("EEE, d MMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("is", Formats.mapOf("EEE, d. MMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("it", Formats.mapOf("EEE d MMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("iw", Formats.mapOf("EEE, d בMMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("ja", Formats.mapOf("M月d日 EEE", "d日", "y年"));
            put("ka", Formats.mapOf("EEE, d MMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("kk", Formats.mapOf("d MMM, EEE", "d", ViewHierarchyNode.JsonKeys.Y));
            put("km", Formats.mapOf("EEE d MMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("kn", Formats.mapOf("EEE, d MMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("ko", Formats.mapOf("MMM d일 EEE", "d일", "y년"));
            put("ky", Formats.mapOf("d-MMM, EEE", "d", ViewHierarchyNode.JsonKeys.Y));
            put("lb", Formats.mapOf("EEE d MMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("ln", Formats.mapOf("EEE d MMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("lo", Formats.mapOf("EEE d MMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("lt", Formats.mapOf("MM-dd, EEE", "dd", ViewHierarchyNode.JsonKeys.Y));
            put("lv", Formats.mapOf("EEE, d. MMM", "d", "y. 'g'."));
            put("mk", Formats.mapOf("EEE, d MMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("ml", Formats.mapOf("MMM d, EEE", "d", ViewHierarchyNode.JsonKeys.Y));
            put("mn", Formats.mapOf("MMM'ын' d. EEE", "d", ViewHierarchyNode.JsonKeys.Y));
            put("mo", Formats.mapOf("EEE, d MMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("mr", Formats.mapOf("EEE, d MMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("ms", Formats.mapOf("EEE, d MMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("mt", Formats.mapOf("EEE, d 'ta'’ MMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("my", Formats.mapOf("MMM d၊ EEE", "d", ViewHierarchyNode.JsonKeys.Y));
            put("nb", Formats.mapOf("EEE d. MMM", "d.", ViewHierarchyNode.JsonKeys.Y));
            put("ne", Formats.mapOf("MMM d, EEE", "d", ViewHierarchyNode.JsonKeys.Y));
            put("nl", Formats.mapOf("EEE d MMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("nn", Formats.mapOf("EEE d. MMM", "d.", ViewHierarchyNode.JsonKeys.Y));
            put("no", Formats.mapOf("EEE d. MMM", "d.", ViewHierarchyNode.JsonKeys.Y));
            put("no_NO", Formats.mapOf("EEE d. MMM", "d.", ViewHierarchyNode.JsonKeys.Y));
            put("or", Formats.mapOf("EEE, MMM d", "d", ViewHierarchyNode.JsonKeys.Y));
            put("pa", Formats.mapOf("EEE, d MMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("pl", Formats.mapOf("EEE, d MMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("pt", Formats.mapOf("EEE, d 'de' MMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("pt_BR", Formats.mapOf("EEE, d 'de' MMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("pt_PT", Formats.mapOf("EEE, d/MM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("ro", Formats.mapOf("EEE, d MMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("ru", Formats.mapOf("ccc, d MMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("sh", Formats.mapOf("EEE d. MMM", "d", "y."));
            put("si", Formats.mapOf("MMM d EEE", "d", ViewHierarchyNode.JsonKeys.Y));
            put("sk", Formats.mapOf("EEE d. M.", "d.", ViewHierarchyNode.JsonKeys.Y));
            put("sl", Formats.mapOf("EEE, d. MMM", "d.", ViewHierarchyNode.JsonKeys.Y));
            put("sq", Formats.mapOf("EEE, d MMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("sr", Formats.mapOf("EEE d. MMM", "d", "y."));
            put("sr_Latn", Formats.mapOf("EEE d. MMM", "d", "y."));
            put("sv", Formats.mapOf("EEE d MMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("sw", Formats.mapOf("EEE, d MMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("ta", Formats.mapOf("MMM d, EEE", "d", ViewHierarchyNode.JsonKeys.Y));
            put("te", Formats.mapOf("d MMM, EEE", "d", ViewHierarchyNode.JsonKeys.Y));
            put("th", Formats.mapOf("EEE d MMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("tl", Formats.mapOf("EEE, MMM d", "d", ViewHierarchyNode.JsonKeys.Y));
            put("tr", Formats.mapOf("d MMMM EEE", "d", ViewHierarchyNode.JsonKeys.Y));
            put("uk", Formats.mapOf("EEE, d MMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("ur", Formats.mapOf("EEE، d MMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("uz", Formats.mapOf("EEE, d-MMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("vi", Formats.mapOf("EEE, d MMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("zh", Formats.mapOf("M月d日EEE", "d日", "y年"));
            put("zh_CN", Formats.mapOf("M月d日EEE", "d日", "y年"));
            put("zh_HK", Formats.mapOf("M月d日EEE", "d日", "y年"));
            put("zh_TW", Formats.mapOf("M月d日 EEE", "d日", "y年"));
            put("zu", Formats.mapOf("EEE, MMM d", "d", ViewHierarchyNode.JsonKeys.Y));
            put("en_ISO", Formats.mapOf("EEE, MMM d", "d", ViewHierarchyNode.JsonKeys.Y));
            put("en_MY", Formats.mapOf("EEE, d MMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("fr_CH", Formats.mapOf("EEE d MMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("it_CH", Formats.mapOf("EEE d MMM", "d", ViewHierarchyNode.JsonKeys.Y));
            put("ps", Formats.mapOf("MMM d, EEE", "d", ViewHierarchyNode.JsonKeys.Y));
        }
    };

    public enum Format {
        MMMEd,
        d,
        y
    }

    public static String get(String str, Format format) throws FormatNotFoundException {
        try {
            return map.get(str).get(format).replaceAll(",", "");
        } catch (NullPointerException unused) {
            throw new FormatNotFoundException();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static EnumMap<Format, String> mapOf(String str, String str2, String str3) {
        return new EnumMap<Format, String>(Format.class, str, str2, str3) { // from class: com.henninghall.date_picker.Formats.2
            final /* synthetic */ String val$MMMed;
            final /* synthetic */ String val$d;
            final /* synthetic */ String val$y;

            {
                this.val$MMMed = str;
                this.val$d = str2;
                this.val$y = str3;
                put((AnonymousClass2) Format.MMMEd, (Format) str);
                put((AnonymousClass2) Format.d, (Format) str2);
                put((AnonymousClass2) Format.y, (Format) str3);
            }
        };
    }

    static class FormatNotFoundException extends Exception {
        FormatNotFoundException() {
        }
    }
}

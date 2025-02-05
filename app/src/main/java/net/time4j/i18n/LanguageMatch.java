package net.time4j.i18n;

import java.util.Locale;

/* loaded from: classes3.dex */
public enum LanguageMatch {
    tl("fil"),
    no("nb"),
    in("id"),
    iw("he");

    static final LanguageMatch[] ALIASES = values();
    private final String alias;

    LanguageMatch(String str) {
        this.alias = str;
    }

    public static String getAlias(Locale locale) {
        String language = locale.getLanguage();
        if (language.equals("no") && locale.getVariant().equals("NY") && locale.getCountry().equals("NO")) {
            return "nn";
        }
        for (LanguageMatch languageMatch : ALIASES) {
            if (language.equals(languageMatch.name())) {
                return languageMatch.alias;
            }
        }
        return language;
    }
}

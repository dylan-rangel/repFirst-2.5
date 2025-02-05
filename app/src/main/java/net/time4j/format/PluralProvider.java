package net.time4j.format;

import java.util.Locale;

/* loaded from: classes3.dex */
public interface PluralProvider {
    PluralRules load(Locale locale, NumberType numberType);
}

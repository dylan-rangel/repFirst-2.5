package net.time4j.calendar.bahai;

import java.util.Locale;
import net.time4j.format.CalendarText;
import net.time4j.format.TextAccessor;

/* loaded from: classes3.dex */
public enum BadiMonth implements BadiDivision {
    BAHA,
    JALAL,
    JAMAL,
    AZAMAT,
    NUR,
    RAHMAT,
    KALIMAT,
    KAMAL,
    ASMA,
    IZZAT,
    MASHIYYAT,
    ILM,
    QUDRAT,
    QAWL,
    MASAIL,
    SHARAF,
    SULTAN,
    MULK,
    ALA;

    public static BadiMonth valueOf(int i) {
        if (i < 1 || i > 19) {
            throw new IllegalArgumentException("Out of range: " + i);
        }
        return values()[i - 1];
    }

    public int getValue() {
        return ordinal() + 1;
    }

    public String getDisplayName(Locale locale) {
        return accessor(locale, FormattedContent.TRANSCRIPTION).print(this);
    }

    public String getMeaning(Locale locale) {
        return accessor(locale, FormattedContent.MEANING).print(this);
    }

    private static TextAccessor accessor(Locale locale, FormattedContent formattedContent) {
        return CalendarText.getInstance("bahai", locale).getTextForms("M", BadiMonth.class, formattedContent.variant());
    }
}

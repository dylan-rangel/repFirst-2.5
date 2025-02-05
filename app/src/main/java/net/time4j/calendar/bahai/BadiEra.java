package net.time4j.calendar.bahai;

import androidx.exifinterface.media.ExifInterface;
import java.util.Locale;
import net.time4j.engine.CalendarEra;
import net.time4j.format.CalendarText;
import net.time4j.format.TextAccessor;
import net.time4j.format.TextWidth;

/* loaded from: classes3.dex */
public enum BadiEra implements CalendarEra {
    BAHAI;

    public String getDisplayName(Locale locale, TextWidth textWidth) {
        return accessor(locale, textWidth).print(this);
    }

    /* renamed from: net.time4j.calendar.bahai.BadiEra$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$time4j$format$TextWidth;

        static {
            int[] iArr = new int[TextWidth.values().length];
            $SwitchMap$net$time4j$format$TextWidth = iArr;
            try {
                iArr[TextWidth.WIDE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$net$time4j$format$TextWidth[TextWidth.ABBREVIATED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$net$time4j$format$TextWidth[TextWidth.SHORT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$net$time4j$format$TextWidth[TextWidth.NARROW.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    static TextAccessor accessor(Locale locale, TextWidth textWidth) {
        String str;
        int i = AnonymousClass1.$SwitchMap$net$time4j$format$TextWidth[textWidth.ordinal()];
        if (i == 1) {
            str = "w";
        } else if (i == 2 || i == 3) {
            str = "a";
        } else {
            if (i != 4) {
                throw new UnsupportedOperationException(textWidth.name());
            }
            str = "n";
        }
        return CalendarText.getInstance("bahai", locale).getTextForms(ExifInterface.LONGITUDE_EAST, BadiEra.class, str);
    }
}

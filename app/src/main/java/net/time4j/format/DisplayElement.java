package net.time4j.format;

import java.lang.Comparable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import net.time4j.engine.BasicElement;

/* loaded from: classes3.dex */
public abstract class DisplayElement<V extends Comparable<V>> extends BasicElement<V> {
    private static final Map<String, String> OTHER_DISPLAY_KEYS;

    static {
        HashMap hashMap = new HashMap();
        hashMap.put("YEAR_OF_DISPLAY", "L_year");
        hashMap.put("MONTH_AS_NUMBER", "L_month");
        hashMap.put("HOUR_FROM_0_TO_24", "L_hour");
        hashMap.put("DAY_OF_MONTH", "L_day");
        hashMap.put("DAY_OF_DIVISION", "L_day");
        OTHER_DISPLAY_KEYS = Collections.unmodifiableMap(hashMap);
    }

    protected DisplayElement(String str) {
        super(str);
    }

    @Override // net.time4j.engine.BasicElement, net.time4j.engine.ChronoElement
    public String getDisplayName(Locale locale) {
        String str;
        switch (getSymbol()) {
            case 'E':
            case 'e':
                str = "L_weekday";
                break;
            case 'G':
                str = "L_era";
                break;
            case 'H':
            case 'K':
            case 'h':
            case 'k':
                str = "L_hour";
                break;
            case 'M':
                str = "L_month";
                break;
            case 'Q':
                str = "L_quarter";
                break;
            case 'W':
            case 'w':
                str = "L_week";
                break;
            case 'Y':
            case 'u':
            case 'y':
                str = "L_year";
                break;
            case 'd':
                str = "L_day";
                break;
            case 'm':
                str = "L_minute";
                break;
            case 's':
                str = "L_second";
                break;
            default:
                String name = name();
                String str2 = OTHER_DISPLAY_KEYS.get(name);
                if (str2 != null) {
                    str = str2;
                    break;
                } else {
                    return name;
                }
        }
        String str3 = CalendarText.getIsoInstance(locale).getTextForms().get(str);
        return str3 == null ? name() : str3;
    }
}

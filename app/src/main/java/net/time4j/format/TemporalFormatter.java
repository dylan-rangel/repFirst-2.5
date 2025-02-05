package net.time4j.format;

import java.text.ParseException;
import java.util.Locale;
import net.time4j.engine.AttributeQuery;
import net.time4j.tz.TZID;

/* loaded from: classes3.dex */
public interface TemporalFormatter<T> {
    String format(T t);

    AttributeQuery getAttributes();

    T parse(CharSequence charSequence) throws ParseException;

    T parse(CharSequence charSequence, RawValues rawValues) throws ParseException;

    String print(T t);

    TemporalFormatter<T> with(Locale locale);

    TemporalFormatter<T> with(Leniency leniency);

    TemporalFormatter<T> withTimezone(String str);

    TemporalFormatter<T> withTimezone(TZID tzid);
}

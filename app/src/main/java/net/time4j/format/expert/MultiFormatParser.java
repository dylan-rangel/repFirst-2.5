package net.time4j.format.expert;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import net.time4j.engine.AttributeQuery;
import net.time4j.engine.ChronoEntity;

/* loaded from: classes3.dex */
public final class MultiFormatParser<T extends ChronoEntity<T>> implements ChronoParser<T> {
    private final ChronoFormatter<T>[] parsers;

    private MultiFormatParser(ChronoFormatter<T>[] chronoFormatterArr) {
        this.parsers = chronoFormatterArr;
        for (ChronoFormatter<T> chronoFormatter : chronoFormatterArr) {
            if (chronoFormatter == null) {
                throw new NullPointerException("Null format cannot be set.");
            }
        }
    }

    @SafeVarargs
    public static <T extends ChronoEntity<T>> MultiFormatParser<T> of(ChronoFormatter<T>... chronoFormatterArr) {
        return new MultiFormatParser<>((ChronoFormatter[]) Arrays.copyOf(chronoFormatterArr, chronoFormatterArr.length));
    }

    public static <T extends ChronoEntity<T>> MultiFormatParser<T> of(List<ChronoFormatter<T>> list) {
        return new MultiFormatParser<>((ChronoFormatter[]) list.toArray((ChronoFormatter[]) Array.newInstance((Class<?>) ChronoFormatter.class, list.size())));
    }

    public T parse(CharSequence charSequence) throws ParseException {
        ParseLog parseLog = new ParseLog();
        for (int i = 0; i < this.parsers.length; i++) {
            parseLog.reset();
            parseLog.setPosition(0);
            T parse = this.parsers[i].parse(charSequence, parseLog);
            if (parse != null && !parseLog.isError() && (this.parsers[i].isToleratingTrailingChars() || parseLog.getPosition() == charSequence.length())) {
                return parse;
            }
        }
        throw new ParseException("Not matched by any format: " + ((Object) charSequence), charSequence.length());
    }

    public T parse(CharSequence charSequence, ParseLog parseLog) {
        int position = parseLog.getPosition();
        for (int i = 0; i < this.parsers.length; i++) {
            parseLog.reset();
            parseLog.setPosition(position);
            T parse = this.parsers[i].parse(charSequence, parseLog);
            if (parse != null && !parseLog.isError()) {
                return parse;
            }
        }
        parseLog.setError(parseLog.getErrorIndex(), "Not matched by any format: " + ((Object) charSequence));
        return null;
    }

    @Override // net.time4j.format.expert.ChronoParser
    public T parse(CharSequence charSequence, ParseLog parseLog, AttributeQuery attributeQuery) {
        int position = parseLog.getPosition();
        for (int i = 0; i < this.parsers.length; i++) {
            parseLog.reset();
            parseLog.setPosition(position);
            T parse = this.parsers[i].parse(charSequence, parseLog, attributeQuery);
            if (parse != null && !parseLog.isError()) {
                return parse;
            }
        }
        parseLog.setError(parseLog.getErrorIndex(), "Not matched by any format: " + ((Object) charSequence));
        return null;
    }
}

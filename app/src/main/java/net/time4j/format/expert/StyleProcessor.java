package net.time4j.format.expert;

import java.io.IOException;
import java.util.Locale;
import java.util.Set;
import net.time4j.Moment;
import net.time4j.PlainDate;
import net.time4j.PlainTime;
import net.time4j.PlainTimestamp;
import net.time4j.engine.AttributeQuery;
import net.time4j.engine.ChronoDisplay;
import net.time4j.engine.ChronoElement;
import net.time4j.engine.Chronology;
import net.time4j.engine.DisplayStyle;
import net.time4j.format.Attributes;
import net.time4j.format.CalendarText;
import net.time4j.format.DisplayMode;
import net.time4j.format.LocalizedPatternSupport;
import net.time4j.tz.TZID;
import net.time4j.tz.Timezone;
import net.time4j.tz.TransitionStrategy;

/* loaded from: classes3.dex */
final class StyleProcessor<T> implements FormatProcessor<T> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final DisplayStyle dateStyle;
    private final ChronoFormatter<T> formatter;
    private final DisplayStyle timeStyle;

    @Override // net.time4j.format.expert.FormatProcessor
    public ChronoElement<T> getElement() {
        return null;
    }

    @Override // net.time4j.format.expert.FormatProcessor
    public boolean isNumerical() {
        return false;
    }

    @Override // net.time4j.format.expert.FormatProcessor
    public FormatProcessor<T> withElement(ChronoElement<T> chronoElement) {
        return this;
    }

    StyleProcessor(DisplayStyle displayStyle, DisplayStyle displayStyle2) {
        this(null, displayStyle, displayStyle2);
    }

    private StyleProcessor(ChronoFormatter<T> chronoFormatter, DisplayStyle displayStyle, DisplayStyle displayStyle2) {
        if (displayStyle == null || displayStyle2 == null) {
            throw new NullPointerException("Missing display style.");
        }
        this.dateStyle = displayStyle;
        this.timeStyle = displayStyle2;
        this.formatter = chronoFormatter;
    }

    @Override // net.time4j.format.expert.FormatProcessor
    public int print(ChronoDisplay chronoDisplay, Appendable appendable, AttributeQuery attributeQuery, Set<ElementPosition> set, boolean z) throws IOException {
        Set<ElementPosition> print = this.formatter.print(chronoDisplay, appendable, attributeQuery, set != null);
        if (set == null) {
            return Integer.MAX_VALUE;
        }
        set.addAll(print);
        return Integer.MAX_VALUE;
    }

    @Override // net.time4j.format.expert.FormatProcessor
    public void parse(CharSequence charSequence, ParseLog parseLog, AttributeQuery attributeQuery, ParsedEntity<?> parsedEntity, boolean z) {
        ChronoFormatter<T> createFormatter;
        if (z) {
            createFormatter = this.formatter;
        } else {
            AttributeQuery attributes = this.formatter.getAttributes();
            TransitionStrategy transitionStrategy = (TransitionStrategy) attributeQuery.get(Attributes.TRANSITION_STRATEGY, attributes.get(Attributes.TRANSITION_STRATEGY, Timezone.DEFAULT_CONFLICT_STRATEGY));
            TZID tzid = (TZID) attributeQuery.get(Attributes.TIMEZONE_ID, attributes.get(Attributes.TIMEZONE_ID, null));
            createFormatter = createFormatter(this.formatter.getChronology(), this.dateStyle, this.timeStyle, (Locale) attributeQuery.get(Attributes.LANGUAGE, this.formatter.getLocale()), ((Boolean) attributeQuery.get(Attributes.FOUR_DIGIT_YEAR, Boolean.FALSE)).booleanValue(), tzid != null ? Timezone.of(tzid).with(transitionStrategy) : null);
        }
        T parse = createFormatter.parse(charSequence, parseLog, attributeQuery);
        if (parseLog.isError() || parse == null) {
            return;
        }
        parsedEntity.setResult(parse);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof StyleProcessor) {
            StyleProcessor styleProcessor = (StyleProcessor) obj;
            if (this.dateStyle.equals(styleProcessor.dateStyle) && this.timeStyle.equals(styleProcessor.timeStyle)) {
                ChronoFormatter<T> chronoFormatter = this.formatter;
                if (chronoFormatter == null) {
                    return styleProcessor.formatter == null;
                }
                return chronoFormatter.equals(styleProcessor.formatter);
            }
        }
        return false;
    }

    public int hashCode() {
        ChronoFormatter<T> chronoFormatter = this.formatter;
        if (chronoFormatter == null) {
            return 0;
        }
        return chronoFormatter.hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(64);
        sb.append(getClass().getName());
        sb.append("[date-style=");
        sb.append(this.dateStyle);
        sb.append(",time-style=");
        sb.append(this.timeStyle);
        sb.append(",delegate=");
        sb.append(this.formatter);
        sb.append(']');
        return sb.toString();
    }

    @Override // net.time4j.format.expert.FormatProcessor
    public FormatProcessor<T> quickPath(ChronoFormatter<?> chronoFormatter, AttributeQuery attributeQuery, int i) {
        TransitionStrategy transitionStrategy = (TransitionStrategy) attributeQuery.get(Attributes.TRANSITION_STRATEGY, Timezone.DEFAULT_CONFLICT_STRATEGY);
        TZID tzid = (TZID) attributeQuery.get(Attributes.TIMEZONE_ID, null);
        return new StyleProcessor(createFormatter(chronoFormatter.getChronology(), this.dateStyle, this.timeStyle, (Locale) attributeQuery.get(Attributes.LANGUAGE, Locale.ROOT), ((Boolean) attributeQuery.get(Attributes.FOUR_DIGIT_YEAR, Boolean.FALSE)).booleanValue(), tzid != null ? Timezone.of(tzid).with(transitionStrategy) : null), this.dateStyle, this.timeStyle);
    }

    String getGeneratedPattern() {
        ChronoFormatter<T> chronoFormatter = this.formatter;
        return chronoFormatter == null ? "" : chronoFormatter.getPattern();
    }

    private static <T> ChronoFormatter<T> createFormatter(Chronology<?> chronology, DisplayStyle displayStyle, DisplayStyle displayStyle2, Locale locale, boolean z, Timezone timezone) {
        String formatPattern;
        if (chronology.equals(PlainDate.axis())) {
            formatPattern = CalendarText.patternForDate((DisplayMode) displayStyle, locale);
        } else if (chronology.equals(PlainTime.axis())) {
            formatPattern = CalendarText.patternForTime((DisplayMode) displayStyle2, locale);
        } else if (chronology.equals(PlainTimestamp.axis())) {
            formatPattern = CalendarText.patternForTimestamp((DisplayMode) displayStyle, (DisplayMode) displayStyle2, locale);
        } else if (chronology.equals(Moment.axis())) {
            formatPattern = CalendarText.patternForMoment((DisplayMode) displayStyle, (DisplayMode) displayStyle2, locale);
        } else if (LocalizedPatternSupport.class.isAssignableFrom(chronology.getChronoType())) {
            formatPattern = chronology.getFormatPattern(displayStyle, locale);
        } else {
            throw new UnsupportedOperationException("Localized format patterns not available: " + chronology);
        }
        if (z && formatPattern.contains("yy") && !formatPattern.contains("yyy")) {
            formatPattern = formatPattern.replace("yy", "yyyy");
        }
        ChronoFormatter<T> ofPattern = ChronoFormatter.ofPattern(formatPattern, PatternType.CLDR, locale, chronology);
        return timezone != null ? ofPattern.with(timezone) : ofPattern;
    }
}

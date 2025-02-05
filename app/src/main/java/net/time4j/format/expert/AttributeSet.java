package net.time4j.format.expert;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import net.time4j.base.ResourceLoader;
import net.time4j.engine.AttributeKey;
import net.time4j.engine.AttributeQuery;
import net.time4j.engine.ChronoCondition;
import net.time4j.engine.ChronoDisplay;
import net.time4j.engine.Chronology;
import net.time4j.format.Attributes;
import net.time4j.format.Leniency;
import net.time4j.format.NumberSymbolProvider;
import net.time4j.format.NumberSystem;
import net.time4j.format.OutputContext;
import net.time4j.format.TextWidth;
import net.time4j.i18n.LanguageMatch;
import net.time4j.i18n.SymbolProviderSPI;
import org.apache.commons.lang3.ClassUtils;

/* loaded from: classes3.dex */
final class AttributeSet implements AttributeQuery {
    private static final NumericalSymbols DEFAULT_NUMERICAL_SYMBOLS;
    private static final char ISO_DECIMAL_SEPARATOR;
    private static final NumberSymbolProvider NUMBER_SYMBOLS;
    private static final ConcurrentMap<String, NumericalSymbols> NUMBER_SYMBOL_CACHE;
    private final Attributes attributes;
    private final Map<String, Object> internals;
    private final int level;
    private final Locale locale;
    private final ChronoCondition<ChronoDisplay> printCondition;
    private final int section;
    static final AttributeKey<String> PLUS_SIGN = Attributes.createKey("PLUS_SIGN", String.class);
    static final AttributeKey<String> MINUS_SIGN = Attributes.createKey("MINUS_SIGN", String.class);

    static {
        NumberSymbolProvider numberSymbolProvider = null;
        int i = 0;
        for (NumberSymbolProvider numberSymbolProvider2 : ResourceLoader.getInstance().services(NumberSymbolProvider.class)) {
            int length = numberSymbolProvider2.getAvailableLocales().length;
            if (length > i) {
                numberSymbolProvider = numberSymbolProvider2;
                i = length;
            }
        }
        if (numberSymbolProvider == null) {
            numberSymbolProvider = SymbolProviderSPI.INSTANCE;
        }
        NUMBER_SYMBOLS = numberSymbolProvider;
        char c = Boolean.getBoolean("net.time4j.format.iso.decimal.dot") ? ClassUtils.PACKAGE_SEPARATOR_CHAR : ',';
        ISO_DECIMAL_SEPARATOR = c;
        NUMBER_SYMBOL_CACHE = new ConcurrentHashMap();
        DEFAULT_NUMERICAL_SYMBOLS = new NumericalSymbols(NumberSystem.ARABIC, '0', c, "+", "-");
    }

    AttributeSet(Attributes attributes, Locale locale) {
        this(attributes, locale, 0, 0, null);
    }

    AttributeSet(Attributes attributes, Locale locale, int i, int i2, ChronoCondition<ChronoDisplay> chronoCondition) {
        if (attributes == null) {
            throw new NullPointerException("Missing format attributes.");
        }
        this.attributes = attributes;
        this.locale = locale == null ? Locale.ROOT : locale;
        this.level = i;
        this.section = i2;
        this.printCondition = chronoCondition;
        this.internals = Collections.emptyMap();
    }

    private AttributeSet(Attributes attributes, Locale locale, int i, int i2, ChronoCondition<ChronoDisplay> chronoCondition, Map<String, Object> map) {
        if (attributes == null) {
            throw new NullPointerException("Missing format attributes.");
        }
        this.attributes = attributes;
        this.locale = locale == null ? Locale.ROOT : locale;
        this.level = i;
        this.section = i2;
        this.printCondition = chronoCondition;
        this.internals = Collections.unmodifiableMap(map);
    }

    @Override // net.time4j.engine.AttributeQuery
    public boolean contains(AttributeKey<?> attributeKey) {
        if (this.internals.containsKey(attributeKey.name())) {
            return true;
        }
        return this.attributes.contains(attributeKey);
    }

    @Override // net.time4j.engine.AttributeQuery
    public <A> A get(AttributeKey<A> attributeKey) {
        if (this.internals.containsKey(attributeKey.name())) {
            return attributeKey.type().cast(this.internals.get(attributeKey.name()));
        }
        return (A) this.attributes.get(attributeKey);
    }

    @Override // net.time4j.engine.AttributeQuery
    public <A> A get(AttributeKey<A> attributeKey, A a) {
        if (this.internals.containsKey(attributeKey.name())) {
            return attributeKey.type().cast(this.internals.get(attributeKey.name()));
        }
        return (A) this.attributes.get(attributeKey, a);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AttributeSet)) {
            return false;
        }
        AttributeSet attributeSet = (AttributeSet) obj;
        return this.attributes.equals(attributeSet.attributes) && this.locale.equals(attributeSet.locale) && this.level == attributeSet.level && this.section == attributeSet.section && isEqual(this.printCondition, attributeSet.printCondition) && this.internals.equals(attributeSet.internals);
    }

    public int hashCode() {
        return (this.attributes.hashCode() * 7) + (this.internals.hashCode() * 37);
    }

    public String toString() {
        return getClass().getName() + "[attributes=" + this.attributes + ",locale=" + this.locale + ",level=" + this.level + ",section=" + this.section + ",print-condition=" + this.printCondition + ",other=" + this.internals + ']';
    }

    Attributes getAttributes() {
        return this.attributes;
    }

    Locale getLocale() {
        return this.locale;
    }

    int getLevel() {
        return this.level;
    }

    int getSection() {
        return this.section;
    }

    ChronoCondition<ChronoDisplay> getCondition() {
        return this.printCondition;
    }

    static AttributeSet createDefaults(Chronology<?> chronology, Attributes attributes, Locale locale) {
        Attributes.Builder builder = new Attributes.Builder(chronology);
        builder.set((AttributeKey<AttributeKey<Leniency>>) Attributes.LENIENCY, (AttributeKey<Leniency>) Leniency.SMART);
        builder.set((AttributeKey<AttributeKey<TextWidth>>) Attributes.TEXT_WIDTH, (AttributeKey<TextWidth>) TextWidth.WIDE);
        builder.set((AttributeKey<AttributeKey<OutputContext>>) Attributes.OUTPUT_CONTEXT, (AttributeKey<OutputContext>) OutputContext.FORMAT);
        builder.set(Attributes.PAD_CHAR, ' ');
        builder.setAll(attributes);
        return new AttributeSet(builder.build(), locale).withLocale(locale);
    }

    AttributeSet withAttributes(Attributes attributes) {
        return new AttributeSet(attributes, this.locale, this.level, this.section, this.printCondition, this.internals);
    }

    <A> AttributeSet withInternal(AttributeKey<A> attributeKey, A a) {
        HashMap hashMap = new HashMap(this.internals);
        if (a == null) {
            hashMap.remove(attributeKey.name());
        } else {
            hashMap.put(attributeKey.name(), a);
        }
        return new AttributeSet(this.attributes, this.locale, this.level, this.section, this.printCondition, hashMap);
    }

    AttributeSet withLocale(Locale locale) {
        String str;
        String str2;
        Attributes.Builder builder = new Attributes.Builder();
        builder.setAll(this.attributes);
        String alias = LanguageMatch.getAlias(locale);
        String country = locale.getCountry();
        if (alias.isEmpty() && country.isEmpty()) {
            locale = Locale.ROOT;
            builder.set((AttributeKey<AttributeKey<NumberSystem>>) Attributes.NUMBER_SYSTEM, (AttributeKey<NumberSystem>) NumberSystem.ARABIC);
            builder.set(Attributes.DECIMAL_SEPARATOR, ISO_DECIMAL_SEPARATOR);
            str = "+";
            str2 = "-";
        } else {
            if (!country.isEmpty()) {
                alias = alias + "_" + country;
            }
            NumericalSymbols numericalSymbols = NUMBER_SYMBOL_CACHE.get(alias);
            if (numericalSymbols == null) {
                try {
                    NumberSymbolProvider numberSymbolProvider = NUMBER_SYMBOLS;
                    numericalSymbols = new NumericalSymbols(numberSymbolProvider.getDefaultNumberSystem(locale), numberSymbolProvider.getZeroDigit(locale), numberSymbolProvider.getDecimalSeparator(locale), numberSymbolProvider.getPlusSign(locale), numberSymbolProvider.getMinusSign(locale));
                } catch (RuntimeException unused) {
                    numericalSymbols = DEFAULT_NUMERICAL_SYMBOLS;
                }
                NumericalSymbols putIfAbsent = NUMBER_SYMBOL_CACHE.putIfAbsent(alias, numericalSymbols);
                if (putIfAbsent != null) {
                    numericalSymbols = putIfAbsent;
                }
            }
            builder.set((AttributeKey<AttributeKey<NumberSystem>>) Attributes.NUMBER_SYSTEM, (AttributeKey<NumberSystem>) numericalSymbols.numsys);
            builder.set(Attributes.ZERO_DIGIT, numericalSymbols.zeroDigit);
            builder.set(Attributes.DECIMAL_SEPARATOR, numericalSymbols.decimalSeparator);
            str = numericalSymbols.plus;
            str2 = numericalSymbols.minus;
        }
        Locale locale2 = locale;
        builder.setLanguage(locale2);
        HashMap hashMap = new HashMap(this.internals);
        hashMap.put(PLUS_SIGN.name(), str);
        hashMap.put(MINUS_SIGN.name(), str2);
        return new AttributeSet(builder.build(), locale2, this.level, this.section, this.printCondition, hashMap);
    }

    static AttributeSet merge(AttributeSet attributeSet, AttributeSet attributeSet2) {
        HashMap hashMap = new HashMap();
        hashMap.putAll(attributeSet2.internals);
        hashMap.putAll(attributeSet.internals);
        return new AttributeSet(new Attributes.Builder().setAll(attributeSet2.attributes).setAll(attributeSet.attributes).build(), Locale.ROOT, 0, 0, null, hashMap).withLocale(attributeSet.locale);
    }

    private static boolean isEqual(Object obj, Object obj2) {
        if (obj == null) {
            return obj2 == null;
        }
        return obj.equals(obj2);
    }

    private static class NumericalSymbols {
        private final char decimalSeparator;
        private final String minus;
        private final NumberSystem numsys;
        private final String plus;
        private final char zeroDigit;

        NumericalSymbols(NumberSystem numberSystem, char c, char c2, String str, String str2) {
            this.numsys = numberSystem;
            this.zeroDigit = c;
            this.decimalSeparator = c2;
            this.plus = str;
            this.minus = str2;
        }
    }
}

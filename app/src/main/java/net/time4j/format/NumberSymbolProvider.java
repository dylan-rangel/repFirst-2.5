package net.time4j.format;

import java.text.DecimalFormatSymbols;
import java.util.Locale;

/* loaded from: classes3.dex */
public interface NumberSymbolProvider {
    public static final NumberSymbolProvider DEFAULT = new NumberSymbolProvider() { // from class: net.time4j.format.NumberSymbolProvider.1
        @Override // net.time4j.format.NumberSymbolProvider
        public Locale[] getAvailableLocales() {
            return DecimalFormatSymbols.getAvailableLocales();
        }

        @Override // net.time4j.format.NumberSymbolProvider
        public char getZeroDigit(Locale locale) {
            return getSymbols(locale).getZeroDigit();
        }

        @Override // net.time4j.format.NumberSymbolProvider
        public char getDecimalSeparator(Locale locale) {
            return getSymbols(locale).getDecimalSeparator();
        }

        @Override // net.time4j.format.NumberSymbolProvider
        public String getPlusSign(Locale locale) {
            return locale.getLanguage().equals("ar") ? "\u200f+" : String.valueOf('+');
        }

        @Override // net.time4j.format.NumberSymbolProvider
        public String getMinusSign(Locale locale) {
            return locale.getLanguage().equals("ar") ? "\u200f-" : String.valueOf(getSymbols(locale).getMinusSign());
        }

        @Override // net.time4j.format.NumberSymbolProvider
        public NumberSystem getDefaultNumberSystem(Locale locale) {
            return NumberSystem.ARABIC;
        }

        private DecimalFormatSymbols getSymbols(Locale locale) {
            return DecimalFormatSymbols.getInstance(locale);
        }
    };

    Locale[] getAvailableLocales();

    char getDecimalSeparator(Locale locale);

    NumberSystem getDefaultNumberSystem(Locale locale);

    String getMinusSign(Locale locale);

    String getPlusSign(Locale locale);

    char getZeroDigit(Locale locale);
}

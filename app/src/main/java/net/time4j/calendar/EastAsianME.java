package net.time4j.calendar;

import java.io.IOException;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.Locale;
import net.time4j.Month;
import net.time4j.engine.AttributeQuery;
import net.time4j.engine.ChronoDisplay;
import net.time4j.engine.ChronoException;
import net.time4j.format.Attributes;
import net.time4j.format.CalendarText;
import net.time4j.format.NumberSystem;
import net.time4j.format.OutputContext;
import net.time4j.format.TextAccessor;
import net.time4j.format.TextElement;
import net.time4j.format.TextWidth;
import net.time4j.format.internal.DualFormatElement;

/* loaded from: classes3.dex */
class EastAsianME implements TextElement<EastAsianMonth>, Serializable {
    static final EastAsianME SINGLETON_EA = new EastAsianME();
    private static final long serialVersionUID = -5874268477318061153L;

    @Override // net.time4j.engine.ChronoElement
    public char getSymbol() {
        return 'M';
    }

    @Override // net.time4j.engine.ChronoElement
    public boolean isDateElement() {
        return true;
    }

    @Override // net.time4j.engine.ChronoElement
    public boolean isLenient() {
        return false;
    }

    @Override // net.time4j.engine.ChronoElement
    public boolean isTimeElement() {
        return false;
    }

    @Override // net.time4j.engine.ChronoElement
    public String name() {
        return "MONTH_OF_YEAR";
    }

    EastAsianME() {
    }

    @Override // net.time4j.engine.ChronoElement
    public Class<EastAsianMonth> getType() {
        return EastAsianMonth.class;
    }

    @Override // java.util.Comparator
    public int compare(ChronoDisplay chronoDisplay, ChronoDisplay chronoDisplay2) {
        return ((EastAsianMonth) chronoDisplay.get(this)).compareTo((EastAsianMonth) chronoDisplay2.get(this));
    }

    @Override // net.time4j.engine.ChronoElement
    public EastAsianMonth getDefaultMinimum() {
        return EastAsianMonth.valueOf(1);
    }

    @Override // net.time4j.engine.ChronoElement
    public EastAsianMonth getDefaultMaximum() {
        return EastAsianMonth.valueOf(12);
    }

    @Override // net.time4j.engine.ChronoElement
    public String getDisplayName(Locale locale) {
        String str = CalendarText.getIsoInstance(locale).getTextForms().get("L_month");
        return str == null ? name() : str;
    }

    protected Object readResolve() throws ObjectStreamException {
        return SINGLETON_EA;
    }

    @Override // net.time4j.format.TextElement
    public void print(ChronoDisplay chronoDisplay, Appendable appendable, AttributeQuery attributeQuery) throws IOException, ChronoException {
        TextAccessor stdMonths;
        Locale locale = (Locale) attributeQuery.get(Attributes.LANGUAGE, Locale.ROOT);
        EastAsianMonth eastAsianMonth = (EastAsianMonth) chronoDisplay.get(this);
        if (attributeQuery.contains(DualFormatElement.COUNT_OF_PATTERN_SYMBOLS)) {
            appendable.append(eastAsianMonth.getDisplayName(locale, (NumberSystem) attributeQuery.get(Attributes.NUMBER_SYSTEM, NumberSystem.ARABIC), attributeQuery));
            return;
        }
        TextWidth textWidth = (TextWidth) attributeQuery.get(Attributes.TEXT_WIDTH, TextWidth.WIDE);
        OutputContext outputContext = (OutputContext) attributeQuery.get(Attributes.OUTPUT_CONTEXT, OutputContext.FORMAT);
        if (eastAsianMonth.isLeap()) {
            stdMonths = CalendarText.getInstance("chinese", locale).getLeapMonths(textWidth, outputContext);
        } else {
            stdMonths = CalendarText.getInstance("chinese", locale).getStdMonths(textWidth, outputContext);
        }
        appendable.append(stdMonths.print(Month.valueOf(eastAsianMonth.getNumber())));
    }

    /* JADX WARN: Removed duplicated region for block: B:61:0x0147  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x014b  */
    @Override // net.time4j.format.TextElement
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public net.time4j.calendar.EastAsianMonth parse(java.lang.CharSequence r19, java.text.ParsePosition r20, net.time4j.engine.AttributeQuery r21) {
        /*
            Method dump skipped, instructions count: 348
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: net.time4j.calendar.EastAsianME.parse(java.lang.CharSequence, java.text.ParsePosition, net.time4j.engine.AttributeQuery):net.time4j.calendar.EastAsianMonth");
    }
}

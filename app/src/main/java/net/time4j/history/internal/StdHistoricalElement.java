package net.time4j.history.internal;

import java.io.ObjectStreamException;
import net.time4j.engine.ChronoElement;
import net.time4j.format.DisplayElement;

/* loaded from: classes3.dex */
public class StdHistoricalElement extends DisplayElement<Integer> {
    public static final ChronoElement<Integer> YEAR_OF_DISPLAY = new StdHistoricalElement("YEAR_OF_DISPLAY", 0, 1, 9999);
    private static final long serialVersionUID = 1;
    private final transient Integer defaultMax;
    private final transient Integer defaultMin;
    private final transient char symbol;

    @Override // net.time4j.engine.ChronoElement
    public boolean isDateElement() {
        return true;
    }

    @Override // net.time4j.engine.BasicElement
    protected boolean isSingleton() {
        return true;
    }

    @Override // net.time4j.engine.ChronoElement
    public boolean isTimeElement() {
        return false;
    }

    protected StdHistoricalElement(String str, char c, int i, int i2) {
        super(str);
        this.symbol = c;
        this.defaultMin = Integer.valueOf(i);
        this.defaultMax = Integer.valueOf(i2);
    }

    @Override // net.time4j.engine.ChronoElement
    public final Class<Integer> getType() {
        return Integer.class;
    }

    @Override // net.time4j.engine.BasicElement, net.time4j.engine.ChronoElement
    public char getSymbol() {
        return this.symbol;
    }

    @Override // net.time4j.engine.ChronoElement
    public Integer getDefaultMinimum() {
        return this.defaultMin;
    }

    @Override // net.time4j.engine.ChronoElement
    public Integer getDefaultMaximum() {
        return this.defaultMax;
    }

    private Object readResolve() throws ObjectStreamException {
        return YEAR_OF_DISPLAY;
    }
}

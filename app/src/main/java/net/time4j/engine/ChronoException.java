package net.time4j.engine;

/* loaded from: classes3.dex */
public class ChronoException extends RuntimeException {
    private static final long serialVersionUID = -6646794951280971956L;

    public ChronoException(String str) {
        super(str);
    }

    public ChronoException(String str, Exception exc) {
        super(str, exc);
    }
}

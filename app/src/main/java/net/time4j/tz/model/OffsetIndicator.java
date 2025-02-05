package net.time4j.tz.model;

/* loaded from: classes3.dex */
public enum OffsetIndicator {
    UTC_TIME { // from class: net.time4j.tz.model.OffsetIndicator.1
        @Override // net.time4j.tz.model.OffsetIndicator
        public char getSymbol() {
            return 'u';
        }
    },
    STANDARD_TIME { // from class: net.time4j.tz.model.OffsetIndicator.2
        @Override // net.time4j.tz.model.OffsetIndicator
        public char getSymbol() {
            return 's';
        }
    },
    WALL_TIME { // from class: net.time4j.tz.model.OffsetIndicator.3
        @Override // net.time4j.tz.model.OffsetIndicator
        public char getSymbol() {
            return 'w';
        }
    };

    static final OffsetIndicator[] VALUES = values();

    public static OffsetIndicator parseSymbol(char c) {
        if (c != 'g') {
            if (c == 's') {
                return STANDARD_TIME;
            }
            if (c != 'u') {
                if (c == 'w') {
                    return WALL_TIME;
                }
                if (c != 'z') {
                    throw new IllegalArgumentException("Unknown offset indicator: " + c);
                }
            }
        }
        return UTC_TIME;
    }

    public char getSymbol() {
        throw new AbstractMethodError();
    }
}

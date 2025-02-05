package net.time4j.history.internal;

/* loaded from: classes3.dex */
public enum HistoricVariant {
    PROLEPTIC_JULIAN,
    PROLEPTIC_GREGORIAN,
    SWEDEN,
    INTRODUCTION_ON_1582_10_15,
    SINGLE_CUTOVER_DATE,
    PROLEPTIC_BYZANTINE;

    /* renamed from: net.time4j.history.internal.HistoricVariant$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$time4j$history$internal$HistoricVariant;

        static {
            int[] iArr = new int[HistoricVariant.values().length];
            $SwitchMap$net$time4j$history$internal$HistoricVariant = iArr;
            try {
                iArr[HistoricVariant.PROLEPTIC_JULIAN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$net$time4j$history$internal$HistoricVariant[HistoricVariant.PROLEPTIC_GREGORIAN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$net$time4j$history$internal$HistoricVariant[HistoricVariant.SWEDEN.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$net$time4j$history$internal$HistoricVariant[HistoricVariant.INTRODUCTION_ON_1582_10_15.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$net$time4j$history$internal$HistoricVariant[HistoricVariant.PROLEPTIC_BYZANTINE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public int getSerialValue() {
        int i = AnonymousClass1.$SwitchMap$net$time4j$history$internal$HistoricVariant[ordinal()];
        if (i == 1) {
            return 2;
        }
        if (i == 2) {
            return 1;
        }
        if (i == 3) {
            return 4;
        }
        if (i != 4) {
            return i != 5 ? 0 : 3;
        }
        return 7;
    }
}

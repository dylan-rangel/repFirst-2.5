package net.time4j.calendar.hindu;

/* loaded from: classes3.dex */
public enum HinduRule {
    ORISSA { // from class: net.time4j.calendar.hindu.HinduRule.1
        @Override // net.time4j.calendar.hindu.HinduRule
        HinduEra getDefaultEra() {
            return HinduEra.SAKA;
        }
    },
    TAMIL { // from class: net.time4j.calendar.hindu.HinduRule.2
        @Override // net.time4j.calendar.hindu.HinduRule
        HinduEra getDefaultEra() {
            return HinduEra.SAKA;
        }
    },
    MALAYALI { // from class: net.time4j.calendar.hindu.HinduRule.3
        @Override // net.time4j.calendar.hindu.HinduRule
        HinduEra getDefaultEra() {
            return HinduEra.KOLLAM;
        }
    },
    MADRAS { // from class: net.time4j.calendar.hindu.HinduRule.4
        @Override // net.time4j.calendar.hindu.HinduRule
        HinduEra getDefaultEra() {
            return HinduEra.SAKA;
        }
    },
    AMANTA { // from class: net.time4j.calendar.hindu.HinduRule.5
        @Override // net.time4j.calendar.hindu.HinduRule
        HinduEra getDefaultEra() {
            return HinduEra.VIKRAMA;
        }
    },
    AMANTA_ASHADHA { // from class: net.time4j.calendar.hindu.HinduRule.6
        @Override // net.time4j.calendar.hindu.HinduRule
        HinduEra getDefaultEra() {
            return HinduEra.VIKRAMA;
        }
    },
    AMANTA_KARTIKA { // from class: net.time4j.calendar.hindu.HinduRule.7
        @Override // net.time4j.calendar.hindu.HinduRule
        HinduEra getDefaultEra() {
            return HinduEra.VIKRAMA;
        }
    },
    PURNIMANTA { // from class: net.time4j.calendar.hindu.HinduRule.8
        @Override // net.time4j.calendar.hindu.HinduRule
        HinduEra getDefaultEra() {
            return HinduEra.VIKRAMA;
        }
    };

    abstract HinduEra getDefaultEra();

    public HinduVariant variant() {
        return new HinduVariant(this, getDefaultEra());
    }
}

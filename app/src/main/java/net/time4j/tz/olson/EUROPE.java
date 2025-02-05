package net.time4j.tz.olson;

/* loaded from: classes3.dex */
public enum EUROPE implements StdZoneIdentifier {
    AMSTERDAM("Amsterdam", "NL"),
    ANDORRA("Andorra", "AD"),
    ATHENS("Athens", "GR"),
    BELGRADE("Belgrade", "RS"),
    BERLIN("Berlin", "DE"),
    BRATISLAVA("Bratislava", "SK"),
    BRUSSELS("Brussels", "BE"),
    BUCHAREST("Bucharest", "RO"),
    BUDAPEST("Budapest", "HU"),
    CHISINAU("Chisinau", "MD"),
    COPENHAGEN("Copenhagen", "DK"),
    DUBLIN("Dublin", "IE"),
    GIBRALTAR("Gibraltar", "GI"),
    GUERNSEY("Guernsey", "GG"),
    HELSINKI("Helsinki", "FI"),
    ISLE_OF_MAN("Isle_of_Man", "IM"),
    ISTANBUL("Istanbul", "TR"),
    JERSEY("Jersey", "JE"),
    KALININGRAD("Kaliningrad", "RU"),
    KIEV("Kiev", "UA"),
    LISBON("Lisbon", "PT"),
    LJUBLJANA("Ljubljana", "SI"),
    LONDON("London", "GB"),
    LUXEMBOURG("Luxembourg", "LU"),
    MADRID("Madrid", "ES"),
    MALTA("Malta", "MT"),
    MARIEHAMN("Mariehamn", "AX"),
    MINSK("Minsk", "BY"),
    MONACO("Monaco", "MC"),
    MOSCOW("Moscow", "RU"),
    OSLO("Oslo", "NO"),
    PARIS("Paris", "FR"),
    PODGORICA("Podgorica", "ME"),
    PRAGUE("Prague", "CZ"),
    RIGA("Riga", "LV"),
    ROME("Rome", "IT"),
    SAMARA("Samara", "RU"),
    SAN_MARINO("San_Marino", "SM"),
    SARAJEVO("Sarajevo", "BA"),
    SIMFEROPOL("Simferopol", "UA"),
    SOFIA("Sofia", "BG"),
    SKOPJE("Skopje", "MK"),
    STOCKHOLM("Stockholm", "SE"),
    TALLINN("Tallinn", "EE"),
    TIRANE("Tirane", "AL"),
    UZHGOROD("Uzhgorod", "UA"),
    VADUZ("Vaduz", "LI"),
    VATICAN("Vatican", "VA"),
    VIENNA("Vienna", "AT"),
    VILNIUS("Vilnius", "LT"),
    VOLGOGRAD("Volgograd", "RU"),
    WARSAW("Warsaw", "PL"),
    ZAGREB("Zagreb", "HR"),
    ZAPOROZHYE("Zaporozhye", "UA"),
    ZURICH("Zurich", "CH");

    private final String city;
    private final String country;
    private final String id;

    @Override // net.time4j.tz.olson.StdZoneIdentifier
    public String getRegion() {
        return "Europe";
    }

    EUROPE(String str, String str2) {
        this.id = "Europe/" + str;
        this.city = str;
        this.country = str2;
    }

    @Override // net.time4j.tz.TZID
    public String canonical() {
        return this.id;
    }

    @Override // net.time4j.tz.olson.StdZoneIdentifier
    public String getCity() {
        return this.city;
    }

    @Override // net.time4j.tz.olson.StdZoneIdentifier
    public String getCountry() {
        return this.country;
    }
}

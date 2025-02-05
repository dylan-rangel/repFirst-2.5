package net.time4j.tz.olson;

/* loaded from: classes3.dex */
public enum AFRICA implements StdZoneIdentifier {
    ABIDJAN("Abidjan", "CI"),
    ACCRA("Accra", "GH"),
    ADDIS_ABABA("Addis_Ababa", "ET"),
    ALGIERS("Algiers", "DZ"),
    ASMARA("Asmara", "ER"),
    BAMAKO("Bamako", "ML"),
    BANGUI("Bangui", "CF"),
    BANJUL("Banjul", "GM"),
    BISSAU("Bissau", "GW"),
    BLANTYRE("Blantyre", "MW"),
    BRAZZAVILLE("Brazzaville", "CG"),
    BUJUMBURA("Bujumbura", "BI"),
    CAIRO("Cairo", "EG"),
    CASABLANCA("Casablanca", "MA"),
    CEUTA("Ceuta", "ES"),
    CONAKRY("Conakry", "GN"),
    DAKAR("Dakar", "SN"),
    DAR_ES_SALAAM("Dar_es_Salaam", "TZ"),
    DJIBOUTI("Djibouti", "DJ"),
    DOUALA("Douala", "CM"),
    EL_AAIUN("El_Aaiun", "EH"),
    FREETOWN("Freetown", "SL"),
    GABORONE("Gaborone", "BW"),
    HARARE("Harare", "ZW"),
    JOHANNESBURG("Johannesburg", "ZA"),
    JUBA("Juba", "SS"),
    KAMPALA("Kampala", "UG"),
    KHARTOUM("Khartoum", "SD"),
    KIGALI("Kigali", "RW"),
    KINSHASA("Kinshasa", "CD"),
    LAGOS("Lagos", "NG"),
    LIBREVILLE("Libreville", "GA"),
    LOME("Lome", "TG"),
    LUANDA("Luanda", "AO"),
    LUBUMBASHI("Lubumbashi", "CD"),
    LUSAKA("Lusaka", "ZM"),
    MALABO("Malabo", "GQ"),
    MAPUTO("Maputo", "MZ"),
    MASERU("Maseru", "LS"),
    MBABANE("Mbabane", "SZ"),
    MOGADISHU("Mogadishu", "SO"),
    MONROVIA("Monrovia", "LR"),
    NAIROBI("Nairobi", "KE"),
    NDJAMENA("Ndjamena", "TD"),
    NIAMEY("Niamey", "NE"),
    NOUAKCHOTT("Nouakchott", "MR"),
    OUAGADOUGOU("Ouagadougou", "BF"),
    PORTO_NOVO("Porto-Novo", "BJ"),
    SAO_TOME("Sao_Tome", "ST"),
    TRIPOLI("Tripoli", "LY"),
    TUNIS("Tunis", "TN"),
    WINDHOEK("Windhoek", "NA");

    private final String city;
    private final String country;
    private final String id;

    @Override // net.time4j.tz.olson.StdZoneIdentifier
    public String getRegion() {
        return "Africa";
    }

    AFRICA(String str, String str2) {
        this.id = "Africa/" + str;
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

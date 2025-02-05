package net.time4j.tz.olson;

/* loaded from: classes3.dex */
public enum AMERICA implements StdZoneIdentifier {
    ADAK("Adak", "US"),
    ANCHORAGE("Anchorage", "US"),
    ANGUILLA("Anguilla", "AI"),
    ANTIGUA("Antigua", "AG"),
    ARAGUAINA("Araguaina", "BR"),
    ARUBA("Aruba", "AW"),
    ASUNCION("Asuncion", "PY"),
    ATIKOKAN("Atikokan", "CA"),
    BAHIA("Bahia", "BR"),
    BAHIA_BANDERAS("Bahia_Banderas", "MX"),
    BARBADOS("Barbados", "BB"),
    BELEM("Belem", "BR"),
    BELIZE("Belize", "BZ"),
    BLANC_SABLON("Blanc-Sablon", "CA"),
    BOA_VISTA("Boa_Vista", "BR"),
    BOGOTA("Bogota", "CO"),
    BOISE("Boise", "US"),
    CAMBRIDGE_BAY("Cambridge_Bay", "CA"),
    CAMPO_GRANDE("Campo_Grande", "BR"),
    CANCUN("Cancun", "MX"),
    CARACAS("Caracas", "VE"),
    CAYENNE("Cayenne", "GF"),
    CAYMAN("Cayman", "KY"),
    CHICAGO("Chicago", "US"),
    CHIHUAHUA("Chihuahua", "MX"),
    COSTA_RICA("Costa_Rica", "CR"),
    CRESTON("Creston", "CA"),
    CUIABA("Cuiaba", "BR"),
    CURACAO("Curacao", "CW"),
    DANMARKSHAVN("Danmarkshavn", "GL"),
    DAWSON("Dawson", "CA"),
    DAWSON_CREEK("Dawson_Creek", "CA"),
    DENVER("Denver", "US"),
    DETROIT("Detroit", "US"),
    DOMINICA("Dominica", "DM"),
    EDMONTON("Edmonton", "CA"),
    EIRUNEPE("Eirunepe", "BR"),
    EL_SALVADOR("El_Salvador", "SV"),
    FORTALEZA("Fortaleza", "BR"),
    GLACE_BAY("Glace_Bay", "CA"),
    GODTHAB("Godthab", "GL"),
    GOOSE_BAY("Goose_Bay", "CA"),
    GRAND_TURK("Grand_Turk", "TC"),
    GRENADA("Grenada", "GD"),
    GUADELOUPE("Guadeloupe", "GP"),
    GUATEMALA("Guatemala", "GT"),
    GUAYAQUIL("Guayaquil", "EC"),
    GUYANA("Guyana", "GY"),
    HALIFAX("Halifax", "CA"),
    HAVANA("Havana", "CU"),
    HERMOSILLO("Hermosillo", "MX"),
    INUVIK("Inuvik", "CA"),
    IQALUIT("Iqaluit", "CA"),
    JAMAICA("Jamaica", "JM"),
    JUNEAU("Juneau", "US"),
    LA_PAZ("La_Paz", "BO"),
    LIMA("Lima", "PE"),
    LOS_ANGELES("Los_Angeles", "US"),
    LOWER_PRINCES("Lower_Princes", "SX"),
    KRALENDIJK("Kralendijk", "BQ"),
    MACEIO("Maceio", "BR"),
    MANAGUA("Managua", "NI"),
    MANAUS("Manaus", "BR"),
    MARIGOT("Marigot", "MF"),
    MARTINIQUE("Martinique", "MQ"),
    MATAMOROS("Matamoros", "MX"),
    MAZATLAN("Mazatlan", "MX"),
    MENOMINEE("Menominee", "US"),
    MERIDA("Merida", "MX"),
    METLAKATLA("Metlakatla", "US"),
    MEXICO_CITY("Mexico_City", "MX"),
    MIQUELON("Miquelon", "PM"),
    MONCTON("Moncton", "CA"),
    MONTERREY("Monterrey", "MX"),
    MONTEVIDEO("Montevideo", "UY"),
    MONTREAL("Montreal", "CA"),
    MONTSERRAT("Montserrat", "MS"),
    NASSAU("Nassau", "BS"),
    NEW_YORK("New_York", "US"),
    NIPIGON("Nipigon", "CA"),
    NOME("Nome", "US"),
    NORONHA("Noronha", "BR"),
    OJINAGA("Ojinaga", "MX"),
    PANAMA("Panama", "PA"),
    PANGNIRTUNG("Pangnirtung", "CA"),
    PARAMARIBO("Paramaribo", "SR"),
    PHOENIX("Phoenix", "US"),
    PORT_OF_SPAIN("Port_of_Spain", "TT"),
    PORT_AU_PRINCE("Port-au-Prince", "HT"),
    PORTO_VELHO("Porto_Velho", "BR"),
    PUERTO_RICO("Puerto_Rico", "PR"),
    RAINY_RIVER("Rainy_River", "CA"),
    RANKIN_INLET("Rankin_Inlet", "CA"),
    RECIFE("Recife", "BR"),
    REGINA("Regina", "CA"),
    RESOLUTE("Resolute", "CA"),
    RIO_BRANCO("Rio_Branco", "BR"),
    SANTA_ISABEL("Santa_Isabel", "MX"),
    SANTAREM("Santarem", "BR"),
    SANTIAGO("Santiago", "CL"),
    SANTO_DOMINGO("Santo_Domingo", "DO"),
    SAO_PAULO("Sao_Paulo", "BR"),
    SCORESBYSUND("Scoresbysund", "GL"),
    SITKA("Sitka", "US"),
    ST_BARTHELEMY("St_Barthelemy", "BL"),
    ST_JOHNS("St_Johns", "CA"),
    ST_KITTS("St_Kitts", "KN"),
    ST_LUCIA("St_Lucia", "LC"),
    ST_THOMAS("St_Thomas", "VI"),
    ST_VINCENT("St_Vincent", "VC"),
    SWIFT_CURRENT("Swift_Current", "CA"),
    TEGUCIGALPA("Tegucigalpa", "HN"),
    THULE("Thule", "GL"),
    THUNDER_BAY("Thunder_Bay", "CA"),
    TIJUANA("Tijuana", "MX"),
    TORONTO("Toronto", "CA"),
    TORTOLA("Tortola", "VG"),
    VANCOUVER("Vancouver", "CA"),
    WHITEHORSE("Whitehorse", "CA"),
    WINNIPEG("Winnipeg", "CA"),
    YAKUTAT("Yakutat", "US"),
    YELLOWKNIFE("Yellowknife", "CA");

    private final String city;
    private final String country;
    private final String id;

    @Override // net.time4j.tz.olson.StdZoneIdentifier
    public String getRegion() {
        return "America";
    }

    AMERICA(String str, String str2) {
        this.id = "America/" + str;
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

    public enum ARGENTINA implements StdZoneIdentifier {
        BUENOS_AIRES("Buenos_Aires"),
        CATAMARCA("Catamarca"),
        CORDOBA("Cordoba"),
        JUJUY("Jujuy"),
        LA_RIOJA("La_Rioja"),
        MENDOZA("Mendoza"),
        RIO_GALLEGOS("Rio_Gallegos"),
        SALTA("Salta"),
        SAN_JUAN("San_Juan"),
        SAN_LUIS("San_Luis"),
        TUCUMAN("Tucuman"),
        USHUAIA("Ushuaia");

        private final String city;
        private final String id;

        @Override // net.time4j.tz.olson.StdZoneIdentifier
        public String getCountry() {
            return "AR";
        }

        @Override // net.time4j.tz.olson.StdZoneIdentifier
        public String getRegion() {
            return "America/Argentina";
        }

        ARGENTINA(String str) {
            this.id = "America/Argentina/" + str;
            this.city = str;
        }

        @Override // net.time4j.tz.TZID
        public String canonical() {
            return this.id;
        }

        @Override // net.time4j.tz.olson.StdZoneIdentifier
        public String getCity() {
            return this.city;
        }
    }

    public enum INDIANA implements StdZoneIdentifier {
        INDIANAPOLIS("Indianapolis"),
        KNOX("Knox"),
        MARENGO("Marengo"),
        PETERSBURG("Petersburg"),
        TELL_CITY("Tell_City"),
        VEVAY("Vevay"),
        VINCENNES("Vincennes"),
        WINAMAC("Winamac");

        private final String city;
        private final String id;

        @Override // net.time4j.tz.olson.StdZoneIdentifier
        public String getCountry() {
            return "US";
        }

        @Override // net.time4j.tz.olson.StdZoneIdentifier
        public String getRegion() {
            return "America/Indiana";
        }

        INDIANA(String str) {
            this.id = "America/Indiana/" + str;
            this.city = str;
        }

        @Override // net.time4j.tz.TZID
        public String canonical() {
            return this.id;
        }

        @Override // net.time4j.tz.olson.StdZoneIdentifier
        public String getCity() {
            return this.city;
        }
    }

    public enum KENTUCKY implements StdZoneIdentifier {
        LOUISVILLE("Louisville"),
        MONTICELLO("Monticello");

        private final String city;
        private final String id;

        @Override // net.time4j.tz.olson.StdZoneIdentifier
        public String getCountry() {
            return "US";
        }

        @Override // net.time4j.tz.olson.StdZoneIdentifier
        public String getRegion() {
            return "America/Kentucky";
        }

        KENTUCKY(String str) {
            this.id = "America/Kentucky/" + str;
            this.city = str;
        }

        @Override // net.time4j.tz.TZID
        public String canonical() {
            return this.id;
        }

        @Override // net.time4j.tz.olson.StdZoneIdentifier
        public String getCity() {
            return this.city;
        }
    }

    public enum NORTH_DAKOTA implements StdZoneIdentifier {
        BEULAH("Beulah"),
        CENTER("Center"),
        NEW_SALEM("New_Salem");

        private final String city;
        private final String id;

        @Override // net.time4j.tz.olson.StdZoneIdentifier
        public String getCountry() {
            return "US";
        }

        @Override // net.time4j.tz.olson.StdZoneIdentifier
        public String getRegion() {
            return "America/North_Dakota";
        }

        NORTH_DAKOTA(String str) {
            this.id = "America/North_Dakota/" + str;
            this.city = str;
        }

        @Override // net.time4j.tz.TZID
        public String canonical() {
            return this.id;
        }

        @Override // net.time4j.tz.olson.StdZoneIdentifier
        public String getCity() {
            return this.city;
        }
    }
}

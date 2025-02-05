package net.time4j.tz.olson;

/* loaded from: classes3.dex */
public enum ASIA implements StdZoneIdentifier {
    ADEN("Aden", "YE"),
    ALMATY("Almaty", "KZ"),
    AMMAN("Amman", "JO"),
    ANADYR("Anadyr", "RU"),
    AQTAU("Aqtau", "KZ"),
    AQTOBE("Aqtobe", "KZ"),
    ASHGABAT("Ashgabat", "TM"),
    BAGHDAD("Baghdad", "IQ"),
    BAHRAIN("Bahrain", "BH"),
    BAKU("Baku", "AZ"),
    BANGKOK("Bangkok", "TH"),
    BEIRUT("Beirut", "LB"),
    BISHKEK("Bishkek", "KG"),
    BRUNEI("Brunei", "BN"),
    CHOIBALSAN("Choibalsan", "MN"),
    CHONGQING("Chongqing", "CN"),
    COLOMBO("Colombo", "LK"),
    DAMASCUS("Damascus", "SY"),
    DHAKA("Dhaka", "BD"),
    DILI("Dili", "TL"),
    DUBAI("Dubai", "AE"),
    DUSHANBE("Dushanbe", "TJ"),
    GAZA("Gaza", "PS"),
    HARBIN("Harbin", "CN"),
    HEBRON("Hebron", "PS"),
    HO_CHI_MINH("Ho_Chi_Minh", "VN"),
    HONG_KONG("Hong_Kong", "HK"),
    HOVD("Hovd", "MN"),
    IRKUTSK("Irkutsk", "RU"),
    JAKARTA("Jakarta", "ID"),
    JAYAPURA("Jayapura", "ID"),
    JERUSALEM("Jerusalem", "IL"),
    KABUL("Kabul", "AF"),
    KAMCHATKA("Kamchatka", "RU"),
    KARACHI("Karachi", "PK"),
    KASHGAR("Kashgar", "CN"),
    KATHMANDU("Kathmandu", "NP"),
    KHANDYGA("Khandyga", "RU"),
    KOLKATA("Kolkata", "IN"),
    KRASNOYARSK("Krasnoyarsk", "RU"),
    KUALA_LUMPUR("Kuala_Lumpur", "MY"),
    KUCHING("Kuching", "MY"),
    KUWAIT("Kuwait", "KW"),
    MACAU("Macau", "MO"),
    MAGADAN("Magadan", "RU"),
    MAKASSAR("Makassar", "ID"),
    MANILA("Manila", "PH"),
    MUSCAT("Muscat", "OM"),
    NICOSIA("Nicosia", "CY"),
    NOVOKUZNETSK("Novokuznetsk", "RU"),
    NOVOSIBIRSK("Novosibirsk", "RU"),
    OMSK("Omsk", "RU"),
    ORAL("Oral", "KZ"),
    PHNOM_PENH("Phnom_Penh", "KH"),
    PONTIANAK("Pontianak", "ID"),
    PYONGYANG("Pyongyang", "KP"),
    QATAR("Qatar", "QA"),
    QYZYLORDA("Qyzylorda", "KZ"),
    RANGOON("Rangoon", "MM"),
    RIYADH("Riyadh", "SA"),
    SAKHALIN("Sakhalin", "RU"),
    SAMARKAND("Samarkand", "UZ"),
    SEOUL("Seoul", "KR"),
    SHANGHAI("Shanghai", "CN"),
    SINGAPORE("Singapore", "SG"),
    TAIPEI("Taipei", "TW"),
    TASHKENT("Tashkent", "UZ"),
    TBILISI("Tbilisi", "GE"),
    TEHRAN("Tehran", "IR"),
    THIMPHU("Thimphu", "BT"),
    TOKYO("Tokyo", "JP"),
    ULAANBAATAR("Ulaanbaatar", "MN"),
    URUMQI("Urumqi", "CN"),
    UST_NERA("Ust-Nera", "RU"),
    VIENTIANE("Vientiane", "LA"),
    VLADIVOSTOK("Vladivostok", "RU"),
    YAKUTSK("Yakutsk", "RU"),
    YEKATERINBURG("Yekaterinburg", "RU"),
    YEREVAN("Yerevan", "AM");

    private final String city;
    private final String country;
    private final String id;

    @Override // net.time4j.tz.olson.StdZoneIdentifier
    public String getRegion() {
        return "Asia";
    }

    ASIA(String str, String str2) {
        this.id = "Asia/" + str;
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

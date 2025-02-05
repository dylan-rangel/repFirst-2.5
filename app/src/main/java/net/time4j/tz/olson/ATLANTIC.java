package net.time4j.tz.olson;

/* loaded from: classes3.dex */
public enum ATLANTIC implements StdZoneIdentifier {
    AZORES("Azores", "PT"),
    BERMUDA("Bermuda", "BM"),
    CANARY("Canary", "ES"),
    CAPE_VERDE("Cape_Verde", "CV"),
    FAROE("Faroe", "FO"),
    MADEIRA("Madeira", "PT"),
    REYKJAVIK("Reykjavik", "IS"),
    SOUTH_GEORGIA("South_Georgia", "GS"),
    ST_HELENA("St_Helena", "SH"),
    STANLEY("Stanley", "FK");

    private final String city;
    private final String country;
    private final String id;

    @Override // net.time4j.tz.olson.StdZoneIdentifier
    public String getRegion() {
        return "Atlantic";
    }

    ATLANTIC(String str, String str2) {
        this.id = "Atlantic/" + str;
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

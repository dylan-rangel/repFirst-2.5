package net.time4j.tz.olson;

/* loaded from: classes3.dex */
public enum ANTARCTICA implements StdZoneIdentifier {
    CASEY("Casey", "AQ"),
    DAVIS("Davis", "AQ"),
    DUMONTDURVILLE("DumontDUrville", "AQ"),
    MACQUARIE("Macquarie", "AU"),
    MAWSON("Mawson", "AQ"),
    MCMURDO("McMurdo", "AQ"),
    PALMER("Palmer", "AQ"),
    ROTHERA("Rothera", "AQ"),
    SYOWA("Syowa", "AQ"),
    VOSTOK("Vostok", "AQ");

    private final String city;
    private final String country;
    private final String id;

    @Override // net.time4j.tz.olson.StdZoneIdentifier
    public String getRegion() {
        return "Antarctica";
    }

    ANTARCTICA(String str, String str2) {
        this.id = "Antarctica/" + str;
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

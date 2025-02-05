package net.openid.appauth.browser;

/* loaded from: classes3.dex */
public class VersionRange {
    public static final VersionRange ANY_VERSION = new VersionRange(null, null);
    private DelimitedVersion mLowerBound;
    private DelimitedVersion mUpperBound;

    public static VersionRange atLeast(String version) {
        return atLeast(DelimitedVersion.parse(version));
    }

    public static VersionRange atLeast(DelimitedVersion version) {
        return new VersionRange(version, null);
    }

    public static VersionRange atMost(String version) {
        return atMost(DelimitedVersion.parse(version));
    }

    public static VersionRange atMost(DelimitedVersion version) {
        return new VersionRange(null, version);
    }

    public static VersionRange between(String lowerBound, String upperBound) {
        return new VersionRange(DelimitedVersion.parse(lowerBound), DelimitedVersion.parse(upperBound));
    }

    public VersionRange(DelimitedVersion lowerBound, DelimitedVersion upperBound) {
        this.mLowerBound = lowerBound;
        this.mUpperBound = upperBound;
    }

    public boolean matches(String version) {
        return matches(DelimitedVersion.parse(version));
    }

    public boolean matches(DelimitedVersion version) {
        DelimitedVersion delimitedVersion = this.mLowerBound;
        if (delimitedVersion != null && delimitedVersion.compareTo(version) > 0) {
            return false;
        }
        DelimitedVersion delimitedVersion2 = this.mUpperBound;
        return delimitedVersion2 == null || delimitedVersion2.compareTo(version) >= 0;
    }

    public String toString() {
        if (this.mLowerBound == null) {
            if (this.mUpperBound == null) {
                return "any version";
            }
            return this.mUpperBound.toString() + " or lower";
        }
        if (this.mUpperBound != null) {
            return "between " + this.mLowerBound + " and " + this.mUpperBound;
        }
        return this.mLowerBound.toString() + " or higher";
    }
}

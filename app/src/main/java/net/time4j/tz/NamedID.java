package net.time4j.tz;

import java.io.Serializable;

/* loaded from: classes3.dex */
class NamedID implements TZID, Serializable {
    private static final long serialVersionUID = -4889632013137688471L;
    private final String tzid;

    NamedID(String str) {
        this.tzid = str;
    }

    @Override // net.time4j.tz.TZID
    public String canonical() {
        return this.tzid;
    }

    public boolean equals(Object obj) {
        if (obj instanceof NamedID) {
            return this.tzid.equals(((NamedID) obj).tzid);
        }
        return false;
    }

    public int hashCode() {
        return this.tzid.hashCode();
    }

    public String toString() {
        return getClass().getName() + "@" + this.tzid;
    }
}

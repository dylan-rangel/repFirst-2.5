package net.time4j.tz.spi;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import net.time4j.tz.TZID;

/* loaded from: classes3.dex */
final class WinZoneID implements TZID, Serializable {
    private static final long serialVersionUID = -4077231634935102213L;
    private final String id;

    WinZoneID(String str) {
        check(str);
        this.id = str;
    }

    @Override // net.time4j.tz.TZID
    public String canonical() {
        return this.id;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof WinZoneID) {
            return this.id.equals(((WinZoneID) obj).id);
        }
        return false;
    }

    public int hashCode() {
        return this.id.hashCode();
    }

    public String toString() {
        return this.id;
    }

    private static void check(String str) {
        if (str.startsWith("WINDOWS~")) {
            return;
        }
        throw new IllegalArgumentException("Not a windows zone: " + str);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        check(this.id);
    }
}

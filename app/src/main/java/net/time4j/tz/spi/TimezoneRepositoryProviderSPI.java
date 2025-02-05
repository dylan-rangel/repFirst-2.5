package net.time4j.tz.spi;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import net.time4j.PlainDate;
import net.time4j.base.GregorianDate;
import net.time4j.scale.LeapSecondProvider;
import net.time4j.tz.TransitionHistory;
import net.time4j.tz.ZoneModelProvider;
import net.time4j.tz.ZoneNameProvider;

/* loaded from: classes3.dex */
public class TimezoneRepositoryProviderSPI implements ZoneModelProvider, LeapSecondProvider {
    private final Map<String, String> aliases;
    private final Map<String, byte[]> data;
    private final PlainDate expires;
    private final Map<GregorianDate, Integer> leapsecs;
    private final String location;
    private final String version;

    @Override // net.time4j.tz.ZoneModelProvider
    public String getFallback() {
        return "";
    }

    @Override // net.time4j.tz.ZoneModelProvider
    public String getName() {
        return "TZDB";
    }

    @Override // net.time4j.tz.ZoneModelProvider
    public ZoneNameProvider getSpecificZoneNameRepository() {
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:41:0x0214  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0227  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0208 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:52:0x022b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public TimezoneRepositoryProviderSPI() {
        /*
            Method dump skipped, instructions count: 559
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: net.time4j.tz.spi.TimezoneRepositoryProviderSPI.<init>():void");
    }

    @Override // net.time4j.tz.ZoneModelProvider
    public Set<String> getAvailableIDs() {
        return this.data.keySet();
    }

    @Override // net.time4j.tz.ZoneModelProvider
    public Map<String, String> getAliases() {
        return this.aliases;
    }

    @Override // net.time4j.tz.ZoneModelProvider
    public TransitionHistory load(String str) {
        try {
            byte[] bArr = this.data.get(str);
            if (bArr != null) {
                return (TransitionHistory) new ObjectInputStream(new ByteArrayInputStream(bArr)).readObject();
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    @Override // net.time4j.tz.ZoneModelProvider
    public String getLocation() {
        return this.location;
    }

    @Override // net.time4j.tz.ZoneModelProvider
    public String getVersion() {
        return this.version;
    }

    @Override // net.time4j.scale.LeapSecondProvider
    public Map<GregorianDate, Integer> getLeapSecondTable() {
        return Collections.unmodifiableMap(this.leapsecs);
    }

    @Override // net.time4j.scale.LeapSecondProvider
    public boolean supportsNegativeLS() {
        return !this.leapsecs.isEmpty();
    }

    @Override // net.time4j.scale.LeapSecondProvider
    public PlainDate getDateOfEvent(int i, int i2, int i3) {
        return PlainDate.of(i, i2, i3);
    }

    @Override // net.time4j.scale.LeapSecondProvider
    public PlainDate getDateOfExpiration() {
        return this.expires;
    }

    public String toString() {
        return "TZ-REPOSITORY(" + this.version + ")";
    }

    private static void checkMagicLabel(DataInputStream dataInputStream, String str) throws IOException {
        byte readByte = dataInputStream.readByte();
        byte readByte2 = dataInputStream.readByte();
        byte readByte3 = dataInputStream.readByte();
        byte readByte4 = dataInputStream.readByte();
        byte readByte5 = dataInputStream.readByte();
        byte readByte6 = dataInputStream.readByte();
        if (readByte == 116 && readByte2 == 122 && readByte3 == 114 && readByte4 == 101 && readByte5 == 112 && readByte6 == 111) {
            return;
        }
        throw new IOException("Invalid tz-repository: " + str);
    }

    private static Class<?> getReference() {
        if (Boolean.getBoolean("test.environment")) {
            try {
                return Class.forName("net.time4j.tz.spi.RepositoryTest");
            } catch (ClassNotFoundException e) {
                throw new AssertionError(e);
            }
        }
        return TimezoneRepositoryProviderSPI.class;
    }
}

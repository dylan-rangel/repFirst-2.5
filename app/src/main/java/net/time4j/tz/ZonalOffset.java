package net.time4j.tz;

import com.facebook.imagepipeline.common.RotationOptions;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import okhttp3.internal.http2.Http2Connection;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.time.TimeZones;

/* loaded from: classes3.dex */
public final class ZonalOffset implements Comparable<ZonalOffset>, TZID, Serializable {
    private static final BigDecimal DECIMAL_240;
    private static final BigDecimal DECIMAL_3600;
    private static final BigDecimal DECIMAL_60;
    private static final BigDecimal DECIMAL_NEG_180;
    private static final BigDecimal DECIMAL_POS_180;
    private static final BigDecimal MRD;
    private static final ConcurrentMap<Integer, ZonalOffset> OFFSET_CACHE;
    public static final ZonalOffset UTC;
    private static final long serialVersionUID = -1410512619471503090L;
    private final transient int fraction;
    private final transient String name;
    private final transient int total;

    static {
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        OFFSET_CACHE = concurrentHashMap;
        DECIMAL_60 = new BigDecimal(60);
        DECIMAL_3600 = new BigDecimal(3600);
        DECIMAL_NEG_180 = new BigDecimal(-180);
        DECIMAL_POS_180 = new BigDecimal(RotationOptions.ROTATE_180);
        DECIMAL_240 = new BigDecimal(240);
        MRD = new BigDecimal(Http2Connection.DEGRADED_PONG_TIMEOUT_NS);
        ZonalOffset zonalOffset = new ZonalOffset(0, 0);
        UTC = zonalOffset;
        concurrentHashMap.put(0, zonalOffset);
    }

    private ZonalOffset(int i, int i2) {
        if (i2 != 0) {
            if (Math.abs(i2) > 999999999) {
                throw new IllegalArgumentException("Fraction out of range: " + i2);
            }
            if (i < -39600 || i > 39600) {
                throw new IllegalArgumentException("Total seconds out of range while fraction is non-zero: " + i);
            }
            if ((i < 0 && i2 > 0) || (i > 0 && i2 < 0)) {
                throw new IllegalArgumentException("Different signs: offset=" + i + ", fraction=" + i2);
            }
        } else if (i < -64800 || i > 64800) {
            throw new IllegalArgumentException("Total seconds out of range: " + i);
        }
        boolean z = i < 0 || i2 < 0;
        StringBuilder sb = new StringBuilder();
        sb.append(z ? '-' : '+');
        int abs = Math.abs(i);
        int i3 = abs / 3600;
        int i4 = (abs / 60) % 60;
        int i5 = abs % 60;
        if (i3 < 10) {
            sb.append('0');
        }
        sb.append(i3);
        sb.append(':');
        if (i4 < 10) {
            sb.append('0');
        }
        sb.append(i4);
        if (i5 != 0 || i2 != 0) {
            sb.append(':');
            if (i5 < 10) {
                sb.append('0');
            }
            sb.append(i5);
            if (i2 != 0) {
                sb.append(ClassUtils.PACKAGE_SEPARATOR_CHAR);
                String valueOf = String.valueOf(Math.abs(i2));
                int length = 9 - valueOf.length();
                for (int i6 = 0; i6 < length; i6++) {
                    sb.append('0');
                }
                sb.append(valueOf);
            }
        }
        this.name = sb.toString();
        this.total = i;
        this.fraction = i2;
    }

    public static ZonalOffset atLongitude(BigDecimal bigDecimal) {
        if (bigDecimal.compareTo(DECIMAL_POS_180) > 0 || bigDecimal.compareTo(DECIMAL_NEG_180) < 0) {
            throw new IllegalArgumentException("Out of range: " + bigDecimal);
        }
        BigDecimal multiply = bigDecimal.multiply(DECIMAL_240);
        BigDecimal scale = multiply.setScale(0, RoundingMode.DOWN);
        BigDecimal multiply2 = multiply.subtract(scale).setScale(9, RoundingMode.HALF_UP).multiply(MRD);
        int intValueExact = scale.intValueExact();
        int intValueExact2 = multiply2.intValueExact();
        if (intValueExact2 == 0) {
            return ofTotalSeconds(intValueExact);
        }
        if (intValueExact2 == 1000000000) {
            return ofTotalSeconds(intValueExact + 1);
        }
        if (intValueExact2 == -1000000000) {
            return ofTotalSeconds(intValueExact - 1);
        }
        return new ZonalOffset(intValueExact, intValueExact2);
    }

    public static ZonalOffset atLongitude(OffsetSign offsetSign, int i, int i2, double d) {
        if (offsetSign == null) {
            throw new NullPointerException("Missing sign.");
        }
        if (i < 0 || i > 180) {
            throw new IllegalArgumentException("Degrees of longitude out of range (0 <= degrees <= 180).");
        }
        if (i2 < 0 || i2 > 59) {
            throw new IllegalArgumentException("Arc minute out of range (0 <= arcMinutes <= 59).");
        }
        if (Double.compare(d, 0.0d) < 0 || Double.compare(d, 60.0d) >= 0) {
            throw new IllegalArgumentException("Arc second out of range (0.0 <= arcSeconds < 60.0).");
        }
        BigDecimal valueOf = BigDecimal.valueOf(i);
        if (i2 != 0) {
            valueOf = valueOf.add(BigDecimal.valueOf(i2).setScale(15, RoundingMode.UNNECESSARY).divide(DECIMAL_60, RoundingMode.HALF_UP));
        }
        if (d != 0.0d) {
            valueOf = valueOf.add(BigDecimal.valueOf(d).setScale(15, RoundingMode.FLOOR).divide(DECIMAL_3600, RoundingMode.HALF_UP));
        }
        if (offsetSign == OffsetSign.BEHIND_UTC) {
            valueOf = valueOf.negate();
        }
        return atLongitude(valueOf);
    }

    public static ZonalOffset ofHours(OffsetSign offsetSign, int i) {
        return ofHoursMinutes(offsetSign, i, 0);
    }

    public static ZonalOffset ofHoursMinutes(OffsetSign offsetSign, int i, int i2) {
        if (offsetSign == null) {
            throw new NullPointerException("Missing sign.");
        }
        if (i < 0 || i > 18) {
            throw new IllegalArgumentException("Hour part out of range (0 <= hours <= 18) in: " + format(i, i2));
        }
        if (i2 < 0 || i2 > 59) {
            throw new IllegalArgumentException("Minute part out of range (0 <= minutes <= 59) in: " + format(i, i2));
        }
        if (i == 18 && i2 != 0) {
            throw new IllegalArgumentException("Time zone offset out of range (-18:00:00 <= offset <= 18:00:00) in: " + format(i, i2));
        }
        int i3 = (i * 3600) + (i2 * 60);
        if (offsetSign == OffsetSign.BEHIND_UTC) {
            i3 = -i3;
        }
        return ofTotalSeconds(i3);
    }

    public static ZonalOffset ofTotalSeconds(int i) {
        return ofTotalSeconds(i, 0);
    }

    public static ZonalOffset ofTotalSeconds(int i, int i2) {
        if (i2 != 0) {
            return new ZonalOffset(i, i2);
        }
        if (i == 0) {
            return UTC;
        }
        if (i % 900 == 0) {
            Integer valueOf = Integer.valueOf(i);
            ConcurrentMap<Integer, ZonalOffset> concurrentMap = OFFSET_CACHE;
            ZonalOffset zonalOffset = concurrentMap.get(valueOf);
            if (zonalOffset != null) {
                return zonalOffset;
            }
            concurrentMap.putIfAbsent(valueOf, new ZonalOffset(i, 0));
            return concurrentMap.get(valueOf);
        }
        return new ZonalOffset(i, 0);
    }

    public OffsetSign getSign() {
        return (this.total < 0 || this.fraction < 0) ? OffsetSign.BEHIND_UTC : OffsetSign.AHEAD_OF_UTC;
    }

    public int getAbsoluteHours() {
        return Math.abs(this.total) / 3600;
    }

    public int getAbsoluteMinutes() {
        return (Math.abs(this.total) / 60) % 60;
    }

    public int getAbsoluteSeconds() {
        return Math.abs(this.total) % 60;
    }

    public int getIntegralAmount() {
        return this.total;
    }

    public int getFractionalAmount() {
        return this.fraction;
    }

    @Override // java.lang.Comparable
    public int compareTo(ZonalOffset zonalOffset) {
        int i = this.total;
        int i2 = zonalOffset.total;
        if (i < i2) {
            return -1;
        }
        if (i > i2) {
            return 1;
        }
        int i3 = this.fraction - zonalOffset.fraction;
        if (i3 < 0) {
            return -1;
        }
        return i3 == 0 ? 0 : 1;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ZonalOffset)) {
            return false;
        }
        ZonalOffset zonalOffset = (ZonalOffset) obj;
        return this.total == zonalOffset.total && this.fraction == zonalOffset.fraction;
    }

    public int hashCode() {
        return (~this.total) + (this.fraction % 64000);
    }

    public String toString() {
        return this.name;
    }

    @Override // net.time4j.tz.TZID
    public String canonical() {
        if (this.total == 0 && this.fraction == 0) {
            return "Z";
        }
        return "UTC" + this.name;
    }

    public static ZonalOffset parse(String str) {
        return parse(str, true);
    }

    public String getStdFormatPattern(Locale locale) {
        boolean z = this.total == 0 && this.fraction == 0;
        try {
            return Timezone.NAME_PROVIDER.getStdFormatPattern(z, locale);
        } catch (Throwable unused) {
            return z ? TimeZones.GMT_ID : "GMT±hh:mm";
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00d6 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00d7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    static net.time4j.tz.ZonalOffset parse(java.lang.String r11, boolean r12) {
        /*
            Method dump skipped, instructions count: 238
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: net.time4j.tz.ZonalOffset.parse(java.lang.String, boolean):net.time4j.tz.ZonalOffset");
    }

    SingleOffsetTimezone getModel() {
        return SingleOffsetTimezone.of(this);
    }

    private static int parse(String str, int i, int i2) {
        int min = Math.min(str.length() - i, i2);
        int i3 = -1;
        for (int i4 = 0; i4 < min; i4++) {
            char charAt = str.charAt(i + i4);
            if (charAt < '0' || charAt > '9') {
                break;
            }
            i3 = i3 == -1 ? charAt - '0' : (i3 * 10) + (charAt - '0');
        }
        return i3;
    }

    private static String format(int i, int i2) {
        return "[hours=" + i + ",minutes=" + i2 + ']';
    }

    private Object writeReplace() {
        return new SPX(this, 15);
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Serialization proxy required.");
    }
}

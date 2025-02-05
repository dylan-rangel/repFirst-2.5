package io.sentry;

import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.util.Locale;

/* loaded from: classes3.dex */
public interface MeasurementUnit {
    public static final String NONE = "none";

    public enum Duration implements MeasurementUnit {
        NANOSECOND,
        MICROSECOND,
        MILLISECOND,
        SECOND,
        MINUTE,
        HOUR,
        DAY,
        WEEK;

        @Override // io.sentry.MeasurementUnit
        public /* synthetic */ String apiName() {
            return CC.$default$apiName(this);
        }
    }

    public enum Fraction implements MeasurementUnit {
        RATIO,
        PERCENT;

        @Override // io.sentry.MeasurementUnit
        public /* synthetic */ String apiName() {
            return CC.$default$apiName(this);
        }
    }

    public enum Information implements MeasurementUnit {
        BIT,
        BYTE,
        KILOBYTE,
        KIBIBYTE,
        MEGABYTE,
        MEBIBYTE,
        GIGABYTE,
        GIBIBYTE,
        TERABYTE,
        TEBIBYTE,
        PETABYTE,
        PEBIBYTE,
        EXABYTE,
        EXBIBYTE;

        @Override // io.sentry.MeasurementUnit
        public /* synthetic */ String apiName() {
            return CC.$default$apiName(this);
        }
    }

    String apiName();

    String name();

    public static final class Custom implements MeasurementUnit {
        private final String name;

        @Override // io.sentry.MeasurementUnit
        public /* synthetic */ String apiName() {
            return CC.$default$apiName(this);
        }

        public Custom(String str) {
            this.name = str;
        }

        @Override // io.sentry.MeasurementUnit
        public String name() {
            return this.name;
        }
    }

    @SynthesizedClassV2(kind = 7, versionHash = "5e5398f0546d1d7afd62641edb14d82894f11ddc41bce363a0c8d0dac82c9c5a")
    /* renamed from: io.sentry.MeasurementUnit$-CC, reason: invalid class name */
    public final /* synthetic */ class CC {
        public static String $default$apiName(MeasurementUnit _this) {
            return _this.name().toLowerCase(Locale.ROOT);
        }
    }
}

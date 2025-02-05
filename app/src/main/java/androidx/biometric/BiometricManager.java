package androidx.biometric;

import android.content.Context;
import android.os.Build;
import androidx.core.hardware.fingerprint.FingerprintManagerCompat;

/* loaded from: classes.dex */
public class BiometricManager {
    public static final int BIOMETRIC_ERROR_HW_UNAVAILABLE = 1;
    public static final int BIOMETRIC_ERROR_NONE_ENROLLED = 11;
    public static final int BIOMETRIC_ERROR_NO_HARDWARE = 12;
    public static final int BIOMETRIC_SUCCESS = 0;
    private final android.hardware.biometrics.BiometricManager mBiometricManager;
    private final FingerprintManagerCompat mFingerprintManager;

    private static class Api29Impl {
        private Api29Impl() {
        }

        static android.hardware.biometrics.BiometricManager create(Context context) {
            return (android.hardware.biometrics.BiometricManager) context.getSystemService(android.hardware.biometrics.BiometricManager.class);
        }

        static int canAuthenticate(android.hardware.biometrics.BiometricManager biometricManager) {
            return biometricManager.canAuthenticate();
        }
    }

    public static BiometricManager from(Context context) {
        return new BiometricManager(context);
    }

    private BiometricManager(Context context) {
        if (Build.VERSION.SDK_INT >= 29) {
            this.mBiometricManager = Api29Impl.create(context);
            this.mFingerprintManager = null;
        } else {
            this.mBiometricManager = null;
            this.mFingerprintManager = FingerprintManagerCompat.from(context);
        }
    }

    BiometricManager(android.hardware.biometrics.BiometricManager biometricManager) {
        this.mBiometricManager = biometricManager;
        this.mFingerprintManager = null;
    }

    public int canAuthenticate() {
        if (Build.VERSION.SDK_INT >= 29) {
            return Api29Impl.canAuthenticate(this.mBiometricManager);
        }
        if (this.mFingerprintManager.isHardwareDetected()) {
            return !this.mFingerprintManager.hasEnrolledFingerprints() ? 11 : 0;
        }
        return 12;
    }
}

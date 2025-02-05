package com.oblador.keychain;

import android.app.KeyguardManager;
import android.content.Context;
import android.os.Build;
import androidx.biometric.BiometricManager;

/* loaded from: classes2.dex */
public class DeviceAvailability {
    public static boolean isBiometricAuthAvailable(Context context) {
        return BiometricManager.from(context).canAuthenticate() == 0;
    }

    public static boolean isFingerprintAuthAvailable(Context context) {
        return context.getPackageManager().hasSystemFeature("android.hardware.fingerprint");
    }

    public static boolean isFaceAuthAvailable(Context context) {
        return context.getPackageManager().hasSystemFeature("android.hardware.biometrics.face");
    }

    public static boolean isIrisAuthAvailable(Context context) {
        return context.getPackageManager().hasSystemFeature("android.hardware.biometrics.iris");
    }

    public static boolean isPermissionsGranted(Context context) {
        if (Build.VERSION.SDK_INT >= 23 && ((KeyguardManager) context.getSystemService("keyguard")).isKeyguardSecure()) {
            return Build.VERSION.SDK_INT >= 28 ? context.checkSelfPermission("android.permission.USE_BIOMETRIC") == 0 : context.checkSelfPermission("android.permission.USE_FINGERPRINT") == 0;
        }
        return false;
    }

    public static boolean isDeviceSecure(Context context) {
        KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService("keyguard");
        return Build.VERSION.SDK_INT >= 23 && keyguardManager != null && keyguardManager.isDeviceSecure();
    }
}

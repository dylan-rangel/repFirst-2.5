package androidx.biometric;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import androidx.fragment.app.FragmentActivity;
import com.oblador.keychain.KeychainModule;

/* loaded from: classes.dex */
class Utils {
    static boolean isUnknownError(int i) {
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
                return false;
            case 6:
            default:
                return true;
        }
    }

    private Utils() {
    }

    static void launchDeviceCredentialConfirmation(String str, FragmentActivity fragmentActivity, Bundle bundle, Runnable runnable) {
        KeyguardManager keyguardManager;
        CharSequence charSequence;
        if (!(fragmentActivity instanceof DeviceCredentialHandlerActivity)) {
            Log.e(str, "Failed to check device credential. Parent handler not found.");
            return;
        }
        DeviceCredentialHandlerActivity deviceCredentialHandlerActivity = (DeviceCredentialHandlerActivity) fragmentActivity;
        if (Build.VERSION.SDK_INT >= 23) {
            keyguardManager = (KeyguardManager) deviceCredentialHandlerActivity.getSystemService(KeyguardManager.class);
        } else {
            Object systemService = deviceCredentialHandlerActivity.getSystemService("keyguard");
            if (!(systemService instanceof KeyguardManager)) {
                Log.e(str, "Failed to check device credential. KeyguardManager not found.");
                deviceCredentialHandlerActivity.handleDeviceCredentialResult(0);
                return;
            }
            keyguardManager = (KeyguardManager) systemService;
        }
        if (keyguardManager == null) {
            Log.e(str, "Failed to check device credential. KeyguardManager was null.");
            deviceCredentialHandlerActivity.handleDeviceCredentialResult(0);
            return;
        }
        CharSequence charSequence2 = null;
        if (bundle != null) {
            charSequence2 = bundle.getCharSequence(KeychainModule.AuthPromptOptions.TITLE);
            charSequence = bundle.getCharSequence(KeychainModule.AuthPromptOptions.SUBTITLE);
        } else {
            charSequence = null;
        }
        Intent createConfirmDeviceCredentialIntent = keyguardManager.createConfirmDeviceCredentialIntent(charSequence2, charSequence);
        if (createConfirmDeviceCredentialIntent == null) {
            Log.e(str, "Failed to check device credential. Got null intent from Keyguard.");
            deviceCredentialHandlerActivity.handleDeviceCredentialResult(0);
            return;
        }
        DeviceCredentialHandlerBridge deviceCredentialHandlerBridge = DeviceCredentialHandlerBridge.getInstance();
        deviceCredentialHandlerBridge.setConfirmingDeviceCredential(true);
        deviceCredentialHandlerBridge.startIgnoringReset();
        if (runnable != null) {
            runnable.run();
        }
        createConfirmDeviceCredentialIntent.setFlags(134742016);
        deviceCredentialHandlerActivity.startActivityForResult(createConfirmDeviceCredentialIntent, 0);
    }

    static void maybeFinishHandler(FragmentActivity fragmentActivity) {
        if (!(fragmentActivity instanceof DeviceCredentialHandlerActivity) || fragmentActivity.isFinishing()) {
            return;
        }
        fragmentActivity.finish();
    }

    static boolean shouldUseFingerprintForCrypto(Context context, String str, String str2) {
        if (Build.VERSION.SDK_INT != 28) {
            return false;
        }
        return isVendorInList(context, str, R.array.crypto_fingerprint_fallback_vendors) || isModelInPrefixList(context, str2, R.array.crypto_fingerprint_fallback_prefixes);
    }

    static boolean shouldHideFingerprintDialog(Context context, String str) {
        if (Build.VERSION.SDK_INT != 28) {
            return false;
        }
        return isModelInPrefixList(context, str, R.array.hide_fingerprint_instantly_prefixes);
    }

    private static boolean isVendorInList(Context context, String str, int i) {
        if (str == null) {
            return false;
        }
        for (String str2 : context.getResources().getStringArray(i)) {
            if (str.equalsIgnoreCase(str2)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isModelInPrefixList(Context context, String str, int i) {
        if (str == null) {
            return false;
        }
        for (String str2 : context.getResources().getStringArray(i)) {
            if (str.startsWith(str2)) {
                return true;
            }
        }
        return false;
    }

    static boolean isConfirmingDeviceCredential() {
        DeviceCredentialHandlerBridge instanceIfNotNull = DeviceCredentialHandlerBridge.getInstanceIfNotNull();
        return instanceIfNotNull != null && instanceIfNotNull.isConfirmingDeviceCredential();
    }
}

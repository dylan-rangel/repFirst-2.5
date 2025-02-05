package androidx.biometric;

import android.content.DialogInterface;
import android.os.Build;
import androidx.biometric.BiometricPrompt;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public class DeviceCredentialHandlerBridge {
    private static final int IGNORING_NEXT_RESET = 1;
    private static final int IGNORING_RESET = 2;
    private static final int NOT_IGNORING_RESET = 0;
    static final int RESULT_ERROR = 2;
    static final int RESULT_NONE = 0;
    static final int RESULT_SUCCESS = 1;
    private static DeviceCredentialHandlerBridge sInstance;
    private BiometricPrompt.AuthenticationCallback mAuthenticationCallback;
    private BiometricFragment mBiometricFragment;
    private int mClientThemeResId;
    private boolean mConfirmingDeviceCredential;
    private Executor mExecutor;
    private FingerprintDialogFragment mFingerprintDialogFragment;
    private FingerprintHelperFragment mFingerprintHelperFragment;
    private DialogInterface.OnClickListener mOnClickListener;
    private int mDeviceCredentialResult = 0;
    private int mIgnoreResetState = 0;

    private DeviceCredentialHandlerBridge() {
    }

    static DeviceCredentialHandlerBridge getInstance() {
        if (sInstance == null) {
            sInstance = new DeviceCredentialHandlerBridge();
        }
        return sInstance;
    }

    static DeviceCredentialHandlerBridge getInstanceIfNotNull() {
        return sInstance;
    }

    void setClientThemeResId(int i) {
        this.mClientThemeResId = i;
    }

    int getClientThemeResId() {
        return this.mClientThemeResId;
    }

    void setBiometricFragment(BiometricFragment biometricFragment) {
        this.mBiometricFragment = biometricFragment;
    }

    BiometricFragment getBiometricFragment() {
        return this.mBiometricFragment;
    }

    void setFingerprintFragments(FingerprintDialogFragment fingerprintDialogFragment, FingerprintHelperFragment fingerprintHelperFragment) {
        this.mFingerprintDialogFragment = fingerprintDialogFragment;
        this.mFingerprintHelperFragment = fingerprintHelperFragment;
    }

    public FingerprintDialogFragment getFingerprintDialogFragment() {
        return this.mFingerprintDialogFragment;
    }

    public FingerprintHelperFragment getFingerprintHelperFragment() {
        return this.mFingerprintHelperFragment;
    }

    void setCallbacks(Executor executor, DialogInterface.OnClickListener onClickListener, BiometricPrompt.AuthenticationCallback authenticationCallback) {
        this.mExecutor = executor;
        this.mOnClickListener = onClickListener;
        this.mAuthenticationCallback = authenticationCallback;
        if (this.mBiometricFragment != null && Build.VERSION.SDK_INT >= 28) {
            this.mBiometricFragment.setCallbacks(executor, onClickListener, authenticationCallback);
            return;
        }
        FingerprintDialogFragment fingerprintDialogFragment = this.mFingerprintDialogFragment;
        if (fingerprintDialogFragment == null || this.mFingerprintHelperFragment == null) {
            return;
        }
        fingerprintDialogFragment.setNegativeButtonListener(onClickListener);
        this.mFingerprintHelperFragment.setCallback(executor, authenticationCallback);
        this.mFingerprintHelperFragment.setHandler(this.mFingerprintDialogFragment.getHandler());
    }

    Executor getExecutor() {
        return this.mExecutor;
    }

    DialogInterface.OnClickListener getOnClickListener() {
        return this.mOnClickListener;
    }

    BiometricPrompt.AuthenticationCallback getAuthenticationCallback() {
        return this.mAuthenticationCallback;
    }

    void setDeviceCredentialResult(int i) {
        this.mDeviceCredentialResult = i;
    }

    int getDeviceCredentialResult() {
        return this.mDeviceCredentialResult;
    }

    void setConfirmingDeviceCredential(boolean z) {
        this.mConfirmingDeviceCredential = z;
    }

    boolean isConfirmingDeviceCredential() {
        return this.mConfirmingDeviceCredential;
    }

    void ignoreNextReset() {
        if (this.mIgnoreResetState == 0) {
            this.mIgnoreResetState = 1;
        }
    }

    void startIgnoringReset() {
        this.mIgnoreResetState = 2;
    }

    void stopIgnoringReset() {
        this.mIgnoreResetState = 0;
    }

    void reset() {
        int i = this.mIgnoreResetState;
        if (i == 2) {
            return;
        }
        if (i == 1) {
            stopIgnoringReset();
            return;
        }
        this.mClientThemeResId = 0;
        this.mBiometricFragment = null;
        this.mFingerprintDialogFragment = null;
        this.mFingerprintHelperFragment = null;
        this.mExecutor = null;
        this.mOnClickListener = null;
        this.mAuthenticationCallback = null;
        this.mDeviceCredentialResult = 0;
        this.mConfirmingDeviceCredential = false;
        sInstance = null;
    }
}

package androidx.biometric;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import java.security.Signature;
import java.util.concurrent.Executor;
import javax.crypto.Cipher;
import javax.crypto.Mac;

/* loaded from: classes.dex */
public class BiometricPrompt implements BiometricConstants {
    static final String BIOMETRIC_FRAGMENT_TAG = "BiometricFragment";
    private static final boolean DEBUG = false;
    private static final boolean DEBUG_FORCE_FINGERPRINT = false;
    private static final int DELAY_MILLIS = 500;
    static final String DIALOG_FRAGMENT_TAG = "FingerprintDialogFragment";
    static final String FINGERPRINT_HELPER_FRAGMENT_TAG = "FingerprintHelperFragment";
    static final String KEY_ALLOW_DEVICE_CREDENTIAL = "allow_device_credential";
    static final String KEY_DESCRIPTION = "description";
    static final String KEY_HANDLING_DEVICE_CREDENTIAL_RESULT = "handling_device_credential_result";
    static final String KEY_NEGATIVE_TEXT = "negative_text";
    static final String KEY_REQUIRE_CONFIRMATION = "require_confirmation";
    static final String KEY_SUBTITLE = "subtitle";
    static final String KEY_TITLE = "title";
    private static final String TAG = "BiometricPromptCompat";
    private final AuthenticationCallback mAuthenticationCallback;
    private BiometricFragment mBiometricFragment;
    private final Executor mExecutor;
    private FingerprintDialogFragment mFingerprintDialogFragment;
    private FingerprintHelperFragment mFingerprintHelperFragment;
    private Fragment mFragment;
    private FragmentActivity mFragmentActivity;
    private boolean mIsHandlingDeviceCredential;
    private final LifecycleObserver mLifecycleObserver;
    private final DialogInterface.OnClickListener mNegativeButtonListener = new DialogInterface.OnClickListener() { // from class: androidx.biometric.BiometricPrompt.1
        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            BiometricPrompt.this.mExecutor.execute(new Runnable() { // from class: androidx.biometric.BiometricPrompt.1.1
                /* JADX WARN: Multi-variable type inference failed */
                /* JADX WARN: Type inference failed for: r0v11, types: [java.lang.CharSequence] */
                /* JADX WARN: Type inference failed for: r0v21, types: [java.lang.CharSequence] */
                @Override // java.lang.Runnable
                public void run() {
                    if (!BiometricPrompt.canUseBiometricFragment() || BiometricPrompt.this.mBiometricFragment == null) {
                        if (BiometricPrompt.this.mFingerprintDialogFragment != null && BiometricPrompt.this.mFingerprintHelperFragment != null) {
                            CharSequence negativeButtonText = BiometricPrompt.this.mFingerprintDialogFragment.getNegativeButtonText();
                            BiometricPrompt.this.mAuthenticationCallback.onAuthenticationError(13, negativeButtonText != 0 ? negativeButtonText : "");
                            BiometricPrompt.this.mFingerprintHelperFragment.cancel(2);
                            return;
                        }
                        Log.e(BiometricPrompt.TAG, "Negative button callback not run. Fragment was null.");
                        return;
                    }
                    CharSequence negativeButtonText2 = BiometricPrompt.this.mBiometricFragment.getNegativeButtonText();
                    BiometricPrompt.this.mAuthenticationCallback.onAuthenticationError(13, negativeButtonText2 != 0 ? negativeButtonText2 : "");
                    BiometricPrompt.this.mBiometricFragment.cleanup();
                }
            });
        }
    };
    private boolean mPausedOnce;

    public static abstract class AuthenticationCallback {
        public void onAuthenticationError(int i, CharSequence charSequence) {
        }

        public void onAuthenticationFailed() {
        }

        public void onAuthenticationSucceeded(AuthenticationResult authenticationResult) {
        }
    }

    public static class CryptoObject {
        private final Cipher mCipher;
        private final Mac mMac;
        private final Signature mSignature;

        public CryptoObject(Signature signature) {
            this.mSignature = signature;
            this.mCipher = null;
            this.mMac = null;
        }

        public CryptoObject(Cipher cipher) {
            this.mCipher = cipher;
            this.mSignature = null;
            this.mMac = null;
        }

        public CryptoObject(Mac mac) {
            this.mMac = mac;
            this.mCipher = null;
            this.mSignature = null;
        }

        public Signature getSignature() {
            return this.mSignature;
        }

        public Cipher getCipher() {
            return this.mCipher;
        }

        public Mac getMac() {
            return this.mMac;
        }
    }

    public static class AuthenticationResult {
        private final CryptoObject mCryptoObject;

        AuthenticationResult(CryptoObject cryptoObject) {
            this.mCryptoObject = cryptoObject;
        }

        public CryptoObject getCryptoObject() {
            return this.mCryptoObject;
        }
    }

    public static class PromptInfo {
        private Bundle mBundle;

        public static class Builder {
            private final Bundle mBundle = new Bundle();

            public Builder setTitle(CharSequence charSequence) {
                this.mBundle.putCharSequence("title", charSequence);
                return this;
            }

            public Builder setSubtitle(CharSequence charSequence) {
                this.mBundle.putCharSequence("subtitle", charSequence);
                return this;
            }

            public Builder setDescription(CharSequence charSequence) {
                this.mBundle.putCharSequence("description", charSequence);
                return this;
            }

            public Builder setNegativeButtonText(CharSequence charSequence) {
                this.mBundle.putCharSequence(BiometricPrompt.KEY_NEGATIVE_TEXT, charSequence);
                return this;
            }

            public Builder setConfirmationRequired(boolean z) {
                this.mBundle.putBoolean(BiometricPrompt.KEY_REQUIRE_CONFIRMATION, z);
                return this;
            }

            public Builder setDeviceCredentialAllowed(boolean z) {
                this.mBundle.putBoolean(BiometricPrompt.KEY_ALLOW_DEVICE_CREDENTIAL, z);
                return this;
            }

            Builder setHandlingDeviceCredentialResult(boolean z) {
                this.mBundle.putBoolean(BiometricPrompt.KEY_HANDLING_DEVICE_CREDENTIAL_RESULT, z);
                return this;
            }

            public PromptInfo build() {
                CharSequence charSequence = this.mBundle.getCharSequence("title");
                CharSequence charSequence2 = this.mBundle.getCharSequence(BiometricPrompt.KEY_NEGATIVE_TEXT);
                boolean z = this.mBundle.getBoolean(BiometricPrompt.KEY_ALLOW_DEVICE_CREDENTIAL);
                boolean z2 = this.mBundle.getBoolean(BiometricPrompt.KEY_HANDLING_DEVICE_CREDENTIAL_RESULT);
                if (TextUtils.isEmpty(charSequence)) {
                    throw new IllegalArgumentException("Title must be set and non-empty");
                }
                if (TextUtils.isEmpty(charSequence2) && !z) {
                    throw new IllegalArgumentException("Negative text must be set and non-empty");
                }
                if (!TextUtils.isEmpty(charSequence2) && z) {
                    throw new IllegalArgumentException("Can't have both negative button behavior and device credential enabled");
                }
                if (z2 && !z) {
                    throw new IllegalArgumentException("Can't be handling device credential result without device credential enabled");
                }
                return new PromptInfo(this.mBundle);
            }
        }

        PromptInfo(Bundle bundle) {
            this.mBundle = bundle;
        }

        Bundle getBundle() {
            return this.mBundle;
        }

        public CharSequence getTitle() {
            return this.mBundle.getCharSequence("title");
        }

        public CharSequence getSubtitle() {
            return this.mBundle.getCharSequence("subtitle");
        }

        public CharSequence getDescription() {
            return this.mBundle.getCharSequence("description");
        }

        public CharSequence getNegativeButtonText() {
            return this.mBundle.getCharSequence(BiometricPrompt.KEY_NEGATIVE_TEXT);
        }

        public boolean isConfirmationRequired() {
            return this.mBundle.getBoolean(BiometricPrompt.KEY_REQUIRE_CONFIRMATION);
        }

        public boolean isDeviceCredentialAllowed() {
            return this.mBundle.getBoolean(BiometricPrompt.KEY_ALLOW_DEVICE_CREDENTIAL);
        }

        boolean isHandlingDeviceCredentialResult() {
            return this.mBundle.getBoolean(BiometricPrompt.KEY_HANDLING_DEVICE_CREDENTIAL_RESULT);
        }
    }

    public BiometricPrompt(FragmentActivity fragmentActivity, Executor executor, AuthenticationCallback authenticationCallback) {
        LifecycleObserver lifecycleObserver = new LifecycleObserver() { // from class: androidx.biometric.BiometricPrompt.2
            @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
            void onPause() {
                if (BiometricPrompt.this.isChangingConfigurations()) {
                    return;
                }
                if (!BiometricPrompt.canUseBiometricFragment() || BiometricPrompt.this.mBiometricFragment == null) {
                    if (BiometricPrompt.this.mFingerprintDialogFragment != null && BiometricPrompt.this.mFingerprintHelperFragment != null) {
                        BiometricPrompt.dismissFingerprintFragments(BiometricPrompt.this.mFingerprintDialogFragment, BiometricPrompt.this.mFingerprintHelperFragment);
                    }
                } else if (BiometricPrompt.this.mBiometricFragment.isDeviceCredentialAllowed()) {
                    if (!BiometricPrompt.this.mPausedOnce) {
                        BiometricPrompt.this.mPausedOnce = true;
                    } else {
                        BiometricPrompt.this.mBiometricFragment.cancel();
                    }
                } else {
                    BiometricPrompt.this.mBiometricFragment.cancel();
                }
                BiometricPrompt.this.maybeResetHandlerBridge();
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
            void onResume() {
                BiometricPrompt.this.mBiometricFragment = BiometricPrompt.canUseBiometricFragment() ? (BiometricFragment) BiometricPrompt.this.getFragmentManager().findFragmentByTag(BiometricPrompt.BIOMETRIC_FRAGMENT_TAG) : null;
                if (BiometricPrompt.canUseBiometricFragment() && BiometricPrompt.this.mBiometricFragment != null) {
                    BiometricPrompt.this.mBiometricFragment.setCallbacks(BiometricPrompt.this.mExecutor, BiometricPrompt.this.mNegativeButtonListener, BiometricPrompt.this.mAuthenticationCallback);
                } else {
                    BiometricPrompt biometricPrompt = BiometricPrompt.this;
                    biometricPrompt.mFingerprintDialogFragment = (FingerprintDialogFragment) biometricPrompt.getFragmentManager().findFragmentByTag(BiometricPrompt.DIALOG_FRAGMENT_TAG);
                    BiometricPrompt biometricPrompt2 = BiometricPrompt.this;
                    biometricPrompt2.mFingerprintHelperFragment = (FingerprintHelperFragment) biometricPrompt2.getFragmentManager().findFragmentByTag(BiometricPrompt.FINGERPRINT_HELPER_FRAGMENT_TAG);
                    if (BiometricPrompt.this.mFingerprintDialogFragment != null) {
                        BiometricPrompt.this.mFingerprintDialogFragment.setNegativeButtonListener(BiometricPrompt.this.mNegativeButtonListener);
                    }
                    if (BiometricPrompt.this.mFingerprintHelperFragment != null) {
                        BiometricPrompt.this.mFingerprintHelperFragment.setCallback(BiometricPrompt.this.mExecutor, BiometricPrompt.this.mAuthenticationCallback);
                        if (BiometricPrompt.this.mFingerprintDialogFragment != null) {
                            BiometricPrompt.this.mFingerprintHelperFragment.setHandler(BiometricPrompt.this.mFingerprintDialogFragment.getHandler());
                        }
                    }
                }
                BiometricPrompt.this.maybeHandleDeviceCredentialResult();
                BiometricPrompt.this.maybeInitHandlerBridge(false);
            }
        };
        this.mLifecycleObserver = lifecycleObserver;
        if (fragmentActivity == null) {
            throw new IllegalArgumentException("FragmentActivity must not be null");
        }
        if (executor == null) {
            throw new IllegalArgumentException("Executor must not be null");
        }
        if (authenticationCallback == null) {
            throw new IllegalArgumentException("AuthenticationCallback must not be null");
        }
        this.mFragmentActivity = fragmentActivity;
        this.mAuthenticationCallback = authenticationCallback;
        this.mExecutor = executor;
        fragmentActivity.getLifecycle().addObserver(lifecycleObserver);
    }

    public BiometricPrompt(Fragment fragment, Executor executor, AuthenticationCallback authenticationCallback) {
        LifecycleObserver lifecycleObserver = new LifecycleObserver() { // from class: androidx.biometric.BiometricPrompt.2
            @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
            void onPause() {
                if (BiometricPrompt.this.isChangingConfigurations()) {
                    return;
                }
                if (!BiometricPrompt.canUseBiometricFragment() || BiometricPrompt.this.mBiometricFragment == null) {
                    if (BiometricPrompt.this.mFingerprintDialogFragment != null && BiometricPrompt.this.mFingerprintHelperFragment != null) {
                        BiometricPrompt.dismissFingerprintFragments(BiometricPrompt.this.mFingerprintDialogFragment, BiometricPrompt.this.mFingerprintHelperFragment);
                    }
                } else if (BiometricPrompt.this.mBiometricFragment.isDeviceCredentialAllowed()) {
                    if (!BiometricPrompt.this.mPausedOnce) {
                        BiometricPrompt.this.mPausedOnce = true;
                    } else {
                        BiometricPrompt.this.mBiometricFragment.cancel();
                    }
                } else {
                    BiometricPrompt.this.mBiometricFragment.cancel();
                }
                BiometricPrompt.this.maybeResetHandlerBridge();
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
            void onResume() {
                BiometricPrompt.this.mBiometricFragment = BiometricPrompt.canUseBiometricFragment() ? (BiometricFragment) BiometricPrompt.this.getFragmentManager().findFragmentByTag(BiometricPrompt.BIOMETRIC_FRAGMENT_TAG) : null;
                if (BiometricPrompt.canUseBiometricFragment() && BiometricPrompt.this.mBiometricFragment != null) {
                    BiometricPrompt.this.mBiometricFragment.setCallbacks(BiometricPrompt.this.mExecutor, BiometricPrompt.this.mNegativeButtonListener, BiometricPrompt.this.mAuthenticationCallback);
                } else {
                    BiometricPrompt biometricPrompt = BiometricPrompt.this;
                    biometricPrompt.mFingerprintDialogFragment = (FingerprintDialogFragment) biometricPrompt.getFragmentManager().findFragmentByTag(BiometricPrompt.DIALOG_FRAGMENT_TAG);
                    BiometricPrompt biometricPrompt2 = BiometricPrompt.this;
                    biometricPrompt2.mFingerprintHelperFragment = (FingerprintHelperFragment) biometricPrompt2.getFragmentManager().findFragmentByTag(BiometricPrompt.FINGERPRINT_HELPER_FRAGMENT_TAG);
                    if (BiometricPrompt.this.mFingerprintDialogFragment != null) {
                        BiometricPrompt.this.mFingerprintDialogFragment.setNegativeButtonListener(BiometricPrompt.this.mNegativeButtonListener);
                    }
                    if (BiometricPrompt.this.mFingerprintHelperFragment != null) {
                        BiometricPrompt.this.mFingerprintHelperFragment.setCallback(BiometricPrompt.this.mExecutor, BiometricPrompt.this.mAuthenticationCallback);
                        if (BiometricPrompt.this.mFingerprintDialogFragment != null) {
                            BiometricPrompt.this.mFingerprintHelperFragment.setHandler(BiometricPrompt.this.mFingerprintDialogFragment.getHandler());
                        }
                    }
                }
                BiometricPrompt.this.maybeHandleDeviceCredentialResult();
                BiometricPrompt.this.maybeInitHandlerBridge(false);
            }
        };
        this.mLifecycleObserver = lifecycleObserver;
        if (fragment == null) {
            throw new IllegalArgumentException("FragmentActivity must not be null");
        }
        if (executor == null) {
            throw new IllegalArgumentException("Executor must not be null");
        }
        if (authenticationCallback == null) {
            throw new IllegalArgumentException("AuthenticationCallback must not be null");
        }
        this.mFragment = fragment;
        this.mAuthenticationCallback = authenticationCallback;
        this.mExecutor = executor;
        fragment.getLifecycle().addObserver(lifecycleObserver);
    }

    public void authenticate(PromptInfo promptInfo, CryptoObject cryptoObject) {
        if (promptInfo == null) {
            throw new IllegalArgumentException("PromptInfo can not be null");
        }
        if (cryptoObject == null) {
            throw new IllegalArgumentException("CryptoObject can not be null");
        }
        if (promptInfo.getBundle().getBoolean(KEY_ALLOW_DEVICE_CREDENTIAL)) {
            throw new IllegalArgumentException("Device credential not supported with crypto");
        }
        authenticateInternal(promptInfo, cryptoObject);
    }

    public void authenticate(PromptInfo promptInfo) {
        if (promptInfo == null) {
            throw new IllegalArgumentException("PromptInfo can not be null");
        }
        authenticateInternal(promptInfo, null);
    }

    private void authenticateInternal(PromptInfo promptInfo, CryptoObject cryptoObject) {
        this.mIsHandlingDeviceCredential = promptInfo.isHandlingDeviceCredentialResult();
        FragmentActivity activity = getActivity();
        if (promptInfo.isDeviceCredentialAllowed() && Build.VERSION.SDK_INT <= 28) {
            if (!this.mIsHandlingDeviceCredential) {
                launchDeviceCredentialHandler(promptInfo);
                return;
            }
            if (Build.VERSION.SDK_INT >= 21) {
                if (activity == null) {
                    Log.e(TAG, "Failed to authenticate with device credential. Activity was null.");
                    return;
                }
                DeviceCredentialHandlerBridge instanceIfNotNull = DeviceCredentialHandlerBridge.getInstanceIfNotNull();
                if (instanceIfNotNull == null) {
                    Log.e(TAG, "Failed to authenticate with device credential. Bridge was null.");
                    return;
                } else if (!instanceIfNotNull.isConfirmingDeviceCredential() && BiometricManager.from(activity).canAuthenticate() != 0) {
                    Utils.launchDeviceCredentialConfirmation(TAG, activity, promptInfo.getBundle(), null);
                    return;
                }
            }
        }
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager.isStateSaved()) {
            Log.w(TAG, "Not launching prompt. authenticate() called after onSaveInstanceState()");
            return;
        }
        Bundle bundle = promptInfo.getBundle();
        boolean z = false;
        this.mPausedOnce = false;
        if (activity != null && cryptoObject != null && Utils.shouldUseFingerprintForCrypto(activity, Build.MANUFACTURER, Build.MODEL)) {
            z = true;
        }
        if (!z && canUseBiometricFragment()) {
            BiometricFragment biometricFragment = (BiometricFragment) fragmentManager.findFragmentByTag(BIOMETRIC_FRAGMENT_TAG);
            if (biometricFragment != null) {
                this.mBiometricFragment = biometricFragment;
            } else {
                this.mBiometricFragment = BiometricFragment.newInstance();
            }
            this.mBiometricFragment.setCallbacks(this.mExecutor, this.mNegativeButtonListener, this.mAuthenticationCallback);
            this.mBiometricFragment.setCryptoObject(cryptoObject);
            this.mBiometricFragment.setBundle(bundle);
            if (biometricFragment == null) {
                fragmentManager.beginTransaction().add(this.mBiometricFragment, BIOMETRIC_FRAGMENT_TAG).commitAllowingStateLoss();
            } else if (this.mBiometricFragment.isDetached()) {
                fragmentManager.beginTransaction().attach(this.mBiometricFragment).commitAllowingStateLoss();
            }
        } else {
            FingerprintDialogFragment fingerprintDialogFragment = (FingerprintDialogFragment) fragmentManager.findFragmentByTag(DIALOG_FRAGMENT_TAG);
            if (fingerprintDialogFragment != null) {
                this.mFingerprintDialogFragment = fingerprintDialogFragment;
            } else {
                this.mFingerprintDialogFragment = FingerprintDialogFragment.newInstance();
            }
            this.mFingerprintDialogFragment.setNegativeButtonListener(this.mNegativeButtonListener);
            this.mFingerprintDialogFragment.setBundle(bundle);
            if (activity != null && !Utils.shouldHideFingerprintDialog(activity, Build.MODEL)) {
                if (fingerprintDialogFragment == null) {
                    this.mFingerprintDialogFragment.show(fragmentManager, DIALOG_FRAGMENT_TAG);
                } else if (this.mFingerprintDialogFragment.isDetached()) {
                    fragmentManager.beginTransaction().attach(this.mFingerprintDialogFragment).commitAllowingStateLoss();
                }
            }
            FingerprintHelperFragment fingerprintHelperFragment = (FingerprintHelperFragment) fragmentManager.findFragmentByTag(FINGERPRINT_HELPER_FRAGMENT_TAG);
            if (fingerprintHelperFragment != null) {
                this.mFingerprintHelperFragment = fingerprintHelperFragment;
            } else {
                this.mFingerprintHelperFragment = FingerprintHelperFragment.newInstance();
            }
            this.mFingerprintHelperFragment.setCallback(this.mExecutor, this.mAuthenticationCallback);
            Handler handler = this.mFingerprintDialogFragment.getHandler();
            this.mFingerprintHelperFragment.setHandler(handler);
            this.mFingerprintHelperFragment.setCryptoObject(cryptoObject);
            handler.sendMessageDelayed(handler.obtainMessage(6), 500L);
            if (fingerprintHelperFragment == null) {
                fragmentManager.beginTransaction().add(this.mFingerprintHelperFragment, FINGERPRINT_HELPER_FRAGMENT_TAG).commitAllowingStateLoss();
            } else if (this.mFingerprintHelperFragment.isDetached()) {
                fragmentManager.beginTransaction().attach(this.mFingerprintHelperFragment).commitAllowingStateLoss();
            }
        }
        fragmentManager.executePendingTransactions();
    }

    public void cancelAuthentication() {
        DeviceCredentialHandlerBridge instanceIfNotNull;
        FingerprintDialogFragment fingerprintDialogFragment;
        BiometricFragment biometricFragment;
        DeviceCredentialHandlerBridge instanceIfNotNull2;
        if (canUseBiometricFragment() && (biometricFragment = this.mBiometricFragment) != null) {
            biometricFragment.cancel();
            if (this.mIsHandlingDeviceCredential || (instanceIfNotNull2 = DeviceCredentialHandlerBridge.getInstanceIfNotNull()) == null || instanceIfNotNull2.getBiometricFragment() == null) {
                return;
            }
            instanceIfNotNull2.getBiometricFragment().cancel();
            return;
        }
        FingerprintHelperFragment fingerprintHelperFragment = this.mFingerprintHelperFragment;
        if (fingerprintHelperFragment != null && (fingerprintDialogFragment = this.mFingerprintDialogFragment) != null) {
            dismissFingerprintFragments(fingerprintDialogFragment, fingerprintHelperFragment);
        }
        if (this.mIsHandlingDeviceCredential || (instanceIfNotNull = DeviceCredentialHandlerBridge.getInstanceIfNotNull()) == null || instanceIfNotNull.getFingerprintDialogFragment() == null || instanceIfNotNull.getFingerprintHelperFragment() == null) {
            return;
        }
        dismissFingerprintFragments(instanceIfNotNull.getFingerprintDialogFragment(), instanceIfNotNull.getFingerprintHelperFragment());
    }

    private void launchDeviceCredentialHandler(PromptInfo promptInfo) {
        FragmentActivity activity = getActivity();
        if (activity == null || activity.isFinishing()) {
            Log.w(TAG, "Failed to start handler activity. Parent activity was null or finishing.");
            return;
        }
        maybeInitHandlerBridge(true);
        Bundle bundle = promptInfo.getBundle();
        bundle.putBoolean(KEY_HANDLING_DEVICE_CREDENTIAL_RESULT, true);
        Intent intent = new Intent(activity, (Class<?>) DeviceCredentialHandlerActivity.class);
        intent.putExtra("prompt_info_bundle", bundle);
        activity.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void maybeInitHandlerBridge(boolean z) {
        FingerprintHelperFragment fingerprintHelperFragment;
        BiometricFragment biometricFragment;
        if (Build.VERSION.SDK_INT >= 29) {
            return;
        }
        DeviceCredentialHandlerBridge deviceCredentialHandlerBridge = DeviceCredentialHandlerBridge.getInstance();
        if (this.mIsHandlingDeviceCredential) {
            if (canUseBiometricFragment() && (biometricFragment = this.mBiometricFragment) != null) {
                deviceCredentialHandlerBridge.setBiometricFragment(biometricFragment);
            } else {
                FingerprintDialogFragment fingerprintDialogFragment = this.mFingerprintDialogFragment;
                if (fingerprintDialogFragment != null && (fingerprintHelperFragment = this.mFingerprintHelperFragment) != null) {
                    deviceCredentialHandlerBridge.setFingerprintFragments(fingerprintDialogFragment, fingerprintHelperFragment);
                }
            }
        } else {
            FragmentActivity activity = getActivity();
            if (activity != null) {
                try {
                    deviceCredentialHandlerBridge.setClientThemeResId(activity.getPackageManager().getActivityInfo(activity.getComponentName(), 0).getThemeResource());
                } catch (PackageManager.NameNotFoundException e) {
                    Log.e(TAG, "Failed to register client theme to bridge", e);
                }
            }
        }
        deviceCredentialHandlerBridge.setCallbacks(this.mExecutor, this.mNegativeButtonListener, this.mAuthenticationCallback);
        if (z) {
            deviceCredentialHandlerBridge.startIgnoringReset();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void maybeHandleDeviceCredentialResult() {
        DeviceCredentialHandlerBridge instanceIfNotNull;
        if (this.mIsHandlingDeviceCredential || (instanceIfNotNull = DeviceCredentialHandlerBridge.getInstanceIfNotNull()) == null) {
            return;
        }
        int deviceCredentialResult = instanceIfNotNull.getDeviceCredentialResult();
        if (deviceCredentialResult == 1) {
            this.mAuthenticationCallback.onAuthenticationSucceeded(new AuthenticationResult(null));
            instanceIfNotNull.stopIgnoringReset();
            instanceIfNotNull.reset();
        } else {
            if (deviceCredentialResult != 2) {
                return;
            }
            this.mAuthenticationCallback.onAuthenticationError(10, getActivity() != null ? getActivity().getString(R.string.generic_error_user_canceled) : "");
            instanceIfNotNull.stopIgnoringReset();
            instanceIfNotNull.reset();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void maybeResetHandlerBridge() {
        DeviceCredentialHandlerBridge instanceIfNotNull = DeviceCredentialHandlerBridge.getInstanceIfNotNull();
        if (instanceIfNotNull != null) {
            instanceIfNotNull.reset();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isChangingConfigurations() {
        return getActivity() != null && getActivity().isChangingConfigurations();
    }

    private FragmentActivity getActivity() {
        FragmentActivity fragmentActivity = this.mFragmentActivity;
        return fragmentActivity != null ? fragmentActivity : this.mFragment.getActivity();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public FragmentManager getFragmentManager() {
        FragmentActivity fragmentActivity = this.mFragmentActivity;
        return fragmentActivity != null ? fragmentActivity.getSupportFragmentManager() : this.mFragment.getChildFragmentManager();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean canUseBiometricFragment() {
        return Build.VERSION.SDK_INT >= 28;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void dismissFingerprintFragments(FingerprintDialogFragment fingerprintDialogFragment, FingerprintHelperFragment fingerprintHelperFragment) {
        fingerprintDialogFragment.dismissSafely();
        fingerprintHelperFragment.cancel(0);
    }
}

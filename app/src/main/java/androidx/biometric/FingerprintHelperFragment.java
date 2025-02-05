package androidx.biometric;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.biometric.BiometricPrompt;
import androidx.core.hardware.fingerprint.FingerprintManagerCompat;
import androidx.core.os.CancellationSignal;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public class FingerprintHelperFragment extends Fragment {
    private static final String TAG = "FingerprintHelperFrag";
    static final int USER_CANCELED_FROM_NEGATIVE_BUTTON = 2;
    static final int USER_CANCELED_FROM_NONE = 0;
    static final int USER_CANCELED_FROM_USER = 1;
    final FingerprintManagerCompat.AuthenticationCallback mAuthenticationCallback = new FingerprintManagerCompat.AuthenticationCallback() { // from class: androidx.biometric.FingerprintHelperFragment.1
        /* JADX INFO: Access modifiers changed from: private */
        public void dismissAndForwardResult(final int i, final CharSequence charSequence) {
            FingerprintHelperFragment.this.mMessageRouter.sendMessage(3);
            if (Utils.isConfirmingDeviceCredential()) {
                return;
            }
            FingerprintHelperFragment.this.mExecutor.execute(new Runnable() { // from class: androidx.biometric.FingerprintHelperFragment.1.1
                @Override // java.lang.Runnable
                public void run() {
                    FingerprintHelperFragment.this.mClientAuthenticationCallback.onAuthenticationError(i, charSequence);
                }
            });
        }

        @Override // androidx.core.hardware.fingerprint.FingerprintManagerCompat.AuthenticationCallback
        public void onAuthenticationError(final int i, final CharSequence charSequence) {
            if (i == 5) {
                if (FingerprintHelperFragment.this.mCanceledFrom == 0) {
                    dismissAndForwardResult(i, charSequence);
                }
                FingerprintHelperFragment.this.cleanup();
                return;
            }
            if (i == 7 || i == 9) {
                dismissAndForwardResult(i, charSequence);
                FingerprintHelperFragment.this.cleanup();
                return;
            }
            if (charSequence == null) {
                Log.e(FingerprintHelperFragment.TAG, "Got null string for error message: " + i);
                charSequence = FingerprintHelperFragment.this.mContext.getResources().getString(R.string.default_error_msg);
            }
            if (Utils.isUnknownError(i)) {
                i = 8;
            }
            FingerprintHelperFragment.this.mMessageRouter.sendMessage(2, i, 0, charSequence);
            FingerprintHelperFragment.this.mHandler.postDelayed(new Runnable() { // from class: androidx.biometric.FingerprintHelperFragment.1.2
                @Override // java.lang.Runnable
                public void run() {
                    dismissAndForwardResult(i, charSequence);
                    FingerprintHelperFragment.this.cleanup();
                }
            }, FingerprintDialogFragment.getHideDialogDelay(FingerprintHelperFragment.this.getContext()));
        }

        @Override // androidx.core.hardware.fingerprint.FingerprintManagerCompat.AuthenticationCallback
        public void onAuthenticationHelp(int i, CharSequence charSequence) {
            FingerprintHelperFragment.this.mMessageRouter.sendMessage(1, charSequence);
        }

        @Override // androidx.core.hardware.fingerprint.FingerprintManagerCompat.AuthenticationCallback
        public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult authenticationResult) {
            FingerprintHelperFragment.this.mMessageRouter.sendMessage(5);
            final BiometricPrompt.AuthenticationResult authenticationResult2 = authenticationResult != null ? new BiometricPrompt.AuthenticationResult(FingerprintHelperFragment.unwrapCryptoObject(authenticationResult.getCryptoObject())) : new BiometricPrompt.AuthenticationResult(null);
            FingerprintHelperFragment.this.mExecutor.execute(new Runnable() { // from class: androidx.biometric.FingerprintHelperFragment.1.3
                @Override // java.lang.Runnable
                public void run() {
                    FingerprintHelperFragment.this.mClientAuthenticationCallback.onAuthenticationSucceeded(authenticationResult2);
                }
            });
            FingerprintHelperFragment.this.cleanup();
        }

        @Override // androidx.core.hardware.fingerprint.FingerprintManagerCompat.AuthenticationCallback
        public void onAuthenticationFailed() {
            FingerprintHelperFragment.this.mMessageRouter.sendMessage(1, FingerprintHelperFragment.this.mContext.getResources().getString(R.string.fingerprint_not_recognized));
            FingerprintHelperFragment.this.mExecutor.execute(new Runnable() { // from class: androidx.biometric.FingerprintHelperFragment.1.4
                @Override // java.lang.Runnable
                public void run() {
                    FingerprintHelperFragment.this.mClientAuthenticationCallback.onAuthenticationFailed();
                }
            });
        }
    };
    private int mCanceledFrom;
    private CancellationSignal mCancellationSignal;
    BiometricPrompt.AuthenticationCallback mClientAuthenticationCallback;
    private Context mContext;
    private BiometricPrompt.CryptoObject mCryptoObject;
    Executor mExecutor;
    private Handler mHandler;
    private MessageRouter mMessageRouter;
    private boolean mShowing;

    static class MessageRouter {
        private final Handler mHandler;

        MessageRouter(Handler handler) {
            this.mHandler = handler;
        }

        void sendMessage(int i) {
            this.mHandler.obtainMessage(i).sendToTarget();
        }

        void sendMessage(int i, Object obj) {
            this.mHandler.obtainMessage(i, obj).sendToTarget();
        }

        void sendMessage(int i, int i2, int i3, Object obj) {
            this.mHandler.obtainMessage(i, i2, i3, obj).sendToTarget();
        }
    }

    static FingerprintHelperFragment newInstance() {
        return new FingerprintHelperFragment();
    }

    void setCryptoObject(BiometricPrompt.CryptoObject cryptoObject) {
        this.mCryptoObject = cryptoObject;
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setRetainInstance(true);
        this.mContext = getContext();
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        if (!this.mShowing) {
            this.mCancellationSignal = new CancellationSignal();
            this.mCanceledFrom = 0;
            FingerprintManagerCompat from = FingerprintManagerCompat.from(this.mContext);
            if (handlePreAuthenticationErrors(from)) {
                this.mMessageRouter.sendMessage(3);
                cleanup();
            } else {
                from.authenticate(wrapCryptoObject(this.mCryptoObject), 0, this.mCancellationSignal, this.mAuthenticationCallback, null);
                this.mShowing = true;
            }
        }
        return super.onCreateView(layoutInflater, viewGroup, bundle);
    }

    void setCallback(Executor executor, BiometricPrompt.AuthenticationCallback authenticationCallback) {
        this.mExecutor = executor;
        this.mClientAuthenticationCallback = authenticationCallback;
    }

    void setHandler(Handler handler) {
        this.mHandler = handler;
        this.mMessageRouter = new MessageRouter(handler);
    }

    void setMessageRouter(MessageRouter messageRouter) {
        this.mMessageRouter = messageRouter;
    }

    void cancel(int i) {
        this.mCanceledFrom = i;
        if (i == 1) {
            sendErrorToClient(10);
        }
        CancellationSignal cancellationSignal = this.mCancellationSignal;
        if (cancellationSignal != null) {
            cancellationSignal.cancel();
        }
        cleanup();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cleanup() {
        this.mShowing = false;
        FragmentActivity activity = getActivity();
        if (getFragmentManager() != null) {
            getFragmentManager().beginTransaction().detach(this).commitAllowingStateLoss();
        }
        if (Utils.isConfirmingDeviceCredential()) {
            return;
        }
        Utils.maybeFinishHandler(activity);
    }

    private boolean handlePreAuthenticationErrors(FingerprintManagerCompat fingerprintManagerCompat) {
        if (!fingerprintManagerCompat.isHardwareDetected()) {
            sendErrorToClient(12);
            return true;
        }
        if (fingerprintManagerCompat.hasEnrolledFingerprints()) {
            return false;
        }
        sendErrorToClient(11);
        return true;
    }

    private void sendErrorToClient(int i) {
        if (Utils.isConfirmingDeviceCredential()) {
            return;
        }
        this.mClientAuthenticationCallback.onAuthenticationError(i, getErrorString(this.mContext, i));
    }

    private String getErrorString(Context context, int i) {
        if (i == 1) {
            return context.getString(R.string.fingerprint_error_hw_not_available);
        }
        switch (i) {
            case 10:
                return context.getString(R.string.fingerprint_error_user_canceled);
            case 11:
                return context.getString(R.string.fingerprint_error_no_fingerprints);
            case 12:
                return context.getString(R.string.fingerprint_error_hw_not_present);
            default:
                Log.e(TAG, "Unknown error code: " + i);
                return context.getString(R.string.default_error_msg);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static BiometricPrompt.CryptoObject unwrapCryptoObject(FingerprintManagerCompat.CryptoObject cryptoObject) {
        if (cryptoObject == null) {
            return null;
        }
        if (cryptoObject.getCipher() != null) {
            return new BiometricPrompt.CryptoObject(cryptoObject.getCipher());
        }
        if (cryptoObject.getSignature() != null) {
            return new BiometricPrompt.CryptoObject(cryptoObject.getSignature());
        }
        if (cryptoObject.getMac() != null) {
            return new BiometricPrompt.CryptoObject(cryptoObject.getMac());
        }
        return null;
    }

    private static FingerprintManagerCompat.CryptoObject wrapCryptoObject(BiometricPrompt.CryptoObject cryptoObject) {
        if (cryptoObject == null) {
            return null;
        }
        if (cryptoObject.getCipher() != null) {
            return new FingerprintManagerCompat.CryptoObject(cryptoObject.getCipher());
        }
        if (cryptoObject.getSignature() != null) {
            return new FingerprintManagerCompat.CryptoObject(cryptoObject.getSignature());
        }
        if (cryptoObject.getMac() != null) {
            return new FingerprintManagerCompat.CryptoObject(cryptoObject.getMac());
        }
        return null;
    }
}

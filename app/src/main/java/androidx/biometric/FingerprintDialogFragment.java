package androidx.biometric;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import com.oblador.keychain.KeychainModule;

/* loaded from: classes.dex */
public class FingerprintDialogFragment extends DialogFragment {
    static final int DISPLAYED_FOR_500_MS = 6;
    private static final String KEY_DIALOG_BUNDLE = "SavedBundle";
    private static final int MESSAGE_DISPLAY_TIME_MS = 2000;
    static final int MSG_DISMISS_DIALOG_AUTHENTICATED = 5;
    static final int MSG_DISMISS_DIALOG_ERROR = 3;
    static final int MSG_RESET_MESSAGE = 4;
    static final int MSG_SHOW_ERROR = 2;
    static final int MSG_SHOW_HELP = 1;
    private static final int STATE_FINGERPRINT = 1;
    private static final int STATE_FINGERPRINT_AUTHENTICATED = 3;
    private static final int STATE_FINGERPRINT_ERROR = 2;
    private static final int STATE_NONE = 0;
    private static final String TAG = "FingerprintDialogFrag";
    private Bundle mBundle;
    private Context mContext;
    private int mErrorColor;
    private TextView mErrorText;
    private ImageView mFingerprintIcon;
    private int mLastState;
    DialogInterface.OnClickListener mNegativeButtonListener;
    private int mTextColor;
    private H mHandler = new H();
    private boolean mDismissInstantly = true;
    private final DialogInterface.OnClickListener mDeviceCredentialButtonListener = new DialogInterface.OnClickListener() { // from class: androidx.biometric.FingerprintDialogFragment.1
        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(final DialogInterface dialogInterface, int i) {
            if (i == -2) {
                if (Build.VERSION.SDK_INT < 21) {
                    Log.e(FingerprintDialogFragment.TAG, "Failed to check device credential. Not supported prior to L.");
                } else {
                    Utils.launchDeviceCredentialConfirmation(FingerprintDialogFragment.TAG, FingerprintDialogFragment.this.getActivity(), FingerprintDialogFragment.this.mBundle, new Runnable() { // from class: androidx.biometric.FingerprintDialogFragment.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            FingerprintDialogFragment.this.onCancel(dialogInterface);
                        }
                    });
                }
            }
        }
    };

    private boolean shouldAnimateForTransition(int i, int i2) {
        if (i == 0 && i2 == 1) {
            return false;
        }
        if (i == 1 && i2 == 2) {
            return true;
        }
        return i == 2 && i2 == 1;
    }

    static FingerprintDialogFragment newInstance() {
        return new FingerprintDialogFragment();
    }

    final class H extends Handler {
        H() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    FingerprintDialogFragment.this.handleShowHelp((CharSequence) message.obj);
                    break;
                case 2:
                    FingerprintDialogFragment.this.handleShowError((CharSequence) message.obj);
                    break;
                case 3:
                    FingerprintDialogFragment.this.handleDismissDialogError((CharSequence) message.obj);
                    break;
                case 4:
                    FingerprintDialogFragment.this.handleResetMessage();
                    break;
                case 5:
                    FingerprintDialogFragment.this.dismissSafely();
                    break;
                case 6:
                    Context context = FingerprintDialogFragment.this.getContext();
                    FingerprintDialogFragment.this.mDismissInstantly = context != null && Utils.shouldHideFingerprintDialog(context, Build.MODEL);
                    break;
            }
        }
    }

    @Override // androidx.fragment.app.DialogFragment
    public Dialog onCreateDialog(Bundle bundle) {
        CharSequence charSequence;
        if (bundle != null && this.mBundle == null) {
            this.mBundle = bundle.getBundle(KEY_DIALOG_BUNDLE);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(this.mBundle.getCharSequence(KeychainModule.AuthPromptOptions.TITLE));
        View inflate = LayoutInflater.from(builder.getContext()).inflate(R.layout.fingerprint_dialog_layout, (ViewGroup) null);
        TextView textView = (TextView) inflate.findViewById(R.id.fingerprint_subtitle);
        TextView textView2 = (TextView) inflate.findViewById(R.id.fingerprint_description);
        CharSequence charSequence2 = this.mBundle.getCharSequence(KeychainModule.AuthPromptOptions.SUBTITLE);
        if (TextUtils.isEmpty(charSequence2)) {
            textView.setVisibility(8);
        } else {
            textView.setVisibility(0);
            textView.setText(charSequence2);
        }
        CharSequence charSequence3 = this.mBundle.getCharSequence("description");
        if (TextUtils.isEmpty(charSequence3)) {
            textView2.setVisibility(8);
        } else {
            textView2.setVisibility(0);
            textView2.setText(charSequence3);
        }
        this.mFingerprintIcon = (ImageView) inflate.findViewById(R.id.fingerprint_icon);
        this.mErrorText = (TextView) inflate.findViewById(R.id.fingerprint_error);
        if (isDeviceCredentialAllowed()) {
            charSequence = getString(R.string.confirm_device_credential_password);
        } else {
            charSequence = this.mBundle.getCharSequence("negative_text");
        }
        builder.setNegativeButton(charSequence, new DialogInterface.OnClickListener() { // from class: androidx.biometric.FingerprintDialogFragment.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                if (FingerprintDialogFragment.this.isDeviceCredentialAllowed()) {
                    FingerprintDialogFragment.this.mDeviceCredentialButtonListener.onClick(dialogInterface, i);
                } else if (FingerprintDialogFragment.this.mNegativeButtonListener != null) {
                    FingerprintDialogFragment.this.mNegativeButtonListener.onClick(dialogInterface, i);
                } else {
                    Log.w(FingerprintDialogFragment.TAG, "No suitable negative button listener.");
                }
            }
        });
        builder.setView(inflate);
        AlertDialog create = builder.create();
        create.setCanceledOnTouchOutside(false);
        return create;
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBundle(KEY_DIALOG_BUNDLE, this.mBundle);
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = getContext();
        if (Build.VERSION.SDK_INT >= 26) {
            this.mErrorColor = getThemedColorFor(android.R.attr.colorError);
        } else {
            this.mErrorColor = ContextCompat.getColor(this.mContext, R.color.biometric_error_color);
        }
        this.mTextColor = getThemedColorFor(android.R.attr.textColorSecondary);
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        this.mLastState = 0;
        updateFingerprintIcon(1);
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        this.mHandler.removeCallbacksAndMessages(null);
    }

    @Override // androidx.fragment.app.DialogFragment, android.content.DialogInterface.OnCancelListener
    public void onCancel(DialogInterface dialogInterface) {
        super.onCancel(dialogInterface);
        FingerprintHelperFragment fingerprintHelperFragment = (FingerprintHelperFragment) getFragmentManager().findFragmentByTag("FingerprintHelperFragment");
        if (fingerprintHelperFragment != null) {
            fingerprintHelperFragment.cancel(1);
        }
    }

    public void setBundle(Bundle bundle) {
        this.mBundle = bundle;
    }

    private int getThemedColorFor(int i) {
        TypedValue typedValue = new TypedValue();
        this.mContext.getTheme().resolveAttribute(i, typedValue, true);
        TypedArray obtainStyledAttributes = getActivity().obtainStyledAttributes(typedValue.data, new int[]{i});
        int color = obtainStyledAttributes.getColor(0, 0);
        obtainStyledAttributes.recycle();
        return color;
    }

    protected CharSequence getNegativeButtonText() {
        return this.mBundle.getCharSequence("negative_text");
    }

    void setNegativeButtonListener(DialogInterface.OnClickListener onClickListener) {
        this.mNegativeButtonListener = onClickListener;
    }

    Handler getHandler() {
        return this.mHandler;
    }

    void dismissSafely() {
        if (getFragmentManager() == null) {
            Log.e(TAG, "Failed to dismiss fingerprint dialog fragment. Fragment manager was null.");
        } else {
            dismissAllowingStateLoss();
        }
    }

    static int getHideDialogDelay(Context context) {
        return (context == null || !Utils.shouldHideFingerprintDialog(context, Build.MODEL)) ? 2000 : 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isDeviceCredentialAllowed() {
        return this.mBundle.getBoolean("allow_device_credential");
    }

    private Drawable getAnimationForTransition(int i, int i2) {
        int i3;
        if (i == 0 && i2 == 1) {
            i3 = R.drawable.fingerprint_dialog_fp_to_error;
        } else if (i == 1 && i2 == 2) {
            i3 = R.drawable.fingerprint_dialog_fp_to_error;
        } else if (i == 2 && i2 == 1) {
            i3 = R.drawable.fingerprint_dialog_error_to_fp;
        } else {
            if (i != 1 || i2 != 3) {
                return null;
            }
            i3 = R.drawable.fingerprint_dialog_error_to_fp;
        }
        return this.mContext.getDrawable(i3);
    }

    private void updateFingerprintIcon(int i) {
        Drawable animationForTransition;
        if (this.mFingerprintIcon == null || Build.VERSION.SDK_INT < 23 || (animationForTransition = getAnimationForTransition(this.mLastState, i)) == null) {
            return;
        }
        AnimatedVectorDrawable animatedVectorDrawable = animationForTransition instanceof AnimatedVectorDrawable ? (AnimatedVectorDrawable) animationForTransition : null;
        this.mFingerprintIcon.setImageDrawable(animationForTransition);
        if (animatedVectorDrawable != null && shouldAnimateForTransition(this.mLastState, i)) {
            animatedVectorDrawable.start();
        }
        this.mLastState = i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleShowHelp(CharSequence charSequence) {
        updateFingerprintIcon(2);
        this.mHandler.removeMessages(4);
        TextView textView = this.mErrorText;
        if (textView != null) {
            textView.setTextColor(this.mErrorColor);
            this.mErrorText.setText(charSequence);
        }
        H h = this.mHandler;
        h.sendMessageDelayed(h.obtainMessage(4), 2000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleShowError(CharSequence charSequence) {
        updateFingerprintIcon(2);
        this.mHandler.removeMessages(4);
        TextView textView = this.mErrorText;
        if (textView != null) {
            textView.setTextColor(this.mErrorColor);
            this.mErrorText.setText(charSequence);
        }
        H h = this.mHandler;
        h.sendMessageDelayed(h.obtainMessage(3), getHideDialogDelay(this.mContext));
    }

    private void dismissAfterDelay(CharSequence charSequence) {
        TextView textView = this.mErrorText;
        if (textView != null) {
            textView.setTextColor(this.mErrorColor);
            if (charSequence != null) {
                this.mErrorText.setText(charSequence);
            } else {
                this.mErrorText.setText(R.string.fingerprint_error_lockout);
            }
        }
        this.mHandler.postDelayed(new Runnable() { // from class: androidx.biometric.FingerprintDialogFragment.3
            @Override // java.lang.Runnable
            public void run() {
                FingerprintDialogFragment.this.dismissSafely();
            }
        }, getHideDialogDelay(this.mContext));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDismissDialogError(CharSequence charSequence) {
        if (this.mDismissInstantly) {
            dismissSafely();
        } else {
            dismissAfterDelay(charSequence);
        }
        this.mDismissInstantly = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleResetMessage() {
        updateFingerprintIcon(1);
        TextView textView = this.mErrorText;
        if (textView != null) {
            textView.setTextColor(this.mTextColor);
            this.mErrorText.setText(this.mContext.getString(R.string.fingerprint_dialog_touch_sensor));
        }
    }
}

package androidx.biometric;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;

/* loaded from: classes.dex */
public class DeviceCredentialHandlerActivity extends AppCompatActivity {
    static final String EXTRA_PROMPT_INFO_BUNDLE = "prompt_info_bundle";
    private static final String KEY_DID_CHANGE_CONFIGURATION = "did_change_configuration";
    private static final String TAG = "DeviceCredentialHandler";
    private boolean mDidChangeConfiguration;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        DeviceCredentialHandlerBridge deviceCredentialHandlerBridge = DeviceCredentialHandlerBridge.getInstance();
        if (deviceCredentialHandlerBridge.getClientThemeResId() != 0) {
            setTheme(deviceCredentialHandlerBridge.getClientThemeResId());
            getTheme().applyStyle(R.style.TransparentStyle, true);
        }
        super.onCreate(bundle);
        boolean z = bundle != null && bundle.getBoolean(KEY_DID_CHANGE_CONFIGURATION, false);
        this.mDidChangeConfiguration = z;
        if (!z) {
            deviceCredentialHandlerBridge.stopIgnoringReset();
        } else {
            this.mDidChangeConfiguration = false;
        }
        setTitle((CharSequence) null);
        setContentView(R.layout.device_credential_handler_activity);
        if (deviceCredentialHandlerBridge.getExecutor() == null || deviceCredentialHandlerBridge.getAuthenticationCallback() == null) {
            Log.e(TAG, "onCreate: Executor and/or callback was null!");
            finish();
        } else {
            new BiometricPrompt(this, deviceCredentialHandlerBridge.getExecutor(), deviceCredentialHandlerBridge.getAuthenticationCallback()).authenticate(new BiometricPrompt.PromptInfo(getIntent().getBundleExtra(EXTRA_PROMPT_INFO_BUNDLE)));
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
        DeviceCredentialHandlerBridge instanceIfNotNull = DeviceCredentialHandlerBridge.getInstanceIfNotNull();
        if (!isChangingConfigurations() || instanceIfNotNull == null) {
            return;
        }
        instanceIfNotNull.ignoreNextReset();
        this.mDidChangeConfiguration = true;
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean(KEY_DID_CHANGE_CONFIGURATION, this.mDidChangeConfiguration);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        handleDeviceCredentialResult(i2);
    }

    void handleDeviceCredentialResult(int i) {
        DeviceCredentialHandlerBridge instanceIfNotNull = DeviceCredentialHandlerBridge.getInstanceIfNotNull();
        if (instanceIfNotNull == null) {
            Log.e(TAG, "onActivityResult: Bridge or callback was null!");
        } else if (i == -1) {
            instanceIfNotNull.setDeviceCredentialResult(1);
            instanceIfNotNull.setConfirmingDeviceCredential(false);
            instanceIfNotNull.startIgnoringReset();
        } else {
            instanceIfNotNull.setDeviceCredentialResult(2);
            instanceIfNotNull.setConfirmingDeviceCredential(false);
            instanceIfNotNull.startIgnoringReset();
        }
        finish();
    }
}

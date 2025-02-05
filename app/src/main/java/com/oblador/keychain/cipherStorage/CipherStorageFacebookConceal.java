package com.oblador.keychain.cipherStorage;

import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyInfo;
import android.util.Log;
import com.facebook.android.crypto.keychain.AndroidConceal;
import com.facebook.android.crypto.keychain.SharedPrefsBackedKeyChain;
import com.facebook.crypto.Crypto;
import com.facebook.crypto.CryptoConfig;
import com.facebook.crypto.Entity;
import com.facebook.react.bridge.AssertionException;
import com.facebook.react.bridge.ReactApplicationContext;
import com.oblador.keychain.KeychainModule;
import com.oblador.keychain.SecurityLevel;
import com.oblador.keychain.cipherStorage.CipherStorage;
import com.oblador.keychain.exceptions.CryptoFailedException;
import java.security.GeneralSecurityException;
import java.security.Key;

/* loaded from: classes2.dex */
public class CipherStorageFacebookConceal extends CipherStorageBase {
    public static final String KEYCHAIN_DATA = "RN_KEYCHAIN";
    private final Crypto crypto;

    @Override // com.oblador.keychain.cipherStorage.CipherStorage
    public String getCipherStorageName() {
        return KeychainModule.KnownCiphers.FB;
    }

    @Override // com.oblador.keychain.cipherStorage.CipherStorage
    public int getMinSupportedApiLevel() {
        return 16;
    }

    @Override // com.oblador.keychain.cipherStorage.CipherStorage
    public boolean isBiometrySupported() {
        return false;
    }

    @Override // com.oblador.keychain.cipherStorage.CipherStorageBase, com.oblador.keychain.cipherStorage.CipherStorage
    public boolean supportsSecureHardware() {
        return false;
    }

    public CipherStorageFacebookConceal(ReactApplicationContext reactApplicationContext) {
        this.crypto = AndroidConceal.get().createDefaultCrypto(new SharedPrefsBackedKeyChain(reactApplicationContext, CryptoConfig.KEY_256));
    }

    @Override // com.oblador.keychain.cipherStorage.CipherStorageBase, com.oblador.keychain.cipherStorage.CipherStorage
    public SecurityLevel securityLevel() {
        return SecurityLevel.ANY;
    }

    @Override // com.oblador.keychain.cipherStorage.CipherStorage
    public CipherStorage.EncryptionResult encrypt(String str, String str2, String str3, SecurityLevel securityLevel) throws CryptoFailedException {
        throwIfInsufficientLevel(securityLevel);
        throwIfNoCryptoAvailable();
        try {
            return new CipherStorage.EncryptionResult(this.crypto.encrypt(str2.getBytes(UTF8), createUsernameEntity(str)), this.crypto.encrypt(str3.getBytes(UTF8), createPasswordEntity(str)), this);
        } catch (Throwable th) {
            throw new CryptoFailedException("Encryption failed for alias: " + str, th);
        }
    }

    @Override // com.oblador.keychain.cipherStorage.CipherStorage
    public CipherStorage.DecryptionResult decrypt(String str, byte[] bArr, byte[] bArr2, SecurityLevel securityLevel) throws CryptoFailedException {
        throwIfInsufficientLevel(securityLevel);
        throwIfNoCryptoAvailable();
        try {
            return new CipherStorage.DecryptionResult(new String(this.crypto.decrypt(bArr, createUsernameEntity(str)), UTF8), new String(this.crypto.decrypt(bArr2, createPasswordEntity(str)), UTF8), SecurityLevel.ANY);
        } catch (Throwable th) {
            throw new CryptoFailedException("Decryption failed for alias: " + str, th);
        }
    }

    @Override // com.oblador.keychain.cipherStorage.CipherStorage
    public void decrypt(CipherStorage.DecryptionResultHandler decryptionResultHandler, String str, byte[] bArr, byte[] bArr2, SecurityLevel securityLevel) {
        try {
            decryptionResultHandler.onDecrypt(decrypt(str, bArr, bArr2, securityLevel), null);
        } catch (Throwable th) {
            decryptionResultHandler.onDecrypt(null, th);
        }
    }

    @Override // com.oblador.keychain.cipherStorage.CipherStorageBase, com.oblador.keychain.cipherStorage.CipherStorage
    public void removeKey(String str) {
        Log.w(LOG_TAG, "CipherStorageFacebookConceal removeKey called. alias: " + str);
    }

    @Override // com.oblador.keychain.cipherStorage.CipherStorageBase
    protected KeyGenParameterSpec.Builder getKeyGenSpecBuilder(String str) throws GeneralSecurityException {
        throw new CryptoFailedException("Not designed for a call");
    }

    @Override // com.oblador.keychain.cipherStorage.CipherStorageBase
    protected KeyInfo getKeyInfo(Key key) throws GeneralSecurityException {
        throw new CryptoFailedException("Not designed for a call");
    }

    @Override // com.oblador.keychain.cipherStorage.CipherStorageBase
    protected Key generateKey(KeyGenParameterSpec keyGenParameterSpec) throws GeneralSecurityException {
        throw new CryptoFailedException("Not designed for a call");
    }

    @Override // com.oblador.keychain.cipherStorage.CipherStorageBase
    protected String getEncryptionAlgorithm() {
        throw new AssertionException("Not designed for a call");
    }

    @Override // com.oblador.keychain.cipherStorage.CipherStorageBase
    protected String getEncryptionTransformation() {
        throw new AssertionException("Not designed for a call");
    }

    private void throwIfNoCryptoAvailable() throws CryptoFailedException {
        if (!this.crypto.isAvailable()) {
            throw new CryptoFailedException("Crypto is missing");
        }
    }

    private static Entity createUsernameEntity(String str) {
        return Entity.create(getEntityPrefix(str) + "user");
    }

    private static Entity createPasswordEntity(String str) {
        return Entity.create(getEntityPrefix(str) + "pass");
    }

    private static String getEntityPrefix(String str) {
        return "RN_KEYCHAIN:" + str;
    }
}

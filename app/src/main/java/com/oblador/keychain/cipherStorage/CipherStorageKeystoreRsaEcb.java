package com.oblador.keychain.cipherStorage;

import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyInfo;
import android.security.keystore.UserNotAuthenticatedException;
import android.util.Log;
import com.oblador.keychain.KeychainModule;
import com.oblador.keychain.SecurityLevel;
import com.oblador.keychain.cipherStorage.CipherStorage;
import com.oblador.keychain.exceptions.CryptoFailedException;
import com.oblador.keychain.exceptions.KeyStoreAccessException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.concurrent.atomic.AtomicInteger;
import javax.crypto.NoSuchPaddingException;

/* loaded from: classes2.dex */
public class CipherStorageKeystoreRsaEcb extends CipherStorageBase {
    public static final String ALGORITHM_RSA = "RSA";
    public static final String BLOCK_MODE_ECB = "ECB";
    public static final int ENCRYPTION_KEY_SIZE = 3072;
    public static final String PADDING_PKCS1 = "PKCS1Padding";
    public static final String TRANSFORMATION_RSA_ECB_PKCS1 = "RSA/ECB/PKCS1Padding";

    @Override // com.oblador.keychain.cipherStorage.CipherStorage
    public String getCipherStorageName() {
        return KeychainModule.KnownCiphers.RSA;
    }

    @Override // com.oblador.keychain.cipherStorage.CipherStorageBase
    protected String getEncryptionAlgorithm() {
        return ALGORITHM_RSA;
    }

    @Override // com.oblador.keychain.cipherStorage.CipherStorageBase
    protected String getEncryptionTransformation() {
        return TRANSFORMATION_RSA_ECB_PKCS1;
    }

    @Override // com.oblador.keychain.cipherStorage.CipherStorage
    public int getMinSupportedApiLevel() {
        return 23;
    }

    @Override // com.oblador.keychain.cipherStorage.CipherStorage
    public boolean isBiometrySupported() {
        return true;
    }

    @Override // com.oblador.keychain.cipherStorage.CipherStorage
    public CipherStorage.EncryptionResult encrypt(String str, String str2, String str3, SecurityLevel securityLevel) throws CryptoFailedException {
        throwIfInsufficientLevel(securityLevel);
        try {
            return innerEncryptedCredentials(getDefaultAliasIfEmpty(str, getDefaultAliasServiceName()), str3, str2, securityLevel);
        } catch (KeyStoreAccessException e) {
            e = e;
            throw new CryptoFailedException("Could not access Keystore for service " + str, e);
        } catch (IOException e2) {
            throw new CryptoFailedException("I/O error: " + e2.getMessage(), e2);
        } catch (InvalidKeyException e3) {
            e = e3;
            throw new CryptoFailedException("Could not encrypt data for service " + str, e);
        } catch (KeyStoreException e4) {
            e = e4;
            throw new CryptoFailedException("Could not access Keystore for service " + str, e);
        } catch (NoSuchAlgorithmException e5) {
            e = e5;
            throw new CryptoFailedException("Could not encrypt data for service " + str, e);
        } catch (InvalidKeySpecException e6) {
            e = e6;
            throw new CryptoFailedException("Could not encrypt data for service " + str, e);
        } catch (NoSuchPaddingException e7) {
            e = e7;
            throw new CryptoFailedException("Could not encrypt data for service " + str, e);
        } catch (Throwable th) {
            throw new CryptoFailedException("Unknown error: " + th.getMessage(), th);
        }
    }

    @Override // com.oblador.keychain.cipherStorage.CipherStorage
    public CipherStorage.DecryptionResult decrypt(String str, byte[] bArr, byte[] bArr2, SecurityLevel securityLevel) throws CryptoFailedException {
        NonInteractiveHandler nonInteractiveHandler = new NonInteractiveHandler();
        decrypt(nonInteractiveHandler, str, bArr, bArr2, securityLevel);
        CryptoFailedException.reThrowOnError(nonInteractiveHandler.getError());
        if (nonInteractiveHandler.getResult() == null) {
            throw new CryptoFailedException("No decryption results and no error. Something deeply wrong!");
        }
        return nonInteractiveHandler.getResult();
    }

    @Override // com.oblador.keychain.cipherStorage.CipherStorage
    public void decrypt(CipherStorage.DecryptionResultHandler decryptionResultHandler, String str, byte[] bArr, byte[] bArr2, SecurityLevel securityLevel) throws CryptoFailedException {
        throwIfInsufficientLevel(securityLevel);
        String defaultAliasIfEmpty = getDefaultAliasIfEmpty(str, getDefaultAliasServiceName());
        Key key = null;
        try {
            try {
                Key extractGeneratedKey = extractGeneratedKey(defaultAliasIfEmpty, securityLevel, new AtomicInteger(1));
                try {
                    decryptionResultHandler.onDecrypt(new CipherStorage.DecryptionResult(decryptBytes(extractGeneratedKey, bArr), decryptBytes(extractGeneratedKey, bArr2)), null);
                } catch (UserNotAuthenticatedException e) {
                    e = e;
                    key = extractGeneratedKey;
                    Log.d(LOG_TAG, "Unlock of keystore is needed. Error: " + e.getMessage(), e);
                    decryptionResultHandler.askAccessPermissions(new CipherStorage.DecryptionContext(defaultAliasIfEmpty, key, bArr2, bArr));
                }
            } catch (Throwable th) {
                decryptionResultHandler.onDecrypt(null, th);
            }
        } catch (UserNotAuthenticatedException e2) {
            e = e2;
        }
    }

    private CipherStorage.EncryptionResult innerEncryptedCredentials(String str, String str2, String str3, SecurityLevel securityLevel) throws GeneralSecurityException, IOException {
        KeyStore keyStoreAndLoad = getKeyStoreAndLoad();
        if (!keyStoreAndLoad.containsAlias(str)) {
            generateKeyAndStoreUnderAlias(str, securityLevel);
        }
        PublicKey generatePublic = KeyFactory.getInstance(ALGORITHM_RSA).generatePublic(new X509EncodedKeySpec(keyStoreAndLoad.getCertificate(str).getPublicKey().getEncoded()));
        return new CipherStorage.EncryptionResult(encryptString(generatePublic, str3), encryptString(generatePublic, str2), this);
    }

    @Override // com.oblador.keychain.cipherStorage.CipherStorageBase
    protected KeyGenParameterSpec.Builder getKeyGenSpecBuilder(String str) throws GeneralSecurityException {
        if (Build.VERSION.SDK_INT < 23) {
            throw new KeyStoreAccessException("Unsupported API" + Build.VERSION.SDK_INT + " version detected.");
        }
        return new KeyGenParameterSpec.Builder(str, 3).setBlockModes(BLOCK_MODE_ECB).setEncryptionPaddings(PADDING_PKCS1).setRandomizedEncryptionRequired(true).setUserAuthenticationRequired(true).setUserAuthenticationValidityDurationSeconds(1).setKeySize(ENCRYPTION_KEY_SIZE);
    }

    @Override // com.oblador.keychain.cipherStorage.CipherStorageBase
    protected KeyInfo getKeyInfo(Key key) throws GeneralSecurityException {
        if (Build.VERSION.SDK_INT < 23) {
            throw new KeyStoreAccessException("Unsupported API" + Build.VERSION.SDK_INT + " version detected.");
        }
        return (KeyInfo) KeyFactory.getInstance(key.getAlgorithm(), CipherStorageBase.KEYSTORE_TYPE).getKeySpec(key, KeyInfo.class);
    }

    @Override // com.oblador.keychain.cipherStorage.CipherStorageBase
    protected Key generateKey(KeyGenParameterSpec keyGenParameterSpec) throws GeneralSecurityException {
        if (Build.VERSION.SDK_INT < 23) {
            throw new KeyStoreAccessException("Unsupported API" + Build.VERSION.SDK_INT + " version detected.");
        }
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(getEncryptionAlgorithm(), CipherStorageBase.KEYSTORE_TYPE);
        keyPairGenerator.initialize(keyGenParameterSpec);
        return keyPairGenerator.generateKeyPair().getPrivate();
    }

    public static class NonInteractiveHandler implements CipherStorage.DecryptionResultHandler {
        private Throwable error;
        private CipherStorage.DecryptionResult result;

        @Override // com.oblador.keychain.cipherStorage.CipherStorage.WithResults
        public void waitResult() {
        }

        @Override // com.oblador.keychain.cipherStorage.CipherStorage.DecryptionResultHandler
        public void askAccessPermissions(CipherStorage.DecryptionContext decryptionContext) {
            onDecrypt(null, new CryptoFailedException("Non interactive decryption mode."));
        }

        @Override // com.oblador.keychain.cipherStorage.CipherStorage.DecryptionResultHandler
        public void onDecrypt(CipherStorage.DecryptionResult decryptionResult, Throwable th) {
            this.result = decryptionResult;
            this.error = th;
        }

        @Override // com.oblador.keychain.cipherStorage.CipherStorage.WithResults
        public CipherStorage.DecryptionResult getResult() {
            return this.result;
        }

        @Override // com.oblador.keychain.cipherStorage.CipherStorage.WithResults
        public Throwable getError() {
            return this.error;
        }
    }
}

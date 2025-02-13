package com.google.android.gms.iid;

import com.oblador.keychain.cipherStorage.CipherStorageKeystoreRsaEcb;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

/* loaded from: classes2.dex */
public final class zzd {
    public static KeyPair zzl() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(CipherStorageKeystoreRsaEcb.ALGORITHM_RSA);
            keyPairGenerator.initialize(2048);
            return keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError(e);
        }
    }
}

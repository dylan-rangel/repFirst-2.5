package com.facebook.crypto.keygen;

import com.facebook.crypto.exception.CryptoInitializationException;
import com.facebook.crypto.util.NativeCryptoLibrary;
import java.security.SecureRandom;

/* loaded from: classes.dex */
public class PasswordBasedKeyDerivation {
    public static final int DEFAULT_ITERATIONS = 4096;
    public static final int DEFAULT_KEY_LENGTH = 16;
    private static final int DEFAULT_SALT_LENGTH = 16;
    public static final int MINIMUM_ITERATIONS = 1;
    public static final int MINIMUM_KEY_LENGTH = 8;
    private static final int MINIMUM_SALT_LENGTH = 4;
    private byte[] mGeneratedKey;
    private int mIterations = 4096;
    private int mKeyLengthInBytes = 16;
    private final NativeCryptoLibrary mNativeLibrary;
    private String mPassword;
    private byte[] mSalt;
    private final SecureRandom mSecureRandom;

    private native int nativePbkdf2(String str, byte[] bArr, int i, byte[] bArr2);

    public PasswordBasedKeyDerivation(SecureRandom secureRandom, NativeCryptoLibrary nativeCryptoLibrary) {
        this.mSecureRandom = secureRandom;
        this.mNativeLibrary = nativeCryptoLibrary;
    }

    public PasswordBasedKeyDerivation setIterations(int i) {
        if (i < 1) {
            throw new IllegalArgumentException("Iterations cannot be less than 1");
        }
        this.mIterations = i;
        return this;
    }

    public PasswordBasedKeyDerivation setPassword(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }
        this.mPassword = str;
        return this;
    }

    public PasswordBasedKeyDerivation setSalt(byte[] bArr) {
        if (bArr != null && bArr.length < 4) {
            throw new IllegalArgumentException("Salt cannot be shorter than 8 bytes");
        }
        this.mSalt = bArr;
        return this;
    }

    public PasswordBasedKeyDerivation setKeyLengthInBytes(int i) {
        if (i < 8) {
            throw new IllegalArgumentException("Key length cannot be less than 8 bytes");
        }
        this.mKeyLengthInBytes = i;
        return this;
    }

    public byte[] generate() throws CryptoInitializationException {
        if (this.mPassword == null) {
            throw new IllegalStateException("Password was not set");
        }
        if (this.mSalt == null) {
            byte[] bArr = new byte[16];
            this.mSalt = bArr;
            this.mSecureRandom.nextBytes(bArr);
        }
        this.mGeneratedKey = new byte[this.mKeyLengthInBytes];
        this.mNativeLibrary.ensureCryptoLoaded();
        if (nativePbkdf2(this.mPassword, this.mSalt, this.mIterations, this.mGeneratedKey) != 1) {
            throw new RuntimeException("Native PBKDF2 failed...");
        }
        return this.mGeneratedKey;
    }

    public int getIterations() {
        return this.mIterations;
    }

    public String getPassword() {
        return this.mPassword;
    }

    public byte[] getSalt() {
        return this.mSalt;
    }

    public int getKeyLengthInBytes() {
        return this.mKeyLengthInBytes;
    }

    public byte[] getGeneratedKey() {
        return this.mGeneratedKey;
    }
}

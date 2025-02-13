package com.facebook.crypto;

import com.facebook.crypto.cipher.NativeGCMCipher;
import com.facebook.crypto.cipher.NativeGCMCipherException;
import com.facebook.crypto.exception.CryptoInitializationException;
import com.facebook.crypto.exception.KeyChainException;
import com.facebook.crypto.keychain.KeyChain;
import com.facebook.crypto.streams.NativeGCMCipherInputStream;
import com.facebook.crypto.streams.NativeGCMCipherOutputStream;
import com.facebook.crypto.util.Assertions;
import com.facebook.crypto.util.NativeCryptoLibrary;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: classes.dex */
public class CryptoAlgoGcm implements CryptoAlgo {
    private final CryptoConfig mConfig;
    private final KeyChain mKeyChain;
    private final NativeCryptoLibrary mNativeLibrary;

    public CryptoAlgoGcm(NativeCryptoLibrary nativeCryptoLibrary, KeyChain keyChain, CryptoConfig cryptoConfig) {
        this.mNativeLibrary = nativeCryptoLibrary;
        this.mKeyChain = keyChain;
        this.mConfig = cryptoConfig;
    }

    @Override // com.facebook.crypto.CryptoAlgo
    public OutputStream wrap(OutputStream outputStream, Entity entity, byte[] bArr) throws IOException, CryptoInitializationException, KeyChainException {
        outputStream.write(1);
        outputStream.write(this.mConfig.cipherId);
        byte[] newIV = this.mKeyChain.getNewIV();
        NativeGCMCipher nativeGCMCipher = new NativeGCMCipher(this.mNativeLibrary);
        nativeGCMCipher.encryptInit(this.mKeyChain.getCipherKey(), newIV);
        outputStream.write(newIV);
        computeCipherAad(nativeGCMCipher, (byte) 1, this.mConfig.cipherId, entity.getBytes());
        return new NativeGCMCipherOutputStream(outputStream, nativeGCMCipher, bArr, this.mConfig.tagLength);
    }

    @Override // com.facebook.crypto.CryptoAlgo
    public InputStream wrap(InputStream inputStream, Entity entity) throws IOException, CryptoInitializationException, KeyChainException {
        byte read = (byte) inputStream.read();
        byte read2 = (byte) inputStream.read();
        Assertions.checkArgumentForIO(read == 1, "Unexpected crypto version " + ((int) read));
        Assertions.checkArgumentForIO(read2 == this.mConfig.cipherId, "Unexpected cipher ID " + ((int) read2));
        byte[] bArr = new byte[this.mConfig.ivLength];
        new DataInputStream(inputStream).readFully(bArr);
        NativeGCMCipher nativeGCMCipher = new NativeGCMCipher(this.mNativeLibrary);
        nativeGCMCipher.decryptInit(this.mKeyChain.getCipherKey(), bArr);
        computeCipherAad(nativeGCMCipher, read, read2, entity.getBytes());
        return new NativeGCMCipherInputStream(inputStream, nativeGCMCipher, this.mConfig.tagLength);
    }

    private void computeCipherAad(NativeGCMCipher nativeGCMCipher, byte b, byte b2, byte[] bArr) throws NativeGCMCipherException {
        nativeGCMCipher.updateAad(new byte[]{b}, 1);
        nativeGCMCipher.updateAad(new byte[]{b2}, 1);
        nativeGCMCipher.updateAad(bArr, bArr.length);
    }

    @Override // com.facebook.crypto.CryptoAlgo
    public int getCipherMetaDataLength() {
        return this.mConfig.ivLength + 2 + this.mConfig.tagLength;
    }
}

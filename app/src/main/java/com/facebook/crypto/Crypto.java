package com.facebook.crypto;

import com.facebook.crypto.exception.CryptoInitializationException;
import com.facebook.crypto.exception.KeyChainException;
import com.facebook.crypto.keychain.KeyChain;
import com.facebook.crypto.mac.NativeMac;
import com.facebook.crypto.streams.FixedSizeByteArrayOutputStream;
import com.facebook.crypto.streams.NativeMacLayeredInputStream;
import com.facebook.crypto.streams.NativeMacLayeredOutputStream;
import com.facebook.crypto.util.Assertions;
import com.facebook.crypto.util.NativeCryptoLibrary;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: classes.dex */
public class Crypto {
    private final CryptoAlgo mCryptoAlgo;
    private final KeyChain mKeyChain;
    private final NativeCryptoLibrary mNativeCryptoLibrary;

    @Deprecated
    public Crypto(KeyChain keyChain, NativeCryptoLibrary nativeCryptoLibrary) {
        this(keyChain, nativeCryptoLibrary, CryptoConfig.KEY_128);
    }

    public Crypto(KeyChain keyChain, NativeCryptoLibrary nativeCryptoLibrary, CryptoConfig cryptoConfig) {
        CheckedKeyChain checkedKeyChain = new CheckedKeyChain(keyChain, cryptoConfig);
        this.mKeyChain = checkedKeyChain;
        this.mNativeCryptoLibrary = nativeCryptoLibrary;
        this.mCryptoAlgo = new CryptoAlgoGcm(nativeCryptoLibrary, checkedKeyChain, cryptoConfig);
    }

    public boolean isAvailable() {
        try {
            this.mNativeCryptoLibrary.ensureCryptoLoaded();
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }

    public OutputStream getCipherOutputStream(OutputStream outputStream, Entity entity) throws IOException, CryptoInitializationException, KeyChainException {
        return getCipherOutputStream(outputStream, entity, null);
    }

    public OutputStream getCipherOutputStream(OutputStream outputStream, Entity entity, byte[] bArr) throws IOException, CryptoInitializationException, KeyChainException {
        return this.mCryptoAlgo.wrap(outputStream, entity, bArr);
    }

    public InputStream getCipherInputStream(InputStream inputStream, Entity entity) throws IOException, CryptoInitializationException, KeyChainException {
        return this.mCryptoAlgo.wrap(inputStream, entity);
    }

    public byte[] encrypt(byte[] bArr, Entity entity) throws KeyChainException, CryptoInitializationException, IOException {
        FixedSizeByteArrayOutputStream fixedSizeByteArrayOutputStream = new FixedSizeByteArrayOutputStream(bArr.length + getCipherMetaDataLength());
        OutputStream cipherOutputStream = getCipherOutputStream(fixedSizeByteArrayOutputStream, entity, null);
        cipherOutputStream.write(bArr);
        cipherOutputStream.close();
        return fixedSizeByteArrayOutputStream.getBytes();
    }

    public byte[] decrypt(byte[] bArr, Entity entity) throws KeyChainException, CryptoInitializationException, IOException {
        int length = bArr.length;
        InputStream cipherInputStream = getCipherInputStream(new ByteArrayInputStream(bArr), entity);
        FixedSizeByteArrayOutputStream fixedSizeByteArrayOutputStream = new FixedSizeByteArrayOutputStream(length - getCipherMetaDataLength());
        byte[] bArr2 = new byte[1024];
        while (true) {
            int read = cipherInputStream.read(bArr2);
            if (read != -1) {
                fixedSizeByteArrayOutputStream.write(bArr2, 0, read);
            } else {
                cipherInputStream.close();
                return fixedSizeByteArrayOutputStream.getBytes();
            }
        }
    }

    public OutputStream getMacOutputStream(OutputStream outputStream, Entity entity) throws IOException, KeyChainException, CryptoInitializationException {
        outputStream.write(1);
        outputStream.write(1);
        NativeMac nativeMac = new NativeMac(this.mNativeCryptoLibrary);
        byte[] macKey = this.mKeyChain.getMacKey();
        nativeMac.init(macKey, macKey.length);
        computeMacAad(nativeMac, (byte) 1, (byte) 1, entity.getBytes());
        return new NativeMacLayeredOutputStream(nativeMac, outputStream);
    }

    public InputStream getMacInputStream(InputStream inputStream, Entity entity) throws IOException, KeyChainException, CryptoInitializationException {
        byte read = (byte) inputStream.read();
        Assertions.checkArgumentForIO(read == 1, "Unexpected mac version " + ((int) read));
        byte read2 = (byte) inputStream.read();
        Assertions.checkArgumentForIO(read2 == 1, "Unexpected mac ID " + ((int) read2));
        NativeMac nativeMac = new NativeMac(this.mNativeCryptoLibrary);
        byte[] macKey = this.mKeyChain.getMacKey();
        nativeMac.init(macKey, macKey.length);
        computeMacAad(nativeMac, read, (byte) 1, entity.getBytes());
        return new NativeMacLayeredInputStream(nativeMac, inputStream);
    }

    private static void computeMacAad(NativeMac nativeMac, byte b, byte b2, byte[] bArr) throws IOException {
        nativeMac.update(new byte[]{b}, 0, 1);
        nativeMac.update(new byte[]{b2}, 0, 1);
        nativeMac.update(bArr, 0, bArr.length);
    }

    int getCipherMetaDataLength() {
        return this.mCryptoAlgo.getCipherMetaDataLength();
    }
}

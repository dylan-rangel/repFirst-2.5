package com.facebook.crypto.keychain;

import com.facebook.crypto.exception.KeyChainException;

/* loaded from: classes.dex */
public interface KeyChain {
    void destroyKeys();

    byte[] getCipherKey() throws KeyChainException;

    byte[] getMacKey() throws KeyChainException;

    byte[] getNewIV() throws KeyChainException;
}

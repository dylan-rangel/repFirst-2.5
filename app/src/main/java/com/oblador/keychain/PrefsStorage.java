package com.oblador.keychain;

import android.content.SharedPreferences;
import android.util.Base64;
import com.facebook.react.bridge.ReactApplicationContext;
import com.oblador.keychain.KeychainModule;
import com.oblador.keychain.cipherStorage.CipherStorage;

/* loaded from: classes2.dex */
public class PrefsStorage {
    public static final String KEYCHAIN_DATA = "RN_KEYCHAIN";
    private final SharedPreferences prefs;

    public static class ResultSet extends CipherStorage.CipherResult<byte[]> {
        public final String cipherStorageName;

        public ResultSet(String str, byte[] bArr, byte[] bArr2) {
            super(bArr, bArr2);
            this.cipherStorageName = str;
        }
    }

    public PrefsStorage(ReactApplicationContext reactApplicationContext) {
        this.prefs = reactApplicationContext.getSharedPreferences("RN_KEYCHAIN", 0);
    }

    public ResultSet getEncryptedEntry(String str) {
        byte[] bytesForUsername = getBytesForUsername(str);
        byte[] bytesForPassword = getBytesForPassword(str);
        String cipherStorageName = getCipherStorageName(str);
        if (bytesForUsername == null || bytesForPassword == null) {
            return null;
        }
        if (cipherStorageName == null) {
            cipherStorageName = KeychainModule.KnownCiphers.FB;
        }
        return new ResultSet(cipherStorageName, bytesForUsername, bytesForPassword);
    }

    public void removeEntry(String str) {
        String keyForUsername = getKeyForUsername(str);
        String keyForPassword = getKeyForPassword(str);
        this.prefs.edit().remove(keyForUsername).remove(keyForPassword).remove(getKeyForCipherStorage(str)).apply();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void storeEncryptedEntry(String str, CipherStorage.EncryptionResult encryptionResult) {
        String keyForUsername = getKeyForUsername(str);
        String keyForPassword = getKeyForPassword(str);
        this.prefs.edit().putString(keyForUsername, Base64.encodeToString((byte[]) encryptionResult.username, 0)).putString(keyForPassword, Base64.encodeToString((byte[]) encryptionResult.password, 0)).putString(getKeyForCipherStorage(str), encryptionResult.cipherName).apply();
    }

    private byte[] getBytesForUsername(String str) {
        return getBytes(getKeyForUsername(str));
    }

    private byte[] getBytesForPassword(String str) {
        return getBytes(getKeyForPassword(str));
    }

    private String getCipherStorageName(String str) {
        return this.prefs.getString(getKeyForCipherStorage(str), null);
    }

    public static String getKeyForUsername(String str) {
        return str + ":u";
    }

    public static String getKeyForPassword(String str) {
        return str + ":p";
    }

    public static String getKeyForCipherStorage(String str) {
        return str + ":c";
    }

    private byte[] getBytes(String str) {
        String string = this.prefs.getString(str, null);
        if (string != null) {
            return Base64.decode(string, 0);
        }
        return null;
    }
}

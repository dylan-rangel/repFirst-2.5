package com.oblador.keychain;

/* loaded from: classes2.dex */
public enum SecurityLevel {
    ANY,
    SECURE_SOFTWARE,
    SECURE_HARDWARE;

    public String jsName() {
        return String.format("SECURITY_LEVEL_%s", name());
    }

    public boolean satisfiesSafetyThreshold(SecurityLevel securityLevel) {
        return compareTo(securityLevel) >= 0;
    }
}

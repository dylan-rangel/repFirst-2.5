package net.openid.appauth;

import java.util.Map;

/* loaded from: classes3.dex */
public interface ClientAuthentication {
    Map<String, String> getRequestHeaders(String clientId);

    Map<String, String> getRequestParameters(String clientId);

    public static class UnsupportedAuthenticationMethod extends Exception {
        private String mAuthMethod;

        public UnsupportedAuthenticationMethod(String field) {
            super("Unsupported client authentication method: " + field);
            this.mAuthMethod = field;
        }

        public String getUnsupportedAuthenticationMethod() {
            return this.mAuthMethod;
        }
    }
}

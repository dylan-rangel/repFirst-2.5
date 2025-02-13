package net.openid.appauth;

import android.net.Uri;
import android.text.TextUtils;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class TokenRequest {
    public static final String GRANT_TYPE_CLIENT_CREDENTIALS = "client_credentials";
    public static final String GRANT_TYPE_PASSWORD = "password";
    static final String KEY_ADDITIONAL_PARAMETERS = "additionalParameters";
    static final String KEY_AUTHORIZATION_CODE = "authorizationCode";
    static final String KEY_CLIENT_ID = "clientId";
    static final String KEY_CODE_VERIFIER = "codeVerifier";
    static final String KEY_CONFIGURATION = "configuration";
    static final String KEY_GRANT_TYPE = "grantType";
    static final String KEY_NONCE = "nonce";
    static final String KEY_REDIRECT_URI = "redirectUri";
    static final String KEY_REFRESH_TOKEN = "refreshToken";
    static final String KEY_SCOPE = "scope";
    static final String PARAM_CODE = "code";
    static final String PARAM_REFRESH_TOKEN = "refresh_token";
    static final String PARAM_SCOPE = "scope";
    public final Map<String, String> additionalParameters;
    public final String authorizationCode;
    public final String clientId;
    public final String codeVerifier;
    public final AuthorizationServiceConfiguration configuration;
    public final String grantType;
    public final String nonce;
    public final Uri redirectUri;
    public final String refreshToken;
    public final String scope;
    public static final String PARAM_CLIENT_ID = "client_id";
    static final String PARAM_CODE_VERIFIER = "code_verifier";
    static final String PARAM_GRANT_TYPE = "grant_type";
    static final String PARAM_REDIRECT_URI = "redirect_uri";
    private static final Set<String> BUILT_IN_PARAMS = Collections.unmodifiableSet(new HashSet(Arrays.asList(PARAM_CLIENT_ID, "code", PARAM_CODE_VERIFIER, PARAM_GRANT_TYPE, PARAM_REDIRECT_URI, "refresh_token", "scope")));

    public static final class Builder {
        private Map<String, String> mAdditionalParameters;
        private String mAuthorizationCode;
        private String mClientId;
        private String mCodeVerifier;
        private AuthorizationServiceConfiguration mConfiguration;
        private String mGrantType;
        private String mNonce;
        private Uri mRedirectUri;
        private String mRefreshToken;
        private String mScope;

        public Builder(AuthorizationServiceConfiguration configuration, String clientId) {
            setConfiguration(configuration);
            setClientId(clientId);
            this.mAdditionalParameters = new LinkedHashMap();
        }

        public Builder setConfiguration(AuthorizationServiceConfiguration configuration) {
            this.mConfiguration = (AuthorizationServiceConfiguration) Preconditions.checkNotNull(configuration);
            return this;
        }

        public Builder setClientId(String clientId) {
            this.mClientId = Preconditions.checkNotEmpty(clientId, "clientId cannot be null or empty");
            return this;
        }

        public Builder setNonce(String nonce) {
            if (TextUtils.isEmpty(nonce)) {
                this.mNonce = null;
            } else {
                this.mNonce = nonce;
            }
            return this;
        }

        public Builder setGrantType(String grantType) {
            this.mGrantType = Preconditions.checkNotEmpty(grantType, "grantType cannot be null or empty");
            return this;
        }

        public Builder setRedirectUri(Uri redirectUri) {
            if (redirectUri != null) {
                Preconditions.checkNotNull(redirectUri.getScheme(), "redirectUri must have a scheme");
            }
            this.mRedirectUri = redirectUri;
            return this;
        }

        public Builder setScope(String scope) {
            if (TextUtils.isEmpty(scope)) {
                this.mScope = null;
            } else {
                setScopes(scope.split(" +"));
            }
            return this;
        }

        public Builder setScopes(String... scopes) {
            if (scopes == null) {
                scopes = new String[0];
            }
            setScopes(Arrays.asList(scopes));
            return this;
        }

        public Builder setScopes(Iterable<String> scopes) {
            this.mScope = AsciiStringListUtil.iterableToString(scopes);
            return this;
        }

        public Builder setAuthorizationCode(String authorizationCode) {
            Preconditions.checkNullOrNotEmpty(authorizationCode, "authorization code must not be empty");
            this.mAuthorizationCode = authorizationCode;
            return this;
        }

        public Builder setRefreshToken(String refreshToken) {
            if (refreshToken != null) {
                Preconditions.checkNotEmpty(refreshToken, "refresh token cannot be empty if defined");
            }
            this.mRefreshToken = refreshToken;
            return this;
        }

        public Builder setCodeVerifier(String codeVerifier) {
            if (codeVerifier != null) {
                CodeVerifierUtil.checkCodeVerifier(codeVerifier);
            }
            this.mCodeVerifier = codeVerifier;
            return this;
        }

        public Builder setAdditionalParameters(Map<String, String> additionalParameters) {
            this.mAdditionalParameters = AdditionalParamsProcessor.checkAdditionalParams(additionalParameters, TokenRequest.BUILT_IN_PARAMS);
            return this;
        }

        public TokenRequest build() {
            String inferGrantType = inferGrantType();
            if (GrantTypeValues.AUTHORIZATION_CODE.equals(inferGrantType)) {
                Preconditions.checkNotNull(this.mAuthorizationCode, "authorization code must be specified for grant_type = authorization_code");
            }
            if ("refresh_token".equals(inferGrantType)) {
                Preconditions.checkNotNull(this.mRefreshToken, "refresh token must be specified for grant_type = refresh_token");
            }
            if (inferGrantType.equals(GrantTypeValues.AUTHORIZATION_CODE) && this.mRedirectUri == null) {
                throw new IllegalStateException("no redirect URI specified on token request for code exchange");
            }
            return new TokenRequest(this.mConfiguration, this.mClientId, this.mNonce, inferGrantType, this.mRedirectUri, this.mScope, this.mAuthorizationCode, this.mRefreshToken, this.mCodeVerifier, Collections.unmodifiableMap(this.mAdditionalParameters));
        }

        private String inferGrantType() {
            String str = this.mGrantType;
            if (str != null) {
                return str;
            }
            if (this.mAuthorizationCode != null) {
                return GrantTypeValues.AUTHORIZATION_CODE;
            }
            if (this.mRefreshToken != null) {
                return "refresh_token";
            }
            throw new IllegalStateException("grant type not specified and cannot be inferred");
        }
    }

    private TokenRequest(AuthorizationServiceConfiguration configuration, String clientId, String nonce, String grantType, Uri redirectUri, String scope, String authorizationCode, String refreshToken, String codeVerifier, Map<String, String> additionalParameters) {
        this.configuration = configuration;
        this.clientId = clientId;
        this.nonce = nonce;
        this.grantType = grantType;
        this.redirectUri = redirectUri;
        this.scope = scope;
        this.authorizationCode = authorizationCode;
        this.refreshToken = refreshToken;
        this.codeVerifier = codeVerifier;
        this.additionalParameters = additionalParameters;
    }

    public Set<String> getScopeSet() {
        return AsciiStringListUtil.stringToSet(this.scope);
    }

    public Map<String, String> getRequestParameters() {
        HashMap hashMap = new HashMap();
        hashMap.put(PARAM_GRANT_TYPE, this.grantType);
        putIfNotNull(hashMap, PARAM_REDIRECT_URI, this.redirectUri);
        putIfNotNull(hashMap, "code", this.authorizationCode);
        putIfNotNull(hashMap, "refresh_token", this.refreshToken);
        putIfNotNull(hashMap, PARAM_CODE_VERIFIER, this.codeVerifier);
        putIfNotNull(hashMap, "scope", this.scope);
        for (Map.Entry<String, String> entry : this.additionalParameters.entrySet()) {
            hashMap.put(entry.getKey(), entry.getValue());
        }
        return hashMap;
    }

    private void putIfNotNull(Map<String, String> map, String key, Object value) {
        if (value != null) {
            map.put(key, value.toString());
        }
    }

    public JSONObject jsonSerialize() {
        JSONObject jSONObject = new JSONObject();
        JsonUtil.put(jSONObject, KEY_CONFIGURATION, this.configuration.toJson());
        JsonUtil.put(jSONObject, KEY_CLIENT_ID, this.clientId);
        JsonUtil.putIfNotNull(jSONObject, KEY_NONCE, this.nonce);
        JsonUtil.put(jSONObject, KEY_GRANT_TYPE, this.grantType);
        JsonUtil.putIfNotNull(jSONObject, KEY_REDIRECT_URI, this.redirectUri);
        JsonUtil.putIfNotNull(jSONObject, "scope", this.scope);
        JsonUtil.putIfNotNull(jSONObject, KEY_AUTHORIZATION_CODE, this.authorizationCode);
        JsonUtil.putIfNotNull(jSONObject, KEY_REFRESH_TOKEN, this.refreshToken);
        JsonUtil.putIfNotNull(jSONObject, KEY_CODE_VERIFIER, this.codeVerifier);
        JsonUtil.put(jSONObject, KEY_ADDITIONAL_PARAMETERS, JsonUtil.mapToJsonObject(this.additionalParameters));
        return jSONObject;
    }

    public String jsonSerializeString() {
        return jsonSerialize().toString();
    }

    public static TokenRequest jsonDeserialize(JSONObject json) throws JSONException {
        Preconditions.checkNotNull(json, "json object cannot be null");
        return new TokenRequest(AuthorizationServiceConfiguration.fromJson(json.getJSONObject(KEY_CONFIGURATION)), JsonUtil.getString(json, KEY_CLIENT_ID), JsonUtil.getStringIfDefined(json, KEY_NONCE), JsonUtil.getString(json, KEY_GRANT_TYPE), JsonUtil.getUriIfDefined(json, KEY_REDIRECT_URI), JsonUtil.getStringIfDefined(json, "scope"), JsonUtil.getStringIfDefined(json, KEY_AUTHORIZATION_CODE), JsonUtil.getStringIfDefined(json, KEY_REFRESH_TOKEN), JsonUtil.getStringIfDefined(json, KEY_CODE_VERIFIER), JsonUtil.getStringMap(json, KEY_ADDITIONAL_PARAMETERS));
    }

    public static TokenRequest jsonDeserialize(String json) throws JSONException {
        Preconditions.checkNotNull(json, "json string cannot be null");
        return jsonDeserialize(new JSONObject(json));
    }
}

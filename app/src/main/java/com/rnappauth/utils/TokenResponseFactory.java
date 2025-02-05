package com.rnappauth.utils;

import android.text.TextUtils;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import net.openid.appauth.AuthorizationResponse;
import net.openid.appauth.TokenResponse;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes.dex */
public final class TokenResponseFactory {
    private static final WritableArray createScopeArray(String str) {
        WritableArray createArray = Arguments.createArray();
        if (!TextUtils.isEmpty(str)) {
            for (String str2 : str.split(StringUtils.SPACE)) {
                createArray.pushString(str2);
            }
        }
        return createArray;
    }

    public static final WritableMap tokenResponseToMap(TokenResponse tokenResponse) {
        WritableMap createMap = Arguments.createMap();
        createMap.putString("accessToken", tokenResponse.accessToken);
        createMap.putMap("additionalParameters", MapUtil.createAdditionalParametersMap(tokenResponse.additionalParameters));
        createMap.putString("idToken", tokenResponse.idToken);
        createMap.putString("refreshToken", tokenResponse.refreshToken);
        createMap.putString("tokenType", tokenResponse.tokenType);
        if (tokenResponse.accessTokenExpirationTime != null) {
            createMap.putString("accessTokenExpirationDate", DateUtil.formatTimestamp(tokenResponse.accessTokenExpirationTime));
        }
        return createMap;
    }

    public static final WritableMap tokenResponseToMap(TokenResponse tokenResponse, AuthorizationResponse authorizationResponse) {
        WritableMap createMap = Arguments.createMap();
        createMap.putString("accessToken", tokenResponse.accessToken);
        createMap.putMap("authorizeAdditionalParameters", MapUtil.createAdditionalParametersMap(authorizationResponse.additionalParameters));
        createMap.putMap("tokenAdditionalParameters", MapUtil.createAdditionalParametersMap(tokenResponse.additionalParameters));
        createMap.putString("idToken", tokenResponse.idToken);
        createMap.putString("refreshToken", tokenResponse.refreshToken);
        createMap.putString("tokenType", tokenResponse.tokenType);
        createMap.putArray("scopes", createScopeArray(authorizationResponse.scope));
        if (tokenResponse.accessTokenExpirationTime != null) {
            createMap.putString("accessTokenExpirationDate", DateUtil.formatTimestamp(tokenResponse.accessTokenExpirationTime));
        }
        return createMap;
    }

    public static final WritableMap authorizationResponseToMap(AuthorizationResponse authorizationResponse) {
        WritableMap createMap = Arguments.createMap();
        createMap.putString("authorizationCode", authorizationResponse.authorizationCode);
        createMap.putString("accessToken", authorizationResponse.accessToken);
        createMap.putMap("additionalParameters", MapUtil.createAdditionalParametersMap(authorizationResponse.additionalParameters));
        createMap.putString("idToken", authorizationResponse.idToken);
        createMap.putString("tokenType", authorizationResponse.tokenType);
        createMap.putArray("scopes", createScopeArray(authorizationResponse.scope));
        if (authorizationResponse.accessTokenExpirationTime != null) {
            createMap.putString("accessTokenExpirationTime", DateUtil.formatTimestamp(authorizationResponse.accessTokenExpirationTime));
        }
        return createMap;
    }

    public static final WritableMap authorizationCodeResponseToMap(AuthorizationResponse authorizationResponse, String str) {
        WritableMap createMap = Arguments.createMap();
        createMap.putString("authorizationCode", authorizationResponse.authorizationCode);
        createMap.putString("accessToken", authorizationResponse.accessToken);
        createMap.putMap("additionalParameters", MapUtil.createAdditionalParametersMap(authorizationResponse.additionalParameters));
        createMap.putString("idToken", authorizationResponse.idToken);
        createMap.putString("tokenType", authorizationResponse.tokenType);
        createMap.putArray("scopes", createScopeArray(authorizationResponse.scope));
        if (authorizationResponse.accessTokenExpirationTime != null) {
            createMap.putString("accessTokenExpirationTime", DateUtil.formatTimestamp(authorizationResponse.accessTokenExpirationTime));
        }
        if (!TextUtils.isEmpty(str)) {
            createMap.putString("codeVerifier", str);
        }
        return createMap;
    }
}

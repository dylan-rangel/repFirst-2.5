package com.rnappauth.utils;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import net.openid.appauth.RegistrationResponse;

/* loaded from: classes.dex */
public final class RegistrationResponseFactory {
    public static final WritableMap registrationResponseToMap(RegistrationResponse registrationResponse) {
        WritableMap createMap = Arguments.createMap();
        createMap.putString("clientId", registrationResponse.clientId);
        createMap.putMap("additionalParameters", MapUtil.createAdditionalParametersMap(registrationResponse.additionalParameters));
        if (registrationResponse.clientIdIssuedAt != null) {
            createMap.putString("clientIdIssuedAt", DateUtil.formatTimestamp(registrationResponse.clientIdIssuedAt));
        }
        if (registrationResponse.clientSecret != null) {
            createMap.putString("clientSecret", registrationResponse.clientSecret);
        }
        if (registrationResponse.clientSecretExpiresAt != null) {
            createMap.putString("clientSecretExpiresAt", DateUtil.formatTimestamp(registrationResponse.clientSecretExpiresAt));
        }
        if (registrationResponse.registrationAccessToken != null) {
            createMap.putString("registrationAccessToken", registrationResponse.registrationAccessToken);
        }
        if (registrationResponse.registrationClientUri != null) {
            createMap.putString("registrationClientUri", registrationResponse.registrationClientUri.toString());
        }
        if (registrationResponse.tokenEndpointAuthMethod != null) {
            createMap.putString("tokenEndpointAuthMethod", registrationResponse.tokenEndpointAuthMethod);
        }
        return createMap;
    }
}

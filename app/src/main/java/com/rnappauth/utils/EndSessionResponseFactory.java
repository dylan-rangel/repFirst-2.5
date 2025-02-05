package com.rnappauth.utils;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import io.sentry.protocol.SentryThread;
import net.openid.appauth.EndSessionResponse;

/* loaded from: classes.dex */
public final class EndSessionResponseFactory {
    public static final WritableMap endSessionResponseToMap(EndSessionResponse endSessionResponse) {
        WritableMap createMap = Arguments.createMap();
        createMap.putString(SentryThread.JsonKeys.STATE, endSessionResponse.state);
        createMap.putString("idTokenHint", endSessionResponse.request.idTokenHint);
        if (endSessionResponse.request.postLogoutRedirectUri != null) {
            createMap.putString("postLogoutRedirectUri", endSessionResponse.request.postLogoutRedirectUri.toString());
        }
        return createMap;
    }
}

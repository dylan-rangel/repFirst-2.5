package com.facebook.react.packagerconnection;

import com.facebook.common.logging.FLog;

/* loaded from: classes.dex */
public abstract class NotificationOnlyHandler implements RequestHandler {
    private static final String TAG = "JSPackagerClient";

    @Override // com.facebook.react.packagerconnection.RequestHandler
    public abstract void onNotification(Object obj);

    @Override // com.facebook.react.packagerconnection.RequestHandler
    public final void onRequest(Object obj, Responder responder) {
        responder.error("Request is not supported");
        FLog.e(TAG, "Request is not supported");
    }
}

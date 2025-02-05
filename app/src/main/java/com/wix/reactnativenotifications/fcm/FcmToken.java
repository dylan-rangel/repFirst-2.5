package com.wix.reactnativenotifications.fcm;

import android.content.Context;
import android.os.Bundle;
import com.facebook.react.ReactApplication;
import com.facebook.react.bridge.ReactContext;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.wix.reactnativenotifications.Defs;
import com.wix.reactnativenotifications.core.JsIOHelper;

/* loaded from: classes.dex */
public class FcmToken implements IFcmToken {
    protected static String sToken;
    protected final Context mAppContext;
    protected final JsIOHelper mJsIOHelper;

    protected FcmToken(Context context) {
        if (!(context instanceof ReactApplication)) {
            throw new IllegalStateException("Application instance isn't a react-application");
        }
        this.mJsIOHelper = new JsIOHelper();
        this.mAppContext = context;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static IFcmToken get(Context context) {
        Context applicationContext = context.getApplicationContext();
        if (applicationContext instanceof INotificationsFcmApplication) {
            return ((INotificationsFcmApplication) applicationContext).getFcmToken(context);
        }
        return new FcmToken(applicationContext);
    }

    @Override // com.wix.reactnativenotifications.fcm.IFcmToken
    public void onNewTokenReady() {
        synchronized (this.mAppContext) {
            refreshToken();
        }
    }

    @Override // com.wix.reactnativenotifications.fcm.IFcmToken
    public void onManualRefresh() {
        synchronized (this.mAppContext) {
            if (sToken == null) {
                refreshToken();
            } else {
                sendTokenToJS();
            }
        }
    }

    @Override // com.wix.reactnativenotifications.fcm.IFcmToken
    public void onAppReady() {
        synchronized (this.mAppContext) {
            if (sToken == null) {
                refreshToken();
            } else {
                sendTokenToJS();
            }
        }
    }

    protected void refreshToken() {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener() { // from class: com.wix.reactnativenotifications.fcm.FcmToken$$ExternalSyntheticLambda0
            @Override // com.google.android.gms.tasks.OnCompleteListener
            public final void onComplete(Task task) {
                FcmToken.this.m219x180d4db1(task);
            }
        });
    }

    /* renamed from: lambda$refreshToken$0$com-wix-reactnativenotifications-fcm-FcmToken, reason: not valid java name */
    /* synthetic */ void m219x180d4db1(Task task) {
        if (task.isSuccessful()) {
            String str = (String) task.getResult();
            sToken = str;
            Object obj = this.mAppContext;
            if (obj instanceof IFcmTokenListenerApplication) {
                ((IFcmTokenListenerApplication) obj).onNewFCMToken(str);
            }
            sendTokenToJS();
        }
    }

    protected void sendTokenToJS() {
        ReactContext currentReactContext = ((ReactApplication) this.mAppContext).getReactNativeHost().getReactInstanceManager().getCurrentReactContext();
        if (currentReactContext == null || !currentReactContext.hasActiveCatalystInstance()) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("deviceToken", sToken);
        this.mJsIOHelper.sendEventToJS(Defs.TOKEN_RECEIVED_EVENT_NAME, bundle, currentReactContext);
    }
}

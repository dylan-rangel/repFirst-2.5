package com.learnium.RNDeviceInfo;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import io.sentry.protocol.OperatingSystem;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/* loaded from: classes2.dex */
public class RNInstallReferrerClient {
    private static Class<?> InstallReferrerClientClazz = null;
    private static Class<?> InstallReferrerStateListenerClazz = null;
    private static final int R_RESPONSE_FEATURE_NOT_SUPPORTED = 2;
    private static final int R_RESPONSE_OK = 0;
    private static final int R_RESPONSE_SERVICE_UNAVAILABLE = 1;
    private static Class<?> ReferrerDetailsClazz;
    private Object installReferrerStateListener;
    private Object mReferrerClient;
    private final SharedPreferences sharedPreferences;

    static {
        try {
            InstallReferrerClientClazz = Class.forName("com.android.installreferrer.api.InstallReferrerClient");
            InstallReferrerStateListenerClazz = Class.forName("com.android.installreferrer.api.InstallReferrerStateListener");
            ReferrerDetailsClazz = Class.forName("com.android.installreferrer.api.ReferrerDetails");
        } catch (Exception unused) {
            System.err.println("RNInstallReferrerClient exception. 'installreferrer' APIs are unavailable.");
        }
    }

    RNInstallReferrerClient(Context context) {
        this.sharedPreferences = context.getSharedPreferences("react-native-device-info", 0);
        Class<?> cls = InstallReferrerClientClazz;
        if (cls == null || InstallReferrerStateListenerClazz == null || ReferrerDetailsClazz == null) {
            return;
        }
        try {
            Object invoke = cls.getMethod("newBuilder", Context.class).invoke(null, context);
            this.mReferrerClient = invoke.getClass().getMethod(OperatingSystem.JsonKeys.BUILD, new Class[0]).invoke(invoke, new Object[0]);
            this.installReferrerStateListener = Proxy.newProxyInstance(InstallReferrerStateListenerClazz.getClassLoader(), new Class[]{InstallReferrerStateListenerClazz}, new InstallReferrerStateListenerProxy());
            InstallReferrerClientClazz.getMethod("startConnection", InstallReferrerStateListenerClazz).invoke(this.mReferrerClient, this.installReferrerStateListener);
        } catch (Exception e) {
            System.err.println("RNInstallReferrerClient exception. getInstallReferrer will be unavailable: " + e.getMessage());
            e.printStackTrace(System.err);
        }
    }

    private class InstallReferrerStateListenerProxy implements InvocationHandler {
        private InstallReferrerStateListenerProxy() {
        }

        @Override // java.lang.reflect.InvocationHandler
        public Object invoke(Object obj, Method method, Object[] objArr) throws Throwable {
            String name = method.getName();
            try {
                if (name.equals("onInstallReferrerSetupFinished") && objArr != null) {
                    Object obj2 = objArr[0];
                    if (obj2 instanceof Integer) {
                        onInstallReferrerSetupFinished(((Integer) obj2).intValue());
                        return null;
                    }
                }
                if (!name.equals("onInstallReferrerServiceDisconnected")) {
                    return null;
                }
                onInstallReferrerServiceDisconnected();
                return null;
            } catch (Exception e) {
                throw new RuntimeException("unexpected invocation exception: " + e.getMessage());
            }
        }

        public void onInstallReferrerSetupFinished(int i) {
            if (i != 0) {
                if (i == 1) {
                    Log.d("InstallReferrerState", "SERVICE_UNAVAILABLE");
                    return;
                } else {
                    if (i != 2) {
                        return;
                    }
                    Log.d("InstallReferrerState", "FEATURE_NOT_SUPPORTED");
                    return;
                }
            }
            try {
                Log.d("InstallReferrerState", "OK");
                String str = (String) RNInstallReferrerClient.ReferrerDetailsClazz.getMethod("getInstallReferrer", new Class[0]).invoke(RNInstallReferrerClient.InstallReferrerClientClazz.getMethod("getInstallReferrer", new Class[0]).invoke(RNInstallReferrerClient.this.mReferrerClient, new Object[0]), new Object[0]);
                SharedPreferences.Editor edit = RNInstallReferrerClient.this.sharedPreferences.edit();
                edit.putString("installReferrer", str);
                edit.apply();
                RNInstallReferrerClient.InstallReferrerClientClazz.getMethod("endConnection", new Class[0]).invoke(RNInstallReferrerClient.this.mReferrerClient, new Object[0]);
            } catch (Exception e) {
                System.err.println("RNInstallReferrerClient exception. getInstallReferrer will be unavailable: " + e.getMessage());
                e.printStackTrace(System.err);
            }
        }

        public void onInstallReferrerServiceDisconnected() {
            Log.d("RNInstallReferrerClient", "InstallReferrerService disconnected");
        }
    }
}

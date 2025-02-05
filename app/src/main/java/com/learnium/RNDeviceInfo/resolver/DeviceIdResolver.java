package com.learnium.RNDeviceInfo.resolver;

import android.content.Context;
import android.content.SharedPreferences;
import com.learnium.RNDeviceInfo.RNDeviceModule;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

/* loaded from: classes2.dex */
public class DeviceIdResolver {
    private final Context context;

    public DeviceIdResolver(Context context) {
        this.context = context;
    }

    public String getInstanceIdSync() {
        String instanceIdFromPrefs = getInstanceIdFromPrefs();
        if (instanceIdFromPrefs != "unknown") {
            return instanceIdFromPrefs;
        }
        try {
            String firebaseInstanceId = getFirebaseInstanceId();
            setInstanceIdInPrefs(firebaseInstanceId);
            return firebaseInstanceId;
        } catch (ClassNotFoundException unused) {
            try {
                String gmsInstanceId = getGmsInstanceId();
                setInstanceIdInPrefs(gmsInstanceId);
                return gmsInstanceId;
            } catch (ClassNotFoundException unused2) {
                String uUIDInstanceId = getUUIDInstanceId();
                setInstanceIdInPrefs(uUIDInstanceId);
                return uUIDInstanceId;
            } catch (IllegalAccessException | NoSuchMethodException | SecurityException | InvocationTargetException unused3) {
                System.err.println("N/A: Unsupported version of com.google.android.gms.iid in your project.");
                String uUIDInstanceId2 = getUUIDInstanceId();
                setInstanceIdInPrefs(uUIDInstanceId2);
                return uUIDInstanceId2;
            }
        } catch (IllegalAccessException | NoSuchMethodException | SecurityException | InvocationTargetException unused4) {
            System.err.println("N/A: Unsupported version of com.google.firebase:firebase-iid in your project.");
            String gmsInstanceId2 = getGmsInstanceId();
            setInstanceIdInPrefs(gmsInstanceId2);
            return gmsInstanceId2;
        }
    }

    String getUUIDInstanceId() {
        return UUID.randomUUID().toString();
    }

    String getInstanceIdFromPrefs() {
        return RNDeviceModule.getRNDISharedPreferences(this.context).getString("instanceId", "unknown");
    }

    void setInstanceIdInPrefs(String str) {
        SharedPreferences.Editor edit = RNDeviceModule.getRNDISharedPreferences(this.context).edit();
        edit.putString("instanceId", str);
        edit.apply();
    }

    String getGmsInstanceId() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Object invoke = Class.forName("com.google.android.gms.iid.InstanceID").getDeclaredMethod("getInstance", Context.class).invoke(null, this.context.getApplicationContext());
        return (String) invoke.getClass().getMethod("getId", new Class[0]).invoke(invoke, new Object[0]);
    }

    String getFirebaseInstanceId() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Object invoke = Class.forName("com.google.firebase.iid.FirebaseInstanceId").getDeclaredMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
        return (String) invoke.getClass().getMethod("getId", new Class[0]).invoke(invoke, new Object[0]);
    }
}

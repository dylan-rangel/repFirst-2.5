package net.time4j.android.spi;

import android.os.SystemClock;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import net.time4j.base.MathUtils;
import net.time4j.scale.TickProvider;

/* loaded from: classes3.dex */
class AndroidTickerSPI implements TickProvider {
    private static final Method ANDROID;
    private static final Object[] EMPTY_ARGS;
    private static final Class[] EMPTY_PARAMS;

    @Override // net.time4j.scale.TickProvider
    public String getPlatform() {
        return "Dalvik";
    }

    AndroidTickerSPI() {
    }

    static {
        Class[] clsArr = new Class[0];
        EMPTY_PARAMS = clsArr;
        Object[] objArr = new Object[0];
        EMPTY_ARGS = objArr;
        Method method = null;
        try {
            Method method2 = SystemClock.class.getMethod("elapsedRealtimeNanos", clsArr);
            method2.invoke(null, objArr);
            method = method2;
        } catch (IllegalAccessException | NoSuchMethodException | RuntimeException | InvocationTargetException unused) {
        }
        ANDROID = method;
    }

    @Override // net.time4j.scale.TickProvider
    public long getNanos() {
        Method method = ANDROID;
        if (method != null) {
            try {
                return ((Long) method.invoke(null, EMPTY_ARGS)).longValue();
            } catch (IllegalAccessException e) {
                e.printStackTrace(System.err);
            } catch (InvocationTargetException e2) {
                e2.printStackTrace(System.err);
            }
        }
        return MathUtils.safeMultiply(SystemClock.elapsedRealtime(), 1000000L);
    }
}

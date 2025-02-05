package io.invertase.firebase.common;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import io.invertase.firebase.interfaces.NativeEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public class ReactNativeFirebaseEventEmitter {
    private static ReactNativeFirebaseEventEmitter sharedInstance = new ReactNativeFirebaseEventEmitter();
    private int jsListenerCount;
    private ReactContext reactContext;
    private final List<NativeEvent> queuedEvents = new ArrayList();
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final HashMap<String, Integer> jsListeners = new HashMap<>();
    private Boolean jsReady = false;

    public static ReactNativeFirebaseEventEmitter getSharedInstance() {
        return sharedInstance;
    }

    public void attachReactContext(final ReactContext reactContext) {
        this.handler.post(new Runnable() { // from class: io.invertase.firebase.common.ReactNativeFirebaseEventEmitter$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                ReactNativeFirebaseEventEmitter.this.m220x726bb524(reactContext);
            }
        });
    }

    /* renamed from: lambda$attachReactContext$0$io-invertase-firebase-common-ReactNativeFirebaseEventEmitter, reason: not valid java name */
    /* synthetic */ void m220x726bb524(ReactContext reactContext) {
        this.reactContext = reactContext;
        sendQueuedEvents();
    }

    public void notifyJsReady(final Boolean bool) {
        this.handler.post(new Runnable() { // from class: io.invertase.firebase.common.ReactNativeFirebaseEventEmitter$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                ReactNativeFirebaseEventEmitter.this.m221x4729fe61(bool);
            }
        });
    }

    /* renamed from: lambda$notifyJsReady$1$io-invertase-firebase-common-ReactNativeFirebaseEventEmitter, reason: not valid java name */
    /* synthetic */ void m221x4729fe61(Boolean bool) {
        this.jsReady = bool;
        sendQueuedEvents();
    }

    public void sendEvent(final NativeEvent nativeEvent) {
        this.handler.post(new Runnable() { // from class: io.invertase.firebase.common.ReactNativeFirebaseEventEmitter$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                ReactNativeFirebaseEventEmitter.this.m222x17d6aabf(nativeEvent);
            }
        });
    }

    /* renamed from: lambda$sendEvent$2$io-invertase-firebase-common-ReactNativeFirebaseEventEmitter, reason: not valid java name */
    /* synthetic */ void m222x17d6aabf(NativeEvent nativeEvent) {
        synchronized (this.jsListeners) {
            if (!this.jsListeners.containsKey(nativeEvent.getEventName()) || !emit(nativeEvent)) {
                this.queuedEvents.add(nativeEvent);
            }
        }
    }

    public void addListener(String str) {
        synchronized (this.jsListeners) {
            this.jsListenerCount++;
            if (!this.jsListeners.containsKey(str)) {
                this.jsListeners.put(str, 1);
            } else {
                this.jsListeners.put(str, Integer.valueOf(this.jsListeners.get(str).intValue() + 1));
            }
        }
        this.handler.post(new Runnable() { // from class: io.invertase.firebase.common.ReactNativeFirebaseEventEmitter$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ReactNativeFirebaseEventEmitter.this.sendQueuedEvents();
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x003a  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x003b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void removeListener(java.lang.String r6, java.lang.Boolean r7) {
        /*
            r5 = this;
            java.util.HashMap<java.lang.String, java.lang.Integer> r0 = r5.jsListeners
            monitor-enter(r0)
            java.util.HashMap<java.lang.String, java.lang.Integer> r1 = r5.jsListeners     // Catch: java.lang.Throwable -> L41
            boolean r1 = r1.containsKey(r6)     // Catch: java.lang.Throwable -> L41
            if (r1 == 0) goto L3f
            java.util.HashMap<java.lang.String, java.lang.Integer> r1 = r5.jsListeners     // Catch: java.lang.Throwable -> L41
            java.lang.Object r1 = r1.get(r6)     // Catch: java.lang.Throwable -> L41
            java.lang.Integer r1 = (java.lang.Integer) r1     // Catch: java.lang.Throwable -> L41
            int r1 = r1.intValue()     // Catch: java.lang.Throwable -> L41
            r2 = 1
            if (r1 <= r2) goto L2d
            boolean r3 = r7.booleanValue()     // Catch: java.lang.Throwable -> L41
            if (r3 == 0) goto L21
            goto L2d
        L21:
            java.util.HashMap<java.lang.String, java.lang.Integer> r3 = r5.jsListeners     // Catch: java.lang.Throwable -> L41
            int r4 = r1 + (-1)
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)     // Catch: java.lang.Throwable -> L41
            r3.put(r6, r4)     // Catch: java.lang.Throwable -> L41
            goto L32
        L2d:
            java.util.HashMap<java.lang.String, java.lang.Integer> r3 = r5.jsListeners     // Catch: java.lang.Throwable -> L41
            r3.remove(r6)     // Catch: java.lang.Throwable -> L41
        L32:
            int r6 = r5.jsListenerCount     // Catch: java.lang.Throwable -> L41
            boolean r7 = r7.booleanValue()     // Catch: java.lang.Throwable -> L41
            if (r7 == 0) goto L3b
            goto L3c
        L3b:
            r1 = 1
        L3c:
            int r6 = r6 - r1
            r5.jsListenerCount = r6     // Catch: java.lang.Throwable -> L41
        L3f:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L41
            return
        L41:
            r6 = move-exception
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L41
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: io.invertase.firebase.common.ReactNativeFirebaseEventEmitter.removeListener(java.lang.String, java.lang.Boolean):void");
    }

    public WritableMap getListenersMap() {
        WritableMap createMap = Arguments.createMap();
        WritableMap createMap2 = Arguments.createMap();
        createMap.putInt("listeners", this.jsListenerCount);
        createMap.putInt("queued", this.queuedEvents.size());
        synchronized (this.jsListeners) {
            for (Map.Entry<String, Integer> entry : this.jsListeners.entrySet()) {
                createMap2.putInt(entry.getKey(), entry.getValue().intValue());
            }
        }
        createMap.putMap("events", createMap2);
        return createMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendQueuedEvents() {
        synchronized (this.jsListeners) {
            Iterator it = new ArrayList(this.queuedEvents).iterator();
            while (it.hasNext()) {
                NativeEvent nativeEvent = (NativeEvent) it.next();
                if (this.jsListeners.containsKey(nativeEvent.getEventName())) {
                    this.queuedEvents.remove(nativeEvent);
                    sendEvent(nativeEvent);
                }
            }
        }
    }

    private boolean emit(NativeEvent nativeEvent) {
        ReactContext reactContext;
        if (this.jsReady.booleanValue() && (reactContext = this.reactContext) != null && reactContext.hasActiveCatalystInstance()) {
            try {
                ((DeviceEventManagerModule.RCTDeviceEventEmitter) this.reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)).emit("rnfb_" + nativeEvent.getEventName(), nativeEvent.getEventBody());
                return true;
            } catch (Exception e) {
                Log.wtf("RNFB_EMITTER", "Error sending Event " + nativeEvent.getEventName(), e);
            }
        }
        return false;
    }
}

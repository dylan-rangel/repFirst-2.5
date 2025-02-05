package io.invertase.firebase.messaging;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import androidx.core.app.NotificationManagerCompat;
import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.Constants;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;
import io.invertase.firebase.common.ReactNativeFirebaseEventEmitter;
import io.invertase.firebase.common.ReactNativeFirebaseModule;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/* loaded from: classes3.dex */
public class ReactNativeFirebaseMessagingModule extends ReactNativeFirebaseModule implements ActivityEventListener {
    private static final String TAG = "Messaging";
    ReadableMap initialNotification;
    private HashMap<String, Boolean> initialNotificationMap;

    @Override // com.facebook.react.bridge.ActivityEventListener
    public void onActivityResult(Activity activity, int i, int i2, Intent intent) {
    }

    ReactNativeFirebaseMessagingModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext, TAG);
        this.initialNotification = null;
        this.initialNotificationMap = new HashMap<>();
        reactApplicationContext.addActivityEventListener(this);
    }

    private WritableMap popRemoteMessageMapFromMessagingStore(String str) {
        ReactNativeFirebaseMessagingStore messagingStore = ReactNativeFirebaseMessagingStoreHelper.getInstance().getMessagingStore();
        WritableMap firebaseMessageMap = messagingStore.getFirebaseMessageMap(str);
        messagingStore.clearFirebaseMessage(str);
        return firebaseMessageMap;
    }

    @ReactMethod
    public void getInitialNotification(Promise promise) {
        WritableMap remoteMessageToWritableMap;
        ReadableMap readableMap = this.initialNotification;
        if (readableMap != null) {
            promise.resolve(readableMap);
            this.initialNotification = null;
            return;
        }
        Activity currentActivity = getCurrentActivity();
        if (currentActivity != null) {
            Intent intent = currentActivity.getIntent();
            if (intent != null && intent.getExtras() != null) {
                String string = intent.getExtras().getString(Constants.MessagePayloadKeys.MSGID);
                if (string == null) {
                    string = intent.getExtras().getString(Constants.MessagePayloadKeys.MSGID_SERVER);
                }
                if (string != null && this.initialNotificationMap.get(string) == null) {
                    RemoteMessage remoteMessage = ReactNativeFirebaseMessagingReceiver.notifications.get(string);
                    if (remoteMessage == null) {
                        remoteMessageToWritableMap = popRemoteMessageMapFromMessagingStore(string);
                    } else {
                        remoteMessageToWritableMap = ReactNativeFirebaseMessagingSerializer.remoteMessageToWritableMap(remoteMessage);
                    }
                    if (remoteMessageToWritableMap != null) {
                        promise.resolve(remoteMessageToWritableMap);
                        this.initialNotificationMap.put(string, true);
                        return;
                    }
                }
            }
        } else {
            Log.w(TAG, "Attempt to call getInitialNotification failed. The current activity is not ready, try calling the method later in the React lifecycle.");
        }
        promise.resolve(null);
    }

    @ReactMethod
    public void setAutoInitEnabled(final Boolean bool, final Promise promise) {
        Tasks.call(getExecutor(), new Callable() { // from class: io.invertase.firebase.messaging.ReactNativeFirebaseMessagingModule$$ExternalSyntheticLambda5
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return ReactNativeFirebaseMessagingModule.lambda$setAutoInitEnabled$0(bool);
            }
        }).addOnCompleteListener(new OnCompleteListener() { // from class: io.invertase.firebase.messaging.ReactNativeFirebaseMessagingModule$$ExternalSyntheticLambda6
            @Override // com.google.android.gms.tasks.OnCompleteListener
            public final void onComplete(Task task) {
                ReactNativeFirebaseMessagingModule.lambda$setAutoInitEnabled$1(Promise.this, task);
            }
        });
    }

    static /* synthetic */ Object lambda$setAutoInitEnabled$0(Boolean bool) throws Exception {
        FirebaseMessaging.getInstance().setAutoInitEnabled(bool.booleanValue());
        return null;
    }

    static /* synthetic */ void lambda$setAutoInitEnabled$1(Promise promise, Task task) {
        if (task.isSuccessful()) {
            promise.resolve(Boolean.valueOf(FirebaseMessaging.getInstance().isAutoInitEnabled()));
        } else {
            rejectPromiseWithExceptionMap(promise, task.getException());
        }
    }

    @ReactMethod
    public void getToken(String str, String str2, final Promise promise) {
        final FirebaseMessaging firebaseMessaging = (FirebaseMessaging) FirebaseApp.getInstance(str).get(FirebaseMessaging.class);
        Tasks.call(getExecutor(), new Callable() { // from class: io.invertase.firebase.messaging.ReactNativeFirebaseMessagingModule$$ExternalSyntheticLambda9
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return ReactNativeFirebaseMessagingModule.lambda$getToken$2(FirebaseMessaging.this);
            }
        }).addOnCompleteListener(new OnCompleteListener() { // from class: io.invertase.firebase.messaging.ReactNativeFirebaseMessagingModule$$ExternalSyntheticLambda10
            @Override // com.google.android.gms.tasks.OnCompleteListener
            public final void onComplete(Task task) {
                ReactNativeFirebaseMessagingModule.lambda$getToken$3(Promise.this, task);
            }
        });
    }

    static /* synthetic */ String lambda$getToken$2(FirebaseMessaging firebaseMessaging) throws Exception {
        return (String) Tasks.await(firebaseMessaging.getToken());
    }

    static /* synthetic */ void lambda$getToken$3(Promise promise, Task task) {
        if (task.isSuccessful()) {
            promise.resolve(task.getResult());
        } else {
            rejectPromiseWithExceptionMap(promise, task.getException());
        }
    }

    @ReactMethod
    public void deleteToken(String str, String str2, final Promise promise) {
        final FirebaseMessaging firebaseMessaging = (FirebaseMessaging) FirebaseApp.getInstance(str).get(FirebaseMessaging.class);
        Tasks.call(getExecutor(), new Callable() { // from class: io.invertase.firebase.messaging.ReactNativeFirebaseMessagingModule$$ExternalSyntheticLambda7
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return ReactNativeFirebaseMessagingModule.lambda$deleteToken$4(FirebaseMessaging.this);
            }
        }).addOnCompleteListener(new OnCompleteListener() { // from class: io.invertase.firebase.messaging.ReactNativeFirebaseMessagingModule$$ExternalSyntheticLambda8
            @Override // com.google.android.gms.tasks.OnCompleteListener
            public final void onComplete(Task task) {
                ReactNativeFirebaseMessagingModule.lambda$deleteToken$5(Promise.this, task);
            }
        });
    }

    static /* synthetic */ Object lambda$deleteToken$4(FirebaseMessaging firebaseMessaging) throws Exception {
        Tasks.await(firebaseMessaging.deleteToken());
        return null;
    }

    static /* synthetic */ void lambda$deleteToken$5(Promise promise, Task task) {
        if (task.isSuccessful()) {
            promise.resolve(task.getResult());
        } else {
            rejectPromiseWithExceptionMap(promise, task.getException());
        }
    }

    @ReactMethod
    public void hasPermission(final Promise promise) {
        Tasks.call(getExecutor(), new Callable() { // from class: io.invertase.firebase.messaging.ReactNativeFirebaseMessagingModule$$ExternalSyntheticLambda13
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return ReactNativeFirebaseMessagingModule.this.m224x81d60b11();
            }
        }).addOnCompleteListener(new OnCompleteListener() { // from class: io.invertase.firebase.messaging.ReactNativeFirebaseMessagingModule$$ExternalSyntheticLambda1
            @Override // com.google.android.gms.tasks.OnCompleteListener
            public final void onComplete(Task task) {
                ReactNativeFirebaseMessagingModule.lambda$hasPermission$7(Promise.this, task);
            }
        });
    }

    /* renamed from: lambda$hasPermission$6$io-invertase-firebase-messaging-ReactNativeFirebaseMessagingModule, reason: not valid java name */
    /* synthetic */ Boolean m224x81d60b11() throws Exception {
        return Boolean.valueOf(NotificationManagerCompat.from(getReactApplicationContext()).areNotificationsEnabled());
    }

    static /* synthetic */ void lambda$hasPermission$7(Promise promise, Task task) {
        if (task.isSuccessful()) {
            promise.resolve(Integer.valueOf(((Boolean) task.getResult()).booleanValue() ? 1 : 0));
        } else {
            rejectPromiseWithExceptionMap(promise, task.getException());
        }
    }

    @ReactMethod
    public void sendMessage(final ReadableMap readableMap, final Promise promise) {
        Tasks.call(getExecutor(), new Callable() { // from class: io.invertase.firebase.messaging.ReactNativeFirebaseMessagingModule$$ExternalSyntheticLambda3
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return ReactNativeFirebaseMessagingModule.lambda$sendMessage$8(ReadableMap.this);
            }
        }).addOnCompleteListener(new OnCompleteListener() { // from class: io.invertase.firebase.messaging.ReactNativeFirebaseMessagingModule$$ExternalSyntheticLambda4
            @Override // com.google.android.gms.tasks.OnCompleteListener
            public final void onComplete(Task task) {
                ReactNativeFirebaseMessagingModule.lambda$sendMessage$9(Promise.this, task);
            }
        });
    }

    static /* synthetic */ Object lambda$sendMessage$8(ReadableMap readableMap) throws Exception {
        FirebaseMessaging.getInstance().send(ReactNativeFirebaseMessagingSerializer.remoteMessageFromReadableMap(readableMap));
        return null;
    }

    static /* synthetic */ void lambda$sendMessage$9(Promise promise, Task task) {
        if (task.isSuccessful()) {
            promise.resolve(task.getResult());
        } else {
            rejectPromiseWithExceptionMap(promise, task.getException());
        }
    }

    @ReactMethod
    public void subscribeToTopic(String str, final Promise promise) {
        FirebaseMessaging.getInstance().subscribeToTopic(str).addOnCompleteListener(new OnCompleteListener() { // from class: io.invertase.firebase.messaging.ReactNativeFirebaseMessagingModule$$ExternalSyntheticLambda2
            @Override // com.google.android.gms.tasks.OnCompleteListener
            public final void onComplete(Task task) {
                ReactNativeFirebaseMessagingModule.lambda$subscribeToTopic$10(Promise.this, task);
            }
        });
    }

    static /* synthetic */ void lambda$subscribeToTopic$10(Promise promise, Task task) {
        if (task.isSuccessful()) {
            promise.resolve(task.getResult());
        } else {
            rejectPromiseWithExceptionMap(promise, task.getException());
        }
    }

    @ReactMethod
    public void unsubscribeFromTopic(String str, final Promise promise) {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(str).addOnCompleteListener(new OnCompleteListener() { // from class: io.invertase.firebase.messaging.ReactNativeFirebaseMessagingModule$$ExternalSyntheticLambda0
            @Override // com.google.android.gms.tasks.OnCompleteListener
            public final void onComplete(Task task) {
                ReactNativeFirebaseMessagingModule.lambda$unsubscribeFromTopic$11(Promise.this, task);
            }
        });
    }

    static /* synthetic */ void lambda$unsubscribeFromTopic$11(Promise promise, Task task) {
        if (task.isSuccessful()) {
            promise.resolve(task.getResult());
        } else {
            rejectPromiseWithExceptionMap(promise, task.getException());
        }
    }

    @ReactMethod
    public void setDeliveryMetricsExportToBigQuery(final Boolean bool, final Promise promise) {
        Tasks.call(getExecutor(), new Callable() { // from class: io.invertase.firebase.messaging.ReactNativeFirebaseMessagingModule$$ExternalSyntheticLambda11
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return ReactNativeFirebaseMessagingModule.lambda$setDeliveryMetricsExportToBigQuery$12(bool);
            }
        }).addOnCompleteListener(new OnCompleteListener() { // from class: io.invertase.firebase.messaging.ReactNativeFirebaseMessagingModule$$ExternalSyntheticLambda12
            @Override // com.google.android.gms.tasks.OnCompleteListener
            public final void onComplete(Task task) {
                ReactNativeFirebaseMessagingModule.lambda$setDeliveryMetricsExportToBigQuery$13(Promise.this, task);
            }
        });
    }

    static /* synthetic */ Object lambda$setDeliveryMetricsExportToBigQuery$12(Boolean bool) throws Exception {
        FirebaseMessaging.getInstance().setDeliveryMetricsExportToBigQuery(bool.booleanValue());
        return null;
    }

    static /* synthetic */ void lambda$setDeliveryMetricsExportToBigQuery$13(Promise promise, Task task) {
        if (task.isSuccessful()) {
            promise.resolve(Boolean.valueOf(FirebaseMessaging.getInstance().deliveryMetricsExportToBigQueryEnabled()));
        } else {
            rejectPromiseWithExceptionMap(promise, task.getException());
        }
    }

    @Override // io.invertase.firebase.common.ReactNativeFirebaseModule, com.facebook.react.bridge.BaseJavaModule
    public Map<String, Object> getConstants() {
        HashMap hashMap = new HashMap();
        hashMap.put("isAutoInitEnabled", Boolean.valueOf(FirebaseMessaging.getInstance().isAutoInitEnabled()));
        hashMap.put("isDeliveryMetricsExportToBigQueryEnabled", Boolean.valueOf(FirebaseMessaging.getInstance().deliveryMetricsExportToBigQueryEnabled()));
        return hashMap;
    }

    @Override // com.facebook.react.bridge.ActivityEventListener
    public void onNewIntent(Intent intent) {
        WritableMap remoteMessageToWritableMap;
        if (intent == null || intent.getExtras() == null) {
            return;
        }
        String string = intent.getExtras().getString(Constants.MessagePayloadKeys.MSGID);
        if (string == null) {
            string = intent.getExtras().getString(Constants.MessagePayloadKeys.MSGID_SERVER);
        }
        if (string != null) {
            RemoteMessage remoteMessage = ReactNativeFirebaseMessagingReceiver.notifications.get(string);
            if (remoteMessage == null) {
                remoteMessageToWritableMap = popRemoteMessageMapFromMessagingStore(string);
            } else {
                remoteMessageToWritableMap = ReactNativeFirebaseMessagingSerializer.remoteMessageToWritableMap(remoteMessage);
            }
            if (remoteMessageToWritableMap != null) {
                WritableNativeMap writableNativeMap = new WritableNativeMap();
                writableNativeMap.merge(remoteMessageToWritableMap);
                this.initialNotification = writableNativeMap;
                ReactNativeFirebaseMessagingReceiver.notifications.remove(string);
                ReactNativeFirebaseEventEmitter.getSharedInstance().sendEvent(ReactNativeFirebaseMessagingSerializer.remoteMessageMapToEvent(remoteMessageToWritableMap, true));
            }
        }
    }
}

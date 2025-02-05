package io.sentry.android.core;

import android.content.Context;
import io.sentry.ILogger;
import io.sentry.android.core.internal.util.ConnectivityChecker;
import io.sentry.transport.ITransportGate;

/* loaded from: classes3.dex */
final class AndroidTransportGate implements ITransportGate {
    private final Context context;
    private final ILogger logger;

    AndroidTransportGate(Context context, ILogger iLogger) {
        this.context = context;
        this.logger = iLogger;
    }

    @Override // io.sentry.transport.ITransportGate
    public boolean isConnected() {
        return isConnected(ConnectivityChecker.getConnectionStatus(this.context, this.logger));
    }

    /* renamed from: io.sentry.android.core.AndroidTransportGate$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$io$sentry$android$core$internal$util$ConnectivityChecker$Status;

        static {
            int[] iArr = new int[ConnectivityChecker.Status.values().length];
            $SwitchMap$io$sentry$android$core$internal$util$ConnectivityChecker$Status = iArr;
            try {
                iArr[ConnectivityChecker.Status.CONNECTED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$io$sentry$android$core$internal$util$ConnectivityChecker$Status[ConnectivityChecker.Status.UNKNOWN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$io$sentry$android$core$internal$util$ConnectivityChecker$Status[ConnectivityChecker.Status.NO_PERMISSION.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    boolean isConnected(ConnectivityChecker.Status status) {
        int i = AnonymousClass1.$SwitchMap$io$sentry$android$core$internal$util$ConnectivityChecker$Status[status.ordinal()];
        return i == 1 || i == 2 || i == 3;
    }
}

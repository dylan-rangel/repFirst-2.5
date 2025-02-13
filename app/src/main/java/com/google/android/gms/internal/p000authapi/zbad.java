package com.google.android.gms.internal.p000authapi;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.auth.api.identity.SaveAccountLinkingTokenRequest;
import com.google.android.gms.auth.api.identity.SavePasswordRequest;

/* compiled from: com.google.android.gms:play-services-auth@@20.7.0 */
/* loaded from: classes2.dex */
public final class zbad extends zba implements IInterface {
    zbad(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.auth.api.identity.internal.ICredentialSavingService");
    }

    public final void zbc(zbaj zbajVar, SaveAccountLinkingTokenRequest saveAccountLinkingTokenRequest) throws RemoteException {
        Parcel zba = zba();
        zbc.zbd(zba, zbajVar);
        zbc.zbc(zba, saveAccountLinkingTokenRequest);
        zbb(1, zba);
    }

    public final void zbd(zbal zbalVar, SavePasswordRequest savePasswordRequest) throws RemoteException {
        Parcel zba = zba();
        zbc.zbd(zba, zbalVar);
        zbc.zbc(zba, savePasswordRequest);
        zbb(2, zba);
    }
}

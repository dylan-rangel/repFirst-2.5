package net.time4j.android;

import android.app.Application;
import android.content.Context;

/* loaded from: classes3.dex */
public abstract class TimeApplication extends Application {
    @Override // android.app.Application
    public void onCreate() {
        super.onCreate();
        ApplicationStarter.initialize((Context) this, false);
    }
}

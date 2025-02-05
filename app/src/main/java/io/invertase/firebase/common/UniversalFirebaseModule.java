package io.invertase.firebase.common;

import android.content.Context;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import javax.annotation.OverridingMethodsMustInvokeSuper;

/* loaded from: classes3.dex */
public class UniversalFirebaseModule {
    private final Context context;
    private final TaskExecutorService executorService = new TaskExecutorService(getName());
    private final String serviceName;

    protected UniversalFirebaseModule(Context context, String str) {
        this.context = context;
        this.serviceName = str;
    }

    public Context getContext() {
        return this.context;
    }

    public Context getApplicationContext() {
        return getContext().getApplicationContext();
    }

    protected ExecutorService getExecutor() {
        return this.executorService.getExecutor();
    }

    public String getName() {
        return "Universal" + this.serviceName + "Module";
    }

    @OverridingMethodsMustInvokeSuper
    public void onTearDown() {
        this.executorService.shutdown();
    }

    public Map<String, Object> getConstants() {
        return new HashMap();
    }
}

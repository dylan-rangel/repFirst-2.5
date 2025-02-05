package com.facebook.react.views.common;

import android.content.Context;
import android.content.ContextWrapper;

/* loaded from: classes.dex */
public class ContextUtils {
    public static <T> T findContextOfType(Context context, Class<? extends T> cls) {
        Context baseContext;
        Object obj = context;
        while (!cls.isInstance(obj)) {
            if (!(obj instanceof ContextWrapper) || obj == (baseContext = obj.getBaseContext())) {
                return null;
            }
            obj = (T) baseContext;
        }
        return (T) obj;
    }
}

package com.facebook.fresco.ui.common;

import android.net.Uri;
import com.facebook.common.internal.Fn;
import javax.annotation.Nullable;

/* loaded from: classes.dex */
public abstract class MultiUriHelper {
    @Nullable
    public static <T> Uri getMainUri(@Nullable T mainRequest, @Nullable T lowResRequest, @Nullable T[] firstAvailableRequest, Fn<T, Uri> requestToUri) {
        T t;
        Uri apply;
        Uri apply2;
        if (mainRequest != null && (apply2 = requestToUri.apply(mainRequest)) != null) {
            return apply2;
        }
        if (firstAvailableRequest != null && firstAvailableRequest.length > 0 && (t = firstAvailableRequest[0]) != null && (apply = requestToUri.apply(t)) != null) {
            return apply;
        }
        if (lowResRequest != null) {
            return requestToUri.apply(lowResRequest);
        }
        return null;
    }
}

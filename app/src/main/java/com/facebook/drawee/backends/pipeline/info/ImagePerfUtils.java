package com.facebook.drawee.backends.pipeline.info;

import com.google.firebase.analytics.FirebaseAnalytics;

/* loaded from: classes.dex */
public class ImagePerfUtils {
    public static String toString(int imageLoadStatus) {
        return imageLoadStatus != 0 ? imageLoadStatus != 1 ? imageLoadStatus != 2 ? imageLoadStatus != 3 ? imageLoadStatus != 4 ? imageLoadStatus != 5 ? "unknown" : "error" : "canceled" : FirebaseAnalytics.Param.SUCCESS : "intermediate_available" : "origin_available" : "requested";
    }

    private ImagePerfUtils() {
    }
}

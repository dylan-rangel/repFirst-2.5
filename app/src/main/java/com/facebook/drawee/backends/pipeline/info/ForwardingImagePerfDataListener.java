package com.facebook.drawee.backends.pipeline.info;

import java.util.Collection;
import java.util.Iterator;

/* loaded from: classes.dex */
public class ForwardingImagePerfDataListener implements ImagePerfDataListener {
    private final Collection<ImagePerfDataListener> mListeners;

    public ForwardingImagePerfDataListener(Collection<ImagePerfDataListener> listeners) {
        this.mListeners = listeners;
    }

    @Override // com.facebook.drawee.backends.pipeline.info.ImagePerfDataListener
    public void onImageLoadStatusUpdated(ImagePerfData imagePerfData, int imageLoadStatus) {
        Iterator<ImagePerfDataListener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onImageLoadStatusUpdated(imagePerfData, imageLoadStatus);
        }
    }

    @Override // com.facebook.drawee.backends.pipeline.info.ImagePerfDataListener
    public void onImageVisibilityUpdated(ImagePerfData imagePerfData, int visibilityState) {
        Iterator<ImagePerfDataListener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onImageVisibilityUpdated(imagePerfData, visibilityState);
        }
    }
}

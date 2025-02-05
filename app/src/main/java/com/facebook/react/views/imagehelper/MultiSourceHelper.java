package com.facebook.react.views.imagehelper;

import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import java.util.List;

/* loaded from: classes.dex */
public class MultiSourceHelper {

    public static class MultiSourceResult {
        private final ImageSource bestResult;
        private final ImageSource bestResultInCache;

        private MultiSourceResult(ImageSource imageSource, ImageSource imageSource2) {
            this.bestResult = imageSource;
            this.bestResultInCache = imageSource2;
        }

        public ImageSource getBestResult() {
            return this.bestResult;
        }

        public ImageSource getBestResultInCache() {
            return this.bestResultInCache;
        }
    }

    public static MultiSourceResult getBestSourceForSize(int i, int i2, List<ImageSource> list) {
        return getBestSourceForSize(i, i2, list, 1.0d);
    }

    public static MultiSourceResult getBestSourceForSize(int i, int i2, List<ImageSource> list, double d) {
        ImageSource imageSource = null;
        byte b = 0;
        byte b2 = 0;
        byte b3 = 0;
        byte b4 = 0;
        byte b5 = 0;
        byte b6 = 0;
        byte b7 = 0;
        byte b8 = 0;
        if (list.isEmpty()) {
            return new MultiSourceResult(imageSource, b8 == true ? 1 : 0);
        }
        if (list.size() == 1) {
            return new MultiSourceResult(list.get(0), b6 == true ? 1 : 0);
        }
        if (i <= 0 || i2 <= 0) {
            return new MultiSourceResult(b3 == true ? 1 : 0, b2 == true ? 1 : 0);
        }
        ImagePipeline imagePipeline = ImagePipelineFactory.getInstance().getImagePipeline();
        double d2 = i * i2 * d;
        double d3 = Double.MAX_VALUE;
        double d4 = Double.MAX_VALUE;
        ImageSource imageSource2 = null;
        ImageSource imageSource3 = null;
        for (ImageSource imageSource4 : list) {
            double abs = Math.abs(1.0d - (imageSource4.getSize() / d2));
            if (abs < d3) {
                imageSource3 = imageSource4;
                d3 = abs;
            }
            if (abs < d4 && (imagePipeline.isInBitmapMemoryCache(imageSource4.getUri()) || imagePipeline.isInDiskCacheSync(imageSource4.getUri()))) {
                imageSource2 = imageSource4;
                d4 = abs;
            }
        }
        if (imageSource2 != null && imageSource3 != null && imageSource2.getSource().equals(imageSource3.getSource())) {
            imageSource2 = null;
        }
        return new MultiSourceResult(imageSource3, imageSource2);
    }
}

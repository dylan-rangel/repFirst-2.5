package com.facebook.imagepipeline.producers;

import com.facebook.common.internal.Preconditions;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.EncodedImage;
import javax.annotation.Nullable;

/* loaded from: classes.dex */
public class ThumbnailBranchProducer implements Producer<EncodedImage> {
    private final ThumbnailProducer<EncodedImage>[] mThumbnailProducers;

    public ThumbnailBranchProducer(ThumbnailProducer<EncodedImage>... thumbnailProducers) {
        ThumbnailProducer<EncodedImage>[] thumbnailProducerArr = (ThumbnailProducer[]) Preconditions.checkNotNull(thumbnailProducers);
        this.mThumbnailProducers = thumbnailProducerArr;
        Preconditions.checkElementIndex(0, thumbnailProducerArr.length);
    }

    @Override // com.facebook.imagepipeline.producers.Producer
    public void produceResults(final Consumer<EncodedImage> consumer, final ProducerContext context) {
        if (context.getImageRequest().getResizeOptions() == null) {
            consumer.onNewResult(null, 1);
        } else {
            if (produceResultsFromThumbnailProducer(0, consumer, context)) {
                return;
            }
            consumer.onNewResult(null, 1);
        }
    }

    private class ThumbnailConsumer extends DelegatingConsumer<EncodedImage, EncodedImage> {
        private final ProducerContext mProducerContext;
        private final int mProducerIndex;

        @Nullable
        private final ResizeOptions mResizeOptions;

        public ThumbnailConsumer(final Consumer<EncodedImage> consumer, final ProducerContext producerContext, int producerIndex) {
            super(consumer);
            this.mProducerContext = producerContext;
            this.mProducerIndex = producerIndex;
            this.mResizeOptions = producerContext.getImageRequest().getResizeOptions();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.facebook.imagepipeline.producers.BaseConsumer
        public void onNewResultImpl(@Nullable EncodedImage newResult, int status) {
            if (newResult != null && (isNotLast(status) || ThumbnailSizeChecker.isImageBigEnough(newResult, this.mResizeOptions))) {
                getConsumer().onNewResult(newResult, status);
            } else if (isLast(status)) {
                EncodedImage.closeSafely(newResult);
                if (ThumbnailBranchProducer.this.produceResultsFromThumbnailProducer(this.mProducerIndex + 1, getConsumer(), this.mProducerContext)) {
                    return;
                }
                getConsumer().onNewResult(null, 1);
            }
        }

        @Override // com.facebook.imagepipeline.producers.DelegatingConsumer, com.facebook.imagepipeline.producers.BaseConsumer
        protected void onFailureImpl(Throwable t) {
            if (ThumbnailBranchProducer.this.produceResultsFromThumbnailProducer(this.mProducerIndex + 1, getConsumer(), this.mProducerContext)) {
                return;
            }
            getConsumer().onFailure(t);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean produceResultsFromThumbnailProducer(int startIndex, Consumer<EncodedImage> consumer, ProducerContext context) {
        int findFirstProducerForSize = findFirstProducerForSize(startIndex, context.getImageRequest().getResizeOptions());
        if (findFirstProducerForSize == -1) {
            return false;
        }
        this.mThumbnailProducers[findFirstProducerForSize].produceResults(new ThumbnailConsumer(consumer, context, findFirstProducerForSize), context);
        return true;
    }

    private int findFirstProducerForSize(int startIndex, @Nullable ResizeOptions resizeOptions) {
        while (true) {
            ThumbnailProducer<EncodedImage>[] thumbnailProducerArr = this.mThumbnailProducers;
            if (startIndex >= thumbnailProducerArr.length) {
                return -1;
            }
            if (thumbnailProducerArr[startIndex].canProvideImageForSize(resizeOptions)) {
                return startIndex;
            }
            startIndex++;
        }
    }
}

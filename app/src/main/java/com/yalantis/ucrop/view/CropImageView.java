package com.yalantis.ucrop.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.AttributeSet;
import com.yalantis.ucrop.R;
import com.yalantis.ucrop.callback.BitmapCropCallback;
import com.yalantis.ucrop.callback.CropBoundsChangeListener;
import com.yalantis.ucrop.model.CropParameters;
import com.yalantis.ucrop.model.ImageState;
import com.yalantis.ucrop.task.BitmapCropTask;
import com.yalantis.ucrop.util.CubicEasing;
import com.yalantis.ucrop.util.RectUtils;
import java.lang.ref.WeakReference;
import java.util.Arrays;

/* loaded from: classes.dex */
public class CropImageView extends TransformImageView {
    public static final float DEFAULT_ASPECT_RATIO = 0.0f;
    public static final int DEFAULT_IMAGE_TO_CROP_BOUNDS_ANIM_DURATION = 500;
    public static final int DEFAULT_MAX_BITMAP_SIZE = 0;
    public static final float DEFAULT_MAX_SCALE_MULTIPLIER = 10.0f;
    public static final float SOURCE_IMAGE_ASPECT_RATIO = 0.0f;
    private CropBoundsChangeListener mCropBoundsChangeListener;
    private final RectF mCropRect;
    private long mImageToWrapCropBoundsAnimDuration;
    private int mMaxResultImageSizeX;
    private int mMaxResultImageSizeY;
    private float mMaxScale;
    private float mMaxScaleMultiplier;
    private float mMinScale;
    private float mTargetAspectRatio;
    private final Matrix mTempMatrix;
    private Runnable mWrapCropBoundsRunnable;
    private Runnable mZoomImageToPositionRunnable;

    public CropImageView(Context context) {
        this(context, null);
    }

    public CropImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CropImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mCropRect = new RectF();
        this.mTempMatrix = new Matrix();
        this.mMaxScaleMultiplier = 10.0f;
        this.mZoomImageToPositionRunnable = null;
        this.mMaxResultImageSizeX = 0;
        this.mMaxResultImageSizeY = 0;
        this.mImageToWrapCropBoundsAnimDuration = 500L;
    }

    public void cropAndSaveImage(Bitmap.CompressFormat compressFormat, int i, BitmapCropCallback bitmapCropCallback) {
        cancelAllAnimations();
        setImageToWrapCropBounds(false);
        new BitmapCropTask(getViewBitmap(), new ImageState(this.mCropRect, RectUtils.trapToRect(this.mCurrentImageCorners), getCurrentScale(), getCurrentAngle()), new CropParameters(this.mMaxResultImageSizeX, this.mMaxResultImageSizeY, compressFormat, i, getImageInputPath(), getImageOutputPath(), getExifInfo()), bitmapCropCallback).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
    }

    public float getMaxScale() {
        return this.mMaxScale;
    }

    public float getMinScale() {
        return this.mMinScale;
    }

    public float getTargetAspectRatio() {
        return this.mTargetAspectRatio;
    }

    public void setCropRect(RectF rectF) {
        this.mTargetAspectRatio = rectF.width() / rectF.height();
        this.mCropRect.set(rectF.left - getPaddingLeft(), rectF.top - getPaddingTop(), rectF.right - getPaddingRight(), rectF.bottom - getPaddingBottom());
        calculateImageScaleBounds();
        setImageToWrapCropBounds();
    }

    public void setTargetAspectRatio(float f) {
        if (getDrawable() == null) {
            this.mTargetAspectRatio = f;
            return;
        }
        if (f == 0.0f) {
            this.mTargetAspectRatio = r0.getIntrinsicWidth() / r0.getIntrinsicHeight();
        } else {
            this.mTargetAspectRatio = f;
        }
        CropBoundsChangeListener cropBoundsChangeListener = this.mCropBoundsChangeListener;
        if (cropBoundsChangeListener != null) {
            cropBoundsChangeListener.onCropAspectRatioChanged(this.mTargetAspectRatio);
        }
    }

    public CropBoundsChangeListener getCropBoundsChangeListener() {
        return this.mCropBoundsChangeListener;
    }

    public void setCropBoundsChangeListener(CropBoundsChangeListener cropBoundsChangeListener) {
        this.mCropBoundsChangeListener = cropBoundsChangeListener;
    }

    public void setMaxResultImageSizeX(int i) {
        this.mMaxResultImageSizeX = i;
    }

    public void setMaxResultImageSizeY(int i) {
        this.mMaxResultImageSizeY = i;
    }

    public void setImageToWrapCropBoundsAnimDuration(long j) {
        if (j > 0) {
            this.mImageToWrapCropBoundsAnimDuration = j;
            return;
        }
        throw new IllegalArgumentException("Animation duration cannot be negative value.");
    }

    public void setMaxScaleMultiplier(float f) {
        this.mMaxScaleMultiplier = f;
    }

    public void zoomOutImage(float f) {
        zoomOutImage(f, this.mCropRect.centerX(), this.mCropRect.centerY());
    }

    public void zoomOutImage(float f, float f2, float f3) {
        if (f >= getMinScale()) {
            postScale(f / getCurrentScale(), f2, f3);
        }
    }

    public void zoomInImage(float f) {
        zoomInImage(f, this.mCropRect.centerX(), this.mCropRect.centerY());
    }

    public void zoomInImage(float f, float f2, float f3) {
        if (f <= getMaxScale()) {
            postScale(f / getCurrentScale(), f2, f3);
        }
    }

    @Override // com.yalantis.ucrop.view.TransformImageView
    public void postScale(float f, float f2, float f3) {
        if (f > 1.0f && getCurrentScale() * f <= getMaxScale()) {
            super.postScale(f, f2, f3);
        } else {
            if (f >= 1.0f || getCurrentScale() * f < getMinScale()) {
                return;
            }
            super.postScale(f, f2, f3);
        }
    }

    public void postRotate(float f) {
        postRotate(f, this.mCropRect.centerX(), this.mCropRect.centerY());
    }

    public void cancelAllAnimations() {
        removeCallbacks(this.mWrapCropBoundsRunnable);
        removeCallbacks(this.mZoomImageToPositionRunnable);
    }

    public void setImageToWrapCropBounds() {
        setImageToWrapCropBounds(true);
    }

    public void setImageToWrapCropBounds(boolean z) {
        float f;
        float max;
        float f2;
        if (!this.mBitmapLaidOut || isImageWrapCropBounds()) {
            return;
        }
        float f3 = this.mCurrentImageCenter[0];
        float f4 = this.mCurrentImageCenter[1];
        float currentScale = getCurrentScale();
        float centerX = this.mCropRect.centerX() - f3;
        float centerY = this.mCropRect.centerY() - f4;
        this.mTempMatrix.reset();
        this.mTempMatrix.setTranslate(centerX, centerY);
        float[] copyOf = Arrays.copyOf(this.mCurrentImageCorners, this.mCurrentImageCorners.length);
        this.mTempMatrix.mapPoints(copyOf);
        boolean isImageWrapCropBounds = isImageWrapCropBounds(copyOf);
        if (isImageWrapCropBounds) {
            float[] calculateImageIndents = calculateImageIndents();
            float f5 = -(calculateImageIndents[0] + calculateImageIndents[2]);
            f2 = -(calculateImageIndents[1] + calculateImageIndents[3]);
            f = f5;
            max = 0.0f;
        } else {
            RectF rectF = new RectF(this.mCropRect);
            this.mTempMatrix.reset();
            this.mTempMatrix.setRotate(getCurrentAngle());
            this.mTempMatrix.mapRect(rectF);
            float[] rectSidesFromCorners = RectUtils.getRectSidesFromCorners(this.mCurrentImageCorners);
            f = centerX;
            max = (Math.max(rectF.width() / rectSidesFromCorners[0], rectF.height() / rectSidesFromCorners[1]) * currentScale) - currentScale;
            f2 = centerY;
        }
        if (z) {
            WrapCropBoundsRunnable wrapCropBoundsRunnable = new WrapCropBoundsRunnable(this, this.mImageToWrapCropBoundsAnimDuration, f3, f4, f, f2, currentScale, max, isImageWrapCropBounds);
            this.mWrapCropBoundsRunnable = wrapCropBoundsRunnable;
            post(wrapCropBoundsRunnable);
        } else {
            postTranslate(f, f2);
            if (isImageWrapCropBounds) {
                return;
            }
            zoomInImage(currentScale + max, this.mCropRect.centerX(), this.mCropRect.centerY());
        }
    }

    private float[] calculateImageIndents() {
        this.mTempMatrix.reset();
        this.mTempMatrix.setRotate(-getCurrentAngle());
        float[] copyOf = Arrays.copyOf(this.mCurrentImageCorners, this.mCurrentImageCorners.length);
        float[] cornersFromRect = RectUtils.getCornersFromRect(this.mCropRect);
        this.mTempMatrix.mapPoints(copyOf);
        this.mTempMatrix.mapPoints(cornersFromRect);
        RectF trapToRect = RectUtils.trapToRect(copyOf);
        RectF trapToRect2 = RectUtils.trapToRect(cornersFromRect);
        float f = trapToRect.left - trapToRect2.left;
        float f2 = trapToRect.top - trapToRect2.top;
        float f3 = trapToRect.right - trapToRect2.right;
        float f4 = trapToRect.bottom - trapToRect2.bottom;
        float[] fArr = new float[4];
        if (f <= 0.0f) {
            f = 0.0f;
        }
        fArr[0] = f;
        if (f2 <= 0.0f) {
            f2 = 0.0f;
        }
        fArr[1] = f2;
        if (f3 >= 0.0f) {
            f3 = 0.0f;
        }
        fArr[2] = f3;
        if (f4 >= 0.0f) {
            f4 = 0.0f;
        }
        fArr[3] = f4;
        this.mTempMatrix.reset();
        this.mTempMatrix.setRotate(getCurrentAngle());
        this.mTempMatrix.mapPoints(fArr);
        return fArr;
    }

    @Override // com.yalantis.ucrop.view.TransformImageView
    protected void onImageLaidOut() {
        super.onImageLaidOut();
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return;
        }
        float intrinsicWidth = drawable.getIntrinsicWidth();
        float intrinsicHeight = drawable.getIntrinsicHeight();
        if (this.mTargetAspectRatio == 0.0f) {
            this.mTargetAspectRatio = intrinsicWidth / intrinsicHeight;
        }
        int i = (int) (this.mThisWidth / this.mTargetAspectRatio);
        if (i > this.mThisHeight) {
            this.mCropRect.set((this.mThisWidth - ((int) (this.mThisHeight * this.mTargetAspectRatio))) / 2, 0.0f, r2 + r4, this.mThisHeight);
        } else {
            this.mCropRect.set(0.0f, (this.mThisHeight - i) / 2, this.mThisWidth, i + r4);
        }
        calculateImageScaleBounds(intrinsicWidth, intrinsicHeight);
        setupInitialImagePosition(intrinsicWidth, intrinsicHeight);
        CropBoundsChangeListener cropBoundsChangeListener = this.mCropBoundsChangeListener;
        if (cropBoundsChangeListener != null) {
            cropBoundsChangeListener.onCropAspectRatioChanged(this.mTargetAspectRatio);
        }
        if (this.mTransformImageListener != null) {
            this.mTransformImageListener.onScale(getCurrentScale());
            this.mTransformImageListener.onRotate(getCurrentAngle());
        }
    }

    protected boolean isImageWrapCropBounds() {
        return isImageWrapCropBounds(this.mCurrentImageCorners);
    }

    protected boolean isImageWrapCropBounds(float[] fArr) {
        this.mTempMatrix.reset();
        this.mTempMatrix.setRotate(-getCurrentAngle());
        float[] copyOf = Arrays.copyOf(fArr, fArr.length);
        this.mTempMatrix.mapPoints(copyOf);
        float[] cornersFromRect = RectUtils.getCornersFromRect(this.mCropRect);
        this.mTempMatrix.mapPoints(cornersFromRect);
        return RectUtils.trapToRect(copyOf).contains(RectUtils.trapToRect(cornersFromRect));
    }

    protected void zoomImageToPosition(float f, float f2, float f3, long j) {
        if (f > getMaxScale()) {
            f = getMaxScale();
        }
        float currentScale = getCurrentScale();
        ZoomImageToPosition zoomImageToPosition = new ZoomImageToPosition(this, j, currentScale, f - currentScale, f2, f3);
        this.mZoomImageToPositionRunnable = zoomImageToPosition;
        post(zoomImageToPosition);
    }

    private void calculateImageScaleBounds() {
        if (getDrawable() == null) {
            return;
        }
        calculateImageScaleBounds(r0.getIntrinsicWidth(), r0.getIntrinsicHeight());
    }

    private void calculateImageScaleBounds(float f, float f2) {
        float min = Math.min(Math.min(this.mCropRect.width() / f, this.mCropRect.width() / f2), Math.min(this.mCropRect.height() / f2, this.mCropRect.height() / f));
        this.mMinScale = min;
        this.mMaxScale = min * this.mMaxScaleMultiplier;
    }

    private void setupInitialImagePosition(float f, float f2) {
        float width = this.mCropRect.width();
        float height = this.mCropRect.height();
        float max = Math.max(this.mCropRect.width() / f, this.mCropRect.height() / f2);
        float f3 = ((width - (f * max)) / 2.0f) + this.mCropRect.left;
        float f4 = ((height - (f2 * max)) / 2.0f) + this.mCropRect.top;
        this.mCurrentImageMatrix.reset();
        this.mCurrentImageMatrix.postScale(max, max);
        this.mCurrentImageMatrix.postTranslate(f3, f4);
        setImageMatrix(this.mCurrentImageMatrix);
    }

    protected void processStyledAttributes(TypedArray typedArray) {
        float abs = Math.abs(typedArray.getFloat(R.styleable.ucrop_UCropView_ucrop_aspect_ratio_x, 0.0f));
        float abs2 = Math.abs(typedArray.getFloat(R.styleable.ucrop_UCropView_ucrop_aspect_ratio_y, 0.0f));
        if (abs == 0.0f || abs2 == 0.0f) {
            this.mTargetAspectRatio = 0.0f;
        } else {
            this.mTargetAspectRatio = abs / abs2;
        }
    }

    private static class WrapCropBoundsRunnable implements Runnable {
        private final float mCenterDiffX;
        private final float mCenterDiffY;
        private final WeakReference<CropImageView> mCropImageView;
        private final float mDeltaScale;
        private final long mDurationMs;
        private final float mOldScale;
        private final float mOldX;
        private final float mOldY;
        private final long mStartTime = System.currentTimeMillis();
        private final boolean mWillBeImageInBoundsAfterTranslate;

        public WrapCropBoundsRunnable(CropImageView cropImageView, long j, float f, float f2, float f3, float f4, float f5, float f6, boolean z) {
            this.mCropImageView = new WeakReference<>(cropImageView);
            this.mDurationMs = j;
            this.mOldX = f;
            this.mOldY = f2;
            this.mCenterDiffX = f3;
            this.mCenterDiffY = f4;
            this.mOldScale = f5;
            this.mDeltaScale = f6;
            this.mWillBeImageInBoundsAfterTranslate = z;
        }

        @Override // java.lang.Runnable
        public void run() {
            CropImageView cropImageView = this.mCropImageView.get();
            if (cropImageView == null) {
                return;
            }
            float min = Math.min(this.mDurationMs, System.currentTimeMillis() - this.mStartTime);
            float easeOut = CubicEasing.easeOut(min, 0.0f, this.mCenterDiffX, this.mDurationMs);
            float easeOut2 = CubicEasing.easeOut(min, 0.0f, this.mCenterDiffY, this.mDurationMs);
            float easeInOut = CubicEasing.easeInOut(min, 0.0f, this.mDeltaScale, this.mDurationMs);
            if (min < this.mDurationMs) {
                cropImageView.postTranslate(easeOut - (cropImageView.mCurrentImageCenter[0] - this.mOldX), easeOut2 - (cropImageView.mCurrentImageCenter[1] - this.mOldY));
                if (!this.mWillBeImageInBoundsAfterTranslate) {
                    cropImageView.zoomInImage(this.mOldScale + easeInOut, cropImageView.mCropRect.centerX(), cropImageView.mCropRect.centerY());
                }
                if (cropImageView.isImageWrapCropBounds()) {
                    return;
                }
                cropImageView.post(this);
            }
        }
    }

    private static class ZoomImageToPosition implements Runnable {
        private final WeakReference<CropImageView> mCropImageView;
        private final float mDeltaScale;
        private final float mDestX;
        private final float mDestY;
        private final long mDurationMs;
        private final float mOldScale;
        private final long mStartTime = System.currentTimeMillis();

        public ZoomImageToPosition(CropImageView cropImageView, long j, float f, float f2, float f3, float f4) {
            this.mCropImageView = new WeakReference<>(cropImageView);
            this.mDurationMs = j;
            this.mOldScale = f;
            this.mDeltaScale = f2;
            this.mDestX = f3;
            this.mDestY = f4;
        }

        @Override // java.lang.Runnable
        public void run() {
            CropImageView cropImageView = this.mCropImageView.get();
            if (cropImageView == null) {
                return;
            }
            float min = Math.min(this.mDurationMs, System.currentTimeMillis() - this.mStartTime);
            float easeInOut = CubicEasing.easeInOut(min, 0.0f, this.mDeltaScale, this.mDurationMs);
            if (min < this.mDurationMs) {
                cropImageView.zoomInImage(this.mOldScale + easeInOut, this.mDestX, this.mDestY);
                cropImageView.post(this);
            } else {
                cropImageView.setImageToWrapCropBounds();
            }
        }
    }
}

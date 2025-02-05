package com.horcrux.svg;

import android.content.res.Resources;
import android.graphics.Matrix;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.graphics.Region;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.StandardCharsets;
import com.facebook.react.module.annotations.ReactModule;
import com.horcrux.rnsvg.NativeSvgRenderableModuleSpec;
import io.sentry.SentryEnvelopeItemHeader;
import io.sentry.protocol.ViewHierarchyNode;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.annotation.Nonnull;

@ReactModule(name = RNSVGRenderableManager.NAME)
/* loaded from: classes2.dex */
class RNSVGRenderableManager extends NativeSvgRenderableModuleSpec {
    private static final int DEFAULT_BUFFER_SIZE = 4096;
    private static final int EOF = -1;
    public static final String NAME = "RNSVGRenderableModule";

    @Override // com.facebook.react.bridge.NativeModule
    @Nonnull
    public String getName() {
        return NAME;
    }

    RNSVGRenderableManager(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
    }

    @Override // com.horcrux.rnsvg.NativeSvgRenderableModuleSpec
    @ReactMethod(isBlockingSynchronousMethod = true)
    public boolean isPointInFill(Double d, ReadableMap readableMap) {
        RenderableView renderableViewByTag = RenderableViewManager.getRenderableViewByTag(d.intValue());
        if (renderableViewByTag == null) {
            return false;
        }
        float f = renderableViewByTag.mScale;
        return renderableViewByTag.hitTest(new float[]{((float) readableMap.getDouble(ViewHierarchyNode.JsonKeys.X)) * f, ((float) readableMap.getDouble(ViewHierarchyNode.JsonKeys.Y)) * f}) != -1;
    }

    @Override // com.horcrux.rnsvg.NativeSvgRenderableModuleSpec
    @ReactMethod(isBlockingSynchronousMethod = true)
    public boolean isPointInStroke(Double d, ReadableMap readableMap) {
        RenderableView renderableViewByTag = RenderableViewManager.getRenderableViewByTag(d.intValue());
        if (renderableViewByTag == null) {
            return false;
        }
        try {
            renderableViewByTag.getPath(null, null);
            renderableViewByTag.initBounds();
            double d2 = renderableViewByTag.mScale;
            int i = (int) (readableMap.getDouble(ViewHierarchyNode.JsonKeys.X) * d2);
            int i2 = (int) (readableMap.getDouble(ViewHierarchyNode.JsonKeys.Y) * d2);
            Region region = renderableViewByTag.mStrokeRegion;
            return region != null && region.contains(i, i2);
        } catch (NullPointerException unused) {
            renderableViewByTag.invalidate();
            return false;
        }
    }

    @Override // com.horcrux.rnsvg.NativeSvgRenderableModuleSpec
    @ReactMethod(isBlockingSynchronousMethod = true)
    public double getTotalLength(Double d) {
        RenderableView renderableViewByTag = RenderableViewManager.getRenderableViewByTag(d.intValue());
        if (renderableViewByTag == null) {
            return 0.0d;
        }
        try {
            return new PathMeasure(renderableViewByTag.getPath(null, null), false).getLength() / renderableViewByTag.mScale;
        } catch (NullPointerException unused) {
            renderableViewByTag.invalidate();
            return -1.0d;
        }
    }

    @Override // com.horcrux.rnsvg.NativeSvgRenderableModuleSpec
    @ReactMethod(isBlockingSynchronousMethod = true)
    public WritableMap getPointAtLength(Double d, ReadableMap readableMap) {
        RenderableView renderableViewByTag = RenderableViewManager.getRenderableViewByTag(d.intValue());
        if (renderableViewByTag == null) {
            return Arguments.createMap();
        }
        try {
            PathMeasure pathMeasure = new PathMeasure(renderableViewByTag.getPath(null, null), false);
            float[] fArr = new float[2];
            float[] fArr2 = new float[2];
            pathMeasure.getPosTan(Math.max(0.0f, Math.min(((float) readableMap.getDouble(SentryEnvelopeItemHeader.JsonKeys.LENGTH)) * renderableViewByTag.mScale, pathMeasure.getLength())), fArr, fArr2);
            double atan2 = Math.atan2(fArr2[1], fArr2[0]);
            WritableMap createMap = Arguments.createMap();
            createMap.putDouble(ViewHierarchyNode.JsonKeys.X, fArr[0] / r8);
            createMap.putDouble(ViewHierarchyNode.JsonKeys.Y, fArr[1] / r8);
            createMap.putDouble("angle", atan2);
            return createMap;
        } catch (NullPointerException unused) {
            renderableViewByTag.invalidate();
            return Arguments.createMap();
        }
    }

    @Override // com.horcrux.rnsvg.NativeSvgRenderableModuleSpec
    @ReactMethod(isBlockingSynchronousMethod = true)
    public WritableMap getBBox(Double d, ReadableMap readableMap) {
        RenderableView renderableViewByTag = RenderableViewManager.getRenderableViewByTag(d.intValue());
        if (renderableViewByTag == null) {
            return Arguments.createMap();
        }
        boolean z = readableMap.getBoolean("fill");
        boolean z2 = readableMap.getBoolean("stroke");
        boolean z3 = readableMap.getBoolean("markers");
        boolean z4 = readableMap.getBoolean("clipped");
        try {
            renderableViewByTag.getPath(null, null);
            float f = renderableViewByTag.mScale;
            renderableViewByTag.initBounds();
            RectF rectF = new RectF();
            RectF rectF2 = renderableViewByTag.mFillBounds;
            RectF rectF3 = renderableViewByTag.mStrokeBounds;
            RectF rectF4 = renderableViewByTag.mMarkerBounds;
            RectF rectF5 = renderableViewByTag.mClipBounds;
            if (z && rectF2 != null) {
                rectF.union(rectF2);
            }
            if (z2 && rectF3 != null) {
                rectF.union(rectF3);
            }
            if (z3 && rectF4 != null) {
                rectF.union(rectF4);
            }
            if (z4 && rectF5 != null) {
                rectF.intersect(rectF5);
            }
            WritableMap createMap = Arguments.createMap();
            createMap.putDouble(ViewHierarchyNode.JsonKeys.X, rectF.left / f);
            createMap.putDouble(ViewHierarchyNode.JsonKeys.Y, rectF.top / f);
            createMap.putDouble("width", rectF.width() / f);
            createMap.putDouble("height", rectF.height() / f);
            return createMap;
        } catch (NullPointerException unused) {
            renderableViewByTag.invalidate();
            return Arguments.createMap();
        }
    }

    @Override // com.horcrux.rnsvg.NativeSvgRenderableModuleSpec
    @ReactMethod(isBlockingSynchronousMethod = true)
    public WritableMap getCTM(Double d) {
        RenderableView renderableViewByTag = RenderableViewManager.getRenderableViewByTag(d.intValue());
        if (renderableViewByTag == null) {
            return Arguments.createMap();
        }
        float f = renderableViewByTag.mScale;
        Matrix matrix = new Matrix(renderableViewByTag.mCTM);
        SvgView svgView = renderableViewByTag.getSvgView();
        if (svgView == null) {
            throw new RuntimeException("Did not find parent SvgView for view with tag: " + d);
        }
        matrix.preConcat(svgView.mInvViewBoxMatrix);
        matrix.getValues(new float[9]);
        WritableMap createMap = Arguments.createMap();
        createMap.putDouble("a", r6[0]);
        createMap.putDouble("b", r6[3]);
        createMap.putDouble("c", r6[1]);
        createMap.putDouble("d", r6[4]);
        createMap.putDouble("e", r6[2] / f);
        createMap.putDouble("f", r6[5] / f);
        return createMap;
    }

    @Override // com.horcrux.rnsvg.NativeSvgRenderableModuleSpec
    @ReactMethod(isBlockingSynchronousMethod = true)
    public WritableMap getScreenCTM(Double d) {
        RenderableView renderableViewByTag = RenderableViewManager.getRenderableViewByTag(d.intValue());
        if (renderableViewByTag == null) {
            return Arguments.createMap();
        }
        renderableViewByTag.mCTM.getValues(new float[9]);
        float f = renderableViewByTag.mScale;
        WritableMap createMap = Arguments.createMap();
        createMap.putDouble("a", r0[0]);
        createMap.putDouble("b", r0[3]);
        createMap.putDouble("c", r0[1]);
        createMap.putDouble("d", r0[4]);
        createMap.putDouble("e", r0[2] / f);
        createMap.putDouble("f", r0[5] / f);
        return createMap;
    }

    @Override // com.horcrux.rnsvg.NativeSvgRenderableModuleSpec
    @ReactMethod
    public void getRawResource(String str, Promise promise) {
        try {
            ReactApplicationContext reactApplicationContext = getReactApplicationContext();
            Resources resources = reactApplicationContext.getResources();
            InputStream openRawResource = resources.openRawResource(resources.getIdentifier(str, "raw", reactApplicationContext.getPackageName()));
            try {
                InputStreamReader inputStreamReader = new InputStreamReader(openRawResource, StandardCharsets.UTF_8);
                char[] cArr = new char[4096];
                StringBuilder sb = new StringBuilder();
                while (true) {
                    int read = inputStreamReader.read(cArr, 0, 4096);
                    if (read != -1) {
                        sb.append(cArr, 0, read);
                    } else {
                        promise.resolve(sb.toString());
                        try {
                            return;
                        } catch (IOException unused) {
                            return;
                        }
                    }
                }
            } finally {
                try {
                    openRawResource.close();
                } catch (IOException unused2) {
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            promise.reject(e);
        }
    }
}

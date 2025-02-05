package com.horcrux.svg;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.view.View;
import android.view.ViewParent;
import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableArray;
import com.horcrux.svg.TextProperties;
import java.util.ArrayList;
import javax.annotation.Nullable;

/* loaded from: classes2.dex */
class TextView extends GroupView {
    double cachedAdvance;
    private TextProperties.AlignmentBaseline mAlignmentBaseline;
    private String mBaselineShift;

    @Nullable
    private ArrayList<SVGLength> mDeltaX;

    @Nullable
    private ArrayList<SVGLength> mDeltaY;
    SVGLength mInlineSize;
    TextProperties.TextLengthAdjust mLengthAdjust;

    @Nullable
    private ArrayList<SVGLength> mPositionX;

    @Nullable
    private ArrayList<SVGLength> mPositionY;

    @Nullable
    private ArrayList<SVGLength> mRotate;
    SVGLength mTextLength;

    public TextView(ReactContext reactContext) {
        super(reactContext);
        this.mInlineSize = null;
        this.mTextLength = null;
        this.mBaselineShift = null;
        this.mLengthAdjust = TextProperties.TextLengthAdjust.spacing;
        this.cachedAdvance = Double.NaN;
    }

    @Override // com.horcrux.svg.VirtualView, android.view.View
    public void invalidate() {
        if (this.mPath == null) {
            return;
        }
        super.invalidate();
        getTextContainer().clearChildCache();
    }

    @Override // com.horcrux.svg.VirtualView
    void clearCache() {
        this.cachedAdvance = Double.NaN;
        super.clearCache();
    }

    public void setInlineSize(Dynamic dynamic) {
        this.mInlineSize = SVGLength.from(dynamic);
        invalidate();
    }

    public void setInlineSize(String str) {
        this.mInlineSize = SVGLength.from(str);
        invalidate();
    }

    public void setInlineSize(Double d) {
        this.mInlineSize = SVGLength.from(d);
        invalidate();
    }

    public void setTextLength(Dynamic dynamic) {
        this.mTextLength = SVGLength.from(dynamic);
        invalidate();
    }

    public void setTextLength(String str) {
        this.mTextLength = SVGLength.from(str);
        invalidate();
    }

    public void setTextLength(Double d) {
        this.mTextLength = SVGLength.from(d);
        invalidate();
    }

    public void setLengthAdjust(@Nullable String str) {
        this.mLengthAdjust = TextProperties.TextLengthAdjust.valueOf(str);
        invalidate();
    }

    public void setMethod(@Nullable String str) {
        this.mAlignmentBaseline = TextProperties.AlignmentBaseline.getEnum(str);
        invalidate();
    }

    public void setBaselineShift(Dynamic dynamic) {
        this.mBaselineShift = SVGLength.toString(dynamic);
        invalidate();
    }

    public void setBaselineShift(String str) {
        this.mBaselineShift = str;
        invalidate();
    }

    public void setBaselineShift(Double d) {
        this.mBaselineShift = String.valueOf(d);
        invalidate();
    }

    public void setVerticalAlign(@Nullable String str) {
        if (str != null) {
            String trim = str.trim();
            int lastIndexOf = trim.lastIndexOf(32);
            try {
                this.mAlignmentBaseline = TextProperties.AlignmentBaseline.getEnum(trim.substring(lastIndexOf));
            } catch (IllegalArgumentException unused) {
                this.mAlignmentBaseline = TextProperties.AlignmentBaseline.baseline;
            }
            try {
                this.mBaselineShift = trim.substring(0, lastIndexOf);
            } catch (IndexOutOfBoundsException unused2) {
                this.mBaselineShift = null;
            }
        } else {
            this.mAlignmentBaseline = TextProperties.AlignmentBaseline.baseline;
            this.mBaselineShift = null;
        }
        invalidate();
    }

    public void setRotate(Dynamic dynamic) {
        this.mRotate = SVGLength.arrayFrom(dynamic);
        invalidate();
    }

    public void setRotate(ReadableArray readableArray) {
        this.mRotate = SVGLength.arrayFrom(readableArray);
        invalidate();
    }

    public void setDeltaX(Dynamic dynamic) {
        this.mDeltaX = SVGLength.arrayFrom(dynamic);
        invalidate();
    }

    public void setDeltaX(ReadableArray readableArray) {
        this.mDeltaX = SVGLength.arrayFrom(readableArray);
        invalidate();
    }

    public void setDeltaY(Dynamic dynamic) {
        this.mDeltaY = SVGLength.arrayFrom(dynamic);
        invalidate();
    }

    public void setDeltaY(ReadableArray readableArray) {
        this.mDeltaY = SVGLength.arrayFrom(readableArray);
        invalidate();
    }

    public void setPositionX(Dynamic dynamic) {
        this.mPositionX = SVGLength.arrayFrom(dynamic);
        invalidate();
    }

    public void setPositionX(ReadableArray readableArray) {
        this.mPositionX = SVGLength.arrayFrom(readableArray);
        invalidate();
    }

    public void setPositionY(Dynamic dynamic) {
        this.mPositionY = SVGLength.arrayFrom(dynamic);
        invalidate();
    }

    public void setPositionY(ReadableArray readableArray) {
        this.mPositionY = SVGLength.arrayFrom(readableArray);
        invalidate();
    }

    @Override // com.horcrux.svg.GroupView, com.horcrux.svg.RenderableView, com.horcrux.svg.VirtualView
    void draw(Canvas canvas, Paint paint, float f) {
        setupGlyphContext(canvas);
        clip(canvas, paint);
        getGroupPath(canvas, paint);
        pushGlyphContext();
        drawGroup(canvas, paint, f);
        popGlyphContext();
    }

    @Override // com.horcrux.svg.GroupView, com.horcrux.svg.RenderableView, com.horcrux.svg.VirtualView
    Path getPath(Canvas canvas, Paint paint) {
        if (this.mPath != null) {
            return this.mPath;
        }
        setupGlyphContext(canvas);
        return getGroupPath(canvas, paint);
    }

    @Override // com.horcrux.svg.GroupView
    Path getPath(Canvas canvas, Paint paint, Region.Op op) {
        return getPath(canvas, paint);
    }

    TextProperties.AlignmentBaseline getAlignmentBaseline() {
        TextProperties.AlignmentBaseline alignmentBaseline;
        if (this.mAlignmentBaseline == null) {
            for (ViewParent parent = getParent(); parent != null; parent = parent.getParent()) {
                if ((parent instanceof TextView) && (alignmentBaseline = ((TextView) parent).mAlignmentBaseline) != null) {
                    this.mAlignmentBaseline = alignmentBaseline;
                    return alignmentBaseline;
                }
            }
        }
        if (this.mAlignmentBaseline == null) {
            this.mAlignmentBaseline = TextProperties.AlignmentBaseline.baseline;
        }
        return this.mAlignmentBaseline;
    }

    String getBaselineShift() {
        String str;
        if (this.mBaselineShift == null) {
            for (ViewParent parent = getParent(); parent != null; parent = parent.getParent()) {
                if ((parent instanceof TextView) && (str = ((TextView) parent).mBaselineShift) != null) {
                    this.mBaselineShift = str;
                    return str;
                }
            }
        }
        return this.mBaselineShift;
    }

    Path getGroupPath(Canvas canvas, Paint paint) {
        if (this.mPath != null) {
            return this.mPath;
        }
        pushGlyphContext();
        this.mPath = super.getPath(canvas, paint);
        popGlyphContext();
        return this.mPath;
    }

    @Override // com.horcrux.svg.GroupView
    void pushGlyphContext() {
        getTextRootGlyphContext().pushContext(((this instanceof TextPathView) || (this instanceof TSpanView)) ? false : true, this, this.mFont, this.mPositionX, this.mPositionY, this.mDeltaX, this.mDeltaY, this.mRotate);
    }

    TextView getTextAnchorRoot() {
        ArrayList<FontData> arrayList = getTextRootGlyphContext().mFontContext;
        ViewParent parent = getParent();
        TextView textView = this;
        for (int size = arrayList.size() - 1; size >= 0 && (parent instanceof TextView) && arrayList.get(size).textAnchor != TextProperties.TextAnchor.start && textView.mPositionX == null; size--) {
            textView = (TextView) parent;
            parent = textView.getParent();
        }
        return textView;
    }

    double getSubtreeTextChunksTotalAdvance(Paint paint) {
        if (!Double.isNaN(this.cachedAdvance)) {
            return this.cachedAdvance;
        }
        double d = 0.0d;
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof TextView) {
                d += ((TextView) childAt).getSubtreeTextChunksTotalAdvance(paint);
            }
        }
        this.cachedAdvance = d;
        return d;
    }

    TextView getTextContainer() {
        ViewParent parent = getParent();
        TextView textView = this;
        while (parent instanceof TextView) {
            textView = (TextView) parent;
            parent = textView.getParent();
        }
        return textView;
    }
}

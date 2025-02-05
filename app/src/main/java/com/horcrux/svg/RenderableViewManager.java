package com.horcrux.svg;

import android.view.View;
import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.JavaOnlyMap;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.uimanager.ViewProps;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.viewmanagers.RNSVGCircleManagerDelegate;
import com.facebook.react.viewmanagers.RNSVGCircleManagerInterface;
import com.facebook.react.viewmanagers.RNSVGClipPathManagerDelegate;
import com.facebook.react.viewmanagers.RNSVGClipPathManagerInterface;
import com.facebook.react.viewmanagers.RNSVGDefsManagerDelegate;
import com.facebook.react.viewmanagers.RNSVGDefsManagerInterface;
import com.facebook.react.viewmanagers.RNSVGEllipseManagerDelegate;
import com.facebook.react.viewmanagers.RNSVGEllipseManagerInterface;
import com.facebook.react.viewmanagers.RNSVGForeignObjectManagerDelegate;
import com.facebook.react.viewmanagers.RNSVGForeignObjectManagerInterface;
import com.facebook.react.viewmanagers.RNSVGGroupManagerDelegate;
import com.facebook.react.viewmanagers.RNSVGGroupManagerInterface;
import com.facebook.react.viewmanagers.RNSVGImageManagerDelegate;
import com.facebook.react.viewmanagers.RNSVGImageManagerInterface;
import com.facebook.react.viewmanagers.RNSVGLineManagerDelegate;
import com.facebook.react.viewmanagers.RNSVGLineManagerInterface;
import com.facebook.react.viewmanagers.RNSVGLinearGradientManagerDelegate;
import com.facebook.react.viewmanagers.RNSVGLinearGradientManagerInterface;
import com.facebook.react.viewmanagers.RNSVGMarkerManagerDelegate;
import com.facebook.react.viewmanagers.RNSVGMarkerManagerInterface;
import com.facebook.react.viewmanagers.RNSVGMaskManagerDelegate;
import com.facebook.react.viewmanagers.RNSVGMaskManagerInterface;
import com.facebook.react.viewmanagers.RNSVGPathManagerDelegate;
import com.facebook.react.viewmanagers.RNSVGPathManagerInterface;
import com.facebook.react.viewmanagers.RNSVGPatternManagerDelegate;
import com.facebook.react.viewmanagers.RNSVGPatternManagerInterface;
import com.facebook.react.viewmanagers.RNSVGRadialGradientManagerDelegate;
import com.facebook.react.viewmanagers.RNSVGRadialGradientManagerInterface;
import com.facebook.react.viewmanagers.RNSVGRectManagerDelegate;
import com.facebook.react.viewmanagers.RNSVGRectManagerInterface;
import com.facebook.react.viewmanagers.RNSVGSymbolManagerDelegate;
import com.facebook.react.viewmanagers.RNSVGSymbolManagerInterface;
import com.facebook.react.viewmanagers.RNSVGTSpanManagerDelegate;
import com.facebook.react.viewmanagers.RNSVGTSpanManagerInterface;
import com.facebook.react.viewmanagers.RNSVGTextManagerDelegate;
import com.facebook.react.viewmanagers.RNSVGTextManagerInterface;
import com.facebook.react.viewmanagers.RNSVGTextPathManagerDelegate;
import com.facebook.react.viewmanagers.RNSVGTextPathManagerInterface;
import com.facebook.react.viewmanagers.RNSVGUseManagerDelegate;
import com.facebook.react.viewmanagers.RNSVGUseManagerInterface;
import com.horcrux.svg.RenderableView;
import com.horcrux.svg.VirtualViewManager;
import io.sentry.protocol.ViewHierarchyNode;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/* loaded from: classes2.dex */
class RenderableViewManager<T extends RenderableView> extends VirtualViewManager<T> {
    RenderableViewManager(VirtualViewManager.SVGClass sVGClass) {
        super(sVGClass);
    }

    static class GroupViewManagerAbstract<U extends GroupView> extends RenderableViewManager<U> {
        GroupViewManagerAbstract(VirtualViewManager.SVGClass sVGClass) {
            super(sVGClass);
        }

        @ReactProp(name = "font")
        public void setFont(U u, @Nullable ReadableMap readableMap) {
            u.setFont(readableMap);
        }

        @ReactProp(name = ViewProps.FONT_SIZE)
        public void setFontSize(U u, Dynamic dynamic) {
            JavaOnlyMap javaOnlyMap = new JavaOnlyMap();
            int i = AnonymousClass1.$SwitchMap$com$facebook$react$bridge$ReadableType[dynamic.getType().ordinal()];
            if (i == 1) {
                javaOnlyMap.putDouble(ViewProps.FONT_SIZE, dynamic.asDouble());
            } else if (i != 2) {
                return;
            } else {
                javaOnlyMap.putString(ViewProps.FONT_SIZE, dynamic.asString());
            }
            u.setFont(javaOnlyMap);
        }

        public void setFontSize(U u, @Nullable String str) {
            JavaOnlyMap javaOnlyMap = new JavaOnlyMap();
            javaOnlyMap.putString(ViewProps.FONT_SIZE, str);
            u.setFont(javaOnlyMap);
        }

        public void setFontSize(U u, @Nullable Double d) {
            JavaOnlyMap javaOnlyMap = new JavaOnlyMap();
            javaOnlyMap.putDouble(ViewProps.FONT_SIZE, d.doubleValue());
            u.setFont(javaOnlyMap);
        }

        @ReactProp(name = ViewProps.FONT_WEIGHT)
        public void setFontWeight(U u, Dynamic dynamic) {
            JavaOnlyMap javaOnlyMap = new JavaOnlyMap();
            int i = AnonymousClass1.$SwitchMap$com$facebook$react$bridge$ReadableType[dynamic.getType().ordinal()];
            if (i == 1) {
                javaOnlyMap.putDouble(ViewProps.FONT_WEIGHT, dynamic.asDouble());
            } else if (i != 2) {
                return;
            } else {
                javaOnlyMap.putString(ViewProps.FONT_WEIGHT, dynamic.asString());
            }
            u.setFont(javaOnlyMap);
        }

        public void setFontWeight(U u, @Nullable String str) {
            JavaOnlyMap javaOnlyMap = new JavaOnlyMap();
            javaOnlyMap.putString(ViewProps.FONT_WEIGHT, str);
            u.setFont(javaOnlyMap);
        }

        public void setFontWeight(U u, @Nullable Double d) {
            JavaOnlyMap javaOnlyMap = new JavaOnlyMap();
            javaOnlyMap.putDouble(ViewProps.FONT_WEIGHT, d.doubleValue());
            u.setFont(javaOnlyMap);
        }
    }

    /* renamed from: com.horcrux.svg.RenderableViewManager$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$facebook$react$bridge$ReadableType;

        static {
            int[] iArr = new int[ReadableType.values().length];
            $SwitchMap$com$facebook$react$bridge$ReadableType = iArr;
            try {
                iArr[ReadableType.Number.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$facebook$react$bridge$ReadableType[ReadableType.String.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    static class GroupViewManager extends GroupViewManagerAbstract<GroupView> implements RNSVGGroupManagerInterface<GroupView> {
        public static final String REACT_CLASS = "RNSVGGroup";

        @Override // com.facebook.react.viewmanagers.RNSVGGroupManagerInterface
        @ReactProp(name = "clipPath")
        public /* bridge */ /* synthetic */ void setClipPath(GroupView groupView, String str) {
            super.setClipPath((GroupViewManager) groupView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGGroupManagerInterface
        @ReactProp(name = "clipRule")
        public /* bridge */ /* synthetic */ void setClipRule(GroupView groupView, int i) {
            super.setClipRule((GroupViewManager) groupView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGGroupManagerInterface
        @ReactProp(name = "display")
        public /* bridge */ /* synthetic */ void setDisplay(GroupView groupView, String str) {
            super.setDisplay((GroupViewManager) groupView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGGroupManagerInterface
        public /* bridge */ /* synthetic */ void setFill(GroupView groupView, @Nullable ReadableMap readableMap) {
            super.setFill((GroupViewManager) groupView, readableMap);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGGroupManagerInterface
        @ReactProp(defaultFloat = 1.0f, name = "fillOpacity")
        public /* bridge */ /* synthetic */ void setFillOpacity(GroupView groupView, float f) {
            super.setFillOpacity((GroupViewManager) groupView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGGroupManagerInterface
        @ReactProp(defaultInt = 1, name = "fillRule")
        public /* bridge */ /* synthetic */ void setFillRule(GroupView groupView, int i) {
            super.setFillRule((GroupViewManager) groupView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGGroupManagerInterface
        @ReactProp(name = "font")
        public /* bridge */ /* synthetic */ void setFont(GroupView groupView, @Nullable ReadableMap readableMap) {
            super.setFont((GroupViewManager) groupView, readableMap);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGGroupManagerInterface
        public /* bridge */ /* synthetic */ void setFontSize(GroupView groupView, @Nullable Double d) {
            super.setFontSize((GroupViewManager) groupView, d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGGroupManagerInterface
        public /* bridge */ /* synthetic */ void setFontSize(GroupView groupView, @Nullable String str) {
            super.setFontSize((GroupViewManager) groupView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGGroupManagerInterface
        public /* bridge */ /* synthetic */ void setFontWeight(GroupView groupView, @Nullable Double d) {
            super.setFontWeight((GroupViewManager) groupView, d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGGroupManagerInterface
        public /* bridge */ /* synthetic */ void setFontWeight(GroupView groupView, @Nullable String str) {
            super.setFontWeight((GroupViewManager) groupView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGGroupManagerInterface
        @ReactProp(name = "markerEnd")
        public /* bridge */ /* synthetic */ void setMarkerEnd(GroupView groupView, String str) {
            super.setMarkerEnd((GroupViewManager) groupView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGGroupManagerInterface
        @ReactProp(name = "markerMid")
        public /* bridge */ /* synthetic */ void setMarkerMid(GroupView groupView, String str) {
            super.setMarkerMid((GroupViewManager) groupView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGGroupManagerInterface
        @ReactProp(name = "markerStart")
        public /* bridge */ /* synthetic */ void setMarkerStart(GroupView groupView, String str) {
            super.setMarkerStart((GroupViewManager) groupView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGGroupManagerInterface
        @ReactProp(name = "mask")
        public /* bridge */ /* synthetic */ void setMask(GroupView groupView, String str) {
            super.setMask((GroupViewManager) groupView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGGroupManagerInterface
        public /* bridge */ /* synthetic */ void setMatrix(GroupView groupView, @Nullable ReadableArray readableArray) {
            super.setMatrix((GroupViewManager) groupView, readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGGroupManagerInterface
        @ReactProp(name = "name")
        public /* bridge */ /* synthetic */ void setName(GroupView groupView, String str) {
            super.setName((GroupViewManager) groupView, str);
        }

        @Override // com.facebook.react.uimanager.BaseViewManager, com.facebook.react.uimanager.BaseViewManagerInterface
        @ReactProp(defaultFloat = 1.0f, name = ViewProps.OPACITY)
        public /* bridge */ /* synthetic */ void setOpacity(@Nonnull View view, float f) {
            super.setOpacity((GroupViewManager) view, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGGroupManagerInterface
        @ReactProp(name = ViewProps.POINTER_EVENTS)
        public /* bridge */ /* synthetic */ void setPointerEvents(GroupView groupView, @Nullable String str) {
            super.setPointerEvents((GroupViewManager) groupView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGGroupManagerInterface
        @ReactProp(name = "propList")
        public /* bridge */ /* synthetic */ void setPropList(GroupView groupView, @Nullable ReadableArray readableArray) {
            super.setPropList((GroupViewManager) groupView, readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGGroupManagerInterface
        @ReactProp(name = "responsible")
        public /* bridge */ /* synthetic */ void setResponsible(GroupView groupView, boolean z) {
            super.setResponsible((GroupViewManager) groupView, z);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGGroupManagerInterface
        public /* bridge */ /* synthetic */ void setStroke(GroupView groupView, @Nullable ReadableMap readableMap) {
            super.setStroke((GroupViewManager) groupView, readableMap);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGGroupManagerInterface
        @ReactProp(name = "strokeDasharray")
        public /* bridge */ /* synthetic */ void setStrokeDasharray(GroupView groupView, @Nullable ReadableArray readableArray) {
            super.setStrokeDasharray((GroupViewManager) groupView, readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGGroupManagerInterface
        @ReactProp(name = "strokeDasharray")
        public /* bridge */ /* synthetic */ void setStrokeDasharray(GroupView groupView, @Nullable String str) {
            super.setStrokeDasharray((GroupViewManager) groupView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGGroupManagerInterface
        @ReactProp(name = "strokeDashoffset")
        public /* bridge */ /* synthetic */ void setStrokeDashoffset(GroupView groupView, float f) {
            super.setStrokeDashoffset((GroupViewManager) groupView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGGroupManagerInterface
        @ReactProp(defaultInt = 1, name = "strokeLinecap")
        public /* bridge */ /* synthetic */ void setStrokeLinecap(GroupView groupView, int i) {
            super.setStrokeLinecap((GroupViewManager) groupView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGGroupManagerInterface
        @ReactProp(defaultInt = 1, name = "strokeLinejoin")
        public /* bridge */ /* synthetic */ void setStrokeLinejoin(GroupView groupView, int i) {
            super.setStrokeLinejoin((GroupViewManager) groupView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGGroupManagerInterface
        @ReactProp(defaultFloat = 4.0f, name = "strokeMiterlimit")
        public /* bridge */ /* synthetic */ void setStrokeMiterlimit(GroupView groupView, float f) {
            super.setStrokeMiterlimit((GroupViewManager) groupView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGGroupManagerInterface
        @ReactProp(defaultFloat = 1.0f, name = "strokeOpacity")
        public /* bridge */ /* synthetic */ void setStrokeOpacity(GroupView groupView, float f) {
            super.setStrokeOpacity((GroupViewManager) groupView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGGroupManagerInterface
        public /* bridge */ /* synthetic */ void setStrokeWidth(GroupView groupView, @Nullable Double d) {
            super.setStrokeWidth((GroupViewManager) groupView, d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGGroupManagerInterface
        public /* bridge */ /* synthetic */ void setStrokeWidth(GroupView groupView, @Nullable String str) {
            super.setStrokeWidth((GroupViewManager) groupView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGGroupManagerInterface
        @ReactProp(name = "vectorEffect")
        public /* bridge */ /* synthetic */ void setVectorEffect(GroupView groupView, int i) {
            super.setVectorEffect((GroupViewManager) groupView, i);
        }

        GroupViewManager() {
            super(VirtualViewManager.SVGClass.RNSVGGroup);
            this.mDelegate = new RNSVGGroupManagerDelegate(this);
        }
    }

    static class PathViewManager extends RenderableViewManager<PathView> implements RNSVGPathManagerInterface<PathView> {
        public static final String REACT_CLASS = "RNSVGPath";

        @Override // com.facebook.react.viewmanagers.RNSVGPathManagerInterface
        @ReactProp(name = "clipPath")
        public /* bridge */ /* synthetic */ void setClipPath(PathView pathView, String str) {
            super.setClipPath((PathViewManager) pathView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPathManagerInterface
        @ReactProp(name = "clipRule")
        public /* bridge */ /* synthetic */ void setClipRule(PathView pathView, int i) {
            super.setClipRule((PathViewManager) pathView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPathManagerInterface
        @ReactProp(name = "display")
        public /* bridge */ /* synthetic */ void setDisplay(PathView pathView, String str) {
            super.setDisplay((PathViewManager) pathView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPathManagerInterface
        public /* bridge */ /* synthetic */ void setFill(PathView pathView, @Nullable ReadableMap readableMap) {
            super.setFill((PathViewManager) pathView, readableMap);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPathManagerInterface
        @ReactProp(defaultFloat = 1.0f, name = "fillOpacity")
        public /* bridge */ /* synthetic */ void setFillOpacity(PathView pathView, float f) {
            super.setFillOpacity((PathViewManager) pathView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPathManagerInterface
        @ReactProp(defaultInt = 1, name = "fillRule")
        public /* bridge */ /* synthetic */ void setFillRule(PathView pathView, int i) {
            super.setFillRule((PathViewManager) pathView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPathManagerInterface
        @ReactProp(name = "markerEnd")
        public /* bridge */ /* synthetic */ void setMarkerEnd(PathView pathView, String str) {
            super.setMarkerEnd((PathViewManager) pathView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPathManagerInterface
        @ReactProp(name = "markerMid")
        public /* bridge */ /* synthetic */ void setMarkerMid(PathView pathView, String str) {
            super.setMarkerMid((PathViewManager) pathView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPathManagerInterface
        @ReactProp(name = "markerStart")
        public /* bridge */ /* synthetic */ void setMarkerStart(PathView pathView, String str) {
            super.setMarkerStart((PathViewManager) pathView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPathManagerInterface
        @ReactProp(name = "mask")
        public /* bridge */ /* synthetic */ void setMask(PathView pathView, String str) {
            super.setMask((PathViewManager) pathView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPathManagerInterface
        public /* bridge */ /* synthetic */ void setMatrix(PathView pathView, @Nullable ReadableArray readableArray) {
            super.setMatrix((PathViewManager) pathView, readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPathManagerInterface
        @ReactProp(name = "name")
        public /* bridge */ /* synthetic */ void setName(PathView pathView, String str) {
            super.setName((PathViewManager) pathView, str);
        }

        @Override // com.facebook.react.uimanager.BaseViewManager, com.facebook.react.uimanager.BaseViewManagerInterface
        @ReactProp(defaultFloat = 1.0f, name = ViewProps.OPACITY)
        public /* bridge */ /* synthetic */ void setOpacity(@Nonnull View view, float f) {
            super.setOpacity((PathViewManager) view, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPathManagerInterface
        @ReactProp(name = ViewProps.POINTER_EVENTS)
        public /* bridge */ /* synthetic */ void setPointerEvents(PathView pathView, @Nullable String str) {
            super.setPointerEvents((PathViewManager) pathView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPathManagerInterface
        @ReactProp(name = "propList")
        public /* bridge */ /* synthetic */ void setPropList(PathView pathView, @Nullable ReadableArray readableArray) {
            super.setPropList((PathViewManager) pathView, readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPathManagerInterface
        @ReactProp(name = "responsible")
        public /* bridge */ /* synthetic */ void setResponsible(PathView pathView, boolean z) {
            super.setResponsible((PathViewManager) pathView, z);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPathManagerInterface
        public /* bridge */ /* synthetic */ void setStroke(PathView pathView, @Nullable ReadableMap readableMap) {
            super.setStroke((PathViewManager) pathView, readableMap);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPathManagerInterface
        @ReactProp(name = "strokeDasharray")
        public /* bridge */ /* synthetic */ void setStrokeDasharray(PathView pathView, @Nullable ReadableArray readableArray) {
            super.setStrokeDasharray((PathViewManager) pathView, readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPathManagerInterface
        @ReactProp(name = "strokeDasharray")
        public /* bridge */ /* synthetic */ void setStrokeDasharray(PathView pathView, @Nullable String str) {
            super.setStrokeDasharray((PathViewManager) pathView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPathManagerInterface
        @ReactProp(name = "strokeDashoffset")
        public /* bridge */ /* synthetic */ void setStrokeDashoffset(PathView pathView, float f) {
            super.setStrokeDashoffset((PathViewManager) pathView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPathManagerInterface
        @ReactProp(defaultInt = 1, name = "strokeLinecap")
        public /* bridge */ /* synthetic */ void setStrokeLinecap(PathView pathView, int i) {
            super.setStrokeLinecap((PathViewManager) pathView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPathManagerInterface
        @ReactProp(defaultInt = 1, name = "strokeLinejoin")
        public /* bridge */ /* synthetic */ void setStrokeLinejoin(PathView pathView, int i) {
            super.setStrokeLinejoin((PathViewManager) pathView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPathManagerInterface
        @ReactProp(defaultFloat = 4.0f, name = "strokeMiterlimit")
        public /* bridge */ /* synthetic */ void setStrokeMiterlimit(PathView pathView, float f) {
            super.setStrokeMiterlimit((PathViewManager) pathView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPathManagerInterface
        @ReactProp(defaultFloat = 1.0f, name = "strokeOpacity")
        public /* bridge */ /* synthetic */ void setStrokeOpacity(PathView pathView, float f) {
            super.setStrokeOpacity((PathViewManager) pathView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPathManagerInterface
        public /* bridge */ /* synthetic */ void setStrokeWidth(PathView pathView, @Nullable Double d) {
            super.setStrokeWidth((PathViewManager) pathView, d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPathManagerInterface
        public /* bridge */ /* synthetic */ void setStrokeWidth(PathView pathView, @Nullable String str) {
            super.setStrokeWidth((PathViewManager) pathView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPathManagerInterface
        @ReactProp(name = "vectorEffect")
        public /* bridge */ /* synthetic */ void setVectorEffect(PathView pathView, int i) {
            super.setVectorEffect((PathViewManager) pathView, i);
        }

        PathViewManager() {
            super(VirtualViewManager.SVGClass.RNSVGPath);
            this.mDelegate = new RNSVGPathManagerDelegate(this);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPathManagerInterface
        @ReactProp(name = "d")
        public void setD(PathView pathView, String str) {
            pathView.setD(str);
        }
    }

    static class TextViewManagerAbstract<K extends TextView> extends GroupViewManagerAbstract<K> {
        TextViewManagerAbstract(VirtualViewManager.SVGClass sVGClass) {
            super(sVGClass);
        }

        @ReactProp(name = "inlineSize")
        public void setInlineSize(K k, Dynamic dynamic) {
            k.setInlineSize(dynamic);
        }

        @ReactProp(name = "textLength")
        public void setTextLength(K k, Dynamic dynamic) {
            k.setTextLength(dynamic);
        }

        @ReactProp(name = "lengthAdjust")
        public void setLengthAdjust(K k, @Nullable String str) {
            k.setLengthAdjust(str);
        }

        @ReactProp(name = "alignmentBaseline")
        public void setMethod(K k, @Nullable String str) {
            k.setMethod(str);
        }

        @ReactProp(name = "baselineShift")
        public void setBaselineShift(K k, Dynamic dynamic) {
            k.setBaselineShift(dynamic);
        }

        @ReactProp(name = "verticalAlign")
        public void setVerticalAlign(K k, @Nullable String str) {
            k.setVerticalAlign(str);
        }

        @ReactProp(name = "rotate")
        public void setRotate(K k, Dynamic dynamic) {
            k.setRotate(dynamic);
        }

        @ReactProp(name = "dx")
        public void setDeltaX(K k, Dynamic dynamic) {
            k.setDeltaX(dynamic);
        }

        @ReactProp(name = "dy")
        public void setDeltaY(K k, Dynamic dynamic) {
            k.setDeltaY(dynamic);
        }

        @ReactProp(name = ViewHierarchyNode.JsonKeys.X)
        public void setX(K k, Dynamic dynamic) {
            k.setPositionX(dynamic);
        }

        @ReactProp(name = ViewHierarchyNode.JsonKeys.Y)
        public void setY(K k, Dynamic dynamic) {
            k.setPositionY(dynamic);
        }

        @Override // com.horcrux.svg.RenderableViewManager.GroupViewManagerAbstract
        @ReactProp(name = "font")
        public void setFont(K k, @Nullable ReadableMap readableMap) {
            k.setFont(readableMap);
        }

        public void setAlignmentBaseline(K k, @Nullable String str) {
            k.setMethod(str);
        }

        public void setDx(K k, @Nullable ReadableArray readableArray) {
            k.setDeltaX(readableArray);
        }

        public void setDy(K k, @Nullable ReadableArray readableArray) {
            k.setDeltaY(readableArray);
        }

        public void setX(K k, @Nullable ReadableArray readableArray) {
            k.setPositionX(readableArray);
        }

        public void setY(K k, @Nullable ReadableArray readableArray) {
            k.setPositionY(readableArray);
        }

        public void setRotate(K k, @Nullable ReadableArray readableArray) {
            k.setRotate(readableArray);
        }

        public void setInlineSize(K k, @Nullable String str) {
            k.setInlineSize(str);
        }

        public void setTextLength(K k, @Nullable String str) {
            k.setTextLength(str);
        }

        public void setBaselineShift(K k, @Nullable String str) {
            k.setBaselineShift(str);
        }

        public void setInlineSize(K k, @Nullable Double d) {
            k.setInlineSize(d);
        }

        public void setTextLength(K k, @Nullable Double d) {
            k.setTextLength(d);
        }

        public void setBaselineShift(K k, @Nullable Double d) {
            k.setBaselineShift(d);
        }
    }

    static class TextViewManager extends TextViewManagerAbstract<TextView> implements RNSVGTextManagerInterface<TextView> {
        public static final String REACT_CLASS = "RNSVGText";

        @Override // com.facebook.react.viewmanagers.RNSVGTextManagerInterface
        public /* bridge */ /* synthetic */ void setAlignmentBaseline(TextView textView, @Nullable String str) {
            super.setAlignmentBaseline((TextViewManager) textView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextManagerInterface
        public /* bridge */ /* synthetic */ void setBaselineShift(TextView textView, @Nullable Double d) {
            super.setBaselineShift((TextViewManager) textView, d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextManagerInterface
        public /* bridge */ /* synthetic */ void setBaselineShift(TextView textView, @Nullable String str) {
            super.setBaselineShift((TextViewManager) textView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextManagerInterface
        @ReactProp(name = "clipPath")
        public /* bridge */ /* synthetic */ void setClipPath(TextView textView, String str) {
            super.setClipPath((TextViewManager) textView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextManagerInterface
        @ReactProp(name = "clipRule")
        public /* bridge */ /* synthetic */ void setClipRule(TextView textView, int i) {
            super.setClipRule((TextViewManager) textView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextManagerInterface
        @ReactProp(name = "display")
        public /* bridge */ /* synthetic */ void setDisplay(TextView textView, String str) {
            super.setDisplay((TextViewManager) textView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextManagerInterface
        public /* bridge */ /* synthetic */ void setDx(TextView textView, @Nullable ReadableArray readableArray) {
            super.setDx((TextViewManager) textView, readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextManagerInterface
        public /* bridge */ /* synthetic */ void setDy(TextView textView, @Nullable ReadableArray readableArray) {
            super.setDy((TextViewManager) textView, readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextManagerInterface
        public /* bridge */ /* synthetic */ void setFill(TextView textView, @Nullable ReadableMap readableMap) {
            super.setFill((TextViewManager) textView, readableMap);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextManagerInterface
        @ReactProp(defaultFloat = 1.0f, name = "fillOpacity")
        public /* bridge */ /* synthetic */ void setFillOpacity(TextView textView, float f) {
            super.setFillOpacity((TextViewManager) textView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextManagerInterface
        @ReactProp(defaultInt = 1, name = "fillRule")
        public /* bridge */ /* synthetic */ void setFillRule(TextView textView, int i) {
            super.setFillRule((TextViewManager) textView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextManagerInterface
        @ReactProp(name = "font")
        public /* bridge */ /* synthetic */ void setFont(TextView textView, @Nullable ReadableMap readableMap) {
            super.setFont((TextViewManager) textView, readableMap);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextManagerInterface
        public /* bridge */ /* synthetic */ void setFontSize(TextView textView, @Nullable Double d) {
            super.setFontSize((TextViewManager) textView, d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextManagerInterface
        public /* bridge */ /* synthetic */ void setFontSize(TextView textView, @Nullable String str) {
            super.setFontSize((TextViewManager) textView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextManagerInterface
        public /* bridge */ /* synthetic */ void setFontWeight(TextView textView, @Nullable Double d) {
            super.setFontWeight((TextViewManager) textView, d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextManagerInterface
        public /* bridge */ /* synthetic */ void setFontWeight(TextView textView, @Nullable String str) {
            super.setFontWeight((TextViewManager) textView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextManagerInterface
        public /* bridge */ /* synthetic */ void setInlineSize(TextView textView, @Nullable Double d) {
            super.setInlineSize((TextViewManager) textView, d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextManagerInterface
        public /* bridge */ /* synthetic */ void setInlineSize(TextView textView, @Nullable String str) {
            super.setInlineSize((TextViewManager) textView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextManagerInterface
        @ReactProp(name = "lengthAdjust")
        public /* bridge */ /* synthetic */ void setLengthAdjust(TextView textView, @Nullable String str) {
            super.setLengthAdjust((TextViewManager) textView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextManagerInterface
        @ReactProp(name = "markerEnd")
        public /* bridge */ /* synthetic */ void setMarkerEnd(TextView textView, String str) {
            super.setMarkerEnd((TextViewManager) textView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextManagerInterface
        @ReactProp(name = "markerMid")
        public /* bridge */ /* synthetic */ void setMarkerMid(TextView textView, String str) {
            super.setMarkerMid((TextViewManager) textView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextManagerInterface
        @ReactProp(name = "markerStart")
        public /* bridge */ /* synthetic */ void setMarkerStart(TextView textView, String str) {
            super.setMarkerStart((TextViewManager) textView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextManagerInterface
        @ReactProp(name = "mask")
        public /* bridge */ /* synthetic */ void setMask(TextView textView, String str) {
            super.setMask((TextViewManager) textView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextManagerInterface
        public /* bridge */ /* synthetic */ void setMatrix(TextView textView, @Nullable ReadableArray readableArray) {
            super.setMatrix((TextViewManager) textView, readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextManagerInterface
        @ReactProp(name = "name")
        public /* bridge */ /* synthetic */ void setName(TextView textView, String str) {
            super.setName((TextViewManager) textView, str);
        }

        @Override // com.facebook.react.uimanager.BaseViewManager, com.facebook.react.uimanager.BaseViewManagerInterface
        @ReactProp(defaultFloat = 1.0f, name = ViewProps.OPACITY)
        public /* bridge */ /* synthetic */ void setOpacity(@Nonnull View view, float f) {
            super.setOpacity((TextViewManager) view, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextManagerInterface
        @ReactProp(name = ViewProps.POINTER_EVENTS)
        public /* bridge */ /* synthetic */ void setPointerEvents(TextView textView, @Nullable String str) {
            super.setPointerEvents((TextViewManager) textView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextManagerInterface
        @ReactProp(name = "propList")
        public /* bridge */ /* synthetic */ void setPropList(TextView textView, @Nullable ReadableArray readableArray) {
            super.setPropList((TextViewManager) textView, readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextManagerInterface
        @ReactProp(name = "responsible")
        public /* bridge */ /* synthetic */ void setResponsible(TextView textView, boolean z) {
            super.setResponsible((TextViewManager) textView, z);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextManagerInterface
        public /* bridge */ /* synthetic */ void setRotate(TextView textView, @Nullable ReadableArray readableArray) {
            super.setRotate((TextViewManager) textView, readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextManagerInterface
        public /* bridge */ /* synthetic */ void setStroke(TextView textView, @Nullable ReadableMap readableMap) {
            super.setStroke((TextViewManager) textView, readableMap);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextManagerInterface
        @ReactProp(name = "strokeDasharray")
        public /* bridge */ /* synthetic */ void setStrokeDasharray(TextView textView, @Nullable ReadableArray readableArray) {
            super.setStrokeDasharray((TextViewManager) textView, readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextManagerInterface
        @ReactProp(name = "strokeDasharray")
        public /* bridge */ /* synthetic */ void setStrokeDasharray(TextView textView, @Nullable String str) {
            super.setStrokeDasharray((TextViewManager) textView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextManagerInterface
        @ReactProp(name = "strokeDashoffset")
        public /* bridge */ /* synthetic */ void setStrokeDashoffset(TextView textView, float f) {
            super.setStrokeDashoffset((TextViewManager) textView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextManagerInterface
        @ReactProp(defaultInt = 1, name = "strokeLinecap")
        public /* bridge */ /* synthetic */ void setStrokeLinecap(TextView textView, int i) {
            super.setStrokeLinecap((TextViewManager) textView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextManagerInterface
        @ReactProp(defaultInt = 1, name = "strokeLinejoin")
        public /* bridge */ /* synthetic */ void setStrokeLinejoin(TextView textView, int i) {
            super.setStrokeLinejoin((TextViewManager) textView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextManagerInterface
        @ReactProp(defaultFloat = 4.0f, name = "strokeMiterlimit")
        public /* bridge */ /* synthetic */ void setStrokeMiterlimit(TextView textView, float f) {
            super.setStrokeMiterlimit((TextViewManager) textView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextManagerInterface
        @ReactProp(defaultFloat = 1.0f, name = "strokeOpacity")
        public /* bridge */ /* synthetic */ void setStrokeOpacity(TextView textView, float f) {
            super.setStrokeOpacity((TextViewManager) textView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextManagerInterface
        public /* bridge */ /* synthetic */ void setStrokeWidth(TextView textView, @Nullable Double d) {
            super.setStrokeWidth((TextViewManager) textView, d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextManagerInterface
        public /* bridge */ /* synthetic */ void setStrokeWidth(TextView textView, @Nullable String str) {
            super.setStrokeWidth((TextViewManager) textView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextManagerInterface
        public /* bridge */ /* synthetic */ void setTextLength(TextView textView, @Nullable Double d) {
            super.setTextLength((TextViewManager) textView, d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextManagerInterface
        public /* bridge */ /* synthetic */ void setTextLength(TextView textView, @Nullable String str) {
            super.setTextLength((TextViewManager) textView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextManagerInterface
        @ReactProp(name = "vectorEffect")
        public /* bridge */ /* synthetic */ void setVectorEffect(TextView textView, int i) {
            super.setVectorEffect((TextViewManager) textView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextManagerInterface
        @ReactProp(name = "verticalAlign")
        public /* bridge */ /* synthetic */ void setVerticalAlign(TextView textView, @Nullable String str) {
            super.setVerticalAlign((TextViewManager) textView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextManagerInterface
        public /* bridge */ /* synthetic */ void setX(TextView textView, @Nullable ReadableArray readableArray) {
            super.setX((TextViewManager) textView, readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextManagerInterface
        public /* bridge */ /* synthetic */ void setY(TextView textView, @Nullable ReadableArray readableArray) {
            super.setY((TextViewManager) textView, readableArray);
        }

        TextViewManager() {
            super(VirtualViewManager.SVGClass.RNSVGText);
            this.mDelegate = new RNSVGTextManagerDelegate(this);
        }

        TextViewManager(VirtualViewManager.SVGClass sVGClass) {
            super(sVGClass);
            this.mDelegate = new RNSVGTextManagerDelegate(this);
        }
    }

    static class TSpanViewManager extends TextViewManagerAbstract<TSpanView> implements RNSVGTSpanManagerInterface<TSpanView> {
        public static final String REACT_CLASS = "RNSVGTSpan";

        @Override // com.facebook.react.viewmanagers.RNSVGTSpanManagerInterface
        public /* bridge */ /* synthetic */ void setAlignmentBaseline(TSpanView tSpanView, @Nullable String str) {
            super.setAlignmentBaseline((TSpanViewManager) tSpanView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTSpanManagerInterface
        public /* bridge */ /* synthetic */ void setBaselineShift(TSpanView tSpanView, @Nullable Double d) {
            super.setBaselineShift((TSpanViewManager) tSpanView, d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTSpanManagerInterface
        public /* bridge */ /* synthetic */ void setBaselineShift(TSpanView tSpanView, @Nullable String str) {
            super.setBaselineShift((TSpanViewManager) tSpanView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTSpanManagerInterface
        @ReactProp(name = "clipPath")
        public /* bridge */ /* synthetic */ void setClipPath(TSpanView tSpanView, String str) {
            super.setClipPath((TSpanViewManager) tSpanView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTSpanManagerInterface
        @ReactProp(name = "clipRule")
        public /* bridge */ /* synthetic */ void setClipRule(TSpanView tSpanView, int i) {
            super.setClipRule((TSpanViewManager) tSpanView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTSpanManagerInterface
        @ReactProp(name = "display")
        public /* bridge */ /* synthetic */ void setDisplay(TSpanView tSpanView, String str) {
            super.setDisplay((TSpanViewManager) tSpanView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTSpanManagerInterface
        public /* bridge */ /* synthetic */ void setDx(TSpanView tSpanView, @Nullable ReadableArray readableArray) {
            super.setDx((TSpanViewManager) tSpanView, readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTSpanManagerInterface
        public /* bridge */ /* synthetic */ void setDy(TSpanView tSpanView, @Nullable ReadableArray readableArray) {
            super.setDy((TSpanViewManager) tSpanView, readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTSpanManagerInterface
        public /* bridge */ /* synthetic */ void setFill(TSpanView tSpanView, @Nullable ReadableMap readableMap) {
            super.setFill((TSpanViewManager) tSpanView, readableMap);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTSpanManagerInterface
        @ReactProp(defaultFloat = 1.0f, name = "fillOpacity")
        public /* bridge */ /* synthetic */ void setFillOpacity(TSpanView tSpanView, float f) {
            super.setFillOpacity((TSpanViewManager) tSpanView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTSpanManagerInterface
        @ReactProp(defaultInt = 1, name = "fillRule")
        public /* bridge */ /* synthetic */ void setFillRule(TSpanView tSpanView, int i) {
            super.setFillRule((TSpanViewManager) tSpanView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTSpanManagerInterface
        @ReactProp(name = "font")
        public /* bridge */ /* synthetic */ void setFont(TSpanView tSpanView, @Nullable ReadableMap readableMap) {
            super.setFont((TSpanViewManager) tSpanView, readableMap);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTSpanManagerInterface
        public /* bridge */ /* synthetic */ void setFontSize(TSpanView tSpanView, @Nullable Double d) {
            super.setFontSize((TSpanViewManager) tSpanView, d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTSpanManagerInterface
        public /* bridge */ /* synthetic */ void setFontSize(TSpanView tSpanView, @Nullable String str) {
            super.setFontSize((TSpanViewManager) tSpanView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTSpanManagerInterface
        public /* bridge */ /* synthetic */ void setFontWeight(TSpanView tSpanView, @Nullable Double d) {
            super.setFontWeight((TSpanViewManager) tSpanView, d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTSpanManagerInterface
        public /* bridge */ /* synthetic */ void setFontWeight(TSpanView tSpanView, @Nullable String str) {
            super.setFontWeight((TSpanViewManager) tSpanView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTSpanManagerInterface
        public /* bridge */ /* synthetic */ void setInlineSize(TSpanView tSpanView, @Nullable Double d) {
            super.setInlineSize((TSpanViewManager) tSpanView, d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTSpanManagerInterface
        public /* bridge */ /* synthetic */ void setInlineSize(TSpanView tSpanView, @Nullable String str) {
            super.setInlineSize((TSpanViewManager) tSpanView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTSpanManagerInterface
        @ReactProp(name = "lengthAdjust")
        public /* bridge */ /* synthetic */ void setLengthAdjust(TSpanView tSpanView, @Nullable String str) {
            super.setLengthAdjust((TSpanViewManager) tSpanView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTSpanManagerInterface
        @ReactProp(name = "markerEnd")
        public /* bridge */ /* synthetic */ void setMarkerEnd(TSpanView tSpanView, String str) {
            super.setMarkerEnd((TSpanViewManager) tSpanView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTSpanManagerInterface
        @ReactProp(name = "markerMid")
        public /* bridge */ /* synthetic */ void setMarkerMid(TSpanView tSpanView, String str) {
            super.setMarkerMid((TSpanViewManager) tSpanView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTSpanManagerInterface
        @ReactProp(name = "markerStart")
        public /* bridge */ /* synthetic */ void setMarkerStart(TSpanView tSpanView, String str) {
            super.setMarkerStart((TSpanViewManager) tSpanView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTSpanManagerInterface
        @ReactProp(name = "mask")
        public /* bridge */ /* synthetic */ void setMask(TSpanView tSpanView, String str) {
            super.setMask((TSpanViewManager) tSpanView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTSpanManagerInterface
        public /* bridge */ /* synthetic */ void setMatrix(TSpanView tSpanView, @Nullable ReadableArray readableArray) {
            super.setMatrix((TSpanViewManager) tSpanView, readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTSpanManagerInterface
        @ReactProp(name = "name")
        public /* bridge */ /* synthetic */ void setName(TSpanView tSpanView, String str) {
            super.setName((TSpanViewManager) tSpanView, str);
        }

        @Override // com.facebook.react.uimanager.BaseViewManager, com.facebook.react.uimanager.BaseViewManagerInterface
        @ReactProp(defaultFloat = 1.0f, name = ViewProps.OPACITY)
        public /* bridge */ /* synthetic */ void setOpacity(@Nonnull View view, float f) {
            super.setOpacity((TSpanViewManager) view, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTSpanManagerInterface
        @ReactProp(name = ViewProps.POINTER_EVENTS)
        public /* bridge */ /* synthetic */ void setPointerEvents(TSpanView tSpanView, @Nullable String str) {
            super.setPointerEvents((TSpanViewManager) tSpanView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTSpanManagerInterface
        @ReactProp(name = "propList")
        public /* bridge */ /* synthetic */ void setPropList(TSpanView tSpanView, @Nullable ReadableArray readableArray) {
            super.setPropList((TSpanViewManager) tSpanView, readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTSpanManagerInterface
        @ReactProp(name = "responsible")
        public /* bridge */ /* synthetic */ void setResponsible(TSpanView tSpanView, boolean z) {
            super.setResponsible((TSpanViewManager) tSpanView, z);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTSpanManagerInterface
        public /* bridge */ /* synthetic */ void setRotate(TSpanView tSpanView, @Nullable ReadableArray readableArray) {
            super.setRotate((TSpanViewManager) tSpanView, readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTSpanManagerInterface
        public /* bridge */ /* synthetic */ void setStroke(TSpanView tSpanView, @Nullable ReadableMap readableMap) {
            super.setStroke((TSpanViewManager) tSpanView, readableMap);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTSpanManagerInterface
        @ReactProp(name = "strokeDasharray")
        public /* bridge */ /* synthetic */ void setStrokeDasharray(TSpanView tSpanView, @Nullable ReadableArray readableArray) {
            super.setStrokeDasharray((TSpanViewManager) tSpanView, readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTSpanManagerInterface
        @ReactProp(name = "strokeDasharray")
        public /* bridge */ /* synthetic */ void setStrokeDasharray(TSpanView tSpanView, @Nullable String str) {
            super.setStrokeDasharray((TSpanViewManager) tSpanView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTSpanManagerInterface
        @ReactProp(name = "strokeDashoffset")
        public /* bridge */ /* synthetic */ void setStrokeDashoffset(TSpanView tSpanView, float f) {
            super.setStrokeDashoffset((TSpanViewManager) tSpanView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTSpanManagerInterface
        @ReactProp(defaultInt = 1, name = "strokeLinecap")
        public /* bridge */ /* synthetic */ void setStrokeLinecap(TSpanView tSpanView, int i) {
            super.setStrokeLinecap((TSpanViewManager) tSpanView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTSpanManagerInterface
        @ReactProp(defaultInt = 1, name = "strokeLinejoin")
        public /* bridge */ /* synthetic */ void setStrokeLinejoin(TSpanView tSpanView, int i) {
            super.setStrokeLinejoin((TSpanViewManager) tSpanView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTSpanManagerInterface
        @ReactProp(defaultFloat = 4.0f, name = "strokeMiterlimit")
        public /* bridge */ /* synthetic */ void setStrokeMiterlimit(TSpanView tSpanView, float f) {
            super.setStrokeMiterlimit((TSpanViewManager) tSpanView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTSpanManagerInterface
        @ReactProp(defaultFloat = 1.0f, name = "strokeOpacity")
        public /* bridge */ /* synthetic */ void setStrokeOpacity(TSpanView tSpanView, float f) {
            super.setStrokeOpacity((TSpanViewManager) tSpanView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTSpanManagerInterface
        public /* bridge */ /* synthetic */ void setStrokeWidth(TSpanView tSpanView, @Nullable Double d) {
            super.setStrokeWidth((TSpanViewManager) tSpanView, d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTSpanManagerInterface
        public /* bridge */ /* synthetic */ void setStrokeWidth(TSpanView tSpanView, @Nullable String str) {
            super.setStrokeWidth((TSpanViewManager) tSpanView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTSpanManagerInterface
        public /* bridge */ /* synthetic */ void setTextLength(TSpanView tSpanView, @Nullable Double d) {
            super.setTextLength((TSpanViewManager) tSpanView, d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTSpanManagerInterface
        public /* bridge */ /* synthetic */ void setTextLength(TSpanView tSpanView, @Nullable String str) {
            super.setTextLength((TSpanViewManager) tSpanView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTSpanManagerInterface
        @ReactProp(name = "vectorEffect")
        public /* bridge */ /* synthetic */ void setVectorEffect(TSpanView tSpanView, int i) {
            super.setVectorEffect((TSpanViewManager) tSpanView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTSpanManagerInterface
        @ReactProp(name = "verticalAlign")
        public /* bridge */ /* synthetic */ void setVerticalAlign(TSpanView tSpanView, @Nullable String str) {
            super.setVerticalAlign((TSpanViewManager) tSpanView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTSpanManagerInterface
        public /* bridge */ /* synthetic */ void setX(TSpanView tSpanView, @Nullable ReadableArray readableArray) {
            super.setX((TSpanViewManager) tSpanView, readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTSpanManagerInterface
        public /* bridge */ /* synthetic */ void setY(TSpanView tSpanView, @Nullable ReadableArray readableArray) {
            super.setY((TSpanViewManager) tSpanView, readableArray);
        }

        TSpanViewManager() {
            super(VirtualViewManager.SVGClass.RNSVGTSpan);
            this.mDelegate = new RNSVGTSpanManagerDelegate(this);
        }

        TSpanViewManager(VirtualViewManager.SVGClass sVGClass) {
            super(sVGClass);
            this.mDelegate = new RNSVGTSpanManagerDelegate(this);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTSpanManagerInterface
        @ReactProp(name = "content")
        public void setContent(TSpanView tSpanView, @Nullable String str) {
            tSpanView.setContent(str);
        }
    }

    static class TextPathViewManager extends TextViewManagerAbstract<TextPathView> implements RNSVGTextPathManagerInterface<TextPathView> {
        public static final String REACT_CLASS = "RNSVGTextPath";

        @Override // com.facebook.react.viewmanagers.RNSVGTextPathManagerInterface
        public /* bridge */ /* synthetic */ void setAlignmentBaseline(TextPathView textPathView, @Nullable String str) {
            super.setAlignmentBaseline((TextPathViewManager) textPathView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextPathManagerInterface
        public /* bridge */ /* synthetic */ void setBaselineShift(TextPathView textPathView, @Nullable Double d) {
            super.setBaselineShift((TextPathViewManager) textPathView, d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextPathManagerInterface
        public /* bridge */ /* synthetic */ void setBaselineShift(TextPathView textPathView, @Nullable String str) {
            super.setBaselineShift((TextPathViewManager) textPathView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextPathManagerInterface
        @ReactProp(name = "clipPath")
        public /* bridge */ /* synthetic */ void setClipPath(TextPathView textPathView, String str) {
            super.setClipPath((TextPathViewManager) textPathView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextPathManagerInterface
        @ReactProp(name = "clipRule")
        public /* bridge */ /* synthetic */ void setClipRule(TextPathView textPathView, int i) {
            super.setClipRule((TextPathViewManager) textPathView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextPathManagerInterface
        @ReactProp(name = "display")
        public /* bridge */ /* synthetic */ void setDisplay(TextPathView textPathView, String str) {
            super.setDisplay((TextPathViewManager) textPathView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextPathManagerInterface
        public /* bridge */ /* synthetic */ void setDx(TextPathView textPathView, @Nullable ReadableArray readableArray) {
            super.setDx((TextPathViewManager) textPathView, readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextPathManagerInterface
        public /* bridge */ /* synthetic */ void setDy(TextPathView textPathView, @Nullable ReadableArray readableArray) {
            super.setDy((TextPathViewManager) textPathView, readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextPathManagerInterface
        public /* bridge */ /* synthetic */ void setFill(TextPathView textPathView, @Nullable ReadableMap readableMap) {
            super.setFill((TextPathViewManager) textPathView, readableMap);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextPathManagerInterface
        @ReactProp(defaultFloat = 1.0f, name = "fillOpacity")
        public /* bridge */ /* synthetic */ void setFillOpacity(TextPathView textPathView, float f) {
            super.setFillOpacity((TextPathViewManager) textPathView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextPathManagerInterface
        @ReactProp(defaultInt = 1, name = "fillRule")
        public /* bridge */ /* synthetic */ void setFillRule(TextPathView textPathView, int i) {
            super.setFillRule((TextPathViewManager) textPathView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextPathManagerInterface
        @ReactProp(name = "font")
        public /* bridge */ /* synthetic */ void setFont(TextPathView textPathView, @Nullable ReadableMap readableMap) {
            super.setFont((TextPathViewManager) textPathView, readableMap);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextPathManagerInterface
        public /* bridge */ /* synthetic */ void setFontSize(TextPathView textPathView, @Nullable Double d) {
            super.setFontSize((TextPathViewManager) textPathView, d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextPathManagerInterface
        public /* bridge */ /* synthetic */ void setFontSize(TextPathView textPathView, @Nullable String str) {
            super.setFontSize((TextPathViewManager) textPathView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextPathManagerInterface
        public /* bridge */ /* synthetic */ void setFontWeight(TextPathView textPathView, @Nullable Double d) {
            super.setFontWeight((TextPathViewManager) textPathView, d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextPathManagerInterface
        public /* bridge */ /* synthetic */ void setFontWeight(TextPathView textPathView, @Nullable String str) {
            super.setFontWeight((TextPathViewManager) textPathView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextPathManagerInterface
        public /* bridge */ /* synthetic */ void setInlineSize(TextPathView textPathView, @Nullable Double d) {
            super.setInlineSize((TextPathViewManager) textPathView, d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextPathManagerInterface
        public /* bridge */ /* synthetic */ void setInlineSize(TextPathView textPathView, @Nullable String str) {
            super.setInlineSize((TextPathViewManager) textPathView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextPathManagerInterface
        @ReactProp(name = "lengthAdjust")
        public /* bridge */ /* synthetic */ void setLengthAdjust(TextPathView textPathView, @Nullable String str) {
            super.setLengthAdjust((TextPathViewManager) textPathView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextPathManagerInterface
        @ReactProp(name = "markerEnd")
        public /* bridge */ /* synthetic */ void setMarkerEnd(TextPathView textPathView, String str) {
            super.setMarkerEnd((TextPathViewManager) textPathView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextPathManagerInterface
        @ReactProp(name = "markerMid")
        public /* bridge */ /* synthetic */ void setMarkerMid(TextPathView textPathView, String str) {
            super.setMarkerMid((TextPathViewManager) textPathView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextPathManagerInterface
        @ReactProp(name = "markerStart")
        public /* bridge */ /* synthetic */ void setMarkerStart(TextPathView textPathView, String str) {
            super.setMarkerStart((TextPathViewManager) textPathView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextPathManagerInterface
        @ReactProp(name = "mask")
        public /* bridge */ /* synthetic */ void setMask(TextPathView textPathView, String str) {
            super.setMask((TextPathViewManager) textPathView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextPathManagerInterface
        public /* bridge */ /* synthetic */ void setMatrix(TextPathView textPathView, @Nullable ReadableArray readableArray) {
            super.setMatrix((TextPathViewManager) textPathView, readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextPathManagerInterface
        @ReactProp(name = "name")
        public /* bridge */ /* synthetic */ void setName(TextPathView textPathView, String str) {
            super.setName((TextPathViewManager) textPathView, str);
        }

        @Override // com.facebook.react.uimanager.BaseViewManager, com.facebook.react.uimanager.BaseViewManagerInterface
        @ReactProp(defaultFloat = 1.0f, name = ViewProps.OPACITY)
        public /* bridge */ /* synthetic */ void setOpacity(@Nonnull View view, float f) {
            super.setOpacity((TextPathViewManager) view, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextPathManagerInterface
        @ReactProp(name = ViewProps.POINTER_EVENTS)
        public /* bridge */ /* synthetic */ void setPointerEvents(TextPathView textPathView, @Nullable String str) {
            super.setPointerEvents((TextPathViewManager) textPathView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextPathManagerInterface
        @ReactProp(name = "propList")
        public /* bridge */ /* synthetic */ void setPropList(TextPathView textPathView, @Nullable ReadableArray readableArray) {
            super.setPropList((TextPathViewManager) textPathView, readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextPathManagerInterface
        @ReactProp(name = "responsible")
        public /* bridge */ /* synthetic */ void setResponsible(TextPathView textPathView, boolean z) {
            super.setResponsible((TextPathViewManager) textPathView, z);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextPathManagerInterface
        public /* bridge */ /* synthetic */ void setRotate(TextPathView textPathView, @Nullable ReadableArray readableArray) {
            super.setRotate((TextPathViewManager) textPathView, readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextPathManagerInterface
        public /* bridge */ /* synthetic */ void setStroke(TextPathView textPathView, @Nullable ReadableMap readableMap) {
            super.setStroke((TextPathViewManager) textPathView, readableMap);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextPathManagerInterface
        @ReactProp(name = "strokeDasharray")
        public /* bridge */ /* synthetic */ void setStrokeDasharray(TextPathView textPathView, @Nullable ReadableArray readableArray) {
            super.setStrokeDasharray((TextPathViewManager) textPathView, readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextPathManagerInterface
        @ReactProp(name = "strokeDasharray")
        public /* bridge */ /* synthetic */ void setStrokeDasharray(TextPathView textPathView, @Nullable String str) {
            super.setStrokeDasharray((TextPathViewManager) textPathView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextPathManagerInterface
        @ReactProp(name = "strokeDashoffset")
        public /* bridge */ /* synthetic */ void setStrokeDashoffset(TextPathView textPathView, float f) {
            super.setStrokeDashoffset((TextPathViewManager) textPathView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextPathManagerInterface
        @ReactProp(defaultInt = 1, name = "strokeLinecap")
        public /* bridge */ /* synthetic */ void setStrokeLinecap(TextPathView textPathView, int i) {
            super.setStrokeLinecap((TextPathViewManager) textPathView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextPathManagerInterface
        @ReactProp(defaultInt = 1, name = "strokeLinejoin")
        public /* bridge */ /* synthetic */ void setStrokeLinejoin(TextPathView textPathView, int i) {
            super.setStrokeLinejoin((TextPathViewManager) textPathView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextPathManagerInterface
        @ReactProp(defaultFloat = 4.0f, name = "strokeMiterlimit")
        public /* bridge */ /* synthetic */ void setStrokeMiterlimit(TextPathView textPathView, float f) {
            super.setStrokeMiterlimit((TextPathViewManager) textPathView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextPathManagerInterface
        @ReactProp(defaultFloat = 1.0f, name = "strokeOpacity")
        public /* bridge */ /* synthetic */ void setStrokeOpacity(TextPathView textPathView, float f) {
            super.setStrokeOpacity((TextPathViewManager) textPathView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextPathManagerInterface
        public /* bridge */ /* synthetic */ void setStrokeWidth(TextPathView textPathView, @Nullable Double d) {
            super.setStrokeWidth((TextPathViewManager) textPathView, d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextPathManagerInterface
        public /* bridge */ /* synthetic */ void setStrokeWidth(TextPathView textPathView, @Nullable String str) {
            super.setStrokeWidth((TextPathViewManager) textPathView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextPathManagerInterface
        public /* bridge */ /* synthetic */ void setTextLength(TextPathView textPathView, @Nullable Double d) {
            super.setTextLength((TextPathViewManager) textPathView, d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextPathManagerInterface
        public /* bridge */ /* synthetic */ void setTextLength(TextPathView textPathView, @Nullable String str) {
            super.setTextLength((TextPathViewManager) textPathView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextPathManagerInterface
        @ReactProp(name = "vectorEffect")
        public /* bridge */ /* synthetic */ void setVectorEffect(TextPathView textPathView, int i) {
            super.setVectorEffect((TextPathViewManager) textPathView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextPathManagerInterface
        @ReactProp(name = "verticalAlign")
        public /* bridge */ /* synthetic */ void setVerticalAlign(TextPathView textPathView, @Nullable String str) {
            super.setVerticalAlign((TextPathViewManager) textPathView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextPathManagerInterface
        public /* bridge */ /* synthetic */ void setX(TextPathView textPathView, @Nullable ReadableArray readableArray) {
            super.setX((TextPathViewManager) textPathView, readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextPathManagerInterface
        public /* bridge */ /* synthetic */ void setY(TextPathView textPathView, @Nullable ReadableArray readableArray) {
            super.setY((TextPathViewManager) textPathView, readableArray);
        }

        TextPathViewManager() {
            super(VirtualViewManager.SVGClass.RNSVGTextPath);
            this.mDelegate = new RNSVGTextPathManagerDelegate(this);
        }

        TextPathViewManager(VirtualViewManager.SVGClass sVGClass) {
            super(sVGClass);
            this.mDelegate = new RNSVGTextPathManagerDelegate(this);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextPathManagerInterface
        @ReactProp(name = "href")
        public void setHref(TextPathView textPathView, String str) {
            textPathView.setHref(str);
        }

        @ReactProp(name = "startOffset")
        public void setStartOffset(TextPathView textPathView, Dynamic dynamic) {
            textPathView.setStartOffset(dynamic);
        }

        @Override // com.horcrux.svg.RenderableViewManager.TextViewManagerAbstract
        @ReactProp(name = "method")
        public void setMethod(TextPathView textPathView, @Nullable String str) {
            textPathView.setMethod(str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextPathManagerInterface
        public void setMidLine(TextPathView textPathView, @Nullable String str) {
            textPathView.setSharp(str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextPathManagerInterface
        @ReactProp(name = "spacing")
        public void setSpacing(TextPathView textPathView, @Nullable String str) {
            textPathView.setSpacing(str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextPathManagerInterface
        public void setStartOffset(TextPathView textPathView, @Nullable String str) {
            textPathView.setStartOffset(str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextPathManagerInterface
        public void setStartOffset(TextPathView textPathView, @Nullable Double d) {
            textPathView.setStartOffset(d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGTextPathManagerInterface
        @ReactProp(name = "side")
        public void setSide(TextPathView textPathView, @Nullable String str) {
            textPathView.setSide(str);
        }

        @ReactProp(name = "midLine")
        public void setSharp(TextPathView textPathView, @Nullable String str) {
            textPathView.setSharp(str);
        }
    }

    static class ImageViewManager extends RenderableViewManager<ImageView> implements RNSVGImageManagerInterface<ImageView> {
        public static final String REACT_CLASS = "RNSVGImage";

        @Override // com.facebook.react.viewmanagers.RNSVGImageManagerInterface
        @ReactProp(name = "clipPath")
        public /* bridge */ /* synthetic */ void setClipPath(ImageView imageView, String str) {
            super.setClipPath((ImageViewManager) imageView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGImageManagerInterface
        @ReactProp(name = "clipRule")
        public /* bridge */ /* synthetic */ void setClipRule(ImageView imageView, int i) {
            super.setClipRule((ImageViewManager) imageView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGImageManagerInterface
        @ReactProp(name = "display")
        public /* bridge */ /* synthetic */ void setDisplay(ImageView imageView, String str) {
            super.setDisplay((ImageViewManager) imageView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGImageManagerInterface
        public /* bridge */ /* synthetic */ void setFill(ImageView imageView, @Nullable ReadableMap readableMap) {
            super.setFill((ImageViewManager) imageView, readableMap);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGImageManagerInterface
        @ReactProp(defaultFloat = 1.0f, name = "fillOpacity")
        public /* bridge */ /* synthetic */ void setFillOpacity(ImageView imageView, float f) {
            super.setFillOpacity((ImageViewManager) imageView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGImageManagerInterface
        @ReactProp(defaultInt = 1, name = "fillRule")
        public /* bridge */ /* synthetic */ void setFillRule(ImageView imageView, int i) {
            super.setFillRule((ImageViewManager) imageView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGImageManagerInterface
        @ReactProp(name = "markerEnd")
        public /* bridge */ /* synthetic */ void setMarkerEnd(ImageView imageView, String str) {
            super.setMarkerEnd((ImageViewManager) imageView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGImageManagerInterface
        @ReactProp(name = "markerMid")
        public /* bridge */ /* synthetic */ void setMarkerMid(ImageView imageView, String str) {
            super.setMarkerMid((ImageViewManager) imageView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGImageManagerInterface
        @ReactProp(name = "markerStart")
        public /* bridge */ /* synthetic */ void setMarkerStart(ImageView imageView, String str) {
            super.setMarkerStart((ImageViewManager) imageView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGImageManagerInterface
        @ReactProp(name = "mask")
        public /* bridge */ /* synthetic */ void setMask(ImageView imageView, String str) {
            super.setMask((ImageViewManager) imageView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGImageManagerInterface
        public /* bridge */ /* synthetic */ void setMatrix(ImageView imageView, @Nullable ReadableArray readableArray) {
            super.setMatrix((ImageViewManager) imageView, readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGImageManagerInterface
        @ReactProp(name = "name")
        public /* bridge */ /* synthetic */ void setName(ImageView imageView, String str) {
            super.setName((ImageViewManager) imageView, str);
        }

        @Override // com.facebook.react.uimanager.BaseViewManager, com.facebook.react.uimanager.BaseViewManagerInterface
        @ReactProp(defaultFloat = 1.0f, name = ViewProps.OPACITY)
        public /* bridge */ /* synthetic */ void setOpacity(@Nonnull View view, float f) {
            super.setOpacity((ImageViewManager) view, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGImageManagerInterface
        @ReactProp(name = ViewProps.POINTER_EVENTS)
        public /* bridge */ /* synthetic */ void setPointerEvents(ImageView imageView, @Nullable String str) {
            super.setPointerEvents((ImageViewManager) imageView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGImageManagerInterface
        @ReactProp(name = "propList")
        public /* bridge */ /* synthetic */ void setPropList(ImageView imageView, @Nullable ReadableArray readableArray) {
            super.setPropList((ImageViewManager) imageView, readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGImageManagerInterface
        @ReactProp(name = "responsible")
        public /* bridge */ /* synthetic */ void setResponsible(ImageView imageView, boolean z) {
            super.setResponsible((ImageViewManager) imageView, z);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGImageManagerInterface
        public /* bridge */ /* synthetic */ void setStroke(ImageView imageView, @Nullable ReadableMap readableMap) {
            super.setStroke((ImageViewManager) imageView, readableMap);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGImageManagerInterface
        @ReactProp(name = "strokeDasharray")
        public /* bridge */ /* synthetic */ void setStrokeDasharray(ImageView imageView, @Nullable ReadableArray readableArray) {
            super.setStrokeDasharray((ImageViewManager) imageView, readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGImageManagerInterface
        @ReactProp(name = "strokeDasharray")
        public /* bridge */ /* synthetic */ void setStrokeDasharray(ImageView imageView, @Nullable String str) {
            super.setStrokeDasharray((ImageViewManager) imageView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGImageManagerInterface
        @ReactProp(name = "strokeDashoffset")
        public /* bridge */ /* synthetic */ void setStrokeDashoffset(ImageView imageView, float f) {
            super.setStrokeDashoffset((ImageViewManager) imageView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGImageManagerInterface
        @ReactProp(defaultInt = 1, name = "strokeLinecap")
        public /* bridge */ /* synthetic */ void setStrokeLinecap(ImageView imageView, int i) {
            super.setStrokeLinecap((ImageViewManager) imageView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGImageManagerInterface
        @ReactProp(defaultInt = 1, name = "strokeLinejoin")
        public /* bridge */ /* synthetic */ void setStrokeLinejoin(ImageView imageView, int i) {
            super.setStrokeLinejoin((ImageViewManager) imageView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGImageManagerInterface
        @ReactProp(defaultFloat = 4.0f, name = "strokeMiterlimit")
        public /* bridge */ /* synthetic */ void setStrokeMiterlimit(ImageView imageView, float f) {
            super.setStrokeMiterlimit((ImageViewManager) imageView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGImageManagerInterface
        @ReactProp(defaultFloat = 1.0f, name = "strokeOpacity")
        public /* bridge */ /* synthetic */ void setStrokeOpacity(ImageView imageView, float f) {
            super.setStrokeOpacity((ImageViewManager) imageView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGImageManagerInterface
        public /* bridge */ /* synthetic */ void setStrokeWidth(ImageView imageView, @Nullable Double d) {
            super.setStrokeWidth((ImageViewManager) imageView, d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGImageManagerInterface
        public /* bridge */ /* synthetic */ void setStrokeWidth(ImageView imageView, @Nullable String str) {
            super.setStrokeWidth((ImageViewManager) imageView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGImageManagerInterface
        @ReactProp(name = "vectorEffect")
        public /* bridge */ /* synthetic */ void setVectorEffect(ImageView imageView, int i) {
            super.setVectorEffect((ImageViewManager) imageView, i);
        }

        ImageViewManager() {
            super(VirtualViewManager.SVGClass.RNSVGImage);
            this.mDelegate = new RNSVGImageManagerDelegate(this);
        }

        @ReactProp(name = ViewHierarchyNode.JsonKeys.X)
        public void setX(ImageView imageView, Dynamic dynamic) {
            imageView.setX(dynamic);
        }

        @ReactProp(name = ViewHierarchyNode.JsonKeys.Y)
        public void setY(ImageView imageView, Dynamic dynamic) {
            imageView.setY(dynamic);
        }

        @ReactProp(name = "width")
        public void setWidth(ImageView imageView, Dynamic dynamic) {
            imageView.setWidth(dynamic);
        }

        @ReactProp(name = "height")
        public void setHeight(ImageView imageView, Dynamic dynamic) {
            imageView.setHeight(dynamic);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGImageManagerInterface
        public void setX(ImageView imageView, @Nullable String str) {
            imageView.setX(str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGImageManagerInterface
        public void setY(ImageView imageView, @Nullable String str) {
            imageView.setY(str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGImageManagerInterface
        public void setWidth(ImageView imageView, @Nullable String str) {
            imageView.setWidth(str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGImageManagerInterface
        public void setHeight(ImageView imageView, @Nullable String str) {
            imageView.setHeight(str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGImageManagerInterface
        public void setX(ImageView imageView, @Nullable Double d) {
            imageView.setX(d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGImageManagerInterface
        public void setY(ImageView imageView, @Nullable Double d) {
            imageView.setY(d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGImageManagerInterface
        public void setWidth(ImageView imageView, @Nullable Double d) {
            imageView.setWidth(d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGImageManagerInterface
        public void setHeight(ImageView imageView, @Nullable Double d) {
            imageView.setHeight(d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGImageManagerInterface
        @ReactProp(customType = "ImageSource", name = "src")
        public void setSrc(ImageView imageView, @Nullable ReadableMap readableMap) {
            imageView.setSrc(readableMap);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGImageManagerInterface
        @ReactProp(name = "align")
        public void setAlign(ImageView imageView, String str) {
            imageView.setAlign(str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGImageManagerInterface
        @ReactProp(name = "meetOrSlice")
        public void setMeetOrSlice(ImageView imageView, int i) {
            imageView.setMeetOrSlice(i);
        }
    }

    static class CircleViewManager extends RenderableViewManager<CircleView> implements RNSVGCircleManagerInterface<CircleView> {
        public static final String REACT_CLASS = "RNSVGCircle";

        @Override // com.facebook.react.viewmanagers.RNSVGCircleManagerInterface
        @ReactProp(name = "clipPath")
        public /* bridge */ /* synthetic */ void setClipPath(CircleView circleView, String str) {
            super.setClipPath((CircleViewManager) circleView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGCircleManagerInterface
        @ReactProp(name = "clipRule")
        public /* bridge */ /* synthetic */ void setClipRule(CircleView circleView, int i) {
            super.setClipRule((CircleViewManager) circleView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGCircleManagerInterface
        @ReactProp(name = "display")
        public /* bridge */ /* synthetic */ void setDisplay(CircleView circleView, String str) {
            super.setDisplay((CircleViewManager) circleView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGCircleManagerInterface
        public /* bridge */ /* synthetic */ void setFill(CircleView circleView, @Nullable ReadableMap readableMap) {
            super.setFill((CircleViewManager) circleView, readableMap);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGCircleManagerInterface
        @ReactProp(defaultFloat = 1.0f, name = "fillOpacity")
        public /* bridge */ /* synthetic */ void setFillOpacity(CircleView circleView, float f) {
            super.setFillOpacity((CircleViewManager) circleView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGCircleManagerInterface
        @ReactProp(defaultInt = 1, name = "fillRule")
        public /* bridge */ /* synthetic */ void setFillRule(CircleView circleView, int i) {
            super.setFillRule((CircleViewManager) circleView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGCircleManagerInterface
        @ReactProp(name = "markerEnd")
        public /* bridge */ /* synthetic */ void setMarkerEnd(CircleView circleView, String str) {
            super.setMarkerEnd((CircleViewManager) circleView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGCircleManagerInterface
        @ReactProp(name = "markerMid")
        public /* bridge */ /* synthetic */ void setMarkerMid(CircleView circleView, String str) {
            super.setMarkerMid((CircleViewManager) circleView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGCircleManagerInterface
        @ReactProp(name = "markerStart")
        public /* bridge */ /* synthetic */ void setMarkerStart(CircleView circleView, String str) {
            super.setMarkerStart((CircleViewManager) circleView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGCircleManagerInterface
        @ReactProp(name = "mask")
        public /* bridge */ /* synthetic */ void setMask(CircleView circleView, String str) {
            super.setMask((CircleViewManager) circleView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGCircleManagerInterface
        public /* bridge */ /* synthetic */ void setMatrix(CircleView circleView, @Nullable ReadableArray readableArray) {
            super.setMatrix((CircleViewManager) circleView, readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGCircleManagerInterface
        @ReactProp(name = "name")
        public /* bridge */ /* synthetic */ void setName(CircleView circleView, String str) {
            super.setName((CircleViewManager) circleView, str);
        }

        @Override // com.facebook.react.uimanager.BaseViewManager, com.facebook.react.uimanager.BaseViewManagerInterface
        @ReactProp(defaultFloat = 1.0f, name = ViewProps.OPACITY)
        public /* bridge */ /* synthetic */ void setOpacity(@Nonnull View view, float f) {
            super.setOpacity((CircleViewManager) view, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGCircleManagerInterface
        @ReactProp(name = ViewProps.POINTER_EVENTS)
        public /* bridge */ /* synthetic */ void setPointerEvents(CircleView circleView, @Nullable String str) {
            super.setPointerEvents((CircleViewManager) circleView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGCircleManagerInterface
        @ReactProp(name = "propList")
        public /* bridge */ /* synthetic */ void setPropList(CircleView circleView, @Nullable ReadableArray readableArray) {
            super.setPropList((CircleViewManager) circleView, readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGCircleManagerInterface
        @ReactProp(name = "responsible")
        public /* bridge */ /* synthetic */ void setResponsible(CircleView circleView, boolean z) {
            super.setResponsible((CircleViewManager) circleView, z);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGCircleManagerInterface
        public /* bridge */ /* synthetic */ void setStroke(CircleView circleView, @Nullable ReadableMap readableMap) {
            super.setStroke((CircleViewManager) circleView, readableMap);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGCircleManagerInterface
        @ReactProp(name = "strokeDasharray")
        public /* bridge */ /* synthetic */ void setStrokeDasharray(CircleView circleView, @Nullable ReadableArray readableArray) {
            super.setStrokeDasharray((CircleViewManager) circleView, readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGCircleManagerInterface
        @ReactProp(name = "strokeDasharray")
        public /* bridge */ /* synthetic */ void setStrokeDasharray(CircleView circleView, @Nullable String str) {
            super.setStrokeDasharray((CircleViewManager) circleView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGCircleManagerInterface
        @ReactProp(name = "strokeDashoffset")
        public /* bridge */ /* synthetic */ void setStrokeDashoffset(CircleView circleView, float f) {
            super.setStrokeDashoffset((CircleViewManager) circleView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGCircleManagerInterface
        @ReactProp(defaultInt = 1, name = "strokeLinecap")
        public /* bridge */ /* synthetic */ void setStrokeLinecap(CircleView circleView, int i) {
            super.setStrokeLinecap((CircleViewManager) circleView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGCircleManagerInterface
        @ReactProp(defaultInt = 1, name = "strokeLinejoin")
        public /* bridge */ /* synthetic */ void setStrokeLinejoin(CircleView circleView, int i) {
            super.setStrokeLinejoin((CircleViewManager) circleView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGCircleManagerInterface
        @ReactProp(defaultFloat = 4.0f, name = "strokeMiterlimit")
        public /* bridge */ /* synthetic */ void setStrokeMiterlimit(CircleView circleView, float f) {
            super.setStrokeMiterlimit((CircleViewManager) circleView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGCircleManagerInterface
        @ReactProp(defaultFloat = 1.0f, name = "strokeOpacity")
        public /* bridge */ /* synthetic */ void setStrokeOpacity(CircleView circleView, float f) {
            super.setStrokeOpacity((CircleViewManager) circleView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGCircleManagerInterface
        public /* bridge */ /* synthetic */ void setStrokeWidth(CircleView circleView, @Nullable Double d) {
            super.setStrokeWidth((CircleViewManager) circleView, d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGCircleManagerInterface
        public /* bridge */ /* synthetic */ void setStrokeWidth(CircleView circleView, @Nullable String str) {
            super.setStrokeWidth((CircleViewManager) circleView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGCircleManagerInterface
        @ReactProp(name = "vectorEffect")
        public /* bridge */ /* synthetic */ void setVectorEffect(CircleView circleView, int i) {
            super.setVectorEffect((CircleViewManager) circleView, i);
        }

        CircleViewManager() {
            super(VirtualViewManager.SVGClass.RNSVGCircle);
            this.mDelegate = new RNSVGCircleManagerDelegate(this);
        }

        @ReactProp(name = "cx")
        public void setCx(CircleView circleView, Dynamic dynamic) {
            circleView.setCx(dynamic);
        }

        @ReactProp(name = "cy")
        public void setCy(CircleView circleView, Dynamic dynamic) {
            circleView.setCy(dynamic);
        }

        @ReactProp(name = "r")
        public void setR(CircleView circleView, Dynamic dynamic) {
            circleView.setR(dynamic);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGCircleManagerInterface
        public void setCx(CircleView circleView, String str) {
            circleView.setCx(str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGCircleManagerInterface
        public void setCx(CircleView circleView, Double d) {
            circleView.setCx(d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGCircleManagerInterface
        public void setCy(CircleView circleView, String str) {
            circleView.setCy(str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGCircleManagerInterface
        public void setCy(CircleView circleView, Double d) {
            circleView.setCy(d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGCircleManagerInterface
        public void setR(CircleView circleView, String str) {
            circleView.setR(str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGCircleManagerInterface
        public void setR(CircleView circleView, Double d) {
            circleView.setR(d);
        }
    }

    static class EllipseViewManager extends RenderableViewManager<EllipseView> implements RNSVGEllipseManagerInterface<EllipseView> {
        public static final String REACT_CLASS = "RNSVGEllipse";

        @Override // com.facebook.react.viewmanagers.RNSVGEllipseManagerInterface
        @ReactProp(name = "clipPath")
        public /* bridge */ /* synthetic */ void setClipPath(EllipseView ellipseView, String str) {
            super.setClipPath((EllipseViewManager) ellipseView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGEllipseManagerInterface
        @ReactProp(name = "clipRule")
        public /* bridge */ /* synthetic */ void setClipRule(EllipseView ellipseView, int i) {
            super.setClipRule((EllipseViewManager) ellipseView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGEllipseManagerInterface
        @ReactProp(name = "display")
        public /* bridge */ /* synthetic */ void setDisplay(EllipseView ellipseView, String str) {
            super.setDisplay((EllipseViewManager) ellipseView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGEllipseManagerInterface
        public /* bridge */ /* synthetic */ void setFill(EllipseView ellipseView, @Nullable ReadableMap readableMap) {
            super.setFill((EllipseViewManager) ellipseView, readableMap);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGEllipseManagerInterface
        @ReactProp(defaultFloat = 1.0f, name = "fillOpacity")
        public /* bridge */ /* synthetic */ void setFillOpacity(EllipseView ellipseView, float f) {
            super.setFillOpacity((EllipseViewManager) ellipseView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGEllipseManagerInterface
        @ReactProp(defaultInt = 1, name = "fillRule")
        public /* bridge */ /* synthetic */ void setFillRule(EllipseView ellipseView, int i) {
            super.setFillRule((EllipseViewManager) ellipseView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGEllipseManagerInterface
        @ReactProp(name = "markerEnd")
        public /* bridge */ /* synthetic */ void setMarkerEnd(EllipseView ellipseView, String str) {
            super.setMarkerEnd((EllipseViewManager) ellipseView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGEllipseManagerInterface
        @ReactProp(name = "markerMid")
        public /* bridge */ /* synthetic */ void setMarkerMid(EllipseView ellipseView, String str) {
            super.setMarkerMid((EllipseViewManager) ellipseView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGEllipseManagerInterface
        @ReactProp(name = "markerStart")
        public /* bridge */ /* synthetic */ void setMarkerStart(EllipseView ellipseView, String str) {
            super.setMarkerStart((EllipseViewManager) ellipseView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGEllipseManagerInterface
        @ReactProp(name = "mask")
        public /* bridge */ /* synthetic */ void setMask(EllipseView ellipseView, String str) {
            super.setMask((EllipseViewManager) ellipseView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGEllipseManagerInterface
        public /* bridge */ /* synthetic */ void setMatrix(EllipseView ellipseView, @Nullable ReadableArray readableArray) {
            super.setMatrix((EllipseViewManager) ellipseView, readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGEllipseManagerInterface
        @ReactProp(name = "name")
        public /* bridge */ /* synthetic */ void setName(EllipseView ellipseView, String str) {
            super.setName((EllipseViewManager) ellipseView, str);
        }

        @Override // com.facebook.react.uimanager.BaseViewManager, com.facebook.react.uimanager.BaseViewManagerInterface
        @ReactProp(defaultFloat = 1.0f, name = ViewProps.OPACITY)
        public /* bridge */ /* synthetic */ void setOpacity(@Nonnull View view, float f) {
            super.setOpacity((EllipseViewManager) view, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGEllipseManagerInterface
        @ReactProp(name = ViewProps.POINTER_EVENTS)
        public /* bridge */ /* synthetic */ void setPointerEvents(EllipseView ellipseView, @Nullable String str) {
            super.setPointerEvents((EllipseViewManager) ellipseView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGEllipseManagerInterface
        @ReactProp(name = "propList")
        public /* bridge */ /* synthetic */ void setPropList(EllipseView ellipseView, @Nullable ReadableArray readableArray) {
            super.setPropList((EllipseViewManager) ellipseView, readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGEllipseManagerInterface
        @ReactProp(name = "responsible")
        public /* bridge */ /* synthetic */ void setResponsible(EllipseView ellipseView, boolean z) {
            super.setResponsible((EllipseViewManager) ellipseView, z);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGEllipseManagerInterface
        public /* bridge */ /* synthetic */ void setStroke(EllipseView ellipseView, @Nullable ReadableMap readableMap) {
            super.setStroke((EllipseViewManager) ellipseView, readableMap);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGEllipseManagerInterface
        @ReactProp(name = "strokeDasharray")
        public /* bridge */ /* synthetic */ void setStrokeDasharray(EllipseView ellipseView, @Nullable ReadableArray readableArray) {
            super.setStrokeDasharray((EllipseViewManager) ellipseView, readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGEllipseManagerInterface
        @ReactProp(name = "strokeDasharray")
        public /* bridge */ /* synthetic */ void setStrokeDasharray(EllipseView ellipseView, @Nullable String str) {
            super.setStrokeDasharray((EllipseViewManager) ellipseView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGEllipseManagerInterface
        @ReactProp(name = "strokeDashoffset")
        public /* bridge */ /* synthetic */ void setStrokeDashoffset(EllipseView ellipseView, float f) {
            super.setStrokeDashoffset((EllipseViewManager) ellipseView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGEllipseManagerInterface
        @ReactProp(defaultInt = 1, name = "strokeLinecap")
        public /* bridge */ /* synthetic */ void setStrokeLinecap(EllipseView ellipseView, int i) {
            super.setStrokeLinecap((EllipseViewManager) ellipseView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGEllipseManagerInterface
        @ReactProp(defaultInt = 1, name = "strokeLinejoin")
        public /* bridge */ /* synthetic */ void setStrokeLinejoin(EllipseView ellipseView, int i) {
            super.setStrokeLinejoin((EllipseViewManager) ellipseView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGEllipseManagerInterface
        @ReactProp(defaultFloat = 4.0f, name = "strokeMiterlimit")
        public /* bridge */ /* synthetic */ void setStrokeMiterlimit(EllipseView ellipseView, float f) {
            super.setStrokeMiterlimit((EllipseViewManager) ellipseView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGEllipseManagerInterface
        @ReactProp(defaultFloat = 1.0f, name = "strokeOpacity")
        public /* bridge */ /* synthetic */ void setStrokeOpacity(EllipseView ellipseView, float f) {
            super.setStrokeOpacity((EllipseViewManager) ellipseView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGEllipseManagerInterface
        public /* bridge */ /* synthetic */ void setStrokeWidth(EllipseView ellipseView, @Nullable Double d) {
            super.setStrokeWidth((EllipseViewManager) ellipseView, d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGEllipseManagerInterface
        public /* bridge */ /* synthetic */ void setStrokeWidth(EllipseView ellipseView, @Nullable String str) {
            super.setStrokeWidth((EllipseViewManager) ellipseView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGEllipseManagerInterface
        @ReactProp(name = "vectorEffect")
        public /* bridge */ /* synthetic */ void setVectorEffect(EllipseView ellipseView, int i) {
            super.setVectorEffect((EllipseViewManager) ellipseView, i);
        }

        EllipseViewManager() {
            super(VirtualViewManager.SVGClass.RNSVGEllipse);
            this.mDelegate = new RNSVGEllipseManagerDelegate(this);
        }

        @ReactProp(name = "cx")
        public void setCx(EllipseView ellipseView, Dynamic dynamic) {
            ellipseView.setCx(dynamic);
        }

        @ReactProp(name = "cy")
        public void setCy(EllipseView ellipseView, Dynamic dynamic) {
            ellipseView.setCy(dynamic);
        }

        @ReactProp(name = "rx")
        public void setRx(EllipseView ellipseView, Dynamic dynamic) {
            ellipseView.setRx(dynamic);
        }

        @ReactProp(name = "ry")
        public void setRy(EllipseView ellipseView, Dynamic dynamic) {
            ellipseView.setRy(dynamic);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGEllipseManagerInterface
        public void setCx(EllipseView ellipseView, @Nullable String str) {
            ellipseView.setCx(str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGEllipseManagerInterface
        public void setCy(EllipseView ellipseView, @Nullable String str) {
            ellipseView.setCy(str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGEllipseManagerInterface
        public void setRx(EllipseView ellipseView, @Nullable String str) {
            ellipseView.setRx(str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGEllipseManagerInterface
        public void setRy(EllipseView ellipseView, @Nullable String str) {
            ellipseView.setRy(str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGEllipseManagerInterface
        public void setCx(EllipseView ellipseView, @Nullable Double d) {
            ellipseView.setCx(d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGEllipseManagerInterface
        public void setCy(EllipseView ellipseView, @Nullable Double d) {
            ellipseView.setCy(d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGEllipseManagerInterface
        public void setRx(EllipseView ellipseView, @Nullable Double d) {
            ellipseView.setRx(d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGEllipseManagerInterface
        public void setRy(EllipseView ellipseView, @Nullable Double d) {
            ellipseView.setRy(d);
        }
    }

    static class LineViewManager extends RenderableViewManager<LineView> implements RNSVGLineManagerInterface<LineView> {
        public static final String REACT_CLASS = "RNSVGLine";

        @Override // com.facebook.react.viewmanagers.RNSVGLineManagerInterface
        @ReactProp(name = "clipPath")
        public /* bridge */ /* synthetic */ void setClipPath(LineView lineView, String str) {
            super.setClipPath((LineViewManager) lineView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGLineManagerInterface
        @ReactProp(name = "clipRule")
        public /* bridge */ /* synthetic */ void setClipRule(LineView lineView, int i) {
            super.setClipRule((LineViewManager) lineView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGLineManagerInterface
        @ReactProp(name = "display")
        public /* bridge */ /* synthetic */ void setDisplay(LineView lineView, String str) {
            super.setDisplay((LineViewManager) lineView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGLineManagerInterface
        public /* bridge */ /* synthetic */ void setFill(LineView lineView, @Nullable ReadableMap readableMap) {
            super.setFill((LineViewManager) lineView, readableMap);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGLineManagerInterface
        @ReactProp(defaultFloat = 1.0f, name = "fillOpacity")
        public /* bridge */ /* synthetic */ void setFillOpacity(LineView lineView, float f) {
            super.setFillOpacity((LineViewManager) lineView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGLineManagerInterface
        @ReactProp(defaultInt = 1, name = "fillRule")
        public /* bridge */ /* synthetic */ void setFillRule(LineView lineView, int i) {
            super.setFillRule((LineViewManager) lineView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGLineManagerInterface
        @ReactProp(name = "markerEnd")
        public /* bridge */ /* synthetic */ void setMarkerEnd(LineView lineView, String str) {
            super.setMarkerEnd((LineViewManager) lineView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGLineManagerInterface
        @ReactProp(name = "markerMid")
        public /* bridge */ /* synthetic */ void setMarkerMid(LineView lineView, String str) {
            super.setMarkerMid((LineViewManager) lineView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGLineManagerInterface
        @ReactProp(name = "markerStart")
        public /* bridge */ /* synthetic */ void setMarkerStart(LineView lineView, String str) {
            super.setMarkerStart((LineViewManager) lineView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGLineManagerInterface
        @ReactProp(name = "mask")
        public /* bridge */ /* synthetic */ void setMask(LineView lineView, String str) {
            super.setMask((LineViewManager) lineView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGLineManagerInterface
        public /* bridge */ /* synthetic */ void setMatrix(LineView lineView, @Nullable ReadableArray readableArray) {
            super.setMatrix((LineViewManager) lineView, readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGLineManagerInterface
        @ReactProp(name = "name")
        public /* bridge */ /* synthetic */ void setName(LineView lineView, String str) {
            super.setName((LineViewManager) lineView, str);
        }

        @Override // com.facebook.react.uimanager.BaseViewManager, com.facebook.react.uimanager.BaseViewManagerInterface
        @ReactProp(defaultFloat = 1.0f, name = ViewProps.OPACITY)
        public /* bridge */ /* synthetic */ void setOpacity(@Nonnull View view, float f) {
            super.setOpacity((LineViewManager) view, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGLineManagerInterface
        @ReactProp(name = ViewProps.POINTER_EVENTS)
        public /* bridge */ /* synthetic */ void setPointerEvents(LineView lineView, @Nullable String str) {
            super.setPointerEvents((LineViewManager) lineView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGLineManagerInterface
        @ReactProp(name = "propList")
        public /* bridge */ /* synthetic */ void setPropList(LineView lineView, @Nullable ReadableArray readableArray) {
            super.setPropList((LineViewManager) lineView, readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGLineManagerInterface
        @ReactProp(name = "responsible")
        public /* bridge */ /* synthetic */ void setResponsible(LineView lineView, boolean z) {
            super.setResponsible((LineViewManager) lineView, z);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGLineManagerInterface
        public /* bridge */ /* synthetic */ void setStroke(LineView lineView, @Nullable ReadableMap readableMap) {
            super.setStroke((LineViewManager) lineView, readableMap);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGLineManagerInterface
        @ReactProp(name = "strokeDasharray")
        public /* bridge */ /* synthetic */ void setStrokeDasharray(LineView lineView, @Nullable ReadableArray readableArray) {
            super.setStrokeDasharray((LineViewManager) lineView, readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGLineManagerInterface
        @ReactProp(name = "strokeDasharray")
        public /* bridge */ /* synthetic */ void setStrokeDasharray(LineView lineView, @Nullable String str) {
            super.setStrokeDasharray((LineViewManager) lineView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGLineManagerInterface
        @ReactProp(name = "strokeDashoffset")
        public /* bridge */ /* synthetic */ void setStrokeDashoffset(LineView lineView, float f) {
            super.setStrokeDashoffset((LineViewManager) lineView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGLineManagerInterface
        @ReactProp(defaultInt = 1, name = "strokeLinecap")
        public /* bridge */ /* synthetic */ void setStrokeLinecap(LineView lineView, int i) {
            super.setStrokeLinecap((LineViewManager) lineView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGLineManagerInterface
        @ReactProp(defaultInt = 1, name = "strokeLinejoin")
        public /* bridge */ /* synthetic */ void setStrokeLinejoin(LineView lineView, int i) {
            super.setStrokeLinejoin((LineViewManager) lineView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGLineManagerInterface
        @ReactProp(defaultFloat = 4.0f, name = "strokeMiterlimit")
        public /* bridge */ /* synthetic */ void setStrokeMiterlimit(LineView lineView, float f) {
            super.setStrokeMiterlimit((LineViewManager) lineView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGLineManagerInterface
        @ReactProp(defaultFloat = 1.0f, name = "strokeOpacity")
        public /* bridge */ /* synthetic */ void setStrokeOpacity(LineView lineView, float f) {
            super.setStrokeOpacity((LineViewManager) lineView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGLineManagerInterface
        public /* bridge */ /* synthetic */ void setStrokeWidth(LineView lineView, @Nullable Double d) {
            super.setStrokeWidth((LineViewManager) lineView, d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGLineManagerInterface
        public /* bridge */ /* synthetic */ void setStrokeWidth(LineView lineView, @Nullable String str) {
            super.setStrokeWidth((LineViewManager) lineView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGLineManagerInterface
        @ReactProp(name = "vectorEffect")
        public /* bridge */ /* synthetic */ void setVectorEffect(LineView lineView, int i) {
            super.setVectorEffect((LineViewManager) lineView, i);
        }

        LineViewManager() {
            super(VirtualViewManager.SVGClass.RNSVGLine);
            this.mDelegate = new RNSVGLineManagerDelegate(this);
        }

        @ReactProp(name = "x1")
        public void setX1(LineView lineView, Dynamic dynamic) {
            lineView.setX1(dynamic);
        }

        @ReactProp(name = "y1")
        public void setY1(LineView lineView, Dynamic dynamic) {
            lineView.setY1(dynamic);
        }

        @ReactProp(name = "x2")
        public void setX2(LineView lineView, Dynamic dynamic) {
            lineView.setX2(dynamic);
        }

        @ReactProp(name = "y2")
        public void setY2(LineView lineView, Dynamic dynamic) {
            lineView.setY2(dynamic);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGLineManagerInterface
        public void setX1(LineView lineView, @Nullable String str) {
            lineView.setX1(str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGLineManagerInterface
        public void setY1(LineView lineView, @Nullable String str) {
            lineView.setY1(str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGLineManagerInterface
        public void setX2(LineView lineView, @Nullable String str) {
            lineView.setX2(str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGLineManagerInterface
        public void setY2(LineView lineView, @Nullable String str) {
            lineView.setY2(str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGLineManagerInterface
        public void setX1(LineView lineView, @Nullable Double d) {
            lineView.setX1(d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGLineManagerInterface
        public void setY1(LineView lineView, @Nullable Double d) {
            lineView.setY1(d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGLineManagerInterface
        public void setX2(LineView lineView, @Nullable Double d) {
            lineView.setX2(d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGLineManagerInterface
        public void setY2(LineView lineView, @Nullable Double d) {
            lineView.setY2(d);
        }
    }

    static class RectViewManager extends RenderableViewManager<RectView> implements RNSVGRectManagerInterface<RectView> {
        public static final String REACT_CLASS = "RNSVGRect";

        @Override // com.facebook.react.viewmanagers.RNSVGRectManagerInterface
        @ReactProp(name = "clipPath")
        public /* bridge */ /* synthetic */ void setClipPath(RectView rectView, String str) {
            super.setClipPath((RectViewManager) rectView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGRectManagerInterface
        @ReactProp(name = "clipRule")
        public /* bridge */ /* synthetic */ void setClipRule(RectView rectView, int i) {
            super.setClipRule((RectViewManager) rectView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGRectManagerInterface
        @ReactProp(name = "display")
        public /* bridge */ /* synthetic */ void setDisplay(RectView rectView, String str) {
            super.setDisplay((RectViewManager) rectView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGRectManagerInterface
        public /* bridge */ /* synthetic */ void setFill(RectView rectView, @Nullable ReadableMap readableMap) {
            super.setFill((RectViewManager) rectView, readableMap);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGRectManagerInterface
        @ReactProp(defaultFloat = 1.0f, name = "fillOpacity")
        public /* bridge */ /* synthetic */ void setFillOpacity(RectView rectView, float f) {
            super.setFillOpacity((RectViewManager) rectView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGRectManagerInterface
        @ReactProp(defaultInt = 1, name = "fillRule")
        public /* bridge */ /* synthetic */ void setFillRule(RectView rectView, int i) {
            super.setFillRule((RectViewManager) rectView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGRectManagerInterface
        @ReactProp(name = "markerEnd")
        public /* bridge */ /* synthetic */ void setMarkerEnd(RectView rectView, String str) {
            super.setMarkerEnd((RectViewManager) rectView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGRectManagerInterface
        @ReactProp(name = "markerMid")
        public /* bridge */ /* synthetic */ void setMarkerMid(RectView rectView, String str) {
            super.setMarkerMid((RectViewManager) rectView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGRectManagerInterface
        @ReactProp(name = "markerStart")
        public /* bridge */ /* synthetic */ void setMarkerStart(RectView rectView, String str) {
            super.setMarkerStart((RectViewManager) rectView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGRectManagerInterface
        @ReactProp(name = "mask")
        public /* bridge */ /* synthetic */ void setMask(RectView rectView, String str) {
            super.setMask((RectViewManager) rectView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGRectManagerInterface
        public /* bridge */ /* synthetic */ void setMatrix(RectView rectView, @Nullable ReadableArray readableArray) {
            super.setMatrix((RectViewManager) rectView, readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGRectManagerInterface
        @ReactProp(name = "name")
        public /* bridge */ /* synthetic */ void setName(RectView rectView, String str) {
            super.setName((RectViewManager) rectView, str);
        }

        @Override // com.facebook.react.uimanager.BaseViewManager, com.facebook.react.uimanager.BaseViewManagerInterface
        @ReactProp(defaultFloat = 1.0f, name = ViewProps.OPACITY)
        public /* bridge */ /* synthetic */ void setOpacity(@Nonnull View view, float f) {
            super.setOpacity((RectViewManager) view, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGRectManagerInterface
        @ReactProp(name = ViewProps.POINTER_EVENTS)
        public /* bridge */ /* synthetic */ void setPointerEvents(RectView rectView, @Nullable String str) {
            super.setPointerEvents((RectViewManager) rectView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGRectManagerInterface
        @ReactProp(name = "propList")
        public /* bridge */ /* synthetic */ void setPropList(RectView rectView, @Nullable ReadableArray readableArray) {
            super.setPropList((RectViewManager) rectView, readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGRectManagerInterface
        @ReactProp(name = "responsible")
        public /* bridge */ /* synthetic */ void setResponsible(RectView rectView, boolean z) {
            super.setResponsible((RectViewManager) rectView, z);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGRectManagerInterface
        public /* bridge */ /* synthetic */ void setStroke(RectView rectView, @Nullable ReadableMap readableMap) {
            super.setStroke((RectViewManager) rectView, readableMap);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGRectManagerInterface
        @ReactProp(name = "strokeDasharray")
        public /* bridge */ /* synthetic */ void setStrokeDasharray(RectView rectView, @Nullable ReadableArray readableArray) {
            super.setStrokeDasharray((RectViewManager) rectView, readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGRectManagerInterface
        @ReactProp(name = "strokeDasharray")
        public /* bridge */ /* synthetic */ void setStrokeDasharray(RectView rectView, @Nullable String str) {
            super.setStrokeDasharray((RectViewManager) rectView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGRectManagerInterface
        @ReactProp(name = "strokeDashoffset")
        public /* bridge */ /* synthetic */ void setStrokeDashoffset(RectView rectView, float f) {
            super.setStrokeDashoffset((RectViewManager) rectView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGRectManagerInterface
        @ReactProp(defaultInt = 1, name = "strokeLinecap")
        public /* bridge */ /* synthetic */ void setStrokeLinecap(RectView rectView, int i) {
            super.setStrokeLinecap((RectViewManager) rectView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGRectManagerInterface
        @ReactProp(defaultInt = 1, name = "strokeLinejoin")
        public /* bridge */ /* synthetic */ void setStrokeLinejoin(RectView rectView, int i) {
            super.setStrokeLinejoin((RectViewManager) rectView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGRectManagerInterface
        @ReactProp(defaultFloat = 4.0f, name = "strokeMiterlimit")
        public /* bridge */ /* synthetic */ void setStrokeMiterlimit(RectView rectView, float f) {
            super.setStrokeMiterlimit((RectViewManager) rectView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGRectManagerInterface
        @ReactProp(defaultFloat = 1.0f, name = "strokeOpacity")
        public /* bridge */ /* synthetic */ void setStrokeOpacity(RectView rectView, float f) {
            super.setStrokeOpacity((RectViewManager) rectView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGRectManagerInterface
        public /* bridge */ /* synthetic */ void setStrokeWidth(RectView rectView, @Nullable Double d) {
            super.setStrokeWidth((RectViewManager) rectView, d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGRectManagerInterface
        public /* bridge */ /* synthetic */ void setStrokeWidth(RectView rectView, @Nullable String str) {
            super.setStrokeWidth((RectViewManager) rectView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGRectManagerInterface
        @ReactProp(name = "vectorEffect")
        public /* bridge */ /* synthetic */ void setVectorEffect(RectView rectView, int i) {
            super.setVectorEffect((RectViewManager) rectView, i);
        }

        RectViewManager() {
            super(VirtualViewManager.SVGClass.RNSVGRect);
            this.mDelegate = new RNSVGRectManagerDelegate(this);
        }

        @ReactProp(name = ViewHierarchyNode.JsonKeys.X)
        public void setX(RectView rectView, Dynamic dynamic) {
            rectView.setX(dynamic);
        }

        @ReactProp(name = ViewHierarchyNode.JsonKeys.Y)
        public void setY(RectView rectView, Dynamic dynamic) {
            rectView.setY(dynamic);
        }

        @ReactProp(name = "width")
        public void setWidth(RectView rectView, Dynamic dynamic) {
            rectView.setWidth(dynamic);
        }

        @ReactProp(name = "height")
        public void setHeight(RectView rectView, Dynamic dynamic) {
            rectView.setHeight(dynamic);
        }

        @ReactProp(name = "rx")
        public void setRx(RectView rectView, Dynamic dynamic) {
            rectView.setRx(dynamic);
        }

        @ReactProp(name = "ry")
        public void setRy(RectView rectView, Dynamic dynamic) {
            rectView.setRy(dynamic);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGRectManagerInterface
        public void setX(RectView rectView, @Nullable String str) {
            rectView.setX(str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGRectManagerInterface
        public void setY(RectView rectView, @Nullable String str) {
            rectView.setY(str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGRectManagerInterface
        public void setHeight(RectView rectView, @Nullable String str) {
            rectView.setHeight(str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGRectManagerInterface
        public void setWidth(RectView rectView, @Nullable String str) {
            rectView.setWidth(str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGRectManagerInterface
        public void setRx(RectView rectView, @Nullable String str) {
            rectView.setRx(str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGRectManagerInterface
        public void setRy(RectView rectView, @Nullable String str) {
            rectView.setRy(str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGRectManagerInterface
        public void setX(RectView rectView, @Nullable Double d) {
            rectView.setX(d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGRectManagerInterface
        public void setY(RectView rectView, @Nullable Double d) {
            rectView.setY(d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGRectManagerInterface
        public void setHeight(RectView rectView, @Nullable Double d) {
            rectView.setHeight(d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGRectManagerInterface
        public void setWidth(RectView rectView, @Nullable Double d) {
            rectView.setWidth(d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGRectManagerInterface
        public void setRx(RectView rectView, @Nullable Double d) {
            rectView.setRx(d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGRectManagerInterface
        public void setRy(RectView rectView, @Nullable Double d) {
            rectView.setRy(d);
        }
    }

    static class ClipPathViewManager extends GroupViewManagerAbstract<ClipPathView> implements RNSVGClipPathManagerInterface<ClipPathView> {
        public static final String REACT_CLASS = "RNSVGClipPath";

        @Override // com.facebook.react.viewmanagers.RNSVGClipPathManagerInterface
        @ReactProp(name = "clipPath")
        public /* bridge */ /* synthetic */ void setClipPath(ClipPathView clipPathView, String str) {
            super.setClipPath((ClipPathViewManager) clipPathView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGClipPathManagerInterface
        @ReactProp(name = "clipRule")
        public /* bridge */ /* synthetic */ void setClipRule(ClipPathView clipPathView, int i) {
            super.setClipRule((ClipPathViewManager) clipPathView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGClipPathManagerInterface
        @ReactProp(name = "display")
        public /* bridge */ /* synthetic */ void setDisplay(ClipPathView clipPathView, String str) {
            super.setDisplay((ClipPathViewManager) clipPathView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGClipPathManagerInterface
        public /* bridge */ /* synthetic */ void setFill(ClipPathView clipPathView, @Nullable ReadableMap readableMap) {
            super.setFill((ClipPathViewManager) clipPathView, readableMap);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGClipPathManagerInterface
        @ReactProp(defaultFloat = 1.0f, name = "fillOpacity")
        public /* bridge */ /* synthetic */ void setFillOpacity(ClipPathView clipPathView, float f) {
            super.setFillOpacity((ClipPathViewManager) clipPathView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGClipPathManagerInterface
        @ReactProp(defaultInt = 1, name = "fillRule")
        public /* bridge */ /* synthetic */ void setFillRule(ClipPathView clipPathView, int i) {
            super.setFillRule((ClipPathViewManager) clipPathView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGClipPathManagerInterface
        @ReactProp(name = "font")
        public /* bridge */ /* synthetic */ void setFont(ClipPathView clipPathView, @Nullable ReadableMap readableMap) {
            super.setFont((ClipPathViewManager) clipPathView, readableMap);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGClipPathManagerInterface
        public /* bridge */ /* synthetic */ void setFontSize(ClipPathView clipPathView, @Nullable Double d) {
            super.setFontSize((ClipPathViewManager) clipPathView, d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGClipPathManagerInterface
        public /* bridge */ /* synthetic */ void setFontSize(ClipPathView clipPathView, @Nullable String str) {
            super.setFontSize((ClipPathViewManager) clipPathView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGClipPathManagerInterface
        public /* bridge */ /* synthetic */ void setFontWeight(ClipPathView clipPathView, @Nullable Double d) {
            super.setFontWeight((ClipPathViewManager) clipPathView, d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGClipPathManagerInterface
        public /* bridge */ /* synthetic */ void setFontWeight(ClipPathView clipPathView, @Nullable String str) {
            super.setFontWeight((ClipPathViewManager) clipPathView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGClipPathManagerInterface
        @ReactProp(name = "markerEnd")
        public /* bridge */ /* synthetic */ void setMarkerEnd(ClipPathView clipPathView, String str) {
            super.setMarkerEnd((ClipPathViewManager) clipPathView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGClipPathManagerInterface
        @ReactProp(name = "markerMid")
        public /* bridge */ /* synthetic */ void setMarkerMid(ClipPathView clipPathView, String str) {
            super.setMarkerMid((ClipPathViewManager) clipPathView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGClipPathManagerInterface
        @ReactProp(name = "markerStart")
        public /* bridge */ /* synthetic */ void setMarkerStart(ClipPathView clipPathView, String str) {
            super.setMarkerStart((ClipPathViewManager) clipPathView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGClipPathManagerInterface
        @ReactProp(name = "mask")
        public /* bridge */ /* synthetic */ void setMask(ClipPathView clipPathView, String str) {
            super.setMask((ClipPathViewManager) clipPathView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGClipPathManagerInterface
        public /* bridge */ /* synthetic */ void setMatrix(ClipPathView clipPathView, @Nullable ReadableArray readableArray) {
            super.setMatrix((ClipPathViewManager) clipPathView, readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGClipPathManagerInterface
        @ReactProp(name = "name")
        public /* bridge */ /* synthetic */ void setName(ClipPathView clipPathView, String str) {
            super.setName((ClipPathViewManager) clipPathView, str);
        }

        @Override // com.facebook.react.uimanager.BaseViewManager, com.facebook.react.uimanager.BaseViewManagerInterface
        @ReactProp(defaultFloat = 1.0f, name = ViewProps.OPACITY)
        public /* bridge */ /* synthetic */ void setOpacity(@Nonnull View view, float f) {
            super.setOpacity((ClipPathViewManager) view, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGClipPathManagerInterface
        @ReactProp(name = ViewProps.POINTER_EVENTS)
        public /* bridge */ /* synthetic */ void setPointerEvents(ClipPathView clipPathView, @Nullable String str) {
            super.setPointerEvents((ClipPathViewManager) clipPathView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGClipPathManagerInterface
        @ReactProp(name = "propList")
        public /* bridge */ /* synthetic */ void setPropList(ClipPathView clipPathView, @Nullable ReadableArray readableArray) {
            super.setPropList((ClipPathViewManager) clipPathView, readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGClipPathManagerInterface
        @ReactProp(name = "responsible")
        public /* bridge */ /* synthetic */ void setResponsible(ClipPathView clipPathView, boolean z) {
            super.setResponsible((ClipPathViewManager) clipPathView, z);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGClipPathManagerInterface
        public /* bridge */ /* synthetic */ void setStroke(ClipPathView clipPathView, @Nullable ReadableMap readableMap) {
            super.setStroke((ClipPathViewManager) clipPathView, readableMap);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGClipPathManagerInterface
        @ReactProp(name = "strokeDasharray")
        public /* bridge */ /* synthetic */ void setStrokeDasharray(ClipPathView clipPathView, @Nullable ReadableArray readableArray) {
            super.setStrokeDasharray((ClipPathViewManager) clipPathView, readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGClipPathManagerInterface
        @ReactProp(name = "strokeDasharray")
        public /* bridge */ /* synthetic */ void setStrokeDasharray(ClipPathView clipPathView, @Nullable String str) {
            super.setStrokeDasharray((ClipPathViewManager) clipPathView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGClipPathManagerInterface
        @ReactProp(name = "strokeDashoffset")
        public /* bridge */ /* synthetic */ void setStrokeDashoffset(ClipPathView clipPathView, float f) {
            super.setStrokeDashoffset((ClipPathViewManager) clipPathView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGClipPathManagerInterface
        @ReactProp(defaultInt = 1, name = "strokeLinecap")
        public /* bridge */ /* synthetic */ void setStrokeLinecap(ClipPathView clipPathView, int i) {
            super.setStrokeLinecap((ClipPathViewManager) clipPathView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGClipPathManagerInterface
        @ReactProp(defaultInt = 1, name = "strokeLinejoin")
        public /* bridge */ /* synthetic */ void setStrokeLinejoin(ClipPathView clipPathView, int i) {
            super.setStrokeLinejoin((ClipPathViewManager) clipPathView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGClipPathManagerInterface
        @ReactProp(defaultFloat = 4.0f, name = "strokeMiterlimit")
        public /* bridge */ /* synthetic */ void setStrokeMiterlimit(ClipPathView clipPathView, float f) {
            super.setStrokeMiterlimit((ClipPathViewManager) clipPathView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGClipPathManagerInterface
        @ReactProp(defaultFloat = 1.0f, name = "strokeOpacity")
        public /* bridge */ /* synthetic */ void setStrokeOpacity(ClipPathView clipPathView, float f) {
            super.setStrokeOpacity((ClipPathViewManager) clipPathView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGClipPathManagerInterface
        public /* bridge */ /* synthetic */ void setStrokeWidth(ClipPathView clipPathView, @Nullable Double d) {
            super.setStrokeWidth((ClipPathViewManager) clipPathView, d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGClipPathManagerInterface
        public /* bridge */ /* synthetic */ void setStrokeWidth(ClipPathView clipPathView, @Nullable String str) {
            super.setStrokeWidth((ClipPathViewManager) clipPathView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGClipPathManagerInterface
        @ReactProp(name = "vectorEffect")
        public /* bridge */ /* synthetic */ void setVectorEffect(ClipPathView clipPathView, int i) {
            super.setVectorEffect((ClipPathViewManager) clipPathView, i);
        }

        ClipPathViewManager() {
            super(VirtualViewManager.SVGClass.RNSVGClipPath);
            this.mDelegate = new RNSVGClipPathManagerDelegate(this);
        }
    }

    static class DefsViewManager extends VirtualViewManager<DefsView> implements RNSVGDefsManagerInterface<DefsView> {
        public static final String REACT_CLASS = "RNSVGDefs";

        @Override // com.facebook.react.viewmanagers.RNSVGDefsManagerInterface
        @ReactProp(name = "clipPath")
        public /* bridge */ /* synthetic */ void setClipPath(DefsView defsView, String str) {
            super.setClipPath((DefsViewManager) defsView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGDefsManagerInterface
        @ReactProp(name = "clipRule")
        public /* bridge */ /* synthetic */ void setClipRule(DefsView defsView, int i) {
            super.setClipRule((DefsViewManager) defsView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGDefsManagerInterface
        @ReactProp(name = "display")
        public /* bridge */ /* synthetic */ void setDisplay(DefsView defsView, String str) {
            super.setDisplay((DefsViewManager) defsView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGDefsManagerInterface
        @ReactProp(name = "markerEnd")
        public /* bridge */ /* synthetic */ void setMarkerEnd(DefsView defsView, String str) {
            super.setMarkerEnd((DefsViewManager) defsView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGDefsManagerInterface
        @ReactProp(name = "markerMid")
        public /* bridge */ /* synthetic */ void setMarkerMid(DefsView defsView, String str) {
            super.setMarkerMid((DefsViewManager) defsView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGDefsManagerInterface
        @ReactProp(name = "markerStart")
        public /* bridge */ /* synthetic */ void setMarkerStart(DefsView defsView, String str) {
            super.setMarkerStart((DefsViewManager) defsView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGDefsManagerInterface
        @ReactProp(name = "mask")
        public /* bridge */ /* synthetic */ void setMask(DefsView defsView, String str) {
            super.setMask((DefsViewManager) defsView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGDefsManagerInterface
        public /* bridge */ /* synthetic */ void setMatrix(DefsView defsView, @Nullable ReadableArray readableArray) {
            super.setMatrix((DefsViewManager) defsView, readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGDefsManagerInterface
        @ReactProp(name = "name")
        public /* bridge */ /* synthetic */ void setName(DefsView defsView, String str) {
            super.setName((DefsViewManager) defsView, str);
        }

        @Override // com.facebook.react.uimanager.BaseViewManager, com.facebook.react.uimanager.BaseViewManagerInterface
        @ReactProp(defaultFloat = 1.0f, name = ViewProps.OPACITY)
        public /* bridge */ /* synthetic */ void setOpacity(@Nonnull View view, float f) {
            super.setOpacity((DefsViewManager) view, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGDefsManagerInterface
        @ReactProp(name = ViewProps.POINTER_EVENTS)
        public /* bridge */ /* synthetic */ void setPointerEvents(DefsView defsView, @Nullable String str) {
            super.setPointerEvents((DefsViewManager) defsView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGDefsManagerInterface
        @ReactProp(name = "responsible")
        public /* bridge */ /* synthetic */ void setResponsible(DefsView defsView, boolean z) {
            super.setResponsible((DefsViewManager) defsView, z);
        }

        DefsViewManager() {
            super(VirtualViewManager.SVGClass.RNSVGDefs);
            this.mDelegate = new RNSVGDefsManagerDelegate(this);
        }
    }

    static class UseViewManager extends RenderableViewManager<UseView> implements RNSVGUseManagerInterface<UseView> {
        public static final String REACT_CLASS = "RNSVGUse";

        @Override // com.facebook.react.viewmanagers.RNSVGUseManagerInterface
        @ReactProp(name = "clipPath")
        public /* bridge */ /* synthetic */ void setClipPath(UseView useView, String str) {
            super.setClipPath((UseViewManager) useView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGUseManagerInterface
        @ReactProp(name = "clipRule")
        public /* bridge */ /* synthetic */ void setClipRule(UseView useView, int i) {
            super.setClipRule((UseViewManager) useView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGUseManagerInterface
        @ReactProp(name = "display")
        public /* bridge */ /* synthetic */ void setDisplay(UseView useView, String str) {
            super.setDisplay((UseViewManager) useView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGUseManagerInterface
        public /* bridge */ /* synthetic */ void setFill(UseView useView, @Nullable ReadableMap readableMap) {
            super.setFill((UseViewManager) useView, readableMap);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGUseManagerInterface
        @ReactProp(defaultFloat = 1.0f, name = "fillOpacity")
        public /* bridge */ /* synthetic */ void setFillOpacity(UseView useView, float f) {
            super.setFillOpacity((UseViewManager) useView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGUseManagerInterface
        @ReactProp(defaultInt = 1, name = "fillRule")
        public /* bridge */ /* synthetic */ void setFillRule(UseView useView, int i) {
            super.setFillRule((UseViewManager) useView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGUseManagerInterface
        @ReactProp(name = "markerEnd")
        public /* bridge */ /* synthetic */ void setMarkerEnd(UseView useView, String str) {
            super.setMarkerEnd((UseViewManager) useView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGUseManagerInterface
        @ReactProp(name = "markerMid")
        public /* bridge */ /* synthetic */ void setMarkerMid(UseView useView, String str) {
            super.setMarkerMid((UseViewManager) useView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGUseManagerInterface
        @ReactProp(name = "markerStart")
        public /* bridge */ /* synthetic */ void setMarkerStart(UseView useView, String str) {
            super.setMarkerStart((UseViewManager) useView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGUseManagerInterface
        @ReactProp(name = "mask")
        public /* bridge */ /* synthetic */ void setMask(UseView useView, String str) {
            super.setMask((UseViewManager) useView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGUseManagerInterface
        public /* bridge */ /* synthetic */ void setMatrix(UseView useView, @Nullable ReadableArray readableArray) {
            super.setMatrix((UseViewManager) useView, readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGUseManagerInterface
        @ReactProp(name = "name")
        public /* bridge */ /* synthetic */ void setName(UseView useView, String str) {
            super.setName((UseViewManager) useView, str);
        }

        @Override // com.facebook.react.uimanager.BaseViewManager, com.facebook.react.uimanager.BaseViewManagerInterface
        @ReactProp(defaultFloat = 1.0f, name = ViewProps.OPACITY)
        public /* bridge */ /* synthetic */ void setOpacity(@Nonnull View view, float f) {
            super.setOpacity((UseViewManager) view, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGUseManagerInterface
        @ReactProp(name = ViewProps.POINTER_EVENTS)
        public /* bridge */ /* synthetic */ void setPointerEvents(UseView useView, @Nullable String str) {
            super.setPointerEvents((UseViewManager) useView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGUseManagerInterface
        @ReactProp(name = "propList")
        public /* bridge */ /* synthetic */ void setPropList(UseView useView, @Nullable ReadableArray readableArray) {
            super.setPropList((UseViewManager) useView, readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGUseManagerInterface
        @ReactProp(name = "responsible")
        public /* bridge */ /* synthetic */ void setResponsible(UseView useView, boolean z) {
            super.setResponsible((UseViewManager) useView, z);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGUseManagerInterface
        public /* bridge */ /* synthetic */ void setStroke(UseView useView, @Nullable ReadableMap readableMap) {
            super.setStroke((UseViewManager) useView, readableMap);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGUseManagerInterface
        @ReactProp(name = "strokeDasharray")
        public /* bridge */ /* synthetic */ void setStrokeDasharray(UseView useView, @Nullable ReadableArray readableArray) {
            super.setStrokeDasharray((UseViewManager) useView, readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGUseManagerInterface
        @ReactProp(name = "strokeDasharray")
        public /* bridge */ /* synthetic */ void setStrokeDasharray(UseView useView, @Nullable String str) {
            super.setStrokeDasharray((UseViewManager) useView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGUseManagerInterface
        @ReactProp(name = "strokeDashoffset")
        public /* bridge */ /* synthetic */ void setStrokeDashoffset(UseView useView, float f) {
            super.setStrokeDashoffset((UseViewManager) useView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGUseManagerInterface
        @ReactProp(defaultInt = 1, name = "strokeLinecap")
        public /* bridge */ /* synthetic */ void setStrokeLinecap(UseView useView, int i) {
            super.setStrokeLinecap((UseViewManager) useView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGUseManagerInterface
        @ReactProp(defaultInt = 1, name = "strokeLinejoin")
        public /* bridge */ /* synthetic */ void setStrokeLinejoin(UseView useView, int i) {
            super.setStrokeLinejoin((UseViewManager) useView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGUseManagerInterface
        @ReactProp(defaultFloat = 4.0f, name = "strokeMiterlimit")
        public /* bridge */ /* synthetic */ void setStrokeMiterlimit(UseView useView, float f) {
            super.setStrokeMiterlimit((UseViewManager) useView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGUseManagerInterface
        @ReactProp(defaultFloat = 1.0f, name = "strokeOpacity")
        public /* bridge */ /* synthetic */ void setStrokeOpacity(UseView useView, float f) {
            super.setStrokeOpacity((UseViewManager) useView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGUseManagerInterface
        public /* bridge */ /* synthetic */ void setStrokeWidth(UseView useView, @Nullable Double d) {
            super.setStrokeWidth((UseViewManager) useView, d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGUseManagerInterface
        public /* bridge */ /* synthetic */ void setStrokeWidth(UseView useView, @Nullable String str) {
            super.setStrokeWidth((UseViewManager) useView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGUseManagerInterface
        @ReactProp(name = "vectorEffect")
        public /* bridge */ /* synthetic */ void setVectorEffect(UseView useView, int i) {
            super.setVectorEffect((UseViewManager) useView, i);
        }

        UseViewManager() {
            super(VirtualViewManager.SVGClass.RNSVGUse);
            this.mDelegate = new RNSVGUseManagerDelegate(this);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGUseManagerInterface
        @ReactProp(name = "href")
        public void setHref(UseView useView, String str) {
            useView.setHref(str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGUseManagerInterface
        public void setX(UseView useView, @Nullable String str) {
            useView.setX(str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGUseManagerInterface
        public void setY(UseView useView, @Nullable String str) {
            useView.setY(str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGUseManagerInterface
        public void setHeight(UseView useView, @Nullable String str) {
            useView.setHeight(str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGUseManagerInterface
        public void setWidth(UseView useView, @Nullable Double d) {
            useView.setWidth(d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGUseManagerInterface
        public void setX(UseView useView, @Nullable Double d) {
            useView.setX(d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGUseManagerInterface
        public void setY(UseView useView, @Nullable Double d) {
            useView.setY(d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGUseManagerInterface
        public void setHeight(UseView useView, @Nullable Double d) {
            useView.setHeight(d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGUseManagerInterface
        public void setWidth(UseView useView, @Nullable String str) {
            useView.setWidth(str);
        }

        @ReactProp(name = ViewHierarchyNode.JsonKeys.X)
        public void setX(UseView useView, Dynamic dynamic) {
            useView.setX(dynamic);
        }

        @ReactProp(name = ViewHierarchyNode.JsonKeys.Y)
        public void setY(UseView useView, Dynamic dynamic) {
            useView.setY(dynamic);
        }

        @ReactProp(name = "width")
        public void setWidth(UseView useView, Dynamic dynamic) {
            useView.setWidth(dynamic);
        }

        @ReactProp(name = "height")
        public void setHeight(UseView useView, Dynamic dynamic) {
            useView.setHeight(dynamic);
        }
    }

    static class SymbolManager extends GroupViewManagerAbstract<SymbolView> implements RNSVGSymbolManagerInterface<SymbolView> {
        public static final String REACT_CLASS = "RNSVGSymbol";

        @Override // com.facebook.react.viewmanagers.RNSVGSymbolManagerInterface
        @ReactProp(name = "clipPath")
        public /* bridge */ /* synthetic */ void setClipPath(SymbolView symbolView, String str) {
            super.setClipPath((SymbolManager) symbolView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGSymbolManagerInterface
        @ReactProp(name = "clipRule")
        public /* bridge */ /* synthetic */ void setClipRule(SymbolView symbolView, int i) {
            super.setClipRule((SymbolManager) symbolView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGSymbolManagerInterface
        @ReactProp(name = "display")
        public /* bridge */ /* synthetic */ void setDisplay(SymbolView symbolView, String str) {
            super.setDisplay((SymbolManager) symbolView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGSymbolManagerInterface
        public /* bridge */ /* synthetic */ void setFill(SymbolView symbolView, @Nullable ReadableMap readableMap) {
            super.setFill((SymbolManager) symbolView, readableMap);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGSymbolManagerInterface
        @ReactProp(defaultFloat = 1.0f, name = "fillOpacity")
        public /* bridge */ /* synthetic */ void setFillOpacity(SymbolView symbolView, float f) {
            super.setFillOpacity((SymbolManager) symbolView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGSymbolManagerInterface
        @ReactProp(defaultInt = 1, name = "fillRule")
        public /* bridge */ /* synthetic */ void setFillRule(SymbolView symbolView, int i) {
            super.setFillRule((SymbolManager) symbolView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGSymbolManagerInterface
        @ReactProp(name = "font")
        public /* bridge */ /* synthetic */ void setFont(SymbolView symbolView, @Nullable ReadableMap readableMap) {
            super.setFont((SymbolManager) symbolView, readableMap);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGSymbolManagerInterface
        public /* bridge */ /* synthetic */ void setFontSize(SymbolView symbolView, @Nullable Double d) {
            super.setFontSize((SymbolManager) symbolView, d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGSymbolManagerInterface
        public /* bridge */ /* synthetic */ void setFontSize(SymbolView symbolView, @Nullable String str) {
            super.setFontSize((SymbolManager) symbolView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGSymbolManagerInterface
        public /* bridge */ /* synthetic */ void setFontWeight(SymbolView symbolView, @Nullable Double d) {
            super.setFontWeight((SymbolManager) symbolView, d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGSymbolManagerInterface
        public /* bridge */ /* synthetic */ void setFontWeight(SymbolView symbolView, @Nullable String str) {
            super.setFontWeight((SymbolManager) symbolView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGSymbolManagerInterface
        @ReactProp(name = "markerEnd")
        public /* bridge */ /* synthetic */ void setMarkerEnd(SymbolView symbolView, String str) {
            super.setMarkerEnd((SymbolManager) symbolView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGSymbolManagerInterface
        @ReactProp(name = "markerMid")
        public /* bridge */ /* synthetic */ void setMarkerMid(SymbolView symbolView, String str) {
            super.setMarkerMid((SymbolManager) symbolView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGSymbolManagerInterface
        @ReactProp(name = "markerStart")
        public /* bridge */ /* synthetic */ void setMarkerStart(SymbolView symbolView, String str) {
            super.setMarkerStart((SymbolManager) symbolView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGSymbolManagerInterface
        @ReactProp(name = "mask")
        public /* bridge */ /* synthetic */ void setMask(SymbolView symbolView, String str) {
            super.setMask((SymbolManager) symbolView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGSymbolManagerInterface
        public /* bridge */ /* synthetic */ void setMatrix(SymbolView symbolView, @Nullable ReadableArray readableArray) {
            super.setMatrix((SymbolManager) symbolView, readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGSymbolManagerInterface
        @ReactProp(name = "name")
        public /* bridge */ /* synthetic */ void setName(SymbolView symbolView, String str) {
            super.setName((SymbolManager) symbolView, str);
        }

        @Override // com.facebook.react.uimanager.BaseViewManager, com.facebook.react.uimanager.BaseViewManagerInterface
        @ReactProp(defaultFloat = 1.0f, name = ViewProps.OPACITY)
        public /* bridge */ /* synthetic */ void setOpacity(@Nonnull View view, float f) {
            super.setOpacity((SymbolManager) view, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGSymbolManagerInterface
        @ReactProp(name = ViewProps.POINTER_EVENTS)
        public /* bridge */ /* synthetic */ void setPointerEvents(SymbolView symbolView, @Nullable String str) {
            super.setPointerEvents((SymbolManager) symbolView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGSymbolManagerInterface
        @ReactProp(name = "propList")
        public /* bridge */ /* synthetic */ void setPropList(SymbolView symbolView, @Nullable ReadableArray readableArray) {
            super.setPropList((SymbolManager) symbolView, readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGSymbolManagerInterface
        @ReactProp(name = "responsible")
        public /* bridge */ /* synthetic */ void setResponsible(SymbolView symbolView, boolean z) {
            super.setResponsible((SymbolManager) symbolView, z);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGSymbolManagerInterface
        public /* bridge */ /* synthetic */ void setStroke(SymbolView symbolView, @Nullable ReadableMap readableMap) {
            super.setStroke((SymbolManager) symbolView, readableMap);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGSymbolManagerInterface
        @ReactProp(name = "strokeDasharray")
        public /* bridge */ /* synthetic */ void setStrokeDasharray(SymbolView symbolView, @Nullable ReadableArray readableArray) {
            super.setStrokeDasharray((SymbolManager) symbolView, readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGSymbolManagerInterface
        @ReactProp(name = "strokeDasharray")
        public /* bridge */ /* synthetic */ void setStrokeDasharray(SymbolView symbolView, @Nullable String str) {
            super.setStrokeDasharray((SymbolManager) symbolView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGSymbolManagerInterface
        @ReactProp(name = "strokeDashoffset")
        public /* bridge */ /* synthetic */ void setStrokeDashoffset(SymbolView symbolView, float f) {
            super.setStrokeDashoffset((SymbolManager) symbolView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGSymbolManagerInterface
        @ReactProp(defaultInt = 1, name = "strokeLinecap")
        public /* bridge */ /* synthetic */ void setStrokeLinecap(SymbolView symbolView, int i) {
            super.setStrokeLinecap((SymbolManager) symbolView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGSymbolManagerInterface
        @ReactProp(defaultInt = 1, name = "strokeLinejoin")
        public /* bridge */ /* synthetic */ void setStrokeLinejoin(SymbolView symbolView, int i) {
            super.setStrokeLinejoin((SymbolManager) symbolView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGSymbolManagerInterface
        @ReactProp(defaultFloat = 4.0f, name = "strokeMiterlimit")
        public /* bridge */ /* synthetic */ void setStrokeMiterlimit(SymbolView symbolView, float f) {
            super.setStrokeMiterlimit((SymbolManager) symbolView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGSymbolManagerInterface
        @ReactProp(defaultFloat = 1.0f, name = "strokeOpacity")
        public /* bridge */ /* synthetic */ void setStrokeOpacity(SymbolView symbolView, float f) {
            super.setStrokeOpacity((SymbolManager) symbolView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGSymbolManagerInterface
        public /* bridge */ /* synthetic */ void setStrokeWidth(SymbolView symbolView, @Nullable Double d) {
            super.setStrokeWidth((SymbolManager) symbolView, d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGSymbolManagerInterface
        public /* bridge */ /* synthetic */ void setStrokeWidth(SymbolView symbolView, @Nullable String str) {
            super.setStrokeWidth((SymbolManager) symbolView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGSymbolManagerInterface
        @ReactProp(name = "vectorEffect")
        public /* bridge */ /* synthetic */ void setVectorEffect(SymbolView symbolView, int i) {
            super.setVectorEffect((SymbolManager) symbolView, i);
        }

        SymbolManager() {
            super(VirtualViewManager.SVGClass.RNSVGSymbol);
            this.mDelegate = new RNSVGSymbolManagerDelegate(this);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGSymbolManagerInterface
        @ReactProp(name = "minX")
        public void setMinX(SymbolView symbolView, float f) {
            symbolView.setMinX(f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGSymbolManagerInterface
        @ReactProp(name = "minY")
        public void setMinY(SymbolView symbolView, float f) {
            symbolView.setMinY(f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGSymbolManagerInterface
        @ReactProp(name = "vbWidth")
        public void setVbWidth(SymbolView symbolView, float f) {
            symbolView.setVbWidth(f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGSymbolManagerInterface
        @ReactProp(name = "vbHeight")
        public void setVbHeight(SymbolView symbolView, float f) {
            symbolView.setVbHeight(f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGSymbolManagerInterface
        @ReactProp(name = "align")
        public void setAlign(SymbolView symbolView, String str) {
            symbolView.setAlign(str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGSymbolManagerInterface
        @ReactProp(name = "meetOrSlice")
        public void setMeetOrSlice(SymbolView symbolView, int i) {
            symbolView.setMeetOrSlice(i);
        }
    }

    static class PatternManager extends GroupViewManagerAbstract<PatternView> implements RNSVGPatternManagerInterface<PatternView> {
        public static final String REACT_CLASS = "RNSVGPattern";

        @Override // com.facebook.react.viewmanagers.RNSVGPatternManagerInterface
        @ReactProp(name = "clipPath")
        public /* bridge */ /* synthetic */ void setClipPath(PatternView patternView, String str) {
            super.setClipPath((PatternManager) patternView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPatternManagerInterface
        @ReactProp(name = "clipRule")
        public /* bridge */ /* synthetic */ void setClipRule(PatternView patternView, int i) {
            super.setClipRule((PatternManager) patternView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPatternManagerInterface
        @ReactProp(name = "display")
        public /* bridge */ /* synthetic */ void setDisplay(PatternView patternView, String str) {
            super.setDisplay((PatternManager) patternView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPatternManagerInterface
        public /* bridge */ /* synthetic */ void setFill(PatternView patternView, @Nullable ReadableMap readableMap) {
            super.setFill((PatternManager) patternView, readableMap);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPatternManagerInterface
        @ReactProp(defaultFloat = 1.0f, name = "fillOpacity")
        public /* bridge */ /* synthetic */ void setFillOpacity(PatternView patternView, float f) {
            super.setFillOpacity((PatternManager) patternView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPatternManagerInterface
        @ReactProp(defaultInt = 1, name = "fillRule")
        public /* bridge */ /* synthetic */ void setFillRule(PatternView patternView, int i) {
            super.setFillRule((PatternManager) patternView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPatternManagerInterface
        @ReactProp(name = "font")
        public /* bridge */ /* synthetic */ void setFont(PatternView patternView, @Nullable ReadableMap readableMap) {
            super.setFont((PatternManager) patternView, readableMap);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPatternManagerInterface
        public /* bridge */ /* synthetic */ void setFontSize(PatternView patternView, @Nullable Double d) {
            super.setFontSize((PatternManager) patternView, d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPatternManagerInterface
        public /* bridge */ /* synthetic */ void setFontSize(PatternView patternView, @Nullable String str) {
            super.setFontSize((PatternManager) patternView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPatternManagerInterface
        public /* bridge */ /* synthetic */ void setFontWeight(PatternView patternView, @Nullable Double d) {
            super.setFontWeight((PatternManager) patternView, d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPatternManagerInterface
        public /* bridge */ /* synthetic */ void setFontWeight(PatternView patternView, @Nullable String str) {
            super.setFontWeight((PatternManager) patternView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPatternManagerInterface
        @ReactProp(name = "markerEnd")
        public /* bridge */ /* synthetic */ void setMarkerEnd(PatternView patternView, String str) {
            super.setMarkerEnd((PatternManager) patternView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPatternManagerInterface
        @ReactProp(name = "markerMid")
        public /* bridge */ /* synthetic */ void setMarkerMid(PatternView patternView, String str) {
            super.setMarkerMid((PatternManager) patternView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPatternManagerInterface
        @ReactProp(name = "markerStart")
        public /* bridge */ /* synthetic */ void setMarkerStart(PatternView patternView, String str) {
            super.setMarkerStart((PatternManager) patternView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPatternManagerInterface
        @ReactProp(name = "mask")
        public /* bridge */ /* synthetic */ void setMask(PatternView patternView, String str) {
            super.setMask((PatternManager) patternView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPatternManagerInterface
        public /* bridge */ /* synthetic */ void setMatrix(PatternView patternView, @Nullable ReadableArray readableArray) {
            super.setMatrix((PatternManager) patternView, readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPatternManagerInterface
        @ReactProp(name = "name")
        public /* bridge */ /* synthetic */ void setName(PatternView patternView, String str) {
            super.setName((PatternManager) patternView, str);
        }

        @Override // com.facebook.react.uimanager.BaseViewManager, com.facebook.react.uimanager.BaseViewManagerInterface
        @ReactProp(defaultFloat = 1.0f, name = ViewProps.OPACITY)
        public /* bridge */ /* synthetic */ void setOpacity(@Nonnull View view, float f) {
            super.setOpacity((PatternManager) view, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPatternManagerInterface
        @ReactProp(name = ViewProps.POINTER_EVENTS)
        public /* bridge */ /* synthetic */ void setPointerEvents(PatternView patternView, @Nullable String str) {
            super.setPointerEvents((PatternManager) patternView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPatternManagerInterface
        @ReactProp(name = "propList")
        public /* bridge */ /* synthetic */ void setPropList(PatternView patternView, @Nullable ReadableArray readableArray) {
            super.setPropList((PatternManager) patternView, readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPatternManagerInterface
        @ReactProp(name = "responsible")
        public /* bridge */ /* synthetic */ void setResponsible(PatternView patternView, boolean z) {
            super.setResponsible((PatternManager) patternView, z);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPatternManagerInterface
        public /* bridge */ /* synthetic */ void setStroke(PatternView patternView, @Nullable ReadableMap readableMap) {
            super.setStroke((PatternManager) patternView, readableMap);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPatternManagerInterface
        @ReactProp(name = "strokeDasharray")
        public /* bridge */ /* synthetic */ void setStrokeDasharray(PatternView patternView, @Nullable ReadableArray readableArray) {
            super.setStrokeDasharray((PatternManager) patternView, readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPatternManagerInterface
        @ReactProp(name = "strokeDasharray")
        public /* bridge */ /* synthetic */ void setStrokeDasharray(PatternView patternView, @Nullable String str) {
            super.setStrokeDasharray((PatternManager) patternView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPatternManagerInterface
        @ReactProp(name = "strokeDashoffset")
        public /* bridge */ /* synthetic */ void setStrokeDashoffset(PatternView patternView, float f) {
            super.setStrokeDashoffset((PatternManager) patternView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPatternManagerInterface
        @ReactProp(defaultInt = 1, name = "strokeLinecap")
        public /* bridge */ /* synthetic */ void setStrokeLinecap(PatternView patternView, int i) {
            super.setStrokeLinecap((PatternManager) patternView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPatternManagerInterface
        @ReactProp(defaultInt = 1, name = "strokeLinejoin")
        public /* bridge */ /* synthetic */ void setStrokeLinejoin(PatternView patternView, int i) {
            super.setStrokeLinejoin((PatternManager) patternView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPatternManagerInterface
        @ReactProp(defaultFloat = 4.0f, name = "strokeMiterlimit")
        public /* bridge */ /* synthetic */ void setStrokeMiterlimit(PatternView patternView, float f) {
            super.setStrokeMiterlimit((PatternManager) patternView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPatternManagerInterface
        @ReactProp(defaultFloat = 1.0f, name = "strokeOpacity")
        public /* bridge */ /* synthetic */ void setStrokeOpacity(PatternView patternView, float f) {
            super.setStrokeOpacity((PatternManager) patternView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPatternManagerInterface
        public /* bridge */ /* synthetic */ void setStrokeWidth(PatternView patternView, @Nullable Double d) {
            super.setStrokeWidth((PatternManager) patternView, d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPatternManagerInterface
        public /* bridge */ /* synthetic */ void setStrokeWidth(PatternView patternView, @Nullable String str) {
            super.setStrokeWidth((PatternManager) patternView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPatternManagerInterface
        @ReactProp(name = "vectorEffect")
        public /* bridge */ /* synthetic */ void setVectorEffect(PatternView patternView, int i) {
            super.setVectorEffect((PatternManager) patternView, i);
        }

        PatternManager() {
            super(VirtualViewManager.SVGClass.RNSVGPattern);
            this.mDelegate = new RNSVGPatternManagerDelegate(this);
        }

        @ReactProp(name = ViewHierarchyNode.JsonKeys.X)
        public void setX(PatternView patternView, Dynamic dynamic) {
            patternView.setX(dynamic);
        }

        @ReactProp(name = ViewHierarchyNode.JsonKeys.Y)
        public void setY(PatternView patternView, Dynamic dynamic) {
            patternView.setY(dynamic);
        }

        @ReactProp(name = "width")
        public void setWidth(PatternView patternView, Dynamic dynamic) {
            patternView.setWidth(dynamic);
        }

        @ReactProp(name = "height")
        public void setHeight(PatternView patternView, Dynamic dynamic) {
            patternView.setHeight(dynamic);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPatternManagerInterface
        public void setX(PatternView patternView, @Nullable String str) {
            patternView.setX(str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPatternManagerInterface
        public void setY(PatternView patternView, @Nullable String str) {
            patternView.setY(str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPatternManagerInterface
        public void setHeight(PatternView patternView, @Nullable String str) {
            patternView.setHeight(str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPatternManagerInterface
        public void setWidth(PatternView patternView, @Nullable String str) {
            patternView.setWidth(str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPatternManagerInterface
        public void setX(PatternView patternView, @Nullable Double d) {
            patternView.setX(d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPatternManagerInterface
        public void setY(PatternView patternView, @Nullable Double d) {
            patternView.setY(d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPatternManagerInterface
        public void setHeight(PatternView patternView, @Nullable Double d) {
            patternView.setHeight(d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPatternManagerInterface
        public void setWidth(PatternView patternView, @Nullable Double d) {
            patternView.setWidth(d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPatternManagerInterface
        @ReactProp(name = "patternUnits")
        public void setPatternUnits(PatternView patternView, int i) {
            patternView.setPatternUnits(i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPatternManagerInterface
        @ReactProp(name = "patternContentUnits")
        public void setPatternContentUnits(PatternView patternView, int i) {
            patternView.setPatternContentUnits(i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPatternManagerInterface
        @ReactProp(name = "patternTransform")
        public void setPatternTransform(PatternView patternView, @Nullable ReadableArray readableArray) {
            patternView.setPatternTransform(readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPatternManagerInterface
        @ReactProp(name = "minX")
        public void setMinX(PatternView patternView, float f) {
            patternView.setMinX(f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPatternManagerInterface
        @ReactProp(name = "minY")
        public void setMinY(PatternView patternView, float f) {
            patternView.setMinY(f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPatternManagerInterface
        @ReactProp(name = "vbWidth")
        public void setVbWidth(PatternView patternView, float f) {
            patternView.setVbWidth(f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPatternManagerInterface
        @ReactProp(name = "vbHeight")
        public void setVbHeight(PatternView patternView, float f) {
            patternView.setVbHeight(f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPatternManagerInterface
        @ReactProp(name = "align")
        public void setAlign(PatternView patternView, String str) {
            patternView.setAlign(str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGPatternManagerInterface
        @ReactProp(name = "meetOrSlice")
        public void setMeetOrSlice(PatternView patternView, int i) {
            patternView.setMeetOrSlice(i);
        }
    }

    static class MaskManager extends GroupViewManagerAbstract<MaskView> implements RNSVGMaskManagerInterface<MaskView> {
        public static final String REACT_CLASS = "RNSVGMask";

        @Override // com.facebook.react.viewmanagers.RNSVGMaskManagerInterface
        @ReactProp(name = "clipPath")
        public /* bridge */ /* synthetic */ void setClipPath(MaskView maskView, String str) {
            super.setClipPath((MaskManager) maskView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMaskManagerInterface
        @ReactProp(name = "clipRule")
        public /* bridge */ /* synthetic */ void setClipRule(MaskView maskView, int i) {
            super.setClipRule((MaskManager) maskView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMaskManagerInterface
        @ReactProp(name = "display")
        public /* bridge */ /* synthetic */ void setDisplay(MaskView maskView, String str) {
            super.setDisplay((MaskManager) maskView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMaskManagerInterface
        public /* bridge */ /* synthetic */ void setFill(MaskView maskView, @Nullable ReadableMap readableMap) {
            super.setFill((MaskManager) maskView, readableMap);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMaskManagerInterface
        @ReactProp(defaultFloat = 1.0f, name = "fillOpacity")
        public /* bridge */ /* synthetic */ void setFillOpacity(MaskView maskView, float f) {
            super.setFillOpacity((MaskManager) maskView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMaskManagerInterface
        @ReactProp(defaultInt = 1, name = "fillRule")
        public /* bridge */ /* synthetic */ void setFillRule(MaskView maskView, int i) {
            super.setFillRule((MaskManager) maskView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMaskManagerInterface
        @ReactProp(name = "font")
        public /* bridge */ /* synthetic */ void setFont(MaskView maskView, @Nullable ReadableMap readableMap) {
            super.setFont((MaskManager) maskView, readableMap);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMaskManagerInterface
        public /* bridge */ /* synthetic */ void setFontSize(MaskView maskView, @Nullable Double d) {
            super.setFontSize((MaskManager) maskView, d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMaskManagerInterface
        public /* bridge */ /* synthetic */ void setFontSize(MaskView maskView, @Nullable String str) {
            super.setFontSize((MaskManager) maskView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMaskManagerInterface
        public /* bridge */ /* synthetic */ void setFontWeight(MaskView maskView, @Nullable Double d) {
            super.setFontWeight((MaskManager) maskView, d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMaskManagerInterface
        public /* bridge */ /* synthetic */ void setFontWeight(MaskView maskView, @Nullable String str) {
            super.setFontWeight((MaskManager) maskView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMaskManagerInterface
        @ReactProp(name = "markerEnd")
        public /* bridge */ /* synthetic */ void setMarkerEnd(MaskView maskView, String str) {
            super.setMarkerEnd((MaskManager) maskView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMaskManagerInterface
        @ReactProp(name = "markerMid")
        public /* bridge */ /* synthetic */ void setMarkerMid(MaskView maskView, String str) {
            super.setMarkerMid((MaskManager) maskView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMaskManagerInterface
        @ReactProp(name = "markerStart")
        public /* bridge */ /* synthetic */ void setMarkerStart(MaskView maskView, String str) {
            super.setMarkerStart((MaskManager) maskView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMaskManagerInterface
        @ReactProp(name = "mask")
        public /* bridge */ /* synthetic */ void setMask(MaskView maskView, String str) {
            super.setMask((MaskManager) maskView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMaskManagerInterface
        public /* bridge */ /* synthetic */ void setMatrix(MaskView maskView, @Nullable ReadableArray readableArray) {
            super.setMatrix((MaskManager) maskView, readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMaskManagerInterface
        @ReactProp(name = "name")
        public /* bridge */ /* synthetic */ void setName(MaskView maskView, String str) {
            super.setName((MaskManager) maskView, str);
        }

        @Override // com.facebook.react.uimanager.BaseViewManager, com.facebook.react.uimanager.BaseViewManagerInterface
        @ReactProp(defaultFloat = 1.0f, name = ViewProps.OPACITY)
        public /* bridge */ /* synthetic */ void setOpacity(@Nonnull View view, float f) {
            super.setOpacity((MaskManager) view, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMaskManagerInterface
        @ReactProp(name = ViewProps.POINTER_EVENTS)
        public /* bridge */ /* synthetic */ void setPointerEvents(MaskView maskView, @Nullable String str) {
            super.setPointerEvents((MaskManager) maskView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMaskManagerInterface
        @ReactProp(name = "propList")
        public /* bridge */ /* synthetic */ void setPropList(MaskView maskView, @Nullable ReadableArray readableArray) {
            super.setPropList((MaskManager) maskView, readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMaskManagerInterface
        @ReactProp(name = "responsible")
        public /* bridge */ /* synthetic */ void setResponsible(MaskView maskView, boolean z) {
            super.setResponsible((MaskManager) maskView, z);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMaskManagerInterface
        public /* bridge */ /* synthetic */ void setStroke(MaskView maskView, @Nullable ReadableMap readableMap) {
            super.setStroke((MaskManager) maskView, readableMap);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMaskManagerInterface
        @ReactProp(name = "strokeDasharray")
        public /* bridge */ /* synthetic */ void setStrokeDasharray(MaskView maskView, @Nullable ReadableArray readableArray) {
            super.setStrokeDasharray((MaskManager) maskView, readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMaskManagerInterface
        @ReactProp(name = "strokeDasharray")
        public /* bridge */ /* synthetic */ void setStrokeDasharray(MaskView maskView, @Nullable String str) {
            super.setStrokeDasharray((MaskManager) maskView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMaskManagerInterface
        @ReactProp(name = "strokeDashoffset")
        public /* bridge */ /* synthetic */ void setStrokeDashoffset(MaskView maskView, float f) {
            super.setStrokeDashoffset((MaskManager) maskView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMaskManagerInterface
        @ReactProp(defaultInt = 1, name = "strokeLinecap")
        public /* bridge */ /* synthetic */ void setStrokeLinecap(MaskView maskView, int i) {
            super.setStrokeLinecap((MaskManager) maskView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMaskManagerInterface
        @ReactProp(defaultInt = 1, name = "strokeLinejoin")
        public /* bridge */ /* synthetic */ void setStrokeLinejoin(MaskView maskView, int i) {
            super.setStrokeLinejoin((MaskManager) maskView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMaskManagerInterface
        @ReactProp(defaultFloat = 4.0f, name = "strokeMiterlimit")
        public /* bridge */ /* synthetic */ void setStrokeMiterlimit(MaskView maskView, float f) {
            super.setStrokeMiterlimit((MaskManager) maskView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMaskManagerInterface
        @ReactProp(defaultFloat = 1.0f, name = "strokeOpacity")
        public /* bridge */ /* synthetic */ void setStrokeOpacity(MaskView maskView, float f) {
            super.setStrokeOpacity((MaskManager) maskView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMaskManagerInterface
        public /* bridge */ /* synthetic */ void setStrokeWidth(MaskView maskView, @Nullable Double d) {
            super.setStrokeWidth((MaskManager) maskView, d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMaskManagerInterface
        public /* bridge */ /* synthetic */ void setStrokeWidth(MaskView maskView, @Nullable String str) {
            super.setStrokeWidth((MaskManager) maskView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMaskManagerInterface
        @ReactProp(name = "vectorEffect")
        public /* bridge */ /* synthetic */ void setVectorEffect(MaskView maskView, int i) {
            super.setVectorEffect((MaskManager) maskView, i);
        }

        MaskManager() {
            super(VirtualViewManager.SVGClass.RNSVGMask);
            this.mDelegate = new RNSVGMaskManagerDelegate(this);
        }

        @ReactProp(name = ViewHierarchyNode.JsonKeys.X)
        public void setX(MaskView maskView, Dynamic dynamic) {
            maskView.setX(dynamic);
        }

        @ReactProp(name = ViewHierarchyNode.JsonKeys.Y)
        public void setY(MaskView maskView, Dynamic dynamic) {
            maskView.setY(dynamic);
        }

        @ReactProp(name = "width")
        public void setWidth(MaskView maskView, Dynamic dynamic) {
            maskView.setWidth(dynamic);
        }

        @ReactProp(name = "height")
        public void setHeight(MaskView maskView, Dynamic dynamic) {
            maskView.setHeight(dynamic);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMaskManagerInterface
        public void setX(MaskView maskView, @Nullable String str) {
            maskView.setX(str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMaskManagerInterface
        public void setY(MaskView maskView, @Nullable String str) {
            maskView.setY(str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMaskManagerInterface
        public void setHeight(MaskView maskView, @Nullable String str) {
            maskView.setHeight(str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMaskManagerInterface
        public void setWidth(MaskView maskView, @Nullable String str) {
            maskView.setWidth(str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMaskManagerInterface
        public void setX(MaskView maskView, @Nullable Double d) {
            maskView.setX(d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMaskManagerInterface
        public void setY(MaskView maskView, @Nullable Double d) {
            maskView.setY(d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMaskManagerInterface
        public void setHeight(MaskView maskView, @Nullable Double d) {
            maskView.setHeight(d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMaskManagerInterface
        public void setWidth(MaskView maskView, @Nullable Double d) {
            maskView.setWidth(d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMaskManagerInterface
        @ReactProp(name = "maskUnits")
        public void setMaskUnits(MaskView maskView, int i) {
            maskView.setMaskUnits(i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMaskManagerInterface
        @ReactProp(name = "maskContentUnits")
        public void setMaskContentUnits(MaskView maskView, int i) {
            maskView.setMaskContentUnits(i);
        }
    }

    static class ForeignObjectManager extends GroupViewManagerAbstract<ForeignObjectView> implements RNSVGForeignObjectManagerInterface<ForeignObjectView> {
        public static final String REACT_CLASS = "RNSVGForeignObject";

        @Override // com.facebook.react.viewmanagers.RNSVGForeignObjectManagerInterface
        @ReactProp(name = "clipPath")
        public /* bridge */ /* synthetic */ void setClipPath(ForeignObjectView foreignObjectView, String str) {
            super.setClipPath((ForeignObjectManager) foreignObjectView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGForeignObjectManagerInterface
        @ReactProp(name = "clipRule")
        public /* bridge */ /* synthetic */ void setClipRule(ForeignObjectView foreignObjectView, int i) {
            super.setClipRule((ForeignObjectManager) foreignObjectView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGForeignObjectManagerInterface
        @ReactProp(name = "display")
        public /* bridge */ /* synthetic */ void setDisplay(ForeignObjectView foreignObjectView, String str) {
            super.setDisplay((ForeignObjectManager) foreignObjectView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGForeignObjectManagerInterface
        public /* bridge */ /* synthetic */ void setFill(ForeignObjectView foreignObjectView, @Nullable ReadableMap readableMap) {
            super.setFill((ForeignObjectManager) foreignObjectView, readableMap);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGForeignObjectManagerInterface
        @ReactProp(defaultFloat = 1.0f, name = "fillOpacity")
        public /* bridge */ /* synthetic */ void setFillOpacity(ForeignObjectView foreignObjectView, float f) {
            super.setFillOpacity((ForeignObjectManager) foreignObjectView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGForeignObjectManagerInterface
        @ReactProp(defaultInt = 1, name = "fillRule")
        public /* bridge */ /* synthetic */ void setFillRule(ForeignObjectView foreignObjectView, int i) {
            super.setFillRule((ForeignObjectManager) foreignObjectView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGForeignObjectManagerInterface
        @ReactProp(name = "font")
        public /* bridge */ /* synthetic */ void setFont(ForeignObjectView foreignObjectView, @Nullable ReadableMap readableMap) {
            super.setFont((ForeignObjectManager) foreignObjectView, readableMap);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGForeignObjectManagerInterface
        public /* bridge */ /* synthetic */ void setFontSize(ForeignObjectView foreignObjectView, @Nullable Double d) {
            super.setFontSize((ForeignObjectManager) foreignObjectView, d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGForeignObjectManagerInterface
        public /* bridge */ /* synthetic */ void setFontSize(ForeignObjectView foreignObjectView, @Nullable String str) {
            super.setFontSize((ForeignObjectManager) foreignObjectView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGForeignObjectManagerInterface
        public /* bridge */ /* synthetic */ void setFontWeight(ForeignObjectView foreignObjectView, @Nullable Double d) {
            super.setFontWeight((ForeignObjectManager) foreignObjectView, d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGForeignObjectManagerInterface
        public /* bridge */ /* synthetic */ void setFontWeight(ForeignObjectView foreignObjectView, @Nullable String str) {
            super.setFontWeight((ForeignObjectManager) foreignObjectView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGForeignObjectManagerInterface
        @ReactProp(name = "markerEnd")
        public /* bridge */ /* synthetic */ void setMarkerEnd(ForeignObjectView foreignObjectView, String str) {
            super.setMarkerEnd((ForeignObjectManager) foreignObjectView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGForeignObjectManagerInterface
        @ReactProp(name = "markerMid")
        public /* bridge */ /* synthetic */ void setMarkerMid(ForeignObjectView foreignObjectView, String str) {
            super.setMarkerMid((ForeignObjectManager) foreignObjectView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGForeignObjectManagerInterface
        @ReactProp(name = "markerStart")
        public /* bridge */ /* synthetic */ void setMarkerStart(ForeignObjectView foreignObjectView, String str) {
            super.setMarkerStart((ForeignObjectManager) foreignObjectView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGForeignObjectManagerInterface
        @ReactProp(name = "mask")
        public /* bridge */ /* synthetic */ void setMask(ForeignObjectView foreignObjectView, String str) {
            super.setMask((ForeignObjectManager) foreignObjectView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGForeignObjectManagerInterface
        public /* bridge */ /* synthetic */ void setMatrix(ForeignObjectView foreignObjectView, @Nullable ReadableArray readableArray) {
            super.setMatrix((ForeignObjectManager) foreignObjectView, readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGForeignObjectManagerInterface
        @ReactProp(name = "name")
        public /* bridge */ /* synthetic */ void setName(ForeignObjectView foreignObjectView, String str) {
            super.setName((ForeignObjectManager) foreignObjectView, str);
        }

        @Override // com.facebook.react.uimanager.BaseViewManager, com.facebook.react.uimanager.BaseViewManagerInterface
        @ReactProp(defaultFloat = 1.0f, name = ViewProps.OPACITY)
        public /* bridge */ /* synthetic */ void setOpacity(@Nonnull View view, float f) {
            super.setOpacity((ForeignObjectManager) view, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGForeignObjectManagerInterface
        @ReactProp(name = ViewProps.POINTER_EVENTS)
        public /* bridge */ /* synthetic */ void setPointerEvents(ForeignObjectView foreignObjectView, @Nullable String str) {
            super.setPointerEvents((ForeignObjectManager) foreignObjectView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGForeignObjectManagerInterface
        @ReactProp(name = "propList")
        public /* bridge */ /* synthetic */ void setPropList(ForeignObjectView foreignObjectView, @Nullable ReadableArray readableArray) {
            super.setPropList((ForeignObjectManager) foreignObjectView, readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGForeignObjectManagerInterface
        @ReactProp(name = "responsible")
        public /* bridge */ /* synthetic */ void setResponsible(ForeignObjectView foreignObjectView, boolean z) {
            super.setResponsible((ForeignObjectManager) foreignObjectView, z);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGForeignObjectManagerInterface
        public /* bridge */ /* synthetic */ void setStroke(ForeignObjectView foreignObjectView, @Nullable ReadableMap readableMap) {
            super.setStroke((ForeignObjectManager) foreignObjectView, readableMap);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGForeignObjectManagerInterface
        @ReactProp(name = "strokeDasharray")
        public /* bridge */ /* synthetic */ void setStrokeDasharray(ForeignObjectView foreignObjectView, @Nullable ReadableArray readableArray) {
            super.setStrokeDasharray((ForeignObjectManager) foreignObjectView, readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGForeignObjectManagerInterface
        @ReactProp(name = "strokeDasharray")
        public /* bridge */ /* synthetic */ void setStrokeDasharray(ForeignObjectView foreignObjectView, @Nullable String str) {
            super.setStrokeDasharray((ForeignObjectManager) foreignObjectView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGForeignObjectManagerInterface
        @ReactProp(name = "strokeDashoffset")
        public /* bridge */ /* synthetic */ void setStrokeDashoffset(ForeignObjectView foreignObjectView, float f) {
            super.setStrokeDashoffset((ForeignObjectManager) foreignObjectView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGForeignObjectManagerInterface
        @ReactProp(defaultInt = 1, name = "strokeLinecap")
        public /* bridge */ /* synthetic */ void setStrokeLinecap(ForeignObjectView foreignObjectView, int i) {
            super.setStrokeLinecap((ForeignObjectManager) foreignObjectView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGForeignObjectManagerInterface
        @ReactProp(defaultInt = 1, name = "strokeLinejoin")
        public /* bridge */ /* synthetic */ void setStrokeLinejoin(ForeignObjectView foreignObjectView, int i) {
            super.setStrokeLinejoin((ForeignObjectManager) foreignObjectView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGForeignObjectManagerInterface
        @ReactProp(defaultFloat = 4.0f, name = "strokeMiterlimit")
        public /* bridge */ /* synthetic */ void setStrokeMiterlimit(ForeignObjectView foreignObjectView, float f) {
            super.setStrokeMiterlimit((ForeignObjectManager) foreignObjectView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGForeignObjectManagerInterface
        @ReactProp(defaultFloat = 1.0f, name = "strokeOpacity")
        public /* bridge */ /* synthetic */ void setStrokeOpacity(ForeignObjectView foreignObjectView, float f) {
            super.setStrokeOpacity((ForeignObjectManager) foreignObjectView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGForeignObjectManagerInterface
        public /* bridge */ /* synthetic */ void setStrokeWidth(ForeignObjectView foreignObjectView, @Nullable Double d) {
            super.setStrokeWidth((ForeignObjectManager) foreignObjectView, d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGForeignObjectManagerInterface
        public /* bridge */ /* synthetic */ void setStrokeWidth(ForeignObjectView foreignObjectView, @Nullable String str) {
            super.setStrokeWidth((ForeignObjectManager) foreignObjectView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGForeignObjectManagerInterface
        @ReactProp(name = "vectorEffect")
        public /* bridge */ /* synthetic */ void setVectorEffect(ForeignObjectView foreignObjectView, int i) {
            super.setVectorEffect((ForeignObjectManager) foreignObjectView, i);
        }

        ForeignObjectManager() {
            super(VirtualViewManager.SVGClass.RNSVGForeignObject);
            this.mDelegate = new RNSVGForeignObjectManagerDelegate(this);
        }

        @ReactProp(name = ViewHierarchyNode.JsonKeys.X)
        public void setX(ForeignObjectView foreignObjectView, Dynamic dynamic) {
            foreignObjectView.setX(dynamic);
        }

        @ReactProp(name = ViewHierarchyNode.JsonKeys.Y)
        public void setY(ForeignObjectView foreignObjectView, Dynamic dynamic) {
            foreignObjectView.setY(dynamic);
        }

        @ReactProp(name = "width")
        public void setWidth(ForeignObjectView foreignObjectView, Dynamic dynamic) {
            foreignObjectView.setWidth(dynamic);
        }

        @ReactProp(name = "height")
        public void setHeight(ForeignObjectView foreignObjectView, Dynamic dynamic) {
            foreignObjectView.setHeight(dynamic);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGForeignObjectManagerInterface
        public void setX(ForeignObjectView foreignObjectView, @Nullable String str) {
            foreignObjectView.setX(str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGForeignObjectManagerInterface
        public void setY(ForeignObjectView foreignObjectView, @Nullable String str) {
            foreignObjectView.setY(str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGForeignObjectManagerInterface
        public void setHeight(ForeignObjectView foreignObjectView, @Nullable String str) {
            foreignObjectView.setHeight(str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGForeignObjectManagerInterface
        public void setWidth(ForeignObjectView foreignObjectView, @Nullable String str) {
            foreignObjectView.setWidth(str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGForeignObjectManagerInterface
        public void setX(ForeignObjectView foreignObjectView, @Nullable Double d) {
            foreignObjectView.setX(d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGForeignObjectManagerInterface
        public void setY(ForeignObjectView foreignObjectView, @Nullable Double d) {
            foreignObjectView.setY(d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGForeignObjectManagerInterface
        public void setHeight(ForeignObjectView foreignObjectView, @Nullable Double d) {
            foreignObjectView.setHeight(d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGForeignObjectManagerInterface
        public void setWidth(ForeignObjectView foreignObjectView, @Nullable Double d) {
            foreignObjectView.setWidth(d);
        }
    }

    static class MarkerManager extends GroupViewManagerAbstract<MarkerView> implements RNSVGMarkerManagerInterface<MarkerView> {
        public static final String REACT_CLASS = "RNSVGMarker";

        @Override // com.facebook.react.viewmanagers.RNSVGMarkerManagerInterface
        @ReactProp(name = "clipPath")
        public /* bridge */ /* synthetic */ void setClipPath(MarkerView markerView, String str) {
            super.setClipPath((MarkerManager) markerView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMarkerManagerInterface
        @ReactProp(name = "clipRule")
        public /* bridge */ /* synthetic */ void setClipRule(MarkerView markerView, int i) {
            super.setClipRule((MarkerManager) markerView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMarkerManagerInterface
        @ReactProp(name = "display")
        public /* bridge */ /* synthetic */ void setDisplay(MarkerView markerView, String str) {
            super.setDisplay((MarkerManager) markerView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMarkerManagerInterface
        public /* bridge */ /* synthetic */ void setFill(MarkerView markerView, @Nullable ReadableMap readableMap) {
            super.setFill((MarkerManager) markerView, readableMap);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMarkerManagerInterface
        @ReactProp(defaultFloat = 1.0f, name = "fillOpacity")
        public /* bridge */ /* synthetic */ void setFillOpacity(MarkerView markerView, float f) {
            super.setFillOpacity((MarkerManager) markerView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMarkerManagerInterface
        @ReactProp(defaultInt = 1, name = "fillRule")
        public /* bridge */ /* synthetic */ void setFillRule(MarkerView markerView, int i) {
            super.setFillRule((MarkerManager) markerView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMarkerManagerInterface
        @ReactProp(name = "font")
        public /* bridge */ /* synthetic */ void setFont(MarkerView markerView, @Nullable ReadableMap readableMap) {
            super.setFont((MarkerManager) markerView, readableMap);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMarkerManagerInterface
        public /* bridge */ /* synthetic */ void setFontSize(MarkerView markerView, @Nullable Double d) {
            super.setFontSize((MarkerManager) markerView, d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMarkerManagerInterface
        public /* bridge */ /* synthetic */ void setFontSize(MarkerView markerView, @Nullable String str) {
            super.setFontSize((MarkerManager) markerView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMarkerManagerInterface
        public /* bridge */ /* synthetic */ void setFontWeight(MarkerView markerView, @Nullable Double d) {
            super.setFontWeight((MarkerManager) markerView, d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMarkerManagerInterface
        public /* bridge */ /* synthetic */ void setFontWeight(MarkerView markerView, @Nullable String str) {
            super.setFontWeight((MarkerManager) markerView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMarkerManagerInterface
        @ReactProp(name = "markerEnd")
        public /* bridge */ /* synthetic */ void setMarkerEnd(MarkerView markerView, String str) {
            super.setMarkerEnd((MarkerManager) markerView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMarkerManagerInterface
        @ReactProp(name = "markerMid")
        public /* bridge */ /* synthetic */ void setMarkerMid(MarkerView markerView, String str) {
            super.setMarkerMid((MarkerManager) markerView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMarkerManagerInterface
        @ReactProp(name = "markerStart")
        public /* bridge */ /* synthetic */ void setMarkerStart(MarkerView markerView, String str) {
            super.setMarkerStart((MarkerManager) markerView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMarkerManagerInterface
        @ReactProp(name = "mask")
        public /* bridge */ /* synthetic */ void setMask(MarkerView markerView, String str) {
            super.setMask((MarkerManager) markerView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMarkerManagerInterface
        public /* bridge */ /* synthetic */ void setMatrix(MarkerView markerView, @Nullable ReadableArray readableArray) {
            super.setMatrix((MarkerManager) markerView, readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMarkerManagerInterface
        @ReactProp(name = "name")
        public /* bridge */ /* synthetic */ void setName(MarkerView markerView, String str) {
            super.setName((MarkerManager) markerView, str);
        }

        @Override // com.facebook.react.uimanager.BaseViewManager, com.facebook.react.uimanager.BaseViewManagerInterface
        @ReactProp(defaultFloat = 1.0f, name = ViewProps.OPACITY)
        public /* bridge */ /* synthetic */ void setOpacity(@Nonnull View view, float f) {
            super.setOpacity((MarkerManager) view, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMarkerManagerInterface
        @ReactProp(name = ViewProps.POINTER_EVENTS)
        public /* bridge */ /* synthetic */ void setPointerEvents(MarkerView markerView, @Nullable String str) {
            super.setPointerEvents((MarkerManager) markerView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMarkerManagerInterface
        @ReactProp(name = "propList")
        public /* bridge */ /* synthetic */ void setPropList(MarkerView markerView, @Nullable ReadableArray readableArray) {
            super.setPropList((MarkerManager) markerView, readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMarkerManagerInterface
        @ReactProp(name = "responsible")
        public /* bridge */ /* synthetic */ void setResponsible(MarkerView markerView, boolean z) {
            super.setResponsible((MarkerManager) markerView, z);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMarkerManagerInterface
        public /* bridge */ /* synthetic */ void setStroke(MarkerView markerView, @Nullable ReadableMap readableMap) {
            super.setStroke((MarkerManager) markerView, readableMap);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMarkerManagerInterface
        @ReactProp(name = "strokeDasharray")
        public /* bridge */ /* synthetic */ void setStrokeDasharray(MarkerView markerView, @Nullable ReadableArray readableArray) {
            super.setStrokeDasharray((MarkerManager) markerView, readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMarkerManagerInterface
        @ReactProp(name = "strokeDasharray")
        public /* bridge */ /* synthetic */ void setStrokeDasharray(MarkerView markerView, @Nullable String str) {
            super.setStrokeDasharray((MarkerManager) markerView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMarkerManagerInterface
        @ReactProp(name = "strokeDashoffset")
        public /* bridge */ /* synthetic */ void setStrokeDashoffset(MarkerView markerView, float f) {
            super.setStrokeDashoffset((MarkerManager) markerView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMarkerManagerInterface
        @ReactProp(defaultInt = 1, name = "strokeLinecap")
        public /* bridge */ /* synthetic */ void setStrokeLinecap(MarkerView markerView, int i) {
            super.setStrokeLinecap((MarkerManager) markerView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMarkerManagerInterface
        @ReactProp(defaultInt = 1, name = "strokeLinejoin")
        public /* bridge */ /* synthetic */ void setStrokeLinejoin(MarkerView markerView, int i) {
            super.setStrokeLinejoin((MarkerManager) markerView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMarkerManagerInterface
        @ReactProp(defaultFloat = 4.0f, name = "strokeMiterlimit")
        public /* bridge */ /* synthetic */ void setStrokeMiterlimit(MarkerView markerView, float f) {
            super.setStrokeMiterlimit((MarkerManager) markerView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMarkerManagerInterface
        @ReactProp(defaultFloat = 1.0f, name = "strokeOpacity")
        public /* bridge */ /* synthetic */ void setStrokeOpacity(MarkerView markerView, float f) {
            super.setStrokeOpacity((MarkerManager) markerView, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMarkerManagerInterface
        public /* bridge */ /* synthetic */ void setStrokeWidth(MarkerView markerView, @Nullable Double d) {
            super.setStrokeWidth((MarkerManager) markerView, d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMarkerManagerInterface
        public /* bridge */ /* synthetic */ void setStrokeWidth(MarkerView markerView, @Nullable String str) {
            super.setStrokeWidth((MarkerManager) markerView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMarkerManagerInterface
        @ReactProp(name = "vectorEffect")
        public /* bridge */ /* synthetic */ void setVectorEffect(MarkerView markerView, int i) {
            super.setVectorEffect((MarkerManager) markerView, i);
        }

        MarkerManager() {
            super(VirtualViewManager.SVGClass.RNSVGMarker);
            this.mDelegate = new RNSVGMarkerManagerDelegate(this);
        }

        @ReactProp(name = "refX")
        public void setRefX(MarkerView markerView, Dynamic dynamic) {
            markerView.setRefX(dynamic);
        }

        @ReactProp(name = "refY")
        public void setRefY(MarkerView markerView, Dynamic dynamic) {
            markerView.setRefY(dynamic);
        }

        @ReactProp(name = "markerWidth")
        public void setMarkerWidth(MarkerView markerView, Dynamic dynamic) {
            markerView.setMarkerWidth(dynamic);
        }

        @ReactProp(name = "markerHeight")
        public void setMarkerHeight(MarkerView markerView, Dynamic dynamic) {
            markerView.setMarkerHeight(dynamic);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMarkerManagerInterface
        public void setRefX(MarkerView markerView, @Nullable String str) {
            markerView.setRefX(str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMarkerManagerInterface
        public void setRefY(MarkerView markerView, @Nullable String str) {
            markerView.setRefY(str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMarkerManagerInterface
        public void setMarkerHeight(MarkerView markerView, @Nullable String str) {
            markerView.setMarkerHeight(str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMarkerManagerInterface
        public void setMarkerWidth(MarkerView markerView, @Nullable String str) {
            markerView.setMarkerWidth(str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMarkerManagerInterface
        public void setRefX(MarkerView markerView, @Nullable Double d) {
            markerView.setRefX(d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMarkerManagerInterface
        public void setRefY(MarkerView markerView, @Nullable Double d) {
            markerView.setRefY(d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMarkerManagerInterface
        public void setMarkerHeight(MarkerView markerView, @Nullable Double d) {
            markerView.setMarkerHeight(d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMarkerManagerInterface
        public void setMarkerWidth(MarkerView markerView, @Nullable Double d) {
            markerView.setMarkerWidth(d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMarkerManagerInterface
        @ReactProp(name = "markerUnits")
        public void setMarkerUnits(MarkerView markerView, String str) {
            markerView.setMarkerUnits(str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMarkerManagerInterface
        @ReactProp(name = "orient")
        public void setOrient(MarkerView markerView, String str) {
            markerView.setOrient(str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMarkerManagerInterface
        @ReactProp(name = "minX")
        public void setMinX(MarkerView markerView, float f) {
            markerView.setMinX(f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMarkerManagerInterface
        @ReactProp(name = "minY")
        public void setMinY(MarkerView markerView, float f) {
            markerView.setMinY(f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMarkerManagerInterface
        @ReactProp(name = "vbWidth")
        public void setVbWidth(MarkerView markerView, float f) {
            markerView.setVbWidth(f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMarkerManagerInterface
        @ReactProp(name = "vbHeight")
        public void setVbHeight(MarkerView markerView, float f) {
            markerView.setVbHeight(f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMarkerManagerInterface
        @ReactProp(name = "align")
        public void setAlign(MarkerView markerView, String str) {
            markerView.setAlign(str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGMarkerManagerInterface
        @ReactProp(name = "meetOrSlice")
        public void setMeetOrSlice(MarkerView markerView, int i) {
            markerView.setMeetOrSlice(i);
        }
    }

    static class LinearGradientManager extends VirtualViewManager<LinearGradientView> implements RNSVGLinearGradientManagerInterface<LinearGradientView> {
        public static final String REACT_CLASS = "RNSVGLinearGradient";

        @Override // com.facebook.react.viewmanagers.RNSVGLinearGradientManagerInterface
        @ReactProp(name = "clipPath")
        public /* bridge */ /* synthetic */ void setClipPath(LinearGradientView linearGradientView, String str) {
            super.setClipPath((LinearGradientManager) linearGradientView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGLinearGradientManagerInterface
        @ReactProp(name = "clipRule")
        public /* bridge */ /* synthetic */ void setClipRule(LinearGradientView linearGradientView, int i) {
            super.setClipRule((LinearGradientManager) linearGradientView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGLinearGradientManagerInterface
        @ReactProp(name = "display")
        public /* bridge */ /* synthetic */ void setDisplay(LinearGradientView linearGradientView, String str) {
            super.setDisplay((LinearGradientManager) linearGradientView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGLinearGradientManagerInterface
        @ReactProp(name = "markerEnd")
        public /* bridge */ /* synthetic */ void setMarkerEnd(LinearGradientView linearGradientView, String str) {
            super.setMarkerEnd((LinearGradientManager) linearGradientView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGLinearGradientManagerInterface
        @ReactProp(name = "markerMid")
        public /* bridge */ /* synthetic */ void setMarkerMid(LinearGradientView linearGradientView, String str) {
            super.setMarkerMid((LinearGradientManager) linearGradientView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGLinearGradientManagerInterface
        @ReactProp(name = "markerStart")
        public /* bridge */ /* synthetic */ void setMarkerStart(LinearGradientView linearGradientView, String str) {
            super.setMarkerStart((LinearGradientManager) linearGradientView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGLinearGradientManagerInterface
        @ReactProp(name = "mask")
        public /* bridge */ /* synthetic */ void setMask(LinearGradientView linearGradientView, String str) {
            super.setMask((LinearGradientManager) linearGradientView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGLinearGradientManagerInterface
        public /* bridge */ /* synthetic */ void setMatrix(LinearGradientView linearGradientView, @Nullable ReadableArray readableArray) {
            super.setMatrix((LinearGradientManager) linearGradientView, readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGLinearGradientManagerInterface
        @ReactProp(name = "name")
        public /* bridge */ /* synthetic */ void setName(LinearGradientView linearGradientView, String str) {
            super.setName((LinearGradientManager) linearGradientView, str);
        }

        @Override // com.facebook.react.uimanager.BaseViewManager, com.facebook.react.uimanager.BaseViewManagerInterface
        @ReactProp(defaultFloat = 1.0f, name = ViewProps.OPACITY)
        public /* bridge */ /* synthetic */ void setOpacity(@Nonnull View view, float f) {
            super.setOpacity((LinearGradientManager) view, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGLinearGradientManagerInterface
        @ReactProp(name = ViewProps.POINTER_EVENTS)
        public /* bridge */ /* synthetic */ void setPointerEvents(LinearGradientView linearGradientView, @Nullable String str) {
            super.setPointerEvents((LinearGradientManager) linearGradientView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGLinearGradientManagerInterface
        @ReactProp(name = "responsible")
        public /* bridge */ /* synthetic */ void setResponsible(LinearGradientView linearGradientView, boolean z) {
            super.setResponsible((LinearGradientManager) linearGradientView, z);
        }

        LinearGradientManager() {
            super(VirtualViewManager.SVGClass.RNSVGLinearGradient);
            this.mDelegate = new RNSVGLinearGradientManagerDelegate(this);
        }

        @ReactProp(name = "x1")
        public void setX1(LinearGradientView linearGradientView, Dynamic dynamic) {
            linearGradientView.setX1(dynamic);
        }

        @ReactProp(name = "y1")
        public void setY1(LinearGradientView linearGradientView, Dynamic dynamic) {
            linearGradientView.setY1(dynamic);
        }

        @ReactProp(name = "x2")
        public void setX2(LinearGradientView linearGradientView, Dynamic dynamic) {
            linearGradientView.setX2(dynamic);
        }

        @ReactProp(name = "y2")
        public void setY2(LinearGradientView linearGradientView, Dynamic dynamic) {
            linearGradientView.setY2(dynamic);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGLinearGradientManagerInterface
        public void setX1(LinearGradientView linearGradientView, @Nullable String str) {
            linearGradientView.setX1(str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGLinearGradientManagerInterface
        public void setY1(LinearGradientView linearGradientView, @Nullable String str) {
            linearGradientView.setY1(str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGLinearGradientManagerInterface
        public void setX2(LinearGradientView linearGradientView, @Nullable String str) {
            linearGradientView.setX2(str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGLinearGradientManagerInterface
        public void setY2(LinearGradientView linearGradientView, @Nullable String str) {
            linearGradientView.setY2(str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGLinearGradientManagerInterface
        public void setX1(LinearGradientView linearGradientView, @Nullable Double d) {
            linearGradientView.setX1(d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGLinearGradientManagerInterface
        public void setY1(LinearGradientView linearGradientView, @Nullable Double d) {
            linearGradientView.setY1(d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGLinearGradientManagerInterface
        public void setX2(LinearGradientView linearGradientView, @Nullable Double d) {
            linearGradientView.setX2(d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGLinearGradientManagerInterface
        public void setY2(LinearGradientView linearGradientView, @Nullable Double d) {
            linearGradientView.setY2(d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGLinearGradientManagerInterface
        @ReactProp(name = "gradient")
        public void setGradient(LinearGradientView linearGradientView, ReadableArray readableArray) {
            linearGradientView.setGradient(readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGLinearGradientManagerInterface
        @ReactProp(name = "gradientUnits")
        public void setGradientUnits(LinearGradientView linearGradientView, int i) {
            linearGradientView.setGradientUnits(i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGLinearGradientManagerInterface
        @ReactProp(name = "gradientTransform")
        public void setGradientTransform(LinearGradientView linearGradientView, @Nullable ReadableArray readableArray) {
            linearGradientView.setGradientTransform(readableArray);
        }
    }

    static class RadialGradientManager extends VirtualViewManager<RadialGradientView> implements RNSVGRadialGradientManagerInterface<RadialGradientView> {
        public static final String REACT_CLASS = "RNSVGRadialGradient";

        @Override // com.facebook.react.viewmanagers.RNSVGRadialGradientManagerInterface
        @ReactProp(name = "clipPath")
        public /* bridge */ /* synthetic */ void setClipPath(RadialGradientView radialGradientView, String str) {
            super.setClipPath((RadialGradientManager) radialGradientView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGRadialGradientManagerInterface
        @ReactProp(name = "clipRule")
        public /* bridge */ /* synthetic */ void setClipRule(RadialGradientView radialGradientView, int i) {
            super.setClipRule((RadialGradientManager) radialGradientView, i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGRadialGradientManagerInterface
        @ReactProp(name = "display")
        public /* bridge */ /* synthetic */ void setDisplay(RadialGradientView radialGradientView, String str) {
            super.setDisplay((RadialGradientManager) radialGradientView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGRadialGradientManagerInterface
        @ReactProp(name = "markerEnd")
        public /* bridge */ /* synthetic */ void setMarkerEnd(RadialGradientView radialGradientView, String str) {
            super.setMarkerEnd((RadialGradientManager) radialGradientView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGRadialGradientManagerInterface
        @ReactProp(name = "markerMid")
        public /* bridge */ /* synthetic */ void setMarkerMid(RadialGradientView radialGradientView, String str) {
            super.setMarkerMid((RadialGradientManager) radialGradientView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGRadialGradientManagerInterface
        @ReactProp(name = "markerStart")
        public /* bridge */ /* synthetic */ void setMarkerStart(RadialGradientView radialGradientView, String str) {
            super.setMarkerStart((RadialGradientManager) radialGradientView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGRadialGradientManagerInterface
        @ReactProp(name = "mask")
        public /* bridge */ /* synthetic */ void setMask(RadialGradientView radialGradientView, String str) {
            super.setMask((RadialGradientManager) radialGradientView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGRadialGradientManagerInterface
        public /* bridge */ /* synthetic */ void setMatrix(RadialGradientView radialGradientView, @Nullable ReadableArray readableArray) {
            super.setMatrix((RadialGradientManager) radialGradientView, readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGRadialGradientManagerInterface
        @ReactProp(name = "name")
        public /* bridge */ /* synthetic */ void setName(RadialGradientView radialGradientView, String str) {
            super.setName((RadialGradientManager) radialGradientView, str);
        }

        @Override // com.facebook.react.uimanager.BaseViewManager, com.facebook.react.uimanager.BaseViewManagerInterface
        @ReactProp(defaultFloat = 1.0f, name = ViewProps.OPACITY)
        public /* bridge */ /* synthetic */ void setOpacity(@Nonnull View view, float f) {
            super.setOpacity((RadialGradientManager) view, f);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGRadialGradientManagerInterface
        @ReactProp(name = ViewProps.POINTER_EVENTS)
        public /* bridge */ /* synthetic */ void setPointerEvents(RadialGradientView radialGradientView, @Nullable String str) {
            super.setPointerEvents((RadialGradientManager) radialGradientView, str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGRadialGradientManagerInterface
        @ReactProp(name = "responsible")
        public /* bridge */ /* synthetic */ void setResponsible(RadialGradientView radialGradientView, boolean z) {
            super.setResponsible((RadialGradientManager) radialGradientView, z);
        }

        RadialGradientManager() {
            super(VirtualViewManager.SVGClass.RNSVGRadialGradient);
            this.mDelegate = new RNSVGRadialGradientManagerDelegate(this);
        }

        @ReactProp(name = "fx")
        public void setFx(RadialGradientView radialGradientView, Dynamic dynamic) {
            radialGradientView.setFx(dynamic);
        }

        @ReactProp(name = "fy")
        public void setFy(RadialGradientView radialGradientView, Dynamic dynamic) {
            radialGradientView.setFy(dynamic);
        }

        @ReactProp(name = "rx")
        public void setRx(RadialGradientView radialGradientView, Dynamic dynamic) {
            radialGradientView.setRx(dynamic);
        }

        @ReactProp(name = "ry")
        public void setRy(RadialGradientView radialGradientView, Dynamic dynamic) {
            radialGradientView.setRy(dynamic);
        }

        @ReactProp(name = "cx")
        public void setCx(RadialGradientView radialGradientView, Dynamic dynamic) {
            radialGradientView.setCx(dynamic);
        }

        @ReactProp(name = "cy")
        public void setCy(RadialGradientView radialGradientView, Dynamic dynamic) {
            radialGradientView.setCy(dynamic);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGRadialGradientManagerInterface
        public void setFx(RadialGradientView radialGradientView, @Nullable String str) {
            radialGradientView.setFx(str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGRadialGradientManagerInterface
        public void setFy(RadialGradientView radialGradientView, @Nullable String str) {
            radialGradientView.setFy(str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGRadialGradientManagerInterface
        public void setCx(RadialGradientView radialGradientView, @Nullable String str) {
            radialGradientView.setCx(str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGRadialGradientManagerInterface
        public void setCy(RadialGradientView radialGradientView, @Nullable String str) {
            radialGradientView.setCy(str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGRadialGradientManagerInterface
        public void setRx(RadialGradientView radialGradientView, @Nullable String str) {
            radialGradientView.setRx(str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGRadialGradientManagerInterface
        public void setRy(RadialGradientView radialGradientView, @Nullable String str) {
            radialGradientView.setRy(str);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGRadialGradientManagerInterface
        public void setFx(RadialGradientView radialGradientView, @Nullable Double d) {
            radialGradientView.setFx(d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGRadialGradientManagerInterface
        public void setFy(RadialGradientView radialGradientView, @Nullable Double d) {
            radialGradientView.setFy(d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGRadialGradientManagerInterface
        public void setCx(RadialGradientView radialGradientView, @Nullable Double d) {
            radialGradientView.setCx(d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGRadialGradientManagerInterface
        public void setCy(RadialGradientView radialGradientView, @Nullable Double d) {
            radialGradientView.setCy(d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGRadialGradientManagerInterface
        public void setRx(RadialGradientView radialGradientView, @Nullable Double d) {
            radialGradientView.setRx(d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGRadialGradientManagerInterface
        public void setRy(RadialGradientView radialGradientView, @Nullable Double d) {
            radialGradientView.setRy(d);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGRadialGradientManagerInterface
        @ReactProp(name = "gradient")
        public void setGradient(RadialGradientView radialGradientView, ReadableArray readableArray) {
            radialGradientView.setGradient(readableArray);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGRadialGradientManagerInterface
        @ReactProp(name = "gradientUnits")
        public void setGradientUnits(RadialGradientView radialGradientView, int i) {
            radialGradientView.setGradientUnits(i);
        }

        @Override // com.facebook.react.viewmanagers.RNSVGRadialGradientManagerInterface
        @ReactProp(name = "gradientTransform")
        public void setGradientTransform(RadialGradientView radialGradientView, @Nullable ReadableArray readableArray) {
            radialGradientView.setGradientTransform(readableArray);
        }
    }

    @ReactProp(name = "fill")
    public void setFill(T t, @Nullable Dynamic dynamic) {
        t.setFill(dynamic);
    }

    public void setFill(T t, @Nullable ReadableMap readableMap) {
        t.setFill(readableMap);
    }

    @ReactProp(defaultFloat = 1.0f, name = "fillOpacity")
    public void setFillOpacity(T t, float f) {
        t.setFillOpacity(f);
    }

    @ReactProp(defaultInt = 1, name = "fillRule")
    public void setFillRule(T t, int i) {
        t.setFillRule(i);
    }

    @ReactProp(name = "stroke")
    public void setStroke(T t, @Nullable Dynamic dynamic) {
        t.setStroke(dynamic);
    }

    public void setStroke(T t, @Nullable ReadableMap readableMap) {
        t.setStroke(readableMap);
    }

    @ReactProp(defaultFloat = 1.0f, name = "strokeOpacity")
    public void setStrokeOpacity(T t, float f) {
        t.setStrokeOpacity(f);
    }

    @ReactProp(name = "strokeDasharray")
    public void setStrokeDasharray(T t, @Nullable ReadableArray readableArray) {
        t.setStrokeDasharray(readableArray);
    }

    @ReactProp(name = "strokeDasharray")
    public void setStrokeDasharray(T t, @Nullable String str) {
        t.setStrokeDasharray(str);
    }

    @ReactProp(name = "strokeDashoffset")
    public void setStrokeDashoffset(T t, float f) {
        t.setStrokeDashoffset(f);
    }

    @ReactProp(name = "strokeWidth")
    public void setStrokeWidth(T t, Dynamic dynamic) {
        t.setStrokeWidth(dynamic);
    }

    public void setStrokeWidth(T t, @Nullable String str) {
        t.setStrokeWidth(str);
    }

    public void setStrokeWidth(T t, @Nullable Double d) {
        t.setStrokeWidth(d);
    }

    @ReactProp(defaultFloat = 4.0f, name = "strokeMiterlimit")
    public void setStrokeMiterlimit(T t, float f) {
        t.setStrokeMiterlimit(f);
    }

    @ReactProp(defaultInt = 1, name = "strokeLinecap")
    public void setStrokeLinecap(T t, int i) {
        t.setStrokeLinecap(i);
    }

    @ReactProp(defaultInt = 1, name = "strokeLinejoin")
    public void setStrokeLinejoin(T t, int i) {
        t.setStrokeLinejoin(i);
    }

    @ReactProp(name = "vectorEffect")
    public void setVectorEffect(T t, int i) {
        t.setVectorEffect(i);
    }

    @ReactProp(name = "propList")
    public void setPropList(T t, @Nullable ReadableArray readableArray) {
        t.setPropList(readableArray);
    }
}

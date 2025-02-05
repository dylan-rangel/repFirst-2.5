package com.swmansion.reanimated.nodes;

import android.graphics.PointF;
import com.facebook.react.bridge.ReadableMap;
import com.swmansion.reanimated.MapUtils;
import com.swmansion.reanimated.NodesManager;

/* loaded from: classes.dex */
public class BezierNode extends Node {
    private final int mInputID;
    private final CubicBezierInterpolator mInterpolator;

    private static class CubicBezierInterpolator {
        protected PointF a;
        protected PointF b;
        protected PointF c;
        protected PointF end;
        protected PointF start;

        public CubicBezierInterpolator(PointF pointF, PointF pointF2) {
            this.a = new PointF();
            this.b = new PointF();
            this.c = new PointF();
            this.start = pointF;
            this.end = pointF2;
        }

        public CubicBezierInterpolator(float f, float f2, float f3, float f4) {
            this(new PointF(f, f2), new PointF(f3, f4));
        }

        public float getInterpolation(float f) {
            return getBezierCoordinateY(getXForTime(f));
        }

        protected float getBezierCoordinateY(float f) {
            this.c.y = this.start.y * 3.0f;
            this.b.y = ((this.end.y - this.start.y) * 3.0f) - this.c.y;
            this.a.y = (1.0f - this.c.y) - this.b.y;
            return f * (this.c.y + ((this.b.y + (this.a.y * f)) * f));
        }

        protected float getXForTime(float f) {
            float f2 = f;
            for (int i = 1; i < 14; i++) {
                float bezierCoordinateX = getBezierCoordinateX(f2) - f;
                if (Math.abs(bezierCoordinateX) < 0.001d) {
                    break;
                }
                f2 -= bezierCoordinateX / getXDerivate(f2);
            }
            return f2;
        }

        private float getXDerivate(float f) {
            return this.c.x + (f * ((this.b.x * 2.0f) + (this.a.x * 3.0f * f)));
        }

        private float getBezierCoordinateX(float f) {
            this.c.x = this.start.x * 3.0f;
            this.b.x = ((this.end.x - this.start.x) * 3.0f) - this.c.x;
            this.a.x = (1.0f - this.c.x) - this.b.x;
            return f * (this.c.x + ((this.b.x + (this.a.x * f)) * f));
        }
    }

    public BezierNode(int i, ReadableMap readableMap, NodesManager nodesManager) {
        super(i, readableMap, nodesManager);
        this.mInputID = MapUtils.getInt(readableMap, "input", "Reanimated: Argument passed to bezier node is either of wrong type or is missing.");
        this.mInterpolator = new CubicBezierInterpolator((float) readableMap.getDouble("mX1"), (float) readableMap.getDouble("mY1"), (float) readableMap.getDouble("mX2"), (float) readableMap.getDouble("mY2"));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.swmansion.reanimated.nodes.Node
    public Double evaluate() {
        return Double.valueOf(this.mInterpolator.getInterpolation(((Double) this.mNodesManager.getNodeValue(this.mInputID)).floatValue()));
    }
}

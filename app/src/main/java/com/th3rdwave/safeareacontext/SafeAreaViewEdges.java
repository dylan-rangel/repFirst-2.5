package com.th3rdwave.safeareacontext;

import com.facebook.react.uimanager.ViewProps;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SafeAreaViewEdges.kt */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003¢\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J1\u0010\u0011\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001R\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t¨\u0006\u0019"}, d2 = {"Lcom/th3rdwave/safeareacontext/SafeAreaViewEdges;", "", ViewProps.TOP, "Lcom/th3rdwave/safeareacontext/SafeAreaViewEdgeModes;", ViewProps.RIGHT, ViewProps.BOTTOM, ViewProps.LEFT, "(Lcom/th3rdwave/safeareacontext/SafeAreaViewEdgeModes;Lcom/th3rdwave/safeareacontext/SafeAreaViewEdgeModes;Lcom/th3rdwave/safeareacontext/SafeAreaViewEdgeModes;Lcom/th3rdwave/safeareacontext/SafeAreaViewEdgeModes;)V", "getBottom", "()Lcom/th3rdwave/safeareacontext/SafeAreaViewEdgeModes;", "getLeft", "getRight", "getTop", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "", "toString", "", "react-native-safe-area-context_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes.dex */
public final /* data */ class SafeAreaViewEdges {
    private final SafeAreaViewEdgeModes bottom;
    private final SafeAreaViewEdgeModes left;
    private final SafeAreaViewEdgeModes right;
    private final SafeAreaViewEdgeModes top;

    public static /* synthetic */ SafeAreaViewEdges copy$default(SafeAreaViewEdges safeAreaViewEdges, SafeAreaViewEdgeModes safeAreaViewEdgeModes, SafeAreaViewEdgeModes safeAreaViewEdgeModes2, SafeAreaViewEdgeModes safeAreaViewEdgeModes3, SafeAreaViewEdgeModes safeAreaViewEdgeModes4, int i, Object obj) {
        if ((i & 1) != 0) {
            safeAreaViewEdgeModes = safeAreaViewEdges.top;
        }
        if ((i & 2) != 0) {
            safeAreaViewEdgeModes2 = safeAreaViewEdges.right;
        }
        if ((i & 4) != 0) {
            safeAreaViewEdgeModes3 = safeAreaViewEdges.bottom;
        }
        if ((i & 8) != 0) {
            safeAreaViewEdgeModes4 = safeAreaViewEdges.left;
        }
        return safeAreaViewEdges.copy(safeAreaViewEdgeModes, safeAreaViewEdgeModes2, safeAreaViewEdgeModes3, safeAreaViewEdgeModes4);
    }

    /* renamed from: component1, reason: from getter */
    public final SafeAreaViewEdgeModes getTop() {
        return this.top;
    }

    /* renamed from: component2, reason: from getter */
    public final SafeAreaViewEdgeModes getRight() {
        return this.right;
    }

    /* renamed from: component3, reason: from getter */
    public final SafeAreaViewEdgeModes getBottom() {
        return this.bottom;
    }

    /* renamed from: component4, reason: from getter */
    public final SafeAreaViewEdgeModes getLeft() {
        return this.left;
    }

    public final SafeAreaViewEdges copy(SafeAreaViewEdgeModes top, SafeAreaViewEdgeModes right, SafeAreaViewEdgeModes bottom, SafeAreaViewEdgeModes left) {
        Intrinsics.checkNotNullParameter(top, "top");
        Intrinsics.checkNotNullParameter(right, "right");
        Intrinsics.checkNotNullParameter(bottom, "bottom");
        Intrinsics.checkNotNullParameter(left, "left");
        return new SafeAreaViewEdges(top, right, bottom, left);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SafeAreaViewEdges)) {
            return false;
        }
        SafeAreaViewEdges safeAreaViewEdges = (SafeAreaViewEdges) other;
        return this.top == safeAreaViewEdges.top && this.right == safeAreaViewEdges.right && this.bottom == safeAreaViewEdges.bottom && this.left == safeAreaViewEdges.left;
    }

    public int hashCode() {
        return (((((this.top.hashCode() * 31) + this.right.hashCode()) * 31) + this.bottom.hashCode()) * 31) + this.left.hashCode();
    }

    public String toString() {
        return "SafeAreaViewEdges(top=" + this.top + ", right=" + this.right + ", bottom=" + this.bottom + ", left=" + this.left + ')';
    }

    public SafeAreaViewEdges(SafeAreaViewEdgeModes top, SafeAreaViewEdgeModes right, SafeAreaViewEdgeModes bottom, SafeAreaViewEdgeModes left) {
        Intrinsics.checkNotNullParameter(top, "top");
        Intrinsics.checkNotNullParameter(right, "right");
        Intrinsics.checkNotNullParameter(bottom, "bottom");
        Intrinsics.checkNotNullParameter(left, "left");
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.left = left;
    }

    public final SafeAreaViewEdgeModes getTop() {
        return this.top;
    }

    public final SafeAreaViewEdgeModes getRight() {
        return this.right;
    }

    public final SafeAreaViewEdgeModes getBottom() {
        return this.bottom;
    }

    public final SafeAreaViewEdgeModes getLeft() {
        return this.left;
    }
}

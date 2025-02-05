package com.th3rdwave.safeareacontext;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.module.annotations.ReactModule;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SafeAreaContextModule.kt */
@ReactModule(name = SafeAreaContextModule.NAME)
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0010\u0000\n\u0002\b\u0004\b\u0007\u0018\u0000 \u000b2\u00020\u0001:\u0001\u000bB\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\u0016\u0010\u0005\u001a\u0010\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b\u0018\u00010\u0006H\u0002J\b\u0010\t\u001a\u00020\u0007H\u0016J\u0014\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006H\u0016¨\u0006\f"}, d2 = {"Lcom/th3rdwave/safeareacontext/SafeAreaContextModule;", "Lcom/th3rdwave/safeareacontext/NativeSafeAreaContextSpec;", "reactContext", "Lcom/facebook/react/bridge/ReactApplicationContext;", "(Lcom/facebook/react/bridge/ReactApplicationContext;)V", "getInitialWindowMetrics", "", "", "", "getName", "getTypedExportedConstants", "Companion", "react-native-safe-area-context_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes.dex */
public final class SafeAreaContextModule extends NativeSafeAreaContextSpec {
    public static final String NAME = "RNCSafeAreaContext";

    @Override // com.facebook.react.bridge.NativeModule
    public String getName() {
        return NAME;
    }

    public SafeAreaContextModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
    }

    @Override // com.th3rdwave.safeareacontext.NativeSafeAreaContextSpec
    public Map<String, Object> getTypedExportedConstants() {
        Map<String, Object> of = MapBuilder.of("initialWindowMetrics", getInitialWindowMetrics());
        Intrinsics.checkNotNullExpressionValue(of, "of<String, Any>(\"initial…etInitialWindowMetrics())");
        return of;
    }

    private final Map<String, Object> getInitialWindowMetrics() {
        Window window;
        Activity currentActivity = getReactApplicationContext().getCurrentActivity();
        ViewGroup viewGroup = (ViewGroup) ((currentActivity == null || (window = currentActivity.getWindow()) == null) ? null : window.getDecorView());
        View findViewById = viewGroup == null ? null : viewGroup.findViewById(android.R.id.content);
        if (findViewById == null) {
            return null;
        }
        EdgeInsets safeAreaInsets = SafeAreaUtilsKt.getSafeAreaInsets(viewGroup);
        Rect frame = SafeAreaUtilsKt.getFrame(viewGroup, findViewById);
        if (safeAreaInsets != null && frame != null) {
            return MapsKt.mapOf(TuplesKt.to("insets", SerializationUtilsKt.edgeInsetsToJavaMap(safeAreaInsets)), TuplesKt.to("frame", SerializationUtilsKt.rectToJavaMap(frame)));
        }
        return null;
    }
}

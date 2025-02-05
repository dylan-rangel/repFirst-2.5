package com.dylanvann.fastimage;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import com.RNFetchBlob.RNFetchBlobConst;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.Headers;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ApplicationVersionSignature;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.NoSuchKeyException;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.uimanager.ViewProps;
import io.sentry.ProfilingTraceData;
import io.sentry.protocol.SentryThread;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
class FastImageViewConverter {
    private static final Drawable TRANSPARENT_DRAWABLE = new ColorDrawable(0);
    private static final Map<String, FastImageCacheControl> FAST_IMAGE_CACHE_CONTROL_MAP = new HashMap<String, FastImageCacheControl>() { // from class: com.dylanvann.fastimage.FastImageViewConverter.1
        {
            put("immutable", FastImageCacheControl.IMMUTABLE);
            put("web", FastImageCacheControl.WEB);
            put("cacheOnly", FastImageCacheControl.CACHE_ONLY);
        }
    };
    private static final Map<String, Priority> FAST_IMAGE_PRIORITY_MAP = new HashMap<String, Priority>() { // from class: com.dylanvann.fastimage.FastImageViewConverter.2
        {
            put("low", Priority.LOW);
            put(ProfilingTraceData.TRUNCATION_REASON_NORMAL, Priority.NORMAL);
            put("high", Priority.HIGH);
        }
    };
    private static final Map<String, ImageView.ScaleType> FAST_IMAGE_RESIZE_MODE_MAP = new HashMap<String, ImageView.ScaleType>() { // from class: com.dylanvann.fastimage.FastImageViewConverter.3
        {
            put("contain", ImageView.ScaleType.FIT_CENTER);
            put("cover", ImageView.ScaleType.CENTER_CROP);
            put("stretch", ImageView.ScaleType.FIT_XY);
            put("center", ImageView.ScaleType.CENTER_INSIDE);
        }
    };

    FastImageViewConverter() {
    }

    static FastImageSource getImageSource(Context context, ReadableMap readableMap) {
        return new FastImageSource(context, readableMap.getString(RNFetchBlobConst.DATA_ENCODE_URI), getHeaders(readableMap));
    }

    static Headers getHeaders(ReadableMap readableMap) {
        Headers headers = Headers.DEFAULT;
        if (!readableMap.hasKey("headers")) {
            return headers;
        }
        ReadableMap map = readableMap.getMap("headers");
        ReadableMapKeySetIterator keySetIterator = map.keySetIterator();
        LazyHeaders.Builder builder = new LazyHeaders.Builder();
        while (keySetIterator.hasNextKey()) {
            String nextKey = keySetIterator.nextKey();
            builder.addHeader(nextKey, map.getString(nextKey));
        }
        return builder.build();
    }

    static RequestOptions getOptions(Context context, FastImageSource fastImageSource, ReadableMap readableMap) {
        Boolean bool;
        Priority priority = getPriority(readableMap);
        FastImageCacheControl cacheControl = getCacheControl(readableMap);
        DiskCacheStrategy diskCacheStrategy = DiskCacheStrategy.AUTOMATIC;
        Boolean bool2 = false;
        int i = AnonymousClass4.$SwitchMap$com$dylanvann$fastimage$FastImageCacheControl[cacheControl.ordinal()];
        if (i == 1) {
            diskCacheStrategy = DiskCacheStrategy.NONE;
            bool = true;
        } else if (i != 2) {
            bool = bool2;
        } else {
            bool2 = true;
            bool = bool2;
        }
        RequestOptions placeholder = new RequestOptions().diskCacheStrategy(diskCacheStrategy).onlyRetrieveFromCache(bool2.booleanValue()).skipMemoryCache(bool.booleanValue()).priority(priority).placeholder(TRANSPARENT_DRAWABLE);
        return fastImageSource.isResource() ? placeholder.apply(RequestOptions.signatureOf(ApplicationVersionSignature.obtain(context))) : placeholder;
    }

    /* renamed from: com.dylanvann.fastimage.FastImageViewConverter$4, reason: invalid class name */
    static /* synthetic */ class AnonymousClass4 {
        static final /* synthetic */ int[] $SwitchMap$com$dylanvann$fastimage$FastImageCacheControl;

        static {
            int[] iArr = new int[FastImageCacheControl.values().length];
            $SwitchMap$com$dylanvann$fastimage$FastImageCacheControl = iArr;
            try {
                iArr[FastImageCacheControl.WEB.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$dylanvann$fastimage$FastImageCacheControl[FastImageCacheControl.CACHE_ONLY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$dylanvann$fastimage$FastImageCacheControl[FastImageCacheControl.IMMUTABLE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    private static FastImageCacheControl getCacheControl(ReadableMap readableMap) {
        return (FastImageCacheControl) getValueFromSource("cache", "immutable", FAST_IMAGE_CACHE_CONTROL_MAP, readableMap);
    }

    private static Priority getPriority(ReadableMap readableMap) {
        return (Priority) getValueFromSource(SentryThread.JsonKeys.PRIORITY, ProfilingTraceData.TRUNCATION_REASON_NORMAL, FAST_IMAGE_PRIORITY_MAP, readableMap);
    }

    static ImageView.ScaleType getScaleType(String str) {
        return (ImageView.ScaleType) getValue(ViewProps.RESIZE_MODE, "cover", FAST_IMAGE_RESIZE_MODE_MAP, str);
    }

    private static <T> T getValue(String str, String str2, Map<String, T> map, String str3) {
        if (str3 != null) {
            str2 = str3;
        }
        T t = map.get(str2);
        if (t != null) {
            return t;
        }
        throw new JSApplicationIllegalArgumentException("FastImage, invalid " + str + " : " + str2);
    }

    private static <T> T getValueFromSource(String str, String str2, Map<String, T> map, ReadableMap readableMap) {
        String str3 = null;
        if (readableMap != null) {
            try {
                str3 = readableMap.getString(str);
            } catch (NoSuchKeyException unused) {
            }
        }
        return (T) getValue(str, str2, map, str3);
    }
}

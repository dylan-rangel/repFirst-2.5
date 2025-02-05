package com.facebook.react.animated;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.UnexpectedNativeTypeException;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import java.util.List;

/* loaded from: classes.dex */
class EventAnimationDriver implements RCTEventEmitter {
    String mEventName;
    private List<String> mEventPath;
    ValueAnimatedNode mValueNode;
    int mViewTag;

    public EventAnimationDriver(String str, int i, List<String> list, ValueAnimatedNode valueAnimatedNode) {
        this.mEventName = str;
        this.mViewTag = i;
        this.mEventPath = list;
        this.mValueNode = valueAnimatedNode;
    }

    @Override // com.facebook.react.uimanager.events.RCTEventEmitter
    public void receiveEvent(int i, String str, WritableMap writableMap) {
        ReadableArray array;
        WritableMap writableMap2;
        if (writableMap == null) {
            throw new IllegalArgumentException("Native animated events must have event data.");
        }
        int i2 = 0;
        ReadableArray readableArray = null;
        WritableMap writableMap3 = writableMap;
        while (i2 < this.mEventPath.size() - 1) {
            if (writableMap3 != null) {
                String str2 = this.mEventPath.get(i2);
                ReadableType type = writableMap3.getType(str2);
                if (type == ReadableType.Map) {
                    writableMap2 = writableMap3.getMap(str2);
                    readableArray = null;
                } else if (type == ReadableType.Array) {
                    array = writableMap3.getArray(str2);
                    readableArray = array;
                    writableMap2 = null;
                } else {
                    throw new UnexpectedNativeTypeException("Unexpected type " + type + " for key '" + str2 + "'");
                }
            } else {
                int parseInt = Integer.parseInt(this.mEventPath.get(i2));
                ReadableType type2 = readableArray.getType(parseInt);
                if (type2 == ReadableType.Map) {
                    writableMap2 = readableArray.getMap(parseInt);
                    readableArray = null;
                } else if (type2 == ReadableType.Array) {
                    array = readableArray.getArray(parseInt);
                    readableArray = array;
                    writableMap2 = null;
                } else {
                    throw new UnexpectedNativeTypeException("Unexpected type " + type2 + " for index '" + parseInt + "'");
                }
            }
            i2++;
            writableMap3 = writableMap2;
        }
        String str3 = this.mEventPath.get(r6.size() - 1);
        if (writableMap3 != null) {
            this.mValueNode.mValue = writableMap3.getDouble(str3);
        } else {
            this.mValueNode.mValue = readableArray.getDouble(Integer.parseInt(str3));
        }
    }

    @Override // com.facebook.react.uimanager.events.RCTEventEmitter
    public void receiveTouches(String str, WritableArray writableArray, WritableArray writableArray2) {
        throw new RuntimeException("receiveTouches is not support by native animated events");
    }
}

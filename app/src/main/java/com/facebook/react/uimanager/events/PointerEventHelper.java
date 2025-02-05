package com.facebook.react.uimanager.events;

import android.view.MotionEvent;
import android.view.View;
import com.facebook.common.logging.FLog;
import com.facebook.react.R;
import com.facebook.react.common.ReactConstants;

/* loaded from: classes.dex */
public class PointerEventHelper {
    public static final String POINTER_CANCEL = "topPointerCancel";
    public static final String POINTER_DOWN = "topPointerDown";
    public static final String POINTER_ENTER = "topPointerEnter";
    public static final String POINTER_LEAVE = "topPointerLeave";
    public static final String POINTER_MOVE = "topPointerMove";
    public static final String POINTER_OUT = "topPointerOut";
    public static final String POINTER_OVER = "topPointerOver";
    public static final String POINTER_TYPE_MOUSE = "mouse";
    public static final String POINTER_TYPE_PEN = "pen";
    public static final String POINTER_TYPE_TOUCH = "touch";
    public static final String POINTER_TYPE_UNKNOWN = "";
    public static final String POINTER_UP = "topPointerUp";

    public enum EVENT {
        CANCEL,
        CANCEL_CAPTURE,
        DOWN,
        DOWN_CAPTURE,
        ENTER,
        ENTER_CAPTURE,
        LEAVE,
        LEAVE_CAPTURE,
        MOVE,
        MOVE_CAPTURE,
        UP,
        UP_CAPTURE,
        OUT,
        OUT_CAPTURE,
        OVER,
        OVER_CAPTURE
    }

    public static String getW3CPointerType(int i) {
        return i != 1 ? i != 2 ? i != 3 ? "" : POINTER_TYPE_MOUSE : POINTER_TYPE_PEN : "touch";
    }

    /* renamed from: com.facebook.react.uimanager.events.PointerEventHelper$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$facebook$react$uimanager$events$PointerEventHelper$EVENT;

        static {
            int[] iArr = new int[EVENT.values().length];
            $SwitchMap$com$facebook$react$uimanager$events$PointerEventHelper$EVENT = iArr;
            try {
                iArr[EVENT.LEAVE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$facebook$react$uimanager$events$PointerEventHelper$EVENT[EVENT.DOWN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$facebook$react$uimanager$events$PointerEventHelper$EVENT[EVENT.MOVE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$facebook$react$uimanager$events$PointerEventHelper$EVENT[EVENT.ENTER.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$facebook$react$uimanager$events$PointerEventHelper$EVENT[EVENT.CANCEL.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$facebook$react$uimanager$events$PointerEventHelper$EVENT[EVENT.UP.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$facebook$react$uimanager$events$PointerEventHelper$EVENT[EVENT.OVER.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$facebook$react$uimanager$events$PointerEventHelper$EVENT[EVENT.OUT.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$facebook$react$uimanager$events$PointerEventHelper$EVENT[EVENT.DOWN_CAPTURE.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$facebook$react$uimanager$events$PointerEventHelper$EVENT[EVENT.UP_CAPTURE.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$facebook$react$uimanager$events$PointerEventHelper$EVENT[EVENT.CANCEL_CAPTURE.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
        }
    }

    public static String getDispatchableEventName(EVENT event) {
        switch (AnonymousClass1.$SwitchMap$com$facebook$react$uimanager$events$PointerEventHelper$EVENT[event.ordinal()]) {
            case 1:
                return POINTER_LEAVE;
            case 2:
                return POINTER_DOWN;
            case 3:
                return POINTER_MOVE;
            case 4:
                return POINTER_ENTER;
            case 5:
                return POINTER_CANCEL;
            case 6:
                return POINTER_UP;
            case 7:
                return POINTER_OVER;
            case 8:
                return POINTER_OUT;
            default:
                FLog.e(ReactConstants.TAG, "No dispatchable event name for type: " + event);
                return null;
        }
    }

    public static int getButtons(String str, String str2, int i) {
        if (isExitEvent(str)) {
            return 0;
        }
        if ("touch".equals(str2)) {
            return 1;
        }
        return i;
    }

    public static int getButtonChange(String str, int i, int i2) {
        int i3 = 0;
        if ("touch".equals(str)) {
            return 0;
        }
        int i4 = i2 ^ i;
        if (i4 == 0) {
            return -1;
        }
        if (i4 != 1) {
            i3 = 2;
            if (i4 != 2) {
                if (i4 == 4) {
                    return 1;
                }
                if (i4 != 8) {
                    return i4 != 16 ? -1 : 4;
                }
                return 3;
            }
        }
        return i3;
    }

    public static boolean isPrimary(int i, int i2, MotionEvent motionEvent) {
        return supportsHover(motionEvent) || i == i2;
    }

    public static boolean isListening(View view, EVENT event) {
        if (view == null) {
            return false;
        }
        int i = AnonymousClass1.$SwitchMap$com$facebook$react$uimanager$events$PointerEventHelper$EVENT[event.ordinal()];
        if (i != 2 && i != 5 && i != 6) {
            switch (i) {
                case 9:
                case 10:
                case 11:
                    break;
                default:
                    Integer num = (Integer) view.getTag(R.id.pointer_events);
                    return (num == null || (num.intValue() & (1 << event.ordinal())) == 0) ? false : true;
            }
        }
        return true;
    }

    public static int getEventCategory(String str) {
        if (str == null) {
            return 2;
        }
        str.hashCode();
        switch (str) {
        }
        return 2;
    }

    public static boolean supportsHover(MotionEvent motionEvent) {
        int source = motionEvent.getSource();
        return source == 8194 || source == 2;
    }

    public static boolean isExitEvent(String str) {
        str.hashCode();
        switch (str) {
            case "topPointerLeave":
            case "topPointerUp":
            case "topPointerOut":
                return true;
            default:
                return false;
        }
    }

    public static double getPressure(int i, String str) {
        if (isExitEvent(str)) {
            return 0.0d;
        }
        return i != 0 ? 0.5d : 0.0d;
    }

    public static boolean isBubblingEvent(String str) {
        str.hashCode();
        switch (str) {
            case "topPointerDown":
            case "topPointerMove":
            case "topPointerOver":
            case "topPointerUp":
            case "topPointerCancel":
            case "topPointerOut":
                return true;
            default:
                return false;
        }
    }
}

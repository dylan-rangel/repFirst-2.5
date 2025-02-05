package com.reactnative.ivpusic.imagepicker;

import android.util.Log;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes2.dex */
class ResultCollector {
    private WritableArray arrayResult;
    private boolean multiple;
    private Promise promise;
    private boolean resultSent;
    private int waitCount;
    private AtomicInteger waitCounter;

    ResultCollector() {
    }

    synchronized void setup(Promise promise, boolean z) {
        this.promise = promise;
        this.multiple = z;
        this.resultSent = false;
        this.waitCount = 0;
        this.waitCounter = new AtomicInteger(0);
        if (z) {
            this.arrayResult = new WritableNativeArray();
        }
    }

    synchronized void setWaitCount(int i) {
        this.waitCount = i;
        this.waitCounter = new AtomicInteger(0);
    }

    private synchronized boolean isRequestValid() {
        if (this.resultSent) {
            Log.w("image-crop-picker", "Skipping result, already sent...");
            return false;
        }
        if (this.promise != null) {
            return true;
        }
        Log.w("image-crop-picker", "Trying to notify success but promise is not set");
        return false;
    }

    synchronized void notifySuccess(WritableMap writableMap) {
        if (isRequestValid()) {
            if (this.multiple) {
                this.arrayResult.pushMap(writableMap);
                if (this.waitCounter.addAndGet(1) == this.waitCount) {
                    this.promise.resolve(this.arrayResult);
                    this.resultSent = true;
                }
            } else {
                this.promise.resolve(writableMap);
                this.resultSent = true;
            }
        }
    }

    synchronized void notifyProblem(String str, String str2) {
        if (isRequestValid()) {
            Log.e("image-crop-picker", "Promise rejected. " + str2);
            this.promise.reject(str, str2);
            this.resultSent = true;
        }
    }

    synchronized void notifyProblem(String str, Throwable th) {
        if (isRequestValid()) {
            Log.e("image-crop-picker", "Promise rejected. " + th.getMessage());
            this.promise.reject(str, th);
            this.resultSent = true;
        }
    }
}

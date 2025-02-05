package com.google.android.material.datepicker;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.core.util.Pair;
import com.android.tools.r8.annotations.SynthesizedClassV2;
import com.google.android.material.datepicker.DateSelector;
import com.google.android.material.internal.ViewUtils;
import java.text.SimpleDateFormat;
import java.util.Collection;

/* loaded from: classes2.dex */
public interface DateSelector<S> extends Parcelable {
    int getDefaultThemeResId(Context context);

    int getDefaultTitleResId();

    String getError();

    Collection<Long> getSelectedDays();

    Collection<Pair<Long, Long>> getSelectedRanges();

    S getSelection();

    String getSelectionContentDescription(Context context);

    String getSelectionDisplayString(Context context);

    boolean isSelectionComplete();

    View onCreateTextInputView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle, CalendarConstraints calendarConstraints, OnSelectionChangedListener<S> onSelectionChangedListener);

    void select(long j);

    void setSelection(S s);

    void setTextInputFormat(SimpleDateFormat simpleDateFormat);

    @SynthesizedClassV2(kind = 7, versionHash = "5e5398f0546d1d7afd62641edb14d82894f11ddc41bce363a0c8d0dac82c9c5a")
    /* renamed from: com.google.android.material.datepicker.DateSelector$-CC, reason: invalid class name */
    public final /* synthetic */ class CC<S> {
        public static void showKeyboardWithAutoHideBehavior(final EditText... editTextArr) {
            if (editTextArr.length == 0) {
                return;
            }
            View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() { // from class: com.google.android.material.datepicker.DateSelector$$ExternalSyntheticLambda0
                @Override // android.view.View.OnFocusChangeListener
                public final void onFocusChange(View view, boolean z) {
                    DateSelector.CC.lambda$showKeyboardWithAutoHideBehavior$0(editTextArr, view, z);
                }
            };
            for (EditText editText : editTextArr) {
                editText.setOnFocusChangeListener(onFocusChangeListener);
            }
            ViewUtils.requestFocusAndShowKeyboard(editTextArr[0]);
        }

        public static /* synthetic */ void lambda$showKeyboardWithAutoHideBehavior$0(EditText[] editTextArr, View view, boolean z) {
            for (EditText editText : editTextArr) {
                if (editText.hasFocus()) {
                    return;
                }
            }
            ViewUtils.hideKeyboard(view);
        }
    }
}

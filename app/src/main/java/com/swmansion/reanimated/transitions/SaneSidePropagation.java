package com.swmansion.reanimated.transitions;

import android.view.ViewGroup;
import androidx.transition.SidePropagation;
import androidx.transition.Transition;
import androidx.transition.TransitionValues;

/* loaded from: classes.dex */
public class SaneSidePropagation extends SidePropagation {
    @Override // androidx.transition.SidePropagation, androidx.transition.TransitionPropagation
    public long getStartDelay(ViewGroup viewGroup, Transition transition, TransitionValues transitionValues, TransitionValues transitionValues2) {
        long startDelay = super.getStartDelay(viewGroup, transition, transitionValues, transitionValues2);
        return startDelay != 0 ? (transitionValues2 == null || getViewVisibility(transitionValues) == 0) ? -startDelay : startDelay : startDelay;
    }
}

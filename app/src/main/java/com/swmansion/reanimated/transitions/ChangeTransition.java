package com.swmansion.reanimated.transitions;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.TimeInterpolator;
import android.view.ViewGroup;
import androidx.transition.ChangeBounds;
import androidx.transition.ChangeTransform;
import androidx.transition.Transition;
import androidx.transition.TransitionPropagation;
import androidx.transition.TransitionValues;

/* loaded from: classes.dex */
final class ChangeTransition extends Transition {
    private final ChangeTransform mChangeTransform = new ChangeTransform();
    private final ChangeBounds mChangeBounds = new ChangeBounds();

    @Override // androidx.transition.Transition
    public void captureStartValues(TransitionValues transitionValues) {
        this.mChangeTransform.captureStartValues(transitionValues);
        this.mChangeBounds.captureStartValues(transitionValues);
    }

    @Override // androidx.transition.Transition
    public void captureEndValues(TransitionValues transitionValues) {
        this.mChangeTransform.captureEndValues(transitionValues);
        this.mChangeBounds.captureEndValues(transitionValues);
    }

    @Override // androidx.transition.Transition
    public Transition setDuration(long j) {
        this.mChangeTransform.setDuration(j);
        this.mChangeBounds.setDuration(j);
        return super.setDuration(j);
    }

    @Override // androidx.transition.Transition
    public Transition setStartDelay(long j) {
        this.mChangeTransform.setStartDelay(j);
        this.mChangeBounds.setStartDelay(j);
        return super.setStartDelay(j);
    }

    @Override // androidx.transition.Transition
    public Transition setInterpolator(TimeInterpolator timeInterpolator) {
        this.mChangeTransform.setInterpolator(timeInterpolator);
        this.mChangeBounds.setInterpolator(timeInterpolator);
        return super.setInterpolator(timeInterpolator);
    }

    @Override // androidx.transition.Transition
    public void setPropagation(TransitionPropagation transitionPropagation) {
        this.mChangeTransform.setPropagation(transitionPropagation);
        this.mChangeBounds.setPropagation(transitionPropagation);
        super.setPropagation(transitionPropagation);
    }

    @Override // androidx.transition.Transition
    public Animator createAnimator(ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
        this.mChangeTransform.setReparent(false);
        Animator createAnimator = this.mChangeTransform.createAnimator(viewGroup, transitionValues, transitionValues2);
        Animator createAnimator2 = this.mChangeBounds.createAnimator(viewGroup, transitionValues, transitionValues2);
        if (createAnimator == null) {
            return createAnimator2;
        }
        if (createAnimator2 == null) {
            return createAnimator;
        }
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(createAnimator, createAnimator2);
        return animatorSet;
    }
}

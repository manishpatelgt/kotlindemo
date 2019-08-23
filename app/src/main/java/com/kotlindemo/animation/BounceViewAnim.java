package com.kotlindemo.animation;

import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by Manish Patel on 8/23/2019.
 */
public interface BounceViewAnim {

    BounceViewAnim setScaleForPushInAnim(float scaleX, float scaleY);

    BounceViewAnim setScaleForPopOutAnim(float scaleX, float scaleY);

    BounceViewAnim setPushInAnimDuration(int timeInMillis);

    BounceViewAnim setPopOutAnimDuration(int timeInMillis);

    BounceViewAnim setInterpolatorPushIn(AccelerateDecelerateInterpolator interpolatorPushIn);

    BounceViewAnim setInterpolatorPopOut(AccelerateDecelerateInterpolator interpolatorPopOut);
}

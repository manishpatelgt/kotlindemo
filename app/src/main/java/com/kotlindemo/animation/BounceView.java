package com.kotlindemo.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.PopupWindow;

import com.google.android.material.tabs.TabLayout;
import com.kotlindemo.R;

import java.lang.ref.WeakReference;
import java.util.logging.Handler;

/**
 * Created by Manish Patel on 8/23/2019.
 */
public class BounceView implements BounceViewAnim {

    public static final float PUSH_IN_SCALE_X = 0.9f;
    public static final float PUSH_IN_SCALE_Y = 0.9f;
    public static final float POP_OUT_SCALE_X = 1.1f;
    public static final float POP_OUT_SCALE_Y = 1.1f;
    public static final int PUSH_IN_ANIM_DURATION = 200;
    public static final int POP_OUT_ANIM_DURATION = 200;
    public static final AccelerateDecelerateInterpolator DEFAULT_INTERPOLATOR
            = new AccelerateDecelerateInterpolator();

    private WeakReference<View> view;
    private WeakReference<Dialog> dialog;
    private WeakReference<PopupWindow> popup;
    private WeakReference<TabLayout> tabLayout;
    private boolean isTouchInsideView = true;
    private float pushInScaleX = PUSH_IN_SCALE_X;
    private float pushInScaleY = PUSH_IN_SCALE_Y;
    private float popOutScaleX = POP_OUT_SCALE_X;
    private float popOutScaleY = POP_OUT_SCALE_Y;
    private int pushInAnimDuration = PUSH_IN_ANIM_DURATION;
    private int popOutAnimDuration = POP_OUT_ANIM_DURATION;
    private AccelerateDecelerateInterpolator pushInInterpolator = DEFAULT_INTERPOLATOR;
    private AccelerateDecelerateInterpolator popOutInterpolator = DEFAULT_INTERPOLATOR;

    private BounceView(View view) {
        this.view = new WeakReference<View>(view);
        if (this.view.get() != null) {
            if (!this.view.get().hasOnClickListeners()) {
                this.view.get().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        }
    }

    public static BounceView addAnimTo(View view) {
        BounceView bounceAnim = new BounceView(view);
        bounceAnim.setAnimToView();
        return bounceAnim;
    }

    @Override
    public BounceViewAnim setScaleForPushInAnim(float scaleX, float scaleY) {
        this.pushInScaleX = scaleX;
        this.pushInScaleY = scaleY;
        return this;
    }

    @Override
    public BounceViewAnim setScaleForPopOutAnim(float scaleX, float scaleY) {
        this.popOutScaleX = scaleX;
        this.popOutScaleY = scaleY;
        return this;
    }

    @Override
    public BounceViewAnim setPushInAnimDuration(int timeInMillis) {
        this.pushInAnimDuration = timeInMillis;
        return this;
    }

    @Override
    public BounceViewAnim setPopOutAnimDuration(int timeInMillis) {
        this.popOutAnimDuration = timeInMillis;
        return this;
    }

    @Override
    public BounceViewAnim setInterpolatorPushIn(AccelerateDecelerateInterpolator interpolatorPushIn) {
        this.pushInInterpolator = interpolatorPushIn;
        return this;
    }

    @Override
    public BounceViewAnim setInterpolatorPopOut(AccelerateDecelerateInterpolator interpolatorPopOut) {
        this.popOutInterpolator = interpolatorPopOut;
        return this;
    }

    private void setAnimToView() {
        if (view != null) {
            view.get().setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(final View v, MotionEvent motionEvent) {
                    int action = motionEvent.getAction();

                    if (action == MotionEvent.ACTION_DOWN) {
                        isTouchInsideView = true;

                        Log.e("BounceView", " MotionEvent.ACTION_DOWN");

                        startAnimScale(v, pushInScaleX, pushInScaleY, pushInAnimDuration, pushInInterpolator, 0);

                    } else if (action == MotionEvent.ACTION_UP) {
                        Log.e("BounceView", " MotionEvent.ACTION_UP");

                        if (isTouchInsideView) {
                            v.animate().cancel();

                            startAnimScale(v, popOutScaleX, popOutScaleY, popOutAnimDuration, popOutInterpolator, 0);
                            startAnimScale(v, 1f, 1f, popOutAnimDuration, popOutInterpolator, popOutAnimDuration + 1);

                            return false;
                        }
                    } else if (action == MotionEvent.ACTION_CANCEL) {
                        if (isTouchInsideView) {
                            v.animate().cancel();

                            startAnimScale(v, 1f, 1f, popOutAnimDuration, DEFAULT_INTERPOLATOR, 0);

                        }

                        return true;
                    } else if (action == MotionEvent.ACTION_MOVE) {
                        if (isTouchInsideView) {
                            float currentX = motionEvent.getX();
                            float currentY = motionEvent.getY();
                            float currentPosX = currentX + v.getLeft();
                            float currentPosY = currentY + v.getTop();
                            float viewLeft = v.getLeft();
                            float viewTop = v.getTop();
                            float viewRight = v.getRight();
                            float viewBottom = v.getBottom();
                            if (!(currentPosX > viewLeft && currentPosX < viewRight
                                    && currentPosY > viewTop && currentPosY < viewBottom)) {
                                isTouchInsideView = false;
                                v.animate().cancel();

                                startAnimScale(v, 1f, 1f, popOutAnimDuration, DEFAULT_INTERPOLATOR, 0);
                            }

                            return true;
                        }
                    }

                    return false;
                }
            });
        }
    }

    public static void startContinueAnimation(View v) {
        final int PUSH_IN_ANIM_DURATION = 500;
        final int POP_OUT_ANIM_DURATION = 500;
        float pushInScaleX = 0.8f;
        float pushInScaleY = 0.8f;
        float popOutScaleX = 1.1f;
        float popOutScaleY = 1.1f;
        int pushInAnimDuration = PUSH_IN_ANIM_DURATION;
        int popOutAnimDuration = POP_OUT_ANIM_DURATION;

        AccelerateDecelerateInterpolator pushInInterpolator = DEFAULT_INTERPOLATOR;
        AccelerateDecelerateInterpolator popOutInterpolator = DEFAULT_INTERPOLATOR;

        v.animate().cancel();
        startAnimScale2(v, pushInScaleX, pushInScaleY, pushInAnimDuration, pushInInterpolator, 0);
        startAnimScale2(v, popOutScaleX, popOutScaleY, popOutAnimDuration, popOutInterpolator, 0);
        startAnimScale2(v, 1f, 1f, popOutAnimDuration, popOutInterpolator, popOutAnimDuration + 1);
    }

    private static void startAnimScale2(final View view, float scaleX, float scaleY,
                                        int animDuration,
                                        AccelerateDecelerateInterpolator interpolator,
                                        int startDelay) {
        ObjectAnimator animX = ObjectAnimator.ofFloat(view, "scaleX", scaleX);
        ObjectAnimator animY = ObjectAnimator.ofFloat(view, "scaleY", scaleY);
        AnimatorSet animatorSet = new AnimatorSet();
        animX.setDuration(animDuration);
        animX.setInterpolator(interpolator);
        animY.setDuration(animDuration);
        animY.setInterpolator(interpolator);

        animY.setRepeatCount(ObjectAnimator.INFINITE);
        animY.setRepeatMode(ObjectAnimator.REVERSE);

        animatorSet.playTogether(animX, animY);
        animatorSet.setStartDelay(startDelay);
        animatorSet.start();
    }

    private void startAnimScale(View view, float scaleX, float scaleY,
                                int animDuration,
                                AccelerateDecelerateInterpolator interpolator,
                                int startDelay) {
        ObjectAnimator animX = ObjectAnimator.ofFloat(view, "scaleX", scaleX);
        ObjectAnimator animY = ObjectAnimator.ofFloat(view, "scaleY", scaleY);
        AnimatorSet animatorSet = new AnimatorSet();
        animX.setDuration(animDuration);
        animX.setInterpolator(interpolator);
        animY.setDuration(animDuration);
        animY.setInterpolator(interpolator);

        animatorSet.playTogether(animX, animY);
        animatorSet.setStartDelay(startDelay);
        animatorSet.start();
    }

}

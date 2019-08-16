package com.kotlindemo.activity.otherthings.ui

import android.os.Bundle
import com.kotlindemo.R
import com.kotlindemo.application.ParentActivity
import kotlinx.android.synthetic.main.activity_auto_textview.*
import kotlinx.android.synthetic.main.activity_auto_textview.toolbar
import android.view.animation.BounceInterpolator
import android.R.attr.button
import android.animation.ObjectAnimator

/**
 * Created by Manish Patel on 8/10/2019.
 */

//https://blog.mindorks.com/autosizing-textview-implementation-for-android
//edge-to-edge https://blog.mindorks.com/exploring-edge-to-edge-feature-in-android-q

class AutoTextViewDemoActivity : ParentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auto_textview)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Load the animation
        /*val myAnim = AnimationUtils.loadAnimation(this,R.anim.bounce)
        val animationDuration = 3 * 1000
        myAnim.duration = animationDuration.toLong()

        // Use custom animation interpolator to achieve the bounce effect
        val interpolator = MyBounceInterpolator(0.2, 20.0)
        myAnim.interpolator = interpolator*/

        val animY = ObjectAnimator.ofFloat(button, "translationY", -100f, 0f)
        animY.duration = 1000//1sec
        animY.interpolator = BounceInterpolator()
        animY.repeatCount = 1
        animY.start()

        bounceButton.setOnClickListener {
            animY.start()
            //bounceButton.startAnimation(myAnim)
        }

        // Run button animation again after it finished
        /*myAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(arg0: Animation) {}
            override fun onAnimationRepeat(arg0: Animation) {}
            override fun onAnimationEnd(arg0: Animation) {
                bounceButton.clearAnimation()
            }
        })*/
    }
}
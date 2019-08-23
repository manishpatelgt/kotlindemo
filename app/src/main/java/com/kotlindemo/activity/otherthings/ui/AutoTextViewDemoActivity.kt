package com.kotlindemo.activity.otherthings.ui

import android.os.Bundle
import com.kotlindemo.R
import com.kotlindemo.application.ParentActivity
import kotlinx.android.synthetic.main.activity_auto_textview.*
import kotlinx.android.synthetic.main.activity_auto_textview.toolbar
import android.view.animation.Animation
import com.kotlindemo.utility.MyBounceInterpolator
import android.view.animation.AnimationUtils
import com.kotlindemo.animation.BounceView

/**
 * Created by Manish Patel on 8/10/2019.
 */

//https://blog.mindorks.com/autosizing-textview-implementation-for-android
//edge-to-edge https://blog.mindorks.com/exploring-edge-to-edge-feature-in-android-q
//BounceView: https://androidexample365.com/customizable-bounce-animation-for-any-view-updation/

class AutoTextViewDemoActivity : ParentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auto_textview)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Load the animation
        val myAnim = AnimationUtils.loadAnimation(this,R.anim.bounce)
        //val animationDuration = 3 * 1000
        //myAnim.duration = animationDuration.toLong()

        // Use custom animation interpolator to achieve the bounce effect
        val interpolator = MyBounceInterpolator(0.2, 20.0)
        //myAnim.interpolator = interpolator

        BounceView.addAnimTo(bounceButton)
        bounceButton.setOnClickListener {
            BounceView.addAnimTo(bounceButton)
            //bounceButton.startAnimation(myAnim)
        }

        // Run button animation again after it finished
        myAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(arg0: Animation) {}
            override fun onAnimationRepeat(arg0: Animation) {}
            override fun onAnimationEnd(arg0: Animation) {
                //bounceButton.clearAnimation()
            }
        })
    }
}
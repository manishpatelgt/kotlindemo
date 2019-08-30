package com.kotlindemo.activity.otherthings.ui

import android.os.Bundle
import android.os.Handler
import com.kotlindemo.R
import com.kotlindemo.application.ParentActivity
import kotlinx.android.synthetic.main.activity_auto_textview.*
import kotlinx.android.synthetic.main.activity_auto_textview.toolbar
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.kotlindemo.animation.BounceView
import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.widget.Button
import com.astritveliu.boom.Boom

/**
 * Created by Manish Patel on 8/10/2019.
 */

//https://blog.mindorks.com/autosizing-textview-implementation-for-android
//edge-to-edge https://blog.mindorks.com/exploring-edge-to-edge-feature-in-android-q
//BounceView: https://androidexample365.com/customizable-bounce-animation-for-any-view-updation/
//StateAnimator: https://android.jlelse.eu/polishing-ui-android-statelistanimator-7b74a06b85a5

class AutoTextViewDemoActivity : ParentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.kotlindemo.R.layout.activity_auto_textview)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Load the animation
        val myAnim = AnimationUtils.loadAnimation(this, com.kotlindemo.R.anim.grow_from_middle)
        val myAnim2 = AnimationUtils.loadAnimation(this, com.kotlindemo.R.anim.bounce_4)
        val myAnim3 = AnimationUtils.loadAnimation(this, com.kotlindemo.R.anim.shrink_grow)

        /*val myAnim = ScaleAnimation(
            0f,
            1f,
            0f,
            1f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        myAnim.duration = 1000
        myAnim.fillAfter = true*/

        Boom(bounceButton3 as Button)

        //bounce1 button animation
        //BounceView.addAnimTo(bounceButton)
        bounceButton.setOnClickListener {
            //bounceButton.startAnimation(myAnim2)
        }

        val animSet =
            AnimatorInflater.loadAnimator(this, com.kotlindemo.R.animator.animate_s) as AnimatorSet
        val animSet2 =
            AnimatorInflater.loadAnimator(this, com.kotlindemo.R.animator.animate_s2) as AnimatorSet

        animSet2.setTarget(bounceButton2)
        animSet.setTarget(bounceButton2)
        animSet.start()

        animSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                animSet2.start()
            }
        })

        animSet2.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                animSet.start()
            }
        })

        //start button2 animation
        bounceButton2.setOnClickListener {
            //bounceButton2.startAnimation(myAnim3)
            //startContinueAnimation()
            //stop as soon as you press
            //BounceView.startContinueAnimation(bounceButton2)
        }

        //Run button animation again after it finished
        myAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(arg0: Animation) {}
            override fun onAnimationRepeat(arg0: Animation) {}
            override fun onAnimationEnd(arg0: Animation) {
                bounceButton2.startAnimation(myAnim3)
            }
        })
    }

    fun startContinueAnimation() {
        val mHandler = Handler()

        val runnableCode = object : Runnable {
            override fun run() {
                BounceView.startContinueAnimation(bounceButton2)
                mHandler.postDelayed(this, 1000)
            }
        }

        mHandler.post(runnableCode)

        /*val mHandler = Handler()
        val mRunnable = Runnable {
            BounceView.startContinueAnimation(bounceButton2)
            mHandler.postDelayed(mRunnable, 2000)
        }
        mHandler.postDelayed(mRunnable, 2000)*/
    }
}
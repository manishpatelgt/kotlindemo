package com.kotlindemo.activity.otherthings.lottie

import android.os.Bundle
import com.kotlindemo.R
import com.kotlindemo.application.ParentActivity
import kotlinx.android.synthetic.main.activity_lottie_animation.*

/**
 * Created by Manish Patel on 8/30/2019.
 */
//https://airbnb.io/lottie/#/android?id=sample-app

class LottieAnimationDemoActivity : ParentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lottie_animation)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
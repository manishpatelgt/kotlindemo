package com.kotlindemo.activity.otherthings

import android.os.Bundle
import com.kotlindemo.R
import com.kotlindemo.activity.otherthings.firebaseremoteconfig.FireBaseRemoteConfigActivity
import com.kotlindemo.activity.otherthings.inappupdates.AppUpdatesDemoActivity
import com.kotlindemo.activity.otherthings.location.LocationDemoActivity
import com.kotlindemo.activity.otherthings.lottie.LottieAnimationDemoActivity
import com.kotlindemo.activity.otherthings.rxjava.ui.RxJavaDemoActivity
import com.kotlindemo.activity.otherthings.shimmer.ShimmerDemoActivity
import com.kotlindemo.activity.otherthings.sqldelight.SQLDelightDemoActivity
import com.kotlindemo.activity.otherthings.ui.AutoTextViewDemoActivity
import com.kotlindemo.activity.otherthings.ui.BounceAnimationDemoActivity
import com.kotlindemo.activity.otherthings.ui.MyTestDemo
import com.kotlindemo.application.ParentActivity
import kotlinx.android.synthetic.main.activity_other_things.*
import org.jetbrains.anko.startActivity

/**
 * Created by Manish Patel on 7/18/2019.
 */

class OtherThingsActivity : ParentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_things)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        location_button.setOnClickListener {
            startActivity<LocationDemoActivity>()
        }

        app_update_button.setOnClickListener {
            startActivity<AppUpdatesDemoActivity>()
        }

        rxjava_button.setOnClickListener {
            startActivity<RxJavaDemoActivity>()
        }

        shimmer_button.setOnClickListener {
            startActivity<ShimmerDemoActivity>()
        }

        autotextview_button.setOnClickListener {
            startActivity<AutoTextViewDemoActivity>()
        }

        firebase_button.setOnClickListener {
            startActivity<FireBaseRemoteConfigActivity>()
        }

        sqlidelight_button.setOnClickListener {
            startActivity<SQLDelightDemoActivity>()
        }

        lottie_button.setOnClickListener {
            startActivity<LottieAnimationDemoActivity>()
        }

        my_test_button.setOnClickListener {
            startActivity<MyTestDemo>()
        }

        bounce_button.setOnClickListener {
            startActivity<BounceAnimationDemoActivity>()
        }
    }
}
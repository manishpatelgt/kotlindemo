package com.kotlindemo.activity.otherthings

import android.os.Bundle
import com.kotlindemo.R
import com.kotlindemo.activity.otherthings.inappupdates.AppUpdatesDemoActivity
import com.kotlindemo.activity.otherthings.location.LocationDemoActivity
import com.kotlindemo.activity.otherthings.rxjava.ui.RxJavaDemoActivity
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
    }
}
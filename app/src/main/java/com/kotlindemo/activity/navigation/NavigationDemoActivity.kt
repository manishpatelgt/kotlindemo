package com.kotlindemo.activity.navigation

import android.os.Bundle
import com.kotlindemo.R
import com.kotlindemo.application.ParentActivity
import kotlinx.android.synthetic.main.activity_navigation.*

/**
 * Created by Manish Patel on 7/17/2019.
 */
//https://github.com/googlesamples/android-architecture-components
class NavigationDemoActivity : ParentActivity() {

    companion object {
        val TAG: String = NavigationDemoActivity.javaClass::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
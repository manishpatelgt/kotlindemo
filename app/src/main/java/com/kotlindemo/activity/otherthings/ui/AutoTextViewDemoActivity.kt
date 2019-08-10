package com.kotlindemo.activity.otherthings.ui

import android.os.Bundle
import com.kotlindemo.R
import com.kotlindemo.application.ParentActivity
import kotlinx.android.synthetic.main.activity_auto_textview.*

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
    }
}
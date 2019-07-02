package com.kotlindemo.activity.lifecycle

import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ProcessLifecycleOwner
import com.kotlindemo.R
import com.kotlindemo.application.ParentActivity
import kotlinx.android.synthetic.main.activity_livedata.*


/**
 * Created by Manish Patel on 5/22/2019.
 */
//https://developer.android.com/topic/libraries/architecture/lifecycle
//https://tutorial.eyehunts.com/android/android-activity-lifecycle-example-kotlin/
//https://riggaroo.co.za/android-architecture-components-looking-lifecycles-part-3/

class LifeCycleActivity : ParentActivity() {

    private val TAG by lazy { LifeCycleActivity::class.java.simpleName }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lifecycle)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        Log.d(TAG, "Owner ON_CREATE")
        ProcessLifecycleOwner.get().lifecycle.addObserver(LifeCycleComponent())
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "Owner ON_START")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "Owner ON_RESUME")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "Owner ON_PAUSE")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "Owner ON_STOP")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "Owner ON_RESTART")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "Owner ON_DESTROY")
    }
}
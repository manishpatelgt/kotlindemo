package com.kotlindemo.application

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.kotlindemo.utility.ToastManager
import android.R.attr.start
import com.kotlindemo.BuildConfig


/**
 * Created by Manish Patel on 5/7/2019.
 */

class DemoApplication : MultiDexApplication(), LifecycleObserver, Application.ActivityLifecycleCallbacks {

    private val TAG by lazy { DemoApplication::class.java.simpleName }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()

        // initialise app as a singleton
        sInstance = this

        // Lifecycle observers
        //ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        //registerActivityLifecycleCallbacks(this)

        //flipper
        /*SoLoader.init(this, false)

        if (BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(this)) {
            val client = AndroidFlipperClient.getInstance(this)
            client.addPlugin(InspectorFlipperPlugin(this, DescriptorMapping.withDefaults()))
            client.start()
        }*/
    }

    var currentActivity: Activity? = null

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onEnterForeground() {
        ToastManager.getInstance().showToast("ON_START")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onEnterBackground() {
        ToastManager.getInstance().showToast("ON_STOP")
    }

    override fun onActivityPaused(activity: Activity?) {
        ToastManager.getInstance().showToast("ON_PAUSE")
    }

    override fun onActivityResumed(activity: Activity?) {
        this.currentActivity = activity
        ToastManager.getInstance().showToast("ON_RESUME")
    }

    override fun onActivityStarted(activity: Activity?) {
        this.currentActivity = activity
        ToastManager.getInstance().showToast("onActivityStarted")
    }

    override fun onActivityDestroyed(activity: Activity?) {
        ToastManager.getInstance().showToast("onActivityDestroyed")
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
        ToastManager.getInstance().showToast("onActivitySaveInstanceState")
    }

    override fun onActivityStopped(activity: Activity?) {
        ToastManager.getInstance().showToast("onActivityStopped")
    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        ToastManager.getInstance().showToast("onActivityCreated")
    }

    companion object {

        // Needs to be volatile as another thread can see a half initialised instance.
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private lateinit var sInstance: DemoApplication

        fun getInstance(): DemoApplication {
            if (sInstance == null) {
                synchronized(DemoApplication::class.java) {
                    if (sInstance == null) {
                        sInstance = DemoApplication()
                    }
                }
            }
            return sInstance
        }

        val context: Context
            get() = sInstance.applicationContext

    }


}
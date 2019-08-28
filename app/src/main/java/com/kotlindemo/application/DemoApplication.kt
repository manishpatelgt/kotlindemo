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
import android.content.Intent
import android.os.Build
import android.os.PowerManager
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.facebook.stetho.Stetho
import com.kotlindemo.BuildConfig
import com.kotlindemo.activity.otherthings.location.EndlessService
import com.kotlindemo.appconstants.Consts
import org.slf4j.LoggerFactory
import java.sql.Timestamp
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit


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

        //stetho
        Stetho.initializeWithDefaults(this)

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

    fun isBatterySavingWhitelistEnabled(): Boolean {
        // Note this setting is only available on Android 6 (SDK 23) and above
        var isBatterySavingWhiteListed = true
        if (Build.VERSION.SDK_INT >= 23) {
            val pm = getSystemService(Context.POWER_SERVICE) as PowerManager
            val res = pm.isIgnoringBatteryOptimizations(BuildConfig.APPLICATION_ID)
            isBatterySavingWhiteListed = res
        }
        return isBatterySavingWhiteListed
    }
    private var scheduler: ScheduledExecutorService? = Executors.newSingleThreadScheduledExecutor()

    fun startScheduler() {
        scheduler?.let { it.shutdown() }
        scheduler = Executors.newSingleThreadScheduledExecutor()
        scheduler!!.scheduleAtFixedRate(saveHHLocation, 0, 10, TimeUnit.SECONDS)
    }

    fun stopScheduler() {
        scheduler?.let {
            scheduler!!.shutdown()
        }
    }

    val saveHHLocation = Runnable {
        logger.debug("inside saveHHLocation")
        logger.debug("Location ready to send ${DemoApplication.currentLocation.toString()}")
    }

    private var locationTimerTask: LocationSendTimerTask? = null
    private var locationSendTimer: Timer? = null

    fun startLocationTimerScheduler() {
        locationSendTimer = Timer()
        locationTimerTask = LocationSendTimerTask()
        locationSendTimer!!.schedule(locationTimerTask, 0, 10 * 1000)
    }

    fun stopLocationTimerScheduler() {
        if (locationTimerTask != null) {
            locationTimerTask!!.cancel()
            locationTimerTask = null
        }

        if (locationSendTimer != null) {
            locationSendTimer!!.cancel()
            locationSendTimer = null
        }
    }

    internal inner class LocationSendTimerTask : TimerTask() {
        override fun run() {
            logger.debug("inside LocationSendTimerTask")
            logger.debug("Location ready to send ${currentLocation.toString()}")
        }
    }

    companion object {

        private val logger = LoggerFactory.getLogger(DemoApplication::class.java)

        // Needs to be volatile as another thread can see a half initialised instance.
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private lateinit var sInstance: DemoApplication

        var currentLocation: Location? = null

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
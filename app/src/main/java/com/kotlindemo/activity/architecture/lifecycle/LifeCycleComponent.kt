package com.kotlindemo.activity.architecture.lifecycle

import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent

/**
 * Created by Manish Patel on 5/22/2019.
 */
//https://riggaroo.co.za/android-architecture-components-looking-lifecycles-part-3/

class LifeCycleComponent : LifecycleObserver {

    private val TAG by lazy { LifeCycleComponent::class.java.simpleName }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreateEvent() {
        //ToastManager.getInstance().showToast("ON_CREATE")
        Log.d(TAG, "Observer ON_CREATE")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStartEvent() {
        //ToastManager.getInstance().showToast("ON_START")
        Log.d(TAG, "Observer ON_START")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStopEvent() {
        //ToastManager.getInstance().showToast("ON_STOP")
        Log.d(TAG, "Observer ON_STOP")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPauseEvent() {
        //ToastManager.getInstance().showToast("ON_PAUSE")
        Log.d(TAG, "Observer ON_PAUSE")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResumeEvent() {
        //ToastManager.getInstance().showToast("ON_RESUME")
        Log.d(TAG, "Observer ON_RESUME")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroyEvent() {
        //ToastManager.getInstance().showToast("ON_DESTROY")
        Log.d(TAG, "Observer ON_DESTROY")
    }
}
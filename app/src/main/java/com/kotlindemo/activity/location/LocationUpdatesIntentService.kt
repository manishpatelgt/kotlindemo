package com.kotlindemo.activity.location

import android.app.IntentService
import android.content.Intent
import com.google.android.gms.location.LocationResult

/**
 * Created by Manish Patel on 7/5/2019.
 */
class LocationUpdatesIntentService : IntentService("LocationUpdatesIntentService") {

    companion object {
        val ACTION_PROCESS_UPDATES = " com.kotlindemo.activity.location.action" + ".PROCESS_UPDATES"
    }

    private val TAG = LocationUpdatesIntentService::class.java.simpleName

    override fun onHandleIntent(intent: Intent?) {

        if (intent != null) {
            val action = intent.action
            if (ACTION_PROCESS_UPDATES == action) {
                val result = LocationResult.extractResult(intent)
                if (result != null) {
                    val locations = result.locations
                    println("locations: ${locations.toString()}")
                }
            }
        }
    }

}
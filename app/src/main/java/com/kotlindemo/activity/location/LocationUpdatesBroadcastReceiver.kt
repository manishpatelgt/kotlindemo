package com.kotlindemo.activity.location

import android.content.BroadcastReceiver
import android.content.Context
import com.google.android.gms.location.LocationResult
import android.content.Intent
import android.util.Log
import org.slf4j.LoggerFactory


/**
 * Created by Manish Patel on 7/5/2019.
 */
class LocationUpdatesBroadcastReceiver : BroadcastReceiver() {

    companion object {
        private val logger = LoggerFactory.getLogger(LocationUpdatesBroadcastReceiver::class.java)
        val ACTION_PROCESS_UPDATES = "com.kotlindemo.activity.location.action.PROCESS_UPDATES"
    }

    override fun onReceive(context: Context, intent: Intent?) {
        if (intent != null) {
            val action = intent.action
            if (ACTION_PROCESS_UPDATES.equals(action)) {
                val result = LocationResult.extractResult(intent)
                if (result != null) {
                    val locations = result.locations
                    val results = LocationResultHelper.getLocationResultText(locations)
                    logger.debug("Locations: $results")
                }
            }
        }
    }
}
package com.kotlindemo.activity.otherthings.location

import android.app.IntentService
import android.content.Intent
import com.google.android.gms.location.LocationResult
import org.slf4j.LoggerFactory

/**
 * Created by Manish Patel on 7/5/2019.
 */
class LocationUpdatesIntentService : IntentService("LocationUpdatesIntentService") {

    companion object {
        private val logger = LoggerFactory.getLogger(LocationUpdatesIntentService::class.java)
        val ACTION_PROCESS_UPDATES = " com.kotlindemo.activity.otherthings.location.action.PROCESS_UPDATES"
    }

    override fun onHandleIntent(intent: Intent?) {

        if (intent != null) {
            val action = intent.action
            if (ACTION_PROCESS_UPDATES == action) {
                val result = LocationResult.extractResult(intent)
                if (result != null) {
                    val locations = result.locations
                    val results = LocationResultHelper.getLocationResultText(locations)
                    logger.debug("Locations: $results")
                    //println("locations: ${locations.toString()}")
                }
            }
        }
    }

}
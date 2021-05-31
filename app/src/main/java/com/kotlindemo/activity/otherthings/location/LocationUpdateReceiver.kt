package com.kotlindemo.activity.otherthings.location

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import com.kotlindemo.application.DemoApplication
import org.slf4j.LoggerFactory


/**
 * Created by Manish Patel on 12/10/2019.
 */
class LocationUpdateReceiver : BroadcastReceiver() {

    companion object {
        private val logger = LoggerFactory.getLogger(LocationUpdateReceiver::class.java)
    }

    override fun onReceive(context: Context, intent: Intent?) {
        if (intent != null) {
            println("inside LocationUpdateReceiver")
            val action = intent.action
            if (GPSService.LOCATION_RECEIVED == action) {
                val locationKey = LocationManager.KEY_LOCATION_CHANGED
                if (intent.hasExtra(locationKey)) {
                    val location = intent.extras[locationKey] as Location
                    if (location != null) {
                        println("inside location != null")
                        DemoApplication.currentLocation = location
                    }
                }
                /*val location: Location = intent.extras.getParcelable("com.google.android.location.LOCATION")
                if (location != null) {
                    println("inside location != null")
                    DemoApplication.currentLocation = location
                }*/
            }
        }
    }
}
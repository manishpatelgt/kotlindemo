package com.kotlindemo.activity.otherthings.location

import android.app.PendingIntent
import android.content.Intent
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.kotlindemo.application.DemoApplication.Companion.context
import com.kotlindemo.utility.ToastManager
import org.slf4j.LoggerFactory

/**
 * Created by Manish Patel on 7/24/2019.
 */
class Utils {

    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var locationRequest: LocationRequest? = null
    private var currentlyProcessingLocation = false

    companion object {

        private val logger = LoggerFactory.getLogger(Utils::class.java)

        // @Volatile - Writes to this property are immediately visible to other threads
        @Volatile
        private var instance: Utils? = null

        // The only way to get hold of the FakeDatabase object
        fun getInstance() =
        // Already instantiated? - return the instance
            // Otherwise instantiate in a thread-safe manner
            instance ?: synchronized(this) {
                // If it's still not instantiated, finally create an object
                // also set the "instance" property to be the currently created one
                instance ?: Utils().also { instance = it }
            }
    }


    fun startTracking() {
        currentlyProcessingLocation = true
        startLocationUpdates()
        requestLocationUpdates()
    }

    fun stopTracking() {
        stopLocationUpdates()
    }

    /**
     * Removes location updates. Note that in this sample we merely log the
     * [SecurityException].
     *
     */
    fun stopLocationUpdates() {
        try {
            logger.error("Stopping location updates")
            if (mFusedLocationClient != null) {
                mFusedLocationClient!!.removeLocationUpdates(pendingIntent)
            }
        } catch (e: SecurityException) {
            e.printStackTrace()
            logger.error("Lost location permission. Could not request updates: " + e.message)
        }
    }


    /**
     * Sets the location request parameters.
     */
    private fun createLocationRequest() {
        locationRequest = LocationRequest()
        locationRequest?.let {
            it.priority = LocationRequest.PRIORITY_LOW_POWER
            it.maxWaitTime = 5000.toLong()
            it.interval = 5000.toLong()  // 5 seconds, in milliseconds
            it.fastestInterval =
                5000.toLong() // the fastest rate in milliseconds at which your app can handle location updates // 1 second, in milliseconds
        }
    }

    // Trigger new location updates at an interval
    private fun startLocationUpdates() {
        try {
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
            // create location request
            createLocationRequest()
        } catch (e: SecurityException) {
            e.printStackTrace()
            logger.error("Lost location permission. Could not request updates: " + e.message)
        }

    }

    /**
     * Makes a request for location updates. Note that in this sample we merely log the
     * [SecurityException].
     */
    fun requestLocationUpdates() {
        logger.debug("Requesting location updates")
        try {
            mFusedLocationClient!!.requestLocationUpdates(locationRequest, pendingIntent)
        } catch (e: SecurityException) {
            e.printStackTrace()
            logger.error("Lost location permission. Could not request updates: " + e.message)
        }

    }

    private val pendingIntent: PendingIntent
        get() {
            val intent = Intent(context, LocationUpdatesBroadcastReceiver::class.java)
            intent.action = LocationUpdatesBroadcastReceiver.ACTION_PROCESS_UPDATES
            return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }

}
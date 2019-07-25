package com.kotlindemo.activity.otherthings.location

import android.app.PendingIntent
import android.content.Intent
import android.location.Location
import android.os.Looper
import com.google.android.gms.location.*
import com.kotlindemo.application.DemoApplication
import com.kotlindemo.application.DemoApplication.Companion.context
import com.kotlindemo.utility.ToastManager
import org.slf4j.LoggerFactory

/**
 * Created by Manish Patel on 7/24/2019.
 */
//https://developer.android.com/guide/topics/location/battery
class Utils {

    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var locationRequest: LocationRequest? = null
    private var currentlyProcessingLocation = false
    private var currentLocation: Location? = null
    private var mLocationCallback: LocationCallback? = null

    private val fastInterval = 2 * 60 * 1000
    private val normalInterval = 15 * 60 * 1000
    private val maxWaitInterval = 60 * 60 * 1000

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
                mFusedLocationClient!!.removeLocationUpdates(mLocationCallback)
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
            it.maxWaitTime = maxWaitInterval.toLong()
            it.interval = normalInterval.toLong()  // 5 seconds, in milliseconds
            it.fastestInterval =
                fastInterval.toLong() // the fastest rate in milliseconds at which your app can handle location updates // 1 second, in milliseconds
        }
    }

    // Trigger new location updates at an interval
    private fun startLocationUpdates() {
        try {
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

            mLocationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult?) {
                    super.onLocationResult(locationResult)
                    onNewLocation(locationResult!!.lastLocation)
                }
            }

            // create location request
            createLocationRequest()

            // get last known location
            getLastLocation()
        } catch (e: SecurityException) {
            e.printStackTrace()
            logger.error("Lost location permission. Could not request updates: " + e.message)
        }

    }

    /**
     * get last location
     */
    private fun getLastLocation() {
        try {
            mFusedLocationClient!!.lastLocation.addOnCompleteListener { task ->
                if (task.isSuccessful && task.result != null) {
                    currentLocation = task.result
                    onNewLocation(currentLocation)
                } else {
                    logger.error("Failed to get location")
                }
            }
        } catch (e: SecurityException) {
            e.printStackTrace()
            logger.error("Lost location permission: " + e.message)
        }

    }

    private fun onNewLocation(location: Location?) {
        currentLocation = location
        DemoApplication.currentLocation = currentLocation
        logger.debug("FusedLocationService onNewLocation: ${location.toString()}")
    }

    /**
     * Makes a request for location updates. Note that in this sample we merely log the
     * [SecurityException].
     */
    fun requestLocationUpdates() {
        logger.debug("Requesting location updates")
        try {
            mFusedLocationClient!!.requestLocationUpdates(locationRequest, mLocationCallback!!, Looper.myLooper())
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
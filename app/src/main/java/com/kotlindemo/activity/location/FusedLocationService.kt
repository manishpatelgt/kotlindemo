package com.kotlindemo.activity.location

import android.app.Service
import android.content.Intent
import android.location.Location
import android.os.Binder
import android.os.Build
import android.os.IBinder
import com.google.android.gms.location.*
import org.slf4j.LoggerFactory
import android.app.PendingIntent

/**
 * Created by Manish Patel on 1/10/2019.
 */

class FusedLocationService : Service() {

    /**
     * Provides access to the Fused Location Provider API.
     */
    private var mFusedLocationClient: FusedLocationProviderClient? = null

    /**
     * Stores parameters for requests to the FusedLocationProviderApi.
     */
    private var locationRequest: LocationRequest? = null

    /**
     * Represents a geographical location.
     */
    private var currentLocation: Location? = null
    /**
     * Callback for changes in location.
     */
    private var mLocationCallback: LocationCallback? = null
    private var currentlyProcessingLocation = false

    private val mBinder = LocalBinder()

    override fun onBind(intent: Intent): IBinder? {
        logger.debug("FusedLocationService onBind")
        return mBinder
    }

    override fun onCreate() {
        super.onCreate()
        logger.debug("FusedLocationService onCreate")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationHelper = NotificationHelper(this)
            val foregroundNotification =
                notificationHelper.getForegroundServiceNotification("Service Running", "GPS Location updated.", null)
            startForeground(NotificationHelper.SERVICE_RUNNING_NOTIFICATION, foregroundNotification)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        logger.debug("FusedLocationService onStartCommand")
        return Service.START_NOT_STICKY
    }

    fun startTracking() {
        // no need to start processing a new location.
        //if (!currentlyProcessingLocation) {
        currentlyProcessingLocation = true
        startLocationUpdates()
        requestLocationUpdates()
        //}
    }

    fun stopTracking() {
        stopLocationUpdates()
        stopForeground(true)
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        currentlyProcessingLocation = false
        logger.error("FusedLocationService onDestroy")
        stopLocationUpdates()
        stopForeground(true)
        stopSelf()
    }

    override fun onTaskRemoved(rootIntent: Intent) {
        currentlyProcessingLocation = false
        stopLocationUpdates()
        stopForeground(true)
        stopSelf()
        super.onTaskRemoved(rootIntent)
    }

    // Trigger new location updates at an interval
    private fun startLocationUpdates() {
        try {
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
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

    /**
     * Makes a request for location updates. Note that in this sample we merely log the
     * [SecurityException].
     */
    fun requestLocationUpdates() {
        logger.debug("Requesting location updates")
        try {
            mFusedLocationClient!!.requestLocationUpdates(locationRequest, getPendingIntent())
        } catch (e: SecurityException) {
            e.printStackTrace()
            logger.error("Lost location permission. Could not request updates: " + e.message)
        }

    }

    private fun getPendingIntent(): PendingIntent {
        val intent = Intent(this, LocationUpdatesIntentService::class.java)
        intent.action = LocationUpdatesIntentService.ACTION_PROCESS_UPDATES
        return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }
    /**
     * Sets the location request parameters.
     */
    private fun createLocationRequest() {
        locationRequest = LocationRequest()
        locationRequest?.let {
            it.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
            it.interval = 5000.toLong()  // 5 seconds, in milliseconds
            it.fastestInterval = 5000.toLong() // the fastest rate in milliseconds at which your app can handle location updates // 1 second, in milliseconds
        }
    }

    private fun onNewLocation(location: Location?) {
        currentLocation = location
        logger.debug("FusedLocationService onNewLocation: ${location.toString()}")
    }

    /**
     * Removes location updates. Note that in this sample we merely log the
     * [SecurityException].
     */
    fun stopLocationUpdates() {
        try {
            if (mFusedLocationClient != null) {
                mFusedLocationClient!!.removeLocationUpdates(mLocationCallback!!)
            }
        } catch (e: SecurityException) {
            e.printStackTrace()
            logger.error("Lost location permission. Could not request updates: " + e.message)
        }

    }

    inner class LocalBinder : Binder() {
        val service: FusedLocationService
            get() = this@FusedLocationService
    }

    companion object {
        private val logger = LoggerFactory.getLogger(FusedLocationService::class.java)
    }
}
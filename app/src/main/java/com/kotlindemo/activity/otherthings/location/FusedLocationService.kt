package com.kotlindemo.activity.otherthings.location

import android.app.Service
import android.content.Intent
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

    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var locationRequest: LocationRequest? = null
    private var currentlyProcessingLocation = false
    private val mBinder = LocalBinder()

    override fun onBind(intent: Intent): IBinder? {
        logger.debug("FusedLocationService onBind")
        return mBinder
    }

    override fun onCreate() {
        super.onCreate()
        logger.debug("FusedLocationService onCreate")
        startTracking()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationHelper = NotificationHelper(this)
            val foregroundNotification =
                notificationHelper.getForegroundServiceNotification("Service Running", "GPS Location updated.", null)
            startForeground(NotificationHelper.SERVICE_RUNNING_NOTIFICATION, foregroundNotification)
        }
        startTracking()
        logger.debug("FusedLocationService onStartCommand")
        return Service.START_STICKY
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
        stopTracking()
    }

    override fun onTaskRemoved(rootIntent: Intent) {
        logger.error("FusedLocationService onTaskRemoved")
        currentlyProcessingLocation = false
        stopTracking()
        super.onTaskRemoved(rootIntent)
    }

    // Trigger new location updates at an interval
    private fun startLocationUpdates() {
        try {
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
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
            val intent = Intent(this, LocationUpdatesBroadcastReceiver::class.java)
            intent.action = LocationUpdatesBroadcastReceiver.ACTION_PROCESS_UPDATES
            return PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }

    private fun getPendingIntent3(): PendingIntent {
        // Note: for apps targeting API level 25 ("Nougat") or lower, either
        // PendingIntent.getService() or PendingIntent.getBroadcast() may be used when requesting
        // location updates. For apps targeting API level O, only
        // PendingIntent.getBroadcast() should be used. This is due to the limits placed on services
        // started in the background in "O".

        // TODO(developer): uncomment to use PendingIntent.getService().
        //        Intent intent = new Intent(this, LocationUpdatesIntentService.class);
        //        intent.setAction(LocationUpdatesIntentService.ACTION_PROCESS_UPDATES);
        //        return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        val intent = Intent(this, LocationUpdatesBroadcastReceiver::class.java)
        intent.action = LocationUpdatesBroadcastReceiver.ACTION_PROCESS_UPDATES
        return PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private fun getPendingIntent2(): PendingIntent {
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
            it.fastestInterval =
                5000.toLong() // the fastest rate in milliseconds at which your app can handle location updates // 1 second, in milliseconds
        }
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

    inner class LocalBinder : Binder() {
        val service: FusedLocationService
            get() = this@FusedLocationService
    }

    companion object {
        private val logger = LoggerFactory.getLogger(FusedLocationService::class.java)
    }
}
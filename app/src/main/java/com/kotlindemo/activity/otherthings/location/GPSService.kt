package com.kotlindemo.activity.otherthings.location

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import com.kotlindemo.application.DemoApplication
import org.jetbrains.anko.doFromSdk
import org.slf4j.LoggerFactory

/**
 * Created by Manish Patel on 12/10/2019.
 */
class GPSService : Service() {

    /** https://medium.com/@rotxed/going-multiprocess-on-android-52975ed8863c **/

    private lateinit var locationManager: LocationManager
    val networkProvider: String = LocationManager.NETWORK_PROVIDER
    val gpsProvider: String = LocationManager.GPS_PROVIDER

    private val pendingIntent: PendingIntent
        get() {
            val intent = Intent(this, LocationUpdateReceiver::class.java)
            intent.action = LOCATION_RECEIVED
            return PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }

    override fun onCreate() {
        super.onCreate()
        logger.debug("GPSService onCreate")
        // Acquire a reference to the system Location Manager
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        // show foreground notification (required)
        doFromSdk(Build.VERSION_CODES.O) {
            val notificationHelper = NotificationHelper(this)
            val foregroundNotification =
                notificationHelper.getForegroundServiceNotification(
                    "Service Running",
                    "GPS Location updated.",
                    null
                )
            startForeground(NotificationHelper.SERVICE_RUNNING_NOTIFICATION, foregroundNotification)
        }
    }

    @SuppressLint("MissingPermission")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        logger.debug("onStartCommand executed with startId: $startId")

        // Register the listener with the Location Manager to receive location updates
        locationManager.requestLocationUpdates(
            networkProvider,
            0,
            0f,
            pendingIntent
        )
        locationManager.requestLocationUpdates(
            gpsProvider,
            0,
            0f,
            pendingIntent
        )

        /** you can use last Known location as it takes time for your location  listener to receive the first location fix is often too long for users wait **/
        currentLocation = locationManager.getLastKnownLocation(networkProvider)
        return START_STICKY
    }

    var previousBestLocation: Location? = null
    var currentLocation: Location? = null

    // Define a listener that responds to location updates
    val locationListener = object : LocationListener {

        override fun onLocationChanged(location: Location) {
            /*if (isBetterLocation(location, previousBestLocation)) {
                previousBestLocation = location
            }*/
            logger.debug("New Location: $location")
            currentLocation = location
            /*DemoApplication.currentLocation = location
            val intent = Intent(LOCATION_RECEIVED)
            intent.putExtra(INTENT_EXTRA_LOCATION, currentLocation)
            DemoApplication.context.sendBroadcast(intent)*/
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
        }

        override fun onProviderEnabled(provider: String) {
        }

        override fun onProviderDisabled(provider: String) {
        }
    }

    private val TWO_MINUTES: Long = 1000 * 60 * 2

    /** Determines whether one Location reading is better than the current Location fix
     * @param location The new Location that you want to evaluate
     * @param currentBestLocation The current Location fix, to which you want to compare the new one
     */
    fun isBetterLocation(location: Location, currentBestLocation: Location?): Boolean {
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true
        }

        // Check whether the new location fix is newer or older
        val timeDelta: Long = location.time - currentBestLocation.time
        val isSignificantlyNewer: Boolean = timeDelta > TWO_MINUTES
        val isSignificantlyOlder: Boolean = timeDelta < -TWO_MINUTES

        when {
            // If it's been more than two minutes since the current location, use the new location
            // because the user has likely moved
            isSignificantlyNewer -> return true
            // If the new location is more than two minutes older, it must be worse
            isSignificantlyOlder -> return false
        }

        // Check whether the new location fix is more or less accurate
        val isNewer: Boolean = timeDelta > 0L
        val accuracyDelta: Float = location.accuracy - currentBestLocation.accuracy
        val isLessAccurate: Boolean = accuracyDelta > 0f
        val isMoreAccurate: Boolean = accuracyDelta < 0f
        val isSignificantlyLessAccurate: Boolean = accuracyDelta > 200f

        // Check if the old and new location are from the same provider
        val isFromSameProvider: Boolean = location.provider == currentBestLocation.provider

        // Determine location quality using a combination of timeliness and accuracy
        return when {
            isMoreAccurate -> true
            isNewer && !isLessAccurate -> true
            isNewer && !isSignificantlyLessAccurate && isFromSameProvider -> true
            else -> false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Remove the listener you previously added
        locationManager.removeUpdates(pendingIntent)
        logger.debug("The service has been destroyed".toUpperCase())
    }

    override fun onBind(intent: Intent): IBinder? {
        logger.debug("Some component want to bind with the service")
        // We don't provide binding, so return null
        return null
    }

    companion object {
        private val logger = LoggerFactory.getLogger(GPSService::class.java)
        val LOCATION_RECEIVED = "fused.location.received"
        val INTENT_EXTRA_LOCATION = "LOCATION"
    }
}
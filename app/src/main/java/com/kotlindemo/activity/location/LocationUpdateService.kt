package com.kotlindemo.activity.location

import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import com.kotlindemo.application.DemoApplication
import com.kotlindemo.utility.NetworkHelper
import org.jetbrains.anko.doFromSdk
import org.slf4j.LoggerFactory
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

/**
 * Created by Manish Patel on 7/8/2019.
 */
class LocationUpdateService : Service() {

    private var scheduler: ScheduledExecutorService? = Executors.newSingleThreadScheduledExecutor()

    private val saveHHLocation = Runnable {
        logger.debug("inside saveHHLocation")
        if (NetworkHelper.connectedToNetwork(applicationContext)) {
            logger.debug("Internet found")
            DemoApplication.currentLocation?.let {
                logger.debug("Location ready to send ${DemoApplication.currentLocation.toString()}")
            }
        } else {
            logger.debug("Internet not found")
        }
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        logger.debug("LocationUpdateService onStartCommand")

        // show foreground notification (required)
        doFromSdk(Build.VERSION_CODES.O) {
            val notificationHelper = NotificationHelper(this)
            val foregroundNotification =
                notificationHelper.getForegroundServiceNotification("Service Running", "GPS Location updated.", null)
            startForeground(NotificationHelper.SERVICE_RUNNING_NOTIFICATION, foregroundNotification)
        }

        scheduler?.let { it.shutdown() }
        scheduler = Executors.newSingleThreadScheduledExecutor()
        scheduler!!.scheduleWithFixedDelay(saveHHLocation, 0, 10, TimeUnit.SECONDS)
        // If we get killed, after returning from here, restart
        return Service.START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        logger.debug("LocationUpdateService onCreate")
    }

    override fun onDestroy() {
        super.onDestroy()

        logger.error("LocationUpdateService onDestroy")
        if (scheduler != null) {
            scheduler!!.shutdownNow()
        }
        stopForeground(true)
        stopSelf()
    }

    override fun onTaskRemoved(rootIntent: Intent) {
        if (scheduler != null) {
            scheduler!!.shutdownNow()
        }
        stopSelf()
        super.onTaskRemoved(rootIntent)
    }

    companion object {
        private val logger = LoggerFactory.getLogger(LocationUpdateService::class.java)
    }

}
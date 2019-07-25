package com.kotlindemo.activity.otherthings.location

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.os.PowerManager
import android.widget.Toast
import com.kotlindemo.application.DemoApplication
import com.kotlindemo.utility.NetworkHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.anko.doFromSdk
import org.slf4j.LoggerFactory
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

/**
 * Created by Manish Patel on 7/24/2019.
 */
class EndlessService : Service() {

    private var wakeLock: PowerManager.WakeLock? = null
    private var isServiceStarted = false

    override fun onBind(intent: Intent): IBinder? {
        logger.debug("Some component want to bind with the service")
        // We don't provide binding, so return null
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        logger.debug("onStartCommand executed with startId: $startId")
        if (intent != null) {
            val action = intent.action
            logger.debug("using an intent with action $action")
            when (action) {
                Actions.START.name -> startService()
                Actions.STOP.name -> stopService()
                else -> logger.debug("This should never happen. No action in the received intent")
            }
        } else {
            logger.debug("with a null intent. It has been probably restarted by the system.")
        }
        // by returning this we make sure the service is restarted if the system kills the service
        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        logger.debug("The service has been created".toUpperCase())
        // show foreground notification (required)
        doFromSdk(Build.VERSION_CODES.O) {
            val notificationHelper = NotificationHelper(this)
            val foregroundNotification =
                notificationHelper.getForegroundServiceNotification("Service Running", "GPS Location updated.", null)
            startForeground(NotificationHelper.SERVICE_RUNNING_NOTIFICATION, foregroundNotification)
        }
    }

    private fun startService() {
        if (isServiceStarted) return
        logger.debug("Starting the foreground service task")
        Toast.makeText(this, "Service starting its task", Toast.LENGTH_SHORT).show()
        isServiceStarted = true
        setServiceState(this, ServiceState.STARTED)

        // we need this lock so our service gets not affected by Doze Mode
        wakeLock =
            (getSystemService(Context.POWER_SERVICE) as PowerManager).run {
                newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "EndlessService::lock").apply {
                    acquire()
                }
            }

        scheduler?.let { it.shutdown() }
        scheduler = Executors.newSingleThreadScheduledExecutor()
        scheduler!!.scheduleAtFixedRate(saveHHLocation, 0, 10, TimeUnit.SECONDS)

        // we're starting a loop in a coroutine
        /*GlobalScope.launch(Dispatchers.IO) {
            while (isServiceStarted) {
                launch(Dispatchers.IO) {
                    pingFakeServer()
                }
                delay(1 * 60 * 1000)
            }
            logger.debug("End of the loop for the service")
        }*/
    }

    private var scheduler: ScheduledExecutorService? = Executors.newSingleThreadScheduledExecutor()

    val saveHHLocation = Runnable {
        logger.debug("inside saveHHLocation")
        logger.debug("Location ready to send ${DemoApplication.currentLocation.toString()}")
    }

    private fun pingFakeServer() {
        logger.debug("pingFakeServer")
    }

    override fun onTaskRemoved(rootIntent: Intent) {
        logger.error("EndlessService onTaskRemoved")
        stopService()
        super.onTaskRemoved(rootIntent)
    }

    private fun stopService() {
        logger.debug("Stopping the foreground service")
        Toast.makeText(this, "Service stopping", Toast.LENGTH_SHORT).show()
        try {
            wakeLock?.let {
                if (it.isHeld) {
                    it.release()
                }
            }
            stopForeground(true)
            stopSelf()
        } catch (e: Exception) {
            logger.error("Service stopped without being started: ${e.message}")
        }
        isServiceStarted = false
        setServiceState(this, ServiceState.STOPPED)
    }

    override fun onDestroy() {
        super.onDestroy()
        scheduler?.let {
            scheduler!!.shutdown()
        }
        logger.debug("The service has been destroyed".toUpperCase())
    }

    companion object {
        private val logger = LoggerFactory.getLogger(EndlessService::class.java)
    }
}
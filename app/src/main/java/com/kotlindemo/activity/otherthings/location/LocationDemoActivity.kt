package com.kotlindemo.activity.otherthings.location

import android.Manifest
import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.net.Uri
import android.os.Bundle
import android.os.IBinder
import android.provider.Settings
import android.widget.Toast
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.kotlindemo.R
import com.kotlindemo.activity.architecture.room.NewWordActivity
import com.kotlindemo.activity.architecture.room.RoomActivity
import com.kotlindemo.activity.architecture.room.entity.Word
import com.kotlindemo.application.DemoApplication
import com.kotlindemo.application.ParentActivity
import com.kotlindemo.utility.ToastManager
import com.kotlindemo.utility.isAtLeastAndroid8
import kotlinx.android.synthetic.main.activity_location.*
import org.slf4j.LoggerFactory

/**
 * Created by Manish Patel on 7/5/2019.
 */
//https://codelabs.developers.google.com/codelabs/background-location-updates-android-o/index.html?index=..%2F..index#0
//https://stackoverflow.com/questions/48170499/updating-location-using-fusedlocationproviderclient-jobscheduler-and-jobservice
//https://github.com/googlecodelabs/background-location-updates-android-o/blob/master/BackgroundLocationUpdates/app/src/main/java/com/google/android/gms/location/sample/backgroundlocationupdates/MainActivity.java
//https://github.com/googlesamples/android-play-location/blob/master/LocationUpdatesPendingIntent/app/src/main/java/com/google/android/gms/location/sample/locationupdatespendingintent/MainActivity.java
//https://github.com/googlesamples/android-play-location
//https://developer.android.com/preview/privacy/device-location
//https://www.freakyjolly.com/android-background-geolocation-service-without-any-kill/
//https://robertohuertas.com/2019/06/29/android_foreground_services/

class LocationDemoActivity : ParentActivity() {

    companion object {
        private val logger = LoggerFactory.getLogger(LocationDemoActivity::class.java)
        var gpsService: FusedLocationService? = null
        var mTracking = false
        val TAG: String = LocationDemoActivity.javaClass::class.java.simpleName

        private val POWER_WHITELIST = 1925
        private val DATA_SAVER_WHITELIST = 1926
        private val BATTERY_SAVER_WHITELIST = 1927
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        startActivityForResult(
            Intent(
                Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS,
                Uri.parse("package:$packageName")
            ), BATTERY_SAVER_WHITELIST
        )

        startButton.setOnClickListener {
            startLocationService()

            logger.debug("START THE FOREGROUND SERVICE ON DEMAND")
            actionOnService(Actions.START)

            //startLocationUpdateService()
            mTracking = true
            toggleButtons()
        }

        stopButton.setOnClickListener {
            mTracking = false
            stopLocationService()

            logger.debug("STOP THE FOREGROUND SERVICE ON DEMAND")
            actionOnService(Actions.STOP)

            toggleButtons()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            DATA_SAVER_WHITELIST -> {
                if (resultCode == Activity.RESULT_OK) {
                } else {
                    ToastManager.getInstance().showToast("DATA_SAVER_WHITELIST RESULT_CANCELED")
                }
            }
            BATTERY_SAVER_WHITELIST -> ToastManager.getInstance().showToast("BATTERY_SAVER_WHITELIST")
        }
    }


    private fun actionOnService(action: Actions) {
        if (getServiceState(this) == ServiceState.STOPPED && action == Actions.STOP) return
        Intent(this, EndlessService::class.java).also {
            it.action = action.name
            if (isAtLeastAndroid8()) {
                logger.debug("Starting the service in >=26 Mode")
                startForegroundService(it)
                return
            }
            logger.debug("Starting the service in < 26 Mode")
            startService(it)
        }
    }

    fun stopLocationService() {
        Utils.getInstance().stopTracking()

        //timer task
        //DemoApplication.getInstance().stopLocationTimerScheduler()

        //startScheduler
        //DemoApplication.getInstance().stopScheduler()

        //stopService(Intent(applicationContext, FusedLocationService::class.java))
        //stopService(Intent(applicationContext, LocationUpdateService::class.java))
    }

    fun startLocationService() {

        Utils.getInstance().startTracking()

        //timer task
        //DemoApplication.getInstance().startLocationTimerScheduler()

        //startScheduler
        //DemoApplication.getInstance().startScheduler()

        /*if (isAtLeastAndroid8()) {
            startForegroundService(Intent(applicationContext, FusedLocationService::class.java))
        } else {
            startService(Intent(applicationContext, FusedLocationService::class.java))
        }*/

        /*val intent = Intent(this.application, FusedLocationService::class.java)
        this.application.startService(intent)
    //        this.getApplication().startForegroundService(intent);
        this.application.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)*/
    }

    fun startLocationUpdateService() {

        if (isAtLeastAndroid8()) {
            startForegroundService(Intent(applicationContext, LocationUpdateService::class.java))
        } else {
            startService(Intent(applicationContext, LocationUpdateService::class.java))
        }
    }

    private fun toggleButtons() {
        startButton.isEnabled = !mTracking
        stopButton.isEnabled = mTracking
        statusTextView.text = if (mTracking) "TRACKING" else "GPS Ready"
    }

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val name = className.className
            if (name.endsWith("FusedLocationService")) {
                gpsService = (service as FusedLocationService.LocalBinder).service
                startButton.isEnabled = true
                statusTextView.text = "GPS Ready"
            }
        }

        override fun onServiceDisconnected(className: ComponentName) {
            if (className.className == "FusedLocationService") {
                gpsService = null
            }
        }
    }

    override fun onResume() {
        super.onResume()

        Dexter.withActivity(this)
            .withPermissions(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    ToastManager.getInstance().showToast("All permission granted")
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest>,
                    token: PermissionToken
                ) {
                    token.continuePermissionRequest()
                }
            }).check()

    }
}
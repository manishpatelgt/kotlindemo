package com.kotlindemo.activity.location

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.kotlindemo.R
import com.kotlindemo.application.ParentActivity
import com.kotlindemo.utility.ToastManager
import kotlinx.android.synthetic.main.activity_location.*

/**
 * Created by Manish Patel on 7/5/2019.
 */
class LocationDemoActivity : ParentActivity() {

    companion object {
        var gpsService: FusedLocationService? = null
        var mTracking = false
        val TAG: String = LocationDemoActivity.javaClass::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)
        setSupportActionBar(toolbar)
        startLocationService()

        startButton.setOnClickListener {
            gpsService?.startTracking()
            mTracking = true
            toggleButtons()
        }

        stopButton.setOnClickListener {
            mTracking = false
            gpsService?.stopTracking()
            toggleButtons()
        }
    }

    fun startLocationService() {
        val intent = Intent(this.application, FusedLocationService::class.java)
        this.application.startService(intent)
//        this.getApplication().startForegroundService(intent);
        this.application.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
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
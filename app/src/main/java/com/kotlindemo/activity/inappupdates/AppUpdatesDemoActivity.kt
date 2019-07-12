package com.kotlindemo.activity.inappupdates

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.kotlindemo.R
import com.kotlindemo.activity.MainActivity
import com.kotlindemo.application.ParentActivity
import kotlinx.android.synthetic.main.activity_app_updates.*

/**
 * Created by Manish Patel on 7/12/2019.
 */
//#In-App update
//https://medium.com/@vigneshmca2k13/implementing-and-testing-in-app-update-in-android-d52680ecdcc7
//https://blog.mindorks.com/implementing-in-app-updates-on-android
//https://stackoverflow.com/questions/55939853/how-to-work-with-androids-in-app-update-api

class AppUpdatesDemoActivity : ParentActivity() {

    companion object {
        val TAG: String = AppUpdatesDemoActivity.javaClass::class.java.simpleName
        const val MY_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_updates)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setupUI()
    }

    fun setupUI() {
        //In updated check
        inAppUpdateCheck()
    }

    var appUpdateManager: AppUpdateManager? = null
    var firebaseRemoteConfig = FirebaseRemoteConfig.getInstance()

    fun inAppUpdateCheck() {

        appUpdateManager = AppUpdateManagerFactory.create(this)

        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(0)
            .build()

        firebaseRemoteConfig.setConfigSettingsAsync(configSettings)
        firebaseRemoteConfig.setDefaults(R.xml.remote_config_defaults)
        firebaseRemoteConfig.fetchAndActivate().addOnCompleteListener(
            this
        ) { task ->
            if (task.isSuccessful) {
                checkforUpdate()
            } else {
                Log.e(TAG, " failed to fetch remote config params")
            }
        }
    }

    // Creates a listener for request status updates.
    private val installStateUpdatedListener = InstallStateUpdatedListener {
        if (it.installStatus() == InstallStatus.DOWNLOADED) {
            popupSnackbarForCompleteUpdate()
        } else if (it.installStatus() == InstallStatus.INSTALLED) {

        } else {
            Log.i(TAG, "InstallStateUpdatedListener: state: " + it.installStatus())
        }
    }

    fun checkforUpdate() {

        val app_update_type = firebaseRemoteConfig.getLong("app_update_type").toInt()
        Log.d(TAG, "app_update_type: $app_update_type")

        val appUpdateInfoTask = appUpdateManager?.appUpdateInfo
        appUpdateManager?.registerListener(installStateUpdatedListener)

        appUpdateInfoTask?.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
                appUpdateManager?.startUpdateFlowForResult(
                    appUpdateInfo,
                    app_update_type,
                    this,
                    MY_REQUEST_CODE
                )
            } else if (appUpdateInfo.installStatus() === InstallStatus.DOWNLOADED) {
                // After the update is downloaded, show a notification
                // and request user confirmation to restart the app.
                popupSnackbarForCompleteUpdate()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(TAG, "inside onActivityResult $resultCode")
        if (requestCode == MY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Log.e(TAG, "Update flow failed! Result code: $resultCode")
            }
        }
    }

    /*fun onStateUpdate(state: InstallState) {
        if (state.installStatus() === InstallStatus.DOWNLOADED) {
            // After the update is downloaded, show a notification
            // and request user confirmation to restart the app.
            popupSnackbarForCompleteUpdate()
        }
    }*/

    /* Displays the snackbar notification and call to action. */
    private fun popupSnackbarForCompleteUpdate() {
        val snackbar = Snackbar.make(
            findViewById(R.id.activity_main_layout),
            "An update has just been downloaded.", Snackbar.LENGTH_INDEFINITE
        )
        snackbar.setAction("RESTART") { appUpdateManager?.completeUpdate() }
        snackbar.setActionTextColor(resources.getColor(R.color.primary_dark_material_dark))
        snackbar.show()
    }

    override fun onStop() {
        super.onStop()
        appUpdateManager?.let {
            it.unregisterListener(installStateUpdatedListener)
        }
    }
}

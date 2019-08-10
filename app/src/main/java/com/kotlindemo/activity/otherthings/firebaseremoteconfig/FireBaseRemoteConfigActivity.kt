package com.kotlindemo.activity.otherthings.firebaseremoteconfig

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.kotlindemo.R
import com.kotlindemo.activity.otherthings.inappupdates.AppUpdatesDemoActivity
import com.kotlindemo.application.ParentActivity
import kotlinx.android.synthetic.main.activity_firebase_remote_config.*

/**
 * Created by Manish Patel on 8/10/2019.
 */
//https://blog.mindorks.com/getting-started-with-firebase-remote-config-in-android

class FireBaseRemoteConfigActivity : ParentActivity() {

    private var APP_BACKGROUND_COLOR_KEY = "app_background_color"
    private var APP_MAIN_TEXT_VIEW = "app_main_text_view"
    private var APP_SUB_TEXT_VIEW = "app_sub_text_view"

    var firebaseRemoteConfig = FirebaseRemoteConfig.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase_remote_config)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setupUI()
    }

    fun setupUI() {
        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(0)
            .build()

        firebaseRemoteConfig.setConfigSettingsAsync(configSettings)
        firebaseRemoteConfig.setDefaults(R.xml.remote_config_defaults)

        //remote_value_main_tv?.text = firebaseRemoteConfig?.getString(APP_MAIN_TEXT_VIEW)
        //remote_value_tv?.text = firebaseRemoteConfig?.getString(APP_SUB_TEXT_VIEW)

        getRemoteConfigValues()
    }

    private fun getRemoteConfigValues() {

        firebaseRemoteConfig.fetchAndActivate().addOnCompleteListener(
            this
        ) { task ->
            if (task.isSuccessful) {
                //changing the textview and backgorund color
                setRemoteConfigValues()
            } else {
                Log.e(AppUpdatesDemoActivity.TAG, " failed to fetch remote config params")
            }
        }
    }

    private fun setRemoteConfigValues() {
        val remoteValueMainText = firebaseRemoteConfig?.getString(APP_MAIN_TEXT_VIEW)
        val remoteValueSubText = firebaseRemoteConfig?.getString(APP_SUB_TEXT_VIEW)
        val remoteValueBackground = firebaseRemoteConfig?.getString(APP_BACKGROUND_COLOR_KEY)
        if (!remoteValueBackground.isNullOrEmpty()) {
            remote_value_main_tv?.text = remoteValueMainText
            remote_value_tv?.text = remoteValueSubText
            main_layout?.setBackgroundColor(Color.parseColor(remoteValueBackground))
        } else
            remote_value_main_tv?.text = "Failed to load values"
    }

}
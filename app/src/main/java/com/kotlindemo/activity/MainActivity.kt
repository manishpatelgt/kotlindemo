package com.kotlindemo.activity

import android.Manifest
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.kotlindemo.activity.coroutines.CoroutinesActivity
import com.kotlindemo.activity.databinding.DataBindingActivity
import com.kotlindemo.activity.databinding2.DataBindingActivity2
import com.kotlindemo.activity.lifecycle.LifeCycleActivity
import com.kotlindemo.activity.livedata.LiveDataActivity
import com.kotlindemo.activity.location.LocationDemoActivity
import com.kotlindemo.activity.motion.MotionActivity
import com.kotlindemo.activity.mvc.view.MVCDemoActivity
import com.kotlindemo.activity.mvp.view.MVPDemoActivity
import com.kotlindemo.activity.mvvm.QuotesActivity
import com.kotlindemo.activity.retrofitdemo.RetrofitDemoActivity
import com.kotlindemo.activity.room.RoomActivity
import com.kotlindemo.activity.viewmodel.ViewModelActivity
import com.kotlindemo.activity.workermanager.WorkManagerActivity
import com.kotlindemo.appconstants.CompanionConsts
import com.kotlindemo.appconstants.Consts
import com.kotlindemo.application.ParentActivity
import com.kotlindemo.utility.ToastManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.startActivity
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import android.annotation.SuppressLint
import android.telephony.SubscriptionManager
import android.os.Build
import android.util.Log
import com.kotlindemo.R
import com.kotlindemo.activity.inappupdates.AppUpdatesDemoActivity

//https://proandroiddev.com/modern-android-development-with-kotlin-september-2017-part-1-f976483f7bd6
//https://www.techotopia.com/index.php/A_Guide_to_using_ConstraintLayout_in_Android_Studio
//https://medium.com/exploring-android/exploring-ktx-for-android-13a369795b51

//https://material.io/develop/android/components/floating-action-button/

//For learning Intent more
//https://www.raywenderlich.com/305-android-intents-tutorial-with-kotlin
//http://www.technoblogpost.com/splash-screen-on-kotlin-language/

//Kotlin Coroutines
//https://codelabs.developers.google.com/codelabs/kotlin-coroutines/index.html?index=..%2F..index#0


class MainActivity : ParentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        //textView?.text = "Say hello to Kotlin"

        mvc_button.setOnClickListener {
            startActivity<MVCDemoActivity>()
        }

        mvp_button.setOnClickListener {
            startActivity<MVPDemoActivity>()
        }

        room_button.setOnClickListener {
            startActivity<RoomActivity>()
        }

        mvvm_button.setOnClickListener {
            startActivity<QuotesActivity>()
        }

        livedata_button.setOnClickListener {
            startActivity<LiveDataActivity>()
        }

        lifecycledata_button.setOnClickListener {
            startActivity<LifeCycleActivity>()
        }

        viewmodel_button.setOnClickListener {
            startActivity<ViewModelActivity>()
        }

        databinding_button.setOnClickListener {
            startActivity<DataBindingActivity>()
        }

        databinding2_button.setOnClickListener {
            startActivity<DataBindingActivity2>()
        }

        work_button.setOnClickListener {
            startActivity<WorkManagerActivity>()
        }

        coroutin_button.setOnClickListener {
            startActivity<CoroutinesActivity>()
        }

        motion_button.setOnClickListener {
            startActivity<MotionActivity>()
        }

        retrofit_button.setOnClickListener {
            startActivity<RetrofitDemoActivity>()
        }

        retrofit_button_2.setOnClickListener {
            startActivity<com.kotlindemo.activity.retrofitdemo2.RetrofitDemoActivity>()
        }

        location_button.setOnClickListener {
            startActivity<LocationDemoActivity>()
        }

        app_update_button.setOnClickListener {
            startActivity<AppUpdatesDemoActivity>()
        }

        //From directly Object
        val pi = Consts.pi

        //From companion
        val pi2 = CompanionConsts.pi

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    //https://en.proft.me/2017/11/27/how-get-phone-number-programmatically-android/
    @SuppressLint("MissingPermission")
    fun readSimCards() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var subscriptionManager = getSystemService(SubscriptionManager::class.java)
            val subscription = subscriptionManager.activeSubscriptionInfoList
            for (i in subscription.indices) {
                val info = subscription[i]
                Log.d(TAG, "number " + info.number)
                Log.d(TAG, "network name : " + info.carrierName)
                Log.d(TAG, "country iso " + info.countryIso)
            }
        }
    }

    override fun onResume() {
        super.onResume()

        Dexter.withActivity(this)
            .withPermission(Manifest.permission.READ_PHONE_STATE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse) {
                    ToastManager.getInstance().showToast("Permission granted")
                    readSimCards()
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) {
                    // check for permanent denial of permission
                    if (response.isPermanentlyDenied) {
                        // navigate user to app settings
                    }
                }

                override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest, token: PermissionToken) {
                    token.continuePermissionRequest()
                }

            }).check()

    }

    companion object {
        val TAG: String = MainActivity.javaClass::class.java.simpleName
        const val MY_REQUEST_CODE = 1
    }
}

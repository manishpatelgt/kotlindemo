package com.kotlindemo.activity.networklibs

import android.os.Bundle
import com.kotlindemo.R
import com.kotlindemo.activity.networklibs.retrofitdemo.RetrofitDemoActivity
import com.kotlindemo.application.ParentActivity
import kotlinx.android.synthetic.main.activity_network_libs.*
import org.jetbrains.anko.startActivity

/**
 * Created by Manish Patel on 7/18/2019.
 */

class NetworkLibsActivity : ParentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_network_libs)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        retrofit_button.setOnClickListener {
            startActivity<RetrofitDemoActivity>()
        }

        retrofit_button_2.setOnClickListener {
            startActivity<com.kotlindemo.activity.networklibs.retrofitdemo2.RetrofitDemoActivity>()
        }

    }
}
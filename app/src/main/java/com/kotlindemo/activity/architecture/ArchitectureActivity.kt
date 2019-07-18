package com.kotlindemo.activity.architecture

import android.os.Bundle
import com.kotlindemo.R
import com.kotlindemo.activity.architecture.databinding.DataBindingActivity
import com.kotlindemo.activity.architecture.databinding2.DataBindingActivity2
import com.kotlindemo.activity.architecture.lifecycle.LifeCycleActivity
import com.kotlindemo.activity.architecture.livedata.LiveDataActivity
import com.kotlindemo.activity.architecture.navigation.NavigationDemoActivity
import com.kotlindemo.activity.architecture.room.RoomActivity
import com.kotlindemo.activity.architecture.viewmodel.ViewModelActivity
import com.kotlindemo.activity.architecture.workermanager.WorkManagerActivity
import com.kotlindemo.application.ParentActivity
import kotlinx.android.synthetic.main.activity_architecture.*
import org.jetbrains.anko.startActivity

/**
 * Created by Manish Patel on 7/18/2019.
 */
//https://developer.android.com/jetpack
class ArchitectureActivity : ParentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_architecture)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        room_button.setOnClickListener {
            startActivity<RoomActivity>()
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

        navigation_button.setOnClickListener {
            startActivity<NavigationDemoActivity>()
        }
    }

    companion object {
        val TAG: String = ArchitectureActivity.javaClass::class.java.simpleName
    }
}
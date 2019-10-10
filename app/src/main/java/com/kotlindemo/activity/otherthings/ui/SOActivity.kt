package com.kotlindemo.activity.otherthings.ui

import android.os.Bundle
import com.kotlindemo.R
import com.kotlindemo.application.ParentActivity
import com.kotlindemo.utility.Inject
import kotlinx.android.synthetic.main.activity_so.*
import java.lang.System.out

/**
 * Created by Manish Patel on 10/4/2019.
 */

class SOActivity : ParentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_so)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        /*var permissionList = Inject.RetrivePermissionList(this)
        println("permissionList: ${permissionList.size}")
        println("permissionList: ${permissionList[0]}")

        for (item in permissionList) {
            println("permission: $item")
        }*/
    }
}
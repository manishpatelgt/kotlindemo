package com.kotlindemo.application

import androidx.appcompat.app.AppCompatActivity
import com.kotlindemo.fragments.DemoDialogFragment

/**
 * Created by Manish Patel on 9/11/2019.
 */
open class BaseActivity : AppCompatActivity() {

    fun showDialog() {
        val demoDialogFragment = DemoDialogFragment.newInstance()
        demoDialogFragment!!.show(supportFragmentManager, "DemoDialogFragment")
    }

}
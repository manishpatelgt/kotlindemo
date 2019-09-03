package com.kotlindemo.activity.androidpatterns

import android.os.Bundle
import com.kotlindemo.R
import com.kotlindemo.activity.androidpatterns.mvc.view.MVCDemoActivity
import com.kotlindemo.activity.androidpatterns.mvp.view.MVPDemoActivity
import com.kotlindemo.activity.androidpatterns.mvvm.QuotesActivity
import com.kotlindemo.activity.androidpatterns.mvvmdatabinding.MVVMDataBindingDemoActivity
import com.kotlindemo.application.ParentActivity
import kotlinx.android.synthetic.main.activity_android_patterns.*
import org.jetbrains.anko.startActivity

/**
 * Created by Manish Patel on 7/18/2019.
 */
class AndroidPatternsActivity : ParentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_android_patterns)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mvc_button.setOnClickListener {
            startActivity<MVCDemoActivity>()
        }

        mvp_button.setOnClickListener {
            startActivity<MVPDemoActivity>()
        }

        mvvm_button.setOnClickListener {
            startActivity<QuotesActivity>()
        }

        mvvm_data_binding_button.setOnClickListener {
            startActivity<MVVMDataBindingDemoActivity>()
        }

    }
}
package com.kotlindemo.activity.viewmodel

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.kotlindemo.R
import com.kotlindemo.application.ParentActivity
import kotlinx.android.synthetic.main.activity_livedata.*
import kotlinx.android.synthetic.main.activity_viewmodel.*
import kotlinx.android.synthetic.main.activity_viewmodel.button_random
import kotlinx.android.synthetic.main.activity_viewmodel.textView
import kotlinx.android.synthetic.main.activity_viewmodel.toolbar
import kotlin.random.Random

/**
 * Created by Manish Patel on 5/23/2019.
 */
//https://github.com/googlesamples/android-architecture-components
//https://blog.pusher.com/realtime-applications-using-android-architecture-components-kotlin/

class ViewModelActivity : ParentActivity() {

    private val myViewModel: MyViewModel by lazy {
        ViewModelProviders.of(this).get(MyViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewmodel)
        setSupportActionBar(toolbar as Toolbar?)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)

        //myViewModel.generateRandomNumber()

        myViewModel.getRandomNumber().observe(this, Observer<String> {
            // update UI
            textView.text = it
        })

        button_random.setOnClickListener {
            myViewModel.generateRandomNumber()
        }
    }
}
package com.kotlindemo.activity.livedata

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.kotlindemo.R
import com.kotlindemo.application.ParentActivity
import kotlinx.android.synthetic.main.activity_livedata.*
import kotlin.random.Random

/**
 * Created by Manish Patel on 5/22/2019.
 */
//http://thetechnocafe.com/understanding-livedata-in-android-architecture-components/

class LiveDataActivity : ParentActivity() {

    private var mutableLiveData: MutableLiveData<String>? = MutableLiveData()

    init {
        mutableLiveData?.value = "Random Init Int: " + Random.nextInt(1, 100)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_livedata)
        setSupportActionBar(toolbar as Toolbar?)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)

        mutableLiveData?.observe(this, Observer<String> {
            textView.text = it
        })

        button_random.setOnClickListener {
            mutableLiveData?.value = "Random Int: " + Random.nextInt(1, 100)
        }

    }

}
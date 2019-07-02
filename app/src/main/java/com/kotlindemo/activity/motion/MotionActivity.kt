package com.kotlindemo.activity.motion

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.kotlindemo.R
import com.kotlindemo.activity.mvvm.QuotesActivity
import com.kotlindemo.application.ParentActivity
import kotlinx.android.synthetic.main.activity_motion.*
import org.jetbrains.anko.startActivity

/**
 * Created by Manish Patel on 5/28/2019.
 */
//https://code.tutsplus.com/tutorials/creating-animations-with-motionlayout-for-android--cms-31497
//https://www.raywenderlich.com/8883-motionlayout-tutorial-for-android-getting-started
//https://www.zoftino.com/android-motionlayout-examples
//https://androidstudio.googleblog.com/2018/12/constraintlayout-200-alpha-3.html

//https://pusher.com/tutorials/constraintlayout-kotlin-part-4 - Implemented

class MotionActivity : ParentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_motion)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        motion_button_1.setOnClickListener {
            startActivity<MotionByLayoutActivity>()
        }

        motion_button_2.setOnClickListener {
            startActivity<MotionByConstraintSetActivity>()
        }

        motion_button_3.setOnClickListener {
            startActivity<MotionByConstraintSetPlusCustomAttributeActivity>()
        }

        motion_button_4.setOnClickListener {
            startActivity<MotionByConstraintSetPlusKeyFrameActivity>()
        }
    }
}
package com.kotlindemo.activity.databinding2

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import com.kotlindemo.R
import com.kotlindemo.application.ParentActivity
import com.kotlindemo.databinding.ActivityDatabindingTwoWayBinding
import kotlinx.android.synthetic.main.activity_databinding_two_way.*
import org.jetbrains.anko.startActivity

/**
 * Created by Manish Patel on 5/23/2019.
 */
//https://developer.android.com/topic/libraries/data-binding/two-way
//https://medium.com/@jencisov/androids-data-binding-s-baseobservable-class-and-bindable-annotation-in-kotlin-1a5c6682a3c1
//https://github.com/Kotlin/kotlin-examples/tree/master/gradle/android-databinding
//http://www.devexchanges.info/2016/09/combining-databinding-and-picasso.html

class DataBindingActivity2 : ParentActivity() {

    private val TAG by lazy { DataBindingActivity2::class.java.simpleName }

    private lateinit var binding: ActivityDatabindingTwoWayBinding

    val weather = WeatherData(
        "Saint Petersburg",
        "10 °C",
        "Cloudy with rain and possible thunderstorms",
        "http://www.saint-petersburg.com/images/weather/weather-in-st-petersburg.jpg"
    )

    var counter = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_databinding_two_way)
        binding.lifecycleOwner = this

        setSupportActionBar(toolbar as Toolbar?)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)

        binding.data = weather
    }

    fun changeTemperatureAndImage(view: View) {
        ++counter
        weather.temperature = "$counter °C"
        weather.imageUrl = "http://i.imgur.com/6zgawxz.jpg"
        weather.notifyChange()
    }

    fun startActivity(view: View) = startActivity<OtherActivity>()
}
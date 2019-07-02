package com.kotlindemo.activity.databinding2

import androidx.databinding.BaseObservable

/**
 * Created by Manish Patel on 5/23/2019.
 */
class WeatherData(
    var location: String,
    var temperature: String,
    var info: String,
    var imageUrl: String
) : BaseObservable()
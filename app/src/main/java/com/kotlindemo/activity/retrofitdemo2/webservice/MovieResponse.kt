package com.kotlindemo.activity.retrofitdemo2.webservice

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Manish Patel on 7/2/2019.
 */
class MovieResponse {

    @SerializedName("success")
    @Expose
    var success: Int = 0

    @SerializedName("response")
    @Expose
    var movieList: List<Movie>? = null

}
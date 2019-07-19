package com.kotlindemo.activity.otherthings.shimmer.webservice

import com.kotlindemo.activity.otherthings.shimmer.models.Recipe
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

/**
 * Created by Manish Patel on 7/19/2019.
 */
interface RetrofitService {

    //https://api.androidhive.info/json/shimmer/menu.php
    @GET("menu.php")
    fun getRecipes(): Deferred<Response<List<Recipe>>>

}
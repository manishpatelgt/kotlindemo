package com.kotlindemo.activity.retrofitdemo;

import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by Manish Patel on 5/24/2019.
 */
interface RetrofitService {

    //https://my-json-server.typicode.com/
    @GET("typicode/demo/posts")
    fun getPosts(): Call<List<Post>>
}
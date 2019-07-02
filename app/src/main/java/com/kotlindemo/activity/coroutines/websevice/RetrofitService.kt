package com.kotlindemo.activity.coroutines.websevice

import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

/**
 * Created by Manish Patel on 5/24/2019.
 */
interface RetrofitService {

    //https://my-json-server.typicode.com/
    @GET("typicode/demo/posts")
    fun getPosts(): Deferred<Response<List<Post>>>

    //https://my-json-server.typicode.com/
    @GET("typicode/demo/posts")
    fun getPosts2(): Call<List<Post>>
}
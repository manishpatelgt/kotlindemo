package com.kotlindemo.activity.retrofitdemo2

import com.kotlindemo.activity.retrofitdemo2.webservice.MovieResponse
import com.kotlindemo.activity.retrofitdemo2.webservice.PostResponse
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Created by Manish Patel on 5/24/2019.
 */
interface RetrofitService {

    //https://my-json-server.typicode.com/
    @GET("typicode/demo/posts")
    fun getPosts(): Deferred<Response<PostResponse>>

    @FormUrlEncoded
    @POST("tasks")
    fun getMovies(
        @Field("type") type: String,
        @Field("page") page: Int,
        @Field("order_by_on") order_by_on: String,
        @Field("order_by_type") order_by_type: String
    ): Deferred<Response<MovieResponse>>
}
package com.kotlindemo.activity.retrofitdemo2;

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Manish Patel on 5/24/2019.
 */
object RetrofitFactory {

    const val BASE_URL = "https://my-json-server.typicode.com/"
    const val BASE_URL2 = "http://filmymojo.com/fm_api/index.php/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val gsonConverter = buildGsonConverter()

    private fun buildGsonConverter(): GsonConverterFactory {
        val gsonBuilder = GsonBuilder()
        val gson = gsonBuilder
            .create()
        return GsonConverterFactory.create(gson)
    }

    //logging
    private val client =
        OkHttpClient().newBuilder()
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()

    val apiService = getServiceApi(getRetrofit())

    val apiService2 = getServiceApi2(getRetrofit2())

    fun getServiceApi(retrofit: Retrofit) = retrofit.create(RetrofitService::class.java)

    fun getServiceApi2(retrofit: Retrofit) = retrofit.create(RetrofitService::class.java)

    fun getRetrofit() = Retrofit.Builder()
        .client(client)
        .baseUrl(BASE_URL)
        .addConverterFactory(gsonConverter)
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    fun getRetrofit2() = Retrofit.Builder()
        .client(client)
        .baseUrl(BASE_URL2)
        .addConverterFactory(gsonConverter)
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

}
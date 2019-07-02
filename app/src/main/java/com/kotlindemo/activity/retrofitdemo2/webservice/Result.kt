package com.kotlindemo.activity.retrofitdemo2.webservice

/**
 * Created by Manish Patel on 7/1/2019.
 */
sealed class Result<out T : Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}
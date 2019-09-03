package com.kotlindemo.activity.androidpatterns.mvvmdatabinding

/**
 * Created by Manish Patel on 9/3/2019.
 */
interface LoginCallBacks {
    fun onSuccess(message: String)
    fun onFailure(message: String)
}
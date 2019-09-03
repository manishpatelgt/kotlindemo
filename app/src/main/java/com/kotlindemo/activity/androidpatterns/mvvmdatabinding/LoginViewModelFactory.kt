package com.kotlindemo.activity.androidpatterns.mvvmdatabinding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory

/**
 * Created by Manish Patel on 9/3/2019.
 */
class LoginViewModelFactory(private var loginCallBacks: LoginCallBacks) : NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(loginCallBacks) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
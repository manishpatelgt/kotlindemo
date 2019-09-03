package com.kotlindemo.activity.androidpatterns.mvvmdatabinding

import android.text.TextUtils

/**
 * Created by Manish Patel on 9/3/2019.
 */
data class LoginModel(var email: String = "", var password: String = "") {

    fun isValid(): Boolean {
        return TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && password.length > 6
    }
}


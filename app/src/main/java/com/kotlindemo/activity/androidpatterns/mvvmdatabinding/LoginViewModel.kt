package com.kotlindemo.activity.androidpatterns.mvvmdatabinding

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel

/**
 * Created by Manish Patel on 9/3/2019.
 */
class LoginViewModel(var loginCallBacks: LoginCallBacks) : ViewModel() {

    private var loginModel = LoginModel()

    init {
        Log.i("LoginViewModel", "LoginViewModel created!")
    }

    val emailTextWatcher = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {

        }

        override fun beforeTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
            loginModel.email = p0.toString()
        }

    }


    val passwordTextWatcher = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {

        }

        override fun beforeTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
            loginModel.password = p0.toString()
        }
    }


    fun onLoginButtonClick(view: View) {
        if (loginModel.isValid()) {
            loginCallBacks.onSuccess("Success")
        } else {
            loginCallBacks.onFailure("Failed")
        }
    }
}
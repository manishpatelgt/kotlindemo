package com.kotlindemo.activity.databinding

/**
 * Created by Manish Patel on 5/23/2019.
 */
class MyName(var name: String = "", var nickname: String = "") {
    fun isNickNameEmpty(): Boolean {
        return nickname.isNullOrEmpty()
    }
}
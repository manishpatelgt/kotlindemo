package com.kotlindemo.activity.coroutines.websevice

/**
 * Created by Manish Patel on 5/24/2019.
 */
data class Post(val id: Int, val title: String) {

    override fun toString(): String {
        return "Id: $id title: $title"
    }
}
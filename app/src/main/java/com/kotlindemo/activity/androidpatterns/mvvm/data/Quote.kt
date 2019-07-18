package com.kotlindemo.activity.androidpatterns.mvvm.data

/**
 * Created by Manish Patel on 5/22/2019.
 */
data class Quote(val quoteText: String, val author: String) {
    override fun toString(): String {
        return "$quoteText - $author"
    }
}
package com.kotlindemo.extensions

import android.content.Context
import android.widget.Toast
import com.kotlindemo.application.DemoApplication.Companion.context

/**
 * Created by Manish Patel on 5/24/2019.
 */

fun Context.showToastMessage(message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG)
}
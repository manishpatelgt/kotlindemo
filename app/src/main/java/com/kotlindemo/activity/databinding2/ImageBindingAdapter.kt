package com.kotlindemo.activity.databinding2

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

/**
 * Created by Manish Patel on 5/23/2019.
 */
//https://softwarebrothers.co/blog/bindingadapter/
//https://developer.android.com/topic/libraries/data-binding/binding-adapters

object ImageBindingAdapter {

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun imageUrl(view: ImageView, url: String) {
        println("Url $url")
        Picasso.get().load(url).into(view)
    }
}

package com.kotlindemo.activity.databinding2

import android.content.Context
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator

/**
 * Created by Manish Patel on 5/23/2019.
 */
/**
 * Hassle-free image loading: http://square.github.io/picasso/
 */

public val Context.picasso: Picasso
    get() = Picasso.get()

/**
 * ImageView will directly load images from network
 */
fun ImageView.loadImage(imageUrl: String) {
    Picasso.get().load(imageUrl)
//        .placeholder(R.drawable.user_placeholder)
//        .error(R.drawable.user_placeholder_error)
        .into(this)
}

/**
 * Allows to apply further image transformations to the loaded image.
 *
 * @sample
 * imageView.load(path) { request -> request.resize(50, 50).centerCrop() }
 */
fun ImageView.loadImage(imageUrl: String, request: (RequestCreator) -> RequestCreator) {
    request(Picasso.get().load(imageUrl)).into(this)
}
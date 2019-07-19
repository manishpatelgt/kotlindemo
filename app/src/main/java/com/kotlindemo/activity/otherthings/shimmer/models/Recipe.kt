package com.kotlindemo.activity.otherthings.shimmer.models

import com.google.gson.annotations.SerializedName

/**
 * Created by Manish Patel on 7/19/2019.
 */
class Recipe {

    @SerializedName("id")
    var id: Int = 0

    @SerializedName("name")
    var name: String? = null

    @SerializedName("description")
    var description: String? = null

    @SerializedName("price")
    var pricde: Double = 0.0

    @SerializedName("thumbnail")
    var thumbnail: String? = null

    @SerializedName("chef")
    var chef: String? = null

    @SerializedName("timestamp")
    var timestamp: String? = null

    override fun toString(): String {
        return "Id: $id name: $name"
    }
}
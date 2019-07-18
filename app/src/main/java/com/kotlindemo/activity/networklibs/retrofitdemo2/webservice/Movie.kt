package com.kotlindemo.activity.networklibs.retrofitdemo2.webservice

/**
 * Created by Manish Patel on 7/2/2019.
 */
data class Movie(
    val id: Int,
    val movie_name: String,
    val actor: String,
    val actress: String,
    val director: String,
    val genre: String
) {

    override fun toString(): String {
        return "Id: $id movie_name: $movie_name"
    }

}
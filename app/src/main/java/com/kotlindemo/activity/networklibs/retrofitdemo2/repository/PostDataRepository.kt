package com.kotlindemo.activity.networklibs.retrofitdemo2.repository

import com.kotlindemo.activity.networklibs.retrofitdemo2.RetrofitFactory
import com.kotlindemo.activity.networklibs.retrofitdemo2.RetrofitService
import com.kotlindemo.activity.networklibs.retrofitdemo2.utils.safeApiCall
import com.kotlindemo.activity.networklibs.retrofitdemo2.webservice.MovieResponse
import com.kotlindemo.activity.networklibs.retrofitdemo2.webservice.PostResponse
import java.io.IOException
import com.kotlindemo.activity.networklibs.retrofitdemo2.webservice.Result

/**
 * Created by Manish Patel on 7/1/2019.
 */
class PostDataRepository constructor(private val retrofitService: RetrofitService) {

    companion object {
        // Singleton instantiation you already know and love
        @Volatile
        private var instance: PostDataRepository? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: PostDataRepository(RetrofitFactory.apiService2).also { instance = it }
            }
    }

    /*suspend fun getPosts(): Result<PostResponse> {
        val response = retrofitService.getPosts().await()
        try {
            if (response.isSuccessful)
                return Result.Success(response.body()!!)
            return Result.Error(IOException("Error occurred during fetching posts!"))
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }*/

    suspend fun getMovies() = safeApiCall(
        call = { makeAPIMoviesCall() },
        errorMessage = "Error occurred"
    )

    suspend fun makeAPIMoviesCall(): Result<MovieResponse> {
        val response = retrofitService.getMovies("in_theaters", 1, "id", "DESC").await()
        if (response.isSuccessful)
            return Result.Success(response.body()!!)
        return Result.Error(IOException("Error occurred during fetching movies!"))
    }

    suspend fun getPosts() = safeApiCall(
        call = { makeAPICall() },
        errorMessage = "Error occurred"
    )

    suspend fun makeAPICall(): Result<PostResponse> {
        val response = retrofitService.getPosts().await()
        if (response.isSuccessful)
            return Result.Success(response.body()!!)
        return Result.Error(IOException("Error occurred during fetching posts!"))
    }
}
package com.kotlindemo.activity.otherthings.shimmer.repository

import com.kotlindemo.activity.networklibs.retrofitdemo2.utils.safeApiCall
import com.kotlindemo.activity.networklibs.retrofitdemo2.webservice.MovieResponse
import com.kotlindemo.activity.networklibs.retrofitdemo2.webservice.Result
import com.kotlindemo.activity.otherthings.shimmer.webservice.RetrofitFactory
import com.kotlindemo.activity.otherthings.shimmer.webservice.RetrofitService
import java.io.IOException

/**
 * Created by Manish Patel on 7/19/2019.
 */
class RecipeDataRepository constructor(private val retrofitService: RetrofitService) {

    companion object {
        // Singleton instantiation you already know and love
        @Volatile
        private var instance: RecipeDataRepository? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: RecipeDataRepository(RetrofitFactory.apiService).also { instance = it }
            }
    }

    suspend fun getRecipes() = safeApiCall(
        call = { makeAPIRecipeCall() },
        errorMessage = "Error occurred"
    )

    suspend fun makeAPIRecipeCall(): Result<MovieResponse> {
        val response = retrofitService.getRecipes().await()
        if (response.isSuccessful)
            return Result.Success(response.body()!!)
        return Result.Error(IOException("Error occurred during fetching movies!"))
    }

}
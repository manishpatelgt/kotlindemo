package com.kotlindemo.activity.otherthings.shimmer.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kotlindemo.activity.otherthings.shimmer.viewmodel.MyViewModel

/**
 * Created by Manish Patel on 7/19/2019.
 */

class MyViewModelFactory constructor(private val recipeDataRepository: RecipeDataRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MyViewModel(recipeDataRepository) as T
    }

}
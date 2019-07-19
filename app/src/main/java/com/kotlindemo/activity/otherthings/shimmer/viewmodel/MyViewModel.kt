package com.kotlindemo.activity.otherthings.shimmer.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlindemo.activity.otherthings.shimmer.models.Result
import com.kotlindemo.activity.otherthings.shimmer.models.Recipe
import com.kotlindemo.activity.otherthings.shimmer.repository.RecipeDataRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * Created by Manish Patel on 7/19/2019.
 */
class MyViewModel constructor(private val recipeDataRepository: RecipeDataRepository) : ViewModel() {

    private val TAG by lazy { MyViewModel::class.java.simpleName }

    var isLoading = MutableLiveData<Boolean>()

    var apiError = MutableLiveData<String>()

    private var recipeJob: Job? = null

    //this is the data that we will fetch asynchronously
    private var recipeList: MutableLiveData<List<Recipe>>? = null

    //we will call this method to get the data
    fun getRecipes(): MutableLiveData<List<Recipe>> {

        if (recipeList == null) {
            recipeList = MutableLiveData()
            //we will load it asynchronously from server in this method
            loadRecipes()
        }

        return recipeList as MutableLiveData<List<Recipe>>
    }

    private fun loadRecipes() {
        isLoading.value = true

        recipeJob = viewModelScope.launch {
            when (val result = recipeDataRepository.getRecipes()) {
                is Result.Success -> {
                    isLoading.value = false
                    recipeList?.value = result.data.toString()
                }
                is Result.Error -> {
                    apiError.value = result.exception.message
                    isLoading.value = false
                }
            }
        }

    }
}
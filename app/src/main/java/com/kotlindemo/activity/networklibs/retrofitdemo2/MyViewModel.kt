package com.kotlindemo.activity.networklibs.retrofitdemo2

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlindemo.activity.networklibs.retrofitdemo2.webservice.Result
import com.kotlindemo.activity.networklibs.retrofitdemo2.repository.PostDataRepository
import com.kotlindemo.activity.networklibs.retrofitdemo2.webservice.Movie
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * Created by Manish Patel on 7/1/2019.
 */
class MyViewModel constructor(private val postDataRepository: PostDataRepository) : ViewModel() {

    private val TAG by lazy { MyViewModel::class.java.simpleName }

    var isLoading = MutableLiveData<Boolean>()

    var apiError = MutableLiveData<String>()

    private var postJob: Job? = null

    //this is the data that we will fetch asynchronously
    private var postList: MutableLiveData<List<Post>>? = null

    //this is the data that we will fetch asynchronously
    private var movieList: MutableLiveData<List<Movie>>? = null

    //we will call this method to get the data
    fun getMovies(): MutableLiveData<List<Movie>> {

        if (movieList == null) {
            movieList = MutableLiveData()
            //we will load it asynchronously from server in this method
            loadMovies()
        }

        return movieList as MutableLiveData<List<Movie>>
    }

    //we will call this method to get the data
    fun getPosts(): MutableLiveData<List<Post>> {

        if (postList == null) {
            postList = MutableLiveData()
            //we will load it asynchronously from server in this method
            loadPosts()
        }

        return postList as MutableLiveData<List<Post>>
    }

    private fun loadMovies() {
        isLoading.value = true

        postJob = viewModelScope.launch {
            when (val result = postDataRepository.getMovies()) {
                is Result.Success -> {
                    isLoading.value = false
                    movieList?.value = result.data.movieList
                }
                is Result.Error -> {
                    apiError.value = result.exception.message
                    isLoading.value = false
                }
            }
        }

    }


    private fun loadPosts() {
        isLoading.value = true

        postJob = viewModelScope.launch {
            when (val result = postDataRepository.getPosts()) {
                is Result.Success -> {
                    isLoading.value = false
                    postList?.value = result.data.posts
                }
                is Result.Error -> {
                    apiError.value = result.exception.message
                    isLoading.value = false
                }
            }
        }

    }

    override fun onCleared() {
        super.onCleared()
        postJob?.cancel()
    }
}
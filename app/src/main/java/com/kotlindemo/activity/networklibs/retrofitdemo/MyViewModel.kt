package com.kotlindemo.activity.networklibs.retrofitdemo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//https://www.simplifiedcoding.net/android-viewmodel-using-retrofit/
/**
 * Created by Manish Patel on 6/8/2019.
 */
class MyViewModel : ViewModel() {

    private val TAG by lazy { MyViewModel::class.java.simpleName }

    //this is the data that we will fetch asynchronously
    private var postList: MutableLiveData<List<Post>>? = null

    var isLoading = MutableLiveData<Boolean>()

    var apiError = MutableLiveData<Throwable>()

    val repository by lazy {
        RetrofitFactory.makeRetrofitService()
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

    private fun loadPosts() {
        isLoading.value = true

        val call = repository.getPosts()

        call.enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                //finally we are setting the list to our MutableLiveData
                postList?.value = response.body()
                isLoading.value = false
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Log.e(TAG, "API call failed error: ${t.message}")
                apiError.value = t
                isLoading.value = false
            }
        })
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel onCleared")
    }
}
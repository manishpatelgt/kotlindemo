package com.kotlindemo.activity.networklibs.retrofitdemo2.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kotlindemo.activity.networklibs.retrofitdemo2.MyViewModel

/**
 * Created by Manish Patel on 7/1/2019.
 */
class MyViewModelFactory constructor(private val postDataRepository: PostDataRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MyViewModel(postDataRepository) as T
    }

}
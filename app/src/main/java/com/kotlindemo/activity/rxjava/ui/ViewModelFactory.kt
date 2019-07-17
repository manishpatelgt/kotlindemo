package com.kotlindemo.activity.rxjava.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kotlindemo.activity.rxjava.persistence.UserDao

/**
 * Created by Manish Patel on 7/17/2019.
 */
/**
 * Factory for ViewModels
 */
class ViewModelFactory(private val dataSource: UserDao) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
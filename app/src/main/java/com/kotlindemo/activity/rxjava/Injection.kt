package com.kotlindemo.activity.rxjava

import android.content.Context
import com.kotlindemo.activity.rxjava.persistence.UserDao
import com.kotlindemo.activity.rxjava.persistence.UsersDatabase
import com.kotlindemo.activity.rxjava.ui.ViewModelFactory

/**
 * Created by Manish Patel on 7/17/2019.
 */

object Injection {

    fun provideUserDataSource(context: Context): UserDao {
        val database = UsersDatabase.getInstance(context)
        return database.userDao()
    }

    fun provideViewModelFactory(context: Context): ViewModelFactory {
        val dataSource = provideUserDataSource(context)
        return ViewModelFactory(dataSource)
    }

}
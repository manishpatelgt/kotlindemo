package com.kotlindemo.activity.otherthings.rxjava.ui

import androidx.lifecycle.ViewModel
import com.kotlindemo.activity.otherthings.rxjava.persistence.User
import com.kotlindemo.activity.otherthings.rxjava.persistence.UserDao
import io.reactivex.Completable
import io.reactivex.Flowable

/**
 * Created by Manish Patel on 7/17/2019.
 */
/**
 * View Model for the [RxJavaDemoActivity]
 */
class UserViewModel(private val dataSource: UserDao) : ViewModel() {

    /**
     * Get the user name of the user.
     * @return a [Flowable] that will emit every time the user name has been updated.
     */
    // for every emission of the user, get the user name
    fun userName(): Flowable<String> {
        return dataSource.getUserById(USER_ID)
            .map { user -> user.userName }
    }

    /**
     * Update the user name.
     * @param userName the new user name
     * *
     * @return a [Completable] that completes when the user name is updated
     */
    fun updateUserName(userName: String): Completable {
        val user = User(USER_ID, userName)
        return dataSource.insertUser(user)
    }

    companion object {
        // using a hardcoded value for simplicity
        const val USER_ID = "1"
    }
}
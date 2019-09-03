package com.kotlindemo.activity.otherthings.rxjava.ui

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProviders
import com.kotlindemo.R
import com.kotlindemo.activity.otherthings.rxjava.Injection
import com.kotlindemo.application.ParentActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_rxjava.*

/**
 * Created by Manish Patel on 7/17/2019.
 */
/**
 * Main screen of the app. Displays a user name and gives the option to update the user name.
 */
//https://github.com/googlesamples/android-architecture-components/tree/master/BasicRxJavaSampleKotlin
//https://www.vogella.com/tutorials/RxJava/article.html

class RxJavaDemoActivity : ParentActivity() {

    companion object {
        val TAG: String = RxJavaDemoActivity.javaClass::class.java.simpleName
    }

    private lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: UserViewModel

    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rxjava)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModelFactory = Injection.provideViewModelFactory(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(UserViewModel::class.java)
        update_user_button.setOnClickListener { updateUserName() }
    }

    override fun onStart() {
        super.onStart()
        // Subscribe to the emissions of the user name from the view model.
        // Update the user name text view, at every onNext emission.
        // In case of error, log the exception.
        disposable.add(
            viewModel.userName()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    this.user_name.text = it },
                    { error -> Log.e(TAG, "Unable to get username", error) })
        )
    }

    override fun onStop() {
        super.onStop()

        // clear all the subscription
        disposable.clear()
    }

    private fun updateUserName() {
        val userName = user_name_input.text.toString()
        // Disable the update button until the user name update has been done
        update_user_button.isEnabled = false
        // Subscribe to updating the user name.
        // Enable back the button once the user name has been updated
        disposable.add(
            viewModel.updateUserName(userName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ update_user_button.isEnabled = true },
                    { error -> Log.e(TAG, "Unable to update username", error) })
        )
    }


}
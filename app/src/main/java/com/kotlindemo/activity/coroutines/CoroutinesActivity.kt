package com.kotlindemo.activity.coroutines

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.kotlindemo.R
import com.kotlindemo.activity.coroutines.websevice.Post
import com.kotlindemo.activity.coroutines.websevice.RetrofitFactory
import com.kotlindemo.application.ParentActivity
import com.kotlindemo.extensions.showToastMessage
import kotlinx.android.synthetic.main.activity_coroutines.*
import kotlinx.android.synthetic.main.activity_coroutines.toolbar
import kotlinx.android.synthetic.main.activity_workmanager.*
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.lang.StringBuilder
import java.util.concurrent.TimeUnit

/**
 * Created by Manish Patel on 5/24/2019.
 */
//https://blog.mindorks.com/mastering-kotlin-coroutines-in-android-step-by-step-guide
//https://github.com/navi25/RetrofitKotlinDeferred
//https://android.jlelse.eu/kotlin-coroutines-and-retrofit-e0702d0b8e8f
//https://github.com/segunfamisa/retrofit-kotlin-sample
//https://android.jlelse.eu/android-networking-in-2019-retrofit-with-kotlins-coroutines-aefe82c4d777
//https://kotlinlang.org/docs/reference/coroutines/basics.html - Official
//https://github.com/JakeWharton/retrofit2-kotlin-coroutines-adapter
//https://github.com/Kotlin/kotlinx.coroutines/blob/master/coroutines-guide.md
//https://github.com/navi25/ShowTime

class CoroutinesActivity : ParentActivity() {

    companion object {
        val TAG: String = CoroutinesActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutines)
        setSupportActionBar(toolbar as Toolbar?)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        setupUI()
    }

    private fun setupUI() {

        //Simple coroutines
        simple_coroutines.setOnClickListener {
            GlobalScope.launch(uiDispatcher) {
                showCoroutineStatus("Hello it has started")
                delay(10000)
                showCoroutineStatus("I have changed after 10 seconds")
            }
        }

        //Network call coroutines
        network_coroutines.setOnClickListener {

            val service = RetrofitFactory.makeRetrofitService()
            showProgress()

            CoroutineScope(Dispatchers.IO).launch {
                val request = service.getPosts()

                withContext(Dispatchers.Main) {
                    try {
                        val response = request.await()
                        if (response.isSuccessful) {
                            hideProgress()
                            //Do something with response e.g show to the UI.
                            val posts: List<Post>? = response.body()
                            showCoroutineStatus("Response: " + posts.toString())
                            Log.d(TAG, posts.toString())
                        } else {
                            apiError()
                            showCoroutineStatus("Error: ${response.code()}")
                        }
                    } catch (e: HttpException) {
                        apiError()
                        showCoroutineStatus("Exception ${e.message}")
                    } catch (e: Throwable) {
                        apiError()
                        showCoroutineStatus("Ooops: Something else went wrong")
                    }
                }

            }

        }
    }

    private fun apiError() {
        hideProgress()
    }

    private fun showProgress() {
        activity_main_co_status.visibility = View.GONE
        loading.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        activity_main_co_status.visibility = View.VISIBLE
        loading.visibility = View.GONE
    }

    private fun showCoroutineStatus(message: String) {
        activity_main_co_status.text = message
    }
}
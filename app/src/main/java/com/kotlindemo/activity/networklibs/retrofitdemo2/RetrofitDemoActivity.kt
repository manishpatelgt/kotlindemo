package com.kotlindemo.activity.networklibs.retrofitdemo2

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.kotlindemo.R
import com.kotlindemo.activity.networklibs.retrofitdemo2.repository.MyViewModelFactory
import com.kotlindemo.activity.networklibs.retrofitdemo2.repository.PostDataRepository
import com.kotlindemo.application.ParentActivity
import kotlinx.android.synthetic.main.activity_retrofit_demo2.*
import kotlinx.android.synthetic.main.activity_retrofit_demo2.toolbar

/**
 * Created by Manish Patel on 7/1/2019.
 */

//https://codinginfinite.com/kotlin-coroutine-call-adapter-retrofit/

class RetrofitDemoActivity : ParentActivity() {

    companion object {
        val TAG: String = RetrofitDemoActivity.javaClass::class.java.simpleName
    }

    /*private val myViewModel: MyViewModel by lazy {
        ViewModelProviders.of(this, MyViewModelFactory(PostDataRepository.getInstance())).get(MyViewModel::class.java)
    }*/

    private lateinit var myViewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit_demo2)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setupUI()
    }

    private fun setupUI() {

        get_post_button.setOnClickListener {
            myViewModel = ViewModelProviders.of(this, MyViewModelFactory(PostDataRepository.getInstance()))
                .get(MyViewModel::class.java)
            loadPosts()
        }

        get_movies_button.setOnClickListener {
            myViewModel = ViewModelProviders.of(this, MyViewModelFactory(PostDataRepository.getInstance2()))
                .get(MyViewModel::class.java)
            loadMovies()
        }

    }

    private fun initOtherObserver() {

        myViewModel.isLoading.observe(this, Observer<Boolean> {
            it?.let { showProgress(it) }
        })

        myViewModel.apiError.observe(this, Observer<String> {
            it?.let {
                Log.e(TAG, "Error: $it")
                activity_status.text = it
            }
        })
    }

    private fun loadMovies() {
        initOtherObserver()
        myViewModel.getMovies().observe(this, Observer { entries ->
            Log.d(TAG, "called observe")
            Log.d(TAG, entries.toString())
            activity_status.text = "Movies $entries"
            myViewModel.isLoading.value = false
        })
    }

    private fun loadPosts() {
        initOtherObserver()
        myViewModel.getPosts().observe(this, Observer { entries ->
            Log.d(TAG, "called observe")
            Log.d(TAG, entries.toString())
            activity_status.text = "Posts $entries"
        })
    }

    private fun showProgress(show: Boolean) {
        if (show) {
            activity_status.visibility = View.GONE
            loading.visibility = View.VISIBLE
        } else {
            activity_status.visibility = View.VISIBLE
            loading.visibility = View.GONE
        }
    }
}
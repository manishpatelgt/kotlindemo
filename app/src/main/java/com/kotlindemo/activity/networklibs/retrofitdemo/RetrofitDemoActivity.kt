package com.kotlindemo.activity.networklibs.retrofitdemo

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.kotlindemo.R
import com.kotlindemo.application.ParentActivity
import kotlinx.android.synthetic.main.activity_retrofit_demo.*
import kotlinx.android.synthetic.main.activity_retrofit_demo.loading
import kotlinx.android.synthetic.main.activity_retrofit_demo.toolbar

/**
 * Created by Manish Patel on 6/8/2019.
 */
class RetrofitDemoActivity : ParentActivity() {

    companion object {
        val TAG: String = RetrofitDemoActivity.javaClass::class.java.simpleName
    }

    private val myViewModel: MyViewModel by lazy {
        ViewModelProviders.of(this).get(MyViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit_demo)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setupUI()
    }

    private fun setupUI() {

        myViewModel.getPosts().observe(this, Observer { entries ->
            Log.d(TAG, "called observe")
            Log.d(TAG, entries.toString())
            activity_status.text = entries.toString()
        })

        myViewModel.isLoading.observe(this, Observer<Boolean> {
            it?.let { showProgress(it) }
        })
        myViewModel.apiError.observe(this, Observer<Throwable> {
            it?.let { Log.e(TAG, "Error: " + it.localizedMessage) }
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
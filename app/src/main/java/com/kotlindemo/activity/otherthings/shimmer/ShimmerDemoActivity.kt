package com.kotlindemo.activity.otherthings.shimmer

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kotlindemo.R
import com.kotlindemo.activity.otherthings.shimmer.adapter.RecipeAdapter
import com.kotlindemo.activity.otherthings.shimmer.models.Recipe
import com.kotlindemo.activity.otherthings.shimmer.repository.MyViewModelFactory
import com.kotlindemo.activity.otherthings.shimmer.repository.RecipeDataRepository
import com.kotlindemo.activity.otherthings.shimmer.viewmodel.MyViewModel
import com.kotlindemo.application.ParentActivity
import kotlinx.android.synthetic.main.activity_shimmer_demo.*
import kotlinx.android.synthetic.main.activity_shimmer_demo.toolbar

/**
 * Created by Manish Patel on 7/19/2019.
 */
//https://www.androidhive.info/2018/01/android-content-placeholder-animation-like-facebook-using-shimmer/

class ShimmerDemoActivity : ParentActivity() {

    companion object {
        val TAG: String = ShimmerDemoActivity.javaClass::class.java.simpleName
    }

    private val myViewModel: MyViewModel by lazy {
        ViewModelProviders.of(this, MyViewModelFactory(RecipeDataRepository.getInstance())).get(MyViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shimmer_demo)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setupUI()
    }

    private fun setupUI() {

        myViewModel.getRecipes().observe(this, Observer { entries ->
            Log.d(TAG, "called observe")
            Log.d(TAG, entries.toString())
            bindRecyclerAdapter(entries)
        })

        myViewModel.isLoading.observe(this, Observer<Boolean> {
            it?.let { shimmerEffect(it) }
        })
        myViewModel.apiError.observe(this, Observer<String> {
            it?.let {
                Log.e(TAG, "Error: $it")
            }
        })
    }

    private lateinit var recipeAdapter: RecipeAdapter

    fun bindRecyclerAdapter(recipes: List<Recipe>) {
        movies_list.setHasFixedSize(true)
        movies_list.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recipeAdapter = RecipeAdapter(recipes, this@ShimmerDemoActivity)
        movies_list.adapter = recipeAdapter
    }

    private fun shimmerEffect(show: Boolean) {
        if (show) {
            shimmer_view_container.visibility = View.VISIBLE
            shimmer_view_container.startShimmerAnimation()
        } else {
            shimmer_view_container.visibility = View.GONE
            shimmer_view_container.stopShimmerAnimation()
        }
    }

    override fun onResume() {
        super.onResume()
        //shimmer_view_container.startShimmerAnimation()
    }

    override fun onPause() {
        super.onPause()
        //shimmer_view_container.stopShimmerAnimation()
    }
}
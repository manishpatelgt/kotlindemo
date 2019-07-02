package com.kotlindemo.activity.mvvm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kotlindemo.activity.mvvm.data.QuoteRepository

/**
 * Created by Manish Patel on 5/22/2019.
 */
// The same repository that's needed for QuotesViewModel
// is also passed to the factory
class QuotesViewModelFactory(private val quoteRepository: QuoteRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return QuotesViewModel(quoteRepository) as T
    }
}
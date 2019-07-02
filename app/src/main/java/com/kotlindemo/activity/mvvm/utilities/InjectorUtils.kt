package com.kotlindemo.activity.mvvm.utilities

import com.kotlindemo.activity.mvvm.data.FakeDatabase
import com.kotlindemo.activity.mvvm.data.QuoteRepository
import com.kotlindemo.activity.mvvm.viewmodel.QuotesViewModelFactory

/**
 * Created by Manish Patel on 5/22/2019.
 */
object InjectorUtils {

    // This will be called from QuotesActivity
    fun provideQuotesViewModelFactory(): QuotesViewModelFactory {
        // ViewModelFactory needs a repository, which in turn needs a DAO from a database
        // The whole dependency tree is constructed right here, in one place
        val quoteRepository = QuoteRepository.getInstance(FakeDatabase.getInstance().quoteDao)
        return QuotesViewModelFactory(quoteRepository)
    }
}
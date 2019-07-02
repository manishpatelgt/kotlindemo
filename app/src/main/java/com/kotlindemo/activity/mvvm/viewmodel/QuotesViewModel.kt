package com.kotlindemo.activity.mvvm.viewmodel

import androidx.lifecycle.ViewModel
import com.kotlindemo.activity.mvvm.data.Quote
import com.kotlindemo.activity.mvvm.data.QuoteRepository

/**
 * Created by Manish Patel on 5/22/2019.
 */
// QuoteRepository dependency will again be passed in the
// constructor using dependency injection
class QuotesViewModel(private val quoteRepository: QuoteRepository) : ViewModel() {

    fun getQuotes() = quoteRepository.getQuotes()

    fun addQuote(quote: Quote) = quoteRepository.addQuote(quote)
}
package com.kotlindemo.application

import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

open class ParentActivity : AppCompatActivity() {

    // dispatches execution into Android main UI thread
    val uiDispatcher: CoroutineDispatcher = Dispatchers.Main

    // dispatches execution into a pool of shared threads (in the background)
    val bgDispatcher: CoroutineDispatcher = Dispatchers.IO

    val uiScope = CoroutineScope(uiDispatcher)

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
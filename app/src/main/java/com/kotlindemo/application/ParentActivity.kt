package com.kotlindemo.application

import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.kotlindemo.fragments.DemoDialogFragment
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.slf4j.LoggerFactory

open class ParentActivity : BaseActivity() {

    companion object {
        private val logger = LoggerFactory.getLogger(ParentActivity::class.java)
    }

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

    override fun onDestroy() {
        super.onDestroy()
        logger.error("ParentActivity onDestroy()")
    }
}
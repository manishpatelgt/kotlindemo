package com.kotlindemo.activity.behavior

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.ShareActionProvider
import com.kotlindemo.R
import com.kotlindemo.application.ParentActivity
import kotlinx.android.synthetic.main.activity_behaviour.*
import androidx.core.view.MenuItemCompat


/**
 * Created by Manish Patel on 7/18/2019.
 */
//https://developer.android.com/training/sharing/shareaction
//https://developer.android.com/reference/kotlin/androidx/appcompat/widget/ShareActionProvider

class BehaviorActivity : ParentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.kotlindemo.R.layout.activity_behaviour)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate menu resource file.
        menuInflater.inflate(R.menu.menu_behavior, menu)

        // Get the menu item.
        val menuItem = menu.findItem(R.id.menu_item_share)
        // Get the provider and hold onto it to set/change the share intent.
        shareActionProvider = MenuItemCompat.getActionProvider(menuItem) as ShareActionProvider

        // Set share Intent.
        // Note: You can set the share Intent afterwords if you don't want to set it right now.
        shareActionProvider?.setShareIntent(ShareText())

        // Locate MenuItem with ShareActionProvider
        /*menu.findItem(R.id.menu_item_share).also { menuItem ->
            // Fetch and store ShareActionProvider
            shareActionProvider = menuItem.actionProvider as? ShareActionProvider
            shareActionProvider?.setShareIntent(ShareText())
        }*/

        // Return true to display menu
        return super.onCreateOptionsMenu(menu)
    }

    private var shareActionProvider: ShareActionProvider? = null

    private fun ShareText(): Intent {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        val shareMsgBody = "Hello, Share this with world !!"
        intent.putExtra(Intent.EXTRA_TEXT, shareMsgBody)
        startActivity(Intent.createChooser(intent, "Spread the message!!"))
        return intent
    }

    // Call to update the share intent
    private fun setShareIntent() {
        shareActionProvider?.setShareIntent(ShareText())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        val id = item!!.itemId

        if (id == R.id.menu_item_share) {
            println("clicked share menu item")
            shareActionProvider?.setShareIntent(ShareText())
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}
package com.kotlindemo.activity.behavior

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.ShareActionProvider
import com.kotlindemo.R
import com.kotlindemo.application.ParentActivity
import kotlinx.android.synthetic.main.activity_behaviour.*
import androidx.core.view.MenuItemCompat
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.kotlindemo.activity.behavior.downloadmanager.DirectoryHelper
import com.kotlindemo.activity.behavior.downloadmanager.DownloadFileService
import com.kotlindemo.utility.ToastManager
import kotlinx.android.synthetic.main.activity_behaviour.toolbar

/**
 * Created by Manish Patel on 7/18/2019.
 */
//https://developer.android.com/training/sharing/shareaction
//https://developer.android.com/reference/kotlin/androidx/appcompat/widget/ShareActionProvider
//https://stackoverflow.com/questions/19118051/unable-to-cast-action-provider-to-share-action-provider/40089492
//Download Manager: https://codinginfinite.com/android-download-manager-example/

class BehaviorActivity : ParentActivity() {

    val IMAGE_DOWNLOAD_PATH = "https://www.freeimageslive.com/galleries/buildings/structures/pics/canal100-0416.jpg"
    val SONG_DOWNLOAD_PATH = "https://cloudup.com/files/inYVmLryD4p/download"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_behaviour)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        download_img_button.setOnClickListener {
            val intent = Intent(this, DownloadFileService::class.java)
            intent.putExtra(DownloadFileService.DOWNLOAD_PATH, IMAGE_DOWNLOAD_PATH)
            intent.putExtra(DownloadFileService.DESTINATION_PATH, DirectoryHelper.ROOT_DIRECTORY_NAME.plus("/"))
            startService(intent)

            /*startService(
                DownloadFileService.getDownloadService(
                    this,
                    IMAGE_DOWNLOAD_PATH,
                    DirectoryHelper.ROOT_DIRECTORY_NAME.plus("/")
                )
            )*/
        }

        button_download_song.setOnClickListener {

            val intent = Intent(this, DownloadFileService::class.java)
            intent.putExtra(DownloadFileService.DOWNLOAD_PATH, SONG_DOWNLOAD_PATH)
            intent.putExtra(DownloadFileService.DESTINATION_PATH, DirectoryHelper.ROOT_DIRECTORY_NAME.plus("/"))
            startService(intent)

            /*startService(
                DownloadFileService.getDownloadService(
                    this,
                    SONG_DOWNLOAD_PATH,
                    DirectoryHelper.ROOT_DIRECTORY_NAME.plus("/")
                )
            )*/
        }

    }

    override fun onResume() {
        super.onResume()

        Dexter.withActivity(this)
            .withPermissions(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    ToastManager.getInstance().showToast("All permission granted")
                    DirectoryHelper.createDirectory()
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest>,
                    token: PermissionToken
                ) {
                    token.continuePermissionRequest()
                }
            }).check()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate menu resource file.
        menuInflater.inflate(R.menu.menu_behavior, menu)

        // Get the menu item.
        val menuItem = menu.findItem(com.kotlindemo.R.id.menu_item_share)
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

        if (id == com.kotlindemo.R.id.menu_item_share) {
            println("clicked share menu item")
            shareActionProvider?.setShareIntent(ShareText())
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}
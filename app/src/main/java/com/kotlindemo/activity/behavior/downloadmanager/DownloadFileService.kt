package com.kotlindemo.activity.behavior.downloadmanager

import android.app.IntentService
import android.content.Intent
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import androidx.annotation.NonNull
import com.kotlindemo.activity.MainActivity

/**
 * Created by Manish Patel on 7/18/2019.
 */
class DownloadFileService : IntentService("DownloadFileService") {

    companion object {
        val DOWNLOAD_PATH = "com.kotlindemo_DownloadFileService_Download_path"
        val DESTINATION_PATH = "com.kotlindemo_DownloadFileService_Destination_path"
    }

    override fun onHandleIntent(intent: Intent?) {
        val downloadPath = intent?.getStringExtra(DOWNLOAD_PATH)
        val destinationPath = intent?.getStringExtra(DESTINATION_PATH)
        startDownload(downloadPath, destinationPath)
    }

    /*fun getDownloadService(@NonNull callingClassContext: Context, @NonNull downloadPath: String, @NonNull destinationPath: String): Intent {
        return Intent(callingClassContext, DownloadFileService::class.java)
            .putExtra(DOWNLOAD_PATH, downloadPath)
            .putExtra(DESTINATION_PATH, destinationPath)
    }*/

    private fun startDownload(downloadPath: String?, destinationPath: String?) {
        val uri = Uri.parse(downloadPath)
        val request = DownloadManager.Request(uri)
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI)  // Tell on which network you want to download file.
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)  // This will show notification on top when downloading the file.
        request.setTitle("Downloading a file") // Title for notification.
        request.setVisibleInDownloadsUi(true)
        request.setDestinationInExternalPublicDir(destinationPath, uri.lastPathSegment)  // Storage directory path
        (getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager).enqueue(request) // This will start downloading
    }
}
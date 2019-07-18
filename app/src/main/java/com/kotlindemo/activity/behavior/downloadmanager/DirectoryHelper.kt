package com.kotlindemo.activity.behavior.downloadmanager

import android.os.Environment
import android.os.Environment.getExternalStorageDirectory
import java.io.File

/**
 * Created by Manish Patel on 7/18/2019.
 */
object DirectoryHelper {

    val ROOT_DIRECTORY_NAME = "DownloadManager"

    fun createDirectory() {
        createFolderDirectories()
    }

    private fun isExternalStorageAvailable(): Boolean {
        val extStorageState = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED == extStorageState
    }

    private fun createFolderDirectories() {
        if (isExternalStorageAvailable())
            createDirectory(ROOT_DIRECTORY_NAME)
    }

    private fun createDirectory(directoryName: String) {
        if (!isDirectoryExists(directoryName)) {
            val file = File(getExternalStorageDirectory(), directoryName)
            file.mkdir()
        }
    }

    private fun isDirectoryExists(directoryName: String): Boolean {
        val path = Environment.getExternalStorageDirectory().toString()
        path.plus("/$directoryName")
        val file = File(path)
        return file.isDirectory && file.exists()
    }
}
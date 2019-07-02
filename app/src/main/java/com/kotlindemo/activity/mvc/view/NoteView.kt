package com.kotlindemo.activity.mvc.view

import android.view.View

/**
 * Created by Manish Patel on 7/2/2019.
 */
interface NoteView {

    /**
     * Returns the root view, i.e, the inflated layout file
     */
    fun getRootView(): View

    interface NoteSavedListener {
        /**
         * Callback function which will be used by our controller to do its things when a note is saved
         */
        fun onNoteSaved(note: String)
    }

    fun setListener(listener: NoteSavedListener)
}
package com.kotlindemo.activity.androidpatterns.mvc.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.kotlindemo.activity.androidpatterns.mvc.view.NoteView.*
import com.kotlindemo.R
import kotlinx.android.synthetic.main.activity_mvc.view.*

/**
 * Created by Manish Patel on 7/2/2019.
 */
class NoteViewImp(layoutInflater: LayoutInflater) : NoteView, View.OnClickListener {

    private var mRootView = layoutInflater.inflate(R.layout.activity_mvc, null)
    var noteSavedListener: NoteSavedListener? = null

    init {
        mRootView.button_save.setOnClickListener(this)
    }

    override fun getRootView() = mRootView

    override fun setListener(listener: NoteSavedListener) {
        noteSavedListener = listener
    }

    override fun onClick(view: View) {
        val note = mRootView.note_editText.text.toString()
        mRootView.note_editText.text.clear()
        Log.d("debug", "Save Button Clicked")
        noteSavedListener?.onNoteSaved(note)
    }

}
package com.kotlindemo.activity.mvc.view

import android.os.Bundle
import android.util.Log
import com.kotlindemo.application.ParentActivity
import com.kotlindemo.activity.mvc.view.NoteView.*

/**
 * Created by Manish Patel on 7/2/2019.
 */
//https://www.codementor.io/dragneelfps/implementing-mvc-pattern-in-android-with-kotlin-i9hi2r06c

class MVCDemoActivity : ParentActivity(), NoteSavedListener {

    private lateinit var mNoteView: NoteView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mNoteView = NoteViewImp(layoutInflater)
        setContentView(mNoteView.getRootView())
        mNoteView.setListener(this)
    }

    override fun onNoteSaved(note: String) {
        Log.d("debug", "Saving note: $note")
        // Save your note here
        // Use content providers, room persistence libraries, whatever you wanna 		 //	do with this note, do it. The implementation doesn't matter to your 		// View and Controller
    }
}
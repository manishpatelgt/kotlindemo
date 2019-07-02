package com.kotlindemo.activity.databinding

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import com.kotlindemo.R
import com.kotlindemo.application.ParentActivity
import com.kotlindemo.databinding.ActivityDatabindingBinding
import kotlinx.android.synthetic.main.activity_livedata.*

/**
 * Created by Manish Patel on 5/23/2019.
 */
//https://developer.android.com/topic/libraries/data-binding/expressions
//https://www.bignerdranch.com/blog/descent-into-databinding/
//https://medium.com/@jencisov/androids-data-binding-s-baseobservable-class-and-bindable-annotation-in-kotlin-1a5c6682a3c1

class DataBindingActivity : ParentActivity() {

    private val TAG by lazy { DataBindingActivity::class.java.simpleName }

    private lateinit var binding: ActivityDatabindingBinding

    private val myName: MyName = MyName("Manish Patel", "MP")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_databinding)
        binding.lifecycleOwner = this

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.myName = myName
        binding.presenter = Presenter()

        binding.doneButton.setOnClickListener {
            addNickname(it)
        }
    }

    class Presenter {
        fun showToast(view: View, myName: MyName) {
            Toast.makeText(view.context, "Yupppyyyyy!!! " + myName.name + " - " + myName.nickname, Toast.LENGTH_LONG).show()
        }
    }

    /**
     * Click handler for the Done button.
     * Demonstrates how data binding can be used to make code much more readable
     * by eliminating calls to findViewById and changing data in the binding object.
     */
    private fun addNickname(view: View) {
        binding.apply {
            myName?.nickname = nicknameEdit.text.toString()
            invalidateAll()
            nicknameEdit.visibility = View.GONE
            doneButton.visibility = View.GONE
            nicknameText.visibility = View.VISIBLE
        }

        // Hide the keyboard.
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}
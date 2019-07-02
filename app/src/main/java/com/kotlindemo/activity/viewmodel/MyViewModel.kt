package com.kotlindemo.activity.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

/**
 * Created by Manish Patel on 5/23/2019.
 */
class MyViewModel : ViewModel() {

    private val TAG by lazy { MyViewModel::class.java.simpleName }

    private var mutableLiveData: MutableLiveData<String> = MutableLiveData()

    init {
        //generateRandomNumber()
        mutableLiveData.value = "Random Init Int: " + Random.nextInt(1, 100)
    }

    fun getRandomNumber(): MutableLiveData<String> {
        return mutableLiveData
    }

    fun generateRandomNumber() {
        mutableLiveData.value = "Random Int: " + Random.nextInt(1, 100)
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel onCleared")
    }

}
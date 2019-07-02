package com.kotlindemo.activity.mvp.model


import com.kotlindemo.activity.mvp.contract.ContractInterface.*
/**
 * Created by Manish Patel on 7/2/2019.
 */
class MainActivityModel : Model {

    private var mCounter = 0

    override fun getCounter() = mCounter

    override fun incrementCounter() {
        mCounter++
    }
}
package com.kotlindemo.activity.mvp.contract

/**
 * Created by Manish Patel on 7/2/2019.
 */
interface ContractInterface {

    interface View {
        fun initView()
        fun updateViewData()
    }

    interface Presenter {
        fun incrementValue()
        fun getCounter(): String
    }

    interface Model {
        fun getCounter(): Int
        fun incrementCounter()
    }
}
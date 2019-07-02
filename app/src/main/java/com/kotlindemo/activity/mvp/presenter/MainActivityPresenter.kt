package com.kotlindemo.activity.mvp.presenter

import com.kotlindemo.activity.mvp.model.MainActivityModel
import com.kotlindemo.activity.mvp.contract.ContractInterface.*

/**
 * Created by Manish Patel on 7/2/2019.
 */
class MainActivityPresenter(_view: View) : Presenter {

    private var view: View = _view
    private var model: Model = MainActivityModel()

    init {
        view.initView()
    }

    override fun incrementValue() {
        model.incrementCounter()
        view.updateViewData()
    }

    override fun getCounter() = model.getCounter().toString()

}
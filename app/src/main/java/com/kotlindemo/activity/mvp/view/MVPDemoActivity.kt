package com.kotlindemo.activity.mvp.view

import android.os.Bundle
import com.kotlindemo.R
import com.kotlindemo.application.ParentActivity
import com.kotlindemo.activity.mvp.contract.ContractInterface.*
import com.kotlindemo.activity.mvp.presenter.MainActivityPresenter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.activity_mvp.*
import kotlinx.android.synthetic.main.activity_viewmodel.*

/**
 * Created by Manish Patel on 7/2/2019.
 */
//https://medium.com/mindorks/android-mvp-pattern-debunked-for-beginners-kotlin-c56c888222e0

class MVPDemoActivity : ParentActivity(), View {

    companion object {
        val TAG: String = MVPDemoActivity.javaClass::class.java.simpleName
    }

    private var presenter: MainActivityPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvp)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        presenter = MainActivityPresenter(this)
    }

    override fun initView() {
        counterTextView.text = presenter?.getCounter()
        clickButton.setOnClickListener { presenter?.incrementValue() }
    }

    override fun updateViewData() {
        counterTextView.text = presenter?.getCounter()
    }
}
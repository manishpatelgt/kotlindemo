package com.kotlindemo.activity.androidpatterns.mvvmdatabinding

import android.os.Bundle
import android.os.PersistableBundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.kotlindemo.R
import com.kotlindemo.application.ParentActivity
import com.kotlindemo.databinding.ActivityMvvmDataBindingBinding
import com.kotlindemo.utility.ToastManager

//https://proandroiddev.com/view-model-creation-in-android-android-architecture-components-kotlin-ce9f6b93a46b
//Codelab: https://codelabs.developers.google.com/codelabs/kotlin-android-training-view-model/#7

/**
 * Created by Manish Patel on 9/3/2019.
 */
class MVVMDataBindingDemoActivity : ParentActivity(), LoginCallBacks {

    private val loginViewModel: LoginViewModel by lazy {
        ViewModelProviders.of(this, LoginViewModelFactory(this)).get(LoginViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMvvmDataBindingBinding>(
            this,
            R.layout.activity_mvvm_data_binding
        ).apply {
            lifecycleOwner = this@MVVMDataBindingDemoActivity
            viewModel = loginViewModel
        }
    }

    override fun onSuccess(message: String) {
        ToastManager.getInstance().showToast(message)
    }

    override fun onFailure(message: String) {
        ToastManager.getInstance().showToast(message)
    }

}
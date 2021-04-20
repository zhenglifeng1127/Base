package com.android.base.mvvm

import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding

abstract class AppVMFragment<VB:ViewBinding,VM: BaseVM> : BaseFragment<VB>() {


    protected lateinit var vm: VM

    override fun initOther() {
        activity?.let {
            vm = viewModel(it)
            vm.initData(arguments)
        }
        super.initOther()
    }

    protected abstract fun viewModel(fragmentActivity: FragmentActivity): VM

}
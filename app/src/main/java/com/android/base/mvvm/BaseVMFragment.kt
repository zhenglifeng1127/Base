package com.android.base.mvvm

import androidx.viewbinding.ViewBinding

/**
 * 和父类使用同一个viewModel时使用
 */
@Suppress("UNCHECKED_CAST")
abstract class BaseVMFragment<VB:ViewBinding,VM: BaseVM> : BaseFragment<VB>() {


    protected lateinit var vm: VM

    override fun initOther() {
        activity?.let {
            if(it is AppVMActivity<*, *>){
                vm = it.getParentVM() as VM
                vm.initData(arguments)
            }
            initVM()
        }
        super.initOther()
    }

    abstract fun initVM()

}
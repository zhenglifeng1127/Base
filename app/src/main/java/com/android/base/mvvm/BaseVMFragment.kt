package com.android.base.mvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding
import org.greenrobot.eventbus.EventBus
/**
 * 不沿用父类VM时使用
 */
abstract class BaseVMFragment<VM: BaseVM,VB:ViewBinding> : Fragment() {

    private var isFirstLoad = true

    protected var binding: VB? = null

    protected lateinit var vm: VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = bindView()
        return binding?.root
    }

    override fun onDestroyView() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
        super.onDestroyView()
        isFirstLoad = true
        binding = null
    }

    override fun onResume() {
        super.onResume()
        if(isFirstLoad){

            activity?.let {
                vm = viewModel(it)
                vm.initData(arguments)
            }

            initArgs()

            initData()

            initListener()

            isFirstLoad = false
        }
    }

    protected abstract fun viewModel(fragmentActivity: FragmentActivity): VM

    protected abstract fun bindView(): VB

    protected abstract fun initArgs()


    protected abstract fun initData()

    open fun initListener(){

    }





}
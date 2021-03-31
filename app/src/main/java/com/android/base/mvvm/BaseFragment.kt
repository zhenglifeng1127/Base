package com.android.base.mvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.greenrobot.eventbus.EventBus

abstract class BaseFragment : Fragment() {


    private var isFirstLoad = true


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return  inflater.inflate(bindView(), container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isFirstLoad = true
    }

    override fun onDestroy() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
        super.onDestroy()
    }


    override fun onResume() {
        super.onResume()
        if(isFirstLoad){
            initArgs()

            initReceiver()

            initData()

            initListener()

            isFirstLoad = false
        }
    }


    protected abstract fun bindView(): Int

    protected abstract fun initArgs()

    protected abstract fun initReceiver()

    protected abstract fun initData()

    open fun initListener(){

    }





}
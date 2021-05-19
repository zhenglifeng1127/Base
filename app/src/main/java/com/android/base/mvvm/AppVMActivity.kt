package com.android.base.mvvm

import androidx.viewbinding.ViewBinding
import com.android.base.loadsir.NetCallback
import com.android.base.ui.LoadingPopup
import com.android.base.utils.*
import com.kingja.loadsir.core.LoadSir


abstract class AppVMActivity<T:ViewBinding,VM:BaseVM>:BaseActivity<T>() {

    protected lateinit var viewModel: VM

    private var loading: LoadingPopup? = null

    private val service by lazy {
        LoadSir.getDefault().register(bind()) {
            checkNet(true)
        }
    }

    override fun initOther() {
        viewModel = viewModel()

        viewModel.initData(intent.extras)

        viewModel.loading.observe(this, {
            if (it) {
                loading = dialog().showLoad()
            } else {
                loading?.dismiss()
            }
        })

        lifecycle.addObserver(viewModel)
        super.initOther()
    }

    override fun initData() {
        if(config.build.IS_NET_CHECK){
            checkNet(false)
        }
        super.initData()
    }

    protected abstract fun viewModel(): VM

    /**
     * 绑定替换视图布局
     */
    protected open fun bind():Any = findViewById(android.R.id.content)

    private fun checkNet(isRefresh:Boolean) {
        if (!getActiveNetworkInfo()) {
            service.showCallback(NetCallback::class.java)
        }else{
            if(isRefresh){
                viewModel.onCreate()
            }
            service.showSuccess()
        }
    }

    fun getParentVM():VM{
        return viewModel
    }

}
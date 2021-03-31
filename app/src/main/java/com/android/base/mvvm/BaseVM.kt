package com.android.base.mvvm

import android.os.Bundle
import androidx.lifecycle.*
import com.android.base.utils.other.ToastUtils
import io.reactivex.rxjava3.disposables.CompositeDisposable


/**
 * 基础VM样式
 */
abstract class BaseVM : ViewModel(), LifecycleObserver {

    /**
     * 使用协程的可以不需要此项
     */
    val disposable: CompositeDisposable by lazy { CompositeDisposable() }

    /**
     * 绑定从intent中传递过来的数据参数，bundle方式
     */
    var extra: Bundle? = null

    /**
     * loading指示器开关，样式可以自定义
     */
    val loading :MutableLiveData<Boolean> = MutableLiveData(false)


    fun toast(msg: String) {
        ToastUtils.center(msg)
    }

    open fun initData(extra: Bundle?) {
        this.extra = extra
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    open fun onCreate() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    open fun onDestroy() {
        disposable.clear()
        System.gc()
    }


}
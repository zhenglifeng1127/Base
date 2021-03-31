package com.android.base.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

// 顶层函数版本
inline fun <reified T : ViewModel> getViewModel(owner: ViewModelStoreOwner, configLiveData: T.() -> Unit = {}): T =ViewModelProvider(owner).get(T::class.java).apply { configLiveData() }

// 扩展函数版本
inline fun <reified T : ViewModel> ViewModelStoreOwner.getSelfViewModel(configLiveData: T.() -> Unit = {}): T = getViewModel(this, configLiveData)
package com.android.base.mvvm

import androidx.lifecycle.MutableLiveData
import com.android.base.entity.CommonPost


/**
 * 基础类，分页列表使用，具体参照样例
 */
abstract class BasePageVM<T, E> : BaseVM() {

    var pagePost: CommonPost<E> = CommonPost(data = null)

    val list: MutableLiveData<MutableList<T>> = MutableLiveData()

    val load: MutableLiveData<Boolean> = MutableLiveData()

    /**
     * 初始化调用分页接口，第一次加载数据请使用refresh(),不要使用此方法获取
     */
    protected abstract fun getPageList()

    fun setListData(data: MutableList<T>, count: Int,perPage:Int = pagePost.perPage) {
        pagePost.isCanLoad = count > pagePost.page * perPage
        list.value = data
        load.value = false
        loading.value = false
    }

    fun loadError() {
        if (pagePost.page > 1) {
            pagePost.page = pagePost.page - 1
        }
        load.value = false
        loading.value = false
    }

    fun result(msg:String){
        toast(msg)
        load.value = false
        loading.value = false
    }

    fun loadMore() {
        pagePost.page = pagePost.page + 1
        pagePost.isLoad = true
        getPageList()
    }

    fun search(keyword:String?){
        pagePost.keyWord = keyword
        refresh()
    }

    fun refresh() {
        pagePost.page = 1
        pagePost.isLoad = false
        getPageList()
    }

    fun changeStatus(status: Int?) {
        pagePost.status = status
        refresh()
    }

}
package com.android.base.mvvm

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.android.base.ui.ClickSwipeRefreshLayout
import com.android.base.utils.clickChildItemDelay
import com.android.base.utils.clickItemDelay
import com.chad.library.adapter.base.BaseQuickAdapter


/**
 * 结合baseQuickAdapter库使用的recyclerview 分页处理基类
 */
@Suppress("UNCHECKED_CAST")
abstract class PageVMFragment<VB:ViewBinding,VM : BasePageVM<T,*>, P : BaseQuickAdapter<T, *>, T>
    : AppVMFragment<VB,VM>() {

    private var adapter: P? = null

    private val rvList by lazy { setLoadList() }

    private val refresh by lazy { setRefresh() }

    open fun emptyLoad(){

    }

    open fun success(){

    }


    /**
     * 设置子项点击ID，不设置3.0适配器无效
     */
    open fun viewIds():IntArray = intArrayOf()

    /**
     * 设置适配器
     */
    abstract fun setAdapterData(it: MutableList<T>): P

    /**
     * 绑定list
     */
    abstract fun setLoadList(): RecyclerView

    /**
     * 设置刷新无传空
     */
    abstract fun setRefresh(): ClickSwipeRefreshLayout?

    /**
     * 子项点击事件
     */
    abstract fun onChildClick(id: Int, item: T, position: Int)

    /**
     * 列表点击事件
     */
    abstract fun onItemClick(item: T, position: Int)

    /**
     * 注意如果重写此方法记得加上super.initData()
     */
    override fun initData() {
        vm.list.observe(this, Observer {
            it.let {
                if (vm.pagePost.isLoad) {
                    loadMore(it)
                } else {
                    setBody(it)
                }
            }

        })
        vm.load.observe(this, Observer {
            refresh?.isRefreshing = false
        })
    }

    override fun initListener() {
        refresh?.setOnRefreshListener {
            vm.refresh()
        }
        super.initListener()
    }


    private fun setBody(it: MutableList<T>) {
        if (it.isEmpty()) {
            adapter?.setNewInstance(null)
            emptyLoad()
        } else {
            success()
            if (adapter == null) {
                adapter = setAdapterData(it)
                adapter?.let {
                    rvList.adapter = adapter
                    it.loadMoreModule.setOnLoadMoreListener {
                        vm.loadMore()
                    }
                    rvList.clickChildItemDelay<T> (viewIds = viewIds()){ apt, id, position ->
                        onChildClick(id, apt.data[position], position)
                    }
                    rvList.clickItemDelay<T> { apt, position ->
                        onItemClick(apt.data[position], position)
                    }
                }
            } else {
                adapter?.setNewInstance(it)
            }
            setCanLoad()
        }
    }

    private fun loadMore(it: MutableList<T>) {
        if (it.isNotEmpty()) {
            adapter?.addData(it)
            setCanLoad()
        }
    }

    private fun setCanLoad() {
        adapter?.let {
            if (vm.pagePost.isCanLoad) {
                it.loadMoreModule.loadMoreComplete()
            } else {
                it.loadMoreModule.loadMoreEnd()
            }
        }
    }

}
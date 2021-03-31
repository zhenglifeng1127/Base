package com.android.base.utils

import com.android.base.entity.TabBean
import com.google.android.material.tabs.TabLayout

/**
 * 动态添加tab方法
 */
fun<T: TabBean> TabLayout.addItem(data:MutableList<T>, isShow:Boolean = false){
    this.removeAllTabs()
    for(item in data){
        addTab(newTab())
        val tab = getTabAt(tabCount-1)
        tab?.let {
            if(isShow)
                it.text = data[tabCount-1].text.plus("("+data[tabCount-1].value+")")
            else
                it.text = data[tabCount-1].text
        }
    }
}

/**
 * 变更tab字体大小拓展
 */
fun TabLayout.setLargeMode(normal:Int,large:Int){
    getTabAt(selectedTabPosition)?.updateText(normal,large,true)
    addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
        override fun onTabReselected(tab: TabLayout.Tab?) {

        }

        override fun onTabUnselected(tab: TabLayout.Tab) {
            tab.updateText(normal,large,false)
        }

        override fun onTabSelected(tab: TabLayout.Tab) {
            tab.updateText(normal,large,true)
        }

    })
}

fun TabLayout.Tab.updateText(normal:Int,large:Int,isSelect:Boolean){
    val str = this.text
    if(isSelect){
        this.text =str?.sizeSpan(large)
    }else{
        this.text =str?.sizeSpan(normal)
    }
}
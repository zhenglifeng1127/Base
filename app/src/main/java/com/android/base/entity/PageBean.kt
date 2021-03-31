package com.nxhz.base.bean

/**
 * 列表类型数据收主类
 */
data class PageBean<T> (val status:Int,var message:String?,val data: PageCountBean<T>?)
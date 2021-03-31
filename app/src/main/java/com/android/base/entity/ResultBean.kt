package com.nxhz.base.bean

/**
 * 非列表类型数据收主类
 */
data class ResultBean<T> (val status:Int,var message:String?,val data: T?)
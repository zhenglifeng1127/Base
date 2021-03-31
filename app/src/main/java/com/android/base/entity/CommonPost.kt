package com.android.base.entity

import com.android.base.config.Constants


/**
 * 分页式VM使用提交数据类
 */
data class CommonPost<T>(
    var page:Int = Constants.FIRST_PAGE,
    var perPage:Int = Constants.FIRST_PER_PAGE,
    var keyWord :String? =null,
    var status :Int? = 0,
    var isLoad:Boolean = false,
    var isCanLoad:Boolean = false,
    var lat:Double? = null,
    var lng:Double? = null,
    var data :T ?=null
    )
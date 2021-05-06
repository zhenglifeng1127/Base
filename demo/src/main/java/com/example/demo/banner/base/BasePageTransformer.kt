package com.example.demo.banner.base

abstract class BasePageTransformer {
    /**
     * 切换页面
     *
     * @param pagerState [PagerState]
     */
    abstract fun transformPage(pagerState: PagerState)
}
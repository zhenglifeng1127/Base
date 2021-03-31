package com.android.base.web

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.util.AttributeSet
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import com.android.base.utils.nullString

class X5WebView : WebView {
    var title: TextView? = null

    @SuppressLint("SetJavaScriptEnabled")
    constructor(arg0: Context, arg1: AttributeSet?) : super(getFixedContext(arg0), arg1) {
        /**
         * 防止加载网页时调起系统浏览器
         */
        val client: WebViewClient = object : WebViewClient() {
            /**
             * 防止加载网页时调起系统浏览器
             */
            override fun shouldOverrideUrlLoading(view: WebView, url: String?): Boolean {
                view.loadUrl(url.nullString())
                return true
            }
        }
        this.webViewClient = client
        // this.setWebChromeClient(chromeClient);
        // WebStorage webStorage = WebStorage.getInstance();
        initWebViewSettings()
        this.isClickable = true
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebViewSettings() {
        val webSetting: WebSettings = this.settings
        webSetting.javaScriptEnabled = true
        webSetting.javaScriptCanOpenWindowsAutomatically = true
        webSetting.allowFileAccess = true
        webSetting.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
        webSetting.builtInZoomControls = true
        webSetting.useWideViewPort = true
        webSetting.setSupportMultipleWindows(true)
        // webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true)
        // webSetting.setDatabaseEnabled(true);
        webSetting.domStorageEnabled = true
        webSetting.setGeolocationEnabled(true)
        webSetting.setSupportZoom(false)
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE)
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.pluginState = WebSettings.PluginState.ON_DEMAND
        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSetting.cacheMode = WebSettings.LOAD_NO_CACHE

        val appCachePath = context.applicationContext.cacheDir.absolutePath;
        webSetting.setAppCachePath(appCachePath);

        // this.getSettingsExtension().setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);//extension
        // settings 的设计
    }

    constructor(arg0: Context) : super(getFixedContext(arg0)) {
        setBackgroundColor(85621)
    }

    companion object{
        fun getFixedContext(context: Context): Context{
            return if (Build.VERSION.SDK_INT in 21..22) context.createConfigurationContext(
                Configuration()
            ) else context
        }
    }

}

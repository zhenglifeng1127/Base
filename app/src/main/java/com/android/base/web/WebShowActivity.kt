package com.android.base.web
import android.os.Bundle
import com.android.base.R
import com.android.base.config.AppManager
import com.android.base.config.Constants
import com.android.base.databinding.ActivityWebShowBinding
import com.android.base.mvvm.AppVMActivity
import com.android.base.utils.getSelfViewModel
import com.android.base.utils.openNext
import com.android.base.utils.other.CommonJs
import com.android.base.utils.other.ToolbarUtils
import com.android.base.utils.visible

class WebShowActivity : AppVMActivity<ActivityWebShowBinding,WebShowVM>() {


    companion object{
        fun goToActivity(title:String ="",url:String,type:Int){
            AppManager.endOfStack().openNext<WebShowActivity>(Bundle().apply {
                putInt("type",type)
                putString("url",url)
                putString("title",title)
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.apply {
            webView.removeAllViews()

            webView.clearHistory()

            webView.clearCache(true)

            webView.destroy()
        }

    }


    override fun viewModel(): WebShowVM  = getSelfViewModel {
        webUrl.observe(this@WebShowActivity, {
            binding.webView.post {
                binding.webView.loadUrl(it)
            }
        })

        webText.observe(this@WebShowActivity,{
            binding.webView.post {
                binding.webView.loadDataWithBaseURL(
                    null,
                    it + Constants.WEB_STYLE,
                    "text/html",
                    "UTF-8",
                    null
                )
            }
        })
    }

    override fun initConfig(bundle: Bundle?) {
        intent.extras?.let {
            val title = it.getString("title","")
            if(title.isEmpty()){
                binding.titleView.root.visible(false)
            }else{
                ToolbarUtils.Builder().apply {
                    toolbar = binding.titleView.toolbar
                    titleView = binding.titleView.toolbarTitle
                    titleText = title
                    titleColor = R.color.tc1
                    icon = R.mipmap.ic_back_black
                    color = R.color.white
                }.build()
            }
            binding. webView.addJavascriptInterface(CommonJs(),"CommonJS")
        }
    }
}
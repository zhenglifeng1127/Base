package com.android.base.web

import androidx.lifecycle.MutableLiveData
import com.android.base.config.Constants
import com.android.base.mvvm.BaseVM

class WebShowVM : BaseVM(){


    val webText by lazy { MutableLiveData<String>() }

    val webUrl by lazy { MutableLiveData<String>() }

    override fun onCreate() {
        super.onCreate()
        extra?.let {
            when(it.getInt("type",0)){
                0->{
                    webUrl.value = it.getString("url","")
                }
                1->{
                    webText.value = it.getString("url","")
                }
                2->{
                    webUrl.value = "${Constants.BASE_URL}${it.getString("url","")}"
                }
                else -> {

                }
            }
        }
    }

}
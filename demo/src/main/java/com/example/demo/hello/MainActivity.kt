package com.example.demo.hello

import android.os.Bundle
import com.android.base.mvvm.BaseActivity
import com.example.demo.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun initConfig(bundle: Bundle?) {
        binding.compose.setContent {
            rootSuffer()
        }
    }
}
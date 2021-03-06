package com.example.demo.hello

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.android.base.mvvm.BaseActivity
import com.example.demo.databinding.ActivityMainBinding
import com.example.demo.theme.ComposeTheme


class MainActivity : BaseActivity<ActivityMainBinding>() {

    @ExperimentalAnimationApi
    @ExperimentalFoundationApi
    override fun initConfig(bundle: Bundle?) {
        binding.compose.setContent {
            ComposeTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    rootSuffer(config = config)
                }
            }

        }
//        setContent {
//
//
//        }
    }
}
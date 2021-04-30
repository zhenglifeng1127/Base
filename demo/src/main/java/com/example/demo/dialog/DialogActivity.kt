package com.example.demo.dialog

import android.os.Bundle
import com.android.base.mvvm.BaseActivity
import com.example.demo.databinding.ActivityDialogBinding

class DialogActivity : BaseActivity<ActivityDialogBinding>() {


    override fun initConfig(bundle: Bundle?) {
        binding.compose.setContent {
            showDialogDemo()
        }
    }
}
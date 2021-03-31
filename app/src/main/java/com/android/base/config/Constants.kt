package com.android.base.config

import android.os.Environment


class Constants {

    companion object {

        const val BASE_URL = ""
//
        val isDebug: Boolean by lazy { true}


        val GALLERY_PATH by lazy {
            AppManager.endOfStack().getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.path + "/"
        }

        val VIDEO_PATH by lazy {
            AppManager.endOfStack().getExternalFilesDir(Environment.DIRECTORY_MOVIES)?.path + "/"
        }

        val spName: String by lazy { "SP_MODE" }

        const val FIRST_PAGE = 1

        const val FIRST_PER_PAGE = 10

        const val WEB_STYLE = "<style>\n" +
                "img{max-width:100%; height:auto}\n" +
                "video{max-width:100%;height:auto}\n" +
                "</style>"

        val TYPES = arrayOf(
            "全屏（竖屏）", "全屏（横屏）", "弹窗（竖屏）",
            "弹窗（横屏）", "底部弹窗", "自定义View", "自定义View（Xml）"
        )


    }
}
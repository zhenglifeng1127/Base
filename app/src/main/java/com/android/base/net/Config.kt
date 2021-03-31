package com.android.base.net

interface Config {
    /**
     * 网络调用配置
     */
    interface NETWORK {
        companion object {
            const val CONNECT_TIME_OUT = 30L

            const val WRITE_TIME_OUT = 40L

            const val READ_TIME_OUT = 40L
        }
    }

}
package com.android.base.utils.other

import android.Manifest
import android.os.Build

class Permission {

    companion object {
        /**
         * 日历权限组
         */
        val CALENDAR by lazy {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                arrayOf()
            } else {
                arrayOf(Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR)
            }
        }

        /**
         * 相机权限
         */
        val CAMERA by lazy {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                arrayOf()
            } else {
                arrayOf(Manifest.permission.CAMERA)
            }
        }

        /**
         * 通讯录权限
         */
        val CONTACTS by lazy {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                arrayOf()
            } else {
                arrayOf(
                    Manifest.permission.READ_CONTACTS,
                    Manifest.permission.WRITE_CONTACTS,
                    Manifest.permission.GET_ACCOUNTS
                )
            }
        }

        /**
         * 定位权限
         */
        val LOCATION by lazy {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                arrayOf()
            } else  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M&&Build.VERSION.SDK_INT <= Build.VERSION_CODES.P){
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                arrayOf(
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
                )
            }else{
                arrayOf()
            }
        }

        /**
         * 录制音频权限
         */
        val MICROPHONE by lazy {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                arrayOf()
            } else {
                arrayOf(Manifest.permission.RECORD_AUDIO)
            }
        }


        /**
         * 电话权限组
         */
        val PHONE by lazy {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                arrayOf()
            } else {
                arrayOf(
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.CALL_PHONE,
                    Manifest.permission.READ_CALL_LOG,
                    Manifest.permission.WRITE_CALL_LOG,
                    Manifest.permission.USE_SIP,
                    Manifest.permission.PROCESS_OUTGOING_CALLS
                )
            }
        }

        /**
         * 传感器权限
         */
        val SENSORS by lazy {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                arrayOf()
            } else {
                arrayOf(Manifest.permission.BODY_SENSORS)
            }
        }

        /**
         * 短信权限
         */
        val SMS by lazy {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                arrayOf()
            } else {
                arrayOf(
                    Manifest.permission.SEND_SMS,
                    Manifest.permission.RECEIVE_SMS,
                    Manifest.permission.READ_SMS,
                    Manifest.permission.RECEIVE_WAP_PUSH,
                    Manifest.permission.RECEIVE_MMS
                )
            }
        }

        /**
         * 读写，Q该权限有变更
         */
        val STORAGE by lazy {
           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M&&Build.VERSION.SDK_INT <= Build.VERSION_CODES.R){
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            }else{
                arrayOf()
            }
        }

        /**
         * 应用安装权限
         */
        val APK by lazy {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                arrayOf()
            } else {
                arrayOf(Manifest.permission.REQUEST_INSTALL_PACKAGES)
            }
        }

    }
}
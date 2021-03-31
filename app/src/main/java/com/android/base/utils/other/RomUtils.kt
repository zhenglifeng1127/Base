package com.android.base.utils.other

import android.annotation.SuppressLint
import android.os.Build
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class RomUtils {
    companion object {
        const val ROM_MIUI = "MIUI"
        const val ROM_EMUI = "EMUI"
        const val ROM_FLYME = "FLYME"
        const val ROM_OPPO = "OPPO"
        const val ROM_SMARTISAN = "SMARTISAN"
        const val ROM_VIVO = "VIVO"
        const val ROM_QIKU = "QIKU"

        private val KEY_VERSION_MIUI = "ro.miui.ui.version.name"
        private val KEY_VERSION_EMUI = "ro.build.version.emui"
        private val KEY_VERSION_OPPO = "ro.build.version.opporom"
        private val KEY_VERSION_SMARTISAN = "ro.smartisan.version"
        private val KEY_VERSION_VIVO = "ro.vivo.os.version"

        private var sName: String? = null
        private var sVersion: String? = null


        //华为
        fun isEmui(): Boolean {
            return check(ROM_EMUI)
        }

        //小米
        fun isMiui(): Boolean {
            return check(ROM_MIUI)
        }

        //vivo
        fun isVivo(): Boolean {
            return check(ROM_VIVO)
        }

        //oppo
        fun isOppo(): Boolean {
            return check(ROM_OPPO)
        }

        //魅族
        fun isFlyme(): Boolean {
            return check(ROM_FLYME)
        }

        //360手机
        fun is360(): Boolean {
            return check(ROM_QIKU) || check("360")
        }

        fun isSmartisan(): Boolean {
            return check(ROM_SMARTISAN)
        }

        fun getName(): String? {
            if (sName == null) {
                check("")
            }
            return sName
        }

        fun getVersion(): String? {
            if (sVersion == null) {
                check("")
            }
            return sVersion
        }


        @SuppressLint("DefaultLocale")
        fun check(rom: String): Boolean {
            if (sName != null) {
                return sName == rom
            }

            if (!getProp(KEY_VERSION_MIUI).isNullOrEmpty()) {
                sVersion = getProp(KEY_VERSION_MIUI)
                sName = ROM_MIUI
            } else if (!getProp(KEY_VERSION_EMUI).isNullOrEmpty()) {
                sVersion = getProp(KEY_VERSION_EMUI)
                sName = ROM_EMUI
            } else if (!getProp(KEY_VERSION_OPPO).isNullOrEmpty()) {
                sVersion = getProp(KEY_VERSION_OPPO)
                sName = ROM_OPPO
            } else if (!getProp(KEY_VERSION_VIVO).isNullOrEmpty()) {
                sVersion = getProp(KEY_VERSION_VIVO)
                sName = ROM_VIVO
            } else if (!getProp(KEY_VERSION_SMARTISAN).isNullOrEmpty()) {
                sVersion = getProp(KEY_VERSION_SMARTISAN)
                sName = ROM_SMARTISAN
            } else {
                sVersion = Build.DISPLAY
                if (sVersion!!.toUpperCase().contains(ROM_FLYME)) {
                    sName = ROM_FLYME
                } else {
                    sVersion = Build.UNKNOWN
                    sName = Build.MANUFACTURER.toUpperCase()
                }
            }
            return sName == rom
        }

        fun getProp(name: String): String? {
            var line: String? = null
            var input: BufferedReader? = null
            try {
                val p = Runtime.getRuntime().exec("getprop $name")
                input = BufferedReader(InputStreamReader(p.inputStream), 1024)
                line = input.readLine()
                input.close()
            } catch (ex: IOException) {
                return null
            } finally {
                if (input != null) {
                    try {
                        input.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }
            }
            return line
        }
    }
}
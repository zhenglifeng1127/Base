package com.android.base.utils.other

import kotlin.math.*

/**
 * 坐标系转化
 */
class PointUtils {

    companion object {
        val BAIDU_LBS_TYPE = "bd09ll"

        var pi = 3.1415926535897932384626
        var a = 6378245.0
        var ee = 0.00669342162296594323

        /**
         * 84 to 火星坐标系 (GCJ-02) World Geodetic System ==> Mars Geodetic System
         *
         * @param lat
         * @param lon
         * @return
         */
        fun gps84_To_Gcj02(lat: Double, lon: Double): Gps? {
            if (outOfChina(lat, lon)) {
                return null
            }
            var dLat = transformLat(lon - 105.0, lat - 35.0)
            var dLon = transformLon(lon - 105.0, lat - 35.0)
            val radLat = lat / 180.0 * pi
            var magic = sin(radLat)
            magic = 1 - ee * magic * magic
            val sqrtMagic = sqrt(magic)
            dLat = dLat * 180.0 / (a * (1 - ee) / (magic * sqrtMagic) * pi)
            dLon = dLon * 180.0 / (a / sqrtMagic * cos(radLat) * pi)
            val mgLat = lat + dLat
            val mgLon = lon + dLon
            return Gps(mgLat, mgLon)
        }

        /**
         * * 火星坐标系 (GCJ-02) to 84 * * @param lon * @param lat * @return
         */
        fun gcj_To_Gps84(lat: Double, lon: Double): Gps {
            val gps = transform(lat, lon)
            val lontitude = lon * 2 - gps.wgLon
            val latitude = lat * 2 - gps.wgLat
            return Gps(latitude, lontitude)
        }

        /**
         * 火星坐标系 (GCJ-02) 与百度坐标系 (BD-09) 的转换算法 将 GCJ-02 坐标转换成 BD-09 坐标
         *
         * @param gg_lat
         * @param gg_lon
         */
        fun gcj02_To_Bd09(gg_lat: Double, gg_lon: Double): Gps {
            val z = sqrt(gg_lon * gg_lon + gg_lat * gg_lat) + 0.00002 * sin(gg_lat * pi)
            val theta = atan2(gg_lat, gg_lon) + 0.000003 * cos(gg_lon * pi)
            val bdLon = z * cos(theta) + 0.0065
            val bdLat = z * sin(theta) + 0.006
            return Gps(bdLat, bdLon)
        }

        /**
         * * 火星坐标系 (GCJ-02) 与百度坐标系 (BD-09) 的转换算法 * * 将 BD-09 坐标转换成GCJ-02 坐标 * * @param
         * bd_lat * @param bd_lon * @return
         */
        fun bd09_To_Gcj02(bd_lat: Double, bd_lon: Double): Gps {
            val x = bd_lon - 0.0065
            val y = bd_lat - 0.006
            val z = sqrt(x * x + y * y) - 0.00002 * sin(y * pi)
            val theta = atan2(y, x) - 0.000003 * cos(x * pi)
            val ggLon = z * cos(theta)
            val ggLat = z * sin(theta)
            return Gps(ggLat, ggLon)
        }

        /**
         * (BD-09)-->84
         * @param bd_lat
         * @param bd_lon
         * @return
         */
        fun bd09_To_Gps84(bd_lat: Double, bd_lon: Double): Gps {

            val gcj02 = bd09_To_Gcj02(bd_lat, bd_lon)
            return gcj_To_Gps84(
                gcj02.wgLat,
                gcj02.wgLon
            )

        }

        fun outOfChina(lat: Double, lon: Double): Boolean {
            return if (lon < 72.004 || lon > 137.8347) true else lat < 0.8293 || lat > 55.8271
        }

        fun transform(lat: Double, lon: Double): Gps {
            if (outOfChina(lat, lon)) {
                return Gps(lat, lon)
            }
            var dLat = transformLat(lon - 105.0, lat - 35.0)
            var dLon = transformLon(lon - 105.0, lat - 35.0)
            val radLat = lat / 180.0 * pi
            var magic = Math.sin(radLat)
            magic = 1 - ee * magic * magic
            val sqrtMagic = sqrt(magic)
            dLat = dLat * 180.0 / (a * (1 - ee) / (magic * sqrtMagic) * pi)
            dLon = dLon * 180.0 / (a / sqrtMagic * cos(radLat) * pi)
            val mgLat = lat + dLat
            val mgLon = lon + dLon
            return Gps(mgLat, mgLon)
        }

        fun transformLat(x: Double, y: Double): Double {
            var ret = (-100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y
                    + 0.2 * sqrt(abs(x)))
            ret += (20.0 * sin(6.0 * x * pi) + 20.0 * sin(2.0 * x * pi)) * 2.0 / 3.0
            ret += (20.0 * sin(y * pi) + 40.0 * sin(y / 3.0 * pi)) * 2.0 / 3.0
            ret += (160.0 * sin(y / 12.0 * pi) + 320 * sin(y * pi / 30.0)) * 2.0 / 3.0
            return ret
        }

        fun transformLon(x: Double, y: Double): Double {
            var ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * sqrt(abs(x))
            ret += (20.0 * sin(6.0 * x * pi) + 20.0 * sin(2.0 * x * pi)) * 2.0 / 3.0
            ret += (20.0 * sin(x * pi) + 40.0 * sin(x / 3.0 * pi)) * 2.0 / 3.0
            ret += (150.0 * sin(x / 12.0 * pi) + 300.0 * sin(x / 30.0 * pi)) * 2.0 / 3.0
            return ret
        }

    }


    data class Gps(
        var wgLat: Double = 0.0,
        var wgLon: Double = 0.0
    ){
        override fun toString(): String {
            return "$wgLat,$wgLon"
        }
    }
}
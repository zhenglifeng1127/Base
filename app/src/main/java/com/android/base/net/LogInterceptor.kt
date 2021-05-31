package com.android.base.net

import android.os.SystemClock
import android.util.Log
import androidx.collection.ArrayMap
import okhttp3.*
import okio.Buffer
import java.util.*

class LogInterceptor:Interceptor {

    companion object{
        var TAG = LogInterceptor::class.java.simpleName

        private val headerIgnoreMap = ArrayMap<String, String>()

    }

    init {
        headerIgnoreMap["Host"] = ""
        headerIgnoreMap["Connection"] = ""
        headerIgnoreMap["Accept-Encoding"] = ""
    }

    protected fun log(message: String) {
        Log.i(TAG, message)
    }




    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val startTime = SystemClock.elapsedRealtime()
        val response = chain.proceed(chain.request())
        val endTime = SystemClock.elapsedRealtime()
        val duration = endTime - startTime
        //url
        val url = request.url.toString()
        log("----------Request Start----------")
        log("" + request.method + " " + url)
        //headers
        val headers = request.headers
        for(i in 0 until headers.size){
            if (!headerIgnoreMap.containsKey(headers.name(i))) {
                log(headers.name(i) + ": " + headers.value(i))
            }
        }

        //param
        val paramString: String = readRequestParamString(request.body)
        if (paramString.isNotEmpty()) {
            log("Params:$paramString")
        }

        //response
        var responseString: String? = ""
        response.body?.let {
            responseString = if (isPlainText(it.contentType())) {
                readContent(response)
            } else {
                "other-type=" + it.contentType()
            }
        }
        log("Response Body:$responseString")
        log("Time:$duration ms")
        log("----------Request End----------")
        return response
    }


    private fun readRequestParamString(requestBody: RequestBody?): String {
        return if (requestBody is MultipartBody) { //判断是否有文件
            val sb = StringBuilder()
            requestBody.parts.forEach {
                if (sb.isNotEmpty()) {
                    sb.append(",")
                }
                if (isPlainText(it.body.contentType())) {
                    sb.append(readContent(it.body))
                } else {
                    sb.append("other-param-type=").append(it.body.contentType())
                }

            }
            sb.toString()
        } else {
            readContent(requestBody)
        }
    }


    private fun readContent(body: RequestBody?): String {
        if (body == null) {
            return ""
        }
        val buffer = Buffer()
        try {
            //小于2m
            if (body.contentLength() <= 2 * 1024 * 1024) {
                body.writeTo(buffer)
            } else {
                return "content is more than 2M"
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return buffer.readUtf8()
    }


    private fun readContent(response: Response?): String {
        if (response == null) {
            return ""
        }
        try {
            return response.peekBody(Long.MAX_VALUE).string()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    private fun isPlainText(mediaType: MediaType?): Boolean {
        mediaType?.let {
            val mediaTypeString = it.toString().toLowerCase(Locale.CHINA)
            if (mediaTypeString.contains("text") || mediaTypeString.contains("application/json")) {
                return true
            }
        }
        return false
    }

}
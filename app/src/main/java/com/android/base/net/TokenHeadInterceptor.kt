package com.android.base.net

import com.android.base.utils.nullString
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * token拦截器附带刷新
 */
class TokenHeadInterceptor : Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {
        var token = ""
        val request: Request = chain.request().newBuilder()
            .addHeader(
                "token", token.nullString("")
            ).build()
        val response = chain.proceed(request)
        if(isTokenFail(response)){
            token = getNewToken()
            request.newBuilder()
                .addHeader(
                    "token", token.nullString("")
                ).build()
            return chain.proceed(request)
        }

        return response
    }


    private fun isTokenFail(response: Response):Boolean{
        return response.code() == 401
    }

    private fun getNewToken():String{

        return ""
    }
}
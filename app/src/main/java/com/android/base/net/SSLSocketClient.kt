package com.nxhz.rider.net.base

import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

class SSLSocketClient {
    companion object{
        fun getSSLSocketFactory(): javax.net.ssl.SSLSocketFactory {
            try {
                val sslContext = SSLContext.getInstance("SSL")
                sslContext.init(null,
                    getTrustManager(), SecureRandom())
                return sslContext.socketFactory
            } catch (e: Exception) {
                throw RuntimeException(e)
            }

        }

        //获取TrustManager
        fun getTrustManager(): Array<X509TrustManager> {
            return arrayOf(object : X509TrustManager {
                override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}

                override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }
            })
        }

        fun getHostnameVerifier(): HostnameVerifier {
            return  HostnameVerifier { _,_ -> true }
        }
    }



}
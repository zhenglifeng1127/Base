package com.android.base.net

import com.android.base.config.Constants
import com.nxhz.rider.net.base.SSLSocketClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.Proxy
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetModule {


    @Provides
    fun provideNewApiService(retrofit: Retrofit): Api =
        retrofit.create(Api::class.java)

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .proxy(Proxy.NO_PROXY)//网络代理设置NO可以防止wifi代理抓包
            .connectTimeout(Config.NETWORK.CONNECT_TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(Config.NETWORK.WRITE_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(Config.NETWORK.READ_TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(TokenHeadInterceptor())
            .addInterceptor(LogInterceptor())
            .retryOnConnectionFailure(true)//断网重连
            .sslSocketFactory(
                SSLSocketClient.getSSLSocketFactory(),
                SSLSocketClient.getTrustManager()[0]
            )
            .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
            .build()
    }



}
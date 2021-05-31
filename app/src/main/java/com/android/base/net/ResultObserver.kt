package com.android.base.net

import com.android.base.config.L
import com.android.base.entity.LoginFailEvent
import com.android.base.utils.nullString
import com.android.base.utils.other.ToastUtils
import com.nxhz.base.bean.ResultBean

import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import org.greenrobot.eventbus.EventBus
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException


abstract class ResultObserver<T> : Observer<ResultBean<T>> {

    private var compositeDisposable: CompositeDisposable? = null
    private var s: Disposable? = null
    private var observerSize: Int = 0


    constructor(compositeDisposable: CompositeDisposable) {
        this.compositeDisposable = compositeDisposable
    }


    constructor(compositeDisposable: CompositeDisposable, size: Int) {
        this.compositeDisposable = compositeDisposable
        observerSize = size
    }

    override fun onSubscribe(d: Disposable?) {
        s = d
        compositeDisposable!!.add(s!!)
    }


    override fun onNext(result: ResultBean<T>) {
        check()
        when(result.status){
            401->{
                onTokenFail()
                return
            }
            1->{
                result.data?.let {
                    onSuccess(it, result.message.nullString("成功"))
                }
            }
            0->{
                onError("0",result.message.nullString("失败"))
            }
            405->{
                ToastUtils.center("对不起当前用户已被禁用，无法使用")
            }
        }

    }

    private fun check() {
        if (0 == observerSize - 1) {
            compositeDisposable!!.remove(s!!)
        } else {
            observerSize -= 1
        }
    }


    override fun onError(t: Throwable) {
        //失败处理
        check()
        L.i(t.message.toString())
        when (t) {
            is SocketTimeoutException, is TimeoutException -> {
                onError("error on time", "连接超时，请稍候再试")
            }
            is ConnectException ->{
                onError("error on connect", "连接失败，请稍候再试")
            }
            is UnknownHostException ->{
                onError("error on host", "连接失败，请稍候再试")
            }
            is JSONException ->{
                onError("error on JSON", "连接失败，请稍候再试")
            }
            is HttpException ->{
                when(val code = t.response()?.code()){
                    401 -> onTokenFail()
                    202 -> onError("202", "连接失败，请稍候再试")
                    else -> {
                        onError(code.toString(), "连接失败，请稍候再试")
                    }
                }
            }
            else ->{
                onError("error on other", "连接失败，请稍候再试")
            }
        }
    }

    override fun onComplete() {

    }

    abstract fun onError(code: String, msg: String)

    open fun onTokenFail() {
        //token超时具体根据实际CODE
        onError("401","登录已失效请重新登录")
        EventBus.getDefault().post(LoginFailEvent())
    }

    abstract fun onSuccess(t: T, msg: String)


}

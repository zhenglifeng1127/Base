package com.android.base.net

import com.android.base.config.L
import com.android.base.entity.LoginFailEvent
import com.android.base.utils.other.ToastUtils
import com.nxhz.base.bean.PageBean
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

/**
 * 仅供参考，实际根据接口而定
 */
abstract class PageObserver<T> : Observer<PageBean<T>> {

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

    override fun onSubscribe(d: Disposable) {
        s = d
        s?.let {
            compositeDisposable?.add(it)
        }
    }

    override fun onNext(result: PageBean<T>) {
        check()
        when(result.status){
            405->{
                ToastUtils.center("对不起当前用户已被禁用，无法使用")
            }
            401->{
                onTokenFail()
            }
            else->{
                result.data?.let {
                    if (it.data.isNullOrEmpty()) {
                        val empty: MutableList<T> = ArrayList()
                        onSuccess(
                            empty,
                            result.message.toString(),
                            it.total,
                            it.per_page
                        )
                    } else{
                        it.data?.let {d->
                            onSuccess(
                                d,
                                result.message.toString(),
                                it.total,
                                it.per_page
                            )
                        }
                    }
                }
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
        L.i("error msg", t.toString())
        when (t) {
            is SocketTimeoutException, is TimeoutException -> {
                onError("error on time", "连接超时，请稍候再试")
            }
            is ConnectException -> {
                onError("error on connect", "连接失败，请稍候再试")
            }
            is UnknownHostException -> {
                onError("error on host", "连接失败，请稍候再试")
            }
            is JSONException -> {
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
            else -> {
                onError("error on other", "连接失败，请稍候再试")
            }
        }
    }

    override fun onComplete() {

    }

    abstract fun onError(code: String, msg: String)

    private fun onTokenFail() {
        //token超时具体根据实际CODE
        onError("401","登录已经失效,请重新登录")
        EventBus.getDefault().post(LoginFailEvent())
    }

    abstract fun onSuccess(t: MutableList<T>, msg: String, count: Int,perPage:Int)


}

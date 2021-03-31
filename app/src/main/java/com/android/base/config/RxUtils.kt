package com.android.base.config

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.schedulers.Schedulers


/**
 * 处理线程切换
 */
object RxUtils {


    /**
     * 线程切换
     */
    fun <T> maybeToMain(): MaybeTransformer<T, T> {

        return MaybeTransformer { upstream ->
            upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

    /**
     * 线程切换
     */
    fun <T> singleToMain(): SingleTransformer<T, T> {

        return SingleTransformer { upstream ->
            upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

    /**
     * 线程切换
     */
    fun <T> flowableToMain(): FlowableTransformer<T, T> {

        return FlowableTransformer { upstream ->
            upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

    fun <T> observableToMain(): ObservableTransformer<T, T> {

        return ObservableTransformer { upstream ->
            upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }
}
package com.android.base.mvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import org.greenrobot.eventbus.EventBus
import java.lang.reflect.ParameterizedType

@Suppress("UNCHECKED_CAST")
abstract class BaseFragment<VB: ViewBinding> : Fragment() {


    private var isFirstLoad = true

    protected var binding: VB? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val type = javaClass.genericSuperclass
        if (type is ParameterizedType) {
            val cls = type.actualTypeArguments[0] as? Class<VB>
            val method = cls?.getMethod("inflate", LayoutInflater::class.java)
            binding = method?.invoke(null, layoutInflater) as VB
        }
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isFirstLoad = true
    }

    override fun onDestroy() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }

        super.onDestroy()
        isFirstLoad = true
        binding = null
    }


    override fun onResume() {
        super.onResume()
        if(isFirstLoad){

            initOther()

            initConfig()

            initData()

            initListener()

            isFirstLoad = false
        }
    }

    protected open fun initOther(){

    }

//    protected abstract fun bindView(): VB

    protected abstract fun initConfig()

    protected abstract fun initData()

    open fun initListener(){

    }


    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {

        if (enter) {
            //如何nextAnim是-1或0那么就是没有设置转场动画，直接走super就行了
            if (nextAnim > 0) {
                val animation = AnimationUtils.loadAnimation(requireActivity(), nextAnim)
                //延迟100毫秒执行让View有一个初始化的时间，防止初始化时刷新页面与动画刷新冲突造成卡顿
                animation.startOffset = 100
                animation.setAnimationListener(object : Animation.AnimationListener{
                    override fun onAnimationStart(animation: Animation?) {
                    }

                    override fun onAnimationEnd(animation: Animation?) {
                        onEnterAnimEnd()
                    }

                    override fun onAnimationRepeat(animation: Animation?) {
                    }

                })
                return animation
            } else {
                onEnterAnimEnd()
            }
        } else {
            if (nextAnim > 0) {
                val animation = AnimationUtils.loadAnimation(requireActivity(), nextAnim)
                //延迟100毫秒执行让View有一个初始化的时间，防止初始化时刷新页面与动画刷新冲突造成卡顿
                animation.startOffset = 100
                return animation
            }
        }
        return super.onCreateAnimation(transit, enter, nextAnim)

    }

    /**
     * 如果真的要延迟初始化，那么重写这个方法，等动画结束了再初始化
     */
    fun onEnterAnimEnd(){

    }


}
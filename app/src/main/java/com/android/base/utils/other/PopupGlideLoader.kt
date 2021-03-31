package com.android.base.utils.other

import android.content.Context
import android.widget.ImageView
import androidx.annotation.NonNull
import com.android.base.utils.centerFit
import com.bumptech.glide.Glide
import com.lxj.xpopup.interfaces.XPopupImageLoader
import java.io.File

/**
 * XPopup用图片加载器，可根据内容自定义
 */
class PopupGlideLoader() : XPopupImageLoader {
    override fun loadImage(position: Int, uri: Any, imageView: ImageView) {
        imageView.centerFit(uri.toString())
    }


    //必须实现这个方法，返回uri对应的缓存文件，可参照下面的实现，内部保存图片会用到。如果你不需要保存图片这个功能，可以返回null。
    override fun getImageFile(@NonNull context: Context, @NonNull uri: Any): File? {
        try {
            return Glide.with(context).downloadOnly().load(uri).submit().get()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }
}
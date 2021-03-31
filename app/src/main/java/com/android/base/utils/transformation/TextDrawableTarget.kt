package com.android.base.utils.transformation

import android.graphics.drawable.Drawable
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition

class TextDrawableTarget(
    @param:NonNull private val tv: TextView, val type: DrawableType, val width:Int
) : SimpleTarget<Drawable?>() {
    override fun onLoadFailed(@Nullable errorDrawable: Drawable?) {}


    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable?>?) {
        tv.drawable(resource,type,width)
    }

}

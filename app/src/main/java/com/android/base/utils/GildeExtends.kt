package com.android.base.utils


import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import com.android.base.config.AppManager
import com.android.base.utils.transformation.CornersTransformation
import com.android.base.utils.transformation.GlideBlurTransformation
import com.android.base.utils.transformation.TextDrawableTarget
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions

/**
 * Glide辅助拓展
 */

/**
 * 设置本地资源
 */
fun ImageView.localImage(id: Int) {
    localImage(this.context, id, this)
}

/**
 * 设置上圆角
 */
fun ImageView.cornerTop(url: String, size: Int) {
    if (url.isNotEmpty()) {
        cornerTop(this.context, url, this, size)
    }
}

/**
 * 设置上圆角
 */
fun ImageView.cornerTop(@DrawableRes id: Int, size: Int) {
    cornerTop(this.context, id, this, size)
}


/**
 * 设置圆角
 */
fun ImageView.corner(url: String, size: Int, defaultValue: Int = -1) {
    if (url.isNotEmpty()) {
        corner(this.context, url, this, size, defaultValue)
    }
}

/**
 * 设置圆形
 */
fun ImageView.circle(url: String, defaultValue: Int = -1) {
    if (url.isNotEmpty()) {
        circle(this.context, url, this, defaultValue)
    }
}

/**
 * 设置高斯模糊
 */
fun ImageView.blur(url: String, defaultValue: Int = -1) {
    if (url.isNotEmpty()) {
        blur(this.context, url, this, defaultValue)
    }
}


/**
 * 设置网络图片
 */
fun ImageView.centerFit(url: String, defaultValue: Int = -1) {
    if (url.isNotEmpty()) {
        centerFit(this.context, url, this, defaultValue)
    }
}


fun TextView.netDrawable(
    url: String,
    width: Int,
    height: Int = width,
    direction: DrawableType,
    size: Int = 4,
    isCrop: Boolean = false,
    isCorners: Boolean = false
) {
    if (url.isNotEmpty()) {
        getDrawText(
            context,
            url,
            width,
            height,
            this,
            direction,
            size,
            isCrop,
            isCorners
        )
    }
}



//-------------glide------------------


private fun localImage(con: Context, id: Int, view: ImageView) {
    Glide.with(con).load(id).into(view)
}


private fun cornerTop(con: Context, url: String, view: ImageView, size: Int) {
    Glide.with(con).load(url).apply(
        getCornersReq(ReqType.CORNER_TOP, size).override(
            view.measuredWidth,
            view.measuredHeight
        )
    ).into(view)

}

private fun cornerTop(con: Context, id: Int, view: ImageView, size: Int) {
    Glide.with(con).load(id).apply(
        getCornersReq(ReqType.CORNER_TOP, size).override(
            view.measuredWidth,
            view.measuredHeight
        )
    ).into(view)

}

//设置长宽
private fun centerFit(
    context: Context,
    url: String,
    view: ImageView,
    defaultId: Int = -1
) {
    Glide.with(context).load(url)
        .apply(
            getReq(
                ReqType.CENTER_FIT,
                defaultId
            ).override(view.measuredWidth, view.measuredHeight)
        )
        .into(view)
}

private fun banner(
    context: Context,
    url: String,
    view: ImageView,
    defaultId: Int = -1
) {
    Glide.with(context).load(url)
        .apply(
            getReq(
                ReqType.BANNER,
                defaultId
            ).override(view.measuredWidth, view.measuredHeight)
        )
        .into(view)
}


private fun corner(context: Context, url: String, view: ImageView, size: Int,defaultValue: Int = -1) {
    Glide.with(context).load(url)
        .apply(
            getCornersReq(ReqType.CORNER_ALL, size,defaultValue).override(
                view.measuredWidth,
                view.measuredHeight
            )
        )
        .into(view)
}

private fun circle(
    context: Context,
    url: String,
    view: ImageView,
    defaultId: Int = -1
) {
    Glide.with(context).load(url)
        .apply(
            getReq(
                ReqType.CIRCLE,
                defaultId
            ).override(view.measuredWidth, view.measuredHeight)
        )
        .into(view)

}

fun blur(
    context: Context,
    url: String,
    view: ImageView,
    defaultId: Int = -1
) {
    Glide.with(context).load(url)
        .apply(
            getReq(
                ReqType.BLUR,
                defaultId
            ).override(view.measuredWidth, view.measuredHeight)
        )
        .into(view)
}

fun getDrawText(
    context: Context,
    url: String,
    width: Int,
    height: Int,
    view: TextView,
    type: DrawableType,
    size: Int = 4,
    isCrop: Boolean = false,
    isCorners: Boolean = false
) {
    val kind: ReqType = when {
        isCrop && isCorners -> {
            ReqType.TEXT_DRAW_ALL
        }
        isCorners && !isCrop -> {
            ReqType.TEXT_DRAW_CORNER
        }
        isCrop && !isCorners -> {
            ReqType.TEXT_DRAW_CROP
        }
        else -> {
            ReqType.TEXT_DRAW
        }
    }
    Glide.with(context).load(url)
        .apply(getCornersReq(kind, size).override(width, height))
        .into(TextDrawableTarget(view, type, width))
}


private fun getReq(type: ReqType, defaultImg: Int): RequestOptions {
    val req = RequestOptions()
    when (type) {
        ReqType.CENTER_FIT -> {
            req.fitCenter().placeholder(defaultImg)
                .error(defaultImg)
        }
        ReqType.CIRCLE -> {
            req.fitCenter().placeholder(defaultImg)
                .error(defaultImg)
                .circleCrop()
        }
        ReqType.BLUR -> {
            req.transform(GlideBlurTransformation(AppManager.endOfStack()))
                .transform(CenterCrop())
                .error(defaultImg)
        }


        else -> {

        }
    }
    return req.skipMemoryCache(false)
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
}


private fun getCornersReq(
    type: ReqType,
    size: Int,
    defaultId: Int = -1
): RequestOptions {
    var req = RequestOptions()
    when (type) {
        ReqType.CORNER_TOP -> {
            val leftTop =
                CornersTransformation(
                    size,
                    0,
                    CornersTransformation.CornerType.TOP_LEFT
                )
            //顶部右边圆角
            val rightTop =
                CornersTransformation(
                    size,
                    0,
                    CornersTransformation.CornerType.TOP_RIGHT
                )
            req.transform(MultiTransformation(leftTop, rightTop)).error(defaultId)
                .placeholder(defaultId)
        }
        ReqType.CORNER_ALL -> {
            req = RequestOptions.bitmapTransform(
                CornersTransformation(
                    size,
                    0,
                    CornersTransformation.CornerType.ALL
                )
            ).placeholder(defaultId).error(defaultId)
        }
        ReqType.CORNER_ALL_BLUR -> {
            req = RequestOptions.bitmapTransform(
                CornersTransformation(
                    size,
                    0,
                    CornersTransformation.CornerType.ALL
                )
            ).placeholder(defaultId)
                .transform(GlideBlurTransformation(AppManager.endOfStack()))
                .transform(CenterCrop())
                .error(defaultId)
        }
        ReqType.TEXT_DRAW_CORNER -> {
            req = RequestOptions.bitmapTransform(
                CornersTransformation(
                    size,
                    0,
                    CornersTransformation.CornerType.ALL
                )
            ).placeholder(defaultId).error(defaultId)
        }
        ReqType.TEXT_DRAW_ALL -> {
            req = RequestOptions.bitmapTransform(
                CornersTransformation(
                    size,
                    0,
                    CornersTransformation.CornerType.ALL
                )
            ).circleCrop().placeholder(defaultId).error(defaultId)
        }
        ReqType.TEXT_DRAW_CROP -> {
            req.error(defaultId)
                .placeholder(defaultId)
                .circleCrop()
        }
        else -> {

        }
    }
    return req.skipMemoryCache(false)
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
}




enum class ReqType {
    CORNER_TOP,
    CORNER_ALL,
    BANNER,
    CENTER_FIT,
    TEXT_DRAW_CROP,
    TEXT_DRAW,
    TEXT_DRAW_CORNER,
    TEXT_DRAW_ALL,
    CORNER_ALL_BLUR,
    BLUR, CIRCLE
}
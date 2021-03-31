package com.android.base.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class CommonImage(
    var url: String? = null,

    var type: Int = 0,

    var loadUrl:String? = null
) : Parcelable
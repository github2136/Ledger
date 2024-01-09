package com.github2136.ledger.common


import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import  com.github2136.photopicker.R

/**
 * Created by yb on 2020/2/17
 */
@GlideModule
class GlideModule : AppGlideModule() {
}
//居中裁剪
val glideOptionsCenterCrop by lazy {
    RequestOptions
        .centerCropTransform()
        .placeholder(R.drawable.img_picker_place)
        .error(R.drawable.img_picker_fail)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
}
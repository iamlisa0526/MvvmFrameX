package com.lisa.mvvmframex.base.utils

import android.app.Activity
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.lisa.mvvmframex.base.R

/**
 * @Description: 图片加载Glide工具类
 * @Author: lisa
 * @CreateDate: 2020/5/29 10:37
 */
object GlideUtil {
    /**
     * 设置Fragment图片
     *
     * @param context
     * @param path
     * @param iv
     * @param placeholder
     */
    @JvmStatic
    fun setFragmentImage(
        context: Fragment,
        path: Any?,
        iv: ImageView,
        @DrawableRes placeholder: Int
    ) {
        configGlide(Glide.with(context), path, iv, placeholder)
    }

    /**
     * 设置Activity图片
     *
     * @param context
     * @param path
     * @param iv
     * @param placeholder
     */
    @JvmStatic
    fun setActivityImage(
        context: Activity,
        path: Any?,
        iv: ImageView,
        @DrawableRes placeholder: Int
    ) {
        configGlide(Glide.with(context), path, iv, placeholder)
    }

    /**
     * Glide配置
     */
    @JvmStatic
    private fun configGlide(
        requestManager: RequestManager,
        path: Any?,
        iv: ImageView,
        @DrawableRes placeholder: Int
    ) {
        requestManager.load(path)
            .centerCrop()
            .placeholder(if (placeholder == 0) R.drawable.default_image_placeholder else placeholder) //                        .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(iv)
    }
}
package com.lisa.mvvmframex.base.utils

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.lisa.mvvmframex.base.R
import kotlin.math.roundToInt

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
     * @param path Any类型可以适配Uri
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
        loadSpecifyHWImage(Glide.with(context), path, iv, placeholder)
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
        loadSpecifyHWImage(Glide.with(context), path, iv, placeholder)
    }

    /**
     * 加载指定宽高的图片
     */
    @JvmStatic
    private fun loadSpecifyHWImage(
        requestManager: RequestManager,
        path: Any?,
        iv: ImageView,
        @DrawableRes placeholder: Int
    ) {
        requestManager.load(path)
            .centerCrop()
            .placeholder(if (placeholder == 0) R.drawable.default_image_placeholder else placeholder)
            .into(iv)
    }

    /**
     * 设置Fragment自适应高度图片
     *
     * @param context
     * @param path Any类型可以适配Uri
     * @param iv
     * @param placeholder
     */
    @JvmStatic
    fun setFragmentAdjustImage(
        context: Fragment,
        path: Any?,
        iv: ImageView,
        @DrawableRes placeholder: Int
    ) {
        loadAdjustHeightImage(Glide.with(context), path, iv, placeholder)
    }


    /**
     * 设置Activity自适应高度图片
     *
     * @param context
     * @param path Any类型可以适配Uri
     * @param iv
     * @param placeholder
     */
    @JvmStatic
    fun setActivityAdjustImage(
        context: Activity,
        path: Any?,
        iv: ImageView,
        @DrawableRes placeholder: Int
    ) {
        loadAdjustHeightImage(Glide.with(context), path, iv, placeholder)
    }

    /**
     * 加载固定宽度,自适应高度的图片
     */
    @JvmStatic
    private fun loadAdjustHeightImage(
        requestManager: RequestManager,
        path: Any?,
        iv: ImageView,
        @DrawableRes placeholder: Int
    ) {
        requestManager
            .load(path)
            .skipMemoryCache(true)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    @Nullable e: GlideException?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Drawable>?,
                    dataSource: com.bumptech.glide.load.DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    //判断imageView的填充方式,如果不是fitXY的填充方式 设置其填充方式
                    if (iv.scaleType !== ImageView.ScaleType.FIT_XY) {
                        iv.scaleType = ImageView.ScaleType.FIT_XY
                    }
                    //进行宽度为match_parent时的适应imageView的高度计算
                    val params = iv.layoutParams
                    val vw = iv.width - iv.paddingLeft - iv.paddingRight
                    val scale = vw.toFloat() / resource.intrinsicWidth.toFloat()
                    val vh = (resource.intrinsicHeight * scale).roundToInt()
                    params.height = vh + iv.paddingTop + iv.paddingBottom
                    iv.layoutParams = params
                    return false
                }
            })
            .placeholder(if (placeholder == 0) R.drawable.default_image_placeholder else placeholder)
            .into(iv)
    }
}
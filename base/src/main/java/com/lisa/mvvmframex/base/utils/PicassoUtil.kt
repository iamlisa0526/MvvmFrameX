package com.lisa.mvvmframex.base.utils

import android.text.TextUtils
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.lisa.mvvmframex.base.R
import com.squareup.picasso.Picasso

/**
 * @Description: 图片加载Picasso工具类
 * @Author: lisa
 * @CreateDate: 2020/5/29 10:37
 */
object PicassoUtil {
    /**
     * 设置图片
     *
     * @param url
     * @param iv
     * @param placeholder
     */
    @JvmStatic
    fun setImage(url: String?, iv: ImageView, @DrawableRes placeholder: Int) {
        if (!TextUtils.isEmpty(url)) {

            Picasso.get()
                .load(if (isNetworkImage(url)) url else "file://$url")
                .resize(100, 100)//自动设置图片宽高像素的大小
                .centerCrop()
                .placeholder(if (placeholder == 0) R.drawable.default_image_placeholder else placeholder)
                .into(iv)
        }
    }

    /**
     * 是否是网络图片
     *
     * @param path
     * @return
     */
    @JvmStatic
    private fun isNetworkImage(path: String?): Boolean {
        if (TextUtils.isEmpty(path)) {
            return false
        }
        return path!!.startsWith("http")
                || path.startsWith("https")
    }
}
package com.lisa.mvvmframex.base.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;

import com.squareup.picasso.Picasso;

/**
 * @Description: 图片加载Picasso工具类
 * @Author: lisa
 * @CreateDate: 2020/5/29 10:37
 */
public class PicassoUtil {
    /**
     * 设置图片
     *
     * @param context
     * @param url
     * @param iv
     * @param palceholder
     */
    public static void setImage(Context context, String url, ImageView iv, @DrawableRes int palceholder) {
        if (!TextUtils.isEmpty(url)) {
            Picasso.with(context)
                    .load(url)
                    .placeholder(palceholder)
                    .into(iv);
        }
    }

}

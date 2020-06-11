package com.lisa.mvvmframex.base.recyclerview

import android.view.View

/**
 * @Description:    Item点击事件监听
 * @Author:         lisa
 * @CreateDate:     2020/5/28 13:48
 */
interface OnItemClickListener<T> {
    fun onItemClick(view: View?, position: Int, model: T)
}
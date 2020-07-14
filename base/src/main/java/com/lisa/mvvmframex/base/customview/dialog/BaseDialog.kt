package com.lisa.mvvmframex.base.customview.dialog

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.WindowManager

/**
 * @Description: Dialog基类
 * @Author: lisa
 * @CreateDate: 2020/6/15 09:54
 */
open class BaseDialog : Dialog {

    constructor(context: Context) : super(context) {
        initView(context)
    }

    private fun initView(context: Context) {
        val params = window?.attributes
        val screenWidth = context.resources.displayMetrics.widthPixels
        params?.width = screenWidth * 3 / 4
        params?.height = WindowManager.LayoutParams.WRAP_CONTENT
        params?.gravity = Gravity.CENTER
        window?.attributes = params
    }

}
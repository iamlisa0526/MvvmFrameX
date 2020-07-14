package com.lisa.mvvmframex.base.customview.dialog

import android.content.Context
import android.view.LayoutInflater
import com.lisa.mvvmframex.base.R

/**
 * @Description:    加载框
 * @Author:         lisa
 * @CreateDate:     2020/7/14 09:13
 */
class LoadingDialog : BaseDialog {

    constructor(context: Context) : super(context) {
        initView(context)
    }

    private fun initView(context: Context) {
        val mContentView = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null)
        setContentView(mContentView)
    }
}
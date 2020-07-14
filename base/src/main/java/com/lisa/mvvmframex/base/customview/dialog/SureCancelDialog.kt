package com.lisa.mvvmframex.base.customview.dialog

import android.content.Context
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.StringRes
import com.lisa.mvvmframex.base.R
import kotlinx.android.synthetic.main.dialog_sure_cancel.*


/**
 * @Description:    确定取消dialog
 * @Author:         lisa
 * @CreateDate:     2020/5/31 09:01
 */
class SureCancelDialog : BaseDialog {
    /**
     * 确认点击
     */
    private var sureListener: View.OnClickListener? = null

    /**
     * 取消点击
     */
    private var cancelListener: View.OnClickListener? = null

    /**
     * 标题id
     */
    private var titleStringRes = 0

    /**
     * 内容id
     */
    private var contentStringRes = 0

    /**
     * 确认文本id
     */
    private var sureTextStringRes = 0

    /**
     * 取消文本id
     */
    private var cancelTextStringRes = 0

    constructor(context: Context) : super(context) {
        initView(context)
    }

    private fun initView(context: Context) {

        val mContentView = LayoutInflater.from(context).inflate(R.layout.dialog_sure_cancel, null)
        setContentView(mContentView)

        //确认
        tv_sure.setOnClickListener {
            sureListener?.onClick(tv_sure)
            dismiss()
        }

        //取消
        tv_cancel.setOnClickListener {
            cancelListener?.onClick(tv_cancel)
            dismiss()
        }

    }

    /**
     * 设置确认点击事件
     */
    fun onSureListener(sureListener: View.OnClickListener): SureCancelDialog {
        this.sureListener = sureListener
        return this
    }

    /**
     * 设置取消点击事件
     */
    fun onCancelListener(cancelListener: View.OnClickListener): SureCancelDialog {
        this.cancelListener = cancelListener
        return this
    }

    /**
     * 设置标题
     */
    fun title(@StringRes titleStringRes: Int): SureCancelDialog {
        this.titleStringRes = titleStringRes
        if (this.titleStringRes != 0) {
            tv_title_dialog.text = context.getString(this.titleStringRes)
            tv_title_dialog.visibility = View.VISIBLE
        }
        return this
    }

    /**
     * 设置内容
     */
    fun content(@StringRes contentStringRes: Int): SureCancelDialog {
        this.contentStringRes = contentStringRes
        if (this.contentStringRes != 0) {
            tv_content.text = context.getString(this.contentStringRes)
            tv_content.visibility = View.VISIBLE
        }
        return this
    }

    /**
     * 确认文本
     */
    fun sureText(@StringRes sureTextStringRes: Int): SureCancelDialog {
        this.sureTextStringRes = sureTextStringRes
        tv_sure.text = context.getString(this.sureTextStringRes)
        return this
    }

    /**
     * 取消文本
     */
    fun cancelText(@StringRes cancelTextStringRes: Int): SureCancelDialog {
        this.cancelTextStringRes = cancelTextStringRes
        tv_cancel.text = context.getString(this.cancelTextStringRes)
        return this
    }

    /**
     * 设置内容中部分文字不同颜色及点击事件
     */
    fun contentStyle(style: SpannableStringBuilder): SureCancelDialog {
        tv_content.movementMethod = LinkMovementMethod.getInstance()
        tv_content.text = style
        tv_content.visibility = View.VISIBLE
        return this
    }

}
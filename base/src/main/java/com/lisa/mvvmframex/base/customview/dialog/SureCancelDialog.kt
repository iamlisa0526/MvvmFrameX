package com.lisa.mvvmframex.base.customview.dialog

import android.content.Context
import android.text.SpannableStringBuilder
import android.text.TextUtils
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
        if (titleStringRes != 0) {
            tv_title_dialog.text = context.getString(titleStringRes)
            tv_title_dialog.visibility = View.VISIBLE
        } else {
            tv_title_dialog.visibility = View.GONE
        }
        return this
    }

    /**
     * 设置标题
     */
    fun title(title: String): SureCancelDialog {
        if (!TextUtils.isEmpty(title)) {
            tv_title_dialog.text = title
            tv_title_dialog.visibility = View.VISIBLE
        } else {
            tv_title_dialog.visibility = View.GONE
        }
        return this
    }

    /**
     * 设置内容
     */
    fun content(@StringRes contentStringRes: Int): SureCancelDialog {
        if (contentStringRes != 0) {
            tv_content.text = context.getString(contentStringRes)
            tv_content.visibility = View.VISIBLE
        } else {
            tv_content.visibility = View.GONE
        }
        return this
    }

    /**
     * 设置内容
     */
    fun content(content: String): SureCancelDialog {
        if (!TextUtils.isEmpty(content)) {
            tv_content.text = content
            tv_content.visibility = View.VISIBLE
        } else {
            tv_content.visibility = View.GONE
        }
        return this
    }

    /**
     * 确认文本
     */
    fun sureText(@StringRes sureTextStringRes: Int): SureCancelDialog {
        tv_sure.text = context.getString(sureTextStringRes)
        return this
    }

    /**
     * 取消文本
     */
    fun cancelText(@StringRes cancelTextStringRes: Int): SureCancelDialog {
        tv_cancel.text = context.getString(cancelTextStringRes)
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
package com.lisa.mvvmframex.base.customview.dialog

import android.content.Context
import android.text.SpannableStringBuilder
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.StringRes
import com.lisa.mvvmframex.base.R
import kotlinx.android.synthetic.main.dialog_edit.*


/**
 * @Description:    可输入的确定取消dialog
 * @Author:         lisa
 * @CreateDate:     2020/5/31 09:01
 */
class EditDialog : BaseDialog {
    /**
     * 列表传输的item数据
     */
    private var mItemData: Any? = null

    /**
     * 用于列表中的dialog
     */
    fun setItemData(itemData: Any?) {
        this.mItemData = itemData
    }

    /**
     * 用于列表中的dialog
     */
    fun getItemData(): Any? {
        return this.mItemData
    }

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

        val mContentView = LayoutInflater.from(context).inflate(R.layout.dialog_edit, null)
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
    fun onSureListener(sureListener: View.OnClickListener): EditDialog {
        this.sureListener = sureListener
        return this
    }

    /**
     * 设置取消点击事件
     */
    fun onCancelListener(cancelListener: View.OnClickListener): EditDialog {
        this.cancelListener = cancelListener
        return this
    }

    /**
     * 设置标题
     */
    fun title(@StringRes titleStringRes: Int): EditDialog {
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
    fun title(title: String): EditDialog {
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
    fun content(@StringRes contentStringRes: Int): EditDialog {
        if (contentStringRes != 0) {
            et_content.setText(context.getString(contentStringRes))
        }
        return this
    }

    /**
     * 设置内容
     */
    fun content(content: String): EditDialog {
        if (!TextUtils.isEmpty(content)) {
            et_content.setText(content)
        }
        return this
    }

    /**
     * 设置输入框提示
     */
    fun hint(@StringRes hintStringRes: Int): EditDialog {
        if (hintStringRes != 0) {
            et_content.hint = context.getString(hintStringRes)
        }
        return this
    }

    /**
     * 设置输入框提示
     */
    fun hint(hint: String): EditDialog {
        if (!TextUtils.isEmpty(hint)) {
            et_content.hint = hint
        }
        return this
    }

    /**
     * 确认文本
     */
    fun sureText(@StringRes sureTextStringRes: Int): EditDialog {
        tv_sure.text = context.getString(sureTextStringRes)
        return this
    }

    /**
     * 取消文本
     */
    fun cancelText(@StringRes cancelTextStringRes: Int): EditDialog {
        tv_cancel.text = context.getString(cancelTextStringRes)
        return this
    }

    /**
     * 设置内容中部分文字不同颜色及点击事件
     */
    fun contentStyle(style: SpannableStringBuilder): EditDialog {
        et_content.movementMethod = LinkMovementMethod.getInstance()
        et_content.text = style
        et_content.visibility = View.VISIBLE
        return this
    }

}
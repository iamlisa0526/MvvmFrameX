package com.lisa.mvvmframex.base.customview

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.lisa.mvvmframex.base.R
import kotlinx.android.synthetic.main.layout_header.view.*

/**
 * Created by admin on 2018/9/12.
 */

class Header @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = -1
) : FrameLayout(context, attrs, defStyleAttr) {
    private var title: String? = ""
    private var titleRight: String? = ""
    private var titleResId = -1
    private var titleRightResId = -1
    private var titleRightIconResId = -1
    private var iconRightResId = -1
    private var backVisible = true
    var titleListener: OnClickListener? = null
    var rightTitleListener: OnClickListener? = null
    var rightIconListener: OnClickListener? = null


    init {
        initView(context, attrs)
    }

    private fun initView(context: Context, attrs: AttributeSet?) {
        handleTypedArray(context, attrs)
        LayoutInflater.from(context).inflate(R.layout.layout_header, this, true)

        iv_back.visibility = if (backVisible) View.VISIBLE else View.GONE
        iv_back.setOnClickListener { (context as Activity).onBackPressed() }

        tv_title.text = title
        if (titleResId != -1) tv_title.text = context.resources.getString(titleResId)
        if (titleRightIconResId != -1) tv_title.setCompoundDrawablesWithIntrinsicBounds(
            0,
            0,
            titleRightIconResId,
            0
        )

        tv_right.text = titleRight
        if (titleRightResId != -1) tv_right.text = context.resources.getString(titleRightResId)

        if (iconRightResId != -1) iv_right.setImageDrawable(
            context.resources.getDrawable(
                iconRightResId
            )
        )

        tv_title.setOnClickListener { titleListener?.onClick(it) }
        tv_right.setOnClickListener { rightTitleListener?.onClick(it) }
        iv_right.setOnClickListener { rightIconListener?.onClick(it) }
    }

    private fun handleTypedArray(context: Context, attrs: AttributeSet?) {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.Header)
            title = typedArray.getString(R.styleable.Header_bl_title)
            titleRight = typedArray.getString(R.styleable.Header_bl_rightTitle)
            titleResId = typedArray.getResourceId(R.styleable.Header_bl_title, -1)
            titleRightResId = typedArray.getResourceId(R.styleable.Header_bl_rightTitle, -1)
            titleRightIconResId = typedArray.getResourceId(R.styleable.Header_bl_titleRightIcon, -1)
            iconRightResId = typedArray.getResourceId(R.styleable.Header_bl_rightIcon, -1)
            backVisible = typedArray.getBoolean(R.styleable.Header_back_visible, true)
            typedArray.recycle()
        }
    }

    /**
     * 设置标题
     */
    fun setTitle(title: String) {
        tv_title.text = title
    }

    /**
     * 设置标题IdRes
     */
    fun setTitle(titleIdRes: Int) {
        tv_title.text = if (titleIdRes == -1) "" else context.resources.getString(titleIdRes)
    }

    /**
     * 设置右标题
     */
    fun setRightTitle(title: String) {
        tv_right.text = title
    }

    /**
     * 获取右标题文字
     */
    fun getRightTitle(): String? {
        return tv_right.text.toString()
    }


    /**
     * 设置右标题IdRes
     */

    fun setRightTitle(titleIdRes: Int) {
        tv_right.text = if (titleIdRes == -1) "" else context.resources.getString(titleIdRes)
    }

    /**
     * 设置右图标IdRes
     */
    fun setRightIcon(iconIdRes: Int) {
        iv_right.setImageDrawable(
            if (iconIdRes == -1) null else context.resources.getDrawable(
                iconIdRes
            )
        )
    }

    /**
     * 设置标题右图标IdRes
     */
    fun setTitleRightIcon(iconIdRes: Int) {
        if (iconIdRes != -1) tv_title.setCompoundDrawablesWithIntrinsicBounds(0, 0, iconIdRes, 0)
    }

    /**
     * 设置返回按钮是否可见
     */
    fun setIvBackVisible(visible: Boolean) {
        iv_back.visibility = if (visible) View.VISIBLE else View.GONE

    }
}


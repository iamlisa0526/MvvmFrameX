package com.lisa.mvvmframex

import android.text.Html
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import com.lisa.mvvmframex.base.customview.dialog.SureCancelDialog
import com.lisa.mvvmframex.base.view.BaseActivity
import com.lisa.mvvmframex.base.view.BaseWebActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity


class MainActivity : BaseActivity() {

    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun init() {
        //官网
        btn_web.setOnClickListener {
            startActivity<BaseWebActivity>(
                "url" to "http://www.maomiwh.com",
                "textZoom" to 100
            )
        }

        scroll_text.movementMethod = LinkMovementMethod.getInstance()

        SureCancelDialog(mContext)
            .contentHeight(800)
            .title("提示")
            .contentStyle(initDialogContentStyle())
            .onSureListener(View.OnClickListener {
                //重置是否同意缓存
                startActivity<MainActivity>()
                finish()
            })
            .onCancelListener(View.OnClickListener {
                //退出应用
                finish()
            }).show()
    }

    /**
     * 设置dialog content部分文字的颜色和点击事件
     */
    private fun initDialogContentStyle(): SpannableStringBuilder {
        val style = SpannableStringBuilder()
        //设置文字
        style.append(getString(R.string.protocol_privacy_content))
        //设置《服务协议》点击事件
        val protocolClickSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
//                startActivity<BaseWebActivity>("url" to Html.USER_PROTOCOL)
            }
        }
        style.setSpan(protocolClickSpan, 13, 19, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        //设置《隐私政策》点击事件
        val privacyClickSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
//                startActivity<BaseWebActivity>("url" to Html.PRIVACY)
            }
        }
        style.setSpan(privacyClickSpan, 20, 26, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        //设置部分文字颜色
        val protocolColorSpan = ForegroundColorSpan(resources.getColor(R.color.primary))
        style.setSpan(protocolColorSpan, 13, 19, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        val privacyColorSpan = ForegroundColorSpan(resources.getColor(R.color.primary))
        style.setSpan(privacyColorSpan, 20, 26, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        return style
    }

}

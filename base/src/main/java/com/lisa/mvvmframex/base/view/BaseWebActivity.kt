package com.lisa.mvvmframex.base.view

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Message
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.webkit.WebView.WebViewTransport
import com.lisa.mvvmframex.base.R
import kotlinx.android.synthetic.main.activity_base_web.*


class BaseWebActivity : BaseActivity() {
    private var mUrl = ""
    private var data = ""


    override fun getLayout(): Int {
        return R.layout.activity_base_web
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun init() {
        mUrl = intent.getStringExtra("url") ?: ""

        val mWebSettings = webView.settings
        mWebSettings.setSupportZoom(true)
        mWebSettings.loadWithOverviewMode = true
        mWebSettings.useWideViewPort = true
        mWebSettings.defaultTextEncodingName = "utf-8"
        mWebSettings.loadsImagesAutomatically = true
        //修复bug：Mixed Content: The page at 'https://xxx' was loaded over HTTPS, but requested an insecure image 'http://xxx.png'. This request has been blocked; the content must be served over HTTPS."
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebSettings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
        //修复bug：3D全景介绍无声音
        mWebSettings.mediaPlaybackRequiresUserGesture = false

        //调用JS方法.安卓版本大于17,加上注解 @JavascriptInterface
        mWebSettings.javaScriptEnabled = true

        saveData(mWebSettings)

        newWin(mWebSettings)

        webView.webChromeClient = webChromeClient
        webView.webViewClient = webViewClient

        if (mUrl.isNotBlank()) {
            webView.loadUrl(mUrl)
        } else {
            data = intent.getStringExtra("data")!!
            webView.settings.defaultTextEncodingName = "utf-8"
            webView.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
            webView.loadDataWithBaseURL(null, data, "text/html", "utf-8", null)
        }

    }

    /**
     * HTML5数据存储
     */
    private fun saveData(mWebSettings: WebSettings) {
        //有时候网页需要自己保存一些关键数据,Android WebView 需要自己设置
        mWebSettings.domStorageEnabled = true
        mWebSettings.databaseEnabled = true
        mWebSettings.setAppCacheEnabled(true)
        mWebSettings.setAppCachePath(applicationContext.cacheDir.absolutePath)
    }

    /**
     * 多窗口的问题
     * html中的_bank标签就是新建窗口打开，有时会打不开，需要加以下配置
     * 然后复写WebChromeClient的onCreateWindow方法
     */
    private fun newWin(mWebSettings: WebSettings) {
        mWebSettings.setSupportMultipleWindows(false)
        mWebSettings.javaScriptCanOpenWindowsAutomatically = true
    }

    private var webChromeClient: WebChromeClient = object : WebChromeClient() {
        //=========HTML5定位start==========================================================
        //需要先加入权限
        //<uses-permission android:name="android.permission.INTERNET"/>
        //<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
        //<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
        override fun onReceivedIcon(view: WebView, icon: Bitmap) {
            super.onReceivedIcon(view, icon)
        }

        override fun onGeolocationPermissionsHidePrompt() {
            super.onGeolocationPermissionsHidePrompt()
        }

        override fun onGeolocationPermissionsShowPrompt(
            origin: String,
            callback: GeolocationPermissions.Callback
        ) {
            callback.invoke(origin, true, false) //注意个函数，第二个参数就是是否同意定位权限，第三个是是否希望内核记住
            super.onGeolocationPermissionsShowPrompt(origin, callback)
        }

        //=========HTML5定位end==========================================================

        //页面标题
        override fun onReceivedTitle(view: WebView?, title: String?) {
            super.onReceivedTitle(view, title)
            title?.let { header.setTitle(it) }
        }

        //加载进度条
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            pb_web.progress = newProgress
            if (newProgress == 100) pb_web.visibility = View.GONE
        }

        //=========多窗口的问题start==========================================================
        override fun onCreateWindow(
            view: WebView,
            isDialog: Boolean,
            isUserGesture: Boolean,
            resultMsg: Message
        ): Boolean {
            val transport = resultMsg.obj as WebViewTransport
            transport.webView = view
            resultMsg.sendToTarget()
            return true
        }
        //=========多窗口的问题end==========================================================
    }

    /**
     * 多页面在同一个WebView中打开，就是不新建activity或者调用系统浏览器打开
     */
    private var webViewClient: WebViewClient = object : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            Log.e("url链接--->", url)
            if (url.startsWith("tel:") || url.startsWith("sms:")) {//打电话、发短信
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
                return true
            }
            view.loadUrl(url)
            return true
        }
    }


    public override fun onResume() {
        super.onResume()
        webView.onResume()
        webView.resumeTimers()
    }

    public override fun onPause() {
        super.onPause()
        webView.onPause()
        webView.pauseTimers() //小心这个！！！暂停整个 WebView 所有布局、解析、JS。
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onDestroy() {
        super.onDestroy()
        destroyWebView()
    }

    private fun destroyWebView() {
        webView.clearHistory()
        (webView.parent as ViewGroup).removeView(webView)
        webView.loadUrl("about:blank")
        webView.stopLoading()
        webView.webChromeClient = null
        webView.webViewClient = null
        webView.destroy()
    }

}
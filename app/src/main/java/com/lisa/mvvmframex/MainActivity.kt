package com.lisa.mvvmframex

import android.widget.Toast
import com.lisa.mvvmframex.base.utils.GlideUtil
import com.lisa.mvvmframex.base.view.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast


class MainActivity : BaseActivity() {
    private val ids = intArrayOf(R.drawable.banner1, R.drawable.banner2, R.drawable.banner3)

    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun init() {
        banner.addData(ids)
        banner.setBannerListener { index -> toast("$index") }

        //加载网络图片
        GlideUtil.setContextImage(this,"https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2107642534,1339643071&fm=26&gp=0.jpg",iv,0)


    }

}

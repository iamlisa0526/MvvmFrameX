package com.lisa.mvvmframex

import android.widget.Toast
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
    }

}

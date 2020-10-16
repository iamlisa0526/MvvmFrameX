package com.lisa.mvvmframex

import com.lisa.mvvmframex.base.view.BaseActivity
import com.lisa.mvvmframex.base.view.BaseWebActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity


class MainActivity : BaseActivity() {
    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun init() {
        btn.setOnClickListener { startActivity<BaseWebActivity>("url" to "http://www.maomiwh.com") }
    }

}

package com.lisa.mvvmframex.base.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.lisa.mvvmframex.base.R
import com.lisa.mvvmframex.base.adapter.BaseTabAdapter
import com.lisa.mvvmframex.base.utils.TabLayoutUtils
import kotlinx.android.synthetic.main.activity_base_tab.*
import java.util.*

/**
 * @Description: BaseTabAdapter
 * @Author: lisa
 * @CreateDate: 2020/6/8 09:10
 */
abstract class BaseTabActivity : BaseActivity() {
    private var fragments = ArrayList<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_tab)
        initTab()
    }

    private fun initTab() {
        vp_fragment.adapter =
            BaseTabAdapter(supportFragmentManager, getTitles(), getFragments(fragments))
        tab_layout.tabMode = TabLayout.MODE_FIXED
        tab_layout.setupWithViewPager(vp_fragment)
        TabLayoutUtils.setIndicator(tab_layout, 25, 25) //设置tab中item的margin
    }

    abstract fun getTitles(): Array<String>

    abstract fun getFragments(fragments: ArrayList<Fragment>): ArrayList<Fragment>
}
package com.lisa.mvvmframex.base.view

import android.view.View
import androidx.fragment.app.Fragment
import com.lisa.mvvmframex.base.R
import com.lisa.mvvmframex.base.adapter.BaseTabAdapter
import kotlinx.android.synthetic.main.activity_base_tab.*
import java.util.*

/**
 * @Description: BaseTabActivity
 * @Author: lisa
 * @CreateDate: 2020/6/8 09:10
 */
abstract class BaseTabActivity : BaseActivity() {

    private var fragments = ArrayList<Fragment>()

    override fun getLayout(): Int {
        return R.layout.activity_base_tab
    }

    override fun init() {
        header.visibility = View.VISIBLE

        vp_fragment.adapter =
            BaseTabAdapter(supportFragmentManager, getTitles(), getFragments(fragments))
        tab_layout.setViewPager(vp_fragment)
    }

    abstract fun getTitles(): Array<String>

    abstract fun getFragments(fragments: ArrayList<Fragment>): ArrayList<Fragment>
}
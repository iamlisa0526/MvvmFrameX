package com.lisa.mvvmframex.base.view

import androidx.fragment.app.Fragment
import com.lisa.mvvmframex.base.R
import com.lisa.mvvmframex.base.adapter.BaseTabAdapter
import kotlinx.android.synthetic.main.layout_base_tab.*
import java.util.*

/**
 * @Description: BaseTabAdapter
 * @Author: lisa
 * @CreateDate: 2020/6/8 09:10
 */
abstract class BaseTabFragment : BaseFragment() {

    private var fragments = ArrayList<Fragment>()

    override fun getLayout(): Int {
        return R.layout.layout_base_tab
    }

    override fun init() {
        fragments.clear()
        vp_fragment.adapter =
            BaseTabAdapter(childFragmentManager, getTitles(), getFragments(fragments))
        tab_layout.setViewPager(vp_fragment)
    }

    abstract fun getTitles(): Array<String>

    abstract fun getFragments(fragments: ArrayList<Fragment>): ArrayList<Fragment>
}
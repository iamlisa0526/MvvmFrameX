package com.lisa.mvvmframex

import androidx.fragment.app.Fragment
import com.lisa.mvvmframex.base.view.BaseTabActivity
import java.util.*


class MainActivity : BaseTabActivity() {

    override fun getTitles(): Array<String> {
        return arrayOf("tab1", "tab2")
    }

    override fun getFragments(fragments: ArrayList<Fragment>): ArrayList<Fragment> {
        fragments.add(MyFragment())
        fragments.add(MyFragment())
        return fragments
    }

}

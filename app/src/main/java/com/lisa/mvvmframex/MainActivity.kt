package com.lisa.mvvmframex

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.lisa.mvvmframex.base.recyclerview.BaseAdapter
import com.lisa.mvvmframex.base.view.BaseActivity
import com.lisa.mvvmframex.base.view.BaseTabActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_text.view.*
import java.util.ArrayList

class MainActivity : BaseTabActivity() {
    override fun getTitles(): Array<String> {
        return arrayOf(
            "热门", "iOS", "Android"
            , "前端", "后端", "设计", "工具资源"
            , "前端", "后端", "设计", "工具资源"
        )
    }

    override fun getFragments(fragments: ArrayList<Fragment>): ArrayList<Fragment> {
        fragments.add(MyFragment())
        fragments.add(MyFragment())
        fragments.add(MyFragment())
        fragments.add(MyFragment())
        fragments.add(MyFragment())
        fragments.add(MyFragment())
        fragments.add(MyFragment())
        fragments.add(MyFragment())
        fragments.add(MyFragment())
        fragments.add(MyFragment())
        fragments.add(MyFragment())

        return fragments
    }


}

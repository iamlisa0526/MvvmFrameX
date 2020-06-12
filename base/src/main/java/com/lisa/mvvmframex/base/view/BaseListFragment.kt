package com.lisa.mvvmframex.base.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.lisa.mvvmframex.base.R
import com.lisa.mvvmframex.base.loadsir.EmptyCallback
import kotlinx.android.synthetic.main.activity_base_list.*

/**
 * @Description:    列表Fragment基类
 * @Author:         lisa
 * @CreateDate:     2020/6/12 10:11
 */
abstract class BaseListFragment<T> : BaseFragment() {
    protected val mList = arrayListOf<T>()
    private lateinit var mAdapter: RecyclerView.Adapter<*>
    private lateinit var mLoadService: LoadService<Any>

//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//
//        val rootView = inflater.inflate(getLayout(), container, false)
//
//        //注册loadsir，显示空布局,错误数据等
//        mLoadService = LoadSir.getDefault().register(rootView)
//
//        //第三步：返回LoadSir生成的LoadLayout
//        return mLoadService.loadLayout
//    }

    override fun init() {
        mAdapter = getAdapter()
        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.adapter = mAdapter

        if (!isRefresh())
            refresh_layout.setEnableRefresh(false)

        request()

    }

    override fun getLayout(): Int {
        return R.layout.activity_base_list
    }

    /**
     * 是否刷新，默认不刷新
     */
    private fun isRefresh(): Boolean {
        return false
    }

    protected abstract fun request()

    /**
     * 获取适配器
     *
     * @return
     */
    protected abstract fun getAdapter(): RecyclerView.Adapter<*>

    /**
     * 更新数据
     */
    protected fun updateData(list: ArrayList<T>) {
        mList.clear()
        mList.addAll(list)
        mAdapter.notifyDataSetChanged()

        if (mList.isEmpty())
            mLoadService.showCallback(EmptyCallback::class.java)
    }
}
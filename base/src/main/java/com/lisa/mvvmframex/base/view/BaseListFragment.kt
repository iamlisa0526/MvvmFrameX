package com.lisa.mvvmframex.base.view

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lisa.mvvmframex.base.R
import kotlinx.android.synthetic.main.layout_base_list.*

/**
 * @Description:    列表Fragment基类
 * @Author:         lisa
 * @CreateDate:     2020/10/10 13:32
 */
abstract class BaseListFragment<T> : BaseFragment() {

    var mList = arrayListOf<T>()

    lateinit var mAdapter: RecyclerView.Adapter<*>

    override fun getLayout(): Int {
        return R.layout.layout_base_list
    }

    override fun init() {

        //初始化RecyclerView
        initRecyclerView()

        //配置空数据，错误布局显示
        configLoadingLayout()

        //配置网络请求
        configRequest()
    }

    /**
     * 初始化RecyclerView
     */
    private fun initRecyclerView() {
        mAdapter = getAdapter()
        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.adapter = mAdapter
    }

    /**
     * 配置空数据，错误布局显示
     */
    private fun configLoadingLayout() {
        loading_layout?.showContent()
        loading_layout?.setRetryListener {//加载失败，点击重试
            request()
        }
    }

    /**
     * 配置网络请求
     */
    abstract fun configRequest()

    /**
     * 请求网络数据
     */
    abstract fun request()

    /**
     * 获取适配器
     * @return
     */
    abstract fun getAdapter(): RecyclerView.Adapter<*>

    /**
     * 是否有头部
     */
    open fun hasHeader(): Boolean {
        return false
    }

    /**
     * 显示空数据UI
     */
    fun showEmptyUI() {
        //显示空数据布局
        if (hasHeader()) {
            loading_layout?.showContent()
        } else {
            if (mList.isEmpty()) {
                loading_layout?.showEmpty()
            } else {
                loading_layout?.showContent()
            }
        }
    }
}

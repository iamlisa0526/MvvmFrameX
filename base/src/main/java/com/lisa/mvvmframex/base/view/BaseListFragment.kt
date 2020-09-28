package com.lisa.mvvmframex.base.view

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lisa.mvvmframex.base.R
import com.lisa.mvvmframex.base.dto.BasePageDto
import com.lisa.mvvmframex.base.qo.BasePostQo
import com.lisa.mvvmframex.base.utils.GsonUtil
import com.zhouyou.http.callback.SimpleCallBack
import com.zhouyou.http.exception.ApiException
import com.zhouyou.http.request.GetRequest
import com.zhouyou.http.request.PostRequest
import kotlinx.android.synthetic.main.activity_base_list.*

/**
 * @Description:    列表Fragment基类
 * @Author:         lisa
 * @CreateDate:     2020/6/12 10:11
 */
abstract class BaseListFragment<T> : BaseFragment() {
    protected var pageNo = 1
    protected val pageSize = 15
    private var basePageDto = BasePageDto<T>()
    private var unPageDto = arrayListOf<T>()

    protected var mList = arrayListOf<T>()
    private lateinit var mAdapter: RecyclerView.Adapter<*>

    /**
     * Post请求Qo
     */
    private val mBasePostQo = BasePostQo()

    override fun getLayout(): Int {
        return R.layout.activity_base_list
    }

    /**
     * 是否刷新，默认刷新
     */
    open fun isRefresh(): Boolean {
        return true
    }

    override fun init() {

        initRecyclerView()

        configLoadingLayout()

        if (isRefresh()) {//分页
            configRefresh()
        } else {//不分页
            refresh_layout?.setEnableRefresh(false)
            refresh_layout?.setEnableLoadMore(false)
            request()
        }

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
            pageNo = 1
            mBasePostQo.page = 1
            request()
        }
    }

    /**
     * 设置刷新加载相关配置
     */
    private fun configRefresh() {
        refresh_layout?.setEnableRefresh(true)//默认是true
        refresh_layout?.autoRefresh()

        //刷新监听
        refresh_layout?.setOnRefreshListener {
            pageNo = 1
            mBasePostQo.page = 1
            request()
        }

        //加载更多监听
        refresh_layout?.setOnLoadMoreListener {
            pageNo++
            mBasePostQo.page++
            request()
        }
    }

    /**
     * 子类实现
     * 如：MyEasyHttp.get("/register")
     */
    protected abstract fun getGetRequest(): GetRequest?

    /**
     * 子类需要覆写即可
     * 如：MyEasyHttp.post("/register")
     */
    open fun getPostRequest(): PostRequest? {
        return null
    }

    /**
     * 请求网络数据
     */
    protected fun request() {

        if (getGetRequest() != null || getPostRequest() != null) loading_layout?.showLoading()

        requestGet()

        requestPost()
    }

    /**
     * get请求
     */
    private fun requestGet() {
        getGetRequest()?.execute(object : SimpleCallBack<Any>() {
            override fun onSuccess(any: Any) {
                val result = GsonUtil.toJson(any)
                Log.i("MyEasyHttp", result)
                if (isRefresh()) {//分页
                    basePageDto = getBasePageDto(result)
                    updatePageData()
                } else {//不分页
                    unPageDto = getUnPageDto(result)
                    mList.clear()
                    mList.addAll(unPageDto)
                    mAdapter.notifyDataSetChanged()
                }

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

            override fun onError(e: ApiException) {
                if (isRefresh()) {
                    if (pageNo > 1 || mBasePostQo.page > 1) {//加载
                        if (e.code != 1010) {//data==null时不算真正的失败，所以不需要pageNo--
                            pageNo--
                            mBasePostQo.page--
                        }
                        refresh_layout?.finishLoadMore(false)//加载失败
                    } else {//刷新
                        refresh_layout?.finishRefresh(false)//刷新失败
                        loading_layout?.setErrorText(e.message)
                        loading_layout?.showError()
                    }
                } else {
                    loading_layout?.setErrorText(e.message)
                    loading_layout?.showError()
                }

                if (401 == e.code) {
                    go2Login()
                }

                if (e.code == 1010) {//data==null时返回NullPointerException
                    onSuccess(Any())
                }
            }
        })

    }

    /**
     * post请求
     */
    private fun requestPost() {
        getPostRequest()?.execute(object : SimpleCallBack<Any>() {
            override fun onSuccess(any: Any) {
                val result = GsonUtil.toJson(any)
                Log.i("MyEasyHttp", result)
                if (isRefresh()) {//分页
                    basePageDto = getBasePageDto(result)
                    updatePageData()
                } else {//不分页
                    unPageDto = getUnPageDto(result)
                    mList.clear()
                    mList.addAll(unPageDto)
                    mAdapter.notifyDataSetChanged()
                }

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

            override fun onError(e: ApiException) {
                if (isRefresh()) {
                    if (pageNo > 1 || mBasePostQo.page > 1) {//加载
                        pageNo--
                        mBasePostQo.page--
                        refresh_layout?.finishLoadMore(false)//加载失败
                    } else {//刷新
                        refresh_layout?.finishRefresh(false)//刷新失败
                        loading_layout?.setErrorText(e.message)
                        loading_layout?.showError()
                    }
                } else {
                    loading_layout?.setErrorText(e.message)
                    loading_layout?.showError()
                }

                if (401 == e.code) {
                    go2Login()
                }
            }
        })

    }

    /**
     * 跳转登录页
     */
    protected abstract fun go2Login()

    /**
     * 更新分页数据
     */
    private fun updatePageData() {

        if (pageNo == 1 || mBasePostQo.page == 1) {//刷新时清空列表
            mList.clear()
        }

        mList.addAll(basePageDto.content)
        mAdapter.notifyDataSetChanged()

        if (pageNo == 1 || mBasePostQo.page == 1) {//刷新
            if (mList.size < pageSize) {
                //完成刷新并标记没有更多数据
                refresh_layout?.finishRefreshWithNoMoreData()
            } else {
                //完成刷新并标记还有更多数据
                refresh_layout?.finishRefresh()
            }
        } else {//加载
            if (mList.size < basePageDto.totalElements) {
                //完成加载并标记还有更多数据
                refresh_layout?.finishLoadMore()
            } else {
                //完成加载并标记没有更多数据
                refresh_layout?.finishLoadMoreWithNoMoreData()
            }
        }

    }

    /**
     * 分页加载时复写该方法，复写时删除super.getBasePageDto(result)
     */
    open fun getBasePageDto(result: String): BasePageDto<T> {
        return basePageDto
    }

    /**
     * 不分页的数据复写该方法，复写时删除super.getUnPageDto(result)
     */
    open fun getUnPageDto(result: String): ArrayList<T> {
        return unPageDto
    }

    /**
     * 获取适配器
     * @return
     */
    protected abstract fun getAdapter(): RecyclerView.Adapter<*>

    /**
     * 是否有头部
     */
    open fun hasHeader(): Boolean {
        return false
    }
}

package com.lisa.mvvmframex.base.view

import com.lisa.mvvmframex.base.qo.BasePostQo
import com.lisa.mvvmframex.base.rxhttp.ErrorInfo
import com.lisa.mvvmframex.base.rxhttp.PageList
import com.rxjava.rxlife.RxLife
import io.reactivex.rxjava3.core.Observable
import kotlinx.android.synthetic.main.activity_base_list.*
import rxhttp.RxHttpPlugins

/**
 * @Description:    分页加载列表Fragment基类
 * @Author:         lisa
 * @CreateDate:     2020/6/12 10:11
 */
abstract class BasePageListActivity<T> : BaseListActivity<T>() {
    /**
     * 页码
     */
    protected var pageNo = 1

    /**
     * 每页条数
     */
    protected val pageSize = 15

    /**
     * Post请求Qo
     */
    private val mBasePostQo = BasePostQo()

    abstract fun getObservablePageList(): Observable<PageList<T>>

    override fun configRequest() {
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

    override fun request() {
        getObservablePageList()
            .to(RxLife.toMain(this)) //感知生命周期，并在主线程回调
            .subscribe(
                { pageList: PageList<T> ->
                    updatePageData(pageList)
                },
                { error ->
                    if (pageNo > 1 || mBasePostQo.page > 1) {//加载
                        refresh_layout?.finishLoadMore(false)//加载失败
                    } else {//刷新
                        refresh_layout?.finishRefresh(false)//刷新失败
                    }
                    loading_layout?.setErrorText(ErrorInfo(error).errorMsg)
                    loading_layout?.showError()

                    if (401 == ErrorInfo(error).errorCode) {
                        //取消所有请求
                        RxHttpPlugins.cancelAll()
                        //跳转登录
                        go2Login()
                    }
                }
            )

    }

    /**
     * 更新分页数据
     */
    private fun updatePageData(pageList: PageList<T>) {

        if (pageNo == 1 || mBasePostQo.page == 1) {//刷新时清空列表
            mList.clear()
        }

        mList.addAll(pageList.content)
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
            if (mList.size < pageList.totalElements) {
                //完成加载并标记还有更多数据
                refresh_layout?.finishLoadMore()
            } else {
                //完成加载并标记没有更多数据
                refresh_layout?.finishLoadMoreWithNoMoreData()
            }
        }

        showEmptyUI()
    }

    /**
     * 跳转登录页
     */
    protected abstract fun go2Login()

}

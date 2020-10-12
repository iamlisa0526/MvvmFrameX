package com.lisa.mvvmframex.base.view

import com.lisa.mvvmframex.base.rxhttp.ErrorInfo
import com.rxjava.rxlife.RxLife
import io.reactivex.rxjava3.core.Observable
import kotlinx.android.synthetic.main.layout_base_list.*
import rxhttp.RxHttpPlugins

/**
 * @Description:    不分页列表Fragment基类
 * @Author:         lisa
 * @CreateDate:     2020/6/12 10:11
 */
abstract class BaseUnPageListFragment<T> : BaseListFragment<T>() {
    override fun configRequest() {
        refresh_layout?.setEnableRefresh(false)
        refresh_layout?.setEnableLoadMore(false)
        request()
    }

    override fun request() {
        getObservableList()?.let {
            it.to(RxLife.toMain(this)) //感知生命周期，并在主线程回调
                .subscribe(
                    { list: List<T> ->
                        mList.clear()
                        mList.addAll(list)
                        mAdapter.notifyDataSetChanged()

                        showEmptyUI()
                    },
                    { error ->
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


    }

    abstract fun getObservableList(): Observable<List<T>>?

    /**
     * 跳转登录页
     */
    protected abstract fun go2Login()
}

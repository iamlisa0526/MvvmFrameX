package com.lisa.mvvmframex.base.view

import com.lisa.mvvmframex.base.rxhttp.ErrorInfo
import com.rxjava.rxlife.RxLife
import io.reactivex.rxjava3.core.Observable
import kotlinx.android.synthetic.main.activity_base_list.*

/**
 * @Description:    不分页列表Fragment基类
 * @Author:         lisa
 * @CreateDate:     2020/6/12 10:11
 */
abstract class BaseUnPageListFragment<T> : BaseListFragment2<T>() {
    override fun configRequest() {
        refresh_layout?.setEnableRefresh(false)
        refresh_layout?.setEnableLoadMore(false)
        request()
    }

    override fun request() {
        getObservableList()
            .to(RxLife.toMain(this)) //感知生命周期，并在主线程回调
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
                }
            )

    }

    abstract fun getObservableList(): Observable<List<T>>
}

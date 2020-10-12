package com.lisa.mvvmframex.base

import android.app.Application
import android.content.Context
import com.lisa.mvvmframex.base.utils.DynamicTimeFormat
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.tamsiree.rxkit.RxTool

/**
 * @Description: Application基类
 * @Author: lisa
 * @CreateDate: 2020/4/12 09:37
 */
abstract class BaseApplication : Application() {
    /**
     * 继承该类的子类在onCreate调用该方法即可，传参应该是Application的debug开关
     *
     * @param isDebug
     */
    fun setDebug(isDebug: Boolean) {
        debug = isDebug
    }

    override fun onCreate() {
        super.onCreate()
        application = this

        //RxTool
        RxTool.init(this)
    }

    /**
     * companion object {init{}}相当于java的static{}
     * static 代码段可以防止内存泄露
     */
    companion object {
        //OOM won't happen
        var application: Application? = null
        var debug = false

        init {
            configRefreshLayout()
        }

        /**
         * 配置RefreshLayout
         */
        private fun configRefreshLayout() {
            //设置全局默认配置（优先级最低，会被其他设置覆盖）
            SmartRefreshLayout.setDefaultRefreshInitializer { context: Context?, layout: RefreshLayout ->
                layout.setEnableAutoLoadMore(true)
                layout.setEnableOverScrollDrag(false)
                layout.setEnableOverScrollBounce(true)
                layout.setEnableLoadMoreWhenContentNotFull(false)
                layout.setEnableScrollContentWhenRefreshed(true)
                layout.setPrimaryColorsId(R.color.page, R.color.black_45)
            }

            //设置全局的Header构建器
            SmartRefreshLayout.setDefaultRefreshHeaderCreator { context: Context?, layout: RefreshLayout? ->
                ClassicsHeader(context).setTimeFormat(DynamicTimeFormat("更新于 %s"))
            }

            //设置全局的Footer构建器
            SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
                ClassicsFooter(context).setDrawableSize(20f)//指定为经典Footer，默认是 BallPulseFooter
            }
        }
    }
}
package com.lisa.mvvmframex.base

import android.app.Application
import android.content.Context
import com.lisa.mvvmframex.base.network.MyTokenInterceptor
import com.lisa.mvvmframex.base.utils.DynamicTimeFormat
import com.lisa.mvvmframex.base.utils.PreferencesUtil
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.zhouyou.http.EasyHttp
import com.zhouyou.http.model.HttpHeaders
import retrofit2.converter.gson.GsonConverterFactory

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
        configRxEasyHttp()
    }

    /**
     * 获取全局URL
     *
     * @return
     */
    protected abstract fun getMyBaseUrl(): String

    /**
     * 配置RxEasyHttp
     *
     * @param baseUrl 全局URL,url只能是域名或者域名+端口号
     */
    private fun configRxEasyHttp() {
        EasyHttp.init(this) //默认初始化

        //全局设置请求头
        val headers = HttpHeaders()
        headers.put(
            "Accept-Encoding",
            "identity"
        ) //解决获取contentLength = urlConn.getContentLength()失败的问题
        EasyHttp.getInstance()
            .setBaseUrl(getMyBaseUrl()) //设置全局URL,url只能是域名或者域名+端口号
            .debug("EasyHttp", true) // 打开该调试开关,最后的true表示是否打印内部异常，一般打开方便调试错误
            .addCommonHeaders(headers)
            .addConverterFactory(GsonConverterFactory.create())//如果是body的方式提交object，必须要加GsonConverterFactory.create(),他的本质就是把object转成json给到服务器，所以必须要加Gson Converter
            .addInterceptor(getMyTokenInterceptor())//添加token拦截器

    }

    abstract fun getMyTokenInterceptor(): MyTokenInterceptor

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
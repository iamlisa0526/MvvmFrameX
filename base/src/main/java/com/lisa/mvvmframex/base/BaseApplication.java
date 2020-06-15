package com.lisa.mvvmframex.base;

import android.app.Application;

import com.kingja.loadsir.core.LoadSir;
import com.lisa.mvvmframex.base.loadsir.EmptyCallback;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.model.HttpHeaders;

/**
 * @Description: Application基类
 * @Author: lisa
 * @CreateDate: 2020/4/12 09:37
 */
public abstract class BaseApplication extends Application {
    //OOM won't happen
    public static Application application;

    public static boolean debug;

    /**
     * 继承该类的子类在onCreate调用该方法即可，传参应该是Application的debug开关
     *
     * @param isDebug
     */
    public void setDebug(boolean isDebug) {
        debug = isDebug;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        application = this;

        configLoadSir();

        configRxEasyHttp(getMyBaseUrl());
    }

    /**
     * 获取全局URL
     *
     * @return
     */
    protected abstract String getMyBaseUrl();

    /**
     * 配置RxEasyHttp
     *
     * @param baseUrl 全局URL,url只能是域名或者域名+端口号
     */
    private void configRxEasyHttp(String baseUrl) {
        EasyHttp.init(this);//默认初始化

        //全局设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.put("Accept-Encoding", "identity");//解决获取contentLength = urlConn.getContentLength()失败的问题

        EasyHttp.getInstance()
                .setBaseUrl(baseUrl)//设置全局URL,url只能是域名或者域名+端口号
                .debug("EasyHttp", true);// 打开该调试开关,最后的true表示是否打印内部异常，一般打开方便调试错误
    }

    /**
     * 配置LoadSir
     */
    private void configLoadSir() {
        LoadSir.beginBuilder()//添加各种状态页
                .addCallback(new EmptyCallback())
                .commit();
    }
}

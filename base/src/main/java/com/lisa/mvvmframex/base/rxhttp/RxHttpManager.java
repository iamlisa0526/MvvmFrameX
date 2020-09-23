package com.lisa.mvvmframex.base.rxhttp;


import android.app.Application;

import com.lisa.mvvmframex.base.BuildConfig;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import rxhttp.RxHttpPlugins;
import rxhttp.wrapper.cahce.CacheMode;
import rxhttp.wrapper.param.RxHttp;

/**
 * 本类所有配置都是非必须的，根据自己需求选择就好
 * User: ljx
 * Date: 2019-11-26
 * Time: 20:44
 */
public class RxHttpManager {

    public static void init(Application context) {
        OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)//默认10s
            .readTimeout(30, TimeUnit.SECONDS)//默认10s
            .writeTimeout(30, TimeUnit.SECONDS)//默认10s
            .build();

        //RxHttp初始化，自定义OkHttpClient对象，非必须
        RxHttp.init(client, BuildConfig.DEBUG);

        //设置缓存策略，非必须
        File cacheFile = new File(context.getExternalCacheDir(), "RxHttpCache");//缓存路径为：Android/data/{app包名目录}/cache/RxHttpCache
        //缓存最大Size，最大为10M，超过这个size，内部会根据LRU算法，将最少使用的缓存自动清除
        //全局缓存模式，先请求网络，失败后，再读取缓存
        RxHttpPlugins.setCache(cacheFile, 1000 * 100, CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE);

        //todo 设置公共参数，非必须,在自己的项目中实现
//        RxHttp.setOnParamAssembly(p -> {
//            /*根据不同请求添加不同参数，子线程执行，每次发送请求前都会被回调
//            如果希望部分请求不回调这里，发请求前调用Param.setAssemblyEnabled(false)即可
//             */
//            Method method = p.getMethod();
//            if (method.isGet()) { //Get请求
//
//            } else if (method.isPost()) { //Post请求
//
//            }
//            return p.add("versionName", "1.0.0")//添加公共参数
//                .add("time", System.currentTimeMillis())
//                .addHeader("deviceType", "android"); //添加公共请求头
//        });
    }
}

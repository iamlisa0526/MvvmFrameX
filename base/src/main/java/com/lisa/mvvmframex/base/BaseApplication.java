package com.lisa.mvvmframex.base;

import android.app.Application;

import com.kingja.loadsir.core.LoadSir;
import com.lisa.mvvmframex.base.loadsir.EmptyCallback;

/**
 * @Description: Application基类
 * @Author: lisa
 * @CreateDate: 2020/4/12 09:37
 */
public class BaseApplication extends Application {
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

        LoadSir.beginBuilder()//添加各种状态页
                .addCallback(new EmptyCallback())
                .commit();


    }
}

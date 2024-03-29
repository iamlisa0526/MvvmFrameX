package com.lisa.mvvmframex.base.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.jeremyliao.liveeventbus.LiveEventBus;
import com.lisa.mvvmframex.base.customview.dialog.LoadingDialog;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.tamsiree.rxkit.RxActivityTool;

/**
 * @Description: Activity基类
 * @Author: lisa
 * @CreateDate: 2020/5/27 09:26
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected Context mContext;

    protected LoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        mContext = this;

        //设置状态栏黑色字体与图标,背景默认透明
        QMUIStatusBarHelper.setStatusBarLightMode(this);

        //设置状态栏白色字体与图标
//        QMUIStatusBarHelper.setStatusBarDarkMode(this);

        if (getLayout() != 0) setContentView(getLayout());

        mLoadingDialog = new LoadingDialog(mContext);

        init();

        RxActivityTool.addActivity(this);
    }

    /**
     * 注册关闭activity事件
     */
    protected void registerCloseEvent() {
        LiveEventBus.get("CLOSE_ACTIVITY", Boolean.class)
                .observe(this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean b) {
                        if (b) finish();
                    }
                });
    }

    /**
     * 发送关闭activity事件
     */
    protected void sendCloseEvent() {
        LiveEventBus.get("CLOSE_ACTIVITY", Boolean.class).post(true);
    }


    protected abstract void init();

    protected abstract @LayoutRes
    int getLayout();

    @Override
    protected void onDestroy() {
        if (mLoadingDialog.isShowing()) mLoadingDialog.dismiss();
        super.onDestroy();
        RxActivityTool.removeActivity(this);
    }
}

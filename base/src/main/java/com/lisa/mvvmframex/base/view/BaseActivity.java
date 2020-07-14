package com.lisa.mvvmframex.base.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.jeremyliao.liveeventbus.LiveEventBus;
import com.lisa.mvvmframex.base.constants.KeyList;
import com.lisa.mvvmframex.base.customview.dialog.LoadingDialog;
import com.zhouyou.http.subsciber.IProgressDialog;

/**
 * @Description: Activity基类
 * @Author: lisa
 * @CreateDate: 2020/5/27 09:26
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected Context mContext;

    protected LoadingDialog mLoadingDialog;

    //用于EasyHttp中ProgressDialogCallBack的dialog
    protected IProgressDialog mIProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

        setContentView(getLayout());

        mLoadingDialog = new LoadingDialog(mContext);

        mIProgressDialog = new IProgressDialog() {
            @Override
            public Dialog getDialog() {
                return mLoadingDialog;
            }
        };

        //关闭activity
        LiveEventBus.get(KeyList.EKEY_CLOSE_ACTIVITY, Boolean.class)
                .observe(this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean b) {
                        if (b) finish();
                    }
                });

        init();
    }

    protected abstract void init();

    protected abstract @LayoutRes
    int getLayout();
}

package com.lisa.mvvmframex.base.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.jeremyliao.liveeventbus.LiveEventBus;

/**
 * @Description: MVC-Activity基类
 * @Author: lisa
 * @CreateDate: 2020/5/27 09:26
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;

        setContentView(getLayout());

        //关闭activity
        LiveEventBus.get("KEY_CLOSE_ACTIVITY", Boolean.class)
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

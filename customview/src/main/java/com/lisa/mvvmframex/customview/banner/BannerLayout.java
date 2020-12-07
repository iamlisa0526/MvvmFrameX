package com.lisa.mvvmframex.customview.banner;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lisa.mvvmframex.customview.R;

/**
 * @Description: Banner
 * @Author: lisa
 * @CreateDate: 2020/12/4 16:11
 */
public class BannerLayout extends FrameLayout implements BannerImageLayout.DotSwitchListener, BannerImageLayout.BannerClickListener {
    private BannerImageLayout bannerImageLayout;
    private LinearLayout linearLayout;
    private BannerListener bannerListener;

    public void setBannerListener(BannerListener listener) {
        this.bannerListener = listener;
    }

    public interface BannerListener {
        void clickBanner(int index);
    }

    public BannerLayout(@NonNull Context context) {
        this(context, null);
    }

    public BannerLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public BannerLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        initBannerImageLayout();
        initBannerDotLayout();
    }

    /**
     * 初始化Banner图片布局
     */
    private void initBannerImageLayout() {
        bannerImageLayout = new BannerImageLayout(getContext());
        LayoutParams lp = new LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        bannerImageLayout.setLayoutParams(lp);
        //设置切换底部圆点的监听
        bannerImageLayout.setDotSwitchListener(this);
        //设置点击Banner的监听
        bannerImageLayout.setBannerClickListener(this);
        addView(bannerImageLayout);
    }

    /**
     * 初始化Banner圆点布局
     */
    private void initBannerDotLayout() {
        linearLayout = new LinearLayout(getContext());
        LayoutParams lp = new LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 40);
        lp.gravity = Gravity.BOTTOM;
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setLayoutParams(lp);
        addView(linearLayout);
    }

    public void addData(int[] ids) {
        for (int id : ids) {
            addImage(id);
            addDot();
        }
    }

    private void addImage(int id) {
        ImageView iv = new ImageView(getContext());
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        iv.setImageResource(id);
        bannerImageLayout.addView(iv);
    }

    private void addDot() {
        ImageView iv = new ImageView(getContext());
        LayoutParams lp = new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        lp.setMargins(5, 5, 5, 5);
        iv.setLayoutParams(lp);
        iv.setImageResource(R.drawable.dot_normal);
        linearLayout.addView(iv);
    }

    @Override
    public void selectImage(int index) {
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            ImageView iv = (ImageView) linearLayout.getChildAt(i);
            if (index == i) {
                iv.setImageResource(R.drawable.dot_select);
            } else {
                iv.setImageResource(R.drawable.dot_normal);
            }
        }
    }

    @Override
    public void clickBanner(int index) {
        if (bannerListener != null) {
            bannerListener.clickBanner(index);
        }
    }


}

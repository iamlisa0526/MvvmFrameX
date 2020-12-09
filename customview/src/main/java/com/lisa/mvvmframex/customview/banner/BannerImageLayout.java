package com.lisa.mvvmframex.customview.banner;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

import androidx.annotation.NonNull;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 自定义ViewGroup中必须要实现的方法有：测量-布局
 *
 * @Description: Banner中图片的Layout
 * @Author: lisa
 * @CreateDate: 2020/12/3 16:55
 */
class BannerImageLayout extends ViewGroup {
    //子View个数
    private int childCount;
    private int childWidth, childHeight;
    //第一次按下位置的横坐标和每一次移动中移动之前的横坐标
    private int x;
    //每张图片的索引
    private int index = 0;
    //方式2利用scroller实现轮播图的手动轮播
    private Scroller scroller;

    //----------------------------自动轮播start----------------------------
    //采用Timer,TimerTask,Handler相结合的方式实现
    private boolean isAuto = true;//默认开启自动轮播
    private Timer timer = new Timer();
    private TimerTask timerTask;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 0:
                    if (++index >= childCount) {//表示如果是最后一张图的话将会从第一张开始重新轮播
                        index = 0;
                    }
                    scrollTo(index * childWidth, 0);

                    dotSwitchListener.selectImage(index);
                    break;
            }
        }
    };

    private void startAuto() {
        isAuto = true;
    }

    private void stopAuto() {
        isAuto = false;
    }

    //----------------------------自动轮播end----------------------------

    //----------------------------轮播图片点击事件start----------------------------
    private boolean isClick;

    private BannerClickListener listener;

    public void setBannerClickListener(BannerClickListener listener) {
        this.listener = listener;
    }

    public interface BannerClickListener {
        void clickBanner(int index);
    }
    //----------------------------轮播图片点击事件end----------------------------

    //----------------------------切换圆点start----------------------------
    private DotSwitchListener dotSwitchListener;

    public void setDotSwitchListener(DotSwitchListener dotSwitchListener) {
        this.dotSwitchListener = dotSwitchListener;
    }

    public interface DotSwitchListener {
        void selectImage(int index);
    }
    //----------------------------切换圆点end----------------------------

    public BannerImageLayout(Context context) {
        this(context, null);
    }

    public BannerImageLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerImageLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public BannerImageLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        scroller = new Scroller(getContext());
        timerTask = new TimerTask() {
            @Override
            public void run() {
                if (isAuto) {
                    handler.sendEmptyMessage(0);
                }
            }
        };
        timer.schedule(timerTask, 100, 2000);
    }

    /**
     * 方式2利用scroller实现轮播图的手动轮播
     */
    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), 0);
            invalidate();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //1、求出子View个数
        childCount = getChildCount();
        if (0 == childCount) {
            setMeasuredDimension(0, 0);
        } else {
            //2、测量子View的宽高
            measureChildren(widthMeasureSpec, heightMeasureSpec);
            //此时以第一个子View为基准，高度就是第一个子View的高度，宽度就是第一个子View的宽度*子View个数
            View firstChild = getChildAt(0);
            //3、根据子View的宽高，求出该ViewGroup的宽高
            childWidth = firstChild.getMeasuredWidth();
            childHeight = firstChild.getMeasuredHeight();
            int width = childWidth * childCount;
            setMeasuredDimension(width, childHeight);
        }
    }

    /**
     * @param changed 当布局位置发生改变为true,否则为false
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            int leftMargin = 0;
            for (int i = 0; i < childCount; i++) {
                View childView = getChildAt(i);
                childView.layout(leftMargin, 0, childWidth + leftMargin, childHeight);
                leftMargin += childWidth;
            }
        }
    }

    /**
     * 事件传递中的事件拦截
     *
     * @param ev
     * @return true表示处理此次拦截事件，否则向下传递，但真正的事件处理是交由onTouchEvent的
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    /**
     * 两种方式实现轮播图的手动轮播：
     * 1、利用scrollTo,scrollBy
     * 2、利用scroller
     *
     * @param event
     * @return true告知父容器我处理了该事件
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //todo 可以尝试利用手势探测GestureDetector实现

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //方式1优化
                if (!scroller.computeScrollOffset()) {
                    scroller.abortAnimation();
                }
                x = (int) event.getX();
                Log.d("BannerLayout", "x:" + x);

                //手动点击按下的时候停止自动轮播
                stopAuto();

                isClick = true;
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) event.getX();
                int distance = moveX - x;
                scrollBy(-distance, 0);
                x = moveX;
                Log.d("BannerLayout", "x:" + x + ", distance:" + distance);

                isClick = false;
                break;
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                Log.d("BannerLayout", "scrollX:" + scrollX);
                index = (scrollX + childWidth / 2) / childWidth;
                //滑动到了第一张图片
                if (index < 0) {
                    index = 0;
                } else if (index > childCount - 1) {//滑动到了最后一张图片
                    index = childCount - 1;
                }


                if (isClick) {
                    listener.clickBanner(index);
                } else {
                    //方式1
//                scrollTo(index * childWidth, 0);

                    //方式2
                    int dx = index * childWidth - scrollX;
                    scroller.startScroll(scrollX, 0, dx, 0);
                    postInvalidate();

                    dotSwitchListener.selectImage(index);
                }

                //手动点击抬起的时候开启自动轮播
                startAuto();
                break;

            default:
                break;
        }
        return true;
    }
}

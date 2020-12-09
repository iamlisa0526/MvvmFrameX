package com.lisa.mvvmframex.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

/**
 * @Description: 有描边的圆形图片, 设置的View的宽高应该是(radius + borderWidth)*2
 * @Author: lisa
 * @CreateDate: 2020/12/8 09:51
 */
public class CircleImageView extends AppCompatImageView {
    /**
     * 半径
     */
    private float radius;

    /**
     * 描边宽度，默认0即没有描边
     */
    private float borderWidth;

    /**
     * 描边颜色，默认白色
     */
    private int borderColor = Color.WHITE;

    /**
     * 描边画笔、bitmap画笔
     */
    private Paint borderPaint, bitmapPaint;

    /**
     * 要绘制的bitmap
     */
    private Bitmap bitmap;

    /**
     * 圆的中心
     */
    private float circleCenter;

    public CircleImageView(Context context) {
        this(context, null);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView);
            borderWidth = (int) ta.getDimension(R.styleable.CircleImageView_borderWidth, 0);
            borderColor = ta.getColor(R.styleable.CircleImageView_borderColor, Color.WHITE);
            radius = ta.getDimension(R.styleable.CircleImageView_radius, 0);
            ta.recycle();
        }

        init();
    }

    /**
     * 初始化
     */
    private void init() {
        borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        borderPaint.setColor(borderColor);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(borderWidth);

        bitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    /**
     * 绘制
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        loadBitmap();
        if (bitmap != null) {
            //圆的中心
            circleCenter = radius + borderWidth;

            //按照新的尺寸缩放原来的bitmap
            bitmap = Bitmap.createScaledBitmap(bitmap, (int) radius * 2, (int) radius * 2, false);

            //绘制圆（注意圆环的绘制半径是真正的半径+圆环宽度/2）
            canvas.drawCircle(circleCenter, circleCenter, radius + borderWidth / 2, borderPaint);
            //绘制bitmap
            canvas.drawBitmap(createCircleBitmap(bitmap), borderWidth, borderWidth, bitmapPaint);
        } else {
            super.onDraw(canvas);
        }
    }

    /**
     * 创建圆形bitmap
     *
     * @param bitmap
     * @return
     */
    private Bitmap createCircleBitmap(Bitmap bitmap) {
        Bitmap bm = Bitmap.createBitmap((int) radius * 2, (int) radius * 2, Bitmap.Config.ARGB_8888);
        Canvas bmCanvas = new Canvas(bm);
        bmCanvas.drawCircle(radius, radius, radius, bitmapPaint);
        bitmapPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        bmCanvas.drawBitmap(bitmap, 0, 0, bitmapPaint);
        //用完及时清除Xfermode否则会有黑色背景
        bitmapPaint.setXfermode(null);
        return bm;
    }

    /**
     * 加载图片
     */
    private void loadBitmap() {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) getDrawable();
        if (bitmapDrawable != null) {
            bitmap = bitmapDrawable.getBitmap();
        }
    }

}

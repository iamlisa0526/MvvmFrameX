package com.lisa.mvvmframex.base.customview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.NoCopySpan;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;

import com.lisa.mvvmframex.base.R;
import com.lisa.mvvmframex.base.utils.ToastUtil;

import java.io.UnsupportedEncodingException;


/**
 * todo 优化
 * Author: dengdayi
 * Date: 2015/10/19 15:20
 * Description:带清除按钮的EditText
 */
public class ClearEditText extends AppCompatEditText {
    private Context mContext;
    /**
     * 删除按钮的引用
     */
    private Drawable mClearDrawable;
    /**
     * 左边 Drawable 图片资源
     */
    Drawable drawableLeft;
    /**
     * 控件是否有焦点
     */
    private boolean mHasFoucs;
    /**
     * 限制最大输入字节数
     */
    private int mMaxBytes;

    public void setmMaxBytes(int mMaxBytes) {
        this.mMaxBytes = mMaxBytes;
    }

    public ClearEditText(Context context) {
        super(context);
        this.mContext = context;
        initView();
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView();
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
    }

    /**
     * 初始化组件
     */
    private void initView() {
        //设置取消下划线背景
        setBackground(null);
        //获取EditText的DrawableRight,假如没有设置我们就使用默认的图片
        Drawable[] d = this.getCompoundDrawables();
        drawableLeft = this.getCompoundDrawables()[0];//获取drawableLeft 图片资源对象
        mClearDrawable = mContext.getApplicationContext().getResources().getDrawable(R.mipmap.edit_clear);
        setDrawableLeft(drawableLeft);
        setClearIcon(mClearDrawable);
        //默认设置隐藏图标
        setClearIconVisible(false);
        //设置焦点改变的监听
        setOnFocusChangeListener(changeListener);
        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (editTextWatcher != null) {
                    editTextWatcher.beforeTextChanged(s, start, count, after);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (mMaxBytes > 0) {
                    Editable editable = ClearEditText.this.getText();
                    int len = editable.toString().getBytes().length;
                    if (len > mMaxBytes) {
                        ToastUtil.showToast(mContext, "最大字节数" + mMaxBytes);
                        int selEndIndex = Selection.getSelectionEnd(editable);
                        String str = editable.toString();
                        //截取新字符串
                        String newStr = getWholeText(str, mMaxBytes);
                        ClearEditText.this.setText(newStr);
                        editable = ClearEditText.this.getText();

                        //新字符串的长度
                        int newLen = editable.length();
                        //旧光标位置超过字符串长度
                        if (selEndIndex > newLen) {
                            selEndIndex = editable.length();
                        }
                        //设置新光标所在的位置
                        Selection.setSelection(editable, selEndIndex);
                    }
                }

                if (editTextWatcher != null) {
                    editTextWatcher.onTextChanged(s, start, before, count);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (editTextWatcher != null) {
                    editTextWatcher.afterTextChanged(s);
                }

                if (editTextWatcher2 != null) {
                    editTextWatcher2.afterTextChanged(s);
                }
                // 判断输入框中是否有值
                setClearIconVisible(getText().length() > 0);
            }
        });
    }

    /**
     * 设置删除图标
     *
     * @param drawable
     */
    public void setClearIcon(Drawable drawable) {
        this.mClearDrawable = drawable;
        setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, drawable, null);
    }

    /**
     * 设置左边图片
     *
     * @param drawableLeft
     */
    public void setDrawableLeft(Drawable drawableLeft) {
        this.drawableLeft = drawableLeft;
        setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, mClearDrawable, null);
    }

//    /**
//     * 删除键 的触发
//     */
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        if (event.getAction() == MotionEvent.ACTION_UP) {
//            if (getCompoundDrawables()[2] != null) {
//                boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight())
//                        && (event.getX() < ((getWidth() - getPaddingRight())));
//                if (touchable) {
//                    this.setText("");
//                }
//            }
//        }
//        return super.onTouchEvent(event);
//    }

    /**
     * 当手指抬起的位置在clean的图标的区域
     * 我们将此视为进行清除操作
     * getWidth():得到控件的宽度
     * event.getX():抬起时的坐标(改坐标是相对于控件本身而言的)
     * getTotalPaddingRight():clean的图标左边缘至控件右边缘的距离
     * getPaddingRight():clean的图标右边缘至控件右边缘的距离
     * 于是:
     * <p/>
     * getWidth() - getTotalPaddingRight()表示:控件左边到clean的图标左边缘的区域
     * getWidth() - getPaddingRight()表示:控件左边到clean的图标右边缘的区域
     * <p/>
     * 所以这两者之间的区域刚好是clean的图标的区域
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {
                boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight() - 20) && (event.getX() < ((getWidth() - getPaddingRight() + 20)));
                if (touchable) {
                    this.setText("");
                }
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 焦点监听,当EidtText获取焦点时触发
     */
    OnFocusChangeListener changeListener = new OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            mHasFoucs = hasFocus;
            if (hasFocus) {
                setClearIconVisible(getText().length() > 0);
            } else {
                setClearIconVisible(false);
            }
        }
    };

    /**
     * 设置EditText 右边清除按钮的图片
     *
     * @param visible
     */
    public void setClearIconVisible(boolean visible) {
        if (visible) {
            setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, mClearDrawable, null);
        } else {
            setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, null, null);
        }
    }

    /**
     * 自定义接口
     */
    public interface EditTextWatcher2 extends NoCopySpan {
        void afterTextChanged(Editable s);
    }

    private EditTextWatcher2 editTextWatcher2;

    /**
     * 提供给外部的监听事件
     */
    public void setEditTextWatcher2(EditTextWatcher2 editTextWatcher) {
        this.editTextWatcher2 = editTextWatcher;
    }


    /**
     * 自定义接口
     */
    public interface EditTextWatcher extends NoCopySpan {
        void beforeTextChanged(CharSequence s, int start,
                               int count, int after);

        void onTextChanged(CharSequence s, int start, int before, int count);

        void afterTextChanged(Editable s);
    }

    private EditTextWatcher editTextWatcher;

    /**
     * 提供给外部的监听事件
     */
    public void setEditTextWatcher(EditTextWatcher editTextWatcher) {
        this.editTextWatcher = editTextWatcher;
    }

    private String getWholeText(String text, int byteCount) {
        try {
            if (text != null && text.getBytes("utf-8").length > byteCount) {
                char[] tempChars = text.toCharArray();
                int sumByte = 0;
                int charIndex = 0;
                for (int i = 0, len = tempChars.length; i < len; i++) {
                    char itemChar = tempChars[i];
                    // 根据Unicode值，判断它占用的字节数
                    if (itemChar >= 0x0000 && itemChar <= 0x007F) {
                        sumByte += 1;
                    } else if (itemChar >= 0x0080 && itemChar <= 0x07FF) {
                        sumByte += 2;
                    } else {
                        sumByte += 3;
                    }
                    if (sumByte > byteCount) {
                        charIndex = i;
                        break;
                    }
                }
                return String.valueOf(tempChars, 0, charIndex);
            }
        } catch (UnsupportedEncodingException e) {
        }
        return text;
    }

}

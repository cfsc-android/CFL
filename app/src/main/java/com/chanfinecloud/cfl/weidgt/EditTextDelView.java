package com.chanfinecloud.cfl.weidgt;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;

import com.chanfinecloud.cfl.R;


/**
 * 带删除的输入框
 */
public class EditTextDelView extends EditText implements View.OnFocusChangeListener,
        TextWatcher {
    /**
     * 删除按钮的引用
     */
    private Drawable mClearDrawable;
    /**
     * 底部横线的颜色
     */
    private ColorDrawable bottomColor;
    private boolean hasFoucs;


    public EditTextDelView(Context context) {
        this(context, null);
    }

    public EditTextDelView(Context context, AttributeSet attrs) {
        // 这里构造方法也很重要，不加这个很多属性不能再XML里面定义
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public EditTextDelView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    //判断是否控件获取焦点时下划线是否更改颜色
    private boolean isUnderline = false;

    public boolean isUnderline() {
        return isUnderline;
    }

    public void setIsUnderline(boolean isUnderline) {
        this.isUnderline = isUnderline;
        if (isUnderline)
            setUnderlineColor(false);
    }

    private void init() {
        // 获取EditText的DrawableRight,假如没有设置我们就使用默认的图片,2是获得右边的图片 顺序是左上右下（0,1,2,3,）
        mClearDrawable = getCompoundDrawables()[2];
        if (mClearDrawable == null) {
            // throw new
            // NullPointerException("You can add drawableRight attribute in XML");
            mClearDrawable = getResources().getDrawable(R.drawable.login_clear_name);
        }

        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(),
                mClearDrawable.getIntrinsicHeight());

        // 默认设置隐藏图标
        setClearIconVisible(false);

        // 设置焦点改变的监听
        setOnFocusChangeListener(this);
        // 设置输入框里面内容发生改变的监听
        addTextChangedListener(this);
    }

    /**
     * 因为我们不能直接给EditText设置点击事件，所以我们用记住我们按下的位置来模拟点击事件 当我们按下的位置 在 EditText的宽度 -
     * 图标到控件右边的间距 - 图标的宽度 和 EditText的宽度 - 图标到控件右边的间距之间我们就算点击了图标，竖直方向就没有考虑
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {
                boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight())
                        && (event.getX() < ((getWidth() - getPaddingRight())));
                if (touchable) {
                    this.setText("");
                }
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 当ClearEditText焦点发生变化的时候，判断里面字符串长度设置清除图标的显示与隐藏
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        this.hasFoucs = hasFocus;
        if (hasFocus) {
            if (isUnderline)
                setUnderlineColor(true);
            setClearIconVisible(getText().length() > 0);
        } else {
            if (isUnderline)
                setUnderlineColor(false);
            setClearIconVisible(false);
        }
    }

    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     *
     * @param visible
     */
    protected void setClearIconVisible(boolean visible) {
        Drawable right = visible ? mClearDrawable : null;

        setCompoundDrawables(getCompoundDrawables()[0],
                getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }

    /**
     * 更改下划线颜色
     *
     * @param isOnFocus 是否获取焦点
     */
    protected void setUnderlineColor(boolean isOnFocus) {
        bottomColor = (ColorDrawable) getCompoundDrawables()[3];
        if (bottomColor == null) {
            bottomColor = new ColorDrawable();
            bottomColor.setColor(getResources().getColor(R.color.input_view_loss_focus_line_color));
        }

        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        bottomColor.setBounds(0, 0, width, 2);

        if (isOnFocus) {
            bottomColor.setColor(getResources().getColor(R.color.input_view_get_focus_line_color));
        } else {
            bottomColor.setColor(getResources().getColor(R.color.input_view_loss_focus_line_color));
        }
        setCompoundDrawables(getCompoundDrawables()[0],
                getCompoundDrawables()[1], getCompoundDrawables()[2], bottomColor);
    }

    /**
     * 当输入框里面内容发生变化的时候回调的方法
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int count, int after) {
        if (hasFoucs) {
            setClearIconVisible(s.length() > 0);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    /**
     * 设置晃动动画
     */
    public void setShakeAnimation() {
        this.setAnimation(shakeAnimation(5));
    }

    /**
     * 晃动动画
     *
     * @param counts 1秒钟晃动多少下
     * @return
     */
    public static Animation shakeAnimation(int counts) {
        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
        // 设置一个循环加速器，使用传入的次数就会出现摆动的效果。
        translateAnimation.setInterpolator(new CycleInterpolator(counts));
        translateAnimation.setDuration(500);
        return translateAnimation;
    }
}
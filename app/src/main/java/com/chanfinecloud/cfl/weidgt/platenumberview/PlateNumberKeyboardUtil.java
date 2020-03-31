package com.chanfinecloud.cfl.weidgt.platenumberview;

import android.app.Activity;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.view.View;

import com.chanfinecloud.cfl.R;


/**
 * Created by zengx on 2019/5/20.
 * Describe:
 */
public class PlateNumberKeyboardUtil {
    private Activity mActivity;
    private KeyboardView mKeyboardView;
    private CarPlateNumberEditView cpnView;
    /**
     * 省份简称键盘
     */
    private Keyboard provinceKeyboard;
    /**
     * 数字与大写字母键盘
     */
    private Keyboard numberKeyboard;
    public PlateNumberKeyboardUtil(Activity activity, CarPlateNumberEditView view){
        mActivity = activity;
        cpnView = view;
        provinceKeyboard = new Keyboard(activity, R.xml.province_abbreviation);
        numberKeyboard = new Keyboard(activity, R.xml.number_or_letters);
        mKeyboardView = (KeyboardView) activity.findViewById(R.id.keyboard_view);
        mKeyboardView.setKeyboard(provinceKeyboard);
        mKeyboardView.setEnabled(true);
        mKeyboardView.setPreviewEnabled(false);
        mKeyboardView.setOnKeyboardActionListener(listener);
    }
    private KeyboardView.OnKeyboardActionListener listener = new KeyboardView.OnKeyboardActionListener() {
        @Override
        public void swipeUp() {
        }

        @Override
        public void swipeRight() {
        }

        @Override
        public void swipeLeft() {
        }

        @Override
        public void swipeDown() {
        }

        @Override
        public void onText(CharSequence text) {

        }

        @Override
        public void onRelease(int primaryCode) {
        }

        @Override
        public void onPress(int primaryCode) {
        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            //判定是否是中文的正则表达式 [\\u4e00-\\u9fa5]判断一个中文 [\\u4e00-\\u9fa5]+多个中文
            String reg = "[\\u4e00-\\u9fa5]";
            if (primaryCode == -1) {// 省份简称与数字键盘切换
                if (cpnView.getPlateNumberText().matches(reg)) {
                    changeKeyboard(true);
                }
            } else if (primaryCode == -3) {
                cpnView.deletePlateNumber();
                if(cpnView.getPlateNumberPosition()==0){
                    changeKeyboard(false);
                }
            } else {
                if(cpnView.getPlateNumberPosition()<8){
                    cpnView.setPlateNumber(Character.toString((char) primaryCode));
                    // 判断第一个字符是否是中文,是，则自动切换到数字软键盘
                    if (cpnView.getPlateNumberText().matches(reg)) {
                        changeKeyboard(true);
                    }
                }
            }
        }
    };

    /**
     * 指定切换软键盘 isNumber false表示要切换为省份简称软键盘 true表示要切换为数字软键盘
     */
    private void changeKeyboard(boolean isNumber) {
        if (isNumber) {
            mKeyboardView.setKeyboard(numberKeyboard);
        } else {
            mKeyboardView.setKeyboard(provinceKeyboard);
        }
    }

    /**
     * 软键盘展示状态
     */
    public boolean isShow() {
        return mKeyboardView.getVisibility() == View.VISIBLE;
    }

    /**
     * 软键盘展示
     */
    public void showKeyboard() {
        int visibility = mKeyboardView.getVisibility();
        if (visibility == View.GONE || visibility == View.INVISIBLE) {
            mKeyboardView.setVisibility(View.VISIBLE);
            cpnView.setFocus(true);
        }
    }

    /**
     * 软键盘隐藏
     */
    public void hideKeyboard() {
        int visibility = mKeyboardView.getVisibility();
        if (visibility == View.VISIBLE) {
            mKeyboardView.setVisibility(View.INVISIBLE);
            cpnView.setFocus(false);
        }
    }
}


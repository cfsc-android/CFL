package com.chanfinecloud.cfl.weidgt;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.chanfinecloud.cfl.CFLApplication;
import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.util.Utils;


/**
 * 进度条工具类
 */
public class ProgressDialogUtil {
    private ProgressDialog progressDialog = null;
    private ImageView progressImageView = null;
    private TextView loadingTitle = null;
    private AnimationDrawable animationDrawable;

    /**
     * @param con
     * @param message
     * @方法说明:
     * @方法名称:startLoad
     * @返回值:void
     */
    public void startLoad(Context con, String message, boolean isClose) {
        if (progressDialog != null) {
            stopLoad();
        }
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(con, R.style.loading_dialog);
            View proView = Utils.LoadXmlView(CFLApplication.getAppContext(),
                    R.layout.loading);
            progressDialog.show();
            progressDialog.setContentView(proView, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            progressImageView = (ImageView) proView.findViewById(R.id.loading_animato);
            progressImageView.setVisibility(View.VISIBLE);
//            if (!Tools.isEmpty(message)) {
//                loadingTitle = (TextView) proView.findViewById(R.id.loading_title);
//                loadingTitle.setVisibility(View.VISIBLE);
//                loadingTitle.setText(message);
//            }

            progressImageView.setImageResource(R.drawable.kprogresshud_spinner);


            AnimationSet animationSet = new AnimationSet(false);
            RotateAnimation rotateAnimation = new RotateAnimation(0, 345,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setDuration(1000);
            rotateAnimation.setRepeatCount(-1);
            rotateAnimation.setInterpolator(new LinearInterpolator());
            animationSet.addAnimation(rotateAnimation);
            progressImageView.startAnimation(animationSet);


        }
//        closeLockScreen();
        if (!isClose)
            closeCancelable();
    }

    /**
     * @param con
     * @param message
     * @方法说明:
     * @方法名称:startLoad
     * @返回值:void
     */
    public void startLoad(Context con, String message) {
        if (progressDialog != null) {
            stopLoad();
        }
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(con, R.style.loading_dialog);
            View proView = Utils.LoadXmlView(CFLApplication.getAppContext(),
                    R.layout.loading);
            progressDialog.show();
            progressDialog.setContentView(proView, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            progressImageView = (ImageView) proView
                    .findViewById(R.id.loading_animato);
            progressImageView.setVisibility(View.VISIBLE);

//            progressImageView.setImageResource(R.drawable.animation_loading_progress_dialog);
            progressImageView.setImageResource(R.drawable.kprogresshud_spinner);
//            animationDrawable = (AnimationDrawable) progressImageView.getDrawable();
//            animationDrawable.start();

            AnimationSet animationSet = new AnimationSet(false);
//                  //参数1：从哪个旋转角度开始
//                  //参数2：转到什么角度
//                  //后4个参数用于设置围绕着旋转的圆的圆心在哪里
//                  //参数3：确定x轴坐标的类型，有ABSOLUT绝对坐标、RELATIVE_TO_SELF相对于自身坐标、RELATIVE_TO_PARENT相对于父控件的坐标
//                  //参数4：x轴的值，0.5f表明是以自身这个控件的一半长度为x轴
//                  //参数5：确定y轴坐标的类型
//                  //参数6：y轴的值，0.5f表明是以自身这个控件的一半长度为x轴
            RotateAnimation rotateAnimation = new RotateAnimation(0, 345,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setDuration(1000);
            rotateAnimation.setRepeatCount(-1);
            rotateAnimation.setInterpolator(new LinearInterpolator());
            animationSet.addAnimation(rotateAnimation);
            progressImageView.startAnimation(animationSet);
        }
        closeCancelable();
//        closeLockScreen();
    }


    public void startCustomerLoad(Context context,View v,boolean cancelable){
        if (progressDialog != null) {
            stopLoad();
        }
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context, R.style.customer_dialog);

            progressDialog.show();
            progressDialog.setContentView(v, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            progressDialog.setCancelable(cancelable);
        }
    }

//    /**
//     * @param message
//     * @方法说明:设置加载内容
//     * @方法名称:setLoadMessage
//     * @返回值:void
//     */
//    public void setLoadMessage(String message) {
//        if (progressImageView != null && !Tools.isEmpty(message)) {
//            progressImageView.setVisibility(View.VISIBLE);
//            progressImageView.setText(message);
//        } else {
//            progressImageView.setVisibility(View.GONE);
//        }
//    }

    /**
     * @方法说明:禁止加载条转动的时候去点击系统返回键
     * @方法名称:closeCancelable
     * @返回值:void
     */
    public void closeCancelable() {
        if (progressDialog != null) {
            progressDialog.setCancelable(false);
        }
    }

    /**
     * @方法说明:允许加载条转动的时候去点击系统返回键
     * @方法名称:openCancelable
     * @返回值:void
     */
    public void openCancelable() {
        if (progressDialog != null) {
            progressDialog.setCancelable(true);
        }
    }

    /**
     * @方法说明：允许点击对话框触摸消失
     * @方法名称：openLockScreen
     * @返回值：void
     */
    public void openLockScreen() {
        if (progressDialog != null) {
            progressDialog.setCanceledOnTouchOutside(true);
        }
    }

    /**
     * @方法说明：禁止点击对话框触摸消失
     * @方法名称：closeLockScreen
     * @返回值：void
     */
    public void closeLockScreen() {
        if (progressDialog != null) {
            progressDialog.setCanceledOnTouchOutside(true);
        }
    }

    /**
     * @方法说明:停止加载
     * @方法名称:stopLoad
     * @返回值:void
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void stopLoad() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
        if (progressImageView != null) {
            progressImageView.clearAnimation();
            progressImageView = null;
        }
    }
}
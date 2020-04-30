package com.chanfinecloud.cfl.ui.base;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Toast;


import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.entity.core.Transition;
import com.chanfinecloud.cfl.http.RequestParam;
import com.chanfinecloud.cfl.receiver.NetBroadcastReceiver;
import com.chanfinecloud.cfl.util.AtyTransitionUtil;
import com.chanfinecloud.cfl.util.LogUtils;
import com.chanfinecloud.cfl.util.LynActivityManager;
import com.chanfinecloud.cfl.util.NetworkUtils;
import com.chanfinecloud.cfl.util.PermissionUtil;
import com.chanfinecloud.cfl.util.StatusBarUtil;
import com.chanfinecloud.cfl.weidgt.ProgressDialogView;
import com.umeng.analytics.MobclickAgent;

import org.xutils.x;

import androidx.activity.ComponentActivity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import butterknife.ButterKnife;

import static com.chanfinecloud.cfl.CFLApplication.activityTrans;
import static com.chanfinecloud.cfl.ui.base.BaseHandler.HTTP_CANCEL;
import static com.chanfinecloud.cfl.ui.base.BaseHandler.HTTP_REQUEST;
import static com.chanfinecloud.cfl.util.PermissionUtil.REQUEST_CODE;


/**
 * Created by Loong on 2020/2/6.
 * Version: 1.0
 * Describe:  Activity基础类 FragmentActivity
 */
public abstract class BaseActivity extends FragmentActivity implements NetBroadcastReceiver.NetEvent {
    public NetBroadcastReceiver netBroadcastReceiver;
    private ProgressDialogView progressDialogView = null;

    private boolean isFullScreen;
    private boolean showStatus;
    public boolean isNetConnect=true;
    protected static BaseHandler handler;
    protected boolean permission=false;
    private boolean clickable = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LynActivityManager.getInstance().pushActivity(this);//Activity入栈
        requestWindowFeature(Window.FEATURE_NO_TITLE);//设置无标题
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//垂直显示
        //是否全屏
        if(isFullScreen){
            //是否显示状态栏
            if(showStatus){
                StatusBarUtil.setTranslucentStatus(this);
            }else{
                StatusBarUtil.fullScreen(this);
            }
        }else{
            StatusBarUtil.setStatusBarMode(this, false, R.color.main_background);
        }
        //x.view().inject(this);//注入activity
        initReceiver();//注册网络状态检测广播服务
        handler=new BaseHandler(this);//初始化BaseHandler

        initData();
    }
    /**
     * damien
     * 丢弃 onCreate   初始数据绑定的地方
     */
    protected abstract void initData();


    /**
     * 设置全屏
     * @param showStatus boolean
     */
    protected void setFullScreen(boolean showStatus){
        this.isFullScreen=true;
        this.showStatus=showStatus;
    }

    /**
     * 注册网络状态检测广播服务
     */
    private void initReceiver(){
        IntentFilter filter = new IntentFilter();//实例化IntentFilter对象
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        netBroadcastReceiver = new NetBroadcastReceiver(this);
        registerReceiver(netBroadcastReceiver, filter);
    }


    /**
     * 检查应用权限
     */
    protected void checkAppPermission(){
        String[] unGetPermission = PermissionUtil.checkPermission();
        if(unGetPermission!=null){
            ActivityCompat.requestPermissions(this,unGetPermission, REQUEST_CODE);
        }else{
            permission=true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);//友盟统计
        clickable = true;
    }

    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);//友盟统计
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消菊花
        if (progressDialogView != null) {
            progressDialogView.stopLoad();
            progressDialogView = null;
        }
     //   handler.sendEmptyMessage(HTTP_CANCEL);//取消http请求
        unregisterReceiver(netBroadcastReceiver);//注销广播服务
        LynActivityManager.getInstance().popActivity(this);//从栈中移除当前Activity
        //移除转场动画
        if(isActivityTrans()){
            Class clazz=this.getClass();
            activityTrans.remove(clazz);
        }
    }

    @Override
    public void onNetChange(int netMobile) {
        isNetConnect= NetworkUtils.isNetConnect(netMobile);
        LogUtils.d(NetworkUtils.isNetConnect(netMobile));
    }

    /**
     * 发送一个请求
     * @param requestParam 请求体
     * @param showProgressDialog 是否转菊花
     */
    protected void sendRequest(RequestParam requestParam, boolean showProgressDialog){
        if(isNetConnect){
            if(showProgressDialog){
                startProgressDialog(true);
            }
            Message message=new Message();
            message.what=HTTP_REQUEST;
            Bundle bundle=new Bundle();
            bundle.putSerializable("request",requestParam);
            message.setData(bundle);
            handler.sendMessage(message);
        }else{
            showToast("没有网络，请前往网络设置检查");
        }
    }

    /**
     * 启动新的Activity 默认Trans-LeftIn
     * @param clazz Class
     */
    public void startActivity(Class clazz){
        startActivity(clazz, Transition.RightIn);
    }

    /**
     * 启动新的Activity
     * @param clazz Class
     * @param transition  转场动画
     */
    public void startActivity(Class clazz, Transition transition){
        activityTrans.put(clazz,transition);
        startActivity(new Intent(this, clazz));
        executeTransition(transition);
    }

    /**
     * 启动新的Activity
     * @param clazz Class
     * @param bundle Bundle
     */
    public void startActivity(Class clazz, Bundle bundle){
        startActivity(clazz,bundle,Transition.RightIn);
    }


    /**
     * 启动新的Activity
     * @param clazz Class
     * @param bundle Bundle
     * @param transition 转场动画
     */
    public void startActivity(Class clazz, Bundle bundle, Transition transition){
        activityTrans.put(clazz,transition);
        Intent intent = new Intent();
        intent.setClass(this, clazz);
        intent.putExtras(bundle);
        startActivity(intent);
        executeTransition(transition);
    }

    /**
     * 启动新的Activity
     * @param clazz Class
     * @param bundle Bundle
     * @param requestCode 返回码
     * @param transition 转场动画
     */
    public void startActivityForResult(Class clazz, Bundle bundle, int requestCode, Transition transition) {
        activityTrans.put(clazz,transition);
        Intent intent = new Intent();
        intent.setClass(this, clazz);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
        executeTransition(transition);
    }

    @Override
    public void finish() {
        super.finish();
        //执行Activity移除转场动画
        if(isActivityTrans()){
            Class clazz=this.getClass();
            Transition currentTrans=getTrans(clazz);
            activityTrans.remove(clazz);
            executeTransition(getReverse(currentTrans));
        }
    }

    /**
     * 执行转场动画
     * @param transition Transition转场动画
     */
    private void executeTransition(Transition transition){
        switch (transition){
            case TopIn:
                AtyTransitionUtil.enterFromTop(this);
                break;
            case TopOut:
                AtyTransitionUtil.exitToTop(this);
                break;
            case LeftIn:
                AtyTransitionUtil.enterFromLeft(this);
                break;
            case LeftOut:
                AtyTransitionUtil.exitToLeft(this);
                break;
            case BottomIn:
                AtyTransitionUtil.enterFromBottom(this);
                break;
            case BottomOut:
                AtyTransitionUtil.exitToBottom(this);
                break;
            case RightIn:
                AtyTransitionUtil.enterFromRight(this);
                break;
            case RightOut:
                AtyTransitionUtil.exitToRight(this);
                break;
        }
    }


    /**
     * 获取当前activity的转场动画
     * @param clazz Class
     * @return Transition
     */
    private Transition getTrans(Class clazz){
        return activityTrans.get(clazz);
    }

    /**
     * 判断当前Activity是否存在转场动画
     * @return Boolean
     */
    private Boolean isActivityTrans(){
        return activityTrans.containsKey(this.getClass());
    }


    /**
     * 获取反向的转场动画
     * @param transition Transition
     * @return Transition
     */
    private Transition getReverse(Transition transition){
        Transition tran = Transition.TopOut;
        switch (transition){
            case TopIn:
                tran = Transition.TopOut;
                break;
            case TopOut:
                tran = Transition.TopIn;
                break;
            case LeftIn:
                tran = Transition.LeftOut;
                break;
            case LeftOut:
                tran = Transition.LeftIn;
                break;
            case BottomIn:
                tran = Transition.BottomOut;
                break;
            case BottomOut:
                tran = Transition.BottomIn;
                break;
            case RightIn:
                tran = Transition.RightOut;
                break;
            case RightOut:
                tran = Transition.RightIn;
                break;
        }
        return tran;
    }

    /**
     * 启动加载框
     */
    protected void startProgressDialog() {
        if (progressDialogView == null) {
            progressDialogView = new ProgressDialogView();
        }
        progressDialogView.startLoad(this, "",false);
    }

    /**
     * 启动加载框
     * @param msg 提示文字
     */
    protected void startProgressDialog(String msg) {
        if (progressDialogView == null) {
            progressDialogView = new ProgressDialogView();
        }
        progressDialogView.startLoad(this, msg,false);
    }

    /**
     * 启动加载框
     * @param cancelable 是否可关闭
     */
    protected void startProgressDialog(boolean cancelable) {
        if (progressDialogView == null) {
            progressDialogView = new ProgressDialogView();
        }
        progressDialogView.startLoad(this, "",cancelable);
    }

    /**
     * 启动加载框
     * @param msg 提示文字
     * @param cancelable 是否可关闭
     */
    protected void startProgressDialog(String msg, boolean cancelable) {
        if (progressDialogView == null) {
            progressDialogView = new ProgressDialogView();
        }
        progressDialogView.startLoad(this, msg,cancelable);
    }

    protected void startCustomerDialog(View v, boolean cancelable){
        if (progressDialogView == null) {
            progressDialogView = new ProgressDialogView();
        }
        progressDialogView.startCustomerLoad(this, v,cancelable);
    }

    /**
     * 关闭加载框
     */
    protected void stopProgressDialog() {
        if (progressDialogView != null) {
            progressDialogView.stopLoad();
        }
    }

    /**
     * 显示Toast
     * @param content Toast文字
     */
    protected void showToast(String content){
        Toast.makeText(this,content, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (REQUEST_CODE == requestCode) {
            for (int grantResult : grantResults) {
                if (grantResult == -1) {
                    permission=false;
                }
            }
        }
    }

    /**
     * 当前是否可以点击
     *
     * @return
     */
    protected boolean isClickable() {
        return clickable;
    }

    /**
     * 锁定点击
     */
    protected void lockClick() {
        clickable = false;
    }
}

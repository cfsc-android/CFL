package com.chanfinecloud.cfl.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;

import android.os.Message;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import com.chanfinecloud.cfl.CFLApplication;
import com.chanfinecloud.cfl.entity.LoginUserEntity;
import com.chanfinecloud.cfl.http.RequestParam;
import com.chanfinecloud.cfl.receiver.NetBroadcastReceiver;
import com.chanfinecloud.cfl.ui.base.BaseHandler;
import com.chanfinecloud.cfl.util.Constants;
import com.chanfinecloud.cfl.util.FileManagement;
import com.chanfinecloud.cfl.util.LogUtils;
import com.chanfinecloud.cfl.util.LynActivityManager;
import com.chanfinecloud.cfl.util.NetworkUtils;
import com.chanfinecloud.cfl.util.Utils;
import com.chanfinecloud.cfl.weidgt.ProgressDialogUtil;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.model.TakePhotoOptions;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.umeng.analytics.MobclickAgent;

import org.xutils.x;

import static com.chanfinecloud.cfl.ui.base.BaseHandler.HTTP_CANCEL;
import static com.chanfinecloud.cfl.ui.base.BaseHandler.HTTP_REQUEST;

/**
 * 此类描述的是:activity基类
 *
 * @author TanYong
 *         create at 2017/4/21 14:04
 */
public abstract class BaseTakePhotoActivity extends FragmentActivity implements TakePhoto.TakeResultListener, InvokeListener,NetBroadcastReceiver.NetEvent {

    //应用是否销毁标志
    protected boolean isDestroy;
    //防止重复点击设置的标志，涉及到点击打开其他Activity时，将该标志设置为false，在onResume事件中设置为true
    private boolean clickable = true;
    /**
     * @说明: 屏幕的宽度
     * @名称:mScreenWidth
     * @类型:int
     */
    protected int mScreenWidth;

    /**
     * @说明: 屏幕的高度
     * @名称:mScreenHeight
     * @类型:int
     */
    protected int mScreenHeight;

    /**
     * @说明: 屏幕的密度
     * @名称:mDensity
     * @类型:float
     */
    protected float mDensity;

    public boolean isNetConnect=true;
    protected static BaseHandler handler;

    private ProgressDialogUtil progressDialogUtil = null;

    public NetBroadcastReceiver netBroadcastReceiver;

    public LoginUserEntity loginUserEntity = FileManagement.getLoginUserEntity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        isDestroy = false;
        //设置无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //垂直显示
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        LynActivityManager.getInstance().pushActivity(this);
        mScreenWidth = metric.widthPixels;
        mScreenHeight = metric.heightPixels;
        Constants.SWidth = mScreenWidth;
        Constants.SHeight = mScreenHeight;
        mDensity = metric.density;
        //设置状态栏颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#2C3447"));
        }
       // x.view().inject(this);
        progressDialogUtil = new ProgressDialogUtil();
      //  x.view().inject(this);
//        EventBus.getDefault().register(this);
//        initView();
//        initEvent();
//        initRequestVo();
//        initCallBack();
//        initData();
//        setTitleView();

        initReceiver();//注册网络状态检测广播服务
        handler=new BaseHandler(this);//初始化BaseHandler

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

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        updateApp();
    }

    /**
     * @author TanYong
     * create at 2017/7/20 9:17
     * TODO：使用蒲公英进行版本更新
     */
    private void updateApp() {
//        new PgyUpdateManager.Builder()
//                .setForced(true)                //设置是否强制提示更新,非自定义回调更新接口此方法有用
//                .setUserCanRetry(false)         //失败后是否提示重新下载，非自定义下载 apk 回调此方法有用
//                .setDeleteHistroyApk(false)     // 检查更新前是否删除本地历史 Apk， 默认为true
//                .setUpdateManagerListener(new UpdateManagerListener() {
//                    @Override
//                    public void onNoUpdateAvailable() {
//                        //没有更新是回调此方法
//                        Log.d("pgyer", "there is no new version");
//                    }
//
//                    @Override
//                    public void onUpdateAvailable(AppBean appBean) {
//                        //有更新回调此方法
//                        Log.d("pgyer", "there is new version can update"
//                                + "new versionCode is " + appBean.getVersionCode());
//                        //调用以下方法，DownloadFileListener 才有效；
//                        //如果完全使用自己的下载方法，不需要设置DownloadFileListener
//                        PgyUpdateManager.downLoadApk(appBean.getDownloadURL());
//                    }
//
//                    @Override
//                    public void checkUpdateFailed(Exception e) {
//                        //更新检测失败回调
//                        //更新拒绝（应用被下架，过期，不在安装有效期，下载次数用尽）以及无网络情况会调用此接口
//                        Log.e("pgyer", "check update failed ", e);
//                    }
//                })
//                .setDownloadFileListener(new DownloadFileListener() {
//                    @Override
//                    public void downloadFailed() {
//                        //下载失败
//                        Log.e("pgyer", "download apk failed");
//                    }
//
//                    @Override
//                    public void downloadSuccessful(Uri uri) {
//                        Log.e("pgyer", "download apk failed");
//                        // 使用蒲公英提供的安装方法提示用户 安装apk
//                        PgyUpdateManager.installApk(uri);
//                    }
//
//                    @Override
//                    public void onProgressUpdate(Integer... integers) {
//                        Log.e("pgyer", "update download apk progress" + integers);
//                    }
//                })
//                .register();
//        PgyUpdateManager.register(this, "${applicationId}.fileprovider",
//                new UpdateManagerListener() {
//                    @Override
//                    public void onUpdateAvailable(final String result) {
//                        // 将新版本信息封装到AppBean中
//                        final AppBean appBean = getAppBeanFromString(result);
//                        new AlertDialog.Builder(BaseTakePhotoActivity.this)
//                                .setTitle("更新")
//                                .setMessage(appBean.getReleaseNote()).setCancelable(false)
//                                .setNegativeButton(
//                                        "确定",
//                                        new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialog, int which) {
//                                                startDownloadTask(BaseTakePhotoActivity.this, appBean.getDownloadURL());
//                                            }
//                                        }).show();
//                    }
//
//                    @Override
//                    public void onNoUpdateAvailable() {
//                    }
//                });
    }


//    /**
//     * 初始化布局
//     */
//    protected abstract void initView();

//    /**
//     * 初始化监听事件
//     */
//    protected void initEvent() {
//
//    }

//    /**
//     * 初始化RequestVo
//     */
//    protected abstract void initRequestVo();

//    /**
//     * 初始化回调函数
//     */
//    protected abstract void initCallBack();


    /**
     * 网络断开
     */
    public void onDisConnect() {

    }

//    /**
//     * 初始化数据
//     */
//    protected abstract void initData();


//    /**
//     * 设置标题栏
//     */
//    protected abstract void setTitleView();

    /**
     * @param pClass
     * @方法说明:启动指定activity
     * @方法名称:openActivity
     * @返回值:void
     */
    public void openActivity(Class<?> pClass) {
        openActivity(pClass, null);
    }

    /**
     * @param pClass
     * @param pBundle
     * @方法说明:启动到指定activity，Bundle传递对象（作用于2个界面之间传递数据）
     * @方法名称:openActivity
     * @返回值:void
     */
    public void openActivity(Class<?> pClass, Bundle pBundle) {
        Intent intent = new Intent(this, pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivity(intent);
    }

    /**
     * @param pAction
     * @方法说明:根据界面name启动到指定界面
     * @方法名称:openActivity
     * @返回值:void
     */
    public void openActivity(String pAction) {
        openActivity(pAction, null);
    }

    /**
     * @param pAction
     * @param pBundle
     * @方法说明:根据界面name启动到指定界面，Bundle传递对象（作用于2个界面之间传递数据）
     * @方法名称:openActivity
     * @返回值:void
     */
    public void openActivity(String pAction, Bundle pBundle) {
        Intent intent = new Intent(pAction);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivity(intent);
    }

    /**
     * @param pClass
     * @方法说明:A-Activity需要在B-Activtiy中执行一些数据操作，而B-Activity又要将，执行操作数据的结果返回给A-Activity
     * @方法名称:openActivityResult
     * @返回值:void
     */
    public void openActivityResult(Class<?> pClass) {
        openActivityResult(pClass, null);
    }

    /**
     * @param pClass
     * @param pBundle
     * @方法说明:A-Activity需要在B-Activtiy中执行一些数据操作， 而B-Activity又要将，执行操作数据的结果返回给A-Activity
     * ， Bundle传递对象（作用于2个界面之间传递数据）
     * @方法名称:openActivityResult
     * @返回值:void
     */
    public void openActivityResult(Class<?> pClass, Bundle pBundle) {
        openActivityResult(pClass, pBundle, 0);
    }

    /**
     * @param pClass
     * @param pBundle
     * @param requestCode
     * @方法说明:A-Activity需要在B-Activtiy中执行一些数据操作， 而B-Activity又要将，执行操作数据的结果返回给A-Activity
     * ， Bundle传递对象（作用于2个界面之间传递数据）
     * @方法名称:openActivityResult
     * @返回值:void
     */
    public void openActivityResult(Class<?> pClass, Bundle pBundle,
                                   int requestCode) {
        Intent intent = new Intent(this, pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * @param pResId
     * @方法说明:获取string资源字段土司
     * @方法名称:showShortToast
     * @返回值:void
     */
    public void showShortToast(int pResId) {
        showShortToast(getString(pResId));
    }

    /**
     * @param pMsg
     * @方法说明:长时间土司
     * @方法名称:showLongToast
     * @返回值:void
     */
    public void showLongToast(String pMsg) {
        Toast.makeText(this, pMsg, Toast.LENGTH_LONG).show();
    }

    /**
     * @param pMsg
     * @方法说明:短时间土司
     * @方法名称:showShortToast
     * @返回值:void
     */
    public void showShortToast(String pMsg) {
        Toast.makeText(this, pMsg, Toast.LENGTH_SHORT).show();
    }

    @SuppressWarnings("static-access")
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.KEYCODE_BACK == keyCode) {
            finishBase();
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * @方法说明:退出栈中所有Activity
     * @方法名称:finishBase
     * @返回值:void
     */
    public void finishBase() {
        /*if (CFLApplication.getInstance().threadPoolManager != null) {
            CFLApplication.getInstance().threadPoolManager.stopAllTask();
        }*/
        LynActivityManager.getInstance().popActivity(this);
    }

    /*
     * @重写方法名:onDestroy
     * @父类:@see android.support.v4.app.FragmentActivity#onDestroy()
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);
        isDestroy = true;
        if (progressDialogUtil != null) {
            progressDialogUtil.stopLoad();
            progressDialogUtil = null;
        }
//        releaseMemory();

        handler.sendEmptyMessage(HTTP_CANCEL);//取消http请求
        unregisterReceiver(netBroadcastReceiver);//注销广播服务
        LynActivityManager.getInstance().popActivity(this);//从栈中移除当前Activity
        finishBase();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);//友盟统计
    }


//    /**
//     * @方法说明:手动释放内存
//     * @方法名称:releaseMemory
//     * @返回值:void
//     */
//    public abstract void releaseMemory();

    /*
     * @重写方法名:onResume
     * @父类:@see android.support.v4.app.FragmentActivity#onResume()
     */
    @Override
    protected void onResume() {
        super.onResume();
        clickable = true;
        MobclickAgent.onResume(this);//友盟统计
        uiRefresh();
    }

    public void uiRefresh() {
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

    @SuppressLint("RestrictedApi")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void startActivityForResult(Intent intent, int requestCode, Bundle options) {
        if (isClickable()) {
            lockClick();
            super.startActivityForResult(intent, requestCode, options);
        }
    }

    protected void startProgressDialog(String msg) {
        if (progressDialogUtil != null) {
            progressDialogUtil.startLoad(this, msg, false);
        }
    }

    protected void startProgressDialog(String msg, boolean isClose) {
        if (progressDialogUtil != null) {
            progressDialogUtil.startLoad(this, msg, isClose);
        }
    }

    protected void stopProgressDialog() {
        if (progressDialogUtil != null) {
            progressDialogUtil.stopLoad();
        }
    }

    public TakePhoto takePhoto;
    private InvokeParam invokeParam;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
    }

    /**
     * 获取TakePhoto实例
     *
     * @return
     */
    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    /**
     * @author TanYong
     * create at 2017/7/4 11:51
     * TODO：设置拍照参数
     */
    public void configTakePhotoOption(TakePhoto takePhoto) {
        TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder();
        builder.setWithOwnGallery(true);//使用TakePhoto自带相册
        builder.setCorrectImage(true);//纠正拍照的照片旋转角度
        takePhoto.setTakePhotoOptions(builder.create());
    }

    /**
     * @author TanYong
     * create at 2017/7/4 11:51
     * TODO：配置图片压缩参数
     */
    public void configCompress(TakePhoto takePhoto) {
        int maxSize = 102400;
        int width = 800;
        int height = 960;
        boolean showProgressBar = true;//显示压缩进度条
        boolean enableRawFile = true;
        CompressConfig config = new CompressConfig.Builder()
                .setMaxSize(maxSize)
                .setMaxPixel(width >= height ? width : height)
                .enableReserveRaw(enableRawFile)//保留原图
                .create();
//        takePhoto.onEnableCompress(config, showProgressBar);
        takePhoto.onEnableCompress(null, false);
    }

    @Override
    public void takeFail(TResult result, String msg) {
        Utils.showPrompt(msg);
    }

    @Override
    public void takeCancel() {
        Utils.showPrompt("取消");
    }

//    public void onEvent(AnyEventType event) {
//        //接收消息
//    }

    /**
     * 显示Toast
     * @param content Toast文字
     */
    protected void showToast(String content){
        Toast.makeText(this,content, Toast.LENGTH_SHORT).show();
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
}

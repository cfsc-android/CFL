package com.chanfinecloud.cfl.ui.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.entity.TokenEntity;
import com.chanfinecloud.cfl.ui.LoginActivity;
import com.chanfinecloud.cfl.ui.base.BaseActivity;
import com.chanfinecloud.cfl.util.FileManagement;
import com.chanfinecloud.cfl.util.LynActivityManager;
import com.chanfinecloud.cfl.util.SharedPreferencesManage;
import com.chanfinecloud.cfl.util.Utils;
import com.chanfinecloud.cfl.weidgt.NoUnderlineSpan;
import com.pgyersdk.feedback.PgyerFeedbackManager;
import com.pgyersdk.update.DownloadFileListener;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;
import com.pgyersdk.update.javabean.AppBean;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.io.File;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by Shuaige on 2020/3/28.
 * Version: 1.0
 * Describe:  设置页Activity
 */
public class SettingActivity extends BaseActivity {
    @BindView(R.id.toolbar_tv_title)
    TextView toolbarTvTitle;
    @BindView(R.id.tv_setting_version)
    TextView tvSettingVersion;
    @BindView(R.id.ll_setting_logout_layer)
    LinearLayout llSettingLogoutLayer;
    @BindView(R.id.tv_setting_contact)
    TextView tvSettingContact;

    private ProgressDialog progressDialog;
    private AppBean _appBean;

    @Override
    protected void initData() {
        setContentView(R.layout.activity_settting);
        ButterKnife.bind(this);

        toolbarTvTitle.setText("设置");
        tvSettingVersion.setText(Utils.getCurrentVersion());
        initCheckVersion();
        NoUnderlineSpan mNoUnderlineSpan = new NoUnderlineSpan();
        if(tvSettingContact.getText() instanceof Spannable){
            Spannable s =(Spannable)tvSettingContact.getText();
            s.setSpan(mNoUnderlineSpan,0,s.length(), Spanned.SPAN_MARK_MARK);
        }
    }

    @OnClick({R.id.toolbar_btn_back,R.id.ll_setting_feedback,R.id.ll_setting_version,R.id.ll_setting_logout,
    R.id.ll_setting_logout_layer,R.id.tv_setting_logout_cancel,R.id.tv_setting_logout_quit,R.id.tv_setting_logout_change,
    R.id.ll_setting_notice,R.id.ll_setting_cache,R.id.ll_setting_about})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.toolbar_btn_back:
                finish();
                break;
            case R.id.ll_setting_feedback:
                feedback();
                break;
            case R.id.ll_setting_version:
                checkVersion();
                break;
            case R.id.ll_setting_logout:
                llSettingLogoutLayer.setVisibility(VISIBLE);
                llSettingLogoutLayer.setAnimation(alphaAnimation());
                break;
            case R.id.ll_setting_logout_layer:
                llSettingLogoutLayer.setVisibility(GONE);
                break;
            case R.id.tv_setting_logout_cancel:
                llSettingLogoutLayer.setVisibility(GONE);
                break;
            case R.id.tv_setting_logout_quit:
                exit();
                llSettingLogoutLayer.setVisibility(GONE);
                break;
            case R.id.tv_setting_logout_change:
                changeAccount();
                llSettingLogoutLayer.setVisibility(GONE);
                break;
            case R.id.ll_setting_notice:
                Bundle bundle=new Bundle();
                bundle.putString("type","0");
                bundle.putString("title","消息通知设置");
                startActivity(SettingDetailActivity.class,bundle);
                break;
            case R.id.ll_setting_cache:
                Bundle _bundle=new Bundle();
                _bundle.putString("type","1");
                _bundle.putString("title","缓存管理");
                startActivity(SettingDetailActivity.class,_bundle);
                break;
            case R.id.ll_setting_about:
                startActivity(SettingAboutActivity.class);
//                Bundle a_bundle=new Bundle();
//                a_bundle.putString("title","关于长房里");
//                a_bundle.putString("url",Constants.BASEHOST+"upload/notice_html/html/20190509/194402.html");
//                openActivity(NewsInfoActivity.class,a_bundle);
                break;
        }
    }

    /**
     * 退出
     */
    private void exit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
        builder.setTitle("温馨提示：");
        builder.setMessage("确定关闭应用？");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LynActivityManager.getInstance().removeAllActivity();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();  //创建AlertDialog对象
        dialog.show();
    }

    /**
     * 切换账号
     */
    private void changeAccount() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
        builder.setTitle("温馨提示：");
        builder.setMessage("确定切换用户？");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                loginOut();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();  //创建AlertDialog对象
        dialog.show();
    }

    private void thirdUnBind(SHARE_MEDIA shareMedia){
        UMShareAPI.get(SettingActivity.this).deleteOauth(SettingActivity.this, shareMedia, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                Log.d("SettingActivity", share_media.toString()+" onStart 授权开始");
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                Log.d("SettingActivity", share_media.toString()+i+" onComplete 授权完成");
                loginOut();
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                Log.d("SettingActivity", "onError 授权错误");
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                Log.d("SettingActivity", share_media.toString()+" onCancel 授权取消");
            }
        });
    }


    private void loginOut(){
//        FileManagement.setLoginType("");
//        FileManagement.setWXLogin(new WeiXinLoginEntity());
//        FileManagement.setQQLogin(new QQLoginEntity());
//        FileManagement.setBaseUser(new LoginUserEntity());
//        FileManagement.saveTokenInfo("");
//        FileManagement.setHikToken("");
//        FileManagement.saveJpushAlias("");
//        FileManagement.saveJpushTags("");
//        JPushInterface.setAliasAndTags(getApplicationContext(), "", null, null);
        //暂时隐藏---------------------------------------------------------
//        TokenEntity tokenEntity= SharedPreferencesManage.getToken();
//        tokenEntity.setExpires_in(0);
//        SharedPreferencesManage.saveToken(tokenEntity);
//        startActivity(LoginActivity.class);
//        LynActivityManager.getInstance().removeAllActivity();

        TokenEntity tokenEntity= SharedPreferencesManage.getToken();
        tokenEntity.setExpires_in(0);
        FileManagement.setTokenEntity(tokenEntity);
        LynActivityManager.getInstance().removeAllActivity();
        startActivity(LoginActivity.class);
    }

    /**
     * 意见反馈
     */
    private void feedback(){
        new PgyerFeedbackManager.PgyerFeedbackBuilder()
                .setShakeInvoke(false)       //fasle 则不触发摇一摇，最后需要调用 invoke 方法
                // true 设置需要调用 register 方法使摇一摇生效
                .setDisplayType(PgyerFeedbackManager.TYPE.DIALOG_TYPE)   //设置以Dialog 的方式打开
                .setColorDialogTitle("#FFFFFF")    //设置Dialog 标题的字体颜色，默认为颜色为#ffffff
                .setColorTitleBg("#2C3447")        //设置Dialog 标题栏的背景色，默认为颜色为#2E2D2D
                .setBarBackgroundColor("#FF0000")      // 设置顶部按钮和底部背景色，默认颜色为 #2E2D2D
                .setBarButtonPressedColor("#FF0000")        //设置顶部按钮和底部按钮按下时的反馈色 默认颜色为 #383737
                .setColorPickerBackgroundColor("#FF0000")   //设置颜色选择器的背景色,默认颜色为 #272828
                .setMoreParam("用户昵称", SharedPreferencesManage.getUserInfo().getName()) //自定义的反馈数据
                .setMoreParam("用户手机",SharedPreferencesManage.getUserInfo().getMobile()) //自定义的反馈数据
                .builder()
                .invoke();                  //激活直接显示的方式
    }

    /**
     * 获取新版本
     */
    private void initCheckVersion(){
        new PgyUpdateManager.Builder()
                .setForced(false)                //设置是否强制提示更新,非自定义回调更新接口此方法有用
                .setUserCanRetry(false)         //失败后是否提示重新下载，非自定义下载 apk 回调此方法有用
                .setDeleteHistroyApk(false)     // 检查更新前是否删除本地历史 Apk， 默认为true
                .setUpdateManagerListener(new UpdateManagerListener() {
                    @Override
                    public void onNoUpdateAvailable() {
                        //没有更新是回调此方法
                        Log.d("pgyer", "there is no new version");

                    }

                    @Override
                    public void onUpdateAvailable(final AppBean appBean) {
                        //有更新回调此方法
                        Log.d("pgyer", "there is new version can update"
                                + "new versionCode is " + appBean.getVersionCode());
                        //调用以下方法，DownloadFileListener 才有效；
                        //如果完全使用自己的下载方法，不需要设置DownloadFileListener
                        _appBean=appBean;

                    }

                    @Override
                    public void checkUpdateFailed(Exception e) {
                        //更新检测失败回调
                        //更新拒绝（应用被下架，过期，不在安装有效期，下载次数用尽）以及无网络情况会调用此接口
                        Log.e("pgyer", "check update failed ", e);
                    }
                })
                .setDownloadFileListener(new DownloadFileListener() {
                    @Override
                    public void downloadFailed() {
                        //下载失败
                        Log.e("pgyer", "download apk failed");
                    }

                    @Override
                    public void downloadSuccessful(File file) {
                        // 使用蒲公英提供的安装方法提示用户 安装apk
                        PgyUpdateManager.installApk(file);
                    }

                    @Override
                    public void onProgressUpdate(Integer... integers) {
                        Log.e("pgyer", "update download apk progress" + integers[0]);
                        initProgressDialog(integers[0]);
                    }
                })
                .register();
    }

    /**
     * 检测新版本
     */
    private void checkVersion(){
        if(_appBean!=null){
            if("99".equals(_appBean.getVersionCode())){
                new AlertDialog.Builder(SettingActivity.this)
                        .setTitle("强制更新")
                        .setMessage("发现新版本"+_appBean.getVersionName()+"\n"+_appBean.getReleaseNote())
                        .setCancelable(false)
                        .setNegativeButton(
                                "确认更新",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        PgyUpdateManager.downLoadApk(_appBean.getDownloadURL());
                                    }
                                }).show();
            }else{
                new AlertDialog.Builder(SettingActivity.this)
                        .setTitle("更新")
                        .setMessage("发现新版本"+_appBean.getVersionName()+"\n"+_appBean.getReleaseNote())
                        .setNegativeButton("确认更新",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                PgyUpdateManager.downLoadApk(_appBean.getDownloadURL());
                            }
                        }).
                        setNeutralButton("下次再说", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
            }

        }else{
            showToast("没有发现可更新版本");
        }
    }
    /**
     * 下载最新版进度
     * @param progress
     */
    private void initProgressDialog(int progress){
        if(progressDialog==null){
            progressDialog=new ProgressDialog(this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setCancelable(false);// 设置是否可以通过点击Back键取消
            progressDialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
            progressDialog.setTitle("下载最新版");
            progressDialog.setMax(100);
            progressDialog.setProgress(0);
            progressDialog.show();
        }else{
            progressDialog.setProgress(progress);
            if(progress==100){
                progressDialog.dismiss();
            }
        }
    }

    private AlphaAnimation alphaAnimation(){
        AlphaAnimation animation=new AlphaAnimation(0,1);
        animation.setDuration(300);
        return animation;
    }
}

package com.chanfinecloud.cfl.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.entity.BaseEntity;
import com.chanfinecloud.cfl.entity.TokenEntity;
import com.chanfinecloud.cfl.entity.smart.CurrentDistrictEntity;
import com.chanfinecloud.cfl.entity.smart.FileEntity;
import com.chanfinecloud.cfl.entity.smart.ResourceEntity;
import com.chanfinecloud.cfl.entity.smart.SmsKeyEntity;
import com.chanfinecloud.cfl.entity.smart.UserInfoEntity;
import com.chanfinecloud.cfl.http.HttpMethod;
import com.chanfinecloud.cfl.http.JsonParse;
import com.chanfinecloud.cfl.http.MyCallBack;
import com.chanfinecloud.cfl.http.RequestParam;
import com.chanfinecloud.cfl.ui.activity.NewsInfoActivity;
import com.chanfinecloud.cfl.ui.activity.ProjectSelectActivity;
import com.chanfinecloud.cfl.ui.activity.RegisterActivity;
import com.chanfinecloud.cfl.ui.base.BaseActivity;
import com.chanfinecloud.cfl.util.FileManagement;
import com.chanfinecloud.cfl.util.LogUtils;
import com.chanfinecloud.cfl.util.Utils;
import com.chanfinecloud.cfl.weidgt.EditTextDelView;
import com.chanfinecloud.cfl.weidgt.alertview.AlertView;
import com.google.gson.Gson;

import org.xutils.common.util.LogUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.chanfinecloud.cfl.config.Config.AUTH;
import static com.chanfinecloud.cfl.config.Config.BASE_URL;
import static com.chanfinecloud.cfl.config.Config.BASIC;
import static com.chanfinecloud.cfl.config.Config.CODETIME;
import static com.chanfinecloud.cfl.config.Config.FILE;
import static com.chanfinecloud.cfl.config.Config.SMS;
import static com.chanfinecloud.cfl.config.Config.USER;


public class LoginActivity extends BaseActivity {

    @BindView(R.id.et_user_mobile_code)
    EditText etUserMobileCode;
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.ll_umeng_login_weixin)
    LinearLayout llUmengLoginWeixin;
    @BindView(R.id.ll_umeng_login_qq)
    LinearLayout llUmengLoginQq;
    @BindView(R.id.tv_login_app_agreement)
    TextView tvLoginAppAgreement;
    @BindView(R.id.etd_user_mobile_number)
    EditTextDelView etdUserMobileNumber;

    private String mobileNum;//手机号码
    private String vcerificationCode;//验证码
    private Timer timer = new Timer();
    int recLen = CODETIME;
    private MyTimerTask task;

    private AlertView mAlertView;

    private String validKey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initData() {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        initStatus();
    }

    private void initStatus() {
        tvGetCode.setClickable(true);
    }

    /**
     * 定时器
     */
    class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {      // UI thread
                @Override
                public void run() {
                    tvGetCode.setClickable(false);
                    tvGetCode.setBackgroundResource(R.drawable.login_btn_yzm_hui);
                    tvGetCode.setText("");
                    tvGetCode.setText(recLen + "s");
                    recLen--;
                    if (recLen < 0) {
                        task.cancel();
                        tvGetCode.setText("获取验证码");
                        tvGetCode.setBackgroundResource(R.drawable.login_btn_yzm);
                        tvGetCode.setClickable(true);
                        recLen = CODETIME;
                    }
                }
            });
        }
    }

    @OnClick({R.id.tv_get_code, R.id.btn_login, R.id.tv_register, R.id.ll_umeng_login_weixin, R.id.ll_umeng_login_qq,
            R.id.tv_login_app_agreement})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_get_code:
                mobileNum = etdUserMobileNumber.getText().toString();
                if (mobileNum.trim().equals("")) {
                    Utils.showPrompt("请输入手机号码");
                } else {
                    checkUnique();//检查用户是否存在
                }
                break;
            case R.id.btn_login:
                mobileNum = etdUserMobileNumber.getText().toString();
                vcerificationCode = etUserMobileCode.getText().toString();
                if (Utils.isEmpty(mobileNum)) {
                    mAlertView = new AlertView("温馨提示", "手机号码不能为空",
                            null, new String[]{"知道了"}, null, LoginActivity.this, AlertView.Style.Alert, null).setCancelable(true);
                    mAlertView.show();
                } else if (Utils.isEmpty(vcerificationCode)) {
                    mAlertView = new AlertView("温馨提示", "验证码不能为空",
                            null, new String[]{"知道了"}, null, this, AlertView.Style.Alert, null).setCancelable(true);
                    mAlertView.show();
                } else {
                    login();
                }
                break;
            case R.id.tv_register:
                startActivity(RegisterActivity.class);
                break;
            case R.id.ll_umeng_login_weixin:
//                authorization(SHARE_MEDIA.WEIXIN);
                break;
            case R.id.ll_umeng_login_qq:
//                authorization(SHARE_MEDIA.QQ);
                break;
            case R.id.tv_login_app_agreement:
                Bundle bundle=new Bundle();
                bundle.putString("title","App使用协议");
                bundle.putString("url","http://dev.chanfine.com:9082/privacy/135310.html");
                startActivity(NewsInfoActivity.class,bundle);
                break;
        }
    }

    /**
     * 检查用户是否存在
     */
    private void checkUnique(){
        RequestParam requestParam=new RequestParam(BASE_URL+USER+"sys/check/unique",HttpMethod.Get);
        Map<String,Object> map=new HashMap<>();
        map.put("fieldName","mobile");
        map.put("fieldValue",mobileNum);
        map.put("tableName","`smart-basic`.cfc_household_info");
        requestParam.setRequestMap(map);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtil.d(result);
                BaseEntity baseEntity= JsonParse.parse(result);
                if(baseEntity.isSuccess()){
                    stopProgressDialog();
                    new AlertDialog.Builder(LoginActivity.this)
                            .setTitle("提示")
                            .setMessage("该号码不存在，去注册")
                            .setCancelable(false)
                            .setNegativeButton("确认",new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(RegisterActivity.class);
                                }
                            }).
                            setNeutralButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();

                }else{
                    sendSMS();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                showToast(ex.getMessage());
                stopProgressDialog();
            }
        });
        sendRequest(requestParam,true);
    }


    /**
     * 登录
     */
    private void login() {
        RequestParam requestParam=new RequestParam(BASE_URL+AUTH+"oauth/user/household/login", HttpMethod.Post);
        Map<String,Object> headerMap=new HashMap<>();
        headerMap.put("client_id","mobile");
        headerMap.put("client_secret","mobile");
        requestParam.setParamHeader(headerMap);
        Map<String,Object> map=new HashMap<>();
        map.put("mobile",mobileNum);
        map.put("key",validKey);
        map.put("validCode",vcerificationCode);
        requestParam.setRequestMap(map);
        requestParam.setAuthorization(false);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d("登录打印"+result);
                Gson gson = new Gson();
                TokenEntity token=gson.fromJson(result,TokenEntity.class);
                token.setInit_time(new Date().getTime()/1000);
                FileManagement.setTokenEntity(token);
                FileManagement.setPhone(mobileNum);
                getUserInfo();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                showToast(ex.getMessage());
                stopProgressDialog();
            }
        });
        sendRequest(requestParam,true);
    }


    /**
     * 获取用户信息
     */
    private void getUserInfo(){
        RequestParam requestParam = new RequestParam(BASE_URL+BASIC+"basic/householdInfo/currentHousehold", HttpMethod.Get);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d(result);
                BaseEntity<UserInfoEntity> baseEntity= JsonParse.parse(result, UserInfoEntity.class);
                if(baseEntity.isSuccess()){
                    FileManagement.setUserInfo(baseEntity.getResult());//缓存用户信息
                    CurrentDistrictEntity currentDistrict = baseEntity.getResult().getCurrentDistrict();
                    if(currentDistrict!=null && !TextUtils.isEmpty(currentDistrict.getProjectId())){
                        startActivity(MainActivity.class);
                    }else{
                        Bundle bundle=new Bundle();
                        bundle.putString("openFrom","Login");
                        startActivity(ProjectSelectActivity.class,bundle);
                    }
//                    if(!TextUtils.isEmpty(baseEntity.getResult().getAvatarId())){
//                        initAvatarResource(baseEntity.getResult().getAvatarId());
//                    }else{
//                        stopProgressDialog();
//                        startActivity(MainActivity.class);
//                    }
                }else{
                    stopProgressDialog();
                    showToast(baseEntity.getMessage());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                showToast(ex.getMessage());
                stopProgressDialog();
            }
        });
        sendRequest(requestParam,false);
    }


    /**
     * 缓存用户头像信息
     */
    private void initAvatarResource(String avatarId){
        RequestParam requestParam=new RequestParam(BASE_URL+FILE+"files/byid/"+avatarId, HttpMethod.Get);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d("result",result);
                BaseEntity<FileEntity> baseEntity= JsonParse.parse(result,FileEntity.class);
                if(baseEntity.isSuccess()){
                    ResourceEntity resourceEntity=new ResourceEntity();
                    resourceEntity.setId(baseEntity.getResult().getId());
                    resourceEntity.setContentType(baseEntity.getResult().getContentType());
                    resourceEntity.setCreateTime(baseEntity.getResult().getCreateTime());
                    resourceEntity.setName(baseEntity.getResult().getName());
                    resourceEntity.setUrl(baseEntity.getResult().getDomain()+baseEntity.getResult().getUrl());
                    FileManagement.setAvatarReseource(resourceEntity);//缓存用户头像信息
                }else{
                    showToast(baseEntity.getMessage());
                }
            }

            @Override
            public void onFinished() {
                super.onFinished();
                stopProgressDialog();
                startActivity(MainActivity.class);
            }
        });
        sendRequest(requestParam,false);
    }


    /**
     * 获取手机验证码
     */
    private void sendSMS() {
        RequestParam requestParam=new RequestParam(BASE_URL+SMS+"sms-internal/codes", HttpMethod.Post);
        Map<String,Object> map=new HashMap<>();
        map.put("phone",mobileNum);
        requestParam.setRequestMap(map);
        requestParam.setAuthorization(false);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtil.d(result);
                BaseEntity<SmsKeyEntity> baseEntity= JsonParse.parse(result, SmsKeyEntity.class);
                if(baseEntity.isSuccess()){
                    validKey = baseEntity.getResult().getKey();
                    etUserMobileCode.setText("");
                    etUserMobileCode.setFocusable(true);
                    etUserMobileCode.setFocusableInTouchMode(true);
                    etUserMobileCode.requestFocus();//获取焦点 光标出现
                    if (timer != null) {
                        timer = new Timer();
                        if (task != null) {
                            task.cancel();  //将原任务从队列中移除
                        }
                        task = new MyTimerTask();  // 新建一个任务
                        timer.schedule(task, 1000, 1000);
                    }
                }else{
                    showToast(baseEntity.getMessage());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                showToast(ex.getMessage());
            }

            @Override
            public void onFinished() {
                super.onFinished();
                stopProgressDialog();
            }
        });
        sendRequest(requestParam,false);
    }

}

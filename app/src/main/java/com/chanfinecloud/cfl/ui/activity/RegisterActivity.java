package com.chanfinecloud.cfl.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.entity.BaseEntity;
import com.chanfinecloud.cfl.entity.TokenEntity;
import com.chanfinecloud.cfl.entity.smart.SmsKeyEntity;
import com.chanfinecloud.cfl.entity.smart.UserInfoEntity;
import com.chanfinecloud.cfl.http.HttpMethod;
import com.chanfinecloud.cfl.http.JsonParse;
import com.chanfinecloud.cfl.http.MyCallBack;
import com.chanfinecloud.cfl.http.RequestParam;
import com.chanfinecloud.cfl.ui.LoginActivity;
import com.chanfinecloud.cfl.ui.MainActivity;
import com.chanfinecloud.cfl.ui.base.BaseActivity;
import com.chanfinecloud.cfl.util.FileManagement;
import com.chanfinecloud.cfl.util.LogUtils;
import com.chanfinecloud.cfl.util.LynActivityManager;
import com.chanfinecloud.cfl.util.Utils;
import com.google.gson.Gson;

import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.chanfinecloud.cfl.config.Config.AUTH;
import static com.chanfinecloud.cfl.config.Config.BASE_URL;
import static com.chanfinecloud.cfl.config.Config.BASIC;
import static com.chanfinecloud.cfl.config.Config.SMS;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.toolbar_btn_back)
    ImageButton toolbarBtnBack;
    @BindView(R.id.toolbar_tv_title)
    TextView toolbarTvTitle;
    @BindView(R.id.toolbar_tv_action)
    TextView toolbarTvAction;
    @BindView(R.id.toolbar_btn_action)
    ImageButton toolbarBtnAction;
    @BindView(R.id.toolbar_ll_view)
    LinearLayout toolbarLlView;
    @BindView(R.id.et_tel_no)
    EditText etTelNo;
    @BindView(R.id.tv_tel_get_ver)
    TextView tvTelGetVer;
    @BindView(R.id.et_tel_code)
    EditText etTelCode;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.ll_umeng_login_weixin)
    LinearLayout llUmengLoginWeixin;
    @BindView(R.id.ll_umeng_login_qq)
    LinearLayout llUmengLoginQq;
    @BindView(R.id.tv_login_app_agreement)
    TextView tvLoginAppAgreement;

    private boolean verFlag;
    private int verNum=0;
    private String validKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initData() {
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        toolbarTvTitle.setText("注册");
        etTelNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(Utils.isMobileNO(etTelNo.getText().toString())){
                    tvTelGetVer.setTextColor(getResources().getColor(R.color.payment_btn));
                    verFlag=true;
                }else{
                    tvTelGetVer.setTextColor(getResources().getColor(R.color.text_black));
                    verFlag=false;
                }
            }
        });

    }


    @SuppressLint("HandlerLeak")
    Handler handler=new Handler(){
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            if(msg.what==0){
                setVerFlag(false);
                verNum=60;
                handler.sendEmptyMessage(1);
            }else if(msg.what==1){
                tvTelGetVer.setText(verNum+"'s后重新获取");
                verNum--;
                if(verNum>0){
                    handler.sendEmptyMessageDelayed(1,1000);
                }else{
                    handler.removeMessages(1);
                    if(Utils.isMobileNO(etTelNo.getText().toString())){
                        setVerFlag(true);
                    }
                }
            }
        }
    };

    /**
     * 获取验证码状态切换
     * @param verFlag
     */
    private void setVerFlag(boolean verFlag){
        this.verFlag=verFlag;
        if(verFlag){
            if(verNum==0){
                tvTelGetVer.setText("获取验证码");
                tvTelGetVer.setTextColor(getResources().getColor(R.color.payment_btn));
            }
        }else{
            tvTelGetVer.setTextColor(getResources().getColor(R.color.text_black));
            if(verNum==0){
                tvTelGetVer.setText("获取验证码");
            }
        }
    }

    @OnClick({R.id.toolbar_btn_back, R.id.tv_tel_get_ver, R.id.btn_register, R.id.tv_login_app_agreement})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_btn_back:
                finish();
                break;
            case R.id.tv_tel_get_ver:
                if(verFlag){
                    sendSMS();
                }
                break;
            case R.id.btn_register:
                if(TextUtils.isEmpty(etTelNo.getText())){
                    showToast("手机号码不能为空");
                    return;
                }
                if(TextUtils.isEmpty(etTelCode.getText())){
                    showToast("验证码不能为空");
                    return;
                }
                telRegister();
                break;
            case R.id.tv_login_app_agreement:
                Bundle bundle=new Bundle();
                bundle.putString("title","App使用协议");
                bundle.putString("url","http://dev.chanfine.com:9082/privacy/135310.html");
                // TODO: 2020/3/28
                //startActivity(NewsInfoActivity.class,bundle);
                break;
        }
    }

    /**
     * 手机号注册
     */
    private void telRegister(){
        RequestParam requestParam = new RequestParam(BASE_URL+BASIC+"basic/householdInfo/register", HttpMethod.Post);
        Map<String,Object> map=new HashMap<>();
        map.put("loginMode","MOBILE");
        map.put("mobile",etTelNo.getText().toString());
        map.put("validKey",validKey);
        map.put("validCode",etTelCode.getText().toString());
        requestParam.setRequestMap(map);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtil.d(result);
                BaseEntity<String> baseEntity= JsonParse.parse(result,String.class);
                if(baseEntity.isSuccess()){
                    String userId= baseEntity.getResult();
                    login(userId);

                }else{
                    showToast(baseEntity.getMessage());

                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                showToast(ex.getMessage());
            }
        });
        sendRequest(requestParam,false);

    }
    //获取用户信息
    private void getUserInfo(String userId){
        RequestParam requestParam = new RequestParam(BASE_URL+BASIC+"basic/householdInfo/"+userId, HttpMethod.Get);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d(result);
                BaseEntity<UserInfoEntity> baseEntity= JsonParse.parse(result, UserInfoEntity.class);
                if(baseEntity.isSuccess()){
                    FileManagement.setUserInfo(baseEntity.getResult());//缓存用户信息
                    LynActivityManager.getInstance().finishActivity(LoginActivity.class);//杀掉登录界面
                    startActivity(MainActivity.class);//跳转到主界面
                    finish();//关闭当前页面
                }else{
                    showToast(baseEntity.getMessage());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
            }
        });

        sendRequest(requestParam, false);

    }


    /**
     * 注册完成直接登录
     * @param userId
     */
    private void login(final String userId) {
        RequestParam requestParam=new RequestParam(BASE_URL+AUTH+"oauth/user/household/login", HttpMethod.Post);

       /* params.addHeader("client_id","mobile");
        params.addHeader("client_secret","mobile");*/

        Map<String,Object> map=new HashMap<>();
        map.put("mobile",etTelNo.getText().toString());
        map.put("key",validKey);
        map.put("validCode",etTelCode.getText().toString());
        requestParam.setRequestMap(map);

        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d(result);
                Gson gson = new Gson();
                TokenEntity token=gson.fromJson(result, TokenEntity.class);
                FileManagement.setTokenEntity(token);
                getUserInfo(userId);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
            }
        });

        sendRequest(requestParam,false);


    }

    /**
     * 获取验证码
     */
    private void sendSMS() {
        RequestParam requestParam = new RequestParam(BASE_URL+SMS+"sms-internal/codes", HttpMethod.Post);
        Map<String,Object> requestMap=new HashMap<>();
        requestMap.put("phone",etTelNo.getText().toString());
        requestParam.setRequestMap(requestMap);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtil.d(result);
                BaseEntity<SmsKeyEntity> baseEntity= JsonParse.parse(result, SmsKeyEntity.class);
                if(baseEntity.isSuccess()){
                    validKey = baseEntity.getResult().getKey();
                    handler.sendEmptyMessage(0);
                }else{
                    showToast(baseEntity.getMessage());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                showToast(ex.getMessage());
            }
        });
        sendRequest(requestParam, false);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(1);
    }
}
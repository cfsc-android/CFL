package com.chanfinecloud.cfl.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.entity.BaseEntity;
import com.chanfinecloud.cfl.entity.ProjectInfo;
import com.chanfinecloud.cfl.entity.QQLoginEntity;
import com.chanfinecloud.cfl.entity.TokenEntity;
import com.chanfinecloud.cfl.entity.WeiXinLoginEntity;
import com.chanfinecloud.cfl.entity.core.Transition;
import com.chanfinecloud.cfl.entity.eventbus.EventBusMessage;
import com.chanfinecloud.cfl.entity.smart.OrderStatusEntity;
import com.chanfinecloud.cfl.entity.smart.OrderTypeListEntity;
import com.chanfinecloud.cfl.entity.smart.SmsKeyEntity;
import com.chanfinecloud.cfl.entity.smart.UserInfoEntity;
import com.chanfinecloud.cfl.http.HttpMethod;
import com.chanfinecloud.cfl.http.JsonParse;
import com.chanfinecloud.cfl.http.MyCallBack;
import com.chanfinecloud.cfl.http.RequestParam;
import com.chanfinecloud.cfl.ui.activity.RegisterActivity;
import com.chanfinecloud.cfl.ui.base.BaseActivity;
import com.chanfinecloud.cfl.util.Constants;
import com.chanfinecloud.cfl.util.FileManagement;
import com.chanfinecloud.cfl.util.LogUtils;
import com.chanfinecloud.cfl.util.Utils;
import com.chanfinecloud.cfl.weidgt.EditTextDelView;
import com.chanfinecloud.cfl.weidgt.alertview.AlertView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.chanfinecloud.cfl.config.Config.AUTH;
import static com.chanfinecloud.cfl.config.Config.BASE_URL;
import static com.chanfinecloud.cfl.config.Config.BASIC;
import static com.chanfinecloud.cfl.config.Config.SMS;
import static com.chanfinecloud.cfl.config.Config.WORKORDER;


public class LoginActivity extends BaseActivity {


    @BindView(R.id.btn_ip_setting)
    ImageView btnIpSetting;
    @BindView(R.id.tv_login_project)
    TextView tvLoginProject;
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
    @BindView(R.id.ll_login)
    LinearLayout llLogin;
    @BindView(R.id.etd_user_mobile_number)
    EditTextDelView etdUserMobileNumber;

    private String mobileNum;//手机号码
    private String vcerificationCode;//验证码
    private Timer timer = new Timer();
    int recLen = Constants.CODETIME;
    private MyTimerTask task;

    private AlertView mAlertView;
    private ProgressDialog progressDialog;

    private ProjectInfo projectInfo;
    private String validKey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initData() {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        projectInfo= FileManagement.getProjectInfo();
        if(projectInfo!=null){
            tvLoginProject.setText(projectInfo.getProjectName());
            Constants.BASEHOST=projectInfo.getProjectHost();
            Constants.HOST= Constants.BASEHOST+"api/";
        }
        EventBus.getDefault().register(this);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(EventBusMessage message){
        if("projectChange".equals(message.getMessage())){
            projectInfo= FileManagement.getProjectInfo();
            tvLoginProject.setText(projectInfo.getProjectName());
            Constants.BASEHOST=projectInfo.getProjectHost();
            Constants.HOST= Constants.BASEHOST+"api/";
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initStatus();
    }

    private void initStatus() {

        tvGetCode.setClickable(true);
    }


    class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            runOnUiThread(new Runnable() {      // UI thread
                @Override
                public void run() {
                    //tvGetCode.setOnClickListener(null);
                    tvGetCode.setClickable(false);
                    tvGetCode.setBackgroundResource(R.drawable.login_btn_yzm_hui);
                    tvGetCode.setText("");
                    tvGetCode.setText(recLen + "s");
                    recLen--;
                    if (recLen < 0) {
                        task.cancel();
                        tvGetCode.setText("获取验证码");
                        tvGetCode.setBackgroundResource(R.drawable.login_btn_yzm);
                        //tvGetCode.setOnClickListener(LoginActivity.this);
                        tvGetCode.setClickable(true);
                        recLen = Constants.CODETIME;
                    }
                }
            });
        }
    }
    /**
     * 点击跳转到主页
     */
    private void loginClick() {
        Bundle bundle = new Bundle();
        bundle.putString("title", "Main");
        startActivity(MainActivity.class, bundle, Transition.LeftIn);
    }

    @OnClick({R.id.btn_ip_setting, R.id.tv_login_project, R.id.tv_get_code, R.id.btn_login, R.id.tv_register, R.id.ll_umeng_login_weixin, R.id.ll_umeng_login_qq, R.id.tv_login_app_agreement})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_ip_setting:
                // TODO: 2020/3/28
                //startActivity(IpSettingActivity.class);
                break;
            case R.id.tv_login_project:
                // TODO: 2020/3/31  startActivity(ProjectListActivity.class);
                break;
            case R.id.tv_get_code:
                mobileNum = etdUserMobileNumber.getText().toString();
                if (mobileNum.trim().equals("")) {
                    Utils.showPrompt("请输入手机号码");
                } else {
                    //调用绑定手机短信接口
                    sendSMS();
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
//                } else if (!(Tools.isMobile(mobileNum))) {
//                    Tools.showPrompt("请输入正确的手机号");
                } else {
//                    startProgressDialog("");
                    login();
                }
// TODO: 2020/3/28记得删除  直接进去
               // loginClick();
                break;
            case R.id.tv_register:
                startActivity(RegisterActivity.class);
                break;
            case R.id.ll_umeng_login_weixin:
                showToast("待开发");
//                if(projectInfo==null){
//                    Toast.makeText(LoginActivity.this,"请选择小区",Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                authorization(SHARE_MEDIA.WEIXIN);
                break;
            case R.id.ll_umeng_login_qq:
                showToast("敬请期待");
//                if(projectInfo==null){
//                    Toast.makeText(LoginActivity.this,"请选择小区",Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                authorization(SHARE_MEDIA.QQ);
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
     * @author TanYong
     * create at 2017/4/21 15:57
     * TODO：登录
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
                initFileData();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                showToast(ex.getMessage());
            }
        });

        sendRequest(requestParam,false);

    }

    /**
     * 登录后初始用户数据
     */
    private void initFileData(){
        initOrderType();
        initOrderStatus();
        initComplainType();
        initComplainStatus();
        getUserInfo();
    }

    //初始化工单类型
    private void initOrderType(){
        RequestParam requestParam = new RequestParam(BASE_URL+WORKORDER+"work/orderType/pageByCondition", HttpMethod.Get);
        Map<String,String> requestMap=new HashMap<>();
        requestMap.put("pageNo","1");
        requestMap.put("pageSize","100");
        requestParam.setRequestMap(requestMap);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d(result);
                BaseEntity<OrderTypeListEntity> baseEntity= JsonParse.parse(result, OrderTypeListEntity.class);
                if(baseEntity.isSuccess()){
                    FileManagement.setOrderType(baseEntity.getResult().getData());
                    checkInitStatus(1);
                }else{
                    showToast(baseEntity.getMessage());
                    checkInitStatus(0);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                showToast(ex.getMessage());
                checkInitStatus(0);
            }
        });
        sendRequest(requestParam,false);
    }
    //初始化工单状态
    private void initOrderStatus(){

        RequestParam requestParam = new RequestParam(BASE_URL+WORKORDER+"work/orderStatus/selectWorkorderStatus", HttpMethod.Get);

        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d(result);
                BaseEntity baseEntity= JsonParse.parse(result);
                if(baseEntity.isSuccess()){
                    Type type = new TypeToken<List<OrderStatusEntity>>() {}.getType();
                    List<OrderStatusEntity> list= (List<OrderStatusEntity>) JsonParse.parseList(result,type);
                    FileManagement.setOrderStatus(list);
                    checkInitStatus(1);
                }else{
                    showToast(baseEntity.getMessage());
                    checkInitStatus(0);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                showToast(ex.getMessage());
                checkInitStatus(0);
            }
        });
        sendRequest(requestParam,false);


    }
    //初始化投诉类型
    private void initComplainType(){

        RequestParam requestParam = new RequestParam(BASE_URL+WORKORDER+"work/complaintType/pageByCondition", HttpMethod.Get);
        Map<String,String> requestMap=new HashMap<>();
        requestMap.put("pageNo","1");
        requestMap.put("pageSize","100");
        requestParam.setRequestMap(requestMap);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d(result);
                BaseEntity<OrderTypeListEntity> baseEntity= JsonParse.parse(result, OrderTypeListEntity.class);
                if(baseEntity.isSuccess()){
                    FileManagement.setComplainType(baseEntity.getResult().getData());
                    checkInitStatus(1);
                }else{
                    showToast(baseEntity.getMessage());
                    checkInitStatus(0);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                showToast(ex.getMessage());
                checkInitStatus(0);
            }
        });
        sendRequest(requestParam,false);

    }
    //初始化投诉状态
    private void initComplainStatus(){

        RequestParam requestParam = new RequestParam(BASE_URL+WORKORDER+"work/complaintStatus/complaintStatusList", HttpMethod.Get);
        Map<String,String> requestMap=new HashMap<>();
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d(result);
                BaseEntity baseEntity= JsonParse.parse(result);
                if(baseEntity.isSuccess()){
                    Type type = new TypeToken<List<OrderStatusEntity>>() {}.getType();
                    List<OrderStatusEntity> list= (List<OrderStatusEntity>) JsonParse.parseList(result,type);
                    FileManagement.setComplainStatus(list);
                    checkInitStatus(1);
                }else{
                    showToast(baseEntity.getMessage());
                    checkInitStatus(0);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                showToast(ex.getMessage());
                checkInitStatus(0);
            }
        });
        sendRequest(requestParam,false);

    }
    //获取用户信息
    private void getUserInfo(){

        RequestParam requestParam = new RequestParam(BASE_URL+BASIC+"basic/householdInfo/phone", HttpMethod.Get);
        Map<String,String> requestMap=new HashMap<>();
        requestMap.put("phoneNumber",FileManagement.getPhone());
        requestParam.setRequestMap(requestMap);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d(result);
                BaseEntity<UserInfoEntity> baseEntity= JsonParse.parse(result, UserInfoEntity.class);
                if(baseEntity.isSuccess()){
                    FileManagement.setUserInfo(baseEntity.getResult());//缓存用户信息
                    checkInitStatus(1);
                }else{
                    showToast(baseEntity.getMessage());
                    checkInitStatus(0);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                showToast(ex.getMessage());
                checkInitStatus(0);
            }
        });
        sendRequest(requestParam,false);
    }


    private List<Integer> initStatus=new ArrayList<>();

    private void checkInitStatus(int status){

        initStatus.add(status);

        LogUtils.d("初始结果打印"+initStatus.toString()+ "index0");
        /*if(initStatus.indexOf(0)==-1&&initStatus.size() ==5){
            startActivity(MainActivity.class);
            finish();
        }*/
        // TODO: 2020/3/28  改回上面的
        if(initStatus.size() >=5){
            startActivity(MainActivity.class);
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    /**
     * @author TanYong
     * create at 2017/4/21 13:36
     * TODO：获取手机验证码
     */
    private void sendSMS() {

        RequestParam requestParam=new RequestParam(BASE_URL+SMS+"sms-internal/codes", HttpMethod.Post);
        Map<String,Object> map=new HashMap<>();
        map.put("phone",mobileNum);
        requestParam.setRequestMap(map);
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
        });
        sendRequest(requestParam,false);
    }

    /**
     * 第三方授权登录
     * @param share_media
     */

    private void authorization(SHARE_MEDIA share_media){
        startProgressDialog("授权中...");
        UMShareAPI.get(LoginActivity.this).getPlatformInfo(LoginActivity.this, share_media, new UMAuthListener(){
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                Log.d("LoginActivity", share_media.toString()+" onStart 授权开始");
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                Log.d("LoginActivity", share_media.toString()+i+" onComplete 授权完成");
                StringBuilder str=new StringBuilder();
                str.append("{");
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    str.append("\""+entry.getKey()+"\":\""+entry.getValue()+"\",");
                }
                String thirdLogin= str.toString();
                thirdLogin=thirdLogin.substring(0,thirdLogin.length()-1);
                thirdLogin+="}";
                Gson gson=new Gson();
                if(SHARE_MEDIA.WEIXIN==share_media){
                    FileManagement.setLoginType("wx");
                    WeiXinLoginEntity weiXinLoginEntity=gson.fromJson(thirdLogin, WeiXinLoginEntity.class);
                    FileManagement.setWXLogin(weiXinLoginEntity);
                    queryThirdBind(weiXinLoginEntity.getUid(),"0");
                    Log.e("LoginActivity",weiXinLoginEntity.toString());
                }else if(SHARE_MEDIA.QQ==share_media){
                    FileManagement.setLoginType("qq");
                    QQLoginEntity qqLoginEntity=gson.fromJson(thirdLogin, QQLoginEntity.class);
                    FileManagement.setQQLogin(qqLoginEntity);
                    queryThirdBind(qqLoginEntity.getUid(),"1");
                    Log.d("LoginActivity", qqLoginEntity.toString());
                }

            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                Log.d("LoginActivity", "onError 授权错误");
                stopProgressDialog();
                showToast("授权错误");
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                Log.d("LoginActivity", share_media.toString()+" onCancel 授权取消");
                stopProgressDialog();
            }
        });

    }

    /**
     * 绑定第三方登录授权
     * @param uid
     * @param type
     */

    private void queryThirdBind(String uid,String type){
        final Map<String,String> requestMap=new HashMap<>();
        requestMap.put("accountId",uid);
        requestMap.put("type",type);
        // TODO: 2020/3/28
        /*XUtils.Get(Constants.HOST+"/queryPhoneNo.action",requestMap,new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                Log.e("result",result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if(jsonObject.getString("resultCode").equals("0")){
                        JSONObject data=jsonObject.getJSONObject("data");
                        FileManagement.saveTokenInfo(data.getString("token"));
                        Gson gson=new Gson();
                        LoginUserEntity loginUserEntity=gson.fromJson(data.getJSONObject("userInfo").toString(), LoginUserEntity.class);
                        FileManagement.setBaseUser(loginUserEntity);
                        Type room_type = new TypeToken<ArrayList<RoomInfoEntity>>() {}.getType();
                        ArrayList<RoomInfoEntity> roomInfoEntities=gson.fromJson(data.getJSONArray("roominfo").toString(),room_type);
                        FileManagement.saveRoomInfo(roomInfoEntities);
                        Type device_type = new TypeToken<ArrayList<DeviceInfoEntity>>() {}.getType();
                        ArrayList<DeviceInfoEntity> deviceInfoEntities=gson.fromJson(data.getJSONArray("deviceInfo").toString(),device_type);
                        FileManagement.setDeviceInfo(deviceInfoEntities);
                        Type third_type = new TypeToken<ArrayList<ThirdInfoEntity>>() {}.getType();
                        ArrayList<ThirdInfoEntity> thirdInfoEntities=gson.fromJson(data.getJSONArray("thirdInfo").toString(),third_type);
                        FileManagement.setThirdInfo(thirdInfoEntities);
                    }else{
                        FileManagement.saveTokenInfo("third");
                        showShortToast(jsonObject.getString("msg"));
                    }

                    openActivity(MainActivity.class);
                    LoginActivity.this.finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                showShortToast(ex.getMessage());
            }

            @Override
            public void onFinished() {
                super.onFinished();
                stopProgressDialog();
            }
        });*/
    }

}

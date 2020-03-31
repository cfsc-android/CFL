package com.chanfinecloud.cfl.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.entity.BaseEntity;
import com.chanfinecloud.cfl.entity.FileEntity;
import com.chanfinecloud.cfl.entity.eventbus.EventBusMessage;
import com.chanfinecloud.cfl.entity.smart.UserInfoEntity;
import com.chanfinecloud.cfl.http.HttpMethod;
import com.chanfinecloud.cfl.http.JsonParse;
import com.chanfinecloud.cfl.http.MyCallBack;
import com.chanfinecloud.cfl.http.ParamType;
import com.chanfinecloud.cfl.http.RequestParam;
import com.chanfinecloud.cfl.ui.base.BaseActivity;
import com.chanfinecloud.cfl.util.FileManagement;
import com.chanfinecloud.cfl.util.FilePathUtil;
import com.chanfinecloud.cfl.util.LogUtils;
import com.chanfinecloud.cfl.util.SharedPreferencesManage;
import com.chanfinecloud.cfl.view.wheelview.BirthWheelDialog;
import com.zhihu.matisse.Matisse;

import org.greenrobot.eventbus.EventBus;
import org.xutils.common.util.LogUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static com.chanfinecloud.cfl.config.Config.BASIC;
import static com.chanfinecloud.cfl.config.Config.BASE_URL;
import static com.chanfinecloud.cfl.config.Config.PHOTO_DIR_NAME;
import static com.chanfinecloud.cfl.config.Config.SD_APP_DIR_NAME;

public class PersonActivity extends BaseActivity {

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
    @BindView(R.id.person_iv_avatar)
    ImageView personIvAvatar;
    @BindView(R.id.person_tv_nick_name)
    TextView personTvNickName;
    @BindView(R.id.person_tv_tel)
    TextView personTvTel;
    @BindView(R.id.person_tv_sex)
    TextView personTvSex;
    @BindView(R.id.person_ll_sex)
    LinearLayout personLlSex;
    @BindView(R.id.person_tv_birthday)
    TextView personTvBirthday;
    @BindView(R.id.person_ll_birthday)
    LinearLayout personLlBirthday;
    @BindView(R.id.person_tv_tel_bind)
    TextView personTvTelBind;
    @BindView(R.id.person_ll_tel_bind)
    LinearLayout personLlTelBind;
    private BirthWheelDialog wheelDialog;
    private String sex;
    private String birthday;
    public static final int REQUEST_CODE_CHOOSE=0x001;
    private UserInfoEntity userInfoEntity;

    @Override
    protected void initData() {
        setContentView(R.layout.activity_person);
        ButterKnife.bind(this);
        toolbarTvTitle.setText("个人资料");
        init();
    }

    private void init(){
        userInfoEntity = FileManagement.getUserInfoEntity();
        personTvNickName.setText(userInfoEntity.getNickName()+"");
        personTvTel.setText(userInfoEntity.getMobile());
        sex = userInfoEntity.getGender();
        if (sex.equals("0")) {
            personTvSex.setText("男");
        } else if (sex.equals("1")) {
            personTvSex.setText("女");
        } else {
            personTvSex.setText("未知");
        }
        birthday = userInfoEntity.getBirthday();
        if (!TextUtils.isEmpty(birthday)) {
            personTvBirthday.setText(birthday.substring(0, 10));
        } else {
            personTvBirthday.setText("请填写出生日期");
        }

        if (!TextUtils.isEmpty(userInfoEntity.getAvatarResource())) {
            Glide.with(this)
                    .load(userInfoEntity.getAvatarResource())
                    .circleCrop()
                    .into(personIvAvatar);

        }
    }

    @OnClick({R.id.toolbar_btn_back, R.id.person_ll_birthday, R.id.person_ll_tel_bind, R.id.person_ll_sex})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_btn_back:
                finish();
                break;
            case R.id.person_ll_birthday:
                wheelDialog = new BirthWheelDialog(this, R.style.Dialog_Floating, new BirthWheelDialog.OnDateTimeConfirm() {
                    @Override
                    public void returnData(String dateText, String dateValue) {
                        Map<String,Object> map=new HashMap<>();
                        map.put("birthday",dateText+" 00:00:00");
                        updateUser(map);
                        wheelDialog.cancel();
                    }
                });
                wheelDialog.show();
                if(!TextUtils.isEmpty(birthday)){
                    wheelDialog.setBirth(userInfoEntity.getBirthday());
                }else{
                    wheelDialog.setBirth("2000-1-1");
                }
                break;
            case R.id.person_ll_tel_bind:
                showToast("待开发");
                break;
            case R.id.person_ll_sex:
                singleChoiceSex();
                break;

        }
    }

    /**
     * 选择男女
     */
    private void singleChoiceSex() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PersonActivity.this);
        builder.setTitle("请选择性别：");
        final String[] cities = {"男", "女"};
        int checkedItem;
        if ( sex.equals("0") ){
            checkedItem = 0;
        }else if (sex.equals("1")){
            checkedItem = 1;
        }else{
            checkedItem = -1;
        }

        builder.setSingleChoiceItems(cities, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sex = "which";
            }
        });
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Map<String,Object> map=new HashMap<>();
                map.put("gender",sex);
                updateUser(map);
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
     * 更新用户信息
     * @param map
     */
    private void updateUser(Map<String,Object> map){
        RequestParam requestParam=new RequestParam(BASE_URL + BASIC + "/basic/householdInfo",HttpMethod.Put);
        map.put("id", userInfoEntity.getId());
        requestParam.setRequestMap(map);
        requestParam.setParamType(ParamType.Json);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d("result",result);
                BaseEntity baseEntity= JsonParse.parse(result);
                if(baseEntity.isSuccess()){
                    getUserInfo();
                }else{
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
        sendRequest(requestParam,true);
    }


    //获取用户信息
    private void getUserInfo(){
        RequestParam requestParam=new RequestParam(BASE_URL + BASIC + "sys/user/",HttpMethod.Get);
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("phoneNumber", SharedPreferencesManage.getUserInfo().getId());
        requestParam.setRequestMap(requestMap);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d("result",result);
                BaseEntity<UserInfoEntity> baseEntity=JsonParse.parse(result,UserInfoEntity.class);
                if(baseEntity.isSuccess()){
                    SharedPreferencesManage.setUserInfo(baseEntity.getResult());
                    init();
                    EventBus.getDefault().post(new EventBusMessage<>("refresh"));
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_CHOOSE&&resultCode==RESULT_OK){
            //图片路径 同样视频地址也是这个 根据requestCode
            List<Uri> pathList = Matisse.obtainResult(data);
            List<String> _List = new ArrayList<>();
            for (Uri _Uri : pathList)
            {
                String _Path = FilePathUtil.getPathByUri(this,_Uri);
                File _File = new File(_Path);
                LogUtil.d("压缩前图片大小->" + _File.length() / 1024 + "k");
                _List.add(_Path);
            }
            compress(_List);
        }
    }

    //压缩图片
    private void compress(List<String> list){
        String _Path = FilePathUtil.createPathIfNotExist("/" + SD_APP_DIR_NAME + "/" + PHOTO_DIR_NAME);
        LogUtil.d("_Path->" + _Path);
        Luban.with(this)
                .load(list)
                .ignoreBy(100)
                .setTargetDir(_Path)
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                    }
                })
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        LogUtil.d(" 压缩开始前调用，可以在方法内启动 loading UI");
                    }

                    @Override
                    public void onSuccess(File file) {
                        LogUtil.d(" 压缩成功后调用，返回压缩后的图片文件");
                        LogUtil.d("压缩后图片大小->" + file.length() / 1024 + "k");
                        LogUtil.d("getAbsolutePath->" + file.getAbsolutePath());
                        uploadPic(file.getAbsolutePath());

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                }).launch();
    }

    //上传照片
    private void uploadPic(final String path){
        RequestParam requestParam=new RequestParam(BASE_URL+"file-manager-ms/files-anon",HttpMethod.Upload);
        Map<String,Object> map=new HashMap<>();
        map.put("UploadFile",new File(path));
        requestParam.setRequestMap(map);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d(result);
                BaseEntity<FileEntity> baseEntity= JsonParse.parse(result,FileEntity.class);
                if(baseEntity.isSuccess()){
                    Map<String,Object> map=new HashMap<>();
                    map.put("faceId",baseEntity.getResult().getId());
                    updateUser(map);
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

}

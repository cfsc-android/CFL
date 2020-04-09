package com.chanfinecloud.cfl.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.entity.BaseEntity;
import com.chanfinecloud.cfl.entity.FileEntity;
import com.chanfinecloud.cfl.entity.eventbus.EventBusMessage;
import com.chanfinecloud.cfl.entity.eventbus.NickNameEventBusData;
import com.chanfinecloud.cfl.entity.smart.ResourceEntity;
import com.chanfinecloud.cfl.entity.smart.UserInfoEntity;
import com.chanfinecloud.cfl.http.HttpMethod;
import com.chanfinecloud.cfl.http.JsonParse;
import com.chanfinecloud.cfl.http.MyCallBack;
import com.chanfinecloud.cfl.http.ParamType;
import com.chanfinecloud.cfl.http.RequestParam;
import com.chanfinecloud.cfl.ui.MainActivity;
import com.chanfinecloud.cfl.ui.base.BaseActivity;
import com.chanfinecloud.cfl.util.FileManagement;
import com.chanfinecloud.cfl.util.FilePathUtil;
import com.chanfinecloud.cfl.util.LogUtils;
import com.chanfinecloud.cfl.util.SharedPreferencesManage;
import com.chanfinecloud.cfl.weidgt.photopicker.PhotoPicker;
import com.chanfinecloud.cfl.weidgt.wheelview.BirthWheelDialog;
import com.google.gson.Gson;
import com.zhihu.matisse.Matisse;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
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
import static com.chanfinecloud.cfl.config.Config.FILE;

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
    private BirthWheelDialog wheelDialog;
    private String sex;
    private String birthday;
    public static final int REQUEST_CODE_CHOOSE=0x001;
    private UserInfoEntity userInfoEntity;
    private FileEntity fileEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        checkAppPermission();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initData() {
        setContentView(R.layout.activity_person);
        ButterKnife.bind(this);
        toolbarTvTitle.setText("个人资料");
        init();
        EventBus.getDefault().register(this);
    }

    private void init(){
        userInfoEntity = FileManagement.getUserInfo();

       if (!TextUtils.isEmpty(userInfoEntity.getNickName())) {
            personTvNickName.setText(userInfoEntity.getNickName());
        } else {
            personTvNickName.setText("请输入昵称");
        }
        personTvTel.setText(userInfoEntity.getMobile());
        sex = userInfoEntity.getGender();
        if(!TextUtils.isEmpty(sex) && sex.equals("0")){
            personTvSex.setText("男");
        } else if (!TextUtils.isEmpty(sex) && sex.equals("1")) {
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
        ResourceEntity avatar=FileManagement.getAvatarResource();
        if (avatar != null && !TextUtils.isEmpty(avatar.getUrl())) {
            Glide.with(this)
                    .load(avatar.getUrl())
                    .circleCrop()
                    .into(personIvAvatar);

        }
    }

    @OnClick({R.id.toolbar_btn_back, R.id.person_ll_birthday, R.id.person_ll_sex, R.id.person_iv_avatar, R.id.person_ll_nick_name})
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
            case R.id.person_ll_sex:
                singleChoiceSex();
                break;
            case R.id.person_iv_avatar:
                if(permission){
                    PhotoPicker.pick(PersonActivity.this,1,true,REQUEST_CODE_CHOOSE);
                }else{
                    showToast("相机或读写手机存储的权限被禁止！");
                }
                break;
            case R.id.person_ll_nick_name:
                startActivity(PersonNickNameActivity.class);
                break;
        }
    }

    /**
     * 编辑性别
     */
    private void editNickName() {
        final EditText et = new EditText(this);
        et.setHint(FileManagement.getUserInfo().getNickName());
        new AlertDialog.Builder(this).setTitle("请修改昵称")
                .setView(et)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //按下确定键后的事件
                        Toast.makeText(getApplicationContext(), et.getText().toString(), Toast.LENGTH_LONG).show();
                        Map<String,Object> map = new HashMap<>();
                        map.put("nickName",et.getText().toString());
                        updateUser(map);
                    }
                }).setNegativeButton("取消",null).show();

    }

    /**
     * 选择男女
     */
    private void singleChoiceSex() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PersonActivity.this);
        builder.setTitle("请选择性别：");
        final String[] cities = {"男", "女"};
        int checkedItem;
        if ( !TextUtils.isEmpty(sex) && sex.equals("0") ){
            checkedItem = 0;
        }else if (!TextUtils.isEmpty(sex) && sex.equals("1")){
            checkedItem = 1;
        }else{
            checkedItem = -1;
        }

        builder.setSingleChoiceItems(cities, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sex = Integer.toString(which);
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
        RequestParam requestParam=new RequestParam(BASE_URL + BASIC + "basic/householdInfo/specificField",HttpMethod.Put);
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

    /**
     * 获取用户信息
     */
    private void getUserInfo(){
        RequestParam requestParam = new RequestParam(BASE_URL+BASIC+"basic/householdInfo/currentHousehold", HttpMethod.Get);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d("result",result);
                BaseEntity<UserInfoEntity> baseEntity= JsonParse.parse(result, UserInfoEntity.class);
                if(baseEntity.isSuccess()){
                    FileManagement.setUserInfo(baseEntity.getResult());//缓存用户信息

                    if(!TextUtils.isEmpty(baseEntity.getResult().getAvatarId())){
                        initAvatarResource(baseEntity.getResult().getAvatarId());
                    }else{
                        init();
                        EventBus.getDefault().post(new EventBusMessage<>("refresh"));
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
                BaseEntity<com.chanfinecloud.cfl.entity.smart.FileEntity> baseEntity= JsonParse.parse(result, com.chanfinecloud.cfl.entity.smart.FileEntity.class);
                if(baseEntity.isSuccess()){
                    ResourceEntity resourceEntity=new ResourceEntity();
                    resourceEntity.setId(baseEntity.getResult().getId());
                    resourceEntity.setContentType(baseEntity.getResult().getContentType());
                    resourceEntity.setCreateTime(baseEntity.getResult().getCreateTime());
                    resourceEntity.setName(baseEntity.getResult().getName());
                    resourceEntity.setUrl(baseEntity.getResult().getDomain()+baseEntity.getResult().getUrl());
                    FileManagement.setAvatarReseource(resourceEntity);//缓存用户头像信息
                    init();
                    EventBus.getDefault().post(new EventBusMessage<>("refresh"));
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

    /**
     * 压缩图片
     * @param list 图片路径list
     */
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

    /**
     * 上传照片
     * @param path 图片路径
     */
    private void uploadPic(final String path){
        RequestParam requestParam=new RequestParam(BASE_URL+ FILE +"files-anon",HttpMethod.Upload);
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
                    map.put("avatarId",baseEntity.getResult().getId());
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(final EventBusMessage message){
        if("nickName".equals(message.getMessage())){
            final Map<String,Object> map=new HashMap<>();
            map.put("nickName",((NickNameEventBusData)message.getData()).getNickName());
            updateUser(map);
        }
    }

}

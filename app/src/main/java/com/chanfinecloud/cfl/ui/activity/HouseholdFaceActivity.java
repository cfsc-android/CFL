package com.chanfinecloud.cfl.ui.activity;

import android.Manifest;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.entity.BaseEntity;
import com.chanfinecloud.cfl.entity.eventbus.EventBusMessage;
import com.chanfinecloud.cfl.entity.eventbus.FaceCollectionEventBusData;
import com.chanfinecloud.cfl.entity.smart.FileEntity;
import com.chanfinecloud.cfl.entity.smart.ResourceEntity;
import com.chanfinecloud.cfl.entity.smart.RoomHouseholdEntity;
import com.chanfinecloud.cfl.http.HttpMethod;
import com.chanfinecloud.cfl.http.JsonParse;
import com.chanfinecloud.cfl.http.MyCallBack;
import com.chanfinecloud.cfl.http.RequestParam;
import com.chanfinecloud.cfl.http.XHttp;
import com.chanfinecloud.cfl.ui.base.BaseActivity;
import com.chanfinecloud.cfl.util.FileManagement;
import com.chanfinecloud.cfl.util.LogUtils;
import com.chanfinecloud.cfl.util.PermissionsUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.common.util.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.chanfinecloud.cfl.config.Config.BASE_URL;
import static com.chanfinecloud.cfl.config.Config.FILE;

public class HouseholdFaceActivity extends BaseActivity {

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
    @BindView(R.id.household_face_iv_pic)
    ImageView householdFaceIvPic;
    @BindView(R.id.household_face_tv_text)
    TextView householdFaceTvText;
    @BindView(R.id.household_face_btn_collection)
    Button householdFaceBtnCollection;

    private static final String[] permission={Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private boolean permissionFlag;

    private boolean edit;
    private RoomHouseholdEntity householdEntity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initData() {
        setContentView(R.layout.activity_household_face);
        ButterKnife.bind(this);

        edit=getIntent().getExtras().getBoolean("edit");
        if(!edit){
            householdFaceBtnCollection.setVisibility(View.GONE);
        }
        householdEntity= (RoomHouseholdEntity) getIntent().getExtras().getSerializable("household");
        if (householdEntity != null){
            toolbarTvTitle.setText(TextUtils.isEmpty(householdEntity.getNickName())?"人脸信息":householdEntity.getNickName());
            Log.e("HouseholdFaceActivity", "initData: "+ householdEntity.toString() );
        }

        initFaceResource();
        PermissionsUtils.getInstance().checkPermissions(this, permission, new PermissionsUtils.IPermissionsResult() {
            @Override
            public void success() {
                LogUtil.d("申请权限通过");
                permissionFlag=true;
            }

            @Override
            public void fail() {
                LogUtil.d("申请权限未通过");
                permissionFlag=false;
            }
        });
        EventBus.getDefault().register(this);
    }

    /**
     * 加载人脸
     */
    private void initFaceResource(){
        if(householdEntity!=null&&householdEntity.getFaceId()!=null){
            RequestParam requestParam=new RequestParam(BASE_URL+FILE+"files/byid/"+householdEntity.getFaceId(), HttpMethod.Get);
            requestParam.setCallback(new MyCallBack<String>(){
                @Override
                public void onSuccess(String result) {
                    super.onSuccess(result);
                    LogUtils.d(result);
                    BaseEntity<FileEntity> baseEntity= JsonParse.parse(result,FileEntity.class);
                    if(baseEntity.isSuccess()){
                        initFaceView(baseEntity.getResult());
                    }else{
                        showToast(baseEntity.getMessage());
                        initFaceView(null);
                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    super.onError(ex, isOnCallback);
                    showToast(ex.getMessage());
                    initFaceView(null);
                }

                @Override
                public void onFinished() {
                    super.onFinished();
                    stopProgressDialog();
                }
            });
            sendRequest(requestParam,true);
        }
    }

    /**
     * 加载人脸图片
     * @param fileEntity FileEntity
     */
    private void initFaceView(FileEntity fileEntity){
        if(fileEntity!=null){
            Glide.with(HouseholdFaceActivity.this)
                    .load(fileEntity.getDomain()+fileEntity.getUrl())
                    .circleCrop()
                    .error(R.drawable.ic_default_img)
                    .into(householdFaceIvPic);
            String createTime = fileEntity.getCreateTime();
            createTime=createTime.replace("T"," ");
            createTime=createTime.substring(0,19);
            householdFaceTvText.setText("上传时间:"+createTime);
            householdFaceBtnCollection.setText("重新上传");
        }else{
            Glide.with(HouseholdFaceActivity.this)
                    .load(R.drawable.icon_user_default)
                    .circleCrop()
                    .into(householdFaceIvPic);
            householdFaceTvText.setText("人脸照片尚未上传");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionsUtils.getInstance().onRequestPermissionsResult(this,requestCode,permissions,grantResults);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(EventBusMessage message){
        if("faceCollection".equals(message.getMessage())){
            householdFaceBtnCollection.setText("重新上传");
            householdFaceTvText.setText("上传时间:"+((FaceCollectionEventBusData)message.getData()).getFaceDisTime());
            Glide.with(this)
                    .load(((FaceCollectionEventBusData)message.getData()).getFaceDisUrl())
                    .circleCrop()
                    .error(R.drawable.ic_default_img)
                    .into(householdFaceIvPic);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.toolbar_btn_back, R.id.household_face_btn_collection})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_btn_back:
                finish();
                break;
            case R.id.household_face_btn_collection:
                if(permissionFlag){
                    Bundle bundle=new Bundle();
                    if (householdEntity != null){
                        bundle.putString("id",householdEntity.getId());
                        bundle.putString("name",householdEntity.getName());
                        bundle.putBoolean("update",householdEntity!=null && householdEntity.getFaceId() != null);
                        startActivity(FaceCollectionPhotoActivity.class,bundle);
                    }
                    else{
                        showToast("房屋信息为空！");
                    }

                }else{
                    showToast("相机或读写手机存储的权限被禁止！");
                }
                break;
        }
    }
}

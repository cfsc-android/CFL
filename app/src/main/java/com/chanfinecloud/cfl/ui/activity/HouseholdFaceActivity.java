package com.chanfinecloud.cfl.ui.activity;

import android.Manifest;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.entity.eventbus.EventBusMessage;
import com.chanfinecloud.cfl.entity.eventbus.FaceCollectionEventBusData;
import com.chanfinecloud.cfl.entity.smart.RoomHouseholdEntity;
import com.chanfinecloud.cfl.ui.base.BaseActivity;
import com.chanfinecloud.cfl.util.PermissionsUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.common.util.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
        toolbarTvTitle.setText(TextUtils.isEmpty(householdEntity.getNickName())?"人脸信息":householdEntity.getNickName());
        if(householdEntity.getFaceInfos()!=null&&householdEntity.getFaceInfos().size()>0){
            Glide.with(this)
                    .load(householdEntity.getFaceInfos().get(0).getUrl())
                    .circleCrop()
                    .error(R.drawable.ic_default_img)
                    .into(householdFaceIvPic);
            householdFaceTvText.setText(householdEntity.getFaceInfos().get(0).getCreateTime());
        }else{
            Glide.with(this)
                    .load(R.drawable.icon_user_default)
                    .circleCrop()
                    .into(householdFaceIvPic);
            householdFaceTvText.setText("人脸照片尚未上传");
        }


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
                    bundle.putString("id",householdEntity.getId());
                    bundle.putString("name",householdEntity.getName());
                    bundle.putBoolean("update",householdEntity.getFaceInfos()!=null&&householdEntity.getFaceInfos().size()>0);
                    startActivity(FaceCollectionPhotoActivity.class,bundle);
                }else{
                    showToast("相机或读写手机存储的权限被禁止！");
                }
                break;
        }
    }
}

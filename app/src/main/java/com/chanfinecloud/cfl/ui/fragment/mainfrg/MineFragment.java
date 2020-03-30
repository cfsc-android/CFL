package com.chanfinecloud.cfl.ui.fragment.mainfrg;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.entity.LoginUserEntity;
import com.chanfinecloud.cfl.entity.RoomInfoEntity;
import com.chanfinecloud.cfl.entity.eventbus.EventBusMessage;
import com.chanfinecloud.cfl.ui.activity.NewsInfoActivity;
import com.chanfinecloud.cfl.ui.activity.SettingActivity;
import com.chanfinecloud.cfl.ui.activity.WaitingForDevelopmentActivity;
import com.chanfinecloud.cfl.ui.base.BaseFragment;
import com.chanfinecloud.cfl.util.Constants;
import com.chanfinecloud.cfl.util.FileManagement;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Damien
 * 我的设置页
 */

public class MineFragment extends BaseFragment {


    @BindView(R.id.iv_mine_head)
    ImageView ivMineHead;
    @BindView(R.id.tv_mine_name)
    TextView tvMineName;
    @BindView(R.id.tv_mine_address)
    TextView tvMineAddress;
    @BindView(R.id.ll_mine_head)
    LinearLayout llMineHead;
    @BindView(R.id.tv_mine_gongdan)
    TextView tvMineGongdan;
    @BindView(R.id.tv_mine_tousu)
    TextView tvMineTousu;
    @BindView(R.id.tv_mine_car)
    TextView tvMineCar;
    @BindView(R.id.tv_mine_bill)
    TextView tvMineBill;
    @BindView(R.id.tv_mine_face)
    TextView tvMineFace;
    @BindView(R.id.tv_mine_express)
    TextView tvMineExpress;
    @BindView(R.id.tv_mine_evaluation)
    TextView tvMineEvaluation;
    @BindView(R.id.tv_mine_data)
    TextView tvMineData;
    @BindView(R.id.tv_mine_setting)
    TextView tvMineSetting;
    private Unbinder unbinder;

    private LoginUserEntity userEntity;
    private ArrayList<RoomInfoEntity> roomInfoEntity;

    private boolean bind;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind=true;
        EventBus.getDefault().register(this);


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(EventBusMessage message){
        if("headerImg".equals(message.getMessage())){
            userEntity=FileManagement.getLoginUserEntity();
            /*XUtilsImageUtils.display(ivMineHead,
                    Constants.BASEHOST+userEntity.getHeadImageUrl(),
                    true);*/
            Glide.with(this)
                    .load(Constants.BASEHOST+userEntity.getHeadImageUrl())
                    .error(R.drawable.ic_default_img)
                    .circleCrop()
                    .into(ivMineHead);
        }else if("bind".equals(message.getMessage())){
            Log.e("bind","Mine_bind");
            bind=true;
            initFaceData();
        }
    }

    private void initFaceData() {
        /** 测试人脸采集 zxl 2019-4-8 */
        if(!bind){
            if(FileManagement.getLoginType().equals("wx")){
                /*XUtilsImageUtils.display(ivMineHead,
                        FileManagement.getWXLogin().getIconurl(),
                        true);*/
                Glide.with(this)
                        .load(FileManagement.getWXLogin().getIconurl())
                        .error(R.drawable.ic_default_img)
                        .circleCrop()
                        .into(ivMineHead);
                /** 注释 zxl 2019-4-8 */
                tvMineName.setText(FileManagement.getWXLogin().getName());
            }else{
               /* XUtilsImageUtils.display(ivMineHead,
                        FileManagement.getQQLogin().getIconurl(),
                        true);*/
                Glide.with(this)
                        .load(FileManagement.getQQLogin().getIconurl())
                        .error(R.drawable.ic_default_img)
                        .circleCrop()
                        .into(ivMineHead);
                /** 注释 zxl 2019-4-8 */
                tvMineName.setText(FileManagement.getQQLogin().getName());
            }
            tvMineAddress.setText("未绑定业主，无法获取房屋信息");
            tvMineAddress.setTextColor(getResources().getColor(R.color.payment_btn));
        }else{
            userEntity = FileManagement.getLoginUserEntity();
            roomInfoEntity = FileManagement.getRoomInfo();
            if (userEntity != null) {
                /*XUtilsImageUtils.display(ivMineHead,
                        Constants.BASEHOST+userEntity.getHeadImageUrl(),
                        true);*/
                Glide.with(this)
                        .load(Constants.BASEHOST+userEntity.getHeadImageUrl())
                        .error(R.drawable.ic_default_img)
                        .circleCrop()
                        .into(ivMineHead);
                tvMineName.setText(userEntity.getNickName());
            }
            tvMineAddress.setText(FileManagement.getUserInfoEntity().getAncestor());
            tvMineAddress.setTextColor(getResources().getColor(R.color.white));
        }
    }

    @Override
    protected void initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_mine, null);
        setContentView(view);
        unbinder = ButterKnife.bind(this, view);

        if(FileManagement.getUserInfoEntity() != null){
            if (!TextUtils.isEmpty(FileManagement.getUserInfoEntity().getAvatarResource())){
                Glide.with(this)
                        .load(FileManagement.getUserInfoEntity().getAvatarResource())
                        .error(R.drawable.ic_default_img)
                        .circleCrop()
                        .into(ivMineHead);
            }

            tvMineName.setText(FileManagement.getUserInfoEntity().getNickName());
            tvMineAddress.setText(FileManagement.getUserInfoEntity().getAncestor());
            tvMineAddress.setTextColor(getResources().getColor(R.color.white));
        }

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.iv_mine_head, R.id.tv_mine_name, R.id.tv_mine_address, R.id.ll_mine_head, R.id.tv_mine_gongdan, R.id.tv_mine_tousu, R.id.tv_mine_car, R.id.tv_mine_bill, R.id.tv_mine_face, R.id.tv_mine_express, R.id.tv_mine_evaluation, R.id.tv_mine_data, R.id.tv_mine_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_mine_head:
                break;
            case R.id.tv_mine_name:
                break;
            case R.id.tv_mine_address:
                break;
            case R.id.ll_mine_head:
                break;
            case R.id.tv_mine_gongdan:
                break;
            case R.id.tv_mine_tousu:
                break;
            case R.id.tv_mine_car:
                break;
            case R.id.tv_mine_bill:
                break;
            case R.id.tv_mine_face:
                break;
            case R.id.tv_mine_express:
                Bundle a_bundle=new Bundle();
                a_bundle.putString("title","包裹查询");
                a_bundle.putString("url","https://m.kuaidi100.com/app/?coname=hao123");
                startActivity(NewsInfoActivity.class,a_bundle);
                break;
            case R.id.tv_mine_evaluation:
                startActivity(new Intent(getActivity(), WaitingForDevelopmentActivity.class).putExtra("title", "评价"));
                break;
            case R.id.tv_mine_data:
                break;
            case R.id.tv_mine_setting:
                startActivity(SettingActivity.class);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (100 == resultCode) {
            userEntity = FileManagement.getLoginUserEntity();
            tvMineName.setText(userEntity.getNickName());
            Glide.with(this)
                    .load(userEntity.getHeadImageUrl())
                    .error(R.drawable.ic_default_img)
                    .circleCrop()
                    .into(ivMineHead);
        }
    }
}

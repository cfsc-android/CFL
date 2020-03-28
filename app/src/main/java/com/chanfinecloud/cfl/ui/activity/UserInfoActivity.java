package com.chanfinecloud.cfl.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.ui.base.BaseActivity;
import com.chanfinecloud.cfl.util.SharedPreferencesManage;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserInfoActivity extends BaseActivity {

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
    @BindView(R.id.user_iv_avatar)
    ImageView userIvAvatar;
    @BindView(R.id.tv_user_nick_name)
    TextView tvUserNickName;
    @BindView(R.id.iv_user_nick_name_set)
    ImageView ivUserNickNameSet;
    @BindView(R.id.ll_user_nick_name)
    LinearLayout llUserNickName;
    @BindView(R.id.tv_user_tel)
    TextView tvUserTel;
    @BindView(R.id.iv_user_tel_set)
    ImageView ivUserTelSet;
    @BindView(R.id.tv_user_sex)
    TextView tvUserSex;
    @BindView(R.id.iv_user_sex_set)
    ImageView ivUserSexSet;
    @BindView(R.id.ll_user_sex)
    LinearLayout llUserSex;
    @BindView(R.id.tv_user_birth)
    TextView tvUserBirth;
    @BindView(R.id.iv_user_birth_set)
    ImageView ivUserBirthSet;
    @BindView(R.id.ll_user_birth)
    LinearLayout llUserBirth;
    @BindView(R.id.tv_user_tel_bind)
    TextView tvUserTelBind;
    @BindView(R.id.iv_user_tel_bind_set)
    ImageView ivUserTelBindSet;
    @BindView(R.id.ll_user_tel_bind)
    LinearLayout llUserTelBind;
    @BindView(R.id.ll_user_tel_bind_ll)
    LinearLayout llUserTelBindLl;
    @BindView(R.id.tv_user_address)
    TextView tvUserAddress;
    @BindView(R.id.ll_user_address)
    LinearLayout llUserAddress;
    @BindView(R.id.tv_user_in_date)
    TextView tvUserInDate;

    @Override
    protected void initData() {
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);
//        toolbarTvTitle.setText("个人资料");
//        userInfoEntity = SharedPreferencesManage.getUserInfo();
//        person_tv_name.setText(userInfoEntity.getUser().getUsername());
//        person_tv_depart.setText(userInfoEntity.getUser().getDepartment());
//        person_tv_no.setText(userInfoEntity.getUser().getWorkNo());
//        sex = userInfoEntity.getUser().getSex();
//        if (sex == 0) {
//            person_tv_gender.setText("男");
//        } else if (sex == 1) {
//            person_tv_gender.setText("女");
//        } else {
//            person_tv_gender.setText("未知");
//        }
//        birthday = userInfoEntity.getUser().getBirthday();
//        if (!TextUtils.isEmpty(birthday)) {
//            person_tv_birth.setText(birthday.substring(0, 10));
//        } else {
//            person_tv_birth.setText("请填写出生日期");
//        }
//        if (!TextUtils.isEmpty(userInfoEntity.getUser().getAvatarUrl())) {
//            Glide.with(this)
//                    .load(userInfoEntity.getUser().getAvatarUrl())
//                    .circleCrop()
//                    .into(person_iv_avatar);
//
//        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}

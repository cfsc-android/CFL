package com.chanfinecloud.cfl.ui.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.entity.eventbus.EventBusMessage;
import com.chanfinecloud.cfl.entity.eventbus.NickNameEventBusData;
import com.chanfinecloud.cfl.entity.smart.UserInfoEntity;
import com.chanfinecloud.cfl.ui.base.BaseActivity;
import com.chanfinecloud.cfl.util.FileManagement;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class PersonNickNameActivity extends BaseActivity {

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
    @BindView(R.id.et_nick_name)
    EditText etNickName;
    private UserInfoEntity userInfo;

    @Override
    protected void initData() {
        setContentView(R.layout.activity_person_nick_name);
        ButterKnife.bind(this);
        userInfo = FileManagement.getUserInfo();
        if (!TextUtils.isEmpty(userInfo.getNickName())) {
            etNickName.setText(userInfo.getNickName());
            etNickName.setSelection(userInfo.getNickName().length());
        }
        etNickName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                toolbarTvAction.setVisibility(VISIBLE);
                toolbarTvAction.setText("确定");
            }
        });
        toolbarTvTitle.setText("编辑昵称");
        toolbarTvAction.setVisibility(GONE);
    }

    @OnClick({R.id.toolbar_btn_back, R.id.toolbar_tv_action})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_btn_back:
                finish();
                break;
            case R.id.toolbar_tv_action:
                EventBusMessage<NickNameEventBusData> eventBusMessage = new EventBusMessage<>("nickName");
                eventBusMessage.setData(new NickNameEventBusData(etNickName.getText().toString()));
                EventBus.getDefault().post(eventBusMessage);
                finish();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}

package com.chanfinecloud.cfl.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.entity.ProjectInfo;
import com.chanfinecloud.cfl.entity.eventbus.EventBusMessage;
import com.chanfinecloud.cfl.ui.base.BaseActivity;
import com.chanfinecloud.cfl.util.FileManagement;
import com.chanfinecloud.cfl.util.Utils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IpSettingActivity extends BaseActivity {

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
    @BindView(R.id.et_ip_setting_name)
    EditText etIpSettingName;
    @BindView(R.id.et_ip_setting_address)
    EditText etIpSettingAddress;
    @BindView(R.id.et_ip_setting_ip)
    EditText etIpSettingIp;
    @BindView(R.id.et_ip_setting_tag)
    EditText etIpSettingTag;
    @BindView(R.id.btn_ip_setting_change)
    Button btnIpSettingChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initData() {
        setContentView(R.layout.activity_ip_setting);
        ButterKnife.bind(this);

        toolbarTvTitle.setText("自定义项目");
    }

    @OnClick({R.id.toolbar_btn_back, R.id.btn_ip_setting_change})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_btn_back:
                finish();
                break;
            case R.id.btn_ip_setting_change:
                if(Utils.isEmpty(etIpSettingName.getText().toString())){
                    showToast("项目名称不能为空");
                    return;
                }

                if(Utils.isEmpty(etIpSettingAddress.getText().toString())){
                    showToast("项目地址不能为空");
                    return;
                }

                if(Utils.isEmpty(etIpSettingIp.getText().toString())){
                    showToast("服务地址不能为空");
                    return;
                }
                FileManagement.setCustomerProject(new ProjectInfo(etIpSettingName.getText().toString(),
                        etIpSettingAddress.getText().toString(),
                        etIpSettingIp.getText().toString(),
                        etIpSettingTag.getText().toString()));
                EventBus.getDefault().post(new EventBusMessage<>("customerProject"));
                finish();
                break;
        }
    }

    @SuppressLint("HandlerLeak")
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1){

            }
        }
    };
}

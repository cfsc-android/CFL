package com.chanfinecloud.cfl.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.entity.BaseEntity;
import com.chanfinecloud.cfl.entity.eventbus.EventBusMessage;
import com.chanfinecloud.cfl.http.HttpMethod;
import com.chanfinecloud.cfl.http.JsonParse;
import com.chanfinecloud.cfl.http.MyCallBack;
import com.chanfinecloud.cfl.http.ParamType;
import com.chanfinecloud.cfl.http.RequestParam;
import com.chanfinecloud.cfl.ui.base.BaseActivity;
import com.chanfinecloud.cfl.util.FileManagement;
import com.chanfinecloud.cfl.util.LogUtils;
import com.chanfinecloud.cfl.util.LynActivityManager;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.chanfinecloud.cfl.config.Config.BASE_URL;
import static com.chanfinecloud.cfl.config.Config.BASIC;

public class HouseholdAuditActivity extends BaseActivity {

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
    @BindView(R.id.household_audit_card_no)
    EditText householdAuditCardNo;
    @BindView(R.id.household_audit_tel)
    EditText householdAuditTel;
    @BindView(R.id.household_audit_remark)
    EditText householdAuditRemark;
    @BindView(R.id.household_type_select_btn)
    Button householdTypeSelectBtn;

    private String roomId,type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initData() {
        setContentView(R.layout.activity_household_audit);
        ButterKnife.bind(this);
        toolbarTvTitle.setText("提交个人信息");
        roomId=getIntent().getExtras().getString("roomId");
        type=getIntent().getExtras().getString("type");


    }

    @OnClick({R.id.toolbar_btn_back, R.id.household_type_select_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_btn_back:
                finish();
                break;
            case R.id.household_type_select_btn:
                audit();
                break;
        }
    }

    private void audit() {
        if (TextUtils.isEmpty(householdAuditCardNo.getText())) {
            showToast("请填写身份证号");
            return;
        }
        if (TextUtils.isEmpty(householdAuditTel.getText())) {
            showToast("请填写手机号");
            return;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("idcardNo", householdAuditCardNo.getText().toString());
        map.put("mobile", householdAuditTel.getText().toString());
        map.put("remark", householdAuditRemark.getText().toString());
        map.put("roomId", roomId);
        map.put("type", type);
        map.put("householdId", FileManagement.getUserInfoEntity().getId());
        RequestParam requestParam = new RequestParam(BASE_URL + BASIC + "basic/verify", HttpMethod.Post);
        requestParam.setParamType(ParamType.Json);
        requestParam.setRequestMap(map);
        requestParam.setCallback(new MyCallBack<String>() {
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d(result);
                BaseEntity baseEntity = JsonParse.parse(result);
                if (baseEntity.isSuccess()) {
                    LynActivityManager.getInstance().finishActivity(HouseholdTypeSelectActivity.class);
                    LynActivityManager.getInstance().finishActivity(RoomSelectActivity.class);
                    LynActivityManager.getInstance().finishActivity(UnitSelectActivity.class);
                    EventBus.getDefault().post(new EventBusMessage<>("householdAudit"));
                    finish();
                } else {
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

}

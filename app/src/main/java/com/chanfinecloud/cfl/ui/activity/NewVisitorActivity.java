package com.chanfinecloud.cfl.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.entity.BaseEntity;
import com.chanfinecloud.cfl.entity.eventbus.EventBusMessage;
import com.chanfinecloud.cfl.entity.smart.QrCodeEntity;
import com.chanfinecloud.cfl.http.HttpMethod;
import com.chanfinecloud.cfl.http.JsonParse;
import com.chanfinecloud.cfl.http.MyCallBack;
import com.chanfinecloud.cfl.http.ParamType;
import com.chanfinecloud.cfl.http.RequestParam;
import com.chanfinecloud.cfl.ui.base.BaseActivity;
import com.chanfinecloud.cfl.util.FileManagement;
import com.chanfinecloud.cfl.util.LogUtils;
import com.chanfinecloud.cfl.util.Utils;
import com.chanfinecloud.cfl.weidgt.WheelDialog;
import com.chanfinecloud.cfl.weidgt.alertview.AlertView;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.chanfinecloud.cfl.config.Config.BASE_URL;
import static com.chanfinecloud.cfl.config.Config.IOT;

public class NewVisitorActivity extends BaseActivity {

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
    @BindView(R.id.et_new_visitor_name)
    EditText etNewVisitorName;
    @BindView(R.id.et_new_visitor_valid_start)
    EditText etNewVisitorValidStart;
    @BindView(R.id.et_new_visitor_valid_end)
    EditText etNewVisitorValidEnd;
    @BindView(R.id.et_new_visitor_valid_num)
    EditText etNewVisitorValidNum;
    @BindView(R.id.btn_new_visitor_create)
    Button btnNewVisitorCreate;

    private WheelDialog wheeldialog;
    private String valid_start,valid_end;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initData() {
        setContentView(R.layout.activity_new_visitor);
        ButterKnife.bind(this);
        
        toolbarTvTitle.setText("新邀请");

    }

    @OnClick({R.id.toolbar_btn_back, R.id.btn_new_visitor_create,R.id.et_new_visitor_valid_start,R.id.et_new_visitor_valid_end})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_btn_back:
                finish();
                break;
            case R.id.btn_new_visitor_create:
                String name = etNewVisitorName.getText().toString();
                String start = etNewVisitorValidStart.getText().toString();
                String end = etNewVisitorValidEnd.getText().toString();
                int num=4;
                if(!Utils.isEmpty(etNewVisitorValidNum.getText().toString())){
                    num=Integer.parseInt(etNewVisitorValidNum.getText().toString());
                }
                if (Utils.isEmpty(name)) {
                    new AlertView("温馨提示", Utils.getStringValue("请输入邀请人姓名"),
                            null, new String[]{"知道了"}, null, this, AlertView.Style.Alert, null).setCancelable(true).show();
                    return;
                }
                if (Utils.isEmpty(start)) {
                    new AlertView("温馨提示", Utils.getStringValue("请输入生效时间"),
                            null, new String[]{"知道了"}, null, this, AlertView.Style.Alert, null).setCancelable(true).show();
                    return;

                }
                if (Utils.isEmpty(end)) {
                    new AlertView("温馨提示", Utils.getStringValue("请输入失效时间"),
                            null, new String[]{"知道了"}, null, this, AlertView.Style.Alert, null).setCancelable(true).show();
                    return;
                }
                if (!Utils.isEmpty(compareDateTime(start,end))) {
                    new AlertView("温馨提示", Utils.getStringValue(compareDateTime(start,end)),
                            null, new String[]{"知道了"}, null, this, AlertView.Style.Alert, null).setCancelable(true).show();
                    return;
                }
                createNewVisitor(name,num,start,end);
                break;
            case R.id.et_new_visitor_valid_start:
                //etNewVisitorValidStart.setClickable(true);
                //etNewVisitorValidStart.setFocusableInTouchMode(true);
                wheeldialog = new WheelDialog(this, R.style.Dialog_Floating, new WheelDialog.OnDateTimeConfirm() {
                    @Override
                    public void returnData(String dateText, String dateValue) {
                        wheeldialog.cancel();
                        etNewVisitorValidStart.setText(dateText);
                        valid_start=dateValue;
                    }
                });
                wheeldialog.show();
                break;
            case R.id.et_new_visitor_valid_end:
                wheeldialog = new WheelDialog(this, R.style.Dialog_Floating, new WheelDialog.OnDateTimeConfirm() {
                    @Override
                    public void returnData(String dateText, String dateValue) {
                        wheeldialog.cancel();
                        etNewVisitorValidEnd.setText(dateText);
                        valid_end=dateValue;
                    }
                });
                wheeldialog.show();
                break;
        }
    }

    /**
     * 创建新的访问者数据
     * @param name
     * @param num
     * @param start
     * @param end
     */
    private void createNewVisitor(final String name, final int num, final String start, final String end) {
        RequestParam requestParam = new RequestParam(BASE_URL + IOT + "community/api/access/v1/qrcode/visitor", HttpMethod.Post);
        Map<String, Object> requestMap = new HashMap<>();
        if (FileManagement.getUserInfo() == null){
            showToast("用户信息获取失败");
            return;
        }
        requestMap.put("phaseId", FileManagement.getUserInfo().getRoomList().get(0).getPhaseId());
        requestMap.put("visitorName", name);
        requestMap.put("cardNo", FileManagement.getUserInfo().getDefaultCardNo());
        requestMap.put("effectTime", valid_start);
        requestMap.put("expireTime", valid_end);
        requestMap.put("openTimes", num);
        requestParam.setRequestMap(requestMap);
        requestParam.setParamType(ParamType.Json);
        requestParam.setCallback(new MyCallBack<String>() {
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d(result);
                BaseEntity<QrCodeEntity> baseEntity = JsonParse.parse(result, QrCodeEntity.class);
                if (baseEntity.isSuccess()) {
                    EventBus.getDefault().post(new EventBusMessage<>("visitorRefresh"));
                    Bundle bundle = new Bundle();
                    bundle.putString("qrCodeUrl", baseEntity.getResult().getQrCodeUrl());
                    bundle.putString("name", name);
                    bundle.putString("start", start);
                    bundle.putString("end", end);
                    bundle.putInt("num", num);
                    startActivity(VisitorQrCodeActivity.class, bundle);
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
    }

    /**
     * 比较生效时间的合理性
     * @param start
     * @param end
     * @return
     */
    private String compareDateTime(String start,String end){
        String err="";
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm" );
        try {
            Date start_date = sdf.parse(start);
            Date end_date=sdf.parse(end);
            if(start_date.getTime()>end_date.getTime()){
                err="失效时间不能小于生效时间";
            }
            if(end_date.getTime()-start_date.getTime()>48*60*60*1000){
                err="有效期不能大于48小时";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return err;
    }
}

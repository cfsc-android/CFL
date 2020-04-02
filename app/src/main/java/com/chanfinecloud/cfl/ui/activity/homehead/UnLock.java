package com.chanfinecloud.cfl.ui.activity.homehead;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.entity.BaseEntity;
import com.chanfinecloud.cfl.entity.smart.QrCodeEntity;
import com.chanfinecloud.cfl.http.HttpMethod;
import com.chanfinecloud.cfl.http.JsonParse;
import com.chanfinecloud.cfl.http.MyCallBack;
import com.chanfinecloud.cfl.http.ParamType;
import com.chanfinecloud.cfl.http.RequestParam;
import com.chanfinecloud.cfl.ui.base.BaseActivity;
import com.chanfinecloud.cfl.util.FileManagement;

import org.xutils.common.util.LogUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.chanfinecloud.cfl.config.Config.BASE_URL;
import static com.chanfinecloud.cfl.config.Config.IOT;

public class UnLock extends BaseActivity {


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
    @BindView(R.id.iv_qr_code)
    ImageView ivQrCode;
    @BindView(R.id.tv_refresh_qr)
    TextView tvRefreshQr;

    //private HikUser hikUser;
    @Override
    protected void initData() {
        setContentView(R.layout.activity_un_lock);
        ButterKnife.bind(this);
        //hikUser= FileManagement.getHikUser();
        toolbarTvTitle.setText("门禁开锁");
        getOpenDoorQrCode();

    }

    /**
     * 获取后台生成的开锁二维码图片
     */
    private void getOpenDoorQrCode() {

        RequestParam requestParam=new RequestParam(BASE_URL+IOT+"community/api/access/v1/qrcode/owner", HttpMethod.Get);

        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
        Map<String,Object> requestMap=new HashMap<>();
        if (FileManagement.getUserInfo() == null){
            showToast("用戶信息获取失败");
            return;
        }
        requestMap.put("cardNo",FileManagement.getUserInfo().getDefaultCardNo());
        requestMap.put("effectTime",sdf.format(new Date()));
        requestMap.put("expireTime",sdf.format(new Date(new Date().getTime()+5*60*1000)));
        requestMap.put("openTimes",4);
        requestMap.put("phaseId",FileManagement.getUserInfo().getRoomList().get(0).getPhaseId());
        requestParam.setRequestMap(requestMap);
        requestParam.setParamType(ParamType.Json);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtil.d(result);
                BaseEntity<QrCodeEntity> baseEntity= JsonParse.parse(result, QrCodeEntity.class);
                if(baseEntity.isSuccess()){
                    Glide.with(getApplicationContext()).load(baseEntity.getResult().getQrCodeUrl()).into(ivQrCode);
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

        sendRequest(requestParam, true);

    }


    @OnClick({R.id.toolbar_btn_back, R.id.tv_refresh_qr})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_btn_back:
                finish();
                break;
            case R.id.tv_refresh_qr:
                getOpenDoorQrCode();
                break;
        }
    }
}

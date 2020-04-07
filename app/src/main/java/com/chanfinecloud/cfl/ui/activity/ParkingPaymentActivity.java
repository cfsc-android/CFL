package com.chanfinecloud.cfl.ui.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.inputmethodservice.KeyboardView;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.entity.core.Transition;
import com.chanfinecloud.cfl.entity.eventbus.EventBusMessage;
import com.chanfinecloud.cfl.http.HttpMethod;
import com.chanfinecloud.cfl.http.MyCallBack;
import com.chanfinecloud.cfl.http.ParamType;
import com.chanfinecloud.cfl.http.RequestParam;
import com.chanfinecloud.cfl.ui.base.BaseActivity;
import com.chanfinecloud.cfl.util.LogUtils;
import com.chanfinecloud.cfl.weidgt.platenumberview.CarPlateNumberEditView;
import com.chanfinecloud.cfl.weidgt.platenumberview.PlateNumberKeyboardUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.chanfinecloud.cfl.config.Config.BASE_URL;
import static com.chanfinecloud.cfl.config.Config.IOT;

public class ParkingPaymentActivity extends BaseActivity {


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
    @BindView(R.id.cpn_edit)
    CarPlateNumberEditView cpnEdit;
    @BindView(R.id.btn_parking_payment_search)
    Button btnParkingPaymentSearch;
    @BindView(R.id.btn_parking_payment_search_mask)
    TextView btnParkingPaymentSearchMask;
    @BindView(R.id.tv_parking_car_in_time)
    TextView tvParkingCarInTime;
    @BindView(R.id.tv_parking_car_park_time)
    TextView tvParkingCarParkTime;
    @BindView(R.id.tv_parking_car_payment)
    TextView tvParkingCarPayment;
    @BindView(R.id.tv_parking_car_pay_amount)
    TextView tvParkingCarPayAmount;
    @BindView(R.id.btn_parking_payment)
    Button btnParkingPayment;
    @BindView(R.id.keyboard_view)
    KeyboardView keyboardView;
    private String billSyscode;
    private String supposeCost;
    private PlateNumberKeyboardUtil plateNumberKeyboardUtil;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void initData() {
        setContentView(R.layout.activity_parking_payment);
        ButterKnife.bind(this);

        toolbarTvTitle.setText("停车交费");
        cpnEdit.setOnPlateNumberValid(new CarPlateNumberEditView.OnPlateNumberValid() {
            @Override
            public void plateNumberValid(boolean valid) {
                if (valid) {
                    btnParkingPaymentSearchMask.setVisibility(View.GONE);
                } else {
                    btnParkingPaymentSearchMask.setVisibility(View.VISIBLE);
                }
            }
        });
        EventBus.getDefault().register(this);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        cpnEdit.setOnPlateNumberValid(new CarPlateNumberEditView.OnPlateNumberValid() {
//            @Override
//            public void plateNumberValid(boolean valid) {
//                if (valid) {
//                    btn_parking_payment_search_mask.setVisibility(View.GONE);
//                } else {
//                    btn_parking_payment_search_mask.setVisibility(View.VISIBLE);
//                }
//            }
//        });
//        EventBus.getDefault().register(this);
//        getFaceGroup();
//        addFace();
//        queryFace();
//        updateFace();
//        deleteFace();

    }

//
//    private void getParkingPayment_bak(String plateNo) {
//        Map<String, Object> requestDataMap = new HashMap<>();
//        requestDataMap.put("plateNo", plateNo);
//        ApiHttpResult.hikParkingPayment(this, requestDataMap, new HttpUtils.DataCallBack() {
//            @Override
//            public void callBack(Object o) {
//                HikBaseEntity hikBaseEntity = (HikBaseEntity) o;
//                if (hikBaseEntity.getCode().equals("0")) {
//                    btn_parking_payment.setVisibility(View.VISIBLE);
//                    HikParkingPayment hikParkingPayment = (HikParkingPayment) hikBaseEntity.getData();
//                    tv_parking_car_in_time.setText(IOSTimeTrans.trans(hikParkingPayment.getEnterTime()));
//                    tv_parking_car_park_time.setText(hikParkingPayment.getParkTime() + "分钟");
//                    tv_parking_car_payment.setText(hikParkingPayment.getSupposeCost() + "元");
//                    supposeCost = hikParkingPayment.getSupposeCost();
//                    tv_parking_car_pay_amount.setText(hikParkingPayment.getPaidCost() + "元");
//                    billSyscode = hikParkingPayment.getBillSyscode();
//                } else {
//                    btn_parking_payment.setVisibility(View.GONE);
//                    showShortToast("没有查到该车牌下账单信息");
//                }
//            }
//        });
//    }

    private void getParkingPayment(String plateNo) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("plateNo", plateNo);
        RequestParam requestParam = new RequestParam(BASE_URL+ IOT +"community/api/car/v1/pay/bill/", HttpMethod.Post);
        requestParam.setRequestMap(requestMap);
        requestParam.setParamType(ParamType.Json);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d(result);
//                HikIscEntity<HikParkingPayment> hikIscEntity = HikJsonParse.parse(result, HikParkingPayment.class);
//                if (hikIscEntity.getCode().equals("0")) {
//                    btnParkingPayment.setVisibility(View.VISIBLE);
//                    tvParkingCarInTime.setText(IOSTimeTrans.trans(hikIscEntity.getData().getEnterTime()));
//                    tvParkingCarParkTime.setText(hikIscEntity.getData().getParkTime() + "分钟");
//                    tvParkingCarPayment.setText(hikIscEntity.getData().getSupposeCost() + "元");
//                    supposeCost = hikIscEntity.getData().getSupposeCost();
//                    tvParkingCarPayAmount.setText(hikIscEntity.getData().getPaidCost() + "元");
//                    billSyscode = hikIscEntity.getData().getBillSyscode();
//                } else {
//                    btnParkingPayment.setVisibility(View.GONE);
//                    showToast("没有查到该车牌下账单信息");
//                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                showToast(ex.getMessage());
            }

        });
        sendRequest(requestParam, false);

    }

//    private void finishPayment_bak(final String actualCost) {
//        Map<String, Object> requestDataMap = new HashMap<>();
//        requestDataMap.put("billSyscode", billSyscode);
//        requestDataMap.put("actualCost", actualCost);
//        ApiHttpResult.hikFinishPayment(this, requestDataMap, new HttpUtils.DataCallBack() {
//            @Override
//            public void callBack(Object o) {
//                HikBaseEntity hikBaseEntity = (HikBaseEntity) o;
//                if (hikBaseEntity.getCode().equals("0")) {
//                    showShortToast("支付成功");
//                    tv_parking_car_pay_amount.setText(actualCost + "元");
//                    btn_parking_payment.setVisibility(View.GONE);
//                } else {
//                    showShortToast("支付失败");
//                }
//            }
//        });
//    }

    /**
     * 完成收费
     *
     * @param actualCost
     */
    private void finishPayment(final String actualCost) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("billSyscode", billSyscode);
        requestMap.put("actualCost", actualCost);

        RequestParam requestParam = new RequestParam(BASE_URL+ IOT +"community/api/car/v1/pay/receipt", HttpMethod.Post);
        requestParam.setRequestMap(requestMap);
        requestParam.setParamType(ParamType.Json);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d(result);
//                if (result.getCode().equals("0")) {
//                    showToast("支付成功");
//                    tvParkingCarPayAmount.setText(actualCost + "元");
//                    btnParkingPayment.setVisibility(View.GONE);
//                } else {
//                    showToast("支付失败");
//                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                showToast(ex.getMessage());
            }
            @Override
            public void onFinished() {
                super.onFinished();
            }

        });
        sendRequest(requestParam, false);
    }


    @OnClick({R.id.toolbar_btn_back, R.id.btn_parking_payment, R.id.cpn_edit, R.id.btn_parking_payment_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_btn_back:
                finish();
                break;
            case R.id.btn_parking_payment:
                Bundle bundle = new Bundle();
                bundle.putString("no", billSyscode);
                bundle.putString("count", supposeCost);
                startActivityForResult(PaymentTestActivity.class, bundle,100, Transition.RightIn);
                break;
            case R.id.cpn_edit:
                if (plateNumberKeyboardUtil == null) {
                    plateNumberKeyboardUtil = new PlateNumberKeyboardUtil(ParkingPaymentActivity.this, cpnEdit);
                    plateNumberKeyboardUtil.showKeyboard();
                } else {
                    plateNumberKeyboardUtil.showKeyboard();
                }
                break;
            case R.id.btn_parking_payment_search:
                if (btnParkingPaymentSearchMask.getVisibility() == View.GONE) {
                    getParkingPayment(cpnEdit.getPlateNumberText());
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            finishPayment(supposeCost);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(EventBusMessage message) {
        if ("paymentOk".equals(message.getMessage())) {
            finishPayment(supposeCost);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (plateNumberKeyboardUtil != null && plateNumberKeyboardUtil.isShow()) {
                plateNumberKeyboardUtil.hideKeyboard();
            } else {
                finish();
            }
        }
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (plateNumberKeyboardUtil != null && plateNumberKeyboardUtil.isShow()) {
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                Rect viewRect = new Rect();
                btnParkingPaymentSearchMask.getGlobalVisibleRect(viewRect);
                if (viewRect.contains((int) ev.getRawX(), (int) ev.getRawY())) {
                    if (btnParkingPaymentSearchMask.getVisibility() == View.GONE) {
                        getParkingPayment(cpnEdit.getPlateNumberText());
                    }
                    plateNumberKeyboardUtil.hideKeyboard();
                    return true;
                } else {
                    keyboardView.getGlobalVisibleRect(viewRect);
                    if (!viewRect.contains((int) ev.getRawX(), (int) ev.getRawY())) {
                        plateNumberKeyboardUtil.hideKeyboard();
                        return true;
                    }
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}

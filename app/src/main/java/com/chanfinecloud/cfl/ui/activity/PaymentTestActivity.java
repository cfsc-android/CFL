package com.chanfinecloud.cfl.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.entity.eventbus.EventBusMessage;
import com.chanfinecloud.cfl.ui.base.BaseActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class PaymentTestActivity extends BaseActivity {

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
    @BindView(R.id.tv_payment_test_no)
    TextView tvPaymentTestNo;
    @BindView(R.id.tv_payment_test_count)
    TextView tvPaymentTestCount;
    @BindView(R.id.iv_payment_test_wx_check)
    ImageView ivPaymentTestWxCheck;
    @BindView(R.id.ll_payment_test_wx)
    LinearLayout llPaymentTestWx;
    @BindView(R.id.iv_payment_test_ali_check)
    ImageView ivPaymentTestAliCheck;
    @BindView(R.id.ll_payment_test_ali)
    LinearLayout llPaymentTestAli;
    @BindView(R.id.iv_payment_test_union_check)
    ImageView ivPaymentTestUnionCheck;
    @BindView(R.id.ll_payment_test_union)
    LinearLayout llPaymentTestUnion;
    @BindView(R.id.btn_payment_test_pay)
    Button btnPaymentTestPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbarTvTitle.setText("支付订单");
        Bundle bundle = getIntent().getExtras();
        tvPaymentTestNo.setText(bundle.getString("no"));
        tvPaymentTestCount.setText(bundle.getString("count"));
    }

    @Override
    protected void initData() {
        setContentView(R.layout.activity_payment_test);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.toolbar_btn_back, R.id.btn_payment_test_pay, R.id.ll_payment_test_wx, R.id.ll_payment_test_ali, R.id.ll_payment_test_union})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_btn_back:
                finish();
                break;
            case R.id.btn_payment_test_pay:
                startProgressDialog("正在支付...");
                handler.sendEmptyMessageDelayed(1, 1000);
                break;
            case R.id.ll_payment_test_wx:
                ivPaymentTestWxCheck.setImageResource(R.drawable.payment_list_select);
                ivPaymentTestAliCheck.setImageResource(R.drawable.payment_list_normal);
                ivPaymentTestUnionCheck.setImageResource(R.drawable.payment_list_normal);
                break;
            case R.id.ll_payment_test_ali:
                ivPaymentTestWxCheck.setImageResource(R.drawable.payment_list_normal);
                ivPaymentTestAliCheck.setImageResource(R.drawable.payment_list_select);
                ivPaymentTestUnionCheck.setImageResource(R.drawable.payment_list_normal);
                break;
            case R.id.ll_payment_test_union:
                ivPaymentTestWxCheck.setImageResource(R.drawable.payment_list_normal);
                ivPaymentTestAliCheck.setImageResource(R.drawable.payment_list_normal);
                ivPaymentTestUnionCheck.setImageResource(R.drawable.payment_list_select);
                break;
        }
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                stopProgressDialog();
                EventBus.getDefault().post(new EventBusMessage<>("paymentOk"));
                finish();
            }
        }
    };
}

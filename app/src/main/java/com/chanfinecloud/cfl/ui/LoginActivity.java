package com.chanfinecloud.cfl.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.entity.core.Transition;
import com.chanfinecloud.cfl.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends BaseActivity {


    @BindView(R.id.login_tv_text)
    TextView loginTvText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initData() {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        loginTvText.setText("Login");
    }


    /**
     * 点击跳转到主页
     */
    private void loginClick() {
        Bundle bundle = new Bundle();
        bundle.putString("title", "Main");
        startActivity(MainActivity.class, bundle, Transition.LeftIn);
    }

    @OnClick(R.id.login_tv_text)
    public void onViewClicked() {

        loginClick();
    }
}

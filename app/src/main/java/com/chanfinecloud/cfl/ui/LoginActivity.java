package com.chanfinecloud.cfl.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.entity.core.Transition;
import com.chanfinecloud.cfl.ui.base.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity {
    @ViewInject(R.id.login_tv_text)
    private TextView login_tv_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        login_tv_text.setText("Login");
    }

    @Override
    protected void initData() {

    }


    @Event({R.id.login_tv_text})
    private void onClickEvent(View v){
        switch (v.getId()){
            case R.id.login_tv_text:
                loginClick();
                break;
        }
    }

    /**
     * 点击跳转到主页
     */
    private void loginClick(){
        Bundle bundle=new Bundle();
        bundle.putString("title","Main");
        startActivity(MainActivity.class,bundle, Transition.LeftIn);
    }
}

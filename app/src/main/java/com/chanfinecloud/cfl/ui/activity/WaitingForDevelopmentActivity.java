package com.chanfinecloud.cfl.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 此类描述的是:待开发activity
 *
 * @author TanYong
 * create at 2017/6/14 17:09
 */
public class WaitingForDevelopmentActivity extends BaseActivity {
    @BindView(R.id.toolbar_tv_title)
    TextView toolbarTvTitle;
    @BindView(R.id.toolbar_btn_back)
    ImageButton toolbarBtnBack;
    private String title;

    @Override
    protected void initData() {
        setContentView(R.layout.activity_waiting_for_development);
        ButterKnife.bind(this);
        title = getIntent().getStringExtra("title");
    }

    @OnClick({R.id.toolbar_btn_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_btn_back:
                finish();
                break;
            default:
                break;
        }
    }
}

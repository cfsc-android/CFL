package com.chanfinecloud.cfl.ui.activity;

import android.view.View;
import android.widget.TextView;

import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.ui.base.BaseActivity;
import com.chanfinecloud.cfl.util.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Shuaige on 2020/3/28.
 * Version: 1.0
 * Describe:  关于APP介绍Activity
 */
public class SettingAboutActivity extends BaseActivity {
    @BindView(R.id.toolbar_tv_title)
    TextView toolbarTvTitle;
    @BindView(R.id.tv_setting_about_version)
    TextView tvSettingAboutVersion;

    @Override
    protected void initData() {
        setContentView(R.layout.activity_setting_about);
        ButterKnife.bind(this);
        toolbarTvTitle.setText("关于长房里");
        tvSettingAboutVersion.setText("Version "+ Utils.getCurrentVersion());
    }

    @OnClick({R.id.toolbar_btn_back})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.toolbar_btn_back:
                finish();
                break;
        }
    }
}

package com.chanfinecloud.cfl.ui.activity;

import android.annotation.SuppressLint;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.chanfinecloud.cfl.ui.base.BaseActivity;
import com.chanfinecloud.cfl.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExpressSearchActivity extends BaseActivity {
    @BindView(R.id.toolbar_tv_title)
    TextView toolbarTvTitle;
    @BindView(R.id.wv_express_search)
    WebView wvExpressSearch;
    @SuppressLint("SetJavaScriptEnabled")

    @Override
    protected void initData() {
        setContentView(R.layout.activity_express_search);
        ButterKnife.bind(this);
        toolbarTvTitle.setText("包裹查询");
        wvExpressSearch.setWebViewClient(new WebViewClient(){});
        wvExpressSearch.getSettings().setJavaScriptEnabled(true);
        wvExpressSearch.loadUrl("https://m.kuaidi100.com/app/?coname=hao123");
    }

    @OnClick({R.id.toolbar_btn_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_btn_back:
                finish();
            break;
        }
    }
}

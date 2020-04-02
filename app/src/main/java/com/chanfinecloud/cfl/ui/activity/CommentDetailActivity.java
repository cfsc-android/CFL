package com.chanfinecloud.cfl.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CommentDetailActivity extends BaseActivity {

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
    @BindView(R.id.iv_second_hand_main_img)
    ImageView ivSecondHandMainImg;
    @BindView(R.id.tv_comment_month)
    TextView tvCommentMonth;
    @BindView(R.id.tv_comment_manage)
    TextView tvCommentManage;
    @BindView(R.id.tv_comment_team)
    TextView tvCommentTeam;

    @Override
    protected void initData() {
        setContentView(R.layout.activity_comment);
        ButterKnife.bind(this);
        toolbarTvTitle.setText(getIntent().getExtras().getString("title"));
        switch (getIntent().getExtras().getInt("img", 0)) {
            case 0:
                ivSecondHandMainImg.setImageResource(R.drawable.comment_month);
                break;
            case 1:
                ivSecondHandMainImg.setImageResource(R.drawable.comment_manage);
                break;
            case 2:
                ivSecondHandMainImg.setImageResource(R.drawable.comment_team);
                break;
        }
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

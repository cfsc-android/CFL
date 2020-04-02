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


public class CommentActivity extends BaseActivity {

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
        toolbarTvTitle.setText("评论");
        ivSecondHandMainImg.setImageResource(R.drawable.comment_index);
    }

    @OnClick({R.id.toolbar_btn_back, R.id.tv_comment_month, R.id.tv_comment_manage, R.id.tv_comment_team})
    public void onViewClicked(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.toolbar_btn_back:
                finish();
                break;
            case R.id.tv_comment_month:
                bundle.putString("title", "月度评价");
                bundle.putInt("img", 0);
                startActivity(CommentDetailActivity.class, bundle);
                break;
            case R.id.tv_comment_manage:
                bundle.putString("title", "管家评价");
                bundle.putInt("img", 1);
                startActivity(CommentDetailActivity.class, bundle);
                break;
            case R.id.tv_comment_team:
                bundle.putString("title", "服务团队");
                bundle.putInt("img", 2);
                startActivity(CommentDetailActivity.class, bundle);
                break;
        }
    }

}

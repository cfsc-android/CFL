package com.chanfinecloud.cfl.ui.activity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.adapter.smart.WorkflowStepAdapter;
import com.chanfinecloud.cfl.entity.smart.WorkflowProcessesEntity;
import com.chanfinecloud.cfl.ui.base.BaseActivity;
import com.chanfinecloud.cfl.util.LogUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WorkflowStepActivity extends BaseActivity {

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
    @BindView(R.id.workflow_step_rlv)
    RecyclerView workflowStepRlv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initData() {
        setContentView(R.layout.activity_workflow_step);
        ButterKnife.bind(this);

        toolbarTvTitle.setText("进度");
        if(getIntent().getExtras().getSerializable("workflowProcessesList")!=null){
            List<WorkflowProcessesEntity> data = (List<WorkflowProcessesEntity>) getIntent().getExtras().getSerializable("workflowProcessesList");
            if(data!=null){
                workflowStepRlv.setLayoutManager(new LinearLayoutManager(this));
                workflowStepRlv.setAdapter(new WorkflowStepAdapter(this,data));
                LogUtils.d("data:"+data.size());
            }
        }


    }

    @OnClick(R.id.toolbar_btn_back)
    public void onViewClicked() {

        finish();
    }
}

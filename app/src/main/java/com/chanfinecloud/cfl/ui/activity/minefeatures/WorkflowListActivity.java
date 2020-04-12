package com.chanfinecloud.cfl.ui.activity.minefeatures;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.adapter.smart.WorkflowListAdapter;
import com.chanfinecloud.cfl.entity.BaseEntity;
import com.chanfinecloud.cfl.entity.core.ListLoadingType;
import com.chanfinecloud.cfl.entity.eventbus.EventBusMessage;
import com.chanfinecloud.cfl.entity.smart.FinishStatusType;
import com.chanfinecloud.cfl.entity.smart.UserType;
import com.chanfinecloud.cfl.entity.smart.WorkflowEntity;
import com.chanfinecloud.cfl.entity.smart.WorkflowListEntity;
import com.chanfinecloud.cfl.entity.smart.WorkflowType;
import com.chanfinecloud.cfl.http.HttpMethod;
import com.chanfinecloud.cfl.http.JsonParse;
import com.chanfinecloud.cfl.http.MyCallBack;
import com.chanfinecloud.cfl.http.RequestParam;
import com.chanfinecloud.cfl.ui.activity.ComplainActivity;
import com.chanfinecloud.cfl.ui.activity.ComplainDetailActivity;
import com.chanfinecloud.cfl.ui.activity.RepairsActivity;
import com.chanfinecloud.cfl.ui.activity.RepairsDetailActivity;
import com.chanfinecloud.cfl.ui.base.BaseActivity;
import com.chanfinecloud.cfl.util.FileManagement;
import com.chanfinecloud.cfl.util.LogUtils;
import com.chanfinecloud.cfl.weidgt.RecyclerViewDivider;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.chanfinecloud.cfl.config.Config.BASE_URL;
import static com.chanfinecloud.cfl.config.Config.BASIC;
import static com.chanfinecloud.cfl.config.Config.WORKORDER;

public class WorkflowListActivity extends BaseActivity {

    @BindView(R.id.toolbar_btn_back)
    ImageButton toolbarBtnBack;
    @BindView(R.id.toolbar_tv_title)
    TextView toolbarTvTitle;
    @BindView(R.id.workflow_list_rlv)
    RecyclerView workflowListRlv;
    @BindView(R.id.workflow_list_srl)
    SmartRefreshLayout workflowListSrl;
    @BindView(R.id.toolbar_tv_action)
    TextView toolbarTvAction;
    @BindView(R.id.toolbar_btn_action)
    ImageButton toolbarBtnAction;
    @BindView(R.id.toolbar_ll_view)
    LinearLayout toolbarLlView;
    private WorkflowListAdapter adapter;
    private List<WorkflowEntity> data = new ArrayList<>();
    private ListLoadingType loadType;
    private int page = 1;
    private int pageSize = 10;
    private WorkflowType workflowType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initData() {
        setContentView(R.layout.activity_workflow_list);
        ButterKnife.bind(this);

        workflowType= (WorkflowType) getIntent().getExtras().getSerializable("workflowType");
        toolbarTvTitle.setText(workflowType.getTypeChs());

        toolbarTvAction.setVisibility(View.GONE);
        toolbarBtnAction.setImageResource(R.drawable.icon_btn_add);
        toolbarBtnAction.setVisibility(View.VISIBLE);


        adapter=new WorkflowListAdapter(this,data);
        workflowListRlv.setLayoutManager(new LinearLayoutManager(this));
        workflowListRlv.addItemDecoration(new RecyclerViewDivider(this, LinearLayoutManager.VERTICAL));
        workflowListRlv.setAdapter(adapter);
        workflowListRlv.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle=new Bundle();
                if("1".equals(workflowType.getType())){//工单
                    bundle.putString("order_id",data.get(position).getId());
                    startActivity(RepairsDetailActivity.class,bundle);
                }else if("2".equals(workflowType.getType())){//投诉
                    bundle.putString("complain_id",data.get(position).getId());
                    startActivity(ComplainDetailActivity.class,bundle);
                }
            }
        });

        workflowListSrl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                page=1;
                getData();
                loadType=ListLoadingType.Refresh;
            }
        });
        workflowListSrl.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                page++;
                getData();
                loadType=ListLoadingType.LoadMore;
            }
        });
        workflowListSrl.autoRefresh();
        EventBus.getDefault().register(this);


    }

    @OnClick({R.id.toolbar_btn_back, R.id.toolbar_btn_action})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_btn_back:
                finish();
                break;
            case R.id.toolbar_btn_action:
                if("1".equals(workflowType.getType())){//工单
                    startActivity(RepairsActivity.class);
                }else if("2".equals(workflowType.getType())){//投诉
                   startActivity(ComplainActivity.class);
                }
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(EventBusMessage message){
        if("WorkListRefresh".equals(message.getMessage())){
            workflowListSrl.autoRefresh();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);

    }

    private void getData(){
        Map<String,String> map=new HashMap<>();
        map.put("pageNo",page+"");
        map.put("pageSize",pageSize+"");
        map.put("type",workflowType.getType());
        map.put("userType", UserType.Household.getType()+"");
        map.put("userId", FileManagement.getUserInfo().getId() +"");
        map.put("projectId",FileManagement.getUserInfo().getCurrentDistrict().getProjectId());
        RequestParam requestParam = new RequestParam(BASE_URL+WORKORDER+"workflow/api/page", HttpMethod.Get);
        requestParam.setRequestMap(map);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d(result);
                BaseEntity<WorkflowListEntity> baseEntity= JsonParse.parse(result,WorkflowListEntity.class);
                if(baseEntity.isSuccess()){
                    if(page==1){
                        data.clear();
                    }
                    data.addAll(baseEntity.getResult().getData());
                    adapter.notifyDataSetChanged();
                    if(loadType==ListLoadingType.Refresh){
                        workflowListSrl.finishRefresh();
                        if(page*pageSize>=baseEntity.getResult().getCount()){
                            workflowListSrl.setNoMoreData(true);
                        }
                    }else{
                        if(page*pageSize>=baseEntity.getResult().getCount()){
                            workflowListSrl.finishLoadMoreWithNoMoreData();
                        }else{
                            workflowListSrl.finishLoadMore();
                        }
                    }

                }else{
                    if(loadType==ListLoadingType.Refresh){
                        workflowListSrl.finishRefresh();
                    }else{
                        page--;
                        workflowListSrl.finishLoadMore(false);
                    }
                    showToast(baseEntity.getMessage());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                if(loadType==ListLoadingType.Refresh){
                    workflowListSrl.finishRefresh();
                }else{
                    page--;
                    workflowListSrl.finishLoadMore(false);
                }
                showToast(ex.getMessage());
            }

        });
        sendRequest(requestParam, false);
        
    }
}

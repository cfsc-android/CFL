package com.chanfinecloud.cfl.ui.activity.homehead;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.adapter.smart.VisitorListAdapter;
import com.chanfinecloud.cfl.entity.BaseEntity;
import com.chanfinecloud.cfl.entity.core.ListLoadingType;
import com.chanfinecloud.cfl.entity.eventbus.EventBusMessage;
import com.chanfinecloud.cfl.entity.smart.VisitorEntity;
import com.chanfinecloud.cfl.entity.smart.VisitorListEntity;
import com.chanfinecloud.cfl.http.HttpMethod;
import com.chanfinecloud.cfl.http.JsonParse;
import com.chanfinecloud.cfl.http.MyCallBack;
import com.chanfinecloud.cfl.http.RequestParam;
import com.chanfinecloud.cfl.ui.activity.NewVisitorActivity;
import com.chanfinecloud.cfl.ui.activity.VisitorQrCodeActivity;
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
import static com.chanfinecloud.cfl.config.Config.IOT;

public class VisitorActivity extends BaseActivity {

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
    @BindView(R.id.visitor_srl)
    SmartRefreshLayout visitorSrl;
    @BindView(R.id.btn_visitor_add)
    Button btnVisitorAdd;
    @BindView(R.id.visitor_rlv)
    RecyclerView visitorRlv;

    private VisitorListAdapter adapter;
    private List<VisitorEntity> data=new ArrayList<>();
    private ListLoadingType loadType;
    private int page=1;
    private int pageSize=10;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initData() {
        setContentView(R.layout.activity_visitor);
        ButterKnife.bind(this);
        toolbarTvTitle.setText("访客邀约");

        adapter=new VisitorListAdapter(data);
        visitorRlv.setLayoutManager(new LinearLayoutManager(this));
        visitorRlv.addItemDecoration(new RecyclerViewDivider(this, LinearLayoutManager.VERTICAL));
        visitorRlv.setAdapter(adapter);
        visitorRlv.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                VisitorEntity visitor = data.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("qrCodeUrl", visitor.getQrcodeUrl());
                bundle.putString("name", visitor.getVisitorName());
                bundle.putString("start", visitor.getEffectTime());
                bundle.putString("end", visitor.getExpireTime());
                bundle.putInt("num",visitor.getOpenTimes());
                startActivity(VisitorQrCodeActivity.class,bundle);
            }
        });

        visitorSrl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                page=1;
                getData();
                loadType=ListLoadingType.Refresh;
            }
        });
        visitorSrl.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                page++;
                getData();
                loadType=ListLoadingType.LoadMore;
            }
        });
        visitorSrl.autoRefresh();
        //5秒后关闭刷新效果
        visitorSrl.finishRefresh(5000);


        EventBus.getDefault().register(this);
    }

    /**
     * 分页请求访问者列表
     */
    private void getData(){
        RequestParam requestParam = new RequestParam(BASE_URL+IOT+"community/api/access/v1/visitor/pages", HttpMethod.Get);
        Map<String,String> map=new HashMap<>();
        map.put("pageNo",page+"");
        map.put("pageSize",pageSize+"");
        if (FileManagement.getUserInfo() != null &&  FileManagement.getUserInfo().getRoomList() != null
                && FileManagement.getUserInfo().getRoomList().size() > 0
                && FileManagement.getUserInfo().getRoomList().get(0) != null)
            map.put("phaseId", FileManagement.getUserInfo().getRoomList().get(0).getPhaseId());
        //map.put("userId",FileManagement.getUserInfo().getId());
        //map.put("userId","a75d45a015c44384a04449ee80dc3503");

        requestParam.setRequestMap(map);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d(result);
                BaseEntity<VisitorListEntity> baseEntity= JsonParse.parse(result, VisitorListEntity.class);
                if(baseEntity.isSuccess()){
                    if(page==1){
                        data.clear();
                    }
                    data.addAll(baseEntity.getResult().getData());
                    adapter.notifyDataSetChanged();
                    if(loadType==ListLoadingType.Refresh){
                        visitorSrl.finishRefresh();
                        if(page*pageSize>=baseEntity.getResult().getCount()){
                            visitorSrl.setNoMoreData(true);
                        }
                    }else{
                        if(page*pageSize>=baseEntity.getResult().getCount()){
                            visitorSrl.finishLoadMoreWithNoMoreData();
                        }else{
                            visitorSrl.finishLoadMore();
                        }
                    }

                }else{
                    if(loadType==ListLoadingType.Refresh){
                        visitorSrl.finishRefresh();
                    }else{
                        page--;
                        visitorSrl.finishLoadMore(false);
                    }
                    showToast(baseEntity.getMessage());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                showToast(ex.getMessage());
            }
        });

        sendRequest(requestParam,false);

    }

     @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(EventBusMessage message){
        if("visitorRefresh".equals(message.getMessage())){
            visitorSrl.autoRefresh();
        }
    }


    @OnClick({R.id.toolbar_btn_back, R.id.btn_visitor_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_btn_back:
                finish();
                break;
            case R.id.btn_visitor_add:
                startActivity(NewVisitorActivity.class);
                break;
        }
    }
}

package com.chanfinecloud.cfl.ui.activity;

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
import com.chanfinecloud.cfl.adapter.smart.ProjectSelectAdapter;
import com.chanfinecloud.cfl.entity.BaseEntity;
import com.chanfinecloud.cfl.entity.core.ListLoadingType;
import com.chanfinecloud.cfl.entity.smart.KeyTitleEntity;
import com.chanfinecloud.cfl.entity.smart.RoomEntity;
import com.chanfinecloud.cfl.entity.smart.RoomListEntity;
import com.chanfinecloud.cfl.http.HttpMethod;
import com.chanfinecloud.cfl.http.JsonParse;
import com.chanfinecloud.cfl.http.MyCallBack;
import com.chanfinecloud.cfl.http.RequestParam;
import com.chanfinecloud.cfl.ui.base.BaseActivity;
import com.chanfinecloud.cfl.util.LogUtils;
import com.chanfinecloud.cfl.weidgt.RecyclerViewDivider;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.chanfinecloud.cfl.config.Config.BASE_URL;
import static com.chanfinecloud.cfl.config.Config.BASIC;

public class RoomSelectActivity extends BaseActivity {

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
    @BindView(R.id.room_select_label)
    TextView roomSelectLabel;
    @BindView(R.id.room_select_rlv)
    RecyclerView roomSelectRlv;
    @BindView(R.id.room_select_srl)
    SmartRefreshLayout roomSelectSrl;

    private ProjectSelectAdapter adapter;
    private List<KeyTitleEntity> data=new ArrayList<>();
    private String unitId,title;
    private ListLoadingType loadType;
    private int page=1;
    private int pageSize=10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initData() {
        setContentView(R.layout.activity_room_select);
        ButterKnife.bind(this);

        toolbarTvTitle.setText("选择房屋");
        unitId=getIntent().getExtras().getString("unitId");
        title=getIntent().getExtras().getString("title");
        roomSelectLabel.setText(title);
        adapter=new ProjectSelectAdapter(this,data);
        roomSelectRlv.setLayoutManager(new LinearLayoutManager(this));
        roomSelectRlv.addItemDecoration(new RecyclerViewDivider(this, LinearLayoutManager.VERTICAL));
        roomSelectRlv.setAdapter(adapter);
        roomSelectRlv.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle=new Bundle();
                bundle.putString("roomId",data.get(position).getKey());
                startActivity(HouseholdTypeSelectActivity.class,bundle);
            }
        });
        roomSelectSrl.setPrimaryColorsId(R.color.view_background,R.color.text_primary);
        roomSelectSrl.setRefreshHeader(new ClassicsHeader(this).setSpinnerStyle(SpinnerStyle.Translate));
        roomSelectSrl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                page=1;
                getData();
                loadType= ListLoadingType.Refresh;
            }
        });
        roomSelectSrl.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                page++;
                getData();
                loadType=ListLoadingType.LoadMore;
            }
        });
        roomSelectSrl.autoRefresh();
        getData();
    }

    private void getData(){
        Map<String,String> map=new HashMap<>();
        map.put("unitId",unitId);
        map.put("pageNo",page+"");
        map.put("pageSize",pageSize+"");
        RequestParam requestParam = new RequestParam(BASE_URL+BASIC+"basic/room/page", HttpMethod.Get);
        requestParam.setRequestMap(map);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d(result);
                BaseEntity<RoomListEntity> baseEntity= JsonParse.parse(result,RoomListEntity.class);
                if(baseEntity.isSuccess()){
                    if(page==1){
                        data.clear();
                    }
                    List<RoomEntity> list=baseEntity.getResult().getData();
                    for (int i = 0; i < list.size(); i++) {
                        data.add(new KeyTitleEntity(list.get(i).getName(),list.get(i).getId()));
                    }
                    adapter.notifyDataSetChanged();
                    if(loadType==ListLoadingType.Refresh){
                        roomSelectSrl.finishRefresh();
                        if(page*pageSize>=baseEntity.getResult().getCount()){
                            roomSelectSrl.setNoMoreData(true);
                        }
                    }else{
                        if(page*pageSize>=baseEntity.getResult().getCount()){
                            roomSelectSrl.finishLoadMoreWithNoMoreData();
                        }else{
                            roomSelectSrl.finishLoadMore();
                        }
                    }

                }else{
                    if(loadType== ListLoadingType.Refresh){
                        roomSelectSrl.finishRefresh();
                    }else{
                        page--;
                        roomSelectSrl.finishLoadMore(false);
                    }
                    showToast(baseEntity.getMessage());
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                showToast(ex.getMessage());
                if(loadType== ListLoadingType.Refresh){
                    roomSelectSrl.finishRefresh();
                }else{
                    page--;
                    roomSelectSrl.finishLoadMore(false);
                }
                showToast(ex.getMessage());
            }

        });
        sendRequest(requestParam, false);

    }



    @OnClick(R.id.toolbar_btn_back)
    public void onViewClicked() {
        finish();
    }
}

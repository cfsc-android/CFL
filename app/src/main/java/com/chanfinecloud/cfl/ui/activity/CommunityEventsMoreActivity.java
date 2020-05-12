package com.chanfinecloud.cfl.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.adapter.CommunityEventsListAdapter;
import com.chanfinecloud.cfl.adapter.EventsMoreListAdapter;
import com.chanfinecloud.cfl.entity.BaseEntity;
import com.chanfinecloud.cfl.entity.smart.EventsEntity;
import com.chanfinecloud.cfl.entity.smart.EventsListEntity;
import com.chanfinecloud.cfl.http.HttpMethod;
import com.chanfinecloud.cfl.http.JsonParse;
import com.chanfinecloud.cfl.http.MyCallBack;
import com.chanfinecloud.cfl.http.RequestParam;
import com.chanfinecloud.cfl.ui.base.BaseActivity;
import com.chanfinecloud.cfl.util.Utils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.xutils.common.util.LogUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.chanfinecloud.cfl.config.Config.ARTICLE;
import static com.chanfinecloud.cfl.config.Config.BASE_URL;

public class CommunityEventsMoreActivity extends BaseActivity {

    @BindView(R.id.toolbar_btn_back)
    ImageButton toolbarBtnBack;
    @BindView(R.id.toolbar_tv_title)
    TextView toolbarTvTitle;
    @BindView(R.id.toolbar_tv_action)
    TextView toolbarTvAction;
    @BindView(R.id.toolbar_btn_radio)
    CheckBox toolbarBtnRadio;
    @BindView(R.id.toolbar_btn_action)
    ImageButton toolbarBtnAction;
    @BindView(R.id.toolbar_ll_view)
    LinearLayout toolbarLlView;
    @BindView(R.id.events_more_recycle)
    RecyclerView eventsMoreRecycle;
    @BindView(R.id.events_more_smart)
    SmartRefreshLayout eventsMoreSmart;

    private EventsMoreListAdapter communityEventsListAdapter;
    private ArrayList<EventsEntity> eventsEntityArrayList = new ArrayList<>();
    private int pageNo = 1;
    private int pageSize = 10;
    private int pageCount = 1;
    private int freshType = 1;//1全部  2 我参加的

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    protected void initData() {
        setContentView(R.layout.activity_community_events_more);
        ButterKnife.bind(this);
        pageNo = 1;
        pageSize = 5;

        toolbarTvTitle.setText("");
        toolbarBtnRadio.setVisibility(View.VISIBLE);
        toolbarBtnRadio.setText("我参与的");
        toolbarBtnRadio.setTextColor(getResources().getColor(R.color.white));
        getEventsData();//

        toolbarBtnRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    toolbarBtnRadio.setTextColor(getResources().getColor(R.color.blue));
                    pageNo = 1;
                    freshType = 2;//我参与的
                    getEventsData();
                }else{
                    toolbarBtnRadio.setTextColor(getResources().getColor(R.color.white));
                    pageNo = 1;
                    freshType = 1;//全部的
                    getEventsData();

                }
            }
        });
        communityEventsListAdapter = new EventsMoreListAdapter(this, eventsEntityArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        eventsMoreRecycle.setLayoutManager(linearLayoutManager);
        eventsMoreRecycle.setAdapter(communityEventsListAdapter);

        eventsMoreRecycle.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

                String id = eventsEntityArrayList.get(position).getId();
                Bundle bundle = new Bundle();
                bundle.putString("eventsID", id);
                startActivity(CommunityEventsDetailActivity.class, bundle);
            }
        });

        eventsMoreRecycle.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (Utils.isVisBottom(eventsMoreRecycle)){

                    if (pageNo < pageCount){
                        pageNo++;
                        getEventsData();
                    }


                }
            }
        });

        eventsMoreSmart.setEnableLoadMore(false);
        eventsMoreSmart.setEnableRefresh(true);
        eventsMoreSmart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                pageNo = 1;
                getEventsData();
                eventsMoreSmart.finishRefresh(3000);
            }
        });



    }

    @OnClick(R.id.toolbar_btn_back)
    public void onViewClicked() {

        finish();
    }

    /**
     * 获取社区活动列表
     * freshType 1 全部类型  2 我参与的
     */
    private void getEventsData() {

        startProgressDialog(true);
        RequestParam requestParam = new RequestParam(BASE_URL + ARTICLE + "smart/event/page", HttpMethod.Get);
        Map<String, String> map = new HashMap<>();
        map.put("pageNo", pageNo+"");
        map.put("pageSize", pageSize+"");
        map.put("isClosed", "0");
        if (freshType == 2){
            // 我参加的
            map.put("isParticipate", "1");
        }
        requestParam.setRequestMap(map);
        requestParam.setCallback(new MyCallBack<String>() {
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtil.d(result);
                BaseEntity<EventsListEntity> baseEntity = JsonParse.parse(result, EventsListEntity.class);
                if (baseEntity.isSuccess()) {
                    if (pageNo == 1)
                        eventsEntityArrayList.clear();

                    pageCount = baseEntity.getResult().getCount();
                    pageCount = new Double(Math.ceil(pageCount/pageSize)).intValue();
                    eventsEntityArrayList.addAll(baseEntity.getResult().getData());
                    communityEventsListAdapter.notifyDataSetChanged();

                } else {
                    showToast(baseEntity.getMessage());
                }

                stopProgressDialog();

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                showToast(ex.getMessage());
            }

            @Override
            public void onFinished() {
                super.onFinished();
                stopProgressDialog();
            }
        });

        sendRequest(requestParam, false);

    }
}

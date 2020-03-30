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
import com.chanfinecloud.cfl.adapter.NoticeListAdapter;
import com.chanfinecloud.cfl.entity.BaseEntity;
import com.chanfinecloud.cfl.entity.NoticeEntity;
import com.chanfinecloud.cfl.entity.core.ListLoadingType;
import com.chanfinecloud.cfl.entity.smart.NoticeListEntity;
import com.chanfinecloud.cfl.entity.smart.NoticeReceiverType;
import com.chanfinecloud.cfl.entity.smart.NoticeType;
import com.chanfinecloud.cfl.http.HttpMethod;
import com.chanfinecloud.cfl.http.JsonParse;
import com.chanfinecloud.cfl.http.MyCallBack;
import com.chanfinecloud.cfl.http.RequestParam;
import com.chanfinecloud.cfl.ui.base.BaseActivity;
import com.chanfinecloud.cfl.util.LogUtils;
import com.chanfinecloud.cfl.view.RecyclerViewDivider;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
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
import static com.chanfinecloud.cfl.config.Config.ARTICLE;

/**
* 此类描述的是:通知公告activity
*
* @author Shuaige
* create at 2020/3/30
*/
public class NoticeActivity extends BaseActivity {

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
    @BindView(R.id.notice_rlv)
    RecyclerView noticeRlv;
    @BindView(R.id.notice_srl)
    SmartRefreshLayout noticeSrl;
    private NoticeListAdapter adapter;
    private List<NoticeEntity> data = new ArrayList<>();
    private ListLoadingType loadType;
    private int page = 1;
    private int pageSize = 10;

    @Override
    protected void initData() {
        setContentView(R.layout.activity_notice);
        ButterKnife.bind(this);
        String noticeType = getIntent().getExtras().getString("notice_type");
        String noticeName = NoticeType.getName(noticeType);
        toolbarTvTitle.setText(noticeName);
        adapter = new NoticeListAdapter(this, data);
        noticeRlv.setLayoutManager(new LinearLayoutManager(this));
        noticeRlv.addItemDecoration(new RecyclerViewDivider(this, LinearLayoutManager.VERTICAL));
        noticeRlv.setAdapter(adapter);
        noticeRlv.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("title", noticeName);
                bundle.putString("noticeId", data.get(position).getId());
                startActivity(NoticeDetailActivity.class, bundle);
            }
        });

        noticeSrl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                page = 1;
                getData();
                loadType = ListLoadingType.Refresh;
            }
        });
        noticeSrl.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                page++;
                getData();
                loadType = ListLoadingType.LoadMore;
            }
        });
        noticeSrl.autoRefresh();
    }

    private void getData() {
        RequestParam requestParam=new RequestParam(BASE_URL + ARTICLE +"smart/content/pages", HttpMethod.Get);
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("projectId", "ec93bb06f5be4c1f19522ca78180e2i9");
        requestMap.put("receiver", NoticeReceiverType.全部.getType() + "," + NoticeReceiverType.业主.getType());
        requestMap.put("announcementTypeId", getIntent().getExtras().getString("notice_type"));
        requestMap.put("auditStatus", "1");
        requestMap.put("pageNo", page + "");
        requestMap.put("pageSize", pageSize + "");
        requestParam.setRequestMap(requestMap);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d("result", result);
                BaseEntity<NoticeListEntity> baseEntity = JsonParse.parse(result, NoticeListEntity.class);
                if (baseEntity.isSuccess()) {
                    if (page == 1) {
                        data.clear();
                    }
                    data.addAll(baseEntity.getResult().getData());
                    adapter.notifyDataSetChanged();
                    if (loadType == ListLoadingType.Refresh) {
                        noticeSrl.finishRefresh();
                        if (page * pageSize >= baseEntity.getResult().getCount()) {
                            noticeSrl.setNoMoreData(true);
                        }
                    } else {
                        if (page * pageSize >= baseEntity.getResult().getCount()) {
                            noticeSrl.finishLoadMoreWithNoMoreData();
                        } else {
                            noticeSrl.finishLoadMore();
                        }
                    }

                } else {
                    if (loadType == ListLoadingType.Refresh) {
                        noticeSrl.finishRefresh();
                    } else {
                        page--;
                        noticeSrl.finishLoadMore(false);
                    }
                    showToast(baseEntity.getMessage());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                if (loadType == ListLoadingType.Refresh) {
                    noticeSrl.finishRefresh();
                } else {
                    page--;
                    noticeSrl.finishLoadMore(false);
                }
                showToast(ex.getMessage());
            }
        });
        sendRequest(requestParam, false);
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

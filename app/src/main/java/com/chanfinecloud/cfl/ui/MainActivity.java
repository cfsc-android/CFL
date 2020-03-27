package com.chanfinecloud.cfl.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.adapter.SimpleAdapter;
import com.chanfinecloud.cfl.entity.core.ListLoadingType;
import com.chanfinecloud.cfl.http.HttpMethod;
import com.chanfinecloud.cfl.http.MyCallBack;
import com.chanfinecloud.cfl.http.RequestParam;
import com.chanfinecloud.cfl.http.XHttp;
import com.chanfinecloud.cfl.ui.base.BaseActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.jpush.android.api.JPushInterface;

import static com.chanfinecloud.cfl.config.Config.BASE_URL;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    @ViewInject(R.id.toolbar_tv_title)
    TextView toolbar_title;

    @ViewInject(R.id.main_srl)
    private SmartRefreshLayout main_srl;
    @ViewInject(R.id.main_rlv)
    private RecyclerView main_rlv;

    private String title;

    private SimpleAdapter adapter;
    private List<String> data=new ArrayList<>();
    private ListLoadingType loadType;
    private int page=1;
    private int pageSize=20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title=getIntent().getExtras().getString("title");
        toolbar_title.setText(title);

        adapter=new SimpleAdapter(data);
        main_rlv.setLayoutManager(new LinearLayoutManager(this));
        main_rlv.setAdapter(adapter);
        main_rlv.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                showToast(data.get(position));
            }
        });

        main_srl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                page=1;
                getData();
                loadType=ListLoadingType.Refresh;
            }
        });
        main_srl.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                page++;
                getData();
                loadType=ListLoadingType.LoadMore;
            }
        });
        main_srl.autoRefresh();
        setAliasAndTag();
    }

    /**
     * 请求数据
     */
    private void getData(){
        RequestParam requestParam=new RequestParam(BASE_URL+"work/orderType/pageByCondition", HttpMethod.Get);
        Map<String,Object> map=new HashMap<>();
        map.put("pageNo",page);
        map.put("pageSize",pageSize);
        requestParam.setRequestMap(map);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtil.d(result);
                if(page==1){
                    data.clear();
                }
                for (int i = pageSize*(page-1); i < pageSize*page; i++) {
                    data.add("item:"+i);
                }
                adapter.notifyDataSetChanged();
                if(loadType==ListLoadingType.Refresh){
                    main_srl.finishRefresh();
                }else{
                    if(page*pageSize>=67){
                        main_srl.finishLoadMoreWithNoMoreData();
                    }else{
                        main_srl.finishLoadMore();
                    }
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


    /**
     * 设置极光推送的alias（别名）和tag(标签)
     */
    private void setAliasAndTag(){
        JPushInterface.setAlias(this,0x01,"ZXL");
        Set<String> tagSet = new LinkedHashSet<>();
        tagSet.add("YZ");
        tagSet.add("CFSC");
        tagSet.add("TEMP");
        JPushInterface.setTags(this,0x02,tagSet);
    }
}

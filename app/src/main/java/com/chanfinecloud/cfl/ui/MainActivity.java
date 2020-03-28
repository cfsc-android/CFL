package com.chanfinecloud.cfl.ui;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.adapter.SimpleAdapter;
import com.chanfinecloud.cfl.entity.core.ListLoadingType;
import com.chanfinecloud.cfl.http.HttpMethod;
import com.chanfinecloud.cfl.http.MyCallBack;
import com.chanfinecloud.cfl.http.RequestParam;
import com.chanfinecloud.cfl.http.XHttp;
import com.chanfinecloud.cfl.ui.base.BaseActivity;
import com.chanfinecloud.cfl.ui.fragment.mainfrg.HomeFragment;
import com.chanfinecloud.cfl.ui.fragment.mainfrg.MineFragment;
import com.chanfinecloud.cfl.util.LynActivityManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.chanfinecloud.cfl.config.Config.BASE_URL;
import static com.chanfinecloud.cfl.config.Config.WORKORDER;

/**
 * Created by damien on 2020/3/26.
 * Version: 1.0
 * Describe:  主页Activity
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.main_fl_content)
    FrameLayout mainFlContent;
    @BindView(R.id.main_tabs_iv_home)
    ImageView mainTabsIvHome;
    @BindView(R.id.main_tabs_iv_key)
    ImageView mainTabsIvKey;
    @BindView(R.id.main_tabs_iv_mine)
    ImageView mainTabsIvMine;
    @BindView(R.id.main_rlv)
    RecyclerView mainRlv;
    @BindView(R.id.main_srl)
    SmartRefreshLayout mainSrl;

    private RecyclerView main_rlv;

    private String title;
    private SimpleAdapter adapter;
    private List<String> data = new ArrayList<>();
    private ListLoadingType loadType;
    private int page = 1;
    private int pageSize = 20;

    private Context context;
    private FragmentManager fragmentManager;
    private Fragment home, mine;
    private long time=0;


    @Override
    protected void initData() {
        setContentView(R.layout.activity_main);
        setAliasAndTag();
        getData();
        ButterKnife.bind(this);
        context=this;
        fragmentManager = getSupportFragmentManager();
        setTabSelection(0);
    }

    private void setTabSelection(int index) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        switch (index) {
            case 0:
                if (home == null) {
                    home = new HomeFragment();
                    transaction.add(R.id.main_fl_content, home);
                } else {
                    transaction.show(home);
                }
                mainTabsIvHome.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.icon_tab_home_focus));
                mainTabsIvMine.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.icon_tab_mine_blur));
                break;
            case 1:
                if (mine == null) {
                    mine = new MineFragment();
                    transaction.add(R.id.main_fl_content, mine);
                } else {
                    transaction.show(mine);
                }
                mainTabsIvHome.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.icon_tab_home_blur));
                mainTabsIvMine.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.icon_tab_mine_focus));
                break;
        }
        transaction.commitAllowingStateLoss();


    }
    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction 用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (home != null) {
            transaction.hide(home);
        }
        if (mine != null) {
            transaction.hide(mine);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 判断按返回键时
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(new Date().getTime()-time<2000&&time!=0){
                /*Intent home = new Intent(Intent.ACTION_MAIN);
                home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                home.addCategory(Intent.CATEGORY_HOME);
                startActivity(home);*/
                LynActivityManager.getInstance().removeAllActivity();
                System.exit(0);

            }else{
                showToast("再按一次退出");
                time=new Date().getTime();
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
        setAliasAndTag();
        getData();
    }
   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // title=getIntent().getExtras().getString("title");
        // toolbar_title.setText(title);

       *//* adapter=new SimpleAdapter(data);
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
        main_srl.autoRefresh();*//*
    }*/



    /**
     * 请求数据
     */
    private void getData(){
        RequestParam requestParam=new RequestParam(BASE_URL+WORKORDER+"work/orderType/pageByCondition", HttpMethod.Get);
        Map<String,Object> map=new HashMap<>();
        map.put("pageNo",page);
        map.put("pageSize",pageSize);
        requestParam.setRequestMap(map);
        requestParam.setCallback(new MyCallBack<String>() {
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtil.d(result);
                if (page == 1) {
                    data.clear();
                }
                for (int i = pageSize * (page - 1); i < pageSize * page; i++) {
                    data.add("item:" + i);
                }
                adapter.notifyDataSetChanged();
                if (loadType == ListLoadingType.Refresh) {
                    main_srl.finishRefresh();
                } else {
                    if (page * pageSize >= 67) {
                        main_srl.finishLoadMoreWithNoMoreData();
                    } else {
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
        sendRequest(requestParam, false);
    }


    @OnClick({R.id.main_tabs_iv_home, R.id.main_tabs_iv_key, R.id.main_tabs_iv_mine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.main_tabs_iv_home:
                setTabSelection(0);
                break;
            case R.id.main_tabs_iv_key:
                showToast("开门钥匙哇");
                break;
            case R.id.main_tabs_iv_mine:
                setTabSelection(1);
                break;
        }
    }


    /**
     * 设置极光推送的alias（别名）和tag(标签)
     */
    private void setAliasAndTag(){
        JPushInterface.setAlias(this,0x01,"ZXL");//别名（userId）

        Set<String> tagSet = new LinkedHashSet<>();
        tagSet.add("YZ");//身份（YG,YK，YZ，ZK，JS...员工端直接写死YG，业主则用当前项目身份）
        tagSet.add("P_234ab909de");//项目（'P_'+业主当前项目Id,员工端多个项目Id则加多个）
        tagSet.add("D_5656ac65de5b");//部门（'D_'+员工的部门Id）
        JPushInterface.setTags(this,0x02,tagSet);
    }
}

package com.chanfinecloud.cfl.ui.activity;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.adapter.CarManageRecyclerViewAdapter;
import com.chanfinecloud.cfl.adapter.smart.CarEntity;
import com.chanfinecloud.cfl.adapter.smart.CarListEntity;
import com.chanfinecloud.cfl.entity.BaseEntity;
import com.chanfinecloud.cfl.entity.eventbus.EventBusMessage;
import com.chanfinecloud.cfl.http.HttpMethod;
import com.chanfinecloud.cfl.http.JsonParse;
import com.chanfinecloud.cfl.http.MyCallBack;
import com.chanfinecloud.cfl.http.RequestParam;
import com.chanfinecloud.cfl.ui.base.BaseActivity;
import com.chanfinecloud.cfl.util.LogUtils;
import com.chanfinecloud.cfl.weidgt.RecyclerViewDivider;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.recyclerview.animators.FadeInLeftAnimator;

import static com.chanfinecloud.cfl.config.Config.BASE_URL;
import static com.chanfinecloud.cfl.config.Config.BASIC;

public class CarManageActivity extends BaseActivity {

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
    @BindView(R.id.car_manage_recycle)
    RecyclerView carManageRecycle;
    @BindView(R.id.car_manage_smart)
    SmartRefreshLayout carManageSmart;


    private CarManageRecyclerViewAdapter carManageListAdapter;
    private ArrayList<CarEntity> carManageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initData() {
        setContentView(R.layout.activity_car_manage);
        ButterKnife.bind(this);
        toolbarTvTitle.setText("车辆管理");
        toolbarBtnAction.setVisibility(View.VISIBLE);
        toolbarBtnAction.setImageResource(R.drawable.btn_home_add);

        EventBus.getDefault().register(this);

        carManageRecycle.setLayoutManager(new LinearLayoutManager(this));
        // Item Decorator:
        carManageRecycle.addItemDecoration(new RecyclerViewDivider(this, LinearLayoutManager.VERTICAL, R.drawable.divider));
        carManageRecycle.setItemAnimator(new FadeInLeftAnimator());

        carManageRecycle.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                // TODO: 2020/5/6   分页请求不清晰

            }
        });

        carManageSmart.setEnableRefresh(true);//是否启用下拉刷新功能
        carManageSmart.setEnableLoadMore(false);//是否启用上拉刷新功能
        carManageSmart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getCarManageList();

                carManageSmart.finishRefresh(3000);
            }
        });
        initListView();
    }

    private void initListView() {


        if (carManageListAdapter == null) {
            carManageListAdapter = new CarManageRecyclerViewAdapter(this, carManageList);
        } else {
            carManageListAdapter.setData(carManageList);
        }

        carManageRecycle.setAdapter(carManageListAdapter);

        getCarManageList();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void deleteCar(int position) {


        String ids = carManageList.get(position).getId();
        RequestParam requestParam = new RequestParam(BASE_URL + BASIC + "basic/vehicleInfo/delete/" + ids, HttpMethod.Delete);
        requestParam.setCallback(new MyCallBack<String>() {
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d(result);
                BaseEntity baseEntity = JsonParse.parse(result);
                if (baseEntity.isSuccess()) {
                    getCarManageList();
                } else {
                    showToast(baseEntity.getMessage());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                showToast(ex.getMessage());
            }

            @Override
            public void onFinished() {
                super.onFinished();
            }
        });
        sendRequest(requestParam, false);
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    private void getCarManageList() {

        startProgressDialog(true);
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("pageNo", "1");
        requestMap.put("pageSize", "50");
        RequestParam requestParam = new RequestParam(BASE_URL + BASIC + "basic/vehicleInfo/vehiclePage", HttpMethod.Get);
        requestParam.setRequestMap(requestMap);
        requestParam.setCallback(new MyCallBack<String>() {
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d(result);
                BaseEntity<CarListEntity> baseEntity = JsonParse.parse(result, CarListEntity.class);
                if (baseEntity.isSuccess()) {
                    carManageList.clear();
                    carManageList.addAll(baseEntity.getResult().getData());
                    carManageListAdapter.setData(carManageList);

                } else {
                    showToast(baseEntity.getMessage());
                }

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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(EventBusMessage message) {
        if ("carAdd".equals(message.getMessage())) {
            getCarManageList();
        } else if ("onCarItemDelete".equals(message.getMessage())) {
            String postion = String.valueOf(message.getData());
            deleteCar(Integer.parseInt(postion));
        }
    }

    @OnClick({R.id.toolbar_btn_back, R.id.toolbar_btn_action})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_btn_back:
                finish();
                break;
            case R.id.toolbar_btn_action:
                startActivity(CarManageAddActivity.class);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_left) {
            //setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
            return true;
        }

        if (id == R.id.action_right) {
            //lvCarManageList.setSwipeDirection(SwipeMenuListView.DIRECTION_RIGHT);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

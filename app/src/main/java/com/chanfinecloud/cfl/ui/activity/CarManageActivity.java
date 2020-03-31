package com.chanfinecloud.cfl.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.adapter.CarManageListAdapter;
import com.chanfinecloud.cfl.adapter.smart.CarEntity;
import com.chanfinecloud.cfl.adapter.smart.CarListEntity;
import com.chanfinecloud.cfl.entity.BaseEntity;
import com.chanfinecloud.cfl.entity.eventbus.EventBusMessage;
import com.chanfinecloud.cfl.http.HttpMethod;
import com.chanfinecloud.cfl.http.JsonParse;
import com.chanfinecloud.cfl.http.MyCallBack;
import com.chanfinecloud.cfl.http.RequestParam;
import com.chanfinecloud.cfl.ui.base.BaseActivity;
import com.chanfinecloud.cfl.util.FileManagement;
import com.chanfinecloud.cfl.util.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.common.util.LogUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @BindView(R.id.lv_car_manage_list)
    SwipeMenuListView lvCarManageList;

    private CarManageListAdapter carManageListAdapter;
    private ArrayList<CarEntity> carManageList=new ArrayList<>();
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
        initListView();
    }

    private void initListView(){
        carManageListAdapter = new CarManageListAdapter(this,carManageList);
        lvCarManageList.setAdapter(carManageListAdapter);
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                openItem.setBackground(new ColorDrawable(Color.rgb(0xF9,0x3F, 0x25)));
                openItem.setWidth(dp2px(90));
                openItem.setTitle("删除");
                openItem.setTitleSize(18);
                openItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(openItem);
            }
        };
        // 为ListView设置创建器
        lvCarManageList.setMenuCreator(creator);

        // 第2步：为ListView设置菜单项点击监听器，来监听菜单项的点击事件
        lvCarManageList.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                new AlertDialog.Builder(CarManageActivity.this)
                        .setTitle("删除车辆")
                        .setMessage("确认要删除车辆？")
                        .setCancelable(true)
                        .setNegativeButton(
                                "确认删除",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        deleteCar(position);
                                    }
                                }).show();
                return false;
            }
        });

        lvCarManageList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle=new Bundle();
                bundle.putSerializable("car",carManageList.get(position));
                startActivity(CarManageAddActivity.class,bundle);
            }
        });
        getCarManageList();
    }

    private void deleteCar(int position){
//        Map<String,Object> requestMap=new HashMap<>();
//        requestMap.put("id",carManageList.get(position).getId());
//        XUtils.Post(Constants.HOST+"/vehicleinfo/delete.action",requestMap,new MyCallBack<String>(){
//            @Override
//            public void onSuccess(String result) {
//                super.onSuccess(result);
//                Log.e("result",result);
//                try {
//                    JSONObject jsonObject = new JSONObject(result);
//                    if(jsonObject.getInt("resultCode")==200) {
//                        getCarManageList();
//                    }else{
//                        showShortToast(jsonObject.getString("msg"));
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//                super.onError(ex, isOnCallback);
//                showShortToast(ex.getMessage());
//            }
//
//            @Override
//            public void onFinished() {
//                super.onFinished();
//            }
//        });
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    private void getCarManageList(){

        Map<String,String> requestMap=new HashMap<>();
        requestMap.put("pageNo","1");
        requestMap.put("pageSize","10");
        RequestParam requestParam = new RequestParam(BASE_URL+BASIC+"basic/vehicleInfo/vehiclePage", HttpMethod.Get);
        requestParam.setRequestMap(requestMap);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d(result);
                BaseEntity<CarListEntity> baseEntity= JsonParse.parse(result, CarListEntity.class);
                if(baseEntity.isSuccess()){
                    carManageList.clear();
                    carManageList.addAll(baseEntity.getResult().getData());
                    carManageListAdapter.notifyDataSetChanged();
                }else{
                    showToast(baseEntity.getMessage());
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(EventBusMessage message){
        if("carAdd".equals(message.getMessage())){
            getCarManageList();
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
            lvCarManageList.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
            return true;
        }

        if (id == R.id.action_right) {
            lvCarManageList.setSwipeDirection(SwipeMenuListView.DIRECTION_RIGHT);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

package com.chanfinecloud.cfl.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
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
import com.chanfinecloud.cfl.adapter.smart.HouseManageAdapter;
import com.chanfinecloud.cfl.entity.BaseEntity;
import com.chanfinecloud.cfl.entity.eventbus.EventBusMessage;
import com.chanfinecloud.cfl.entity.smart.ApprovalStatusType;
import com.chanfinecloud.cfl.entity.smart.CurrentDistrictEntity;
import com.chanfinecloud.cfl.entity.smart.HouseholdRoomEntity;
import com.chanfinecloud.cfl.entity.smart.UserInfoEntity;
import com.chanfinecloud.cfl.http.HttpMethod;
import com.chanfinecloud.cfl.http.JsonParse;
import com.chanfinecloud.cfl.http.MyCallBack;
import com.chanfinecloud.cfl.http.ParamType;
import com.chanfinecloud.cfl.http.RequestParam;
import com.chanfinecloud.cfl.ui.activity.minefeatures.HouseHoldActivity;
import com.chanfinecloud.cfl.ui.base.BaseActivity;
import com.chanfinecloud.cfl.util.FileManagement;
import com.chanfinecloud.cfl.util.LogUtils;
import com.chanfinecloud.cfl.util.LynActivityManager;
import com.chanfinecloud.cfl.util.UserInfoUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.chanfinecloud.cfl.config.Config.BASE_URL;
import static com.chanfinecloud.cfl.config.Config.BASIC;

public class HouseManageActivity extends BaseActivity {

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
    @BindView(R.id.house_manage_current_smlv_list)
    SwipeMenuListView houseManageCurrentSmlvList;
    @BindView(R.id.house_manage_smlv_list)
    SwipeMenuListView houseManageSmlvList;
    @BindView(R.id.house_manage_ll_add)
    LinearLayout houseManageLlAdd;

    private HouseManageAdapter currentAdapter;
    private HouseManageAdapter otherAdapter;

    private List<HouseholdRoomEntity> currentData=new ArrayList<>();
    private List<HouseholdRoomEntity> otherData=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initData() {
        setContentView(R.layout.activity_house_manage);
        ButterKnife.bind(this);
        toolbarTvTitle.setText("房屋管理");
        currentAdapter = new HouseManageAdapter(this,currentData,false);
        houseManageCurrentSmlvList.setAdapter(currentAdapter);
        houseManageCurrentSmlvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HouseholdRoomEntity householdRoom=currentData.get(position);
                if(householdRoom.getHouseholdType() != null && householdRoom.getHouseholdType().equals("YZ")){
                    Bundle bundle=new Bundle();
                    bundle.putString("roomId",householdRoom.getId());
                    startActivity(HouseholdAuditListActivity.class,bundle);
                }
            }
        });
        // 为ListView设置创建器
        houseManageCurrentSmlvList.setMenuCreator(creator);
        // 第2步：为ListView设置菜单项点击监听器，来监听菜单项的点击事件
        houseManageCurrentSmlvList.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                new AlertDialog.Builder(HouseManageActivity.this)
                        .setTitle("删除房屋")
                        .setMessage("确认要删除房屋？")
                        .setCancelable(true)
                        .setNegativeButton(
                                "确认删除",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        deleteCurrentRoom();
                                    }
                                }).show();
                return false;
            }
        });
        otherAdapter = new HouseManageAdapter(this,otherData,false);
        houseManageSmlvList.setAdapter(otherAdapter);
        houseManageSmlvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HouseholdRoomEntity householdRoom=otherData.get(position);
                if(householdRoom.getHouseholdType() != null && householdRoom.getHouseholdType().equals("YZ")){
                    Bundle bundle=new Bundle();
                    bundle.putString("roomId",householdRoom.getId());
                    startActivity(HouseholdAuditListActivity.class,bundle);
                }else{
                    showToast("对不起，您没有权限！");
                }
            }
        });
        // 为ListView设置创建器
        houseManageSmlvList.setMenuCreator(creator);
        // 第2步：为ListView设置菜单项点击监听器，来监听菜单项的点击事件
        houseManageSmlvList.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                new AlertDialog.Builder(HouseManageActivity.this)
                        .setTitle("删除房屋")
                        .setMessage("确认要删除房屋？")
                        .setCancelable(true)
                        .setNegativeButton(
                                "确认删除",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        HouseholdRoomEntity roomEntity=otherData.get(position);
                                        if(roomEntity.getApprovalStatus()== ApprovalStatusType.Pass.getType()){
                                            deleteOtherRoom(roomEntity.getId());
                                        }else{
                                            deleteRefuseRoom(roomEntity.getApprovalId());
                                        }
                                    }
                                }).show();
                return false;
            }
        });
        intRoomData();
    }

    /**
     * 加载房屋信息
     */
    private void intRoomData(){
        currentData.clear();
        otherData.clear();

        CurrentDistrictEntity currentRoom= FileManagement.getUserInfo().getCurrentDistrict();
        List<HouseholdRoomEntity> roomList= FileManagement.getUserInfo().getRoomList();
        if(roomList!=null&&roomList.size()>0){
            for (int i = 0; i < roomList.size(); i++) {
                roomList.get(i).setApprovalStatus(2);
                if(roomList.get(i).getId().equals(currentRoom.getRoomId())){
                    currentData.add(roomList.get(i));
                }else{
                    otherData.add(roomList.get(i));
                }
            }
            currentAdapter.notifyDataSetChanged();
            otherAdapter.notifyDataSetChanged();
        }
        getRoomData();
    }

    /**
     * 获取房屋审核数据
     */
    private void getRoomData(){
        Map<String,String> requestMap=new HashMap<>();
        requestMap.put("householdId",FileManagement.getUserInfo().getId());
        RequestParam requestParam = new RequestParam(BASE_URL+BASIC+"basic/verify/pendingList", HttpMethod.Get);
        requestParam.setRequestMap(requestMap);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d(result);
                Type type = new TypeToken<List<HouseholdRoomEntity>>() {}.getType();
                BaseEntity<List<HouseholdRoomEntity>> baseEntity= JsonParse.parse(result,type);
                if(baseEntity.isSuccess()){
                    List<HouseholdRoomEntity> list= baseEntity.getResult();
                    for (int i = 0; i < list.size(); i++) {
                        if(list.get(i).getApprovalStatus()== ApprovalStatusType.Refuse.getType()){
                            otherData.add(list.get(i));
                        }
                    }
                    otherAdapter.notifyDataSetChanged();
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

    /**
     * 删除当前房屋
     */
    private void deleteCurrentRoom(){

        Map<String,String> map=new HashMap<>();
        List<HouseholdRoomEntity> list=FileManagement.getUserInfo().getRoomList();
        String roomId=currentData.get(0).getId();
        for (int i = 0; i < list.size(); i++) {
            if(!roomId.equals(list.get(i).getId())){
                map.put(list.get(i).getId(),list.get(i).getHouseholdType());
            }
        }
        Map<String,Object> requestMap=new HashMap<>();
        requestMap.put("id",FileManagement.getUserInfo().getId());
        requestMap.put("roomMap",map);

        Gson gson=new Gson();
        LogUtils.d(gson.toJson(requestMap));

        RequestParam requestParam = new RequestParam(BASE_URL+BASIC+"basic/householdInfo", HttpMethod.Put);
        requestParam.setRequestMap(requestMap);
        requestParam.setParamType(ParamType.Json);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d(result);
                BaseEntity baseEntity= JsonParse.parse(result);
                if(baseEntity.isSuccess()){
                    unBindRoom();
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

    /**
     * 解除当前房屋绑定
     */
    private void unBindRoom(){
        Map<String,Object> map=new HashMap<>();
        map.put("projectId",currentData.get(0).getProjectId());
        map.put("householdId", FileManagement.getUserInfo().getId());
        RequestParam requestParam = new RequestParam(BASE_URL+BASIC+"basic/current/bind", HttpMethod.Post);
        requestParam.setRequestMap(map);
        requestParam.setParamType(ParamType.Json);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d(result);
                BaseEntity baseEntity= JsonParse.parse(result);
                if(baseEntity.isSuccess()){
                    //刷新用户缓存
                    UserInfoUtil.refreshUserInfoByServerCache(new UserInfoUtil.OnRefreshListener() {
                        @Override
                        public void onSuccess() {
                            LynActivityManager.getInstance().finishActivity(HouseHoldActivity.class);
                            EventBus.getDefault().post(new EventBusMessage<>("projectSelect"));
                            //EventBus.getDefault().post(new EventBusMessage<>("houseRefresh"));
                            finish();
                        }

                        @Override
                        public void onFail(String msg) {
                            showToast(msg);
                        }
                    });
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

    /**
     * 刪除其他房屋
     * @param roomId 房屋ID
     */
    private void deleteOtherRoom(String roomId){
        Map<String,String> map=new HashMap<>();
        List<HouseholdRoomEntity> list=FileManagement.getUserInfo().getRoomList();
        for (int i = 0; i < list.size(); i++) {
            if(!roomId.equals(list.get(i).getId())){
                map.put(list.get(i).getId(),list.get(i).getHouseholdType());
            }
        }
        Map<String,Object> requestMap=new HashMap<>();
        requestMap.put("id",FileManagement.getUserInfo().getId());
        requestMap.put("roomMap",map);
        RequestParam requestParam = new RequestParam(BASE_URL+BASIC+"basic/householdInfo", HttpMethod.Put);
        requestParam.setRequestMap(requestMap);
        requestParam.setParamType(ParamType.Json);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d(result);
                BaseEntity baseEntity= JsonParse.parse(result);
                if(baseEntity.isSuccess()){
                    //刷新用户缓存
                    UserInfoUtil.refreshUserInfoByServerCache(new UserInfoUtil.OnRefreshListener() {
                        @Override
                        public void onSuccess() {
                            intRoomData();
                        }

                        @Override
                        public void onFail(String msg) {
                            showToast(msg);
                        }
                    });
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

    /**
     * 刪除审核被拒的房屋
     * @param approvalId 审核ID
     */
    private void deleteRefuseRoom(String approvalId){

        Map<String,Object> map=new HashMap<>();
        map.put("id",approvalId);
        RequestParam requestParam = new RequestParam(BASE_URL+BASIC+"basic/verify/delete", HttpMethod.Delete);
        requestParam.setRequestMap(map);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d(result);
                LogUtils.d(result);
                BaseEntity baseEntity= JsonParse.parse(result);
                if(baseEntity.isSuccess()){
                    intRoomData();
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

    private SwipeMenuCreator creator = new SwipeMenuCreator() {

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

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }





    @OnClick({R.id.toolbar_btn_back, R.id.house_manage_ll_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_btn_back:
                finish();
                break;
            case R.id.house_manage_ll_add:
                Bundle bundle=new Bundle();
                bundle.putString("openFrom","HouseManage");
                startActivity(ProjectSelectActivity.class,bundle);
                break;
        }
    }
}

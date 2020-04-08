package com.chanfinecloud.cfl.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.adapter.smart.RoomHouseholdListAdapter;
import com.chanfinecloud.cfl.entity.BaseEntity;
import com.chanfinecloud.cfl.entity.eventbus.EventBusMessage;
import com.chanfinecloud.cfl.entity.smart.RoomEntity;
import com.chanfinecloud.cfl.entity.smart.RoomHouseholdEntity;
import com.chanfinecloud.cfl.entity.smart.UserInfoEntity;
import com.chanfinecloud.cfl.http.HttpMethod;
import com.chanfinecloud.cfl.http.JsonParse;
import com.chanfinecloud.cfl.http.MyCallBack;
import com.chanfinecloud.cfl.http.ParamType;
import com.chanfinecloud.cfl.http.RequestParam;
import com.chanfinecloud.cfl.ui.MainActivity;
import com.chanfinecloud.cfl.ui.activity.minefeatures.HouseHoldActivity;
import com.chanfinecloud.cfl.ui.base.BaseActivity;
import com.chanfinecloud.cfl.util.FileManagement;
import com.chanfinecloud.cfl.util.LogUtils;
import com.chanfinecloud.cfl.util.LynActivityManager;
import com.chanfinecloud.cfl.util.UserInfoUtil;
import com.chanfinecloud.cfl.weidgt.RecyclerViewDivider;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.chanfinecloud.cfl.config.Config.BASE_URL;
import static com.chanfinecloud.cfl.config.Config.BASIC;

public class OtherRoomDetailActivity extends BaseActivity {

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
    @BindView(R.id.other_room_project_name)
    TextView otherRoomProjectName;
    @BindView(R.id.other_room_room_name)
    TextView otherRoomRoomName;
    @BindView(R.id.other_room_room_code)
    TextView otherRoomRoomCode;
    @BindView(R.id.other_room_user_avatar)
    ImageView otherRoomUserAvatar;
    @BindView(R.id.other_room_user_name)
    TextView otherRoomUserName;
    @BindView(R.id.other_room_user_type)
    TextView otherRoomUserType;
    @BindView(R.id.other_room_ll)
    LinearLayout otherRoomLl;
    @BindView(R.id.other_room_rv_other)
    RecyclerView otherRoomRvOther;
    @BindView(R.id.other_room_btn_trans)
    Button otherRoomBtnTrans;

    private RoomHouseholdListAdapter adapter;
    private List<RoomHouseholdEntity> data=new ArrayList<>();
    private RoomHouseholdEntity currentHousehold;
    private RoomEntity roomEntity;
    private String roomId;

    public OtherRoomDetailActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initData() {
        setContentView(R.layout.activity_other_room_detail);
        ButterKnife.bind(this);

        toolbarTvTitle.setText("其他房屋");
        roomId=getIntent().getExtras().getString("roomId");
        adapter=new RoomHouseholdListAdapter(this,data);
        otherRoomRvOther.setLayoutManager(new LinearLayoutManager(this));
        otherRoomRvOther.addItemDecoration(new RecyclerViewDivider(this, LinearLayoutManager.VERTICAL));
        otherRoomRvOther.setAdapter(adapter);
        otherRoomRvOther.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle=new Bundle();
                bundle.putSerializable("household",data.get(position));
                bundle.putBoolean("edit",false);
                startActivity(HouseholdFaceActivity.class,bundle);
            }
        });
        getRoomData();


    }

    private void getRoomData(){
        RequestParam requestParam = new RequestParam(BASE_URL+BASIC+"basic/room/"+roomId , HttpMethod.Get);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d(result);
                BaseEntity<RoomEntity> baseEntity= JsonParse.parse(result,RoomEntity.class);
                if(baseEntity.isSuccess()){
                    roomEntity=baseEntity.getResult();
                    List<RoomHouseholdEntity> householdEntityList=roomEntity.getHouseholdBoList();
                    for (int i = 0; i <householdEntityList.size() ; i++) {
                        RoomHouseholdEntity household=householdEntityList.get(i);
                        if(FileManagement.getUserInfo().getId().equals(household.getId())){
                            currentHousehold=household;
                            initCurrentRoomView();
                        }else{
                            data.add(household);
                        }
                    }
                    adapter.notifyDataSetChanged();
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

    @OnClick({R.id.toolbar_btn_back, R.id.other_room_ll, R.id.other_room_btn_trans})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_btn_back:
                finish();
                break;
            case R.id.other_room_ll:
                Bundle bundle=new Bundle();
                bundle.putSerializable("household",currentHousehold);
                bundle.putBoolean("edit",true);
                // TODO: 2020/4/3  此处传入得faceInfo  为空  照理说应该是有数据的  是不是两个表的数据没同步  登录进来是有的
                startActivity(HouseholdFaceActivity.class,bundle);
                break;
            case R.id.other_room_btn_trans:
                bindRoom();
                break;
        }
    }

    private void initCurrentRoomView(){
        if(!TextUtils.isEmpty(currentHousehold.getAvatarResource())){
            Glide.with(this)
                    .load(currentHousehold.getAvatarResource())
                    .error(R.drawable.ic_default_img)
                    .circleCrop()
                    .into(otherRoomUserAvatar);
        }else{
            Glide.with(this)
                    .load(R.drawable.icon_user_default)
                    .circleCrop()
                    .into(otherRoomUserAvatar);
        }
        otherRoomProjectName.setText(roomEntity.getProjectName());
        otherRoomRoomName.setText(roomEntity.getFullName());
        otherRoomRoomCode.setText(roomEntity.getCode());
        otherRoomUserName.setText(TextUtils.isEmpty(currentHousehold.getNickName())?currentHousehold.getName():currentHousehold.getNickName());
        otherRoomUserType.setText(currentHousehold.getHouseholdTypeDisplay());
    }


    private void bindRoom(){

        Map<String,Object> map=new HashMap<>();
        map.put("projectId",roomEntity.getProjectId());
        map.put("phaseId",roomEntity.getPhaseId());
        map.put("buildingId",roomEntity.getBuildingId());
        map.put("unitId",roomEntity.getUnitId());
        map.put("roomId",roomEntity.getId());
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
                    UserInfoUtil.refreshUserInfoByServerCache(new UserInfoUtil.OnRefreshListener() {
                        @Override
                        public void onSuccess() {
                            LynActivityManager.getInstance().finishActivity(HouseHoldActivity.class);
                            EventBus.getDefault().post(new EventBusMessage<>("projectSelect"));
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

}

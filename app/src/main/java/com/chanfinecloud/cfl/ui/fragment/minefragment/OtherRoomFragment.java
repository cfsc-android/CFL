package com.chanfinecloud.cfl.ui.fragment.minefragment;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.adapter.smart.OtherRoomListAdapter;
import com.chanfinecloud.cfl.entity.BaseEntity;
import com.chanfinecloud.cfl.entity.eventbus.EventBusMessage;
import com.chanfinecloud.cfl.entity.smart.CurrentDistrictEntity;
import com.chanfinecloud.cfl.entity.smart.HouseholdRoomEntity;
import com.chanfinecloud.cfl.entity.smart.UserInfoEntity;
import com.chanfinecloud.cfl.http.HttpMethod;
import com.chanfinecloud.cfl.http.JsonParse;
import com.chanfinecloud.cfl.http.MyCallBack;
import com.chanfinecloud.cfl.http.RequestParam;
import com.chanfinecloud.cfl.ui.activity.OtherRoomDetailActivity;
import com.chanfinecloud.cfl.ui.base.BaseFragment;
import com.chanfinecloud.cfl.util.FileManagement;
import com.chanfinecloud.cfl.util.LogUtils;
import com.chanfinecloud.cfl.weidgt.RecyclerViewDivider;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.chanfinecloud.cfl.config.Config.BASE_URL;
import static com.chanfinecloud.cfl.config.Config.BASIC;

/**
 * 我的房屋--其他房屋
 */
public class OtherRoomFragment extends BaseFragment {


    @BindView(R.id.other_room_rv)
    RecyclerView otherRoomRv;
    private Unbinder unbinder;

    private Activity context;
    private OtherRoomListAdapter adapter;
    private List<HouseholdRoomEntity> data=new ArrayList<>();
    @Override
    protected void initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_other_room, null);
        setContentView(view);
        unbinder = ButterKnife.bind(this, view);
        context=getActivity();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initData() {
        adapter=new OtherRoomListAdapter(context,data);
        otherRoomRv.setLayoutManager(new LinearLayoutManager(context));
        otherRoomRv.addItemDecoration(new RecyclerViewDivider(context, LinearLayoutManager.VERTICAL));
        otherRoomRv.setAdapter(adapter);
        otherRoomRv.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle=new Bundle();
                bundle.putString("roomId",data.get(position).getId());
                startActivity(OtherRoomDetailActivity.class,bundle);
            }
        });
        getRoomData();
        initOtherRoom();
    }

    private void initOtherRoom(){
        UserInfoEntity userInfoEntity= FileManagement.getUserInfo();
        List<HouseholdRoomEntity> householdRoomEntities= userInfoEntity.getRoomList();
        CurrentDistrictEntity currentDistrictEntity=userInfoEntity.getCurrentDistrict();
        if(householdRoomEntities!=null&&householdRoomEntities.size()>0){
            for (int i = 0; i < householdRoomEntities.size(); i++) {
                HouseholdRoomEntity householdRoomEntity=householdRoomEntities.get(i);
                if(!TextUtils.isEmpty(currentDistrictEntity.getRoomId())){
                    if(!TextUtils.isEmpty(currentDistrictEntity.getRoomId())&&!currentDistrictEntity.getRoomId().equals(householdRoomEntity.getId())){
                        householdRoomEntity.setApprovalStatus(2);
                        data.add(householdRoomEntity);
                    }
                }else{
                    householdRoomEntity.setApprovalStatus(2);
                    data.add(householdRoomEntity);
                }

            }
            adapter.notifyDataSetChanged();
        }

    }

    private void getRoomData(){
        Map<String,String> map=new HashMap<>();
        map.put("householdId",FileManagement.getUserInfo().getId());
        RequestParam requestParam = new RequestParam(BASE_URL+BASIC+"basic/verify/pendingList" , HttpMethod.Get);
        requestParam.setRequestMap(map);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d(result);
                Type type = new TypeToken<List<HouseholdRoomEntity>>() {}.getType();
                BaseEntity<List<HouseholdRoomEntity>> baseEntity= JsonParse.parse(result,type);
                if(baseEntity.isSuccess()){
                    data.addAll(baseEntity.getResult());
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(EventBusMessage message){
        if("HouseholdRefresh".equals(message.getMessage())){
            data.clear();
            initOtherRoom();
            getRoomData();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
        EventBus.getDefault().unregister(this);
    }

}

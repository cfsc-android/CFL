package com.chanfinecloud.cfl.ui.fragment.minefragment;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
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
import com.chanfinecloud.cfl.entity.smart.CurrentDistrictEntity;
import com.chanfinecloud.cfl.entity.smart.RoomEntity;
import com.chanfinecloud.cfl.entity.smart.RoomHouseholdEntity;
import com.chanfinecloud.cfl.entity.smart.UserInfoEntity;
import com.chanfinecloud.cfl.http.HttpMethod;
import com.chanfinecloud.cfl.http.JsonParse;
import com.chanfinecloud.cfl.http.MyCallBack;
import com.chanfinecloud.cfl.http.RequestParam;
import com.chanfinecloud.cfl.ui.activity.HouseholdFaceActivity;
import com.chanfinecloud.cfl.ui.activity.UnitSelectActivity;
import com.chanfinecloud.cfl.ui.base.BaseFragment;
import com.chanfinecloud.cfl.util.FileManagement;
import com.chanfinecloud.cfl.util.LogUtils;
import com.chanfinecloud.cfl.weidgt.RecyclerViewDivider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.chanfinecloud.cfl.config.Config.BASE_URL;
import static com.chanfinecloud.cfl.config.Config.BASIC;
import static com.chanfinecloud.cfl.config.Config.WORKORDER;

/**
 * damien
 * 我的房屋 -- 当前房屋
 */
public class CurrentRoomFragment extends BaseFragment {


    @BindView(R.id.current_room_project_name)
    TextView currentRoomProjectName;
    @BindView(R.id.current_room_room_name)
    TextView currentRoomRoomName;
    @BindView(R.id.current_room_project_line)
    TextView currentRoomProjectLine;
    @BindView(R.id.current_room_room_code)
    TextView currentRoomRoomCode;
    @BindView(R.id.current_room_project_ll)
    LinearLayout currentRoomProjectLl;
    @BindView(R.id.current_room_avatar)
    ImageView currentRoomAvatar;
    @BindView(R.id.current_room_nick_name)
    TextView currentRoomNickName;
    @BindView(R.id.current_room_btn_add)
    Button currentRoomBtnAdd;
    @BindView(R.id.current_room_ll_add)
    LinearLayout currentRoomLlAdd;
    @BindView(R.id.current_room_user_avatar)
    ImageView currentRoomUserAvatar;
    @BindView(R.id.current_room_user_name)
    TextView currentRoomUserName;
    @BindView(R.id.current_room_user_type)
    TextView currentRoomUserType;
    @BindView(R.id.current_room_ll)
    LinearLayout currentRoomLl;
    @BindView(R.id.current_room_ll_show)
    LinearLayout currentRoomLlShow;
    @BindView(R.id.current_room_rv_other)
    RecyclerView currentRoomRvOther;
    private Unbinder unbinder;

    private Activity context;
    private RoomHouseholdListAdapter adapter;
    private List<RoomHouseholdEntity> data=new ArrayList<>();
    private RoomHouseholdEntity currentHousehold;
    private RoomEntity roomEntity;
    private UserInfoEntity userInfo;
    @Override
    protected void initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_current_room, null);
        setContentView(view);
        unbinder = ButterKnife.bind(this, view);
        context=getActivity();
        userInfo= FileManagement.getUserInfoEntity();
    }

    @Override
    protected void initData() {

        CurrentDistrictEntity currentDistrictEntity = userInfo.getCurrentDistrict();
        if(!TextUtils.isEmpty(currentDistrictEntity.getRoomId())){
            getRoomData();
            currentRoomLlShow.setVisibility(View.VISIBLE);
            adapter=new RoomHouseholdListAdapter(context,data);
            currentRoomRvOther.setLayoutManager(new LinearLayoutManager(context));
            currentRoomRvOther.addItemDecoration(new RecyclerViewDivider(context, LinearLayoutManager.VERTICAL));
            currentRoomRvOther.setAdapter(adapter);
            currentRoomRvOther.addOnItemTouchListener(new OnItemClickListener() {
                @Override
                public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("household",data.get(position));
                    bundle.putBoolean("edit",false);
                    startActivity(HouseholdFaceActivity.class,bundle);
                }
            });
        }else{
            currentRoomProjectLine.setVisibility(View.GONE);
            currentRoomProjectLl.setVisibility(View.GONE);
            currentRoomLlAdd.setVisibility(View.VISIBLE);
            if(userInfo.getAvatarResource()!=null){
                Glide.with(context)
                        .load(userInfo.getAvatarResource().getUrl())
                        .error(R.drawable.ic_default_img)
                        .circleCrop()
                        .into(currentRoomAvatar);
            }
            currentRoomNickName.setText(TextUtils.isEmpty(userInfo.getNickName())?userInfo.getName():userInfo.getNickName());
        }
    }

    private void getRoomData() {
        RequestParam requestParam=new RequestParam(BASE_URL+BASIC+"basic/room/"+FileManagement.getUserInfoEntity().getCurrentDistrict().getRoomId(), HttpMethod.Get);
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
                        if(FileManagement.getUserInfoEntity().getId().equals(household.getId())){
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
    }

    private void initCurrentRoomView(){
        if(!TextUtils.isEmpty(currentHousehold.getAvatarResource())){
            Glide.with(context)
                    .load(currentHousehold.getAvatarResource())
                    .error(R.drawable.ic_default_img)
                    .circleCrop()
                    .into(currentRoomUserAvatar);
        }else{
            Glide.with(context)
                    .load(R.drawable.icon_user_default)
                    .circleCrop()
                    .into(currentRoomUserAvatar);
        }
        currentRoomProjectName.setText(roomEntity.getProjectName());
        currentRoomRoomName.setText(roomEntity.getFullName());
        currentRoomRoomCode.setText(roomEntity.getCode());
        currentRoomUserName.setText(TextUtils.isEmpty(currentHousehold.getNickName())?currentHousehold.getName():currentHousehold.getNickName());
        currentRoomUserType.setText(currentHousehold.getHouseholdTypeDisplay());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @OnClick({R.id.current_room_btn_add, R.id.current_room_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.current_room_btn_add:
                startActivity(UnitSelectActivity.class);
                break;
            case R.id.current_room_ll:
                Bundle bundle=new Bundle();
                bundle.putSerializable("household",currentHousehold);
                bundle.putBoolean("edit",true);
                startActivity(HouseholdFaceActivity.class,bundle);
                break;
        }
    }
}

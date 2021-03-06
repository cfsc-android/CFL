package com.chanfinecloud.cfl.ui.fragment.minefragment;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.chanfinecloud.cfl.entity.eventbus.EventBusMessage;
import com.chanfinecloud.cfl.entity.smart.CurrentDistrictEntity;
import com.chanfinecloud.cfl.entity.smart.ResourceEntity;
import com.chanfinecloud.cfl.entity.smart.RoomEntity;
import com.chanfinecloud.cfl.entity.smart.RoomHouseholdEntity;
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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.chanfinecloud.cfl.config.Config.BASE_URL;
import static com.chanfinecloud.cfl.config.Config.BASIC;

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
    private UserInfoEntity userInfo;
    private RoomEntity roomEntity;

    @Override
    protected void initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_current_room, null);
        setContentView(view);
        unbinder = ButterKnife.bind(this, view);
        context=getActivity();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initData() {

        initRoomData();

    }

    private void initRoomData() {
        userInfo= FileManagement.getUserInfo();
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
            currentRoomProjectLine.setVisibility(View.VISIBLE);
            currentRoomProjectLl.setVisibility(View.VISIBLE);
            currentRoomLlAdd.setVisibility(View.GONE);


        }else{
            currentRoomProjectLine.setVisibility(View.GONE);
            currentRoomProjectLl.setVisibility(View.GONE);
            currentRoomLlAdd.setVisibility(View.VISIBLE);
            ResourceEntity avatarResource=userInfo.getAvatarResource();
            if(avatarResource!=null){
                Glide.with(context)
                        .load(avatarResource.getUrl())
                        .error(R.drawable.ic_default_img)
                        .circleCrop()
                        .into(currentRoomAvatar);
            }
            currentRoomNickName.setText(TextUtils.isEmpty(userInfo.getNickName())?userInfo.getName():userInfo.getNickName());
            currentRoomProjectName.setText(currentDistrictEntity.getProjectName());
            currentRoomRoomName.setText("物业中心");
        }
    }

    private void getRoomData() {

        if (FileManagement.getUserInfo() != null && FileManagement.getUserInfo().getCurrentDistrict() != null && !TextUtils.isEmpty(FileManagement.getUserInfo().getCurrentDistrict().getRoomId())) {

            RequestParam requestParam = new RequestParam(BASE_URL + BASIC + "basic/room/" + FileManagement.getUserInfo().getCurrentDistrict().getRoomId(), HttpMethod.Get);
            requestParam.setCallback(new MyCallBack<String>() {
                @Override
                public void onSuccess(String result) {
                    super.onSuccess(result);
                    LogUtils.d(result);
                    stopProgressDialog();
                    BaseEntity<RoomEntity> baseEntity = JsonParse.parse(result, RoomEntity.class);
                    if (baseEntity.isSuccess()) {
                        roomEntity = baseEntity.getResult();
                        List<RoomHouseholdEntity> householdEntityList = roomEntity.getHouseholdBoList();
                        for (int i = 0; i < householdEntityList.size(); i++) {
                            RoomHouseholdEntity household = householdEntityList.get(i);
                            if (FileManagement.getUserInfo().getId().equals(household.getId())) {
                                currentHousehold = household;
                                initCurrentRoomView();
                            } else {
                                data.add(household);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        showToast(baseEntity.getMessage());
                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    super.onError(ex, isOnCallback);
                    showToast(ex.getMessage());
                    stopProgressDialog();
                }

                @Override
                public void onCancelled(CancelledException cex) {
                    super.onCancelled(cex);
                    stopProgressDialog();
                }

                @Override
                public void onFinished() {
                    super.onFinished();
                    stopProgressDialog();
                }
            });

            sendRequest(requestParam, true);

        }
    }

    private void initCurrentRoomView(){
        if(currentHousehold.getAvatarResource() != null && !TextUtils.isEmpty(currentHousehold.getAvatarResource().getUrl())){
            Glide.with(context)
                    .load(currentHousehold.getAvatarResource().getUrl())
                    .error(R.drawable.icon_user_default)
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


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(EventBusMessage message){
        if("HouseholdRefresh".equals(message.getMessage())){
            data.clear();
            initRoomData();
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

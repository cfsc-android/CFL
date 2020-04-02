package com.chanfinecloud.cfl.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.adapter.smart.EquipmentAdapter;
import com.chanfinecloud.cfl.entity.BaseEntity;
import com.chanfinecloud.cfl.entity.smart.EquipmentInfoBo;
import com.chanfinecloud.cfl.http.HttpMethod;
import com.chanfinecloud.cfl.http.JsonParse;
import com.chanfinecloud.cfl.http.MyCallBack;
import com.chanfinecloud.cfl.http.ParamType;
import com.chanfinecloud.cfl.http.RequestParam;
import com.chanfinecloud.cfl.ui.base.BaseActivity;
import com.chanfinecloud.cfl.util.FileManagement;
import com.chanfinecloud.cfl.view.RecyclerViewDivider;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.util.LogUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.chanfinecloud.cfl.config.Config.BASE_URL;
import static com.chanfinecloud.cfl.config.Config.IOT;

public class UnLockListActivity extends BaseActivity {


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
    @BindView(R.id.unlock_rlv)
    RecyclerView unlockRlv;

    private EquipmentAdapter adapter;
    private List<EquipmentInfoBo> data=new ArrayList<>();
    @Override
    protected void initData() {
        setContentView(R.layout.activity_un_lock_list);
        ButterKnife.bind(this);
        toolbarTvTitle.setText("门禁列表");
        getData();
    }

    /**
     * 获取门禁数据
     */
    private void getData(){
        RequestParam requestParam=new RequestParam(BASE_URL+IOT+"community/api/access/v1/devices/user", HttpMethod.Get);
        Map<String,Object> map=new HashMap<>();
        map.put("phaseId", FileManagement.getUserInfo().getRoomList().get(0).getPhaseId());
        map.put("userId",FileManagement.getUserInfo().getId());
        requestParam.setRequestMap(map);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtil.d(result);
                Type type = new TypeToken<List<EquipmentInfoBo>>() {}.getType();
                BaseEntity<List<EquipmentInfoBo>> baseEntity= JsonParse.parse(result,type);
                if(baseEntity.isSuccess()){
                    data.addAll(baseEntity.getResult());
                    initAdapter();
                }else{
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
        sendRequest(requestParam,true);
    }

    /**
     * 初始化门禁视图
     */
    private void initAdapter(){
        adapter=new EquipmentAdapter(this,data);
        unlockRlv.setLayoutManager(new LinearLayoutManager(this));
        unlockRlv.addItemDecoration(new RecyclerViewDivider(this, LinearLayoutManager.VERTICAL));
        unlockRlv.setAdapter(adapter);
        unlockRlv.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                if(!((EquipmentAdapter)adapter).getEquipmentOpen(position)){
                    openDoor(position);
                }
            }
        });
    }

    /**
     * 开门
     * @param position 列表索引
     */
    private void openDoor(final int position){
        RequestParam requestParam=new RequestParam(BASE_URL+IOT+"community/api/access/v1/devices/user", HttpMethod.Post);
        Map<String,Object> map=new HashMap<>();
        map.put("cmd","open");
        map.put("deviceSerial",data.get(position).getDeviceSerial());
        map.put("phaseId", FileManagement.getUserInfo().getRoomList().get(0).getPhaseId());
        requestParam.setRequestMap(map);
        requestParam.setParamType(ParamType.Json);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtil.d(result);
                BaseEntity baseEntity= JsonParse.parse(result);
                if(baseEntity.isSuccess()){
                    adapter.setEquipmentOpen(position);
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
        sendRequest(requestParam,false);
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

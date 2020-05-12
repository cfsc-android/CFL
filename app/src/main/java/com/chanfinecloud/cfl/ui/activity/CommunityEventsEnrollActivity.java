package com.chanfinecloud.cfl.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.adapter.smart.EventsEnrollInfoListAdapter;
import com.chanfinecloud.cfl.entity.BaseEntity;
import com.chanfinecloud.cfl.entity.smart.EventEnrollInfoEntity;
import com.chanfinecloud.cfl.entity.smart.EventsEntity;
import com.chanfinecloud.cfl.http.HttpMethod;
import com.chanfinecloud.cfl.http.JsonParse;
import com.chanfinecloud.cfl.http.MyCallBack;
import com.chanfinecloud.cfl.http.ParamType;
import com.chanfinecloud.cfl.http.RequestParam;
import com.chanfinecloud.cfl.ui.base.BaseActivity;
import com.chanfinecloud.cfl.util.FileManagement;
import com.chanfinecloud.cfl.util.Utils;

import org.json.JSONObject;
import org.xutils.common.util.LogUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.chanfinecloud.cfl.config.Config.ARTICLE;
import static com.chanfinecloud.cfl.config.Config.BASE_URL;

public class CommunityEventsEnrollActivity extends BaseActivity {

    @BindView(R.id.toolbar_btn_back)
    ImageButton toolbarBtnBack;
    @BindView(R.id.toolbar_tv_title)
    TextView toolbarTvTitle;
    @BindView(R.id.toolbar_tv_action)
    TextView toolbarTvAction;
    @BindView(R.id.toolbar_btn_radio)
    CheckBox toolbarBtnRadio;
    @BindView(R.id.toolbar_btn_action)
    ImageButton toolbarBtnAction;
    @BindView(R.id.toolbar_ll_view)
    LinearLayout toolbarLlView;
    @BindView(R.id.events_enroll_name)
    TextView eventsEnrollName;
    @BindView(R.id.events_enroll_time)
    TextView eventsEnrollTime;
    @BindView(R.id.events_enroll_address)
    TextView eventsEnrollAddress;
    @BindView(R.id.events_enroll_rlv)
    RecyclerView eventsEnrollRlv;
    @BindView(R.id.events_enroll_add)
    TextView eventsEnrollAdd;
    @BindView(R.id.events_enroll_goto)
    TextView eventsEnrollGoto;
    @BindView(R.id.events_enroll_pic)
    ImageView eventsEnrollPic;


    private EventsEntity eventsEntity;
    private List<EventEnrollInfoEntity> eventEnrollInfoEntityList = new ArrayList<>();
    private EventsEnrollInfoListAdapter eventsEnrollInfoListAdapter;
    private String eventsID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initData() {
        setContentView(R.layout.activity_community_events_enroll);
        ButterKnife.bind(this);

        toolbarTvTitle.setText("填写报名信息");
        eventsID = getIntent().getStringExtra("enrollEventsID");

        getEventDetailById();
        eventsEnrollInfoListAdapter = new EventsEnrollInfoListAdapter(this, eventEnrollInfoEntityList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        eventsEnrollRlv.setLayoutManager(linearLayoutManager);
        eventsEnrollRlv.setAdapter(eventsEnrollInfoListAdapter);

        eventsEnrollInfoListAdapter.setOnItemTextChangeListener(new EventsEnrollInfoListAdapter.OnItemTextChangeListener() {
            @Override
            public void OnItemTextChanged(String mContont, int nPosition, int nType) {
                if (nType == 1) {
                    eventEnrollInfoEntityList.get(nPosition).setName(mContont);
                } else {
                    eventEnrollInfoEntityList.get(nPosition).setMobile(mContont);
                }
            }
        });
        eventsAddPerson();

    }

    private void eventsAddPerson() {
        EventEnrollInfoEntity eventEnrollInfoEntity = new EventEnrollInfoEntity();
        eventEnrollInfoEntity.setName("nihao ");
        eventEnrollInfoEntity.setMobile("18570302328");
        eventEnrollInfoEntityList.add(eventEnrollInfoEntity);
        eventsEnrollInfoListAdapter.notifyDataSetChanged();
        //   eventsEnrollInfoListAdapter.notifyItemInserted(eventEnrollInfoEntityList.size() - 1);
    }

    private void getEventDetailById() {

        startProgressDialog(true);
        RequestParam requestParam = new RequestParam(BASE_URL + ARTICLE + "smart/event/" + eventsID, HttpMethod.Get);
        requestParam.setCallback(new MyCallBack<String>() {
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtil.d(result);
                BaseEntity<EventsEntity> baseEntity = JsonParse.parse(result, EventsEntity.class);
                if (baseEntity.isSuccess()) {
                    eventsEntity = baseEntity.getResult();

                    freshEnrollData();

                } else {
                    showToast(baseEntity.getMessage());
                }

                stopProgressDialog();

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

    private void freshEnrollData() {

        if (eventsEntity != null) {
            eventsEnrollName.setText(eventsEntity.getTitle());
            eventsEnrollTime.setText(eventsEntity.getStartTime() + "-" + eventsEntity.getEndTime());
            eventsEnrollAddress.setText(eventsEntity.getLocation());

            Glide.with(this).load(eventsEntity.getCoverImageResource().getUrl())
                    .error(R.drawable.car_manage_test)
                    .into(eventsEnrollPic);


        }

    }

    @OnClick({R.id.toolbar_btn_back, R.id.events_enroll_add, R.id.events_enroll_goto})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_btn_back:
                finish();
                break;
            case R.id.events_enroll_add:
                if (eventsEntity != null && eventEnrollInfoEntityList.size() < eventsEntity.getSingleMaxNumber()) {
                    eventsAddPerson();
                } else {
                    showToast("最大报名人数为" + eventsEntity.getSingleMaxNumber());
                }
                break;
            case R.id.events_enroll_goto:

                if (eventEnrollInfoEntityList.size() > 0) {
                    Log.e("onViewClicked", "CommunityEventsEnrollActivity " + eventEnrollInfoEntityList.toString());

                    for (int i = 0; i < eventEnrollInfoEntityList.size(); i++) {
                        EventEnrollInfoEntity eventEnrollInfoEntity = eventEnrollInfoEntityList.get(i);
                        if (eventEnrollInfoEntity != null) {
                            if (Utils.isEmpty(eventEnrollInfoEntity.getName())) {
                                showToast("请输入姓名");
                                return;
                            }

                            if (Utils.isEmpty(eventEnrollInfoEntity.getMobile())) {
                                showToast("请输入手机号码");
                                return;
                            }
                        } else {
                            showToast("请输入姓名和电话");
                            return;
                        }

                    }
                }

                gotoEnrollSend();
                break;
        }
    }

    private void gotoEnrollSend() {

        startProgressDialog(true);
        RequestParam requestParam = new RequestParam(BASE_URL + ARTICLE + "smart/event/registration", HttpMethod.Post);
        Map<String, Object> map = new HashMap<>();
        map.put("eventId", eventsID);
        if (FileManagement.getUserInfo() != null && FileManagement.getUserInfo().getCurrentDistrict() != null)
            map.put("householdId", FileManagement.getUserInfo().getCurrentDistrict().getHouseholdId());
        else {
            showToast("请绑定房屋后再来参加活动");
            return;
        }
        map.put("detailList", eventEnrollInfoEntityList);
        requestParam.setRequestMap(map);
        requestParam.setParamType(ParamType.Json);
        requestParam.setCallback(new MyCallBack<String>() {
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtil.d(result);
                BaseEntity<JSONObject> baseEntity = JsonParse.parse(result, JSONObject.class);
                if (baseEntity.isSuccess()) {
                    stopProgressDialog();
                    new AlertDialog.Builder(CommunityEventsEnrollActivity.this)
                            .setTitle("提示")
                            .setMessage("报名成功")
                            .setCancelable(true)
                            .setNegativeButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            }).show();


                } else {
                    showToast(baseEntity.getMessage());
                    stopProgressDialog();
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
}

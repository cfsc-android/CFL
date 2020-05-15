package com.chanfinecloud.cfl.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
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
import com.chanfinecloud.cfl.weidgt.imagepreview.IconImageView;
import com.chanfinecloud.cfl.weidgt.imagepreview.ImageViewInfo;
import com.chanfinecloud.cfl.weidgt.imagepreview.PreviewBuilder;

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
    IconImageView eventsEnrollPic;
    @BindView(R.id.events_enroll_delete)
    TextView eventsEnrollDelete;


    private EventsEntity eventsEntity;
    private List<EventEnrollInfoEntity> eventEnrollInfoEntityList = new ArrayList<>();
    private EventsEnrollInfoListAdapter eventsEnrollInfoListAdapter;
    private String eventsID;
    private List<ImageViewInfo> contentImageData = new ArrayList<>();
    private int deleteFlag = 0;//0可以删除 1 删除中

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
                } else if (nType == 2) {
                    eventEnrollInfoEntityList.get(nPosition).setMobile(mContont);
                } else if (nType == 3) {
                    eventEnrollInfoEntityList.get(nPosition).setGender(mContont);
                } else if (nType == 4) {
                    if (Utils.isEmpty(mContont))
                        eventEnrollInfoEntityList.get(nPosition).setAge(0);
                    else
                        eventEnrollInfoEntityList.get(nPosition).setAge(Integer.parseInt(mContont));
                }
            }
        });
        eventsAddPerson();

    }

    private void eventsAddPerson() {
        EventEnrollInfoEntity eventEnrollInfoEntity = new EventEnrollInfoEntity();
        eventEnrollInfoEntity.setName("");
        eventEnrollInfoEntity.setMobile("");
        eventEnrollInfoEntity.setEventId("");
        eventEnrollInfoEntity.setHouseholdId("");
        eventEnrollInfoEntity.setId("");
        eventEnrollInfoEntity.setRemark("");
        eventEnrollInfoEntityList.add(eventEnrollInfoEntity);
        eventsEnrollInfoListAdapter.notifyDataSetChanged();

        changeDeleteBtnStatus();
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

            if (eventsEntity.getCoverImageResource() != null && !Utils.isEmpty(eventsEntity.getCoverImageResource().getUrl())){
                Glide.with(this).load(eventsEntity.getCoverImageResource().getUrl())
                        .error(R.drawable.car_manage_test)
                        .into(eventsEnrollPic);
            }else{
                Glide.with(this).load(R.drawable.car_manage_test)
                        .into(eventsEnrollPic);
            }


            contentImageData.clear();
            contentImageData.add(new ImageViewInfo(eventsEntity.getCoverImageResource().getUrl()));

        }

    }

    @OnClick({R.id.toolbar_btn_back, R.id.events_enroll_add, R.id.events_enroll_goto, R.id.events_enroll_pic,
            R.id.events_enroll_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_btn_back:
                finish();
                break;
            case R.id.events_enroll_add:
                int singleMax = 5;
                if (!Utils.isEmpty(String.valueOf(eventsEntity.getSingleMaxNumber())))
                    singleMax = eventsEntity.getSingleMaxNumber();
                if (eventsEntity != null && eventEnrollInfoEntityList.size() < singleMax) {
                    eventsAddPerson();
                } else {
                    showToast("最大报名数为" + singleMax + "人");
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

                            if (Utils.isEmpty(eventEnrollInfoEntity.getGender())) {
                                showToast("请输入性别");
                                return;
                            }

                            if (Utils.isEmpty(String.valueOf(eventEnrollInfoEntity.getAge()))) {
                                showToast("请输入年龄");
                                return;
                            }
                        } else {
                            showToast("请输入报名信息");
                            return;
                        }

                    }
                }
                gotoEnrollSend();

                break;
            case R.id.events_enroll_pic:
                if (contentImageData != null && contentImageData.size() > 0) {
                    PreviewBuilder.from(CommunityEventsEnrollActivity.this)
                            .setImgs(contentImageData)
                            .setCurrentIndex(0)
                            .setSingleFling(true)
                            .setType(PreviewBuilder.IndicatorType.Number)
                            .start();
                }

                break;
            case R.id.events_enroll_delete:
                if (eventsEntity != null && eventEnrollInfoEntityList.size() > 1 && deleteFlag == 0) {
                    deleteFlag = 1;
                    eventsDeletePerson();
                }
                break;
        }
    }

    /**
     * 删除最后一个人  至少留一个人
     */
    private void eventsDeletePerson() {
        eventEnrollInfoEntityList.remove(eventEnrollInfoEntityList.size() - 1);
        eventsEnrollInfoListAdapter.notifyDataSetChanged();
        deleteFlag = 0;
        changeDeleteBtnStatus();
    }

    private void changeDeleteBtnStatus() {
        if (eventEnrollInfoEntityList.size() > 1) {
            eventsEnrollDelete.setVisibility(View.VISIBLE);
        }else{
            eventsEnrollDelete.setVisibility(View.GONE);
        }
    }

    private void gotoEnrollSend() {

        startProgressDialog(true);
        RequestParam requestParam = new RequestParam(BASE_URL + ARTICLE + "smart/event/registration", HttpMethod.Post);
        Map<String, Object> map = new HashMap<>();

        if (FileManagement.getUserInfo() != null && FileManagement.getUserInfo().getCurrentDistrict() != null)
            map.put("householdId", FileManagement.getUserInfo().getCurrentDistrict().getHouseholdId());
        else {
            showToast("请绑定房屋后再来参加活动");
            return;
        }
        map.put("eventId", eventsID);
        map.put("detailList", eventEnrollInfoEntityList);
        requestParam.setParamType(ParamType.Json);
        requestParam.setRequestMap(map);
        requestParam.setCallback(new MyCallBack<String>() {
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtil.d(result);
                BaseEntity baseEntity = JsonParse.parse(result);
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

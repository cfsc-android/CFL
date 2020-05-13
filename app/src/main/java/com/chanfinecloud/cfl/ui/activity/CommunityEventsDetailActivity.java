package com.chanfinecloud.cfl.ui.activity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.entity.BaseEntity;
import com.chanfinecloud.cfl.entity.smart.EventsEntity;
import com.chanfinecloud.cfl.entity.smart.EventsListEntity;
import com.chanfinecloud.cfl.http.HttpMethod;
import com.chanfinecloud.cfl.http.JsonParse;
import com.chanfinecloud.cfl.http.MyCallBack;
import com.chanfinecloud.cfl.http.RequestParam;
import com.chanfinecloud.cfl.ui.base.BaseActivity;
import com.chanfinecloud.cfl.util.DateUtil;

import org.xutils.common.util.LogUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.chanfinecloud.cfl.config.Config.ARTICLE;
import static com.chanfinecloud.cfl.config.Config.BASE_URL;
import static com.chanfinecloud.cfl.config.Config.DAY_MILLISECOND;

public class CommunityEventsDetailActivity extends BaseActivity {

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
    @BindView(R.id.events_detail_tile)
    TextView eventsDetailTile;
    @BindView(R.id.events_detail_start)
    TextView eventsDetailStart;
    @BindView(R.id.events_detail_end)
    TextView eventsDetailEnd;
    @BindView(R.id.events_detail_address)
    TextView eventsDetailAddress;
    @BindView(R.id.events_detail_humans)
    TextView eventsDetailHumans;
    @BindView(R.id.events_detail_pay)
    TextView eventsDetailPay;
    @BindView(R.id.events_detail_contactor)
    TextView eventsDetailContactor;
    @BindView(R.id.events_detail_phone)
    TextView eventsDetailPhone;
    @BindView(R.id.events_detail_deadline)
    TextView eventsDetailDeadline;
    @BindView(R.id.events_detail_content)
    TextView eventsDetailContent;
    @BindView(R.id.events_detail_goto)
    TextView eventsDetailGoto;
    private EventsEntity eventsEntity;
    private String eventsID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initData() {
        setContentView(R.layout.activity_community_events_detail);
        ButterKnife.bind(this);
        toolbarTvTitle.setText("活动详情");

        eventsDetailContent.setMovementMethod(ScrollingMovementMethod.getInstance());
        eventsDetailContent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //父节点不拦截子节点
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    //父节点不拦截子节点
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    //父节点拦截子节点
                    v.getParent().requestDisallowInterceptTouchEvent(false);
                }
                return false;
            }
        });

        eventsID = getIntent().getStringExtra("eventsID");

    }

    @Override
    protected void onResume() {
        super.onResume();
        getEventDetailById();
    }

    private void getEventDetailById() {

        startProgressDialog(true);
        RequestParam requestParam = new RequestParam(BASE_URL + ARTICLE + "smart/event/"+ eventsID, HttpMethod.Get);

        requestParam.setCallback(new MyCallBack<String>() {
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtil.d(result);
                BaseEntity<EventsEntity> baseEntity = JsonParse.parse(result, EventsEntity.class);
                if (baseEntity.isSuccess()) {
                    eventsEntity = baseEntity.getResult();

                    freshDetailData();

                } else {
                    showToast(baseEntity.getMessage());
                }

                stopProgressDialog();

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                showToast(ex.getMessage());
                stopProgressDialog();
            }

            @Override
            public void onFinished() {
                super.onFinished();
                stopProgressDialog();
            }
        });

        sendRequest(requestParam, false);


    }

    private void freshDetailData() {
        if (eventsEntity != null){
            eventsDetailStart.setText("开始时间：   "+ eventsEntity.getStartTime());
            eventsDetailEnd.setText("结束时间：   "+ eventsEntity.getEndTime());
            eventsDetailAddress.setText("活动地点：   "+ eventsEntity.getLocation());
            eventsDetailHumans.setText("参加人数：   "+ eventsEntity.getEnrollmentNumber());
            eventsDetailPay.setText("活动费用：   "+ eventsEntity.getActivityCosts());
            eventsDetailContactor.setText("    联系人：   "+ eventsEntity.getContactPerson());
            eventsDetailPhone.setText("联系电话：   "+ eventsEntity.getContactNumber());
            eventsDetailDeadline.setText("报名截止：   "+ eventsEntity.getRegistrationDeadline());
            eventsDetailContent.setText(eventsEntity.getContent());
            eventsDetailTile.setText(eventsEntity.getTitle());
            Date deadline = DateUtil.stringToDate(eventsEntity.getRegistrationDeadline(), DateUtil.FORMAT_DATE);
            Date endLine = DateUtil.stringToDate(eventsEntity.getEndTime(), DateUtil.FORMAT_DATE);
            Date currentDate = new Date();
            if (currentDate.getTime() < deadline.getTime() + DAY_MILLISECOND){
                eventsDetailGoto.setText("报名已截止");
                eventsDetailGoto.setClickable(false);
                eventsDetailGoto.setBackgroundResource(R.drawable.btn_gray_shape);

            }else if (currentDate.getTime() < endLine.getTime() + DAY_MILLISECOND){
                eventsDetailGoto.setText("活动已结束");
                eventsDetailGoto.setClickable(false);
                eventsDetailGoto.setBackgroundResource(R.drawable.btn_gray_shape);

            }else{

                if (eventsEntity.isParticipate()){

                    eventsDetailGoto.setText("重新报名");

                }
            }

        }
    }

    @OnClick({R.id.toolbar_btn_back, R.id.events_detail_goto})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_btn_back:
                finish();
                break;
            case R.id.events_detail_goto:
                if (eventsEntity != null){
                    String id = eventsEntity.getId();
                    Bundle bundle = new Bundle();
                    bundle.putString("enrollEventsID", id);
                    startActivity(CommunityEventsEnrollActivity.class, bundle);
                }else{
                    
                    showToast("活动性情获取失败，不能参加");
                }
                break;
        }
    }
}

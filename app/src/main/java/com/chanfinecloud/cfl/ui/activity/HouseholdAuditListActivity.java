package com.chanfinecloud.cfl.ui.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.adapter.smart.HouseholdAuditListAdapter;
import com.chanfinecloud.cfl.entity.BaseEntity;
import com.chanfinecloud.cfl.entity.smart.ApprovalStatusType;
import com.chanfinecloud.cfl.entity.smart.AuditEntity;
import com.chanfinecloud.cfl.entity.smart.AuditListEntity;
import com.chanfinecloud.cfl.http.HttpMethod;
import com.chanfinecloud.cfl.http.JsonParse;
import com.chanfinecloud.cfl.http.MyCallBack;
import com.chanfinecloud.cfl.http.ParamType;
import com.chanfinecloud.cfl.http.RequestParam;
import com.chanfinecloud.cfl.ui.base.BaseActivity;
import com.chanfinecloud.cfl.util.LogUtils;

import org.xutils.common.util.LogUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.chanfinecloud.cfl.config.Config.BASE_URL;
import static com.chanfinecloud.cfl.config.Config.BASIC;

public class HouseholdAuditListActivity extends BaseActivity {

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
    @BindView(R.id.household_audit_list)
    SwipeMenuListView householdAuditList;
    @BindView(R.id.household_audit_none)
    TextView householdAuditNone;

    private HouseholdAuditListAdapter adapter;
    private List<AuditEntity> data = new ArrayList<>();

    private String roomId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initData() {
        setContentView(R.layout.activity_household_audit_list);
        ButterKnife.bind(this);

        toolbarTvTitle.setText("审核处理");
        roomId = getIntent().getExtras().getString("roomId");

        adapter = new HouseholdAuditListAdapter(this, data);
        householdAuditList.setAdapter(adapter);
        // 为ListView设置创建器
        householdAuditList.setMenuCreator(creator);
        // 第2步：为ListView设置菜单项点击监听器，来监听菜单项的点击事件
        householdAuditList.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                LogUtil.d("position:" + position + " index:" + index);
                AuditEntity auditEntity = data.get(position);

                if (auditEntity.getStatus() == ApprovalStatusType.Audit.getType()) {
                    if (index == 0) {
                        refuseAudit(position);
                    } else {
                        passAudit(position);
                    }
                } else {
                    showToast("已经处理过了，需要重新申请");
                }

                return false;
            }
        });
        getData();


    }

    private void getData() {

        Map<String, Object> map = new HashMap<>();
//        map.put("householdId", FileManagement.getUserInfo().getId());
        map.put("roomId", roomId);
        map.put("pageNo", 1);
        map.put("pageSize", 100);
        RequestParam requestParam = new RequestParam(BASE_URL + BASIC + "basic/verify/page", HttpMethod.Get);
        requestParam.setRequestMap(map);
        requestParam.setCallback(new MyCallBack<String>() {
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d(result);
                BaseEntity<AuditListEntity> baseEntity = JsonParse.parse(result, AuditListEntity.class);
                if (baseEntity.isSuccess()) {
                    if (baseEntity.getResult().getCount() == 0) {
                        householdAuditNone.setVisibility(View.VISIBLE);
                        householdAuditList.setVisibility(View.GONE);
                    } else {
                        householdAuditList.setVisibility(View.VISIBLE);
                        householdAuditNone.setVisibility(View.GONE);
                        data.clear();
                        data.addAll(baseEntity.getResult().getData());
                        adapter.setData(data);
                        householdAuditList.setAdapter(adapter);
                    }

                } else {
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
     * 通过审核
     *
     * @param position
     */
    private void passAudit(int position) {
        AuditEntity audit = data.get(position);
        Map<String, String> map = new HashMap<>();
        map.put("householdId", audit.getHouseholdId());
        map.put("id", audit.getId());
        map.put("roomId", audit.getRoomId());
        map.put("type", audit.getType());
        RequestParam requestParam = new RequestParam(BASE_URL + BASIC + "basic/verify/bind", HttpMethod.Get);
        requestParam.setRequestMap(map);
        requestParam.setCallback(new MyCallBack<String>() {
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d(result);
                BaseEntity baseEntity = JsonParse.parse(result);
                if (baseEntity.isSuccess()) {
                    getData();
                } else {
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
     * 审核拒绝
     *
     * @param position
     */
    private void refuseAudit(int position) {
        AuditEntity audit = data.get(position);
        Map<String, String> map = new HashMap<>();
        map.put("id", audit.getId());
        RequestParam requestParam = new RequestParam(BASE_URL + BASIC + "basic/verify", HttpMethod.Put);
        requestParam.setRequestMap(map);
        requestParam.setParamType(ParamType.Json);
        requestParam.setCallback(new MyCallBack<String>() {
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d(result);
                BaseEntity baseEntity = JsonParse.parse(result);
                if (baseEntity.isSuccess()) {
                    getData();
                } else {
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

            switch (menu.getViewType()) {
                case 1:
                    SwipeMenuItem refuseItem = new SwipeMenuItem(
                            getApplicationContext());
                    refuseItem.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
                    refuseItem.setWidth(dp2px(90));
                    refuseItem.setTitle("拒绝");
                    refuseItem.setTitleSize(18);
                    refuseItem.setTitleColor(Color.WHITE);
                    menu.addMenuItem(refuseItem);
                    SwipeMenuItem passItem = new SwipeMenuItem(
                            getApplicationContext());
                    passItem.setBackground(new ColorDrawable(Color.rgb(19, 173, 87)));
                    passItem.setWidth(dp2px(90));
                    passItem.setTitle("通过");
                    passItem.setTitleSize(18);
                    passItem.setTitleColor(Color.WHITE);
                    menu.addMenuItem(passItem);
                    break;

            }

        }
    };

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    @OnClick(R.id.toolbar_btn_back)
    public void onViewClicked() {
        finish();
    }
}

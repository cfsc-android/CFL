package com.chanfinecloud.cfl.ui.activity.minefeatures;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.adapter.smart.HouseholdPagerAdapter;
import com.chanfinecloud.cfl.entity.eventbus.EventBusMessage;
import com.chanfinecloud.cfl.ui.activity.HouseManageActivity;
import com.chanfinecloud.cfl.ui.base.BaseActivity;
import com.chanfinecloud.cfl.ui.fragment.minefragment.CurrentRoomFragment;
import com.chanfinecloud.cfl.ui.fragment.minefragment.OtherRoomFragment;
import com.chanfinecloud.cfl.weidgt.easyindicator.EasyIndicator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HouseHoldActivity extends BaseActivity {

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
    @BindView(R.id.household_ei_tab)
    EasyIndicator householdEiTab;
    @BindView(R.id.household_vp_tab)
    ViewPager householdVpTab;

    private HouseholdPagerAdapter adapter;
    private ArrayList<Fragment> data=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initData() {
        setContentView(R.layout.activity_house_hold);
        ButterKnife.bind(this);

        toolbarTvTitle.setText("我的房屋");
        toolbarBtnAction.setVisibility(View.GONE);
        toolbarTvAction.setVisibility(View.VISIBLE);
        toolbarTvAction.setText("管理房屋");

        data.add(new CurrentRoomFragment());
        data.add(new OtherRoomFragment());
        adapter=new HouseholdPagerAdapter(getSupportFragmentManager(),data);

        householdEiTab.setTabTitles(new String[]{"当前房屋","其他房屋","",""});
        householdEiTab.setViewPager(householdVpTab, adapter);
        householdVpTab.setOffscreenPageLimit(1);
        householdVpTab.setCurrentItem(0);

        EventBus.getDefault().register(this);
    }

    @OnClick({R.id.toolbar_btn_back, R.id.toolbar_tv_title, R.id.toolbar_tv_action})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_btn_back:
                finish();
                break;
            case R.id.toolbar_tv_title:
                break;
            case R.id.toolbar_tv_action:
                startActivity(HouseManageActivity.class);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(EventBusMessage message){
        if("householdAudit".equals(message.getMessage())){
            householdVpTab.setCurrentItem(1);
        }else if ("houseRefresh".equals(message.getMessage())){
            householdVpTab.setCurrentItem(0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
    }
}

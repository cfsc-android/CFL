package com.chanfinecloud.cfl.ui;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.entity.BaseEntity;
import com.chanfinecloud.cfl.entity.eventbus.EventBusMessage;
import com.chanfinecloud.cfl.entity.smart.CurrentDistrictEntity;
import com.chanfinecloud.cfl.entity.smart.OrderStatusEntity;
import com.chanfinecloud.cfl.entity.smart.OrderTypeListEntity;
import com.chanfinecloud.cfl.http.HttpMethod;
import com.chanfinecloud.cfl.http.JsonParse;
import com.chanfinecloud.cfl.http.MyCallBack;
import com.chanfinecloud.cfl.http.RequestParam;
import com.chanfinecloud.cfl.ui.activity.UnLockListActivity;
import com.chanfinecloud.cfl.ui.activity.minefeatures.HouseHoldActivity;
import com.chanfinecloud.cfl.ui.base.BaseActivity;
import com.chanfinecloud.cfl.ui.fragment.mainfrg.HomeFragment;
import com.chanfinecloud.cfl.ui.fragment.mainfrg.MineFragment;
import com.chanfinecloud.cfl.util.FileManagement;
import com.chanfinecloud.cfl.util.LogUtils;
import com.chanfinecloud.cfl.util.LynActivityManager;
import com.chanfinecloud.cfl.weidgt.NoScrollViewPager;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

import static com.chanfinecloud.cfl.config.Config.BASE_URL;
import static com.chanfinecloud.cfl.config.Config.WORKORDER;

/**
 * Created by damien on 2020/3/26.
 * Version: 1.0
 * Describe:  主页Activity
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.vp_content)
    NoScrollViewPager vpContent;
    @BindView(R.id.rb_home)
    RadioButton rbHome;
    @BindView(R.id.rb_mine)
    RadioButton rbMine;
    @BindView(R.id.rg_main)
    RadioGroup rgMain;
    @BindView(R.id.main_btn_unlock)
    TextView mainBtnUnlock;


    private boolean bind;
    private Context context;
    private FragmentManager fragmentManager;
    private List<Fragment> fragmentList = new ArrayList<>();
    private Fragment home, mine;
    private long time = 0;


    @Override
    protected void initData() {
        setContentView(R.layout.activity_main);
        setAliasAndTag();
        ButterKnife.bind(this);
        context = this;
        initView();
        initFileData();
        CurrentDistrictEntity currentDistrict = FileManagement.getUserInfo().getCurrentDistrict();
        if (currentDistrict != null && !TextUtils.isEmpty(currentDistrict.getRoomId())) {
            bind = true;
        } else {
            bind = false;
            showUnBindView();
        }
        EventBus.getDefault().register(this);
        //showUnBindView();
    }

    private void initView() {
        fragmentManager = getSupportFragmentManager();
        home = new HomeFragment();
        mine = new MineFragment();
        fragmentList.add(home);
        fragmentList.add(mine);
        vpContent.setOffscreenPageLimit(fragmentList.size());
        vpContent.setAdapter(new MyFrageStatePagerAdapter(getSupportFragmentManager()));
        vpContent.setNoScroll(true);
        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                vpContent.setCurrentItem(rgMain.indexOfChild(rgMain.findViewById(checkedId)));
            }
        });
        changeView(0);
    }

    /**
     * 登录后初始用户数据
     */
    private void initFileData() {
        initOrderType();
        initOrderStatus();
        initComplainType();
        initComplainStatus();
    }

    /**
     * 初始化工单类型
     */
    private void initOrderType() {
        RequestParam requestParam = new RequestParam(BASE_URL + WORKORDER + "work/orderType/pageByCondition", HttpMethod.Get);
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("pageNo", "1");
        requestMap.put("pageSize", "100");
        requestParam.setRequestMap(requestMap);
        requestParam.setCallback(new MyCallBack<String>() {
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d(result);
                BaseEntity<OrderTypeListEntity> baseEntity = JsonParse.parse(result, OrderTypeListEntity.class);
                if (baseEntity.isSuccess()) {
                    FileManagement.setOrderType(baseEntity.getResult().getData());
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
     * 初始化工单状态
     */
    private void initOrderStatus() {

        RequestParam requestParam = new RequestParam(BASE_URL + WORKORDER + "work/orderStatus/selectWorkorderStatus", HttpMethod.Get);

        requestParam.setCallback(new MyCallBack<String>() {
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d(result);
                BaseEntity baseEntity = JsonParse.parse(result);
                if (baseEntity.isSuccess()) {
                    Type type = new TypeToken<List<OrderStatusEntity>>() {
                    }.getType();
                    List<OrderStatusEntity> list = (List<OrderStatusEntity>) JsonParse.parseList(result, type);
                    FileManagement.setOrderStatus(list);
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
     * 初始化投诉类型
     */
    private void initComplainType() {

        RequestParam requestParam = new RequestParam(BASE_URL + WORKORDER + "work/complaintType/pageByCondition", HttpMethod.Get);
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("pageNo", "1");
        requestMap.put("pageSize", "100");
        requestParam.setRequestMap(requestMap);
        requestParam.setCallback(new MyCallBack<String>() {
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d(result);
                BaseEntity<OrderTypeListEntity> baseEntity = JsonParse.parse(result, OrderTypeListEntity.class);
                if (baseEntity.isSuccess()) {
                    FileManagement.setComplainType(baseEntity.getResult().getData());
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
     * 初始化投诉状态
     */
    private void initComplainStatus() {

        RequestParam requestParam = new RequestParam(BASE_URL + WORKORDER + "work/complaintStatus/complaintStatusList", HttpMethod.Get);
        Map<String, String> requestMap = new HashMap<>();
        requestParam.setCallback(new MyCallBack<String>() {
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d(result);
                BaseEntity baseEntity = JsonParse.parse(result);
                if (baseEntity.isSuccess()) {
                    Type type = new TypeToken<List<OrderStatusEntity>>() {
                    }.getType();
                    List<OrderStatusEntity> list = (List<OrderStatusEntity>) JsonParse.parseList(result, type);
                    FileManagement.setComplainStatus(list);
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


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(EventBusMessage message) {
        if ("projectSelect".equals(message.getMessage())) {
            changeView(0);
        } else if ("unbind".equals(message.getMessage())) {
            LogUtils.d("=============================" + "unbind" + "==========================");
            showUnBindView();
        }
    }

    private void showUnBindView() {
        View unSelectView = LayoutInflater.from(this).inflate(R.layout.dialog_unselect_room, null);
        Button selectBtn=unSelectView.findViewById(R.id.btn_select_room);
        selectBtn.setOnClickListener(this);
        Button unSelectBtn=unSelectView.findViewById(R.id.btn_un_select_room);
        unSelectBtn.setOnClickListener(this);
        ImageView closeBtn=unSelectView.findViewById(R.id.iv_un_select_room);
        closeBtn.setOnClickListener(this);
        startCustomerDialog(unSelectView, false);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 判断按返回键时
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (new Date().getTime() - time < 2000 && time != 0) {
                /*Intent home = new Intent(Intent.ACTION_MAIN);
                home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                home.addCategory(Intent.CATEGORY_HOME);
                startActivity(home);*/
                LynActivityManager.getInstance().removeAllActivity();
                System.exit(0);

            } else {
                showToast("再按一次退出");
                time = new Date().getTime();
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick({R.id.main_btn_unlock, R.id.rb_home, R.id.rb_mine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.main_btn_unlock:
                if (bind) {
                    startActivity(UnLockListActivity.class);
                } else {
                    EventBus.getDefault().post(new EventBusMessage<>("unbind"));
                }
                break;
            case R.id.rb_home:
                changeView(0);
                break;
            case R.id.rb_mine:
                changeView(1);
                break;

        }
    }


    /**
     * 设置极光推送的alias（别名）和tag(标签)
     */
    private void setAliasAndTag() {
        JPushInterface.setAlias(this, 0x01, "ZXL");//别名（userId）

        Set<String> tagSet = new LinkedHashSet<>();
        tagSet.add("YZ");//身份（YG,YK，YZ，ZK，JS...员工端直接写死YG，业主则用当前项目身份）
        tagSet.add("P_234ab909de");//项目（'P_'+业主当前项目Id,员工端多个项目Id则加多个）
        tagSet.add("D_5656ac65de5b");//部门（'D_'+员工的部门Id）
        JPushInterface.setTags(this, 0x02, tagSet);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_select_room:
                startActivity(HouseHoldActivity.class);
                stopProgressDialog();
                break;
            case R.id.btn_un_select_room:
                stopProgressDialog();
                break;
            case R.id.iv_un_select_room:
                stopProgressDialog();
                break;
        }
    }


    /**
     * 定义ViewPager适配器。
     */
    class MyFrageStatePagerAdapter extends FragmentStatePagerAdapter {

        public MyFrageStatePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        /**
         * 每次更新完成ViewPager的内容后，调用该接口，此处复写主要是为了让导航按钮上层的覆盖层能够动态的移动
         */
        @Override
        public void finishUpdate(ViewGroup container) {
            super.finishUpdate(container);//这句话要放在最前面，否则会报错
        }
    }

    /**
     * 手动设置ViewPager要显示的视图
     *
     * @param desTab
     */
    private void changeView(int desTab) {
        vpContent.setCurrentItem(desTab, true);
        imageMove(desTab);
    }

    /**
     * 移动覆盖层
     *
     * @param moveToTab 目标Tab，也就是要移动到的导航选项按钮的位置
     *                  第一个导航按钮对应0，第二个对应1，以此类推
     */
    private void imageMove(int moveToTab) {
        switch (moveToTab) {
            case 0:
                rbHome.setChecked(true);
                break;
            case 1:
                rbMine.setChecked(true);
                break;
        }
    }

}

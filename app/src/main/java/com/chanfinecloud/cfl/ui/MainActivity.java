package com.chanfinecloud.cfl.ui;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
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
import android.widget.Toast;

import com.chanfinecloud.cfl.CFLApplication;
import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.entity.BaseEntity;
import com.chanfinecloud.cfl.entity.eventbus.EventBusMessage;
import com.chanfinecloud.cfl.entity.smart.CurrentDistrictEntity;
import com.chanfinecloud.cfl.entity.smart.HouseholdRoomEntity;
import com.chanfinecloud.cfl.entity.smart.OrderStatusEntity;
import com.chanfinecloud.cfl.entity.smart.OrderTypeListEntity;
import com.chanfinecloud.cfl.entity.smart.RoomEntity;
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
import com.chanfinecloud.cfl.util.UserInfoUtil;
import com.chanfinecloud.cfl.util.permission.AnyPermission;
import com.chanfinecloud.cfl.util.permission.RequestInterceptor;
import com.chanfinecloud.cfl.util.permission.RequestListener;
import com.chanfinecloud.cfl.util.permission.RuntimeRequester;
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
import java.util.concurrent.Executor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import me.leolin.shortcutbadger.ShortcutBadger;
import per.goweii.anylayer.AnyLayer;
import per.goweii.anylayer.Layer;

import static com.chanfinecloud.cfl.config.Config.BASE_URL;
import static com.chanfinecloud.cfl.config.Config.SET_JPUSH_ALIAS_SEQUENCE;
import static com.chanfinecloud.cfl.config.Config.SET_JPUSH_TAGS_SEQUENCE;
import static com.chanfinecloud.cfl.config.Config.WORKORDER;
import static com.chanfinecloud.cfl.util.FileManagement.getUserInfo;

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


    private List<Fragment> fragmentList = new ArrayList<>();
    private Fragment home, mine;
    private long time = 0;
    private RuntimeRequester mRuntimeRequester;
    private int isFirstIn = 0;


    @Override
    protected void initData() {
        setContentView(R.layout.activity_main);
        setAliasAndTag();
        ButterKnife.bind(this);
        initView();
        initFileData();
        isFirstIn = 0;
        CurrentDistrictEntity currentDistrict = getUserInfo().getCurrentDistrict();
        if (currentDistrict != null && !TextUtils.isEmpty(currentDistrict.getRoomId())) {
            CFLApplication.bind = true;
        } else {
            CFLApplication.bind = false;
            showUnBindView();
        }
        EventBus.getDefault().register(this);
        //showUnBindView();
    }

    private void initView() {
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
        UserInfoUtil.initAvatarResource(null);//缓存用户头像
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
                Type type = new TypeToken<List<OrderStatusEntity>>() {}.getType();
                BaseEntity<List<OrderStatusEntity>> baseEntity = JsonParse.parse(result,type);
                if (baseEntity.isSuccess()) {
                    FileManagement.setOrderStatus(baseEntity.getResult());
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
                Type type = new TypeToken<List<OrderStatusEntity>>() {}.getType();
                BaseEntity<List<OrderStatusEntity>> baseEntity = JsonParse.parse(result,type);
                if (baseEntity.isSuccess()) {
                    FileManagement.setComplainStatus(baseEntity.getResult());
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
        LogUtils.d(message);
        if ("projectSelect".equals(message.getMessage())) {
            CurrentDistrictEntity currentDistrict = getUserInfo().getCurrentDistrict();
            if (currentDistrict != null && !TextUtils.isEmpty(currentDistrict.getRoomId())) {
                CFLApplication.bind = true;
            } else {
                CFLApplication.bind = false;
            }
            changeView(0);//切换到首页
            resetTag();
        }else if("AuditPass".equals(message.getMessage())){
            CurrentDistrictEntity currentDistrict = getUserInfo().getCurrentDistrict();
            if (currentDistrict != null && !TextUtils.isEmpty(currentDistrict.getRoomId())) {
                CFLApplication.bind = true;
            } else {
                CFLApplication.bind = false;
            }
        }else if ("unbind".equals(message.getMessage())) {
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
                if (CFLApplication.bind) {
                    startActivity(UnLockListActivity.class);
                } else {
                    showUnBindView();
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
        if(FileManagement.getPushFlag()){
            JPushInterface.setAlias(this,SET_JPUSH_ALIAS_SEQUENCE, getUserInfo().getId());
            Set<String> tagSet = new LinkedHashSet<>();
            CurrentDistrictEntity currentDistrict= getUserInfo().getCurrentDistrict();
            if(!TextUtils.isEmpty(currentDistrict.getProjectId())){
                tagSet.add("P_"+currentDistrict.getProjectId());//项目（'P_'+业主当前项目Id,员工端多个项目Id则加多个）
            }
            if(!TextUtils.isEmpty(currentDistrict.getRoomId())){
                List<HouseholdRoomEntity> list= getUserInfo().getRoomList();
                if (list != null){
                    for (int i = 0; i < list.size(); i++) {
                        if(list.get(i).getId().equals(currentDistrict.getRoomId())){
                            tagSet.add(list.get(i).getHouseholdType());
                        }
                    }

                }else{
                    tagSet.add("YK");
                }
            }else{
                tagSet.add("YK");
            }
            if(tagSet.size()>0){
                JPushInterface.setTags(this, SET_JPUSH_TAGS_SEQUENCE, tagSet);
            }
        }
    }

    /**
     * 重置极光推送的tag（标签）
     */
    private void resetTag(){
        Set<String> tagSet = new LinkedHashSet<>();
        CurrentDistrictEntity currentDistrict= getUserInfo().getCurrentDistrict();
        if(!TextUtils.isEmpty(currentDistrict.getProjectId())){
            tagSet.add("P_"+currentDistrict.getProjectId());//项目（'P_'+业主当前项目Id,员工端多个项目Id则加多个）
        }
        if(!TextUtils.isEmpty(currentDistrict.getRoomId())){
            List<HouseholdRoomEntity> list= getUserInfo().getRoomList();
            for (int i = 0; i < list.size(); i++) {
                if(list.get(i).getId().equals(currentDistrict.getRoomId())){
                    tagSet.add(list.get(i).getHouseholdType());
                }
            }

        }else{
            tagSet.add("YK");
        }
        if(tagSet.size()>0){
            JPushInterface.setTags(this, SET_JPUSH_TAGS_SEQUENCE, tagSet);
        }
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
                break;
//            case R.id.btn_un_select_room:
//                stopProgressDialog();
//                break;
//            case R.id.iv_un_select_room:
//                stopProgressDialog();
//                break;
        }
        stopProgressDialog();
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


    @Override
    protected void onResume() {
        super.onResume();

        ShortcutBadger.removeCount(getApplicationContext()); //for 1.1.4+

        if ( isFirstIn == 0){
            isFirstIn = 1;
        }else{
            //动态请求权通知权限
            // TODO: 2020/4/20 fangkaiceshi
            //requestOverlay();
        }

    }

    //获取悬浮窗权限
    public void requestOverlay() {

        AnyPermission.with(this).overlay()
                .onWithoutPermission(new RequestInterceptor<Void>() {
                    @Override
                    public void intercept(@NonNull final Void data, @NonNull final Executor executor) {
                        AnyLayer.dialog(MainActivity.this)
                                .contentView(R.layout.dialog_runtime_before_request)
                                .backgroundColorRes(R.color.dialog_bg)
                                .cancelableOnTouchOutside(false)
                                .cancelableOnClickKeyBack(false)
                                .bindData(new Layer.DataBinder() {
                                    @Override
                                    public void bindData(Layer  anyLayer) {
                                        TextView tvTitle = anyLayer.getView(R.id.tv_dialog_permission_title);
                                        TextView tvDescription = anyLayer.getView(R.id.tv_dialog_permission_description);
                                        TextView tvNext = anyLayer.getView(R.id.tv_dialog_permission_next);

                                        tvNext.setText("去打开");
                                        tvTitle.setText("悬浮窗，锁屏显示");
                                        tvDescription.setText("我们将开始请求悬浮窗，锁屏显示权限");
                                    }
                                })
                                .onClickToDismiss(new Layer.OnClickListener() {
                                    @Override
                                    public void onClick(Layer layer, View v) {
                                        executor.execute();
                                    }
                                }, R.id.tv_dialog_permission_next)
                                .onClickToDismiss(new Layer.OnClickListener() {
                                    @Override
                                    public void onClick(Layer anyLayer, View v) {
                                        executor.cancel();
                                    }
                                }, R.id.tv_dialog_permission_close)
                                .show();
                    }
                })
                .request(new RequestListener() {
                    @Override
                    public void onSuccess() {
                        // Toast.makeText(MainActivity.this, "成功1", Toast.LENGTH_SHORT).show();
                        requestNotificationShow();
                    }

                    @Override
                    public void onFailed() {
                        Toast.makeText(MainActivity.this, "权限获取失败", Toast.LENGTH_SHORT).show();
                        // requestNotificationShow();
                    }
                });
    }

    private void requestNotificationShow() {
        AnyPermission.with(this).notificationShow()
                .onWithoutPermission(new RequestInterceptor<Void>() {
                    @Override
                    public void intercept(@NonNull final Void data, @NonNull final Executor executor) {
                        AnyLayer.dialog(MainActivity.this)
                                .contentView(R.layout.dialog_runtime_before_request)
                                .backgroundColorRes(R.color.dialog_bg)
                                .cancelableOnTouchOutside(false)
                                .cancelableOnClickKeyBack(false)
                                .bindData(new Layer.DataBinder() {
                                    @Override
                                    public void bindData(Layer anyLayer) {
                                        TextView tvTitle = anyLayer.getView(R.id.tv_dialog_permission_title);
                                        TextView tvDescription = anyLayer.getView(R.id.tv_dialog_permission_description);
                                        TextView tvNext = anyLayer.getView(R.id.tv_dialog_permission_next);

                                        tvNext.setText("去打开");
                                        tvTitle.setText("通知，道全锁屏通知");
                                        tvDescription.setText("我们将请求开启通知及锁屏通知权限");
                                    }
                                })
                                .onClickToDismiss(new Layer.OnClickListener() {
                                    @Override
                                    public void onClick(Layer anyLayer, View v) {
                                        executor.execute();
                                    }
                                }, R.id.tv_dialog_permission_next)
                                .onClickToDismiss(new Layer.OnClickListener() {
                                    @Override
                                    public void onClick(Layer anyLayer, View v) {
                                        executor.cancel();
                                    }
                                }, R.id.tv_dialog_permission_close)
                                .show();
                    }
                })
                .request(new RequestListener() {
                    @Override
                    public void onSuccess() {
                        // Toast.makeText(MainActivity.this, "成功2", Toast.LENGTH_SHORT).show();
                        requestRuntime();
                    }

                    @Override
                    public void onFailed() {
                        Toast.makeText(MainActivity.this, "获取通知权限失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mRuntimeRequester != null) {
            mRuntimeRequester.onActivityResult(requestCode);
        }
    }

    private void requestRuntime() {
        mRuntimeRequester = AnyPermission.with(this).runtime(1)
                .permissions(Manifest.permission.DISABLE_KEYGUARD)
                .onBeforeRequest(new RequestInterceptor<String>() {
                    @Override
                    public void intercept(@NonNull final String permission, @NonNull final Executor executor) {
                        AnyLayer.dialog(MainActivity.this)
                                .contentView(R.layout.dialog_runtime_before_request)
                                .backgroundColorRes(R.color.dialog_bg)
                                .cancelableOnTouchOutside(false)
                                .cancelableOnClickKeyBack(false)
                                .bindData(new Layer.DataBinder() {
                                    @Override
                                    public void bindData(Layer anyLayer) {
                                        TextView tvTitle = anyLayer.getView(R.id.tv_dialog_permission_title);
                                        TextView tvDescription = anyLayer.getView(R.id.tv_dialog_permission_description);
                                        TextView tvNext = anyLayer.getView(R.id.tv_dialog_permission_next);

                                        tvNext.setText("去授权");
                                        tvTitle.setText(AnyPermission.with(MainActivity.this).name(permission));
                                        tvDescription.setText("我们将开始请求\"" + AnyPermission.with(MainActivity.this).name(permission) + "\"权限");
                                    }
                                })
                                .onClickToDismiss(new Layer.OnClickListener() {
                                    @Override
                                    public void onClick(Layer anyLayer, View v) {
                                        executor.execute();
                                    }
                                }, R.id.tv_dialog_permission_next)
                                .onClickToDismiss(new Layer.OnClickListener() {
                                    @Override
                                    public void onClick(Layer anyLayer, View v) {
                                        executor.cancel();
                                    }
                                }, R.id.tv_dialog_permission_close)
                                .show();
                    }
                })
                .onBeenDenied(new RequestInterceptor<String>() {
                    @Override
                    public void intercept(@NonNull final String permission, @NonNull final Executor executor) {
                        AnyLayer.dialog(MainActivity.this)
                                .contentView(R.layout.dialog_runtime_before_request)
                                .backgroundColorRes(R.color.dialog_bg)
                                .cancelableOnTouchOutside(false)
                                .cancelableOnClickKeyBack(false)
                                .bindData(new Layer.DataBinder() {
                                    @Override
                                    public void bindData(Layer anyLayer) {
                                        TextView tvTitle = anyLayer.getView(R.id.tv_dialog_permission_title);
                                        TextView tvDescription = anyLayer.getView(R.id.tv_dialog_permission_description);
                                        TextView tvNext = anyLayer.getView(R.id.tv_dialog_permission_next);

                                        tvNext.setText("重新授权");
                                        tvTitle.setText(AnyPermission.with(MainActivity.this).name(permission));
                                        tvDescription.setText("啊哦，\"" + AnyPermission.with(MainActivity.this).name(permission) + "\"权限被拒了");
                                    }
                                })
                                .onClickToDismiss(new Layer.OnClickListener() {
                                    @Override
                                    public void onClick(Layer anyLayer, View v) {
                                        executor.execute();
                                    }
                                }, R.id.tv_dialog_permission_next)
                                .onClickToDismiss(new Layer.OnClickListener() {
                                    @Override
                                    public void onClick(Layer anyLayer, View v) {
                                        executor.cancel();
                                    }
                                }, R.id.tv_dialog_permission_close)
                                .show();
                    }
                })
                .onGoSetting(new RequestInterceptor<String>() {
                    @Override
                    public void intercept(@NonNull final String permission, @NonNull final Executor executor) {
                        AnyLayer.dialog(MainActivity.this)
                                .contentView(R.layout.dialog_runtime_before_request)
                                .backgroundColorRes(R.color.dialog_bg)
                                .cancelableOnTouchOutside(false)
                                .cancelableOnClickKeyBack(false)
                                .bindData(new Layer.DataBinder() {
                                    @Override
                                    public void bindData(Layer anyLayer) {
                                        TextView tvTitle = anyLayer.getView(R.id.tv_dialog_permission_title);
                                        TextView tvDescription = anyLayer.getView(R.id.tv_dialog_permission_description);
                                        TextView tvNext = anyLayer.getView(R.id.tv_dialog_permission_next);

                                        tvNext.setText("去设置");
                                        tvTitle.setText(AnyPermission.with(MainActivity.this).name(permission));
                                        tvDescription.setText("不能禁止\"" + AnyPermission.with(MainActivity.this).name(permission) + "\"权限");
                                    }
                                })
                                .onClickToDismiss(new Layer.OnClickListener() {
                                    @Override
                                    public void onClick(Layer anyLayer, View v) {
                                        executor.execute();
                                    }
                                }, R.id.tv_dialog_permission_next)
                                .onClickToDismiss(new Layer.OnClickListener() {
                                    @Override
                                    public void onClick(Layer anyLayer, View v) {
                                        executor.cancel();
                                    }
                                }, R.id.tv_dialog_permission_close)
                                .show();
                    }
                })
                .request(new RequestListener() {

                    @Override
                    public void onSuccess() {
                        //  Toast.makeText(MainActivity.this, "成功3", Toast.LENGTH_SHORT).show();

                        // requestNotificationAccess();
                        //有些手机会一直闪
                        /*if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O && LocalOSUtils.isMIUI()){

                            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1){
                                gotoMiuiPermission25();
                            } else {
                                gotoMiuiPermission();
                            }
                        }else{
                            gotoMiuiPermission25();
                        }*/
                    }

                    @Override
                    public void onFailed() {
                        Toast.makeText(MainActivity.this, "获取权限失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void gotoMiuiPermission25() {
        try {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getPackageName(), null);
            intent.setData(uri);
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "跳转失败", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    /**
     * 跳转到miui的权限管理页面
     */
    private void gotoMiuiPermission() {
        Intent i = new Intent("miui.intent.action.APP_PERM_EDITOR");
        ComponentName componentName = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
        i.setComponent(componentName);
        i.putExtra("extra_pkgname", getPackageName());
        Toast.makeText(MainActivity.this, "成功4", Toast.LENGTH_SHORT).show();
        try {
            startActivity(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}

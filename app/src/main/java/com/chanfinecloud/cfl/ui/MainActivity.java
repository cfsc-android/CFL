package com.chanfinecloud.cfl.ui;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.chanfinecloud.cfl.CFLApplication;
import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.config.HikConfig;
import com.chanfinecloud.cfl.entity.BaseEntity;
import com.chanfinecloud.cfl.entity.eventbus.EventBusMessage;
import com.chanfinecloud.cfl.entity.smart.CurrentDistrictEntity;
import com.chanfinecloud.cfl.entity.smart.HouseholdRoomEntity;
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
import com.chanfinecloud.cfl.util.UserInfoUtil;
import com.chanfinecloud.cfl.weidgt.NoScrollViewPager;
import com.google.gson.reflect.TypeToken;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RequestExecutor;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import me.leolin.shortcutbadger.ShortcutBadger;

import static android.provider.Settings.EXTRA_APP_PACKAGE;
import static android.provider.Settings.EXTRA_CHANNEL_ID;
import static com.chanfinecloud.cfl.config.Config.BASE_URL;
import static com.chanfinecloud.cfl.config.Config.IOT;
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
    private int isFirstIn = 0;
    private boolean permissionFlag = false;
    private boolean isInitCloudSDK = false;

    @Override
    protected void initData() {
        setContentView(R.layout.activity_main);
        setAliasAndTag();
        ButterKnife.bind(this);
        isInitCloudSDK = false;
        initView();
        initFileData();
        isFirstIn = 0;
        permissionFlag = false;
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

    private void initCloudOpenSDK() {

        if (CFLApplication.bind && !isInitCloudSDK){
            RequestParam requestParam = new RequestParam(BASE_URL + IOT + "community/api/access/v1/poseidon/token", HttpMethod.Get);
            Map<String, String> requestMap = new HashMap<>();
            requestMap.put("phaseId",FileManagement.getUserInfo().getCurrentDistrict().getPhaseId());
            requestParam.setRequestMap(requestMap);
            requestParam.setCallback(new MyCallBack<String>() {
                @Override
                public void onSuccess(String result) {
                    super.onSuccess(result);
                    LogUtils.d(result);
                    BaseEntity baseEntity = JsonParse.parse(result);
                    if (baseEntity.isSuccess()) {
                        String sdkToken = String.valueOf(baseEntity.getResult());
                        HikConfig.OAUTH_TOKEN = sdkToken;
                        CFLApplication.initCloudOpenSDKConfig();

                    } else {
                        showToast(baseEntity.getMessage());
                    }
                }

                @Override
                public void onCancelled(CancelledException cex) {
                    super.onCancelled(cex);
                    Log.e("initCloudOpenSDK", "onCancelled: "+cex.getMessage() );
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    super.onError(ex, isOnCallback);
                    showToast(ex.getMessage());
                }
            });
            sendRequest(requestParam, false);


        }


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
            isInitCloudSDK = false;
            initCloudOpenSDK();
        }else if("AuditPass".equals(message.getMessage())){
            CurrentDistrictEntity currentDistrict = getUserInfo().getCurrentDistrict();
            if (currentDistrict != null && !TextUtils.isEmpty(currentDistrict.getRoomId())) {
                CFLApplication.bind = true;
            } else {
                CFLApplication.bind = false;
            }
        }else if ("unbind".equals(message.getMessage())) {
            showUnBindView();
        }else if ("CloudOpenSDKInit".equals(message.getMessage())){
            isInitCloudSDK = true;
        }else if ("CloudOpenSDKNotInit".equals(message.getMessage())){
            isInitCloudSDK = false;
            //不成功不放弃 这似乎不太好吧
            initCloudOpenSDK();
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
        EventBus.getDefault().unregister(this);
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
            if (!permissionFlag){
                //悬浮窗权限不强求吧
                //requestAndOverlay();
                requestAndNotificationListener();
            }

        }
        if (!isInitCloudSDK){
            initCloudOpenSDK();//初始化海康SDK
        }

    }

    public void requestAndOverlay(){
        AndPermission.with(this)
                .overlay()
                .rationale(new Rationale<Void>() {
                    @Override
                    public void showRationale(Context context, Void data, RequestExecutor executor) {

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, R.style.dialog);
                        View view = LayoutInflater.from(context).inflate(R.layout.dialog_runtime_before_request, null);
                        TextView tvTitle = view.findViewById(R.id.tv_dialog_permission_title);
                        TextView tvDescription = view.findViewById(R.id.tv_dialog_permission_description);
                        TextView tvNext = view.findViewById(R.id.tv_dialog_permission_next);
                        TextView tvClose = view.findViewById(R.id.tv_dialog_permission_close);

                        tvNext.setText("去打开");
                        tvTitle.setText("悬浮窗显示");
                        tvDescription.setText("我们将开始请求悬浮窗显示权限");
                        // 设置我们自己定义的布局文件作为弹出框的Content
                        alertDialogBuilder.setView(view);
                        //按对话框以外的地方不起作用,按返回键也不起作用,防止点击外面对话框消失
                        alertDialogBuilder.setCancelable(false);
                        //这个位置十分重要，只有位于这个位置逻辑才是正确的
                        AlertDialog dialog = alertDialogBuilder.show();
                        tvNext.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                executor.execute();
                                dialog.dismiss();
                            }
                        });

                        tvClose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                executor.cancel();
                                dialog.dismiss();
                            }
                        });

                    }
                })
                .onGranted(permissions -> {
                    // Storage permission are allowed.
                    requestAndNotificationListener();
                })
                .onDenied(permissions -> {
                    // Storage permission are not allowed.
                    Toast.makeText(MainActivity.this, "悬浮窗权限获取失败", Toast.LENGTH_SHORT).show();
                })
                .start();

    }

    private void requestAndNotificationListener() {
        AndPermission.with(this)
                .notification()
                .listener()
                .rationale(new Rationale<Void>() {
                    @Override
                    public void showRationale(Context context, Void data, RequestExecutor executor) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, R.style.dialog);
                        View view = LayoutInflater.from(context).inflate(R.layout.dialog_runtime_before_request, null);
                        TextView tvTitle = view.findViewById(R.id.tv_dialog_permission_title);
                        TextView tvDescription = view.findViewById(R.id.tv_dialog_permission_description);
                        TextView tvNext = view.findViewById(R.id.tv_dialog_permission_next);
                        TextView tvClose = view.findViewById(R.id.tv_dialog_permission_close);

                        tvNext.setText("去打开");
                        tvTitle.setText("管理通知");
                        tvDescription.setText("我们将开始请求通知管理权限");
                        // 设置我们自己定义的布局文件作为弹出框的Content
                        alertDialogBuilder.setView(view);
                        //按对话框以外的地方不起作用,按返回键也不起作用,防止点击外面对话框消失
                        alertDialogBuilder.setCancelable(false);
                        //这个位置十分重要，只有位于这个位置逻辑才是正确的
                        AlertDialog dialog = alertDialogBuilder.show();
                        tvNext.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                executor.execute();
                                dialog.dismiss();
                            }
                        });

                        tvClose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                executor.cancel();
                                dialog.dismiss();
                            }
                        });

                    }
                })
                .onGranted(permissions -> {
                    // Storage permission are allowed.
                    //permissionFlag = true;
                    requestAndNotificationShow();

                })
                .onDenied(permissions -> {
                    // Storage permission are not allowed.
                    Toast.makeText(MainActivity.this, "通知管理权限获取失败", Toast.LENGTH_SHORT).show();
                })
                .start();

    }


    private void requestAndNotificationShow() {
        AndPermission.with(this)
                .notification()
                .permission()
                .rationale(new Rationale<Void>() {
                    @Override
                    public void showRationale(Context context, Void data, RequestExecutor executor) {

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, R.style.dialog);
                        View view = LayoutInflater.from(context).inflate(R.layout.dialog_runtime_before_request, null);
                        TextView tvTitle = view.findViewById(R.id.tv_dialog_permission_title);
                        TextView tvDescription = view.findViewById(R.id.tv_dialog_permission_description);
                        TextView tvNext = view.findViewById(R.id.tv_dialog_permission_next);
                        TextView tvClose = view.findViewById(R.id.tv_dialog_permission_close);

                        tvNext.setText("去打开");
                        tvTitle.setText("接收通知");
                        tvDescription.setText("我们将开始请求通知显示权限");
                        // 设置我们自己定义的布局文件作为弹出框的Content
                        alertDialogBuilder.setView(view);
                        //按对话框以外的地方不起作用,按返回键也不起作用,防止点击外面对话框消失
                        alertDialogBuilder.setCancelable(false);
                        //这个位置十分重要，只有位于这个位置逻辑才是正确的
                        AlertDialog dialog = alertDialogBuilder.show();
                        tvNext.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    toOpenNotificationAccessManually();
                                }else{
                                    executor.execute();
                                }
                                dialog.dismiss();
                            }
                        });

                        tvClose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                executor.cancel();
                                dialog.dismiss();
                            }
                        });

                    }
                })
                .onGranted(permissions -> {
                    // Storage permission are allowed.
                    //requestAndNotificationListener();
                    permissionFlag = true;
                })
                .onDenied(permissions -> {
                    // Storage permission are not allowed.
                    Toast.makeText(MainActivity.this, "显示通知权限获取失败", Toast.LENGTH_SHORT).show();
                })
                .start();
    }

    /**
     * 去设置里手动打开Notification权限
     *
     */
    public void toOpenNotificationAccessManually() {
        try {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            //这种方案适用于 API 26, 即8.0（含8.0）以上可以用
            intent.putExtra(EXTRA_APP_PACKAGE, getPackageName());
            intent.putExtra(EXTRA_CHANNEL_ID, getApplicationInfo().uid);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            // 出现异常则跳转到应用设置界面：锤子坚果3——OC105 API25
            Intent intent = new Intent();
            //下面这种方案是直接跳转到当前应用的设置界面。
            //https://blog.csdn.net/ysy950803/article/details/71910806
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getPackageName(), null);
            intent.setData(uri);
            startActivity(intent);
        }
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

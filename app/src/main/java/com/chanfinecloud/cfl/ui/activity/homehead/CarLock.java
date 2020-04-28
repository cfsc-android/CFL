package com.chanfinecloud.cfl.ui.activity.homehead;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.adapter.AbstractSpinerAdapter;
import com.chanfinecloud.cfl.adapter.CarRecordListAdapter;
import com.chanfinecloud.cfl.adapter.smart.CarEntity;
import com.chanfinecloud.cfl.adapter.smart.CarListEntity;
import com.chanfinecloud.cfl.entity.BaseEntity;
import com.chanfinecloud.cfl.entity.HikAlarmAddition;
import com.chanfinecloud.cfl.entity.HikAlarmCarlist;
import com.chanfinecloud.cfl.entity.HikCarCrossRecord;
import com.chanfinecloud.cfl.entity.HikCarCrossRecordList;
import com.chanfinecloud.cfl.entity.HikUser;
import com.chanfinecloud.cfl.entity.core.ListLoadingType;
import com.chanfinecloud.cfl.http.HttpMethod;
import com.chanfinecloud.cfl.http.JsonParse;
import com.chanfinecloud.cfl.http.MyCallBack;
import com.chanfinecloud.cfl.http.ParamType;
import com.chanfinecloud.cfl.http.RequestParam;
import com.chanfinecloud.cfl.ui.base.BaseActivity;
import com.chanfinecloud.cfl.util.FileManagement;
import com.chanfinecloud.cfl.weidgt.RecyclerViewDivider;
import com.chanfinecloud.cfl.weidgt.SpinerPopWindow;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

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
import static com.chanfinecloud.cfl.config.Config.IOT;

/**
 * damien 2020/3/30 只能锁车
 */
public class CarLock extends BaseActivity {

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
    @BindView(R.id.tv_car_code_select)
    TextView tvCarCodeSelect;
    @BindView(R.id.tv_car_lock_flag)
    TextView tvCarLockFlag;
    @BindView(R.id.s_car_lock)
    Switch sCarLock;
    @BindView(R.id.ll_car_lock_status)
    LinearLayout llCarLockStatus;
    @BindView(R.id.car_lock_rlv)
    RecyclerView carLockRlv;
    @BindView(R.id.car_lock_srl)
    SmartRefreshLayout carLockSrl;
    @BindView(R.id.ll_car_lock_record)
    LinearLayout llCarLockRecord;

    private SpinerPopWindow mpopwindow;

    private ArrayList<String> carList = new ArrayList<>();

    private CarRecordListAdapter carRecordListAdapter;
    private ArrayList<HikCarCrossRecord> carRecordArrayList=new ArrayList<>();

    private int page = 1;//第几页
    private int pageSize = 10;//每页显示多少条
    private int totalPages = 0;//总的页面数

    //车辆出入记录
    private int recordPage = 1;//第几页
    private int recordPageSize = 10;//每页显示多少条
    private int recordPotalPages = 0;//总的页面数
    //===========筛选条件=========
    private String startTime;//开始时间
    private String endTime;//结束时间

    private HikUser hikUser;
    private String currentPlateNo="";
    private String currentAlarmSyscode="";
    private String phaseId="";
    private ListLoadingType loadType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initData() {
        setContentView(R.layout.activity_car_lock);
        ButterKnife.bind(this);

       // hikUser= FileManagement.getHikUser();

        if (FileManagement.getUserInfo() != null
                && FileManagement.getUserInfo().getRoomList() != null
                && FileManagement.getUserInfo().getRoomList().get(0) != null
                && FileManagement.getUserInfo().getRoomList().size() > 0
                ){

            phaseId=FileManagement.getUserInfo().getRoomList().get(0).getPhaseId();
        }

        toolbarTvTitle.setText("智能锁车");
        sCarLock.setChecked(true);
        sCarLock.setClickable(false);
        tvCarLockFlag.setText("解锁");
        Drawable drawable=getResources().getDrawable(R.drawable.icon_car_unlock);
        drawable.setBounds(0, 0, 68, 68);
        tvCarLockFlag.setCompoundDrawables(drawable,null,null,null);

        //smatrefresh 刷新开始///////////
        carRecordListAdapter=new CarRecordListAdapter(this, carRecordArrayList);;
        carLockRlv.setLayoutManager(new LinearLayoutManager(this));
        carLockRlv.addItemDecoration(new RecyclerViewDivider(this, LinearLayoutManager.VERTICAL));
        carLockRlv.setAdapter(carRecordListAdapter);
        carLockRlv.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
        carLockSrl.setPrimaryColorsId(R.color.view_background,R.color.text_primary);
        carLockSrl.setRefreshHeader(new ClassicsHeader(this).setSpinnerStyle(SpinnerStyle.Translate));
        carLockSrl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                recordPage=1;
                getCarCrossRecord();
                loadType=ListLoadingType.Refresh;
            }
        });
        carLockSrl.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                recordPage++;
                getCarCrossRecord();
                loadType= ListLoadingType.LoadMore;
            }
        });
        carLockSrl.autoRefresh();
        carLockSrl.finishRefresh(5000);//5秒后关闭刷新效果
        ////////////////////////smart refresh 刷新结束


        getCarList();

    }

    /**
     * 获取车辆列表
     */
    private void getCarList(){
        RequestParam requestParam = new RequestParam(BASE_URL+BASIC+"basic/vehicleInfo/vehiclePage", HttpMethod.Get);
        Map<String,String> requestMap=new HashMap<>();
        requestMap.put("pageNo","1");
        requestMap.put("pageSize","100");
        requestParam.setRequestMap(requestMap);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtil.d(result);
                BaseEntity<CarListEntity> baseEntity= JsonParse.parse(result, CarListEntity.class);
                if(baseEntity.isSuccess()){
                    List<String> vehicleCodes=new ArrayList<>();
                    for (CarEntity carEntity : baseEntity.getResult().getData()) {
                        if(carEntity.getAuditStatus()==1){
                            vehicleCodes.add(carEntity.getPlateNO());
                        }
                    }
                    sCarLock.setClickable(true);
                    initCarList(vehicleCodes);
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

    private void initCarList(List<String> vehicleCodes){
        if(vehicleCodes.size()>0){
            mpopwindow = new SpinerPopWindow(CarLock.this);
            currentPlateNo=vehicleCodes.get(0);
            tvCarCodeSelect.setText(currentPlateNo);
            getAlarmCar();
            initCarCross();
            carList.clear();
            for (int i = 0; i < vehicleCodes.size(); i++) {
                carList.add(vehicleCodes.get(i));
            }
            mpopwindow.refreshData(carList, 0);
            mpopwindow.setItemListener(new AbstractSpinerAdapter.IOnItemSelectListener() {
                @Override
                public void onItemClick(int pos) {
                    currentPlateNo=carList.get(pos);
                    tvCarCodeSelect.setText(carList.get(pos));
                    page=1;
                    totalPages=0;
                    sCarLock.setOnCheckedChangeListener(null);
                    carRecordArrayList.clear();
                    carRecordListAdapter.notifyDataSetChanged();
                    getAlarmCar();
                    initCarCross();
                }
            });
        }else{
            sCarLock.setClickable(false);
            showToast("没有获得车辆信息");
        }
    }


    /**
     *
     */
    private void getAlarmCar(){
        Map<String, Object> requestMap=new HashMap<>();
        requestMap.put("searchKey",currentPlateNo);
        requestMap.put("pageNo",1);
        requestMap.put("pageSize",100);
        RequestParam requestParam = new RequestParam(BASE_URL+IOT+"community/api/car/v1/alarm/list/"+phaseId,  HttpMethod.Post);
        requestParam.setRequestMap(requestMap);
        requestParam.setParamType(ParamType.Json);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtil.d(result);
                BaseEntity<HikAlarmCarlist> baseEntity= JsonParse.parse(result, HikAlarmCarlist.class);
                if(baseEntity.isSuccess()){
                    if(baseEntity.getResult().getList().size()>0){
                        currentAlarmSyscode=baseEntity.getResult().getList().get(0).getAlarmSyscode();
                        tvCarLockFlag.setText("锁车");
                        sCarLock.setChecked(false);
                        Drawable drawable=getResources().getDrawable(R.drawable.icon_car_lock);
                        drawable.setBounds(0, 0, 68, 68);
                        tvCarLockFlag.setCompoundDrawables(drawable,null,null,null);
                    }else{
                        currentAlarmSyscode="";
                        tvCarLockFlag.setText("解锁");
                        sCarLock.setChecked(true);
                        Drawable drawable=getResources().getDrawable(R.drawable.icon_car_unlock);
                        drawable.setBounds(0, 0, 68, 68);
                        tvCarLockFlag.setCompoundDrawables(drawable,null,null,null);
                    }
                    sCarLock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if(isChecked){
                                alarmCarDeletion();
                            }else{
                                alarmCarAddition();
                            }
                        }
                    });
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
        sendRequest(requestParam, false);

    }

    /**
     * 车辆布控
     */
    private void alarmCarAddition(){
        
        Map<String, Object> requestMap=new HashMap<>();
        requestMap.put("plateNo",currentPlateNo);
        RequestParam requestParam = new RequestParam(BASE_URL+IOT+"community/api/car/v1/alarm/"+phaseId,  HttpMethod.Post);
        requestParam.setRequestMap(requestMap);
        requestParam.setParamType(ParamType.Json);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtil.d(result);
                BaseEntity<HikAlarmAddition> baseEntity= JsonParse.parse(result, HikAlarmAddition.class);
                if(baseEntity.isSuccess()){
                    currentAlarmSyscode=baseEntity.getResult().getAlarmSyscode();
                    tvCarLockFlag.setText("锁车");
                    Drawable drawable=getResources().getDrawable(R.drawable.icon_car_lock);
                    drawable.setBounds(0, 0, 68, 68);
                    tvCarLockFlag.setCompoundDrawables(drawable,null,null,null);
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


    /**
     * 车辆取消布控
     */
    private void alarmCarDeletion(){
        RequestParam requestParam = new RequestParam(BASE_URL+IOT+"community/api/car/v1/alarm/deletion/"+phaseId, HttpMethod.Post);
        Map<String, Object> requestMap=new HashMap<>();
        requestMap.put("alarmSyscodes",currentAlarmSyscode);
        requestParam.setRequestMap(requestMap);
        requestParam.setParamType(ParamType.Json);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtil.d(result);
                BaseEntity<CarListEntity> baseEntity= JsonParse.parse(result,CarListEntity.class);
                if(baseEntity.isSuccess()){
                    currentAlarmSyscode="";
                    tvCarLockFlag.setText("解锁");
                    Drawable drawable=getResources().getDrawable(R.drawable.icon_car_unlock);
                    drawable.setBounds(0, 0, 68, 68);
                    tvCarLockFlag.setCompoundDrawables(drawable,null,null,null);
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

    /**
     * 初始化出入记录
     */
    private void initCarCross(){

        getCarCrossRecord();
    }

    /**
     * 车辆出入记录
     */
    private void getCarCrossRecord(){
        Map<String,Object> requestMap=new HashMap<>();
        requestMap.put("plateNo",currentPlateNo);
        requestMap.put("pageNo",recordPage);
        requestMap.put("pageSize",recordPageSize);
        RequestParam requestParam = new RequestParam(BASE_URL+IOT+"community/api/car/v1/cross/list/"+phaseId, HttpMethod.Post);
        requestParam.setRequestMap(requestMap);
        requestParam.setParamType(ParamType.Json);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtil.d(result);
                BaseEntity<HikCarCrossRecordList> baseEntity= JsonParse.parse(result, HikCarCrossRecordList.class);
                if(baseEntity.isSuccess()){
                    if(recordPage==1 && carRecordArrayList.size() > 0){
                        carRecordArrayList.clear();
                    }
                    carRecordArrayList.addAll(baseEntity.getResult().getList());
                    carRecordListAdapter.notifyDataSetChanged();
                    if(loadType==ListLoadingType.Refresh){
                        carLockSrl.finishRefresh();
                        if(page*pageSize>=baseEntity.getResult().getTotal()){
                            carLockSrl.setNoMoreData(true);
                        }
                    }else{
                        if(page*pageSize>=baseEntity.getResult().getTotal()){
                            carLockSrl.finishLoadMoreWithNoMoreData();
                        }else{
                            carLockSrl.finishLoadMore();
                        }
                    }

                }else{
                    if(loadType==ListLoadingType.Refresh){
                        carLockSrl.finishRefresh();
                    }else{
                        page--;
                        carLockSrl.finishLoadMore(false);
                    }
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


    @OnClick({R.id.toolbar_btn_back, R.id.tv_car_code_select})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_btn_back:
                finish();
                break;
            case R.id.tv_car_code_select:
                if(mpopwindow==null){
                    showToast("没有获得车辆信息");
                }else{
                    mpopwindow.setWidth(tvCarCodeSelect.getWidth());
                    if (carList.size() < 6) {
                        mpopwindow.setHeight(tvCarCodeSelect.getHeight() * carList.size());
                    } else {
                        mpopwindow.setHeight(tvCarCodeSelect.getHeight() * 4);
                    }
                    mpopwindow.showAsDropDown(tvCarCodeSelect);
                }
                break;
        }
    }
}

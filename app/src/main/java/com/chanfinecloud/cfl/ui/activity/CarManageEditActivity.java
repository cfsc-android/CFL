package com.chanfinecloud.cfl.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.adapter.AbstractSpinerAdapter;
import com.chanfinecloud.cfl.adapter.smart.CarEntity;
import com.chanfinecloud.cfl.entity.BaseEntity;
import com.chanfinecloud.cfl.entity.HikParkInfo;
import com.chanfinecloud.cfl.entity.enumtype.CarColor;
import com.chanfinecloud.cfl.entity.enumtype.CarType;
import com.chanfinecloud.cfl.entity.enumtype.PlateColor;
import com.chanfinecloud.cfl.entity.enumtype.PlateType;
import com.chanfinecloud.cfl.entity.eventbus.EventBusMessage;
import com.chanfinecloud.cfl.entity.smart.CarChargeInfo;
import com.chanfinecloud.cfl.http.HttpMethod;
import com.chanfinecloud.cfl.http.JsonParse;
import com.chanfinecloud.cfl.http.MyCallBack;
import com.chanfinecloud.cfl.http.ParamType;
import com.chanfinecloud.cfl.http.RequestParam;
import com.chanfinecloud.cfl.ui.base.BaseActivity;
import com.chanfinecloud.cfl.util.FileManagement;
import com.chanfinecloud.cfl.util.LogUtils;
import com.chanfinecloud.cfl.util.Utils;
import com.chanfinecloud.cfl.weidgt.SpinerPopWindow;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.chanfinecloud.cfl.config.Config.BASE_URL;
import static com.chanfinecloud.cfl.config.Config.BASIC;
import static com.chanfinecloud.cfl.util.EnumUtils.getCarColorString;
import static com.chanfinecloud.cfl.util.EnumUtils.getCarTypeString;
import static com.chanfinecloud.cfl.util.EnumUtils.getPlateColorString;
import static com.chanfinecloud.cfl.util.EnumUtils.getPlateTypeString;

public class CarManageEditActivity extends BaseActivity {

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
    @BindView(R.id.tv_car_manage_add_car_photo)
    ImageView tvCarManageAddCarPhoto;
    @BindView(R.id.tv_car_manage_add_plate_color)
    TextView tvCarManageAddPlateColor;
    @BindView(R.id.tv_car_manage_add_plate_type)
    TextView tvCarManageAddPlateType;
    @BindView(R.id.tv_car_manage_add_car_color)
    TextView tvCarManageAddCarColor;
    @BindView(R.id.tv_car_manage_add_car_type)
    TextView tvCarManageAddCarType;
    @BindView(R.id.tv_car_manage_edit_pay_mode)
    TextView tvCarManageEditPayMode;
    @BindView(R.id.tv_car_manage_edit_charge)
    TextView tvCarManageEditCharge;
    @BindView(R.id.ll_car_manage_charge_pay_1)
    LinearLayout llCarManageChargePay1;
    @BindView(R.id.ll_car_manage_charge_pay_3)
    LinearLayout llCarManageChargePay3;
    @BindView(R.id.ll_car_manage_charge_pay_6)
    LinearLayout llCarManageChargePay6;
    @BindView(R.id.ll_car_manage_charge_pay_list)
    LinearLayout llCarManageChargePayList;


    private String carImagePath="";

    private SpinerPopWindow plateColorPop;
    private ArrayList<String> plateColorList = new ArrayList<>();
    private ArrayList<String> plateTypeList = new ArrayList<>();
    private ArrayList<String> carColorList = new ArrayList<>();
    private ArrayList<String> carTypeList = new ArrayList<>();

    private String plateColor,plateType,carColor,carType;
    private CarEntity carManageEntity;

    private CarChargeInfo info;
    private int addMonth;
    private String feeStr;

    private String tempStartTime;
    private String tempEndTime;
    private boolean reCharge;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initData() {
        setContentView(R.layout.activity_car_manage_edit);
        ButterKnife.bind(this);

        toolbarTvTitle.setText("编辑车辆");
        toolbarTvAction.setText("确定");
        toolbarTvAction.setVisibility(View.VISIBLE);
        toolbarBtnAction.setVisibility(View.GONE);
        tvCarManageEditCharge.setVisibility(View.VISIBLE);
        tvCarManageEditCharge.setText("包期");
        initViewData();
        Bundle bundle=getIntent().getExtras();
        carManageEntity= (CarEntity) bundle.getSerializable("car");
        StringBuffer sb=new StringBuffer(carManageEntity.getPlateNO());
        sb.insert(2," · ");
        toolbarTvTitle.setText(sb);
        initEditData();
        if(carManageEntity.getAuditStatus()==0){
        //    getCarChargeInfo();
        }else{
            tvCarManageEditCharge.setVisibility(View.GONE);
        }
        EventBus.getDefault().register(this);
        if(FileManagement.getParkIndexCode()==null||"".equals(FileManagement.getParkIndexCode())){
//            getParkList();
        }


    }

    private void initEditData(){
//        if(carManageEntity.getCarPhoto()!=null){
//            XUtilsImageUtils.display(tv_car_manage_add_car_photo,Constants.BASEHOST+carManageEntity.getCarPhoto(),ImageView.ScaleType.CENTER_INSIDE);
//        }
        if(carManageEntity.getPlateColor()!=null){
            tvCarManageAddPlateColor.setText(getPlateColorString(carManageEntity.getPlateColor()));
            plateColor=carManageEntity.getPlateColor();
        }
        if(carManageEntity.getPlateType()!=null){
            tvCarManageAddPlateType.setText(getPlateTypeString(carManageEntity.getPlateType()));
            plateType=carManageEntity.getPlateType();
        }
        if(carManageEntity.getVehicleColor()!=null){
            tvCarManageAddCarColor.setText(getCarColorString(carManageEntity.getVehicleColor()));
            carColor=carManageEntity.getVehicleColor();
        }
        if(carManageEntity.getVehicleType()!=null){
            tvCarManageAddCarType.setText(getCarTypeString(carManageEntity.getVehicleType()));
            carType=carManageEntity.getVehicleType();
        }
    }

    private void initViewData(){
        for(PlateColor color: PlateColor.values()){
            plateColorList.add(color.getColor());
        }
        for(PlateType type:PlateType.values()){
            plateTypeList.add(type.getType());
        }
        for(CarColor color:CarColor.values()){
            carColorList.add(color.getColor());
        }
        for(CarType type:CarType.values()){
            carTypeList.add(type.getType());
        }

    }

    @OnClick({R.id.toolbar_btn_back, R.id.tv_car_manage_add_car_photo, R.id.tv_car_manage_add_plate_color,
            R.id.tv_car_manage_add_plate_type, R.id.tv_car_manage_add_car_color, R.id.tv_car_manage_add_car_type,
            R.id.tv_car_manage_edit_charge, R.id.ll_car_manage_charge_pay_1,
            R.id.ll_car_manage_charge_pay_3, R.id.ll_car_manage_charge_pay_6, R.id.toolbar_tv_action})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_btn_back:
                finish();
                break;
            case R.id.toolbar_tv_action:
                saveCar();
                break;
            case R.id.tv_car_manage_add_car_photo:
                // TODO: 2020/4/7   暂时不需要图片更新
                break;
            case R.id.tv_car_manage_add_plate_color:
                initPop(plateColorList,tvCarManageAddPlateColor,0);
                break;
            case R.id.tv_car_manage_add_plate_type:
                initPop(plateTypeList,tvCarManageAddPlateType,1);
                break;
            case R.id.tv_car_manage_add_car_color:
                initPop(carColorList,tvCarManageAddCarColor,2);
                break;
            case R.id.tv_car_manage_add_car_type:
                initPop(carTypeList,tvCarManageAddCarType,3);
                break;
            case R.id.tv_car_manage_edit_charge:
                if(llCarManageChargePayList.getVisibility()==View.GONE){
                    llCarManageChargePayList.setVisibility(View.VISIBLE);
                }else{
                    llCarManageChargePayList.setVisibility(View.GONE);
                }
                break;
            case R.id.ll_car_manage_charge_pay_1:
                addMonth=1;
                feeStr="218.00";
                Bundle bundle_1=new Bundle();
                bundle_1.putString("no","NCL"+new Date().getTime());
                bundle_1.putString("count","218.00");

                startActivity(PaymentTestActivity.class,bundle_1);
                break;
            case R.id.ll_car_manage_charge_pay_3:
                addMonth=3;
                feeStr="618.00";
                Bundle bundle_3=new Bundle();
                bundle_3.putString("no","NCL"+new Date().getTime());
                bundle_3.putString("count","618.00");

                startActivity(PaymentTestActivity.class,bundle_3);
                break;
            case R.id.ll_car_manage_charge_pay_6:
                addMonth=6;
                feeStr="1118.00";
                Bundle bundle_6=new Bundle();
                bundle_6.putString("no","NCL"+new Date().getTime());
                bundle_6.putString("count","1118.00");
                startActivity(PaymentTestActivity.class,bundle_6);
                break;
        }
    }

    private void initPop(ArrayList<String> data,final TextView view, final int result){
        plateColorPop=new SpinerPopWindow(this);
        plateColorPop.setItemListener(new AbstractSpinerAdapter.IOnItemSelectListener() {
            @Override
            public void onItemClick(int pos) {
                switch (result){
                    case 0:
                        String _color=plateColorList.get(pos);
                        for(PlateColor color:PlateColor.values()){
                            if(_color.equals(color.getColor())){
                                view.setText(_color);
                                plateColor=color.getValue();
                            }
                        }
                        break;
                    case 1:
                        String _type=plateTypeList.get(pos);
                        for(PlateType type:PlateType.values()){
                            if(_type.equals(type.getType())){
                                view.setText(_type);
                                plateType=type.getValue();
                            }
                        }
                        break;
                    case 2:
                        String k_color=carColorList.get(pos);
                        for(CarColor color:CarColor.values()){
                            if(k_color.equals(color.getColor())){
                                view.setText(k_color);
                                carColor=color.getValue();
                            }
                        }
                        break;
                    case 3:
                        String k_type=carTypeList.get(pos);
                        for(CarType type:CarType.values()){
                            if(k_type.equals(type.getType())){
                                view.setText(k_type);
                                carType=type.getValue();
                            }
                        }
                        break;
                }
            }
        });
        plateColorPop.refreshData(data, 0);
        plateColorPop.setWidth(view.getWidth());
        if (data.size() < 6) {
            plateColorPop.setHeight(view.getHeight() * data.size());
        } else {
            plateColorPop.setHeight(view.getHeight() * 4);
        }
        plateColorPop.showAsDropDown(view);
    }

    private void saveCar(){

        startProgressDialog("保存中...", true);
        Map<String,Object> map=new HashMap<>();
        String action="basic/vehicleInfo/update";
        if(carManageEntity!=null){
            map.put("id",carManageEntity.getId());
            map.put("plateNO",carManageEntity.getPlateNO());
        }
        map.put("vehicleColor",carColor);
        map.put("vehicleType",carType);
        map.put("plateColor",plateColor);
        map.put("plateType",plateType);

        if(!"".endsWith(carImagePath)){
            map.put("pic",new File(carImagePath));
        }
        
        if (FileManagement.getUserInfo() != null){
            map.put("householdId",FileManagement.getUserInfo().getId()+"");
            map.put("ownerPhone",FileManagement.getUserInfo().getMobile()+"");
            if (FileManagement.getUserInfo().getRoomList() != null)
                map.put("roomId",FileManagement.getUserInfo().getRoomList().get(0).getId() +"");
        }


        RequestParam requestParam = new RequestParam(BASE_URL+BASIC+action, HttpMethod.Post);
        requestParam.setParamType(ParamType.Json);
        requestParam.setRequestMap(map);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d(result);
                BaseEntity baseEntity= JsonParse.parse(result);
                if(baseEntity.isSuccess()){
                    EventBus.getDefault().post(new EventBusMessage<>("carAdd"));
                    finish();
                }else{
                    showToast(baseEntity.getMessage());
                }
               /* try {
                    JSONObject jsonObject = new JSONObject(result);
                    if(jsonObject.getInt("code")==200) {
                        EventBus.getDefault().post(new EventBusMessage<>("carAdd"));
                        finish();
                    }else{
                        showToast(jsonObject.getString("msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
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

            @Override
            public void onCancelled(CancelledException cex) {
                super.onCancelled(cex);
                stopProgressDialog();
            }
        });
        sendRequest(requestParam, false);

    }

    private void getParkList(){

        RequestParam requestParam =  new RequestParam(BASE_URL+BASIC+"isc/park/parklist.action", HttpMethod.Post);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                Log.e("result",result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if(jsonObject.getString("code").equals("0")) {
                        Gson gson=new Gson();
                        Type type = new TypeToken<List<HikParkInfo>>() {}.getType();
                        List<HikParkInfo> list=gson.fromJson(jsonObject.getString("data"),type);
                        if(list!=null&&list.size()>0){
                            FileManagement.setParkIndexCode(list.get(0).getParkIndexCode());
                            if(reCharge){
                                carCharge(tempStartTime,tempEndTime);
                            }
                        }
                    }else{
                        showToast(jsonObject.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

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

    private void carCharge(final String startTime, final String endTime){
        Map<String, Object> requestDataMap=new HashMap<>();
        requestDataMap.put("parkSyscode",FileManagement.getParkIndexCode());
        if (Utils.isEmpty(carManageEntity.getParkingNO())){
            requestDataMap.put("plateNo","123456");
        }else{
            requestDataMap.put("plateNo",carManageEntity.getParkingNO());
        }
        requestDataMap.put("fee",feeStr);
        requestDataMap.put("startTime",startTime);
        requestDataMap.put("endTime",endTime);
        RequestParam requestParam =  new RequestParam(BASE_URL+BASIC+"isc/car/charge.action", HttpMethod.Post);
        requestParam.setRequestMap(requestDataMap);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                Log.e("result",result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if(jsonObject.getString("code").equals("0")) {
                        getCarChargeInfo();
                        EventBus.getDefault().post(new EventBusMessage<>("carCharge"));
                        Log.e("carCharge","车辆充值成功！");
                    }else{
                        if(jsonObject.getString("code").equals("0x00072202")){
                            tempStartTime=startTime;
                            tempEndTime=endTime;
                            reCharge=true;
                            getParkList();
                        }else{
                            showToast(jsonObject.getString("msg"));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    showToast(e.getMessage());
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

    private void getCarChargeInfo(){
        Map<String, Object> requestDataMap=new HashMap<>();
        requestDataMap.put("plateNo",carManageEntity.getPlateNO());
        requestDataMap.put("pageNo",1);
        requestDataMap.put("pageSize",1000);

        RequestParam requestParam =  new RequestParam(BASE_URL+BASIC+"isc/car/charge/page.action", HttpMethod.Post);
        requestParam.setRequestMap(requestDataMap);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                Log.e("result",result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if(jsonObject.getString("code").equals("0")) {
                        JSONObject data=jsonObject.getJSONObject("data");
                        Gson gson=new Gson();
                        Type type = new TypeToken<List<CarChargeInfo>>() {}.getType();
                        List<CarChargeInfo> list=gson.fromJson(data.getString("list"),type);
                        if(list.size()>0){
                            info = list.get(0);
                            if(info.getGroupName()!=null&&!"".equals(info.getGroupName())){
                                tvCarManageEditPayMode.setText(info.getGroupName());
                                tvCarManageEditCharge.setVisibility(View.GONE);
                            }else{
                                if(info.getValidity()!=null&&info.getValidity().size()>0){
                                    tvCarManageEditPayMode.setText("包期\r\n"+info.getValidity().get(0).getFunctionTime().get(0).getStartTime()+
                                            "至"+info.getValidity().get(0).getFunctionTime().get(0).getEndTime());
                                    try {
                                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                        Date end_date= sdf.parse(info.getValidity().get(0).getFunctionTime().get(0).getEndTime());
                                        if(end_date.getTime()<=new Date().getTime()){
                                            tvCarManageEditPayMode.setText("包期-已过期\r\n"+info.getValidity().get(0).getFunctionTime().get(0).getStartTime()+
                                                    "至"+info.getValidity().get(0).getFunctionTime().get(0).getEndTime());
                                        }else if(end_date.getTime()>new Date().getTime()&&end_date.getTime()<new Date().getTime()+7*24*60*60*1000){
                                            tvCarManageEditCharge.setText("即将到期，续费");
                                        }else{
                                            tvCarManageEditCharge.setVisibility(View.GONE);
                                        }
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                }else{
                                    tvCarManageEditPayMode.setText("临时车缴费");
                                }
                            }
                        }

                    }else{
                        showToast(jsonObject.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                showToast(ex.getMessage());
            }
        });


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(EventBusMessage message){
        if("paymentOk".equals(message.getMessage())){
            llCarManageChargePayList.setVisibility(View.GONE);
            Date startDate=new Date();
            boolean flag=false;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if(info!= null && info.getValidity()!=null&&info.getValidity().size()>0){
                try {
                    Date end_date= sdf.parse(info.getValidity().get(0).getFunctionTime().get(0).getEndTime());
                    if(end_date.getTime()>new Date().getTime()){
                        startDate=end_date;
                        flag=true;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            Calendar calendar=Calendar.getInstance();
            calendar.setTime(startDate);
            calendar.add(Calendar.MONTH,+addMonth);
            String endDateStr=sdf.format(calendar.getTime());
            String startDateStr=sdf.format(startDate);
            if(flag){
                startDateStr=info.getValidity().get(0).getFunctionTime().get(0).getStartTime();
            }
            // TODO: 2020/4/9   取消注释
            //carCharge(startDateStr,endDateStr);
        }else if ("carCharge".equals(message.getMessage())){
            showToast("车辆充值成功");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       // EventBus.getDefault().post(new EventBusMessage<>("carAdd"));
        EventBus.getDefault().unregister(this);

    }

}

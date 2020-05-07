package com.chanfinecloud.cfl.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.adapter.AbstractSpinerAdapter;
import com.chanfinecloud.cfl.adapter.smart.CarEntity;
import com.chanfinecloud.cfl.entity.BaseEntity;
import com.chanfinecloud.cfl.entity.FileEntity;
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
import com.chanfinecloud.cfl.util.FilePathUtil;
import com.chanfinecloud.cfl.util.LogUtils;
import com.chanfinecloud.cfl.util.Utils;
import com.chanfinecloud.cfl.weidgt.SpinerPopWindow;
import com.chanfinecloud.cfl.weidgt.photopicker.PhotoPicker;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhihu.matisse.Matisse;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.util.LogUtil;

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
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static com.chanfinecloud.cfl.config.Config.BASE_URL;
import static com.chanfinecloud.cfl.config.Config.BASIC;
import static com.chanfinecloud.cfl.config.Config.FILE;
import static com.chanfinecloud.cfl.config.Config.IOT;
import static com.chanfinecloud.cfl.config.Config.PHOTO_DIR_NAME;
import static com.chanfinecloud.cfl.config.Config.SD_APP_DIR_NAME;
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
    @BindView(R.id.tv_car_manage_edit_car_photo)
    ImageView tvCarManageEditCarPhoto;


    private String carImagePath = "";

    private SpinerPopWindow plateColorPop;
    private ArrayList<String> plateColorList = new ArrayList<>();
    private ArrayList<String> plateTypeList = new ArrayList<>();
    private ArrayList<String> carColorList = new ArrayList<>();
    private ArrayList<String> carTypeList = new ArrayList<>();

    private String plateColor, plateType, carColor, carType, vehicleImageId;
    private CarEntity carManageEntity;
    private String phaseId;
    private int addMonth;
    private String feeStr;

    private String tempStartTime;
    private String tempEndTime;
    private boolean reCharge;

    public static final int REQUEST_CODE_CHOOSE = 0x001;
    public static final int REQUEST_CODE_CAPTURE = 0x002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        checkAppPermission();
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
        Bundle bundle = getIntent().getExtras();
        carManageEntity = (CarEntity) bundle.getSerializable("car");
        StringBuffer sb = new StringBuffer(carManageEntity.getPlateNO());
        sb.insert(2, " · ");
        toolbarTvTitle.setText(sb);
        if (FileManagement.getUserInfo() !=null && FileManagement.getUserInfo().getCurrentDistrict() != null) {
            phaseId = FileManagement.getUserInfo().getCurrentDistrict().getPhaseId();
        }
        initEditData();
        if (carManageEntity.getAuditStatus() == 1) {
                getCarChargeInfo();
        } else {
            tvCarManageEditCharge.setVisibility(View.GONE);
        }
        EventBus.getDefault().register(this);
        if (FileManagement.getParkIndexCode() == null || "".equals(FileManagement.getParkIndexCode())) {
            getParkList();
        }


    }

    private void initEditData() {
        if(carManageEntity.getVehicleImageResource()!=null){
            Glide.with(getApplicationContext()).load(carManageEntity.getVehicleImageResource().getUrl()).into(tvCarManageEditCarPhoto);
            vehicleImageId = carManageEntity.getVehicleImageId();
        }
        if (carManageEntity.getPlateColor() != null) {
            tvCarManageAddPlateColor.setText(getPlateColorString(carManageEntity.getPlateColor()));
            plateColor = carManageEntity.getPlateColor();
        }
        if (carManageEntity.getPlateType() != null) {
            tvCarManageAddPlateType.setText(getPlateTypeString(carManageEntity.getPlateType()));
            plateType = carManageEntity.getPlateType();
        }
        if (carManageEntity.getVehicleColor() != null) {
            tvCarManageAddCarColor.setText(getCarColorString(carManageEntity.getVehicleColor()));
            carColor = carManageEntity.getVehicleColor();
        }
        if (carManageEntity.getVehicleType() != null) {
            tvCarManageAddCarType.setText(getCarTypeString(carManageEntity.getVehicleType()));
            carType = carManageEntity.getVehicleType();
        }
    }

    private void initViewData() {
        for (PlateColor color : PlateColor.values()) {
            plateColorList.add(color.getColor());
        }
        for (PlateType type : PlateType.values()) {
            plateTypeList.add(type.getType());
        }
        for (CarColor color : CarColor.values()) {
            carColorList.add(color.getColor());
        }
        for (CarType type : CarType.values()) {
            carTypeList.add(type.getType());
        }
    }

    @OnClick({R.id.toolbar_btn_back, R.id.tv_car_manage_edit_car_photo, R.id.tv_car_manage_add_plate_color,
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
            case R.id.tv_car_manage_edit_car_photo:
                if (permission) {
                    PhotoPicker.pick(CarManageEditActivity.this, 1, true, REQUEST_CODE_CHOOSE);
                } else {
                    showToast("相机或读写手机存储的权限被禁止！");
                }
                break;
            case R.id.tv_car_manage_add_plate_color:
                initPop(plateColorList, tvCarManageAddPlateColor, 0);
                break;
            case R.id.tv_car_manage_add_plate_type:
                initPop(plateTypeList, tvCarManageAddPlateType, 1);
                break;
            case R.id.tv_car_manage_add_car_color:
                initPop(carColorList, tvCarManageAddCarColor, 2);
                break;
            case R.id.tv_car_manage_add_car_type:
                initPop(carTypeList, tvCarManageAddCarType, 3);
                break;
            case R.id.tv_car_manage_edit_charge:
                if (llCarManageChargePayList.getVisibility() == View.GONE) {
                    llCarManageChargePayList.setVisibility(View.VISIBLE);
                } else {
                    llCarManageChargePayList.setVisibility(View.GONE);
                }
                break;
            case R.id.ll_car_manage_charge_pay_1:
                addMonth = 1;
                feeStr = "218.00";
                Bundle bundle_1 = new Bundle();
                bundle_1.putString("no", "NCL" + new Date().getTime());
                bundle_1.putString("count", "218.00");

                startActivity(PaymentTestActivity.class, bundle_1);
                break;
            case R.id.ll_car_manage_charge_pay_3:
                addMonth = 3;
                feeStr = "618.00";
                Bundle bundle_3 = new Bundle();
                bundle_3.putString("no", "NCL" + new Date().getTime());
                bundle_3.putString("count", "618.00");

                startActivity(PaymentTestActivity.class, bundle_3);
                break;
            case R.id.ll_car_manage_charge_pay_6:
                addMonth = 6;
                feeStr = "1118.00";
                Bundle bundle_6 = new Bundle();
                bundle_6.putString("no", "NCL" + new Date().getTime());
                bundle_6.putString("count", "1118.00");
                startActivity(PaymentTestActivity.class, bundle_6);
                break;
        }
    }

    private void initPop(ArrayList<String> data, final TextView view, final int result) {
        plateColorPop = new SpinerPopWindow(this);
        plateColorPop.setItemListener(new AbstractSpinerAdapter.IOnItemSelectListener() {
            @Override
            public void onItemClick(int pos) {
                switch (result) {
                    case 0:
                        String _color = plateColorList.get(pos);
                        for (PlateColor color : PlateColor.values()) {
                            if (_color.equals(color.getColor())) {
                                view.setText(_color);
                                plateColor = color.getValue();
                            }
                        }
                        break;
                    case 1:
                        String _type = plateTypeList.get(pos);
                        for (PlateType type : PlateType.values()) {
                            if (_type.equals(type.getType())) {
                                view.setText(_type);
                                plateType = type.getValue();
                            }
                        }
                        break;
                    case 2:
                        String k_color = carColorList.get(pos);
                        for (CarColor color : CarColor.values()) {
                            if (k_color.equals(color.getColor())) {
                                view.setText(k_color);
                                carColor = color.getValue();
                            }
                        }
                        break;
                    case 3:
                        String k_type = carTypeList.get(pos);
                        for (CarType type : CarType.values()) {
                            if (k_type.equals(type.getType())) {
                                view.setText(k_type);
                                carType = type.getValue();
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

    private void saveCar() {
        startProgressDialog("保存中...", true);
        Map<String, Object> map = new HashMap<>();
        String action = "basic/vehicleInfo/update";
        if (carManageEntity != null) {
            map.put("id", carManageEntity.getId());
            map.put("plateNO", carManageEntity.getPlateNO());
        }
        map.put("vehicleImageId", vehicleImageId);
        map.put("vehicleColor", carColor);
        map.put("vehicleType", carType);
        map.put("plateColor", plateColor);
        map.put("plateType", plateType);

        RequestParam requestParam = new RequestParam(BASE_URL + BASIC + action, HttpMethod.Post);
        requestParam.setParamType(ParamType.Json);
        requestParam.setRequestMap(map);
        requestParam.setCallback(new MyCallBack<String>() {
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d(result);
                BaseEntity baseEntity = JsonParse.parse(result);
                if (baseEntity.isSuccess()) {
                    EventBus.getDefault().post(new EventBusMessage<>("carAdd"));
                    finish();
                } else {
                    showToast(baseEntity.getMessage());
                }
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

    /**
     * 获取停车库列表
     */
    private void getParkList() {
        Map<String,Object> requestMap=new HashMap<>();
        requestMap.put("phaseId", phaseId);
        RequestParam requestParam = new RequestParam(BASE_URL + IOT + "community/api/car/v1/parking/list/" + phaseId, HttpMethod.Post);
        requestParam.setParamType(ParamType.Json);
        requestParam.setRequestMap(requestMap);

        requestParam.setCallback(new MyCallBack<String>() {
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                Log.e("result", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getString("code").equals("200")) {
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<HikParkInfo>>() {
                        }.getType();
                        List<HikParkInfo> list = gson.fromJson(jsonObject.getString("result"), type);
                        if (list != null && list.size() > 0) {
                            //停车场唯一标识parkIndexCode
                            FileManagement.setParkIndexCode(list.get(0).getParkIndexCode());
                            if (reCharge) {
                                carCharge(tempStartTime, tempEndTime);
                            }
                        }
                    } else {
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

        sendRequest(requestParam, false);

    }

    /**
     * 车辆包期
     */
    private void carCharge(final String startTime, final String endTime) {
        Map<String, Object> requestDataMap = new HashMap<>();
        requestDataMap.put("parkSyscode", FileManagement.getParkIndexCode());
        if (carManageEntity != null) {
            requestDataMap.put("plateNO", carManageEntity.getPlateNO());
        }
        requestDataMap.put("fee", feeStr);
        requestDataMap.put("startTime", startTime);
        requestDataMap.put("endTime",  endTime);
        requestDataMap.put("phaseId", phaseId);

        RequestParam requestParam = new RequestParam(BASE_URL + IOT + "community/api/car/v1/charge/" + phaseId, HttpMethod.Post);
        requestParam.setRequestMap(requestDataMap);
        requestParam.setParamType(ParamType.Json);
        requestParam.setCallback(new MyCallBack<String>() {
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                Log.e("result", result);
                    BaseEntity baseEntity= JsonParse.parse(result);
                    if(baseEntity.isSuccess()){
                        getCarChargeInfo();
                        EventBus.getDefault().post(new EventBusMessage<>("carCharge"));
                        Log.e("carCharge", "车辆充值成功！");
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
     * 查询车辆包期信息
     */
    private void getCarChargeInfo() {
        Map<String, Object> requestDataMap = new HashMap<>();
        requestDataMap.put("id", carManageEntity.getId());
        RequestParam requestParam = new RequestParam(BASE_URL + BASIC + "basic/vehicleInfo/" + carManageEntity.getId(), HttpMethod.Get);
        requestParam.setRequestMap(requestDataMap);
        requestParam.setParamType(ParamType.Json);
        requestParam.setCallback(new MyCallBack<String>() {
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                Log.e("result", result);
                BaseEntity<CarEntity> baseEntity= JsonParse.parse(result,CarEntity.class);
                if(baseEntity.isSuccess()){
                        carManageEntity = baseEntity.getResult();
                        if (carManageEntity.getType() == 3) {
                            tvCarManageEditPayMode.setText("群组车");
                            tvCarManageEditCharge.setVisibility(View.GONE);
                        } else {
                                if (carManageEntity.getStartTime() != null && carManageEntity.getEndTime() != null) {
                                    tvCarManageEditPayMode.setText("包期\r\n" + carManageEntity.getStartTime() + "至" + carManageEntity.getEndTime());
                                    try {
                                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                        Date end_date = sdf.parse(carManageEntity.getEndTime());
                                        if (carManageEntity.getOverDue().equals(true)) {
                                            tvCarManageEditPayMode.setText("包期-已过期\r\n" + carManageEntity.getStartTime() +
                                                    "至" + carManageEntity.getEndTime());
                                        } else if (end_date.getTime() > new Date().getTime() && end_date.getTime() < new Date().getTime() + 7 * 24 * 60 * 60 * 1000) {
                                            tvCarManageEditCharge.setText("即将到期，续费");
                                        } else {
                                            tvCarManageEditCharge.setVisibility(View.GONE);
                                        }
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    tvCarManageEditPayMode.setText("临时车缴费");
                                }
                            }
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(EventBusMessage message) {
        if ("paymentOk".equals(message.getMessage())) {
            llCarManageChargePayList.setVisibility(View.GONE);
            Date startDate = new Date();
            boolean flag = false;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if (carManageEntity.getStartTime() != null && carManageEntity.getEndTime() != null) {
                try {
                    Date end_date = sdf.parse(carManageEntity.getEndTime());
                    if (end_date.getTime() > new Date().getTime()) {
                        startDate = end_date;
                        flag = true;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            calendar.add(Calendar.MONTH, +addMonth);
            String endDateStr = sdf.format(calendar.getTime());
            String startDateStr = sdf.format(startDate);
            if (flag) {
                startDateStr = carManageEntity.getStartTime();
            }
            carCharge(startDateStr,endDateStr);
        } else if ("carCharge".equals(message.getMessage())) {
            showToast("车辆充值成功");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       // EventBus.getDefault().post(new EventBusMessage<>("carAdd"));
        EventBus.getDefault().unregister(this);

    }

    /**
     * 拍照或者选取图片结果
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            //图片路径 同样视频地址也是这个 根据requestCode
            List<Uri> pathList = Matisse.obtainResult(data);
            List<String> _List = new ArrayList<>();
            for (Uri _Uri : pathList) {
                String _Path = FilePathUtil.getPathByUri(this, _Uri);
                File _File = new File(_Path);
                LogUtil.d("压缩前图片大小->" + _File.length() / 1024 + "k");
                _List.add(_Path);
            }
            compress(_List);
        }

    }

    //压缩图片
    private void compress(List<String> list) {
        String _Path = FilePathUtil.createPathIfNotExist("/" + SD_APP_DIR_NAME + "/" + PHOTO_DIR_NAME);
        LogUtil.d("_Path->" + _Path);
        Luban.with(this)
                .load(list)
                .ignoreBy(100)
                .setTargetDir(_Path)
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                    }
                })
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        LogUtil.d(" 压缩开始前调用，可以在方法内启动 loading UI");
                    }

                    @Override
                    public void onSuccess(File file) {
                        LogUtil.d(" 压缩成功后调用，返回压缩后的图片文件");
                        LogUtil.d("压缩后图片大小->" + file.length() / 1024 + "k");
                        LogUtil.d("getAbsolutePath->" + file.getAbsolutePath());
                        uploadPic(file.getAbsolutePath());
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                }).launch();
    }
    //上传照片
    private void uploadPic(final String path){
        Map<String,Object> requestMap=new HashMap<>();
        requestMap.put("resourceKey",carManageEntity.getId());
        requestMap.put("UploadFile",new File(path));

        RequestParam requestParam = new RequestParam(BASE_URL+FILE+"files-anon", HttpMethod.Upload);
        requestParam.setRequestMap(requestMap);

        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d(result);
                Glide.with(getApplicationContext()).load(new File(path)).into(tvCarManageEditCarPhoto);
                BaseEntity<FileEntity> baseEntity= JsonParse.parse(result,FileEntity.class);
                if(baseEntity.isSuccess()){
                    vehicleImageId = baseEntity.getResult().getId();
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

}

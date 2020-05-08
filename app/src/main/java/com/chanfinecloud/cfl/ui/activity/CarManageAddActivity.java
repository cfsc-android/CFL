package com.chanfinecloud.cfl.ui.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.inputmethodservice.KeyboardView;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
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
import com.chanfinecloud.cfl.entity.enumtype.CarColor;
import com.chanfinecloud.cfl.entity.enumtype.CarType;
import com.chanfinecloud.cfl.entity.enumtype.PlateColor;
import com.chanfinecloud.cfl.entity.enumtype.PlateType;
import com.chanfinecloud.cfl.entity.eventbus.EventBusMessage;
import com.chanfinecloud.cfl.http.HttpMethod;
import com.chanfinecloud.cfl.http.JsonParse;
import com.chanfinecloud.cfl.http.MyCallBack;
import com.chanfinecloud.cfl.http.ParamType;
import com.chanfinecloud.cfl.http.RequestParam;
import com.chanfinecloud.cfl.ui.base.BaseActivity;
import com.chanfinecloud.cfl.util.FileManagement;
import com.chanfinecloud.cfl.util.FilePathUtil;
import com.chanfinecloud.cfl.util.LogUtils;
import com.chanfinecloud.cfl.weidgt.SpinerPopWindow;
import com.chanfinecloud.cfl.weidgt.photopicker.PhotoPicker;
import com.chanfinecloud.cfl.weidgt.platenumberview.CarPlateNumberEditView;
import com.chanfinecloud.cfl.weidgt.platenumberview.PlateNumberKeyboardUtil;
import com.zhihu.matisse.Matisse;

import org.greenrobot.eventbus.EventBus;
import org.xutils.common.util.LogUtil;

import java.io.File;
import java.util.ArrayList;
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
import static com.chanfinecloud.cfl.config.Config.PHOTO_DIR_NAME;
import static com.chanfinecloud.cfl.config.Config.SD_APP_DIR_NAME;

public class CarManageAddActivity extends BaseActivity {

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
    @BindView(R.id.cpn_edit)
    CarPlateNumberEditView cpnEdit;
    @BindView(R.id.tv_car_manage_add_plate_color)
    TextView tvCarManageAddPlateColor;
    @BindView(R.id.tv_car_manage_add_plate_type)
    TextView tvCarManageAddPlateType;
    @BindView(R.id.tv_car_manage_add_car_color)
    TextView tvCarManageAddCarColor;
    @BindView(R.id.tv_car_manage_add_car_type)
    TextView tvCarManageAddCarType;
    @BindView(R.id.tv_car_manage_add_car_photo)
    ImageView tvCarManageAddCarPhoto;
    @BindView(R.id.btn_car_manage_submit)
    Button btnCarManageSubmit;
    @BindView(R.id.btn_parking_payment_search_mask)
    TextView btnParkingPaymentSearchMask;
    @BindView(R.id.keyboard_view)
    KeyboardView keyboardView;

    //private ArrayList<TImage> tImages = new ArrayList<>();// 添加图片集合
    private String carImagePath = "";


    private PlateNumberKeyboardUtil plateNumberKeyboardUtil;
    private SpinerPopWindow plateColorPop;
    private ArrayList<String> plateColorList = new ArrayList<>();
    private ArrayList<String> plateTypeList = new ArrayList<>();
    private ArrayList<String> carColorList = new ArrayList<>();
    private ArrayList<String> carTypeList = new ArrayList<>();

    private String plateColor, plateType, carColor, carType;
    private String resourceKey;

    public static final int REQUEST_CODE_CHOOSE = 0x001;
    public static final int REQUEST_CODE_CAPTURE = 0x002;
    CarEntity carEntity = new  CarEntity();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        checkAppPermission();
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initData() {
        setContentView(R.layout.activity_car_manage_add);
        ButterKnife.bind(this);

        toolbarTvTitle.setText("新增车辆");
        toolbarTvAction.setText("确定");
        toolbarBtnAction.setVisibility(View.GONE);
        initCarData();
        cpnEdit.setOnPlateNumberValid(new CarPlateNumberEditView.OnPlateNumberValid() {
            @Override
            public void plateNumberValid(boolean valid) {
                if (valid) {
                    toolbarTvAction.setVisibility(View.VISIBLE);
//                    btn_parking_payment_search_mask.setVisibility(View.GONE);
                } else {
                    toolbarTvAction.setVisibility(View.INVISIBLE);
//                    btn_parking_payment_search_mask.setVisibility(View.VISIBLE);
                }
            }
        });

        resourceKey = UUID.randomUUID().toString().replaceAll("-","");

    }


    private void initCarData() {

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


    @OnClick({R.id.toolbar_btn_back, R.id.tv_car_manage_add_plate_color, R.id.tv_car_manage_add_plate_type,
            R.id.tv_car_manage_add_car_color, R.id.tv_car_manage_add_car_type, R.id.tv_car_manage_add_car_photo,
            R.id.btn_car_manage_submit, R.id.btn_parking_payment_search_mask, R.id.toolbar_tv_action, R.id.cpn_edit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_btn_back:
                finish();
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
            case R.id.tv_car_manage_add_car_photo:

                if (permission) {
                    PhotoPicker.pick(CarManageAddActivity.this, 1, true, REQUEST_CODE_CHOOSE);
                } else {
                    showToast("相机或读写手机存储的权限被禁止！");
                }

                break;
            case R.id.btn_car_manage_submit:
            case R.id.toolbar_tv_action:
                saveCar();
                break;
            case R.id.cpn_edit:
                if (plateNumberKeyboardUtil == null) {
                    plateNumberKeyboardUtil = new PlateNumberKeyboardUtil(CarManageAddActivity.this, cpnEdit);
                    plateNumberKeyboardUtil.showKeyboard();
                } else {
                    plateNumberKeyboardUtil.showKeyboard();
                }
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


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (plateNumberKeyboardUtil != null && plateNumberKeyboardUtil.isShow()) {
                plateNumberKeyboardUtil.hideKeyboard();
            } else {
                finish();
            }
        }
        return false;
    }

    private void saveCar() {

        startProgressDialog("保存中...", true);
        Map<String, Object> map = new HashMap<>();
        String action = "basic/vehicleInfo/add";

        map.put("id", resourceKey);
        map.put("vehicleColor", carColor);
        map.put("vehicleType", carType);
        map.put("plateColor", plateColor);
        map.put("plateType", plateType);
        map.put("plateNO", cpnEdit.getPlateNumberText());
        map.put("vehicleImageId", carEntity.getVehicleImageId());
        if (FileManagement.getUserInfo() != null) {
            map.put("householdId", FileManagement.getUserInfo().getId() + "");
            map.put("ownerPhone", FileManagement.getUserInfo().getMobile() + "");
            map.put("ownerName", FileManagement.getUserInfo().getName()+ "");
            if (FileManagement.getUserInfo().getCurrentDistrict() != null) {
                map.put("projectId", FileManagement.getUserInfo().getCurrentDistrict().getProjectId() + "");
            }
        }


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

            @Override
            public void onCancelled(CancelledException cex) {
                super.onCancelled(cex);
                stopProgressDialog();
            }
        });
        sendRequest(requestParam, false);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (plateNumberKeyboardUtil != null && plateNumberKeyboardUtil.isShow()) {
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                Rect viewRect = new Rect();
                keyboardView.getGlobalVisibleRect(viewRect);
                if (!viewRect.contains((int) ev.getRawX(), (int) ev.getRawY())) {
                    plateNumberKeyboardUtil.hideKeyboard();
                    return true;
                }
            }
        }
        return super.dispatchTouchEvent(ev);
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
        requestMap.put("resourceKey",resourceKey);
        requestMap.put("UploadFile",new File(path));

        RequestParam requestParam = new RequestParam(BASE_URL+FILE+"files-anon", HttpMethod.Upload);
        requestParam.setRequestMap(requestMap);

        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d(result);
                Glide.with(getApplicationContext()).load(new File(path)).into(tvCarManageAddCarPhoto);
                BaseEntity<FileEntity> baseEntity= JsonParse.parse(result,FileEntity.class);
                if(baseEntity.isSuccess()){
                    carEntity.setVehicleImageId(baseEntity.getResult().getId());
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

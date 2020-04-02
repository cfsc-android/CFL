package com.chanfinecloud.cfl.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.entity.BaseEntity;
import com.chanfinecloud.cfl.entity.eventbus.EventBusMessage;
import com.chanfinecloud.cfl.entity.smart.OrderTypeEntity;
import com.chanfinecloud.cfl.entity.smart.UserType;
import com.chanfinecloud.cfl.entity.smart.WorkflowType;
import com.chanfinecloud.cfl.http.HttpMethod;
import com.chanfinecloud.cfl.http.JsonParse;
import com.chanfinecloud.cfl.http.MyCallBack;
import com.chanfinecloud.cfl.http.ParamType;
import com.chanfinecloud.cfl.http.RequestParam;
import com.chanfinecloud.cfl.ui.activity.minefeatures.WorkflowListActivity;
import com.chanfinecloud.cfl.ui.base.BaseActivity;
import com.chanfinecloud.cfl.util.FileManagement;
import com.chanfinecloud.cfl.util.FilePathUtil;
import com.chanfinecloud.cfl.util.LogUtils;
import com.chanfinecloud.cfl.util.LynActivityManager;
import com.chanfinecloud.cfl.util.PermissionsUtils;
import com.chanfinecloud.cfl.weidgt.WheelDialog;
import com.chanfinecloud.cfl.weidgt.imagepreview.ImagePreviewListAdapter;
import com.chanfinecloud.cfl.weidgt.imagepreview.ImageViewInfo;
import com.chanfinecloud.cfl.weidgt.imagepreview.PreviewBuilder;
import com.chanfinecloud.cfl.weidgt.photopicker.PhotoPicker;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.jaredrummler.materialspinner.MaterialSpinnerAdapter;
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
import static com.chanfinecloud.cfl.config.Config.FILE;
import static com.chanfinecloud.cfl.config.Config.PHOTO_DIR_NAME;
import static com.chanfinecloud.cfl.config.Config.SD_APP_DIR_NAME;
import static com.chanfinecloud.cfl.config.Config.WORKORDER;

public class RepairsActivity extends BaseActivity {

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
    @BindView(R.id.add_order_et_address)
    EditText addOrderEtAddress;
    @BindView(R.id.add_order_et_contact)
    EditText addOrderEtContact;
    @BindView(R.id.add_order_et_contact_tel)
    EditText addOrderEtContactTel;
    @BindView(R.id.add_order_et_plain_time)
    EditText addOrderEtPlainTime;
    @BindView(R.id.add_order_et_remark)
    EditText addOrderEtRemark;
    @BindView(R.id.add_order_rlv_pic)
    RecyclerView addOrderRlvPic;
    @BindView(R.id.add_order_ms_problem_type)
    MaterialSpinner addOrderMsProblemType;
    @BindView(R.id.add_order_ms_project_type)
    MaterialSpinner addOrderMsProjectType;
    @BindView(R.id.login_btn_login)
    Button loginBtnLogin;

    private static final String[] permission={Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private static final int REQUEST_CODE_CHOOSE=0x001;
    private List<ImageViewInfo> dataList=new ArrayList<>();
    private GridLayoutManager mGridLayoutManager;
    private ImagePreviewListAdapter adapter;
    private boolean permissionFlag;
    private List<OrderTypeEntity> orderTypeEntityList;
    private List<String> problemData=new ArrayList<>();
    private List<OrderTypeEntity> problemTypeData=new ArrayList<>();
    private List<String> projectData=new ArrayList<>();
    private List<OrderTypeEntity> projectTypeData=new ArrayList<>();
    private int problemValue,projectValue;
    private String resourceKey;
    private MaterialSpinnerAdapter projectDataAdapter;
    private WheelDialog wheeldialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initData() {
        setContentView(R.layout.activity_repairs);
        ButterKnife.bind(this);

        toolbarTvTitle.setText("创建工单");
        //默认不弹出软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        PermissionsUtils.getInstance().checkPermissions(this, permission, new PermissionsUtils.IPermissionsResult() {
            @Override
            public void success() {
                LogUtil.d("申请权限通过");
                permissionFlag=true;
            }

            @Override
            public void fail() {
                LogUtil.d("申请权限未通过");
                permissionFlag=false;
            }
        });

        dataList.add(new ImageViewInfo("plus"));
        addOrderRlvPic.setLayoutManager(mGridLayoutManager = new GridLayoutManager(this,4));
        adapter=new ImagePreviewListAdapter(this, R.layout.item_workflow_image_perview_list,dataList);
        addOrderRlvPic.setAdapter(adapter);
        addOrderRlvPic.addOnItemTouchListener(new com.chad.library.adapter.base.listener.OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                if(position==dataList.size()-1){
                    if(permissionFlag){
                        PhotoPicker.pick(RepairsActivity.this,10,true,REQUEST_CODE_CHOOSE);
                    }else{
                        showToast("相机或读写手机存储的权限被禁止！");
                    }
                }else{
                    List<ImageViewInfo> data=new ArrayList<>();
                    for (int i = 0; i < dataList.size()-1; i++) {
                        data.add(dataList.get(i));
                    }
                    computeBoundsBackward(mGridLayoutManager.findFirstVisibleItemPosition());
                    PreviewBuilder.from(RepairsActivity.this)
                            .setImgs(data)
                            .setCurrentIndex(position)
                            .setSingleFling(true)
                            .setType(PreviewBuilder.IndicatorType.Number)
                            .start();
                }
            }
        });


        orderTypeEntityList= FileManagement.getOrderType();
        initProblemSpinner();
        resourceKey= UUID.randomUUID().toString().replaceAll("-","");
        addOrderEtAddress.setText(FileManagement.getUserInfo().getAncestor());
        addOrderEtContact.setText(FileManagement.getUserInfo().getName());
        addOrderEtContactTel.setText(FileManagement.getUserInfo().getMobile());

    }

    @OnClick({R.id.toolbar_btn_back, R.id.login_btn_login, R.id.add_order_et_plain_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_btn_back:
                finish();
                break;
            case R.id.login_btn_login:
                addOrderSubmit();
                break;
            case R.id.add_order_et_plain_time:
                //addOrderEtPlainTime.setClickable(true);
                //addOrderEtPlainTime.setFocusableInTouchMode(true);
                wheeldialog = new WheelDialog(this, R.style.Dialog_Floating, new WheelDialog.OnDateTimeConfirm() {
                    @Override
                    public void returnData(String dateText, String dateValue) {
                        wheeldialog.cancel();
                        addOrderEtPlainTime.setText(dateText);

                    }
                });
                wheeldialog.show();
                break;
        }
    }

    private void initProblemSpinner(){
        for (int i = 0; i < orderTypeEntityList.size(); i++) {
            if(orderTypeEntityList.get(i).getParentId()==0){
                problemData.add(orderTypeEntityList.get(i).getName());
                problemTypeData.add(orderTypeEntityList.get(i));
            }
        }
        problemValue=problemTypeData.get(0).getId();
        addOrderMsProblemType.setItems(problemData);
        addOrderMsProblemType.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                LogUtils.d(position+"_"+id+"_"+item.toString());
                problemValue=problemTypeData.get(position).getId();
                initProjectSpinner();
            }
        });
        initProjectSpinner();
    }

    private void initProjectSpinner(){
        projectData.clear();
        projectTypeData.clear();
        for (int i = 0; i < orderTypeEntityList.size(); i++) {
            if(orderTypeEntityList.get(i).getParentId()==problemValue){
                projectData.add(orderTypeEntityList.get(i).getName());
                projectTypeData.add(orderTypeEntityList.get(i));
                projectValue=orderTypeEntityList.get(i).getId();
            }
        }
        projectValue=projectTypeData.get(0).getId();
        if(projectDataAdapter==null){
            projectDataAdapter=new MaterialSpinnerAdapter(this,projectData);
            addOrderMsProjectType.setAdapter(projectDataAdapter);
            addOrderMsProjectType.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                @Override
                public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                    LogUtils.d(position+"_"+id+"_"+item.toString());
                    projectValue=projectTypeData.get(position).getId();
                }
            });
        }else{
            projectDataAdapter.notifyDataSetChanged();
            addOrderMsProjectType.setSelectedIndex(0);
        }

    }

    //计算返回的边界
    private void computeBoundsBackward(int firstCompletelyVisiblePos) {
        for (int i = firstCompletelyVisiblePos; i < adapter.getItemCount(); i++) {
            View itemView = mGridLayoutManager.findViewByPosition(i);
            Rect bounds = new Rect();
            if (itemView != null) {
                ImageView imageView = itemView.findViewById(R.id.iiv_item_image_preview);
                imageView.getGlobalVisibleRect(bounds);
            }
            adapter.getItem(i).setBounds(bounds);
        }
    }



    private void addOrderSubmit(){
        if(TextUtils.isEmpty(addOrderEtAddress.getText())){
            showToast("请输入维修地点");
            return;
        }
        if(TextUtils.isEmpty(addOrderEtContact.getText())){
            showToast("请输入联系人");
            return;
        }
        if(TextUtils.isEmpty(addOrderEtContactTel.getText())){
            showToast("请输入联系电话");
            return;
        }
        if(TextUtils.isEmpty(addOrderEtRemark.getText())){
            showToast("请输入问题描述");
            return;
        }

        Map<String,Object> requestMap=new HashMap<>();
        requestMap.put("address",addOrderEtAddress.getText().toString());
        requestMap.put("createType", UserType.Household.getType());
        requestMap.put("expectTime",addOrderEtPlainTime.getText().toString()+":00");
        requestMap.put("householdId",FileManagement.getUserInfo().getId());
        requestMap.put("linkMan",addOrderEtContact.getText().toString());
        requestMap.put("mobile",addOrderEtContactTel.getText().toString());
        requestMap.put("problemDesc",addOrderEtRemark.getText().toString());
        requestMap.put("projectId",FileManagement.getUserInfo().getRoomList().get(0).getProjectId());
        requestMap.put("reportType", UserType.Household.getType());
        requestMap.put("roomId",FileManagement.getUserInfo().getRoomList().get(0).getId());
        requestMap.put("typeId",projectValue);
        if(dataList.size()>1)
            requestMap.put("problemResourceKey",resourceKey);


        RequestParam requestParam = new RequestParam(BASE_URL+WORKORDER+"workflow/api/workorder", HttpMethod.Post);
        requestParam.setRequestMap(requestMap);
        requestParam.setParamType(ParamType.Json);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d(result);
                BaseEntity baseEntity= JsonParse.parse(result);
                if(baseEntity.isSuccess()){
                    if(LynActivityManager.getInstance().getActivityByClass(WorkflowListActivity.class)!=null){
                        EventBus.getDefault().post(new EventBusMessage<>("WorkListRefresh"));
                    }else{
                        Bundle bundle=new Bundle();
                        bundle.putSerializable("workflowType", WorkflowType.Order);
                        startActivity(WorkflowListActivity.class,bundle);
                    }
                    finish();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_CHOOSE&&resultCode==RESULT_OK){
            //图片路径 同样视频地址也是这个 根据requestCode
            List<Uri> pathList = Matisse.obtainResult(data);
            List<String> _List = new ArrayList<>();
            for (Uri _Uri : pathList)
            {
                String _Path = FilePathUtil.getPathByUri(this,_Uri);
                File _File = new File(_Path);
                LogUtil.d("压缩前图片大小->" + _File.length() / 1024 + "k");
                _List.add(_Path);
            }
            compress(_List);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionsUtils.getInstance().onRequestPermissionsResult(this,requestCode,permissions,grantResults);
    }

    //压缩图片
    private void compress(List<String> list){
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
//                        mUploadPic(file.getAbsolutePath());
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                }).launch();
    }

    //上传照片
    private void uploadPic(final String path){
        Map<String,String> requestMap=new HashMap<>();
        requestMap.put("resourceKey",resourceKey);
        RequestParam requestParam = new RequestParam(BASE_URL+FILE+"files-anon", HttpMethod.Upload);
        requestParam.setRequestMap(requestMap);
        requestParam.setFilepath(path);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d(result);
                dataList.add(dataList.size()-1,new ImageViewInfo(path));
                adapter.notifyDataSetChanged();
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

package com.chanfinecloud.cfl.ui.fragment.minefragment;

import android.animation.Animator;
import android.app.Activity;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.entity.BaseEntity;
import com.chanfinecloud.cfl.entity.UserEntity;
import com.chanfinecloud.cfl.entity.WorkflowViewEntity;
import com.chanfinecloud.cfl.entity.eventbus.EventBusMessage;
import com.chanfinecloud.cfl.entity.smart.EmergencyLevelType;
import com.chanfinecloud.cfl.entity.smart.OperationInfoEntity;
import com.chanfinecloud.cfl.entity.smart.WorkflowFormContentEntity;
import com.chanfinecloud.cfl.entity.smart.WorkflowFormEntity;
import com.chanfinecloud.cfl.entity.smart.WorkflowOrderActionType;
import com.chanfinecloud.cfl.entity.smart.WorkflowType;
import com.chanfinecloud.cfl.http.HttpMethod;
import com.chanfinecloud.cfl.http.JsonParse;
import com.chanfinecloud.cfl.http.MyCallBack;
import com.chanfinecloud.cfl.http.ParamType;
import com.chanfinecloud.cfl.http.RequestParam;
import com.chanfinecloud.cfl.ui.activity.ComplainDetailActivity;
import com.chanfinecloud.cfl.ui.activity.RepairsDetailActivity;
import com.chanfinecloud.cfl.ui.base.BaseFragment;
import com.chanfinecloud.cfl.util.AnimationUtil;
import com.chanfinecloud.cfl.util.LogUtils;
import com.chanfinecloud.cfl.util.SharedPreferencesManage;
import com.chanfinecloud.cfl.weidgt.WheelDialog;
import com.chanfinecloud.cfl.weidgt.imagepreview.ImagePreviewListAdapter;
import com.chanfinecloud.cfl.weidgt.imagepreview.ImageViewInfo;
import com.chanfinecloud.cfl.weidgt.photopicker.PhotoPicker;
import com.google.gson.Gson;
import com.idlestar.ratingstar.RatingStarView;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.greenrobot.eventbus.EventBus;
import org.xutils.common.util.LogUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.chanfinecloud.cfl.config.Config.BASE_URL;
import static com.chanfinecloud.cfl.config.Config.BASIC;
import static com.chanfinecloud.cfl.config.Config.WORKORDER;

/**
 *
 */
public class WorkflowActionFragment extends BaseFragment {

    public WorkflowActionFragment() {
    }
    public WorkflowActionFragment newInstance(Bundle bundle) {
        WorkflowActionFragment fragment = new WorkflowActionFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @BindView(R.id.workflow_action_name)
    TextView workflowActionName;
    @BindView(R.id.workflow_action_toggle_icon)
    ImageView workflowActionToggleIcon;
    @BindView(R.id.workflow_action_toggle)
    LinearLayout workflowActionToggle;
    @BindView(R.id.workflow_action_content_ll)
    LinearLayout workflowActionContentLl;
    @BindView(R.id.workflow_action_content)
    FrameLayout workflowActionContent;
    private Unbinder unbinder;

    private Activity context;
    private String action,businessId;
    private boolean permission;
    private List<OperationInfoEntity> operationInfoEntities;
    private boolean toggle=true;

    private List<ImageViewInfo> picList=new ArrayList<>();
    private ImagePreviewListAdapter adapter;

    private WorkflowType workflowType;
    private String fromJson;
    private Map<String, WorkflowViewEntity> workflowViewEntityMap=new HashMap<>();
    private WheelDialog wheeldialog;

    @Override
    protected void initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_workflow_action, null);
        setContentView(view);
        unbinder = ButterKnife.bind(this, view);

        context = getActivity();
        action = getArguments().getString("action");
        permission=getArguments().getBoolean("permission");
        businessId = getArguments().getString("businessId");
        workflowType= (WorkflowType) getArguments().getSerializable("workflowType");
        operationInfoEntities = (List<OperationInfoEntity>) getArguments().getSerializable("operationInfos");
        WorkflowOrderActionType[] types=WorkflowOrderActionType.values();
        for (int i = 0; i < types.length; i++) {
            if(action.equals(types[i].name())){
                fromJson=types[i].getForm();
            }
        }
    }

    @Override
    protected void initData() {

        workflowActionName.setText(action);
        initActionView();
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick({R.id.workflow_action_toggle_icon, R.id.workflow_action_toggle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.workflow_action_toggle_icon:
                break;
            case R.id.workflow_action_toggle:
                toggle=!toggle;
                int width = View.MeasureSpec.makeMeasureSpec(0,
                        View.MeasureSpec.UNSPECIFIED);
                int height = View.MeasureSpec.makeMeasureSpec(0,
                        View.MeasureSpec.UNSPECIFIED);
                workflowActionContent.measure(width, height);
                if(toggle){
                    workflowActionContent.setVisibility(View.VISIBLE);
                    AnimationUtil.startTransYAnimation(workflowActionContent,workflowActionContent.getMeasuredHeight(),0,200,new AnimationUtil.AnimationListener(){
                        @Override
                        public void onAnimationStart(Animator animation, boolean isReverse) {
                            super.onAnimationStart(animation, isReverse);
                            AnimationUtil.startRotateAnimation(workflowActionToggle,180,0,200);

                            AnimationUtil.startRotateAnimation(workflowActionName,180,0,200);


                        }
                    });
                }else{
                    AnimationUtil.startTransYAnimation(workflowActionContent,0,workflowActionContent.getMeasuredHeight(),200,new AnimationUtil.AnimationListener(){
                        @Override
                        public void onAnimationStart(Animator animation, boolean isReverse) {
                            super.onAnimationStart(animation, isReverse);
                            AnimationUtil.startRotateAnimation(workflowActionToggle,0,180,200);

                            AnimationUtil.startRotateAnimation(workflowActionName,0,180,200);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation, boolean isReverse) {
                            super.onAnimationEnd(animation, isReverse);
                            workflowActionContent.setVisibility(View.GONE);
                        }
                    });
                }
                break;
        }
    }

    //照片选择回填
    public void setPicData(ImageViewInfo imageViewInfo){
        picList.add(picList.size()-1,imageViewInfo);
        adapter.notifyDataSetChanged();
    }
    //初始化流程表单视图-assign(人员指派)
    private WorkflowViewEntity initAssignView(String label){
        return null;
    }
    //初始化流程表单视图-emergencyLevel(紧急程度)
    private WorkflowViewEntity initEmergencyLevelView(String label){
        View v = LayoutInflater.from(context).inflate(R.layout.workflow_action_spinner,null);
        TextView labelView=v.findViewById(R.id.action_spinner_label);
        labelView.setText(label);
        MaterialSpinner spinner=v.findViewById(R.id.action_spinner_spinner);
        spinner.setItems(EmergencyLevelType.getEmergencyLevelTypeList());
        spinner.setTag(EmergencyLevelType.getEmergencyLevelTypeValue(0));
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                view.setTag(EmergencyLevelType.getEmergencyLevelTypeValue(position));
            }
        });
        WorkflowViewEntity<MaterialSpinner> workflowView=new WorkflowViewEntity<>(v);
        workflowView.setLabel(labelView);
        workflowView.setContent(spinner);
        return workflowView;
    }
    //初始化流程表单视图-date
    private WorkflowViewEntity initDateView(String label){
        View v = LayoutInflater.from(context).inflate(R.layout.workflow_action_date,null);
        TextView labelView=v.findViewById(R.id.action_date_label);
        labelView.setText(label);
        final TextView date=v.findViewById(R.id.action_date_content);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wheeldialog = new WheelDialog(context, R.style.Dialog_Floating, new WheelDialog.OnDateTimeConfirm() {
                    @Override
                    public void returnData(String dateText, String dateValue) {
                        wheeldialog.cancel();
                        date.setText(dateText);
                    }
                });
                wheeldialog.show();
            }
        });
        WorkflowViewEntity<TextView> workflowView=new WorkflowViewEntity<>(v);
        workflowView.setLabel(labelView);
        workflowView.setContent(date);
        return workflowView;
    }
    //初始化流程表单视图-photo
    private WorkflowViewEntity initPhotoView(String label){
        View v = LayoutInflater.from(context).inflate(R.layout.workflow_action_photo,null);
        TextView labelView=v.findViewById(R.id.action_photo_label);
        labelView.setText(label);
        RecyclerView photo=v.findViewById(R.id.action_photo_content);
        picList.add(new ImageViewInfo("plus"));
        adapter=new ImagePreviewListAdapter(context,R.layout.item_workflow_image_perview_list,picList);
        photo.setLayoutManager(new GridLayoutManager(context,4));
        photo.setAdapter(adapter);
        photo.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                if(position==picList.size()-1){
                    if(permission){
                        int request_code=0;
                        switch (workflowType){
                            case Order:
                                request_code= RepairsDetailActivity.REQUEST_CODE_CHOOSE;
                                break;
                            case Complain:
                                request_code= ComplainDetailActivity.REQUEST_CODE_CHOOSE;
                                break;
                        }
                        PhotoPicker.pick(context,10,true,request_code );
                    }else{
                        showToast("相机或读写手机存储的权限被禁止！");
                    }
                }
            }
        });

        WorkflowViewEntity<RecyclerView> workflowView=new WorkflowViewEntity<>(v);
        workflowView.setLabel(labelView);
        workflowView.setContent(photo);
        return workflowView;
    }
    //初始化流程表单视图-remark
    private WorkflowViewEntity initRemarkView(String label){
        View v = LayoutInflater.from(context).inflate(R.layout.workflow_action_remark,null);
        TextView labelView=v.findViewById(R.id.action_remark_label);
        labelView.setText(label);
        EditText remark=v.findViewById(R.id.action_remark_content);
        WorkflowViewEntity<EditText> workflowView=new WorkflowViewEntity<>(v);
        workflowView.setLabel(labelView);
        workflowView.setContent(remark);
        return workflowView;
    }
    //初始化流程表单视图-input
    private WorkflowViewEntity initInputView(String label){
        View v = LayoutInflater.from(context).inflate(R.layout.workflow_action_input,null);
        TextView labelView=v.findViewById(R.id.action_text_label);
        labelView.setText(label);
        EditText text=v.findViewById(R.id.action_text_content);
        WorkflowViewEntity<EditText> workflowView=new WorkflowViewEntity<>(v);
        workflowView.setLabel(labelView);
        workflowView.setContent(text);
        return workflowView;
    }
    //初始化流程表单视图-rate
    private WorkflowViewEntity initRateView(String label){
        View v = LayoutInflater.from(context).inflate(R.layout.workflow_action_rate,null);
        TextView labelView=v.findViewById(R.id.action_rate_label);
        labelView.setText(label);
        RatingStarView rateView=v.findViewById(R.id.action_rate_content);
        WorkflowViewEntity<RatingStarView> workflowView=new WorkflowViewEntity<>(v);
        workflowView.setLabel(labelView);
        workflowView.setContent(rateView);
        return workflowView;
    }
    //初始化流程表单视图-submit
    private WorkflowViewEntity initSubmitButtonView(String btnText){
        View v = LayoutInflater.from(context).inflate(R.layout.workflow_action_submit,null);
        Button submitView=v.findViewById(R.id.action_button_submit);
        submitView.setText(btnText);
        WorkflowViewEntity workflowView=new WorkflowViewEntity(v);
        workflowView.setSubmit(submitView);
        return workflowView;
    }
    //初始化流程表单视图-choose
    private WorkflowViewEntity initChooseButtonView(String acceptText,String rejectText){
        View v = LayoutInflater.from(context).inflate(R.layout.workflow_action_button,null);
        Button acceptView=v.findViewById(R.id.action_button_accept);
        acceptView.setText(acceptText);
        Button rejectView=v.findViewById(R.id.action_button_reject);
        rejectView.setText(rejectText);
        WorkflowViewEntity workflowView=new WorkflowViewEntity(v);
        workflowView.setAccept(acceptView);
        workflowView.setReject(rejectView);
        return workflowView;
    }


    private List<WorkflowFormContentEntity> initFormContent(){
        Gson gson=new Gson();
        WorkflowFormEntity formEntity=gson.fromJson(fromJson, WorkflowFormEntity.class);
        List<WorkflowFormContentEntity> formContent= formEntity.getFormContent();
        Collections.sort(formContent,new Comparator<WorkflowFormContentEntity>() {
            @Override
            public int compare(WorkflowFormContentEntity o1, WorkflowFormContentEntity o2) {
                return o1.getSort()-o2.getSort();
            }
        });
        return formContent;
    }


    //初始化流程表单
    private void initActionView(){
        List<WorkflowFormContentEntity> formContent= initFormContent();
        for (int i = 0; i < formContent.size(); i++) {
            WorkflowViewEntity workflowViewEntity=null;
            if("choose".equals(formContent.get(i).getFormItemType())){
                String label=formContent.get(i).getFormItemLabel();
                String[] labels=label.split(",");
                if(labels.length>=2){
                    workflowViewEntity=initChooseButtonView(labels[0],labels[1]);
                }else{
                    workflowViewEntity=initChooseButtonView("接受","不接受");
                }
                workflowViewEntity.getAccept().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (int j = 0; j < operationInfoEntities.size(); j++) {
                            if(operationInfoEntities.get(j).getChoose()==1){
                                initHandleMap(j);
                            }
                        }
                    }
                });
                workflowViewEntity.getReject().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (int j = 0; j < operationInfoEntities.size(); j++) {
                            if(operationInfoEntities.get(j).getChoose()==0){
                                initHandleMap(j);
                            }
                        }
                    }
                });
            }else if("assignId".equals(formContent.get(i).getFormItemType())){
                workflowViewEntity=initAssignView(formContent.get(i).getFormItemLabel());
            }else if("resourceKey".equals(formContent.get(i).getFormItemType())){
                workflowViewEntity=initPhotoView(formContent.get(i).getFormItemLabel());
            }else if("remark".equals(formContent.get(i).getFormItemType())||"content".equals(formContent.get(i).getFormItemType())){
                workflowViewEntity=initRemarkView(formContent.get(i).getFormItemLabel());
            }else if("emergencyLevel".equals(formContent.get(i).getFormItemType())){
                workflowViewEntity=initEmergencyLevelView(formContent.get(i).getFormItemLabel());
            }else if("date".equals(formContent.get(i).getFormItemType())){
                workflowViewEntity=initDateView(formContent.get(i).getFormItemLabel());
            }else if("rate".equals(formContent.get(i).getFormItemType())){
                workflowViewEntity=initRateView(formContent.get(i).getFormItemLabel());
            }else if("manualCost".equals(formContent.get(i).getFormItemType())||"materialCost".equals(formContent.get(i).getFormItemType())||"fee".equals(formContent.get(i).getFormItemType())){
                workflowViewEntity=initInputView(formContent.get(i).getFormItemLabel());
                EditText et= (EditText) workflowViewEntity.getContent();
                et.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
            }else if("submit".equals(formContent.get(i).getFormItemType())){
                workflowViewEntity=initSubmitButtonView(formContent.get(i).getFormItemLabel());
                workflowViewEntity.getSubmit().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initHandleMap(0);
                    }
                });
            }
            if(workflowViewEntity!=null){
                workflowActionContentLl.addView(workflowViewEntity.getView());
                workflowViewEntityMap.put(formContent.get(i).getFormItemType(),workflowViewEntity);
            }
        }
    }

    private void initHandleMap(int operationIndex){
        Map<String,Object> map=new HashMap<>();
        for (Map.Entry<String,WorkflowViewEntity> entry:workflowViewEntityMap.entrySet()) {
            WorkflowViewEntity workflowViewEntity=entry.getValue();
            if("resourceKey".equals(entry.getKey())){
                switch (workflowType){
                    case Order:
                        map.put("resourceKey",((RepairsDetailActivity)context).resourceKey);
                        break;
                    case Complain:
                        map.put("resourceKey",((ComplainDetailActivity)context).resourceKey);
                        break;
                }
            }else if("remark".equals(entry.getKey())){
                EditText remark= (EditText) workflowViewEntity.getContent();
                map.put("remark",remark.getText().toString());
            }else if("input_number".equals(entry.getKey())){
                EditText remark= (EditText) workflowViewEntity.getContent();
                map.put("remark",remark.getText().toString());
            }else if("fee".equals(entry.getKey())){
                EditText fee= (EditText) workflowViewEntity.getContent();
                map.put("fee",Double.parseDouble(fee.getText().toString()));
            }else if("manualCost".equals(entry.getKey())){
                EditText manualCost= (EditText) workflowViewEntity.getContent();
                map.put("manualCost",Double.parseDouble(manualCost.getText().toString()));
            }else if("materialCost".equals(entry.getKey())){
                EditText materialCost= (EditText) workflowViewEntity.getContent();
                map.put("materialCost",Double.parseDouble(materialCost.getText().toString()));
            }else if("assignId".equals(entry.getKey())){
                MaterialSpinner assignId= (MaterialSpinner) workflowViewEntity.getContent();
                map.put("assignId",assignId.getTag().toString());
            }else if("content".equals(entry.getKey())){
                EditText content= (EditText) workflowViewEntity.getContent();
                map.put("content",content.getTag().toString());
            }else if("date".equals(entry.getKey())){
                TextView date= (TextView) workflowViewEntity.getContent();
                map.put("date",date.getTag().toString());
            }else if("emergencyLevel".equals(entry.getKey())){
                MaterialSpinner emergencyLevel= (MaterialSpinner) workflowViewEntity.getContent();
                map.put("emergencyLevel",emergencyLevel.getTag().toString());
            }else if("fee".equals(entry.getKey())){
                EditText fee= (EditText) workflowViewEntity.getContent();
                map.put("fee",Double.parseDouble(fee.getText().toString()));
            }
        }
        map.put("businessId",businessId);
        map.put("choose",operationInfoEntities.get(operationIndex).getChoose());
        map.put("operationDesc",operationInfoEntities.get(operationIndex).getDesc());
        map.put("operationId",operationInfoEntities.get(operationIndex).getId());
        map.put("operationName",operationInfoEntities.get(operationIndex).getName());

        Gson gson=new Gson();
        LogUtil.d(gson.toJson(map));
        workflowAction(map);
    }

    private void workflowAction(Map<String,Object> map){

        RequestParam requestParam = new RequestParam(BASE_URL+WORKORDER+"workflow/api/push/"+workflowType.getType(), HttpMethod.Post);
        requestParam.setRequestMap(map);
        requestParam.setParamType(ParamType.Json);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d(result);
                BaseEntity baseEntity= JsonParse.parse(result);
                if(baseEntity.isSuccess()){
                    EventBus.getDefault().post(new EventBusMessage<>("WorkflowActionRefresh"));
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

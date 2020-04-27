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

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.entity.BaseEntity;
import com.chanfinecloud.cfl.entity.WorkflowViewEntity;
import com.chanfinecloud.cfl.entity.eventbus.EventBusMessage;
import com.chanfinecloud.cfl.entity.smart.ComplainDetailsEntity;
import com.chanfinecloud.cfl.entity.smart.EmergencyLevelType;
import com.chanfinecloud.cfl.entity.smart.OperationInfoEntity;
import com.chanfinecloud.cfl.entity.smart.OrderDetailsEntity;
import com.chanfinecloud.cfl.entity.smart.WorkflowFormContentEntity;
import com.chanfinecloud.cfl.entity.smart.WorkflowProcessesEntity;
import com.chanfinecloud.cfl.entity.smart.WorkflowType;
import com.chanfinecloud.cfl.entity.smart.WorkflowViewTagEntity;
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
import com.chanfinecloud.cfl.util.Utils;
import com.chanfinecloud.cfl.weidgt.EditTextFilterView;
import com.chanfinecloud.cfl.weidgt.WheelDialog;
import com.chanfinecloud.cfl.weidgt.imagepreview.ImagePreviewListAdapter;
import com.chanfinecloud.cfl.weidgt.imagepreview.ImageViewInfo;
import com.chanfinecloud.cfl.weidgt.photopicker.PhotoPicker;
import com.google.gson.Gson;
import com.idlestar.ratingstar.RatingStarView;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.greenrobot.eventbus.EventBus;
import org.xutils.common.util.LogUtil;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.chanfinecloud.cfl.config.Config.BASE_URL;
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
    private WorkflowProcessesEntity workflowProcesses;
    private List<OperationInfoEntity> operationInfo;
    private ComplainDetailsEntity complainDetail;
    private OrderDetailsEntity orderDetail;
    private boolean toggle=true;


    private List<ImageViewInfo> picList=new ArrayList<>();
    private ImagePreviewListAdapter adapter;

    private WorkflowType workflowType;
    private List<WorkflowViewTagEntity> workflowViewTagList =new ArrayList<>();


    private WheelDialog wheeldialog;

    @Override
    protected void initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_workflow_action, null);
        setContentView(view);
        unbinder = ButterKnife.bind(this, view);

        context = getActivity();
        permission=getArguments().getBoolean("permission");
        action = getArguments().getString("action");
        businessId = getArguments().getString("businessId");
        workflowType= (WorkflowType) getArguments().getSerializable("workflowType");
        if(workflowType==WorkflowType.Complain){
            complainDetail= (ComplainDetailsEntity) getArguments().getSerializable("complainDetail");
        }else{
            orderDetail= (OrderDetailsEntity) getArguments().getSerializable("orderDetail");
        }
        workflowProcesses = (WorkflowProcessesEntity) getArguments().getSerializable("workflowProcesses");
        operationInfo=workflowProcesses.getOperationInfos();

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

    @OnClick({ R.id.workflow_action_toggle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
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
                            AnimationUtil.startRotateAnimation(workflowActionToggleIcon,180,0,200);
                        }

                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);
                            AnimationUtil.startRotateAnimation(workflowActionToggleIcon,180,0,200);
                        }
                    });
                }else{
                    AnimationUtil.startTransYAnimation(workflowActionContent,0,workflowActionContent.getMeasuredHeight(),200,new AnimationUtil.AnimationListener(){
                        @Override
                        public void onAnimationStart(Animator animation, boolean isReverse) {
                            super.onAnimationStart(animation, isReverse);
                            AnimationUtil.startRotateAnimation(workflowActionToggleIcon,0,180,200);

                        }

                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);
                            AnimationUtil.startRotateAnimation(workflowActionToggleIcon,0,180,200);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation, boolean isReverse) {
                            super.onAnimationEnd(animation, isReverse);
                            workflowActionContent.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            workflowActionContent.setVisibility(View.GONE);
                        }
                    });
                }
                break;
        }
    }

    /**
     * 照片选择回填
     * @param imageViewInfo 图片实体
     */
    public void setPicData(ImageViewInfo imageViewInfo){
        picList.add(picList.size()-1,imageViewInfo);
        adapter.notifyDataSetChanged();
    }

    /**
     * 初始化流程表单视图-spinner
     * @param label 表单item名称
     * @param type 下拉类型(主管、员工、紧急程度)
     * @return WorkflowViewEntity
     */
    private WorkflowViewEntity initSpinner(String label,String type){
        View v = LayoutInflater.from(context).inflate(R.layout.workflow_action_spinner,null);
        TextView labelView=v.findViewById(R.id.action_spinner_label);
        labelView.setText(label);
        MaterialSpinner spinner=v.findViewById(R.id.action_spinner_spinner);
        List<String> adapterData=new ArrayList<>();
        if(type.equals("emergency")){
            spinner.setItems(EmergencyLevelType.getEmergencyLevelTypeList());
            spinner.setTag(EmergencyLevelType.getEmergencyLevelTypeValue(0));
            spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                @Override
                public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                    view.setTag(EmergencyLevelType.getEmergencyLevelTypeValue(position));
                }
            });
        }
        WorkflowViewEntity<MaterialSpinner> workflowView=new WorkflowViewEntity<>(v);
        workflowView.setLabel(labelView);
        workflowView.setContent(spinner);
        return workflowView;
    }

    /**
     * 初始化流程表单视图-date
     * @param label 表单item名称
     * @return WorkflowViewEntity
     */
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

    /**
     * 初始化流程表单视图-photo
     * @param label 表单item名称
     * @return WorkflowViewEntity
     */
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
                                request_code=RepairsDetailActivity.REQUEST_CODE_CHOOSE;
                                break;
                            case Complain:
                                request_code= ComplainDetailActivity.REQUEST_CODE_CHOOSE;
                                break;
                        }
                        PhotoPicker.pick(context,1,true, request_code);
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

    /**
     * 初始化流程表单视图-remark
     * @param label 表单item名称
     * @return WorkflowViewEntity
     */
    private WorkflowViewEntity initRemarkView(String label){
        View v = LayoutInflater.from(context).inflate(R.layout.workflow_action_remark,null);
        TextView labelView=v.findViewById(R.id.action_remark_label);
        labelView.setText(label);
        EditTextFilterView remark=v.findViewById(R.id.action_remark_content);
        WorkflowViewEntity<EditText> workflowView=new WorkflowViewEntity<>(v);
        workflowView.setLabel(labelView);
        workflowView.setContent(remark);
        return workflowView;
    }

    /**
     * 初始化流程表单视图-input
     * @param label 表单item名称
     * @return WorkflowViewEntity
     */
    private WorkflowViewEntity initInputView(String label){
        View v = LayoutInflater.from(context).inflate(R.layout.workflow_action_input,null);
        TextView labelView=v.findViewById(R.id.action_text_label);
        labelView.setText(label);
        EditTextFilterView text=v.findViewById(R.id.action_text_content);
        WorkflowViewEntity<EditText> workflowView=new WorkflowViewEntity<>(v);
        workflowView.setLabel(labelView);
        workflowView.setContent(text);
        return workflowView;
    }

    /**
     * 初始化流程表单视图-rate
     * @param label 表单item名称
     * @return WorkflowViewEntity
     */
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

    /**
     * 初始化流程表单视图-rate
     * @param btnText 提交按钮文字
     * @return WorkflowViewEntity
     */
    private WorkflowViewEntity initSubmitButtonView(String btnText){
        View v = LayoutInflater.from(context).inflate(R.layout.workflow_action_submit,null);
        Button submitView=v.findViewById(R.id.action_button_submit);
        submitView.setText(btnText);
        WorkflowViewEntity workflowView=new WorkflowViewEntity(v);
        workflowView.setSubmit(submitView);
        return workflowView;
    }

    /**
     * 初始化流程表单视图-choose
     * @param acceptText accept按钮文字
     * @param rejectText reject按钮文字
     * @return WorkflowViewEntity
     */
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

    /**
     * 初始化流程表单视图-text
     * @param label 表单item名称
     * @return WorkflowViewEntity
     */
    private WorkflowViewEntity initTextView(String label){
        View v = LayoutInflater.from(context).inflate(R.layout.workflow_action_text,null);
        TextView labelView=v.findViewById(R.id.action_text_label);
        labelView.setText(label);
        TextView text=v.findViewById(R.id.action_text_content);
        WorkflowViewEntity<TextView> workflowView=new WorkflowViewEntity<>(v);
        workflowView.setLabel(labelView);
        workflowView.setContent(text);
        return workflowView;
    }

    /**
     * 初始化流程表单
     */
    private void initActionView(){
        List<WorkflowFormContentEntity> formContent= workflowProcesses.getFormContent();
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
                        for (int j = 0; j < operationInfo.size(); j++) {
                            if(operationInfo.get(j).getChoose()==1){
                                initHandleMap(j);
                            }
                        }
                    }
                });
                workflowViewEntity.getReject().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (int j = 0; j < operationInfo.size(); j++) {
                            if(operationInfo.get(j).getChoose()==0){
                                initHandleMap(j);
                            }
                        }
                    }
                });
            }else if("director".equals(formContent.get(i).getFormItemType())){
                workflowViewEntity=initSpinner(formContent.get(i).getFormItemLabel(),"director");
            }else if("employee".equals(formContent.get(i).getFormItemType())){
                workflowViewEntity=initSpinner(formContent.get(i).getFormItemLabel(),"employee");
            }else if("emergency".equals(formContent.get(i).getFormItemType())){
                workflowViewEntity=initSpinner(formContent.get(i).getFormItemLabel(),"emergency");
            }else if("pick_photo".equals(formContent.get(i).getFormItemType())){
                workflowViewEntity=initPhotoView(formContent.get(i).getFormItemLabel());
            }else if("textarea".equals(formContent.get(i).getFormItemType())){
                workflowViewEntity=initRemarkView(formContent.get(i).getFormItemLabel());
            }else if("pick_date".equals(formContent.get(i).getFormItemType())){
                workflowViewEntity=initDateView(formContent.get(i).getFormItemLabel());
            }else if("rate".equals(formContent.get(i).getFormItemType())){
                workflowViewEntity=initRateView(formContent.get(i).getFormItemLabel());
            }else if("input_number".equals(formContent.get(i).getFormItemType())){
                workflowViewEntity=initInputView(formContent.get(i).getFormItemLabel());
                EditText et= (EditText) workflowViewEntity.getContent();
                //et.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                et.setInputType(0x00002002);
            }else if("text".equals(formContent.get(i).getFormItemType())){
                workflowViewEntity=initTextView(formContent.get(i).getFormItemLabel());
                TextView textView= (TextView) workflowViewEntity.getContent();
                String textValue;
                if(workflowType==WorkflowType.Complain){
                    textValue= (String) getFieldValueByName(formContent.get(i).getFieldName(),complainDetail);
                }else{
                    textValue= (String) getFieldValueByName(formContent.get(i).getFieldName(),orderDetail);
                }
                textView.setText(textValue);
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
                workflowViewTagList.add(new WorkflowViewTagEntity(formContent.get(i).getFormItemType(),formContent.get(i).getFormKey(),workflowViewEntity));
            }
        }
    }

    /**
     * 根据属性名获取属性值
     * */
    private Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[] {});
            Object value = method.invoke(o, new Object[] {});
            return value;
        } catch (Exception e) {
            LogUtils.d(e.getMessage());
            return null;
        }
    }

    /**
     * 初始化表单参数处理
     * @param operationIndex choose index
     */
    private void initHandleMap(int operationIndex){
        Map<String,Object> map=new HashMap<>();

        for (int i = 0; i < workflowViewTagList.size(); i++) {
            WorkflowViewTagEntity workflowViewTag=workflowViewTagList.get(i);
            if("pick_photo".equals(workflowViewTag.getFormType())){
                switch (workflowType){
                    case Order:
                        map.put(workflowViewTag.getFormKey(),((RepairsDetailActivity)context).resourceKey);
                        break;
                    case Complain:
                        map.put(workflowViewTag.getFormKey(),((ComplainDetailActivity)context).resourceKey);
                        break;
                }
            }else if("textarea".equals(workflowViewTag.getFormType())){
                EditTextFilterView remark= (EditTextFilterView) workflowViewTag.getWorkflowView().getContent();
                map.put(workflowViewTag.getFormKey(),remark.getText().toString());
            }else if("director".equals(workflowViewTag.getFormType())||"employee".equals(workflowViewTag.getFormType())||"emergency".equals(workflowViewTag.getFormType())){
                MaterialSpinner assignId= (MaterialSpinner) workflowViewTag.getWorkflowView().getContent();
                map.put(workflowViewTag.getFormKey(),assignId.getTag().toString());
            }else if("pick_date".equals(workflowViewTag.getFormType())){
                TextView date= (TextView) workflowViewTag.getWorkflowView().getContent();
                map.put(workflowViewTag.getFormKey(),date.getTag().toString());
            }else if("rate".equals(workflowViewTag.getFormType())){
                RatingStarView rate= (RatingStarView) workflowViewTag.getWorkflowView().getContent();
                map.put(workflowViewTag.getFormKey(),rate.getRating());
            }else if("input_number".equals(workflowViewTag.getFormType())){
                EditTextFilterView manualCost= (EditTextFilterView) workflowViewTag.getWorkflowView().getContent();
                if (Utils.isEmpty(manualCost.getText().toString())){
                    showToast("请填写正确的数值内容");
                    return;
                }else{
                    map.put(workflowViewTag.getFormKey(),Double.parseDouble(manualCost.getText().toString()));
                }

            }
        }
        map.put("businessId",businessId);
        map.put("choose",operationInfo.get(operationIndex).getChoose());
        map.put("operationDesc",operationInfo.get(operationIndex).getDesc());
        map.put("operationId",operationInfo.get(operationIndex).getId());
        map.put("operationName",operationInfo.get(operationIndex).getName());

        Gson gson=new Gson();
        LogUtil.d(gson.toJson(map));
        workflowAction(map);
    }
    /**
     * 初始化流程处理请求参数
     * @param map
     */
    private void workflowAction(Map<String,Object> map){
        RequestParam requestParam=new RequestParam(BASE_URL+WORKORDER+"workflow/api/push/"+workflowType.getType(),HttpMethod.Post);
        requestParam.setRequestMap(map);
        requestParam.setParamType(ParamType.Json);
        requestParam.setCallback(myCallBack);
        sendRequest(requestParam,true);
    }

    /**
     * 请求回调
     */
    private MyCallBack myCallBack=new MyCallBack<String>(){
        @Override
        public void onSuccess(String result) {
            super.onSuccess(result);
            LogUtils.d("result",result);
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

        @Override
        public void onFinished() {
            super.onFinished();
            stopProgressDialog();
        }
    };

}
